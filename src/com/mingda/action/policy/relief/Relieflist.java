package com.mingda.action.policy.relief;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;

public class Relieflist {

	/**
	 * ����ҵ������� ��������(������) ��������(������) ɾ������(������)
	 * 
	 * @param hashmap
	 * @return
	 * 
	 */
	static Logger log = Logger.getLogger(Relieflist.class);

	public StringBuffer updateListMonery(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		// ҵ���ѯ
		PolicyQuery policyquery = new PolicyQuery();
		//
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jempid = hashmap.get("jempid").toString();
		String jdeptid = hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmid = hashmap.get("jfmid").toString();
		//
		String standardsql = policyquery.getPolicyStandardType(jpid);
		// ��Ҫ����������ʶ
		String accmode = policyquery.getPolicyUserAccFlag(jpid);
		// ҵ���������
		String acctype = policyaccquery.getPolicyAccTypeFlag(jpid);
		// ��һ����������
		String onedepth = policyquery.getPolicyOneFlowFromId(jpid);
		// �������α�ʶ
		HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
		String optid = hashmapaccflag.get("optid").toString();
		String accflag = hashmapaccflag.get("accflag").toString();
		String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
		String accmoney = hashmapaccflag.get("accmoney").toString();
		//

		if ("-1".equals(accflag)) { // �޽�������
			shtml.append("��ҵ��δִ�д���,�ȴ�����������Ϣ!");
		} else if ("0".equals(accflag)) { // �½�������
			// ����Ȩ�ޱ�ʶ
			HashMap hashmappower = new HashMap();
			hashmappower = policyquery
					.getPolicyCheckFlags(jdepth, jempid, jpid);
			//
			String onecheck = hashmappower.get("onecheck").toString();
			String usercheck = hashmappower.get("usercheck").toString();
			//
			if ("1".equals(usercheck)) {
				// �û�����Ȩ��
				if ("1".equals(onecheck)) {
					// ��һ��������
					// ===============================================//
					HashMap hashmapacc = new HashMap();
					hashmapacc.put("jdeptid", jdeptid);
					hashmapacc.put("jempid", jempid);
					hashmapacc.put("jpid", jpid);
					hashmapacc.put("jfmid", jfmid);
					hashmapacc.put("onedepth", onedepth);
					hashmapacc.put("accmode", accmode);
					hashmapacc.put("acctype", acctype);
					hashmapacc.put("optid", optid);
					hashmapacc.put("accflag", accflag);
					hashmapacc.put("accmoneyflag", accmoneyflag);
					hashmapacc.put("accmoney", accmoney);
					// ���λ���ID
					String sdeptid = getStandardDeptID(jpid, jdeptid);
					if ("".equals(sdeptid)) {
						shtml.append("��ҵ��û�����ñ�׼!");
						return shtml;
					}
					hashmapacc.put("sdeptid", sdeptid);
					// ==============================================//
					// ��ʼ��������
					iniListMonery(hashmapacc);
					// ���ǿ��ִ�б�׼����(����������)
					if ("1".equals(standardsql)) {
						// ���Ӿ�������(���ϱ�׼����)
						addListMonerySQL(hashmapacc);
					} else {
						// ���Ӿ�������
						addListMonery(hashmapacc);
					}
					// ���¾�������
					updListMonery(hashmapacc);
					// ���¼�ͥ��Ϣ�����ʶ
					updateFamilyIsChang(hashmapacc);
				}
				// ��������
				delListMonery(hashmap);
			} else {
				shtml.append("���û�û�д�ҵ������Ȩ��,�޷���������!");
			}
		} else if ("1".equals(accflag)) { // ���ڽ�������

			shtml.append("��ҵ������ִ�н��㴦��,��ȴ�!");
		} else if ("2".equals(accflag)) { // �����������

			shtml.append("��ҵ��������,�ȴ������µĽ�����Ϣ!");
		}
		return shtml;
	}

	/**
	 * ��ʼ�����������
	 * 
	 * @param hashmap
	 * @return
	 */
	public StringBuffer iniListMonery(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		String matchsql = "", newsql = "", newphysql = "", newaccphysql = "";
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmid = hashmap.get("jfmid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		// ���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//

		// ������(��ͥ����)
		String sqlfamily = "select a.family_id "
				+ "from biz_t_optcheck a,info_t_family b "
				+ "where a.family_id = b.family_id " + "and a.policy_id = '"
				+ jpid + "' " + "and a.ifover = '" + onedepth + "' "
				+ "and b.organization_id like '" + jdeptid + "%' ";
		if (!"all".equals(jfmid)) {
			sqlfamily += "and a.family_id = '" + jfmid + "' ";
		}
		// ������(��Ա����)
		String sqlmember = "select a.family_id,a.member_id "
				+ "from biz_t_optcheck a,info_t_family b "
				+ "where a.family_id = b.family_id " + "and a.policy_id = '"
				+ jpid + "' " + "and a.ifover = '" + onedepth + "' "
				+ "and b.organization_id like '" + jdeptid + "%' ";
		if (!"all".equals(jfmid)) {
			sqlmember += "and a.family_id = '" + jfmid + "' ";
		}
		//
		// ������������SQL
		if ("0".equals(acctype)) { // ��ͥ����
			// ��ͥ����
			newsql = "update biz_t_optcheck opt set opt.checkmoney = '0',opt.sqlmoney = '0' "
					+ ",physql = '',locsql = '',accphysql = '',acclocsql = '' "
					+ "where opt.policy_id = '"
					+ jpid
					+ "' and exists ("
					+ sqlfamily + " and opt.family_id = a.family_id )";
		} else { // ��Ա����
			// ��Ա����
			newsql = "update biz_t_optcheck opt set opt.checkmoney = '0',opt.sqlmoney = '0' "
					+ ",physql = '',locsql = '',accphysql = '',acclocsql = '' "
					+ "where opt.policy_id = '"
					+ jpid
					+ "' and exists ("
					+ sqlmember
					+ " and opt.family_id = a.family_id and opt.member_id = a.member_id )";
		}
		// log.debug("iniListMonery:"+newsql);
		log.debug("���±��ϵ�0021" + newsql);
		// ִ��
		PublicComm pubilccomm = new PublicComm();
		pubilccomm.ExeSQLFromUserSQL(newsql);

		return shtml;
	}

	/**
	 * ������������
	 * 
	 * @param hashmap
	 * @return
	 */
	public StringBuffer addListMonery(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		String matchsql = "", newsql = "", newphysql = "", newaccphysql = "";
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmid = hashmap.get("jfmid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		// ���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();

		//
		// ������(��ͥ����)
		String sqlfamily = "select a.family_id "
				+ "from biz_t_optcheck a,info_t_family b "
				+ "where a.family_id = b.family_id and a.policy_id = '" + jpid
				+ "' and b.organization_id like '" + jdeptid + "%'";
		// ������(��Ա����)
		String sqlmember = "select a.family_id,a.member_id "
				+ "from biz_t_optcheck a,info_t_family b "
				+ "where a.family_id = b.family_id and a.policy_id = '" + jpid
				+ "' and b.organization_id like '" + jdeptid + "%'";
		//
		// ������(�Ѿ������ļ�ͥ)
		String sqlcheckfamily = "select fm.family_id "
				+ "from biz_t_optcheck opt,info_t_family fm "
				+ "where opt.family_id = fm.family_id and opt.policy_id = '"
				+ jpid + "' and fm.organization_id like '" + jdeptid
				+ "%' and opt.ifover <> '" + onedepth + "' ";
		//
		//
		// ������������SQL
		if ("0".equals(acctype)) { // ��ͥ����
			matchsql = "select f.family_id,f.masterid,xoptcheck_id.nextval,'"
					+ jpid + "','" + acctype + "','1','0','" + onedepth
					+ "','','','0','" + jempid + "',sysdate "
					+ " from info_t_family f ";
			matchsql += " where f.status = '1' and f.organization_id like '"
					+ jdeptid + "%' " + "and not exists (" + sqlfamily
					+ " and  a.family_id = f.family_id) ";
			// ��ͥ����
			newsql = "insert into biz_t_optcheck "
					+ "(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) "
					+ matchsql;
			//
		} else { // ��Ա����
			matchsql = "select d.family_id,d.member_id,xoptcheck_id.nextval,'"
					+ jpid + "','" + acctype + "','1','0','" + onedepth
					+ "','','','0','" + jempid + "',sysdate "
					+ " from info_t_family c,info_t_member d ";
			matchsql += " where  c.family_id = d.family_id and c.status = '1' and c.organization_id like '"
					+ jdeptid
					+ "%' "
					+ " and not exists ("
					+ sqlmember
					+ " and a.family_id = d.family_id  and a.member_id = d.member_id) "
					+ " and not exists ("
					+ sqlcheckfamily
					+ " and fm.family_id = c.family_id) ";
			// ��Ա����
			newsql = "insert into biz_t_optcheck "
					+ "(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) "
					+ matchsql;
			//
		}
		log.debug("addListMonery:" + newsql);
		// ִ��
		PublicComm pubilccomm = new PublicComm();
		pubilccomm.ExeSQLFromUserSQL(newsql);
		return shtml;
	}

	/**
	 * ������������(����׼����)
	 * 
	 * @param hashmap
	 * @return
	 */
	public StringBuffer addListMonerySQL(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		String matchsql = "", newsql = "", newphysql = "", newaccphysql = "";
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		// ���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();

		//

		String sql = "select standard_id, planmoney, physql, locsql "
				+ "from doper_t_standard " + "where flag = '1' "
				+ "and physql is not null " + "and policy_id = '" + jpid + "' "
				+ "and organization_id = '" + sdeptid + "' "
				+ "order by planmoney desc ";
		log.error(sql);
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			log.debug("���±��ϵ�0015" + sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				//
				String standardid = rs.getString("standard_id");
				String planmoney = rs.getString("planmoney");
				String physql = rs.getString("physql");
				String locsql = rs.getString("locsql");
				//
				matchsql = "";
				newsql = "";
				newphysql = "";
				newaccphysql = "";
				//
				// ������(��ͥ����)
				String sqlfamily = "select a.family_id "
						+ "from biz_t_optcheck a,info_t_family b "
						+ "where a.family_id = b.family_id and a.policy_id = '"
						+ jpid + "' and b.organization_id like '" + jdeptid
						+ "%'  and b.familyno like '" + jdeptid + "%'";
				// ������(��Ա����)
				String sqlmember = "select a.family_id,a.member_id "
						+ "from biz_t_optcheck a,info_t_family b "
						+ "where a.family_id = b.family_id and a.policy_id = '"
						+ jpid + "' and b.organization_id like '" + jdeptid
						+ "%'  and b.familyno like '" + jdeptid + "%'";
				//
				// ������(�Ѿ������ļ�ͥ)
				String sqlcheckfamily = "select fm.family_id "
						+ "from biz_t_optcheck opt,info_t_family fm "
						+ "where opt.family_id = fm.family_id and opt.policy_id = '"
						+ jpid + "' and fm.organization_id like '" + jdeptid
						+ "%' and b.familyno like '" + jdeptid
						+ "%' and opt.ifover <> '" + onedepth + "' ";
				//
				//
				if ("1".equals(accmoneyflag)) { // �û����������===========================================
					// ��������������SQL
					if ("0".equals(acctype)) {
						// ����������SQL���
						newphysql = physql
								+ " and info_t_family.organization_id like '"
								+ jdeptid
								+ "%' and info_t_family.familyno like '"
								+ jdeptid + "%'";
						// ������ת��ȥ�����ҿո�
						physql = physql.replace("'", "''");
						locsql = locsql.replace("'", "''");
						matchsql = "select c.family_id,f.masterid,xoptcheck_id.nextval,'"
								+ jpid
								+ "','"
								+ acctype
								+ "','1','"
								+ accmoney
								+ "','"
								+ onedepth
								+ "','"
								+ physql
								+ "','"
								+ locsql
								+ "','"
								+ accmoney
								+ "','"
								+ jempid
								+ "',sysdate "
								+ " from ("
								+ newphysql
								+ ") c,info_t_family f ";
						matchsql += " where c.family_id = f.family_id "
								+ " and not exists (" + sqlfamily
								+ " and b.family_id = c.family_id ) ";
						// ��ͥ����
						newsql = "insert into biz_t_optcheck "
								+ "(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) "
								+ matchsql;
						log.error(newsql);
					} else {
						// ����������SQL���
						newphysql = physql
								+ " and info_t_family.familyno like '"
								+ jdeptid
								+ "%' info_t_family.organization_id like '"
								+ jdeptid + "%'";
						// ������ת��ȥ�����ҿո�
						physql = physql.replace("'", "''");
						locsql = locsql.replace("'", "''");
						matchsql = "select c.family_id,c.member_id,xoptcheck_id.nextval,'"
								+ jpid
								+ "','"
								+ acctype
								+ "','1','"
								+ accmoney
								+ "','"
								+ onedepth
								+ "','"
								+ physql
								+ "','"
								+ locsql
								+ "','"
								+ accmoney
								+ "','"
								+ jempid
								+ "',sysdate " + " from (" + newphysql + ") c ";
						matchsql += " where not exists ("
								+ sqlmember
								+ " and a.family_id = c.family_id and a.member_id = c.member_id ) "
								+ " and not exists (" + sqlcheckfamily
								+ " and fm.family_id = c.family_id ) ";
						// ��Ա����
						newsql = "insert into biz_t_optcheck "
								+ "(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) "
								+ matchsql;
						log.error(newsql);
					}
				} else { // �û������������==========================================
					HashMap hashmapacc = getDeptAccPHYSQL(standardid, jdeptid);
					String accphysql = hashmapacc.get("accphysql").toString();
					String acclocsql = hashmapacc.get("acclocsql").toString();
					// ��������������SQL
					if (!"".equals(accphysql)) { // ����������
						if ("0".equals(acctype)) { // ��ͥ����
							// ����������SQL���
							newphysql = physql
									+ " and info_t_family.organization_id like '"
									+ jdeptid
									+ "%' and info_t_family.familyno like '"
									+ jdeptid + "%'";
							// ������ת��ȥ�����ҿո�
							physql = physql.replace("'", "''");
							locsql = locsql.replace("'", "''");

							newaccphysql = accphysql;
							// ������ת��ȥ�����ҿո�
							accphysql = accphysql.replace("'", "''");
							acclocsql = acclocsql.replace("'", "''");
							matchsql = "select c.family_id,f.masterid,xoptcheck_id.nextval,'"
									+ jpid
									+ "','"
									+ acctype
									+ "','1',d.acccount,'"
									+ onedepth
									+ "','"
									+ physql
									+ "','"
									+ locsql
									+ "','"
									+ accphysql
									+ "','"
									+ acclocsql
									+ "',d.acccount,'"
									+ jempid
									+ "',sysdate "
									+ " from ("
									+ newphysql
									+ ") c,("
									+ newaccphysql + ") d,info_t_family f ";
							matchsql += " where c.family_id = d.familyid and c.family_id = f.family_id  "
									+ " and not exists ("
									+ sqlfamily
									+ " and b.family_id = c.family_id ) ";
							// ��ͥ����
							newsql = "insert into biz_t_optcheck "
									+ "(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,accphysql,acclocsql,sqlmoney,optoper,optdt) "
									+ matchsql;
							log.error(newsql);

						} else { // ��Ա����
							// ����������SQL���
							newphysql = physql
									+ "   and info_t_family.organization_id like '"
									+ jdeptid
									+ "%' and info_t_family.familyno like '"
									+ jdeptid + "%'";
							// ������ת��ȥ�����ҿո�
							physql = physql.replace("'", "''");
							locsql = locsql.replace("'", "''");
							newaccphysql = accphysql;
							// ������ת��ȥ�����ҿո�
							accphysql = accphysql.replace("'", "''");
							acclocsql = acclocsql.replace("'", "''");
							matchsql = "select c.family_id,c.member_id,xoptcheck_id.nextval,'"
									+ jpid
									+ "','"
									+ acctype
									+ "','1',d.acccount,'"
									+ onedepth
									+ "','"
									+ physql
									+ "','"
									+ locsql
									+ "','"
									+ accphysql
									+ "','"
									+ acclocsql
									+ "',d.acccount,'"
									+ jempid
									+ "',sysdate "
									+ " from ("
									+ newphysql
									+ ") c,("
									+ newaccphysql + ") d ";
							matchsql += " where c.family_id = d.familyid and c.member_id = d.memberid "
									+ " and not exists ("
									+ sqlmember
									+ " and a.family_id = c.family_id and a.member_id = c.member_id ) "
									+ " and not exists ("
									+ sqlcheckfamily
									+ " and fm.family_id = c.family_id ) ";
							// ��Ա����
							newsql = "insert into biz_t_optcheck "
									+ "(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,accphysql,acclocsql,sqlmoney,optoper,optdt) "
									+ matchsql;
							log.error(newsql);
						}
					}
				}
				// log.debug("addListMonerySQL:" + newsql);
				// ִ��
				PublicComm pubilccomm = new PublicComm();
				pubilccomm.ExeSQLFromUserSQL(newsql);
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		return shtml;
	}

	/**
	 * ���¾�������
	 * 
	 * @param hashmap
	 * @return
	 */
	public StringBuffer updListMonery(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		String matchsql = "", newsql = "", newphysql = "", newaccphysql = "";
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmid = hashmap.get("jfmid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		// ���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//

		String sql = "select standard_id, planmoney, physql, locsql "
				+ "from doper_t_standard " + "where flag = '1' "
				+ "and physql is not null " + "and policy_id = '" + jpid + "' "
				+ "and organization_id = '" + sdeptid + "' "
				+ "order by planmoney asc ";
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			log.debug("���±��ϵ�0016" + sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String standardid = rs.getString("standard_id");
				String planmoney = rs.getString("planmoney");
				String physql = rs.getString("physql");
				String locsql = rs.getString("locsql");
				//
				matchsql = "";
				newsql = "";
				newphysql = "";
				newaccphysql = "";
				//
				// ������(��ͥ����)
				String sqlfamily = "select a.family_id "
						+ "from biz_t_optcheck a,info_t_family b "
						+ "where a.family_id = b.family_id "
						+ "and a.policy_id = '" + jpid + "' "
						+ "and a.ifover = '" + onedepth + "' "
						+ " and b.familyno like '" + jdeptid
						+ "%' and  b.organization_id like '" + jdeptid + "%' ";
				if (!"all".equals(jfmid)) {
					sqlfamily += "and a.family_id = '" + jfmid + "' ";
				}
				// ������(��Ա����)
				String sqlmember = "select a.family_id,a.member_id "
						+ "from biz_t_optcheck a,info_t_family b "
						+ "where a.family_id = b.family_id "
						+ "and a.policy_id = '" + jpid + "' "
						+ "and a.ifover = '" + onedepth + "' "
						+ " and familyno like '" + jdeptid
						+ "%' and b.organization_id like '" + jdeptid + "%' ";
				if (!"all".equals(jfmid)) {
					sqlmember += "and a.family_id = '" + jfmid + "' ";
				}
				//
				//
				if ("1".equals(accmoneyflag)) { // �û����������===========================================
					// ��������SQL
					if ("0".equals(acctype)) { // ��ͥ����
						// ������SQL���
						newphysql = physql
								+ " info_t_family.familyno like '"
								+ jdeptid
								+ "%' and  info_t_family.organization_id like '"
								+ jdeptid + "%'";
						// ������ת��ȥ�����ҿո�
						physql = physql.replace("'", "''");
						locsql = locsql.replace("'", "''");
						// ��ͥ����
						newsql = "update biz_t_optcheck opt set opt.checkmoney = '"
								+ accmoney
								+ "',opt.sqlmoney = '"
								+ accmoney
								+ "' "
								+ ",physql = '"
								+ physql
								+ "',locsql = '"
								+ locsql
								+ "',accphysql = '',acclocsql = '' "
								+ "where opt.policy_id = '"
								+ jpid
								+ "' and exists ("
								+ sqlfamily
								+ " and opt.family_id = a.family_id ) "
								+ "and exists ("
								+ newphysql
								+ " and opt.family_id = info_t_family.family_id)";
					} else { // ��Ա����
						// ������SQL���
						newphysql = physql
								+ " and info_t_family.familyno like '"
								+ jdeptid
								+ "%' and info_t_family.organization_id like '"
								+ jdeptid + "%'";
						// ������ת��ȥ�����ҿո�
						physql = physql.replace("'", "''");
						locsql = locsql.replace("'", "''");
						// ��Ա����
						newsql = "update biz_t_optcheck opt set opt.checkmoney = '"
								+ accmoney
								+ "',opt.sqlmoney = '"
								+ accmoney
								+ "' "
								+ ",physql = '"
								+ physql
								+ "',locsql = '"
								+ locsql
								+ "',accphysql = '',acclocsql = '' "
								+ "where opt.policy_id = '"
								+ jpid
								+ "' and exists ("
								+ sqlmember
								+ " and opt.family_id = a.family_id and opt.member_id = a.member_id ) "
								+ "and exists ("
								+ newphysql
								+ " and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_member.member_id)";
					}
				} else { // �û������������===========================================
					HashMap hashmapacc = getDeptAccPHYSQL(standardid, jdeptid);
					String accphysql = hashmapacc.get("accphysql").toString();
					String acclocsql = hashmapacc.get("acclocsql").toString();
					if (!"".equals(accphysql)) { // �޻���������
						// ��������SQL
						if ("0".equals(acctype)) { // ��ͥ����
							// ������SQL���
							newphysql = physql
									+ " and  info_t_family.organization_id  like '"
									+ jdeptid
									+ "%' and info_t_family.familyno like '"
									+ jdeptid + "%'";
							// ������ת��ȥ�����ҿո�
							physql = physql.replace("'", "''");
							locsql = locsql.replace("'", "''");
							// ��ͥ����
							matchsql = "select acccount from (" + accphysql
									+ ") where familyid = opt.family_id ";
							// ������ת��ȥ�����ҿո�
							accphysql = accphysql.replace("'", "''");
							acclocsql = acclocsql.replace("'", "''");
							//
							newsql = "update biz_t_optcheck opt set  ";

							newsql += "opt.checkmoney = (" + matchsql
									+ "),opt.sqlmoney = (" + matchsql + "), ";
							newsql += "physql = '"
									+ physql
									+ "',locsql = '"
									+ locsql
									+ "',accphysql = '"
									+ accphysql
									+ "',acclocsql = '"
									+ acclocsql
									+ "' "
									+ "where opt.policy_id = '"
									+ jpid
									+ "' and exists ("
									+ sqlfamily
									+ " and opt.family_id = a.family_id ) "
									+ "and exists ("
									+ newphysql
									+ " and opt.family_id = info_t_family.family_id)";
						} else { // ��Ա����
							// ������SQL���
							newphysql = physql
									+ " and info_t_family.organization_id like '"
									+ jdeptid
									+ "%' and info_t_family.familyno like '"
									+ jdeptid + "%'";
							// ������ת��ȥ�����ҿո�
							physql = physql.replace("'", "''");
							locsql = locsql.replace("'", "''");
							// ��Ա����
							matchsql = "select acccount from ("
									+ accphysql
									+ ") where familyid = opt.family_id and memberid = opt.member_id ";
							// ������ת��ȥ�����ҿո�
							accphysql = accphysql.replace("'", "''");
							acclocsql = acclocsql.replace("'", "''");
							//
							newsql = "update biz_t_optcheck opt set  ";

							newsql += "opt.checkmoney = (" + matchsql
									+ "),opt.sqlmoney = (" + matchsql + "), ";
							newsql += "physql = '"
									+ physql
									+ "',locsql = '"
									+ locsql
									+ "',accphysql = '"
									+ accphysql
									+ "',acclocsql = '"
									+ acclocsql
									+ "' "
									+ "where opt.policy_id = '"
									+ jpid
									+ "' and exists ("
									+ sqlmember
									+ " and opt.family_id = a.family_id and opt.member_id = a.member_id ) "
									+ "and exists ("
									+ newphysql
									+ " and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_member.member_id)";
						}
					}
				}
				log.debug("updListMonery:" + newsql);
				// ִ��
				PublicComm pubilccomm = new PublicComm();
				pubilccomm.ExeSQLFromUserSQL(newsql);

			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		return shtml;
	}

	/**
	 * ���ǿ��ִ����������
	 * 
	 * @param hashmap
	 * @return
	 */
	public StringBuffer delListMonery(HashMap hashmap) {
		StringBuffer shtml = new StringBuffer("");
		//
		//
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		String newsql = "";
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jpid = hashmap.get("jpid").toString();

		// ҵ���������
		String acctype = policyaccquery.getPolicyAccTypeFlag(jpid);
		//
		// ǿ��ִ�������Ѿ�ɾ����ͥ����================================
		newsql = "delete biz_t_optcheckidea ide where exists (select opt.optcheck_id from biz_t_optcheck opt where opt.policy_id = '"
				+ jpid
				+ "'  and exists (select fm.family_id from info_t_family fm where  fm.familyno like '"
				+ jdeptid
				+ "%'  and fm.organization_id like '"
				+ jdeptid
				+ "%' and fm.status = '0' and fm.family_id = opt.family_id) and ide.optcheck_id = opt.optcheck_id)";
		// log.debug("delListMonery:"+newsql);
		// ִ��
		PublicComm pubilccomm = new PublicComm();
		pubilccomm.ExeSQLFromUserSQL(newsql);
		//
		newsql = "delete biz_t_optcheck opt where opt.policy_id = '"
				+ jpid
				+ "'  and exists (select fm.family_id from info_t_family fm where fm.familyno like '"
				+ jdeptid + "%' and fm.organization_id like '" + jdeptid
				+ "%' and fm.status = '0' and fm.family_id = opt.family_id)";
		// log.debug("delListMonery:"+newsql);
		// ִ��
		pubilccomm = new PublicComm();
		pubilccomm.ExeSQLFromUserSQL(newsql);
		// ===========================================================
		// ǿ��ִ�������ظ�����=========================================
		if ("0".equals(acctype)) { // ��ͥ����
			/*
			 * newsql =
			 * "delete from biz_t_optcheckidea ide where exists (select a.optcheck_id from biz_t_optcheck a where policy_id = '"
			 * + jpid +
			 * "'  and exists (select fm.family_id from info_t_family fm where fm.organization_id like '"
			 * + jdeptid +
			 * "%' and fm.family_id =  a.family_id) and (a.family_id) in (select family_id from biz_t_optcheck where policy_id = '"
			 * + jpid +
			 * "'  group by family_id having count(*) > 1) and rowid not in (select min(rowid) from biz_t_optcheck where policy_id = '"
			 * + jpid +
			 * "'  group by family_id having count(*) > 1) and ide.optcheck_id = a.optcheck_id)"
			 * ;
			 */

			newsql = "delete biz_t_optcheckidea ttt where ttt.optcheckidea_id in "
					+ " (select min(tt.optcheckidea_id) from biz_t_optcheckidea tt "
					+ " where exists (select 1 from info_t_family fam, biz_t_optcheck k "
					+ " where fam.family_id = k.family_id  and k.optcheck_id = tt.optcheck_id "
					+ " and k.policy_id = '"
					+ jpid
					+ "' and fam.organization_id  like '"
					+ jdeptid
					+ "%' and fam.familyno like '"
					+ jdeptid
					+ "%') having "
					+ " count(*) > 1 group by tt.optcheck_id)";
			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);
			/*
			 * newsql = "delete from biz_t_optcheck a where policy_id = '" +
			 * jpid +
			 * "' and exists (select fm.family_id from info_t_family fm where fm.organization_id like '"
			 * + jdeptid +
			 * "%' and fm.family_id =  a.family_id) and (a.family_id) in (select family_id from biz_t_optcheck where policy_id = '"
			 * + jpid +
			 * "' group by family_id having count(*) > 1) and rowid not in (select min(rowid) from biz_t_optcheck where policy_id = '"
			 * + jpid + "' group by family_id having count(*) > 1)";
			 */

			newsql = " delete from biz_t_optcheck a " + " where policy_id = '"
					+ jpid + "' " + " and a.optcheck_id in "
					+ " (select min(k.optcheck_id) "
					+ " from biz_t_optcheck k " + " where k.policy_id = '"
					+ jpid + "' " + " and exists (select 1 "
					+ " from info_t_family fam "
					+ " where  fam.organization_id like '" + jdeptid
					+ "%'  and fam.familyno like '" + jdeptid + "%' "
					+ " and k.family_id = fam.family_id) having "
					+ " count(*) > 1 " + " group by k.family_id)";

			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);
		} else { // ��Ա����
			/*
			 * newsql =
			 * "delete from biz_t_optcheckidea ide where exists (select a.optcheck_id from biz_t_optcheck a where policy_id = '"
			 * + jpid +
			 * "' and exists (select fm.family_id from info_t_family fm where fm.organization_id like '"
			 * + jdeptid +
			 * "%' and fm.family_id =  a.family_id) and (a.family_id, a.member_id) in (select family_id, member_id  from biz_t_optcheck where policy_id = '"
			 * + jpid +
			 * "' group by family_id, member_id having count(*) > 1) and rowid not in (select min(rowid) from biz_t_optcheck where policy_id = '"
			 * + jpid +
			 * "'  group by family_id, member_id having count(*) > 1) and ide.optcheck_id = a.optcheck_id)"
			 * ;
			 */

			newsql = "delete biz_t_optcheckidea ttt "
					+ " where ttt.optcheckidea_id in  "
					+ " (select min(tt.optcheckidea_id) "
					+ "  from biz_t_optcheckidea tt "
					+ "  where exists "
					+ " (select 1 "
					+ "  from Info_t_Member mem, info_t_family fam, biz_t_optcheck k "
					+ " where mem.member_id = k.member_id "
					+ "   and mem.family_id = fam.family_id "
					+ "  and k.optcheck_id = tt.optcheck_id "
					+ "  and k.policy_id = '" + jpid + "' "
					+ " and fam.organization_id like '" + jdeptid
					+ "%'  and fam.familyno like '" + jdeptid + "%') having "
					+ " count(*) > 1 " + "  group by tt.optcheck_id)";

			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);
			/*
			 * newsql = "delete  biz_t_optcheck a where policy_id = '" + jpid +
			 * "' " +
			 * " and exists (select fm.family_id from info_t_family fm where fm.organization_id like '"
			 * + jdeptid +
			 * "%' and fm.family_id =  a.family_id) and (a.family_id, a.member_id) in (select family_id, member_id from biz_t_optcheck where policy_id = '"
			 * + jpid + "' " +
			 * " group by family_id, member_id having count(*) > 1) and rowid not in (select min(rowid) from biz_t_optcheck where policy_id = '"
			 * + jpid + "' " +
			 * " group by family_id, member_id having count(*) > 1)";
			 */
			newsql = "delete from biz_t_optcheck a " + " where policy_id = '"
					+ jpid + "' " + "  and a.optcheck_id in "
					+ "  (select min(k.optcheck_id) "
					+ "  from biz_t_optcheck k " + " where k.policy_id = '"
					+ jpid + "' " + "  and exists (select 1 "
					+ "  from info_t_family fam ,info_t_member mem "
					+ "  where fam.organization_id like '" + jdeptid + "%' "
					+ "and fam.familyno like '" + jdeptid
					+ "%' and mem.family_id=fam.family_id  "
					+ "  and k.member_id = mem.member_id) having "
					+ " count(*) > 1 " + "  group by k.member_id)";
			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);
		}
		// =====================================================================
		// ǿ��ִ����ʱ������������(�ǵͱ�������)=================================
		if ("82".equals(jpid) || "99".equals(jpid)) {
			newsql = "delete biz_t_optcheck opt where opt.policy_id = '"
					+ jpid
					+ "' "
					+ " and exists (select fm.family_id from info_t_family fm where fm.familyno like '"
					+ jdeptid
					+ "%' and fm.organization_id like '"
					+ jdeptid
					+ "%' and fm.family_id = opt.family_id)"
					+ " and not exists (select opt1.family_id from biz_t_optcheck opt1 where opt1.policy_id = 21 "
					+ " and opt.family_id = opt1.family_id)";
			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);

			newsql = "delete from biz_t_optcheck ck "
					+ " where ck.family_id in (select fam.family_id "
					+ " from info_t_family fam, biz_t_familystatus s "
					+ " where s.family_id = fam.family_id "
					+ " and s.policy_id = '21' "
					+ " and fam.organization_id like '" + jdeptid + "%' "
					+ " and s.flag = '3') " + " and ck.policy_id = '" + jpid
					+ "'";
			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);

		}

		// ==================================================================
		// ǿ��ִ����ʱ������������(ͣ������)==================================
		if ("82".equals(jpid) || "99".equals(jpid)) {
			newsql = "delete biz_t_optcheck opt where opt.policy_id = '"
					+ jpid
					+ "' "
					+ " and exists (select fm.family_id from info_t_family fm where  fm.familyno like '"
					+ jdeptid
					+ "%' and  fm.organization_id like '"
					+ jdeptid
					+ "%' and fm.family_id = opt.family_id)"
					+ " and exists (select opt1.family_id from biz_t_optcheck opt1 where opt1.policy_id = 21 and opt1.result = 8 "
					+ " and opt.family_id = opt1.family_id)";
			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);
			newsql = "delete from biz_t_optcheck ck "
					+ " where ck.family_id in (select fam.family_id "
					+ " from info_t_family fam, biz_t_familystatus s "
					+ " where s.family_id = fam.family_id "
					+ " and s.policy_id = '21' "
					+ " and fam.organization_id like '" + jdeptid + "%' "
					+ " and s.flag = '3') " + " and ck.policy_id = '" + jpid
					+ "'";
			// log.debug("delListMonery:"+newsql);
			// ִ��
			pubilccomm = new PublicComm();
			pubilccomm.ExeSQLFromUserSQL(newsql);
		}
		// ==================================================================
		return shtml;
	}

	/**
	 * ���¼�ͥ�����ʶ
	 * 
	 * @param jdeptid
	 * @return
	 */
	public String updateFamilyIsChang(HashMap hashmap) {
		String srep = "";
		//
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmid = hashmap.get("jfmid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		// ���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//
		// ������(��ͥ����)
		String sqlfamily = "select a.family_id "
				+ "from biz_t_optcheck a,info_t_family b "
				+ "where a.family_id = b.family_id " + "and a.policy_id = '"
				+ jpid + "' " + "and a.ifover = '" + onedepth + "' "
				+ "and b.organization_id like '" + jdeptid + "%' ";
		if (!"all".equals(jfmid)) {
			sqlfamily += "and a.family_id = '" + jfmid + "' ";
		}
		//
		String sql = "update info_t_family fm set fm.ischange = '0' "
				+ "where fm.ischange = '1' and exists (" + sqlfamily
				+ " and fm.family_id = a.family_id )";
		// ִ��
		PublicComm pubilccomm = new PublicComm();
		pubilccomm.ExeSQLFromUserSQL(sql);
		// log.debug(sql);
		return srep;
	}

	/**
	 * ��ȡ��׼�ĵ�ǰ�����ĺ��㹫ʽ��Ϣ
	 * 
	 * @param standardid
	 * @param deptid
	 * @return
	 */
	public HashMap getDeptAccPHYSQL(String standardid, String deptid) {
		HashMap hashmap = new HashMap();
		int irow = 0;
		String sql = "select accexpphysql, accexplocsql "
				+ "from doper_t_standarddept "
				+ "where flag = '1' and accexpphysql is not null "
				+ "and standard_id = '"
				+ standardid
				+ "' "
				+ "and instr('#' || '"
				+ deptid
				+ "', '#' || organization_id) > 0  order by organization_id desc ";
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			log.debug("���±��ϵ�0017" + sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hashmap.put("accphysql", rs.getString("accexpphysql"));
				hashmap.put("acclocsql", rs.getString("accexplocsql"));
				irow++;
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		if (irow == 0) {
			hashmap.put("accphysql", "");
			hashmap.put("acclocsql", "");
		}
		return hashmap;
	}

	/**
	 * ��ȡ���λ���ID
	 * 
	 * @param jpid
	 * @param jdeptid
	 * @return
	 */
	public String getStandardDeptID(String jpid, String jdeptid) {
		String sdeptid = "";
		//

		String sql = "select standard_id,organization_id "
				+ "from doper_t_standard "
				+ "where flag = '1' "
				+ "and physql is not null "
				+ "and policy_id = '"
				+ jpid
				+ "' "
				+ "and instr('#' || '"
				+ jdeptid
				+ "', '#' || organization_id) > 0  order by organization_id desc ";

		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			log.debug("���±��ϵ�0014" + sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sdeptid = rs.getString("organization_id");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		return sdeptid;
	}
}
