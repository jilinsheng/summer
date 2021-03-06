/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.oa;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.SessionFactory;
import com.mingda.dao.NetTNoticeDAO;
import com.mingda.entity.NetTNotice;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 12-27-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class NoticedetailAction extends Action {
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
		Session session = SessionFactory.getSession();

		HttpSession hsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) hsession
				.getAttribute("employee");
		// 读取通知和公告
		String notciehtml = "";
		NetTNoticeDAO noticedao = new NetTNoticeDAO();
		List<NetTNotice> notices = noticedao.findNoticeClient(employee
				.getSysTOrganization().getOrganizationId());
		if (null != notices && notices.size() > 0) {
			for (int i = 0; i < notices.size(); i++) {
				String a = "";
				String type = "";
				NetTNotice notice = notices.get(i);
				if ("1".equals(notice.getNoticetype().toString())) {
					a = "noticebrowser.do?reltable=NET_T_NOTICE&relid="
							+ notice.getNoticeId() + "";
					type = "通知";
				} else {
					a = "announcmentbrowser.do?reltable=NET_T_NOTICE&relid="
							+ notice.getNoticeId() + "";
					type = "公告";
				}
				notciehtml = notciehtml
				+ "<tr height=\"19\"><td height=\"25\" class=\"content\"><a href=\""+a+"\" target=\"_blank\"><span style=\"width:100px\">"
				+type+"&nbsp;&nbsp;"+ notice.getAuthor() + ":</span><span style=\"width:300px;text-align:left\">"
				+ notice.getTitle() + "</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"width:150px;text-align:right\"> &nbsp;&nbsp;&nbsp;&nbsp;"
				+ notice.getIssuetime() + "</span></a></td></tr>";
				//style="text-align:right"
			}
			request.setAttribute("notciehtml", notciehtml);
		}
		// 读取通知和公告
		session.close();
		return mapping.findForward("noticedetail");
	}
}