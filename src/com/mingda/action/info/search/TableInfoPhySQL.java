package com.mingda.action.info.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.myjdbc.ConnectionManager;

/**
 * ����Ϣ���� 1���߼���������������ת�� 2��Ĭ�Ϲ��ɲ�ѯ��ʾ��� 3����Ϣ������� 4����Ϣ��ҵ���ѯ������Ϲ��� 5���������ʽ��������
 * 
 * @author xiu
 * 
 */
public class TableInfoPhySQL {
	static Logger log = Logger.getLogger(TableInfoPhySQL1.class);

	// �����ѯ��䲿��
	private String physelect = "";// ��������ѡ���[select]����
	private String phyfrom = "";// ��������ѡ���[from]����
	private String phywhererep = "";// ����������ϵ[where]ǿ��ϵ����
	//
	private String phypartid = "";// ѭ���ĸ��������ֶα��
	private String phychildid = "";// ѭ�����ӱ�����ֶα��
	private String phychildexp = "";// ѭ�����ӱ�������ʽ
	private String phypartexp = "";// ѭ���ĸ��ڵ��������ʽ

	//
	// ======================================������Ϣ�����BEG=================================
	/**
	 * ȡ������SQL���
	 * 
	 * @param tselect
	 * @param tfrom
	 * @param twhere
	 * @param tmode
	 *            [0��ʾ��ѯ���ؼ�¼��][1��ʾ����sql���]
	 * @param tbegpage
	 * @param tendpage
	 * @param conn
	 * @param tgroupmode
	 *            [0]������[1]���鰴��ѯ�������
	 * @param tordermode
	 *            [0]������[1]����FAMILYNO��ͥ���
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer getphysql(String tselect, String tfrom, String twhere,
			String tmode, String tbegpage, String tendpage, String tgroupmode,
			String tordermode, Connection conn) {
		log.debug(tselect + "," + tfrom + "," + twhere + "," + tmode + ","
				+ tbegpage + "," + tendpage + "," + tgroupmode + ","
				+ tordermode + "," + tordermode);
		StringBuffer shtml = new StringBuffer("");
		String ssql = "";
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		String tname = "";
		String locigselect = tselect;
		String locigfrom = tfrom;
		String locigwhere = twhere;
		String locigmode = tmode; // ��ѯ����ȡ��SQL���[�ڳ�������]
		String locigbegpage = tbegpage;
		String locigendpage = tendpage;
		String lociggroupmode = tgroupmode;
		String locigordermode = tordermode;
		//
		// ��ʼ��
		setPhyselect("");
		setPhyfrom("");
		setPhywhererep("");

		// ������ת�������ȥ�����ҿո�
		locigwhere = locigwhere.replace("' ", "'");
		locigwhere = locigwhere.replace(" '", "'");

		// ��ӡ����
		// log.debug("xiu�߼�������ʼSQL���:" + locigselect + " from " + locigfrom +
		// " where "+ locigwhere);

		// �޲�ѯ�ֶ�
		if (locigselect == null || locigselect.equals("")) {
			shtml.append("-1");
			return shtml;
		}
		// �޲�ѯ��
		if (locigfrom == null || locigfrom.equals("")) {
			shtml.append("-1");
			return shtml;
		}
		// ���߼��������ϵ
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = ilength - 1; r >= 0; r--) {
			tname = atname[r].trim();
			// log.debug("ahahahha:"+tname);
			// �Ƿ��Ѿ������Ҵ����ϵ��
			boolean exitsflag = false;
			int iexits = getPhyfrom().indexOf(tname);// ����λ��
			if (iexits >= 0) {
				exitsflag = true;
			}
			// ���Ѿ�����
			if (!exitsflag) {
				//
				boolean existtable = false; // ����ڱ�ʶ
				//
				String sql = "select table_id,super_table_id,logicname,depth "
						+ "from sys_t_table where status = '1' and logicname = '"
						+ tname + "'"; // ����SQL���

				log.debug(sql);

				// Connection conn = null; //����Connection����
				PreparedStatement pstmt = null; // ����PreparedStatement����
				ResultSet rs = null; // ����ResultSet����
				try {
					 conn = ConnectionManager.getConnection(); //��ȡ���ݿ�����
					pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
					// ���ò���
					rs = pstmt.executeQuery();

					while (rs.next()) {
						String tid = rs.getString("table_id");
						String parentid = rs.getString("super_table_id");
						String logicname = rs.getString("logicname");
						String depth = rs.getString("depth");
						//
						setPhypartid(parentid);// ����ѭ��
						setPhychildid(tid);// ����ѭ��

						int i = Integer.parseInt(depth);
						// ���ڵ�
						if (i == 0) {
							// ����
							if (getPhyfrom().equals("") || getPhyfrom() == null) {
								setPhyfrom(logicname);
							} else {
								int ibeg = getPhyfrom().indexOf(logicname);// ��һ�γ���λ��
								if (ibeg < 0) {
									setPhyfrom(getPhyfrom() + "," + logicname);
								}
							}
						} else {
							// �и��ڵ�
							for (int j = i; j > 0; j--) {
								if (i == j) {
									// ����
									if (getPhyfrom().equals("")
											|| getPhyfrom() == null) {
										setPhyfrom(logicname);
									} else {
										int ibeg = getPhyfrom().indexOf(
												logicname);// ��һ�γ���λ��
										if (ibeg < 0) {
											setPhyfrom(getPhyfrom() + ","
													+ logicname);
										}
									}
								}
								// ȡ�ø��ӹ�ϵ
								setpartchildrepfromid(getPhypartid(),
										getPhychildid());
							}
						}
						// ����ڱ�ʶ
						existtable = true;
					}
					// ����ڱ�ʶ
					if (!existtable) {// �����������������[�ִ�ı��߼����Ƹı����϶�����]
						// �������
						if (getPhyfrom().equals("") || getPhyfrom() == null) {
							setPhyfrom(tname);
						} else {
							int ibeg = getPhyfrom().indexOf(tname);// ��һ�γ���λ��
							if (ibeg < 0) {
								setPhyfrom(getPhyfrom() + "," + tname);
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					ConnectionManager.closeQuietly(rs); // �رս����
					ConnectionManager.closeQuietly(pstmt); // �ر�PreparedStatement
					// ConnectionManager.closeQuietly(); //�ر�����
				}
			}
		}
		//
		// 1����ѯ����ֶ�=============================================================
		setPhyselect(locigselect);
		// 2����ѯ��==================================================================
		if (getPhyfrom().length() <= 0) {
			setPhyfrom(locigfrom);
		}
		// 3����ѯ����================================================================
		if (locigwhere.length() > 0) {
			if (getPhywhererep().length() == 0) {
				setPhywhererep(locigwhere);
			} else {
				setPhywhererep(getPhywhererep() + " and " + locigwhere);
			}
		}

		// ��ӡ����
		// log.debug("xiu�߼���������SQL���:select " + getPhyselect() + " from " +
		// getPhyfrom() + " where " + getPhywhererep());

		// �����ֵ�ֵ[�С�11]������'11'
		String[] atstr = getPhywhererep().split("]");
		int itlen = atstr.length;
		if (itlen >= 1) {
			for (int i = 0; i < itlen; i++) {
				setPhywhererep(replaceDisc(getPhywhererep()));
			}
		}
		// log.debug(getPhywhererep());
		//
		//
		// ת����ѯ�ֶκ�����
		replacelogicexp(getPhyselect(), getPhyfrom(), getPhywhererep());
		// ת����ѯ�������
		lociggroupmode = replacelogicgroupby(getPhyfrom(), lociggroupmode);
		// ת����ѯ��
		replacelogicfrom(getPhyfrom());
		//

		// �����ظ��������ѯ�ֶκͲ�ѯ��
		String snewselect = "", snewsflag = "F";
		int itochar = getPhyselect().indexOf("to_char");// ���ڵ�ת���ַ�ʱ����λ��
		if (itochar < 0) {
			String[] asselect = getPhyselect().split(",");
			int itslen = asselect.length;
			for (int x = 0; x < itslen; x++) {
				String sselect = asselect[x].trim();
				// ��ֹ���ֶ�������ͬ����INFO_T_FAMILY.ID,INFO_T_FAMILY.ID2
				// ��ͬ����ΪINFO_T_FAMILY.ID
				String[] anewselect = snewselect.split(",");
				int inewslen = anewselect.length;
				// ��ʼ��
				snewsflag = "F";
				for (int p = 0; p < inewslen; p++) {
					String stselect = anewselect[p].trim();
					if (stselect.equals(sselect)) {
						snewsflag = "T";
					}
				}
				// ��������ͬ
				if (snewsflag.equals("F")) {
					if (snewselect.equals("")) {
						snewselect = sselect;
					} else {
						snewselect += "," + sselect;
					}
				}
			}
			// �²�ѯ�ֶ�
			setPhyselect(snewselect);
		}
		//
		String snewfrom = "", snewtflag = "F";
		String[] asfrom = getPhyfrom().split(",");
		int itflen = asfrom.length;
		for (int y = 0; y < itflen; y++) {
			String sfrom = asfrom[y].trim();
			// ��ֹ��������ͬ����INFO_T_FAMILYCLASS,INFO_T_FAMILY
			// ��ͬ����ΪINFO_T_FAMILY
			String[] anewfrom = snewfrom.split(",");
			int inewtlen = anewfrom.length;
			// ��ʼ��
			snewtflag = "F";
			for (int j = 0; j < inewtlen; j++) {
				String stfrom = anewfrom[j].trim();
				if (stfrom.equals(sfrom)) {
					snewtflag = "T";
				}
			}
			// ��������ͬ
			if (snewtflag.equals("F")) {
				if (snewfrom.equals("")) {
					snewfrom = sfrom;
				} else {
					snewfrom += "," + sfrom;
				}
			}
		}
		// �²�ѯ��======================================================
		setPhyfrom(snewfrom);
		//

		// ���������Ƿ�Ϊ��
		if (getPhywhererep().equals("") || getPhywhererep() == null) {
			ssql = "select " + getPhyselect() + " from " + getPhyfrom();
		} else {
			ssql = "select " + getPhyselect() + " from " + getPhyfrom()
					+ " where " + getPhywhererep();
		}
		// ��ӡ����
		// log.debug("xiu����SQL���:"+ssql);
		//
		//
		/*
		 * ======================================================================
		 * ================
		 * =====================================Ĭ��SQL������========
		 * =============================
		 * ========================================
		 * ===============================================
		 */
		//
		// ========================����==================
		if (lociggroupmode.equals("1")) {// ��ѯ�ֶη���
			ssql += " group by " + getPhyselect();
		} else if (!lociggroupmode.equals("0")) {// �����ֶη���(�ų������ֶ�)
			ssql += " group by " + lociggroupmode;
		}
		// ========================����==================
		// ========================����==================
		if (locigordermode.equals("1")) { // ��ͥ�������[��ͥ���]
			ssql += " order by nlssort(FAMILYNO, 'NLS_SORT=SCHINESE_PINYIN_M') ";
		} else { // ��������[locigordermode]
			int ibeg = 0; // �����ֶγ���λ��
			int iabeg = locigordermode.indexOf(" asc"); // �����ֶγ���λ��
			int idbeg = locigordermode.indexOf(" desc"); // �����ֶγ���λ��
			if (iabeg > 0) {
				ibeg = iabeg;
			} else if (idbeg > 0) {
				ibeg = idbeg;
			}
			//
			if (ibeg > 0) { // ���趨����
				String ordername = locigordermode.substring(0, ibeg);
				String ordertype = locigordermode.substring(ibeg,
						locigordermode.length());
				ordername = ordername.trim();
				int itbeg = ordername.indexOf(".");// ��һ�γ���λ��
				if (itbeg >= 0) {
					String stname = ordername.substring(0, itbeg);
					String sfname = ordername.substring(itbeg + 1,
							ordername.length());
					String stemptn = tableinfoquery
							.gettableidfromphysic(stname);
					if (stemptn.equals("") || stemptn == null) {
						ssql += " order by " + locigordermode;
					} else {
						String sftype = tableinfoquery
								.getfieldtypefromphysicname(stname, sfname);
						if (sftype.equals("0")) {
							// �ַ�������
							ssql += " order by nlssort(" + ordername
									+ ", 'NLS_SORT=SCHINESE_PINYIN_M') "
									+ ordertype;
						} else {
							ssql += " order by " + ordername + " " + ordertype;
						}
					}
				} else {
					ssql += " order by " + locigordermode;
				}
			}
		}
		// ========================����==================
		// ========================��ҳ==================
		// ��ҳ��ѯ["0"]��["0"]
		if (!"0".equals(locigbegpage) || !"0".equals(locigendpage)) { // ��ѯ��ҳ
			// ��ҳ
			ssql = "select * from (select mytab.*, rownum row_num from ("
					+ ssql + ") mytab) where row_num between " + locigbegpage
					+ " and  " + locigendpage;
		}
		// ========================��ҳ==================
		//
		/*
		 * ======================================================================
		 * ================
		 * =====================================����SQL������========
		 * =============================
		 * ========================================
		 * ===============================================
		 */
		//
		// ======================================����sql���Ϸ���=========================
		String tmpcount = validationfromsql(ssql);
		if ("-1".equals(tmpcount)) { // �������
			// ����-1Ϊ��ʾ��ѯ��䲻�Ϸ�
			shtml.append("-1");
			log.debug("xiu��������SQL���:" + ssql);
			return shtml;
		} else if ("0".equals(tmpcount)) { // �޲�ѯ�����¼
			if (locigmode.equals(constantdefine.REPLACESQL_EXE)) { // ִ�в�ѯ����0Ϊ�޽��
				// ����0Ϊ��ʾ��ѯ���Ϸ�,���ǽ��Ϊ0����¼
				shtml.append("0");
				return shtml;
			}
		}
		// ======================================����sql���Ϸ���=========================
		if(ssql.indexOf("INFO_T_FAMILYCLASS.CLASSTYPE")>0){
			String bb =ssql.substring(0,ssql.lastIndexOf("where")+5)+" INFO_T_MEMBER.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and INFO_T_FAMILYCLASS.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and 1=1 and  "+ssql.substring(ssql.lastIndexOf("where")+5);
			ssql=bb;
		}
		shtml.append(ssql);
		// log.debug("xiu����SQL���:"+ssql);
		// ��ӡ����
		Log4jApp.logger("xiu����SQL���:" + ssql);
		log.debug("����" + ssql);

		// log.debug(shtml);
		return shtml;
	}

	/**
	 * �ɱ���ź��ӱ�� �������ĸ���͸����ϵ
	 * 
	 * @param id
	 */
	void setpartchildrepfromid(String parid, String childid) {
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		String sql = "select table_id,super_table_id,logicname,depth "
				+ "from sys_t_table where status = '1' and table_id = '"
				+ parid + "'"; // ����SQL���

		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String tid = rs.getString("table_id");
				String parentid = rs.getString("super_table_id");
				String logicname = rs.getString("logicname");

				setPhypartid(parentid);// ����ѭ��
				setPhychildid(tid);// ����ѭ��

				setPhychildexp(tableinfoquery.getfkexpvaluefromid(childid));// ����ѭ��
				setPhypartexp(tableinfoquery.getpkexpvaluefromid(parid));// ����ѭ��
				//

				// ����
				if (getPhyfrom().equals("") || getPhyfrom() == null) {
					setPhyfrom(logicname);
				} else {
					int ibeg = getPhyfrom().indexOf(logicname);// ��һ�γ���λ��
					if (ibeg < 0) {
						setPhyfrom(getPhyfrom() + "," + logicname);
					}
				}
				// ���ӹ�ϵ
				if (getPhywhererep().length() == 0) {
					if (getPhychildexp().length() > 0
							&& getPhypartexp().length() > 0) {
						setPhywhererep(getPhychildexp() + " = "
								+ getPhypartexp());
					}
				} else {
					if (getPhychildexp().length() > 0
							&& getPhypartexp().length() > 0) {
						setPhywhererep(getPhywhererep() + " and "
								+ getPhychildexp() + " = " + getPhypartexp());
					}
				}
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		// Log4jApp.logger(sql);
	}

	/**
	 * �����ֵ�ֵ
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String replaceDisc(String str) {
		String srep = "", stbegstr = "", stendstr = "", ststr = "";
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		String replacechr = constantdefine.REPLACEEXP_CHR;

		int ibeg = 0, iend = 0;
		// Log4jApp.logger("str:"+str);
		// �ֵ�[�С�11]
		ibeg = str.indexOf("[");// ��һ�γ���λ��
		iend = str.indexOf("]");// ��һ�γ���λ��
		if (ibeg == -1 || iend == -1) {
			return str;
		}
		// ������[�С�11]
		ststr = str.substring(ibeg, iend + 1);
		// Log4jApp.logger("ststr:"+ststr);

		// ����ǰ���
		stbegstr = str.substring(0, ibeg);
		stendstr = str.substring(iend + 1, str.length());
		// Log4jApp.logger("stbegstr:"+stbegstr);
		// Log4jApp.logger("stendstr:"+stendstr);

		// ������'11'
		String[] atstr = ststr.split(replacechr);
		ststr = atstr[1];
		ststr = ststr.substring(0, ststr.length() - 1);
		ststr = "'" + ststr + "'";
		// Log4jApp.logger("ststr:"+ststr);

		// ������ϲ�
		srep = stbegstr + ststr + stendstr;
		// Log4jApp.logger("srep:"+srep);
		return srep;
	}

	/**
	 * ת�� �߼����� �߼���ѯ�ֶ� Ϊ�������� �����ѯ�ֶ�
	 * 
	 * @param logicselect
	 * @param locigfrom
	 * @param logicwhere
	 */
	public void replacelogicexp(String logicselect, String locigfrom,
			String logicwhere) {
		//
		String logicname = "", logicexp = "", phyexp = "";
		// ���߼��������ϵ
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = 0; r < ilength; r++) {
			logicname = atname[r].trim();

			// ת���ֶκ�����
			String sql = "select a.logicname || '.'|| b.logicname as logicexp,"
					+ "a.physicalname || '.'|| b.physicalname as phyexp  "
					+ "from sys_t_table a,sys_t_field b  "
					+ "where a.table_id = b.table_id and a.logicname = '"
					+ logicname + "'"; // ����SQL���
			// Log4jApp.logger(sql);
			Connection conn = null; // ����Connection����
			PreparedStatement pstmt = null; // ����PreparedStatement����
			ResultSet rs = null; // ����ResultSet����
			try {
				conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
				pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
				// ���ò���
				rs = pstmt.executeQuery();
				while (rs.next()) {
					logicexp = rs.getString("logicexp");
					phyexp = rs.getString("phyexp");

					logicselect = logicselect.replace(logicexp, phyexp);
					logicwhere = logicwhere.replace(logicexp, phyexp);
				}
			} catch (SQLException e) {
				Log4jApp.logger(e.toString());
			} finally {
				DBUtils.close(rs); // �رս����
				DBUtils.close(pstmt); // �ر�PreparedStatement
				DBUtils.close(conn); // �ر�����
			}
		}
		// ת��Ϊ�������
		setPhyselect(logicselect);
		// Log4jApp.logger("�����ѯ�ֶ�:"+ getPhyselect());
		setPhywhererep(logicwhere);
		// Log4jApp.logger("�����ѯ����:"+ getPhywhererep());
	}

	/**
	 * ת�� �߼��������
	 * 
	 * @param locigfrom
	 * @param logicgroupby
	 * @return
	 */
	public String replacelogicgroupby(String locigfrom, String logicgroupby) {
		//
		String srep = "";
		String logicname = "", logicexp = "", phyexp = "";
		// ���߼��������ϵ
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = 0; r < ilength; r++) {
			logicname = atname[r].trim();

			// ת���ֶκ�����
			String sql = "select a.logicname || '.'|| b.logicname as logicexp,"
					+ "a.physicalname || '.'|| b.physicalname as phyexp  "
					+ "from sys_t_table a,sys_t_field b  "
					+ "where a.table_id = b.table_id and a.logicname = '"
					+ logicname + "'"; // ����SQL���
			// Log4jApp.logger(sql);
			Connection conn = null; // ����Connection����
			PreparedStatement pstmt = null; // ����PreparedStatement����
			ResultSet rs = null; // ����ResultSet����
			try {
				conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
				pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
				// ���ò���
				rs = pstmt.executeQuery();
				while (rs.next()) {
					logicexp = rs.getString("logicexp");
					phyexp = rs.getString("phyexp");

					logicgroupby = logicgroupby.replace(logicexp, phyexp);
				}
			} catch (SQLException e) {
				Log4jApp.logger(e.toString());
			} finally {
				DBUtils.close(rs); // �رս����
				DBUtils.close(pstmt); // �ر�PreparedStatement
				DBUtils.close(conn); // �ر�����
			}
		}
		// ת��Ϊ�������
		srep = logicgroupby;
		// Log4jApp.logger("�����ѯ�������:"+ srep);
		return srep;
	}

	/**
	 * ת�� �߼���ѯ�� �����ѯ��
	 * 
	 * @param locigfrom
	 */
	public void replacelogicfrom(String locigfrom) {
		//
		String logicname = "", logicexp = "", phyexp = "";
		// ���߼��������ϵ
		String[] atname = locigfrom.split(",");
		int ilength = atname.length;

		for (int r = 0; r < ilength; r++) {
			logicname = atname[r].trim();
			// ת���ֶκ�����
			String sql = "select logicname ,physicalname  from sys_t_table where logicname = '"
					+ logicname + "'"; // ����SQL���

			Connection conn = null; // ����Connection����
			PreparedStatement pstmt = null; // ����PreparedStatement����
			ResultSet rs = null; // ����ResultSet����
			try {
				conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
				pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
				// ���ò���
				rs = pstmt.executeQuery();
				while (rs.next()) {
					logicexp = rs.getString("logicname");
					phyexp = rs.getString("physicalname");
					locigfrom = locigfrom.replace(logicexp, phyexp);
				}
			} catch (SQLException e) {
				Log4jApp.logger(e.toString());
			} finally {
				DBUtils.close(rs); // �رս����
				DBUtils.close(pstmt); // �ر�PreparedStatement
				DBUtils.close(conn); // �ر�����
			}
		}
		// ת��Ϊ�������
		setPhyfrom(locigfrom);
		// Log4jApp.logger("�����ѯ��:"+ getPhyfrom());
	}

	/**
	 * ��sql����ѯͳ���� ����У��sql������Ч��
	 * 
	 * @param ssql
	 * @return
	 */
	/*
	 * public String validationfromsql(String ssql) { String sresult = "0",sql =
	 * "";
	 * 
	 * sql = "select count(*) as testcount from ("+ ssql +")";
	 * //log.debug("xiuУ��SQL���:"+sql); //Log4jApp.logger(sql); Connection conn =
	 * null; //����Connection���� PreparedStatement pstmt = null;
	 * //����PreparedStatement���� ResultSet rs = null; //����ResultSet���� try { conn =
	 * DBUtils.getConnection(); //��ȡ���ݿ����� pstmt = conn.prepareStatement(sql);
	 * //����sql����PreparedStatement //���ò��� rs = pstmt.executeQuery(); while
	 * (rs.next()) { sresult = rs.getString("testcount"); } } catch
	 * (SQLException e) { sresult = "-1"; Log4jApp.logger(e.toString()); }
	 * finally { DBUtils.close(rs); //�رս���� DBUtils.close(pstmt);
	 * //�ر�PreparedStatement DBUtils.close(conn); //�ر����� } return sresult; }
	 */
	public String validationfromsql(String ssql) {
		log.debug("validationfromsql:" + ssql);
		return "0";
	}

	/**
	 * ��SQL����ȡ��¼����
	 * 
	 * @param tsql
	 * @return
	 */
	public String getcountfromsql(String tsql) {
		String srep = "";

		String sql = "select count(*) as count from (" + tsql + ")"; // ����SQL���
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ���ò���
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("count");
			}
		} catch (SQLException e) {
			Log4jApp.logger(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}

	//
	//
	private String getPhyselect() {
		return physelect;
	}

	private void setPhyselect(String physelect) {
		this.physelect = physelect;
	}

	private String getPhyfrom() {
		return phyfrom;
	}

	private void setPhyfrom(String phyfrom) {
		this.phyfrom = phyfrom;
	}

	private String getPhywhererep() {
		return phywhererep;
	}

	private void setPhywhererep(String phywhererep) {
		this.phywhererep = phywhererep;
	}

	private String getPhypartid() {
		return phypartid;
	}

	private void setPhypartid(String phypartid) {
		this.phypartid = phypartid;
	}

	private String getPhychildid() {
		return phychildid;
	}

	private void setPhychildid(String phychildid) {
		this.phychildid = phychildid;
	}

	private String getPhychildexp() {
		return phychildexp;
	}

	private void setPhychildexp(String phychildexp) {
		this.phychildexp = phychildexp;
	}

	private String getPhypartexp() {
		return phypartexp;
	}

	private void setPhypartexp(String phypartexp) {
		this.phypartexp = phypartexp;
	}
	
	public static void main(String[] args) throws Exception {
		String name = "select INFO_T_FAMILY.ENSURECOUNT, INFO_T_FAMILY.AVGINCOME, INFO_T_FAMILYCLASS.CLASSTYPE860 from INFO_T_MEMBER, INFO_T_FAMILY, INFO_T_FAMILYCLASS where INFO_T_FAMILY.FAMILY_ID ='6405579' group by INFO_T_FAMILY.ENSURECOUNT, INFO_T_FAMILY.AVGINCOME, INFO_T_FAMILYCLASS.CLASSTYPE860";
	System.out.println(name.indexOf("INFO_T_FAMILYCLASS.CLASSTYPE"));
	System.out.println(name.substring(0,name.lastIndexOf("where")+5)+" INFO_T_MEMBER.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and INFO_T_FAMILYCLASS.FAMILY_ID=INFO_T_FAMILY.FAMILY_ID and 1=1 and  "+name.substring(name.lastIndexOf("where")+5));
	}
}
