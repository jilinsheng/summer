package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

public class PolicyManageCheckManage {
	static Logger log = Logger.getLogger(PolicyManageCheckManage.class);
	/**
     * ��ǰҵ���Ƿ��Ѿ��������
     * ���ؽ��:0��δ�������1�Ѿ��������2�������������
     * @param pid
     * @param empid
     * @return
     */
    public String getPolicyAllAccFlag(String pid,String empid){
		String  srep = "0",optaccid = ""; 
		//��������������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery(); 
	 	//
	 	optaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid, empid);
	 	if(optaccid.equals("2")){
	 		//û��Ҫ������������ҵ��
    		srep = "2";
    		return srep;    		
    	}
	 	//
    	//�Ƿ���������ʶ
    	String sql = "select accflag from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and optacc_id = '"+optaccid+"' ";
        //
    	//log.debug("sql:"+sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
            	srep = rs.getString("accflag");
            	break;
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
	 * ��ȡҵ���Ƿ��������������ʶ
	 * 0����1��������
	 * @param pid
	 * @return
	 */
	public String getPolicyCheckFlag(String pid) {
    	String srep = "0";
        
        String sql = "select checkflag from biz_t_checkflow where policy_id = '" + pid + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("checkflag");            	
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
     * ȡ��ҵ���λҵ������Ȩ����ر�ʶ(�����ʶ��,�Ÿ���)
     * @param pid
     * @param empid
     * @return
     */
	public String getPolicyPostPowerFlags(String pid,String empid) {
    	String  srep = "";
    	String sql = "select a.checkflag,a.checkmoreflag,a.reportflag " +
    			"from biz_t_checkpower a,sys_t_employee b,sys_t_empext c " +
    			"where a.post_id = b.post_id and b.employee_id = c.employee_id " +
    			"and c.empext_id = '"+empid+"' and a.policy_id = '"+pid+"'";   //����SQL���
        
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
	 * ת���������Ϊ����˵��
	 * @param checkid
	 * @return
	 */
	public String repalcePolicyCheckFlag(String checkid){
		String srep = "";
		//��������
	    ConstantDefine constantdefine = new ConstantDefine();
	    if(checkid.equals(constantdefine.POLICYCHECKCODE_NEW)){
	    	srep = constantdefine.POLICYCHECK_NEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_OK)){
	    	srep = constantdefine.POLICYCHECK_OK;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_RENEW)){
	    	srep = constantdefine.POLICYCHECK_RENEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_MOVE)){
	    	srep = constantdefine.POLICYCHECK_MOVE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_OLE)){
	    	srep = constantdefine.POLICYCHECK_OLE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_STOP)){
	    	srep = constantdefine.POLICYCHECK_STOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_REM)){
	    	srep = constantdefine.POLICYCHECK_REM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_NOTOK)){
	    	srep = constantdefine.POLICYCHECK_NOTOK;
	    }
		return srep;
	}
	/**
     * ��������
     * ������ͥID
     * ����0��������1������
     * (���������ڱ��������ϼ�δ����������º˲���ɳ��������������) 
     * @param empid
     * @param pid
     * @param fmid
     * @return
     */
    public String getPolicyIsRmCheckFlag(String empid,String pid,String fmid) {
    	String srep = "0",stemp = "";
    	String empdepth = "",nextdepth = "",backdepth = "";
        String empcheckflag = "",nextcheckflag = "",newresult = "",remresult = "";
        // ���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      //��������������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery(); 
      	//
	    //��������
        ConstantDefine constantdefine = new ConstantDefine();
        //
        empdepth = tableinfoquery.getempdepth(empid);
        backdepth = policymanagecheckquery.getPolicyBackFlowFromId(pid, empid);
        nextdepth = policymanagecheckquery.getPolicyNextFlowFromId(pid, empid);
        //
        newresult = constantdefine.POLICYCHECKCODE_NEW;
        remresult = constantdefine.POLICYCHECKCODE_RENEW;
        //
        if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	empcheckflag = "checkflag1";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	empcheckflag = "checkflag2";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	empcheckflag = "checkflag3";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	empcheckflag = "checkflag4";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	empcheckflag = "checkflag5";
        }
        //
        //
        if(nextdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	nextcheckflag = "checkflag1";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	nextcheckflag = "checkflag2";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	nextcheckflag = "checkflag3";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	nextcheckflag = "checkflag4";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	nextcheckflag = "checkflag5";
        }
        //
        String sql = "select optcheck_id from biz_t_optcheck " +
        		"where policy_id = '" + pid + "' and family_id = '" + fmid + "' ";
        //
		if(empdepth.equals(nextdepth)){
			stemp = "and (" +
							"(ifover = '"+empdepth+"' and ("+empcheckflag+" <> '"+newresult+"')) " +
						")";   //����SQL���
			sql = sql + stemp;
		}else{
			stemp = "and (" +
							"(ifover = '"+empdepth+"' and ("+empcheckflag+" <> '"+newresult+"')) " +
							"or " +
							"(ifover = '"+nextdepth+"' and ("+nextcheckflag+" = '"+newresult+"' or "+nextcheckflag+" = '"+remresult+"'))" +
						")";   //����SQL���
			sql = sql + stemp;
		}
        				
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("optcheck_id");   
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
    	//
    	if(!stemp.equals("")){
    		srep = "1";
    	}
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
        
        String sql = "select useraccflag from biz_t_checkflow where policy_id = '" + pid + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("useraccflag");            	
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
     * ȡ���������̱�ʶ
     * @param pid
     * @param empid
     * @return
     */
    public String getPolicyFlowFlag(String pid,String empid) {
        String srep = "0";
        // ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
        //��������
        ConstantDefine constantdefine = new ConstantDefine();
        String dpath = tableinfoquery.getempdepth(empid);
        //
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + pid + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              if(dpath.equals(constantdefine.POLICYQUERYCODE_5)){
                srep = rs.getString("appstate1");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_4)){
                srep = rs.getString("appstate2");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_3)){
                srep = rs.getString("appstate3");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_2)){
                srep = rs.getString("appstate4");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_1)){
                srep = rs.getString("appstate5");
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
     * ȡ��ҵ���������
     * @param pid
     * @return
     */
    public String getPolicyAccTypeFlag(String pid) {
        String srep = "0";
        //
        String sql = "select acctype from doper_t_policy where policy_id = '" + pid + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("acctype");              
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
     * ���ܼ�ͥ������
     * @param pid
     * @param fmid
     * @return
     */
    public HashMap sumPolicyFamilyMoney(String pid,String fmid) {
    	HashMap hashmap = new HashMap();
    	//
    	String recheckmoney = "",countmoney = "";
        String sql = "select sum(recheckmoney) recheckmoney ,sum(countmoney) countmoney from biz_t_optcheck " +
        		"where policy_id = '" + pid + "' and family_id = '"+fmid+"'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	recheckmoney = rs.getString("recheckmoney");  
            	countmoney = rs.getString("countmoney"); 
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
        hashmap.put("recheckmoney", recheckmoney);
        hashmap.put("countmoney", countmoney);
        
    	return hashmap;
    }

}
