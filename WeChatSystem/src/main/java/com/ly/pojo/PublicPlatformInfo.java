package com.ly.pojo;

/**
 * 公众平台基本信息
 * @author 刘勇
 *
 */
public class PublicPlatformInfo {
	
	
	private int id;           //唯一编号
	private String appID;     //应用ID
	private String appsecret; //应用密钥
	private String token;     //认证token
	private String platform_id;
	
	
	public String getPlatform_id() {
		return platform_id;
	}
	public void setPlatform_id(String platform_id) {
		this.platform_id = platform_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
