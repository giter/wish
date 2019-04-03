package com.wish.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * create by ff on 2018/7/26
 */
public class RoleBean implements Serializable {

    /**/
    private Integer id;
    /**/
    private String role;
    /**/
    private String description;
    /*是否使用，1使用，0禁用*/
    private Integer useflag;
    /*插入时间*/
    private Date insertTime;
    /*插入用户id*/
    private Integer insertId;
    /*更新时间*/
    private Date updateTime;
    /*更新人id*/
    private Integer updateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUseflag() {
        return useflag;
    }

    public void setUseflag(Integer useflag) {
        this.useflag = useflag;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getInsertId() {
        return insertId;
    }

    public void setInsertId(Integer insertId) {
        this.insertId = insertId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}
