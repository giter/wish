package com.wish.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;



public class GoodsOrderBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//
	private Integer id;

	//支付订单id
	private Integer payOrderId;

	//机器id
	private String machineId;

	//货道号
	private String slotNo;
//	产品编号
	private Integer productId;

	private Integer price;

	//是否已经出货，-3,已经下单，-2 等待出货,-1正在出货，0出货成功，1出货失败，4出货结果未知
	private Integer status;

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

	public Integer getPayOrderId(){
		return this.payOrderId;
	}

	public void setPayOrderId(Integer payOrderId){
		this.payOrderId = payOrderId;
	}

	public String getMachineId(){
		return this.machineId;
	}

	public void setMachineId(String machineId){
		this.machineId = machineId;
	}

	public String getSlotNo(){
		return this.slotNo;
	}

	public void setSlotNo(String slotNo){
		this.slotNo = slotNo;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setStatus(Integer status){
		this.status = status;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}