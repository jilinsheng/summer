package com.mingda.common.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	public void init(FilterConfig cong) {
	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) {
		try {
			srequest.setCharacterEncoding("GB18030");
			sresponse.setCharacterEncoding("GB18030");
			chain.doFilter(srequest, sresponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
	}

}
