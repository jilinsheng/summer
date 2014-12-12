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

		//
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 获取action
		String action = request.getParameter("action");

		try {

			// log.debug(action);

			// ****************************政策业务属性字典管理**************************************//
			// 取得政策业务所有字典描述的下拉框
			if ("getPolicySelect".equals(action)) {
				String hname = request.getParameter("HName");
				String hclass = request.getParameter("HClass");
				String discid = request.getParameter("HDiscId");
				// 字典查询处理类
				DictionaryManage dictionarymanage = new DictionaryManage();
				out.print(dictionarymanage.getPolicySelect(hname, hclass,
						discid));
			}
			// ****************************政策业务管理**************************************//
			// 取得业务列表
			else if ("getPolicysHtml".equals(action)) {
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicysHtml());
			}
			// 获取业务属性
			else if ("getPolicyItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyItemHtml(sid));
			}
			// 更新业务状态表
			else if ("updatePolicyStatus".equals(action)) {
				String Id = request.getParameter("Id");
				String Status = request.getParameter("Status");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updatePolicyStatus(Id, Status));
			}
			// 更新业务表
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
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				// 表查询处理类
				TableInfoQuery tableinfoquery = new TableInfoQuery();
				//
				if ("addpolicy".equals(EditType)) {
					if (policymanageedit.existsPolicy(pname)) {
						out.print("业务名称已经存在!");
						return;
					}
					// 取得新业务ID序列
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
						// 停用
						policymanageedit.updatePolicyStatus(pid, "0");
						out.print("业务名称已经存在!");
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
			}// 获取业务审批流程属性
			else if ("getPolicyFlowItem".equals(action)) {
				String Id = request.getParameter("Id");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyFlowItem(Id));
			}// 更新业务审批流程表
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
				// 业务处理类
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
			// 取得档次列表
			else if ("getPolicySqlsHtml".equals(action)) {
				String PId = request.getParameter("PId");
				String PName = request.getParameter("PName");
				String JDeptid = request.getParameter("JDeptid");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicySqlsHtml(PId, PName,
						JDeptid));
			}
			// 获取档次属性
			else if ("getPolicySqlItemHtml".equals(action)) {
				String Id = request.getParameter("Id");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicySqlItemHtml(Id));
			}
			// 获取业务标准SQL属性
			else if ("getPolicySqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicySqlItem(sid, mode));
			}
			// 更新业务标准SQL属性
			else if ("updatePolicySql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updatePolicySql(sid, sphysql,
						slocsql));
			}
			// 更新档次状态表
			else if ("updateStandardStatus".equals(action)) {
				String Id = request.getParameter("Id");
				String Status = request.getParameter("Status");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updateStandardStatus(Id, Status));
			}
			// 更新档次表
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
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				if ("addstandard".equals(EditType)) {
					if (policymanageedit.existsStrandard(splanmoney, pid,
							sdeptid)) {
						out.print("该业务档次金额已经存在!");
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
						// 停用
						policymanageedit.updateStandardStatus(sid, "0");
						out.print("该业务机构档次金额已经存在!");
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
			// 取得档次列表
			else if ("getPolicyDeptsHtml".equals(action)) {
				String SId = request.getParameter("SId");
				String SDeptName = request.getParameter("SDeptName");
				String SName = request.getParameter("SName");
				String SMoney = request.getParameter("SMoney");
				String SAccDesc = request.getParameter("SAccDesc");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyDeptsHtml(SId, SDeptName,
						SName, SMoney, SAccDesc));
			}
			// 获取档次属性
			else if ("getPolicyDeptItemHtml".equals(action)) {
				String Id = request.getParameter("Id");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyDeptItemHtml(Id));
			}
			// 获取业务标准SQL属性
			else if ("getPolicyDeptSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.getPolicyDeptSqlItem(sid, mode));
			}
			// 更新业务标准SQL属性
			else if ("updatePolicyDeptSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				String selexp = request.getParameter("selexp");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updatePolicyDeptSql(sid, sphysql,
						slocsql, selexp));
			}
			// 更新机构状态表
			else if ("updateDeptStatus".equals(action)) {
				String Id = request.getParameter("Id");
				String Status = request.getParameter("Status");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				out.print(policymanageedit.updateDeptStatus(Id, Status));
			}
			// 更新所属机构表
			else if ("updateDept".equals(action)) {
				String Sql = "";

				String EditType = request.getParameter("EditType");
				String sid = request.getParameter("sid");
				String did = request.getParameter("did");
				String ddeptid = request.getParameter("ddeptid");

				String dcheckmoney = request.getParameter("dcheckmoney");
				String dcheckdesc = request.getParameter("dcheckdesc");
				String daccdesc = request.getParameter("daccdesc");
				// 业务处理类
				PolicyManageEdit policymanageedit = new PolicyManageEdit();
				if ("adddept".equals(EditType)) {
					if (policymanageedit.existsDept(sid, ddeptid)) {
						out.print("该档次机构核算公式已经存在!");
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
						// 停用
						policymanageedit.updateDeptStatus(did, "0");
						out.print("该档次机构核算公式已经存在!");
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
			// ****************************分类施保**************************************//
			// 取得分类施保列表
			else if ("getPolicyChildsHtml".equals(action)) {
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildsHtml());
			}
			// 获取分类施保属性
			else if ("getPolicyChildItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildItemHtml(sid));
			}
			// 更新分类施保状态表
			else if ("updatePoilcyChildStatus".equals(action)) {
				String sid = request.getParameter("sid");
				String status = request.getParameter("status");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild
						.updatePoilcyChildStatus(sid, status));
			}
			// 更新分类施保表
			else if ("savePolicyChild".equals(action)) {
				String sql = "";
				String mode = request.getParameter("mode");
				String cpid = request.getParameter("cpid");
				String cname = request.getParameter("cname");
				String cpropid = request.getParameter("cpropid");
				String cdesc = request.getParameter("cdesc");
				String csqltype = request.getParameter("csqltype");
				String cmoneytype = request.getParameter("cmoneytype");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				if ("add".equals(mode)) {
					if (policymanagechild.existsPolicyChildFromName(cpropid,
							cname)) {
						out.print("该分类施保已经存在!");
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
			// 取得分类施保标准列表
			else if ("getPolicyChildSqlsHtml".equals(action)) {
				String sid = request.getParameter("sid");
				String sname = request.getParameter("sname");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildSqlsHtml(sid, sname));
			}
			// 获取分类施保标准属性
			else if ("getPolicyChildSqlItemHtml".equals(action)) {
				String sid = request.getParameter("sid");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildSqlItemHtml(sid));
			}
			// 更新分类施保标准状态表
			else if ("updatePoilcyChildSqlStatus".equals(action)) {
				String sid = request.getParameter("sid");
				String status = request.getParameter("status");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.updatePoilcyChildSqlStatus(sid,
						status));
			}
			// 更新分类施保标准表
			else if ("savePolicyChildSql".equals(action)) {
				String sql = "";
				String mode = request.getParameter("mode");
				String vsid = request.getParameter("vsid");
				String vname = request.getParameter("vname");
				String vprosid = request.getParameter("vprosid");
				String vdesc = request.getParameter("vdesc");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				if ("add".equals(mode)) {
					if (policymanagechild.existsPolicyChildSql(vprosid, vname)) {
						out.print("该分类施保标准已经存在!");
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
			// 获取分类施保标准SQL属性
			else if ("getPolicyChildSqlItem".equals(action)) {
				String sid = request.getParameter("sid");
				String mode = request.getParameter("mode");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.getPolicyChildSqlItem(sid, mode));
			}
			// 更新分类施保标准SQL属性
			else if ("updatePolicyChildSql".equals(action)) {
				String sid = request.getParameter("sid");
				String sphysql = request.getParameter("sphysql");
				String slocsql = request.getParameter("slocsql");
				// 分类施保处理类
				PolicyManageChild policymanagechild = new PolicyManageChild();
				out.print(policymanagechild.updatePolicyChildSql(sid, sphysql,
						slocsql));
			}
			// ****************************走访调查管理**************************************//
			// 获取家庭走访描述(单个家庭ID)
			else if ("getInterviewIdeaItem".equals(action)) {
				String viewid = request.getParameter("viewid");
				// 政策业务走访处理类
				PolicyManageInterView policymanageinterview = new PolicyManageInterView();
				out.print(policymanageinterview.getInterviewIdeaItem(viewid));
			}
			// 设置家庭走访描述(单个家庭ID)
			else if ("setInterviewIdeaItem".equals(action)) {
				String fmid = request.getParameter("fmid");
				String iperson = request.getParameter("iperson");
				String idt = request.getParameter("idt");
				String iresult = request.getParameter("iresult");
				String itype = request.getParameter("itype");
				String ideptid = request.getParameter("ideptid");
				String operid = request.getParameter("operid");
				// 政策业务走访处理类
				PolicyManageInterView policymanageinterview = new PolicyManageInterView();
				out.print(policymanageinterview.setInterviewIdeaItem(fmid,
						iperson, idt, iresult, itype, ideptid, operid));
			}
			// ****************************业务审批管理**************************************//
			// 单个\批量家庭或者成员添加生成业务审批名单
			else if ("setNewPolciyMatchMore".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				// 政策业务标准处理类
				/*
				 * PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
				 * out.print(policycheckmatch.SetNewPolciyMatchMore(pid,empid));
				 */
				Policycheckinfo policycheckinfo = new Policycheckinfo();
				out.print(policycheckinfo.SetNewPolciyMatchMore(pid, empid));
			}
			// 单个家庭或者成员拟发救助金
			else if ("updatePolciyMatchOne".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				String fmid = request.getParameter("fmid");
				// 政策业务标准处理类
				PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
				out.print(policycheckmatch.UpdatePolciyMatchOne(pid, empid,
						fmid));
			}
			// 批量家庭或者成员拟发救助金
			else if ("updatePolciyMatchMore".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				// 政策业务标准处理类
				PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
				out.print(policycheckmatch.UpdatePolciyMatchMore(pid, empid));
			}
			// 撤消业务家庭审批(单个家庭ID)
			else if ("removeCheckIdea".equals(action)) {
				String empid = request.getParameter("empid");
				String pid = request.getParameter("pid");
				String fmid = request.getParameter("fmid");
				// 政策审批处理类
				Policycheckidea policycheckidea = new Policycheckidea();
				out.print(policycheckidea.removeCheckIdea(empid, pid, fmid));
			}

			// 获取业务审批角色权限列表
			else if ("getPolicyPostsPower".equals(action)) {
				String pid = request.getParameter("pid");
				String empid = request.getParameter("empid");
				// 政策业务权限处理类
				PolicyManagePower policymanagepower = new PolicyManagePower();
				out.print(policymanagepower.getPolicyPostsPower(pid, empid));
			}
			// 设置业务审批角色权限列表
			else if ("setPolicyPostPowers".equals(action)) {
				String pid = request.getParameter("pid");
				String postid = request.getParameter("postid");
				String checkflag = request.getParameter("checkflag");
				String checkmoreflag = request.getParameter("checkmoreflag");
				String reportflag = request.getParameter("reportflag");
				// 政策业务权限处理类
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
