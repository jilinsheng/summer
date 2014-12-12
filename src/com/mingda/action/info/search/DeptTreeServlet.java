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
		
		String action = request.getParameter("action"); //��ȡ
		String id = request.getParameter("id"); //��ȡҪ���صĽڵ���
		
		//log.debug(id);
		//��ȡ���ڵ�
	    if ("root".equals(action)) {                 
	    	out.print(getroot(id)); //���
	    }//��ȡ�ӽڵ� 
	    else if ("child".equals(action)) {
	    	out.print(getchild(id)); //���
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
	 * ȡ�ñ����ڵ�
	 * @param id
	 * @return
	 */
	StringBuffer getroot(String id) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",sid ="",sname = "",sfullname = "",stid = "",stcid = "";
		
		
		
        String sql = "select organization_id,parentorgid,orgname,fullname,depth from sys_t_organization " +
        		" where organization_id = '" + id + "'";   //����SQL���
        log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
           // conn = DBUtils.getConnection();   
            conn = ConnectionManager.getConnection();
            //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            
       
            while (rs.next()) {
            	stemp = rs.getString("depth");
            	
            	sid = rs.getString("organization_id");
            	sname = rs.getString("orgname");
            	sfullname= rs.getString("fullname");
            	
            	stid = "dept"+sid;
            	stcid = "deptitem"+sid;
            	//����
            	
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
      /*      DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);  */              //�ر�����
            
        	ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
			ConnectionManager.closeQuietly();
        }
        shtml.append("<div align=\"center\" id = \"selname\" style=\"font-size:12px;color:#3366CC;\">ѡ��:��</div>");
        shtml.append("<div align=\"center\">");
        shtml.append("<button title = 'ȷ��' class='btn' id='btnquery' onclick='okdept()'>ȷ��</button>");
        shtml.append("<button title = '�ÿ�' class='btn' id='btnquery' onclick='cleardept()'>�ÿ�</button>");
        shtml.append("<button title = 'ȡ��' class='btn' id='btnquery' onclick='closedept()'>ȡ��</button>");
        shtml.append("</div>");
        
        return shtml;
    }
	/**
	 * ȡ���ӽڵ�
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
        		" where parentorgid = '" + id + "'";   //����SQL���
        log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            //conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	stemp = rs.getString("depth");
            	sid = rs.getString("organization_id");
            	sname = rs.getString("orgname");
            	sfullname= rs.getString("fullname");
            	
            	stid = "dept"+sid;
            	stcid = "deptitem"+sid;
            	//����
            	
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
         /*   DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
*/            
            ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
			ConnectionManager.closeQuietly();
        }
        return shtml;
    }
	/**
	 * �Ƿ���Ҷ�ӽڵ�
	 * @param id
	 * @param conn2 
	 * @return
	 */    
	boolean esitenddepth(String id, Connection conn) {
		boolean brep= true;
		String stemp = "";
        String sql = "select organization_id,parentorgid,orgname,depth from sys_t_organization " +
        		" where parentorgid = '" + id + "'";   //����SQL���
        
      //  Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
         ResultSet rs = null;                    //����ResultSet����
        try {
            //conn = DBUtils.getConnection();     //��ȡ���ݿ�����
           // conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) {            	
            	brep = false;            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
           /* DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
*/            
            ConnectionManager.closeQuietly(rs);
			ConnectionManager.closeQuietly(pstmt);
			//ConnectionManager.closeQuietly();
        }
        return brep;
    }
}
