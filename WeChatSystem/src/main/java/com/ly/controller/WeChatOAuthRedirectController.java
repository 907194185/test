package com.ly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ly.pojo.wechat.OAuthAccessToken;
import com.ly.pojo.wechat.WeChatUserInfo;
import com.ly.service.dao.WeChatUserServiceDao;
import com.ly.util.Constant;
import com.ly.util.HttpRequestUtil;

@Controller
@RequestMapping("oAuthRedirect")
public class WeChatOAuthRedirectController {

	@Autowired 
	private WeChatUserServiceDao weChatUserServiceDao;
	
	
	@RequestMapping("getInfo")
	@ResponseBody
	public void getOAuthInfo(String code,String appId,String secret){
		Gson gson = new Gson();
		OAuthAccessToken oAuthAccessToken = null;
		System.out.println("code="+code);
		StringBuilder sb = new StringBuilder();
		sb.append("appid="+appId);
		sb.append("&secret="+secret);
		sb.append("&code="+code); 
		sb.append("&grant_type=authorization_code");
		String oauthResult = HttpRequestUtil.sendGet(Constant.OAUTH_URL_ACCESS_TOKEN, sb.toString());
		System.out.println("授权接口结果："+oauthResult);
		if (oauthResult.contains("access_token")) {
			oAuthAccessToken = gson.fromJson(oauthResult, OAuthAccessToken.class);
			sb = new StringBuilder();
			sb.append("access_token="+oAuthAccessToken.getAccess_token());
			sb.append("&openid="+oAuthAccessToken.getOpenid());
			sb.append("&lang=zh_CN");
			
			String oauthUserInfoResult = HttpRequestUtil.sendGet(Constant.OAUTH_URL_USERINFO, sb.toString());
			System.out.println("用户基本信息="+oauthUserInfoResult);
			WeChatUserInfo weChatUserInfo = gson.fromJson(oauthUserInfoResult, WeChatUserInfo.class);
			if (weChatUserInfo!=null) {
				System.out.println("授权获取用户信息："+weChatUserInfo);
				if (weChatUserServiceDao.findUserBuOpenid(weChatUserInfo.getOpenid())!=null) {
					weChatUserServiceDao.updateUser(weChatUserInfo);
				}else {
					weChatUserServiceDao.addUser(weChatUserInfo);
				}
			}
		}else {
			System.out.println("获取access_token失败");
		}
		
	}
}
