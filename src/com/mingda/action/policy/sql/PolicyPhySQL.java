package com.mingda.action.policy.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;

public class PolicyPhySQL {
	static Logger log = Logger.getLogger(PolicyPhySQL.class);
	// ��������
	ConstantDefine constantdefine = new ConstantDefine();

	public HashMap getCheckPolicyPhySql(HashMap hashmap ,Connection conn) {
		HashMap rephashmap = new HashMap();
		// ���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();

		//
		/** ͨ�õ�SQL�������� **/
		String jmode = hashmap.get("jmode").toString();
		String jselect = hashmap.get("jselect").toString();
		String jfrom = hashmap.get("jfrom").toString();
		String jwhere = hashmap.get("jwhere").toString();
		String jbegpage = hashmap.get("jbegpage").toString();
		String jendpage = hashmap.get("jendpage").toString();
		//
		String jorder = hashmap.get("jorder").toString();
		/** ����Ĵ������ **/
		String jdeptid = hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jtabid = hashmap.get("jtabid").toString();
		//
		String jsaltype = hashmap.get("jsaltype").toString();
		String jtabquery = hashmap.get("jtabquery").toString();
		String jtabidea = hashmap.get("jtabidea").toString();
		String jtabbakidea = hashmap.get("jtabbakidea").toString();
		//
		String jexemode = hashmap.get("jexemode").toString();
		//   
		/** �������� **/
		// �߼�����
		String sql = "", groupby = "", stemp = "", sxmlth = "";
		String tftable, tmtable, tfield, tfamily, tmember;
		String id = "", familyid = "", mastername = "", ensurecount = "", consultincome = "", deptid = "", deptname = "";
		// ��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";// ��ͥ��
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		// ��Ա��Ϣ
		tmtable = "INFO_T_MEMBER";
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);

		tfield = "FAMILY_ID";// ��ͥID
		id = tfamily + "."
				+ tableinfoquery.getfieldlocicfromphysic(tftable, tfield);

		tfield = "FAMILYNO";// ��ͥ���
		familyid = tfamily + "."
				+ tableinfoquery.getfieldlocicfromphysic(tftable, tfield);

		tfield = "MASTERNAME";// ��������
		mastername = tfamily + "."
				+ tableinfoquery.getfieldlocicfromphysic(tftable, tfield);

		tfield = "ENSURECOUNT";// �����˿�
		ensurecount = tfamily + "."
				+ tableinfoquery.getfieldlocicfromphysic(tftable, tfield);

		tfield = "CONSULTINCOME";// ��������
		consultincome = tfamily + "."
				+ tableinfoquery.getfieldlocicfromphysic(tftable, tfield);

		tfield = "ORGANIZATION_ID";// ��ͥ����ID
		deptid = tfamily + "."
				+ tableinfoquery.getfieldlocicfromphysic(tftable, tfield);

		deptname = "SYS_T_ORGANIZATION.FULLNAME";// ��ͥ����
		//
		// ��ѯ�ֶ�
		// ��ͥID,��ͥ���,��������,�����˿�,��������
		String tempselect = id + "," + familyid + "," + mastername + ","
				+ ensurecount + "," + consultincome;
		// ��ͥ��̬�ֶ�
		if (null != jselect) {
			String[] selectname = jselect.split(",");
			String tempname = "";
			int ilength = selectname.length;
			for (int h = 0; h < ilength; h++) {
				tempname = selectname[h];
				//
				int iend = tempselect.indexOf(tempname);// �Ƿ����
				if (iend < 0) {
					tempselect = tempselect + "," + tempname;
				}
			}
		}
		// =================================================================================================Ĭ�ϲ�ѯBEG
		// ��ѯ�ֶ�=======================
		jselect = tempselect;
		//
		// ��ѯ��=========================
		if (null == jfrom || jfrom.equals("")) { // ======�����ظ��ڴ�������SQL���ʱ�ᴦ�����
			jfrom = tfamily + "," + "SYS_T_ORGANIZATION";
		} else {
			jfrom = jfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
		}
		// ��ѯ����======================
		// ��ѯ����
		if (null == jwhere || jwhere.equals("")) {

		} else {
			jwhere += "  and ";
		}
		// ��������ѯ
		jwhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";// ��������ѯ��ϵ
		//
		String temp1 = jwhere.substring(0, jwhere.length());
		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY",
				"ORGANIZATION_ID");// ��֯�����߼�����
		int iend = temp1.indexOf(temp2);// �Ƿ������֯����
		if (iend < 0) {
			jwhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '" + jdeptid
					+ "%'";
		}

		// =================================================================================================Ĭ�ϲ�ѯEND
		//
		String smoneyflag = "BIZ_T_OPTCHECK.MONEYFLAG"; // ����״̬
		//
		String scheckflow = constantdefine.POLICYQUERYCODE_5; // Ĭ������
		String scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG5"; // Ĭ������
		String sbakcheckflag = ""; // Ĭ��
		//
		if (jdepth.equals(constantdefine.POLICYQUERYCODE_5)) { // ����
			scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG1";
			scheckflow = constantdefine.POLICYQUERYCODE_5;
		} else if (jdepth.equals(constantdefine.POLICYQUERYCODE_4)) {// �ֵ�
			scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG2";
			scheckflow = constantdefine.POLICYQUERYCODE_4;
			sbakcheckflag = "BIZ_T_OPTCHECK.CHECKFLAG1";
		} else if (jdepth.equals(constantdefine.POLICYQUERYCODE_3)) {// ����
			scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG3";
			scheckflow = constantdefine.POLICYQUERYCODE_3;
			sbakcheckflag = "BIZ_T_OPTCHECK.CHECKFLAG2";
		} else if (jdepth.equals(constantdefine.POLICYQUERYCODE_2)) {// �о�
			scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG4";
			scheckflow = constantdefine.POLICYQUERYCODE_2;
			sbakcheckflag = "BIZ_T_OPTCHECK.CHECKFLAG3";
		} else if (jdepth.equals(constantdefine.POLICYQUERYCODE_1)) {// ʡ��
			scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG5";
			scheckflow = constantdefine.POLICYQUERYCODE_1;
		}
		//
		String sischang = "ISCHANGE"; // ��Ϣ�仯��ʶ
		String ssaltype = "SALTYPE"; // ��������
		if ("export".equals(jexemode)) {
			ssaltype = "SYS_T_DICTIONARY.ITEM"; // ���������ֵ�
			jfrom += ",SYS_T_DICTIONARY ";
			jwhere += " and INFO_T_FAMILY.SALTYPE = SYS_T_DICTIONARY.DICTIONARY_ID ";
		}

		// =================================================================================================ҵ���ѯBEG
		// XMLͷ����======================
		sxmlth = jselect;
		sxmlth += ",��ͥ��.��������";
		sxmlth += ",������.���ھ�����";
		sxmlth += ",������.�ⷢ������";
		sxmlth += ",������.����״̬";
		sxmlth += ",������.������";
		sxmlth += ",������.����";
		sxmlth += ",������.����ID";
		sxmlth += ",��ͥ��.��Ϣ�仯��ʶ";
		//
		// ��ѯ�ֶ�=====================================
		jselect += "," + ssaltype; // ��������
		jselect += ",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY";
		jselect += ",SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
		jselect += "," + smoneyflag; // ����״̬
		jselect += "," + scheckflag; // �����������
		jselect += "," + deptname; // ����name
		jselect += "," + deptid; // ����id
		jselect += "," + sischang; // ��Ϣ�仯��ʶ
		// ��ѯ��========================================
		jfrom += ",BIZ_T_OPTCHECK ";
		// ��ѯ����======================================
		jwhere += " and INFO_T_FAMILY.FAMILY_ID = BIZ_T_OPTCHECK.FAMILY_ID and BIZ_T_OPTCHECK.POLICY_ID = '"
				+ jpid + "' ";
		//

		// MONEYFLAG 1����2˳��3����4����5����6��ͣ7�����
		// MONEYFLAG �������:1δ����2ͬ�����3��������4 5 6 7 8ȡ������9��ͬ�����
		// ==================================tab========================================
		if (jtabid.equals("0")) { // ��ѯ��ǩȫ������

		} else if (jtabid.equals("1")) {// ��ѯ��ǩ��������
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG = '"
					+ constantdefine.POLICYCHECKMONEYCODE_NEW + "' " + "and "
					+ scheckflag + " = '" + constantdefine.POLICYCHECKCODE_NEW
					+ "' ";
			jwhere += " and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		} else if (jtabid.equals("2")) {// ��ѯ��ǩ��������
			jwhere += " and " + "(BIZ_T_OPTCHECK.MONEYFLAG = '"
					+ constantdefine.POLICYCHECKMONEYCODE_TOP + "' "
					+ "or BIZ_T_OPTCHECK.MONEYFLAG = '"
					+ constantdefine.POLICYCHECKMONEYCODE_BOM + "'"
					+ "or BIZ_T_OPTCHECK.MONEYFLAG = '"
					+ constantdefine.POLICYCHECKMONEYCODE_NOT + "' "
					+ "or BIZ_T_OPTCHECK.MONEYFLAG = '"
					+ constantdefine.POLICYCHECKMONEYCODE_OLE + "') ";
			jwhere += "and " + scheckflag + " = '"
					+ constantdefine.POLICYCHECKCODE_NEW + "' ";
			jwhere += "and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		} else if (jtabid.equals("3")) {// ��ѯ��ǩ��������
			jwhere += " and " + scheckflag + " = '"
					+ constantdefine.POLICYCHECKCODE_RENEW + "' ";
			jwhere += " and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		} else if (jtabid.equals("4")) {// ��ѯ��ǩ��������
			jwhere += " and " + scheckflag + " <> '"
					+ constantdefine.POLICYCHECKCODE_NEW + "' ";
		}
		// ================================tab==========================================
		// ================================tabitems=====================================
		// ���˲�ѯ
		// ��������
		if (!"0".equals(jsaltype)) {
			jwhere += " and INFO_T_FAMILY.SALTYPE = '" + jsaltype + "' ";
		}
		// ����״̬
		if (!"0".equals(jtabquery)) {// 0ȫ��;1����;2����;3˳��;4����;7�����;
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG = '" + jtabquery + "' ";
		}
		// �������
		if (!"0".equals(jtabidea)) {// 0ȫ��;1δ����;2ͬ�����;3��ͬ�����;8��������;9ȡ������
			jwhere += " and " + scheckflag + " = '" + jtabidea + "' ";
		}
		// �¼��������
		if (!"0".equals(jtabbakidea)) {// 0ȫ��;1δ����;2ͬ�����;3��ͬ�����;8��������;9ȡ������
			if (!"".equals(sbakcheckflag)) {
				jwhere += " and " + sbakcheckflag + " = '" + jtabbakidea + "' ";
			}
		}
		// ================================tabitems=====================================
		// �����ֶ�=======================
		groupby = tempselect;
		groupby += "," + ssaltype;
		// groupby += ",BIZ_T_OPTCHECK.RECHECKMONEY";
		// groupby += ", BIZ_T_OPTCHECK.COUNTMONEY";
		groupby += "," + smoneyflag;
		groupby += "," + scheckflag;
		groupby += "," + deptname;
		groupby += "," + deptid;
		groupby += "," + sischang;
		// =================================================================================================ҵ���ѯEND
		// �ۺ�SQL��ҳ���
		log.debug("test count 4");
		sql = tableinfophysql.getphysql(jselect, jfrom, jwhere, jmode,
				jbegpage, jendpage, groupby, jorder,conn).toString();
		// �ۺ�SQL����ҳ���
		String countsql = tableinfophysql.getphysql(jselect, jfrom, jwhere,
				jmode, "0", "0", groupby, jorder,conn).toString();
		// ������Ϣ===================
		HashMap hmcount = getCheckCountFromSQL(countsql);
		String sqlcount = hmcount.get("sqlcount").toString();
		String sumpopcount = hmcount.get("sumpopcount").toString();
		String summoney = hmcount.get("summoney").toString();
		String sumolemoney = hmcount.get("sumolemoney").toString();
		String sumnewmoney = hmcount.get("sumnewmoney").toString();
		//
		// log.debug("sql:" + sql);
		// log.debug("countsql:" + countsql);
		// ����ֵ=====================
		rephashmap.put("sql", sql.toString());
		rephashmap.put("countsql", countsql.toString());
		rephashmap.put("xmlth", sxmlth.toString());
		//
		rephashmap.put("sqlcount", sqlcount.toString());
		rephashmap.put("sumpopcount", sumpopcount.toString());
		rephashmap.put("summoney", summoney.toString());
		rephashmap.put("sumolemoney", sumolemoney.toString());
		rephashmap.put("sumnewmoney", sumnewmoney.toString());
		//
		return rephashmap;
	}
	
	/**
	 * ��ȡ������Ϣ��Ŀ
	 * @param sql
	 * @return
	 */
	public HashMap getCheckCountFromSQL(String sql){
		HashMap hashmap = new HashMap();
    	String sptype = "",stemp = "";
    	boolean brow = false;
    	String sqlcount = "0",sumpopcount ="0",summoney = "0",sumolemoney = "0",sumnewmoney = "0";
    	//��ӱ����˿ڡ��������롢���ھ������ⷢ������
    	stemp = "count(*) ca,sum(ENSURECOUNT) cb,sum(CONSULTINCOME) cc,sum(RECHECKMONEY) cd,sum(COUNTMONEY) ce";
    	sql = "select "+stemp+" from (" +sql + ")";   //����SQL��� 
    	//log.debug("sql:"+sql);
    	//Log4jApp.logger("sql:"+sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
		    //���ò���
		    rs = pstmt.executeQuery();
		    while (rs.next()) {	 
		    	
		    	stemp = rs.getString(1);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sqlcount", stemp);
		    	//
		    	stemp = rs.getString(2);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumpopcount", stemp);
		    	//
		    	stemp = rs.getString(3);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("summoney", stemp);
		    	//
		    	stemp = rs.getString(4);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumolemoney", stemp);
		    	//
		    	stemp = rs.getString(5);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumnewmoney", stemp);
		    	//
		    	brow = true;
		    }
		} catch (SQLException e) {
		    Log4jApp.logger(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		//�޼�¼����
    	if(!brow){
    		hashmap.put("sqlcount", 0);
    		hashmap.put("sumpopcount", 0);
    		hashmap.put("summoney", 0);
    		hashmap.put("sumolemoney", 0);
    		hashmap.put("sumnewmoney", 0);
    	}
    	return hashmap;    	
    }
	/**
	 * ��ȡ������Ϣ��Ŀ
	 * @param sql
	 * @return
	 */
	public HashMap getIdeaCountFromSQL(String sql){
		HashMap hashmap = new HashMap();
    	String stemp = "";
    	boolean brow = false;
    	String sqlcount = "0",sumolemoney = "0",sumnewmoney = "0";
    	//��ӱ����˿ڡ��������롢���ھ������ⷢ������
    	stemp = "count(*) ca,sum(recheckmoney) cd,sum(checkmoney) ce";
    	sql = "select "+stemp+" from (" +sql + ")";   //����SQL��� 
    	//log.debug("sql:"+sql);
    	//Log4jApp.logger("sql:"+sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
		    //���ò���
		    rs = pstmt.executeQuery();
		    while (rs.next()) {	 
		    	
		    	stemp = rs.getString(1);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sqlcount", stemp);
		    	//
		    	stemp = rs.getString(2);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumolemoney", stemp);
		    	//
		    	stemp = rs.getString(3);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumnewmoney", stemp);
		    	//
		    	brow = true;
		    }
		} catch (SQLException e) {
		    Log4jApp.logger(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		//�޼�¼����
    	if(!brow){
    		hashmap.put("sqlcount", 0);
    		hashmap.put("sumolemoney", 0);
    		hashmap.put("sumnewmoney", 0);
    	}
    	return hashmap;    	
    }
}
