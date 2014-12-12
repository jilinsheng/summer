package com.mingda.action.policy.manage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.check.PolicyCheckMatch;
import com.mingda.action.policy.check.Policycheckidea;
import com.mingda.action.policy.check.Policycheckinfo;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.action.querymanage.DictionaryManage;
import com.mingda.common.myjdbc.ConnectionManager;

public class PolicyManageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PolicyManageServlet() {
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

		//
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡaction
		String action = request.getParameter("action");

		try {

			// log.debug(action);

			// ****************************����ҵ�������ֵ����**************************************//
			// ȡ������ҵ�������ֵ�������������
			if ("getPolicySelect".equals(action)) {
				String hname = request.getParameter("HName");
				String hclass = request.getParameter("HClass");
				String discid = request.getParameter("HDiscId");
				// �ֵ��ѯ������
				DictionaryManage dictionarymanage = new DictionaryManage();
				out.print(dictionarymanage.getPolicySelect(hname, hclass,
						discid));
			}
			// ****************************����ҵ�����**************************************//
			// ȡ��ҵ���б�
			else if ("getPolicysHtml".equals(action)) {
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicysHtml());
			}
			// ��ȡҵ������
			else if ("getPolicyItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyItemHtml(sid));
			}
			// ����ҵ��״̬��
			else if ("updatePolicyStatus".equals(action)) {
				String Id = request.getParameter("Id");
				String Status = request.getParameter("Status");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updatePolicyStatus(Id, Status));
			}
			// ����ҵ���
			else if ("updatePolicy".equals(action)) {

				String Sql = "";

				String EditType = request.getParameter("EditType");
				String pid = request.getParameter("pid");
				String pmenu = request.getParameter("pmenu");
				String pname = request.getParameter("pname");
				String pdescr = request.getParameter("pdescr");
				String pnote = request.getParameter("pnote");
				String pcycnum = request.getParameter("pcycnum");

				String pobjtype = request.getParameter("pobjtype");
				String ptype = request.getParameter("ptype");
				String pacctu = request.getParameter("pacctu");
				String pacctype = request.getParameter("pacctype");
				String pcycle = request.getParameter("pcycle");
				String pisprint = request.getParameter("pisprint");
				String pbegdt = request.getParameter("pbegdt");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				// ���ѯ������
				TableInfoQuery tableinfoquery = new TableInfoQuery();
				//
				if ("addpolicy".equals(EditType)) {
					if (policymanageedit.existsPolicy(pname)) {
						out.print("ҵ�������Ѿ�����!");
						return;
					}
					// ȡ����ҵ��ID����
					pid = tableinfoquery.getseqfromname("XPRIVILEGE_ID");
					//
					Sql = "insert into doper_t_policy "
							+ "(policy_id,objtype,menutype,name,descr,type,acctype,"
							+ "acctu,cycle,cycnum,isprint,begdt,note,flag) "
							+ "values ('"
							+ pid
							+ "','"
							+ pobjtype
							+ "','"
							+ pmenu
							+ "','"
							+ pname
							+ "','"
							+ pdescr
							+ "','"
							+ ptype
							+ "','"
							+ pacctype
							+ "',"
							+ "'"
							+ pacctu
							+ "','"
							+ pcycle
							+ "','"
							+ pcycnum
							+ "','"
							+ pisprint
							+ "',to_date('"
							+ pbegdt
							+ "','yyyy-mm-dd'),'"
							+ pnote + "','1')";
				} else if ("editpolicy".equals(EditType)) {
					if (policymanageedit.existsUpdatePolicy(pid, pname)) {
						// ͣ��
						policymanageedit.updatePolicyStatus(pid, "0");
						out.print("ҵ�������Ѿ�����!");
						return;
					}
					Sql = "update doper_t_policy " + "set objtype = '"
							+ pobjtype + "'," + "menutype = '" + pmenu + "',"
							+ "name = '" + pname + "'," + "descr = '" + pdescr
							+ "'," + "type = '" + ptype + "'," + "acctype = '"
							+ pacctype + "'," + "acctu = '" + pacctu + "',"
							+ "cycle = '" + pcycle + "'," + "cycnum = '"
							+ pcycnum + "'," + "isprint = '" + pisprint + "',"
							+ "begdt = to_date('" + pbegdt + "','yyyy-mm-dd'),"
							+ "note = '" + pnote + "' " + "where policy_id = '"
							+ pid + "'";
				}
				out.print(policymanageedit.updatePolicy(Sql, EditType, pid,
						pmenu));
			}// ��ȡҵ��������������
			else if ("getPolicyFlowItem".equals(action)) {
				String Id = request.getParameter("Id");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyFlowItem(Id));
			}// ����ҵ���������̱�
			else if ("updatePolicyFlow".equals(action)) {
				String Sql = "";

				String EditType = request.getParameter("EditType");
				String id = request.getParameter("id");
				String pid = request.getParameter("pid");
				String paccdept = request.getParameter("paccdept");
				String puseraccflag = request.getParameter("puseraccflag");
				String pcheckflag = request.getParameter("pcheckflag");
				String palllimit = request.getParameter("palllimit");
				String plimit1 = request.getParameter("plimit1");
				String plimit2 = request.getParameter("plimit2");
				String plimit3 = request.getParameter("plimit3");
				String plimit4 = request.getParameter("plimit4");
				String plimit5 = request.getParameter("plimit5");
				//
				String pappstate1 = request.getParameter("pappstate1");
				String pappstate2 = request.getParameter("pappstate2");
				String pappstate3 = request.getParameter("pappstate3");
				String pappstate4 = request.getParameter("pappstate4");
				String pappstate5 = request.getParameter("pappstate5");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();

				if ("addpolicyflow".equals(EditType)) {
					Sql = "insert into biz_t_checkflow "
							+ "(checkflow_id, policy_id,accdept,useraccflag,checkflag, alllimit, appstate1, limit1, appstate2, limit2, appstate3, limit3, appstate4, limit4, appstate5, limit5) "
							+ "values (xcheckflow_id.nextval,'"
							+ pid
							+ "','"
							+ paccdept
							+ "','"
							+ puseraccflag
							+ "','"
							+ pcheckflag
							+ "','"
							+ palllimit
							+ "','"
							+ pappstate1
							+ "','"
							+ plimit1
							+ "',"
							+ "'"
							+ pappstate2
							+ "','"
							+ plimit2
							+ "','"
							+ pappstate3
							+ "','"
							+ plimit3
							+ "','"
							+ pappstate4
							+ "','"
							+ plimit4
							+ "','"
							+ pappstate5
							+ "','"
							+ plimit5
							+ "')";
				} else if ("editpolicyflow".equals(EditType)) {
					Sql = "update biz_t_checkflow " + "set accdept = '"
							+ paccdept + "'," + "useraccflag = '"
							+ puseraccflag + "'," + "checkflag = '"
							+ pcheckflag + "'," + "alllimit = '" + palllimit
							+ "'," + "appstate1 = '" + pappstate1 + "',"
							+ "limit1 = '" + plimit1 + "'," + "appstate2 = '"
							+ pappstate2 + "'," + "limit2 = '" + plimit2 + "',"
							+ "appstate3 = '" + pappstate3 + "',"
							+ "limit3 = '" + plimit3 + "'," + "appstate4 = '"
							+ pappstate4 + "'," + "limit4 = '" + plimit4 + "',"
							+ "appstate5 = '" + pappstate5 + "',"
							+ "limit5 = '" + plimit5 + "' "
							+ "where checkflow_id = '" + id + "'";
				}
				out.print(policymanageedit.updatePolicyStrandardDept(Sql,
						EditType));
			}
			// ȡ�õ����б�
			else if ("getPolicySqlsHtml".equals(action)) {
				String PId = request.getParameter("PId");
				String PName = request.getParameter("PName");
				String JDeptid = request.getParameter("JDeptid");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicySqlsHtml(PId, PName,
						JDeptid));
			}
			// ��ȡ��������
			else if ("getPolicySqlItemHtml".equals(action)) {
				String Id = request.getParameter("Id");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicySqlItemHtml(Id));
			}
			// ��ȡҵ���׼SQL����
			else if ("getPolicySqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicySqlItem(sid, mode));
			}
			// ����ҵ���׼SQL����
			else if ("updatePolicySql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updatePolicySql(sid, sphysql,
						slocsql));
			}
			// ���µ���״̬��
			else if ("updateStandardStatus".equals(action)) {
				String Id = request.getParameter("Id");
				String Status = request.getParameter("Status");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updateStandardStatus(Id, Status));
			}
			// ���µ��α�
			else if ("updateStandard".equals(action)) {
				String Sql = "";

				String EditType = request.getParameter("EditType");
				String pid = request.getParameter("pid");
				String sid = request.getParameter("sid");
				String sdescr = request.getParameter("sdescr");
				String splanmoney = request.getParameter("splanmoney");
				String splandesc = request.getParameter("splandesc");
				String ssuperpolicy = request.getParameter("ssuperpolicy");
				String snotpolicy = request.getParameter("snotpolicy");
				String sdeptid = request.getParameter("sdeptid");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				if ("addstandard".equals(EditType)) {
					if (policymanageedit.existsStrandard(splanmoney, pid,
							sdeptid)) {
						out.print("��ҵ�񵵴ν���Ѿ�����!");
						return;
					}
					Sql = "insert into doper_t_standard "
							+ "(standard_id, policy_id, descr,organization_id, planmoney, plandesc,superpolicy,notpolicy, flag) "
							+ "values (xstandard_id.nextval,'" + pid + "','"
							+ sdescr + "','" + sdeptid + "'," + "'"
							+ splanmoney + "','" + splandesc + "','"
							+ ssuperpolicy + "','" + snotpolicy + "','1')";
				} else if ("editstandard".equals(EditType)) {
					if (policymanageedit.existsUpdateStrandard(sid, splanmoney,
							pid, sdeptid)) {
						// ͣ��
						policymanageedit.updateStandardStatus(sid, "0");
						out.print("��ҵ��������ν���Ѿ�����!");
						return;
					}
					Sql = "update doper_t_standard " + "set descr = '" + sdescr
							+ "'," + "planmoney = '" + splanmoney + "',"
							+ "plandesc = '" + splandesc + "',"
							+ "organization_id = '" + sdeptid + "',"
							+ "superpolicy = '" + ssuperpolicy + "',"
							+ "notpolicy = '" + snotpolicy + "' "
							+ "where standard_id = '" + sid + "'";
				}
				out.print(policymanageedit.updatePolicyStrandardDept(Sql,
						EditType));
			}
			// ȡ�õ����б�
			else if ("getPolicyDeptsHtml".equals(action)) {
				String SId = request.getParameter("SId");
				String SDeptName = request.getParameter("SDeptName");
				String SName = request.getParameter("SName");
				String SMoney = request.getParameter("SMoney");
				String SAccDesc = request.getParameter("SAccDesc");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyDeptsHtml(SId, SDeptName,
						SName, SMoney, SAccDesc));
			}
			// ��ȡ��������
			else if ("getPolicyDeptItemHtml".equals(action)) {
				String Id = request.getParameter("Id");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyDeptItemHtml(Id));
			}
			// ��ȡҵ���׼SQL����
			else if ("getPolicyDeptSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyDeptSqlItem(sid, mode));
			}
			// ����ҵ���׼SQL����
			else if ("updatePolicyDeptSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				String selexp = request.getParameter("selexp");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updatePolicyDeptSql(sid, sphysql,
						slocsql, selexp));
			}
			// ���»���״̬��
			else if ("updateDeptStatus".equals(action)) {
				String Id = request.getParameter("Id");
				String Status = request.getParameter("Status");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updateDeptStatus(Id, Status));
			}
			// ��������������
			else if ("updateDept".equals(action)) {
				String Sql = "";

				String EditType = request.getParameter("EditType");
				String sid = request.getParameter("sid");
				String did = request.getParameter("did");
				String ddeptid = request.getParameter("ddeptid");

				String dcheckmoney = request.getParameter("dcheckmoney");
				String dcheckdesc = request.getParameter("dcheckdesc");
				String daccdesc = request.getParameter("daccdesc");
				// ҵ������
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				if ("adddept".equals(EditType)) {
					if (policymanageedit.existsDept(sid, ddeptid)) {
						out.print("�õ��λ������㹫ʽ�Ѿ�����!");
						return;
					}
					Sql = "insert into doper_t_standarddept "
							+ "(standarddept_id,standard_id,organization_id,checkmoney,checkdesc,"
							+ "accdesc,flag) "
							+ "values (xstandarddept_id.nextval,'" + sid
							+ "','" + ddeptid + "','" + dcheckmoney + "','"
							+ dcheckdesc + "'," + "'" + daccdesc + "','1')";
				} else if ("editdept".equals(EditType)) {
					if (policymanageedit.existsUpdateDept(did, sid, ddeptid)) {
						// ͣ��
						policymanageedit.updateDeptStatus(did, "0");
						out.print("�õ��λ������㹫ʽ�Ѿ�����!");
						return;
					}
					Sql = "update doper_t_standarddept "
							+ "set organization_id = '" + ddeptid + "',"
							+ "checkmoney = '" + dcheckmoney + "',"
							+ "checkdesc = '" + dcheckdesc + "',"
							+ "accdesc = '" + daccdesc + "' "
							+ "where standarddept_id = '" + did + "'";
				}
				out.print(policymanageedit.updatePolicyStrandardDept(Sql,
						EditType));
			}
			// ****************************����ʩ��**************************************//
			// ȡ�÷���ʩ���б�
			else if ("getPolicyChildsHtml".equals(action)) {
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildsHtml());
			}
			// ��ȡ����ʩ������
			else if ("getPolicyChildItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildItemHtml(sid));
			}
			// ���·���ʩ��״̬��
			else if ("updatePoilcyChildStatus".equals(action)) {
				String sid = request.getParameter("sid");
				String status = request.getParameter("status");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild
						.updatePoilcyChildStatus(sid, status));
			}
			// ���·���ʩ����
			else if ("savePolicyChild".equals(action)) {
				String sql = "";
				String mode = request.getParameter("mode");
				String cpid = request.getParameter("cpid");
				String cname = request.getParameter("cname");
				String cpropid = request.getParameter("cpropid");
				String cdesc = request.getParameter("cdesc");
				String csqltype = request.getParameter("csqltype");
				String cmoneytype = request.getParameter("cmoneytype");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				if ("add".equals(mode)) {
					if (policymanagechild.existsPolicyChildFromName(cpropid,
							cname)) {
						out.print("�÷���ʩ���Ѿ�����!");
						return;
					}
					sql = "insert into doper_t_policychild "
							+ "(policychild_id, policy_id, name, policydesc,sqltype,moneytype, status) "
							+ "values (xpolicychild_id.nextval,'" + cpropid
							+ "','" + cname + "','" + cdesc + "','" + csqltype
							+ "','" + cmoneytype + "','1')";
				} else if ("edit".equals(mode)) {
					sql = "update doper_t_policychild " + "set policy_id = '"
							+ cpropid + "'," + "name = '" + cname + "',"
							+ "policydesc = '" + cdesc + "'," + "sqltype = '"
							+ csqltype + "'," + "moneytype = '" + cmoneytype
							+ "' " + "where policychild_id = '" + cpid + "'";
				}
				out.print(policymanagechild.savePolicyChild(sql, mode));
			}
			// ȡ�÷���ʩ����׼�б�
			else if ("getPolicyChildSqlsHtml".equals(action)) {
				String sid = request.getParameter("sid");
				String sname = request.getParameter("sname");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildSqlsHtml(sid, sname));
			}
			// ��ȡ����ʩ����׼����
			else if ("getPolicyChildSqlItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildSqlItemHtml(sid));
			}
			// ���·���ʩ����׼״̬��
			else if ("updatePoilcyChildSqlStatus".equals(action)) {
				String sid = request.getParameter("sid");
				String status = request.getParameter("status");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.updatePoilcyChildSqlStatus(sid,
						status));
			}
			// ���·���ʩ����׼��
			else if ("savePolicyChildSql".equals(action)) {
				String sql = "";
				String mode = request.getParameter("mode");
				String vsid = request.getParameter("vsid");
				String vname = request.getParameter("vname");
				String vprosid = request.getParameter("vprosid");
				String vdesc = request.getParameter("vdesc");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				if ("add".equals(mode)) {
					if (policymanagechild.existsPolicyChildSql(vprosid, vname)) {
						out.print("�÷���ʩ����׼�Ѿ�����!");
						return;
					}
					sql = "insert into doper_t_policychildsql "
							+ "(policychildsql_id, policychild_id, name, sqldesc, status) "
							+ "values (xpolicychildsql_id.nextval,'" + vprosid
							+ "','" + vname + "','" + vdesc + "','1')";
				} else if ("edit".equals(mode)) {
					sql = "update doper_t_policychildsql " + "set name = '"
							+ vname + "'," + "sqldesc = '" + vdesc + "' "
							+ "where policychildsql_id = '" + vsid + "'";
				}
				out.print(policymanagechild.savePolicyChildSql(sql, mode));
			}
			// ��ȡ����ʩ����׼SQL����
			else if ("getPolicyChildSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildSqlItem(sid, mode));
			}
			// ���·���ʩ����׼SQL����
			else if ("updatePolicyChildSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				// ����ʩ��������
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.updatePolicyChildSql(sid, sphysql,
						slocsql));
			}
			// ****************************�߷õ������**************************************//
			// ��ȡ��ͥ�߷�����(������ͥID)
			else if ("getInterviewIdeaItem".equals(action)) {
				String viewid = request.getParameter("viewid");
				// ����ҵ���߷ô�����
				PolicyManageInterView policymanageinterview = new PolicyManageInterView();
				out.print(policymanageinterview.getInterviewIdeaItem(viewid));
			}
			// ���ü�ͥ�߷�����(������ͥID)
			else if ("setInterviewIdeaItem".equals(action)) {
				String fmid = request.getParameter("fmid");
				String iperson = request.getParameter("iperson");
				String idt = request.getParameter("idt");
				String iresult = request.getParameter("iresult");
				String itype = request.getParameter("itype");
				String ideptid = request.getParameter("ideptid");
				String operid = request.getParameter("operid");
				// ����ҵ���߷ô�����
				PolicyManageInterView policymanageinterview = new PolicyManageInterView();
				out.print(policymanageinterview.setInterviewIdeaItem(fmid,
						iperson, idt, iresult, itype, ideptid, operid));
			}
			// ****************************ҵ����������**************************************//
			// ����\������ͥ���߳�Ա�������ҵ����������
			else if ("setNewPolciyMatchMore".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				// ����ҵ���׼������
				/*
				 * PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
				 * out.print(policycheckmatch.SetNewPolciyMatchMore(pid,empid));
				 */
				Policycheckinfo policycheckinfo = new Policycheckinfo();
				out.print(policycheckinfo.SetNewPolciyMatchMore(pid, empid));
			}
			// ������ͥ���߳�Ա�ⷢ������
			else if ("updatePolciyMatchOne".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				String fmid = request.getParameter("fmid");
				// ����ҵ���׼������
				PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
				out.print(policycheckmatch.UpdatePolciyMatchOne(pid, empid,
						fmid));
			}
			// ������ͥ���߳�Ա�ⷢ������
			else if ("updatePolciyMatchMore".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				// ����ҵ���׼������
				PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
				out.print(policycheckmatch.UpdatePolciyMatchMore(pid, empid));
			}
			// ����ҵ���ͥ����(������ͥID)
			else if ("removeCheckIdea".equals(action)) {
				String empid = request.getParameter("empid");
				String pid = request.getParameter("pid");
				String fmid = request.getParameter("fmid");
				// ��������������
				Policycheckidea policycheckidea = new Policycheckidea();
				out.print(policycheckidea.removeCheckIdea(empid, pid, fmid));
			}

			// ��ȡҵ��������ɫȨ���б�
			else if ("getPolicyPostsPower".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				// ����ҵ��Ȩ�޴�����
				PolicyManagePower policymanagepower = new PolicyManagePower();
				out.print(policymanagepower.getPolicyPostsPower(pid, empid));
			}
			// ����ҵ��������ɫȨ���б�
			else if ("setPolicyPostPowers".equals(action)) {
				String pid = request.getParameter("pid");
				String postid = request.getParameter("postid");
				String checkflag = request.getParameter("checkflag");
				String checkmoreflag = request.getParameter("checkmoreflag");
				String reportflag = request.getParameter("reportflag");
				// ����ҵ��Ȩ�޴�����
				PolicyManagePower policymanagepower = new PolicyManagePower();
				out.print(policymanagepower.setPolicyPostPowers(pid, postid,
						checkflag, checkmoreflag, reportflag));
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
