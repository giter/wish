package com.wish.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by ff on 2018/9/17
 */
public class CookieUtil {

    public static void updateCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = new Cookie("JLoginId",System.currentTimeMillis()+"");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(request.getSession().getMaxInactiveInterval());
        boolean flag = true;
        if (cookies.length>0){
            for (Cookie c:cookies){
                if(c.getName().equalsIgnoreCase("JLoginId")){
                    c.setMaxAge(0);
                    response.addCookie(cookie);
                    flag=false;
                    break;
                }
            }
        }
        if (flag){
            response.addCookie(cookie);
        }
    }
}
