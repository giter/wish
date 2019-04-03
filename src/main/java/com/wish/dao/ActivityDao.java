package com.wish.dao;


import com.wish.bean.ActivityBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao  {

    ActivityBean findById(Integer id);

    List<ActivityBean> findAll();

    List<ActivityBean> query(Map<String, Object> condition);

    void save(ActivityBean bean);

    void update(ActivityBean bean);
    void deletes(Integer[] ids);

    Integer inTime(@Param("id") Integer id);

}