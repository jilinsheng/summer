package com.mingda.action.policy.account;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;
import com.mingda.entity.BizTOptacc;
import com.mingda.entity.SysTOrganization;

public class Accountpage {
	static Logger log = Logger.getLogger(Accountpage.class);
	/**
	 * ��ȡҵ���������
	 * 
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public StringBuffer getPolicyAccTable(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		String temp = "", tempsql = "";
		int irow = 0;
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		//
		String jacc = hashmap.get("jacc").toString();
		//
		String jyear = hashmap.get("jyear").toString();
		String jmonth = hashmap.get("jmonth").toString();
		String jdesc = hashmap.get("jdesc").toString();
		String jbegdt = hashmap.get("jbegdt").toString();
		String jenddt = hashmap.get("jenddt").toString();
		//
		String jbegpage = hashmap.get("jbegpage").toString();
		String jendpage = hashmap.get("jendpage").toString();
		//
		String jqdeptid = hashmap.get("jqdeptid").toString();
		//
		PublicComm pubilccomm = new PublicComm();
		// ����Ȩ�ޱ�ʶ
		HashMap hashmappower = new HashMap();
		PolicyQuery policyquery = new PolicyQuery();
		hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid);
		String endcheck = hashmappower.get("endcheck").toString();
		//
		String sql = "select optacc_id," + "policy_id," + "b.fullname,"
				+ "accyear," + "accmonth," + "accdesc," + "accbegdt,"
				+ "accenddt," + "accexedt," + "accexeoper," + "accexeoverdt,"
				+ "accflag," + "accmoneyflag," + "accmoney," + "accoper,"
				+ "accdt " + "from biz_t_optacc a, sys_t_organization b "
				+ "where a.organization_id = b.organization_id "
				+ "and policy_id = '" + jpid + "' ";
		//
		if ("0".equals(jacc)) { // -1ȫ��(0δ����1������2�������)
			tempsql = "and accflag = '" + jacc + "' ";
			sql += tempsql;
		} else {
			if (!"-1".equals(jacc)) { // -1ȫ��(0δ����1������2�������)
				tempsql = "and accflag = '" + jacc + "' ";
				sql += tempsql;
			}
			if (!"".equals(jyear)) {
				tempsql = "and accyear = '" + jyear + "' ";
				sql += tempsql;
			}
			if (!"".equals(jmonth)) {
				tempsql = "and accmonth = '" + jmonth + "' ";
				sql += tempsql;
			}
			if (!"".equals(jdesc)) {
				tempsql = "and accdesc like '%" + jdesc + "%' ";
				sql += tempsql;
			}
			if (!"".equals(jbegdt) && !"".equals(jenddt)) {
				//
				tempsql = " and to_char(accbegdt,'yyyy-mm-dd') >=  '" + jbegdt
						+ "'";
				sql += tempsql;
				tempsql = " and to_char(accbegdt,'yyyy-mm-dd') <=  '" + jenddt
						+ "'";
				sql += tempsql;
				//
				tempsql = " and to_char(accenddt,'yyyy-mm-dd') >=  '" + jbegdt
						+ "'";
				sql += tempsql;
				tempsql = " and to_char(accenddt,'yyyy-mm-dd') <=  '" + jenddt
						+ "'";
				sql += tempsql;
			}
		}
		if (!"".equals(jqdeptid)) {
			tempsql = "and a.organization_id like '" + jqdeptid + "%' ";
			sql += tempsql;
		} else {
			tempsql = "and a.organization_id like '" + jdeptid + "%' ";
			sql += tempsql;
		}
		//
		tempsql = " order by accflag,accdt desc";
		sql += tempsql;
		//
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB18030");
		Element root = doc.addElement("root");
		Element data = root.addElement("data");
		//
		Element eCount = data.addElement("counts");
		// ����¼����
		Element eCountChild = eCount.addElement("sqlcount");
		String sqlcount = pubilccomm.getResultCount(sql);
		eCountChild.setText(sqlcount);
		// ��ҳ
		sql = "select * from (select mytab.*, rownum row_num from (" + sql
				+ ") mytab) where row_num between " + jbegpage + " and  "
				+ jendpage;
		//
		// =============================================table
		shtml.append("<table id = 'acctb' width='100%' cellpadding='0' cellspacing='0'>");
		// =============================th
		shtml.append("<tr class='colField mybackground'>");
		shtml.append("<td height='25'>ִ�����</td>");
		shtml.append("<td height='25'>����</td>");
		shtml.append("<td height='25'>���</td>");
		shtml.append("<td height='25'>�·�</td>");
		shtml.append("<td height='25'>����</td>");
		shtml.append("<td height='25'>��ʼ����</td>");
		shtml.append("<td height='25'>��������</td>");
		// shtml.append("<td height='25'>��ʶ</td>");
		// shtml.append("<td height='25'>���</td>");
		shtml.append("<td width = '64' height='25'>����</td>");
		shtml.append("</tr>");
		// =============================th
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// =============================tr
				// ��˫��
				int mathrow = irow % 2;
				if (mathrow == 1) {// ������ɫ
					shtml.append("<tr style = 'background: #F2F5F7;'>");
				} else {
					shtml.append("<tr>");
				}
				String accid = rs.getString("optacc_id");
				//
				String accflag = rs.getString("accflag");
				//
				String policyid = rs.getString("policy_id");
				if ("1".equals(accflag)) {
					temp = "���ڽ���";
				} else if ("2".equals(accflag)) {
					temp = "�������";
				} else {
					temp = "δ����";
				}
				shtml.append("<td height='25' class='colValue' style = 'color: #6BA6FF;'>"
						+ temp + "</td>");
				shtml.append("<td height='25' class='colValue'>"
						+ rs.getString("fullname") + "</td>");
				shtml.append("<td height='25' class='colValue'>"
						+ rs.getString("accyear") + "</td>");
				shtml.append("<td height='25' class='colValue'>"
						+ rs.getString("accmonth") + "</td>");
				shtml.append("<td height='25' class='colValue'>"
						+ rs.getString("accdesc") + "</td>");
				//
				temp = rs.getString("accbegdt");
				temp = pubilccomm.getformatdt(temp);
				shtml.append("<td height='25' class='colValue'>" + temp
						+ "</td>");
				temp = rs.getString("accenddt");
				temp = pubilccomm.getformatdt(temp);
				shtml.append("<td height='25' class='colValue'>" + temp
						+ "</td>");
				temp = rs.getString("accmoneyflag");
				
				if ("21".equals(policyid)) {
					if ("0".equals(accflag)) { // δ����
						shtml.append("<td height='25' class='colValue'>"
								+ "��</td>");
					} else { // ���ڽ�����߽������
						shtml.append("<td height='25' class='colValue'>"
								+ "��</td>");
					}

					shtml.append("</tr>");
				} else {

					if ("0".equals(accflag)) { // δ����
						if ("1".equals(endcheck)) {// ҵ������Ȩ��
							shtml.append("<td height='25' class='colValue'>"
									+ "<button class = 'btn' onclick=\"delPolicyNowAcc('"
									+ accid
									+ "')\"> ɾ  �� </button>"
									+ "<button class = 'btn' onclick=\"accPolicyNowAcc('"
									+ accid + "')\"> ��  �� </button>" + "</td>");
						} else { // ҵ��������Ȩ��
							shtml.append("<td height='25' class='colValue'>"
									+ "��</td>");
						}
					} else { // ���ڽ�����߽������
						shtml.append("<td height='25' class='colValue'>"
								+ "��</td>");
					}

					shtml.append("</tr>");
				}
				//
				irow++;
				// =============================tr
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
		}
		shtml.append("</table>");
		// =============================================table
		//
		Element eTable = data.addElement("table");
		Element eTableChild = eTable.addElement("elements");
		eTableChild.setText(shtml.toString());

		// ============================================
		Node node = root.selectSingleNode("/root/data");
		// log.debug(node.asXML());
		StringBuffer rhtml = new StringBuffer("");
		rhtml.append(node.asXML());
		//
		return rhtml;
	}

	/**
	 * ���ҵ���������
	 * 
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public StringBuffer addPolicyNowAcc(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		//
		String jyear = hashmap.get("jyear").toString();
		String jmonth = hashmap.get("jmonth").toString();
		String jdesc = hashmap.get("jdesc").toString();
		String jbegdt = hashmap.get("jbegdt").toString();
		String jenddt = hashmap.get("jenddt").toString();
		//
		String jmoneyflag = hashmap.get("jmoneyflag").toString();
		String jmoney = hashmap.get("jmoney").toString();

		// ��ȡҵ�����������Ϣ
		HashMap hashmapacc = new HashMap();
		hashmapacc = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
		//String optid = hashmapacc.get("optid").toString();
		String accflag = hashmapacc.get("accflag").toString();
		if ("-1".equals(accflag)) { // �޽�������

		} else if ("0".equals(accflag)) { // �½�������
			shtml.append("��ҵ�����δ���㴦������,���ܲ���!");
			return shtml;
		} else if ("1".equals(accflag)) { // ���ڽ�������
			shtml.append("��ҵ������ִ�н��㴦��,���ܲ���!");
			return shtml;
		} else if ("2".equals(accflag)) { // �����������

		}
		// ����Ȩ�ޱ�ʶ
		HashMap hashmappower = new HashMap();
		PolicyQuery policyquery = new PolicyQuery();
		hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid);
		String endcheck = hashmappower.get("endcheck").toString();
		// Ȩ��
		if (!"1".equals(endcheck)) { // �޽���ҵ������Ȩ��
			shtml.append("���û�û����Ӹ�ҵ��������δ���Ȩ��,���ܲ���!");
			return shtml;
		} else { // ����ҵ������Ȩ��
			// �Ƿ���ڸû�����������
			boolean isacc = policyaccquery.existsPolicyAcc(hashmap);
			if (isacc) {
				shtml.append("��ҵ���Ѿ����ڽ�������,���ܲ���!");
				return shtml;
			}
			//
			String sql = "insert into biz_t_optacc " + "(optacc_id,"
					+ "policy_id," + "organization_id," + "accyear,"
					+ "accmonth," + "accdesc," + "accbegdt," + "accenddt,"
					+ "accflag," + "accmoneyflag," + "accmoney," + "accoper,"
					+ "accdt) " + "values " + "(xoptacc_id.nextval," + "'"
					+ jpid
					+ "',"
					+ "'"
					+ jdeptid
					+ "',"
					+ "'"
					+ jyear
					+ "',"
					+ "'"
					+ jmonth
					+ "',"
					+ "'"
					+ jdesc
					+ "',"
					+ "to_date('"
					+ jbegdt
					+ "','yyyy-mm-dd'),"
					+ "to_date('"
					+ jenddt
					+ "','yyyy-mm-dd'),"
					+ "'0',"
					+ "'"
					+ jmoneyflag
					+ "',"
					+ "'"
					+ jmoney
					+ "',"
					+ "'"
					+ jempid
					+ "',"
					+ "sysdate"
					+ ")";
			log.debug(sql);
			Connection conn = null; // ����Connection����
			PreparedStatement pstmt = null; // ����PreparedStatement����
			try {
				conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql);// ����sql����PreparedStatement
				pstmt.execute();
				conn.commit();
				shtml.append("��ӽ������β����ɹ�!");
			} catch (SQLException e) {
				try {
					shtml.append("��ӽ������β���ʧ��!");
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				DBUtils.close(pstmt); // �ر�PreparedStatement
				//DBUtils.close(conn); // �ر�����
			}
		}
		return shtml;
	}

	/**
	 * ɾ����ִ�е�ҵ������
	 * 
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public StringBuffer delPolicyNowAcc(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		//
		String jaccid = hashmap.get("jaccid").toString();
		//
		//
		// ��ȡҵ���������
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		boolean bacc = policyaccquery.existsNowPolicyAcc(jaccid);
		//
		if (!bacc) {
			shtml.append("ɾ���������β���ʧ��!");
			return shtml;
		}
		String sql = "delete biz_t_optacc where optacc_id = '" + jaccid + "'";
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);// ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit(); // �ر�
			//
			shtml.append("ɾ���������β����ɹ�!");

		} catch (SQLException e) {
			try {
				shtml.append("ɾ���������β���ʧ��!");
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
		}
		return shtml;
	}

	/**
	 * ��������(�洢����)
	 * 
	 * @param hashmap
	 * @return
	 */
	public StringBuffer accPolicyNowAcc(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jaccid = hashmap.get("jaccid").toString();
		log.debug(jempid+">>>>>>"+jaccid);
		// ��ȡҵ���������
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		boolean bacc = policyaccquery.existsNowPolicyAcc(jaccid);
		//
		if (!bacc) {
			shtml.append("�������β���ʧ��!");
			return shtml;
		}else{
			
		}
		Connection conn = null; // ����Connection����
		CallableStatement proc = null;
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			if("21".equals(jpid)){
				log.debug(jempid+">>>>>>"+jaccid+">>{ call GENERATELIST(?,?) }");
				proc = conn.prepareCall("{ call GENERATELIST21(?,?) }");
			}else{
				log.debug(jempid+">>>>>>"+jaccid+">>{ call GENERATELIST(?,?) }");
				proc = conn.prepareCall("{ call GENERATELIST(?,?) }");
			}
			proc.setString(1, jaccid);
			proc.setString(2, jempid);
			proc.execute();
			conn.commit(); 
			shtml.append("�������β����ɹ�!");
		} catch (SQLException e) {
			try {
				shtml.append("�������β���ʧ��!");
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				proc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//DBUtils.close(conn); // �ر�����
		}
		return shtml;
	}

	public List<BizTOptacc> getBatchsByOrganization() {
		ArrayList<BizTOptacc> list = new ArrayList<BizTOptacc>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select org.orgname,org.organization_id as oid, acc.*  from sys_t_organization org "
				+ " left join biz_t_optacc acc on (acc.organization_id = org.organization_id and "
				+ " acc.policy_id = 21 and "
				+ "  acc.accflag in (0, 1)) "
				+ " where org.depth = 3 " + " order by org.organization_id";
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				BizTOptacc e = new BizTOptacc();
				String ORGNAME = rs.getString("ORGNAME");
				String organizationId = rs.getString("OID");
				SysTOrganization sysTOrganization = new SysTOrganization();
				sysTOrganization.setOrgname(ORGNAME);
				sysTOrganization.setOrganizationId(organizationId);
				e.setSysTOrganization(sysTOrganization);
				Date accbegdt = rs.getDate("ACCBEGDT");
				e.setAccbegdt(accbegdt);
				String accdesc = rs.getString("ACCDESC");
				e.setAccdesc(accdesc);
				Date accdt = rs.getDate("ACCDT");
				e.setAccdt(accdt);
				Date accenddt = rs.getDate("ACCENDDT");
				e.setAccenddt(accenddt);
				e.setAccexedt(rs.getDate("ACCEXEDT"));
				BigDecimal accexeoper = rs.getBigDecimal("ACCEXEOPER");
				e.setAccexeoper(accexeoper);
				Date accexeoverdt = rs.getDate("ACCEXEOVERDT");
				e.setAccexeoverdt(accexeoverdt);
				String accflag = rs.getString("ACCFLAG");
				e.setAccflag(accflag);
				Double accmoney = rs.getDouble("ACCMONEY");
				e.setAccmoney(accmoney);
				String accmoneyflag = rs.getString("ACCMONEYFLAG");
				e.setAccmoneyflag(accmoneyflag);
				String accmonth = rs.getString("ACCMONTH");
				e.setAccmonth(accmonth);
				BigDecimal accoper = rs.getBigDecimal("ACCOPER");
				e.setAccoper(accoper);
				String accyear = rs.getString("ACCYEAR");
				e.setAccyear(accyear);
				BigDecimal optaccId = rs.getBigDecimal("OPTACC_ID");
				e.setOptaccId(optaccId);
				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs);
			DBUtils.close(ps);
			//DBUtils.close(conn);
		}
		return list;
	}

	public List<BizTOptacc> getDoneBatchsByOrganization(String oid) {
		ArrayList<BizTOptacc> list = new ArrayList<BizTOptacc>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select *  from biz_t_optacc t where t.organization_id = '"
				+ oid
				+ "'  and t.policy_id = '21' order by t.accyear, to_number(t.accmonth)";
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				BizTOptacc e = new BizTOptacc();
				String organizationId = rs.getString("organization_id");
				SysTOrganization sysTOrganization = new SysTOrganization();
				sysTOrganization.setOrganizationId(organizationId);
				e.setSysTOrganization(sysTOrganization);
				Date accbegdt = rs.getDate("ACCBEGDT");
				e.setAccbegdt(accbegdt);
				String accdesc = rs.getString("ACCDESC");
				e.setAccdesc(accdesc);
				Date accdt = rs.getDate("ACCDT");
				e.setAccdt(accdt);
				Date accenddt = rs.getDate("ACCENDDT");
				e.setAccenddt(accenddt);
				e.setAccexedt(rs.getDate("ACCEXEDT"));
				BigDecimal accexeoper = rs.getBigDecimal("ACCEXEOPER");
				e.setAccexeoper(accexeoper);
				Date accexeoverdt = rs.getDate("ACCEXEOVERDT");
				e.setAccexeoverdt(accexeoverdt);
				String accflag = rs.getString("ACCFLAG");
				e.setAccflag(accflag);
				Double accmoney = rs.getDouble("ACCMONEY");
				e.setAccmoney(accmoney);
				String accmoneyflag = rs.getString("ACCMONEYFLAG");
				e.setAccmoneyflag(accmoneyflag);
				String accmonth = rs.getString("ACCMONTH");
				e.setAccmonth(accmonth);
				BigDecimal accoper = rs.getBigDecimal("ACCOPER");
				e.setAccoper(accoper);
				String accyear = rs.getString("ACCYEAR");
				e.setAccyear(accyear);
				BigDecimal optaccId = rs.getBigDecimal("OPTACC_ID");
				e.setOptaccId(optaccId);

				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs);
			DBUtils.close(ps);
			//DBUtils.close(conn);
		}
		return list;
	}

	public String cancelPolicyNowAcc(String jdeptid, String jaccid, String jpid) {
		String str = "";
		String sql = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql = "select * from impl_t_month_optacc acc where acc.optacc_id='"
					+ jaccid + "'";
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			boolean flag = false;
			if (rs.next()) {
				flag = false;
			} else {
				flag = true;
			}
			if (flag) {
				sql = "delete from impl_t_accoptcheck t where t.optacc_id='"
						+ jaccid + "'";
				log.debug(sql);
				ps = conn.prepareStatement(sql);
				ps.execute();

				sql = "delete from biz_t_optcheckidea d where exists  (select 1 "
						+ "from biz_t_optcheck ck  where ck.policy_id =  '"
						+ jpid
						+ "' and ck.optcheck_id = d.optcheck_id "
						+ " and exists (select 1 "
						+ " from info_t_family fam "
						+ " where fam.familyno like '"
						+ jdeptid
						+ "%'"
						+ " and fam.family_id = ck.family_id))";
				log.debug(sql);
				ps = conn.prepareStatement(sql);
				ps.execute();
				sql = "delete from biz_t_optcheck ck "
						+ " where ck.policy_id = '" + jpid + "' "
						+ " and exists (select 1 " + " from info_t_family fam "
						+ " where fam.familyno like '" + jdeptid + "%' "
						+ " and fam.family_id = ck.family_id)";
				log.debug(sql);
				ps = conn.prepareStatement(sql);
				ps.execute();
				sql = " insert into biz_t_optcheck " + " (optcheck_id, "
						+ " policy_id, " + " family_id, " + " member_id, "
						+ " acctype, " + " countmoney, " + " moneyflag, "
						+ " checkmoney, " + " checkflag1, " + " checkflag2, "
						+ " checkflag3, " + " checkflag4, " + " checkflag5, "
						+ " checkchildmoney, " + " adjustmoney, " + " ifover, "
						+ " result, " + " resultoper, " + " resultdt, "
						+ " rebegdt, " + " reenddt, " + " recheckmoney, "
						+ " physql, " + " locsql, " + " accphysql, "
						+ " acclocsql, " + " sqlmoney, " + " optoper, "
						+ " optdt) " + " select optcheck_id, " + " policy_id, "
						+ " family_id, " + " member_id, " + " acctype, "
						+ " countmoney, " + " moneyflag, " + " checkmoney, "
						+ " checkflag1, " + " checkflag2, " + " checkflag3, "
						+ " checkflag4, " + " checkflag5, "
						+ " checkchildmoney, " + " adjustmoney, " + " ifover, "
						+ " result, " + " resultoper, " + " resultdt, "
						+ " rebegdt, " + " reenddt, " + " recheckmoney, "
						+ " physql, " + " locsql, " + " accphysql, "
						+ " acclocsql, " + " sqlmoney, " + " optoper, "
						+ " optdt " + " from biz_t_optcheck2 "
						+ " where optacc_id = '" + jaccid + "'";
				log.debug(sql);
				ps = conn.prepareStatement(sql);
				ps.execute();
				sql = "insert into biz_t_optcheckidea "
						+ " (optcheckidea_id, "
						+ " optcheck_id, "
						+ " appideaman, "
						+ " appmandelnum, "
						+ " appmanoknum, "
						+ " appmannum, "
						+ " appmannotnum, "
						+ " appresult, "
						+ " apparea, "
						+ " rebegdt, "
						+ " reenddt, "
						+ " apptime, "
						+ " note, "
						+ " depth, "
						+ " checkoper, "
						+ " checkdt, "
						+ " status) "
						+ " select d2. optcheckidea_id, "
						+ " ck2.optcheck_id, "
						+ " d2.appideaman, "
						+ " d2.appmandelnum, "
						+ " d2.appmanoknum, "
						+ " d2.appmannum, "
						+ " d2.appmannotnum, "
						+ " d2.appresult, "
						+ " d2.apparea, "
						+ " d2.rebegdt, "
						+ " d2.reenddt, "
						+ " d2.apptime, "
						+ " d2.note, "
						+ " d2.depth, "
						+ " d2.checkoper, "
						+ " d2.checkdt, "
						+ " d2.status "
						+ " from biz_t_optcheckidea2 d2, biz_t_optcheck2 ck2 "
						+ " where ck2.optcheck2_id = d2.optcheck2_id  and ck2.optacc_id = '"
						+ jaccid + "'";
				log.debug(sql);

				ps = conn.prepareStatement(sql);
				ps.execute();

				sql = "delete from biz_t_optcheckidea2 d2 where exists (select 1 "
						+ " from biz_t_optcheck2 ck2 "
						+ " where ck2.optcheck2_id = d2.optcheck2_id "
						+ " and ck2.optacc_id = '')";
				ps = conn.prepareStatement(sql);
				ps.execute();

				sql = "delete from biz_t_optcheck2 where optacc_id = ''";
				ps = conn.prepareStatement(sql);
				ps.execute();

				sql = " update    biz_t_optacc  a set a.accflag=0 where a.optacc_id='"
						+ jaccid + "'";
				ps = conn.prepareStatement(sql);
				ps.execute();
			} else {
				str = "ҵ��Ӧ�����ɷ����������ܹ���������";
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.close(rs);
			DBUtils.close(ps);
			//DBUtils.close(conn);
		}
		return str;
	}
}
