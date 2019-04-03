package com.wish.dao;

import com.wish.bean.AddressBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface AddressLogDao {

    AddressBean findById(Integer id);

    List<AddressBean> findAll();

    List<AddressBean> query(Map<String, Object> condition);

    void save(AddressBean bean);

    void update(AddressBean bean);
    void deletes(Integer[] ids);
    void deleteDef(@Param("buyerId") Integer buyerId);

    AddressBean findByBuyId(Integer buyerId);
}