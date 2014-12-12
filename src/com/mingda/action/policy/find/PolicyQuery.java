package com.mingda.action.policy.find;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;

public class PolicyQuery {
	static Logger log = Logger.getLogger(PolicyQuery.class);
	/**
	 * ��ҵ���Ż�ȡҵ������
	 * @param pid
	 * @return
	 */
	public String getPolicyDescFromId(String pid) {
		String srep = "";
		String sql = "select descr " + "from doper_t_policy "
				+ "where flag = '1' and policy_id = '" + pid + "' ";
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
		    log.debug("���±��ϵ�0007"+sql);
			rs = pstmt.executeQuery();
			//            
			if (rs.next()) {
				srep = rs.getString("descr");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}
	/**
     * ��ҵ����ȡ��ҵ�����(0����1ũ��)
     * @param pid
     * @return
     */
    public String getPolicyObjTypeFromId(String pid){
    	String srep = "0";
    	String sql = "select " 
    		+ "objtype " 
    		+ "from doper_t_policy " 
    		+ "where flag = '1' and policy_id = '"+pid+"' ";
    	//log.debug(sql);
    	Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement 
            log.debug("���±��ϵ�0008"+sql);
            rs = pstmt.executeQuery();
            //            
            if (rs.next()) {
            	//
            	srep = rs.getString("objtype");            	
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
     * ��ȡҵ���Ƿ�ǿ��ִ�б�׼����
     * @param pid
     * @return
     */
    public String getPolicyStandardType(String pid) {
    	String srep = "0",sup = "",nup = "";
        
        String sql = "select superpolicy,notpolicy from doper_t_standard where flag=1 and  policy_id = '" + pid + "' ";   //����SQL���
        log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            log.debug("���±��ϵ�0009"+sql);
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	sup = rs.getString("superpolicy");
            	nup = rs.getString("notpolicy");
            	//log.debug("sup:"+sup);
            	//log.debug("nup:"+nup);
            	if(null != nup || null != nup){
            		srep="1";
            	}         	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        log.debug("srep:"+srep);
        return srep;
	}
    /**
	 * ��ȡҵ���Ƿ������д�������ʶ
	 * 0������1�������
	 * @param pid
	 * @return
	 */
	public String getPolicyUserAccFlag(String pid) {
    	String srep = "0";
        
        String sql = "select useraccflag from biz_t_checkflow where policy_id = '" + pid + "' ";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            log.debug("���±��ϵ�0010"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	srep = rs.getString("useraccflag");            	
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
	 * ��ȡ�û�����Ȩ����Ϣ
	 * @param pid
	 * @param empid
	 * @return
	 */
	public HashMap getPolicyCheckFlags(String depth,String empid,String pid) {
    	HashMap hashmap = new HashMap();
    	boolean brow = false;
    	String oneflag = "0",endflag = "0";
    	//��ȡ�Ƿ��ǵ�һ��������
    	if(depth.equals(getPolicyOneFlowFromId(pid))){
    		oneflag = "1";
    	}
    	//��ȡ�Ƿ���������������
    	if(depth.equals(getPolicyEndFlowFromId(pid))){
    		endflag = "1";
    	}
    	//
    	hashmap.put("onecheck", oneflag);    
    	hashmap.put("endcheck", endflag); 
    	//
    	String sql = "select "     		
    		+ "a.checkflag checkflag," 
    		+ "b.checkflag morecheck," 
    		+ "a.checkmoreflag usermorecheck," 
    		+ "a.reportflag reportflag " 
    		+ "from biz_t_checkpower a,biz_t_checkflow b,sys_t_employee c,sys_t_empext d " 
    		+ "where a.policy_id = b.policy_id and a.post_id = c.post_id and c.employee_id = d.employee_id " 
    		+ "and d.empext_id = '"+empid+"' and a.policy_id = '"+pid+"' ";   //����SQL���
        
        log.debug("���±��ϵ�0001"+sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement  
            log.debug("���±��ϵ�0011"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	hashmap.put("usercheck", rs.getString(1));    
            	hashmap.put("morecheck", rs.getString(2)); 
            	hashmap.put("usermorecheck", rs.getString(3)); 
            	hashmap.put("report", rs.getString(4)); 
            	brow = true;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        if(!brow){
        	hashmap.put("usercheck",0);    
        	hashmap.put("morecheck",0); 
        	hashmap.put("usermorecheck",0); 
        	hashmap.put("report",0); 
        }
    	return hashmap;
    }

    /**
     * ȡ��ҵ����������Ϊ��һ�����Ļ�������
     * @param id
     * @return
     */
    public String getPolicyOneFlowFromId(String id) {
    	String srep = "0",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
    	//��������
        ConstantDefine constantdefine = new ConstantDefine();
        //
    	String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 " 
    		+ "from biz_t_checkflow " 
    		+ "where policy_id = '" + id + "' ";   //����SQL���
        log.debug("���±��ϵ�0002"+sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            log.debug("���±��ϵ�0013"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");
            	if (appstate1.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_5;
				} else if (appstate2.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_4;
				} else if (appstate3.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_3;
				} else if (appstate4.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_2;
				} else if (appstate5.equals("1")){
					srep = constantdefine.POLICYQUERYCODE_1;
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
     * ȡ��ҵ����������Ϊ��������Ļ�������
     * @param id
     * @return
     */
    public String getPolicyEndFlowFromId(String id) {
    	String srep = "0",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
    	//��������
        ConstantDefine constantdefine = new ConstantDefine();
        //
    	String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 " 
    		+ "from biz_t_checkflow " 
    		+ "where policy_id = '" + id + "' ";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement 
            log.debug("���±��ϵ�0003"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");
            	if (appstate5.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_1;
				} else if (appstate4.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_2;
				} else if (appstate3.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_3;
				} else if (appstate2.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_4;
				} else if (appstate1.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_5;
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
     * ǰ��
     * ȡ������������һ����������
     * ���ػ�������[5����4�ֵ�3��2��1ʡ]
     * ����0��˵��������������������
     * @param id
     * @param empid
     * @return
     */
    public String getPolicyNextFlowFromId(String id, String empid) {
		String srep = "0", empdepth = "", appstate1 = "", appstate2 = "", appstate3 = "", appstate4 = "", appstate5 = "";
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		//
		empdepth = tableinfoquery.getempdepth(empid);
		srep = empdepth;
		//
		String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 "
				+ "from biz_t_checkflow " + "where policy_id = '" + id + "' "; // ����SQL���
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
		    log.debug("���±��ϵ�0004"+sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				appstate1 = rs.getString("appstate1");
				appstate2 = rs.getString("appstate2");
				appstate3 = rs.getString("appstate3");
				appstate4 = rs.getString("appstate4");
				appstate5 = rs.getString("appstate5");
				if (empdepth.equals(constantdefine.POLICYQUERYCODE_5)) {
					srep = constantdefine.POLICYQUERYCODE_5;
					if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_4)) {
					srep = constantdefine.POLICYQUERYCODE_4;
					if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_3)) {
					srep = constantdefine.POLICYQUERYCODE_3;
					if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_2)) {
					srep = constantdefine.POLICYQUERYCODE_2;
					if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_1)) {
					srep = constantdefine.POLICYQUERYCODE_1;
				}

			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}
    /**
     * ����
     * ȡ������������һ����������
     * ���ػ�������[5����4�ֵ�3��2��1ʡ]
     * ����0��˵��������������������
     * @param id
     * @param empid
     * @return
     */
    public String getPolicyBackFlowFromId(String id,String empid) {
        String srep = "0", empdepth = "", appstate1 = "", appstate2 = "", appstate3 = "", appstate4 = "", appstate5 = "";
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// ��������
		ConstantDefine constantdefine = new ConstantDefine();

		//
		empdepth = tableinfoquery.getempdepth(empid);
		srep = empdepth;
		//
		String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 "
				+ "from biz_t_checkflow " + "where policy_id = '" + id + "' "; // ����SQL���
		// log.debug(sql);
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
		    log.debug("���±��ϵ�0005"+sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				appstate1 = rs.getString("appstate1");
				appstate2 = rs.getString("appstate2");
				appstate3 = rs.getString("appstate3");
				appstate4 = rs.getString("appstate4");
				appstate5 = rs.getString("appstate5");
				if (empdepth.equals(constantdefine.POLICYQUERYCODE_1)) {
					srep = constantdefine.POLICYQUERYCODE_1;
					if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_2)) {
					srep = constantdefine.POLICYQUERYCODE_2;
					if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_3)) {
					srep = constantdefine.POLICYQUERYCODE_3;
					if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_4)) {
					srep = constantdefine.POLICYQUERYCODE_4;
					if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_5)) {
					srep = constantdefine.POLICYQUERYCODE_5;
				}

			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			DBUtils.close(conn); // �ر�����
		}
		return srep;
	}
    /**
     * ��ȡ������������
     * @param pid
     * @return
     */
    public HashMap getPolicyFlowsFromId(String pid) {
    	HashMap  hashmap = new HashMap();
        
        String depth1 = "0",depth2 = "0",depth3 = "0",depth4 = "0",depth5 = "0";
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + pid + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement 
            log.debug("���±��ϵ�0006"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	depth1 = rs.getString("appstate1");
            	depth2 = rs.getString("appstate2");
            	depth3 = rs.getString("appstate3");
            	depth4 = rs.getString("appstate4");
            	depth5 = rs.getString("appstate5");            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        //
        hashmap.put("depth1", depth1);
        hashmap.put("depth2", depth2);
        hashmap.put("depth3", depth3);
        hashmap.put("depth4", depth4);
        hashmap.put("depth5", depth5);
        return hashmap;
    }    
}
