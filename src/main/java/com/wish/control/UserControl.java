package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/7/24
 */
@RestController
@RequestMapping("/user")
public class UserControl {

    @Resource
    private UserService userService;

    @RequestMapping("/query")
    public Object getUsers(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
//        角色过滤
        List<String> roles = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("user:director")){
            roles.add("director");
        }
        if (subject.isPermitted("user:sale")){
            roles.add("sale");
        }
        if (subject.isPermitted("user:agent")){
            roles.add("agent");
        }
        if (subject.isPermitted("user:operator")){
            roles.add("operator");
        }
        if (subject.isPermitted("user:admin")){
            roles.add("admin");
        }
        condition.put("roles",roles);

        PageHelper.startPage(pageNum, pageSize);
        List list = userService.findByCondition(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

    @RequestMapping("/add")
    public Object addUser(@RequestBody JSONObject data, HttpSession session){
        try {
            Integer roleId = data.getInteger("roleId");
            System.out.println(roleId);
            UserBean userBean = JsonUtil.jsonTojava(data,UserBean.class);
            UserBean userBean1 = (UserBean) session.getAttribute("user");
            userBean.setInsertId(userBean1.getId());
            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole("admin")){
                userService.createUser(userBean,roleId);
            }else if (subject.hasRole("sale")){
                if (roleId==3){
                    userService.createUser(userBean,roleId);
                }else {
                    return JsonUtil.genError("无权限创建该角色");
                }
            }else {
                return JsonUtil.genError("无权限创建用户");
            }



            return JsonUtil.genSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.genError(e.toString());
        }
    }

    @RequestMapping("/deletes")
    public Object delUsers(@RequestBody Integer[] ids){
        userService.deleteUsers(ids);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/update")
    public Object updateUser(@RequestBody UserBean userBean,HttpSession session){
        UserBean userBean1 = (UserBean) session.getAttribute("user");
        userBean.setInsertId(userBean1.getId());
        userBean.setPassword(null);
        userService.update(userBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/findById")
    public Object findById(@RequestBody Integer id){
        UserBean userBean = userService.findById(id);
        return JsonUtil.genSuccess(userBean);
    }

    //用户名联想
    @RequestMapping("/getAgent")
    public Object getAgenByName(){
        List list = userService.getAgent();

        return JsonUtil.genSuccess(list);
    }
}
