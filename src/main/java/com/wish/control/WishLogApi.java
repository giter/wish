package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.WishLogService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/9/21
 */
@RestController
@RequestMapping("/wishLog")
public class WishLogApi {

    @Resource
    private WishLogService wishLogService;

    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = wishLogService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

}
