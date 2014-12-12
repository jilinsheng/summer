/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system.dictionary;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.dao.SysTDictsortDAO;
import com.mingda.entity.SysTDictsort;

/**
 * MyEclipse Struts Creation date: 03-04-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class DictsortsaveAction extends Action {
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
		Transaction tx = session.beginTransaction();

		String dictid = request.getParameter("dictid");
		String dsname = request.getParameter("dsname");

		try {
			SysTDictsortDAO dao = new SysTDictsortDAO();
			SysTDictsort sysTDictsort = new SysTDictsort();
			if (null == dictid || "".equals(dictid)) {
				sysTDictsort.setStatus("1");
				sysTDictsort.setIsread("0");
			} else {
				sysTDictsort = dao.findById(new Long(dictid));
			}
			sysTDictsort.setName(dsname);
			dao.save(sysTDictsort);
			tx.commit();
			request.setAttribute("dict", sysTDictsort);
			DictionaryHandle dictionaryHandle = new DictionaryHandle();
			this.servlet.getServletContext().setAttribute("dictionary",
					dictionaryHandle.createDictionaryXML());
			PrintWriter out = response.getWriter();
			out.println("<script>window.parent.frames[\"leftFrame\"].location.reload();</script>");
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
		}
		return null;
	}
}