package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.MachineBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.ProductService;
import com.wish.service.MachineService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/7/28
 */
@RestController
@RequestMapping("/machineManager")
public class MachineManagerApi {

    @Resource
    private MachineService machineService;

    @Resource
    private ProductService productService;

    /**
     * 只能运维人员添加机器
     * @param machineBean
     * @param session
     * @return
     */
    @RequestMapping("/save")
    public Object save(@RequestBody MachineBean machineBean, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("operator") || subject.hasRole("admin") ){
            machineBean.setInsertId(userBean.getId());
            MachineBean machineBean1 = machineService.findById(machineBean.getId());
            if (machineBean1!=null){
                return JsonUtil.genError("该机器已经存在");
            }
            machineService.save(machineBean);
            return JsonUtil.genSuccess();
        }else {
            return JsonUtil.genError("无权添加代理商");
        }

    }

    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data,HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> condition = data.getJSONObject("condition");
        if (subject.hasRole("agent") ){
            condition.put("userId",userBean.getId());
        }
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List list = machineService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }


    @RequestMapping("/findById")
    public Object findById(@RequestBody String id){
        MachineBean machineBean = machineService.findById(id);
        return JsonUtil.genSuccess(machineBean);
    }

    @RequestMapping("/update")
    public Object update(@RequestBody MachineBean machineBean,HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        machineBean.setUpdateId(userBean.getId());
        machineService.update(machineBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/deletes")
    public Object deletes(@RequestBody String[] ids){
        machineService.deletes(ids);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/findGoodsById")
    public Object findGoodsById(@RequestBody String id){
        List list = productService.findByMachineId(id);
        return JsonUtil.genSuccess(list);
    }


}
