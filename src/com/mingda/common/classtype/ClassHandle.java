package com.mingda.common.classtype;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.Session;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;

public class ClassHandle extends BaseHibernateDAO {
	public Document getClassType(Document doc, String nodeId, String code)
			throws Exception {
		Session session = getSession();
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sql = "select * from sys_t_classtype t, sys_t_field f, "
				+ "sys_t_table tab "
				+ "where f.status ='1' and  f.field_id = t.field_id"
				+ " and tab.table_id = f.table_id " + " and tab.code = '"
				+ code + "'";
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String flag = ConstantDefine.CLASSSTATUS_NO;
				String sql1 = rs.getString("PHYSQL");
				String column = rs.getString("PHYSICALNAME");
				if (sql1 == null || sql1.equals("") || sql1.equals("0")) {

				} else {
					if (code.equals("FAMILYCLASS")) {

						sql1 += " and INFO_T_FAMILY.FAMILY_ID= " + nodeId;
					} else {
						sql1 += " and INFO_T_MEMBER.MEMBER_ID= " + nodeId;
					}
					Log4jApp.logger("分类" + sql1);
					ps1 = conn.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						flag = ConstantDefine.CLASSSTATUS_COMMIT;
					} else {
						flag = ConstantDefine.CLASSSTATUS_NO;
					}
					ps1.close();
					rs1.close();
				}
				Element current = (Element) doc.selectSingleNode("//" + code
						+ "/property[@column='" + column + "']");
				if (current != null) {
					if (flag.equals(ConstantDefine.CLASSSTATUS_COMMIT)) {
						if (current.getText().equals(
								ConstantDefine.CLASSSTATUS_YES)) {

						} else {
							current.setText(flag);
						}
					} else {
						current.setText(flag);
					}
				}
			}
			// 外键复制
			Element current = (Element) doc.selectSingleNode("//" + code
					+ "/property[@isforeign='true']");
			current.setText(nodeId);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != rs1) {
				rs1.close();
			}
			if (null != ps) {

				ps.close();
			}
			if (null != ps1) {

				ps1.close();
			}
			if (null != conn) {

				conn.close();
			}
		}
		return doc;

	}

	public String ClassCommit(String nodeName, String nodeId,
			String propertyName, String val) throws Exception {
		String temp = "";
		if (nodeName.equals("FAMILYCLASS")) {
			temp = "FAMILY_ID";
		} else {
			temp = "MEMBER_ID";
		}
		Log4jApp.logger("确认分类：" + val);
		if (val.equals("0")) {
			val = ConstantDefine.CLASSSTATUS_YES;
		} else {
			val = ConstantDefine.CLASSSTATUS_COMMIT;

		}
		Session session = getSession();
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update info_t_" + nodeName + " set " + propertyName + "="
				+ val + " where " + temp + "=" + nodeId;
		Log4jApp.logger("确认分类：" + sql);
		try {
			conn = session.connection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			//ps.execute();
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			ps.close();
			conn.close();
		}
		return "1";
	}
}
