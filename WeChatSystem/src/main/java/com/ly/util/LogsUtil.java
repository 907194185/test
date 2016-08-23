package com.ly.util;

import org.apache.log4j.Logger;


public class LogsUtil {
	
	public static void writeLog(Class c,String content){
		Logger logger = Logger.getLogger(c);
		logger.info(content);
	}

}
