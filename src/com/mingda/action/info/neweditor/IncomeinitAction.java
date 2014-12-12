/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.neweditor;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTFamilyDAO;
import com.mingda.common.ibatis.dao.InfoTFamilyincomeDAO;
import com.mingda.common.ibatis.dao.InfoTPayoutDAO;
import com.mingda.common.ibatis.data.InfoTFamily;
import com.mingda.common.ibatis.data.InfoTFamilyincome;
import com.mingda.common.ibatis.data.InfoTPayout;

/**
 * MyEclipse Struts Creation date: 04-13-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="income" path="/page/info/neweditor/income.jsp"
 */
public class IncomeinitAction extends Action {
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

		String familyId = (String) request.getParameter("familyId");

		SqlMapClient client = SqlMapper.getSqlMapper();

		try {
			client.startTransaction();

			InfoTFamilyincomeDAO infoTFamilyincomeDAO = new InfoTFamilyincomeDAO(
					client);
			InfoTPayoutDAO infoTPayoutDAO = new InfoTPayoutDAO(client);

			InfoTFamilyincome infoTFamilyincome = new InfoTFamilyincome();
			infoTFamilyincome.setFamilyId(new BigDecimal(familyId));
			
			InfoTPayout infoTPayout = new InfoTPayout();
			infoTPayout.setFamilyId(new Integer(familyId));
			
			infoTFamilyincome = infoTFamilyincomeDAO
					.selectByFamilyId(infoTFamilyincome);
			infoTPayout = infoTPayoutDAO.selectByFamilyId(infoTPayout);
			
			InfoTFamilyDAO  famdao =new InfoTFamilyDAO(client);
			InfoTFamily family =famdao.selectFamilyById(familyId);
			
			request.setAttribute("population", family.getPopulation().toString());
			
			request.setAttribute("income", infoTFamilyincome);
			request.setAttribute("payout", infoTPayout);
			request.setAttribute("familyId", familyId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				client.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mapping.findForward("income");
	}
}