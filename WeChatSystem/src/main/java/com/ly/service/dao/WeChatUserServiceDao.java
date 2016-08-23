package com.ly.service.dao;

import com.ly.pojo.wechat.WeChatUserInfo;

public interface WeChatUserServiceDao {
	
	public WeChatUserInfo findUserBuOpenid(String openid);
	
	public void addUser(WeChatUserInfo weChatUserInfo);
	
	public void updateUser(WeChatUserInfo weChatUserInfo);
	

}
