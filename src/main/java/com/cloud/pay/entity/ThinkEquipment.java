package com.cloud.pay.entity;

/**
 * @Author:宁志洋
 * @Date:2020/9/18 19:17
 */
public class ThinkEquipment {
    private Integer id;
    private String code;
    private String ip;
    private String username;
    private String password;
    private String port;
    private String liveUrl;
    private String name;
    private Byte cate;
    private Byte status;
    private String createTime;
    private String updateTime;
    private Byte deleteStatus;
    private String liveImg;
    private String statusMsg;
    private String deleteTime;

    public ThinkEquipment() {
    }

    public ThinkEquipment(Integer id, String code, String ip, String username, String password, String port, String liveUrl, String name, Byte cate, Byte status, String createTime, String updateTime, Byte deleteStatus, String liveImg, String statusMsg,String deleteTime) {
        this.id = id;
        this.code = code;
        this.ip = ip;
        this.username = username;
        this.password = password;
        this.port = port;
        this.liveUrl = liveUrl;
        this.name = name;
        this.cate = cate;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteStatus = deleteStatus;
        this.liveImg = liveImg;
        this.statusMsg = statusMsg;
        this.deleteTime = deleteTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getCate() {
        return cate;
    }

    public void setCate(Byte cate) {
        this.cate = cate;
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

    public String getLiveImg() {
        return liveImg;
    }

    public void setLiveImg(String liveImg) {
        this.liveImg = liveImg;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return "ThinkEquipment{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", ip='" + ip + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port='" + port + '\'' +
                ", liveUrl='" + liveUrl + '\'' +
                ", name='" + name + '\'' +
                ", cate=" + cate +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", liveImg='" + liveImg + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                ", deleteTime='" + deleteTime + '\'' +
                '}';
    }
}
