package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.query.QueryPageXml;
import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.ConstantDefine;

/**
 * ��ѯ���� 1����ѯ����ѡ��� 2����ѯ����ѡ������ 3����ѯ�������ʽ���� 4����ѯ��Ϣҳ������
 * 
 * @author xiu
 * 
 */
public class QueryManage extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(QueryManage.class);

	/**
	 * �ɵ�¼�û���� ��ѯģ����� ȡ�ò�ѯ���ñ���
	 * 
	 * @param empid
	 * @param mode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getQueryExp(String empid) {

		String srep = "";

		String sql = "select iniquery_id,code from sys_t_iniquery "
				+ "where status = '1' and employee_id = '" + empid + "'";

		List list = getSession().createSQLQuery(sql).list();

		if (null != list && list.size() > 0) {
			Object[] rs = (Object[]) list.get(0);
			if (null == rs[0] || "".equals(rs[0].toString())) {
				srep = "";
			} else {
				srep = rs[0].toString();
			}
		}

		return srep;
	}

	/**
	 * ȡ��������ѯѡ������ҳ��
	 * 
	 * @param empid
	 * @param mode
	 * @return
	 */
	@SuppressWarnings({ "unused", "static-access" })
	public StringBuffer getQueryExpHtml(String empid) {
		StringBuffer shtml = new StringBuffer("");
		StringBuffer fhtml = new StringBuffer("");
		StringBuffer mhtml = new StringBuffer("");
		StringBuffer bhtml = new StringBuffer("");
		String tftable, tmtable, tfield, tfamily, tmember;
		String osname = "", osid = "";
		String adminid = "";
		String sparid = "";
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		adminid = constantdefine.ADMIN_ID;

		// ��ѯѡ���
		shtml.append("<form id = \"queryinfoform\">");
		// ����
		fhtml.append("<select id=\"forderitemquery\" style = \"width:100%;font-size:12px\">");
		mhtml.append("<select id=\"morderitemquery\" style = \"width:100%;font-size:12px\">");
		// Ĭ������
		// ��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";// ��ͥ��
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		// ��Ա��Ϣ
		tmtable = "INFO_T_MEMBER";// ��Ա��
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		//
		tfield = "FAMILYNO";// ��ͥ���
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		mhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "MASTERNAME";// ��������
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "MASTERPAPERID";// �������֤
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "POPULATION";// ��ͥ�˿�
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		//
		tfield = "MEMBERNAME";// ��Ա����
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		mhtml.append("<option value='" + osid + "'>" + osname + "</option>");

		// ��ѯ����ѡ���
		shtml.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		shtml.append("<tr class=\"queryform\" align=\"center\">");
		shtml.append("<td colspan = '3'>��Ϣ��ѯѡ��</td>");
		// shtml.append("<th>�����</th>");
		// shtml.append("<th>����ֵ</th>");
		shtml.append("</tr>");
		// ϵͳĬ������
		sparid = getQueryExp(adminid);// -1ΪϵͳĬ������
		if (sparid.equals("")) {// ��������Ϣ��ѯ����
			shtml.append("");
		} else {// ������Ϣ��ѯ����
			// ��Ϣ��ѯѡ��
			shtml.append(getQueryInfoExpHtml(sparid));
		}
		// �Ƿ��е�ǰ�û�������
		sparid = getQueryExp(empid);
		if (sparid.equals("")) {// ������
			shtml.append("");
		} else {
			// ��Ϣ��ѯѡ��
			shtml.append(getQueryInfoExpHtml(sparid));
		}
		shtml.append("</table>");
		//
		//
		mhtml.append("</select>");
		fhtml.append("</select>");
		//
		bhtml.append("<select id=\"orderquery\" style = \"width:100%;font-size:12px\">");
		bhtml.append("<option value='asc'>����</option>");
		bhtml.append("<option value='desc'>����</option>");
		bhtml.append("</select>");
		//
		shtml.append("<fieldset>");
		shtml.append("<legend>�������</legend>");
		shtml.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		shtml.append("<tr align=\"center\">");
		shtml.append("<td id = \"fmtd\" width=\"50%\">" + fhtml + "</td>");// ��ͥ������
		shtml.append("<td id = \"mbtd\" style=\"display:none\" width=\"50%\">"
				+ mhtml + "</td>");// ��Ա������
		shtml.append("<td width=\"50%\">" + bhtml + "</td>");
		shtml.append("</tr>");
		shtml.append("</table>");
		shtml.append("</fieldset>");
		//
		//
		shtml.append("</form>");
		return shtml;
	}

	/**
	 * ��Ϣ���� ���ɰ��趨�õ���Ϣ������ѯҳ��
	 * 
	 * @param sparid
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer getQueryInfoExpHtml(String sparid) {
		StringBuffer shtml = new StringBuffer("");
		StringBuffer fhtml = new StringBuffer("");
		StringBuffer mhtml = new StringBuffer("");
		String tid = "", tspan = "", texp = "", tvalue = "", ttype = "";
		String tiid = "", tvid = "", tdid = "", tname = "", tphyname = "", fphyname = "", fname = "", ttid = "", tfid = "";
		String osname = "", osid = "";
		int ibeg = 0;

		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		String sql = "select iniqueryitem_id, iniquery_id, fieldnameloc, matchexp, logicexp, status "
				+ "from sys_t_iniqueryitem where status = '1' and  iniquery_id = '"
				+ sparid + "' order by sequence";
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {

			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tid = rs.getString("iniqueryitem_id");
				tspan = rs.getString("fieldnameloc");
				texp = rs.getString("matchexp");
				tvalue = rs.getString("logicexp");

				//
				// �ֶ�����
				ibeg = tspan.indexOf(".");// .����λ��
				if (ibeg > 0) {
					tname = tspan.substring(0, ibeg);
					fname = tspan.substring(ibeg + 1, tspan.length());
					//
					tphyname = tableinfoquery.gettablephysicfromlogic(tname);
					fphyname = tableinfoquery.getfieldphysicfromlocic(tname,
							fname);
					//
					ttype = tableinfoquery.getfieldtypefromlogicname(tname,
							fname);
					tdid = tableinfoquery.getdiscfromlogicname(tname, fname);
				}
				// log.debug(tiid);
				// log.debug(tvid);
				// log.debug(tvalue);
				//
				// ������
				tvid = "value" + tid;
				ttid = "table" + tid;
				tfid = "field" + tid;
				// �����ֶ�
				osid = tphyname + "." + fphyname;
				osname = fname;
				if (tphyname.equals("INFO_T_FAMILY")) {// ��ͥ�ֶ�
					//
					String temp = fhtml.toString();
					int iend = temp.indexOf(osid);// �Ƿ����
					if (iend < 0) {
						fhtml.append("<option value='" + osid + "'>" + osname
								+ "</option>");
					}
				} else if (tphyname.equals("INFO_T_MEMBER")) {// ��Ա�ֶ�{
					String temp = mhtml.toString();
					int iend = temp.indexOf(osid);// �Ƿ����
					if (iend < 0) {
						mhtml.append("<option value='" + osid + "'>" + osname
								+ "</option>");
					}
				}
				//
				if ("".equals(ttype) || null == ttype || "".equals(tdid)) {// �ֶν�������[�ֶδ�����߸���]
					// ����ɾ��
					delQuerySeq(tid, "");
				} else {
					shtml.append("<tr align=\"center\" title = '" + texp
							+ "' >");
					shtml.append("<td width=\"40%\" valign=\"middle\"><span class = 'itemstyle'>"
							+ fname + "</span></td>");
					//
					//
					// shtml.append("<td width=\"10%\"><img width = '10px' hight = '10px' src='/db/page/images/question.gif' alt=\""+texp+"\"></img></td>");
					if ("0".equals(ttype)) {// �ַ�
						// ������
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						if (texp.equals("��ֵ") || texp.equals("����")) {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" disabled = disabled style = \"width:100%\" value = '"
									+ texp + "'></input></td>");
						} else {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" style = \"width:100%\"></input></td>");
						}

					} else if ("1".equals(ttype)) {// ����
						// ������
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						if (texp.equals("��ֵ") || texp.equals("����")) {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" disabled = disabled style = \"width:100%\" value = '"
									+ texp + "'></input></td>");
						} else {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" style = \"width:100%\"></input></td>");
						}
					} else if ("2".equals(ttype)) {// ��ֵ
						// ������
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						if (texp.equals("��ֵ") || texp.equals("����")) {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" disabled = disabled style = \"width:100%\" value = '"
									+ texp + "'></input></td>");
						} else {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" style = \"width:100%\"></input></td>");
						}

					} else if ("3".equals(ttype)) {// ����
						// ������
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						shtml.append("<td width=\"50%\"><input id=\""
								+ tiid
								+ "\" type=\"text\" style = \"width:100%\" onFocus=\"setday(this)\"></input></td>");
					} else if ("4".equals(ttype)) {// �ֵ�
						// ������
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						shtml.append("<td width=\"50%\">"
								+ getDiscHtml(tiid, tdid) + "</td>");
					} else if (constantdefine.FIELDTYPE_DEPT.equals(ttype)) {// ����(���³�������)
						// ������
						tiid = "qdept" + tid;
						shtml.append("<td width=\"50%\"><input id=\""
								+ tiid
								+ "\" type=\"text\" style = \"width:100%\" onclick=\"choicedept(\'"
								+ tiid + "\');queryDept()\"></input></td>");
					}
					shtml.append("<td style=\"display:none\">");
					shtml.append("<span style=\"display:none\"><input id = \""
							+ ttid + "\" value = \"" + tname
							+ "\" type=\"text\" ></input></span>");
					shtml.append("<span style=\"display:none\"><input id = \""
							+ tvid + "\" value = \"" + tvalue
							+ "\" type=\"text\" ></input></span>");
					shtml.append("<span style=\"display:none\"><input id = \""
							+ tfid + "\" value = \"" + tspan
							+ "\" type=\"text\" ></input></span>");
					shtml.append("</td>");
					//
					shtml.append("</tr>");
				}
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * ȡ���ֵ�ѡ��������
	 * 
	 * @param selectid
	 * @param discid
	 * @return
	 */
	@SuppressWarnings("static-access")
	StringBuffer getDiscHtml(String selectid, String discid) {
		StringBuffer shtml = new StringBuffer("");
		String sid = "", sname = "", sfullname = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		// [��]
		String replacechr = constantdefine.REPLACEEXP_CHR;
		// [��ѡ]
		String stname = constantdefine.NOTSELECT_NAME;

		shtml.append("<select id=\"" + selectid
				+ "\" style = \"width:100%;font-size:12px\">");
		shtml.append("<option value=\"\">" + stname + "</option>");
		//
		String sql = "select dictionary_id,item from sys_t_dictionary "
				+ "where dictsort_id = '" + discid
				+ "' and status = '1' order by sequence"; // ����SQL���
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sid = rs.getString("dictionary_id");
				sname = rs.getString("item");
				sfullname = "[" + sname + replacechr + sid + "]";
				shtml.append("<option value=\"" + sfullname + "\">" + sname
						+ "</option>");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		shtml.append("</select>");
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * ���ɲ�ѯ�����б�
	 * 
	 * @param empid
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer getQueryExpSeq(String empid) {
		StringBuffer shtml = new StringBuffer("");
		StringBuffer ssyshtml = new StringBuffer("");
		StringBuffer suserhtml = new StringBuffer("");
		String adminid = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		adminid = constantdefine.ADMIN_ID;
		//
		shtml.append("<ul id=\"arrangableNodes\">");
		// ϵͳĬ��
		ssyshtml.append(getQueryExpSeqOne(adminid));
		shtml.append(ssyshtml);
		// �û�����
		suserhtml.append(getQueryExpSeqOne(empid));
		shtml.append(suserhtml);
		//
		if (suserhtml.toString().equals("")) {
			shtml.append("<div align='center'><span class='caption'>[ϵͳĬ������]</span></div>");
		} else {
			// ��ǰ�û�������
			String sparid = getQueryExp(empid);
			shtml.append("<div align='center'><span class='caption pointer' onclick=\"delAllQuerySeq('"
					+ sparid + "');\">[���ɾ���û�ȫ������]</span></div>");
		}
		shtml.append("<div align='center'><input type='button' onclick='saveQuerySeq();' value='��������'></input></div>");
		shtml.append("<div align='center' class = 'itemstyle'>�����������ק����</div>");
		shtml.append("</ul>");
		//
		shtml.append("<div id='movableNode'><ul></ul></div>");
		shtml.append("<div id='arrDestInditcator'><img src='/db/page/images/insert.gif'></img></div>");
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * ȡ���û�����ϵͳ�û��Ĳ�ѯ��������
	 * 
	 * @param empid
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public StringBuffer getQueryExpSeqOne(String empid) {
		StringBuffer shtml = new StringBuffer("");
		String adminid = "";
		String id = "", name = "", divid = "", sexp = "", seqid = "";
		boolean sysini = false;
		int irow = 0;
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		adminid = constantdefine.ADMIN_ID;
		if (empid.equals(adminid)) {// ϵͳ����
			sysini = true;
		}

		// �Ƿ��е�ǰ�û�������
		String sparid = getQueryExp(empid);

		String sql = " select iniqueryitem_id,iniquery_id,fieldnameloc,matchexp,sequence "
				+ "from sys_t_iniqueryitem "
				+ "where status = '1' and iniquery_id = '"
				+ sparid
				+ "'  order by  sequence";
		// log.debug(sql);
		try {
			List<Object[]> list = getSession().createSQLQuery(sql).list();
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					Object[] rs = (Object[]) list.get(i);

					if (null == rs[0] || "".equals(rs[0].toString())) {
						id = "";
					} else {
						id = rs[0].toString();
					}
					if (null == rs[2] || "".equals(rs[2].toString())) {
						name = "";
					} else {
						name = rs[2].toString();
					}
					if (null == rs[3] || "".equals(rs[3].toString())) {
						sexp = "";
					} else {
						sexp = rs[3].toString();
					}
					if (null == rs[4] || "".equals(rs[4].toString())) {
						seqid = "";
					} else {
						seqid = rs[4].toString();
					}

					irow = irow + 1;

					// div���seq+id
					divid = "seq" + id;

					// �ֶ�����
					int ibeg = name.indexOf(".");// .����λ��
					if (ibeg > 0) {
						name = name.substring(ibeg + 1, name.length());
					}

					shtml.append("<li class='itemstyle licss' id=\"" + divid
							+ "\">" + "[" + seqid + "]" + name + " " + sexp
							+ "");

					if (!sysini) {
						shtml.append("<span class='caption pointer'><img height = '12' width = '12' src='/db/page/images/close.gif' alt= 'ɾ���û�����' onclick=\"delQuerySeq('"
								+ id + "');\"/></span>");
						// shtml.append("<span class='caption pointer'  onclick=\"delQuerySeq('"+id+"');\">[���ɾ��]</span>");
					}
					shtml.append("</li>");
				}
			}

		} catch (RuntimeException e) {
			log.debug(e.toString());
		}
		if (!sysini) {
			// �޲�ѯ����
			if (irow < 1) {
				// ɾ��ȫ��
				delAllQuerySeq(sparid, empid);
			}
		}
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * �����������ʾ˳��
	 * 
	 * @param tnewseq
	 * @return
	 */
	public String saveQuerySeq(String tnewseq, String empid) {
		String shtml = "", seqid = "", qid = "";
		String[] seqs = tnewseq.split(","); // �����ڵ���Ϣ�зֳ�����
		// log.debug(tnewseq);
		for (int i = 0; i < seqs.length; i++) {
			String[] tmp = seqs[i].split("_"); // �з�id����˳����
			qid = tmp[0]; // ����id����
			// div���seq+id
			qid = qid.substring(3, qid.length());
			seqid = tmp[1]; // ������˳�����
			/*
			 * log.debug(qid); log.debug(seqid);
			 */

			String sql = "update sys_t_iniqueryitem set sequence = '" + seqid
					+ "' where iniqueryitem_id = '" + qid + "'";// ����������ݿ��SQL���
			Connection conn = null; // ����Connection����
			PreparedStatement pstmt = null; // ����PreparedStatement����
			try {
				conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
				pstmt.execute(); // ִ��
				conn.commit();
				shtml = "��������ʾ˳������ɹ�!"; // ��ʾ����ɹ�
			} catch (SQLException e) {
				shtml = "��������ʾ˳�����ʧ��!"; // ���ֱ����쳣
				log.debug(e.toString());
			} finally {
				DBUtils.close(pstmt); // �ر�PreparedStatement
				// DBUtils.close(conn); // �ر�����
			}
		}
		// ==========================
		// �����û����Ʋ�ѯXML
		QueryPageXml querypagexml = new QueryPageXml();
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			querypagexml.SaveQuerySeqXml(empid);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
		} finally {
			if(session.isOpen())
			session.close();
		}
		// ==========================
		return shtml;
	}

	/**
	 * ��Ϣ ȫ��ɾ���û�������Ϣ��ѯ����
	 * 
	 * @param tparid
	 * @return
	 */
	public String delAllQuerySeq(String tparid, String empid) {
		String shtml = "";
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			String sql1 = "delete sys_t_iniqueryitem where iniquery_id = '"
					+ tparid + "'";// ����������ݿ��SQL���
			String sql2 = "delete sys_t_iniquery where iniquery_id = '"
					+ tparid + "'";// ����������ݿ��SQL���
			try {
				getSession().createSQLQuery(sql1).executeUpdate();
				getSession().createSQLQuery(sql2).executeUpdate();
				shtml = "ȫ��ɾ���û���ѯ���ò����ɹ�!"; // ��ʾ����ɹ�
			} catch (RuntimeException e) {
				shtml = "ȫ��ɾ���û���ѯ���ò���ʧ��!"; // ���ֱ����쳣
				log.debug(e.toString());
			}
			QueryPageXml querypagexml = new QueryPageXml();
			querypagexml.SaveQuerySeqXml(empid);
			// ==========================
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
		} finally {
			if(session.isOpen())
			session.close();
		}
		return shtml;
	}

	/**
	 * ��Ϣ ɾ���û���Ϣ��ѯ����
	 * 
	 * @param tid
	 * @return
	 */
	public String delQuerySeq(String tid, String empid) {
		String shtml = "";
		String sql = "delete sys_t_iniqueryitem where iniqueryitem_id = '"
				+ tid + "'";// ����������ݿ��SQL���
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			pstmt.execute(); // ִ��
			conn.commit();
			shtml = "ɾ���û���ѯ���ò����ɹ�!"; // ��ʾ����ɹ�
		} catch (SQLException e) {
			shtml = "ɾ���û���ѯ���ò���ʧ��!"; // ���ֱ����쳣
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// DBUtils.close(conn); // �ر�����
		}
		// ==========================
		// �����û����Ʋ�ѯXML
		QueryPageXml querypagexml = new QueryPageXml();
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			querypagexml.SaveQuerySeqXml(empid);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
		} finally {
			session.close();
		}
		// ==========================
		return shtml;
	}

	/**
	 * ��Ϣ ����û�������Ϣ��ѯ����
	 * 
	 * @param empid
	 * @param info
	 * @param exp
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unused")
	public String addQuerySeq(String empid, String info, String exp, String type) {
		String shtml = "";
		String sql1 = "", sql2 = "", sqid = "", slogicexp = "";
		String sparid = "";
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		// PreparedStatement pstmt = null; // ����PreparedStatement����
		try {
			// �Ƿ��е�ǰ�û�������
			sparid = getQueryExp(empid);
			// ���ɱ��ʽ
			slogicexp = getQueryLogicExpFromMatchexp(info, exp, type);
			if (slogicexp.equals("")) {
				shtml = "����û���ѯ���ò���ʧ��!"; // ���ֱ����쳣
				return shtml;
			}
			//
			if (sparid.equals("")) {// ������
				// ���ѯ������
				TableInfoQuery tableinfoquery = new TableInfoQuery();
				sqid = tableinfoquery.getseqfromname("XINIQUERY_ID");
				// ��������
				ConstantDefine constantdefine = new ConstantDefine();
				sql1 = "insert into sys_t_iniquery (iniquery_id,name,status,employee_id) "
						+ "values ('" + sqid + "','��Ϣ','1','" + empid + "')";// ����������ݿ��SQL���
				log.debug(sql1);
				sql2 = "insert into sys_t_iniqueryitem (iniqueryitem_id,iniquery_id,fieldnameloc,matchexp,logicexp,status,sequence) "
						+ "values (xiniqueryitem_id.nextval,'"
						+ sqid
						+ "','"
						+ info + "','" + exp + "','" + slogicexp + "','1','1')";// ����������ݿ��SQL���
				log.debug(sql2);
				getSession().createSQLQuery(sql1).executeUpdate();
				getSession().createSQLQuery(sql2).executeUpdate();
			} else {
				sql2 = "insert into sys_t_iniqueryitem (iniqueryitem_id,iniquery_id,fieldnameloc,matchexp,logicexp,status,sequence) "
						+ "values (xiniqueryitem_id.nextval,'"
						+ sparid
						+ "','"
						+ info + "','" + exp + "','" + slogicexp + "','1','1')";// ����������ݿ��SQL���
				log.debug(sql2);
				getSession().createSQLQuery(sql2).executeUpdate();
			}
			// ==========================
			// �����û����Ʋ�ѯXML
			QueryPageXml querypagexml = new QueryPageXml();
			querypagexml.SaveQuerySeqXml(empid);
			// ==========================
			shtml = "����û���ѯ���ò����ɹ�!"; // ��ʾ����ɹ�
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
			shtml = "����û���ѯ���ò���ʧ��!"; // ���ֱ����쳣
		} finally {
			session.close();
		}
		return shtml;
	}

	/**
	 * ��Ϣ ���ֶκͱ��ʽ�����������ñ��ʽ
	 * 
	 * @param info
	 * @param exp
	 * @param type
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getQueryLogicExpFromMatchexp(String info, String exp,
			String type) {
		String srep = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		String replacechr = constantdefine.REPLACEEXP_CHR;
		// �������������������滻����SQL���������
		// ��������
		if (type.equals(constantdefine.FIELDTYPE_CHR)) {
			// �ַ�
			if (exp.equals("��ƥ��")) {
				srep = info + " LIKE " + "''" + replacechr + "%''";
			} else if (exp.equals("��ƥ��")) {
				srep = info + " LIKE ''%" + replacechr + "''";
			} else if (exp.equals("�м�ƥ��")) {
				srep = info + " LIKE ''%" + replacechr + "%''";
			}
			// ���������Ŵ�������
			replacechr = "''" + replacechr + "''";
		} else if (type.equals(constantdefine.FIELDTYPE_DATE)) {
			// ����
			// ���������Ŵ�������
			replacechr = "to_date(''" + replacechr + "'',''yyyy-mm-dd'')";
			// ���ڲ�����ƥ��
			if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("�м�ƥ��")) {
				srep = "";
			}
		} else if (type.equals(constantdefine.FIELDTYPE_DEPT)) {
			// ����
			if (exp.equals("��ƥ��")) {
				srep = info + " LIKE " + "''" + replacechr + "%''";
			} else if (exp.equals("��ƥ��")) {
				srep = info + " LIKE ''%" + replacechr + "''";
			} else if (exp.equals("�м�ƥ��")) {
				srep = info + " LIKE ''%" + replacechr + "%''";
			}
			// ���������Ŵ�������
			replacechr = "''" + replacechr + "''";
		} else if (type.equals(constantdefine.FIELDTYPE_INT)) {
			// ����
			// ���ڲ�����ƥ��
			if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("�м�ƥ��")) {
				srep = "";
			}
			//
			replacechr = replacechr;
		} else if (type.equals(constantdefine.FIELDTYPE_NUM)) {
			// ��ֵ
			// ���ڲ�����ƥ��
			if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("�м�ƥ��")) {
				srep = "";
			}
			replacechr = replacechr;
		} else {
			// �ֵ�ֵ������ƥ��
			if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("��ƥ��")) {
				srep = "";
			} else if (exp.equals("�м�ƥ��")) {
				srep = "";
			}
			// �ֵ䲻��Ҫ������,��Ϊ��Ҫ����
			replacechr = "" + replacechr + "";
		}
		if (exp.equals("����")) {
			srep = info + " = " + replacechr;
		} else if (exp.equals("����")) {
			srep = info + " > " + replacechr;
		} else if (exp.equals("С��")) {
			srep = info + " < " + replacechr;
		} else if (exp.equals("������")) {
			srep = info + " <> " + replacechr;
		} else if (exp.equals("���ڵ���")) {
			srep = info + " >= " + replacechr;
		} else if (exp.equals("С�ڵ���")) {
			srep = info + " <= " + replacechr;
		} else if (exp.equals("��ֵ")) {
			srep = info + " is null";
		} else if (exp.equals("����")) {
			srep = info + " is not null";
		}
		return srep;
	}

	/**
	 * ������������ѡ��
	 * 
	 * @param sname
	 * @return
	 */
	public StringBuffer getSelectYear(String sname) {
		StringBuffer shtml = new StringBuffer("");
		shtml.append("<select id=\"" + sname
				+ "\" style = \"width:100%;font-size:12px\">");
		for (int i = 1990; i <= 2050; i++) {
			shtml.append("<option value='" + i + "'>" + i + "</option>");
		}
		shtml.append("</select>");
		return shtml;
	}

	/**
	 * ������������ѡ��
	 * 
	 * @param sname
	 * @return
	 */
	public StringBuffer getSelectMonth(String sname) {
		StringBuffer shtml = new StringBuffer("");
		shtml.append("<select id=\"" + sname
				+ "\" style = \"width:100%;font-size:12px\">");
		for (int i = 1; i <= 12; i++) {
			shtml.append("<option value='" + i + "'>" + i + "</option>");
		}
		shtml.append("</select>");
		return shtml;
	}
}
