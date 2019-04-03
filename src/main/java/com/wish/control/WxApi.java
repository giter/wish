package com.wish.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wish.Config;
import com.wish.bean.*;
import com.wish.common.util.JsonUtil;
import com.wish.dao.*;
import com.wish.service.*;
import org.apache.commons.lang3.StringUtils;import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/8/9
 */
@RestController
@RequestMapping("/wx")
public class WxApi {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private MachineService machineService;
    @Resource
    private AnswerLogDao answerLogDao;
    @Resource
    private AcWishDao acWishDao;
    @Resource
    private PrizeLogDao prizeLogDao;
    @Resource
    private AddressDao addressDao;
    @Resource
    private ShowImgDao showImgDao;
    @Resource
    private BuyShowService buyShowService;
    @Resource
    private BuyerDao buyerDao;
    @Resource
    private WishLogService wishLogService;
    @Resource
    private ActivityService activityService;
    @Resource
    private QuestionService questionService;
    @Resource
    private OrderService orderService;
    @Resource
    private AddressLogDao addressLogDao;




    @RequestMapping("/getUserInfo")
    public Object getUserInfo(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        AddressBean addressBean = addressDao.findByBuyId(buyerId);
        BuyerBean buyerBean = buyerDao.findById(buyerId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("buyer",buyerBean);
        jsonObject.put("address",addressBean);
        return JsonUtil.genSuccess(jsonObject);
    }

//==================================兑奖=====================

    @RequestMapping("/getPrizeInfo")
    public Object getPrizeInfo(@RequestBody JSONObject data){
        PrizeBean prizeBean;
        String str = data.getString("prizeLogId");
        if (str.length()<=11){
            Integer prizeLogId = Integer.parseInt(str);
            prizeBean = prizeLogDao.findPrizeById(prizeLogId);
        }else {
            prizeBean = prizeLogDao.findPrizeByOldId(str);
        }
        prizeBean.setPimg(Config.SERVER+Config.RESOURCE_DIR+prizeBean.getPimg());
        return JsonUtil.genSuccess(prizeBean);

    }


    /**
     * 兑奖
     * @param data
     * @return
     */
    @RequestMapping("/cachePrize")
    public Object cachePrize(@RequestBody JSONObject data,HttpSession session){
        logger.info("兑奖："+data);
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyerId");
        Integer buyerId=null;
        if (buyerBean==null){
            if (data.getInteger("buyerId")==null){
                return JsonUtil.genError("buyerId不存在");
            }
            buyerId= data.getInteger("buyerId");
        }else {
            buyerId = buyerBean.getId();
        }

        Integer addressId = data.getInteger("addressId");
        String str = data.getString("prizeLogId");
        //        保存地址 到 log
        AddressBean addressBean = addressDao.findById(addressId);
        addressBean.setId(null);
        addressLogDao.save(addressBean);
        logger.info("地址："+ JSON.toJSONString(addressBean));
        PrizeLogBean prizeLogBean = new PrizeLogBean();


        prizeLogBean.setBuyerId(buyerId);
        prizeLogBean.setAddressId(addressBean.getId());
        prizeLogBean.setStatus(2);//兑换
        if (str.length()<=11){
            Integer prizeLogId = Integer.parseInt(str);
            prizeLogBean.setId(prizeLogId);
            PrizeBean prizeBean = prizeLogDao.findPrizeById(prizeLogId);
            if (prizeBean==null){
                return JsonUtil.genError("该二维码已被使用");
            }
            prizeLogDao.cachePrize(prizeLogBean);
        }else {
            prizeLogBean.setOldId(str);
            PrizeBean prizeBean = prizeLogDao.findPrizeByOldId(str);
            if (prizeBean==null){
                return JsonUtil.genError("该二维码已被使用");
            }
            prizeLogDao.oldcachePrize(prizeLogBean);
        }

        logger.info("奖品信息："+ JSON.toJSONString(prizeLogBean));
        return JsonUtil.genSuccess();

    }


    /**
     * 获取商品列表信息
     * @param data
     * @return
     */
    @RequestMapping("/getGoodsList")
    public Object getGoodsList(@RequestBody JSONObject data){
        String machine = data.getString("machine");
        JSONObject jsonObject = machineService.queryGoodsByMachineId(machine);
        return JsonUtil.genSuccess(jsonObject);
    }


    @RequestMapping("/queryPrizeLog")
    public Object queryPrizeLog(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",buyerId);
        List list = prizeLogDao.query(condition);
        return JsonUtil.genSuccess(list);

    }

    @RequestMapping("/queryOrder")
    public Object queryOrder(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
//        JSONObject page = data.getJSONObject("page");
//        Integer pageNum=page.getInteger("pageNum");
//        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",buyerId);
        condition.put("pageNum",1);
        condition.put("pageSize",20);
//        long total = orderService.querydetailCount(condition);
        List list = orderService.querydetail(condition);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);

//        pageInfo.setPageNum(pageNum);
//        pageInfo.setPageSize(pageSize);
//        pageInfo.setTotal(total);
//        pageInfo.setPages((int) (total/pageSize));
        return JsonUtil.genSuccess(list);
    }








//=========================地址相关=================================
//保存用户地址
    @RequestMapping("saveAddress")
    public Object saveAddress(@RequestBody AddressBean addressBean,HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        if (buyerBean == null) {
            if (addressBean.getBuyerId()==null){
                return JsonUtil.genError("buyerid为空");
            }
            addressBean.setInsertId(addressBean.getBuyerId());
        }else {
            addressBean.setInsertId(buyerBean.getId());
        }
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",addressBean.getBuyerId());
        condition.put("def",1);
        List list = addressDao.query(condition);
        if (list.size()>0){
            addressBean.setDef(0);
        }else {
            addressBean.setDef(1);
        }
        addressDao.save(addressBean);
        return JsonUtil.genSuccess();
    }

//    根据用户id获取用户地址
    @RequestMapping("getAddress")
    public Object getAddress(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        if (buyerId==null){
            return JsonUtil.genSuccess(new ArrayList());
        }
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",buyerId);
        List list = addressDao.query(condition);
        return JsonUtil.genSuccess(list);
    }
//    删除用户地址
    @RequestMapping("deletesAddress")
    public Object deletesAddress(@RequestBody Integer[] ids){
        addressDao.deletes(ids);
        return JsonUtil.genSuccess();
    }
//更新用户地址
    @RequestMapping("updateAddress")
    public Object updateAddress(@RequestBody AddressBean addressBean){
        logger.debug("==================================");
        logger.debug(addressBean.getId()+" "+addressBean.getAddress());
        addressBean.setUpdateId(addressBean.getBuyerId());
        addressBean.setDef(null);
        addressDao.update(addressBean);
        return JsonUtil.genSuccess();
    }

//    设置默认收货地址
    @RequestMapping("/setDefAddress")
    public Object setDefAddress(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        Integer addressId = data.getInteger("addressId");
        addressDao.deleteDef(buyerId);
        AddressBean addressBean = new AddressBean();
        addressBean.setId(addressId);
        addressBean.setBuyerId(buyerId);
        addressBean.setDef(1);
        addressDao.update(addressBean);
        return JsonUtil.genSuccess();
    }

//===================================获取轮播图

    @RequestMapping("/getCycleImg")
    public Object getCycleImg(){
        List<String> result = new ArrayList<>();
        List<String> list = showImgDao.getshowImg();
        for (String img:list){
            img= Config.SERVER+Config.RESOURCE_DIR+img;
            result.add(img);
        }
        return JsonUtil.genSuccess(result);
    }

//===================================获取用户评论
//保存用户评论图片
    @RequestMapping("/buyerImgSave")
    public Object buyerImgSave(@RequestBody BuyerShowBean buyerShowBean){
        logger.info(JSON.toJSONString(buyerShowBean));
        buyerShowBean.setInsertId(buyerShowBean.getBuyerId());
        buyShowService.save(buyerShowBean);
        return JsonUtil.genSuccess();
    }

//获取展示图片
    @RequestMapping("/buyerImgShow")
    public Object buyerImgShow(@RequestBody JSONObject data){
        JSONObject page = data.getJSONObject("page");
        Integer pageNum=page.getInteger("pageNum");
        Integer pageSize=page.getInteger("pageSize");
        Map<String,Object> condition = data.getJSONObject("condition");
        Integer buyerId = (Integer) condition.get("buyerId");
        if (buyerId==null){
            condition.put("status",2);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,Object>> list = buyShowService.query(condition);
        for (Map<String,Object> map:list){
            map.put("img",Config.SERVER+Config.RESOURCE_DIR+map.get("img"));
        }
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        return JsonUtil.genSuccess(pageInfo);
    }

//    点赞
    @RequestMapping("/buyerImgZan")
    public Object buyerImgZan(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        buyShowService.zan(data.getInteger("id"),buyerId);
        return JsonUtil.genSuccess();
    }


//    =====================================许愿==============

    /**
     * 保存心愿
     * @param wishlogBean
     * 必填：activityId，wishId，buyerId
     * @return
     */
    @RequestMapping("/saveWish")
    public Object saveWish(@RequestBody WishlogBean wishlogBean){
        logger.info(JSON.toJSONString(wishlogBean));
        if(!activityService.inTime(wishlogBean.getActivityId())){
            return JsonUtil.genError("该活动还未开始或者已经过期无法参加");
        }
        if(wishLogService.haveWish(wishlogBean)){
            return JsonUtil.genError("该活动已经许愿");
        }
        wishLogService.save(wishlogBean);
        return JsonUtil.genSuccess(wishlogBean);
    }

    /**
     * 查询本人所有心愿信息
     * @param data
     * @return
     */
    @RequestMapping("/queryWish")
    public Object queryWish(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("buyerId",buyerId);
        List list = wishLogService.query(condition);
        return JsonUtil.genSuccess(list);
    }

//    查询心愿排行榜
    @RequestMapping("/rank")
    public Object rank(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        Integer activityId = data.getInteger("activityId");
        List list = wishLogService.rank(activityId);
        Map<String,Object> myRank = wishLogService.myRank(activityId,buyerId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rank",list);
        jsonObject.put("my",myRank);
        return JsonUtil.genSuccess(jsonObject);
    }

//    查询可选择心愿
    @RequestMapping("/queryAcWish")
    public Object queryAcWish(@RequestBody JSONObject data){
        Integer activityId = data.getInteger("activityId");
        Map<String,Object> condition = new HashMap<>();
        condition.put("activityId",activityId);
        List list = acWishDao.query(condition);
        return JsonUtil.genSuccess(list);
    }

//    ==============================================答题===========

    @RequestMapping("/queryQuestion")
    public Object queryQuestion(){
        List list = questionService.randomSelect();
        return JsonUtil.genSuccess(list);
    }


    @RequestMapping("/checkPaper")
    public Object checkPaper(@RequestBody JSONObject data){
        Integer buyerId = data.getInteger("buyerId");
        Integer wishLogId = data.getInteger("wishLogId");
        if (haveAnswer(buyerId,wishLogId)){
            return JsonUtil.genError("已经答题");
        }else {
            return JsonUtil.genSuccess();
        }
    }

    /**
     * 提交答题结果
     * @param data
     * @return
     */
    @RequestMapping("/savePaper")
    public Object savePaper(@RequestBody JSONObject data){
        logger.info(data);
        Integer buyerId = data.getInteger("buyerId");
        Integer wishLogId = data.getInteger("wishLogId");
        if(haveAnswer(buyerId,wishLogId)){
            return JsonUtil.genError("已经答题");
        }
        List<PaperBean> paperBeans = JSONArray.parseArray(data.getJSONArray("paper").toJSONString(),PaperBean.class);
        Integer point = questionService.mark(paperBeans,buyerId);
        wishLogService.addPoint(point,wishLogId,buyerId);
        AnswerLogBean answerLogBean = new AnswerLogBean();
        answerLogBean.setWishlogid(wishLogId);
        answerLogBean.setBuyerId(buyerId);
        answerLogBean.setPoint(point);
        answerLogBean.setInsertId(buyerId);
        answerLogDao.save(answerLogBean);
        return JsonUtil.genSuccess(point);
    }






    private boolean haveAnswer(Integer buyerId,Integer wishLogId){
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",buyerId);
        condition.put("wishlogid",wishLogId);
        List list = answerLogDao.query(condition);
        if (list.size()>0){
            return true;
        }else {
            return false;
        }
    }
}
