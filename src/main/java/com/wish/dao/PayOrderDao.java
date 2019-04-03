package com.wish.dao;


import com.wish.bean.PayOrderBean;

import java.util.List;
import java.util.Map;

public interface PayOrderDao {

    PayOrderBean findById(Integer id);

    List<PayOrderBean> findAll();

    List<Map<String,Object>> query(Map<String,Object> condition);

    List<Map<String,Object>> queryDetail(Map<String,Object> condition);

    long querydetailCount(Map<String, Object> condition);

    void save(PayOrderBean bean);

    void update(PayOrderBean bean);

    void deletes(Integer[] ids);

    PayOrderBean findByTradeNo(String tradeNo);

    void addOutCount(Integer payOrderId);


}