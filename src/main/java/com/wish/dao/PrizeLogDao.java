package com.wish.dao;


import java.util.List;
import java.util.Map;
import com.wish.bean.PrizeBean;
import com.wish.bean.PrizeLogBean;
import org.apache.ibatis.annotations.Param;


public interface PrizeLogDao {

    PrizeLogBean findById(@Param("id") Integer id);

    List<PrizeLogBean> findAll();

    List<Map<String,Object>> query(Map<String, Object> condition);

    void save(PrizeLogBean bean);

    void update(PrizeLogBean bean);

    void deletes(Integer[] ids);

    PrizeBean findPrizeById(Integer id);

    PrizeBean findPrizeByOldId(String id);

    //    兑奖，只能是未兑换的才可以兑奖
    void cachePrize(PrizeLogBean prizeLogBean);

    void oldcachePrize(PrizeLogBean prizeLogBean);


}