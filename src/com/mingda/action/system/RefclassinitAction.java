/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

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

import com.mingda.action.info.search.DBUtils;

/**
 * MyEclipse Struts Creation date: 08-04-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class RefclassinitAction extends Action {
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
	 * @throws SQLException
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		String sql = "select st.logicname as tbname,sf.logicname fdname,sc.classtype_id,sc.physql from "
				+ " sys_t_classtype sc, sys_t_field sf, sys_t_table st "
				+ " where sc.field_id = sf.field_id and sf.table_id = st.table_id";
		Connection conn = null;// 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB18030");
		Element root = doc.addElement("tbody");
		try {
			conn = DBUtils.getConnection();// 获取数据库连接
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String tbname = rs.getString("tbname").trim();
				Element parent = (Element) root
						.selectSingleNode("tr[@property='" + tbname + "']");
				if (parent == null) {
					parent = root.addElement("tr").addAttribute("property",
							tbname);
					parent.addElement("td").addAttribute("class", "colfield")
							.addAttribute("colspan", "2").setText(tbname);
				}
				Element tr = parent.addElement("tr");
				Element td = tr.addElement("td");
				// td.addElement("input").addAttribute("type", "hidden")
				// .addAttribute("id", rs.getString("classtype_id"))
				// .addAttribute("value", rs.getString("physql"));
				td.addAttribute("class", "colvalue").setText(
						rs.getString("fdname"));
				Element button = tr.addElement("td").addAttribute("class",
						"colvalue").addElement("button");
				button.addAttribute("onclick",
						"updatedata('" + rs.getString("classtype_id") + "')")
						.addAttribute("class", "btn3_mouseout").addAttribute(
								"onmouseover",
								"this.className='btn3_mouseover'")
						.addAttribute("onmouseout",
								"this.className='btn3_mouseout'").addAttribute(
								"onmousedown",
								"this.className='btn3_mousedown'")
						.addAttribute("onmouseup",
								"this.className='btn3_mouseup'").setText("刷新");

			}
			request.setAttribute("info", root);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return mapping.findForward("refclass");
	}
}