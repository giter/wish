package com.wish.dao;


import com.wish.bean.AnswerLogBean;
import java.util.List;
import java.util.Map;

public interface AnswerLogDao {

    List<AnswerLogBean> query(Map<String, Object> condition);

    void save(AnswerLogBean bean);
}