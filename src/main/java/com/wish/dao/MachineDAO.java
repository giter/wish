package com.wish.dao;

import java.util.List;
import java.util.Map;
import com.wish.bean.MachineBean;
import org.apache.ibatis.annotations.Param;


public interface MachineDAO {

    MachineBean findById(@Param("id") String id);

    List<MachineBean> findAll();

    List<MachineBean> query(Map<String, Object> condition);

    void save(MachineBean bean);

    void update(MachineBean bean);

    void deletes(String[] ids);


}