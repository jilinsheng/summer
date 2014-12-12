package com.mingda.action.info.neweditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemovememberinitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String familyId = request.getParameter("familyId");
		String memberId = request.getParameter("memberId");
		request.setAttribute("familyId", familyId);
		request.setAttribute("memberId", memberId);
		return mapping.findForward("removemember");
	}
}
