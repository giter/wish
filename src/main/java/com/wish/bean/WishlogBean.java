package com.wish.bean;
import java.io.Serializable;
import java.util.Date;


public class WishlogBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//id
	private Integer id;

	//活动id
	private Integer activityId;

	//心愿id
	private Integer wishId;

	//购买人id
	private Integer buyerId;

	//具体心愿描述
	private String introduction;

	//心愿状态，1未使用，2已使用
	private Integer status;

	//等分总计
	private Integer point;

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

	public Integer getWishId(){
		return this.wishId;
	}

	public void setWishId(Integer wishId){
		this.wishId = wishId;
	}

	public Integer getBuyerId(){
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId){
		this.buyerId = buyerId;
	}

	public Integer getPoint(){
		return this.point;
	}

	public void setPoint(Integer point){
		this.point = point;
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}