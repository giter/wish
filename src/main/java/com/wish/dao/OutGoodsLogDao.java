package com.wish.dao;


import java.util.List;
import java.util.Map;
import com.wish.bean.OutGoodsLogBean;




public interface OutGoodsLogDao {

    OutGoodsLogBean findById(Integer id);

    List<OutGoodsLogBean> findAll();

    List<OutGoodsLogBean> query(Map<String, Object> condition);

    void save(OutGoodsLogBean bean);

    void update(OutGoodsLogBean bean);

    void deletes(Integer[] ids);

}