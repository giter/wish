package com.wish.service;

import com.wish.bean.ShowImgBean;
import com.wish.dao.ShowImgDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图服务
 */
@Service
public class ImgService {

    @Resource
    private ShowImgDao showImgDao;

    public List<ShowImgBean> findAll(){
        return showImgDao.findAll();
    }

    public void save(ShowImgBean showImgBean){
        showImgDao.save(showImgBean);
    }

    public void update(ShowImgBean showImgBean){
        showImgDao.update(showImgBean);
    }

    public ShowImgBean findById(Integer id){
        return showImgDao.findById(id);
    }

}
