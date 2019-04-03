package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * create by ff on 2018/6/23
 */
@RestController
@RequestMapping("/auth")
public class AuthControl {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private UserService userService;

    @RequestMapping(value = "/subLogin")
    public Object subLogin(@RequestBody UserBean userBean, HttpSession session,HttpServletResponse response,HttpServletRequest request){
        logger.info(JSON.toJSONString(userBean));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userBean.getUsername(), userBean.getPassword());
        try {
            subject.login(token);
            UserBean userBean1 = userService.findByUsername(userBean.getUsername());
            session.setAttribute("user",userBean1);
            List list = userService.getPremissionByUserId(userBean1.getId());
//            CookieUtil.updateCookie(request,response);
            return JsonUtil.genSuccess(list);
        } catch (AuthenticationException e) {
            session.invalidate();
            e.printStackTrace();
            return JsonUtil.genError(e.toString());
        }
    }


    @RequestMapping("/logout")
    public Object logout(HttpSession session){
        session.removeAttribute("user");
        session.invalidate();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return JsonUtil.genSuccess();
    }


    @RequestMapping("/test1")
    public Object test1(){


        return JsonUtil.genSuccess();
    }


}
