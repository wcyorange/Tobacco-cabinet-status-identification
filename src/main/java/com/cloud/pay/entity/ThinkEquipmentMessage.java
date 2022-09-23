package com.cloud.pay.entity;

/**
 * @Author:宁志洋
 * @Date:2020/9/18 19:18
 */
public class ThinkEquipmentMessage {
    private Integer id;
    private Integer eqId;
    private String title;
    private String msg;
    private String imgUrl;
    private Byte isGood;
    private Byte status;
    private String createTime;
    private String updateTime;
    private Byte deleteStatus;
    private String deleteTime;

    public ThinkEquipmentMessage() {
    }

    public ThinkEquipmentMessage(Integer id, Integer eqId, String title, String msg, String imgUrl, Byte isGood, Byte status, String createTime, String updateTime, Byte deleteStatus, String deleteTime) {
        this.id = id;
        this.eqId = eqId;
        this.title = title;
        this.msg = msg;
        this.imgUrl = imgUrl;
        this.isGood = isGood;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteStatus = deleteStatus;
        this.deleteTime = deleteTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEqId() {
        return eqId;
    }

    public void setEqId(Integer eqId) {
        this.eqId = eqId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Byte getIsGood() {
        return isGood;
    }

    public void setIsGood(Byte isGood) {
        this.isGood = isGood;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String creatTime) {
        this.createTime = creatTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Byte deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }
}
