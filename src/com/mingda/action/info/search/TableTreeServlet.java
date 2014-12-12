package com.mingda.action.info.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

@SuppressWarnings("serial")
public class TableTreeServlet extends HttpServlet {
	static Logger log = Logger.getLogger(TableTreeServlet.class);
	/**
	 * Constructor of the object.
	 */
	public TableTreeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn =null;
		try {
			conn =ConnectionManager.getConnection();
		
		response.setContentType("text/xml; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();		
		String action = request.getParameter("action"); //获取
		String id = request.getParameter("id"); //获取要加载的节点编号		
		String smode = request.getParameter("mode");//调用模块功能'query'只做查询'console'可以控制 		
		//log.debug(id + action + smode);
		//获取根节点
	    if ("root".equals(action)) {                 
	    	out.print(getroot(id,smode)); //输出
	    }//获取子节点 
	    else if ("child".equals(action)) {
	    	out.print(getchild(id,smode)); //输出
	    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnectionManager.closeQuietly();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	/**
	 * 取得表描述根节点
	 * @param id
	 * @param smode
	 * @return
	 */
	StringBuffer getroot(String id,String smode) {
		StringBuffer shtml= new StringBuffer("");
		String sid ="",sname = "",sreadonly = "";
		//id暂时不用[直接调用信息树根节点]
		
        String sql = "select table_id,logicname,readonly from sys_t_table where status = '1' and  depth = '0' order by sequence";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	sid = rs.getString("table_id");
            	sname = rs.getString("logicname");
            	sreadonly = rs.getString("readonly");
            	
            	shtml.append("<div style=\"margin-left:12px;width:200px;\" id=\""+sid+"\" class=\"closed\">"); 
            	if("console".equals(smode)){
            		shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclickconsole(\'page/info/search/TableTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
                	shtml.append("<span class=\"sqltable pointer\" ondblclick=\"seltableclick(\'"+sid+"\',\'"+sname+"\')\">"+sname+"</span>");
                	if("0".equals(sreadonly)){
	            		shtml.append("<span class=\"sqltable pointer\" onclick=\"addchild(\'"+sid+"\')\">[扩子表]</span>");
	            		shtml.append("<span class=\"sqltable pointer\" onclick=\"addfield(\'"+sid+"\')\">[扩字段]</span>");
                	}
            	}else if("query".equals(smode)){
            		shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclick(\'page/info/search/TableTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
                	shtml.append("<span class=\"sqltable\" ondblclick=\"seltableclick(\'"+sid+"\',\'"+sname+"\')\">"+sname+"</span>");
            	}
            	shtml.append("</div>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        return shtml;
    }
	/**
	 * 取得表描述子节点
	 * @param id
	 * @param smode
	 * @return
	 */
	StringBuffer getchild(String id,String smode) {
		StringBuffer shtml= new StringBuffer("");
		String sid ="",sname = "",sreadonly = "";
        String sql = "select table_id,logicname,readonly from sys_t_table where status = '1' and  super_table_id = '" + id + "' order by sequence";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //取得字段
        	shtml.append(getchildvalue(id));
            while (rs.next()) { 
            	sid = rs.getString("table_id");
            	sname = rs.getString("logicname");
            	//只读
            	sreadonly = rs.getString("readonly");
            	
            	shtml.append("<div style=\"margin-left:12px;width:200px;\" id=\""+sid+"\" class=\"closed\">");
            	if("console".equals(smode)){
            		shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclickconsole(\'page/info/search/TableTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
                	shtml.append("<span class=\"sqltable pointer\" ondblclick=\"seltableclick(\'"+sid+"\',\'"+sname+"\')\">"+sname+"</span>");
                	if("0".equals(sreadonly)){
                		shtml.append("<span class=\"sqltable pointer\" onclick=\"addchild(\'"+sid+"\')\">[扩子表]</span>");
                		shtml.append("<span class=\"sqltable pointer\" onclick=\"addfield(\'"+sid+"\')\">[扩字段]</span>");
                	}
            	}else if("query".equals(smode)){
            		shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclick(\'page/info/search/TableTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
                	shtml.append("<span class=\"sqltable\" ondblclick=\"seltableclick(\'"+sid+"\',\'"+sname+"\')\">"+sname+"</span>");
            	}
            	shtml.append("</div>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
           // DBUtils.close(conn);                //关闭连接
        }
        return shtml;
    }
	/**
	 * 取得表字段描述
	 * @param id
	 * @return
	 */
	StringBuffer getchildvalue(String id) {
		StringBuffer shtml= new StringBuffer("");
		String sfielddivid = "";
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	
		String sid ="",sname = "",sfullname = "",sdicsort = "",sdata = "-2",sorganzation = "-3",sfieldmode = "-1";
        String sql = "select field_id,a.dicsort,a.logicname as onename," +
        			"b.logicname || '.' || a.logicname as allname,a.physicalname,a.fieldtype " +
	        		"from sys_t_field a,sys_t_table b " +
	        		"where a.table_id = b.table_id and a.status = '1' and  a.table_id = '"+ id +"' " +
        				"order by a.sequence";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) {            	
            	sid = rs.getString("field_id");
            	sname = rs.getString("onename");
            	sfullname = rs.getString("allname");
            	sdicsort = rs.getString("dicsort");
            	sorganzation = rs.getString("physicalname");
            	//字段类型
            	sdata = rs.getString("fieldtype");
            	//
            	sfielddivid = "field"+sid;
            	//
            	//机构
            	if("ORGANIZATION_ID".equals(sorganzation)){
            		sfieldmode = constantdefine.FIELDTYPE_DEPT;            		
            	}
            	//日期型
            	else if("3".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_DATE;
            		
            	}
            	//数值型
            	else if("2".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_NUM;
            		
            	}
            	//整型
            	else if("1".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_INT;
            		
            	}
            	//字符型
            	else if("0".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_CHR;
            		
            	}
            	//字典值为大于0的数值[-1为其他操作类型]
            	else{            		
            		sfieldmode = sdicsort;
            	}
            	//log.debug("s:"+sorganzation+"d:"+sdata+"x:"+sdicsort);
            	//log.debug("f:"+sname+":"+sfieldmode);
            	shtml.append("<div style=\"margin-left:12px;\" id=\""+sfielddivid+"\" class=\"closed\" >");
            	shtml.append("<img src=\"/db/page/images/page.png\" id=\"img"+sfielddivid+"\">");
            	//方法说明:selfieldclick(字段编号,逻辑名称,表逻辑名称.字段逻辑名称,字段操作模型)
            	shtml.append("<span class=\"pointer\" ondblclick=\"selfieldclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\',\'"+sfieldmode+"\')\">"+sname+"</span></div>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
           // DBUtils.close(conn);                //关闭连接
        }
        return shtml;
    }
}
