package com.wish.dao;

import com.wish.bean.AddGoodsLogBean;

import java.util.List;
import java.util.Map;

public interface AddGoodsLogDAO {

    AddGoodsLogBean findById(Integer id);

    List<AddGoodsLogBean> query(Map<String,Object> condition);

    void save(AddGoodsLogBean addGoodsLogBean);

}
