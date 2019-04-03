package com.wish.service;

import com.wish.bean.BuyerShowBean;
import com.wish.dao.BuyerDao;
import com.wish.dao.BuyerShowDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/8/23
 */
@Service
public class BuyShowService {

    @Resource
    private BuyerShowDao buyerShowDao;
    @Resource
    private BuyerDao buyerDao;

    public void save(BuyerShowBean buyerShowBean){
        buyerShowDao.save(buyerShowBean);
    }

    public List<Map<String,Object>> query(Map<String,Object> condition){
        return buyerShowDao.query(condition);
    }

    @Transactional
    public void zan(Integer id,Integer buyerId){
        buyerShowDao.zan(id);
        buyerDao.zan(buyerId);
    }

    public void update(BuyerShowBean buyerShowBean){
        buyerShowDao.update(buyerShowBean);
    }
}
