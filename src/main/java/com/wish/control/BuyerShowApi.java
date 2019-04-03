package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.BuyerShowBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.BuyShowService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyerShow")
public class BuyerShowApi {

    @Resource
    private BuyShowService buyShowService;

//    查询用户评论
    @RequestMapping("/query")
    public Object buyerImgShow(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = buyShowService.query(condition);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

//    用户评论审核结果
    @RequestMapping("/pass")
    public Object pass(@RequestBody Integer id, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        BuyerShowBean buyerShowBean =new BuyerShowBean();
        buyerShowBean.setUpdateId(userBean.getId());
        buyerShowBean.setStatus(2);
        buyerShowBean.setId(id);
        buyShowService.update(buyerShowBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/notPass")
    public Object notPass(@RequestBody Integer id, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        BuyerShowBean buyerShowBean =new BuyerShowBean();
        buyerShowBean.setUpdateId(userBean.getId());
        buyerShowBean.setStatus(3);
        buyerShowBean.setId(id);
        buyShowService.update(buyerShowBean);
        return JsonUtil.genSuccess();
    }

}
