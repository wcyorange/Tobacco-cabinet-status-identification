package com.cloud.pay.entity;

public class FlatformTaxType {
    private Integer ttId;

    private String ttName;

    private Integer tType;

    private Integer num;

    private Float tax;

    private Float pTax;

    private Float money;

    public Integer getTtId() {
        return ttId;
    }

    public void setTtId(Integer ttId) {
        this.ttId = ttId;
    }

    public String getTtName() {
        return ttName;
    }

    public void setTtName(String ttName) {
        this.ttName = ttName == null ? null : ttName.trim();
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getpTax() {
        return pTax;
    }

    public void setpTax(Float pTax) {
        this.pTax = pTax;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}