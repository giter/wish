package com.wish.service;


import com.alibaba.fastjson.JSONObject;
import com.wish.Config;
import com.wish.WxConfig;
import com.wish.bean.MachineBean;
import com.wish.common.util.OkhttpUtil;
import com.wish.dao.MachineDAO;
import com.wish.dao.MachineGoodsDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 机器管理
 * create by ff on 2018/7/28
 */
@Service
public class MachineService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private MachineDAO machineDAO;
    @Resource
    private MachineGoodsDAO machineGoodsDAO;


    public List<MachineBean> query(Map<String,Object> condition){
        return machineDAO.query(condition);
    }

    public void save(MachineBean machineBean){
         String qrcodePath = "qrcode/"+System.currentTimeMillis()/1000+"_"+machineBean.getId()+".jpeg";
         String path = Config.UPLOAD_DIR+qrcodePath;
         String accessToken = WxConfig.getAccessToken();
         String url= "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
         JSONObject json = createQrcodeParam(machineBean.getId());
         String result = OkhttpUtil.postJson(url,json,path);
         if (result.equals("success")){
             machineBean.setQrcode(qrcodePath);
         }else {
            logger.info("生成小程序码失败"+machineBean.getId());
         }
        machineDAO.save(machineBean);
    }

    public void deletes(String[] ids){
        machineDAO.deletes(ids);
    }

    public void update(MachineBean machineBean){
        machineDAO.update(machineBean);
    }

    public MachineBean findById(String id){
        return machineDAO.findById(id);
    }


    private JSONObject createQrcodeParam(String machineId){
        JSONObject json = new JSONObject();
        json.put("scene","machineId="+machineId);
        json.put("page",Config.INDEXPAGE);
        json.put("width",450);
        json.put("auto_color",true);//自动配置线条颜色
        json.put("is_hyaline",false);
        return json;
    }

    public JSONObject queryGoodsByMachineId(String machineId) {
        List list = machineGoodsDAO.queryGoodsByMachineId(machineId);
        MachineBean machineBean = machineDAO.findById(machineId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("width",machineBean.getWidth());
        jsonObject.put("height",machineBean.getHeight());
        jsonObject.put("address",machineBean.getAddress());
        jsonObject.put("status",machineBean.getStatus());
        return jsonObject;
    }



}
