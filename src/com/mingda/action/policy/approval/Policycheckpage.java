package com.mingda.action.policy.approval;

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
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;
import com.mingda.action.policy.sql.PolicyPhySQL;
import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.myjdbc.ConnectionManager;

public class Policycheckpage {
	static Logger log = Logger.getLogger(Policycheckpage.class);
	   /**
	 * 转换审批结果为中文说明
	 * @param checkid
	 * @return
	 */
	public String repalcePolicyCheckFlag(String checkid){
		
		String srep = "";
		//常量定义
	    ConstantDefine constantdefine = new ConstantDefine();
	    if(checkid.equals(constantdefine.POLICYCHECKCODE_NEW)){
	    	srep = constantdefine.POLICYCHECK_NEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_OK)){
	    	srep = constantdefine.POLICYCHECK_OK;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_RENEW)){
	    	srep = constantdefine.POLICYCHECK_RENEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_MOVE)){
	    	srep = constantdefine.POLICYCHECK_MOVE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_OLE)){
	    	srep = constantdefine.POLICYCHECK_OLE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_STOP)){
	    	srep = constantdefine.POLICYCHECK_STOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_REM)){
	    	srep = constantdefine.POLICYCHECK_REM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_NOTOK)){
	    	srep = constantdefine.POLICYCHECK_NOTOK;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_NOT)){
	    	srep = constantdefine.POLICYCHECK_NOT;
	    }
		return srep;
	}
	/**
	 * 替换救助状态
	 * @param checkid
	 * @return
	 */
	public String repalcePolicyMoneyFlag(String checkid){
		String srep = "";
		//常量定义
	    ConstantDefine constantdefine = new ConstantDefine();
	    if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_NEW)){
	    	srep = constantdefine.POLICYCHECKMONEY_NEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_OLE)){
	    	srep = constantdefine.POLICYCHECKMONEY_OLE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_TOP)){
	    	srep = constantdefine.POLICYCHECKMONEY_TOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_BOM)){
	    	srep = constantdefine.POLICYCHECKMONEY_BOM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_REM)){
	    	srep = constantdefine.POLICYCHECKMONEY_REM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_STOP)){
	    	srep = constantdefine.POLICYCHECKMONEY_STOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_NOT)){
	    	srep = constantdefine.POLICYCHECKMONEY_NOT;
	    }
		return srep;
	}
	/**
	 * 获取业务审批排序选择框
	 * @param sname
	 * @param pid
	 * @return
	 */
	public StringBuffer getCheckPolicyOrderSelect(String sname,String pid){
		StringBuffer shtml= new StringBuffer("");
		String tftable,tfield,tfamily;
        String id = "",familyid = "",mastername = "",saltype = "",deptid = ""; 
        
        String stemp = "";
        //表查询处理类
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
        
        
		//添加保障人口、计算收入、上期救助金、拟发救助金(分城市、农村)
		//
    	//
		shtml.append("<select id=\""+sname+"\" style = \"font-size:12px\">");
		//家庭信息
		tftable = "INFO_T_FAMILY";//家庭表    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//
		//
		tfield = "FAMILYNO";//家庭编号
		familyid = tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		//
		stemp = "INFO_T_FAMILY.FAMILYNO";
		shtml.append("<option value=\""+stemp+"\">"+familyid+"</option>");
		
		tfield = "MASTERNAME";//户主姓名
		mastername = tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		//
		stemp = "INFO_T_FAMILY.MASTERNAME";
		shtml.append("<option value=\""+stemp+"\">"+mastername+"</option>");
		//	
		tfield = "SALTYPE";//保障类型
		saltype = tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		stemp = "INFO_T_FAMILY.SALTYPE";
		shtml.append("<option value=\""+stemp+"\">保障类型</option>");
		//
		tfield = "ORGANIZATION_ID";//家庭机构
		deptid = tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		//
		stemp = "INFO_T_FAMILY.ORGANIZATION_ID";
		shtml.append("<option value=\""+stemp+"\">"+deptid+"</option>");		
    	//
		stemp = "INFO_T_FAMILY.ENSURECOUNT";
		shtml.append("<option value=\""+stemp+"\">保障人口</option>");
		stemp = "INFO_T_FAMILY.CONSULTINCOME";
		shtml.append("<option value=\""+stemp+"\">计算收入</option>");
		//
		//处理成汇总字段
		stemp = "RECHECKMONEY";
		shtml.append("<option value=\""+stemp+"\">上期救助金</option>");
		stemp = "COUNTMONEY";
		shtml.append("<option value=\""+stemp+"\">拟发救助金</option>");
		//
		shtml.append("</select>");
		return shtml;
	}
	/**
	 * 获取保障类型[391字典值]
	 * @param sname
	 * @return
	 */
	public StringBuffer getCheckPolicySalTypeSelect(String sname){
		StringBuffer shtml= new StringBuffer("");
		String sql = "",stmpid = "",stmpname = "";
		//
		shtml.append("<select id=\""+sname+"\" style = \"font-size:12px\">");
		shtml.append("<option value=\"0\">全部</option>");
		//
		sql = "select dictionary_id," 
			+ "item  " 
			+ "from sys_t_dictionary " 
			+ "where dictsort_id = '391' and status = '1'";
		//
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
		    //设置参数
		    rs = pstmt.executeQuery();
		    while (rs.next()) {	
		    	stmpid = rs.getString("dictionary_id");
		    	stmpname = rs.getString("item");
		    	shtml.append("<option value=\""+stmpid+"\">"+stmpname+"</option>");		    	
		    }
		} catch (SQLException e) {
		    Log4jApp.logger(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		   // DBUtils.close(conn);                //关闭连接
		}
		//
		shtml.append("</select>");
		return shtml;
	}
	/**
	 * 生成XML文件
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getCheckResultForXml(HashMap hashmap) {
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuffer shtml= new StringBuffer("");
		PolicyPhySQL policyphysql = new PolicyPhySQL();
		Policychecksql policychecksql = new Policychecksql();
		PolicyQuery policyquery = new PolicyQuery();		
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        String jpid = hashmap.get("jpid").toString();
        //
        String jtabid = hashmap.get("jtabid").toString();
        //=================SQL获取方式================
        String jsqlmode = hashmap.get("jsqlmode").toString();
        //=================SQL获取方式================  
        String jexemode = hashmap.get("jexemode").toString();
        //
        //字典值
    	Document dictionary = (Document) hashmap.get("dictionary");
    	//
        //
        //审批权限标识
        HashMap hashmappower = new HashMap();        
        hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid); 
        String onecheck = hashmappower.get("onecheck").toString();
        String endcheck = hashmappower.get("endcheck").toString();
        String usercheck = hashmappower.get("usercheck").toString();
        String morecheck = hashmappower.get("morecheck").toString();
        String usermorecheck = hashmappower.get("usermorecheck").toString();
        String report = hashmappower.get("report").toString();
		//结算批次标识
        HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
        String optid = hashmapaccflag.get("optid").toString();
        String accflag = hashmapaccflag.get("accflag").toString();
        String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
        String accmoney = hashmapaccflag.get("accmoney").toString();
        //需要计算救助金标识
		String jaccmode = policyquery.getPolicyUserAccFlag(jpid);
		//结算批次信息
		String pdesc = policyquery.getPolicyDescFromId(jpid);
        String accdesc = policyaccquery.getNowPolicyAcc(optid);
        accdesc = "政策描述:" + pdesc +"  "+ accdesc ;
        //==============================================校验=========================================
        if("0".equals(usercheck)){							//无审批权限
        	shtml.append("该用户无该业务审批权限,无需审批信息!");
        	return shtml;
        }
        //==============================================================//
        if("-1".equals(accflag)){							//无结算批次        	
        	shtml.append("该业务未执行处理,等待建立结算信息!");
        	return shtml;
        }else if("0".equals(accflag)){						//新结算批次        	
        	
        }else if("1".equals(accflag)){						//正在结算批次        	
        	shtml.append(accdesc);
        	shtml.append("  提示:该业务正在执行结算处理,请等待!");
        	return shtml;
        }else if("2".equals(accflag)){						//结算完成批次        	
        	
        }
        //==============================================校验=========================================
        HashMap hashmapinfo = new HashMap();
        hashmapinfo = policyphysql.getCheckPolicyPhySql(hashmap,conn);
        String sql = hashmapinfo.get("sql").toString();
        String countsql = hashmapinfo.get("countsql").toString();
        String xmlth = hashmapinfo.get("xmlth").toString();
		//============================================================xml==============================
        //
		Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //=========================需要计算救助金标识=====================
        Element eAccmode=data.addElement("accmode");
        //分页SQL语句
        Element eAccmodeChild =eAccmode.addElement("accmode");
        eAccmodeChild.setText(jaccmode);
        //=========================需要计算救助金标识=====================
        
        //=================================================================查询汇总BEG 
        String sqlcount = hashmapinfo.get("sqlcount").toString();
        String sumpopcount = hashmapinfo.get("sumpopcount").toString();
        //格式化财务数据
        String summoney = hashmapinfo.get("summoney").toString();	
        String sumolemoney = hashmapinfo.get("sumolemoney").toString();
        String sumnewmoney = hashmapinfo.get("sumnewmoney").toString();
         
        Element eCount=data.addElement("counts");
        //表格记录总数
        Element eCountChild =eCount.addElement("sqlcount");
        eCountChild.setText(sqlcount);
        //表格保障人口总数
        eCountChild =eCount.addElement("sumpopcount");
        eCountChild.setText(sumpopcount); 
        //表格计算收入汇总
        eCountChild =eCount.addElement("summoney");
        eCountChild.setText(summoney); 
        //表格上期救助金汇总
        eCountChild =eCount.addElement("sumolemoney");
        eCountChild.setText(sumolemoney); 
        //表格拟发救助金汇总
        eCountChild =eCount.addElement("sumnewmoney");
        eCountChild.setText(sumnewmoney); 
        //=================================================================查询汇总END 
        //=================================================================结算完成BEG
        Element eAccFlag=data.addElement("accflag");        
        Element eAccFlagChild =eAccFlag.addElement("accflag");
        eAccFlagChild.setText(accflag);
        //
        Element eAccDesc=data.addElement("accdesc");        
        Element eAccDescChild =eAccDesc.addElement("accdesc");
        eAccDescChild.setText(accdesc);
        //=================================================================结算完成END
        //=================================================================审批标识BEG
        Element eFlag=data.addElement("flags");        
        Element eFlagChild =eFlag.addElement("onecheck");
        eFlagChild.setText(onecheck);
        eFlagChild =eFlag.addElement("endcheck");
        eFlagChild.setText(endcheck);
        eFlagChild =eFlag.addElement("usercheck");
        eFlagChild.setText(usercheck);
        eFlagChild =eFlag.addElement("morecheck");
        eFlagChild.setText(morecheck);
        eFlagChild =eFlag.addElement("usermorecheck");
        eFlagChild.setText(usermorecheck);
        eFlagChild =eFlag.addElement("report");
        eFlagChild.setText(report);
        //获取表格
        //
        HashMap hashmaptable = new HashMap();
        //
        hashmaptable.put("jtabid", jtabid);
        //
        hashmaptable.put("sql", sql);
        hashmaptable.put("countsql", countsql);
        hashmaptable.put("xmlth", xmlth);
        //
        hashmaptable.put("accflag", accflag);
        //
        hashmaptable.put("onecheck", onecheck);
        hashmaptable.put("endcheck", endcheck);
        hashmaptable.put("usercheck", usercheck);
        hashmaptable.put("morecheck", morecheck);
        hashmaptable.put("usermorecheck", usermorecheck);
        hashmaptable.put("report", report);
        //字典值列表
        hashmaptable.put("dictionary",dictionary);
        //=================================================================审批标识END
        //
        //=================================================================构造表格BEG
        if(!"sql".equals(jsqlmode)){
            String stable = policychecksql.getCheckPageTable(hashmaptable).toString();
            Element eTable=data.addElement("table");
            Element eTableChild =eTable.addElement("elements");
            eTableChild.setText(stable);
    	}
        //==============SQL信息==========================================
        Element eSql=data.addElement("sql");
        //分页SQL语句
        Element eSqlChild =eSql.addElement("sql");
        log.debug(sql);
        eSqlChild.setText(sql);
        //SQL语句
        eSqlChild =eSql.addElement("countsql");
        eSqlChild.setText(countsql);
        //SQL语句列名称
        eSqlChild =eSql.addElement("xmlth");
        eSqlChild.setText(xmlth);
        //==============SQL信息==========================================
        
        //=================================================================构造表格END
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML());
        shtml.append(node.asXML());
        //============================================================xml==============================
		return shtml;
	}
	/**
	 * 撤销审批结果
	 * @param hashmap
	 * @return
	 */
	public StringBuffer DelIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String snewcheck = "1",sifover = "5",scheckflag = "checkflag1";
		String snextifover = "4",snextcheckflag = "checkflag2";
		String sql = "";
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        String jpid = hashmap.get("jpid").toString();        
        String jfmids = hashmap.get("jfmids").toString();
        String jendcheck = hashmap.get("jendcheck").toString();
        String jreport = hashmap.get("jreport").toString();
        //未审批
        snewcheck = constantdefine.POLICYCHECKCODE_NEW;
        //是否可以撤销审批意见
        boolean bTmp  = existsDelIdeaFlag(hashmap);
        if(!bTmp){
        	shtml.append("不能撤销(原因:上级已经评议审批)!");
        	return shtml;
        }
        //撤销审批意见
		if ("1".equals(jreport)) { // 上报名单(确认名单)
			if ("1".equals(jendcheck)) { // 终审机构
				sifover = jdepth;
				//
				if (sifover.equals(constantdefine.POLICYQUERYCODE_5)) { // 社区
					scheckflag = "checkflag1";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
					scheckflag = "checkflag2";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
					scheckflag = "checkflag3";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
					scheckflag = "checkflag4";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
					scheckflag = "checkflag5";
				}
				//更新为未审批状态
				sql = "update biz_t_optcheck opt "
					+ "set result = '"+snewcheck+"',"
					+ "resultoper = '"+jempid+"',"
					+ "resultdt = sysdate,"
					+ scheckflag + " = '"+snewcheck+"' "
					+ "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") " ;//本级审批状态
			} else { 					// 不是终审机构
				sifover = jdepth;
				PolicyQuery policyquery = new PolicyQuery();
				snextifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
				//
				if (sifover.equals(constantdefine.POLICYQUERYCODE_5)) { // 社区
					scheckflag = "checkflag1";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
					scheckflag = "checkflag2";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
					scheckflag = "checkflag3";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
					scheckflag = "checkflag4";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
					scheckflag = "checkflag5";
				}
				if (snextifover.equals(constantdefine.POLICYQUERYCODE_5)) { // 社区
					snextcheckflag = "checkflag1";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
					snextcheckflag = "checkflag2";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
					snextcheckflag = "checkflag3";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
					snextcheckflag = "checkflag4";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
					snextcheckflag = "checkflag5";
				}
				//更新为未审批状态
				sql = "update biz_t_optcheck opt "
					+ "set ifover = '"+sifover+"'," 
					+ "result = '"+snewcheck+"',"
					+ "resultoper = '"+jempid+"',"
					+ "resultdt = sysdate,"
					+ scheckflag + " = '"+snewcheck+"' "
					+ "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") " ;//上级为未审批状态
			}
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append(pubilccomm.ExeSQLFromUserSQL(sql));
		}else{
			shtml.append("该用户没有撤销审批意见权限!");
		}
		return shtml;
	}
	/**
	 * 是否可以撤销审批结果标识
	 * @param hashmap
	 * @return
	 */
	public boolean existsDelIdeaFlag(HashMap hashmap) {
		boolean bReturn  = false;
		//常量定义
		ConstantDefine constantdefine = new ConstantDefine();
		String snewcheck = "1",sremcheck = "3",sifover = "5",scheckflag = "checkflag1";
		String snextifover = "4",snextcheckflag = "checkflag2";
		String sql = "";
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        String jpid = hashmap.get("jpid").toString();        
        String jfmids = hashmap.get("jfmids").toString();
        String jendcheck = hashmap.get("jendcheck").toString();
        String jreport = hashmap.get("jreport").toString();
        //未审批
        snewcheck = constantdefine.POLICYCHECKCODE_NEW;
        //
		if ("1".equals(jendcheck)) { // 终审机构
			sifover = jdepth;
			//
			if (sifover.equals(constantdefine.POLICYQUERYCODE_5)) { // 社区
				scheckflag = "checkflag1";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
				scheckflag = "checkflag2";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
				scheckflag = "checkflag3";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
				scheckflag = "checkflag4";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
				scheckflag = "checkflag5";
			}
			//是否存在家庭
			sql = "select family_id "
				+ "from biz_t_optcheck opt "
				+ "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") " 
				+ "and ifover = '"+sifover+"' ";		//本级审批状态
		} else { 					// 不是终审机构
			sifover = jdepth;
			PolicyQuery policyquery = new PolicyQuery();
			snextifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
			//
			if (sifover.equals(constantdefine.POLICYQUERYCODE_5)) { // 社区
				scheckflag = "checkflag1";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
				scheckflag = "checkflag2";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
				scheckflag = "checkflag3";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
				scheckflag = "checkflag4";
			} else if (sifover.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
				scheckflag = "checkflag5";
			}
			if (snextifover.equals(constantdefine.POLICYQUERYCODE_5)) { // 社区
				snextcheckflag = "checkflag1";
			} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_4)) {// 街道
				snextcheckflag = "checkflag2";
			} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_3)) {// 区县
				snextcheckflag = "checkflag3";
			} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_2)) {// 市局
				snextcheckflag = "checkflag4";
			} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_1)) {// 省厅
				snextcheckflag = "checkflag5";
			}
			//更新为未审批状态
			sql = "select family_id "
				+ "from biz_t_optcheck opt "
				+ "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") " 
				+ "and ((ifover = '"+sifover+"' and "+ scheckflag + " <> '"+snewcheck+"') " 
				+ "or (ifover = '"+snextifover+"' and "+snextcheckflag+" = '"+snewcheck+"')) ";//上级为未审批状态
		}
		//log.debug(sql);
		//
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
		    //设置参数
		    rs = pstmt.executeQuery();
		    if (rs.next()) {	
		    	bReturn = true;
		    }
		} catch (SQLException e) {
		    Log4jApp.logger(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		   // DBUtils.close(conn);                //关闭连接
		}
		return bReturn;
	}
}
