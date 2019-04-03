package com.wish.service;

import com.wish.bean.WishlogBean;
import com.wish.dao.WishlogDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WishLogService {

    @Resource
    private WishlogDao wishlogDao;

//    查询心愿记录
    public List<Map<String,Object>> query(Map<String,Object> condition){
        return wishlogDao.query(condition);
    }

//    获取得分前10名
    public List<Map<String,Object>> rank(Integer activityId){
        return wishlogDao.rank(activityId);
    }
// 获取自己的等分信息
    public Map<String,Object> myRank(Integer activityId,Integer buyerId){
        return wishlogDao.myRank(activityId,buyerId);
    }


//    生成心愿记录
    public WishlogBean save(WishlogBean wishlogBean){
        wishlogBean.setInsertId(wishlogBean.getBuyerId());
        wishlogBean.setStatus(2);//设置心愿状态（新版本不影响）
        wishlogBean.setPoint(0);
        wishlogDao.save(wishlogBean);
        return wishlogBean;
    }

//    查询该用户是否已经许过愿
    public boolean haveWish(WishlogBean wishlogBean){
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",wishlogBean.getBuyerId());
        condition.put("activityId",wishlogBean.getActivityId());
        List list = wishlogDao.query(condition);
        if (list==null || list.size()==0){
            return false;
        }else {
            return true;
        }
    }

    public void addPoint(Integer point,Integer wishLogId,Integer buyerId){
        wishlogDao.addPoint(point,wishLogId,buyerId);
    }




}
