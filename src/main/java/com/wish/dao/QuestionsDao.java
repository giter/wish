package com.wish.dao;


import com.wish.bean.PaperBean;
import com.wish.bean.QuestionsBean;

import java.util.List;
import java.util.Map;

public interface QuestionsDao  {

    QuestionsBean findById(Integer id);

    List<QuestionsBean> findAll();

    List<QuestionsBean> query(Map<String, Object> condition);

    void save(QuestionsBean bean);

    void update(QuestionsBean bean);
    void deletes(Integer[] ids);

    List<Map<String,Object>> randomSelect();

    List<QuestionsBean> selectInIds(List<PaperBean> items);

}