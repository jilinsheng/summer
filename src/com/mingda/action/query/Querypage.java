package com.mingda.action.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.querymanage.QueryManage;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

public class Querypage {
	static Logger log = Logger.getLogger(Querypage.class);

	@SuppressWarnings("static-access")
	public StringBuffer getQueryExpHtml(String empid) {
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		StringBuffer shtml = new StringBuffer("");
		StringBuffer shtmltb = new StringBuffer("");
		StringBuffer shtmlfm = new StringBuffer("");
		StringBuffer shtmlmm = new StringBuffer("");
		String tftable, tmtable, tfield, tfamily, tmember;
		String osname = "", osid = "";
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		//
		QueryManage querymanage = new QueryManage();
		//
		// Ĭ������
		// =================================����==================================
		shtmlfm.append("<select id='fmlist' style = 'width:100%;'>");
		shtmlmm.append("<select id='mmlist' style = 'width:100%;display:none;'>");
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
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "MASTERNAME";// ��������
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		//
		tfield = "MEMBERNAME";// ��Ա����
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "PAPERID";// ��Ա���֤
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "RELMASTER";// �뻧����ϵ
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");
		//
		tfield = "MASTERPAPERID";// �������֤
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "POPULATION";// ��ͥ�˿�
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "SALMONEY";// ���Ͻ��
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "DESSALTYPE";// ��������
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "ORGANIZATION_ID";// ��ͥ����ID
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");

		// =================================����=================================
		// ==========================��ѯ����ѡ���===============================
		//
		String stmp = "", sparid = "0", stvid = "", sfvid = "", sqvid = "";
		String tid = "", fvalue = "", texp = "", qvalue = "";
		String tname = "", fname = "", tphyname = "", fphyname = "", ttype = "", tdisid = "";
		//
		shtmltb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' style = 'font-size: 12px;'>");
		// �Ƿ��е�ǰ�û�������
		sparid = getQueryExp(empid);
		String sql = "select " + "iniqueryitem_id," + "iniquery_id,"
				+ "fieldnameloc," + "matchexp," + "logicexp," + "status "
				+ "from sys_t_iniqueryitem  " + "where status = '1' ";
		if (!"".equals(sparid)) {
			sql += "and (iniquery_id = '0' or iniquery_id = '" + sparid + "') ";
		} else {
			sql += "and iniquery_id = '0' ";
		}
		sql += "order by sequence";
		 log.debug(sql);
		
		try {

			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tid = rs.getString("iniqueryitem_id");
				fvalue = rs.getString("fieldnameloc");
				texp = rs.getString("matchexp");
				qvalue = rs.getString("logicexp");
				
				
				//
				// �ֶ�����
				int ibeg = fvalue.indexOf(".");// .����λ��
				if (ibeg > 0) {
					tname = fvalue.substring(0, ibeg);
					fname = fvalue.substring(ibeg + 1, fvalue.length());
					//
					tphyname = tableinfoquery.gettablephysicfromlogic(tname);
					fphyname = tableinfoquery.getfieldphysicfromlocic(tname,
							fname);
					//
					ttype = tableinfoquery.getfieldtypefromlogicname(tname,
							fname);
					tdisid = tableinfoquery.getdiscfromlogicname(tname, fname);
					log.debug(tname+","+ttype+","+fname);
				}
				log.debug(tname+","+ttype+","+fname);
				// �ֶ�ƥ�����
				if ("".equals(ttype)) {
					// ����ɾ��
					log.debug(tname+","+ttype+","+fname);
					querymanage.delQuerySeq(tid, empid);
				} else {
					// ====================��ѯ����===============
					shtmltb.append("<tr align='center' title = '" + texp + "'>");
					shtmltb.append("<td width='50%'>");
					shtmltb.append("<span style='color: #6BA6FF;'>" + fname
							+ "</span>");
					shtmltb.append("</td>");
					shtmltb.append("<td>");
					//
					String jname = "";
					if ("0".equals(ttype) || "1".equals(ttype)
							|| "2".equals(ttype)) {// 0�ַ�1����2��ֵ
						if ("INFO_T_FAMILY".equals(tphyname)) { // ��ͥ�ֶ�
							jname = "fmqv[]";
						} else if ("INFO_T_MEMBER".equals(tphyname)) { // ��Ա�ֶ�{
							jname = "mmqv[]";
						} else {
							jname = "qv[]";
						}
						if (texp.equals("��ֵ") || texp.equals("����")) {
							shtmltb.append("<input name='"
									+ jname
									+ "' id = '"
									+ tid
									+ "' type='text' disabled = disabled value = '"
									+ texp + "' style = 'width:100%;'></input>");
						} else {
							shtmltb.append("<input name='"
									+ jname
									+ "' id = '"
									+ tid
									+ "' type='text' style = 'width:100%;'></input>");
						}
					} else if ("3".equals(ttype)) {// ����
						if ("INFO_T_FAMILY".equals(tphyname)) { // ��ͥ�ֶ�
							jname = "fmqv[]";
						} else if ("INFO_T_MEMBER".equals(tphyname)) { // ��Ա�ֶ�{
							jname = "mmqv[]";
						} else {
							jname = "qv[]";
						}
						if (texp.equals("��ֵ") || texp.equals("����")) {
							shtmltb.append("<input name='"
									+ jname
									+ "' id = '"
									+ tid
									+ "' type='text' disabled = disabled value = '"
									+ texp
									+ "' style = 'width:100%;' onFocus=\"setday(this)\"></input>");
						} else {
							shtmltb.append("<input name='"
									+ jname
									+ "' id = '"
									+ tid
									+ "' type='text' style = 'width:100%;' onFocus=\"setday(this)\"></input>");
						}
					} else if ("4".equals(ttype)) {// �ֵ�
						if ("INFO_T_FAMILY".equals(tphyname)) { // ��ͥ�ֶ�
							jname = "fmqv[]";
						} else if ("INFO_T_MEMBER".equals(tphyname)) { // ��Ա�ֶ�{
							jname = "mmqv[]";
						} else {
							jname = "qv[]";
						}
						shtmltb.append(getDiscHtml(jname, tid, tdisid));
					} else if (constantdefine.FIELDTYPE_DEPT.equals(ttype)) {// ����(���³�������)
						jname = "dv[]";
						if (texp.equals("��ֵ") || texp.equals("����")) {
							shtmltb.append("<input name='"
									+ jname
									+ "' id = '"
									+ tid
									+ "' type='text' disabled = disabled value = '"
									+ texp
									+ "' style = 'width:100%;' onclick=\"queryDept(this)\"></input>");
						} else {
							shtmltb.append("<input name='"
									+ jname
									+ "' id = '"
									+ tid
									+ "' type='text' style = 'width:100%;' onclick=\"queryDept(this)\"></input>");
						}
						// ����ѡ�л���ID
						shtmltb.append("<input id = 'd"
								+ tid
								+ "' type='text' style = display:none;'></input>");
					}

					shtmltb.append("</td>");
					shtmltb.append("<td>");
					stvid = "t" + tid;
					shtmltb.append("<input id = '" + stvid
							+ "' type='text' value = \"" + tname
							+ "\" style = display:none;'></input>");
					sfvid = "f" + tid;
					shtmltb.append("<input id = '" + sfvid
							+ "' type='text' value = \"" + fvalue
							+ "\" style = display:none;'></input>");
					sqvid = "v" + tid;
					shtmltb.append("<input id = '" + sqvid
							+ "' type='text' value = \"" + qvalue
							+ "\" style = display:none;'></input>");
					shtmltb.append("</td>");
					shtmltb.append("</tr>");
					// ====================��ѯ����===============
					// ======================�����ֶ�=============
					osid = tphyname + "." + fphyname;
					osname = fname;
					if ("INFO_T_FAMILY".equals(tphyname)) { // ��ͥ�ֶ�
						//
						String temp = shtmlfm.toString();
						int iend = temp.indexOf(osid);// �Ƿ����
						if (iend < 0) {
							shtmlfm.append("<option value='" + osid + "'>"
									+ osname + "</option>");
						}
					} else if ("INFO_T_MEMBER".equals(tphyname)) { // ��Ա�ֶ�{
						String temp = shtmlmm.toString();
						int iend = temp.indexOf(osid);// �Ƿ����
						if (iend < 0) {
							shtmlmm.append("<option value='" + osid + "'>"
									+ osname + "</option>");
						}
					}
					// ======================�����ֶ�=============
				}
			}
		
		//
		shtmltb.append("</table>");
		// ==========================��ѯ����ѡ���===============================
		shtml.append("<fieldset>");
		shtml.append("<legend>��ѯ����ѡ��</legend>");
		shtml.append(shtmltb);
		shtml.append("</fieldset>");

		// =================================����=====================
		shtmlfm.append("</select>");
		shtmlmm.append("</select>");
		//
		shtml.append("<fieldset>");
		shtml.append("<legend>��ѯ����ѡ��</legend>");
		shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' style = 'font-size: 12px;'>");
		shtml.append("<tr>");
		shtml.append("<td width='50%' align='center'>");
		shtml.append(shtmlfm);
		shtml.append(shtmlmm);
		shtml.append("</td>");
		shtml.append("<td align='center'>");
		shtml.append("<input type='radio' name='rdo' id='rdoup' checked='checked' onclick=''></input>");
		shtml.append("<span style='color: #6BA6FF;'>����</span>");
		shtml.append("<input type='radio' name='rdo' id='rdodown' onclick=''></input>");
		shtml.append("<span style='color: #6BA6FF;'>����</span>");
		shtml.append("</td>");
		shtml.append("</tr>");
		shtml.append("</fieldset>");
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs);
			DBUtils.close(pstmt);
			log.debug("shtml111");
			ConnectionManager.closeQuietly();
			log.debug("shtml222");
		}
		log.debug(shtml);
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
	StringBuffer getDiscHtml(String selectname, String selectid, String discid) {
		StringBuffer shtml = new StringBuffer("");
		String sid = "", sname = "", sfullname = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		// [��]
		String replacechr = constantdefine.REPLACEEXP_CHR;
		// [��ѡ]
		String stname = constantdefine.NOTSELECT_NAME;

		shtml.append("<select name = '" + selectname + "' id=\"" + selectid
				+ "\" style = \"width:100%;font-size:12px\">");
		shtml.append("<option value=\"\">" + stname + "</option>");
		//
		String sql = "select dictionary_id,item from sys_t_dictionary "
				+ "where dictsort_id = '" + discid + "' "
				+ "and status = '1' order by sequence"; // ����SQL���
		log.debug(sql);
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
			// �ر�����
		}
		shtml.append("</select>");
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * ��ȡ���û���ѯ����
	 * 
	 * @param empid
	 * @return
	 */
	public String getQueryExp(String empid) {
		String srep = "";

		String sql = "select iniquery_id,code from sys_t_iniquery "
				+ "where status = '1' and employee_id = '" + empid + "'";
		log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("iniquery_id");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			// �ر�����
		}
		return srep;
	}
}
