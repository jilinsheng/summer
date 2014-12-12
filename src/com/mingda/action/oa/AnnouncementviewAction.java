/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.oa;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.dao.NetTNoticeDAO;
import com.mingda.entity.NetTNotice;
import com.mingda.entity.SysTEmployee;
/**
 * MyEclipse Struts Creation date: 12-26-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class AnnouncementviewAction extends Action {
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
	static Logger logger = Logger.getLogger(NoticeviewAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Session session = SessionFactory.getSession();
		HttpSession hsession = request.getSession();
		Transaction tx = session.beginTransaction();
		String html = "";
		try {
			boolean flag = true;
			FileUpload fu = new FileUpload(41943040, 4096, request);
			String title = null;
			String context = null;
			// List<String> orgids = new ArrayList<String>();
			DiskFileUpload ff = new DiskFileUpload();
			ff.setSizeMax(41943040);
			ff.setSizeThreshold(4096);
			ff.setHeaderEncoding("gb18030");
			// request对象中表单元素
			List<FileItem> list = ff.parseRequest(request);
			// request对象中文件域
			List<FileItem> files = new ArrayList<FileItem>();
			Iterator<FileItem> it = list.iterator();
			while (it.hasNext()) {
				FileItem fi = (FileItem) it.next();
				logger.debug("表单字段名:" + fi.getFieldName() +  "  文件域:" + fi.getName());
				if ("title".equals(fi.getFieldName().trim())) {
					title = fi.getString("gb18030");
				}
				if ("context".equals(fi.getFieldName().trim())) {
					context = fi.getString("gb18030");
				}
				if ("file".equals(fi.getFieldName().trim())) {
					if (-1 == fi.getName().indexOf("-")) {
					} else {
						flag = false;
					}
					files.add(fi);
				}
			}

			if (flag) {
				SysTEmployee employee = (SysTEmployee) hsession
						.getAttribute("employee");
				NetTNoticeDAO ndao = new NetTNoticeDAO();
				NetTNotice notice = new NetTNotice();
				notice.setAuditor(new BigDecimal(employee.getEmployeeId()));
				notice.setAuthor(employee.getSysTEmpext().getName());
				notice.setContent(context);
				notice.setIssuetime(new Date());
				notice.setNoticetype(new BigDecimal("2"));
				notice.setOrgan(employee.getSysTOrganization()
						.getOrganizationId());
				notice.setTitle(title);
				notice.setState("1");
				String tableid=ndao.save(notice);
				fu.uploadFile("NET_T_NOTICE", tableid, files);
				tx.commit();
				html = "保存成功";
			} else {
				html = "上传的文件，文件名不可以包含‘-’";
			}
		} catch (FileUploadException e) {
			tx.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		response.setCharacterEncoding("gb18030");
		try {
			PrintWriter out = response.getWriter();
			out.println("<p align=\"center\">");
			out.println(html);
			out.println("</p>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}