package com.mingda.action.policy.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;

public class Policyinfoedit {
	static Logger log = Logger.getLogger(Policyinfoedit.class);
	/**
	 * 获取政策业务相关信息设置表
	 * @param pid
	 * @return
	 */
	public StringBuffer getPolicyInfosHtml(String pid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String sid = "",stname = "",sfname = "",sflag = "";     	
    	//
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;
			temp ="<td height='23'>表名称</td>";
			html +=temp;			
			temp ="<td height='23'>字段名称</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select c.policyinfo_id,c.status,b.logicname as tablename,a.logicname as fieldname " +
        		"from sys_t_field a,sys_t_table b,doper_t_policyinfo c " +
        		"where a.table_id = b.table_id and a.field_id = c.field_id " +
        			"and a.status = '1' and c.policy_id = '"+pid+"' " +
        		"order by a.logicname,b.logicname";   //定义SQL语句
        
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
            	sid = rs.getString("policyinfo_id");
            	stname = rs.getString("tablename");
            	sfname = rs.getString("fieldname");
            	sflag = rs.getString("status");
            	
                
                html +="<tr>";		
		    		//各列值	
	                html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/close.gif' alt = '删除' onclick=\"DeletePolicyInfo('"+sid+"','"+stname + "','" + sfname + "')\" />";									
					html += "</td>";
                	html += "<td height='23' class='colValue status"+sflag+"'>"+stname+"</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sfname+"</td>";
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePolicyInfoStatus('" + sid + "','" + stname + "','" + sfname + "','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePolicyInfoStatus('" + sid + "','" + stname + "','" + sfname + "','1')\">启用</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        //
	    html +="</table>";        
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
	/**
	 * 插入政策业务相关信息表
	 * @param pid
	 * @param fid
	 * @return
	 */
	public String insertPolicyInfo(String pid,String fid) {
    	String srep = "";
    	//是否存在政策业务相关信息
    	if(existsPolicyinfo(pid,fid)){
    		srep = "已经存在政策业务相关信息!";
    		return srep;
    	}
		//
		
		String sql = "insert into doper_t_policyinfo " +
				"(policyinfo_id,policy_id,field_id,status)" +
				" values " +
				"(xpolicyinfo_id.nextval,'"+pid+"','"+fid+"','1')";
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
  	  		srep = "插入政策业务相关信息表操作成功!";
          
  	  	} catch (SQLException e) {  	  		
  	  		try {
  	  			srep = "插入政策业务相关信息操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		DBUtils.close(conn);                //关闭连接
  	  	}
    	return srep;
    }
	/**
	 * 更新政策业务相关信息表
	 * @param id
	 * @param status
	 * @return
	 */
	public String updatePolicyInfoStatus(String id,String status) {
    	String srep = "";
		//		
		String sql = "update doper_t_policyinfo set status = '"+status+"' where policyinfo_id = '"+id+"'";
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
  	  		if("1".equals(status)){
  	  			srep = "启用政策业务相关信息表操作成功!";
  	  		}else{
  	  			srep = "停用政策业务相关信息表操作成功!";
  	  		}
  	  	} catch (SQLException e) {
  	  		try {
	  	  		if("1".equals(status)){
	  	  			srep = "启用政策业务相关信息表操作失败!";
	  	  		}else{
	  	  			srep = "停用政策业务相关信息表操作失败!";
	  	  		}
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		DBUtils.close(conn);                //关闭连接
  	  	}
    	return srep;
    }
	/**
	 * 删除政策业务相关信息表
	 * @param id
	 * @return
	 */
	public String deletePolicyInfo(String id) {
    	String srep = "";
		//		
		String sql = "delete doper_t_policyinfo where policyinfo_id = '"+id+"'";
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
  	  		srep = "删除政策业务相关信息表操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "删除政策业务相关信息操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		DBUtils.close(conn);                //关闭连接
  	  	}
    	return srep;
    }
	/**
	 * 是否存在该业务相关信息设置表
	 * @param pid
	 * @param fid
	 * @return
	 */
	public boolean existsPolicyinfo(String pid,String fid){
		boolean  brep = false;
		
		String sql = "";
		sql = "select policyinfo_id from doper_t_policyinfo " +
				"where policy_id = '"+pid+"' and field_id = '"+fid+"'";
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
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
		    DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
}
