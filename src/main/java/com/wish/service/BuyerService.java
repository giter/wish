package com.wish.service;

import com.wish.bean.BuyerBean;
import com.wish.dao.BuyerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BuyerService {

    @Resource
    private BuyerDao buyerDao;


    /**
     * 登录
     * 用户不存在则注册，用户存在则登录
     * @param buyerBean
     * @return
     */
    public BuyerBean login(BuyerBean buyerBean) {
        BuyerBean buyerBean1 = buyerDao.findByOpenId(buyerBean.getOpenId());
        if (buyerBean1==null){
            buyerBean.setPoints(0);
            buyerBean.setZan(0);
            buyerDao.save(buyerBean);
            return buyerBean;
        }else {
            return buyerBean1;
        }
    }

    public BuyerBean findById(Integer id){
        return buyerDao.findById(id);
    }
}
