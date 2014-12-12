package com.mingda.action.policy.query;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.querymanage.QueryManagePolicy;
import com.mingda.common.myjdbc.ConnectionManager;

public class PolicyQueryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public PolicyQueryServlet() {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		//
		Connection conn =null;
		try {
			conn =ConnectionManager.getConnection();
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// 获取action
			String action = request.getParameter("action");
			// log.debug(action);
			// 取得业务选择下拉框
			if ("getPolicyChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyChoice(sname, "0"));
			}
			// 取得业务审批标准选择下拉框
			else if ("getPolicyCheckChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyCheckChoice(sname));
			}
			// 取得业务审批进度选择下拉框
			else if ("getPolicyFlowChoice".equals(action)) {
				String sname = request.getParameter("sname");
				String pid = request.getParameter("pid");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyFlowChoice(sname,
						pid, "0"));
			}
			// 取得业务终审选择下拉框
			else if ("getPolicyResultChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyResultChoice(sname,
						"0"));
			}
			// 取得业务所属选择下拉框
			else if ("getPolicyFlagChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyFlagChoice(sname));
			}
			// 取得业务审批标准名单选项
			else if ("getPolicyAutoUlLi".equals(action)) {
				String sname = request.getParameter("sname");
				String smode = request.getParameter("smode");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery
						.getPolicyAutoUlLi(sname, smode));
			}
			// 取得业务审批标准选项
			else if ("getPolicyCheckUlLi".equals(action)) {
				String sname = request.getParameter("sname");
				String smode = request.getParameter("smode");
				String sinfomode = request.getParameter("sinfomode");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyCheckUlLi(sname,
						smode, sinfomode));
			}
			// 取得审批业务选择下拉框
			if ("getCheckPolicyOrder".equals(action)) {
				String sname = request.getParameter("sname");
				String empid = request.getParameter("empid");
				String pid = request.getParameter("pid");
				out.print(tableinfoquery.getCheckPolicyOrder(sname, empid, pid));
			}
			// 取得审批业务选择下拉框
			if ("getCheckPolicyChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyChoice(sname, "1"));
			}
			// 取得业务名称
			else if ("getPolicyName".equals(action)) {
				String pid = request.getParameter("pid");
				// 政策业务查询处理类
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyNameFromId(pid));
			}
			// 取得业务属性表格Html
			else if ("getPolicyItemsHtml".equals(action)) {
				String pid = request.getParameter("pid");
				// 政策业务处理类
				QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
				out.print(querymanagepolicy.getPolicyItemsHtml(pid ,conn));
			}
			// 取得业务所有档次标准表格Html
			else if ("getPolicyStandardsHtml".equals(action)) {
				String pid = request.getParameter("pid");
				// 政策业务处理类
				QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
				out.print(querymanagepolicy.getPolicyStandardsHtml(pid));
			}
			// 取得业务档次所有机构标准表格Html
			else if ("getPolicyStandardDeptsHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// 政策业务处理类
				QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
				out.print(querymanagepolicy.getPolicyStandardDeptsHtml(sid));
			}
		} catch (Exception e) {
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
