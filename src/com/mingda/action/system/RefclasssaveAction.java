/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.action.info.search.DBUtils;

/**
 * MyEclipse Struts Creation date: 08-04-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class RefclasssaveAction extends Action {
	static Logger log = Logger.getLogger(RefclasssaveAction.class);
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
		String objid = request.getParameter("objid");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/html;charset=GB18030");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String sql = "select st.logicname || '>>' || sf.logicname as nav, "
				+ "sc.physql,st.physicalname as tablename,sf.physicalname as fieldname,st.table_id,st.path "
				+ "from sys_t_classtype sc, sys_t_field sf, sys_t_table st "
				+ " where st.table_id = sf.table_id and sc.field_id = sf.field_id "
				+ "and sc.classtype_id = " + objid;
		// log.debug(sql);
		Connection conn = null;// 声明Connection对象
		PreparedStatement ps = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection();// 获取数据库连接
			conn.setAutoCommit(false);
			String nav = null;
			String tableid = null;
			String tablename = null;
			String fieldname = null;
			String subsql = null;
			String fkfield = null;
			String tablepath = "";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				nav = rs.getString("nav");
				tableid = rs.getString("table_id");
				tablename = rs.getString("tablename");
				fieldname = rs.getString("fieldname");
				subsql = rs.getString("physql");
				tablepath = rs.getString("path");
			}
			rs.close();
			sql = "select sf.physicalname from sys_t_field sf where sf.table_id ="
					+ tableid + " and sf.isforeign = 1";
			rs = ps.executeQuery(sql);
			if (rs.next()) {
				fkfield = rs.getString("physicalname");
			}
			rs.close();
			sql = " update " + tablename + " osort set " + fieldname
					+ " = 281 where not exists (select * from (" + subsql
					+ ") ssort where ssort." + fkfield + " = osort." + fkfield
					+ ")";
			//log.debug(sql);
			ps.execute(sql);
			sql = " update " + tablename + " osort set " + fieldname
					+ " = 282 where exists (select * from (" + subsql
					+ ") ssort where ssort." + fkfield + " = osort." + fkfield
					+ ") and " + fieldname + "<>'280'";
			log.debug(sql);
			ps.execute(sql);
			conn.commit();
			out.write("操作成功!");
			//log.debug("操作成功!");
		} catch (SQLException e) {
			conn.rollback();
			out.write("操作失败!");
			e.printStackTrace();
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
		out.flush();
		out.close();
		return null;
	}
}