package com.mingda.common.log4j;
import org.apache.log4j.Logger;
public class Log4jApp {
	/**
	 * 
	 * @param context ������ļ�����Ϣ
	 */
	static Logger log = Logger.getLogger("com.mingda");
	public static void logger(String context) {
		log.error(context);
	}
}
