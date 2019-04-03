package com.wish.bean;

import java.io.Serializable;
import java.util.*;



public class PayOrderBean implements Serializable {
    
	//
	private Integer id;

	//支付订单号
	private String payTradeNo;

	private String machineId;
	//总金额单位为分，或者积分，
	private Integer totalFee;

	//支付类型，1为微信，2为积分
	private Integer payType;

	//支付状态，0待支付，1字符成功
	private Integer payStatus;

	//购买商品总数
	private Integer buyNum;

	//出货商品总数
	private Integer outNum;

	//描述
	private String detail;

	//插入时间
	private Date insertTime;

	//插入id，即购买人id
	private Integer insertId;

	//更新时间
	private Date updateTime;

	//更新人id
	private Integer updateId;

	private List<String> names;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getPayTradeNo(){
		return this.payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo){
		this.payTradeNo = payTradeNo;
	}

	public Integer getTotalFee(){
		return this.totalFee;
	}

	public void setTotalFee(Integer totalFee){
		this.totalFee = totalFee;
	}

	public Integer getPayType(){
		return this.payType;
	}

	public void setPayType(Integer payType){
		this.payType = payType;
	}

	public Integer getPayStatus(){
		return this.payStatus;
	}

	public void setPayStatus(Integer payStatus){
		this.payStatus = payStatus;
	}

	public Integer getBuyNum(){
		return this.buyNum;
	}

	public void setBuyNum(Integer buyNum){
		this.buyNum = buyNum;
	}

	public Integer getOutNum(){
		return this.outNum;
	}

	public void setOutNum(Integer outNum){
		this.outNum = outNum;
	}

	public String getDetail(){
		return this.detail;
	}

	public void setDetail(String detail){
		this.detail = detail;
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

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
}