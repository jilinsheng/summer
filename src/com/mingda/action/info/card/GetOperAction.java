package com.mingda.action.info.card;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;

/**
 * MyEclipse Struts Creation date: 07-24-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class GetOperAction extends Action {
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
	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String nodeId = request.getParameter("nodeId");
		Document oper = DocumentHelper.createDocument();
		Session session = SessionFactory.getSession();
		Connection con = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select t.family_id,t.flag, po.policy_id, t.money, po.name "
				+ " from biz_t_familystatus t, doper_t_policy po "
				+ " where po.policy_id = t.policy_id "
				+ " and t.family_id = '"
				+ nodeId + "'";
		Element root = oper.addElement("table").addAttribute("class",
				"tablelist");
		Element tr = root.addElement("tr");
		tr.addElement("th").setText("业务名称");
		tr.addElement("th").setText("业务状态");
		tr.addElement("th").setText("发放记录");
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				tr = root.addElement("tr");
				tr.addElement("td").setText(rs.getString("name"));
				tr.addElement("td").setText(rs.getString("money"));
				tr.addElement("td").addElement("a").addAttribute(
						"href",
						"a.do?familyId=" + rs.getString("family_id")
								+ "&policyId=" + rs.getString("policy_id"))
						.addAttribute("target", "_blank").setText("详细");
			}
			if(root.elements().isEmpty()){
				root.addElement("tr").addElement("td").setText("没有救助记录");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					rs.close();
				}
				if (con != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			PrintWriter out =response.getWriter();
			out.write(oper.getRootElement().asXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}