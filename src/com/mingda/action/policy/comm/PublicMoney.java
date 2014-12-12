package com.mingda.action.policy.comm;

import java.text.DecimalFormat;

public class PublicMoney {
	private static final int DEF_DIV_SCALE = 3;

	private PublicMoney() {
	}
	
	public static String parseMoney(String str1) {
		//
		Double dstr = Double.parseDouble(str1);			//变换成数值型
		DecimalFormat f = new DecimalFormat("0.00");	//小数只有两位   
		str1 = f.format(dstr);							//变换后的数据 
		//
		int len = str1.length();
		int k = 0;
		int m = 0;
		int dot = 0;
		int start = 0;
		String str_dot = "";
		String str_start = "";
		//
		for (k = 1; k < len; k++) {
			if (str1.substring(k, k + 1).equals(".")) {
				dot = k + 1;
				break;
			}
		}
		start = (dot - 1) % DEF_DIV_SCALE;
		if (start == 0) {
			str_start = "";
		} else {
			str_start = str1.substring(0, start) + ",";
		}
		m = 0;
		for (k = start; k < dot; k += DEF_DIV_SCALE) {
			str_start += str1.substring(k, k + DEF_DIV_SCALE) + ",";
			m++;
		}
		if (start == 0) {
			str_start = str_start.substring(0, dot + m - 3);
		} else {
			str_start = str_start.substring(0, dot + m - 2);
		}
		str_dot = str1.substring(dot, len);
		str1 = str_start + "." + str_dot;

		return str1;
	}
}
