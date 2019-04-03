package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.OrderService;
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
 * create by ff on 2018/8/16
 */
@RestController
@RequestMapping("/order")
public class OrderApi {

    @Resource
    private OrderService orderService;

//查看订单
    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data,HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        Map<String,Object> condition = data.getJSONObject("condition");
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("agent")){//如果是代理商则只能看到自己
            condition.put("userId",userBean.getId());
        }
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List list = orderService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

}
