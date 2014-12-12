/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.policy.query;

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
import org.dom4j.Document;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.Pager;
import com.mingda.common.SessionFactory;
import com.mingda.common.implemention.BillmonthHandle;
import com.mingda.common.page.PageView;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 02-24-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="querypolicyfamily"
 *                        path="/page/policy/query/querypolicyfamily.jsp"
 */
@SuppressWarnings("unchecked")
public class QueryPolicyFamilyAction extends Action {
	static Logger log = Logger.getLogger(QueryPolicyFamilyAction.class);

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

		String monthname = request.getParameter("monthname");
		String orgid = request.getParameter("orgid");
		String policyId = request.getParameter("policyId");
		HttpSession hsession = request.getSession();
		Session session = SessionFactory.getSession();

		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		PageView pv = new PageView();

		List<Object[]> rs = null;

		String curpage = request.getParameter("cur_page");
		String sql = "";
		String type = "";
		String value = "";

		Transaction tx = session.beginTransaction();

		try {
			int totalrow = 0;
			Pager page = null;
			if (curpage == null || curpage.equals("")) {

				value = request.getParameter("value");
				type = request.getParameter("type");
				String accyear = monthname.substring(0, 4);
				String accmonth = monthname.substring(4);
				String sal = request.getParameter("sal");
				if ("".equals(sal) || null == sal) {
				} else {
					sal = "	and f.dessaltype = " + sal + "";
				}

				String sorgid = orgid;
				if (sorgid.length() >= 6) {
					sorgid = sorgid.substring(0, 6);
					sorgid = " and b.organization_id='" + sorgid + "'";
				} else {
					sorgid = "and instr( 'a'||b.organization_id ,'a'||'"
							+ sorgid + "')>0";
				}

				sql = "select f.family_id,f.familyno,"
						+ "  f.mastername,f.masterpaperid,a.countmoney,a.checkmoney,a.checkchildmoney,"
						+ "  b.optacc_id,b.policy_id,b.organization_id, f.status ,f.masterpapertype,"
						+ "(select org.orgname  from sys_t_organization org where org.organization_id =f.organization_id) as orgname "
						+ " ,f.dessaltype  ,f.ensurecount,  f.consultincome from info_t_familyclass cls, impl_t_accoptcheck a, info_t_family f, biz_t_optacc b "
						+ "  where cls.family_id =f.family_id  "
						+
						// "and f.status ='1' " +
						"and f.family_id = a.family_id" + sal
						+ "  and a.policy_id = '" + policyId + "'"
						+ "  and b.optacc_id = a.optacc_id"
						+ "  and b.accyear = '" + accyear + "'"
						+ "  and b.accmonth = '" + accmonth + "' " + sorgid
						+ "  and f.familyno like '" + orgid + "%' and f.organization_id like '" + orgid + "%'";
				// FAMILY_ID FAMILYNO MASTERNAME MASTERPAPERID COUNTMONEY
				// CHECKMONEY
				// CHECKCHILDMONEY OPTACC_ID POLICY_ID ORGANIZATION_ID
				// STATUS MASTERPAPERTYPE ORGNAME DESSALTYPE ENSURECOUNT
				// CONSULTINCOME
				HashMap cols = new HashMap();
				cols.put(3, "身份证号");
				cols.put(1, "家庭编号");
				cols.put(2, "户主姓名");
				cols.put(12, "所属");
				cols.put(4, "总金额");
				cols.put(14, "保障人口");
				cols.put(15, "家庭收入");
				hsession.setAttribute("cols", cols);

				if (type != null) {
					if (type.equals("mastername")) {
						sql += " and  f.mastername like '%" + value + "%'";
					} else if (type.equals("paperid")) {
						sql += " and f.masterpaperid='" + value + "'";
					} else if (type.equals("famno")) {
						sql += " and f.familyno='" + value + "'";
					}
				}
				sql = sql + " order by f.familyno ";

				log.error("账单查询语句:" + sql);
				BigDecimal total = (BigDecimal) session.createSQLQuery(
						"select count(1) from ( " + sql + " )").uniqueResult();
				hsession.setAttribute("sql", sql);
				if (null != total) {
					totalrow = total.intValue();
				}
				page = new Pager(totalrow, new Long(1).intValue(), "0");
				rs = session.createSQLQuery(
						"select * from (select mytab.*, rownum row_num from ("
								+ sql + ") mytab) where row_num between "
								+ page.getBeiginrow() + " and "
								+ page.getEndrow()).list();
			} else {
				sql = (String) hsession.getAttribute("sql");
				BigDecimal total = (BigDecimal) session.createSQLQuery(
						"select count(1) from ( " + sql + " )").uniqueResult();
				hsession.setAttribute("sql", sql);
				if (null != total) {
					totalrow = total.intValue();
				}
				page = new Pager(totalrow, new Long(curpage).intValue(), "0");
				rs = session.createSQLQuery(
						"select * from (select mytab.*, rownum row_num from ("
								+ sql + ") mytab) where row_num between "
								+ page.getBeiginrow() + " and "
								+ page.getEndrow()).list();
			}
			request.setAttribute("monthid", monthname);
			page.setUrl("queryPolicyFamily.do?monthname=" + monthname
					+ "&orgid=" + orgid + "&policyId=" + policyId);
			String html = "";
			for (Object[] o : rs) {

				BigDecimal family_id = (BigDecimal) o[0];
				if (null == family_id) {
					family_id = new BigDecimal(0);
				}

				String familyno = (String) o[1];
				if (null == familyno || "".equals(familyno)) {
					familyno = "";
				}
				String mastername = (String) o[2];
				if (null == mastername || "".equals(mastername)) {
					mastername = "";
				}
				BigDecimal masterpapertype = (BigDecimal) o[11];
				if (null == masterpapertype) {
					masterpapertype = new BigDecimal(0);
				}
				String masterpaperid = (String) o[3];
				if (null == masterpaperid || "".equals(masterpaperid)) {
					masterpaperid = "";
				}
				BigDecimal countmoney = (BigDecimal) o[4];
				if (null == countmoney) {
					countmoney = new BigDecimal(0);
				}
				BigDecimal dessaltype = (BigDecimal) o[13];
				if (null == dessaltype) {
					dessaltype = new BigDecimal(0);
				}

				html += "<tr><td><a style=\"text-decoration:none\" href=\"/db/page/info/card/newfamilyinfocard.do?entityId="
						+ family_id.toString()
						+ "\" target=\"_blank\">"
						+ familyno
						+ "</a></td><td>"
						+ mastername
						+ "</td><td>"
						+ pv.getDictionartHandle().getDictsortToValue(
								dictionary, masterpapertype.toString())
						+ "</td><td>"
						+ masterpaperid
						+ "</td><td>"
						+ countmoney.toString()
						+ "</td><td>"
						+ pv.getDictionartHandle().getDictsortToValue(
								dictionary, dessaltype.toString())
						+ "</td></tr>";
			}
			request.setAttribute("html", html);
			request.setAttribute("toolsmenu", page.getToolsMenu());

			request.setAttribute("monthname", monthname);
			request.setAttribute("policyId", policyId);
			BillmonthHandle billmonthhandle = new BillmonthHandle();
			request.setAttribute("monlist",
					billmonthhandle.getAccMonth("2", "1"));

			HttpSession httpsession = request.getSession();
			SysTEmployee employee = (SysTEmployee) httpsession
					.getAttribute("employee");
			SysTOrganizationDAO organdao = new SysTOrganizationDAO();
			request.setAttribute("orglist", organdao.findByParentId(employee
					.getSysTOrganization().getOrganizationId()));
			tx.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping.findForward("querypolicyfamily");
	}
}