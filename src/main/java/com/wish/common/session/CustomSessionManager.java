package com.wish.common.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * create by ff on 2018/6/23
 */
public class CustomSessionManager extends DefaultWebSessionManager {


    public CustomSessionManager() {
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
//        cookie.setMaxAge(180);
        cookie.setHttpOnly(false); //more secure, protects against XSS attacks
        setSessionIdCookie(cookie);
        setSessionIdCookieEnabled(true);
        setSessionIdUrlRewritingEnabled(true);
    }

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request=null;
        if (sessionKey instanceof WebSessionKey){
            request =  ((WebSessionKey)sessionKey).getServletRequest();
        }
        if (request!=null && sessionId!=null){
            Session session = (Session) request.getAttribute(sessionId.toString());
            if (session != null){
                return session;
            }
        }

        Session session = super.retrieveSession(sessionKey);
        if (request!=null && sessionId!=null){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}
