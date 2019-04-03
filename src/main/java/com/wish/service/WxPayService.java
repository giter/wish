package com.wish.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.wish.WxConfig;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxPayService {

    private WXPay wxPay = new WXPay(WxConfig.getPayInstance());
    /**
     * 微信统一下单
     * @return
     */
    public JSONObject unifiedorder(Map<String,String> data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",0);
        try {
            String tradeType = data.get("trade_type");
            Map<String, String> result = wxPay.unifiedOrder(data);//微信sdk，统一下单接口
            System.out.println("统一下单结果");
            System.out.println(JSON.toJSONString(result));
            if (!"SUCCESS".equals(result.get("result_code"))){
                jsonObject.put("object",result);
                jsonObject.put("msg","统一下单失败");
                return jsonObject;
            }
            if (!WXPayUtil.isSignatureValid(result, WxConfig.SECRET)){
                jsonObject.put("msg","验证签名失败");
                return jsonObject;
            }
            jsonObject.put("result",0);
            if ("JSAPI".equals(tradeType)){//小程序，微信公众号
                Map<String,String> tt = new HashMap<>();
                tt.put("appId",result.get("appid"));
                tt.put("nonceStr",result.get("nonce_str"));
                tt.put("timeStamp",System.currentTimeMillis()/1000+"");
                tt.put("package","prepay_id="+result.get("prepay_id"));
                tt.put("signType","MD5");
                String sign = WXPayUtil.generateSignature(tt, WxConfig.SECRET);
                tt.put("paySign",sign);
                jsonObject.put("object",tt);
            }
            if ("NATIVE".equals(tradeType)){//扫码
                jsonObject.put("url",result.get("code_url"));
            }
            if ("APP".equals(tradeType)){//app
                jsonObject.put("object",result);
            }
            if ("MWEB".equals(tradeType)){//H5
                jsonObject.put("url",result.get("mweb_url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }




}
