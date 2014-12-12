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
 * ��Ϣ�Ϸ�У�������
 * @author xiu
 *
 */
public class InfoValidate {
	static Logger log = Logger.getLogger(InfoValidate.class);
	
	/**
	 * ȡ����Ϣ�Ϸ���֤�б�
	 * @return
	 */
	public String getInfoValidates(){
		//���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
		JSONArray array = new JSONArray();      //����JSON����
        //����SQL���
        String sql = "select validateterm_id,name,itemdesc,type,physql,locsql,status from biz_t_validateterm";   
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
                obj.put("vid", rs.getString("validateterm_id"));
                obj.put("vname", rs.getString("name"));
                //
                String temp = rs.getString("type");
                temp = tableinfoquery.getdiscionaryfromid(temp);
                obj.put("vtype",temp);
                //
                obj.put("vdesc", rs.getString("itemdesc"));  
                obj.put("vphysql", rs.getString("physql"));
                obj.put("vlocsql", rs.getString("locsql"));
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
	 * ȡ����Ϣ�Ϸ���֤����
	 * @param sid
	 * @return
	 */
	public String getInfoValidateItems(String sid){
		JSONArray array = new JSONArray();      //����JSON����
        //����SQL���
        String sql = "select validateterm_id,name,itemdesc,type,physql,locsql,status from biz_t_validateterm where validateterm_id = '"+sid+"'";   
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
                obj.put("vid", rs.getString("validateterm_id"));
                obj.put("vname", rs.getString("name"));
                obj.put("vdesc", rs.getString("itemdesc"));
                obj.put("vtype", rs.getString("type")); 
                obj.put("vphysql", rs.getString("physql"));
                obj.put("vlocsql", rs.getString("locsql"));
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
     * ȡ����Ϣ�Ϸ���֤SQL���
     * @param sid
     * @param mode
     * @return
     */
    public String getInfoValidateSqlItem(String sid,String mode) {
        String srep = "";
        String sql = "select physql,locsql from biz_t_validateterm where validateterm_id = '"+sid+"'";   
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	if(mode.equals("loc")){
            		srep = rs.getString("locsql");
            	}else if(mode.equals("phy")){
            		srep = rs.getString("physql");
            	}              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
	/**
	 * ������֤״̬
	 * @param Id
	 * @param Status
	 * @return
	 */
	public String updateValidateStatus(String Id,String Status) {
	    String srep = "";
		String sql = "update biz_t_validateterm set status = '"+Status+"' where validateterm_id = '"+Id+"'";  //���µ���״̬SQL
		
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
		    	srep = "ͣ����֤�����ɹ�!";
		    }else{
		    	srep = "������֤�����ɹ�!";
		    }
		} catch (SQLException e) {
			if("0".equals(Status)){
		    	srep = "ͣ����֤����ʧ��!";
		    }else{
		    	srep = "������֤����ʧ��!";
		    }
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
	/**
	 * ������Ϣ�Ϸ���֤����
	 * @param Mode
	 * @param Name
	 * @param Sql
	 * @return
	 */
	public String updateValidate(String Mode,String Name,String Sql) {
		String srep = "";
		//
		if(Mode.equals("add")){
			if(existsValidate(Name)){
				srep = "��Ϣ�Ϸ���֤�����Ѿ�����!";
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
		    	srep = "�����֤��������ɹ�!";
		    }else if(Mode.equals("edit")){
		    	srep = "������֤��������ɹ�!";
		    }
		    
		} catch (SQLException e) {
			if(Mode.equals("add")){
				srep = "�����֤�������ʧ��!";
		    }else if(Mode.equals("edit")){
		    	srep = "������֤�������ʧ��!";
		    }
			
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
	/**
	 * ������Ϣ�Ϸ���֤����SQL���
	 * @param Id
	 * @param PhySql
	 * @param LocSql
	 * @return
	 */
	public String updateValidateSql(String Id,String PhySql,String LocSql) {
		String srep = "";
		//������ת��ȥ�����ҿո�
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update biz_t_validateterm set " +
				"physql = '"+PhySql+"'," +
				"locsql = '"+LocSql+"' where validateterm_id = '"+Id+"'";  //���µ���״̬SQL
		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		    pstmt.executeUpdate();              //ִ��
		    conn.commit();                      //�ر�
		    srep = "������֤������������ɹ�!";
		} catch (SQLException e) {
			srep = "������֤�����������ʧ��!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
	/**
	 * �Ƿ������Ϣ��֤
	 * @param sname
	 * @return
	 */
    boolean existsValidate(String sname) {
    	boolean brep = false;
        String sql = "select validateterm_id from biz_t_validateterm where name =  '"+sname+"'";   
        
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
}
