package com.wish.dao;


import java.util.List;
import java.util.Map;
import com.wish.bean.GoodsOrderBean;
import org.apache.ibatis.annotations.Param;


public interface GoodsOrderDao  {

    GoodsOrderBean findById(Integer id);

    List<GoodsOrderBean> findAll();

    List<GoodsOrderBean> query(Map<String, Object> condition);

    void save(GoodsOrderBean bean);

    void update(GoodsOrderBean bean);

    void deletes(Integer[] ids);

    void insertList(List<GoodsOrderBean> list);

    void updateStatusByPayOrderId(@Param("payOrederId") Integer payOrederId);

    GoodsOrderBean getOrderInfo(@Param("machineId") String machineId, @Param("slotNo") String slotNo);

    List<Map<String,Object>> querySale(Map<String,Object> condition);

}