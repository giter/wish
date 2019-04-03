package com.wish.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;



public class PaperBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//id
	private Integer id;

	//问题id
	private Integer questionId;

	//答案
	private String answer;

	//状态，是否正确，1正确0错误
	private Integer status;

	//是否使用，1使用，0禁用
	private Integer useflag;

	//插入时间
	private Date insertTime;

	//插入用户id，也就是答题人id，buyerid
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

	public Integer getQuestionId(){
		return this.questionId;
	}

	public void setQuestionId(Integer questionId){
		this.questionId = questionId;
	}

	public String getAnswer(){
		return this.answer;
	}

	public void setAnswer(String answer){
		this.answer = answer;
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