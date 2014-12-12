package com.mingda.action.info.neweditor;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTMemberDAO;
import com.mingda.entity.SysTEmployee;

public class RemovememberAction extends Action {

	@SuppressWarnings("static-access")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String familyId = request.getParameter("familyId");
		String memberId = request.getParameter("memberId");
		String delreason = request.getParameter("delreason");
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		SqlMapper sqlmapper = new SqlMapper();
		SqlMapClient client = sqlmapper.getSqlMapper();
		try {
			client.startTransaction();
			InfoTMemberDAO memberdao = new InfoTMemberDAO(client);
			//memberdao.deleteOptcheckForMember(memberId, familyId);
			//memberdao.deleteMemberclass(memberId);
			//memberdao.deleteMember(memberId);
			memberdao.removeMember(memberId, employee.getEmployeeId(), delreason,familyId);
			//memberdao.getFamilyCount(new Integer(familyId));
			request.setAttribute("model", "É¾³ý³É¹¦");
			client.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("model", "É¾³ýÊ§°Ü ");
		} finally {
			try {
				client.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mapping.findForward("result");
	}

}
