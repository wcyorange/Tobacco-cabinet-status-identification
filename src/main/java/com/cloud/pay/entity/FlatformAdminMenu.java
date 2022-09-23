package com.cloud.pay.entity;


import java.util.List;

public class FlatformAdminMenu {
   private Integer auId;

   private String auUrl;

   private String auTitle;

   private String auInfo;

   private Integer auLevel;

   private Integer auClickable;

   private Integer auPId;

   private Integer auSort;

   private Integer auState;

   private String auClass;

   private Integer auType;

   private Integer uId;

   private Integer checked;

   private String createTime;

   private Integer deleteStatus;

   private String deleteTime;

   private List<FlatformAdminMenu> sub_list;

    public Integer getAuId() {
        return auId;
    }

    public void setAuId(Integer auId) {
        this.auId = auId;
    }

    public String getAuUrl() {
        return auUrl;
    }

    public void setAuUrl(String auUrl) {
        this.auUrl = auUrl;
    }

    public String getAuTitle() {
        return auTitle;
    }

    public void setAuTitle(String auTitle) {
        this.auTitle = auTitle;
    }

    public String getAuInfo() {
        return auInfo;
    }

    public void setAuInfo(String auInfo) {
        this.auInfo = auInfo;
    }

    public Integer getAuLevel() {
        return auLevel;
    }

    public void setAuLevel(Integer auLevel) {
        this.auLevel = auLevel;
    }

    public Integer getAuClickable() {
        return auClickable;
    }

    public void setAuClickable(Integer auClickable) {
        this.auClickable = auClickable;
    }

    public Integer getAuPId() {
        return auPId;
    }

    public void setAuPId(Integer auPId) {
        this.auPId = auPId;
    }

    public Integer getAuSort() {
        return auSort;
    }

    public void setAuSort(Integer auSort) {
        this.auSort = auSort;
    }

    public Integer getAuState() {
        return auState;
    }

    public void setAuState(Integer auState) {
        this.auState = auState;
    }

    public String getAuClass() {
        return auClass;
    }

    public void setAuClass(String auClass) {
        this.auClass = auClass;
    }

    public Integer getAuType() {
        return auType;
    }

    public void setAuType(Integer auType) {
        this.auType = auType;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
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

    public List<FlatformAdminMenu> getSub_list() {
        return sub_list;
    }

    public void setSub_list(List<FlatformAdminMenu> sub_list) {
        this.sub_list = sub_list;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}