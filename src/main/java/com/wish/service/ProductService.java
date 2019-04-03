package com.wish.service;

import com.wish.bean.GoodsDetailBean;
import com.wish.bean.MachineGoodsBean;
import com.wish.bean.ProductBean;
import com.wish.dao.MachineGoodsDAO;
import com.wish.dao.ProductDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * create by ff on 2018/7/28
 */
@Service
public class ProductService {

    @Resource
    private MachineGoodsDAO machineGoodsDAO;

    @Resource
    private ProductDAO productDAO;

    public List<GoodsDetailBean> findByMachineId(String id){
        Map<String,Object> condition = new HashMap<>();
        condition.put("machineId",id);
        return machineGoodsDAO.query(condition);
    }

    public void save(ProductBean productBean){
        productDAO.save(productBean);
    }

    public void update(ProductBean productBean){
        productDAO.update(productBean);
    }

    public List<ProductBean> query(Map<String,Object> condition){
        return productDAO.query(condition);
    }

    public ProductBean findById(Integer id){
       return productDAO.findById(id);
    }



    public void deletes(String[] ids){
        productDAO.deletes(ids);
    }

}
