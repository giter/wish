package com.wish.dao;


import java.util.List;
import java.util.Map;
import com.wish.bean.ShowImgBean;



public interface ShowImgDao {

    ShowImgBean findById(Integer id);

    List<ShowImgBean> findAll();

    List<ShowImgBean> query(Map<String, Object> condition);

    void save(ShowImgBean bean);

    void update(ShowImgBean bean);

    void deletes(Integer[] ids);

    List<String> getshowImg();
}