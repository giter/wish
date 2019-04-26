package com.wish.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wish.bean.MachineGoodsBean;
import com.wish.bean.OutGoodsLogBean;
import com.wish.common.util.JsonUtil;
import com.wish.dao.MachineGoodsDAO;
import com.wish.service.OrderService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 老机器的api接口
 * create by ff on 2018/9/26
 */
@RestController
@RequestMapping("/api")
public class OldMachineApi {


    @Resource
    private OrderService orderService;

    @Resource
    private MachineGoodsDAO machineGoodsDAO;

    /**
     *
     * 机器轮询订单接口
     * @param device_id 机器id
     * @param networkStatus 网络状态
     * @param temperature 温度
     * @return
     */
    @RequestMapping("/order/getAllOrder")
    public Object getOrder(@RequestParam(required = true) String device_id,
                           @RequestParam(required = false) String networkStatus,
                           @RequestParam(required = false) String temperature){

        Map<String,Object> result = orderService.oldStartOutGoods(device_id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","0000");
        jsonObject.put("msg","success");
        jsonObject.put("result",result);
        return jsonObject;
    }

//预警接口
    @RequestMapping(value = "/device/getWarningInfo")
    public Object getWarningInfo(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toJSONString());

        JSONObject res = new JSONObject();
        res.put("code","0000");
        res.put("msg","success");
        res.put("result","成功");
        return res;
    }

//    出货结果上报接口
    @RequestMapping("/order/getOrderFeedBack")
    public Object getOrderFeedBack(@RequestBody JSONObject data){
        String TradeNo = data.getString("order_id");
        String MachineID = data.getString("device_id");
        JSONArray list = data.getJSONArray("cargo_lane");
        String msg = "";
        for (Object obj:list){
            JSONObject json = (JSONObject) obj;
            String slotNo = json.getString("id");
            boolean staus = json.getBoolean("status");
            OutGoodsLogBean outGoodsLogBean = new OutGoodsLogBean();
            outGoodsLogBean.setMachineId(MachineID);
//            outGoodsLogBean.setPayType(PayType);
            outGoodsLogBean.setTradeNo(TradeNo);
            outGoodsLogBean.setSlotNo(slotNo);
            if (staus==true) {//出货成功
                outGoodsLogBean.setStatus(2);
            }else {//出货失败
                outGoodsLogBean.setStatus(1);
            }
            outGoodsLogBean.setTime(System.currentTimeMillis()/1000+"");
//            outGoodsLogBean.setAmount(Amount);
//            outGoodsLogBean.setProductId(ProductID);
//            outGoodsLogBean.setName(Name);
//            outGoodsLogBean.setType(Type);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TradeNo", outGoodsLogBean.getTradeNo());
            jsonObject.put("SlotNo", outGoodsLogBean.getSlotNo());
            msg += orderService.finsihOutGoods(outGoodsLogBean);
            System.out.println(outGoodsLogBean.getMachineId()+outGoodsLogBean.getSlotNo()+outGoodsLogBean.getTradeNo()+"上报结果");
            System.out.println(msg);
        }

        JSONObject res = new JSONObject();
        res.put("code","0000");
        res.put("msg", "success");
        res.put("result", "成功");


        return res;
    }


    @RequestMapping("/device/loadGoods")
    public Object loadGoods(@RequestBody JSONObject data) {
        try {
            JSONArray idstrs = data.getJSONArray("ids");
            List<MachineGoodsBean> list = new ArrayList<>();
            for (Object str:idstrs){
                String id = (String) str;
                for (int slotNo=0;slotNo<20;slotNo++){
                    MachineGoodsBean machineGoodsBean = new MachineGoodsBean();
                    machineGoodsBean.setMachineId(id);
                    machineGoodsBean.setSlotNo(slotNo+"");
                    machineGoodsBean.setKeyNum(slotNo);
                    machineGoodsBean.setCapacity(3);
                    machineGoodsBean.setStock(3);
                    machineGoodsBean.setStatus(0);
                    machineGoodsBean.setProductId(0+"");//写死商品id是0 该商品已经录入
                    list.add(machineGoodsBean);
                }
            }
            machineGoodsDAO.oldMachineSave(list);
            return JsonUtil.genSuccess(null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.genError(e.toString());
        }

    }



}
