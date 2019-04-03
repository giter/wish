package com.wish.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.wish.Config;
import com.wish.WxConfig;
import com.wish.bean.*;
import com.wish.dao.*;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ProductDAO productDAO;
    @Resource
    private PayOrderDao payOrderDao;
    @Resource
    private MachineGoodsDAO machineGoodsDAO;
    @Resource
    private GoodsOrderDao goodsOrderDao;
    @Resource
    private OutGoodsLogDao outGoodsLogDao;
    @Resource
    private BuyerDao buyerDao;

    @Resource
    private MachineDAO machineDAO;

    private WXPay wxPay = new WXPay(WxConfig.getPayInstance());


    /**
     * 积分支付
     * @param data
     * @param buyerId
     * @return
     */
    @Transactional
    public PayOrderBean pointPay(JSONObject data,Integer buyerId){
        PayOrderBean payOrderBean = payOrder(data,buyerId,2);//2标识积分支付
        buyerDao.reducePoint(payOrderBean.getTotalFee(),payOrderBean.getInsertId());
        return payOrderBean;
    }

    /**
     * 微信支付
     * @param data
     * @param buyerId
     * @return
     */
    @Transactional
    public PayOrderBean prePayOrder(JSONObject data,Integer buyerId){
        PayOrderBean payOrderBean = payOrder(data,buyerId,1);
        JSONArray list = data.getJSONArray("list");
        Integer totalFee = list.size() * Config.ADDPOINT;
        buyerDao.addPoint(totalFee,buyerId);
        return payOrderBean;
    }


    /**
     *
     * 生成订单
     * 如果是微信支付，则为预支付订单
     * 如果是积分支付，则为支付完成够的订单
     * totalFee 为支付金额或者积分总数
     * todo 积分支付总积分需要另外计算
     * @param data
     * machineId,
     * buyerId,
     * list:[{
     *     slotNo:1
     *     num:1
     * }]
     */
    @Transactional
    public PayOrderBean payOrder(JSONObject data,Integer buyerId,Integer payType){
        String machineId = data.getString("machineId");
        Integer totalPrice =0;
        Integer totalNum=0;
        List<GoodsOrderBean> goodsOrderBeans = new ArrayList<>();
        JSONArray list = data.getJSONArray("list");
        List<String> goodsNames = new ArrayList<String>();
        for (Object item:list){
            JSONObject json = (JSONObject) item;
            String slotNo = json.getString("slotNo");
            GoodsDetailBean goodsDetailBean = getByMachineIdAndSlot(machineId,slotNo);
            goodsNames.add(goodsDetailBean.getName());

            Integer num = json.getInteger("num");
            Integer price = goodsDetailBean.getPrice();
            totalNum+=num;
            totalPrice+=num * price;
            List<GoodsOrderBean> goodsOrderBeanList = createGoodsOrderBean(goodsDetailBean,num,buyerId,machineId);
            goodsOrderBeans.addAll(goodsOrderBeanList);
        }
        PayOrderBean payOrderBean = new PayOrderBean();
        payOrderBean.setPayTradeNo(createTradeNo(buyerId,machineId));
        payOrderBean.setMachineId(machineId);
        payOrderBean.setTotalFee(totalPrice);
        if (payType==1) {
            payOrderBean.setPayType(1);
            payOrderBean.setPayStatus(0);//等待支付
        }else {
            payOrderBean.setPayType(2);//修改支付类型为积分支付
            payOrderBean.setPayStatus(1);//直接设置为支付成功
            payOrderBean.setTotalFee(list.size()*Config.REDUCEPOINT);
        }
        payOrderBean.setBuyNum(totalNum);
        payOrderBean.setDetail("这是对该笔订单的一个简单描述，或者备注");
        payOrderBean.setInsertId(buyerId);
        payOrderBean.setNames(goodsNames);
        payOrderDao.save(payOrderBean);
        updateGoodsOrderPayId(goodsOrderBeans,payOrderBean.getId());
        goodsOrderDao.insertList(goodsOrderBeans);
        return payOrderBean;
    }



    private GoodsDetailBean  getByMachineIdAndSlot(String machineId,String slotNo){
        Map<String,Object> condition = new HashMap<>();
        condition.put("machineId",machineId);
        condition.put("slotNo",slotNo);
        List<GoodsDetailBean> list= machineGoodsDAO.query(condition);
       return list.get(0);
    }

    /**
     * 生成交易号
     * @param buyerId
     * @param machineId
     * @return
     */
    private String createTradeNo(Integer buyerId,String machineId){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(new Date());
        Random rand = new Random();
        String tradeNo = dateString+buyerId+machineId+rand.nextInt(10000);
        return tradeNo;
    }
    /**
     * 根据商品id，和数量，生成商品订单信息
     * @param goodsDetailBean
     * @param num
     * @return
     */
    private List<GoodsOrderBean> createGoodsOrderBean(GoodsDetailBean goodsDetailBean,Integer num,Integer userId,String machineId){
        List<GoodsOrderBean> list = new ArrayList<>();
        for (int i=0;i<num;i++){
            GoodsOrderBean goodsOrderBean = new GoodsOrderBean();
            goodsOrderBean.setMachineId(machineId);
            goodsDetailBean.setSlotNo(goodsDetailBean.getSlotNo());
            goodsOrderBean.setProductId(goodsDetailBean.getProductId());
            goodsOrderBean.setPrice(goodsDetailBean.getPrice());
            goodsOrderBean.setStatus(-2);
            goodsOrderBean.setSlotNo(goodsDetailBean.getSlotNo());
            goodsOrderBean.setInsertId(userId);
           list.add(goodsOrderBean);
        }
        return list;
    }


    /**
     * 更新goodsorder信息的支付id
     * @param list
     * @param payId
     */
    private void updateGoodsOrderPayId( List<GoodsOrderBean> list,Integer payId){
        for (GoodsOrderBean bean:list){
            bean.setPayOrderId(payId);
        }
    }

    /**
     * 支付成功
     * @Param tradeNo 订单id
     */
    @Transactional
    public void  payFinish(String tradeNo){
        PayOrderBean payOrderBean = payOrderDao.findByTradeNo(tradeNo);
        payOrderBean.setPayStatus(1);//支付成功
        payOrderDao.update(payOrderBean);
        goodsOrderDao.updateStatusByPayOrderId(payOrderBean.getId());
    }

    /**
     * 开始出货
     * 查询出货信息
     * 返回出货结果，
     * 更新出货状态
     */
    @Transactional
    public GoodsOrderBean startOutGoods(String machineId){
        Map<String,Object> condiction = new HashMap<>();
        condiction.put("machineId",machineId);//机器id
        condiction.put("status",-2);//已经付款，待出货
        List<GoodsOrderBean> list = goodsOrderDao.query(condiction);
        if (list==null || list.size()==0){
            return null;
        }
        GoodsOrderBean goodsOrderBean = list.get(0);
        goodsOrderBean.setStatus(-1);//正在出货
        goodsOrderDao.update(goodsOrderBean);
        return  goodsOrderBean;
    }


    /**
     * 老接口
     * 开始出货
     * 查询出货信息
     * 返回出货结果，
     * 更新出货状态
     */
    @Transactional
    public Map<String,Object> oldStartOutGoods(String machineId){
        Map<String,Object> map = new HashMap<>();
        String ids = "";
        String payOrderId="";
        Map<String,Object> condiction = new HashMap<>();
        condiction.put("machineId",machineId);//机器id
        condiction.put("status",-2);//已经付款，待出货
        List<GoodsOrderBean> list = goodsOrderDao.query(condiction);
        for (GoodsOrderBean goodsOrderBean:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",goodsOrderBean.getSlotNo());
            goodsOrderBean.setStatus(-1);//正在出货
            goodsOrderDao.update(goodsOrderBean);
            jsonObject.put("stauts",true);
            ids += goodsOrderBean.getSlotNo()+",";
            payOrderId = ""+goodsOrderBean.getPayOrderId();
        }
        if (ids.contains(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        map.put("order_id",payOrderId+"");
        map.put("cargo_lane",ids);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        map.put("timeStamp", df.format(new Date()));
        return map;
    }



    public void machineBeat(String machineId){
        MachineBean machineBean = new MachineBean();
        machineBean.setId(machineId);
        machineDAO.update(machineBean);
    }

    /**
     * 出货成功
     * 修改货物的出货状态，
     * 保存出货状态
     * 修改商品库存
     * 如果出库失败，则修改状态退款
     *
     */
    @Transactional
    public String finsihOutGoods(OutGoodsLogBean outGoodsLogBean){
        try {
            //保存出货日志
            outGoodsLogDao.save(outGoodsLogBean);
            //查找单个商品订单
            GoodsOrderBean goodsOrderBean = goodsOrderDao.getOrderInfo(outGoodsLogBean.getMachineId(),outGoodsLogBean.getSlotNo());
            if (goodsOrderBean!=null) {
                goodsOrderBean.setStatus(outGoodsLogBean.getStatus());
                goodsOrderDao.update(goodsOrderBean);//更改订单状态
//                出货成功 增加货物总量
                if (outGoodsLogBean.getStatus()==0 || outGoodsLogBean.getStatus()==2) {
                    payOrderDao.addOutCount(goodsOrderBean.getPayOrderId());
//                    修改机器库存
                    reduceMachineNum(outGoodsLogBean);
                }else if (outGoodsLogBean.getStatus()==3|| outGoodsLogBean.getStatus()==1 ){
//                    出货失败,进行退款
                   PayOrderBean payOrderBean = payOrderDao.findById(goodsOrderBean.getPayOrderId());
                   refund(payOrderBean.getPayTradeNo(),payOrderBean.getTotalFee()+"",goodsOrderBean.getPrice()+"");
                }
                return "";
            }else {
                return "订单不存在";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    public JSONObject refund(String transactionId, String totalFee, String refundFee) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("transaction_id", transactionId);//微信交易号
        data.put("out_refund_no", transactionId);//商户退款单号
        data.put("total_fee", totalFee);//订单总金额
        data.put("refund_fee", refundFee);//退款金额
        data.put("refund_fee_type", "CNY");//货币
        data.put("refund_desc", "出货失败退款");//退款原因
        try {
            Map<String, String> map = wxPay.refund(data);
            System.out.println(map);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void reduceMachineNum(OutGoodsLogBean outGoodsLogBean){
        machineGoodsDAO.reduceSotck(outGoodsLogBean.getMachineId(),outGoodsLogBean.getSlotNo());
    }

    public List<Map<String,Object>> querySale(Map<String,Object> condition){
        return goodsOrderDao.querySale(condition);
    }

    public List query(Map<String, Object> condition) {
        return payOrderDao.query(condition);
    }


    public List querydetail(Map<String,Object> condition){
        List<Map<String,Object>> list = payOrderDao.queryDetail(condition);
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map<String,Object> map:list){
            Map<String,Object> maprel = new HashMap<>();
            maprel.put("productName",MapUtils.getString(map,"productName"));
            maprel.put("goodsOrderId",MapUtils.getString(map,"goodsOrderId"));
            maprel.put("slot_no",MapUtils.getString(map,"slot_no"));
            maprel.put("price",MapUtils.getString(map,"price"));

            Integer id = MapUtils.getInteger(map,"id");
            Map map1 = getOrderById(id,result);
            if (map1==null){
                Map<String,Object> map2 = new HashMap<>();
                map2.put("id",MapUtils.getInteger(map,"id"));
                map2.put("tradeNo",MapUtils.getString(map,"tradeNo"));
                map2.put("payStatus",MapUtils.getInteger(map,"payStatus"));
                map2.put("machineId",MapUtils.getString(map,"machineId"));
                map2.put("insertTime",MapUtils.getString(map,"insertTime"));
                List<Map<String,Object>> details = new ArrayList<>();
                details.add(maprel);
                map2.put("details",details);
                result.add(map2);
            }else {
                List<Map<String,Object>> details = (List<Map<String, Object>>) map1.get("details");
                details.add(maprel);
            }
        }
        return result;
    }

    public Map<String,Object> getOrderById(Integer id,List<Map<String,Object>> list){
        for (Map<String,Object> map:list){
            Integer id2 = MapUtils.getInteger(map,"id");
            if (id.equals(id2)){
                return map;
            }
        }
        return null;
    }

    public long querydetailCount(Map<String, Object> condition) {
        return payOrderDao.querydetailCount(condition);
    }
}
