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
		String action = request.getParameter("action"); //��ȡ
		String id = request.getParameter("id"); //��ȡҪ���صĽڵ���		
		String smode = request.getParameter("mode");//����ģ�鹦��'query'ֻ����ѯ'console'���Կ��� 		
		//log.debug(id + action + smode);
		//��ȡ���ڵ�
	    if ("root".equals(action)) {                 
	    	out.print(getroot(id,smode)); //���
	    }//��ȡ�ӽڵ� 
	    else if ("child".equals(action)) {
	    	out.print(getchild(id,smode)); //���
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
	 * ȡ�ñ��������ڵ�
	 * @param id
	 * @param smode
	 * @return
	 */
	StringBuffer getroot(String id,String smode) {
		StringBuffer shtml= new StringBuffer("");
		String sid ="",sname = "",sreadonly = "";
		//id��ʱ����[ֱ�ӵ�����Ϣ�����ڵ�]
		
        String sql = "select table_id,logicname,readonly from sys_t_table where status = '1' and  depth = '0' order by sequence";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
	            		shtml.append("<span class=\"sqltable pointer\" onclick=\"addchild(\'"+sid+"\')\">[���ӱ�]</span>");
	            		shtml.append("<span class=\"sqltable pointer\" onclick=\"addfield(\'"+sid+"\')\">[���ֶ�]</span>");
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        return shtml;
    }
	/**
	 * ȡ�ñ������ӽڵ�
	 * @param id
	 * @param smode
	 * @return
	 */
	StringBuffer getchild(String id,String smode) {
		StringBuffer shtml= new StringBuffer("");
		String sid ="",sname = "",sreadonly = "";
        String sql = "select table_id,logicname,readonly from sys_t_table where status = '1' and  super_table_id = '" + id + "' order by sequence";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //ȡ���ֶ�
        	shtml.append(getchildvalue(id));
            while (rs.next()) { 
            	sid = rs.getString("table_id");
            	sname = rs.getString("logicname");
            	//ֻ��
            	sreadonly = rs.getString("readonly");
            	
            	shtml.append("<div style=\"margin-left:12px;width:200px;\" id=\""+sid+"\" class=\"closed\">");
            	if("console".equals(smode)){
            		shtml.append("<img src=\"/db/page/images/nolines_plus.gif\" id=\"img"+sid+"\" onclick=\"doclickconsole(\'page/info/search/TableTreeServlet\',\'"+sid+"\',\'child\')\" style=\"cursor:hand\">");
                	shtml.append("<span class=\"sqltable pointer\" ondblclick=\"seltableclick(\'"+sid+"\',\'"+sname+"\')\">"+sname+"</span>");
                	if("0".equals(sreadonly)){
                		shtml.append("<span class=\"sqltable pointer\" onclick=\"addchild(\'"+sid+"\')\">[���ӱ�]</span>");
                		shtml.append("<span class=\"sqltable pointer\" onclick=\"addfield(\'"+sid+"\')\">[���ֶ�]</span>");
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
           // DBUtils.close(conn);                //�ر�����
        }
        return shtml;
    }
	/**
	 * ȡ�ñ��ֶ�����
	 * @param id
	 * @return
	 */
	StringBuffer getchildvalue(String id) {
		StringBuffer shtml= new StringBuffer("");
		String sfielddivid = "";
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	
		String sid ="",sname = "",sfullname = "",sdicsort = "",sdata = "-2",sorganzation = "-3",sfieldmode = "-1";
        String sql = "select field_id,a.dicsort,a.logicname as onename," +
        			"b.logicname || '.' || a.logicname as allname,a.physicalname,a.fieldtype " +
	        		"from sys_t_field a,sys_t_table b " +
	        		"where a.table_id = b.table_id and a.status = '1' and  a.table_id = '"+ id +"' " +
        				"order by a.sequence";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            
            while (rs.next()) {            	
            	sid = rs.getString("field_id");
            	sname = rs.getString("onename");
            	sfullname = rs.getString("allname");
            	sdicsort = rs.getString("dicsort");
            	sorganzation = rs.getString("physicalname");
            	//�ֶ�����
            	sdata = rs.getString("fieldtype");
            	//
            	sfielddivid = "field"+sid;
            	//
            	//����
            	if("ORGANIZATION_ID".equals(sorganzation)){
            		sfieldmode = constantdefine.FIELDTYPE_DEPT;            		
            	}
            	//������
            	else if("3".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_DATE;
            		
            	}
            	//��ֵ��
            	else if("2".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_NUM;
            		
            	}
            	//����
            	else if("1".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_INT;
            		
            	}
            	//�ַ���
            	else if("0".equals(sdata)){
            		sfieldmode = constantdefine.FIELDTYPE_CHR;
            		
            	}
            	//�ֵ�ֵΪ����0����ֵ[-1Ϊ������������]
            	else{            		
            		sfieldmode = sdicsort;
            	}
            	//log.debug("s:"+sorganzation+"d:"+sdata+"x:"+sdicsort);
            	//log.debug("f:"+sname+":"+sfieldmode);
            	shtml.append("<div style=\"margin-left:12px;\" id=\""+sfielddivid+"\" class=\"closed\" >");
            	shtml.append("<img src=\"/db/page/images/page.png\" id=\"img"+sfielddivid+"\">");
            	//����˵��:selfieldclick(�ֶα��,�߼�����,���߼�����.�ֶ��߼�����,�ֶβ���ģ��)
            	shtml.append("<span class=\"pointer\" ondblclick=\"selfieldclick(\'"+sid+"\',\'"+sname+"\',\'"+sfullname+"\',\'"+sfieldmode+"\')\">"+sname+"</span></div>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
           // DBUtils.close(conn);                //�ر�����
        }
        return shtml;
    }
}
