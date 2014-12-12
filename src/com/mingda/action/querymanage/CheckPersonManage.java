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
 * ���������˹�����
 * @author xiu
 *
 */
public class CheckPersonManage {
	static Logger log = Logger.getLogger(CheckPersonManage.class);
	/**
	 * ȡ�øû��������������б�
	 * @param sdeptid
	 * @return
	 */
	public String getCheckPersons(String sdeptid){
		JSONArray array = new JSONArray();      //����JSON����
		//���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
        //����SQL���
        String sql = "select optreviewperson_id,name,sex,face,officephone,officename,post,address,organization_id,status from biz_t_optreviewperson where organization_id = '"+sdeptid+"'";   
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //�������������JSON�����м���JSONObject
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return array.toString();
	}
	/**
	 * ȡ����������������
	 * @param sid
	 * @return
	 */
	public String getCheckPersonItems(String sid){
		JSONArray array = new JSONArray();      //����JSON����
        //����SQL���
        String sql = "select optreviewperson_id,name,sex,face,officephone,officename,post,address,organization_id,status from biz_t_optreviewperson where optreviewperson_id = '"+sid+"'";   
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //�������������JSON�����м���JSONObject
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return array.toString();
	}
	/**
	 * ����������״̬
	 * @param Id
	 * @param Status
	 * @return
	 */
	public String updateCheckPersonStatus(String Id,String Status) {
	    String srep = "";
		String sql = "update biz_t_optreviewperson set status = '"+Status+"' where optreviewperson_id = '"+Id+"'";  //���µ���״̬SQL
		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		    pstmt.executeUpdate();              //ִ��
		    conn.commit();                      //�ر�
		    if("0".equals(Status)){
		    	srep = "ͣ�������˲����ɹ�!";
		    }else{
		    	srep = "���������˲����ɹ�!";
		    }
		} catch (SQLException e) {
			if("0".equals(Status)){
		    	srep = "ͣ�������˲���ʧ��!";
		    }else{
		    	srep = "���������˲���ʧ��!";
		    }
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
	/**
	 * ��������������
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
				srep = "�����������Ѿ�����!";
				return srep;
			}
		}
    	String sql = Sql;  //���µ���״̬SQL
    	
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		    pstmt.executeUpdate();              //ִ��
		    conn.commit();                      //�ر�
		    if(Mode.equals("add")){
		    	srep = "��������˱�������ɹ�!";
		    }else if(Mode.equals("edit")){
		    	srep = "���������˱�������ɹ�!";
		    }
		    
		} catch (SQLException e) {
			if(Mode.equals("add")){
				srep = "��������˱������ʧ��!";
		    }else if(Mode.equals("edit")){
		    	srep = "���������˱������ʧ��!";
		    }
			
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
	/**
	 * �Ƿ����������
	 * @param sname
	 * @param sdeptid
	 * @return
	 */
    boolean existsCheckPerson(String sname,String sdeptid) {
    	boolean brep = false;
        String sql = "select optreviewperson_id from biz_t_optreviewperson where name =  '"+sname+"' and organization_id = '"+sdeptid+"'";   
        
        Connection conn = null;                 //����Connection����
	    PreparedStatement pstmt = null;         //����PreparedStatement����
	    ResultSet rs = null;                    //����ResultSet����
	    try {
	        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
	        //���ò���
	        rs = pstmt.executeQuery();
	        while (rs.next()) {	
	        	//����
	        	brep = true;
	        }
	    } catch (SQLException e) {
	        Log4jApp.logger(e.toString());
	    } finally {
	        DBUtils.close(rs);                  //�رս����
	        DBUtils.close(pstmt);               //�ر�PreparedStatement
	        //DBUtils.close(conn);                //�ر�����
	    }		    
        return brep;
    }
    /**
     * ȡ������������������
     * @param sname
     * @param empid
     * @return
     */
    public StringBuffer getCheckPersonChoice(String sname,String empid){
    	StringBuffer shtml= new StringBuffer("");
    	String id = "",name = "",deptid = "";
    	boolean isexists = false;
    	// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
	    deptid = tableinfoquery.getempdepid(empid);
	    //
    	String sql = "select optreviewperson_id,name from biz_t_optreviewperson where status = '1' and organization_id = '"+deptid+"'";      
        //log.debug(sql);
    	
    	//
    	Connection conn = null;                 //����Connection����
    	PreparedStatement pstmt = null;         //����PreparedStatement����
    	ResultSet rs = null;                    //����ResultSet����
    	try {
    		conn = DBUtils.getConnection();     //��ȡ���ݿ�����
    		pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
    		//����
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
    		DBUtils.close(rs);                  //�رս����
    		DBUtils.close(pstmt);               //�ر�PreparedStatement
    		//DBUtils.close(conn);                //�ر�����
    	}
    	//�Ƿ����������
        if(!isexists){
        	StringBuffer stemp= new StringBuffer("");
        	return stemp;
        }
    	return shtml;
    }
}
