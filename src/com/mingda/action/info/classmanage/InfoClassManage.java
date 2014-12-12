package com.mingda.action.info.classmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;

public class InfoClassManage {
	static Logger log = Logger.getLogger(InfoClassManage.class);
	/**
	 * ȡ�÷����б�
	 * @param mode
	 * @return
	 */
    public StringBuffer getClasssHtml(String mode) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String sid = "",sname = "",sdesc = "",sflag = "",sfid = ""; 
    	//
    	String sql = "";
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>�����б�</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
		if(mode.equals("family")){
    		//��ͥ����
    		sql = "select a.classtype_id,b.logicname,a.explains,a.status,b.field_id " +
    				"from sys_t_classtype a,sys_t_field b,sys_t_table c " +
    					"where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_FAMILYCLASS'";   //����SQL���
    	}else{
    		//��Ա����
    		sql = "select a.classtype_id,b.logicname,a.explains,a.status,b.field_id " +
					"from sys_t_classtype a,sys_t_field b,sys_t_table c " +
						"where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_MEMBERCLASS'";   //����SQL���
    	}
		//
		
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	//
            	sid = rs.getString("classtype_id");
            	sname = rs.getString("logicname");
            	sdesc = rs.getString("explains");
            	sflag = rs.getString("status");
            	sfid = rs.getString("field_id");
            	//
                
                html +="<tr>";		
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+sid+"','"+sname+"','"+sfid+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					html += "<td height='23' class='colValue'>"+sdesc+"</td>";
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"delClass('" + sid + "','" + sfid + "')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"reeditClass('" + sid + "','" + sfid + "')\">����</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        //
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"GetClassItemHtml('')\">��������</button>";    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * ȡ�÷�������
     * @param id
     * @return
     */
    @SuppressWarnings("unused")
	public StringBuffer getClassItemHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",stitle = "",temp = "",mode = "";
    	String sql = "";
    	String sid = "",sname = "",sdesc = "",sflag = "",sfid = ""; 
    	//    	
    	if(id.equals("")){
    		mode = "addclass";   		
    		stitle = "[����]";
    	}else{
    		mode = "editclass"; 
    		//
    		sql = "select a.classtype_id,b.logicname,a.explains,a.status,b.field_id " +
			"from sys_t_classtype a,sys_t_field b,sys_t_table c " +
				"where a.field_id = b.field_id and b.table_id = c.table_id and a.classtype_id = '"+id+"'";   //����SQL���
    		
        	//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	//
                	sid = rs.getString("classtype_id");
                	sname = rs.getString("logicname");
                	sdesc = rs.getString("explains");
                	sflag = rs.getString("status");
                	sfid = rs.getString("field_id");
                	//
                	stitle = "["+sname+"]";
                	//                	
                }
            } catch (SQLException e) {
                log.debug(e.toString());
            } finally {
                DBUtils.close(rs);                  //�رս����
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                DBUtils.close(conn);                //�ر�����
            }
    	}	
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"��������</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
		//		
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'cname' value = '"+sname+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'cdesc' value = '"+sdesc+"'></input></td>";
		html +="</tr>";
		//
		//
    	html +="</table>";
    	
    	
    	if(mode.equals("addclass")){//����
	    	html += "<button class = 'btn' onclick=\"SaveClass('"+mode+"')\">����</button>"; 	   		
    	}else if(mode.equals("editclass")){//�༭
    		//����ʱ��������
	    	if(sflag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"sqlaction()\">��������</button>";
	    		html += "<button class = 'btn' onclick=\"SaveClass('"+mode+"')\">����</button>";
	    		//�Ƿ��Ѿ���������
	    		if(existsClassSql(sid)){
	    			html += "<button class = 'btn' onclick=\"refsysinfo()\">ˢ��ϵͳ����</button>"; 
	    		}	    		 
	    	}		    	
    	}
    	//	
    	html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	   
        return shtml;
    }
    /**
     * �Ƿ��Ѿ����÷�������
     * @param parid
     * @return
     */
    public boolean existsClassSql(String parid){
		boolean  brep = false;
		
		String sql = "";
		sql = "select classtype_id, field_id,physql, logicsql from sys_t_classtype where  physql is not null and  classtype_id = '"+parid+"'";
		//		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				brep = true;
				break;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
}
