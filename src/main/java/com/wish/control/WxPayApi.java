package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.wish.Config;
import com.wish.WxConfig;
import com.wish.bean.BuyerBean;
import com.wish.bean.PayOrderBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.BuyerService;
import com.wish.service.OrderService;
import com.wish.service.UserService;
import com.wish.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信小程序接口
 * create by ff on 2018/7/31
 */
@RestController
@RequestMapping("/wxpay")
public class WxPayApi {
    Logger logger = Logger.getLogger(this.getClass().getName());



    @Resource
    private WxPayService wxPayService;
    @Resource
    private OrderService orderService;
    @Resource
    private BuyerService buyerService;


    private WXPay wxPay = new WXPay(WxConfig.getPayInstance());
    private Integer expire = 30 * 60 * 1000;//30分钟


    /**
     * 支付结果异步通知
     *
     * @param string
     * @return
     */
    @RequestMapping("/notify")
    public Object mynotify(@RequestBody String string) {//支付成功之后的回调，可以在这边写支付成功后的逻辑处理
        System.out.println("================================================");
        System.out.println("notify");
        System.out.println(string);
        try {
            Map map = WXPayUtil.xmlToMap(string);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
            if (WXPayUtil.isSignatureValid(map, WxConfig.SECRET)) {
                System.out.println("系统验证签名正确");
            }

            String tradeNo = jsonObject.getString("out_trade_no");
            orderService.payFinish(tradeNo);

            System.out.println("商户订单号"+jsonObject.getString("out_trade_no"));
            System.out.println("支付完成时间"+jsonObject.getString("time_end"));
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(string);
        System.out.println("================================================");
        return 1;
    }


    /**
     * 下单
     *  machineId,
     * list:[{
     *     slotNo:1
     *     num:1
     * }]
     *
     * @return
     */
    @RequestMapping(value = "/unifiedorder")
    public Object unifiedorder(@RequestBody JSONObject json,HttpServletRequest request) {
        logger.info("下单："+ json);
        Integer buyerId = json.getInteger("buyerId");
        BuyerBean buyerBean = buyerService.findById(buyerId);
        PayOrderBean payOrderBean = orderService.prePayOrder(json,buyerId);

        HashMap<String, String> data = new HashMap<>();
        //必须部分
        data.put("body", payOrderBean.getNames().get(0));//商品的简单描述
        data.put("out_trade_no", payOrderBean.getPayTradeNo());//订单号，32个字符以内，只能是数字，字母
        data.put("detail", payOrderBean.getNames().get(0));//商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
        data.put("product_id", ""+payOrderBean.getId());//商品id，扫码支付必传。
        data.put("total_fee", ""+payOrderBean.getTotalFee());//订单总金额，单位为分
        data.put("fee_type", "CNY");//币种
        data.put("spbill_create_ip", getRemoteHost(request));//终端ip,
        data.put("notify_url", WxConfig.NOTIFYURL);//异步接收微信支付结果的回调通知
        data.put("trade_type", "JSAPI");//交易类型，JSAPI--公众号支付，NATIVE--原生扫码支付，APP--app支付，MWEB--H5支付
        data.put("sign_type", "MD5");//签名加密方式，默认是MD5
        data.put("openid", buyerBean.getOpenId());//交易类型位公众号支付时必须


        //非必须部分
//        data.put("attach", "附加数据");//会在查询api和支付通知api中返回，可以做为自定义参数使用
//        data.put("goods_tag", "满100减20");//订单优惠标记
        data.put("limit_pay", "no_credit");//no_credit--限制用户不能使用信用卡支付
        //交易有效时间
        Date now = new Date();
        Date wil = new Date(now.getTime() + expire);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        data.put("time_start", simpleDateFormat.format(now));//交易起始时间，格式为yyyyMMddHHmmss,或者基于北京时间的时间戳
        data.put("time_expire", simpleDateFormat.format(wil));//交易结束时间，即订单有效时间
        data.put("scene_info", "{\"store_info\" : {\n" +
                "\"id\": \"1\",\n" +
                "\"name\": \"心愿机\",\n" +
                "\"area_code\": \"440305\",\n" +
                "\"address\": \"心愿有限公司\" }}\n");//上报实际门店信息


        return wxPayService.unifiedorder(data);
    }


    /**
     * 积分购买
     * @param data
     * @return
     *      *   * machineId,
     * list:[{
     *     slotNo:1
     *     num:1
     * }]
     */
    @RequestMapping("/pointPay")
    public Object pointPay(@RequestBody JSONObject data){
        logger.info("积分购买："+ data);
        BuyerBean buyerBean = buyerService.findById(data.getInteger("buyerId"));
        JSONArray list = data.getJSONArray("list");
        if ((list.size()*Config.REDUCEPOINT)>buyerBean.getPoints()){
            return JsonUtil.genError("积分不足");
        }
        PayOrderBean payOrderBean = orderService.pointPay(data,buyerBean.getId());
        return JsonUtil.genSuccess(payOrderBean);
    }


    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }


    private void prepay(){
        String nonceStr = WXPayUtil.generateNonceStr();

    }


}
