package com.wish.dao;


import com.wish.bean.WishlogBean;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.List;
import java.util.Map;

public interface WishlogDao  {

    WishlogBean findById(Integer id);

    List<WishlogBean> findAll();

    List<Map<String,Object>> query(Map<String, Object> condition);
    List<Map<String,Object>> rank(@Param("activityId") Integer activityId);
    Map<String,Object> myRank(@Param("activityId") Integer activityId, @Param("buyerId")Integer buyerId);

    void save(WishlogBean bean);

    void update(WishlogBean bean);

    void deletes(Integer[] ids);

    void addPoint(@Param("point") Integer point,@Param("wishLogId") Integer wishLogId,@Param("buyerId") Integer buyerId);
}