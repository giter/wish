package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.OrderService;
import com.wish.util.ExcelUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/9/14
 */
@RestController
@RequestMapping("/sale")
public class SaleApi {
    @Resource
    private OrderService orderService;

    //    查看销售报表
    @RequestMapping("/querySale")
    public Object querySale(@RequestBody JSONObject data, HttpSession session){
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
        List list = orderService.querySale(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }


    @RequestMapping("/export")
    public Object export(String machineId,String startTime,String endTime,String tradeNo, HttpSession session, HttpServletResponse response) throws IOException {
        UserBean userBean = (UserBean) session.getAttribute("user");
        Map<String,Object> condition = new HashMap<>();
        condition.put("tradeNo",tradeNo);
        condition.put("machineId",machineId);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        Subject subject = SecurityUtils.getSubject();

        if (subject.hasRole("agent")){//如果是代理商则只能看到自己
            condition.put("userId",userBean.getId());
        }

        List list = orderService.querySale(condition);
        ExcelUtil.initDownResponse("销售报表.xls",response);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("items",list);
        ExcelUtil.generate("/sale.xls",response.getOutputStream(),map);
        return null;
    }
}
