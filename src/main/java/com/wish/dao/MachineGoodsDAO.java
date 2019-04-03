package com.wish.dao;


import java.util.List;
import java.util.Map;

import com.wish.bean.GoodsDetailBean;
import com.wish.bean.MachineGoodsBean;
import org.apache.ibatis.annotations.Param;


public interface MachineGoodsDAO {

    MachineGoodsBean findById(Integer id);

    List<MachineGoodsBean> findAll();

    List<GoodsDetailBean> query(Map<String, Object> condition);

    void save(MachineGoodsBean bean);

    void oldMachineSave(@Param("list") List<MachineGoodsBean> list);

    void update(MachineGoodsBean bean);

    void deletes(Integer[] ids);

     void reduceSotck(@Param("machineId") String machineId,@Param("slotNo") String slotNo);

    MachineGoodsBean getByMachineIdAndSlot(@Param("machineId") String machineId,@Param("soltNo") String slotNo);

    List<Map<String,Object>> queryGoodsByMachineId(String machineId);



}