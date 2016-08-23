package com.ly.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ly.mapper.AccessTokenMapper;
import com.ly.mapper.PublicPlatformInfoMapper;
import com.ly.pojo.AccessToken;
import com.ly.pojo.PublicPlatformInfo;
import com.ly.pojo.wechat.AccessTokenForApi;
import com.ly.pojo.wechat.TicketForApi;
import com.ly.util.Constant;
import com.ly.util.DateFormatUtil;
import com.ly.util.HttpRequestUtil;

@Component
@Lazy(false)
public class AccessTokenTask {
	
	@Autowired
	AccessTokenMapper accessTokenMapper;
	
	@Autowired
	PublicPlatformInfoMapper platformInfoMapper;

	@Scheduled(cron="0 0/5 * * * *")
	public void run(){
		System.out.println("进入定时="+DateFormatUtil.format.format(new Date()));
		List<PublicPlatformInfo> list = platformInfoMapper.findAllPPI();
		Gson gson = new Gson();
		AccessTokenForApi accessTokenForApi = null;
		TicketForApi ticketForApi = null;
		AccessToken accessToken = null;
		String accessTokenResult = "";
		String ticketResult = "";
		for(PublicPlatformInfo platformInfo : list){
			accessTokenResult = HttpRequestUtil.sendGet(Constant.ACCESSTOKEN_URL, "grant_type=client_credential&appid="+platformInfo.getAppID()+"&secret="+platformInfo.getAppsecret());
			System.out.println("定时1="+accessTokenResult);
			if (accessTokenResult.contains("access_token")) {
				accessTokenForApi = gson.fromJson(accessTokenResult, AccessTokenForApi.class);
				ticketResult = HttpRequestUtil.sendGet(Constant.JSAPITICKET_URL, "access_token="+accessTokenForApi.getAccess_token()+"&type=jsapi");
				System.out.println("定时2="+ticketResult);
				if (ticketResult.contains("ticket")) {
					ticketForApi = gson.fromJson(ticketResult, TicketForApi.class);
					accessToken = new AccessToken();
					accessToken.setAccess_token(accessTokenForApi.getAccess_token());
					accessToken.setPlatform_id(platformInfo.getPlatform_id());
					accessToken.setCreatetime(DateFormatUtil.format.format(new Date()));
					accessToken.setTicket(ticketForApi.getTicket());
					
					if (accessTokenMapper.findAccessTokenByOne(platformInfo.getPlatform_id())!=null) {
						accessTokenMapper.updateAccessToken(accessToken);
					}else {
						accessTokenMapper.insertAccessToken(accessToken);
					}
				}
				
				
			}
		}
		
	}
}
