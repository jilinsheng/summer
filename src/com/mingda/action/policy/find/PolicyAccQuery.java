package com.mingda.action.policy.find;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;


public class PolicyAccQuery {	
	static Logger log = Logger.getLogger(PolicyAccQuery.class);
	/**
	 * ��ȡҵ�����������Ϣ
		 * optacc_id
		 * -1�޽������α��
		 * accflag
		 * -1δ���µĽ�������
		 * 0�½�������
		 * 1���ڽ�������
		 * 2�����������
		 * accmoneyflag
		 * 0δ���������ʶ
		 * 1���������ʶ
		 * accmoney
		 * 0�޶��������
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public HashMap getPolicyAccInfo(String pid,String deptid){
		HashMap hashmap = new HashMap();  
    	//
		String sql = "select " 
    		+ "optacc_id,accflag,accmoneyflag,accmoney " 
    		+ "from biz_t_optacc " 
    		+ "where policy_id = '"+pid+"' " 
    		+ "and instr('#' || '"+deptid+"', '#' || organization_id) > 0  "
    		+ "order by accdt desc";
        //
		int irow = 0;
    	//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			if(rs.next()){
				hashmap.put("optid", rs.getString("optacc_id"));
				hashmap.put("accflag", rs.getString("accflag"));
				hashmap.put("accmoneyflag", rs.getString("accmoneyflag"));
				hashmap.put("accmoney", rs.getString("accmoney"));
				irow++;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    DBUtils.close(conn);                //�ر�����
		}
		if(irow == 0){
			hashmap.put("optid", "-1");
			hashmap.put("accflag", "-1");
			hashmap.put("accmoneyflag","0");
			hashmap.put("accmoney", "0");
		}
		return hashmap;
	}
    /**
     * ȡ��ҵ���������
     * @param pid
     * @return
     */
    public String getPolicyAccTypeFlag(String pid) {
        String srep = "0";
        //
        String sql = "select acctype from doper_t_policy where policy_id = '" + pid + "' ";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	srep = rs.getString("acctype");              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ��ȡ����������Ϣ����
     * @param accid
     * @return
     */
    public String getNowPolicyAcc(String accid){
    	String srep = "";
    	String sql = "";
    	 sql = "select optacc_id,accyear,accmonth,accdesc,accflag " 
         	+ "from biz_t_optacc " 
         	+ "where optacc_id = '" + accid + "' ";   //����SQL���
    	//log.debug(sql);
         Connection conn = null;                 //����Connection����
         PreparedStatement pstmt = null;         //����PreparedStatement����
         ResultSet rs = null;                    //����ResultSet����
         try {
             conn = DBUtils.getConnection();     //��ȡ���ݿ�����
             pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
             rs = pstmt.executeQuery();            
             if (rs.next()) {             	
            	 srep = rs.getString("accyear") + " ��   "; 
            	 srep += rs.getString("accmonth") + " ��   ";
            	 //srep += " ��������:"+ rs.getString("accdesc");
            	 String tmp = rs.getString("accflag");
            	 if("0".equals(tmp)){
            		 srep += " ״̬:δ����  ����ҵ����԰���";
            	 }else if("1".equals(tmp)){
            		 srep += " ״̬:������  ����ҵ���Ժ����";
            	 }else if("2".equals(tmp)){
            		 srep += " ״̬:�������  ����ҵ���ܰ���";
            	 }            	 
             }
         } catch (SQLException e) {
             log.debug(e.toString());
         } finally {
             DBUtils.close(rs);                  //�رս����
             DBUtils.close(pstmt);               //�ر�PreparedStatement
             DBUtils.close(conn);                //�ر�����
         }
    	return srep;
    }
    /**
     * �Ƿ����ҵ���������
     * @param hashmap
     * @return
     */
    public boolean existsPolicyAcc(HashMap hashmap) {
    	boolean brep = false;
    	String sql = "",tempsql = "";
    	//
    	String jdeptid =hashmap.get("jdeptid").toString();
    	String jpid = hashmap.get("jpid").toString();
		String jyear = hashmap.get("jyear").toString();
		String jmonth = hashmap.get("jmonth").toString();
		String jdesc = hashmap.get("jdesc").toString();
        //
        sql = "select optacc_id " 
        	+ "from biz_t_optacc " 
        	+ "where policy_id = '" + jpid + "' "
        	+ "and accyear = '" + jyear + "' "
        	+ "and accmonth = '" + jmonth + "' " 
        	+ "and organization_id = '"+jdeptid+"' ";   //����SQL���
        //�������(ũ��ͱ�21)ÿ��һ��
        if(!"21".equals(jpid)){
        	if(!"".equals(jdesc)){
            	tempsql = "and accdesc = '" + jdesc + "' ";
            	sql += tempsql;
            }       
        }
        //
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	String accid = rs.getString("optacc_id");  
            	brep = true;
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
    /**
     * �������Ƿ�δ����,�ȴ�����
     * @param accid
     * @return
     */
    public boolean existsNowPolicyAcc(String accid){
    	boolean brep = false;
    	String sql = "";
    	 sql = "select optacc_id " 
         	+ "from biz_t_optacc " 
         	+ "where accflag = '0' and optacc_id = '" + accid + "' ";   //����SQL���
    	log.debug(sql);
         Connection conn = null;                 //����Connection����
         PreparedStatement pstmt = null;         //����PreparedStatement����
         ResultSet rs = null;                    //����ResultSet����
         try {
             conn = DBUtils.getConnection();     //��ȡ���ݿ�����
             pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
             rs = pstmt.executeQuery();            
             if (rs.next()) {             	
             	brep = true;
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
