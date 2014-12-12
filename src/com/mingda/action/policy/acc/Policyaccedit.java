package com.mingda.action.policy.acc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
public class Policyaccedit {
	static Logger log = Logger.getLogger(Policyaccedit.class);
	/**
	 * �������Ƿ��Ѿ�ҵ���������
	 * �Ƿ������
	 * (�������̨���̴�����ϱ�ʶ)
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String getPolicyAllCheckAccNoOverFlag(String pid,String empid){
		String  srep = "0";
		String empdepid = "";
	 	//
    	// ���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//
    	String sql = "select optacc_id,accflag from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and accoverflag = '0' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 " +
    							"order by accflag asc";
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
				srep = "1";
				break;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		  //  DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
	/**
	 * ��ǰҵ�������������߳����������
	 * @param empid
	 * @param pid
	 * @param mode
	 * @return
	 */
	public String setPolicyAllAccFlag(String empid,String pid,String mode){
		String srep = "",soptaccid = "";
	    //����ҵ���ѯ������
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();		
	    //
    	//�Ƿ�������ڰ����ҵ�����������
    	soptaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	if(soptaccid.equals("2")){
    		srep = "��ǰҵ��δ����,����ʧ��!";
    		return srep;    		
    	}
    	//���������������������
    	if(mode.equals("1")){//�������   
	    	//	
    	}else if(mode.equals("0")){//����������� 
    		//�������������
    		return "�������������";
    	}
    	//
    	//
	    String sql  = "update biz_t_optacc set accflag = '" +mode+"',accdt = sysdate,accoper = '"+empid+"'  " +
						"where optacc_id = '"+soptaccid+"'";
		//log.debug(sql);
		
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
			pstmt.executeUpdate();              //ִ��            
			conn.commit();                      //�ر�
			if(mode.equals("1")){
				srep = "�����������ɹ�!";
			}else{
				srep = "���������������ɹ�!";
			}			
		} catch (SQLException e) {
			try {
				if(mode.equals("1")){
			    	srep = "����������ʧ��!";
			    }else{
			    	srep = "��������������ʧ��!";
			    }
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
	 * ������ǰҵ�����������ݺ��·�
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public String setPolicyAllCheckAccItems(String pid,String empid,String year,String month){
		String srep = "",empdepid = "",soptaccid = "";			
	 	//
		// ���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//����ҵ���ѯ������
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//�Ƿ�������ڰ����ҵ�����������
    	soptaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	if(soptaccid.equals("2")){
    		//���������ҵ����������¼
    		//
        	empdepid = tableinfoquery.getempdepid(empid);
    		//��ǰ����·��Ƿ����
    		if(existsPolicyAllCheckAccItems(pid,empid,year,month)){
    			srep = "������������Ѿ�����,����ʹ��!";
    		}else{
    			String sql  = "insert into biz_t_optacc " +
    					"(optacc_id, policy_id, organization_id, accyear, accmonth,begoper,begdt)" +
    					"values " +
    					"(xoptacc_id.nextval,'"+pid+"','"+empdepid+"','"+year+"','"+month+"','"+empid+"',sysdate)";  //SQL
    		    //log.debug(sql);
    		    
    		    Connection conn = null;                 //����Connection����
    		    PreparedStatement pstmt = null;         //����PreparedStatement����
    		    try {
    		        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
    		        conn.setAutoCommit(false);
    		        pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
    		        pstmt.execute();              //ִ��            
    		        conn.commit();                      //�ر�
    		        srep = "����������������ڲ����ɹ�!";
    		    } catch (SQLException e) {
    		    	try {
    		    		srep = "����������������ڲ���ʧ��!";
    		            conn.rollback();
    		        } catch (SQLException e1) {          
    		            e1.printStackTrace();
    		        }
    		    } finally {
    		        DBUtils.close(pstmt);               //�ر�PreparedStatement
    		      //  DBUtils.close(conn);                //�ر�����
    		    }
    		}   		
    	}else{
    		srep = "��ǰҵ����δ����������,����ʧ��!";
    		return srep; 
    	}    	
		return srep;
	}
	/**
	 * ɾ����ǰҵ�����������ݺ��·�
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public String deletePolicyAllCheckAccItems(String pid,String empid,String year,String month){
		String srep = "",empdepid = "";			
	 	//
		// ���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//��ǰ����·��Ƿ����
		if(existsPolicyAllCheckAccItemsAcc(pid,empid,year,month)){
			srep = "������������Ѿ�����,�����Ѿ��������!";
		}else{
			String sql  = "delete biz_t_optacc where accflag = '0' and policy_id = '"+pid+"' " +
					"and accyear = '"+year+"' and accmonth = '"+month+"'  " +
					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0";  //SQL
		    //log.debug(sql);
		    
		    Connection conn = null;                 //����Connection����
		    PreparedStatement pstmt = null;         //����PreparedStatement����
		    try {
		        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		        conn.setAutoCommit(false);
		        pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		        pstmt.execute();              //ִ��            
		        conn.commit();                      //�ر�
		        srep = "ɾ������������ڲ����ɹ�!";
		    } catch (SQLException e) {
		    	try {
		    		srep = "ɾ������������ڲ���ʧ��!";
		            conn.rollback();
		        } catch (SQLException e1) {          
		            e1.printStackTrace();
		        }
		    } finally {
		        DBUtils.close(pstmt);               //�ر�PreparedStatement
		      //  DBUtils.close(conn);                //�ر�����
		    }
		}    	
		return srep;
	}
  /**
   * ȡ�õ�ǰҵ�����������ݺ��·�
   * @param pid
   * @param empid
   * @return
   */
	public String getPolicyAllCheckAccYearItems(String pid,String empid){		
		String srep = "",optaccid = "";
		//����ҵ���ѯ������
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();    	
    	optaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	//�����ڰ���ҵ������
    	if(optaccid.equals("2")){
    		return srep;
    	}
    	String sql = "select optacc_id,accyear,accmonth from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and optacc_id = '"+optaccid+"'";
        //
    	//log.debug("sql:"+sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
           
            while (rs.next()) {                
            	srep = rs.getString("accyear");                
                break;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
           // DBUtils.close(conn);                //�ر�����
        }
        return srep;
	}
	/**
	 * ȡ�õ�ǰҵ����������·�
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String getPolicyAllCheckAccMonthItems(String pid,String empid){		
		String srep = "",optaccid = "";
		//����ҵ���ѯ������
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();     	
    	optaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	//�����ڰ���ҵ������
    	if(optaccid.equals("2")){
    		return srep;
    	}
    	String sql = "select optacc_id,accyear,accmonth from biz_t_optacc " +
    					"where policy_id = '"+pid+"' and optacc_id = '"+optaccid+"'";
    	//log.debug(sql);
    	Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
           
            while (rs.next()) {                
            	srep = rs.getString("accmonth");
            	break;                
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
           // DBUtils.close(conn);                //�ر�����
        }        
        return srep;
	}
	/**
	 * �ж��Ƿ�����µ�ҵ�����������ݺ��·�
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean existsPolicyAllCheckAccItems(String pid,String empid,String year,String month){
		boolean  brep = false;
    	String empdepid = ""; 	 	
	 	//
    	// ���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//��������ʶ
    	String sql = "select optacc_id from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and accyear = '"+year+"' and accmonth = '"+month+"' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 order by accflag asc";
        //
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
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
	/**
	 * �ж��Ƿ�����µ�ҵ�����������ݺ��·ݲ����Ѿ��������
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean existsPolicyAllCheckAccItemsAcc(String pid,String empid,String year,String month){
		boolean  brep = false;
    	String empdepid = ""; 	 	
	 	//
    	// ���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//��������ʶ
    	String sql = "select optacc_id from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and accyear = '"+year+"' and accmonth = '"+month+"' and accflag ='1' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 order by accflag asc";
        //
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
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
