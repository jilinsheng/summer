package com.mingda.common.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;

public class CardStatFilter implements Filter {
	static Logger log = Logger.getLogger(CardStatFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		PreparedStatement ps = null;
		try {
			String familyId = request.getParameter("familyId");
			log.error("家庭id：" + familyId +" 需要重新生成家庭卡片");
			if (null == familyId || "".equals(familyId)) {
			} else {
				String sql = "update info_t_family fam set fam.tohtml=1 where fam.family_id='"
						+ familyId + "'";
				Connection con = session.connection();
				ps = con.prepareStatement(sql);
				ps.execute();
				tx.commit();
			}
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.close();
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		log.debug("监视家庭维护状态");
	}

}
