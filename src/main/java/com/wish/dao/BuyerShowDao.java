package com.wish.dao;


import com.wish.bean.BuyerShowBean;

import java.util.List;
import java.util.Map;

public interface BuyerShowDao {

    BuyerShowBean findById(Integer id);

    List<BuyerShowBean> findAll();

    List<Map<String,Object>> query(Map<String, Object> condition);

    void save(BuyerShowBean bean);

    void update(BuyerShowBean bean);
    void deletes(Integer[] ids);
    void zan(Integer id);
}