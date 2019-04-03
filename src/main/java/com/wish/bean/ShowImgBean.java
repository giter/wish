package com.wish.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;



public class ShowImgBean implements Serializable {

	//需要展示图片的id
	private Integer id;

	//图片相对路径
	private String imgPath;

	//展示顺序
	private Integer viewOrder;
//图片真实名
	private String realName;
//图片状态，1上架，2下架
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

	public String getImgPath(){
		return this.imgPath;
	}

	public void setImgPath(String imgPath){
		this.imgPath = imgPath;
	}

	public Integer getViewOrder(){
		return this.viewOrder;
	}

	public void setViewOrder(Integer viewOrder){
		this.viewOrder = viewOrder;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}