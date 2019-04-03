package com.wish.control;

import com.wish.bean.ShowImgBean;
import com.wish.bean.UserBean;
import com.wish.common.util.JsonUtil;
import com.wish.service.ImgService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cycleImg")
public class ImgApi {

    @Resource
    private ImgService imgService;

    @RequestMapping("/save")
    public Object save(@RequestBody ShowImgBean showImgBean, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        showImgBean.setInsertId(userBean.getId());
        showImgBean.setStatus(1);
        imgService.save(showImgBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/update")
    public Object update(@RequestBody ShowImgBean showImgBean, HttpSession session){
        UserBean userBean = (UserBean) session.getAttribute("user");
        showImgBean.setUpdateId(userBean.getId());
        imgService.update(showImgBean);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/findAll")
    public Object findAll(){
        List list = imgService.findAll();
        return JsonUtil.genSuccess(list);
    }

}
