package com.mingda.action.policy.check;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageCheckManage;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

/**
 * 政策业务
 * 符合条件处理
 * 符合标准处理
 * @author xiu
 *
 */
public class PolicyCheckMatch {
	static Logger log = Logger.getLogger(PolicyCheckMatch.class);
	/**
	 * 符合业务标准
	 * 家庭或者成员
	 * @param hashmap
	 * @return
	 */
	public String SetNewPolciyMatchMore(String pid,String empid) {
		log.debug(System.currentTimeMillis());
		String srep = "";
		String physql = "",deptid = "";
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批查询处理
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//业务核算类型
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);		
		//
		//第一级审批机构
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//不是第一级审批机构
			srep = "登录机构不是业务第一级审批机构,无法生成名单!";
			return srep;
		}
		//业务是否已经终审结算标识
        String isallaccflag = policymanagecheckmanage.getPolicyAllAccFlag(pid,empid);
        if(isallaccflag.equals("1")){//已经终审结算
        	srep = "业务已经终审结算,无法计算拟发金额!";
    		return srep;
        }else if(isallaccflag.equals("2")){
        	srep = "业务未办理,无法计算拟发金额!";
    		return srep;
        }
		//用户机构
    	deptid = tableinfoquery.getempdepid(empid);
	    //
        String sql = "select standard_id,physql from doper_t_standard " +
						"where flag = '1' and policy_id = '"+pid+"' " +
							"order by planmoney desc";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	physql = rs.getString("physql");            	
            	if(null != physql){
            		//
                	HashMap hashmap = new HashMap();
                	hashmap.put("pid", pid);
                	hashmap.put("acctype", acctype);
                	hashmap.put("physql", physql);
                	hashmap.put("deptid", deptid);
                	hashmap.put("empid", empid);
                	//
                	SetNewPolciyMatchMoreSql(hashmap);
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        log.debug(System.currentTimeMillis());
        //
    	//更新新增家庭或成员救助金      
        log.debug(System.currentTimeMillis());
        UpdatePolciyMatchMoreNew(pid,empid);
        log.debug(System.currentTimeMillis());
        //
        return srep;
    } 
	public String SetNewPolciyMatchMoreSql(HashMap hashmap) {
		String srep = "",sql = "";
		String fmid = "",memid = "",moneyflag = "";
		//
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //业务审批处理
	    Policycheckmanage policycheckmanage = new Policycheckmanage();
	    //
	    String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();
		String physql = hashmap.get("physql").toString();
		String deptid = hashmap.get("deptid").toString();
		String empid = hashmap.get("empid").toString();
    	
		//
		int ibeg=0,iend=0;
    	ibeg = physql.indexOf("from");//第一次出现位置
    	iend = physql.indexOf("where");//第一次出现位置
    	if(ibeg==-1 || iend == -1)
    	{
    		return srep;
    	}
    	String tselect = physql.substring(0, ibeg);
		String tfrom = physql.substring(ibeg+5, iend);
		String twhere = physql.substring(iend+5, physql.length());
		//log.debug("tselect:"+tselect+"tfrom:"+tfrom +"twhere:" + twhere);
		//
		boolean isdept = false; 
	    String [] asfrom = tfrom.split(",");
	    int itflen = asfrom.length;	    
	    for (int y=0;y<itflen;y++){
	    	String sfrom = asfrom[y];
	    	if("SYS_T_ORGANIZATION".equals(sfrom) || "sys_t_organization".equals(sfrom)){
	    		isdept = true;
	    	}
	    }
    	if(!isdept){
    		tfrom += ",SYS_T_ORGANIZATION ";
    		twhere += " and INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID";
    		twhere += " and SYS_T_ORGANIZATION.ORGANIZATION_ID like '"+deptid+"%'";
    	}
    	//审批表(家庭核算)
    	String sqlfamily = "select a.family_id " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
    	//审批表(成员核算)
    	String sqlmember = "select a.family_id,a.member_id " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
    	//
    	sql = tselect+" from "+tfrom +" where " + twhere;
    	if("0".equals(acctype)){
			//家庭核算
        	sql += " minus " + sqlfamily;
		}else if("1".equals(acctype)){
			//成员核算			
        	sql += " minus " + sqlmember;
		}
    	//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	//
            	
            	if("0".equals(acctype)){
        			//家庭核算
            		fmid = rs.getString("family_id");            		
            		memid = tableinfoquery.getfamilymasterid(fmid);
            		String stopstatus = constantdefine.POLICYFLAGCODE_STOP;            		
        			String statu = tableinfoquery.getfamilystarus(fmid);
        			if(stopstatus.equals(statu)){
        				//暂停户
        				moneyflag = constantdefine.POLICYNEWCHECKCODE_STOP;
        			}else{
        				//普通户
        				moneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
        			} 
        		}else if("1".equals(acctype)){
        			//成员核算			
        			fmid = rs.getString("family_id");
            		memid = rs.getString("member_id");
            		moneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;   
        		}
            	
            	//插入审批表
            	if(null == memid || "".equals(memid)){
            		//数据错误不做处理
            	}else{             		
            		//插入记录            		
	    			HashMap hashmapinsert = new HashMap();
	    			hashmapinsert.put("pid", pid);
	    			hashmapinsert.put("acctype", acctype);
	    			hashmapinsert.put("moneyflag", moneyflag);
	    			hashmapinsert.put("fmid", fmid);
	    	    	hashmapinsert.put("memid", memid);
	    	    	hashmapinsert.put("empid", empid);
	    	    	policycheckmanage.insertPolicyCheck(hashmapinsert);	    	    	
            	}
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
	 * 更新单个家庭或者家庭成员救助金
	 * @param pid
	 * @param empid
	 * @param tfmid
	 * @return
	 */
	public String UpdatePolciyMatchOne(String pid,String empid,String tfmid) {
		String srep = "";  
		String moneyflag = "",checkid = "",fmid = "",memid = "",ifover = "";
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批查询处理
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		
		//业务核算类型
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);	
		//第一级审批机构
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//不是第一级审批机构
			return srep;
		}
		
		//
    	String sql = "select optcheck_id,moneyflag,family_id,member_id,ifover from biz_t_optcheck " +
    			"where policy_id = '"+pid+"' and  family_id = '"+tfmid+"' and ifover = '"+onedepth+"'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	checkid = rs.getString("optcheck_id");
            	moneyflag = rs.getString("moneyflag");
            	fmid = rs.getString("family_id");
            	memid = rs.getString("member_id");
            	ifover = rs.getString("ifover");
            	//更新记录
    			HashMap hashmapupdate = new HashMap();
    			hashmapupdate.put("checkid", checkid);
    			hashmapupdate.put("pid", pid);
    			hashmapupdate.put("acctype", acctype);
    			hashmapupdate.put("moneyflag", moneyflag);
    			hashmapupdate.put("fmid", fmid);
    			hashmapupdate.put("memid", memid);
    			hashmapupdate.put("ifover", ifover);
    			//更新审批表记录
    			UpdatePolciyMatch(hashmapupdate);
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
	 * 批量更新新增家庭或者成员救助金
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String UpdatePolciyMatchMoreNew(String pid,String empid) {
		String srep = "";  
		String checkid = "",moneyflag = "",fmid = "",memid = "",ifover = "";
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批查询处理
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//业务核算类型
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);	
		//第一级审批机构
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);		
		if(!empdepth.equals(onedepth)){
			//不是第一级审批机构
			return srep;
		}
		//
		//用户机构
    	String deptid = tableinfoquery.getempdepid(empid);
		//
    	String sql = "select a.optcheck_id,a.moneyflag,a.family_id,a.member_id,a.ifover " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and policy_id = '"+pid+"' " +
    					"and ifover = 'F' and b.organization_id like '"+deptid+"%'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	checkid = rs.getString("optcheck_id");
            	moneyflag = rs.getString("moneyflag");
            	fmid = rs.getString("family_id");
            	memid = rs.getString("member_id");
            	//第一级审批机构
            	ifover = onedepth;
            	//更新记录
    			HashMap hashmapupdate = new HashMap();
    			hashmapupdate.put("checkid", checkid);
    			hashmapupdate.put("pid", pid);
    			hashmapupdate.put("acctype", acctype);
    			hashmapupdate.put("moneyflag", moneyflag);
    			hashmapupdate.put("fmid", fmid);
    			hashmapupdate.put("memid", memid);
    			hashmapupdate.put("ifover", ifover);
    			//更新审批表记录
    			UpdatePolciyMatch(hashmapupdate);
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
	 * 批量更新救助金
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String UpdatePolciyMatchMore(String pid,String empid) {
		String srep = "";  
		String checkid = "",moneyflag = "",fmid = "",memid = "",ifover = "";
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批查询处理
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//业务核算类型
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);	
		//第一级审批机构
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//不是第一级审批机构
			return srep;
		}
		//
		//用户机构
    	String deptid = tableinfoquery.getempdepid(empid);
		//
    	String sql = "select a.optcheck_id,a.moneyflag,a.family_id,a.member_id,a.ifover " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and policy_id = '"+pid+"' " +
    					"and ifover = '"+onedepth+"' and b.organization_id like '"+deptid+"%'";   //定义SQL语句
    	
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	checkid = rs.getString("optcheck_id");
            	moneyflag = rs.getString("moneyflag");
            	fmid = rs.getString("family_id");
            	memid = rs.getString("member_id");
            	ifover = rs.getString("ifover");
            	//更新记录
    			HashMap hashmapupdate = new HashMap();
    			hashmapupdate.put("checkid", checkid);
    			hashmapupdate.put("pid", pid);
    			hashmapupdate.put("acctype", acctype);
    			hashmapupdate.put("moneyflag", moneyflag);
    			hashmapupdate.put("fmid", fmid);
    			hashmapupdate.put("memid", memid);
    			hashmapupdate.put("ifover", ifover);
    			//更新审批表记录
    			UpdatePolciyMatch(hashmapupdate);
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
	 * 判断家庭或者成员是否符合业务标准
	 * @param hashmap
	 * @return
	 */
	public HashMap UpdatePolciyMatch(HashMap hashmap) {
		HashMap shashmap = new HashMap();
		String matchflag= "0";
		String standardid = "",physql = "",locsql = "",matchmoney = "0",accphysql = "",acclocsql = "";
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批处理
	    Policycheckmanage policycheckmanage = new Policycheckmanage();
	    //审批政策业务符合标准处理类
	    PolicyCheckMatchSql policycheckmatchsql = new PolicyCheckMatchSql();
	    //分类施保处理类
	    PolicyChildCheckMatch policychildcheckmatch = new PolicyChildCheckMatch();
		//
		String checkid = hashmap.get("checkid").toString();
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//业务核算类型
		String moneyflag = hashmap.get("moneyflag").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		String ifover = hashmap.get("ifover").toString();
        //
		//
        /********************
         *******是否符合标准**
         *******************/
		HashMap hashmapsql = new HashMap();
		hashmapsql.put("pid", pid);
		hashmapsql.put("acctype", acctype);
		hashmapsql.put("fmid", fmid);
		hashmapsql.put("memid", memid);
		//获取标准
		HashMap hashmapflag = new HashMap();
		hashmapflag = GetPolciyMatchOneFlag(hashmapsql);
		standardid = hashmapflag.get("standardid").toString();
		matchflag = hashmapflag.get("matchflag").toString();
		physql = hashmapflag.get("physql").toString();
		locsql = hashmapflag.get("locsql").toString();
        //
        /********************
         *******审批表更新记录**
         *******************/
        String newmoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
		if("0".equals(matchflag) && newmoneyflag.equals(moneyflag)){
    		//删除记录
    		HashMap hashmapdelete = new HashMap();
    		hashmapdelete.put("checkid", checkid);
    		hashmapdelete.put("moneyflag", moneyflag);
    		policycheckmanage.deletePolicyCheck(hashmapdelete);
    		//删除审批政策业务家庭或者成员标准表
    		policycheckmatchsql.deletePolicyCheckSql(checkid);
    		//分类施保
    		HashMap hashmapchild = new HashMap();
    		hashmapchild.put("pid", pid);
    		hashmapchild.put("checkid", checkid);
    		hashmapchild.put("fmid", fmid);
    		hashmapchild.put("memid", memid);
        	policychildcheckmatch.deletePolicyChildCheck(hashmapchild);
        	//
    	}else{
    		/*******************
    		 *****计算标准救助金***
    		 ******************/
    		//
    		//需要计算救助金标识
    		String accmode = policymanagecheckmanage.getPolicyUserAccFlag(pid);
    		//
    		if("0".equals(accmode)){
    			//计算救助金
        		HashMap hashmapmoney = new HashMap();
    			hashmapmoney.put("pid", pid);
    			hashmapmoney.put("acctype", acctype);
    			hashmapmoney.put("fmid", fmid);
    			hashmapmoney.put("memid", memid);
    			//计算救助金
        		HashMap hashmapmoneysql = new HashMap();
        		hashmapmoneysql = GetPolciyMatchAcc(hashmapmoney,standardid);
    			matchmoney = hashmapmoneysql.get("matchmoney").toString();
    			accphysql = hashmapmoneysql.get("accphysql").toString();
    			acclocsql = hashmapmoneysql.get("acclocsql").toString();
    			//没有救助金或者救助金为0
    			if(null == matchmoney || "0".equals(matchmoney)){
    				//删除记录
    	    		HashMap hashmapdelete = new HashMap();
    	    		hashmapdelete.put("checkid", checkid);
    	    		hashmapdelete.put("moneyflag", moneyflag);
    	    		policycheckmanage.deletePolicyCheck(hashmapdelete);
    	    		//删除审批政策业务家庭或者成员标准表
    	    		policycheckmatchsql.deletePolicyCheckSql(checkid);
    	    		//分类施保
    	    		HashMap hashmapchild = new HashMap();
    	    		hashmapchild.put("pid", pid);
    	    		hashmapchild.put("checkid", checkid);
    	    		hashmapchild.put("fmid", fmid);
    	    		hashmapchild.put("memid", memid);
    	        	policychildcheckmatch.deletePolicyChildCheck(hashmapchild);
    	    		//
    			}else{
    				//更新记录
    	        	HashMap hashmapupdate = new HashMap();
    	        	hashmapupdate.put("accmode", accmode);
    	        	hashmapupdate.put("checkid", checkid);
    	        	hashmapupdate.put("matchflag", matchflag);
    	        	hashmapupdate.put("moneyflag", moneyflag);
    	        	hashmapupdate.put("matchmoney", matchmoney);
    	        	hashmapupdate.put("ifover", ifover);
    	        	//更新单个家庭或者成员审批信息
    	        	policycheckmanage.updatePolicyCheck(hashmapupdate);
    	        	//更新单个家庭或者成员救助标准状态
    	        	policycheckmanage.updatePolicyMoneyFlag(checkid);
    	        	//更新审批政策业务家庭或者成员标准表
    	    		HashMap hashmapchecksql = new HashMap();
    	    		hashmapchecksql.put("checkid", checkid);
    	    		hashmapchecksql.put("physql", physql);
    	    		hashmapchecksql.put("locsql", locsql);
    	    		hashmapchecksql.put("accphysql", accphysql);
    	    		hashmapchecksql.put("acclocsql", acclocsql);
    	    		policycheckmatchsql.updatePolicyCheckSql(hashmapchecksql);
    	        	//分类施保
    	    		HashMap hashmapchild = new HashMap();
    	    		hashmapchild.put("pid", pid);
    	    		hashmapchild.put("checkid", checkid);
    	    		hashmapchild.put("fmid", fmid);
    	    		hashmapchild.put("memid", memid);
    	        	policychildcheckmatch.SetPolciyChildMatchOne(hashmapchild);
    			}    			
    		}else{
    			//更新记录
            	HashMap hashmapupdate = new HashMap();
            	hashmapupdate.put("accmode", accmode);
            	hashmapupdate.put("checkid", checkid);
            	hashmapupdate.put("matchflag", matchflag);
            	hashmapupdate.put("moneyflag", moneyflag);
            	hashmapupdate.put("matchmoney", matchmoney);
            	hashmapupdate.put("ifover", ifover);
            	//更新单个家庭或者成员审批信息
	        	policycheckmanage.updatePolicyCheck(hashmapupdate);
	        	//更新单个家庭或者成员救助标准状态
	        	policycheckmanage.updatePolicyMoneyFlag(checkid);
	        	//更新审批政策业务家庭或者成员标准表
	    		HashMap hashmapchecksql = new HashMap();
	    		hashmapchecksql.put("checkid", checkid);
	    		hashmapchecksql.put("physql", physql);
	    		hashmapchecksql.put("locsql", locsql);
	    		hashmapchecksql.put("accphysql", accphysql);
	    		hashmapchecksql.put("acclocsql", acclocsql);
	    		policycheckmatchsql.updatePolicyCheckSql(hashmapchecksql);
	        	//分类施保
	    		HashMap hashmapchild = new HashMap();
	    		hashmapchild.put("pid", pid);
	    		hashmapchild.put("checkid", checkid);
	    		hashmapchild.put("fmid", fmid);
	    		hashmapchild.put("memid", memid);
	        	policychildcheckmatch.SetPolciyChildMatchOne(hashmapchild);
    		} 
    		 
    	}    
		//
        return shashmap;
    } 
	/**
	 * 家庭是否符合标准条件
	 * @param physql
	 * @param fmid
	 * @return
	 */
	public String GetPolciyMatchFamilyFlag(String physql,String fmid) {
		String srep = "0",sql = "";
		//
		sql = physql + " and info_t_family.family_id = '"+fmid+"'";
		//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = "1";
            	break;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }	
		return srep;
	}
	/**
	 * 成员是否符合标准条件
	 * @param physql
	 * @param memid
	 * @return
	 */
	public String GetPolciyMatchMemberFlag(String physql,String memid) {
		String srep = "0",sql = "";
		//
		sql = physql + " and info_t_member.member_id = '"+memid+"'";
		//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = "1";
            	break;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }	
		return srep;
	}
	/**
	 * 优先筛选机构标准
	 * 计算符合标准救助金
	 * @param hashmap
	 * @param standardid
	 * @return
	 */
	public HashMap GetPolciyMatchAcc(HashMap hashmap,String standardid){
		HashMap hashmapsql = new HashMap();
		String accphysql = "",acclocsql = "",matchmoney = "0";
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//业务核算类型
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		
		//家庭所属机构
		String deptid = tableinfoquery.getfamilydepid(fmid);
		//
		String sql = "select standarddept_id,accexpphysql,accexplocsql from doper_t_standarddept " +
						"where  flag = '1' and standard_id = '"+standardid+"' " +
						" and instr('#'||'"+deptid+"','#'||organization_id) >0 " +
						" order by organization_id desc";
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				//取得第一个就近机构标准				
				accphysql = rs.getString("accexpphysql");
				acclocsql = rs.getString("accexplocsql");
				if(null == accphysql || "".equals(accphysql)){
					//没有机构设置
				}else{
					//核算救助金
					matchmoney = GetPolciyMatchMoneyOne(hashmap,accphysql);					
					break;
				}				
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		//
		hashmapsql.put("matchmoney", matchmoney);
		hashmapsql.put("accphysql", accphysql);
		hashmapsql.put("acclocsql", acclocsql);
		//
		return hashmapsql;
	}
	/**
	 * 获取单个家庭或者成员保障标准状态
	 * @param checkid
	 * @return
	 */
	public String GetPolciyMatchMoneyFlag(String pip,String fmid){
		String sql = "",temp = "",moneyflag = "";
		String solemoney ="0",scountmoney = "0";
		double dolemoney =0,dcountmoney = 0;
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	//	
    	String sole = constantdefine.POLICYNEWCHECKCODE_OLE;
    	String stop = constantdefine.POLICYNEWCHECKCODE_TOP;
    	String sbom = constantdefine.POLICYNEWCHECKCODE_BOM;    	
    	//
		sql = "select sum(countmoney) as countmoney,sum(recheckmoney) as recheckmoney " +
				"from biz_t_optcheck " +
				"where policy_id = '"+pip+"' and family_id = '"+fmid+"'";
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				//取得第一个就近机构标准	
				temp = rs.getString("countmoney");
				if(null != temp){
					scountmoney = temp;
				}	
				temp = rs.getString("recheckmoney");
				if(null != temp){
					solemoney = temp;
				}				
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		if(!"0".equals(solemoney)){			
			//金额转换
			dcountmoney = new Double(scountmoney);
			dolemoney = new Double(solemoney);	
			if(dcountmoney>dolemoney){
				moneyflag = stop;//调高
			}else if(dcountmoney<dolemoney){
				moneyflag = sbom;//降低			
			}else if(dcountmoney==dolemoney){
				moneyflag = sole;//顺延		
			}
		}
		return moneyflag;
	}
	/**
	 * 获取当前审批记录的家庭ID
	 * @param checkid
	 * @return
	 */
	public HashMap GetPolciyMatchMoneyFamily(String checkid){
		String sql = "",temp = "",pid = "",fmid = "";
		HashMap hashmap = new HashMap();
		//
		sql = "select policy_id,family_id from biz_t_optcheck where optcheck_id = '"+checkid+"'";
		//log.debug("sql:"+sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 				
				temp  = rs.getString("policy_id");
				if(null != temp){
					pid = temp;
				}
				temp  = rs.getString("family_id");
				if(null != temp){
					fmid = temp;
				}				
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		hashmap.put("pid", pid);
		hashmap.put("fmid", fmid);
		return hashmap;
	}
	/**
	 * 计算救助金
	 * @param hashmap
	 * @param tsql
	 * @return
	 */
	public String GetPolciyMatchMoneyOne(HashMap hashmap,String tsql){
		String  srep = "0",sql = "";
		//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//业务核算类型
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//
		if("0".equals(acctype)){
			//家庭核算
			sql = tsql + " where INFO_T_FAMILY.FAMILY_ID = '"+ fmid +"'";
		}else if("1".equals(acctype)){
			//成员核算
			sql = tsql + " and INFO_T_MEMBER.MEMBER_ID = '"+ memid +"'";
		}
		//log.debug("sql:"+sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				//计算出家庭的核算标准金额[acccount]为核算公式页面定义
				String acccount  = rs.getString("acccount");
				if(null != acccount){
					BigDecimal bl = new BigDecimal(acccount);
					bl = bl.setScale(2, BigDecimal.ROUND_HALF_UP);				
					srep = bl.toString();
				}				
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 获取家庭或者成员是否符合业务标准
	 * @param hashmap
	 * @return
	 */
	public HashMap GetPolciyMatchOneFlag(HashMap hashmap){
		HashMap hashmapflag = new HashMap();
		String standardid = "",physql = "",locsql = "",matchflag = "0";
		//
        /********************
         *******是否符合标准**
         *******************/
		//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//业务核算类型
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//
        String sql = "select standard_id,physql,locsql from doper_t_standard " +
				"where flag = '1' and policy_id = '"+pid+"' " +
					"order by planmoney desc";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	standardid = rs.getString("standard_id");
            	physql = rs.getString("physql");
            	locsql = rs.getString("locsql");
            	if(null != physql){
            		if("0".equals(acctype)){
            			//家庭核算        			
            			matchflag = GetPolciyMatchFamilyFlag(physql,fmid);
            			if("1".equals(matchflag)){
            				//符合标准
            				break;
            			}
            		}else if("1".equals(acctype)){
            			//成员核算
            			matchflag = GetPolciyMatchMemberFlag(physql,memid);
            			if("1".equals(matchflag)){
            				//符合标准
            				break;
            			}
            		}
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
        hashmapflag.put("standardid", standardid);
        hashmapflag.put("matchflag", matchflag);
        hashmapflag.put("physql", physql);
        hashmapflag.put("locsql", locsql);
        //
		return hashmapflag;
	}
}
