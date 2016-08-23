package com.ly.mapper;

import com.ly.pojo.wechat.WeChatUserInfo;

public interface WeChatUserMapper {
	
	public WeChatUserInfo findUserByOpenid(String openid);
	
	public void insertUser(WeChatUserInfo weChatUserInfo);
	
	public void updateUser(WeChatUserInfo weChatUserInfo);

}
