package com.mingda.action.info.search;

import java.sql.*;

import com.mingda.action.info.search.DBUtils;
import com.mingda.common.myjdbc.ConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class DeptTreeServlet extends HttpServlet {
	static Logger log = Logger.getLogger(DeptTreeServlet.class);
	/**
	 * Constructor of the object.
	 */
	public DeptTreeServlet() {
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
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		response.setContentType("text/xml; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();		
		
		String action = request.getParameter("action"); //获取
		String id = request.getParameter("id"); //获取要加载的节点编号
		
		//log.debug(id);
		//获取根节点
	    if ("root".equals(action)) {                 
	    	out.print(getroot(id)); //输出
	    }//获取子节点 
	    else if ("child".equals(action)) {
	    	out.print(getchild(id)); //输出
	    }
	}	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		doGet(request,response);
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
	 * 取得本级节点
	 * @param id
	 * @return
	 */
	StringBuffer getroot(String id) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",sid ="",sname = "",sfullname = "",stid = "",stcid = "";
		
		
		
        String sql = "select organization_id,parentorgid,orgname,fullname,depth from sys_t_organization " +
        		" where organization_id = '" + id + "'";   //定义SQL语句
        log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
           // conn = DBUtils.getConnection();   
            conn = ConnectionManager.getConnection();
            //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
       
            while (rs.next()) {
            	stemp = rs.getString("depth");
            	
            	sid = rs.getString("organization_id");
            	sname = rs.getString("orgname");
            	sfullname= rs.getString("fullname");
            	
            	stid = "dept"+sid;
            	stcid = "deptitem"+sid;
            	//社区
            	
            	if ("5".equals(stemp)){  
            		shtml.append("<div style=\"margin-left:1px;font-size:12px;\" id=\""+stcid+"\" class=\"closed\" ><img src=\"/db/page/images/page.png\" id=\"img"+stcid+"\"><span style=\"cursor:hand\" onclick=\"seldeptclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sname+"</span></div>");
            	}else{
            		//log.debug(sid);
            		boolean brep= esitenddepth(sid , conn);
            		if(brep){
            			//log.debug(brep);
            			shtml.append("<div style=\"margin-left:1px;font-size:12px;\" id=\""+stcid+"\" class=\"closed\" ><img src=\"/db/page/images/page.png\" id=\"img"+stcid+"\"><span style=\"cursor:hand\" onclick=\"seldeptclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sname+"</span></div>");
            		}else{
            			//log.debug(brep);
            			shtml.append("<div style=\"margin-left:1px;font-size:12px;\" id=\""+stid+"\" class=\"closed\"><img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+stid+"\" onclick=\"doclick(\'page/info/search/DeptTreeServlet\',\'"+stid+"\',\'child\')\" style=\"cursor:hand\"><span style=\"cursor:hand\" onclick=\"seldeptclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sname+"</span></div>");
            		}
            	}
            }   
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
      /*      DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);  */              //关闭连接
            
        	ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
			ConnectionManager.closeQuietly();
        }
        shtml.append("<div align=\"center\" id = \"selname\" style=\"font-size:12px;color:#3366CC;\">选中:无</div>");
        shtml.append("<div align=\"center\">");
        shtml.append("<button title = '确定' class='btn' id='btnquery' onclick='okdept()'>确定</button>");
        shtml.append("<button title = '置空' class='btn' id='btnquery' onclick='cleardept()'>置空</button>");
        shtml.append("<button title = '取消' class='btn' id='btnquery' onclick='closedept()'>取消</button>");
        shtml.append("</div>");
        
        return shtml;
    }
	/**
	 * 取得子节点
	 * @param id
	 * @return
	 */
	StringBuffer getchild(String deptid) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",sid ="",sname = "",sfullname = "",stid = "",stcid = "",id= "";
		
		//sid = "dept"+sid;
		id = deptid.substring(4,deptid.length());
		//log.debug(deptid+id);
		
        String sql = "select organization_id,parentorgid,orgname,fullname,depth from sys_t_organization " +
        		" where parentorgid = '" + id + "'";   //定义SQL语句
        log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            //conn = DBUtils.getConnection();     //获取数据库连接
            
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	stemp = rs.getString("depth");
            	sid = rs.getString("organization_id");
            	sname = rs.getString("orgname");
            	sfullname= rs.getString("fullname");
            	
            	stid = "dept"+sid;
            	stcid = "deptitem"+sid;
            	//社区
            	
            	if ("5".equals(stemp)){  
            		shtml.append("<div style=\"margin-left:12px;font-size:12px;\" id=\""+stcid+"\" class=\"closed\" ><img src=\"/db/page/images/page.png\" id=\"img"+stcid+"\"><span style=\"cursor:hand\" onclick=\"seldeptclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sname+"</span></div>");
            	}else{
            		//log.debug(sid);
            		 
            		boolean brep= esitenddepth(sid,conn);
            		 
            		if(brep){
            			//log.debug(brep);
            			shtml.append("<div style=\"margin-left:6px;font-size:12px;\" id=\""+stcid+"\" class=\"closed\" ><img src=\"/db/page/images/page.png\" id=\"img"+stcid+"\"><span style=\"cursor:hand\" onclick=\"seldeptclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sname+"</span></div>");
            		}else{
            			//log.debug(brep);
            			shtml.append("<div style=\"margin-left:6px;font-size:12px;\" id=\""+stid+"\" class=\"closed\"><img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+stid+"\" onclick=\"doclick(\'page/info/search/DeptTreeServlet\',\'"+stid+"\',\'child\')\" style=\"cursor:hand\"><span style=\"cursor:hand\" onclick=\"seldeptclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sname+"</span></div>");
            		}
            	}
            }            
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
         /*   DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
*/            
            ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
			ConnectionManager.closeQuietly();
        }
        return shtml;
    }
	/**
	 * 是否是叶子节点
	 * @param id
	 * @param conn2 
	 * @return
	 */    
	boolean esitenddepth(String id, Connection conn) {
		boolean brep= true;
		String stemp = "";
        String sql = "select organization_id,parentorgid,orgname,depth from sys_t_organization " +
        		" where parentorgid = '" + id + "'";   //定义SQL语句
        
      //  Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
         ResultSet rs = null;                    //声明ResultSet对象
        try {
            //conn = DBUtils.getConnection();     //获取数据库连接
           // conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) {            	
            	brep = false;            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
           /* DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
*/            
            ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
			//ConnectionManager.closeQuietly();
        }
        return brep;
    }
}
