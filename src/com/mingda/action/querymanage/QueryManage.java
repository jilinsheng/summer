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
 * 查询管理 1、查询条件选项定制 2、查询条件选项排序 3、查询条件表达式生成 4、查询信息页面生成
 * 
 * @author xiu
 * 
 */
public class QueryManage extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(QueryManage.class);

	/**
	 * 由登录用户编号 查询模块编码 取得查询设置编码
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
	 * 取得条件查询选项设置页面
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
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		adminid = constantdefine.ADMIN_ID;

		// 查询选项表单
		shtml.append("<form id = \"queryinfoform\">");
		// 排序
		fhtml.append("<select id=\"forderitemquery\" style = \"width:100%;font-size:12px\">");
		mhtml.append("<select id=\"morderitemquery\" style = \"width:100%;font-size:12px\">");
		// 默认排序
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
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		mhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "MASTERNAME";// 户主姓名
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "MASTERPAPERID";// 户主身份证
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		tfield = "POPULATION";// 家庭人口
		osname = tableinfoquery.getfieldlocicfromphysic(tftable, tfield);
		osid = "INFO_T_FAMILY." + tfield;
		fhtml.append("<option value='" + osid + "'>" + osname + "</option>");
		//
		tfield = "MEMBERNAME";// 成员名称
		osname = tableinfoquery.getfieldlocicfromphysic(tmtable, tfield);
		osid = "INFO_T_MEMBER." + tfield;
		mhtml.append("<option value='" + osid + "'>" + osname + "</option>");

		// 查询条件选择表
		shtml.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		shtml.append("<tr class=\"queryform\" align=\"center\">");
		shtml.append("<td colspan = '3'>信息查询选项</td>");
		// shtml.append("<th>运算符</th>");
		// shtml.append("<th>条件值</th>");
		shtml.append("</tr>");
		// 系统默认设置
		sparid = getQueryExp(adminid);// -1为系统默认设置
		if (sparid.equals("")) {// 不存在信息查询设置
			shtml.append("");
		} else {// 存在信息查询设置
			// 信息查询选项
			shtml.append(getQueryInfoExpHtml(sparid));
		}
		// 是否有当前用户的设置
		sparid = getQueryExp(empid);
		if (sparid.equals("")) {// 无设置
			shtml.append("");
		} else {
			// 信息查询选项
			shtml.append(getQueryInfoExpHtml(sparid));
		}
		shtml.append("</table>");
		//
		//
		mhtml.append("</select>");
		fhtml.append("</select>");
		//
		bhtml.append("<select id=\"orderquery\" style = \"width:100%;font-size:12px\">");
		bhtml.append("<option value='asc'>升序</option>");
		bhtml.append("<option value='desc'>降序</option>");
		bhtml.append("</select>");
		//
		shtml.append("<fieldset>");
		shtml.append("<legend>结果排序</legend>");
		shtml.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		shtml.append("<tr align=\"center\">");
		shtml.append("<td id = \"fmtd\" width=\"50%\">" + fhtml + "</td>");// 家庭排序列
		shtml.append("<td id = \"mbtd\" style=\"display:none\" width=\"50%\">"
				+ mhtml + "</td>");// 成员排序列
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
	 * 信息条件 生成按设定好的信息条件查询页面
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

		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		String sql = "select iniqueryitem_id, iniquery_id, fieldnameloc, matchexp, logicexp, status "
				+ "from sys_t_iniqueryitem where status = '1' and  iniquery_id = '"
				+ sparid + "' order by sequence";
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {

			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 设置参数
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tid = rs.getString("iniqueryitem_id");
				tspan = rs.getString("fieldnameloc");
				texp = rs.getString("matchexp");
				tvalue = rs.getString("logicexp");

				//
				// 字段类型
				ibeg = tspan.indexOf(".");// .出现位置
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
				// 对象编号
				tvid = "value" + tid;
				ttid = "table" + tid;
				tfid = "field" + tid;
				// 排序字段
				osid = tphyname + "." + fphyname;
				osname = fname;
				if (tphyname.equals("INFO_T_FAMILY")) {// 家庭字段
					//
					String temp = fhtml.toString();
					int iend = temp.indexOf(osid);// 是否存在
					if (iend < 0) {
						fhtml.append("<option value='" + osid + "'>" + osname
								+ "</option>");
					}
				} else if (tphyname.equals("INFO_T_MEMBER")) {// 成员字段{
					String temp = mhtml.toString();
					int iend = temp.indexOf(osid);// 是否存在
					if (iend < 0) {
						mhtml.append("<option value='" + osid + "'>" + osname
								+ "</option>");
					}
				}
				//
				if ("".equals(ttype) || null == ttype || "".equals(tdid)) {// 字段解析错误[字段错误或者更名]
					// 可以删除
					delQuerySeq(tid, "");
				} else {
					shtml.append("<tr align=\"center\" title = '" + texp
							+ "' >");
					shtml.append("<td width=\"40%\" valign=\"middle\"><span class = 'itemstyle'>"
							+ fname + "</span></td>");
					//
					//
					// shtml.append("<td width=\"10%\"><img width = '10px' hight = '10px' src='/db/page/images/question.gif' alt=\""+texp+"\"></img></td>");
					if ("0".equals(ttype)) {// 字符
						// 对象编号
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						if (texp.equals("空值") || texp.equals("不空")) {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" disabled = disabled style = \"width:100%\" value = '"
									+ texp + "'></input></td>");
						} else {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" style = \"width:100%\"></input></td>");
						}

					} else if ("1".equals(ttype)) {// 整型
						// 对象编号
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						if (texp.equals("空值") || texp.equals("不空")) {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" disabled = disabled style = \"width:100%\" value = '"
									+ texp + "'></input></td>");
						} else {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" style = \"width:100%\"></input></td>");
						}
					} else if ("2".equals(ttype)) {// 数值
						// 对象编号
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						if (texp.equals("空值") || texp.equals("不空")) {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" disabled = disabled style = \"width:100%\" value = '"
									+ texp + "'></input></td>");
						} else {
							shtml.append("<td width=\"50%\"><input id=\""
									+ tiid
									+ "\" type=\"text\" style = \"width:100%\"></input></td>");
						}

					} else if ("3".equals(ttype)) {// 日期
						// 对象编号
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
					} else if ("4".equals(ttype)) {// 字典
						// 对象编号
						if (tphyname.equals("INFO_T_FAMILY")) {
							tiid = "qfmid" + tid;
						} else if (tphyname.equals("INFO_T_MEMBER")) {
							tiid = "qmmid" + tid;
						} else {
							tiid = "query" + tid;
						}
						shtml.append("<td width=\"50%\">"
								+ getDiscHtml(tiid, tdid) + "</td>");
					} else if (constantdefine.FIELDTYPE_DEPT.equals(ttype)) {// 机构(重新常量定义)
						// 对象编号
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
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}
		// log.debug(shtml);
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
	StringBuffer getDiscHtml(String selectid, String discid) {
		StringBuffer shtml = new StringBuffer("");
		String sid = "", sname = "", sfullname = "";
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		// [☆]
		String replacechr = constantdefine.REPLACEEXP_CHR;
		// [不选]
		String stname = constantdefine.NOTSELECT_NAME;

		shtml.append("<select id=\"" + selectid
				+ "\" style = \"width:100%;font-size:12px\">");
		shtml.append("<option value=\"\">" + stname + "</option>");
		//
		String sql = "select dictionary_id,item from sys_t_dictionary "
				+ "where dictsort_id = '" + discid
				+ "' and status = '1' order by sequence"; // 定义SQL语句
		// log.debug(sql);
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
			// DBUtils.close(conn); // 关闭连接
		}
		shtml.append("</select>");
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * 生成查询设置列表
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
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		adminid = constantdefine.ADMIN_ID;
		//
		shtml.append("<ul id=\"arrangableNodes\">");
		// 系统默认
		ssyshtml.append(getQueryExpSeqOne(adminid));
		shtml.append(ssyshtml);
		// 用户设置
		suserhtml.append(getQueryExpSeqOne(empid));
		shtml.append(suserhtml);
		//
		if (suserhtml.toString().equals("")) {
			shtml.append("<div align='center'><span class='caption'>[系统默认设置]</span></div>");
		} else {
			// 当前用户的设置
			String sparid = getQueryExp(empid);
			shtml.append("<div align='center'><span class='caption pointer' onclick=\"delAllQuerySeq('"
					+ sparid + "');\">[点击删除用户全部设置]</span></div>");
		}
		shtml.append("<div align='center'><input type='button' onclick='saveQuerySeq();' value='保存排序'></input></div>");
		shtml.append("<div align='center' class = 'itemstyle'>按左键上下拖拽排序</div>");
		shtml.append("</ul>");
		//
		shtml.append("<div id='movableNode'><ul></ul></div>");
		shtml.append("<div id='arrDestInditcator'><img src='/db/page/images/insert.gif'></img></div>");
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * 取得用户或者系统用户的查询条件设置
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
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		adminid = constantdefine.ADMIN_ID;
		if (empid.equals(adminid)) {// 系统设置
			sysini = true;
		}

		// 是否有当前用户的设置
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

					// div编号seq+id
					divid = "seq" + id;

					// 字段类型
					int ibeg = name.indexOf(".");// .出现位置
					if (ibeg > 0) {
						name = name.substring(ibeg + 1, name.length());
					}

					shtml.append("<li class='itemstyle licss' id=\"" + divid
							+ "\">" + "[" + seqid + "]" + name + " " + sexp
							+ "");

					if (!sysini) {
						shtml.append("<span class='caption pointer'><img height = '12' width = '12' src='/db/page/images/close.gif' alt= '删除用户设置' onclick=\"delQuerySeq('"
								+ id + "');\"/></span>");
						// shtml.append("<span class='caption pointer'  onclick=\"delQuerySeq('"+id+"');\">[点击删除]</span>");
					}
					shtml.append("</li>");
				}
			}

		} catch (RuntimeException e) {
			log.debug(e.toString());
		}
		if (!sysini) {
			// 无查询设置
			if (irow < 1) {
				// 删除全部
				delAllQuerySeq(sparid, empid);
			}
		}
		// log.debug(shtml);
		return shtml;
	}

	/**
	 * 保存更新新显示顺序
	 * 
	 * @param tnewseq
	 * @return
	 */
	public String saveQuerySeq(String tnewseq, String empid) {
		String shtml = "", seqid = "", qid = "";
		String[] seqs = tnewseq.split(","); // 将各节点信息切分成数组
		// log.debug(tnewseq);
		for (int i = 0; i < seqs.length; i++) {
			String[] tmp = seqs[i].split("_"); // 切分id和新顺序编号
			qid = tmp[0]; // 设置id参数
			// div编号seq+id
			qid = qid.substring(3, qid.length());
			seqid = tmp[1]; // 设置新顺序参数
			/*
			 * log.debug(qid); log.debug(seqid);
			 */

			String sql = "update sys_t_iniqueryitem set sequence = '" + seqid
					+ "' where iniqueryitem_id = '" + qid + "'";// 定义更新数据库的SQL语句
			Connection conn = null; // 声明Connection对象
			PreparedStatement pstmt = null; // 声明PreparedStatement对象
			try {
				conn = DBUtils.getConnection(); // 获取数据库连接
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
				pstmt.execute(); // 执行
				conn.commit();
				shtml = "保存新显示顺序操作成功!"; // 表示保存成功
			} catch (SQLException e) {
				shtml = "保存新显示顺序操作失败!"; // 出现保存异常
				log.debug(e.toString());
			} finally {
				DBUtils.close(pstmt); // 关闭PreparedStatement
				// DBUtils.close(conn); // 关闭连接
			}
		}
		// ==========================
		// 更新用户定制查询XML
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
	 * 信息 全部删除用户定制信息查询设置
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
					+ tparid + "'";// 定义更新数据库的SQL语句
			String sql2 = "delete sys_t_iniquery where iniquery_id = '"
					+ tparid + "'";// 定义更新数据库的SQL语句
			try {
				getSession().createSQLQuery(sql1).executeUpdate();
				getSession().createSQLQuery(sql2).executeUpdate();
				shtml = "全部删除用户查询设置操作成功!"; // 表示保存成功
			} catch (RuntimeException e) {
				shtml = "全部删除用户查询设置操作失败!"; // 出现保存异常
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
	 * 信息 删除用户信息查询设置
	 * 
	 * @param tid
	 * @return
	 */
	public String delQuerySeq(String tid, String empid) {
		String shtml = "";
		String sql = "delete sys_t_iniqueryitem where iniqueryitem_id = '"
				+ tid + "'";// 定义更新数据库的SQL语句
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			pstmt.execute(); // 执行
			conn.commit();
			shtml = "删除用户查询设置操作成功!"; // 表示保存成功
		} catch (SQLException e) {
			shtml = "删除用户查询设置操作失败!"; // 出现保存异常
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt); // 关闭PreparedStatement
			// DBUtils.close(conn); // 关闭连接
		}
		// ==========================
		// 更新用户定制查询XML
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
	 * 信息 添加用户设置信息查询条件
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
		// PreparedStatement pstmt = null; // 声明PreparedStatement对象
		try {
			// 是否有当前用户的设置
			sparid = getQueryExp(empid);
			// 生成表达式
			slogicexp = getQueryLogicExpFromMatchexp(info, exp, type);
			if (slogicexp.equals("")) {
				shtml = "添加用户查询设置操作失败!"; // 出现保存异常
				return shtml;
			}
			//
			if (sparid.equals("")) {// 无设置
				// 表查询处理类
				TableInfoQuery tableinfoquery = new TableInfoQuery();
				sqid = tableinfoquery.getseqfromname("XINIQUERY_ID");
				// 常量定义
				ConstantDefine constantdefine = new ConstantDefine();
				sql1 = "insert into sys_t_iniquery (iniquery_id,name,status,employee_id) "
						+ "values ('" + sqid + "','信息','1','" + empid + "')";// 定义更新数据库的SQL语句
				log.debug(sql1);
				sql2 = "insert into sys_t_iniqueryitem (iniqueryitem_id,iniquery_id,fieldnameloc,matchexp,logicexp,status,sequence) "
						+ "values (xiniqueryitem_id.nextval,'"
						+ sqid
						+ "','"
						+ info + "','" + exp + "','" + slogicexp + "','1','1')";// 定义更新数据库的SQL语句
				log.debug(sql2);
				getSession().createSQLQuery(sql1).executeUpdate();
				getSession().createSQLQuery(sql2).executeUpdate();
			} else {
				sql2 = "insert into sys_t_iniqueryitem (iniqueryitem_id,iniquery_id,fieldnameloc,matchexp,logicexp,status,sequence) "
						+ "values (xiniqueryitem_id.nextval,'"
						+ sparid
						+ "','"
						+ info + "','" + exp + "','" + slogicexp + "','1','1')";// 定义更新数据库的SQL语句
				log.debug(sql2);
				getSession().createSQLQuery(sql2).executeUpdate();
			}
			// ==========================
			// 更新用户定制查询XML
			QueryPageXml querypagexml = new QueryPageXml();
			querypagexml.SaveQuerySeqXml(empid);
			// ==========================
			shtml = "添加用户查询设置操作成功!"; // 表示保存成功
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			re.printStackTrace();
			shtml = "添加用户查询设置操作失败!"; // 出现保存异常
		} finally {
			session.close();
		}
		return shtml;
	}

	/**
	 * 信息 由字段和表达式构造条件设置表达式
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
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String replacechr = constantdefine.REPLACEEXP_CHR;
		// 单引号用两个单引号替换避免SQL语句插入出错
		// 日期类型
		if (type.equals(constantdefine.FIELDTYPE_CHR)) {
			// 字符
			if (exp.equals("左匹配")) {
				srep = info + " LIKE " + "''" + replacechr + "%''";
			} else if (exp.equals("右匹配")) {
				srep = info + " LIKE ''%" + replacechr + "''";
			} else if (exp.equals("中间匹配")) {
				srep = info + " LIKE ''%" + replacechr + "%''";
			}
			// 两个单引号代表单引号
			replacechr = "''" + replacechr + "''";
		} else if (type.equals(constantdefine.FIELDTYPE_DATE)) {
			// 日期
			// 两个单引号代表单引号
			replacechr = "to_date(''" + replacechr + "'',''yyyy-mm-dd'')";
			// 日期不能用匹配
			if (exp.equals("左匹配")) {
				srep = "";
			} else if (exp.equals("右匹配")) {
				srep = "";
			} else if (exp.equals("中间匹配")) {
				srep = "";
			}
		} else if (type.equals(constantdefine.FIELDTYPE_DEPT)) {
			// 机构
			if (exp.equals("左匹配")) {
				srep = info + " LIKE " + "''" + replacechr + "%''";
			} else if (exp.equals("右匹配")) {
				srep = info + " LIKE ''%" + replacechr + "''";
			} else if (exp.equals("中间匹配")) {
				srep = info + " LIKE ''%" + replacechr + "%''";
			}
			// 两个单引号代表单引号
			replacechr = "''" + replacechr + "''";
		} else if (type.equals(constantdefine.FIELDTYPE_INT)) {
			// 整型
			// 日期不能用匹配
			if (exp.equals("左匹配")) {
				srep = "";
			} else if (exp.equals("右匹配")) {
				srep = "";
			} else if (exp.equals("中间匹配")) {
				srep = "";
			}
			//
			replacechr = replacechr;
		} else if (type.equals(constantdefine.FIELDTYPE_NUM)) {
			// 数值
			// 日期不能用匹配
			if (exp.equals("左匹配")) {
				srep = "";
			} else if (exp.equals("右匹配")) {
				srep = "";
			} else if (exp.equals("中间匹配")) {
				srep = "";
			}
			replacechr = replacechr;
		} else {
			// 字典值不能用匹配
			if (exp.equals("左匹配")) {
				srep = "";
			} else if (exp.equals("右匹配")) {
				srep = "";
			} else if (exp.equals("中间匹配")) {
				srep = "";
			}
			// 字典不需要单引号,因为需要解析
			replacechr = "" + replacechr + "";
		}
		if (exp.equals("等于")) {
			srep = info + " = " + replacechr;
		} else if (exp.equals("大于")) {
			srep = info + " > " + replacechr;
		} else if (exp.equals("小于")) {
			srep = info + " < " + replacechr;
		} else if (exp.equals("不等于")) {
			srep = info + " <> " + replacechr;
		} else if (exp.equals("大于等于")) {
			srep = info + " >= " + replacechr;
		} else if (exp.equals("小于等于")) {
			srep = info + " <= " + replacechr;
		} else if (exp.equals("空值")) {
			srep = info + " is null";
		} else if (exp.equals("不空")) {
			srep = info + " is not null";
		}
		return srep;
	}

	/**
	 * 成生年下拉框选择
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
	 * 成生月下拉框选择
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
