package com.mingda.action.querymanage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingda.common.myjdbc.ConnectionManager;

@SuppressWarnings("serial")
public class QueryManageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public QueryManageServlet() {
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
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			// Connection conn =session.connection();
			// ��ѯ������
			QueryManage querymanage = new QueryManage();
			// �ֵ��ѯ������
			DictionaryManage dictionarymanage = new DictionaryManage();
			// ��Ϣ�Ϸ�У�鴦����
			InfoValidate infovalidate = new InfoValidate();
			// ���������˴�����
			CheckPersonManage checkpersonmanage = new CheckPersonManage();
			//
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// ��ȡaction
			String action = request.getParameter("action");
			// log.debug(action);
			//
			// ȡ���û���ѯҳ��
			if ("getQueryExpHtml".equals(action)) {
				String empid = request.getParameter("empid");
				out.print(querymanage.getQueryExpHtml(empid));
			} else if ("getQueryExpSeq".equals(action)) {
				String empid = request.getParameter("empid");
				out.print(querymanage.getQueryExpSeq(empid));
			}
			//
			else if ("saveQuerySeq".equals(action)) {
				String seqnew = request.getParameter("seqnew");
				String empid = request.getParameter("empid");
				out.print(querymanage.saveQuerySeq(seqnew, empid));
			} else if ("delAllQuerySeq".equals(action)) {
				String parid = request.getParameter("parid");
				String empid = request.getParameter("empid");
				out.print(querymanage.delAllQuerySeq(parid, empid));
			} else if ("delQuerySeq".equals(action)) {
				String id = request.getParameter("id");
				String empid = request.getParameter("empid");
				out.print(querymanage.delQuerySeq(id, empid));
			} else if ("addQuerySeq".equals(action)) {
				String empid = request.getParameter("empid");
				String tinfo = request.getParameter("tinfo");
				String texp = request.getParameter("texp");
				String ttype = request.getParameter("ttype");
				out.print(querymanage.addQuerySeq(empid, tinfo, texp, ttype));
			}
			// ȡ�����������
			else if ("getSelectYear".equals(action)) {
				String sname = request.getParameter("sname");
				out.print(querymanage.getSelectYear(sname));
			}
			// ȡ���·�������
			else if ("getSelectMonth".equals(action)) {
				String sname = request.getParameter("sname");
				out.print(querymanage.getSelectMonth(sname));
			}
			// ȡ���ֵ�������ѡ��
			else if ("getDiscionarySelect".equals(action)) {
				String sname = request.getParameter("sname");
				String sdiscid = request.getParameter("sdiscid");
				out.print(dictionarymanage.getDiscionarySelect(sname, sdiscid));
			}
			// ȡ����Ϣ�Ϸ���֤�б�
			else if ("getInfoValidates".equals(action)) {
				out.print(infovalidate.getInfoValidates());
			}
			// ȡ����Ϣ�Ϸ���֤����
			else if ("getInfoValidateItems".equals(action)) {
				String sid = request.getParameter("sid");
				out.print(infovalidate.getInfoValidateItems(sid));
			}
			// ȡ����Ϣ�Ϸ���֤����
			else if ("getInfoValidateSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				out.print(infovalidate.getInfoValidateSqlItem(sid, mode));
			}
			// ������Ϣ�Ϸ���֤״̬
			else if ("updateValidateStatus".equals(action)) {
				String sid = request.getParameter("sid");
				String status = request.getParameter("status");
				out.print(infovalidate.updateValidateStatus(sid, status));
			}
			// ������Ϣ�Ϸ���֤
			else if ("updateValidate".equals(action)) {
				String mode = request.getParameter("mode");
				String sid = request.getParameter("sid");
				String sname = request.getParameter("sname");
				String sdesc = request.getParameter("sdesc");
				String stype = request.getParameter("stype");
				String sql = "";
				if (mode.equals("add")) {
					sql = "insert into biz_t_validateterm (validateterm_id,name,itemdesc,type,status) "
							+ "values "
							+ "(xvalidateterm_id.nextval,'"
							+ sname
							+ "','" + sdesc + "','" + stype + "','1')";
				} else if (mode.equals("edit")) {
					sql = "update biz_t_validateterm set " + "name = '" + sname
							+ "'," + "itemdesc = '" + sdesc + "'," + "type = '"
							+ stype + "' " + "where validateterm_id = '" + sid
							+ "'";
				}
				out.print(infovalidate.updateValidate(mode, sname, sql));
			}
			// ������Ϣ�Ϸ���֤����SQL
			else if ("updateValidateSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				out.print(infovalidate.updateValidateSql(sid, sphysql, slocsql));
			}
			// ȡ�������������б�
			else if ("getCheckPersons".equals(action)) {
				String sdeptid = request.getParameter("sdeptid");
				out.print(checkpersonmanage.getCheckPersons(sdeptid));
			}
			// ȡ����������������
			else if ("getCheckPersonItems".equals(action)) {
				String sid = request.getParameter("sid");
				out.print(checkpersonmanage.getCheckPersonItems(sid));
			}
			// ��������������״̬
			else if ("updateCheckPersonStatus".equals(action)) {
				String sid = request.getParameter("sid");
				String status = request.getParameter("status");
				out.print(checkpersonmanage
						.updateCheckPersonStatus(sid, status));
			}
			// ��������������
			else if ("updateCheckPerson".equals(action)) {
				String mode = request.getParameter("mode");
				String vdeptid = request.getParameter("vdeptid");
				String vid = request.getParameter("vid");
				String vname = request.getParameter("vname");
				String vsex = request.getParameter("vsex");
				String vface = request.getParameter("vface");
				String vofficename = request.getParameter("vofficename");
				String vofficephone = request.getParameter("vofficephone");
				String vpost = request.getParameter("vpost");
				String vaddress = request.getParameter("vaddress");
				String sql = "";
				if (mode.equals("add")) {
					sql = "insert into biz_t_optreviewperson "
							+ "(optreviewperson_id,name,sex,face,officename,officephone,post,address,organization_id,status) "
							+ "values " + "(xoptreviewperson_id.nextval,'"
							+ vname + "','" + vsex + "','" + vface + "','"
							+ vofficename + "','" + vofficephone + "','"
							+ vpost + "','" + vaddress + "','" + vdeptid
							+ "','1')";
				} else if (mode.equals("edit")) {
					sql = "update biz_t_optreviewperson set " + "name = '"
							+ vname + "'," + "sex = '" + vsex + "',"
							+ "face = '" + vface + "'," + "officename = '"
							+ vofficename + "'," + "officephone = '"
							+ vofficephone + "'," + "post = '" + vpost + "',"
							+ "address = '" + vaddress + "' "
							+ "where optreviewperson_id = '" + vid + "'";
				}
				out.print(checkpersonmanage.updateCheckPerson(mode, vname,
						vdeptid, sql));
			}
			// ȡ������������������
			else if ("getCheckPersonChoice".equals(action)) {
				String sname = request.getParameter("sname");
				String empid = request.getParameter("empid");
				out.print(checkpersonmanage.getCheckPersonChoice(sname, empid));
			}

		} catch (SQLException re) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			re.printStackTrace();
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
