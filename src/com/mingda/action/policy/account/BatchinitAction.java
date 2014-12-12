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

//省级建立批次
public class BatchinitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<BizTOptacc> list = new ArrayList<BizTOptacc>();
		try{
		Accountpage acc = new Accountpage();
		list = acc.getBatchsByOrganization();
		request.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionManager.closeQuietly();
		}
		return mapping.findForward("batchinit");
	}

}
