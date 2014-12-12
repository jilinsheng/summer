/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system.unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.hibernate.Session;

import com.mingda.common.Pager;
import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTUnit;

/**
 * MyEclipse Struts Creation date: 09-03-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class UnitqueryAction extends Action {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession hsession = request.getSession();

		/*
		 * SysTEmployee employee = (SysTEmployee) hsession
		 * .getAttribute("employee");
		 */
		Session session = SessionFactory.getSession();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String curpage = request.getParameter("cur_page");
		String unitname = "";
		String sql = "";
		try {
			conn = session.connection();
			int totalrow = 0;
			Pager page = null;
			if (curpage == null || curpage.equals("")) {
				unitname = request.getParameter("unitname");
				sql = "select * from sys_t_unit u ";
				if (!"".equals("unitname")) {
					unitname = unitname.replace(",", "%");
				}
				sql = sql + " where  u.unitname like '%" + unitname + "%'";
				pstmt = conn.prepareStatement("select count(1) from ( " + sql
						+ " )");
				Log4jApp.logger("������λ:" + sql);
				hsession.setAttribute("sql", sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					totalrow = rs.getInt(1);
				}
				page = new Pager(totalrow, new Long(1).intValue(), "0");
				pstmt = conn
						.prepareStatement("select * from (select mytab.*, rownum row_num from ("
								+ sql
								+ ") mytab) where row_num between "
								+ page.getBeiginrow()
								+ " and "
								+ page.getEndrow());
				rs = pstmt.executeQuery();
			} else {
				sql = (String) hsession.getAttribute("sql");
				pstmt = conn.prepareStatement("select count(1) from ( " + sql
						+ " )");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					totalrow = rs.getInt(1);
				}
				page = new Pager(totalrow, new Long(curpage).intValue(), "0");
				pstmt = conn
						.prepareStatement("select * from (select mytab.*, rownum row_num from ("
								+ sql
								+ ") mytab) where row_num between "
								+ page.getBeiginrow()
								+ " and "
								+ page.getEndrow());
				rs = pstmt.executeQuery();
			}
			page.setUrl("unitquery.do");
			ArrayList<SysTUnit> arr = new ArrayList<SysTUnit>();
			// unit_id, unitname, address, runstate, industry, unittype,
			// unitflag, detail
			while (rs.next()) {
				SysTUnit ssysTUnit = new SysTUnit();
				ssysTUnit.setAddress(rs.getString("address"));
				ssysTUnit.setDetail(rs.getString("detail"));
				ssysTUnit.setIndustry(rs.getLong("industry"));
				ssysTUnit.setRunstate(rs.getLong("runstate"));
				ssysTUnit.setUnitflag(rs.getString("unitflag"));
				ssysTUnit.setUnitId(rs.getLong("unit_id"));
				ssysTUnit.setUnitname(rs.getString("unitname"));
				ssysTUnit.setUnittype(rs.getLong("unittype"));
				arr.add(ssysTUnit);
			}
			if (arr.size() > 0) {
				request.setAttribute("arr", arr);
				request.setAttribute("toolsmenu", page.getToolsMenu());
			} else {
				String unitform = "";
				PageView pv = new PageView();
				Document dictionary = (Document) this.servlet
						.getServletContext().getAttribute("dictionary");

				unitform += "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"table7\">";
				unitform += "<tr><th>��λ����</th><td><input name=\"unitname\" type=\"text\" value=\""
						+ unitname
						+ "\"/><button onclick=\"checkunit()\">У���ظ�</button></td></tr>";
				unitform += "<tr><th>��ҵ����</th><td><select name=\"unittype\" style=\"width:152\">"
						+ pv.getDictionartHandle().getDictsortToOption(
								dictionary, "340", "1") + "</select></td></tr>";
				unitform += "<tr><th>������ҵ</th><td><select name=\"industry\" style=\"width:152\">"
						+ pv.getDictionartHandle().getDictsortToOption(
								dictionary, "341", "1") + "</select></td></tr>";
				unitform += "<tr><th>��Ӫ״̬</th><td><select name=\"runstate\" style=\"width:152\">"
						+ pv.getDictionartHandle().getDictsortToOption(
								dictionary, "342", "1") + "</select></td></tr>";
				unitform += "<tr><th>��λ��ַ</th><td><input name=\"address\" type=\"text\"/></td></tr>";
				unitform += "<tr><th>��ע</th><td><input name=\"detail\" type=\"text\"/></td></tr>";
				unitform += "</table><div id=\"insbnt\" style=\"display:block\"><input  type=\"submit\" value=\"����\"/></div>";
				request.setAttribute("unitform", unitform);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return mapping.findForward("unitquery");
	}
}