package com.cloud.pay.entity;

/**
 * @Author:宁志洋
 * @Date:2020/9/18 19:17
 */
public class ThinkEquipmentCate {
    private Integer id;
    private String name;
    private Byte status;
    private String createTime;
    private String updateTime;
    private Byte deleteStatus;
    private String deleteTime;

    public ThinkEquipmentCate() {
    }

    public ThinkEquipmentCate(Integer id, String name, Byte status, String createTime, String updateTime, Byte deleteStatus, String deleteTime) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreatTime() {
        return createTime;
    }

    public void setCreatTime(String creatTime) {
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
