package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.RoleBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/7/26
 */
@RestController
@RequestMapping("/role")
public class RoleApi {
    @Resource
    private RoleService roleService;

    @RequestMapping("/findById")
    public Object findById(@RequestBody Integer id){
        RoleBean roleBean = roleService.findById(id);
        return JsonUtil.genSuccess(roleBean);
    }

    @RequestMapping("/findAll")
    public Object findAll(){
        List<RoleBean> list = roleService.findAll();
        return JsonUtil.genSuccess(list);
    }

    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject date){
        JSONObject page = date.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = date.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = roleService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

    @RequestMapping("/save")
    public Object save(@RequestBody RoleBean roleBean, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        roleBean.setInsertId(userBean.getId());
        roleService.save(roleBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/update")
    public Object update(@RequestBody RoleBean roleBean,HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        roleBean.setUpdateId(userBean.getId());
        roleService.update(roleBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/deletes")
    public Object deletes(@RequestBody Integer[] ids){
        roleService.deletes(ids);
        return JsonUtil.genSuccess();
    }
}
