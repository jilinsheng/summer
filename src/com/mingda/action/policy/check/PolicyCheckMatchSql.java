package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;

public class PolicyCheckMatchSql {
	static Logger log = Logger.getLogger(PolicyCheckMatchSql.class);
	/**
	 * ɾ����������ҵ���ͥ���߳�Ա��׼��
	 * @param checkid
	 * @return
	 */
	public String deletePolicyCheckSql(String checkid) {
    	String srep = "";
		String sql = "";
		//
		sql = "delete biz_t_optchecksql where optcheck_id = '"+checkid+"'";  //����״̬SQL
		
		//log.debug(sql1);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��		   
		    conn.commit();                     //�ر�		    
		    srep = "ɾ����������ҵ�񵵴β����ɹ�!";
		} catch (SQLException e) {			
			try {
				srep = "ɾ����������ҵ�񵵴β���ʧ��!";
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
	 * ������������ҵ���ͥ���߳�Ա��׼��
	 * @param hashmap
	 * @return
	 */
	public String updatePolicyCheckSql(HashMap hashmap) {
    	String srep = "",sql = "";
    	//
	    String checkid = hashmap.get("checkid").toString();
		String physql = hashmap.get("physql").toString();
		String locsql = hashmap.get("locsql").toString();
		String accphysql = hashmap.get("accphysql").toString();
		String acclocsql = hashmap.get("acclocsql").toString();
    	//
		//������ת��ȥ�����ҿո�
		physql = physql.replace("'","''");
		locsql = locsql.replace("'","''");
		accphysql = accphysql.replace("'","''");
		acclocsql = acclocsql.replace("'","''");
		//
		if(existsPolicyCheckSql(checkid)){
			sql = "update biz_t_optchecksql " +
				"set physql = '"+physql+"'," +
				"locsql = '"+locsql+"'," +
				"accphysql = '"+accphysql+"'," +
				"acclocsql = '"+acclocsql+"' " +
				"where optcheck_id = '"+checkid+"'";
		}else{
			sql = "insert into biz_t_optchecksql " +
					"(optchecksql_id,optcheck_id,physql,locsql,accphysql,acclocsql) " +
					"values " +
					"(xoptchecksql_id.nextval,'"+checkid+"','"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"')";
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
  	  		srep = "������������ҵ�񵵴β����ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "������������ҵ�񵵴β���ʧ��!";
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
	 * �Ƿ������������ҵ���׼��׼
	 * @param checkid
	 * @return
	 */
	public boolean existsPolicyCheckSql(String checkid){
		boolean  brep = false;
		
		String sql = "select optchecksql_id from biz_t_optchecksql where optcheck_id = '"+checkid+"'";   //����SQL���
		
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
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
}
