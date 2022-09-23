package com.cloud.pay.entity;

public class FlatformPartner {
    private Integer parId;

    private String parName;

    private String identity;

    private String imgUrlFont;

    private String imgUrlBack;

    private Integer uId;

    private Integer pId;

    private String address;

    private String openid;

    private String tinyName;

    private String sex;

    private String avatarUrl;

    private String mobile;

    private Integer status;

    private String createTime;

    private Integer deleteStatus;

    private String deleteTime;

    private String cardnumber;

    private String cardbank;

    private String cardname;

    private Integer dealNumbers;

    public Integer getParId() {
        return parId;
    }

    public void setParId(Integer parId) {
        this.parId = parId;
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName == null ? null : parName.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getImgUrlFont() {
        return imgUrlFont;
    }

    public void setImgUrlFont(String imgUrlFont) {
        this.imgUrlFont = imgUrlFont == null ? null : imgUrlFont.trim();
    }

    public String getImgUrlBack() {
        return imgUrlBack;
    }

    public void setImgUrlBack(String imgUrlBack) {
        this.imgUrlBack = imgUrlBack == null ? null : imgUrlBack.trim();
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getTinyName() {
        return tinyName;
    }

    public void setTinyName(String tinyName) {
        this.tinyName = tinyName == null ? null : tinyName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
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

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public String getCardbank() {
        return cardbank;
    }

    public void setCardbank(String cardbank) {
        this.cardbank = cardbank == null ? null : cardbank.trim();
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname == null ? null : cardname.trim();
    }

    public Integer getDealNumbers() {
        return dealNumbers;
    }

    public void setDealNumbers(Integer dealNumbers) {
        this.dealNumbers = dealNumbers;
    }
}