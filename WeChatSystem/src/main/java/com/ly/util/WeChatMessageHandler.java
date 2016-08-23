package com.ly.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ly.mapper.AccessTokenMapper;
import com.ly.pojo.AccessToken;
import com.ly.pojo.wechat.WeChatUserInfo;
import com.ly.service.dao.WeChatUserServiceDao;

@Component
public class WeChatMessageHandler {

	@Autowired
	private AccessTokenMapper accessTokenMapper;

	@Autowired 
	private WeChatUserServiceDao weChatUserServiceDao;
	
	/**
	 * 响应消息
	 * @param response
	 * @param content 响应内容
	 */
	public void print(HttpServletResponse response,String content){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(content);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (out!=null) {
				out.close();
			}
		}
	}

	/**
	 * 读取微信推送的内容
	 * @param request
	 * @return
	 */
	public String readStreamParameter(HttpServletRequest request){
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader=null;
		ServletInputStream in = null;
		try{
			in = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line=null;
			while((line = reader.readLine())!=null){
				buffer.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null!=reader){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	public void messageHandle(HttpServletRequest request,HttpServletResponse response){
		String message = readStreamParameter(request);
		LogsUtil.writeLog(WeChatMessageHandler.class, message);
		Map<String, String> map = WeChatXmlParse.xmlToMap(message);
		String msgType = map.get("MsgType");
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put("ToUserName", map.get("FromUserName"));
		respMap.put("FromUserName", map.get("ToUserName"));
		String responseMsg = "success";
		//接受普通消息
		if (msgType.equalsIgnoreCase("text")) { //普通文本消息
			respMap.put("MsgType", "text");
			respMap.put("Content", "你好！！！");
			responseMsg = CommonMessageUtil.createXmlMsg(msgType, respMap);
		}else if (msgType.equalsIgnoreCase("image")) { //图片消息

		}else if (msgType.equalsIgnoreCase("voice")) { //语言消息

		}else if (msgType.equalsIgnoreCase("video")) { //视频消息

		}else if (msgType.equalsIgnoreCase("shortvideo")) { //小视频消息

		}else if (msgType.equalsIgnoreCase("location")) { //地理位置消息

		}else if (msgType.equalsIgnoreCase("link")) { //链接消息

		}

		//接受事件推送
		String event = ""; //事件类型
		String eventKey = "";  //事件key值
		if (msgType.equalsIgnoreCase("event")) { 
			event = map.get("Event");  //事件类型
			if (event.equalsIgnoreCase("subscribe")) { //关注时间
				//获取用户基本信息
				Gson gson = new Gson();
				AccessToken accessToken = accessTokenMapper.findAccessTokenByOne(map.get("ToUserName"));
				String userinfo_result = HttpRequestUtil.sendGet(Constant.USERBASEINFO_URL,"access_token="+accessToken.getAccess_token()+"&openid="+map.get("FromUserName")+"&lang=zh_CN");
				System.out.println("关注获取用户基本信息="+userinfo_result);
				WeChatUserInfo weChatUserInfo = gson.fromJson(userinfo_result, WeChatUserInfo.class);
				if (weChatUserInfo!=null) {
					System.out.println(weChatUserInfo);
					if (weChatUserServiceDao.findUserBuOpenid(weChatUserInfo.getOpenid())!=null) {
						weChatUserServiceDao.updateUser(weChatUserInfo);
					}else {
						weChatUserServiceDao.addUser(weChatUserInfo);
					}
				}
				msgType = "text";
				respMap.put("MsgType", "text");
				respMap.put("Content", "欢迎关注我们，你好！！！\n<a href='http://www.baidu.com'>百度一下</a>");
				responseMsg = CommonMessageUtil.createXmlMsg(msgType, respMap);
				
				//客服发消息
				HttpRequestUtil.sendPost(Constant.CUSTOM_MESSAGE_SEND+accessToken.getAccess_token(), "{\"touser\":\""+map.get("FromUserName")+"\",\"msgtype\":\"text\",\"text\":{\"content\":\"<a href='http://www.baidu.com'>Hello World</a>\"}}");
			}else if (msgType.equalsIgnoreCase("unsubscribe")) { //取消关注事件
				
			}else if (msgType.equalsIgnoreCase("LOCATION")) { //用户同意公众号上报地理位置

			}else if (msgType.equalsIgnoreCase("CLICK")) { //自定义菜单点击事件
				eventKey = map.get("EventKey");
				
			}
		}
		
		//回复消息
		print(response, responseMsg);
	}


}
