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

		// Ԥ����ͬ������ύ����
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

		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		//
		Connection conn =null;
		try {
			conn =ConnectionManager.getConnection();
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// ��ȡaction
			String action = request.getParameter("action");
			// log.debug(action);
			// ȡ��ҵ��ѡ��������
			if ("getPolicyChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyChoice(sname, "0"));
			}
			// ȡ��ҵ��������׼ѡ��������
			else if ("getPolicyCheckChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyCheckChoice(sname));
			}
			// ȡ��ҵ����������ѡ��������
			else if ("getPolicyFlowChoice".equals(action)) {
				String sname = request.getParameter("sname");
				String pid = request.getParameter("pid");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyFlowChoice(sname,
						pid, "0"));
			}
			// ȡ��ҵ������ѡ��������
			else if ("getPolicyResultChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyResultChoice(sname,
						"0"));
			}
			// ȡ��ҵ������ѡ��������
			else if ("getPolicyFlagChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyFlagChoice(sname));
			}
			// ȡ��ҵ��������׼����ѡ��
			else if ("getPolicyAutoUlLi".equals(action)) {
				String sname = request.getParameter("sname");
				String smode = request.getParameter("smode");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery
						.getPolicyAutoUlLi(sname, smode));
			}
			// ȡ��ҵ��������׼ѡ��
			else if ("getPolicyCheckUlLi".equals(action)) {
				String sname = request.getParameter("sname");
				String smode = request.getParameter("smode");
				String sinfomode = request.getParameter("sinfomode");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyCheckUlLi(sname,
						smode, sinfomode));
			}
			// ȡ������ҵ��ѡ��������
			if ("getCheckPolicyOrder".equals(action)) {
				String sname = request.getParameter("sname");
				String empid = request.getParameter("empid");
				String pid = request.getParameter("pid");
				out.print(tableinfoquery.getCheckPolicyOrder(sname, empid, pid));
			}
			// ȡ������ҵ��ѡ��������
			if ("getCheckPolicyChoice".equals(action)) {
				String sname = request.getParameter("sname");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyChoice(sname, "1"));
			}
			// ȡ��ҵ������
			else if ("getPolicyName".equals(action)) {
				String pid = request.getParameter("pid");
				// ����ҵ���ѯ������
				PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
				out.print(policymanagecheckquery.getPolicyNameFromId(pid));
			}
			// ȡ��ҵ�����Ա��Html
			else if ("getPolicyItemsHtml".equals(action)) {
				String pid = request.getParameter("pid");
				// ����ҵ������
				QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
				out.print(querymanagepolicy.getPolicyItemsHtml(pid ,conn));
			}
			// ȡ��ҵ�����е��α�׼���Html
			else if ("getPolicyStandardsHtml".equals(action)) {
				String pid = request.getParameter("pid");
				// ����ҵ������
				QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
				out.print(querymanagepolicy.getPolicyStandardsHtml(pid));
			}
			// ȡ��ҵ�񵵴����л�����׼���Html
			else if ("getPolicyStandardDeptsHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// ����ҵ������
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
