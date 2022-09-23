package com.cloud.pay.entity;

public class FlatformOrder {

    private String orderId;

    private String orderName;

    private String orderTime;

    private Integer id;

    private  Integer orderType;

    private Integer parId;

    private Float money;

    private String createTime;

    private Integer deleteStatus;

    private String deleteTime;

    private String parName1;

    private FlatformPartner flatformPartner;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getParId() {
        return parId;
    }

    public void setParId(Integer parId) {
        this.parId = parId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public FlatformPartner getFlatformPartner() {
        return flatformPartner;
    }

    public void setFlatformPartner(FlatformPartner flatformPartner) {
        this.flatformPartner = flatformPartner;
    }

    public String getParName1() {
        return parName1;
    }

    public void setParName1(String parName1) {
        this.parName1 = parName1;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
