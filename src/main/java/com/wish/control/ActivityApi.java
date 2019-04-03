package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.ActivityBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.ActivityService;import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/9/21
 */
@RestController
@RequestMapping("/activity")
public class ActivityApi {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ActivityService activityService;

    @RequestMapping("/save")
    public Object save(@RequestBody ActivityBean activityBean, HttpSession session){
        UserBean userBean= (UserBean) session.getAttribute("user");
        activityBean.setInsertId(userBean.getId());
        activityService.save(activityBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = activityService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }
}
