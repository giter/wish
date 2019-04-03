package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.wish.WxConfig;
import com.wish.bean.*;
import com.wish.common.util.JsonUtil;
import com.wish.service.AddGoodsService;
import com.wish.service.MachineService;
import com.wish.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.Mac;
import java.util.HashMap;
import java.util.Map;

/**
 * 给机器使用的所有接口
 * create by ff on 2018/7/28
 */
@RestController
@RequestMapping("/machine")
public class MachineApi {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private AddGoodsService addGoodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private MachineService machineService;


    /**
     * 机器添加商品
     * @param addGoodsLogBean
     * @return
     */
    @RequestMapping("/addGoods")
    public Object addGoods(@RequestBody AddGoodsLogBean addGoodsLogBean){
        JSONObject result = new JSONObject();
        result.put("SlotNo",addGoodsLogBean.getSlotNo());
        result.put("TradeNo",addGoodsLogBean.getTradeNo());
        try {
            addGoodsLogBean.setInsertId(0);
            ProductBean productBean = addGoodsService.addGoods(addGoodsLogBean);
            result.put("Status",0);
            result.put("ImageUrl",productBean.getImageUrl());
            result.put("ImageDetailUrl",productBean.getImageDetailUrl());
            result.put("Err","success");
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            result.put("Status",2);
            result.put("Err",e.toString());
        }
        if (addGoodsLogBean.getStatus()!=0){//如果机器故障，则报错
            addGoodsService.sendErr(addGoodsLogBean);
            result.put("Status",2);
            result.put("Err","货道故障，tradeNoe:"+addGoodsLogBean.getTradeNo());
        }
        return result;
    }

// 轮寻接口
    @RequestMapping("/checkGoods")
    public Object checkGoods(String Funcode,String MachineID){
        MachineBean machineBean = new MachineBean();
        machineBean.setId(MachineID);
        machineService.update(machineBean);//更新机器update时间
        GoodsOrderBean goodsOrderBean = orderService.startOutGoods(MachineID);
        JSONObject jsonObject = new JSONObject();
        if (goodsOrderBean!=null){
            jsonObject.put("status",0);
            jsonObject.put("MsgType",0);//控制出货
            jsonObject.put("TradeNo",goodsOrderBean.getPayOrderId());
            jsonObject.put("SlotNo",goodsOrderBean.getSlotNo());
            jsonObject.put("ProductID",goodsOrderBean.getProductId());
        }else {
            jsonObject.put("status",1);//失败
            jsonObject.put("MsgType",0);//控制出货
            jsonObject.put("TradeNo","");
            jsonObject.put("SlotNo","");
            jsonObject.put("ProductID","");
        }
        return jsonObject;
    }

//出货成功
    @RequestMapping("/outSuccess")
    public Object outSuccess(String Funcode, String MachineID, Integer PayType,
                             String TradeNo, String SlotNo,Integer status,String time,
                             String Amount,String ProductID,String Name,String Type){
        OutGoodsLogBean outGoodsLogBean = new OutGoodsLogBean();
        outGoodsLogBean.setMachineId(MachineID);
        outGoodsLogBean.setPayType(PayType);
        outGoodsLogBean.setTradeNo(TradeNo);
        outGoodsLogBean.setSlotNo(SlotNo);
        outGoodsLogBean.setStatus(status);
        outGoodsLogBean.setTime(time);
        outGoodsLogBean.setAmount(Amount);
        outGoodsLogBean.setProductId(ProductID);
        outGoodsLogBean.setName(Name);
        outGoodsLogBean.setType(Type);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TradeNo", outGoodsLogBean.getTradeNo());
        jsonObject.put("SlotNo", outGoodsLogBean.getSlotNo());
        System.out.println("出货："+jsonObject);
            String msg = orderService.finsihOutGoods(outGoodsLogBean);
            if (StringUtils.isEmpty(msg)) {
                jsonObject.put("Status", 0);
                jsonObject.put("Err", "成功");
            }else {
                jsonObject.put("Status", 1);
                jsonObject.put("Err", msg);
            }
        System.out.println("出货结果："+jsonObject);

        return jsonObject;
    }




}
