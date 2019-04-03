package com.wish.service;

import com.wish.bean.AddGoodsLogBean;
import com.wish.bean.MachineGoodsBean;
import com.wish.bean.ProductBean;
import com.wish.dao.AddGoodsLogDAO;
import com.wish.dao.MachineDAO;
import com.wish.dao.MachineGoodsDAO;
import com.wish.dao.ProductDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddGoodsService {
    Logger logger = Logger.getLogger(this.getClass().getName());


    @Resource
    private AddGoodsLogDAO addGoodsLogDAO;

    @Resource
    private MachineDAO machineDAO;

    @Resource
    private ProductDAO productDAO;

    @Resource
    private MachineGoodsDAO machineGoodsDAO;



    /**
     * 机器加货
     * 1.保存商品添加日志
     * 2.查看商品表中是否具有改id的商品，没有则添加，返回商品基本信息
     * 3.修改机器商品表中商品的数量，和库存
     * @param addGoodsLogBean
     * @return
     */

    public ProductBean addGoods(AddGoodsLogBean addGoodsLogBean){
        addGoodsLogDAO.save(addGoodsLogBean);
        ProductBean productBean = productDAO.findById(Integer.parseInt(addGoodsLogBean.getProductId()));
        if (productBean==null){//如果为空则临时添加一个商品信息，以确保正常加货
            productBean = new ProductBean();
            productBean.setId(Integer.parseInt(addGoodsLogBean.getProductId()));
            productBean.setPrice(addGoodsLogBean.getPrice());
            productBean.setType(addGoodsLogBean.getType());
            productBean.setName(addGoodsLogBean.getMachineId()+"-"+addGoodsLogBean.getSlotNo());
            productBean.setIntroduction("机器:"+addGoodsLogBean.getMachineId()+" 货道:"+addGoodsLogBean.getSlotNo()+"添加未知商品，商品id:"+addGoodsLogBean.getProductId());
            productBean.setInsertId(0);
            productDAO.save(productBean);
        }


        MachineGoodsBean machineGoodsBean = new MachineGoodsBean();
        machineGoodsBean.setMachineId(addGoodsLogBean.getMachineId());
        machineGoodsBean.setSlotNo(addGoodsLogBean.getSlotNo());
        machineGoodsBean.setKeyNum(addGoodsLogBean.getKeyNum());
        machineGoodsBean.setStatus(addGoodsLogBean.getStatus());
        machineGoodsBean.setStock(addGoodsLogBean.getStock());
        machineGoodsBean.setCapacity(addGoodsLogBean.getCapacity());
        machineGoodsBean.setProductId(addGoodsLogBean.getProductId());
        machineGoodsBean.setInsertId(0);
        machineGoodsBean.setUpdateId(0);

        MachineGoodsBean machineGoodsBeanOld = machineGoodsDAO.getByMachineIdAndSlot(addGoodsLogBean.getMachineId(),addGoodsLogBean.getSlotNo());
        if (machineGoodsBeanOld==null){//如果是第一次进行加货，则添加货道信息,否则更新货道信息
            machineGoodsDAO.save(machineGoodsBean);
        }else {
            machineGoodsBean.setId(machineGoodsBeanOld.getId());
            machineGoodsDAO.update(machineGoodsBean);
        }
        return productBean;
    }


    /**
     * 上货，发现机器故障，进行处理
     * 发生错误，应当发送短信给用户。 ====================================未完成
     * @param addGoodsLogBean
     */
    public void sendErr(AddGoodsLogBean addGoodsLogBean) {
        if (addGoodsLogBean.getStatus()==0){
            logger.warn("机器编号"+addGoodsLogBean.getMachineId()+"的"+addGoodsLogBean.getSlotNo()+"货道故障");
        }
    }
}
