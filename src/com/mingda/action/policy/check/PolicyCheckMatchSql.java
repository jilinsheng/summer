package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;

public class PolicyCheckMatchSql {
	static Logger log = Logger.getLogger(PolicyCheckMatchSql.class);
	/**
	 * 删除审批政策业务家庭或者成员标准表
	 * @param checkid
	 * @return
	 */
	public String deletePolicyCheckSql(String checkid) {
    	String srep = "";
		String sql = "";
		//
		sql = "delete biz_t_optchecksql where optcheck_id = '"+checkid+"'";  //更新状态SQL
		
		//log.debug(sql1);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行		   
		    conn.commit();                     //关闭		    
		    srep = "删除审批政策业务档次操作成功!";
		} catch (SQLException e) {			
			try {
				srep = "删除审批政策业务档次操作失败!";
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
	 * 更新审批政策业务家庭或者成员标准表
	 * @param hashmap
	 * @return
	 */
	public String updatePolicyCheckSql(HashMap hashmap) {
    	String srep = "",sql = "";
    	//
	    String checkid = hashmap.get("checkid").toString();
		String physql = hashmap.get("physql").toString();
		String locsql = hashmap.get("locsql").toString();
		String accphysql = hashmap.get("accphysql").toString();
		String acclocsql = hashmap.get("acclocsql").toString();
    	//
		//单引号转义去掉左右空格
		physql = physql.replace("'","''");
		locsql = locsql.replace("'","''");
		accphysql = accphysql.replace("'","''");
		acclocsql = acclocsql.replace("'","''");
		//
		if(existsPolicyCheckSql(checkid)){
			sql = "update biz_t_optchecksql " +
				"set physql = '"+physql+"'," +
				"locsql = '"+locsql+"'," +
				"accphysql = '"+accphysql+"'," +
				"acclocsql = '"+acclocsql+"' " +
				"where optcheck_id = '"+checkid+"'";
		}else{
			sql = "insert into biz_t_optchecksql " +
					"(optchecksql_id,optcheck_id,physql,locsql,accphysql,acclocsql) " +
					"values " +
					"(xoptchecksql_id.nextval,'"+checkid+"','"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"')";
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
  	  		srep = "更新审批政策业务档次操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "更新审批政策业务档次操作失败!";
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
	 * 是否存在审批政策业务标准标准
	 * @param checkid
	 * @return
	 */
	public boolean existsPolicyCheckSql(String checkid){
		boolean  brep = false;
		
		String sql = "select optchecksql_id from biz_t_optchecksql where optcheck_id = '"+checkid+"'";   //定义SQL语句
		
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
			while (rs.next()){ 
				brep = true;
				break;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
}
