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
	 * ��ȡ����ҵ�������Ϣ���ñ�
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
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>������</td>";
			html +=temp;			
			temp ="<td height='23'>�ֶ�����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select c.policyinfo_id,c.status,b.logicname as tablename,a.logicname as fieldname " +
        		"from sys_t_field a,sys_t_table b,doper_t_policyinfo c " +
        		"where a.table_id = b.table_id and a.field_id = c.field_id " +
        			"and a.status = '1' and c.policy_id = '"+pid+"' " +
        		"order by a.logicname,b.logicname";   //����SQL���
        
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
            	sid = rs.getString("policyinfo_id");
            	stname = rs.getString("tablename");
            	sfname = rs.getString("fieldname");
            	sflag = rs.getString("status");
            	
                
                html +="<tr>";		
		    		//����ֵ	
	                html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/close.gif' alt = 'ɾ��' onclick=\"DeletePolicyInfo('"+sid+"','"+stname + "','" + sfname + "')\" />";									
					html += "</td>";
                	html += "<td height='23' class='colValue status"+sflag+"'>"+stname+"</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sfname+"</td>";
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePolicyInfoStatus('" + sid + "','" + stname + "','" + sfname + "','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePolicyInfoStatus('" + sid + "','" + stname + "','" + sfname + "','1')\">����</button></td>";	
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
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
	/**
	 * ��������ҵ�������Ϣ��
	 * @param pid
	 * @param fid
	 * @return
	 */
	public String insertPolicyInfo(String pid,String fid) {
    	String srep = "";
    	//�Ƿ��������ҵ�������Ϣ
    	if(existsPolicyinfo(pid,fid)){
    		srep = "�Ѿ���������ҵ�������Ϣ!";
    		return srep;
    	}
		//
		
		String sql = "insert into doper_t_policyinfo " +
				"(policyinfo_id,policy_id,field_id,status)" +
				" values " +
				"(xpolicyinfo_id.nextval,'"+pid+"','"+fid+"','1')";
		//log.debug(sql);
	      
  	  	Connection conn = null;                 //����Connection����
  	  	PreparedStatement pstmt = null;         //����PreparedStatement����
  	  	try {
  	  		conn = DBUtils.getConnection();     //��ȡ���ݿ�����
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
  	  		pstmt.execute();              //ִ��  	  		 
  	  		conn.commit();                      //�ر�
  	  		//
  	  		srep = "��������ҵ�������Ϣ������ɹ�!";
          
  	  	} catch (SQLException e) {  	  		
  	  		try {
  	  			srep = "��������ҵ�������Ϣ����ʧ��!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //�ر�PreparedStatement
  	  		DBUtils.close(conn);                //�ر�����
  	  	}
    	return srep;
    }
	/**
	 * ��������ҵ�������Ϣ��
	 * @param id
	 * @param status
	 * @return
	 */
	public String updatePolicyInfoStatus(String id,String status) {
    	String srep = "";
		//		
		String sql = "update doper_t_policyinfo set status = '"+status+"' where policyinfo_id = '"+id+"'";
		//log.debug(sql);
	      
  	  	Connection conn = null;                 //����Connection����
  	  	PreparedStatement pstmt = null;         //����PreparedStatement����
  	  	try {
  	  		conn = DBUtils.getConnection();     //��ȡ���ݿ�����
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
  	  		pstmt.execute();              //ִ��  	  		 
  	  		conn.commit();                      //�ر�
  	  		//
  	  		if("1".equals(status)){
  	  			srep = "��������ҵ�������Ϣ������ɹ�!";
  	  		}else{
  	  			srep = "ͣ������ҵ�������Ϣ������ɹ�!";
  	  		}
  	  	} catch (SQLException e) {
  	  		try {
	  	  		if("1".equals(status)){
	  	  			srep = "��������ҵ�������Ϣ�����ʧ��!";
	  	  		}else{
	  	  			srep = "ͣ������ҵ�������Ϣ�����ʧ��!";
	  	  		}
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //�ر�PreparedStatement
  	  		DBUtils.close(conn);                //�ر�����
  	  	}
    	return srep;
    }
	/**
	 * ɾ������ҵ�������Ϣ��
	 * @param id
	 * @return
	 */
	public String deletePolicyInfo(String id) {
    	String srep = "";
		//		
		String sql = "delete doper_t_policyinfo where policyinfo_id = '"+id+"'";
		//log.debug(sql);
	      
  	  	Connection conn = null;                 //����Connection����
  	  	PreparedStatement pstmt = null;         //����PreparedStatement����
  	  	try {
  	  		conn = DBUtils.getConnection();     //��ȡ���ݿ�����
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
  	  		pstmt.execute();              //ִ��  	  		 
  	  		conn.commit();                      //�ر�
  	  		//
  	  		srep = "ɾ������ҵ�������Ϣ������ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "ɾ������ҵ�������Ϣ����ʧ��!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //�ر�PreparedStatement
  	  		DBUtils.close(conn);                //�ر�����
  	  	}
    	return srep;
    }
	/**
	 * �Ƿ���ڸ�ҵ�������Ϣ���ñ�
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
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
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
