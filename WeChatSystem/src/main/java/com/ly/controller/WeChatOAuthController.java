package com.ly.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ly.pojo.PublicPlatformInfo;
import com.ly.service.dao.PublicPlatformInfoServiceDao;
import com.ly.util.Constant;

/**
 * 微信授权中转程序 
 * @author 刘勇
 *
 */
@Controller
@RequestMapping("oAuth")
public class WeChatOAuthController {
	
	@Autowired
	private PublicPlatformInfoServiceDao platformInfoServiceDao;
	
	@RequestMapping("forward")
	public void forward(Integer id,HttpServletResponse response) throws IOException{
		PublicPlatformInfo platformInfo;
		StringBuilder url = new StringBuilder();
		if (id!=null) {
			platformInfo = platformInfoServiceDao.getPPIById(id);
			if (platformInfo!=null) {
				url.append(Constant.OAUTH_URL_CODE);
				url.append("?appid="+platformInfo.getAppID());
				url.append("&redirect_uri="+URLEncoder.encode("http://liuyong.ngrok.natapp.cn/WeChatSystem/oAuthRedirect/getInfo?appId="+platformInfo.getAppID()+"&secret="+platformInfo.getAppsecret(), "utf-8"));
				url.append("&response_type=code");
				url.append("&scope=snsapi_userinfo");
				url.append("&state=STATE#wechat_redirect");
			}else {
				return;
			}
		}else {
			return;
		}
		response.sendRedirect(url.toString());
		
	}

}
