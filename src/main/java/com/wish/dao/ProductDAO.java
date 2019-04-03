package com.wish.dao;

import com.wish.bean.ProductBean;
import com.wish.bean.RoleBean;

import java.util.List;
import java.util.Map;

public interface ProductDAO {

    ProductBean findById(Integer id);

    List<ProductBean> findAll();

    List<ProductBean> query(Map<String,Object> condition);

    void save(ProductBean productBean);

    void update(ProductBean productBean);

    void deletes(String[] ids);
}
