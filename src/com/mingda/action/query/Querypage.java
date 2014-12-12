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
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		StringBuffer shtml = new StringBuffer("");
		StringBuffer shtmltb = new StringBuffer("");
		StringBuffer shtmlfm = new StringBuffer("");
		StringBuffer shtmlmm = new StringBuffer("");
		String tftable, tmtable, tfield, tfamily, tmember;
		String osname = "", osid = "";
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		//
		QueryManage querymanage = new QueryManage();
		//
		// 默认排序
		// =================================排序==================================
		shtmlfm.append("<select id='fmlist' style = 'width:100%;'>");
		shtmlmm.append("<select id='mmlist' style = 'width:100%;display:none;'>");
		// 家庭信息
		tftable = "INFO_T_FAMILY";// 家庭表
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		// 成员信息
		tmtable = "INFO_T_MEMBER";// 成员表
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		//
		tfield = "FAMILYNO";// 家庭编号
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "MASTERNAME";// 户主姓名
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		//
		tfield = "MEMBERNAME";// 成员名称
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "PAPERID";// 成员身份证
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "RELMASTER";// 与户主关系
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");
		//
		tfield = "MASTERPAPERID";// 户主身份证
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "POPULATION";// 家庭人口
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "SALMONEY";// 保障金额
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "DESSALTYPE";// 保障类型
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");

		tfield = "ORGANIZATION_ID";// 家庭机构ID
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		shtmlfm.append("<option value='" + osid + "'>" + osname + "</option>");
		shtmlmm.append("<option value='" + osid + "'>" + osname + "</option>");

		// =================================排序=================================
		// ==========================查询条件选择表===============================
		//
		String stmp = "", sparid = "0", stvid = "", sfvid = "", sqvid = "";
		String tid = "", fvalue = "", texp = "", qvalue = "";
		String tname = "", fname = "", tphyname = "", fphyname = "", ttype = "", tdisid = "";
		//
		shtmltb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' style = 'font-size: 12px;'>");
		// 是否有当前用户的设置
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

			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tid = rs.getString("iniqueryitem_id");
				fvalue = rs.getString("fieldnameloc");
				texp = rs.getString("matchexp");
				qvalue = rs.getString("logicexp");
				
				
				//
				// 字段类型
				int ibeg = fvalue.indexOf(".");// .出现位置
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
				// 字段匹配错误
				if ("".equals(ttype)) {
					// 可以删除
					log.debug(tname+","+ttype+","+fname);
					querymanage.delQuerySeq(tid, empid);
				} else {
					// ====================查询设置===============
					shtmltb.append("<tr align='center' title = '" + texp + "'>");
					shtmltb.append("<td width='50%'>");
					shtmltb.append("<span style='color: #6BA6FF;'>" + fname
							+ "</span>");
					shtmltb.append("</td>");
					shtmltb.append("<td>");
					//
					String jname = "";
					if ("0".equals(ttype) || "1".equals(ttype)
							|| "2".equals(ttype)) {// 0字符1整型2数值
						if ("INFO_T_FAMILY".equals(tphyname)) { // 家庭字段
							jname = "fmqv[]";
						} else if ("INFO_T_MEMBER".equals(tphyname)) { // 成员字段{
							jname = "mmqv[]";
						} else {
							jname = "qv[]";
						}
						if (texp.equals("空值") || texp.equals("不空")) {
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
					} else if ("3".equals(ttype)) {// 日期
						if ("INFO_T_FAMILY".equals(tphyname)) { // 家庭字段
							jname = "fmqv[]";
						} else if ("INFO_T_MEMBER".equals(tphyname)) { // 成员字段{
							jname = "mmqv[]";
						} else {
							jname = "qv[]";
						}
						if (texp.equals("空值") || texp.equals("不空")) {
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
					} else if ("4".equals(ttype)) {// 字典
						if ("INFO_T_FAMILY".equals(tphyname)) { // 家庭字段
							jname = "fmqv[]";
						} else if ("INFO_T_MEMBER".equals(tphyname)) { // 成员字段{
							jname = "mmqv[]";
						} else {
							jname = "qv[]";
						}
						shtmltb.append(getDiscHtml(jname, tid, tdisid));
					} else if (constantdefine.FIELDTYPE_DEPT.equals(ttype)) {// 机构(重新常量定义)
						jname = "dv[]";
						if (texp.equals("空值") || texp.equals("不空")) {
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
						// 隐藏选中机构ID
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
					// ====================查询设置===============
					// ======================排序字段=============
					osid = tphyname + "." + fphyname;
					osname = fname;
					if ("INFO_T_FAMILY".equals(tphyname)) { // 家庭字段
						//
						String temp = shtmlfm.toString();
						int iend = temp.indexOf(osid);// 是否存在
						if (iend < 0) {
							shtmlfm.append("<option value='" + osid + "'>"
									+ osname + "</option>");
						}
					} else if ("INFO_T_MEMBER".equals(tphyname)) { // 成员字段{
						String temp = shtmlmm.toString();
						int iend = temp.indexOf(osid);// 是否存在
						if (iend < 0) {
							shtmlmm.append("<option value='" + osid + "'>"
									+ osname + "</option>");
						}
					}
					// ======================排序字段=============
				}
			}
		
		//
		shtmltb.append("</table>");
		// ==========================查询条件选择表===============================
		shtml.append("<fieldset>");
		shtml.append("<legend>查询条件选项</legend>");
		shtml.append(shtmltb);
		shtml.append("</fieldset>");

		// =================================排序=====================
		shtmlfm.append("</select>");
		shtmlmm.append("</select>");
		//
		shtml.append("<fieldset>");
		shtml.append("<legend>查询排序选项</legend>");
		shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' style = 'font-size: 12px;'>");
		shtml.append("<tr>");
		shtml.append("<td width='50%' align='center'>");
		shtml.append(shtmlfm);
		shtml.append(shtmlmm);
		shtml.append("</td>");
		shtml.append("<td align='center'>");
		shtml.append("<input type='radio' name='rdo' id='rdoup' checked='checked' onclick=''></input>");
		shtml.append("<span style='color: #6BA6FF;'>升序</span>");
		shtml.append("<input type='radio' name='rdo' id='rdodown' onclick=''></input>");
		shtml.append("<span style='color: #6BA6FF;'>降序</span>");
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
	 * 取得字典选项下拉框
	 * 
	 * @param selectid
	 * @param discid
	 * @return
	 */
	@SuppressWarnings("static-access")
	StringBuffer getDiscHtml(String selectname, String selectid, String discid) {
		StringBuffer shtml = new StringBuffer("");
		String sid = "", sname = "", sfullname = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		// [☆]
		String replacechr = constantdefine.REPLACEEXP_CHR;
		// [不选]
		String stname = constantdefine.NOTSELECT_NAME;

		shtml.append("<select name = '" + selectname + "' id=\"" + selectid
				+ "\" style = \"width:100%;font-size:12px\">");
		shtml.append("<option value=\"\">" + stname + "</option>");
		//
		String sql = "select dictionary_id,item from sys_t_dictionary "
				+ "where dictsort_id = '" + discid + "' "
				+ "and status = '1' order by sequence"; // 定义SQL语句
		log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
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
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// 关闭连接
		}
		shtml.append("</select>");
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * 获取该用户查询设置
	 * 
	 * @param empid
	 * @return
	 */
	public String getQueryExp(String empid) {
		String srep = "";

		String sql = "select iniquery_id,code from sys_t_iniquery "
				+ "where status = '1' and employee_id = '" + empid + "'";
		log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				srep = rs.getString("iniquery_id");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// 关闭连接
		}
		return srep;
	}
}
