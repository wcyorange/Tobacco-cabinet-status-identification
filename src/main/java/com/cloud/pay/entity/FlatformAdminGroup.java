package com.cloud.pay.entity;



public class FlatformAdminGroup implements Comparable<FlatformAdminGroup>{

	private Integer agId;

	private String agName;

	private String agInfo;

	private String agAuth;

	private Integer agStatus;

	private Integer uId;

	private String createTime;

	private Integer deleteStatus;

	private String deleteTime;

	public Integer getAgId() {
		return agId;
	}

	public void setAgId(Integer agId) {
		this.agId = agId;
	}

	public String getAgName() {
		return agName;
	}

	public void setAgName(String agName) {
		this.agName = agName;
	}

	public String getAgInfo() {
		return agInfo;
	}

	public void setAgInfo(String agInfo) {
		this.agInfo = agInfo;
	}

	public String getAgAuth() {
		return agAuth;
	}

	public void setAgAuth(String agAuth) {
		this.agAuth = agAuth;
	}

	public Integer getAgStatus() {
		return agStatus;
	}

	public void setAgStatus(Integer agStatus) {
		this.agStatus = agStatus;
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

	@Override
	public int compareTo(FlatformAdminGroup o) {
		return 0;
	}
}