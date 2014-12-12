package com.mingda.action.policy.approvalidea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.approval.Policycheckpage;
import com.mingda.action.policy.approval2.Policychecksql;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;
import com.mingda.action.policy.sql.PolicyPhySQL;
import com.mingda.common.ConstantDefine;

public class Policyideapage {
	static Logger log = Logger.getLogger(Policyideapage.class);
	/**
	 * 获取审批结果下拉框
	 * @param sname
	 * @param stabid
	 * @return
	 */
	public StringBuffer getCheckIdeaSelect(String sname,String stabid,String sonecheck){
		StringBuffer shtml= new StringBuffer("");
		//
		String iall = "<option value='0'>全部</option>";
		String inew = "<option value='1'>未审批</option>";
		String iok = "<option value='2'>同意救助</option>";		
		String ire = "<option value='3'>重新审批</option>";
		String idel = "<option value='8'>取消救助</option>";
		String inot = "<option value='9'>不同意救助</option>";
		//
		shtml.append("<select id=\""+sname+"\" style = \"font-size:12px\">");
		//
		if("1".equals(stabid)){				//新增
			shtml.append(iok);			
			if(!"1".equals(sonecheck)){	//第一级审批机构不能选择(重新审批)
				shtml.append(ire);
			}
		}else if("2".equals(stabid)){		//调整复查
			shtml.append(iok);			
			shtml.append(idel);
			if(!"1".equals(sonecheck)){	//第一级审批机构不能选择(重新审批)
				shtml.append(ire);
			}
		}
		//
		shtml.append("</select>");
		return shtml;
	}
	/**
     * 取得致困原因分类复选选择
     * 字典编码为[115]
     * @param sname
     * @return
     */
    public StringBuffer getRequestTypeCheckBox(){
    	StringBuffer shtml = new StringBuffer("");
		String id = "", name = "";
		int irow = 0;
		String sql = "select  " 
			+ "dictionary_id," 
			+ "item " 
			+ "from sys_t_dictionary " 
			+ "where dictsort_id = '115' and status = '1'";
		// log.debug(sql);
		//
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 参数
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getString("dictionary_id");
				name = rs.getString("item");
				shtml.append("<input type='checkbox' name = 'cbxq[]' id='q" + id + "' title=" + name + ">" + name + "</input>");
				irow++;
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			//DBUtils.close(conn); // 关闭连接
		}
		if(irow==0){
			shtml.append("无记录!");
		}
		return shtml;
    }
    /**
     * 获取该机构评议人
     * @param deptid
     * @return
     */
    public StringBuffer getRequestPersonCheckBox(String deptid){
    	StringBuffer shtml = new StringBuffer("");
    	int irow = 0;
		String id = "", name = "";
		String sql = "select " 
			+ "optreviewperson_id," 
			+ "name " 
			+ "from biz_t_optreviewperson " 
			+ "where status = '1' and organization_id = '"+deptid+"'";
		// log.debug(sql);
		//
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
			// 参数
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getString("optreviewperson_id");
				name = rs.getString("name");
				shtml.append("<input type='checkbox' name = 'cbxp[]' id='p" + id + "' title=" + name + ">" + name + "</input>");
				irow++;
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			//DBUtils.close(conn); // 关闭连接
		}
		if(irow==0){
			shtml.append("无记录!");
		}
		return shtml;
    }
    /**
     * 更新单个救助金
     * @param optid
     * @param money
     * @return
     */
    public StringBuffer updateMoneyFromOptID(String optid,String money){
    	StringBuffer shtml= new StringBuffer("");
    	String sql = "update biz_t_optcheck	"
    		+ "set checkmoney = '"+money+"' "
    		+ "where optcheck_id = '"+optid+"'";
    	PublicComm pubilccomm = new PublicComm();
    	shtml.append(pubilccomm.ExeSQLFromUserSQL(sql));
    	return shtml;
    }
	/**
	 * 生成XML文件
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaResultForXml(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		String sql = "";
		//
		PolicyPhySQL policyphysql = new PolicyPhySQL();
		Policyideasql policyideasql = new Policyideasql();
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jpid = hashmap.get("jpid").toString();
		String jaccmode = hashmap.get("jaccmode").toString();
		String jfmids = hashmap.get("jfmids").toString();
		//
		// 业务核算类型
	    String acctype = policyaccquery.getPolicyAccTypeFlag(jpid);
		//
	    if ("1".equals(acctype)) { // 成员核算
	    	sql = "select " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "b.member_id,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "a.membername "
	        	+ "from info_t_member a, biz_t_optcheck b,info_t_family f "
	        	+ "where a.member_id = b.member_id " 
	        	+ "and a.family_id = f.family_id " 
	        	+ "and b.family_id = f.family_id "
	        	+ "and b.policy_id = '"+jpid+"' "
	        	+ "and b.family_id in ("+jfmids+") " 
	        	+ "group by " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "b.member_id,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "a.membername ";
	    }else{					   // 家庭核算
	    	sql = "select " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "f.masterid member_id,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "f.mastername membername "
	        	+ "from biz_t_optcheck b,info_t_family f "
	        	+ "where b.family_id = f.family_id "
	        	+ "and b.policy_id = '"+jpid+"' "
	        	+ "and b.family_id in ("+jfmids+") " 
	        	+ "group by " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "f.masterid ,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "f.mastername ";
	    }	
	    //log.debug(sql);
		//获取汇总信息
		HashMap hmcount = policyphysql.getIdeaCountFromSQL(sql);
	    String sqlcount = hmcount.get("sqlcount").toString();
	    String sumolemoney = hmcount.get("sumolemoney").toString();
	    String sumnewmoney = hmcount.get("sumnewmoney").toString();
	    //
	    //构造审批表格
        HashMap hashmapsql = new HashMap();
        hashmapsql.put("sql", sql);
        hashmapsql.put("jaccmode", jaccmode);
        hashmapsql.put("jonecheck", jonecheck);
        hashmapsql.put("jendcheck", jendcheck);
        String stable = policyideasql.getIdeaPageTable(hashmapsql).toString();
		//============================================================xml==============================
        //
		Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //
        //=================================================================查询汇总BEG 
        Element eCount=data.addElement("counts");
        //表格记录总数
        Element eCountChild =eCount.addElement("sqlcount");
        eCountChild.setText(sqlcount);
        //表格上期救助金汇总
        eCountChild =eCount.addElement("sumolemoney");
        eCountChild.setText(sumolemoney); 
        //表格拟发救助金汇总
        eCountChild =eCount.addElement("sumnewmoney");
        eCountChild.setText(sumnewmoney); 
        //=================================================================查询汇总END
        //=================================================================审批表格
        Element eTable=data.addElement("table");
        Element eTableChild =eTable.addElement("elements");
        eTableChild.setText(stable);
        //=================================================================审批表格
        //
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML());
        shtml.append(node.asXML());
        //============================================================xml==============================
		return shtml;
	}
	/**
	 * 校验审批
	 * @param mode
	 * @param hashmap
	 * @return
	 */
	public StringBuffer validationCheckIdea(String mode,HashMap hashmap){
    	StringBuffer shtml= new StringBuffer("");
    	String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        //
        String jpid = hashmap.get("jpid").toString();
        ////结算批次标识
		PolicyAccQuery policyaccquery = new PolicyAccQuery();    	
        HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
        String optid = hashmapaccflag.get("optid").toString();
        String accflag = hashmapaccflag.get("accflag").toString();
        String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
        String accmoney = hashmapaccflag.get("accmoney").toString();
        //
		if ("-1".equals(accflag)) { // 无结算批次
			shtml.append("该业务未执行处理,等待建立结算信息!");
			return shtml;
		} else if ("0".equals(accflag)) { // 新结算批次
			if ("addCheckIdea".equals(mode)) {
				shtml.append(addCheckIdea(hashmap));
			} else if ("updateCheckIdea".equals(mode)) {
				shtml.append(updateCheckIdea(hashmap));
			} else if ("allCheckIdea".equals(mode)) {
				shtml.append(allCheckIdea(hashmap));
			} else if ("moneyCheckIdea".equals(mode)) {
				shtml.append(moneyCheckIdea(hashmap));
			} else if ("allMoneyCheckIdea".equals(mode)) {
				shtml.append(allMoneyCheckIdea(hashmap));
			}
			//更新保障状态
			UpdateEndSalType(mode,hashmap);
			
		} else if ("1".equals(accflag)) { // 正在结算批次
			shtml.append("该业务正在执行结算处理,请等待!");
			return shtml;
		} else if ("2".equals(accflag)) { // 结算完成批次
			shtml.append("该业务结算完毕,等待建立新的结算信息!");
			return shtml;
		}
		return shtml;
    }
	/**
	 * 更新农村低保保障类型
	 * @param mode
	 * @param hashmap	 
	 * @return
	 */
	public StringBuffer UpdateEndSalType(String mode,HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		String sql = "",jfmids = "",jallsql = "";
		//
		String jpid = hashmap.get("jpid").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		
		
		//
		if("21".equals(jpid)){		//指定农村低保
			if("1".equals(jendcheck)){	//终审审批
				if ("addCheckIdea".equals(mode)) {
					jfmids = hashmap.get("jfmids").toString();					
					sql = "update INFO_T_FAMILY set desSalType = SalType where family_id in ("+jfmids+") ";
				} else if ("updateCheckIdea".equals(mode)) {
					jfmids = hashmap.get("jfmids").toString();
					sql = "update INFO_T_FAMILY set desSalType = SalType where family_id in ("+jfmids+") ";
				} else if ("allCheckIdea".equals(mode)) {
					jallsql = hashmap.get("jallsql").toString();
					sql = "update INFO_T_FAMILY fm set fm.desSalType = fm.SalType where  exists (select t.family_id from ("+jallsql+") t where fm.family_id = t.family_id) ";
				} else if ("moneyCheckIdea".equals(mode)) {
					jfmids = hashmap.get("jfmids").toString();
					sql = "update INFO_T_FAMILY set desSalType = SalType where family_id in ("+jfmids+") ";
				} else if ("allMoneyCheckIdea".equals(mode)) {
					jallsql = hashmap.get("jallsql").toString();
					sql = "update INFO_T_FAMILY fm set fm.desSalType = fm.SalType where  exists (select t.family_id from ("+jallsql+") t where fm.family_id = t.family_id) ";
				}
				//log.debug(sql);
				PublicComm pubilccomm = new PublicComm();
		    	shtml.append("更新保障状态:"+pubilccomm.ExeSQLFromUserSQL(sql));
			}
		}
		
		return shtml;
    }
	/**
	 * 单户审批
	 * @param hashmap
	 * @return
	 */
	public StringBuffer addCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jbegdt = hashmap.get("jbegdt").toString();
		String jenddt = hashmap.get("jenddt").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		String jdesc = hashmap.get("jdesc").toString();
		String jmanall = hashmap.get("jmanall").toString();
		String jmanok = hashmap.get("jmanok").toString();
		String jmandel = hashmap.get("jmandel").toString();
		String jmannot = hashmap.get("jmannot").toString();
		String jmanids = hashmap.get("jmanids").toString();
		//
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";
		//更新语句
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//上报名单(确认名单)
			//更新
			usql = "update biz_t_optcheck opt set ";
			//
			//审批结果校验
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//重新审批(审批结果)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//街道
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//区县
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//市局
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//不是重新审批(审批结果)
				if("1".equals(jendcheck)){	//终审审批
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ "rebegdt = to_date('"+jbegdt+"','yyyy-mm-dd'),"
						+ "reenddt = to_date('"+jenddt+"','yyyy-mm-dd')," 
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//下级审批	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//条件
			usql += "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") ";
			//
			if("1".equals(jtabid)){				//新增查询模块
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("更新审批表:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}
		//查询语句
		String sql = "select " 
			+ "xoptcheckidea_id.nextval," 
			+ "t.optcheck_id, "	
			+ "'"+jmanids+"',"
			+ "'"+jmandel+"',"
			+ "'"+jmanok+"',"
			+ "'"+jmanall+"',"
			+ "'"+jmannot+"',"
			+ "'"+jresult+"',"
			+ "'"+jdesc+"',"
			+ "to_date('"+jbegdt+"','yyyy-mm-dd')," 
			+ "to_date('"+jenddt+"','yyyy-mm-dd')," 
			+ "to_date('"+jcheckdt+"','yyyy-mm-dd'),"
			+ "'单户审批',"
			+ "'"+jdepth+"',"
			+ "'"+jempid+"',"
			+ "sysdate,"
			+ "'1'"
			+ "from biz_t_optcheck t " 
			+ "where t.policy_id = '"+jpid+"' " 
			+ "and t.family_id in ("+jfmids+") ";
		//救助金不能为0
		if("1".equals(jtabid)){				//新增查询模块
			sql += " and checkmoney > 0 ";
		}
		//插入语句
		String isql = "insert into biz_t_optcheckidea "
			+ "(optcheckidea_id,"
			+ "optcheck_id,"
			+ "appideaman,"
			+ "appmandelnum,"
			+ "appmanoknum,"
			+ "appmannum,"
			+ "appmannotnum,"
			+ "appresult,"
			+ "apparea,"
			+ "rebegdt,"
			+ "reenddt,"
			+ "apptime,"
			+ "note,"
			+ "depth,"
			+ "checkoper,"
			+ "checkdt,"
			+ "status) " + sql;
		
		//log.debug(isql);
		
		PublicComm pubilccomm = new PublicComm();
    	shtml.append("添加审批意见:"+pubilccomm.ExeSQLFromUserSQL(isql));
		return shtml;
	}
	/**
	 * 选中审批
	 * @param hashmap
	 * @return
	 */
	public StringBuffer updateCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jbegdt = hashmap.get("jbegdt").toString();
		String jenddt = hashmap.get("jenddt").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		//
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";
		//更新语句
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//上报名单(确认名单)
			//更新
			usql = "update biz_t_optcheck opt set ";
			//
			//审批结果校验
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//重新审批(审批结果)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//街道
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//区县
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//市局
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//不是重新审批(审批结果)
				if("1".equals(jendcheck)){	//终审审批
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ "rebegdt = to_date('"+jbegdt+"','yyyy-mm-dd'),"
						+ "reenddt = to_date('"+jenddt+"','yyyy-mm-dd')," 
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//下级审批	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//条件
			usql += "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") ";
			//救助金不能为0
			if("1".equals(jtabid)){				//新增查询模块
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("更新审批表:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * 全部审批
	 * @param hashmap
	 * @return
	 */
	public StringBuffer allCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jallsql = hashmap.get("jallsql").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		//
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";	
		//更新语句
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//上报名单(确认名单)
			//更新
			usql = "update biz_t_optcheck  opt set ";
			//
			//审批结果校验
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//重新审批(审批结果)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//街道
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//区县
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//市局
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//不是重新审批(审批结果)
				if("1".equals(jendcheck)){	//终审审批
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//下级审批	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//条件
			usql += "where policy_id = '"+jpid+"' " 
				+ "and exists (select * from ("+jallsql+") t where opt.family_id = t.family_id) ";
			//救助金不能为0
			if("1".equals(jtabid)){				//新增查询模块
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("更新审批表:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * 定额选中审批
	 * @param hashmap
	 * @return
	 */
	public StringBuffer moneyCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		String jmoney = hashmap.get("jmoney").toString();
		//
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";	
		//更新语句
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//上报名单(确认名单)
			//更新
			usql = "update biz_t_optcheck opt set ";
			//
			//审批结果校验
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//重新审批(审批结果)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//街道
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//区县
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//市局
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//不是重新审批(审批结果)
				if("1".equals(jendcheck)){	//终审审批
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "checkmoney = '"+jmoney+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//下级审批	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ "checkmoney = '"+jmoney+"',"
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//条件
			usql += "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") ";
			//救助金不能为0
			if("1".equals(jtabid)){				//新增查询模块
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("更新审批表:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * 定额全部审批
	 * @param hashmap
	 * @return
	 */
	public StringBuffer allMoneyCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jallsql = hashmap.get("jallsql").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		String jmoney = hashmap.get("jmoney").toString();
		//
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";	
		//更新语句
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//上报名单(确认名单)
			//更新
			usql = "update biz_t_optcheck opt set ";
			//
			//审批结果校验
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//重新审批(审批结果)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//街道
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//区县
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//市局
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//不是重新审批(审批结果)
				if("1".equals(jendcheck)){	//终审审批
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "checkmoney = '"+jmoney+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//下级审批	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ "checkmoney = '"+jmoney+"',"
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//条件
			usql += "where policy_id = '"+jpid+"' " 
				+ "and exists (select * from ("+jallsql+") t where opt.family_id = t.family_id) ";
			//救助金不能为0
			if("1".equals(jtabid)){				//新增查询模块
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("更新审批表:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * 获取审批流程
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaFlowForXml(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//        
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		PolicyQuery policyquery = new PolicyQuery();
		Policycheckpage policycheckpage = new Policycheckpage();
		//审批权限标识    
        HashMap hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid); 
        String onecheck = hashmappower.get("onecheck").toString();
        String endcheck = hashmappower.get("endcheck").toString();
        String usercheck = hashmappower.get("usercheck").toString();
        String morecheck = hashmappower.get("morecheck").toString();
        String usermorecheck = hashmappower.get("usermorecheck").toString();
        String report = hashmappower.get("report").toString();
		//审批流程		
		HashMap hashmapflow = policyquery.getPolicyFlowsFromId(jpid);
		String depth1 = hashmapflow.get("depth1").toString();
		String depth2 = hashmapflow.get("depth2").toString();
		String depth3 = hashmapflow.get("depth3").toString();
		String depth4 = hashmapflow.get("depth4").toString();
		String depth5 = hashmapflow.get("depth5").toString();
		
		//
		String optid = "";
		String checkflag1 = "1";
		String checkflag2 = "1";
		String checkflag3 = "1";
		String checkflag4 = "1";
		String checkflag5 = "1";
		String sql = "select t.optcheck_id," 
			+ "t.checkflag1," 
			+ "t.checkflag2," 
			+ "t.checkflag3," 
			+ "t.checkflag4," 
			+ "t.checkflag5 "
			+ "from biz_t_optcheck t "
			+ "where t.family_id = '"+jfmids+"' "
			+ "and t.policy_id = '"+jpid+"' "
			+ "order by t.member_id";
		//
		//=============================================table
		shtml.append("<table id = 'ideaflowtb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
//  		shtml.append("<tr class='colField'>");
//  		shtml.append("<td height='25'>机构描述</td>");
//  		shtml.append("<td height='25'>审批结果</td>");
//  		shtml.append("<td height='25'>详细</td>");
//  		shtml.append("</tr>");
  		//=============================th
        //=============================tr
        //log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //
            if (rs.next()) {
            	optid = rs.getString("optcheck_id");
            	checkflag1 = rs.getString("checkflag1");
            	checkflag2 = rs.getString("checkflag2");
            	checkflag3 = rs.getString("checkflag3");
            	checkflag4 = rs.getString("checkflag4");
            	checkflag5 = rs.getString("checkflag5");
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        //
        
		//
        //社区
        if("1".equals(depth1)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_5+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag1)+"</td>");
      		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_5 +"','"+optid+"')\">详细</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>无单户审批详细信息</td>");
      		}
    		shtml.append("</tr>");
    		//
    		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
	    		//审批详细
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_5);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
      		//
        }
        //街道
        if("1".equals(depth2)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_4+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag2)+"</td>");
      		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_4 +"','"+optid+"')\">详细</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>无单户审批详细信息</td>");
      		}
    		shtml.append("</tr>");
    		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
	    		//审批详细
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_4);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //区县
        if("1".equals(depth3)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_3+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag3)+"</td>");
      		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_3 +"','"+optid+"')\">详细</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>无单户审批详细信息</td>");
      		}
    		shtml.append("</tr>");
    		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
	    		//审批详细
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_3);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //市
        if("1".equals(depth4)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_2+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag4)+"</td>");
      		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_2 +"','"+optid+"')\">详细</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>无单户审批详细信息</td>");
      		}
    		shtml.append("</tr>");
    		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
	    		//审批详细
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_2);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //省
        if("1".equals(depth5)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_1+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag5)+"</td>");
      		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_1 +"','"+optid+"')\">详细</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>无单户审批详细信息</td>");
      		}
    		shtml.append("</tr>");
    		//审批详细信息
      		if("0".equals(morecheck)){	//业务批量审批标识
	    		//审批详细
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_1);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //=============================tr        
        shtml.append("</table>");
        //=============================================table
		return shtml;
	}
	/**
	 * 审批详细信息
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaFlowDesrForXml(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		Policycheckpage policycheckpage = new Policycheckpage();
		PolicyQuery policyquery = new PolicyQuery();
		//
		String jpid = hashmap.get("jpid").toString();	
		String joptdepth = hashmap.get("joptdepth").toString();		   
		String joptid = hashmap.get("joptid").toString();
		//
		//第一审批机构
		String onedepth = policyquery.getPolicyOneFlowFromId(jpid);
		//		
		String sql = "select optcheckidea_id,"
			+ "optcheck_id,"
			+"appideaman,"
			+"appmandelnum,"
			+"appmanoknum,"
			+"appmannum,"
			+"appmannotnum,"
			+"appresult,"
			+"apparea,"
			+"to_char(apptime, 'yyyy-mm-dd') apptime,"
			+"emp.name "
			+"from biz_t_optcheckidea opt, sys_t_empext emp "
			+"where opt.checkoper = emp.employee_id "
			+"and opt.optcheck_id = '"+joptid+"' ";
		//
		sql += "and opt.depth = '"+joptdepth+"' " ;
		sql += "order by apptime desc";
		//
		//=============================================table
		shtml.append("<table id = 'ideaflowdesctb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
  		shtml.append("<tr style='text-align: center;font-size:9pt;background-color:#F2F5F7;'>");
  		//
  		shtml.append("<td height='25'>审批结果</td>");
  		shtml.append("<td height='25'>审批日期</td>");
  		shtml.append("<td height='25'>审批意见</td>");
  		if(joptdepth.equals(onedepth)){		//第一审批机构
  			shtml.append("<td height='25'>居民代表人数</td>");
  	  		shtml.append("<td height='25'>同意人数</td>");
  	  		shtml.append("<td height='25'>弃权人数</td>");
  	  		shtml.append("<td height='25'>不同意人数</td>");
  	  		shtml.append("<td height='25'>居民代表</td>");
  		}else{							//不是第一审批机构
  			shtml.append("<td height='25'>评议审批人</td>");
  		}
  		shtml.append("<td height='25'>操作人</td>");
  		//
  		shtml.append("</tr>");
  		//=============================th
  		int irow = 0;
  		//log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	//=============================tr 
            	shtml.append("<tr>");
                //
          		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(rs.getString("appresult"))+"</td>");
          		shtml.append("<td height='25' class='colValue'>"+rs.getString("apptime")+"</td>");
          		shtml.append("<td height='25' class='colValue'>"+rs.getString("apparea")+"</td>");
          		if(joptdepth.equals(onedepth)){	//第一审批机构
          			shtml.append("<td height='25' class='colValue'>"+rs.getString("appmannum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+rs.getString("appmanoknum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+rs.getString("appmandelnum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+rs.getString("appmannotnum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+getIdeaPerson(rs.getString("appideaman"))+"</td>");
          		}else{							//不是第一审批机构
          			shtml.append("<td height='25' class='colValue'>"+getIdeaPerson(rs.getString("appideaman"))+"</td>");
          		}
          		shtml.append("<td height='25' class='colValue'>"+rs.getString("name")+"</td>");
          		//
        		shtml.append("</tr>");
            	//=============================tr   
        		irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        } 
        if(irow==0){
        	shtml.append("<tr>");
        	shtml.append("<td height='25' class='colValue' colspan = '9'>无单户审批详细信息</td>");
        	shtml.append("</tr>");
        }
        shtml.append("</table>");
        //=============================================table
        //
		return shtml;
	}
	/**
	 * 审批居民代表
	 * @param ids
	 * @return
	 */
	public StringBuffer getIdeaPerson(String ids) {
		StringBuffer shtml= new StringBuffer("");
		int irow = 0;
		String sql = "select name " 
			+ "from biz_t_optreviewperson t " 
			+ "where optreviewperson_id in ("+ids+")";
		
		//log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	if(irow==0){
            		shtml.append(rs.getString("name"));
            	}else{
            		shtml.append("," + rs.getString("name"));
            	}
            	//
            	irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        } 
        if(irow==0){
        	shtml.append("无");
        }
		return shtml;
	}
}
