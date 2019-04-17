package com.wish.service;

import com.alibaba.fastjson.JSONObject;
import com.wish.Config;
import com.wish.WxConfig;
import com.wish.bean.PrizeBean;
import com.wish.bean.PrizeLogBean;
import com.wish.common.util.OkhttpUtil;
import com.wish.common.util.ZipUtil;
import com.wish.dao.PrizeDao;
import com.wish.dao.PrizeLogDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/8/6
 */
@Service
public class PrizeService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private PrizeDao prizeDao;

    @Resource
    private PrizeLogDao prizeLogDao;

    /**
     * 新增奖品
     * @param prizeBean
     */
    public void save(PrizeBean prizeBean){
        List<File> imgs = new ArrayList<>();
        prizeDao.save(prizeBean);
        for (int i=0;i<prizeBean.getNum();i++){
            String path = generate(prizeBean.getId());
            imgs.add(new File(Config.UPLOAD_DIR+path));
        }
        ZipUtil.zipFiles(imgs,
                new File(Config.UPLOAD_DIR+"prize/"+prizeBean.getName()+prizeBean.getId()+".zip"));
    }


    public void delete(Integer[] ids){
        prizeDao.deletes(ids);
    }


    public String generate(Integer prizeId){
            PrizeLogBean prizeLogBean = new PrizeLogBean();
            prizeLogBean.setPrizeId(prizeId);
            prizeLogBean.setStatus(1);
            prizeLogDao.save(prizeLogBean);
            String path = createQrCode(prizeLogBean.getId());
            if (path!=null){//生成成功则更新二维码信息
                prizeLogBean.setImg(path);
                prizeLogDao.update(prizeLogBean);
                return path;
            }else {//生成失败则，删除
//                prizeLogDao.deletes(new Integer[]{prizeLogBean.getId()});
                return null;
            }

    }

    public List<Map<String, Object>> getLogBeanByPrizeId(Integer prizeId){
        Map<String,Object> condition = new HashMap<>();
        condition.put("prizeId",prizeId);
        return prizeLogDao.query(condition);
    }


    private String createQrCode(Integer prizeLogId){
        String qrcodePath = "prize/"+System.currentTimeMillis()+"_"+prizeLogId+".jpeg";
        String path = Config.UPLOAD_DIR+qrcodePath;
        String accessToken = WxConfig.getAccessToken();
        String url= "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
        JSONObject json = createQrcodeParam(prizeLogId);
        String result = OkhttpUtil.postJson(url,json,path);
        if (result.equals("success")){
            return qrcodePath;
        }else {
            logger.info("生成小程序奖品码失败");
            return null;
        }
    }



    private JSONObject createQrcodeParam(Integer prizeLogId){
        JSONObject json = new JSONObject();
        json.put("scene","prizeId="+prizeLogId);
        json.put("page",Config.PRIZEPAGE);
        json.put("width",450);
        json.put("auto_color",true);//自动配置线条颜色
        json.put("is_hyaline",false);
        return json;
    }

    public List query(Map<String, Object> condition) {
        List<PrizeBean> list = prizeDao.query(condition);
        for (PrizeBean prizeBean:list){
            prizeBean.setZipPath("prize/"+prizeBean.getName()+prizeBean.getId()+".zip");
        }
        return list;
    }
}
