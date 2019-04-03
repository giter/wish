package com.wish.control;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.bean.ProductBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.ProductService;import org.apache.log4j.Logger;
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
@RequestMapping("/product")
public class ProductApi {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ProductService productService;

    @RequestMapping("/query")
    public Object query(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        PageHelper.startPage(pageNum, pageSize);
        List list = productService.query(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

    @RequestMapping("/save")
    public Object save(@RequestBody ProductBean productBean, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        productBean.setInsertId(userBean.getId());
        productService.save(productBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/update")
    public Object update(@RequestBody ProductBean productBean,HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        productBean.setUpdateId(userBean.getId());
        productService.update(productBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/findById")
    public Object findById(@RequestBody String id){
       ProductBean productBean = productService.findById(Integer.parseInt(id));
       return JsonUtil.genSuccess(productBean);
    }

    @RequestMapping("/deletes")
    public Object deletes(@RequestBody String[] ids){
        productService.deletes(ids);
        return JsonUtil.genSuccess();
    }
}
