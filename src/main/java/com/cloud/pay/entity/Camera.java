package com.cloud.pay.entity;

/**
 * @author 宁志洋
 */

public class Camera {

    private String ip;
    private short port;
    private String username;
    private String password;
    //设备信息
    private String info;
    //默认序号
    private Integer defaultID;

    public Camera() {
    }

    public Camera(String ip, short port, String username, String password, String info, Integer defaultID) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.info = info;
        this.defaultID = defaultID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getDefaultID() {
        return defaultID;
    }

    public void setDefaultID(Integer defaultID) {
        this.defaultID = defaultID;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", info=" + info +
                ", defaultID=" + defaultID +
                '}';
    }
}
