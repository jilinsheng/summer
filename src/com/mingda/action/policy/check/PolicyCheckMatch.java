package com.mingda.action.policy.check;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageCheckManage;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

/**
 * ����ҵ��
 * ������������
 * ���ϱ�׼����
 * @author xiu
 *
 */
public class PolicyCheckMatch {
	static Logger log = Logger.getLogger(PolicyCheckMatch.class);
	/**
	 * ����ҵ���׼
	 * ��ͥ���߳�Ա
	 * @param hashmap
	 * @return
	 */
	public String SetNewPolciyMatchMore(String pid,String empid) {
		log.debug(System.currentTimeMillis());
		String srep = "";
		String physql = "",deptid = "";
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ��������ѯ����
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//ҵ���������
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);		
		//
		//��һ����������
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//���ǵ�һ����������
			srep = "��¼��������ҵ���һ����������,�޷���������!";
			return srep;
		}
		//ҵ���Ƿ��Ѿ���������ʶ
        String isallaccflag = policymanagecheckmanage.getPolicyAllAccFlag(pid,empid);
        if(isallaccflag.equals("1")){//�Ѿ��������
        	srep = "ҵ���Ѿ��������,�޷������ⷢ���!";
    		return srep;
        }else if(isallaccflag.equals("2")){
        	srep = "ҵ��δ����,�޷������ⷢ���!";
    		return srep;
        }
		//�û�����
    	deptid = tableinfoquery.getempdepid(empid);
	    //
        String sql = "select standard_id,physql from doper_t_standard " +
						"where flag = '1' and policy_id = '"+pid+"' " +
							"order by planmoney desc";   //����SQL���
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
            	if(null != physql){
            		//
                	HashMap hashmap = new HashMap();
                	hashmap.put("pid", pid);
                	hashmap.put("acctype", acctype);
                	hashmap.put("physql", physql);
                	hashmap.put("deptid", deptid);
                	hashmap.put("empid", empid);
                	//
                	SetNewPolciyMatchMoreSql(hashmap);
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        log.debug(System.currentTimeMillis());
        //
    	//����������ͥ���Ա������      
        log.debug(System.currentTimeMillis());
        UpdatePolciyMatchMoreNew(pid,empid);
        log.debug(System.currentTimeMillis());
        //
        return srep;
    } 
	public String SetNewPolciyMatchMoreSql(HashMap hashmap) {
		String srep = "",sql = "";
		String fmid = "",memid = "",moneyflag = "";
		//
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //ҵ����������
	    Policycheckmanage policycheckmanage = new Policycheckmanage();
	    //
	    String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();
		String physql = hashmap.get("physql").toString();
		String deptid = hashmap.get("deptid").toString();
		String empid = hashmap.get("empid").toString();
    	
		//
		int ibeg=0,iend=0;
    	ibeg = physql.indexOf("from");//��һ�γ���λ��
    	iend = physql.indexOf("where");//��һ�γ���λ��
    	if(ibeg==-1 || iend == -1)
    	{
    		return srep;
    	}
    	String tselect = physql.substring(0, ibeg);
		String tfrom = physql.substring(ibeg+5, iend);
		String twhere = physql.substring(iend+5, physql.length());
		//log.debug("tselect:"+tselect+"tfrom:"+tfrom +"twhere:" + twhere);
		//
		boolean isdept = false; 
	    String [] asfrom = tfrom.split(",");
	    int itflen = asfrom.length;	    
	    for (int y=0;y<itflen;y++){
	    	String sfrom = asfrom[y];
	    	if("SYS_T_ORGANIZATION".equals(sfrom) || "sys_t_organization".equals(sfrom)){
	    		isdept = true;
	    	}
	    }
    	if(!isdept){
    		tfrom += ",SYS_T_ORGANIZATION ";
    		twhere += " and INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID";
    		twhere += " and SYS_T_ORGANIZATION.ORGANIZATION_ID like '"+deptid+"%'";
    	}
    	//������(��ͥ����)
    	String sqlfamily = "select a.family_id " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
    	//������(��Ա����)
    	String sqlmember = "select a.family_id,a.member_id " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
    	//
    	sql = tselect+" from "+tfrom +" where " + twhere;
    	if("0".equals(acctype)){
			//��ͥ����
        	sql += " minus " + sqlfamily;
		}else if("1".equals(acctype)){
			//��Ա����			
        	sql += " minus " + sqlmember;
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
            	//
            	
            	if("0".equals(acctype)){
        			//��ͥ����
            		fmid = rs.getString("family_id");            		
            		memid = tableinfoquery.getfamilymasterid(fmid);
            		String stopstatus = constantdefine.POLICYFLAGCODE_STOP;            		
        			String statu = tableinfoquery.getfamilystarus(fmid);
        			if(stopstatus.equals(statu)){
        				//��ͣ��
        				moneyflag = constantdefine.POLICYNEWCHECKCODE_STOP;
        			}else{
        				//��ͨ��
        				moneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
        			} 
        		}else if("1".equals(acctype)){
        			//��Ա����			
        			fmid = rs.getString("family_id");
            		memid = rs.getString("member_id");
            		moneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;   
        		}
            	
            	//����������
            	if(null == memid || "".equals(memid)){
            		//���ݴ���������
            	}else{             		
            		//�����¼            		
	    			HashMap hashmapinsert = new HashMap();
	    			hashmapinsert.put("pid", pid);
	    			hashmapinsert.put("acctype", acctype);
	    			hashmapinsert.put("moneyflag", moneyflag);
	    			hashmapinsert.put("fmid", fmid);
	    	    	hashmapinsert.put("memid", memid);
	    	    	hashmapinsert.put("empid", empid);
	    	    	policycheckmanage.insertPolicyCheck(hashmapinsert);	    	    	
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
		return srep;
	}
	/**
	 * ���µ�����ͥ���߼�ͥ��Ա������
	 * @param pid
	 * @param empid
	 * @param tfmid
	 * @return
	 */
	public String UpdatePolciyMatchOne(String pid,String empid,String tfmid) {
		String srep = "";  
		String moneyflag = "",checkid = "",fmid = "",memid = "",ifover = "";
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ��������ѯ����
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		
		//ҵ���������
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);	
		//��һ����������
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//���ǵ�һ����������
			return srep;
		}
		
		//
    	String sql = "select optcheck_id,moneyflag,family_id,member_id,ifover from biz_t_optcheck " +
    			"where policy_id = '"+pid+"' and  family_id = '"+tfmid+"' and ifover = '"+onedepth+"'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	checkid = rs.getString("optcheck_id");
            	moneyflag = rs.getString("moneyflag");
            	fmid = rs.getString("family_id");
            	memid = rs.getString("member_id");
            	ifover = rs.getString("ifover");
            	//���¼�¼
    			HashMap hashmapupdate = new HashMap();
    			hashmapupdate.put("checkid", checkid);
    			hashmapupdate.put("pid", pid);
    			hashmapupdate.put("acctype", acctype);
    			hashmapupdate.put("moneyflag", moneyflag);
    			hashmapupdate.put("fmid", fmid);
    			hashmapupdate.put("memid", memid);
    			hashmapupdate.put("ifover", ifover);
    			//�����������¼
    			UpdatePolciyMatch(hashmapupdate);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //        
    	return srep;
    }
	/**
	 * ��������������ͥ���߳�Ա������
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String UpdatePolciyMatchMoreNew(String pid,String empid) {
		String srep = "";  
		String checkid = "",moneyflag = "",fmid = "",memid = "",ifover = "";
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ��������ѯ����
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//ҵ���������
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);	
		//��һ����������
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);		
		if(!empdepth.equals(onedepth)){
			//���ǵ�һ����������
			return srep;
		}
		//
		//�û�����
    	String deptid = tableinfoquery.getempdepid(empid);
		//
    	String sql = "select a.optcheck_id,a.moneyflag,a.family_id,a.member_id,a.ifover " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and policy_id = '"+pid+"' " +
    					"and ifover = 'F' and b.organization_id like '"+deptid+"%'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	checkid = rs.getString("optcheck_id");
            	moneyflag = rs.getString("moneyflag");
            	fmid = rs.getString("family_id");
            	memid = rs.getString("member_id");
            	//��һ����������
            	ifover = onedepth;
            	//���¼�¼
    			HashMap hashmapupdate = new HashMap();
    			hashmapupdate.put("checkid", checkid);
    			hashmapupdate.put("pid", pid);
    			hashmapupdate.put("acctype", acctype);
    			hashmapupdate.put("moneyflag", moneyflag);
    			hashmapupdate.put("fmid", fmid);
    			hashmapupdate.put("memid", memid);
    			hashmapupdate.put("ifover", ifover);
    			//�����������¼
    			UpdatePolciyMatch(hashmapupdate);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //        
    	return srep;
    }
	/**
	 * �������¾�����
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String UpdatePolciyMatchMore(String pid,String empid) {
		String srep = "";  
		String checkid = "",moneyflag = "",fmid = "",memid = "",ifover = "";
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ��������ѯ����
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//ҵ���������
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);	
		//��һ����������
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//���ǵ�һ����������
			return srep;
		}
		//
		//�û�����
    	String deptid = tableinfoquery.getempdepid(empid);
		//
    	String sql = "select a.optcheck_id,a.moneyflag,a.family_id,a.member_id,a.ifover " +
    			"from biz_t_optcheck a,info_t_family b " +
    			"where a.family_id = b.family_id and policy_id = '"+pid+"' " +
    					"and ifover = '"+onedepth+"' and b.organization_id like '"+deptid+"%'";   //����SQL���
    	
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	checkid = rs.getString("optcheck_id");
            	moneyflag = rs.getString("moneyflag");
            	fmid = rs.getString("family_id");
            	memid = rs.getString("member_id");
            	ifover = rs.getString("ifover");
            	//���¼�¼
    			HashMap hashmapupdate = new HashMap();
    			hashmapupdate.put("checkid", checkid);
    			hashmapupdate.put("pid", pid);
    			hashmapupdate.put("acctype", acctype);
    			hashmapupdate.put("moneyflag", moneyflag);
    			hashmapupdate.put("fmid", fmid);
    			hashmapupdate.put("memid", memid);
    			hashmapupdate.put("ifover", ifover);
    			//�����������¼
    			UpdatePolciyMatch(hashmapupdate);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //        
    	return srep;
    }
	/**
	 * �жϼ�ͥ���߳�Ա�Ƿ����ҵ���׼
	 * @param hashmap
	 * @return
	 */
	public HashMap UpdatePolciyMatch(HashMap hashmap) {
		HashMap shashmap = new HashMap();
		String matchflag= "0";
		String standardid = "",physql = "",locsql = "",matchmoney = "0",accphysql = "",acclocsql = "";
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ����������
	    Policycheckmanage policycheckmanage = new Policycheckmanage();
	    //��������ҵ����ϱ�׼������
	    PolicyCheckMatchSql policycheckmatchsql = new PolicyCheckMatchSql();
	    //����ʩ��������
	    PolicyChildCheckMatch policychildcheckmatch = new PolicyChildCheckMatch();
		//
		String checkid = hashmap.get("checkid").toString();
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//ҵ���������
		String moneyflag = hashmap.get("moneyflag").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		String ifover = hashmap.get("ifover").toString();
        //
		//
        /********************
         *******�Ƿ���ϱ�׼**
         *******************/
		HashMap hashmapsql = new HashMap();
		hashmapsql.put("pid", pid);
		hashmapsql.put("acctype", acctype);
		hashmapsql.put("fmid", fmid);
		hashmapsql.put("memid", memid);
		//��ȡ��׼
		HashMap hashmapflag = new HashMap();
		hashmapflag = GetPolciyMatchOneFlag(hashmapsql);
		standardid = hashmapflag.get("standardid").toString();
		matchflag = hashmapflag.get("matchflag").toString();
		physql = hashmapflag.get("physql").toString();
		locsql = hashmapflag.get("locsql").toString();
        //
        /********************
         *******��������¼�¼**
         *******************/
        String newmoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
		if("0".equals(matchflag) && newmoneyflag.equals(moneyflag)){
    		//ɾ����¼
    		HashMap hashmapdelete = new HashMap();
    		hashmapdelete.put("checkid", checkid);
    		hashmapdelete.put("moneyflag", moneyflag);
    		policycheckmanage.deletePolicyCheck(hashmapdelete);
    		//ɾ����������ҵ���ͥ���߳�Ա��׼��
    		policycheckmatchsql.deletePolicyCheckSql(checkid);
    		//����ʩ��
    		HashMap hashmapchild = new HashMap();
    		hashmapchild.put("pid", pid);
    		hashmapchild.put("checkid", checkid);
    		hashmapchild.put("fmid", fmid);
    		hashmapchild.put("memid", memid);
        	policychildcheckmatch.deletePolicyChildCheck(hashmapchild);
        	//
    	}else{
    		/*******************
    		 *****�����׼������***
    		 ******************/
    		//
    		//��Ҫ����������ʶ
    		String accmode = policymanagecheckmanage.getPolicyUserAccFlag(pid);
    		//
    		if("0".equals(accmode)){
    			//���������
        		HashMap hashmapmoney = new HashMap();
    			hashmapmoney.put("pid", pid);
    			hashmapmoney.put("acctype", acctype);
    			hashmapmoney.put("fmid", fmid);
    			hashmapmoney.put("memid", memid);
    			//���������
        		HashMap hashmapmoneysql = new HashMap();
        		hashmapmoneysql = GetPolciyMatchAcc(hashmapmoney,standardid);
    			matchmoney = hashmapmoneysql.get("matchmoney").toString();
    			accphysql = hashmapmoneysql.get("accphysql").toString();
    			acclocsql = hashmapmoneysql.get("acclocsql").toString();
    			//û�о�������߾�����Ϊ0
    			if(null == matchmoney || "0".equals(matchmoney)){
    				//ɾ����¼
    	    		HashMap hashmapdelete = new HashMap();
    	    		hashmapdelete.put("checkid", checkid);
    	    		hashmapdelete.put("moneyflag", moneyflag);
    	    		policycheckmanage.deletePolicyCheck(hashmapdelete);
    	    		//ɾ����������ҵ���ͥ���߳�Ա��׼��
    	    		policycheckmatchsql.deletePolicyCheckSql(checkid);
    	    		//����ʩ��
    	    		HashMap hashmapchild = new HashMap();
    	    		hashmapchild.put("pid", pid);
    	    		hashmapchild.put("checkid", checkid);
    	    		hashmapchild.put("fmid", fmid);
    	    		hashmapchild.put("memid", memid);
    	        	policychildcheckmatch.deletePolicyChildCheck(hashmapchild);
    	    		//
    			}else{
    				//���¼�¼
    	        	HashMap hashmapupdate = new HashMap();
    	        	hashmapupdate.put("accmode", accmode);
    	        	hashmapupdate.put("checkid", checkid);
    	        	hashmapupdate.put("matchflag", matchflag);
    	        	hashmapupdate.put("moneyflag", moneyflag);
    	        	hashmapupdate.put("matchmoney", matchmoney);
    	        	hashmapupdate.put("ifover", ifover);
    	        	//���µ�����ͥ���߳�Ա������Ϣ
    	        	policycheckmanage.updatePolicyCheck(hashmapupdate);
    	        	//���µ�����ͥ���߳�Ա������׼״̬
    	        	policycheckmanage.updatePolicyMoneyFlag(checkid);
    	        	//������������ҵ���ͥ���߳�Ա��׼��
    	    		HashMap hashmapchecksql = new HashMap();
    	    		hashmapchecksql.put("checkid", checkid);
    	    		hashmapchecksql.put("physql", physql);
    	    		hashmapchecksql.put("locsql", locsql);
    	    		hashmapchecksql.put("accphysql", accphysql);
    	    		hashmapchecksql.put("acclocsql", acclocsql);
    	    		policycheckmatchsql.updatePolicyCheckSql(hashmapchecksql);
    	        	//����ʩ��
    	    		HashMap hashmapchild = new HashMap();
    	    		hashmapchild.put("pid", pid);
    	    		hashmapchild.put("checkid", checkid);
    	    		hashmapchild.put("fmid", fmid);
    	    		hashmapchild.put("memid", memid);
    	        	policychildcheckmatch.SetPolciyChildMatchOne(hashmapchild);
    			}    			
    		}else{
    			//���¼�¼
            	HashMap hashmapupdate = new HashMap();
            	hashmapupdate.put("accmode", accmode);
            	hashmapupdate.put("checkid", checkid);
            	hashmapupdate.put("matchflag", matchflag);
            	hashmapupdate.put("moneyflag", moneyflag);
            	hashmapupdate.put("matchmoney", matchmoney);
            	hashmapupdate.put("ifover", ifover);
            	//���µ�����ͥ���߳�Ա������Ϣ
	        	policycheckmanage.updatePolicyCheck(hashmapupdate);
	        	//���µ�����ͥ���߳�Ա������׼״̬
	        	policycheckmanage.updatePolicyMoneyFlag(checkid);
	        	//������������ҵ���ͥ���߳�Ա��׼��
	    		HashMap hashmapchecksql = new HashMap();
	    		hashmapchecksql.put("checkid", checkid);
	    		hashmapchecksql.put("physql", physql);
	    		hashmapchecksql.put("locsql", locsql);
	    		hashmapchecksql.put("accphysql", accphysql);
	    		hashmapchecksql.put("acclocsql", acclocsql);
	    		policycheckmatchsql.updatePolicyCheckSql(hashmapchecksql);
	        	//����ʩ��
	    		HashMap hashmapchild = new HashMap();
	    		hashmapchild.put("pid", pid);
	    		hashmapchild.put("checkid", checkid);
	    		hashmapchild.put("fmid", fmid);
	    		hashmapchild.put("memid", memid);
	        	policychildcheckmatch.SetPolciyChildMatchOne(hashmapchild);
    		} 
    		 
    	}    
		//
        return shashmap;
    } 
	/**
	 * ��ͥ�Ƿ���ϱ�׼����
	 * @param physql
	 * @param fmid
	 * @return
	 */
	public String GetPolciyMatchFamilyFlag(String physql,String fmid) {
		String srep = "0",sql = "";
		//
		sql = physql + " and info_t_family.family_id = '"+fmid+"'";
		//log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = "1";
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
	 * ��Ա�Ƿ���ϱ�׼����
	 * @param physql
	 * @param memid
	 * @return
	 */
	public String GetPolciyMatchMemberFlag(String physql,String memid) {
		String srep = "0",sql = "";
		//
		sql = physql + " and info_t_member.member_id = '"+memid+"'";
		//log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = "1";
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
	 * ����ɸѡ������׼
	 * ������ϱ�׼������
	 * @param hashmap
	 * @param standardid
	 * @return
	 */
	public HashMap GetPolciyMatchAcc(HashMap hashmap,String standardid){
		HashMap hashmapsql = new HashMap();
		String accphysql = "",acclocsql = "",matchmoney = "0";
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//ҵ���������
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		
		//��ͥ��������
		String deptid = tableinfoquery.getfamilydepid(fmid);
		//
		String sql = "select standarddept_id,accexpphysql,accexplocsql from doper_t_standarddept " +
						"where  flag = '1' and standard_id = '"+standardid+"' " +
						" and instr('#'||'"+deptid+"','#'||organization_id) >0 " +
						" order by organization_id desc";
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
				//ȡ�õ�һ���ͽ�������׼				
				accphysql = rs.getString("accexpphysql");
				acclocsql = rs.getString("accexplocsql");
				if(null == accphysql || "".equals(accphysql)){
					//û�л�������
				}else{
					//���������
					matchmoney = GetPolciyMatchMoneyOne(hashmap,accphysql);					
					break;
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
		hashmapsql.put("matchmoney", matchmoney);
		hashmapsql.put("accphysql", accphysql);
		hashmapsql.put("acclocsql", acclocsql);
		//
		return hashmapsql;
	}
	/**
	 * ��ȡ������ͥ���߳�Ա���ϱ�׼״̬
	 * @param checkid
	 * @return
	 */
	public String GetPolciyMatchMoneyFlag(String pip,String fmid){
		String sql = "",temp = "",moneyflag = "";
		String solemoney ="0",scountmoney = "0";
		double dolemoney =0,dcountmoney = 0;
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	//	
    	String sole = constantdefine.POLICYNEWCHECKCODE_OLE;
    	String stop = constantdefine.POLICYNEWCHECKCODE_TOP;
    	String sbom = constantdefine.POLICYNEWCHECKCODE_BOM;    	
    	//
		sql = "select sum(countmoney) as countmoney,sum(recheckmoney) as recheckmoney " +
				"from biz_t_optcheck " +
				"where policy_id = '"+pip+"' and family_id = '"+fmid+"'";
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
				//ȡ�õ�һ���ͽ�������׼	
				temp = rs.getString("countmoney");
				if(null != temp){
					scountmoney = temp;
				}	
				temp = rs.getString("recheckmoney");
				if(null != temp){
					solemoney = temp;
				}				
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		if(!"0".equals(solemoney)){			
			//���ת��
			dcountmoney = new Double(scountmoney);
			dolemoney = new Double(solemoney);	
			if(dcountmoney>dolemoney){
				moneyflag = stop;//����
			}else if(dcountmoney<dolemoney){
				moneyflag = sbom;//����			
			}else if(dcountmoney==dolemoney){
				moneyflag = sole;//˳��		
			}
		}
		return moneyflag;
	}
	/**
	 * ��ȡ��ǰ������¼�ļ�ͥID
	 * @param checkid
	 * @return
	 */
	public HashMap GetPolciyMatchMoneyFamily(String checkid){
		String sql = "",temp = "",pid = "",fmid = "";
		HashMap hashmap = new HashMap();
		//
		sql = "select policy_id,family_id from biz_t_optcheck where optcheck_id = '"+checkid+"'";
		//log.debug("sql:"+sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 				
				temp  = rs.getString("policy_id");
				if(null != temp){
					pid = temp;
				}
				temp  = rs.getString("family_id");
				if(null != temp){
					fmid = temp;
				}				
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		hashmap.put("pid", pid);
		hashmap.put("fmid", fmid);
		return hashmap;
	}
	/**
	 * ���������
	 * @param hashmap
	 * @param tsql
	 * @return
	 */
	public String GetPolciyMatchMoneyOne(HashMap hashmap,String tsql){
		String  srep = "0",sql = "";
		//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//ҵ���������
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//
		if("0".equals(acctype)){
			//��ͥ����
			sql = tsql + " where INFO_T_FAMILY.FAMILY_ID = '"+ fmid +"'";
		}else if("1".equals(acctype)){
			//��Ա����
			sql = tsql + " and INFO_T_MEMBER.MEMBER_ID = '"+ memid +"'";
		}
		//log.debug("sql:"+sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				//�������ͥ�ĺ����׼���[acccount]Ϊ���㹫ʽҳ�涨��
				String acccount  = rs.getString("acccount");
				if(null != acccount){
					BigDecimal bl = new BigDecimal(acccount);
					bl = bl.setScale(2, BigDecimal.ROUND_HALF_UP);				
					srep = bl.toString();
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
	 * ��ȡ��ͥ���߳�Ա�Ƿ����ҵ���׼
	 * @param hashmap
	 * @return
	 */
	public HashMap GetPolciyMatchOneFlag(HashMap hashmap){
		HashMap hashmapflag = new HashMap();
		String standardid = "",physql = "",locsql = "",matchflag = "0";
		//
        /********************
         *******�Ƿ���ϱ�׼**
         *******************/
		//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//ҵ���������
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//
        String sql = "select standard_id,physql,locsql from doper_t_standard " +
				"where flag = '1' and policy_id = '"+pid+"' " +
					"order by planmoney desc";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	standardid = rs.getString("standard_id");
            	physql = rs.getString("physql");
            	locsql = rs.getString("locsql");
            	if(null != physql){
            		if("0".equals(acctype)){
            			//��ͥ����        			
            			matchflag = GetPolciyMatchFamilyFlag(physql,fmid);
            			if("1".equals(matchflag)){
            				//���ϱ�׼
            				break;
            			}
            		}else if("1".equals(acctype)){
            			//��Ա����
            			matchflag = GetPolciyMatchMemberFlag(physql,memid);
            			if("1".equals(matchflag)){
            				//���ϱ�׼
            				break;
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
        //
        hashmapflag.put("standardid", standardid);
        hashmapflag.put("matchflag", matchflag);
        hashmapflag.put("physql", physql);
        hashmapflag.put("locsql", locsql);
        //
		return hashmapflag;
	}
}
