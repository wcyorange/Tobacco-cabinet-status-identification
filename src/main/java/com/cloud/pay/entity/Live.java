package com.cloud.pay.entity;

/**
 * @author Administrator
 */

public class Live {
    private Integer lId;
    private String lName;
    private String lCode;
    private String lMsg;
    private String lLiveUrl;
    private String lCreateTime;
    private Integer lDeleteStatus;
    private String lDeleteTime;

    public Live() {
    }

    public Live(Integer lId, String lName, String lCode, String lMsg, String lLiveUrl, String lCreateTime, Integer lDeleteStatus, String lDeleteTime) {
        this.lId = lId;
        this.lName = lName;
        this.lCode = lCode;
        this.lMsg = lMsg;
        this.lLiveUrl = lLiveUrl;
        this.lCreateTime = lCreateTime;
        this.lDeleteStatus = lDeleteStatus;
        this.lDeleteTime = lDeleteTime;
    }

    public Integer getlId() {
        return lId;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlCode() {
        return lCode;
    }

    public void setlCode(String lCode) {
        this.lCode = lCode;
    }

    public String getlMsg() {
        return lMsg;
    }

    public void setlMsg(String lMsg) {
        this.lMsg = lMsg;
    }

    public String getlLiveUrl() {
        return lLiveUrl;
    }

    public void setlLiveUrl(String lLiveUrl) {
        this.lLiveUrl = lLiveUrl;
    }

    public String getlCreateTime() {
        return lCreateTime;
    }

    public void setlCreateTime(String lCreateTime) {
        this.lCreateTime = lCreateTime;
    }

    public Integer getlDeleteStatus() {
        return lDeleteStatus;
    }

    public void setlDeleteStatus(Integer lDeleteStatus) {
        this.lDeleteStatus = lDeleteStatus;
    }

    public String getlDeleteTime() {
        return lDeleteTime;
    }

    public void setlDeleteTime(String lDeleteTime) {
        this.lDeleteTime = lDeleteTime;
    }
}
