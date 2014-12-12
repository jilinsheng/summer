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
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// �ֵ��ѯ������
		DictionaryManage dictionarymanage = new DictionaryManage();
		// ��Ϣ���ദ����
		InfoClassManage infoclassmanage = new InfoClassManage();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();
		//
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡaction
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
			// �ɱ�����ȡ�� ���и��ڵ�������͹�ϵ
			else if ("getphysql".equals(action)) {
				// mode����ģ��
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
				if ("getsql".equals(mode)) {// ȡ��SQL����ѯ(���ش�����������ȷ��SQL���)
					log.debug("test count 1");
					out.print(tableinfophysql.getphysql(tselect, tfrom, twhere,
							tmode, tbegpage, tendpage, "0", "0", conn));
				} else if ("getsqlpolicystandard".equals(mode)) {
					// �����ƶ�����ҵ������
					String ssuperpolicy = request.getParameter("ssuperpolicy");
					String snotpolicy = request.getParameter("snotpolicy");
					String ttype = request.getParameter("ttype");
					hashmap.put("ssuperpolicy", ssuperpolicy);
					hashmap.put("snotpolicy", snotpolicy);
					hashmap.put("ttype", ttype);
					// ҵ��SQL��䴦����
					QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
					out.print(querymanagepolicy.GetSqlPolicyStandard(hashmap,
							conn));

				} else if ("getsqlpolicydeptacc".equals(mode)) {
					// �����ƶ�������׼
					String selwhere = request.getParameter("selwhere");
					String ttype = request.getParameter("ttype");
					hashmap.put("selwhere", selwhere);
					hashmap.put("ttype", ttype);
					// ҵ��SQL��䴦����
					QueryManagePolicy querymanagepolicy = new QueryManagePolicy();
					out.print(querymanagepolicy.GetSqlPolicyAcc(hashmap));

				} else if ("getsqlpolicychild".equals(mode)) {
					// ����ʩ����׼
					String ttype = request.getParameter("ttype");
					hashmap.put("ttype", ttype);
					// ҵ��SQL��䴦����
					QueryManageChild querymanagechild = new QueryManageChild();
					out.print(querymanagechild.GetSqlPolicyChild(hashmap));

				} else if ("interviewfamilysql".equals(mode)) {// ִ����Ϣ��ѯ[��ͥ��ѯ]
					// �߷ü�¼��ѯ
					// ��ͥ����Ϣ
					String sql = "", xmlth = "";
					String tdept = request.getParameter("tdept");// ��¼����
					String torder = request.getParameter("torder");// ����
					String ttabid = request.getParameter("ttabid");// ѡ�в�ѯ��ǩID
					String tbegdt = request.getParameter("tbegdt");// ��ʼ����
					String tenddt = request.getParameter("tenddt");// ��������

					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("ttabid", ttabid);
					hashmap.put("tbegdt", tbegdt);
					hashmap.put("tenddt", tenddt);
					// ��SQL��䴦����
					QueryManageInterView querymanageinterview = new QueryManageInterView();
					out.print(querymanageinterview.InterViewFamilySql(hashmap));
					//
				} else if ("exefamilysql".equals(mode)) {// ִ����Ϣ��ѯ[��ͥ��ѯ]
					// ��ͥ��ѯ
					String tdept = request.getParameter("tdept"); // ��¼����
					String torder = request.getParameter("torder");// ����
					//
					String tempid = request.getParameter("tempid"); // �û����
					String tpolicy = request.getParameter("tpolicy"); // ѡ��ҵ��
					String tpolicysta = request.getParameter("tpolicysta"); // ѡ���ͥҵ��״̬
					//
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					hashmap.put("tpolicysta", tpolicysta);
					// ��SQL��䴦����
					QueryManageInfo querymanageinfo = new QueryManageInfo();
					out.print(querymanageinfo.GetExeFamilySql(hashmap));
					//
					//
				} else if ("exemembersql".equals(mode)) {// ִ����Ϣ��ѯ[��Ա��ѯ]
					// ��Ա��ѯ
					String tdept = request.getParameter("tdept"); // ��¼����
					String torder = request.getParameter("torder");// ����
					//
					String tempid = request.getParameter("tempid"); // �û����
					String tpolicy = request.getParameter("tpolicy"); // ѡ��ҵ��
					String tpolicysta = request.getParameter("tpolicysta"); // ѡ���ͥҵ��״̬
					//
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					hashmap.put("tpolicysta", tpolicysta);
					// ��SQL��䴦����
					QueryManageInfo querymanageinfo = new QueryManageInfo();
					out.print(querymanageinfo.GetExeMemberSql(hashmap));
					//
					//
				} else if ("exefamilychecksql".equals(mode)) {// ִ��ҵ��������ѯ[��ͥ��ѯ]
					// ������ҵ��������ͥ��ѯ
					String torder = request.getParameter("torder");// ����
					//
					String tdept = request.getParameter("tdept"); // ��¼����
					String tempid = request.getParameter("tempid"); // �û����
					String tpolicy = request.getParameter("tpolicy"); // ѡ��ҵ��
																		// �������޹�
					String tautotabid = request.getParameter("tautotabid"); // ��ѯѡ��
					String tchecktabid = request.getParameter("tchecktabid"); // ��ѯѡ��
					String tchoiceflag = request.getParameter("tchoiceflag"); // ��ѯȫ������ҳȫ��(ȫ��ʱȡSQL���ҳȫ��ʱ����XML)
					//
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					hashmap.put("tautotabid", tautotabid);
					hashmap.put("tchecktabid", tchecktabid);
					hashmap.put("tchoiceflag", tchoiceflag);
					// �Ƿ񵼳�����
					if (tchoiceflag.equals("export")) {
						HttpSession session = request.getSession();
						// ��SQL��䴦����
						QueryManageCheck querynanagecheck = new QueryManageCheck();
						String xmls = querynanagecheck.GetExeCheckFamilySql(
								hashmap).toString();
						// �����������
						Exportmanage ExportManage = new Exportmanage();
						out.print(ExportManage.SetSessionForExport(session,
								xmls));
					} else {
						// ��SQL��䴦����
						QueryManageCheck querynanagecheck = new QueryManageCheck();
						out.print(querynanagecheck
								.GetExeCheckFamilySql(hashmap));
					}
					//
				} else if ("getAutoCheckFamilySql".equals(mode)) {// ����ҵ���Զ�ɸѡ������ͥ���߳�Ա
					// ������ҵ��������ͥ��ѯ
					String torder = request.getParameter("torder");// ����
					//
					String tdept = request.getParameter("tdept"); // ��¼����
					String tempid = request.getParameter("tempid"); // �û����
					String tpolicy = request.getParameter("tpolicy"); // ѡ��ҵ��
																		// �������޹�
					hashmap.put("tdept", tdept);
					hashmap.put("torder", torder);
					hashmap.put("tempid", tempid);
					hashmap.put("tpolicy", tpolicy);
					// ����ҵ���Զ�ɸѡ����������
					QueryManageCheckAuto querymanagecheckauto = new QueryManageCheckAuto();
					out.print(querymanagecheckauto
							.GetAutoCheckFamilySql(hashmap));

				}
			}
			// ȡ�������������
			else if ("getexpselect".equals(action)) {
				String tfieldtype = request.getParameter("fieldtype");
				out.print(tableinfoquery.getexpselect(tfieldtype));
			}
			// ȡ�÷����б��
			else if ("getClasssHtml".equals(action)) {
				String mode = request.getParameter("mode");
				out.print(infoclassmanage.getClasssHtml(mode));
			}
			// ȡ�÷�������
			else if ("getClassItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				out.print(infoclassmanage.getClassItemHtml(sid));
			}
			// ��ӷ���
			else if ("updateClass".equals(action)) {
				String mode = request.getParameter("mode");
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				String classType = request.getParameter("classType");
				String className = request.getParameter("className");
				String classDesc = request.getParameter("classDesc");
				// ��������������
				if ("addclass".equals(mode)) {
					out.print(tableinfoquery.addCalssType(classType, className,
							classDesc));
				} else if ("editclass".equals(mode)) {
					out.print(tableinfoquery.updateCalss(classId, classFId,
							className, classDesc));
				}

			}
			// ��ȡ�����׼SQL����
			else if ("getClassSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				out.print(tableinfoquery.getClassSqlItem(sid, mode));
			}
			// ���·����׼SQL����
			else if ("updateClassSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				out.print(tableinfoquery.updateClassSql(sid, sphysql, slocsql));
			}

			// ȡ�÷����б��
			else if ("getCalssListsUl".equals(action)) {
				String classtype = request.getParameter("classtype");
				out.print(tableinfoquery.getCalssListsUl(classtype));
			}
			// ȡ�÷�������
			else if ("getCalssItem".equals(action)) {
				String classId = request.getParameter("classId");
				out.print(tableinfoquery.getCalssItem(classId));
			}
			// ��ӷ���
			else if ("addClassType".equals(action)) {
				String classType = request.getParameter("classType");
				String className = request.getParameter("className");
				String classDesc = request.getParameter("classDesc");
				out.print(tableinfoquery.addCalssType(classType, className,
						classDesc));

			}
			// ͣ�÷���
			else if ("delClassType".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				out.print(tableinfoquery.delCalssType(classId, classFId));
			}
			// ���÷���
			else if ("reeditClass".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				out.print(tableinfoquery.reeditClass(classId, classFId));
			}
			// ���·���
			else if ("updateCalssTypeName".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				String className = request.getParameter("className");
				out.print(tableinfoquery.updateCalssTypeName(classId, classFId,
						className));
			}
			// ���·�������
			else if ("updateCalssTypeDesc".equals(action)) {
				String classId = request.getParameter("classId");
				String classFId = request.getParameter("classFId");
				String classDesc = request.getParameter("classDesc");
				out.print(tableinfoquery.updateCalssTypeDesc(classId, classFId,
						classDesc));
			}
			// ���·�������
			else if ("updateCalssTypeSql".equals(action)) {
				String classId = request.getParameter("classId");
				String classLogicSql = request.getParameter("classLogicSql");
				String classPhySql = request.getParameter("classPhySql");
				out.print(tableinfoquery.updateCalssTypeSql(classId,
						classLogicSql, classPhySql));
			}
			// ȡ���ֵ������б�
			else if ("getDiscsHtml".equals(action)) {
				String discid = request.getParameter("discid");
				out.print(dictionarymanage.getDiscsHtml(discid));
			}
			// ȡ���ֵ�����ֵ
			else if ("getDiscItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				out.print(dictionarymanage.getDiscItemHtml(sid));
			}
			// �����ֵ�����ֵ
			else if ("updateDisc".equals(action)) {
				String EditType = request.getParameter("EditType");
				String did = request.getParameter("did");
				String dname = request.getParameter("dname");
				String pdid = request.getParameter("pdid");
				out.print(dictionarymanage.updateDisc(EditType, did, dname,
						pdid));
			}
			// �����ֵ�����״̬
			else if ("updateDiscStatus".equals(action)) {
				String did = request.getParameter("did");
				String status = request.getParameter("status");
				out.print(dictionarymanage.updateDiscStatus(did, status));
			}

			// ����ֵ�ֵ
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
