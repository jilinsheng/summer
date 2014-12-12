package com.mingda.action.policy.find;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;


public class PolicyAccQuery {	
	static Logger log = Logger.getLogger(PolicyAccQuery.class);
	/**
	 * 获取业务结算批次信息
		 * optacc_id
		 * -1无结算批次编号
		 * accflag
		 * -1未有新的结算批次
		 * 0新结算批次
		 * 1正在结算批次
		 * 2结算完成批次
		 * accmoneyflag
		 * 0未定额救助标识
		 * 1定额救助标识
		 * accmoney
		 * 0无定额救助金
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public HashMap getPolicyAccInfo(String pid,String deptid){
		HashMap hashmap = new HashMap();  
    	//
		String sql = "select " 
    		+ "optacc_id,accflag,accmoneyflag,accmoney " 
    		+ "from biz_t_optacc " 
    		+ "where policy_id = '"+pid+"' " 
    		+ "and instr('#' || '"+deptid+"', '#' || organization_id) > 0  "
    		+ "order by accdt desc";
        //
		int irow = 0;
    	//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			if(rs.next()){
				hashmap.put("optid", rs.getString("optacc_id"));
				hashmap.put("accflag", rs.getString("accflag"));
				hashmap.put("accmoneyflag", rs.getString("accmoneyflag"));
				hashmap.put("accmoney", rs.getString("accmoney"));
				irow++;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    DBUtils.close(conn);                //关闭连接
		}
		if(irow == 0){
			hashmap.put("optid", "-1");
			hashmap.put("accflag", "-1");
			hashmap.put("accmoneyflag","0");
			hashmap.put("accmoney", "0");
		}
		return hashmap;
	}
    /**
     * 取得业务核算类型
     * @param pid
     * @return
     */
    public String getPolicyAccTypeFlag(String pid) {
        String srep = "0";
        //
        String sql = "select acctype from doper_t_policy where policy_id = '" + pid + "' ";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	srep = rs.getString("acctype");              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     * 获取结算批次信息描述
     * @param accid
     * @return
     */
    public String getNowPolicyAcc(String accid){
    	String srep = "";
    	String sql = "";
    	 sql = "select optacc_id,accyear,accmonth,accdesc,accflag " 
         	+ "from biz_t_optacc " 
         	+ "where optacc_id = '" + accid + "' ";   //定义SQL语句
    	//log.debug(sql);
         Connection conn = null;                 //声明Connection对象
         PreparedStatement pstmt = null;         //声明PreparedStatement对象
         ResultSet rs = null;                    //声明ResultSet对象
         try {
             conn = DBUtils.getConnection();     //获取数据库连接
             pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
             rs = pstmt.executeQuery();            
             if (rs.next()) {             	
            	 srep = rs.getString("accyear") + " 年   "; 
            	 srep += rs.getString("accmonth") + " 月   ";
            	 //srep += " 批次描述:"+ rs.getString("accdesc");
            	 String tmp = rs.getString("accflag");
            	 if("0".equals(tmp)){
            		 srep += " 状态:未结算  救助业务可以办理";
            	 }else if("1".equals(tmp)){
            		 srep += " 状态:正结算  救助业务稍后办理";
            	 }else if("2".equals(tmp)){
            		 srep += " 状态:结算完成  救助业务不能办理";
            	 }            	 
             }
         } catch (SQLException e) {
             log.debug(e.toString());
         } finally {
             DBUtils.close(rs);                  //关闭结果集
             DBUtils.close(pstmt);               //关闭PreparedStatement
             DBUtils.close(conn);                //关闭连接
         }
    	return srep;
    }
    /**
     * 是否存在业务结算批次
     * @param hashmap
     * @return
     */
    public boolean existsPolicyAcc(HashMap hashmap) {
    	boolean brep = false;
    	String sql = "",tempsql = "";
    	//
    	String jdeptid =hashmap.get("jdeptid").toString();
    	String jpid = hashmap.get("jpid").toString();
		String jyear = hashmap.get("jyear").toString();
		String jmonth = hashmap.get("jmonth").toString();
		String jdesc = hashmap.get("jdesc").toString();
        //
        sql = "select optacc_id " 
        	+ "from biz_t_optacc " 
        	+ "where policy_id = '" + jpid + "' "
        	+ "and accyear = '" + jyear + "' "
        	+ "and accmonth = '" + jmonth + "' " 
        	+ "and organization_id = '"+jdeptid+"' ";   //定义SQL语句
        //特殊控制(农村低保21)每月一次
        if(!"21".equals(jpid)){
        	if(!"".equals(jdesc)){
            	tempsql = "and accdesc = '" + jdesc + "' ";
            	sql += tempsql;
            }       
        }
        //
        
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	String accid = rs.getString("optacc_id");  
            	brep = true;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        return brep;
    }
    /**
     * 该批次是否未结算,等待结算
     * @param accid
     * @return
     */
    public boolean existsNowPolicyAcc(String accid){
    	boolean brep = false;
    	String sql = "";
    	 sql = "select optacc_id " 
         	+ "from biz_t_optacc " 
         	+ "where accflag = '0' and optacc_id = '" + accid + "' ";   //定义SQL语句
    	log.debug(sql);
         Connection conn = null;                 //声明Connection对象
         PreparedStatement pstmt = null;         //声明PreparedStatement对象
         ResultSet rs = null;                    //声明ResultSet对象
         try {
             conn = DBUtils.getConnection();     //获取数据库连接
             pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
             rs = pstmt.executeQuery();            
             if (rs.next()) {             	
             	brep = true;
             }
         } catch (SQLException e) {
             log.debug(e.toString());
         } finally {
             DBUtils.close(rs);                  //关闭结果集
             DBUtils.close(pstmt);               //关闭PreparedStatement
             DBUtils.close(conn);                //关闭连接
         }
    	return brep;
    }
}
