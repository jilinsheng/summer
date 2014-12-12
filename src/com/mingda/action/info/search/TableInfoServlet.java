package com.mingda.action.info.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mingda.action.info.classmanage.InfoClassManage;
import com.mingda.action.policy.export.Exportmanage;
import com.mingda.action.querymanage.DictionaryManage;
import com.mingda.action.querymanage.QueryManageCheck;
import com.mingda.action.querymanage.QueryManageCheckAuto;
import com.mingda.action.querymanage.QueryManageChild;
import com.mingda.action.querymanage.QueryManageInfo;
import com.mingda.action.querymanage.QueryManageInterView;
import com.mingda.action.querymanage.QueryManagePolicy;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

@SuppressWarnings("serial")
public class TableInfoServlet extends HttpServlet {
	static Logger log = Logger.getLogger(TableInfoServlet.class);
	/**
	 * Constructor of the object.
	 */
	public TableInfoServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 预防不同浏览器提交混淆
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 字典查询处理类
		DictionaryManage dictionarymanage = new DictionaryManage();
		// 信息分类处理类
		InfoClassManage infoclassmanage = new InfoClassManage();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		//
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 获取action
		String action = request.getParameter("action");
		// Log4jApp.logger(action);
		/*
		 * try {
		 * 
		 * conn.setAutoCommit(false);
		 */
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();

			if ("testphysql".equals(action)) {
				String sql = request.getParameter("sql");
				out.print(tableinfoquery.validationfromphysql(sql, conn));
			}
			// 由表描述取得 所有父节点表描述和关系
			else if ("getphysql".equals(action)) {
				// mode调用模块
				String mode = request.getParameter("mode");
				String tselect = request.getParameter("tselect");
				String tfrom = request.getParameter("tfrom");
				String twhere = request.getParameter("twhere");
				String tmode = request.getParameter("tmode");
				String tbegpage = request.getParameter("tbegpage");
				String tendpage = request.getParameter("tendpage");
				// Log4jApp.logger(mode);

				//
				HashMap hashmap = new HashMap();
				hashmap.put("tmode", tmode);
				hashmap.put("tselect", tselect);
				hashmap.put("tfrom", tfrom);
				hashmap.put("twhere", twhere);
				hashmap.put("tbegpage", tbegpage);
				hashmap.put("tendpage", tendpage);

				//
				//
				if ("getsql".equals(mode)) {// 取得SQL语句查询(返回错误结果或者正确的SQL语句)
					log.debug("test count 1");
					out.print(tableinfophysql.getphysql(tselect, tfrom, twhere,
							tmode, tbegpage, tendpage, "0", "0", conn));
				} else if ("getsqlpolicystandard".equals(mode)) {
					// 政策制定档次业务所属
					String ssuperpolicy = request.getParameter("ssuperpolicy");
					String snotpolicy = request.getParameter("snotpolicy");
					String ttype = request.getParameter("ttype");
					hashmap.put("ssuperpolicy", ssuperpolicy);
					hashmap.put("snotpolicy", snotpolicy);
					hashmap.put("ttype", ttype);
					// 业务SQL语句处理类
					QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
					out.print(querymanagepolicy.GetSqlPolicyStandard(hashmap,
							conn));

				} else if ("getsqlpolicydeptacc".equals(mode)) {
					// 政策制定机构标准
					String selwhere = request.getParameter("selwhere");
					String ttype = request.getParameter("ttype");
					hashmap.put("selwhere", selwhere);
					hashmap.put("ttype", ttype);
					// 业务SQL语句处理类
					QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
					out.print(querymanagepolicy.GetSqlPolicyAcc(hashmap));

				} else if ("getsqlpolicychild".equals(mode)) {
					// 分类施保标准
					String ttype = request.getParameter("ttype");
					hashmap.put("ttype", ttype);
					// 业务SQL语句处理类
					QueryManageChild querymanagechild = new QueryManageChild();
					out.print(querymanagechild.GetSqlPolicyChild(hashmap));

				} else if ("interviewfamilysql".equals(mode)) {// 执行信息查询[家庭查询]
					// 走访记录查询
					// 家庭表信息
					String sql = "", xmlth = "";
					String tdept = request.getParameter("tdept");// 登录机构
					String torder = request.getParameter("torder");// 排序
					String ttabid = request.getParameter("ttabid");// 选中查询标签ID
					String tbegdt = request.getParameter("tbegdt");// 开始日期
					String tenddt = request.getParameter("tenddt");// 结束日期

					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("ttabid", ttabid);
					hashmap.put("tbegdt", tbegdt);
					hashmap.put("tenddt", tenddt);
					// 表SQL语句处理类
					QueryManageInterView querymanageinterview = new QueryManageInterView();
					out.print(querymanageinterview.InterViewFamilySql(hashmap));
					//
				} else if ("exefamilysql".equals(mode)) {// 执行信息查询[家庭查询]
					// 家庭查询
					String tdept = request.getParameter("tdept"); // 登录机构
					String torder = request.getParameter("torder");// 排序
					//
					String tempid = request.getParameter("tempid"); // 用户编号
					String tpolicy = request.getParameter("tpolicy"); // 选择业务
					String tpolicysta = request.getParameter("tpolicysta"); // 选择家庭业务状态
					//
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					hashmap.put("tpolicysta", tpolicysta);
					// 表SQL语句处理类
					QueryManageInfo querymanageinfo = new QueryManageInfo();
					out.print(querymanageinfo.GetExeFamilySql(hashmap));
					//
					//
				} else if ("exemembersql".equals(mode)) {// 执行信息查询[成员查询]
					// 成员查询
					String tdept = request.getParameter("tdept"); // 登录机构
					String torder = request.getParameter("torder");// 排序
					//
					String tempid = request.getParameter("tempid"); // 用户编号
					String tpolicy = request.getParameter("tpolicy"); // 选择业务
					String tpolicysta = request.getParameter("tpolicysta"); // 选择家庭业务状态
					//
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					hashmap.put("tpolicysta", tpolicysta);
					// 表SQL语句处理类
					QueryManageInfo querymanageinfo = new QueryManageInfo();
					out.print(querymanageinfo.GetExeMemberSql(hashmap));
					//
					//
				} else if ("exefamilychecksql".equals(mode)) {// 执行业务审批查询[家庭查询]
					// 带机构业务审批家庭查询
					String torder = request.getParameter("torder");// 排序
					//
					String tdept = request.getParameter("tdept"); // 登录机构
					String tempid = request.getParameter("tempid"); // 用户编号
					String tpolicy = request.getParameter("tpolicy"); // 选择业务
																		// 跟机构无关
					String tautotabid = request.getParameter("tautotabid"); // 查询选项
					String tchecktabid = request.getParameter("tchecktabid"); // 查询选项
					String tchoiceflag = request.getParameter("tchoiceflag"); // 查询全部还是页全部(全部时取SQL语句页全部时生成XML)
					//
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					hashmap.put("tautotabid", tautotabid);
					hashmap.put("tchecktabid", tchecktabid);
					hashmap.put("tchoiceflag", tchoiceflag);
					// 是否导出处理
					if (tchoiceflag.equals("export")) {
						HttpSession session = request.getSession();
						// 表SQL语句处理类
						QueryManageCheck querynanagecheck = new QueryManageCheck();
						String xmls = querynanagecheck.GetExeCheckFamilySql(
								hashmap).toString();
						// 导出表格处理类
						Exportmanage ExportManage = new Exportmanage();
						out.print(ExportManage.SetSessionForExport(session,
								xmls));
					} else {
						// 表SQL语句处理类
						QueryManageCheck querynanagecheck = new QueryManageCheck();
						out.print(querynanagecheck
								.GetExeCheckFamilySql(hashmap));
					}
					//
				} else if ("getAutoCheckFamilySql".equals(mode)) {// 政策业务自动筛选条件家庭或者成员
					// 带机构业务审批家庭查询
					String torder = request.getParameter("torder");// 排序
					//
					String tdept = request.getParameter("tdept"); // 登录机构
					String tempid = request.getParameter("tempid"); // 用户编号
					String tpolicy = request.getParameter("tpolicy"); // 选择业务
																		// 跟机构无关
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					// 政策业务自动筛选条件处理类
					QueryManageCheckAuto querymanagecheckauto = new QueryManageCheckAuto();
					out.print(querymanagecheckauto
							.GetAutoCheckFamilySql(hashmap));

				}
			}
			// 取得运算符下拉框
			else if ("getexpselect".equals(action)) {
				String tfieldtype = request.getParameter("fieldtype");
				out.print(tableinfoquery.getexpselect(tfieldtype));
			}
			// 取得分类列表框
			else if ("getClasssHtml".equals(action)) {
				String mode = request.getParameter("mode");
				out.print(infoclassmanage.getClasssHtml(mode));
			}
			// 取得分类属性
			else if ("getClassItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				out.print(infoclassmanage.getClassItemHtml(sid));
			}
			// 添加分类
			else if ("updateClass".equals(action)) {
				String mode = request.getParameter("mode");
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				String classType = request.getParameter("classType");
				String className = request.getParameter("className");
				String classDesc = request.getParameter("classDesc");
				// 更新所属机构表
				if ("addclass".equals(mode)) {
					out.print(tableinfoquery.addCalssType(classType, className,
							classDesc));
				} else if ("editclass".equals(mode)) {
					out.print(tableinfoquery.updateCalss(classId, classFId,
							className, classDesc));
				}

			}
			// 获取分类标准SQL属性
			else if ("getClassSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				out.print(tableinfoquery.getClassSqlItem(sid, mode));
			}
			// 更新分类标准SQL属性
			else if ("updateClassSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				out.print(tableinfoquery.updateClassSql(sid, sphysql, slocsql));
			}

			// 取得分类列表框
			else if ("getCalssListsUl".equals(action)) {
				String classtype = request.getParameter("classtype");
				out.print(tableinfoquery.getCalssListsUl(classtype));
			}
			// 取得分类属性
			else if ("getCalssItem".equals(action)) {
				String classId = request.getParameter("classId");
				out.print(tableinfoquery.getCalssItem(classId));
			}
			// 添加分类
			else if ("addClassType".equals(action)) {
				String classType = request.getParameter("classType");
				String className = request.getParameter("className");
				String classDesc = request.getParameter("classDesc");
				out.print(tableinfoquery.addCalssType(classType, className,
						classDesc));

			}
			// 停用分类
			else if ("delClassType".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				out.print(tableinfoquery.delCalssType(classId, classFId));
			}
			// 启用分类
			else if ("reeditClass".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				out.print(tableinfoquery.reeditClass(classId, classFId));
			}
			// 更新分类
			else if ("updateCalssTypeName".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				String className = request.getParameter("className");
				out.print(tableinfoquery.updateCalssTypeName(classId, classFId,
						className));
			}
			// 更新分类描述
			else if ("updateCalssTypeDesc".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				String classDesc = request.getParameter("classDesc");
				out.print(tableinfoquery.updateCalssTypeDesc(classId, classFId,
						classDesc));
			}
			// 更新分类条件
			else if ("updateCalssTypeSql".equals(action)) {
				String classId = request.getParameter("classId");
				String classLogicSql = request.getParameter("classLogicSql");
				String classPhySql = request.getParameter("classPhySql");
				out.print(tableinfoquery.updateCalssTypeSql(classId,
						classLogicSql, classPhySql));
			}
			// 取得字典属性列表
			else if ("getDiscsHtml".equals(action)) {
				String discid = request.getParameter("discid");
				out.print(dictionarymanage.getDiscsHtml(discid));
			}
			// 取得字典属性值
			else if ("getDiscItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				out.print(dictionarymanage.getDiscItemHtml(sid));
			}
			// 更新字典属性值
			else if ("updateDisc".equals(action)) {
				String EditType = request.getParameter("EditType");
				String did = request.getParameter("did");
				String dname = request.getParameter("dname");
				String pdid = request.getParameter("pdid");
				out.print(dictionarymanage.updateDisc(EditType, did, dname,
						pdid));
			}
			// 更新字典属性状态
			else if ("updateDiscStatus".equals(action)) {
				String did = request.getParameter("did");
				String status = request.getParameter("status");
				out.print(dictionarymanage.updateDiscStatus(did, status));
			}

			// 添加字典值
			else if ("adddiscionary".equals(action)) {
				String sname = request.getParameter("sname");
				String sdiscid = request.getParameter("sdiscid");
				out.print(dictionarymanage.addDiscionary(sname, sdiscid));
			}
			/*
			 * } catch (SQLException e) { e.printStackTrace(); try {
			 * conn.rollback(); } catch (SQLException e1) {
			 * e1.printStackTrace(); } }finally{
			 * ConnectionManager.closeQuietly(); }
			 */
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeQuietly();
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
