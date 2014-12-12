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
		String action = request.getParameter("action"); //��ȡ
		String id = request.getParameter("id"); //��ȡҪ���صĽڵ���
		
		//log.debug(id + action);
		//��ȡ���ڵ�
	    if ("root".equals(action)) {                 
	    	out.print(getroot(id)); //���
	    }//��ȡ�ӽڵ� 
	    else if ("child".equals(action)) {
	    	out.print(getchild(id)); //���
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
	 * ȡ���ֵ���ڵ�
	 * @param id
	 * @return
	 */
	StringBuffer getroot(String id) {
		StringBuffer shtml= new StringBuffer("");
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	String replacechr = constantdefine.REPLACEEXP_CHR;
    	
		String sid ="",sname = "";
		
		if(id.length()<=0){
			return new StringBuffer("��û��ѡ������ֵ�ѡ��!");
			
		}
        String sql = "select dictsort_id,name from sys_t_dictsort where dictsort_id = '"+ id +"' order by sequence";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	sid = rs.getString("dictsort_id");
            	sname = rs.getString("name");
            	sid = "disc"+sid;
            	shtml.append("<div style=\"margin-left:12px;width:180px;\" id=\""+sid+"\" class=\"closed\">");
            	shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclick(\'page/info/search/DiscTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
            	shtml.append("<span style=\"color:#3366CC;font-size:12px;font-weight:bold;\">"+sname+"["+replacechr+"��ʾ����]"+"</span></div>");            		
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        shtml.append("<div align=\"center\" id = \"selname\" style=\"font-size:12px;font-weight:bold;color:#3366CC;\">ѡ��:��</div>");
        shtml.append("<div align=\"center\">");
        shtml.append("<input name=\"BtnOk\" type=\"button\" value=\"ȷ��\" onclick=\"okdisc()\"/>");
        shtml.append("<input name=\"BtnClose\" type=\"button\" value=\"ȡ��\" onclick=\"closedisc()\"/>");
        shtml.append("</div>");
        return shtml;
    }
	/**
	 * ȡ���ֵ������ӽڵ�
	 * @param discid
	 * @return
	 */
	StringBuffer getchild(String discid) {
		StringBuffer shtml= new StringBuffer("");
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	String replacechr = constantdefine.REPLACEEXP_CHR;
    	
		String id = "",sid ="",sname = "",sfullname = "";
		//sid = "disc"+sid;
		id = discid.substring(4,discid.length());
		//log.debug(discid+id);
		
        String sql = "select dictionary_id,item from sys_t_dictionary where dictsort_id = '" + id + "' order by sequence";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        return shtml;
    }
}
