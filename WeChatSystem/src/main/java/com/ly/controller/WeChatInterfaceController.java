package com.ly.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.mapper.AccessTokenMapper;
import com.ly.pojo.PublicPlatformInfo;
import com.ly.service.dao.PublicPlatformInfoServiceDao;
import com.ly.util.SignUtil;
import com.ly.util.WeChatMessageHandler;

@Controller
@RequestMapping("weChatAction")
public class WeChatInterfaceController {

	@Autowired
	private PublicPlatformInfoServiceDao platformInfoServiceDao;

	@Autowired
	private WeChatMessageHandler weChatMessageHandler;
	//private static String TOKEN = "liuyong";
	
	/**
	 * 接入微信公众平台
	 * @author 刘勇
	 * @param response 响应
	 * @param signature 微信传过的签名
	 * @param timestamp 微信传过的时间戳
	 * @param nonce 微信传过的随机字符串
	 * @param echostr 微信传过来的随机数
	 */
	@RequestMapping("valid")
	@ResponseBody
	public void valid(HttpServletResponse response,HttpServletRequest request,Integer id,String signature,String timestamp,String nonce,String echostr){
		System.out.println(signature+"--"+timestamp+"--"+nonce+"--"+echostr);
		response.setCharacterEncoding("utf-8");

		if(echostr==null || echostr.isEmpty()){    //认证成功后用户与微信公众号交互
			System.out.println("普通消息进入");
			weChatMessageHandler.messageHandle(request, response);
		} else{  //首次认证接入公众号
			System.out.println("首次认证");
			if (id==null) {
				weChatMessageHandler.print(response,"error");
				return;
			}
			//查询数据库获取对应用户token
			PublicPlatformInfo platformInfo = platformInfoServiceDao.getPPIById(id);
			if (platformInfo==null) {
				weChatMessageHandler.print(response,"error");
				return;
			}
			String[] params = {platformInfo.getToken(),timestamp,nonce};
			System.out.println("---"+params.toString());
			if(SignUtil.sign(params).equals(signature)){  //认证成功
				weChatMessageHandler.print(response,echostr);
			}else{ //认证失败
				weChatMessageHandler.print(response,"error");
			}
		}
	}

	


}
