package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mingda.action.info.search.DBUtils;

public class PolicyManagePower {
	static Logger log = Logger.getLogger(PolicyManagePower.class);
	/**
	 * 取得业务岗位权限列表
	 * @param pid
	 * @param empid
	 * @return
	 */
    public String getPolicyPostsPower(String pid,String empid) {
        JSONArray array = new JSONArray();      //定义JSON数组
        String sql = "select post_id,name from sys_t_post where status = '1' order by sequence asc";   //定义SQL语句
        
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //遍历结果集，给JSON数组中加入JSONObject
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("pid", rs.getString("post_id"));
                obj.put("pname", rs.getString("name"));
                //
                String postid = rs.getString("post_id"); 
                String sflags = getPolicyPostPowers(pid,postid);
                String scheckflag = "0";
        		String scheckmoreflag = "0";
        		String sreportflag = "0";
                if(!sflags.equals("")){//无岗位权限设置                	
                	String [] aflag = sflags.split(",");
                	scheckflag = aflag[0];
            		scheckmoreflag = aflag[1];
            		sreportflag = aflag[2];                	
                }
                //各权限
                obj.put("pcheckflag", scheckflag);
                obj.put("pcheckmoreflag", scheckmoreflag);
                obj.put("preportflag", sreportflag);
        		
                array.add(obj);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return array.toString();
    }
    /**
     * 取得业务岗位业务审批权限(多个权限标识用,号隔开)
     * @param pid     
     * @param postid
     * @return
     */
    public String getPolicyPostPowers(String pid,String postid) {
    	String  srep = "";
    	String sql = "select checkflag,checkmoreflag,reportflag " +
    			"from biz_t_checkpower " +
    			"where post_id = '"+postid+"' and policy_id = '"+pid+"'";   //定义SQL语句
        
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
     * 设置业务岗位权限表
     * @param pid
     * @param postid
     * @param checkflag
     * @param checkmoreflag
     * @param reportflag
     * @return
     */
    String setPolicyPostPowers(String pid,String postid,String checkflag,String checkmoreflag,String reportflag) {
    	String  srep = "";
    	String sql = "";
        
    	if(existsPolicyPostPowers(pid,postid)){
    		//存在
    		sql = "update biz_t_checkpower " +
    				"set checkflag = '"+checkflag+"'," +
    				"reportflag = '"+reportflag+"'," +
    				"checkmoreflag = '"+checkmoreflag+"' " +
    				"where policy_id = '"+pid+"' and post_id = '"+postid+"'";
    	}else{
    		//不存在
    		sql = "insert into biz_t_checkpower " +
    				"(checkpower_id, policy_id, post_id, checkflag, reportflag, checkmoreflag) " +
    				"values " +
    				"(xcheckpower_id.nextval,'"+pid+"','"+postid+"','"+checkflag+"','"+reportflag+"','"+checkmoreflag+"')";	
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
            srep = "岗位权限设置操作成功!";
        } catch (SQLException e) {
        	try {
        		srep = "岗位权限设置操作失败!";
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
     * 业务岗位权限是否存在
     * @param pid
     * @param postid
     * @return
     */
    public boolean existsPolicyPostPowers(String pid,String postid){
		boolean  brep = false;		
		//
		String sql = "select checkpower_id from biz_t_checkpower " +
					"where policy_id = '"+pid+"' and post_id = '"+postid+"'";  //SQL
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
