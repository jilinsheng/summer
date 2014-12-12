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

import com.mingda.common.Pager;
import com.mingda.common.SessionFactory;
import com.mingda.common.implemention.BillmonthHandle;
import com.mingda.common.page.PageView;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.entity.SysTEmployee;

@SuppressWarnings("unchecked")
public class NoticePolicyQueryAction extends Action {
	static Logger log = Logger.getLogger(NoticePolicyQueryAction.class);
	@SuppressWarnings("rawtypes")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpsession = request.getSession();
		String orgid = request.getParameter("orgid");
		String policyId = request.getParameter("policyId");
		String sal = request.getParameter("sal");
		HttpSession hsession = request.getSession();
		Session session = SessionFactory.getSession();
		List<Object[]> rs = null;
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		PageView pv = new PageView();
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
				if ("".equals(sal) || null == sal) {
				} else {
					sal = "	fam.dessaltype = " + sal + "  and";
				}
				sql = "select chk.countmoney, "
						+ " chk.checkmoney, "
						+ " chk.checkchildmoney, "
						+ " fam.masterpaperid, "
						+ " fam.familyno, "
						+ " fam.mastername, "
						+ " fam.ensurecount, "
						+ "  org.fullname, "
						+ "  fam.famaddress, "
						+ "  fam.family_id, "
						+ "  fam.masterpapertype, "
						+ "  fam.dessaltype, "
						+ "  dic.item as dv_dessaltype "
						+ "  from  (select bz.family_id, "
						+ " sum(decode(bz.result, 2, bz.checkmoney, recheckmoney)) as countmoney, "
						+ " sum(bz.checkmoney) as checkmoney, "
						+ " sum(bz.checkchildmoney) as checkchildmoney "
						+ " from biz_t_optcheck bz "
						+ " where bz.policy_id = '"
						+ policyId
						+ "' "
						+ " and ((bz.result = 2 and bz.moneyflag = 1) or "
						+ " (bz.moneyflag in (2, 3, 4, 7) and bz.result <> 8)) "
						+ " group by bz.family_id) chk, "
						+ "  info_t_family      fam, "
						+ "   sys_t_organization org, "
						+ "  sys_t_dictionary   dic " + " where " + sal
						+ " fam.family_id = chk.family_id "
						+ "  and fam.organization_id = org.organization_id "
						+ "  and fam.dessaltype = dic.dictionary_id(+) "
						+ " and fam.status = '1' " + " "
						+ "  and fam.familyno like '" + orgid
						+ "%' and fam.organization_id like '" + orgid + "%'";
				HashMap cols = new HashMap();
				cols.put(3, "证件号码");
				cols.put(4, "家庭编号");
				cols.put(5, "户主姓名");
				cols.put(6, "保障人口");
				cols.put(7, "所属");
				cols.put(8, "家庭地址");
				cols.put(0, "总金额");
				cols.put(12, "保障类型");
				httpsession.setAttribute("cols", cols);
				if (type != null) {
					if (type.equals("mastername")) {
						sql += " and  fam.mastername like '%" + value + "%'";
					} else if (type.equals("paperid")) {
						sql += " and fam.masterpaperid='" + value + "'";
					} else if (type.equals("famno")) {
						sql += " and fam.familyno='" + value + "'";
					}
				}
				sql = sql + " order by fam.familyno ";
				log.error("账单查询语句:" + sql);
				BigDecimal allrows = (BigDecimal) session.createSQLQuery(
						"select count(1) from ( " + sql + " )").uniqueResult();
				if (null != allrows) {
					totalrow = allrows.intValue();
				}
				hsession.setAttribute("sql", sql);
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
			page.setUrl("noticePolicyQuery.do?&orgid=" + orgid + "&policyId="
					+ policyId);
			String html = "";
			for (Object[] o : rs) {
				String saltype = (String) o[12];
				BigDecimal money = (BigDecimal) o[0];
				if (null == money) {
					money = new BigDecimal(0);
				}
				String onname = (String) o[7];
				if (null == onname || "".equals(onname)) {
					onname = "";
				}
				BigDecimal salcount = (BigDecimal) o[6];
				if (null == salcount) {
					salcount = new BigDecimal(0);
				}
				String familyno = (String) o[4];
				if (null == familyno || "".equals(familyno)) {
					familyno = "";
				}
				String mastername = (String) o[5];
				if (null == mastername || "".equals(mastername)) {
					mastername = "";
				}
				String address = (String) o[8];
				if (null == address || "".equals(address)) {
					address = "";
				}
				BigDecimal familyid = (BigDecimal) o[9];

				if (null == familyid) {
					familyid = new BigDecimal(0);
				}
				BigDecimal masterpapertype = (BigDecimal) o[10];
				String masterpapertype1 = "";
				if (null == masterpapertype) {
				} else {
					masterpapertype1 = pv.getDictionartHandle()
							.getDictsortToValue(dictionary,
									masterpapertype.toString());
				}
				String paperid = (String) o[3];
				if (null == paperid || "".equals(paperid)) {
					paperid = "";
				}
				html += "<tr><td><a style=\"text-decoration:none\" href=\"/db/page/info/card/newfamilyinfocard.do?entityId="
						+ familyid.toString()
						+ "\" target=\"_blank\">"
						+ familyno
						+ "</a></td><td>"
						+ mastername
						+ "</td><td>"
						+ masterpapertype1
						+ "</td><td>"
						+ paperid
						+ "</td><td>"
						+ money
						+ "</td><td>"
						+ onname
						+ "</td><td>"
						+ address
						+ "</td><td>"
						+ saltype
						+ "</td></tr>";
			}
			request.setAttribute("html", html);
			request.setAttribute("toolsmenu", page.getToolsMenu());
			request.setAttribute("policyId", policyId);
			BillmonthHandle billmonthhandle = new BillmonthHandle();
			HashMap<String, String> map = billmonthhandle.getNoticeStat(orgid,
					policyId);
			String str = "当前业务在保户：" + map.get("1") + "户	" + map.get("2")
					+ "人		" + map.get("3") + "元";
			request.setAttribute("str", str);
			SysTEmployee employee = (SysTEmployee) httpsession
					.getAttribute("employee");
			String orgids = employee.getSysTOrganization().getOrganizationId();
			SysTOrganizationDAO organdao = new SysTOrganizationDAO();
			request.setAttribute("orglist", organdao.findByParentId(orgids));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return mapping.findForward("noticepolicyquery");
	}
}