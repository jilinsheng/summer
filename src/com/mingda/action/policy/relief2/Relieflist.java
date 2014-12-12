package com.mingda.action.policy.relief2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;


public class Relieflist {
	static Logger log = Logger.getLogger(Relieflist.class);
	
	/**
	 * ����ҵ�������
	 * ��������(������)
	 * ��������(������)
	 * ɾ������(������)
	 * @param hashmap
	 * @return
	 */
	public StringBuffer updateListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//ҵ���ѯ
		PolicyQuery policyquery = new PolicyQuery();
		//
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jempid = hashmap.get("jempid").toString();
		String jdeptid = hashmap.get("jdeptid").toString();
        String jdepth = hashmap.get("jdepth").toString();
        String jpid = hashmap.get("jpid").toString();
        //
        //��Ҫ����������ʶ
		String accmode = policyquery.getPolicyUserAccFlag(jpid);
		//ҵ���������
		String acctype = policyaccquery.getPolicyAccTypeFlag(jpid);
		//��һ����������
		String onedepth = policyquery.getPolicyOneFlowFromId(jpid);
        //�������α�ʶ
        HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
        String optid = hashmapaccflag.get("optid").toString();
        String accflag = hashmapaccflag.get("accflag").toString();
        String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
        String accmoney = hashmapaccflag.get("accmoney").toString();
        //
        if("-1".equals(accflag)){							//�޽�������        	
        	shtml.append("��ҵ��δִ�д���,�ȴ�����������Ϣ!");
        }else if("0".equals(accflag)){						//�½�������        	
        	//����Ȩ�ޱ�ʶ
            HashMap hashmappower = new HashMap();        
            hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid);            
            //
    		String onecheck = hashmappower.get("onecheck").toString();
    		String usercheck = hashmappower.get("usercheck").toString();
    		//
    		if("1".equals(usercheck)){
    			//�û�����Ȩ��
    			if("1".equals(onecheck)){
        			//��һ��������
    				//===============================================//
    				HashMap hashmapacc = new HashMap();
    				hashmapacc.put("jdeptid", jdeptid);
    				hashmapacc.put("jempid", jempid);
    				hashmapacc.put("jpid", jpid);
    				hashmapacc.put("onedepth", onedepth);
    				hashmapacc.put("accmode", accmode);
    				hashmapacc.put("acctype", acctype);
    				hashmapacc.put("optid", optid);
    				hashmapacc.put("accflag", accflag);
    				hashmapacc.put("accmoneyflag", accmoneyflag);
    				hashmapacc.put("accmoney", accmoney);
    				//���λ���ID
    				String sdeptid = getStandardDeptID(jpid,jdeptid);
    				if("".equals(sdeptid)){
    					shtml.append("��ҵ��û�����ñ�׼!");
    					return shtml;
    				}
    				hashmapacc.put("sdeptid", sdeptid);
    				//==============================================//    				    				
    				//����������������
    				delListMonery(hashmapacc);
    				//���²�����������
    				upoldListMonery(hashmapacc);
    				//����˳�Ӿ�������
    				deloldListMonery(hashmapacc);
    				//���¾�������
    				upListMonery(hashmapacc);    				    				
    				//���Ӿ�������
    				addListMonery(hashmapacc);
    				//���¼�ͥ��Ϣ�����ʶ
    				updateFamilyIsChang(hashmapacc);
    				//
    				//shtml.append("���¸�ҵ���������,������������!");
        		}
    		}else{
    			shtml.append("���û�û�д�ҵ������Ȩ��,�޷���������!");
    		}    		
        }else if("1".equals(accflag)){						//���ڽ�������
        	
        	shtml.append("��ҵ������ִ�н��㴦��,��ȴ�!");
        }else if("2".equals(accflag)){						//�����������
        	
        	shtml.append("��ҵ��������,�ȴ������µĽ�����Ϣ!");
        }
		return shtml;
	}
	/**
	 * ����������������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer delListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String matchsql = "",newsql = "",newphysql = "",newaccphysql = "";
        //
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		//���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//��ȡ�õ��θû�����������SQL���		
		newphysql = getStandardAllSql(jpid,jdeptid,sdeptid);
		//
    	//������(��ͥ����)
    	String sqlfamily = "select a.family_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag = '1' "
    		+ "and b.familyno like '"+jdeptid+"%'";
    	//������(��Ա����)
    	String sqlmember = "select a.family_id,a.member_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag = '1' "
    		+ "and b.b.familynolike '"+jdeptid+"%'";
    	//
    	//
    	//ɾ����������SQL
    	if("0".equals(acctype)){						//��ͥ����
    		//ɾ��������SQL���           		
			//��ͥ����	            	    
    		newsql = "delete biz_t_optcheck opt where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id)";
		}else{										   //��Ա����
			//ɾ��������SQL���           		
			//��Ա����	            	    
    		newsql = "delete biz_t_optcheck opt where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id  and opt.member_id = stan.member_id)";
		}            	
    	//log.debug("newsql:"+newsql);
    	//ִ��
    	PublicComm pubilccomm = new PublicComm();
    	pubilccomm.ExeSQLFromUserSQL(newsql);
        return shtml;
	}
	/**
	 * ����˳�Ӿ�������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer deloldListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String matchsql = "",newsql = "",newphysql = "",newaccphysql = "";
        //
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		//���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//��ȡ�õ��θû�����������SQL���		
		newphysql = getStandardAllSql(jpid,jdeptid,sdeptid);
		//
		//
    	//������(��ͥ����)
    	String sqlfamily = "select a.family_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag in (2,3,4) "
    		+ "and b.familyno like '"+jdeptid+"%'";
    	//������(��Ա����)
    	String sqlmember = "select a.family_id,a.member_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag in (2,3,4) "
    		+ "and b.familyno like '"+jdeptid+"%'";
    	//
    	//================================================�����=============================================================
    	//������������SQL
    	if("0".equals(acctype)){						//��ͥ����
    		//����������SQL���          		
			//��ͥ����	            	    
    		newsql = "update biz_t_optcheck opt set moneyflag = '7',opt.checkmoney = '0',opt.sqlmoney = '0',physql = '',locsql = '',accphysql = '',acclocsql = '' " 
    			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id)";
		}else{										   //��Ա����
			//����������SQL���      		
			//��Ա����	            	    
    		newsql = "update biz_t_optcheck opt set moneyflag = '7',opt.checkmoney = '0',opt.sqlmoney = '0',physql = '',locsql = '',accphysql = '',acclocsql = '' " 
    			+ "where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id  and opt.member_id = stan.member_id)";
		} 
    	//================================================�����=============================================================
    	//log.debug("newsql:"+newsql);
    	//ִ��
    	PublicComm pubilccomm = new PublicComm();
    	pubilccomm.ExeSQLFromUserSQL(newsql);
        return shtml;
	}
	/**
	 * ����˳�Ӳ�����������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer upoldListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String matchsql = "",newsql = "",newphysql = "",newaccphysql = "";
        //
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		//���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//
		
        String sql = "select standard_id, planmoney, physql, locsql " 
        	+ "from doper_t_standard "
        	+ "where flag = '1' "
        	+ "and physql is not null "
        	+ "and policy_id = '"+jpid+"'  "
        	+ "and organization_id = '"+sdeptid+"' "
        	+ "order by planmoney asc ";
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	String standardid = rs.getString("standard_id");
            	String planmoney = rs.getString("planmoney");
            	String physql = rs.getString("physql");
            	String locsql = rs.getString("locsql");
            	//
            	matchsql = "";
            	newsql = "";
            	newphysql = "";
            	newaccphysql = "";
            	//
            	//������(��ͥ����)
            	String sqlfamily = "select a.family_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and a.moneyflag = '7' "
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//������(��Ա����)
            	String sqlmember = "select a.family_id,a.member_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and a.moneyflag  = '7' "
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//
            	//================================================�����ת���=============================================================
            	//
            	if("1".equals(accmoneyflag)){				//�û����������===========================================
            		//������������SQL
                	if("0".equals(acctype)){						//��ͥ����
                		//����������SQL���
                		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";  
                		//������ת��ȥ�����ҿո�
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
        				//��ͥ����	            	    
                		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
                			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
                			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
                			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
        			}else{										   //��Ա����
        				//����������SQL���
                		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
                		//������ת��ȥ�����ҿո�
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
        				//��Ա����	            	    
                		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
                			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
                			+ "where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
                			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
        			} 
            	}else{										//�û������������===========================================
            		HashMap hashmapacc = getDeptAccPHYSQL(standardid,jdeptid);
                	String accphysql = hashmapacc.get("accphysql").toString();
                	String acclocsql = hashmapacc.get("acclocsql").toString();
                	if(!"".equals(accphysql)){	//�޻���������
	            		//ɾ����������SQL
		            	if("0".equals(acctype)){						//��ͥ����
		            		//ɾ��������SQL���
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
		            		//������ת��ȥ�����ҿո�
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//��ͥ����
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id ";
		            		//������ת��ȥ�����ҿո�
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		            		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+") " 
		            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
		            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
		    			}else{										   //��Ա����
		    				//ɾ��������SQL���
		            		newphysql = physql+ " and info_t_family.familyno like '"+jdeptid+"%'"; 
		            		//������ת��ȥ�����ҿո�
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//��Ա����	   
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id and memberid = opt.member_id ";
		            		//������ת��ȥ�����ҿո�
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		            		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+") " 
		            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
		            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
		    			}
                	}
            	}
            	
            	//================================================�����ת���=============================================================
            	//log.debug("newsql:"+newsql);
            	//ִ��
            	PublicComm pubilccomm = new PublicComm();
            	pubilccomm.ExeSQLFromUserSQL(newsql);
            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return shtml;
	}
	/**
	 * ���¾�������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer upListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String matchsql = "",newsql = "",newphysql = "",newaccphysql = "";
        //
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		//���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//
		
		String sql = "select standard_id, planmoney, physql, locsql " 
        	+ "from doper_t_standard "
        	+ "where flag = '1' "
        	+ "and physql is not null "
        	+ "and policy_id = '"+jpid+"' "
        	+ "and organization_id = '"+sdeptid+"' "
        	+ "order by planmoney asc ";
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	String standardid = rs.getString("standard_id");
            	String planmoney = rs.getString("planmoney");
            	String physql = rs.getString("physql");
            	String locsql = rs.getString("locsql");
            	//
            	matchsql = "";
            	newsql = "";
            	newphysql = "";
            	newaccphysql = "";
            	//
            	//������(��ͥ����)
            	String sqlfamily = "select a.family_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//������(��Ա����)
            	String sqlmember = "select a.family_id,a.member_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//
            	//
            	if("1".equals(accmoneyflag)){				//�û����������===========================================
	            	//ɾ����������SQL
	            	if("0".equals(acctype)){						//��ͥ����
	            		//ɾ��������SQL���
	            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
	            		//������ת��ȥ�����ҿո�
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	    				//��ͥ����	            	    
	            		newsql = "update biz_t_optcheck opt set opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
	            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
	            			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
	            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
	    			}else{										   //��Ա����
	    				//ɾ��������SQL���
	            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
	            		//������ת��ȥ�����ҿո�
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	    				//��Ա����	            	    
	            		newsql = "update biz_t_optcheck opt set opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
	            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
	            			+ "where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
	            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
	    			} 
            	}else{										//�û������������===========================================
            		HashMap hashmapacc = getDeptAccPHYSQL(standardid,jdeptid);
                	String accphysql = hashmapacc.get("accphysql").toString();
                	String acclocsql = hashmapacc.get("acclocsql").toString();
                	if(!"".equals(accphysql)){	//�޻���������
	            		//ɾ����������SQL
		            	if("0".equals(acctype)){						//��ͥ����
		            		//ɾ��������SQL���
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
		            		//������ת��ȥ�����ҿո�
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//��ͥ����
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id ";
		            		//������ת��ȥ�����ҿո�
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
			        		//
			        		newsql = "update biz_t_optcheck opt set  ";
//			        		if("0".equals(accmode)){				//�û��������޸ľ�����
//			        			newsql += "opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+"), ";
//			        		}
			        		newsql += "opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+"), ";
		            		newsql += "physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            				+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
		            				+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
		    			}else{										   //��Ա����
		    				//ɾ��������SQL���
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
		            		//������ת��ȥ�����ҿո�
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//��Ա����	   
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id and memberid = opt.member_id ";
		            		//������ת��ȥ�����ҿո�
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
			        		//
			        		newsql = "update biz_t_optcheck opt set  ";
//			        		if("0".equals(accmode)){				//�û��������޸ľ�����
//			        			newsql += "opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+"), ";
//			        		}
			        		newsql += "opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+"), ";
			        		newsql += "physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            				+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
		            				+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
		    			}
                	}
            	}
            	//log.debug("newsql:"+newsql);
            	//ִ��
            	PublicComm pubilccomm = new PublicComm();
            	pubilccomm.ExeSQLFromUserSQL(newsql);
            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return shtml;
	}
	/**
	 * ������������	
	 * @param hashmap
	 * @return
	 */
	public StringBuffer addListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String matchsql = "",newsql = "",newphysql = "",newaccphysql = "";
        //
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		//���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		
		//
		
        String sql = "select standard_id, planmoney, physql, locsql " 
        	+ "from doper_t_standard "
        	+ "where flag = '1' "
        	+ "and physql is not null "
        	+ "and policy_id = '"+jpid+"' "
        	+ "and organization_id = '"+sdeptid+"' "
        	+ "order by planmoney desc ";
        
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
            	String standardid = rs.getString("standard_id");
            	String planmoney = rs.getString("planmoney");
            	String physql = rs.getString("physql");
            	String locsql = rs.getString("locsql");
            	//
            	matchsql = "";
            	newsql = "";
            	newphysql = "";
            	newaccphysql = "";
            	//
            	//������(��ͥ����)
            	String sqlfamily = "select a.family_id " +
            			"from biz_t_optcheck a,info_t_family b " +
            			"where a.family_id = b.family_id and a.policy_id = '"+jpid+"' and b.organization_id like '"+jdeptid+"%'";
            	//������(��Ա����)
            	String sqlmember = "select a.family_id,a.member_id " +
            			"from biz_t_optcheck a,info_t_family b " +
            			"where a.family_id = b.family_id and a.policy_id = '"+jpid+"' and b.organization_id like '"+jdeptid+"%'";
            	//
            	//
            	if("1".equals(accmoneyflag)){				//�û����������===========================================
            		//��������������SQL
	            	if("0".equals(acctype)){
	            		//����������SQL���
	            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
	            		//������ת��ȥ�����ҿո�
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	            	    matchsql = "select c.family_id,f.masterid,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1','"+accmoney+"','"+onedepth+"','"+physql+"','"+locsql+"','"+accmoney+"','"+jempid+"',sysdate " 
	            	    		+ " from (" + newphysql + ") c,info_t_family f ";
	            	    matchsql += " where not exists (" + sqlfamily + " and b.family_id = c.family_id and b.family_id = f.family_id) " 
	            	    		+ "and c.family_id = f.family_id";
	    				//��ͥ����	            	    
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) " + matchsql ;
	    			}else{
	    				//����������SQL���
	    				newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
	    				//������ת��ȥ�����ҿո�
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	    				matchsql = "select c.family_id,c.member_id,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1','"+accmoney+"','"+onedepth+"','"+physql+"','"+locsql+"','"+accmoney+"','"+jempid+"',sysdate " 
	    						+ " from (" + newphysql + ") c ";
	            	    matchsql += " where not exists (" + sqlmember + " and b.family_id = c.family_id ) ";
	    				//��Ա����	            	    
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) " + matchsql ;	    				
	    			}	            	
            	}else{										//�û������������==========================================
            		HashMap hashmapacc = getDeptAccPHYSQL(standardid,jdeptid);
                	String accphysql = hashmapacc.get("accphysql").toString();
                	String acclocsql = hashmapacc.get("acclocsql").toString();
                	//��������������SQL
                	if(!"".equals(accphysql)){	//�޻���������	
		            	if("0".equals(acctype)){			//��ͥ����	
		            		//����������SQL���
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
		            		//������ת��ȥ�����ҿո�
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		            		            			
	            			newaccphysql = accphysql;
		            		//������ת��ȥ�����ҿո�
			        		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		            	    matchsql = "select c.family_id,f.masterid,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1',d.acccount,'"+onedepth+"','"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"',d.acccount,'"+jempid+"',sysdate " 
		            	    		+ " from (" + newphysql + ") c,(" + newaccphysql + ") d,info_t_family f ";
		            	    matchsql += " where not exists (" + sqlfamily + " and b.family_id = c.family_id and b.family_id = d.familyid and b.family_id = f.family_id ) " 
			            	    	+ "and c.family_id = d.familyid and c.family_id = f.family_id and d.familyid = f.family_id";
		            	    //��ͥ����	            	    
		            		newsql = "insert into biz_t_optcheck " +
		            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,accphysql,acclocsql,sqlmoney,optoper,optdt) " + matchsql ;
		            			            		
		    			}else{								//��Ա����
		    				//����������SQL���
		    				newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
		    				//������ת��ȥ�����ҿո�
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
	            			newaccphysql = accphysql;
		            		//������ת��ȥ�����ҿո�
			        		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		    				matchsql = "select c.family_id,c.member_id,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1',d.acccount,'"+onedepth+"','"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"',d.acccount,'"+jempid+"',sysdate " 
		    							+ " from (" + newphysql + ") c,(" + newaccphysql + ") d ";
		            	    matchsql += " where not exists (" + sqlmember + " and b.family_id = c.family_id and b.family_id = d.familyid ) " 
		            	    		+ "and c.family_id = d.familyid ";
		            	    //��Ա����	            	    
		            		newsql = "insert into biz_t_optcheck " +
		            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,accphysql,acclocsql,sqlmoney,optoper,optdt) " + matchsql ;
		    			}	    				
	    			}	            	
            	}
            	//log.debug("matchsql:"+matchsql);
            	//log.debug("newsql:"+newsql);
            	//ִ��
            	PublicComm pubilccomm = new PublicComm();
            	pubilccomm.ExeSQLFromUserSQL(newsql);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return shtml;
	}
	/**
	 * ���¼�ͥ�����ʶ
	 * @param jdeptid
	 * @return
	 */
	public String updateFamilyIsChang(HashMap hashmap) {
		String srep = "";
        //
		//
		String jdeptid = hashmap.get("jdeptid").toString();
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String onedepth = hashmap.get("onedepth").toString();
		//
		String accmode = hashmap.get("accmode").toString();
		String acctype = hashmap.get("acctype").toString();
		//
		String optid = hashmap.get("optid").toString();
		String accflag = hashmap.get("accflag").toString();
		String accmoneyflag = hashmap.get("accmoneyflag").toString();
		String accmoney = hashmap.get("accmoney").toString();
		//���λ���ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//
		//������(��ͥ����)
    	String sqlfamily = "select a.family_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and b.organization_id like '"+jdeptid+"%'";
    	//
        String sql = "update info_t_family fm set fm.ischange = '0' "
        	+ "where fm.ischange = '1' and exists ("+sqlfamily+" and fm.family_id = a.family_id )";
        //ִ��
    	PublicComm pubilccomm = new PublicComm();
    	pubilccomm.ExeSQLFromUserSQL(sql);
        //log.debug(sql);
        return srep;
	}
	/**
	 * ��ȡ��׼�ĵ�ǰ�����ĺ��㹫ʽ��Ϣ
	 * @param standardid
	 * @param deptid
	 * @return
	 */
	public HashMap getDeptAccPHYSQL(String standardid,String deptid) {
		HashMap hashmap = new HashMap();
		int irow = 0;
		String sql = "select accexpphysql, accexplocsql "
		  + "from doper_t_standarddept "
		  + "where flag = '1' and accexpphysql is not null " 
		  + "and standard_id = '"+standardid+"' " 
		  + "and instr('#' || '"+deptid+"', '#' || organization_id) > 0 ";
		//log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {	
            	hashmap.put("accphysql", rs.getString("accexpphysql"));
            	hashmap.put("acclocsql", rs.getString("accexplocsql"));
            	irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        if(irow == 0){
        	hashmap.put("accphysql", "");
        	hashmap.put("acclocsql", "");
        }
		return hashmap;
	}
	/**
	 * ��ȡ���λ���ID
	 * @param jpid
	 * @param jdeptid
	 * @return
	 */
	public String getStandardDeptID(String jpid,String jdeptid) {
		String sdeptid = "";
        //
		
        String sql = "select standard_id,organization_id " 
        	+ "from doper_t_standard "
        	+ "where flag = '1' "
        	+ "and physql is not null "
        	+ "and policy_id = '"+jpid+"' "
        	+ "and instr('#' || '"+jdeptid+"', '#' || organization_id) > 0 ";
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	sdeptid = rs.getString("organization_id");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return sdeptid;
	}
	/**
	 * ��ȡ��ǰҵ�����Ըû������е��α�׼����SQL���
	 * @param jpid
	 * @param jdeptid
	 * @param sdeptid
	 * @return
	 */
	public String getStandardAllSql(String jpid,String jdeptid,String sdeptid) {
		String srep = "",tmp = "";
        //
		
        String sql = "select standard_id,physql,locsql " 
        	+ "from doper_t_standard "
        	+ "where flag = '1' "
        	+ "and physql is not null "
        	+ "and policy_id = '"+jpid+"' "
        	+ "and organization_id = '"+sdeptid+"' ";
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	tmp= rs.getString("physql");
            	if("".equals(srep)){
            		srep = tmp + " and info_t_family.organization_id like '"+jdeptid+"%'";
            	}else{
            		srep += " union " + tmp + " and info_t_family.organization_id like '"+jdeptid+"%'";
            	}            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //log.debug(srep);
        return srep;
	}
}
