package com.ly.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class WeChatXmlParse {
	
	public static Map<String, String> xmlToMap(String str){
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(str);
			Element root = document.getRootElement();
			System.out.println(root.getName());
			Iterator<Element> iterator = root.elementIterator();
			Element subElement;
			while(iterator.hasNext()){
				subElement = iterator.next();
				System.out.println(subElement.getName()+"="+subElement.getText());
				map.put(subElement.getName(), subElement.getText());
			}
		} catch (DocumentException e) {
			LogsUtil.writeLog(WeChatXmlParse.class, "解析xml出错了="+e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

}
