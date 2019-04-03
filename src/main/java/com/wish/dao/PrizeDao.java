package com.wish.dao;


import java.util.List;
import java.util.Map;
import com.wish.bean.PrizeBean;





public interface PrizeDao  {

    PrizeBean findById(Integer id);

    List<PrizeBean> findAll();

    List<PrizeBean> query(Map<String, Object> condition);

    void save(PrizeBean bean);

    void update(PrizeBean bean);

    void deletes(Integer[] ids);

}