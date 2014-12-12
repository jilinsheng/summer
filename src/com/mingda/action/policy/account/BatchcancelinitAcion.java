package com.mingda.action.policy.account;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.myjdbc.ConnectionManager;
import com.mingda.entity.BizTOptacc;

public class BatchcancelinitAcion extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<BizTOptacc> list = new ArrayList<BizTOptacc>();
		try {
			Accountpage acc = new Accountpage();
			String oid = request.getParameter("oid");
			list = acc.getDoneBatchsByOrganization(oid);
			boolean flag = true;
			for (BizTOptacc e : list) {
				if (e.getAccflag().equals("2")) {
				} else {
					flag = false;
				}
			}
			if (flag) {
				request.setAttribute("list", list);
			} else {
				request.setAttribute("info", "此机构有正在处理中、结算中的业务！不能撤销已经结算业务！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly();
		}
		return mapping.findForward("batchcancel");
	}

}
