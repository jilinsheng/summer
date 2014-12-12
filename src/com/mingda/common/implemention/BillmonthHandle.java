package com.mingda.common.implemention;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTMonth;
import com.mingda.entity.SysTEmployee;

@SuppressWarnings("unchecked")
public class BillmonthHandle extends BaseHibernateDAO {

	static Logger log = Logger.getLogger(BillmonthHandle.class);

	public String addMonth(SysTEmployee employee, String monthname,
			String monthid, String monthtemp) throws SQLException {

		String sql = "";
		sql = "select hibernate_sequence.nextval from dual";

		monthid = getSession().createSQLQuery(sql).uniqueResult().toString();

		log.error("实施sql：" + monthid);

		sql = "insert into impl_t_month "
				+ " (month_id, monname, detail, employee_id, opttime ,organization_id)"
				+ " values " + " ('" + monthid + "' ,'" + monthname
				+ "', '', '" + employee.getEmployeeId() + "',sysdate,'"
				+ employee.getSysTOrganization().getOrganizationId() + "')";
		log.error("实施sql：" + sql);

		getSession().createSQLQuery(sql).executeUpdate();

		sql = "insert into impl_t_month_optacc " + " (optacc_id, month_id) "
				+ " select  acc.optacc_id, '" + monthid
				+ "' from biz_t_optacc acc where acc.optacc_id in ("
				+ monthtemp + ")";
		log.error("实施sql：" + sql);
		getSession().createSQLQuery(sql).executeUpdate();

		sql = "insert into impl_t_bill (bill_id, " + "  money," + "  month_id,"
				+ "  family_id," + "  implmonth," + "  familyno,"
				+ "  mastername," + "  paperid," + "  orgno," + "  population,"
				+ "  accouts," + "  bankcode," + "  familyaddress, saltype)"
				+ "  select hibernate_sequence.nextval,"
				+ "  salinfo.salmoney," + "  '"
				+ monthid
				+ "',"
				+ "  fam.family_id,"
				+ "  '"
				+ monthname
				+ "',"
				+ "  fam.familyno,"
				+ "  fam.mastername,"
				+ "  fam.masterpaperid,"
				+ "  fam.organization_id,"
				+ "  fam.population,"
				+ "   act.accounts ,"
				+ "    '',"
				+ "      fam.rpraddress , fam.DESSALTYPE"
				+ "  from (select ac.family_id, sum(ac.countmoney) as salmoney"
				+ "     from impl_t_accoptcheck ac"
				+ "     where ac.optacc_id in ("
				+ monthtemp
				+ ")"
				+ "      group by ac.family_id) salinfo,"
				+ "  info_t_family fam, impl_t_account act ,"
				+ "      sys_t_organization org"
				+ "  where fam.family_id = salinfo.family_id and fam.family_id = act.family_id(+)"
				+ "   and fam.organization_id = org.organization_id";
		log.error("实施sql：" + sql);

		getSession().createSQLQuery(sql).executeUpdate();

		sql = "update impl_t_accchange ac "
				+ " set ac.month_id = '"
				+ monthid
				+ "'"
				+ " where ac.month_id is null"
				+ " and exists"
				+ " (select * from info_t_family fam where fam.organization_id like '"
				+ employee.getSysTOrganization().getOrganizationId()
				+ "%' and fam.family_id = ac.family_id)";

		log.error("实施sql：" + sql);

		getSession().createSQLQuery(sql).executeUpdate();

		return monthid;
	}

	public void editMonth(SysTEmployee employee, String monthname,
			String monthid, Connection con, String monthtemp)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "update impl_t_month " + " set " + "      monname = '"
					+ monthname + "'," + "" + "    employee_id = '"
					+ employee.getEmployeeId() + "',"
					+ "    opttime = sysdate , organization_id ='"
					+ employee.getSysTOrganization().getOrganizationId() + "'"
					+ " where month_id = '" + monthid + "'";
			log.debug("实施sql：" + sql);
			ps = con.prepareStatement(sql);
			ps.execute();

			sql = "delete impl_t_month_optacc am where am.month_id ='"
					+ monthid + "'";
			log.debug("实施sql：" + sql);
			ps = con.prepareStatement(sql);
			ps.execute();

			sql = "insert into impl_t_month_optacc "
					+ " (optacc_id, month_id) " + " select  acc.optacc_id, '"
					+ monthid
					+ "' from biz_t_optacc acc where acc.optacc_id in ("
					+ monthtemp + ")";
			log.debug("实施sql：" + sql);
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != ps) {
				ps.close();
			}
		}
	}

	public void delMonth(String monthid) throws SQLException {
		String sql = "";
		try {

			sql = "update impl_t_accchange ac set ac.month_id = '' where ac.month_id ='"
					+ monthid + "'";
			log.debug("1删除实施sql：" + sql);
			getSession().createSQLQuery(sql).executeUpdate();

			sql = "delete impl_t_bill bill where bill.month_id ='" + monthid
					+ "'";
			log.debug("1删除实施sql：" + sql);
			getSession().createSQLQuery(sql).executeUpdate();
			sql = "delete impl_t_month_optacc oa where oa.month_id ='"
					+ monthid + "'";
			log.debug("2删除实施sql：" + sql);
			getSession().createSQLQuery(sql).executeUpdate();
			sql = "delete impl_t_month m where m.month_id ='" + monthid + "'";
			log.debug("3删除实施sql：" + sql);
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/****
	 * 实施月份按业务分类查询
	 * 
	 * @param con
	 * @param monthid
	 * @return
	 * @throws SQLException
	 */
	public List getMonthDataByPolicy(String monthid) throws SQLException {

		List list = null;
		List<Object[]> rs = null;
		String sql = "";

		try {
			sql = "select biz_t_optacc.optacc_id , "
					+ " from impl_t_month, impl_t_month_optacc, biz_t_optacc "
					+ " where impl_t_month.month_id = impl_t_month_optacc.month_id "
					+ " and biz_t_optacc.optacc_id = impl_t_month_optacc.optacc_id "
					+ " and impl_t_month.month_id = '" + monthid + "'";

			rs = getSession().createSQLQuery(sql).list();

			// 业务结算id 列表

			ArrayList<String> accs = new ArrayList<String>();
			for (Object[] o : rs) {
				accs.add(o[0].toString());
			}
			for (int i = 0; i < accs.size(); i++) {

			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 生成账单文件
	 * 
	 * @param os
	 * @param monthid
	 * @param defineid
	 * @return
	 * @throws Exception
	 */
	public OutputStream createBillFile(OutputStream os, String monthid,
			String defineid, String orgid, String type) throws Exception {
		for (Object str : this.getBillFileData(monthid, orgid, type)) {
			os.write(new String(str.toString() + "\r\n").getBytes());
		}

		return os;
	}

	/***
	 * 查询数据
	 * 
	 * @param monthid
	 * @param defineid
	 * @return
	 * @throws Exception
	 */
	public List<Object> getBillFileData(String monthid, String orgid,
			String type) throws Exception {
		List<Object> list = new ArrayList<Object>();
		List<Object[]> rs = new ArrayList<Object[]>();
		try {
			String sql = "";
			// 变更户主停发文件
			if ("1".equals(type)) {
				sql = "select ac.familyno, ac. mastername, ac.paperid,'' as changtype,'' as oldinfo , 0 as money from impl_t_accchange ac, info_t_family fam "
						+ " where fam.family_id = ac.family_id "
						+ "  and fam.organization_id like '"
						+ orgid
						+ "%' "
						+ " and ac.changetype = 'S' and ac.month_id='"
						+ monthid
						+ "' and  EXISTS (SELECT 1 "
						+ " FROM impl_t_account acc "
						+ " WHERE acc.account_id = ac.account_id "
						+ " AND acc.accounts IS NOT NULL) order by fam.familyno";
				log.error("停发：" + sql);
				log.debug(sql);
			}
			// 居民迁移新开折文件
			if ("2".equals(type)) {
				/*sql = "select b.familyno, " + "  b. mastername,"
						+ "   b. paperid," + "   b.changetype,"
						+ "     b. oldinfo," + "    0 as money"
						+ "  from (SELECT fam.familyno,"
						+ "       to_char(fam.mastername) as mastername, to_char(fam.masterpaperid) as paperid,"
						+ "     'N' AS changetype," + "      '' AS oldinfo"
						+ "   FROM impl_t_account acc, info_t_family fam"
						+ "   WHERE fam.family_id = acc.family_id"
						+ "     AND fam.organization_id like '"
						+ orgid
						+ "%'"
						+ "    AND acc.accounts IS NULL"
						+ "    and exists (select 1"
						+ "        from impl_t_bill bill"
						+ "         where bill.month_id = '"
						+ monthid
						+ "'"
						+ "           and bill.orgno like '"
						+ orgid
						+ "%'"
						+ "              and bill.family_id = acc.family_id)"
						+ "     UNION"
						+ "    SELECT ac.familyno,"
						+ "         ac.mastername,"
						+ "      ac.paperid,"
						+ "        ac.changetype,"
						+ "         ac.oldinfo"
						+ "     FROM impl_t_accchange ac, info_t_family fam"
						+ "    WHERE fam.family_id = ac.family_id"
						+ "       AND fam.organization_id LIKE '"
						+ orgid
						+ "%'"
						+ "     AND ac.changetype = 'M' and ac.month_id ='"+monthid+"' "
						+ "    AND EXISTS (SELECT 1"
						+ "        FROM impl_t_account acc"
						+ "         WHERE acc.account_id = ac.account_id"
						+ "             AND acc.accounts IS NOT NULL)"
						+ "   union"
						+ "  select t1.familyno,"
						+ "          t1.mastername,"
						+ "         t1.paperid,"
						+ "         t1.changetype,"
						+ "          '' as oldinfo"
						+ "     from impl_t_accchange t1, impl_t_accchange t2"
						+ "    where t1.family_id = t2.family_id"
						+ "      and t1.changetype = 'N'"
						+ "     and t2.changetype = 'S' and t1.month_id='"
						+ monthid
						+ "' and t2.month_id='"
						+ monthid
						+ "'"
						+ "     and exists (select *"
						+ "        from impl_t_bill bill"
						+ "     where bill.month_id = '"
						+ monthid
						+ "'"
						+ "       and bill.orgno like '"
						+ orgid
						+ "%'"
						+ "       and bill.family_id = t1.family_id)) b"
						+ "  order by b.familyno";*/
				sql = "select b.familyno, b. mastername, b. paperid, b.changetype, b. oldinfo, b.money as money "
						+ " from (SELECT fam.familyno, "
						+ " to_char(fam.mastername) as mastername, "
						+ " to_char(fam.masterpaperid) as paperid, "
						+ " 'N' AS changetype, "
						+ " '' AS oldinfo, "
						+ " bill.money "
						+ " FROM impl_t_account acc, info_t_family fam, impl_t_bill bill "
						+ " WHERE fam.family_id = acc.family_id "
						+ " AND fam.organization_id like '"+orgid+"%' "
						+ " AND acc.accounts IS NULL "
						+ " and bill.month_id = '"+monthid+"' "
						+ " and bill.orgno like '"+orgid+"%' "
						+ " and bill.family_id = acc.family_id "
						+ " UNION "
						+ " SELECT ac.familyno, "
						+ " ac.mastername, "
						+ " ac.paperid, "
						+ " ac.changetype, "
						+ " ac.oldinfo, "
						+ " bill.money "
						+ " FROM impl_t_accchange ac, info_t_family fam, impl_t_bill bill "
						+ " WHERE fam.family_id = ac.family_id "
						+ " AND fam.organization_id LIKE '"+orgid+"%' "
						+ " AND ac.changetype = 'M' "
						+ " and ac.month_id = '"+monthid+"' "
						+ " AND EXISTS (SELECT 1 "
						+ " FROM impl_t_account acc "
						+ " WHERE acc.account_id = ac.account_id "
						+ " AND acc.accounts IS NOT NULL) "
						+ " and bill.month_id = '"+monthid+"' "
						+ " and bill.orgno like '"+orgid+"%' "
						+ " and bill.family_id = ac.family_id "
						+ " union "
						+ " select t1.familyno, "
						+ " t1.mastername, "
						+ " t1.paperid, "
						+ " t1.changetype, "
						+ " '' as oldinfo, "
						+ " bill.money "
						+ " from impl_t_accchange t1, impl_t_accchange t2, impl_t_bill bill "
						+ " where t1.family_id = t2.family_id "
						+ " and t1.changetype = 'N' "
						+ " and t2.changetype = 'S' "
						+ " and t1.month_id = '"+monthid+"' "
						+ " and t2.month_id = '"+monthid+"' "
						+ " and bill.month_id = '"+monthid+"' "
						+ " and bill.orgno like '"+orgid+"%' "
						+ " and bill.family_id = t1.family_id) b "
						+ " order by b.familyno";

				log.error("新开折迁移：" + sql);
				log.debug(sql);
			}
			// 续存文件
			if ("3".equals(type)) {
				sql = "select bill.familyno, bill.mastername, bill.paperid, '' as changetype, acc.accounts as oldinfo, bill.money from impl_t_bill bill  ,impl_t_account acc "
						+ " where bill.month_id = '"
						+ monthid
						+ "' and bill.orgno like '"
						+ orgid
						+ "%' and acc.family_id=bill.family_id  and acc.accounts is not null "
						+ " and not exists (select 1  from impl_t_accchange ac where ac.month_id = '"
						+ monthid
						+ "' and ac.family_id = bill.family_id) order by bill.familyno";
				log.error("续存:  " + sql);
				log.debug(sql);
			}
			rs = getSession().createSQLQuery(sql).list();
			int k=1;
			for (Object[] o : rs) {
				String str = "";

				String familyno = (String) o[0];
				if (null == familyno || "".equals(familyno)
						|| "null".equals(familyno)) {
					familyno = "";
				}
				String mastername = (String) o[1];
				if (null == mastername || "".equals(mastername)
						|| "null".equals(mastername)) {
					mastername = "";
				}
				String paperid = (String) o[2];
				if (null == paperid || "".equals(paperid)
						|| "null".equals(paperid)) {
					paperid = "";
				}
				Character changetype = (Character) o[3];
				String changetype1 = "";
				if (null == changetype || "".equals(changetype)
						|| "null".equals(changetype)) {
					changetype = null;
				} else {
					changetype1 = changetype.toString();
				}
				String temp = (String) o[4];
				if (null == temp || "".equals(temp) || "null".equals(temp)) {
					temp = "";
				}
				BigDecimal money = (BigDecimal) o[5];
				if (null == money) {
					money = new BigDecimal(0);
				}

				/*if ("1".equals(type)) {
					str = familyno + "," + mastername + "," + paperid + ",S,"
							+ mastername;
				}

				if ("2".equals(type)) {
					str = familyno + "," + mastername + "," + paperid + ","
							+ changetype1 + "," + temp;
				}
				if ("3".equals(type)) {
					str = familyno + "," + money.doubleValue();
				}*/
				
				//JL 22010106050089 肖国胜 220104195607080014 S
				if ("1".equals(type)) {
					str = "JL	"+familyno + "	" + mastername + "	" + paperid + "	S";
				}
				//1	22010101030299	刘忠义	250.00	A	220105194508030419	JL
			
				if ("2".equals(type)) {
					str = k+"	"+familyno + "	" + mastername + "	" +money.doubleValue()+"	A	"+ paperid + "	JL";
					k++;
				}
				//22010101030299	刘忠义	250.00	202541441200036		JL
				if ("3".equals(type)) {
					str = familyno + "	"+ mastername+"	"+ money.doubleValue()+"	"+temp+"	JL";
				}
				list.add(str);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/***
	 * 读取接口定义格式
	 * 
	 * @param defineid
	 * @return
	 * @throws Exception
	 */
	public Document getInterfaceDefine(String defineid) throws Exception {
		return null;
	}

	/**
	 * 是否可以撤销
	 * 
	 * @param organizationId
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public boolean isDeleteMonth(String organizationId) throws SQLException {
		boolean flag = true;
		String sql = "select * from impl_t_accchange acc, info_t_family fam where fam.organization_id like '"
				+ organizationId
				+ "%' and fam.family_id = acc.family_id and acc.month_id is null";
		log.debug("是否可以删除：" + sql);
		try {
			List rs = getSession().createSQLQuery(sql).list();
			if (null != rs && rs.size() > 0) {
				flag = false;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	/**
	 * 取实施月份
	 * 
	 * @param con
	 * @param organizationId
	 * @return
	 * @throws SQLException
	 */
	public List<ImplTMonth> findUsedMonth(String organizationId)
			throws SQLException {
		List<ImplTMonth> list = new ArrayList<ImplTMonth>();
		String sql = "select m.monname, "
				+ "substr(m.monname, 1, 4) || '年' || substr(m.monname, 5, 6) || '月' as monthdetail "
				+ " from impl_t_month m " + " where m.organization_id like '"
				+ organizationId + "%'";
		log.debug("是否可以删除：" + sql);
		List<Object[]> rs = null;
		try {
			rs = getSession().createSQLQuery(sql).list();
			for (Object[] o : rs) {
				ImplTMonth month = new ImplTMonth();
				month.setMonname(o[0].toString());
				month.setDetail(o[1].toString());
				list.add(month);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return list;

	}

	/**
	 * 业务处理年月
	 * 
	 * @param flag
	 *            业务结算标识
	 * @param overflag
	 *            后台处理标识
	 * @return
	 * @throws SQLException
	 * 
	 */
	public List<AccMonth> getAccMonth(String flag, String overflag)
			throws SQLException {

		List<AccMonth> list = new ArrayList<AccMonth>();
		List<Object[]> rs = null;
		String temp = "";
		if (!"".equals(flag)) {
			temp = "t.accflag ='" + flag + "'";
		}
		if (!"".equals(overflag)) {
			temp = "t.accflag ='" + flag + "'";
		}
		if (!"".equals(flag) && !"".equals(overflag)) {
			temp = "t.accflag ='" + flag + "'";
		}

		String sql = "select t.accyear  , t.accmonth from biz_t_optacc t where 1=1  group by t.accyear ,t.accmonth order by t.accyear desc ,t.accmonth desc";

		log.debug("是否可以删除：" + sql);

		try {
			rs = getSession().createSQLQuery(sql).list();
			for (Object[] o : rs) {
				AccMonth accMonth = new AccMonth();
				String accyear = (String) o[0];
				String accmonth = (String) o[1];
				accMonth.setAccyear(accyear);
				accMonth.setAccmonth(accmonth);
				list.add(accMonth);
			}

		} catch (RuntimeException e) {
			throw e;
		}
		return list;
	}

	/**
	 * 业务查询
	 * 
	 * @param accmonth
	 *            月份
	 * @param accyear
	 *            年
	 * @param policyId
	 *            业务id
	 * @param organizationId
	 *            结构id
	 * @return 返回信息列表
	 * @throws SQLException
	 */
	public List<PolicyFamily> getFamilyByPolicy(String accmonth,
			String accyear, String policyId, String organizationId)
			throws SQLException {

		List<PolicyFamily> list = new ArrayList<PolicyFamily>();
		List<Object[]> rs = null;

		String sql = "select f.family_id,f.familyno, "
				+ "  f.mastername,a.countmoney,a.checkmoney,a.checkchildmoney,"
				+ "  b.optacc_id,b.policy_id,b.organization_id, f.status "
				+ "  from impl_t_accoptcheck a, info_t_family f, biz_t_optacc b "
				+ "  where f.family_id = a.family_id" + "  and a.policy_id = '"
				+ policyId + "'" + "  and b.optacc_id = a.optacc_id"
				+ "  and b.accyear = '" + accyear + "'"
				+ "  and b.accmonth = '" + accmonth + "'"
				+ "  and f.organization_id like '" + organizationId + "%'";

		log.debug("业务查询：" + sql);

		try {
			rs = getSession().createSQLQuery(sql).list();

			for (Object[] o : rs) {

				PolicyFamily policyFamily = new PolicyFamily();

				BigDecimal CHECKCHILDMONEY = (BigDecimal) o[5];
				if (null == CHECKCHILDMONEY) {
					CHECKCHILDMONEY = new BigDecimal(0);
				}
				BigDecimal CHECKMONEY = (BigDecimal) o[4];
				if (null == CHECKMONEY) {
					CHECKMONEY = new BigDecimal(0);
				}
				BigDecimal COUNTMONEY = (BigDecimal) o[3];
				if (null == COUNTMONEY) {
					COUNTMONEY = new BigDecimal(0);
				}
				BigDecimal family_id = (BigDecimal) o[0];
				if (null == family_id) {
					family_id = new BigDecimal(0);
				}
				String FAMILYNO = (String) o[1];
				if (null == FAMILYNO || "".equals(FAMILYNO)) {
					FAMILYNO = "";
				}
				String MASTERNAME = (String) o[2];
				if (null == MASTERNAME || "".equals(MASTERNAME)) {
					MASTERNAME = "";
				}
				BigDecimal OPTACC_ID = (BigDecimal) o[6];
				if (null == OPTACC_ID) {
					OPTACC_ID = new BigDecimal(0);
				}
				String ORGANIZATION_ID = (String) o[7];
				if (null == ORGANIZATION_ID || "".equals(ORGANIZATION_ID)) {
					ORGANIZATION_ID = "";
				}
				BigDecimal POLICY_ID = (BigDecimal) o[8];
				if (null == POLICY_ID) {
					POLICY_ID = new BigDecimal(0);
				}
				Character STATUS = (Character) o[9];
				String STATUS1 = "";
				if (null == STATUS || "".equals(STATUS)
						|| "null".equals(STATUS)) {
					STATUS1 = null;
				} else {
					STATUS1 = STATUS.toString();
				}

				policyFamily.setCheckchildmoney(CHECKCHILDMONEY.toString());
				policyFamily.setCheckmoney(CHECKMONEY.toString());
				policyFamily.setCountmoney(COUNTMONEY.toString());
				policyFamily.setFamilyId(family_id.toString());
				policyFamily.setFamilyno(FAMILYNO);
				policyFamily.setMastername(MASTERNAME);
				policyFamily.setOptaccId(OPTACC_ID.toString());
				policyFamily.setOrganizationId(ORGANIZATION_ID);
				policyFamily.setPolicyId(POLICY_ID.toString());
				policyFamily.setStatus(STATUS1.toString());

				list.add(policyFamily);
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public HashMap<String, String> getNoticeStat(String organizationId,
			String policyId) throws SQLException {
		HashMap<String, String> map = new HashMap<String, String>();
		List<Object[]> rs = null;
		try {

			String sql = "select count(1) as hs , sum(fam.ensurecount) as rs  ,sum(chk.countmoney) as zm "
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
					+ "  sys_t_dictionary   dic "
					+ " where "
					+ " fam.family_id = chk.family_id "
					+ "  and fam.organization_id = org.organization_id "
					+ "  and fam.dessaltype = dic.dictionary_id(+) "
					+ " and fam.status = '1' "
					+ " "
					+ "  and fam.familyno like '"
					+ organizationId
					+ "%'   and fam.organization_id like '"
					+ organizationId
					+ "%'";

			log.debug(sql);
			rs = getSession().createSQLQuery(sql).list();

			for (Object[] o : rs) {

				BigDecimal hss = (BigDecimal) o[0];
				if (null == hss) {
					hss = new BigDecimal(0);
				}
				BigDecimal rss = (BigDecimal) o[1];
				if (null == rss) {
					rss = new BigDecimal(0);
				}
				BigDecimal zms = (BigDecimal) o[2];
				if (null == zms) {
					zms = new BigDecimal(0);
				}

				map.put("1", null == hss.toString() ? "0" : hss.toString());
				map.put("2", null == rss.toString() ? "0" : rss.toString());
				map.put("3", null == zms.toString() ? "0" : zms.toString());
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return map;

	}
}
