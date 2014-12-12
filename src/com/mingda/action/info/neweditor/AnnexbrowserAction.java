/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.neweditor;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTAnnexDAO;
import com.mingda.common.ibatis.data.InfoTAnnex;

/**
 * MyEclipse Struts Creation date: 05-31-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class AnnexbrowserAction extends Action {
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
		SqlMapClient client = SqlMapper.getSqlMapper();
		String annexId = request.getParameter("annexId");
		try {
			client.startTransaction();
			InfoTAnnexDAO annexdao = new InfoTAnnexDAO(client);
			InfoTAnnex annex = new InfoTAnnex();
			annex.setAnnexId(new Integer(annexId));
			annex = annexdao.selectByPrimary(annex);
			request.setAttribute("annex", annex);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				client.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mapping.findForward("annexbrowser");
	}
}