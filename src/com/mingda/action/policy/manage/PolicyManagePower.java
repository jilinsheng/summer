package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mingda.action.info.search.DBUtils;

public class PolicyManagePower {
	static Logger log = Logger.getLogger(PolicyManagePower.class);
	/**
	 * ȡ��ҵ���λȨ���б�
	 * @param pid
	 * @param empid
	 * @return
	 */
    public String getPolicyPostsPower(String pid,String empid) {
        JSONArray array = new JSONArray();      //����JSON����
        String sql = "select post_id,name from sys_t_post where status = '1' order by sequence asc";   //����SQL���
        
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
                obj.put("pid", rs.getString("post_id"));
                obj.put("pname", rs.getString("name"));
                //
                String postid = rs.getString("post_id"); 
                String sflags = getPolicyPostPowers(pid,postid);
                String scheckflag = "0";
        		String scheckmoreflag = "0";
        		String sreportflag = "0";
                if(!sflags.equals("")){//�޸�λȨ������                	
                	String [] aflag = sflags.split(",");
                	scheckflag = aflag[0];
            		scheckmoreflag = aflag[1];
            		sreportflag = aflag[2];                	
                }
                //��Ȩ��
                obj.put("pcheckflag", scheckflag);
                obj.put("pcheckmoreflag", scheckmoreflag);
                obj.put("preportflag", sreportflag);
        		
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
     * ȡ��ҵ���λҵ������Ȩ��(���Ȩ�ޱ�ʶ��,�Ÿ���)
     * @param pid     
     * @param postid
     * @return
     */
    public String getPolicyPostPowers(String pid,String postid) {
    	String  srep = "";
    	String sql = "select checkflag,checkmoreflag,reportflag " +
    			"from biz_t_checkpower " +
    			"where post_id = '"+postid+"' and policy_id = '"+pid+"'";   //����SQL���
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep += rs.getString("checkflag");
            	srep += ","+rs.getString("checkmoreflag");
            	srep += ","+rs.getString("reportflag");            	
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
     * ����ҵ���λȨ�ޱ�
     * @param pid
     * @param postid
     * @param checkflag
     * @param checkmoreflag
     * @param reportflag
     * @return
     */
    String setPolicyPostPowers(String pid,String postid,String checkflag,String checkmoreflag,String reportflag) {
    	String  srep = "";
    	String sql = "";
        
    	if(existsPolicyPostPowers(pid,postid)){
    		//����
    		sql = "update biz_t_checkpower " +
    				"set checkflag = '"+checkflag+"'," +
    				"reportflag = '"+reportflag+"'," +
    				"checkmoreflag = '"+checkmoreflag+"' " +
    				"where policy_id = '"+pid+"' and post_id = '"+postid+"'";
    	}else{
    		//������
    		sql = "insert into biz_t_checkpower " +
    				"(checkpower_id, policy_id, post_id, checkflag, reportflag, checkmoreflag) " +
    				"values " +
    				"(xcheckpower_id.nextval,'"+pid+"','"+postid+"','"+checkflag+"','"+reportflag+"','"+checkmoreflag+"')";	
    	}
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
            srep = "��λȨ�����ò����ɹ�!";
        } catch (SQLException e) {
        	try {
        		srep = "��λȨ�����ò���ʧ��!";
	            conn.rollback();
	        } catch (SQLException e1) {          
	            e1.printStackTrace();
	        }
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
    	return srep;
    }
    /**
     * ҵ���λȨ���Ƿ����
     * @param pid
     * @param postid
     * @return
     */
    public boolean existsPolicyPostPowers(String pid,String postid){
		boolean  brep = false;		
		//
		String sql = "select checkpower_id from biz_t_checkpower " +
					"where policy_id = '"+pid+"' and post_id = '"+postid+"'";  //SQL
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
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
}
