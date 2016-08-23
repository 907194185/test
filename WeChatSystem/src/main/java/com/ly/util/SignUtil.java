package com.ly.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

public class SignUtil {

	public static String sign(String[] params){
		Arrays.sort(params);
		StringBuilder sb = new StringBuilder();
		for(String str : params){
			sb.append(str);
		}
		String sign = DigestUtils.sha1Hex(sb.toString());
		return sign;
	}
	
	public static String jsapiSign(String[] params){
		Arrays.sort(params);
		StringBuilder sb = new StringBuilder();
		for(String str : params){
			sb.append(str);
			sb.append("&");
		}
		String sign = DigestUtils.sha1Hex(sb.substring(0, sb.lastIndexOf("&")).toString());
		return sign;
	}
}
