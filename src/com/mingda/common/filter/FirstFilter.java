package com.mingda.common.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mingda.entity.SysTEmployee;

/**
 * @author �Բ��
 * 
 * �ж��û��Ƿ��н���ϵͳ��Ȩ��
 */
public class FirstFilter implements Filter {

	public void init(FilterConfig cong) {
		// do nothing
	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) {

		try {
			HttpServletRequest requst = (HttpServletRequest) srequest;
			HttpServletResponse response = (HttpServletResponse) sresponse;
			HttpSession session = requst.getSession();
			// ��session��ȡ�����Ա����,���Ϊ��˵��û�е�¼,����ת����¼ҳ��.弟弟
			SysTEmployee operator = (SysTEmployee) session.getAttribute("employee");
			if (operator == null) {
				response.sendRedirect("/db/logout.jsp");
			} else {
				chain.doFilter(srequest, sresponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		// do nothing
	}

}
