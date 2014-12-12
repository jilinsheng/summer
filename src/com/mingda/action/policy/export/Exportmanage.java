package com.mingda.action.policy.export;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class Exportmanage {
	/**
	 * 设置导出表格列名和SQL混合用;号隔开
	 * @param session
	 * @param xmls
	 * @return
	 */
	public StringBuffer SetSessionForExport(HttpSession session,String xmls) {
		StringBuffer shtml= new StringBuffer("");
		String[] c_name = null;
		c_name = xmls.split(";");
		String cols = c_name[0];
		String sql = c_name[1];
		//
		shtml = SetSessionForExport(session,cols,sql);
		
		return shtml;
	}
	/**
	 * 设置导出表格列名和SQL
	 * @param session
	 * @param xmlth
	 * @param sql
	 * @return
	 */
	public StringBuffer SetSessionForExport(HttpSession session,String xmlth,String sql) {
		StringBuffer shtml= new StringBuffer("");
		//		
		HashMap hashmapxml = new HashMap();
		String[] col_name = null;
		int col_count = 0;
		col_name = xmlth.split(",");
		col_count = col_name.length;
		for (int i = 0; i < col_count; i++) {              
         	//查询字段列
			String sname = col_name[i];
			hashmapxml.put(i, sname);
        }
		session.setAttribute("cols", hashmapxml);
		session.setAttribute("sql", sql);
		if(null == hashmapxml){
			shtml.append("0");
		}else{
			shtml.append("1");
		}
		return shtml;
	}
}
