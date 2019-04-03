package com.wish.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;



public class QuestionsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
	//id
	private Integer id;

	//问题
	private String question;

	//答案a
	private String answerA;

	//答案b
	private String answerB;

	//答案c
	private String answerC;

	//答案d
	private String answerD;

	//正确答案，只能填写ABCD中的一个
	private String correct;

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

	public String getQuestion(){
		return this.question;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getAnswerA(){
		return this.answerA;
	}

	public void setAnswerA(String answerA){
		this.answerA = answerA;
	}

	public String getAnswerB(){
		return this.answerB;
	}

	public void setAnswerB(String answerB){
		this.answerB = answerB;
	}

	public String getAnswerC(){
		return this.answerC;
	}

	public void setAnswerC(String answerC){
		this.answerC = answerC;
	}

	public String getAnswerD(){
		return this.answerD;
	}

	public void setAnswerD(String answerD){
		this.answerD = answerD;
	}

	public String getCorrect(){
		return this.correct;
	}

	public void setCorrect(String correct){
		this.correct = correct;
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