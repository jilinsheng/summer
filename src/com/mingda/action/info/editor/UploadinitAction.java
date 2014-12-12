/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.editor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.SessionFactory;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.common.page.PageView;

/**
 * MyEclipse Struts Creation date: 07-03-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class UploadinitAction extends Action {
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
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		String code = request.getParameter("code");
		String pid = request.getParameter("pid");
		String codeId = request.getParameter("codeId");

		Document treedoc = (Document) servlet.getServletContext().getAttribute(
				"tree");
		TreeHandle tree = new TreeHandleImpl(treedoc);

		PageView pv = new PageView();
		String str = "";
		String type = "0";
		Document doc = null;
		if (pid != null) {
			doc = tree.selectSingleEntity(code, null);
			Element element = (Element) doc.selectSingleNode("//" + code
					+ "/property[@isforeign='true']");
			element.setText(pid);
		}
		String url = null;
		if (codeId != null) {
			doc = tree.selectSingleEntity(code, new Long(codeId));
			Element element = (Element) doc.selectSingleNode("//" + code
					+ "/property[@column='TYPE']");
			// 路径
			url = doc
					.selectSingleNode("//" + code + "/property[@column='URL']")
					.getText();
			type = element.getText();
			if (type.equals("1")) {
				// 组合图片路径
				url = "http://" + request.getServerName() + ":"
						+ request.getServerPort() + "/upload/" + url;

			} else if (type.equals("2")) {
				// 组合视频路径
			} else {
			}
		}
		doc = pv.writeFormbyXML(doc, dictionary, 1);
		str = doc.getRootElement().asXML();
		request.setAttribute("url", url);
		request.setAttribute("str", str);
		request.setAttribute("type", type);
		SessionFactory.closeSession();
		return mapping.findForward("upload");
	}
}