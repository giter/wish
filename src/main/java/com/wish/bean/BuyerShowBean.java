package com.wish.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;



public class BuyerShowBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//id
	private Integer id;

	//购买人id
	private Integer buyerId;

	//机器id
	private String machineId;

	//上传图片地址
	private String img;

	//描述
	private String describe;

	//点赞数
	private Integer zan;

	//状态，1未审核，2审核通过
	private Integer status;
	//备注，暂时无用
	private String note;

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

	public Integer getBuyerId(){
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId){
		this.buyerId = buyerId;
	}

	public String getMachineId(){
		return this.machineId;
	}

	public void setMachineId(String machineId){
		this.machineId = machineId;
	}

	public String getImg(){
		return this.img;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getDescribe(){
		return this.describe;
	}

	public void setDescribe(String describe){
		this.describe = describe;
	}

	public Integer getZan(){
		return this.zan;
	}

	public void setZan(Integer zan){
		this.zan = zan;
	}

	public String getNote(){
		return this.note;
	}

	public void setNote(String note){
		this.note = note;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}