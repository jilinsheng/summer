/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.oa;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.dao.NetTFileDAO;
import com.mingda.dao.NetTTransferDAO;
import com.mingda.dao.SysTEmployeeDAO;
import com.mingda.entity.NetTFile;
import com.mingda.entity.NetTTransfer;

/**
 * MyEclipse Struts Creation date: 12-31-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="transferbrowser"
 *                        path="/page/oa/transferbrowser.jsp"
 */
public class TransferbrowserAction extends Action {
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

		String relid = request.getParameter("relid");
		String reltable = request.getParameter("reltable");
		if (null != relid) {
			NetTTransferDAO transferdao = new NetTTransferDAO();
			NetTTransfer notice = transferdao.findById(new Long(relid));
			SysTEmployeeDAO empdao = new SysTEmployeeDAO();
			empdao.findById(new Long(notice.getOwner().longValue()));
			request.setAttribute("ownername", empdao.findById(
					new Long(notice.getOwner().longValue())).getSysTEmpext()
					.getName());
			request.setAttribute("recename", empdao.findById(
					new Long(notice.getReceiver().longValue())).getSysTEmpext()
					.getName());
			request.setAttribute("notice", notice);
			NetTFileDAO filedao = new NetTFileDAO();
			List<NetTFile> netTFiles = filedao.findByProperty(reltable, relid);
			request.setAttribute("filelist", netTFiles);
		}
		return mapping.findForward("transferbrowser");
	}
}