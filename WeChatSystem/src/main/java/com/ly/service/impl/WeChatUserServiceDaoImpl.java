package com.ly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.mapper.WeChatUserMapper;
import com.ly.pojo.wechat.WeChatUserInfo;
import com.ly.service.dao.WeChatUserServiceDao;

@Service
public class WeChatUserServiceDaoImpl implements WeChatUserServiceDao{

	@Autowired
	private WeChatUserMapper weChatUserMapper;
	
	@Override
	public WeChatUserInfo findUserBuOpenid(String openid) {
		WeChatUserInfo weChatUserInfo = weChatUserMapper.findUserByOpenid(openid);
		return weChatUserInfo;
	}

	@Override
	public void addUser(WeChatUserInfo weChatUserInfo) {
		weChatUserMapper.insertUser(weChatUserInfo);
	}

	@Override
	public void updateUser(WeChatUserInfo weChatUserInfo) {
		weChatUserMapper.updateUser(weChatUserInfo);
	}

}
