package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.wish.bean.PrizeLogBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.dao.PrizeLogDao;
import com.wish.util.ExcelUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/8/7
 */
@RestController
@RequestMapping("/prizeLog")
public class PrizeLogApi {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private PrizeLogDao prizeLogDao;

//    查询兑奖状态
    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = prizeLogDao.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

//    发货
    @RequestMapping("/send")
    public Object send(@RequestBody Integer prizeLogId, HttpSession session){

        PrizeLogBean prizeLogBean = prizeLogDao.findById(prizeLogId);
        if (prizeLogBean.getStatus()==2){
            UserBean userBean = (UserBean) session.getAttribute("user");
            prizeLogBean.setUpdateId(userBean.getId());
            prizeLogBean.setStatus(3);//将状态改为已发货
            logger.info("send prize"+ JSON.toJSONString(prizeLogBean));
            prizeLogDao.update(prizeLogBean);
            return JsonUtil.genSuccess();
        }else {
            return JsonUtil.genError("用户还未兑奖");
        }

    }

    @RequestMapping("/export")
    public Object export(@RequestParam String addressName,
                         @RequestParam Integer prizeId,
                         @RequestParam Integer status,
                         HttpServletResponse response) throws IOException {
        Map<String,Object> condition = new HashMap<>();
        if (StringUtil.isNotEmpty(addressName)) {
            condition.put("addressName", addressName);
        }
        condition.put("prizeId",prizeId);
        condition.put("status",status);
        List<Map<String,Object>> list = prizeLogDao.query(condition);
        for (Map<String,Object> map:list){
            Integer st = MapUtils.getInteger(map,"status");
            if (st==1){
                map.put("status","未兑奖");
            }else if (st==2){
                map.put("status","已兑奖");
            }else if (st==3){
                map.put("status","已发货");
            }else if (st==4){
                map.put("status","已过期");
            }
        }
        ExcelUtil.initDownResponse("兑换记录.xls",response);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("items",list);
        ExcelUtil.generate("/prizelog.xls",response.getOutputStream(),map);
        return null;
    }








}
