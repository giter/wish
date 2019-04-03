package com.wish.common.shiro;

import com.wish.bean.UserBean;
import com.wish.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * create by ff on 2018/6/21
 */
public class NormalRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 获取授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = userService.getRoles(username);
        authorizationInfo.setRoles(roles);//获取所有角色
        authorizationInfo.setStringPermissions(userService.getPremissions(username));//获取所有权限
        return authorizationInfo;
    }

    /**
     * 获取认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        UserBean userBean = userService.findByUsername(username);
        if (userBean == null){
            throw new UnknownAccountException();//没找到账号
        }

        if (Boolean.TRUE.equals(userBean.getUseflag())){
            throw new LockedAccountException();//账号锁定
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userBean.getUsername(), //用户名
                userBean.getPassword(), //密码,凭证
                ByteSource.Util.bytes(userBean.getSalt()),//salt，凭证的盐
                getName()  //realm name
        );
        return authenticationInfo;//系统正确的身份信息
    }
}
