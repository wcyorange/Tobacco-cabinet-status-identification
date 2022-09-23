package com.cloud.pay.entity;

/**
 * @Author:宁志洋
 * @Date:2020/9/18 19:18
 */
public class ThinkEquipmentVideo {
    private Integer id;
    private Integer eqId;
    private String videoUrl;
    private String unionId;
    private Byte status;
    private String createTime;
    private String updateTime;
    private String beginTime;
    private String endTime;
    private Byte deleteStatus;
    private String deleteTime;

    public ThinkEquipmentVideo() {
    }

    public ThinkEquipmentVideo(Integer id, Integer eqId, String videoUrl, String unionId, Byte status, String createTime, String updateTime, String beginTime, String endTime, Byte deleteStatus, String deleteTime) {
        this.id = id;
        this.eqId = eqId;
        this.videoUrl = videoUrl;
        this.unionId = unionId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.beginTime = beginTime;
        this.endTime = endTime;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
