package com.wish.bean;
import java.io.Serializable;
import java.util.Date;



public class AcWishBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//id
	private Integer id;

	//活动id
	private Integer activityId;

	//心愿内容
	private String content;

	//是否使用，1使用，0禁用
	private Integer useflag;

	//插入时间
	private Date insertTime;

	//插入用户id
	private Integer insertId;

	//更新时间
	private Date updateTime;

	//更新人id
	private Integer updateId;


	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getActivityId(){
		return this.activityId;
	}

	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

	public String getContent(){
		return this.content;
	}

	public void setContent(String content){
		this.content = content;
	}

	public Integer getUseflag(){
		return this.useflag;
	}

	public void setUseflag(Integer useflag){
		this.useflag = useflag;
	}

	public Date getInsertTime(){
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime){
		this.insertTime = insertTime;
	}

	public Integer getInsertId(){
		return this.insertId;
	}

	public void setInsertId(Integer insertId){
		this.insertId = insertId;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Integer getUpdateId(){
		return this.updateId;
	}

	public void setUpdateId(Integer updateId){
		this.updateId = updateId;
	}


}