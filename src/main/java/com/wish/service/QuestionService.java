package com.wish.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wish.Config;
import com.wish.bean.PaperBean;
import com.wish.bean.QuestionsBean;
import com.wish.dao.QuestionsDao;
import org.apache.commons.collections.MapUtils;import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private QuestionsDao questionsDao;

    //随机抽取问题，目前是3个
    public List<Map<String,Object>> randomSelect(){
        return questionsDao.randomSelect();
    }


    //判分
    public Integer mark(List<PaperBean> paperBeans,Integer buyerId){
        List<QuestionsBean> questionsBeans = questionsDao.selectInIds(paperBeans);
        Integer point=0;
        boolean flag = true;//是否全对标识
        for (PaperBean paperBean:paperBeans){
            paperBean.setInsertId(buyerId);
            if (checkQuestion(paperBean.getQuestionId(),paperBean.getAnswer(),questionsBeans)){
                paperBean.setStatus(1);
                point += Config.QUESTIONPOINT;
            }else {
                flag=false;
                paperBean.setStatus(0);
            }
        }
        if (flag){//全部答对加1分
            point += Config.QUESTIONPOINT;
        }
        return point;
    }


    private boolean checkQuestion(Integer id,String answer,List<QuestionsBean> questionsBeans){
        for (QuestionsBean questionsBean:questionsBeans){
            if (id.equals(questionsBean.getId())){
                answer=answer.trim().toUpperCase();
                if (answer.equals(questionsBean.getCorrect().trim().toUpperCase())){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }


}
