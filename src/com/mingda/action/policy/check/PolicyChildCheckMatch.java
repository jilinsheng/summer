package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;

/**
 * ����ʩ��
 * ������������
 * ���ϱ�׼����
 * @author xiu
 *
 */
public class PolicyChildCheckMatch {
	static Logger log = Logger.getLogger(PolicyChildCheckMatch.class);
    /**
     * ���õ�����ͥ���߳�Ա����ʩ��������
     * @param hashmap
     * @return
     */
	public String SetPolciyChildMatchOne(HashMap hashmap){
    	String  srep = "0";
    	String checkchildid = "",physql = "",locsql = "";
    	//
	    String pid = hashmap.get("pid").toString();
		String checkid = hashmap.get("checkid").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//�Ƿ���ڷ���ʩ��
		if(!existsPolicyChildFromPid(pid)){
			return srep;
		}
		//
		String sql = "select a.policychild_id,a.sqltype from doper_t_policychild a,doper_t_policy b " +
				"where a.status = '1' and a.policy_id = b.policy_id and b.policy_id = '"+pid+"'";   //����SQL���
		
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
				String childpid = rs.getString("policychild_id"); 
				String sqltype = rs.getString("sqltype");
				if(null != childpid){
					if("2".equals(sqltype)){
						//��Ա����
						HashMap  hashmapmem = new HashMap();
						hashmapmem = getPolicyChildMemberSql(childpid,memid);
						physql = hashmapmem.get("physql").toString();
						locsql = hashmapmem.get("locsql").toString();
						//�Ƿ���Ϸ���ʩ����׼
						if(!"".equals(physql)){
							//���·���ʩ��������
					    	checkchildid = GetCheckChildMember(childpid,memid);					    	
							if("".equals(checkchildid)){//������								
								//��ӷ���ʩ��������ͥ��Ա��¼
								HashMap hashmapinsert = new HashMap();
								hashmapinsert.put("checkid", checkid);
								hashmapinsert.put("childpid", childpid);
								hashmapinsert.put("fmid", fmid);
								hashmapinsert.put("memid", memid);
								hashmapinsert.put("physql", physql);
								hashmapinsert.put("locsql", locsql);
								//
								insertPolicyChildCheck(hashmapinsert);
							}else{//���ڷ���ʩ��������¼
								//����
								updatePolicyChildCheck(checkchildid,physql,locsql);
							}							
						}else{
							//�����ϱ�׼
							//ɾ������ʩ��������ͥ��Ա��¼
							deletePolicyChildCheckMember(childpid,memid);					    	
						}						
					}else{
						//��ͥ����
						HashMap  hashmapfm = new HashMap();
						hashmapfm = getPolicyChildFamilySql(childpid,fmid);
						physql = hashmapfm.get("physql").toString();
						locsql = hashmapfm.get("locsql").toString();
						//�Ƿ���Ϸ���ʩ����׼
						if(!"".equals(physql)){
							//���·���ʩ��������
							checkchildid = GetCheckChildFamily(childpid,fmid);
							if("".equals(checkchildid)){//������								
								//��ӷ���ʩ��������ͥ��¼
								HashMap hashmapinsert = new HashMap();
								hashmapinsert.put("checkid", checkid);
								hashmapinsert.put("childpid", childpid);
								hashmapinsert.put("fmid", fmid);
								hashmapinsert.put("memid", memid);
								hashmapinsert.put("physql", physql);
								hashmapinsert.put("locsql", locsql);
								//
								insertPolicyChildCheck(hashmapinsert);
							}else{//���ڷ���ʩ��������¼
								//����
								updatePolicyChildCheck(checkchildid,physql,locsql);
							}
						}else{
							//�����ϱ�׼
							//ɾ������ʩ��������ͥ��¼
							deletePolicyChildCheckFamily(childpid,fmid);
						}
					}
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
     * ��ͥ�Ƿ���Ϸ���ʩ����׼
     * @param childpid
     * @param fmid
     * @return
     */
    public HashMap getPolicyChildFamilySql(String childpid,String fmid) {
    	HashMap  hashmap = new HashMap();
    	String physql = "",locsql = "",status = "0";
    	//
        String sql = "select physql,locsql,status from doper_t_policychildsql " +
        		"where physql is not null " +
        		"and policychild_id = '"+childpid+"' ";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	physql = rs.getString("physql");
            	locsql = rs.getString("locsql");
            	status = rs.getString("status");
            	//
            	if(status.equals("0")){//ͣ��
            		break;
            	}
            	if(null == physql || "".equals(physql)){//û�����ñ�׼����
            		break;
            	}            	
            	//����SQL���������Ƿ��������
            	boolean brep = existsFamilyIdFromPolicyChild(physql,fmid);
            	if(brep){//��������
            		break;
            	}else{
            		physql = "";
            		locsql = "";
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
    	hashmap.put("physql", physql);
    	hashmap.put("locsql", locsql);
    	//
        return hashmap;
    }

    /**
     * ��Ա�Ƿ���Ϸ���ʩ����׼
     * @param childpid
     * @param memid
     * @return
     */
    public HashMap getPolicyChildMemberSql(String childpid,String memid) {
    	HashMap  hashmap = new HashMap();
    	String physql = "",locsql = "",status = "";
    	//
    	//
        String sql = "select physql,locsql,status from doper_t_policychildsql " +
        		"where physql is not null " +
        		"and policychild_id = '"+childpid+"' ";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	physql = rs.getString("physql");
            	locsql = rs.getString("locsql");
            	status = rs.getString("status");
            	//
            	if(status.equals("0")){//ͣ��
            		break;
            	}
            	if(null == physql || "".equals(physql)){//û�����ñ�׼����
            		break;
            	}            	
            	//����SQL���������Ƿ��������
            	boolean brep = existsMemberIdFromPolicyChild(physql,memid);
            	if(brep){//��������            		
            		break;
            	}else{
            		physql = "";
            		locsql = "";
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
    	hashmap.put("physql", physql);
    	hashmap.put("locsql", locsql);
    	//
        return hashmap;
    }
    /**
     * �������ʩ����������Ϣ
     * @param hashmap
     * @return
     */
    public String insertPolicyChildCheck(HashMap hashmap) {
    	String srep = "",schildcheckid = "";
		String sql1 = "",sql2 = "";
		String checkid = hashmap.get("checkid").toString();
		String childpid = hashmap.get("childpid").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		String physql = hashmap.get("physql").toString();
		String locsql = hashmap.get("locsql").toString();
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //������ת��ȥ�����ҿո�
		physql = physql.replace("'","''");
		locsql = locsql.replace("'","''");
	    //
		schildcheckid = tableinfoquery.getseqfromname("XOPTCHECKCHILD_ID");
		sql1 = "insert into biz_t_optcheckchild " +
				"(optcheckchild_id, policychild_id,optcheck_id,  family_id, member_id) " +
				"values " +
				"('"+schildcheckid+"','"+childpid+"','"+checkid+"','"+fmid+"','"+memid+"')";  //���µ���״̬SQL
		
		sql2 = "insert into biz_t_optcheckchildsql " +
				"(optcheckchildsql_id,optcheckchild_id,physql,locsql) " +
				"values (xoptcheckchildsql_id.nextval,'"+schildcheckid+"','"+physql+"','"+locsql+"')";
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql2);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    conn.commit();                     //�ر�		    
		    srep = "������������ʩ�������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "������������ʩ������ʧ��!";
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
     * ������������ʩ����׼
     * @param checkchildid
     * @param physql
     * @param locsql
     * @return
     */
    public String updatePolicyChildCheck(String checkchildid,String physql,String locsql) {
    	String srep = "",sql = "";
    	//������ת��ȥ�����ҿո�
		physql = physql.replace("'","''");
		locsql = locsql.replace("'","''");
	    //
    	//
    	sql = "update biz_t_optcheckchildsql " +
    			"set physql = '"+physql+"'," +
    			"locsql = '"+locsql+"' " +
    			"where optcheckchild_id = '"+checkchildid+"'";
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
  	  		srep = "������������ʩ����׼�����ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "������������ʩ����׼����ʧ��!";
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
     * ɾ������ʩ��������ͥ��¼
     * @param childpid
     * @param fmid
     * @return
     */
    public String deletePolicyChildCheckFamily(String childpid,String fmid) {
    	String srep = "";
		String sql1 = "",sql2 = "",sql3 = "";
		
		sql1 = "delete biz_t_optcheckchildidea " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and family_id ='"+fmid+"')";  //����״̬SQL
		sql2 = "delete biz_t_optcheckchildsql " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and family_id ='"+fmid+"')";  //����״̬SQL
		sql3 = "delete biz_t_optcheckchild where policychild_id = '"+childpid+"' and family_id ='"+fmid+"'";  //����״̬SQL
		//log.debug(sql1);
		//log.debug(sql2);
		//log.debug(sql3);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql2);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql3);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    conn.commit();                     //�ر�		    
		    srep = "ɾ������ʩ�������ɹ�!";
		} catch (SQLException e) {			
			try {
				srep = "ɾ������ʩ������ʧ��!";
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
     * ɾ������ʩ��������ͥ��Ա��¼
     * @param childpid
     * @param memid
     * @return
     */
    public String deletePolicyChildCheckMember(String childpid,String memid) {
    	String srep = "";
		String sql1 = "",sql2 = "",sql3 = "";
		
		sql1 = "delete biz_t_optcheckchildidea " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and member_id ='"+memid+"')";  //����״̬SQL
		sql2 = "delete biz_t_optcheckchildsql " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and member_id ='"+memid+"')";  //����״̬SQL
		sql3 = "delete biz_t_optcheckchild where policychild_id = '"+childpid+"' and member_id ='"+memid+"'";  //����״̬SQL
		//log.debug(sql1);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql2);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql3);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    conn.commit();                     //�ر�		    
		    srep = "ɾ������ʩ�������ɹ�!";
		} catch (SQLException e) {			
			try {
				srep = "ɾ������ʩ������ʧ��!";
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
     * ɾ������ʩ��������ͥ��¼
     * @param hashmap
     * @return
     */
    public String deletePolicyChildCheck(HashMap hashmap) {
    	String srep = "";
		String sql1 = "",sql2 = "",sql3 = "";
		//
	    String pid = hashmap.get("pid").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//
		sql1 = "delete biz_t_optcheckchildidea " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild a,doper_t_policychild b " +
				"where a.policychild_id = b.policychild_id and b.policy_id = '"+pid+"' " +
						"and family_id ='"+fmid+"' and member_id ='"+memid+"')";  //����״̬SQL
		sql2 = "delete biz_t_optcheckchildsql " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild a,doper_t_policychild b " +
				"where a.policychild_id = b.policychild_id and b.policy_id = '"+pid+"' " +
						"and family_id ='"+fmid+"' and member_id ='"+memid+"')";  //����״̬SQL
		sql3 = "delete biz_t_optcheckchild " +
				"where policychild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild a,doper_t_policychild b " +
				"where a.policychild_id = b.policychild_id and b.policy_id = '"+pid+"' " +
						"and family_id ='"+fmid+"' and member_id ='"+memid+"')";  //����״̬SQL
		//log.debug(sql1);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql2);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql3);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    conn.commit();                     //�ر�		    
		    srep = "ɾ������ʩ�������ɹ�!";
		} catch (SQLException e) {
			try {
				srep = "ɾ������ʩ������ʧ��!";
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
	 * ȡ�÷���ʩ�����û��������ID
	 * @param empid
	 * @param childid
	 * @return
	 */
	public String getPolicyChildChickIdeas(String empid,String childid) {
        String srep = "";
        String sql = "select optcheckchildidea_id from biz_t_optcheckchildidea " +
        		"where optcheckchild_id = '"+childid+"' and childcheckoper = '"+empid+"'";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("optcheckchildidea_id");        
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
     * ȡ�÷���ʩ����������ͥ
     * @param childpid
     * @param fmid
     * @return
     */
    public String GetCheckChildFamily(String childpid,String fmid){
    	String srep = "";
		
		String sql = "select optcheckchild_id,optcheck_id from biz_t_optcheckchild " +
				"where policychild_id = '"+childpid+"' and family_id = '"+fmid+"'";   //����SQL���
		
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
				srep = rs.getString("optcheckchild_id");
				if(null == srep){
					srep = "";
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
     * ȡ�÷���ʩ����������ͥ��Ա
     * @param childpid
     * @param memid
     * @return
     */
    public String GetCheckChildMember(String childpid,String memid){
		String srep = "";
		String sql = "select optcheckchild_id,optcheck_id from biz_t_optcheckchild " +
				"where policychild_id = '"+childpid+"' and member_id = '"+memid+"'";   //����SQL���
		
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
				srep = rs.getString("optcheckchild_id");
				if(null == srep){
					srep = "";
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
     * ��ͥ�Ƿ���Ϸ���ʩ������
     * @param physql
     * @param fmid
     * @return
     */
    public boolean existsFamilyIdFromPolicyChild(String physql,String fmid){
		boolean  brep = false;
		
		String sql = physql + " and  INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";   //����SQL���
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
    /**
     * ��Ա�Ƿ���Ϸ���ʩ������
     * @param physql
     * @param memid
     * @return
     */
    public boolean existsMemberIdFromPolicyChild(String physql,String memid){
		boolean  brep = false;
		
		String sql = physql + " and  INFO_T_MEMBER.MEMBER_ID = '"+memid+"'";   //����SQL���
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
	/**
     * �Ƿ���ڷ���ʩ������
     * @param pid
     * @return
     */
    public boolean existsPolicyChildFromPid(String pid){
		boolean  brep = false;
		
		String sql = "select a.policychild_id from doper_t_policychild a,doper_t_policy b " +
				"where a.status = '1' and a.policy_id = b.policy_id and b.policy_id = '"+pid+"'";   //����SQL���
		
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
