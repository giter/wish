package com.wish.service;

import com.wish.bean.RoleBean;
import com.wish.dao.RoleDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/7/26
 */
@Service
public class RoleService {
    @Resource
    private RoleDAO roleDAO;


    public RoleBean findById(Integer id){
        return roleDAO.findById(id);
    }

    public List<RoleBean> findAll(){
        return roleDAO.findAll();
    }

    public List<RoleBean> query(Map<String,Object> condition){
        return roleDAO.query(condition);
    }

    public void save(RoleBean roleBean){
        roleDAO.save(roleBean);
    }

    public void update(RoleBean roleBean){
        roleDAO.update(roleBean);
    }

    public void deletes(Integer[] ids){
        roleDAO.deletes(ids);
    }
}
