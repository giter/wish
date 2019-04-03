package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.PrizeBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.PrizeService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/8/6
 */
@RestController
@RequestMapping("/prize")
public class PrizeApi {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private PrizeService prizeService;


    @RequestMapping("/save")
    public Object save(@RequestBody PrizeBean prizeBean,HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        logger.info("user:"+ JSON.toJSONString(userBean));
        prizeBean.setInsertId(userBean.getId());
        prizeBean.setPimg(prizeBean.getImg());
        logger.info("prize save:"+ JSON.toJSONString(prizeBean));
        prizeService.save(prizeBean);
        return JsonUtil.genSuccess();
    }


    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = prizeService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

//    @RequestMapping("/deletes")
//    public Object deletes(@RequestBody Integer[] ids){
//        prizeService.delete(ids);
//        return JsonUtil.genSuccess();
//    }
//
//    @RequestMapping("/generate")
//    public Object generatePrize(@RequestBody Integer prizeId){
//        String msg = prizeService.generate(prizeId);
//        if (msg.equals("success")){
//            return JsonUtil.genSuccess();
//        }else {
//            return JsonUtil.genError(msg);
//        }
//    }
//
//
//    @RequestMapping("/getDetail")
//    public Object getDetail(@RequestBody Integer prizeId){
//        List list = prizeService.getLogBeanByPrizeId(prizeId);
//        return JsonUtil.genSuccess(list);
//    }

}
