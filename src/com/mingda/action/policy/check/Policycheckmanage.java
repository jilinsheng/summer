package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageChild;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

public class Policycheckmanage {
	static Logger log = Logger.getLogger(Policycheckmanage.class);
    /**
     * 插入审批表
     * @param hashmap
     * @return
     */
    public String insertPolicyCheck(HashMap hashmap) {
    	String srep = "";
    	//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//业务核算类型
		String moneyflag = hashmap.get("moneyflag").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		String empid = hashmap.get("empid").toString();
		//
		
		String sql = "insert into biz_t_optcheck " +
				"(optcheck_id,policy_id,family_id,member_id,acctype,moneyflag,optoper,optdt)" +
				" values " +
				"(xoptcheck_id.nextval,'"+pid+"','"+fmid+"','"+memid+"','"+acctype+"','"+moneyflag+"','"+empid+"',sysdate)";
		//log.debug(sql);
	      
  	  	Connection conn = null;                 //声明Connection对象
  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
  	  	try {
  	  		conn = DBUtils.getConnection();     //获取数据库连接
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  	  		 
  	  		conn.commit();                      //关闭
  	  		//
  	  		srep = "插入审批表操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "插入审批表操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		//DBUtils.close(conn);                //关闭连接
  	  	}
    	return srep;
    }
    /**
     * 更新审批表救助金
     * @param hashmap
     * @return
     */
    public String updatePolicyCheck(HashMap hashmap) {
    	String srep = "",sql = "";
    	//
    	String accmode = hashmap.get("accmode").toString();
		String checkid = hashmap.get("checkid").toString();
		String matchflag = hashmap.get("matchflag").toString();
		String moneyflag = hashmap.get("moneyflag").toString();
		String matchmoney = hashmap.get("matchmoney").toString();
		String ifover = hashmap.get("ifover").toString();
		//
		//是否计算救助金
		if("0".equals(accmode)){
			sql = "update biz_t_optcheck " +
				"set " +
					"moneyaout = '"+matchflag+"', " +
					"moneyflag = '"+moneyflag+"', " +
					"checkmoney = '"+matchmoney+"', " +
					"ifover = '"+ifover+"' " +
				"where optcheck_id = '"+checkid+"'";
		}else{
			sql = "update biz_t_optcheck " +
				"set " +
					"moneyaout = '"+matchflag+"', " +
					"moneyflag = '"+moneyflag+"', " +
					"ifover = '"+ifover+"' " +
				"where optcheck_id = '"+checkid+"'";
		}
		
		//log.debug(sql);
	      
  	  	Connection conn = null;                 //声明Connection对象
  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
  	  	try {
  	  		conn = DBUtils.getConnection();     //获取数据库连接
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  	  		 
  	  		conn.commit();                      //关闭
  	  		//
  	  		srep = "更新审批表操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "更新审批表操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		//DBUtils.close(conn);                //关闭连接
  	  	}
    	return srep;
    }
    /**
     * 更新单个家庭或者成员标准状态
     * @param checkid
     * @return
     */
    public String updatePolicyMoneyFlag(String checkid) {
    	String srep = "",sql = "",moneyflag = "";
    	String pid = "",fmid = "";
    	//政策业务标准处理类
    	PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
    	//
    	HashMap hashmap = new HashMap();
    	hashmap = policycheckmatch.GetPolciyMatchMoneyFamily(checkid);
    	pid = hashmap.get("pid").toString();
    	fmid = hashmap.get("fmid").toString();    	
    	//计算家庭救助标准状态(顺延、调高、降低)
    	moneyflag = policycheckmatch.GetPolciyMatchMoneyFlag(pid,fmid);
    	//
    	if(null == moneyflag || "".equals(moneyflag)){
    		return srep;
    	}
    	//
    	sql = "update biz_t_optcheck set moneyflag = '"+moneyflag+"' " +
    			"where policy_id = '"+pid+"' and family_id = '"+fmid+"'";
    	//log.debug(sql);
	      
  	  	Connection conn = null;                 //声明Connection对象
  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
  	  	try {
  	  		conn = DBUtils.getConnection();     //获取数据库连接
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  	  		 
  	  		conn.commit();                      //关闭
  	  		//
  	  		srep = "更新审批表操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "更新审批表操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		//DBUtils.close(conn);                //关闭连接
  	  	}
    	return srep;
    }
    /**
     * 删除不符合标准家庭或者成员
     * 顺延的家庭或者成员更新为不达标(不符合标准)
     * @param hashmap
     * @return
     */
    public String deletePolicyCheck(HashMap hashmap) {
    	String srep = "",sql = "";
    	//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	//
		String checkid = hashmap.get("checkid").toString();
		String moneyflag = hashmap.get("moneyflag").toString();
		//
		String moneynewflag = constantdefine.POLICYNEWCHECKCODE_NEW;		
		//
		if(moneynewflag.equals(moneyflag)){
			//直接删除(包括审批意见)
			//
			String sql1  = "delete biz_t_optchecktype t " +
					"where exists (select optchecktype_id from biz_t_optchecktype a,biz_t_optcheckidea b,biz_t_optcheck c " +
						"where a.optcheckidea_id = b.optcheckidea_id " +
							"and b.optcheck_id = c.optcheck_id " +
								"and c.optcheck_id = '"+checkid+"' " +
									"and t.optchecktype_id = a.optchecktype_id)";  //SQL
	        //log.debug(sql1);
			String sql2  = "delete biz_t_optcheckidea t " +
					"where exists (select optcheckidea_id from biz_t_optcheckidea a,biz_t_optcheck b " +
						"where a.optcheck_id = b.optcheck_id " +
							"and b.optcheck_id = '"+checkid+"' " +
								"and t.optcheckidea_id = a.optcheckidea_id )";  //SQL
			//log.debug(sql2);
			String sql3  = "delete biz_t_optcheck " +
					"where optcheck_id = '"+checkid+"'";  //SQL
			//log.debug(sql3);
			Connection conn = null;                 //声明Connection对象
	  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
	  	  	try {
	  	  		conn = DBUtils.getConnection();     //获取数据库连接
	  	  		conn.setAutoCommit(false);
	  	  		pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
	  	  		pstmt.execute();              //执行  	
	  	  		pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
	  	  		pstmt.execute();              //执行 
	  	  		pstmt = conn.prepareStatement(sql3);//根据sql创建PreparedStatement            
	  	  		pstmt.execute();              //执行 
	  	  		conn.commit();                      //关闭
	  	  		//
	  	  		srep = "更新审批表操作成功!";
	          
	  	  	} catch (SQLException e) {
	  	  		try {
	  	  			srep = "更新审批表操作失败!";
	  	  			conn.rollback();
	  	  		} catch (SQLException e1) {          
	  	  			e1.printStackTrace();
	  	  		}
	  	  	} finally {
	  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
	  	  		//DBUtils.close(conn);                //关闭连接
	  	  	}
		}else{
			//更新为不达标
			String matchflag = constantdefine.POLICYAOUTCHECKCODE_NOMATCH;
			sql = "update biz_t_optcheck " +
					"set moneyaout = '"+matchflag+"' " +
					"where optcheck_id = '"+checkid+"'";

			//log.debug(sql);
		      
	  	  	Connection conn = null;                 //声明Connection对象
	  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
	  	  	try {
	  	  		conn = DBUtils.getConnection();     //获取数据库连接
	  	  		conn.setAutoCommit(false);
	  	  		pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
	  	  		pstmt.execute();              //执行  	  		 
	  	  		conn.commit();                      //关闭
	  	  		//
	  	  		srep = "更新审批表操作成功!";
	          
	  	  	} catch (SQLException e) {
	  	  		try {
	  	  			srep = "更新审批表操作失败!";
	  	  			conn.rollback();
	  	  		} catch (SQLException e1) {          
	  	  			e1.printStackTrace();
	  	  		}
	  	  	} finally {
	  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
	  	  		//DBUtils.close(conn);                //关闭连接
	  	  	}
		}
		
    	return srep;
    }
    
    /**
     * 单个或批量业务审批意见和结果
     * @param hashmap
     * @return
     */
    public String setMoreCheckIdea(HashMap hashmap) {
    	String srep = "";
    	String empdepth = "",nextdepth = "",backdepth = "",onedepth = "",enddepth = "";
    	String checkid = "",checkmoney = "0",checkchildid = "",checkchildmoney = "0";
    	// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//业务审批查询处理类
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
		String pid = hashmap.get("pid").toString();//业务ID
		String allaccflag = hashmap.get("allaccflag").toString();//终审结算标识
		String preportflag = hashmap.get("preportflag").toString();//岗位确认名单标识
		String empid = hashmap.get("empid").toString();//用户ID
		//
		String reqtype = hashmap.get("reqtype").toString();//致困原因ID(11,12,13)
		String reptype = hashmap.get("reptype").toString();//审批结果ID		
		String repman = hashmap.get("repman").toString();//评议人名称(秀秀1,秀秀2)
		String repidea = hashmap.get("repidea").toString();//评议意见
		String repdt = hashmap.get("repdt").toString();//评议时间
		String repbegdt = hashmap.get("repbegdt").toString();//开始救助时间
		String rependdt = hashmap.get("rependdt").toString();//结束救助时间
		//
		String idsmoneys = hashmap.get("idsmoneys").toString();//审批表ID和救助金(2222,122.50;2223,210.23)
		//分类施保审批
		String idschildmoneys = hashmap.get("idschildmoneys").toString();//分类施保审批表ID和分类施保救助金(2222,122.50;2223,210.23)
		//
		if(allaccflag.equals("1")){//已经终审结算
        	srep = "业务已经终审结算,无法计算拟发金额!";
    		return srep;
        }else if(allaccflag.equals("2")){
        	srep = "业务未办理,无法计算拟发金额!";
    		return srep;
        }
		//
		//机构审批级别
	    empdepth = tableinfoquery.getempdepth(empid);
	    nextdepth = policymanagecheckquery.getPolicyNextFlowFromId(pid,empid);
	    backdepth = policymanagecheckquery.getPolicyBackFlowFromId(pid,empid);
	    onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
	    enddepth = policymanagecheckquery.getPolicyEndFlowFromId(pid);
		//基本保障审批	    
	    String [] aidsmoneys = idsmoneys.split(";");
	    for(int i=0;i<aidsmoneys.length;i++){
	    	String idmoney = aidsmoneys[i];
	    	String [] aidmoney = idmoney.split(",");	    	
	    	checkid = aidmoney[0];
	    	checkmoney = aidmoney[1];
	    	//
	    	//评议属性
			HashMap hashmapone = new HashMap();
			hashmapone.put("empid", empid);
			hashmapone.put("empdepth", empdepth);
			hashmapone.put("nextdepth", nextdepth);
			hashmapone.put("backdepth", backdepth);
			hashmapone.put("onedepth", onedepth);
			hashmapone.put("enddepth", enddepth);
			//
			hashmapone.put("preportflag", preportflag);
			//
			hashmapone.put("reqtype", reqtype);
			hashmapone.put("reptype", reptype);
			hashmapone.put("repman", repman);
			hashmapone.put("repidea", repidea);
			hashmapone.put("repdt", repdt);
			hashmapone.put("repbegdt", repbegdt);
			hashmapone.put("rependdt", rependdt);
			//
			hashmapone.put("checkid", checkid);
			hashmapone.put("checkmoney", checkmoney);
			//单个审批意见和结果
			srep = setOneCheckIdea(hashmapone);
			//
    		//更新单个家庭或者成员救助标准状态
        	updatePolicyMoneyFlag(checkid);
	    }
	    boolean bolchild = false ;
	    PolicyManageChild policyManageChild =new PolicyManageChild();
	    bolchild = policyManageChild. existsPolicyChildFromId(pid);
	    if (bolchild){
	    	//分类施保审批	    
		    String [] achildidsmoneys = idschildmoneys.split(";");
		    for(int i=0;i<achildidsmoneys.length;i++){
		    	String childidmoney = achildidsmoneys[i];
		    	String [] achildidmoney = childidmoney.split(",");	    	
		    	checkchildid = achildidmoney[0];
		    	checkchildmoney = achildidmoney[1];
			    //评议属性
				HashMap hashmapchildone = new HashMap();
				hashmapchildone.put("empid", empid);
				hashmapchildone.put("empdepth", empdepth);
				hashmapchildone.put("repdt", repdt);
				hashmapchildone.put("checkchildid", checkchildid);
				hashmapchildone.put("checkchildmoney", checkchildmoney);
				//单个审批分类施保
			    setOneCheckChildIdea(hashmapchildone);
		    }
	    }	    
	    //
    	return srep;
    }
    /**
     * 全部(sql语句)业务审批意见和结果
     * @param hashmap
     * @return
     */
    public String setAllCheckIdeaFamily(HashMap hashmap) {    	
		String srep = "";
    	String empdepth = "",nextdepth = "",backdepth = "",onedepth = "",enddepth = "";
    	String fmid = "";
    	// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//业务审批查询处理类
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
		String pid = hashmap.get("pid").toString();//业务ID
		String allaccflag = hashmap.get("allaccflag").toString();//终审结算标识
		String preportflag = hashmap.get("preportflag").toString();//岗位确认名单标识
		String empid = hashmap.get("empid").toString();//用户ID
		//
		String reqtype = hashmap.get("reqtype").toString();//致困原因ID(11,12,13)
		String reptype = hashmap.get("reptype").toString();//审批结果ID		
		String repman = hashmap.get("repman").toString();//评议人名称(秀秀1,秀秀2)
		String repidea = hashmap.get("repidea").toString();//评议意见
		String repdt = hashmap.get("repdt").toString();//评议时间
		String repbegdt = hashmap.get("repbegdt").toString();//开始救助时间
		String rependdt = hashmap.get("rependdt").toString();//结束救助时间
		//
		String selsql = hashmap.get("selsql").toString();//全部语句SQL
		//		
		//
		if(allaccflag.equals("1")){//已经终审结算
        	srep = "业务已经终审结算,无法计算拟发金额!";
    		return srep;
        }else if(allaccflag.equals("2")){
        	srep = "业务未办理,无法计算拟发金额!";
    		return srep;
        }
		//
		//机构审批级别
	    empdepth = tableinfoquery.getempdepth(empid);
	    nextdepth = policymanagecheckquery.getPolicyNextFlowFromId(pid,empid);
	    backdepth = policymanagecheckquery.getPolicyBackFlowFromId(pid,empid);
	    onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
	    enddepth = policymanagecheckquery.getPolicyEndFlowFromId(pid);
		//
	    String sql = selsql;
	    //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //参数
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	fmid = rs.getString("FAMILY_ID");
            	//
            	//评议属性
        		HashMap hashmapone = new HashMap();
        		hashmapone.put("pid", pid);
        		hashmapone.put("empid", empid);
        		hashmapone.put("empdepth", empdepth);
        		hashmapone.put("nextdepth", nextdepth);
        		hashmapone.put("backdepth", backdepth);
        		hashmapone.put("onedepth", onedepth);
        		hashmapone.put("enddepth", enddepth);
        		//
        		hashmapone.put("preportflag", preportflag);
        		//
        		hashmapone.put("reqtype", reqtype);
        		hashmapone.put("reptype", reptype);
        		hashmapone.put("repman", repman);
        		hashmapone.put("repidea", repidea);
        		hashmapone.put("repdt", repdt);
        		hashmapone.put("repbegdt", repbegdt);
        		hashmapone.put("rependdt", rependdt);
        		//
        		hashmapone.put("fmid", fmid);
        		//单个家庭审批
        		srep = setOneCheckIdeaFamily(hashmapone);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
	    
	    //
    	return srep;
    }
    /**
     * 单个家庭或者成员评议审批意见和结果
     * @param hashmap
     * @return
     */
    public String setOneCheckIdeaFamily(HashMap hashmap) {
    	String srep = "",sql = "";
    	String checkid = "",checkmoney = "0";
    	//
    	String pid = hashmap.get("pid").toString();//业务ID
		String empid = hashmap.get("empid").toString();//用户ID
		String preportflag = hashmap.get("preportflag").toString();//岗位确认名单标识
		//
		String empdepth = hashmap.get("empdepth").toString();//用户机构级别
		String nextdepth = hashmap.get("nextdepth").toString();//用户上级审批机构级别
		String backdepth = hashmap.get("backdepth").toString();//用户下级审批机构级别
		String onedepth = hashmap.get("onedepth").toString();//业务第一审批机构级别
		String enddepth = hashmap.get("enddepth").toString();//业务最后审批机构级别
		//
		String reqtype = hashmap.get("reqtype").toString();//致困原因ID
		String reptype = hashmap.get("reptype").toString();//审批结果ID		
		String repman = hashmap.get("repman").toString();//评议人名称(秀秀1,秀秀2)
		String repidea = hashmap.get("repidea").toString();//评议意见
		String repdt = hashmap.get("repdt").toString();//评议时间
		String repbegdt = hashmap.get("repbegdt").toString();//开始救助时间
		String rependdt = hashmap.get("rependdt").toString();//结束救助时间
		//
		String fmid = hashmap.get("fmid").toString();//单个家庭ID
		//
		sql = "select optcheck_id,checkmoney from biz_t_optcheck " +
				"where policy_id = '"+pid+"' and family_id = '"+fmid+"'";
		//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //参数
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	checkid = rs.getString("optcheck_id");
            	checkmoney = rs.getString("checkmoney");
            	
        	    //评议属性
        		HashMap hashmapone = new HashMap();
        		hashmapone.put("empid", empid);
        		hashmapone.put("empdepth", empdepth);
        		hashmapone.put("nextdepth", nextdepth);
        		hashmapone.put("backdepth", backdepth);
        		hashmapone.put("onedepth", onedepth);
        		hashmapone.put("enddepth", enddepth);
        		//
        		hashmapone.put("preportflag", preportflag);
        		//
        		hashmapone.put("reqtype", reqtype);
        		hashmapone.put("reptype", reptype);
        		hashmapone.put("repman", repman);
        		hashmapone.put("repidea", repidea);
        		hashmapone.put("repdt", repdt);
        		hashmapone.put("repbegdt", repbegdt);
        		hashmapone.put("rependdt", rependdt);
        		//
        		hashmapone.put("checkid", checkid);
        		hashmapone.put("checkmoney", checkmoney);
        		
        		//单个审批意见和结果
        		srep = setOneCheckIdea(hashmapone); 
        		//
        		//更新单个家庭或者成员救助标准状态
	        	updatePolicyMoneyFlag(checkid);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
		
		//
    	return srep;
    }
    /**
     * 单个家庭或者成员评议审批意见和结果
     * @param hashmap
     * @return
     */
    public String setOneCheckIdea(HashMap hashmap) {
    	String srep = "";
    	//
    	// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//常量定义
	    ConstantDefine constantdefine = new ConstantDefine(); 
    	//
		String empid = hashmap.get("empid").toString();//用户ID
		String preportflag = hashmap.get("preportflag").toString();//岗位确认名单标识
		//
		String empdepth = hashmap.get("empdepth").toString();//用户机构级别
		String nextdepth = hashmap.get("nextdepth").toString();//用户上级审批机构级别
		String backdepth = hashmap.get("backdepth").toString();//用户下级审批机构级别
		String onedepth = hashmap.get("onedepth").toString();//业务第一审批机构级别
		String enddepth = hashmap.get("enddepth").toString();//业务最后审批机构级别
		//
		String reqtype = hashmap.get("reqtype").toString();//致困原因ID
		String reptype = hashmap.get("reptype").toString();//审批结果ID		
		String repman = hashmap.get("repman").toString();//评议人名称(秀秀1,秀秀2)
		String repidea = hashmap.get("repidea").toString();//评议意见
		String repdt = hashmap.get("repdt").toString();//评议时间
		String repbegdt = hashmap.get("repbegdt").toString();//开始救助时间
		String rependdt = hashmap.get("rependdt").toString();//结束救助时间
		//单个
		String checkid = hashmap.get("checkid").toString();//单个审批表ID
		String checkmoney = hashmap.get("checkmoney").toString();//单个审批救助金		
		//
		 
	    //重新核查
	    String stemptype = constantdefine.POLICYCHECKCODE_RENEW;	    
	    //
	    String sql2="",temp = "";
	    if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){//社区级别		     
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//重新核查  			  
	    		temp = "checkflag1 	  = '"+reptype+"'";
	    		sql2 += temp;
	    	}else{
	    		temp = "checkflag1 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//用户所属岗位有审批名单确认权限
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}    			  
	    	}
	    	//   		  
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//可以终审
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    	      	   		  
	    	}
	    	//救助金
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道级别		      
			 sql2 = "update biz_t_optcheck ";
			 temp = "set ";
			 sql2 += temp;
			 if(reptype.equals(stemptype)){//重新核查
				 temp = "checkflag2 	  = '"+reptype+"'";
				 sql2 += temp;
				 if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
					 temp = ",checkflag1 	  = '"+reptype+"'";
					 sql2 += temp;
					 temp = ",ifover 	  = '"+backdepth+"'";
					 sql2 += temp;
				 }
				 
			 }else{
				 temp = "checkflag2 	  = '"+reptype+"'";
				 sql2 += temp;
				 if(preportflag.equals("1")){//用户所属岗位有审批名单确认权限
					 temp = ",ifover 	  = '"+nextdepth+"'";
					 sql2 += temp;
				 }
			 }
			 //
			 if(!repbegdt.equals("")&& !rependdt.equals("")){
				 temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
				 sql2 += temp;
				 temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
				 sql2 += temp;
			 }
			 if(empdepth.equals(enddepth)){
				 //
				 //可以终审
				 temp = ",result 	  = '"+reptype+"'";
				 sql2 += temp;
				 temp = ",resultoper = '"+empid+"'";
				 sql2 += temp;
				 temp = ",resultdt   = sysdate";
				 sql2 += temp;	 
				 
			 }	
			 //
			 //救助金
			 temp = ",checkmoney   = '"+checkmoney+"'";
			 sql2 += temp;
			 //
			 temp = " where optcheck_id = '"+checkid+"'";
			 sql2 += temp;
			 //
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区级别
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//重新核查
	    		temp = "checkflag3 	  = '"+reptype+"'";
  	    	  	sql2 += temp;
  	    	  	if(backdepth.equals(constantdefine.POLICYQUERYCODE_4)){
  	    	  		temp = ",checkflag2 	  = '"+reptype+"'";
  	    	  		sql2 += temp;
  	    	  		temp = ",ifover 	  = '"+backdepth+"'";
  	    	  		sql2 += temp;
  	    	  	}
  	    	  	if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
  	    	  		temp = ",checkflag1 	  = '"+reptype+"'";
  	    	  		sql2 += temp;
  	    	  		temp = ",ifover 	  = '"+backdepth+"'";
  	    	  		sql2 += temp;
  	    	  	}    			  
	    	}else{
	    		temp = "checkflag3 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//用户所属岗位有审批名单确认权限
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}
	    	}
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//可以终审
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    		
	    	}
	    	//
	    	//救助金
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市级别
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//重新核查
	    		temp = "checkflag4 	  = '"+reptype+"'";
	    		sql2 += temp; 
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_3)){
	    			temp = ",checkflag3 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
      		    	  sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_4)){
	    			temp = ",checkflag2 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
	    			temp = ",checkflag1 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}    
	    	}else{
	    		temp = "checkflag4 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//用户所属岗位有审批名单确认权限
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}
	    	}
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//可以终审
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    		
	    	}
	    	//	
	    	//救助金
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省级别
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//重新核查
	    		temp = "checkflag5 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_2)){
	    			temp = ",checkflag4 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_3)){
	    			temp = ",checkflag3 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_4)){
	    			temp = ",checkflag2 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
	    			temp = ",checkflag1 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}    
	    	}else{
	    		temp = "checkflag5 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//用户所属岗位有审批名单确认权限
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}
	    	}
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//可以终审
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    				
	    	}
	    	//
	    	//救助金
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }
		//
	    //
	    String checkideaid = tableinfoquery.getseqfromname("XOPTCHECKIDEA_ID");
	    String sql1 = "insert into biz_t_optcheckidea " +
	      		"(optcheckidea_id,optcheck_id,depth,appideaman,apparea,rebegdt,reenddt,appresult,apptime,checkoper,checkdt) " +
	      		"values " +
	      		"("+checkideaid+","+checkid+",'"+empdepth+"','"+repman+"','"+repidea+"',to_date('"+repbegdt+"','yyyy-mm-dd'),to_date('"+rependdt+"','yyyy-mm-dd'),'"+reptype+"',to_date('"+repdt+"','yyyy-mm-dd'),'"+empid+"',sysdate)";
		//
		//log.debug("sql2:"+sql2);
		//log.debug("sql1:"+sql1);
	    
	    //SQL执行标识
	    boolean boolrep = false;
	    //
  	  	Connection conn = null;                 //声明Connection对象
  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
  	  	try {
  	  		conn = DBUtils.getConnection();     //获取数据库连接
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  
  	  		pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  	  		 
  	  		conn.commit();                      //关闭
  	  		//成功标识
  	  		boolrep=true;
  	  		//审批提示
            if(preportflag.equals("1")){//用户所属岗位有审批名单确认权限
            	if(empdepth.equals(enddepth)){//可以终审
            		srep = "评议操作成功,意见将拟为终审意见!";
            	}else{
            		if(reptype.equals(stemptype)){//重新核查
            			if(empdepth.equals(onedepth)){
            				srep = "评议操作成功,名单进入重新核查!";
            			}else{
            				srep = "评议操作成功,名单退回重新核查!";
            			}
            		}else{
            			srep = "评议操作成功,名单进入上级审批!";
            		}
            	}
            }else{
            	srep = "评议操作成功!";
            }
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "评议审批操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		//DBUtils.close(conn);                //关闭连接
  	  	}
  	  	//成功标识
  	  	if(boolrep){
    		//添加审批家庭或成员困难原因
    		insertOneCheckIdeaReqType(checkideaid,reqtype);
    		//
  	  	}
  	  	//
    	return srep;
    }
    /**
     * 单个家庭或者成员分类施保
     * @param hashmap
     * @return
     */
    public String setOneCheckChildIdea(HashMap hashmap) {
    	String srep = "",sql1 = "",sql2 = "",childideaid = "";
    	//常量定义
	    ConstantDefine constantdefine = new ConstantDefine();
	    //分类施保处理类
	    PolicyChildCheckMatch policychildcheckmatch = new PolicyChildCheckMatch();
    	//
		String empid = hashmap.get("empid").toString();//用户ID
		String empdepth = hashmap.get("empdepth").toString();//用户机构级别
		String repdt = hashmap.get("repdt").toString();//评议时间
		//单个
		String checkchildid = hashmap.get("checkchildid").toString();//单个分类施保审批表ID
		String checkchildmoney = hashmap.get("checkchildmoney").toString();//单个分类施保审批救助金
		//
		String tempfild = "";
		if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){//社区级别
	    	tempfild = "itemmoney1";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道级别
	    	tempfild = "itemmoney2";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县级别
	    	tempfild = "itemmoney3";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局级别
	    	tempfild = "itemmoney4";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅级别
	    	tempfild = "itemmoney5";
	    }
	    //审批分类施保
		sql1 = "update biz_t_optcheckchild " +
				"set itemmoney = '"+checkchildmoney+"'" +
					","+tempfild+" = '"+checkchildmoney+"'" +
					",itemresultoper = '"+empid+"'" +
					",itemresultdt = sysdate " +
				"where optcheckchild_id = '"+checkchildid+"'";  //更新档次状态SQL
		//审批分类施保意见
		//取得分类施保该用户审批意见ID
		childideaid = policychildcheckmatch.getPolicyChildChickIdeas(empid,checkchildid);
	    if(childideaid.equals("")){	    	
	    	sql2 = "insert into biz_t_optcheckchildidea " +
					"(optcheckchildidea_id,optcheckchild_id,depth,childmoney,childapptime,childcheckoper,childcheckdt,status) " +
					"values " +
					"(xoptcheckchildidea_id.nextval,'"+checkchildid+"','"+empdepth+"','"+checkchildmoney+"',to_date('"+repdt+"','yyyy-mm-dd'),'"+empid+"',sysdate,'1')";  //更新档次状态SQL
	    }else{
	    	sql2 = "update biz_t_optcheckchildidea " +
					    "set childmoney = '"+checkchildmoney+"'," +
					    	"childapptime = to_date('"+repdt+"','yyyy-mm-dd')," +
					    	"childcheckdt = sysdate " +
					"where optcheckchildidea_id = '"+childideaid+"'";  //更新档次状态SQL
	    }
		//log.debug(sql1);
		//log.debug(sql2);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    conn.commit();                //关闭		   
		    srep = "更新分类施保审批金额操作成功!";
		} catch (SQLException e) {			
			try {
				srep = "更新分类施保审批金额操作失败!";
			      conn.rollback();
			  } catch (SQLException e1) {          
			      e1.printStackTrace();
			  }
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
    }
    /**
     * 添加评议审批困难原因
     * @param checkideaid
     * @param reqtype
     * @return
     */
    public String insertOneCheckIdeaReqType(String checkideaid,String reqtype){
    	String srep = "",sql = "",typeid = "";
	  	
	  	String [] atypeid = reqtype.split(",");
		int ilength = atypeid.length;
	  	for (int h=0;h<ilength;h++){		  		
	  		typeid = atypeid[h];
	  		//
		    sql = "insert into biz_t_optchecktype " +
		      		"(optchecktype_id,optcheckidea_id,reqtype) " +
		      		"values " +
		      		"(xoptchecktype_id.nextval,"+checkideaid+",'"+typeid+"')";
	    	  
		  	//log.debug(sql);
	        
			Connection conn = null;                 //声明Connection对象
			PreparedStatement pstmt = null;         //声明PreparedStatement对象
			try {
			    conn = DBUtils.getConnection();     //获取数据库连接
			    conn.setAutoCommit(false);
			    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
			    pstmt.execute();              //执行  
			    conn.commit();                      //关闭	            
			    srep = "添加审批困难原因操作成功!";
			} catch (SQLException e) {
			  try {
			    srep = "添加审批困难原因操作失败!";
			      conn.rollback();
			  } catch (SQLException e1) {          
			      e1.printStackTrace();
			  }
			} finally {
			    DBUtils.close(pstmt);               //关闭PreparedStatement
			    //DBUtils.close(conn);                //关闭连接
			}
	  	}		  	
	  	return srep;
  	}
}
