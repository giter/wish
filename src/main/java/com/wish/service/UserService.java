package com.wish.service;

import com.wish.bean.UserBean;
import com.wish.common.util.PasswordUtil;
import com.wish.dao.UserDAO;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

/**
 * create by ff on 2018/6/23
 */
@Service
public class UserService {

    @Resource
    private UserDAO userDAO;

    public Set<String> getRoles(String userName){

        return userDAO.findRoles(userName);
    }

    public Set<String> getPremissions(String userName){
        return userDAO.findPermissions(userName);
    }


    public List<Map<String,Object>> getPremissionByUserId(Integer userId){
        List<Map<String,Object>> preList = userDAO.getPermissions(userId);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> tempMap=null;
        for (Map<String,Object> map:preList){
            Integer id = MapUtils.getInteger(map,"id");
            String permission = MapUtils.getString(map,"permission");
            String description = MapUtils.getString(map,"description");
            Integer supId = MapUtils.getInteger(map,"sup_id");
            if (supId==null){
                tempMap = new HashMap<>();
                tempMap.put("id",id);
                tempMap.put("desc",description);
                tempMap.put("permission",permission);
                tempMap.put("supId",supId);
                tempMap.put("list",new ArrayList<Map<String,Object>>());
                list.add(tempMap);
            }else {
                HashMap listMap = new HashMap();
                listMap.put("id",id);
                listMap.put("desc",description);
                listMap.put("permission",permission);
                listMap.put("supId",supId);
                if (tempMap==null) continue;
                List<Map> list1 = (List<Map>) tempMap.get("list");
                list1.add(listMap);
            }
        }
        return list;
    }


    public UserBean findByUsername(String userName) {
        return userDAO.findUser(userName);
    }

    /**
     * 新建用户
     * @param userBean
     * @return
     */
    @Transactional
    public UserBean createUser(UserBean userBean,Integer roleId) {
        PasswordUtil.encryptPassword(userBean);//生成数据库密码
        userBean.setUseflag(1);
        boolean result = userDAO.createUser(userBean);
        System.out.println("create:"+result);
        userDAO.addrole(userBean.getId(),roleId);
        return userBean;
    }

    public List<UserBean> findByCondition(Map condition){
        List list = userDAO.findByCondition(condition);
        return list;
    }


    public void deleteUsers(Integer[] ids) {
        userDAO.deleteUsers(ids);
    }

    public void update(UserBean userBean) {
        userDAO.update(userBean);
    }

    public UserBean findById(Integer id){
        return  userDAO.findById(id);
    }

    public List getAgent() {
        return userDAO.getAgent();
    }
}
