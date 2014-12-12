package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

public class PolicyManageCheckManage {
	static Logger log = Logger.getLogger(PolicyManageCheckManage.class);
	/**
     * 当前业务是否已经终审结算
     * 返回结果:0还未终审结算1已经终审结算2不能做终审结算
     * @param pid
     * @param empid
     * @return
     */
    public String getPolicyAllAccFlag(String pid,String empid){
		String  srep = "0",optaccid = ""; 
		//政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery(); 
	 	//
	 	optaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid, empid);
	 	if(optaccid.equals("2")){
	 		//没有要办理的终审结算业务
    		srep = "2";
    		return srep;    		
    	}
	 	//
    	//是否终审结算标识
    	String sql = "select accflag from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and optacc_id = '"+optaccid+"' ";
        //
    	//log.debug("sql:"+sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //参数
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
            	srep = rs.getString("accflag");
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
	 * 获取业务是否可以批量审批标识
	 * 0单审1批量审批
	 * @param pid
	 * @return
	 */
	public String getPolicyCheckFlag(String pid) {
    	String srep = "0";
        
        String sql = "select checkflag from biz_t_checkflow where policy_id = '" + pid + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("checkflag");            	
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
     * 取得业务岗位业务审批权限相关标识(多个标识用,号隔开)
     * @param pid
     * @param empid
     * @return
     */
	public String getPolicyPostPowerFlags(String pid,String empid) {
    	String  srep = "";
    	String sql = "select a.checkflag,a.checkmoreflag,a.reportflag " +
    			"from biz_t_checkpower a,sys_t_employee b,sys_t_empext c " +
    			"where a.post_id = b.post_id and b.employee_id = c.employee_id " +
    			"and c.empext_id = '"+empid+"' and a.policy_id = '"+pid+"'";   //定义SQL语句
        
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep += rs.getString("checkflag");
            	srep += ","+rs.getString("checkmoreflag");
            	srep += ","+rs.getString("reportflag");            	
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
	    }
		return srep;
	}
	/**
     * 重新审批
     * 单个家庭ID
     * 返回0不可重审1可重审
     * (审批进度在本级或者上级未评议或者重新核查均可撤消审批意见重审) 
     * @param empid
     * @param pid
     * @param fmid
     * @return
     */
    public String getPolicyIsRmCheckFlag(String empid,String pid,String fmid) {
    	String srep = "0",stemp = "";
    	String empdepth = "",nextdepth = "",backdepth = "";
        String empcheckflag = "",nextcheckflag = "",newresult = "",remresult = "";
        // 表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      //政策审批处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery(); 
      	//
	    //常量定义
        ConstantDefine constantdefine = new ConstantDefine();
        //
        empdepth = tableinfoquery.getempdepth(empid);
        backdepth = policymanagecheckquery.getPolicyBackFlowFromId(pid, empid);
        nextdepth = policymanagecheckquery.getPolicyNextFlowFromId(pid, empid);
        //
        newresult = constantdefine.POLICYCHECKCODE_NEW;
        remresult = constantdefine.POLICYCHECKCODE_RENEW;
        //
        if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	empcheckflag = "checkflag1";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	empcheckflag = "checkflag2";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	empcheckflag = "checkflag3";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	empcheckflag = "checkflag4";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	empcheckflag = "checkflag5";
        }
        //
        //
        if(nextdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	nextcheckflag = "checkflag1";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	nextcheckflag = "checkflag2";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	nextcheckflag = "checkflag3";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	nextcheckflag = "checkflag4";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	nextcheckflag = "checkflag5";
        }
        //
        String sql = "select optcheck_id from biz_t_optcheck " +
        		"where policy_id = '" + pid + "' and family_id = '" + fmid + "' ";
        //
		if(empdepth.equals(nextdepth)){
			stemp = "and (" +
							"(ifover = '"+empdepth+"' and ("+empcheckflag+" <> '"+newresult+"')) " +
						")";   //定义SQL语句
			sql = sql + stemp;
		}else{
			stemp = "and (" +
							"(ifover = '"+empdepth+"' and ("+empcheckflag+" <> '"+newresult+"')) " +
							"or " +
							"(ifover = '"+nextdepth+"' and ("+nextcheckflag+" = '"+newresult+"' or "+nextcheckflag+" = '"+remresult+"'))" +
						")";   //定义SQL语句
			sql = sql + stemp;
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
            	srep = rs.getString("optcheck_id");   
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
    	//
    	if(!stemp.equals("")){
    		srep = "1";
    	}
        return srep;
    }
    
    /**
	 * 获取业务是否可以填写救助金标识
	 * 0不允许1允许更新
	 * @param pid
	 * @return
	 */
	public String getPolicyUserAccFlag(String pid) {
    	String srep = "0";
        
        String sql = "select useraccflag from biz_t_checkflow where policy_id = '" + pid + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("useraccflag");            	
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
     * 取得审批流程标识
     * @param pid
     * @param empid
     * @return
     */
    public String getPolicyFlowFlag(String pid,String empid) {
        String srep = "0";
        // 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
        //常量定义
        ConstantDefine constantdefine = new ConstantDefine();
        String dpath = tableinfoquery.getempdepth(empid);
        //
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + pid + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              if(dpath.equals(constantdefine.POLICYQUERYCODE_5)){
                srep = rs.getString("appstate1");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_4)){
                srep = rs.getString("appstate2");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_3)){
                srep = rs.getString("appstate3");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_2)){
                srep = rs.getString("appstate4");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_1)){
                srep = rs.getString("appstate5");
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
     * 取得业务核算类型
     * @param pid
     * @return
     */
    public String getPolicyAccTypeFlag(String pid) {
        String srep = "0";
        //
        String sql = "select acctype from doper_t_policy where policy_id = '" + pid + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("acctype");              
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
     * 汇总家庭救助金
     * @param pid
     * @param fmid
     * @return
     */
    public HashMap sumPolicyFamilyMoney(String pid,String fmid) {
    	HashMap hashmap = new HashMap();
    	//
    	String recheckmoney = "",countmoney = "";
        String sql = "select sum(recheckmoney) recheckmoney ,sum(countmoney) countmoney from biz_t_optcheck " +
        		"where policy_id = '" + pid + "' and family_id = '"+fmid+"'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	recheckmoney = rs.getString("recheckmoney");  
            	countmoney = rs.getString("countmoney"); 
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
        hashmap.put("recheckmoney", recheckmoney);
        hashmap.put("countmoney", countmoney);
        
    	return hashmap;
    }

}
