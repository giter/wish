package com.wish.dao;

import com.wish.bean.UserBean;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by ff on 2017/12/4
 */
public interface UserDAO {

    Set<String> findRoles(@Param("username") String username);

    Set<String> findPermissions(@Param("username") String username);

    UserBean findUser(@Param("username") String username);

   boolean createUser(@Param("userBean") UserBean userBean);

    List<Map<String,Object>> findByCondition(@Param("condition")Map<String,Object> condition);

    void deleteUsers(@Param("ids")Integer[] ids);

    void update(@Param("userBean")UserBean userBean);

    UserBean findById(@Param("id")Integer id);

    void addrole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    List<Map<String,Object>> getPermissions(Integer userId);

    List<Map<String,Object>> getAgent();
}
