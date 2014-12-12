package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.log4j.Log4jApp;

/**
 * 信息合法校验控制类
 * @author xiu
 *
 */
public class InfoValidate {
	static Logger log = Logger.getLogger(InfoValidate.class);
	
	/**
	 * 取得信息合法验证列表
	 * @return
	 */
	public String getInfoValidates(){
		//表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
		JSONArray array = new JSONArray();      //定义JSON数组
        //定义SQL语句
        String sql = "select validateterm_id,name,itemdesc,type,physql,locsql,status from biz_t_validateterm";   
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
                obj.put("vid", rs.getString("validateterm_id"));
                obj.put("vname", rs.getString("name"));
                //
                String temp = rs.getString("type");
                temp = tableinfoquery.getdiscionaryfromid(temp);
                obj.put("vtype",temp);
                //
                obj.put("vdesc", rs.getString("itemdesc"));  
                obj.put("vphysql", rs.getString("physql"));
                obj.put("vlocsql", rs.getString("locsql"));
                obj.put("vstatus", rs.getString("status"));
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
	 * 取得信息合法验证属性
	 * @param sid
	 * @return
	 */
	public String getInfoValidateItems(String sid){
		JSONArray array = new JSONArray();      //定义JSON数组
        //定义SQL语句
        String sql = "select validateterm_id,name,itemdesc,type,physql,locsql,status from biz_t_validateterm where validateterm_id = '"+sid+"'";   
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
                obj.put("vid", rs.getString("validateterm_id"));
                obj.put("vname", rs.getString("name"));
                obj.put("vdesc", rs.getString("itemdesc"));
                obj.put("vtype", rs.getString("type")); 
                obj.put("vphysql", rs.getString("physql"));
                obj.put("vlocsql", rs.getString("locsql"));
                obj.put("vstatus", rs.getString("status"));
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
     * 取得信息合法验证SQL语句
     * @param sid
     * @param mode
     * @return
     */
    public String getInfoValidateSqlItem(String sid,String mode) {
        String srep = "";
        String sql = "select physql,locsql from biz_t_validateterm where validateterm_id = '"+sid+"'";   
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	if(mode.equals("loc")){
            		srep = rs.getString("locsql");
            	}else if(mode.equals("phy")){
            		srep = rs.getString("physql");
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
	 * 更新验证状态
	 * @param Id
	 * @param Status
	 * @return
	 */
	public String updateValidateStatus(String Id,String Status) {
	    String srep = "";
		String sql = "update biz_t_validateterm set status = '"+Status+"' where validateterm_id = '"+Id+"'";  //更新档次状态SQL
		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.executeUpdate();              //执行
		    conn.commit();                      //关闭
		    if("0".equals(Status)){
		    	srep = "停用验证操作成功!";
		    }else{
		    	srep = "启用验证操作成功!";
		    }
		} catch (SQLException e) {
			if("0".equals(Status)){
		    	srep = "停用验证操作失败!";
		    }else{
		    	srep = "启用验证操作失败!";
		    }
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 更新信息合法验证属性
	 * @param Mode
	 * @param Name
	 * @param Sql
	 * @return
	 */
	public String updateValidate(String Mode,String Name,String Sql) {
		String srep = "";
		//
		if(Mode.equals("add")){
			if(existsValidate(Name)){
				srep = "信息合法验证名称已经存在!";
				return srep;
			}
		}
    	String sql = Sql;  //更新档次状态SQL
    	
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.executeUpdate();              //执行
		    conn.commit();                      //关闭
		    if(Mode.equals("add")){
		    	srep = "添加验证保存操作成功!";
		    }else if(Mode.equals("edit")){
		    	srep = "更新验证保存操作成功!";
		    }
		    
		} catch (SQLException e) {
			if(Mode.equals("add")){
				srep = "添加验证保存操作失败!";
		    }else if(Mode.equals("edit")){
		    	srep = "更新验证保存操作失败!";
		    }
			
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 更新信息合法验证条件SQL语句
	 * @param Id
	 * @param PhySql
	 * @param LocSql
	 * @return
	 */
	public String updateValidateSql(String Id,String PhySql,String LocSql) {
		String srep = "";
		//单引号转义去掉左右空格
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update biz_t_validateterm set " +
				"physql = '"+PhySql+"'," +
				"locsql = '"+LocSql+"' where validateterm_id = '"+Id+"'";  //更新档次状态SQL
		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.executeUpdate();              //执行
		    conn.commit();                      //关闭
		    srep = "更新验证条件保存操作成功!";
		} catch (SQLException e) {
			srep = "更新验证条件保存操作失败!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 是否存在信息验证
	 * @param sname
	 * @return
	 */
    boolean existsValidate(String sname) {
    	boolean brep = false;
        String sql = "select validateterm_id from biz_t_validateterm where name =  '"+sname+"'";   
        
        Connection conn = null;                 //声明Connection对象
	    PreparedStatement pstmt = null;         //声明PreparedStatement对象
	    ResultSet rs = null;                    //声明ResultSet对象
	    try {
	        conn = DBUtils.getConnection();     //获取数据库连接
	        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
	        //设置参数
	        rs = pstmt.executeQuery();
	        while (rs.next()) {	
	        	//存在
	        	brep = true;
	        }
	    } catch (SQLException e) {
	        Log4jApp.logger(e.toString());
	    } finally {
	        DBUtils.close(rs);                  //关闭结果集
	        DBUtils.close(pstmt);               //关闭PreparedStatement
	        //DBUtils.close(conn);                //关闭连接
	    }		    
        return brep;
    }
}
