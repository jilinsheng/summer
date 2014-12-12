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
 * 审批评议人管理类
 * @author xiu
 *
 */
public class CheckPersonManage {
	static Logger log = Logger.getLogger(CheckPersonManage.class);
	/**
	 * 取得该机构审批评议人列表
	 * @param sdeptid
	 * @return
	 */
	public String getCheckPersons(String sdeptid){
		JSONArray array = new JSONArray();      //定义JSON数组
		//表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
        //定义SQL语句
        String sql = "select optreviewperson_id,name,sex,face,officephone,officename,post,address,organization_id,status from biz_t_optreviewperson where organization_id = '"+sdeptid+"'";   
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
                obj.put("vid", rs.getString("optreviewperson_id"));
                obj.put("vname", rs.getString("name"));
                //
                String temp = rs.getString("sex");
                temp = tableinfoquery.getdiscionaryfromid(temp);
                obj.put("vsex",temp); 
                //
                temp = rs.getString("face");
                temp = tableinfoquery.getdiscionaryfromid(temp);
                obj.put("vface",temp);
                //
                obj.put("vofficename", rs.getString("officename"));
                obj.put("vofficephone", rs.getString("officephone"));
                obj.put("vpost", rs.getString("post"));
                obj.put("vaddress", rs.getString("address"));
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
	 * 取得审批评议人属性
	 * @param sid
	 * @return
	 */
	public String getCheckPersonItems(String sid){
		JSONArray array = new JSONArray();      //定义JSON数组
        //定义SQL语句
        String sql = "select optreviewperson_id,name,sex,face,officephone,officename,post,address,organization_id,status from biz_t_optreviewperson where optreviewperson_id = '"+sid+"'";   
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
                obj.put("vid", rs.getString("optreviewperson_id"));
                obj.put("vname", rs.getString("name"));
                obj.put("vsex", rs.getString("sex"));  
                obj.put("vface", rs.getString("face"));
                obj.put("vofficename", rs.getString("officename"));
                obj.put("vofficephone", rs.getString("officephone"));
                obj.put("vpost", rs.getString("post"));
                obj.put("vaddress", rs.getString("address"));
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
	 * 更新评议人状态
	 * @param Id
	 * @param Status
	 * @return
	 */
	public String updateCheckPersonStatus(String Id,String Status) {
	    String srep = "";
		String sql = "update biz_t_optreviewperson set status = '"+Status+"' where optreviewperson_id = '"+Id+"'";  //更新档次状态SQL
		
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
		    	srep = "停用评议人操作成功!";
		    }else{
		    	srep = "启用评议人操作成功!";
		    }
		} catch (SQLException e) {
			if("0".equals(Status)){
		    	srep = "停用评议人操作失败!";
		    }else{
		    	srep = "启用评议人操作失败!";
		    }
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 更新评议人属性
	 * @param Mode
	 * @param Name
	 * @param Deptid
	 * @param Sql
	 * @return
	 */
	public String updateCheckPerson(String Mode,String Name,String Deptid,String Sql) {
		String srep = "";
		//
		if(Mode.equals("add")){
			if(existsCheckPerson(Name,Deptid)){
				srep = "评议人名称已经存在!";
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
		    	srep = "添加评议人保存操作成功!";
		    }else if(Mode.equals("edit")){
		    	srep = "更新评议人保存操作成功!";
		    }
		    
		} catch (SQLException e) {
			if(Mode.equals("add")){
				srep = "添加评议人保存操作失败!";
		    }else if(Mode.equals("edit")){
		    	srep = "更新评议人保存操作失败!";
		    }
			
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 是否存在评议人
	 * @param sname
	 * @param sdeptid
	 * @return
	 */
    boolean existsCheckPerson(String sname,String sdeptid) {
    	boolean brep = false;
        String sql = "select optreviewperson_id from biz_t_optreviewperson where name =  '"+sname+"' and organization_id = '"+sdeptid+"'";   
        
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
    /**
     * 取得审批评议人下拉框
     * @param sname
     * @param empid
     * @return
     */
    public StringBuffer getCheckPersonChoice(String sname,String empid){
    	StringBuffer shtml= new StringBuffer("");
    	String id = "",name = "",deptid = "";
    	boolean isexists = false;
    	// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
	    deptid = tableinfoquery.getempdepid(empid);
	    //
    	String sql = "select optreviewperson_id,name from biz_t_optreviewperson where status = '1' and organization_id = '"+deptid+"'";      
        //log.debug(sql);
    	
    	//
    	Connection conn = null;                 //声明Connection对象
    	PreparedStatement pstmt = null;         //声明PreparedStatement对象
    	ResultSet rs = null;                    //声明ResultSet对象
    	try {
    		conn = DBUtils.getConnection();     //获取数据库连接
    		pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
    		//参数
    		rs = pstmt.executeQuery();
    		//
    		shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
    		//
    		while (rs.next()) {  
    			isexists = true;
    			id = rs.getString("optreviewperson_id");
    			name = rs.getString("name");
    			shtml.append("<option value=\""+id+"\">"+name+"</option>");
    		}
    		//
    		shtml.append("</select>");
    		//
    	} catch (SQLException e) {
    		log.debug(e.toString());
    	} finally {
    		DBUtils.close(rs);                  //关闭结果集
    		DBUtils.close(pstmt);               //关闭PreparedStatement
    		//DBUtils.close(conn);                //关闭连接
    	}
    	//是否存在评议人
        if(!isexists){
        	StringBuffer stemp= new StringBuffer("");
        	return stemp;
        }
    	return shtml;
    }
}
