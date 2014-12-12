package com.mingda.common.log4j;
import org.apache.log4j.Logger;
public class Log4jApp {
	/**
	 * 
	 * @param context 输出到文件的信息
	 */
	static Logger log = Logger.getLogger("com.mingda");
	public static void logger(String context) {
		log.error(context);
	}
}
