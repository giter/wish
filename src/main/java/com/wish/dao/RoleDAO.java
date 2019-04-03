package com.wish.dao;

import com.wish.bean.RoleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/7/26
 */
public interface RoleDAO {

    RoleBean findById(Integer id);

    List<RoleBean> findAll();

    List<RoleBean> query(Map<String,Object> condition);

    void save(RoleBean roleBean);

    void update(RoleBean roleBean);

    void deletes(Integer[] ids);
}
