package com.wish.dao;


import com.wish.bean.PaperBean;

import java.util.List;
import java.util.Map;

public interface PaperDao  {

    PaperBean findById(Integer id);

    List<PaperBean> findAll();

    List<PaperBean> query(Map<String, Object> condition);

    void save(PaperBean bean);

    void update(PaperBean bean);
    void deletes(Integer[] ids);

}