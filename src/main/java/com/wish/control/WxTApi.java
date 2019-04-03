package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.wish.bean.BuyerBean;
import com.wish.bean.PayOrderBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.BuyerService;
import com.wish.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 *
 * 微信登录，购买测试使用api
 */

@RestController
@RequestMapping("/wxt")
public class WxTApi {

    @Resource
    private BuyerService buyerService;
    @Resource
    private OrderService orderService;

    @RequestMapping("/login")
    public Object login(@RequestBody BuyerBean buyerBean, HttpSession session){
        BuyerBean buyer = buyerService.login(buyerBean);
        return JsonUtil.genSuccess(buyer.getId());
    }

    /**
     *
     * @param data
     * @return
     *
     *   * machineId,
     * list:[{
     *     slotNo:1
     *     num:1
     * }]
     */
    @RequestMapping("/prePay")
    public Object prePay(@RequestBody JSONObject data,HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        PayOrderBean payOrderBean = orderService.prePayOrder(data,buyerBean.getId());
        return JsonUtil.genSuccess(payOrderBean);
    }

    @RequestMapping("/payFinish")
    public Object payFinish(@RequestBody JSONObject data){
        String tradeNo = data.getString("tradeNo");
        orderService.payFinish(tradeNo);
        return JsonUtil.genSuccess();
    }



}
