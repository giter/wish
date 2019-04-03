package com.wish.dao;


import com.wish.bean.AcWishBean;

import java.util.List;
import java.util.Map;

public interface AcWishDao {

    AcWishBean findById(Integer id);

    List<AcWishBean> findAll();

    List<AcWishBean> query(Map<String, Object> condition);

    void save(AcWishBean bean);

    void update(AcWishBean bean);
    void deletes(Integer[] ids);

}