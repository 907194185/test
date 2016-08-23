package com.ly.util;

import java.util.Map;

public class CommonMessageUtil {

	public static String createXmlMsg(String msgType,Map<String, String> content){
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(content.get("ToUserName")).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(content.get("FromUserName")).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(System.currentTimeMillis()).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[").append(content.get("MsgType")).append("]]></MsgType>");
		if (msgType.equalsIgnoreCase("text")) {
			sb.append("<Content><![CDATA[").append(content.get("Content")).append("]]></Content>");
		}else if (msgType.equalsIgnoreCase("image")) {
			
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
