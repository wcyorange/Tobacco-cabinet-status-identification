package com.cloud.pay.entity;

public class FlatformCostFlow {
    private Integer cfId;

    private Integer cfType;

    private String cfTime;

    private Integer parId;

    private String cusName;

    private String cusMobile;

    private String cusAddress;

    private String cusPrepareTime;

    private String cusDesc;

    private Integer sId;

    private Float money;

    private Integer status;

    private Integer deleteStatus;

    private String deleteTime;

    private String typeName;

    private FlatformPartner flatformPartner;

    public Integer getCfId() {
        return cfId;
    }

    public void setCfId(Integer cfId) {
        this.cfId = cfId;
    }

    public Integer getCfType() {
        return cfType;
    }

    public void setCfType(Integer cfType) {
        this.cfType = cfType;
    }

    public String getCfTime() {
        return cfTime;
    }

    public void setCfTime(String cfTime) {
        this.cfTime = cfTime == null ? null : cfTime.trim();
    }

    public Integer getParId() {
        return parId;
    }

    public void setParId(Integer parId) {
        this.parId = parId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName == null ? null : cusName.trim();
    }

    public String getCusMobile() {
        return cusMobile;
    }

    public void setCusMobile(String cusMobile) {
        this.cusMobile = cusMobile == null ? null : cusMobile.trim();
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress == null ? null : cusAddress.trim();
    }

    public String getCusPrepareTime() {
        return cusPrepareTime;
    }

    public void setCusPrepareTime(String cusPrepareTime) {
        this.cusPrepareTime = cusPrepareTime == null ? null : cusPrepareTime.trim();
    }

    public String getCusDesc() {
        return cusDesc;
    }

    public void setCusDesc(String cusDesc) {
        this.cusDesc = cusDesc == null ? null : cusDesc.trim();
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.deleteTime = deleteTime == null ? null : deleteTime.trim();
    }

    public FlatformPartner getFlatformPartner() {
        return flatformPartner;
    }

    public void setFlatformPartner(FlatformPartner flatformPartner) {
        this.flatformPartner = flatformPartner;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}