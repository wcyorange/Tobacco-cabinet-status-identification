package com.cloud.pay.entity;

public class FlatformSource {
    private Integer sId;

    private Integer sType;

    private String sName;

    private String sDesc;

    private String sMobile;

    private String sAddress;

    private Integer sStar;

    private String v1;

    private String v2;

    private String v3;

    private String v4;

    private String v5;

    private String v6;

    private String v7;

    private String sMoney;

    private Integer deleteStatus;

    private String deleteTime;

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getsType() {
        return sType;
    }

    public void setsType(Integer sType) {
        this.sType = sType;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName == null ? null : sName.trim();
    }

    public String getsDesc() {
        return sDesc;
    }

    public void setsDesc(String sDesc) {
        this.sDesc = sDesc == null ? null : sDesc.trim();
    }

    public String getsMobile() {
        return sMobile;
    }

    public void setsMobile(String sMobile) {
        this.sMobile = sMobile == null ? null : sMobile.trim();
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress == null ? null : sAddress.trim();
    }

    public Integer getsStar() {
        return sStar;
    }

    public void setsStar(Integer sStar) {
        this.sStar = sStar;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1 == null ? null : v1.trim();
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2 == null ? null : v2.trim();
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3 == null ? null : v3.trim();
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4 == null ? null : v4.trim();
    }

    public String getV5() {
        return v5;
    }

    public void setV5(String v5) {
        this.v5 = v5 == null ? null : v5.trim();
    }

    public String getV6() {
        return v6;
    }

    public void setV6(String v6) {
        this.v6 = v6 == null ? null : v6.trim();
    }

    public String getV7() {
        return v7;
    }

    public void setV7(String v7) {
        this.v7 = v7 == null ? null : v7.trim();
    }

    public String getsMoney() {
        return sMoney;
    }

    public void setsMoney(String sMoney) {
        this.sMoney = sMoney == null ? null : sMoney.trim();
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
}