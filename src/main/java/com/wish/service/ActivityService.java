package com.wish.service;

import com.alibaba.fastjson.JSONObject;
import com.wish.Config;
import com.wish.WxConfig;
import com.wish.bean.ActivityBean;
import com.wish.common.util.OkhttpUtil;
import com.wish.dao.ActivityDao;
import com.wish.dao.WishlogDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ActivityDao activityDao;
    @Resource
    private WishlogDao wishlogDao;

//生成活动信息
    public boolean save(ActivityBean activityBean){
        activityDao.save(activityBean);
        String path = createQrCode(activityBean.getId());
        if (path!=null){//生成成功则更新二维码信息
            activityBean.setImg(path);
            activityDao.update(activityBean);
            return true;
        }else {//生成失败则，删除
            logger.info(activityBean.getId()+"生成心愿二维码失败");
//                prizeLogDao.deletes(new Integer[]{prizeLogBean.getId()});
            return false;
        }
    }

    public List<ActivityBean> query(Map<String,Object> condition){
        return activityDao.query(condition);
    }

    public boolean inTime(Integer id){
        if (activityDao.inTime(id)>0){
            return true;
        }else {
            return false;
        }
    }



    private String createQrCode(Integer id){
        String qrcodePath = "wish/activity"+id+".jpeg";
        String path = Config.UPLOAD_DIR+qrcodePath;
        String accessToken = WxConfig.getAccessToken();
        String url= "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
        JSONObject json = createQrcodeParam(id);
        String result = OkhttpUtil.postJson(url,json,path);
        if (result.equals("success")){
            return qrcodePath;
        }else {
            logger.info("生成小程序心愿码失败");
            return null;
        }
    }

    private JSONObject createQrcodeParam(Integer wishLogId){
        JSONObject json = new JSONObject();
        json.put("scene","activity="+wishLogId);
        json.put("page",Config.PRIZEPAGE);
        json.put("width",450);
        json.put("auto_color",true);//自动配置线条颜色
        json.put("is_hyaline",false);
        return json;
    }
}
