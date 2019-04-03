package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wish.Config;
import com.wish.WxConfig;
import com.wish.bean.BuyerBean;
import com.wish.bean.BuyerShowBean;
import com.wish.common.util.JsonUtil;
import com.wish.common.util.OkhttpUtil;
import com.wish.dao.BuyerDao;
import com.wish.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/wxLogin")
public class WxLogin {

    private static String access_token = null;

    @Autowired
    private BuyerService buyerService;

    /**
     * 微信服务器验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping("/checkSignature")
    public Object checkSignature(String signature, Long timestamp, String nonce, String echostr) {
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        return echostr;
    }


    @RequestMapping("/getToken")
    @ResponseBody
    public Object getToken(@RequestBody JSONObject data, HttpSession session) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Config.APPID
                + "&secret=" + Config.SECRET + "&js_code=" + data.getString("code")
                + "&grant_type=authorization_code";
        String str = OkhttpUtil.get(url);
        System.out.println(str);
        JSONObject jsonObject = JSON.parseObject(str);
        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");

        BuyerBean buyerBean = new BuyerBean();
        buyerBean.setAvatarUrl(data.getString("avatarUrl"));
        buyerBean.setNickName(data.getString("nickName"));
        buyerBean.setSex(Integer.parseInt(data.getString("gender")));
        buyerBean.setOpenId(openId);
        BuyerBean bean = buyerService.login(buyerBean);
        session.setAttribute("buyer",bean);

        return JsonUtil.genSuccess(bean);
    }

    @RequestMapping("/share")
    public String getShare(Model model, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url2 = requestUri.substring(contextPath.length());
        request.setAttribute("wxConfig", WxConfig.sign(url2, null));
        return "share";
    }


    private String reflushToken(String reflushToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WxConfig.APPID + "&grant_type=refresh_token&refresh_token=" + reflushToken;
        String str = OkhttpUtil.get(url);
        System.out.println(str);
        return str;
    }

    private String getUserInfo(String access_token, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId + "&lang=zh_CN";
        String str = OkhttpUtil.get(url);
        System.out.println(str);
        return str;
    }



}
