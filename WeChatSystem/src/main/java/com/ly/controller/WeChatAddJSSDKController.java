package com.ly.controller;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ly.mapper.AccessTokenMapper;
import com.ly.pojo.AccessToken;
import com.ly.pojo.PublicPlatformInfo;
import com.ly.pojo.wechat.AccessTokenForApi;
import com.ly.pojo.wechat.PageJSSDKSetting;
import com.ly.pojo.wechat.TicketForApi;
import com.ly.service.dao.PublicPlatformInfoServiceDao;
import com.ly.util.Constant;
import com.ly.util.HttpRequestUtil;
import com.ly.util.SignUtil;

@Controller
@RequestMapping("addJSSDKAction")
public class WeChatAddJSSDKController {

	@Autowired
	private PublicPlatformInfoServiceDao platformInfoServiceDao;
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@RequestMapping("getSetting")
	@ResponseBody
	public String getSetting(Integer id,String path){
		if (id!=null) {
			PublicPlatformInfo platformInfo = platformInfoServiceDao.getPPIById(id);
			AccessToken accessToken = null;
			if (platformInfo!=null) {
				System.out.println(11111);
				accessToken = accessTokenMapper.findAccessTokenByOne(platformInfo.getPlatform_id());
			}
			Gson gson = new Gson();
			if (accessToken!=null) {
				System.out.println(2222);
				String noncestr = RandomStringUtils.randomAlphabetic(20);
				String jsapi_ticket = accessToken.getTicket();
				String timestamp = String.valueOf(System.currentTimeMillis()/1000);
				String url = path;
				
				String[] params = {"noncestr="+noncestr,"jsapi_ticket="+jsapi_ticket,"timestamp="+timestamp,"url="+url};
				String sign = SignUtil.jsapiSign(params);
				System.out.println("sign="+sign);
				PageJSSDKSetting pageJSSDKSetting = new PageJSSDKSetting();
				pageJSSDKSetting.setAppId(platformInfo.getAppID());
				pageJSSDKSetting.setNonceStr(noncestr);
				pageJSSDKSetting.setTimestamp(timestamp);
				pageJSSDKSetting.setSignature(sign);
				System.out.println("json="+gson.toJson(pageJSSDKSetting));
				return gson.toJson(pageJSSDKSetting);
			}
			//String accessTokenResult = HttpRequestUtil.sendGet(Constant.ACCESSTOKEN_URL, "grant_type=client_credential&appid="+platformInfo.getAppID()+"&secret="+platformInfo.getAppsecret());
			//System.out.println(accessTokenResult);
			//AccessTokenForApi accessToken = null;
			//TicketForApi jsapiTicket = null;
			//if (accessTokenResult.contains("access_token")) {
			//	accessToken = gson.fromJson(accessTokenResult, AccessTokenForApi.class);
			//	System.out.println("accesstoken=="+accessToken.getAccess_token());
			//}
			//if (accessToken!=null) {
			//	String jsapiTicketResult = HttpRequestUtil.sendGet(Constant.JSAPITICKET_URL, "access_token="+accessToken.getAccess_token()+"&type=jsapi");
			//	System.out.println(jsapiTicketResult);
			//	if (jsapiTicketResult.contains("ticket")) {
			//		jsapiTicket = gson.fromJson(jsapiTicketResult, TicketForApi.class);
			//	}
				
			//}
			/*if (jsapiTicket!=null) {
				String noncestr = RandomStringUtils.randomAlphabetic(20);
				String jsapi_ticket = jsapiTicket.getTicket();
				String timestamp = String.valueOf(System.currentTimeMillis()/1000);
				String url = path;
				
				String[] params = {"noncestr="+noncestr,"jsapi_ticket="+jsapi_ticket,"timestamp="+timestamp,"url="+url};
				String sign = SignUtil.jsapiSign(params);
				System.out.println("sign="+sign);
				PageJSSDKSetting pageJSSDKSetting = new PageJSSDKSetting();
				pageJSSDKSetting.setAppId(platformInfo.getAppID());
				pageJSSDKSetting.setNonceStr(noncestr);
				pageJSSDKSetting.setTimestamp(timestamp);
				pageJSSDKSetting.setSignature(sign);
				System.out.println("json="+gson.toJson(pageJSSDKSetting));
				return gson.toJson(pageJSSDKSetting);
			}*/
		}else {
			return null;
		}
		return "";
	}
}
