/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system.unit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;

import com.mingda.common.page.PageView;
import com.mingda.dao.SysTUnitDAO;
import com.mingda.entity.SysTUnit;

/**
 * MyEclipse Struts Creation date: 09-10-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="unitquery"
 *                        path="/page/system/unit/unitquery.jsp"
 */
public class UnitsaveinitAction extends Action {
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
		String unitid = request.getParameter("unitid");
		SysTUnitDAO unitdao = new SysTUnitDAO();
		SysTUnit unit = unitdao.findById(new Long(unitid));

		String unitform = "";
		PageView pv = new PageView();
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");

		unitform += "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"table7\">";
		unitform += "<tr><th>单位名称</th><td><input name=\"unitname\" type=\"text\" value=\""
				+ unit.getUnitname()
				+ "\"/><button onclick=\"checkunit()\">校验重复</button></td></tr>";
		unitform += "<tr><th>企业类型</th><td><select name=\"unittype\" style=\"width:152\">"
				+ pv.getDictionartHandle().getDictsortToOption(dictionary,
						"340", unit.getUnittype().toString())
				+ "</select></td></tr>";
		unitform += "<tr><th>所属行业</th><td><select name=\"industry\" style=\"width:152\">"
				+ pv.getDictionartHandle().getDictsortToOption(dictionary,
						"341", unit.getIndustry().toString())
				+ "</select></td></tr>";
		unitform += "<tr><th>运营状态</th><td><select name=\"runstate\" style=\"width:152\">"
				+ pv.getDictionartHandle().getDictsortToOption(dictionary,
						"342", unit.getRunstate().toString())
				+ "</select></td></tr>";
		unitform += "<tr><th>单位地址</th><td><input name=\"address\" type=\"text\"/></td></tr>";
		if (null == unit.getDetail() || "".equals(unit.getDetail())
				|| "null".equals(unit.getDetail())) {
			unit.setDetail("");
		}
		unitform += "<tr><th>备注</th><td><input name=\"detail\" type=\"text\" value=\""
				+ unit.getDetail() + "\"/></td></tr>";
		unitform += "</table><div id=\"insbnt\"><input  type=\"submit\" value=\"保存\"/></div>";
		request.setAttribute("unitform", unitform);
		return mapping.findForward("unitquery");
	}
}