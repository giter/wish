package com.wish.bean;

import java.io.Serializable;
import java.util.Date;

public class GoodsDetailBean implements Serializable {

    //id
    private Integer id;

    //机器编号
    private String machineId;

    //货道号
    private String slotNo;

    //按键号
    private Integer keyNum;

    //货道状态，0正常，其他故障
    private Integer status;

    //现存（上货/配送后）
    private Integer stock;

    //容量
    private Integer capacity;

//    产品编号
    private Integer productId;

    //商品名称
    private String name;

//    商品价格
    private int price;

//    商品类型
    private String type;

//    商品简介
    private String introduction;

//    商品图片地址
    private String imageUrl;

//    商品详情图片地址
    private String imageDetailUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public Integer getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(Integer keyNum) {
        this.keyNum = keyNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDetailUrl() {
        return imageDetailUrl;
    }

    public void setImageDetailUrl(String imageDetailUrl) {
        this.imageDetailUrl = imageDetailUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
