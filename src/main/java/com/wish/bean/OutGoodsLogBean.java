package com.wish.bean;
import java.io.Serializable;
import java.util.Date;



public class OutGoodsLogBean implements Serializable {
    
	//id
	private Integer id;

	//机器编号
	private String machineId;

	//支付类型
	private Integer payType;

	//订单号
	private String tradeNo;

	//货道号
	private String slotNo;

	//出货状态
	private Integer status;

	//时间
	private String time;

	//金额
	private String amount;

	//产品编码
	private String productId;

	//商品名称
	private String name;

	//商品类型
	private String type;

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

	public String getMachineId(){
		return this.machineId;
	}

	public void setMachineId(String machineId){
		this.machineId = machineId;
	}

	public Integer getPayType(){
		return this.payType;
	}

	public void setPayType(Integer payType){
		this.payType = payType;
	}

	public String getTradeNo(){
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo){
		this.tradeNo = tradeNo;
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

	public String getTime(){
		return this.time;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getAmount(){
		return this.amount;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getProductId(){
		return this.productId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type = type;
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