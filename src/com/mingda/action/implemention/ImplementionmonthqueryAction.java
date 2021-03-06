/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.implemention;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.mingda.common.Pager;
import com.mingda.common.SessionFactory;
import com.mingda.common.implemention.BillmonthHandle;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 12-05-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
@SuppressWarnings("unchecked")
public class ImplementionmonthqueryAction extends Action {
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
	static Logger log = Logger.getLogger(ImplementionmonthqueryAction.class);

	@SuppressWarnings({ "rawtypes", "unused" })
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List<Object[]> rs = null;
		String monthname = request.getParameter("monthname");
		String orgid = request.getParameter("orgid");

		HttpSession hsession = request.getSession();
		Session session = SessionFactory.getSession();

		String curpage = request.getParameter("cur_page");
		String sql = "";
		String type = "";
		String value = "";
		try {
			int totalrow = 0;
			Pager page = null;
			if (curpage == null || curpage.equals("")) {

				value = request.getParameter("value");
				type = request.getParameter("type");

				sql = "select bill.family_id ,bill.familyno ,bill.mastername," +
						"bill.paperid,bill.accouts,bill.money,bill.month_id "
						+ "from impl_t_bill bill, info_t_family fam where "
						+ "fam.family_id = bill.family_id and fam.familyno like '"
						+ orgid + "%' and bill.implmonth = '" + monthname + "'";

				if (type != null) {
					if (type.equals("mastername")) {
						sql += " and  bill.mastername like '%" + value + "%'";
					} else if (type.equals("paperid")) {
						sql += " and bill.paperid='" + value + "'";
					} else if (type.equals("famno")) {
						sql += " and bill.familyno='" + value + "'";
					}
				}
				sql = sql + " order by bill.familyno ";

				log.debug("账单查询语句:" + sql);
				
				HashMap cols = new HashMap();
				cols.put(1, "家庭编号");
				cols.put(2, "户主姓名");
				cols.put(3, "证件号码");
				cols.put(4, "银行账号");
				cols.put(5, "总金额");
				
				hsession.setAttribute("cols", cols);
				
				hsession.setAttribute("sql", sql);

				BigDecimal allrows = (BigDecimal) session.createSQLQuery(
						"select count(1) from ( " + sql + " )").uniqueResult();
				hsession.setAttribute("sql", sql);

				if (null != allrows) {
					totalrow = allrows.intValue();
				}
				page = new Pager(totalrow, new Long(1).intValue(), "0");

				rs = session.createSQLQuery(
						"select * from (select mytab.*, rownum row_num from ("
								+ sql + ") mytab) where row_num between "
								+ page.getBeiginrow() + " and "
								+ page.getEndrow()).list();
			} else {
				sql = (String) hsession.getAttribute("sql");
				BigDecimal allrows = (BigDecimal) session.createSQLQuery(
						"select count(1) from ( " + sql + " )").uniqueResult();
				hsession.setAttribute("sql", sql);

				if (null != allrows) {
					totalrow = allrows.intValue();
				}
				page = new Pager(totalrow, new Long(curpage).intValue(), "0");
				rs = session.createSQLQuery(
						"select * from (select mytab.*, rownum row_num from ("
								+ sql + ") mytab) where row_num between "
								+ page.getBeiginrow() + " and "
								+ page.getEndrow()).list();
			}
			request.setAttribute("monthid", monthname);
			page.setUrl("implementionmonthquery.do?monthname=" + monthname
					+ "&orgid=" + orgid);
			String html = "";
			for (Object[] o : rs) {
				// bill.family_id 0 ,bill.familyno 1 ,bill.mastername
				// 2,bill.paperid 3,bill.accouts 4 ,bill.money,bill.month_id
				String accouts = (String) o[4];
				if (null == accouts || "".equals(accouts)
						|| "null".equals(accouts)) {
					accouts = " ";
				}
				String familyno = (String) o[1];
				if (null == familyno || "".equals(familyno)
						|| "null".equals(familyno)) {
					familyno = "";
				}
				String mastername = (String) o[2];
				if (null == mastername || "".equals(mastername)
						|| "null".equals(mastername)) {
					mastername = "";
				}
				String paperid = (String) o[3];
				if (null == paperid || "".equals(paperid)
						|| "null".equals(paperid)) {
					paperid = "";
				}
				BigDecimal money = (BigDecimal) o[5];
				if (null == money) {
					money = new BigDecimal(0);
				}
				BigDecimal monthid = (BigDecimal) o[6];
				if (null == money) {
					money = new BigDecimal(0);
				}
				BigDecimal familyid = (BigDecimal) o[0];
				if (null == familyid) {
					familyid = new BigDecimal(0);
				}

				html += "<tr><td><a target=\"_blank\" href=\"../info/card/newfamilyinfocard.do?entityId="
						+ familyid.toString()
						+ "\">"
						+ familyno
						+ "</a></td><td>"
						+ mastername
						+ "</td><td>"
						+ paperid
						+ "</td><td>"
						+ accouts
						+ "</td><td>"
						+ money.toString()
						+ "</td><td><a target=\"_blank\" href=\"querypolicybymonth.do?familyId="
						+ familyid.toString()
						+ "&imonthId="
						+ monthid.toString() + "\">查看详细</a></td></tr>";
			}
			request.setAttribute("html", html);
			request.setAttribute("toolsmenu", page.getToolsMenu());
			request.setAttribute("monthname", monthname);
			BillmonthHandle monthhandle = new BillmonthHandle();
			request.setAttribute("monlist", monthhandle.findUsedMonth(orgid));
			HttpSession httpsession = request.getSession();
			SysTEmployee employee = (SysTEmployee) httpsession
					.getAttribute("employee");
			SysTOrganizationDAO organdao = new SysTOrganizationDAO();
			request.setAttribute("orglist", organdao.findByParentId(employee
					.getSysTOrganization().getOrganizationId()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping.findForward("implementionmonthquery");
	}
}