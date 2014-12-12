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

import com.mingda.common.ConstantDefine;

public class DiscTreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(DiscTreeServlet.class);
	/**
	 * Constructor of the object.
	 */
	public DiscTreeServlet() {
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

		response.setContentType("text/xml; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();		
		String action = request.getParameter("action"); //获取
		String id = request.getParameter("id"); //获取要加载的节点编号
		
		//log.debug(id + action);
		//获取根节点
	    if ("root".equals(action)) {                 
	    	out.print(getroot(id)); //输出
	    }//获取子节点 
	    else if ("child".equals(action)) {
	    	out.print(getchild(id)); //输出
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
	 * 取得字典根节点
	 * @param id
	 * @return
	 */
	StringBuffer getroot(String id) {
		StringBuffer shtml= new StringBuffer("");
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	String replacechr = constantdefine.REPLACEEXP_CHR;
    	
		String sid ="",sname = "";
		
		if(id.length()<=0){
			return new StringBuffer("你没有选择相关字典选项!");
			
		}
        String sql = "select dictsort_id,name from sys_t_dictsort where dictsort_id = '"+ id +"' order by sequence";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	sid = rs.getString("dictsort_id");
            	sname = rs.getString("name");
            	sid = "disc"+sid;
            	shtml.append("<div style=\"margin-left:12px;width:180px;\" id=\""+sid+"\" class=\"closed\">");
            	shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclick(\'page/info/search/DiscTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
            	shtml.append("<span style=\"color:#3366CC;font-size:12px;font-weight:bold;\">"+sname+"["+replacechr+"表示代码]"+"</span></div>");            		
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        shtml.append("<div align=\"center\" id = \"selname\" style=\"font-size:12px;font-weight:bold;color:#3366CC;\">选中:无</div>");
        shtml.append("<div align=\"center\">");
        shtml.append("<input name=\"BtnOk\" type=\"button\" value=\"确定\" onclick=\"okdisc()\"/>");
        shtml.append("<input name=\"BtnClose\" type=\"button\" value=\"取消\" onclick=\"closedisc()\"/>");
        shtml.append("</div>");
        return shtml;
    }
	/**
	 * 取得字典描述子节点
	 * @param discid
	 * @return
	 */
	StringBuffer getchild(String discid) {
		StringBuffer shtml= new StringBuffer("");
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	String replacechr = constantdefine.REPLACEEXP_CHR;
    	
		String id = "",sid ="",sname = "",sfullname = "";
		//sid = "disc"+sid;
		id = discid.substring(4,discid.length());
		//log.debug(discid+id);
		
        String sql = "select dictionary_id,item from sys_t_dictionary where dictsort_id = '" + id + "' order by sequence";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	sid = rs.getString("dictionary_id");
            	sname = rs.getString("item");
            	sfullname = "["+sname+replacechr+sid+"]";
            	shtml.append("<div style=\"margin-left:12px;\" id=\"discitem"+sid+"\" class=\"closed\">");
            	shtml.append("<img src=\"/db/page/images/page.png\" id=\"imgdiscitem"+sid+"\">");
            	shtml.append("<span style=\"font-size:12px;\" class = \"pointer\" onclick=\"seldiscclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\')\">"+sfullname+"</span></div>");	
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
}
