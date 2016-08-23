package com.ly.util;

/**
 * 常量池
 * @author 刘勇
 *
 */
public class Constant {

	/**
	 * 基础accesstoken获取接口 <br />
	 * get的方式  参数 grant_type=client_credential&appid=APPID&secret=APPSECRET
	 */
	public static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

	/**
	 * jssdk ticket获取接口 <br />
	 * get的方式 参数 access_token=ACCESS_TOKEN&type=jsapi
	 */
	public static final String JSAPITICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	/**
	 * 获取用户基本信息接口 <br />
	 * get的方式 参数 access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 */
	public static final String USERBASEINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

	/**
	 * 授权获取code接口 <br />
	 * get的方式 参数 appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 */
	public static final String OAUTH_URL_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize";

	/**
	 * 授权通过code获取access_token 接口 <br />
	 * get的方式 参数 appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 */
	public static final String OAUTH_URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

	/**
	 * 授权获取用户基本信息接口 <br />
	 * get的方式 参数 access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 */
	public static final String OAUTH_URL_USERINFO = "https://api.weixin.qq.com/sns/userinfo";

	/**
	 * 客服发消息接口 <br />
	 * post的方式 参数类型json
	 */
	public static final String CUSTOM_MESSAGE_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
}
