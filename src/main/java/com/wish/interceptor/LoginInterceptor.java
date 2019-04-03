package com.wish.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wish.common.util.JsonUtil;
import com.wish.util.CookieUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginInterceptor implements HandlerInterceptor {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        Subject subject = SecurityUtils.getSubject();

        if (url.startsWith("/auth/") || url.startsWith("/machine/")
                || url.startsWith("/wx") || url.startsWith("/file") || url.startsWith("/api/")) {
            return true;
        }

        if (!subject.isAuthenticated()) {//未登录,来自机器的相关请求
//                response.sendRedirect("/wish/403.html");
//                return false;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
//                    去除session
                    request.getSession().invalidate();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("result",-1);
                    jsonObject.put("msg","未登录");
                    out = response.getWriter();
                    out.append(jsonObject.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    return false;
                }

        }else {
            //已登录，进行权限过滤
            if(subject.isPermitted(url)){
                return true;
            }


        }
        System.out.println("logininterceptor:"+url);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }



}
