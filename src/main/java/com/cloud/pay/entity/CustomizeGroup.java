package com.cloud.pay.entity;

/**
 * @author 宁志洋
 * @date 2020/10/31
 */
public class CustomizeGroup {
    private Integer id;
    private String groupName;
    private String eqId;
    private String uName;
    private Integer deleteStatus;
    private String deleteTime;

    public CustomizeGroup() {
    }

    public CustomizeGroup(Integer id, String groupName, String eqId, Integer deleteStatus, String deleteTime,String uName) {
        this.id = id;
        this.groupName = groupName;
        this.eqId = eqId;
        this.uName = uName;
        this.deleteStatus = deleteStatus;
        this.deleteTime = deleteTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
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

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    @Override
    public String toString() {
        return "CustomizeGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", eqId='" + eqId + '\'' +
                '}';
    }
}
