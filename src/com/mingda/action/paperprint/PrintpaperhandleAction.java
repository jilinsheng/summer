/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.paperprint;

import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.action.organ.OrgansaveAction;
import com.mingda.common.SessionFactory;
import com.mingda.common.paperprint.PaperPrint;
import com.mingda.entity.ImplTPaperrecord;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 01-16-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class PrintpaperhandleAction extends Action {
	static Logger log = Logger.getLogger(PrintpaperhandleAction.class);
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
		log.debug("开始打印证件");
		String htmlstr = "";
		String ptid = request.getParameter("ptid");
		String fid = request.getParameter("fid");
		String reason = request.getParameter("reason");
		
		HttpSession httpsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) httpsession
				.getAttribute("employee");
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		ActionForward forward  = null;
		try {
			PaperPrint paperprint = new PaperPrint();
			boolean a= paperprint.isPrintHandle(ptid, fid);
			log.debug(a);
			if (a) {
				ImplTPaperrecord implTPaperrecord = new ImplTPaperrecord();
				implTPaperrecord.setReason(reason);
				implTPaperrecord.setOperator(new BigDecimal(employee
						.getEmployeeId()));
				implTPaperrecord = paperprint.savePrintHandle(ptid, fid,
						implTPaperrecord);
				htmlstr = "";
				// 证件类型id
				Long papertype = implTPaperrecord.getImplTPapertype()
						.getPapertypeId();
				// 证件记录id
				Long paperrecordid = implTPaperrecord.getPaperrecordId();
				// 证件名称
				String papername = implTPaperrecord.getImplTPapertype()
						.getPaperxml();
				
				tx.commit();
				forward =new ActionForward("/page/sysreport/ReportEngineView.jsp?Sys_ReportName="
						+ papername
						+ "&Sys_paperrecordid="
						+ paperrecordid);
				log.debug("/page/sysreport/ReportEngineView.jsp?Sys_ReportName="
						+ papername
						+ "&Sys_paperrecordid="
						+ paperrecordid);
				return  forward;
			} else {
				htmlstr = "<link rel=\"stylesheet\" href=\"../css/style.css\" type=\"text/css\"></link><p></p><p align=\"center\">此证件已经打印过</p><p align=\"center\">"
						+ "<button class=\"bnt\" onclick=\"javascript:window.close();window.opener.close()\">关闭</button>"
						+ "</p>";
				response.setCharacterEncoding("GB18030");
				PrintWriter out = response.getWriter();
				out.println(htmlstr);
				out.flush();
				out.close();
			}
		} catch (Exception ex) {
			tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
}