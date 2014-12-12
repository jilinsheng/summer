package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageChild;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

public class Policycheckmanage {
	static Logger log = Logger.getLogger(Policycheckmanage.class);
    /**
     * ����������
     * @param hashmap
     * @return
     */
    public String insertPolicyCheck(HashMap hashmap) {
    	String srep = "";
    	//
		String pid = hashmap.get("pid").toString();
		String acctype = hashmap.get("acctype").toString();//ҵ���������
		String moneyflag = hashmap.get("moneyflag").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		String empid = hashmap.get("empid").toString();
		//
		
		String sql = "insert into biz_t_optcheck " +
				"(optcheck_id,policy_id,family_id,member_id,acctype,moneyflag,optoper,optdt)" +
				" values " +
				"(xoptcheck_id.nextval,'"+pid+"','"+fmid+"','"+memid+"','"+acctype+"','"+moneyflag+"','"+empid+"',sysdate)";
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
  	  		srep = "��������������ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "�������������ʧ��!";
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
     * ���������������
     * @param hashmap
     * @return
     */
    public String updatePolicyCheck(HashMap hashmap) {
    	String srep = "",sql = "";
    	//
    	String accmode = hashmap.get("accmode").toString();
		String checkid = hashmap.get("checkid").toString();
		String matchflag = hashmap.get("matchflag").toString();
		String moneyflag = hashmap.get("moneyflag").toString();
		String matchmoney = hashmap.get("matchmoney").toString();
		String ifover = hashmap.get("ifover").toString();
		//
		//�Ƿ���������
		if("0".equals(accmode)){
			sql = "update biz_t_optcheck " +
				"set " +
					"moneyaout = '"+matchflag+"', " +
					"moneyflag = '"+moneyflag+"', " +
					"checkmoney = '"+matchmoney+"', " +
					"ifover = '"+ifover+"' " +
				"where optcheck_id = '"+checkid+"'";
		}else{
			sql = "update biz_t_optcheck " +
				"set " +
					"moneyaout = '"+matchflag+"', " +
					"moneyflag = '"+moneyflag+"', " +
					"ifover = '"+ifover+"' " +
				"where optcheck_id = '"+checkid+"'";
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
  	  		srep = "��������������ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "�������������ʧ��!";
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
     * ���µ�����ͥ���߳�Ա��׼״̬
     * @param checkid
     * @return
     */
    public String updatePolicyMoneyFlag(String checkid) {
    	String srep = "",sql = "",moneyflag = "";
    	String pid = "",fmid = "";
    	//����ҵ���׼������
    	PolicyCheckMatch policycheckmatch = new PolicyCheckMatch();
    	//
    	HashMap hashmap = new HashMap();
    	hashmap = policycheckmatch.GetPolciyMatchMoneyFamily(checkid);
    	pid = hashmap.get("pid").toString();
    	fmid = hashmap.get("fmid").toString();    	
    	//�����ͥ������׼״̬(˳�ӡ����ߡ�����)
    	moneyflag = policycheckmatch.GetPolciyMatchMoneyFlag(pid,fmid);
    	//
    	if(null == moneyflag || "".equals(moneyflag)){
    		return srep;
    	}
    	//
    	sql = "update biz_t_optcheck set moneyflag = '"+moneyflag+"' " +
    			"where policy_id = '"+pid+"' and family_id = '"+fmid+"'";
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
  	  		srep = "��������������ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "�������������ʧ��!";
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
     * ɾ�������ϱ�׼��ͥ���߳�Ա
     * ˳�ӵļ�ͥ���߳�Ա����Ϊ�����(�����ϱ�׼)
     * @param hashmap
     * @return
     */
    public String deletePolicyCheck(HashMap hashmap) {
    	String srep = "",sql = "";
    	//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	//
		String checkid = hashmap.get("checkid").toString();
		String moneyflag = hashmap.get("moneyflag").toString();
		//
		String moneynewflag = constantdefine.POLICYNEWCHECKCODE_NEW;		
		//
		if(moneynewflag.equals(moneyflag)){
			//ֱ��ɾ��(�����������)
			//
			String sql1  = "delete biz_t_optchecktype t " +
					"where exists (select optchecktype_id from biz_t_optchecktype a,biz_t_optcheckidea b,biz_t_optcheck c " +
						"where a.optcheckidea_id = b.optcheckidea_id " +
							"and b.optcheck_id = c.optcheck_id " +
								"and c.optcheck_id = '"+checkid+"' " +
									"and t.optchecktype_id = a.optchecktype_id)";  //SQL
	        //log.debug(sql1);
			String sql2  = "delete biz_t_optcheckidea t " +
					"where exists (select optcheckidea_id from biz_t_optcheckidea a,biz_t_optcheck b " +
						"where a.optcheck_id = b.optcheck_id " +
							"and b.optcheck_id = '"+checkid+"' " +
								"and t.optcheckidea_id = a.optcheckidea_id )";  //SQL
			//log.debug(sql2);
			String sql3  = "delete biz_t_optcheck " +
					"where optcheck_id = '"+checkid+"'";  //SQL
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
	  	  		conn.commit();                      //�ر�
	  	  		//
	  	  		srep = "��������������ɹ�!";
	          
	  	  	} catch (SQLException e) {
	  	  		try {
	  	  			srep = "�������������ʧ��!";
	  	  			conn.rollback();
	  	  		} catch (SQLException e1) {          
	  	  			e1.printStackTrace();
	  	  		}
	  	  	} finally {
	  	  		DBUtils.close(pstmt);               //�ر�PreparedStatement
	  	  		//DBUtils.close(conn);                //�ر�����
	  	  	}
		}else{
			//����Ϊ�����
			String matchflag = constantdefine.POLICYAOUTCHECKCODE_NOMATCH;
			sql = "update biz_t_optcheck " +
					"set moneyaout = '"+matchflag+"' " +
					"where optcheck_id = '"+checkid+"'";

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
	  	  		srep = "��������������ɹ�!";
	          
	  	  	} catch (SQLException e) {
	  	  		try {
	  	  			srep = "�������������ʧ��!";
	  	  			conn.rollback();
	  	  		} catch (SQLException e1) {          
	  	  			e1.printStackTrace();
	  	  		}
	  	  	} finally {
	  	  		DBUtils.close(pstmt);               //�ر�PreparedStatement
	  	  		//DBUtils.close(conn);                //�ر�����
	  	  	}
		}
		
    	return srep;
    }
    
    /**
     * ����������ҵ����������ͽ��
     * @param hashmap
     * @return
     */
    public String setMoreCheckIdea(HashMap hashmap) {
    	String srep = "";
    	String empdepth = "",nextdepth = "",backdepth = "",onedepth = "",enddepth = "";
    	String checkid = "",checkmoney = "0",checkchildid = "",checkchildmoney = "0";
    	// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//ҵ��������ѯ������
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
		String pid = hashmap.get("pid").toString();//ҵ��ID
		String allaccflag = hashmap.get("allaccflag").toString();//��������ʶ
		String preportflag = hashmap.get("preportflag").toString();//��λȷ��������ʶ
		String empid = hashmap.get("empid").toString();//�û�ID
		//
		String reqtype = hashmap.get("reqtype").toString();//����ԭ��ID(11,12,13)
		String reptype = hashmap.get("reptype").toString();//�������ID		
		String repman = hashmap.get("repman").toString();//����������(����1,����2)
		String repidea = hashmap.get("repidea").toString();//�������
		String repdt = hashmap.get("repdt").toString();//����ʱ��
		String repbegdt = hashmap.get("repbegdt").toString();//��ʼ����ʱ��
		String rependdt = hashmap.get("rependdt").toString();//��������ʱ��
		//
		String idsmoneys = hashmap.get("idsmoneys").toString();//������ID�;�����(2222,122.50;2223,210.23)
		//����ʩ������
		String idschildmoneys = hashmap.get("idschildmoneys").toString();//����ʩ��������ID�ͷ���ʩ��������(2222,122.50;2223,210.23)
		//
		if(allaccflag.equals("1")){//�Ѿ��������
        	srep = "ҵ���Ѿ��������,�޷������ⷢ���!";
    		return srep;
        }else if(allaccflag.equals("2")){
        	srep = "ҵ��δ����,�޷������ⷢ���!";
    		return srep;
        }
		//
		//������������
	    empdepth = tableinfoquery.getempdepth(empid);
	    nextdepth = policymanagecheckquery.getPolicyNextFlowFromId(pid,empid);
	    backdepth = policymanagecheckquery.getPolicyBackFlowFromId(pid,empid);
	    onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
	    enddepth = policymanagecheckquery.getPolicyEndFlowFromId(pid);
		//������������	    
	    String [] aidsmoneys = idsmoneys.split(";");
	    for(int i=0;i<aidsmoneys.length;i++){
	    	String idmoney = aidsmoneys[i];
	    	String [] aidmoney = idmoney.split(",");	    	
	    	checkid = aidmoney[0];
	    	checkmoney = aidmoney[1];
	    	//
	    	//��������
			HashMap hashmapone = new HashMap();
			hashmapone.put("empid", empid);
			hashmapone.put("empdepth", empdepth);
			hashmapone.put("nextdepth", nextdepth);
			hashmapone.put("backdepth", backdepth);
			hashmapone.put("onedepth", onedepth);
			hashmapone.put("enddepth", enddepth);
			//
			hashmapone.put("preportflag", preportflag);
			//
			hashmapone.put("reqtype", reqtype);
			hashmapone.put("reptype", reptype);
			hashmapone.put("repman", repman);
			hashmapone.put("repidea", repidea);
			hashmapone.put("repdt", repdt);
			hashmapone.put("repbegdt", repbegdt);
			hashmapone.put("rependdt", rependdt);
			//
			hashmapone.put("checkid", checkid);
			hashmapone.put("checkmoney", checkmoney);
			//������������ͽ��
			srep = setOneCheckIdea(hashmapone);
			//
    		//���µ�����ͥ���߳�Ա������׼״̬
        	updatePolicyMoneyFlag(checkid);
	    }
	    boolean bolchild = false ;
	    PolicyManageChild policyManageChild =new PolicyManageChild();
	    bolchild = policyManageChild. existsPolicyChildFromId(pid);
	    if (bolchild){
	    	//����ʩ������	    
		    String [] achildidsmoneys = idschildmoneys.split(";");
		    for(int i=0;i<achildidsmoneys.length;i++){
		    	String childidmoney = achildidsmoneys[i];
		    	String [] achildidmoney = childidmoney.split(",");	    	
		    	checkchildid = achildidmoney[0];
		    	checkchildmoney = achildidmoney[1];
			    //��������
				HashMap hashmapchildone = new HashMap();
				hashmapchildone.put("empid", empid);
				hashmapchildone.put("empdepth", empdepth);
				hashmapchildone.put("repdt", repdt);
				hashmapchildone.put("checkchildid", checkchildid);
				hashmapchildone.put("checkchildmoney", checkchildmoney);
				//������������ʩ��
			    setOneCheckChildIdea(hashmapchildone);
		    }
	    }	    
	    //
    	return srep;
    }
    /**
     * ȫ��(sql���)ҵ����������ͽ��
     * @param hashmap
     * @return
     */
    public String setAllCheckIdeaFamily(HashMap hashmap) {    	
		String srep = "";
    	String empdepth = "",nextdepth = "",backdepth = "",onedepth = "",enddepth = "";
    	String fmid = "";
    	// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//ҵ��������ѯ������
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
		String pid = hashmap.get("pid").toString();//ҵ��ID
		String allaccflag = hashmap.get("allaccflag").toString();//��������ʶ
		String preportflag = hashmap.get("preportflag").toString();//��λȷ��������ʶ
		String empid = hashmap.get("empid").toString();//�û�ID
		//
		String reqtype = hashmap.get("reqtype").toString();//����ԭ��ID(11,12,13)
		String reptype = hashmap.get("reptype").toString();//�������ID		
		String repman = hashmap.get("repman").toString();//����������(����1,����2)
		String repidea = hashmap.get("repidea").toString();//�������
		String repdt = hashmap.get("repdt").toString();//����ʱ��
		String repbegdt = hashmap.get("repbegdt").toString();//��ʼ����ʱ��
		String rependdt = hashmap.get("rependdt").toString();//��������ʱ��
		//
		String selsql = hashmap.get("selsql").toString();//ȫ�����SQL
		//		
		//
		if(allaccflag.equals("1")){//�Ѿ��������
        	srep = "ҵ���Ѿ��������,�޷������ⷢ���!";
    		return srep;
        }else if(allaccflag.equals("2")){
        	srep = "ҵ��δ����,�޷������ⷢ���!";
    		return srep;
        }
		//
		//������������
	    empdepth = tableinfoquery.getempdepth(empid);
	    nextdepth = policymanagecheckquery.getPolicyNextFlowFromId(pid,empid);
	    backdepth = policymanagecheckquery.getPolicyBackFlowFromId(pid,empid);
	    onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
	    enddepth = policymanagecheckquery.getPolicyEndFlowFromId(pid);
		//
	    String sql = selsql;
	    //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	fmid = rs.getString("FAMILY_ID");
            	//
            	//��������
        		HashMap hashmapone = new HashMap();
        		hashmapone.put("pid", pid);
        		hashmapone.put("empid", empid);
        		hashmapone.put("empdepth", empdepth);
        		hashmapone.put("nextdepth", nextdepth);
        		hashmapone.put("backdepth", backdepth);
        		hashmapone.put("onedepth", onedepth);
        		hashmapone.put("enddepth", enddepth);
        		//
        		hashmapone.put("preportflag", preportflag);
        		//
        		hashmapone.put("reqtype", reqtype);
        		hashmapone.put("reptype", reptype);
        		hashmapone.put("repman", repman);
        		hashmapone.put("repidea", repidea);
        		hashmapone.put("repdt", repdt);
        		hashmapone.put("repbegdt", repbegdt);
        		hashmapone.put("rependdt", rependdt);
        		//
        		hashmapone.put("fmid", fmid);
        		//������ͥ����
        		srep = setOneCheckIdeaFamily(hashmapone);
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
     * ������ͥ���߳�Ա������������ͽ��
     * @param hashmap
     * @return
     */
    public String setOneCheckIdeaFamily(HashMap hashmap) {
    	String srep = "",sql = "";
    	String checkid = "",checkmoney = "0";
    	//
    	String pid = hashmap.get("pid").toString();//ҵ��ID
		String empid = hashmap.get("empid").toString();//�û�ID
		String preportflag = hashmap.get("preportflag").toString();//��λȷ��������ʶ
		//
		String empdepth = hashmap.get("empdepth").toString();//�û���������
		String nextdepth = hashmap.get("nextdepth").toString();//�û��ϼ�������������
		String backdepth = hashmap.get("backdepth").toString();//�û��¼�������������
		String onedepth = hashmap.get("onedepth").toString();//ҵ���һ������������
		String enddepth = hashmap.get("enddepth").toString();//ҵ�����������������
		//
		String reqtype = hashmap.get("reqtype").toString();//����ԭ��ID
		String reptype = hashmap.get("reptype").toString();//�������ID		
		String repman = hashmap.get("repman").toString();//����������(����1,����2)
		String repidea = hashmap.get("repidea").toString();//�������
		String repdt = hashmap.get("repdt").toString();//����ʱ��
		String repbegdt = hashmap.get("repbegdt").toString();//��ʼ����ʱ��
		String rependdt = hashmap.get("rependdt").toString();//��������ʱ��
		//
		String fmid = hashmap.get("fmid").toString();//������ͥID
		//
		sql = "select optcheck_id,checkmoney from biz_t_optcheck " +
				"where policy_id = '"+pid+"' and family_id = '"+fmid+"'";
		//log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	checkid = rs.getString("optcheck_id");
            	checkmoney = rs.getString("checkmoney");
            	
        	    //��������
        		HashMap hashmapone = new HashMap();
        		hashmapone.put("empid", empid);
        		hashmapone.put("empdepth", empdepth);
        		hashmapone.put("nextdepth", nextdepth);
        		hashmapone.put("backdepth", backdepth);
        		hashmapone.put("onedepth", onedepth);
        		hashmapone.put("enddepth", enddepth);
        		//
        		hashmapone.put("preportflag", preportflag);
        		//
        		hashmapone.put("reqtype", reqtype);
        		hashmapone.put("reptype", reptype);
        		hashmapone.put("repman", repman);
        		hashmapone.put("repidea", repidea);
        		hashmapone.put("repdt", repdt);
        		hashmapone.put("repbegdt", repbegdt);
        		hashmapone.put("rependdt", rependdt);
        		//
        		hashmapone.put("checkid", checkid);
        		hashmapone.put("checkmoney", checkmoney);
        		
        		//������������ͽ��
        		srep = setOneCheckIdea(hashmapone); 
        		//
        		//���µ�����ͥ���߳�Ա������׼״̬
	        	updatePolicyMoneyFlag(checkid);
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
     * ������ͥ���߳�Ա������������ͽ��
     * @param hashmap
     * @return
     */
    public String setOneCheckIdea(HashMap hashmap) {
    	String srep = "";
    	//
    	// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//��������
	    ConstantDefine constantdefine = new ConstantDefine(); 
    	//
		String empid = hashmap.get("empid").toString();//�û�ID
		String preportflag = hashmap.get("preportflag").toString();//��λȷ��������ʶ
		//
		String empdepth = hashmap.get("empdepth").toString();//�û���������
		String nextdepth = hashmap.get("nextdepth").toString();//�û��ϼ�������������
		String backdepth = hashmap.get("backdepth").toString();//�û��¼�������������
		String onedepth = hashmap.get("onedepth").toString();//ҵ���һ������������
		String enddepth = hashmap.get("enddepth").toString();//ҵ�����������������
		//
		String reqtype = hashmap.get("reqtype").toString();//����ԭ��ID
		String reptype = hashmap.get("reptype").toString();//�������ID		
		String repman = hashmap.get("repman").toString();//����������(����1,����2)
		String repidea = hashmap.get("repidea").toString();//�������
		String repdt = hashmap.get("repdt").toString();//����ʱ��
		String repbegdt = hashmap.get("repbegdt").toString();//��ʼ����ʱ��
		String rependdt = hashmap.get("rependdt").toString();//��������ʱ��
		//����
		String checkid = hashmap.get("checkid").toString();//����������ID
		String checkmoney = hashmap.get("checkmoney").toString();//��������������		
		//
		 
	    //���º˲�
	    String stemptype = constantdefine.POLICYCHECKCODE_RENEW;	    
	    //
	    String sql2="",temp = "";
	    if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){//��������		     
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//���º˲�  			  
	    		temp = "checkflag1 	  = '"+reptype+"'";
	    		sql2 += temp;
	    	}else{
	    		temp = "checkflag1 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//�û�������λ����������ȷ��Ȩ��
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}    			  
	    	}
	    	//   		  
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//��������
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    	      	   		  
	    	}
	    	//������
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�����		      
			 sql2 = "update biz_t_optcheck ";
			 temp = "set ";
			 sql2 += temp;
			 if(reptype.equals(stemptype)){//���º˲�
				 temp = "checkflag2 	  = '"+reptype+"'";
				 sql2 += temp;
				 if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
					 temp = ",checkflag1 	  = '"+reptype+"'";
					 sql2 += temp;
					 temp = ",ifover 	  = '"+backdepth+"'";
					 sql2 += temp;
				 }
				 
			 }else{
				 temp = "checkflag2 	  = '"+reptype+"'";
				 sql2 += temp;
				 if(preportflag.equals("1")){//�û�������λ����������ȷ��Ȩ��
					 temp = ",ifover 	  = '"+nextdepth+"'";
					 sql2 += temp;
				 }
			 }
			 //
			 if(!repbegdt.equals("")&& !rependdt.equals("")){
				 temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
				 sql2 += temp;
				 temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
				 sql2 += temp;
			 }
			 if(empdepth.equals(enddepth)){
				 //
				 //��������
				 temp = ",result 	  = '"+reptype+"'";
				 sql2 += temp;
				 temp = ",resultoper = '"+empid+"'";
				 sql2 += temp;
				 temp = ",resultdt   = sysdate";
				 sql2 += temp;	 
				 
			 }	
			 //
			 //������
			 temp = ",checkmoney   = '"+checkmoney+"'";
			 sql2 += temp;
			 //
			 temp = " where optcheck_id = '"+checkid+"'";
			 sql2 += temp;
			 //
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){//������
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//���º˲�
	    		temp = "checkflag3 	  = '"+reptype+"'";
  	    	  	sql2 += temp;
  	    	  	if(backdepth.equals(constantdefine.POLICYQUERYCODE_4)){
  	    	  		temp = ",checkflag2 	  = '"+reptype+"'";
  	    	  		sql2 += temp;
  	    	  		temp = ",ifover 	  = '"+backdepth+"'";
  	    	  		sql2 += temp;
  	    	  	}
  	    	  	if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
  	    	  		temp = ",checkflag1 	  = '"+reptype+"'";
  	    	  		sql2 += temp;
  	    	  		temp = ",ifover 	  = '"+backdepth+"'";
  	    	  		sql2 += temp;
  	    	  	}    			  
	    	}else{
	    		temp = "checkflag3 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//�û�������λ����������ȷ��Ȩ��
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}
	    	}
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//��������
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    		
	    	}
	    	//
	    	//������
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�м���
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//���º˲�
	    		temp = "checkflag4 	  = '"+reptype+"'";
	    		sql2 += temp; 
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_3)){
	    			temp = ",checkflag3 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
      		    	  sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_4)){
	    			temp = ",checkflag2 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
	    			temp = ",checkflag1 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}    
	    	}else{
	    		temp = "checkflag4 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//�û�������λ����������ȷ��Ȩ��
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}
	    	}
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//��������
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    		
	    	}
	    	//	
	    	//������
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ����
	    	sql2 = "update biz_t_optcheck ";
	    	temp = "set ";
	    	sql2 += temp;
	    	if(reptype.equals(stemptype)){//���º˲�
	    		temp = "checkflag5 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_2)){
	    			temp = ",checkflag4 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_3)){
	    			temp = ",checkflag3 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_4)){
	    			temp = ",checkflag2 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}
	    		if(backdepth.equals(constantdefine.POLICYQUERYCODE_5)){
	    			temp = ",checkflag1 	  = '"+reptype+"'";
	    			sql2 += temp;
	    			temp = ",ifover 	  = '"+backdepth+"'";
	    			sql2 += temp;
	    		}    
	    	}else{
	    		temp = "checkflag5 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		if(preportflag.equals("1")){//�û�������λ����������ȷ��Ȩ��
	    			temp = ",ifover 	  = '"+nextdepth+"'";
	    			sql2 += temp;
	    		}
	    	}
	    	//
	    	if(!repbegdt.equals("")&& !rependdt.equals("")){
	    		temp = ",rebegdt    = to_date('"+repbegdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    		temp = ",reenddt    = to_date('"+rependdt+"','yyyy-mm-dd')";
	    		sql2 += temp;
	    	}
	    	if(empdepth.equals(enddepth)){
	    		//
	    		//��������
	    		temp = ",result 	  = '"+reptype+"'";
	    		sql2 += temp;
	    		temp = ",resultoper = '"+empid+"'";
	    		sql2 += temp;
	    		temp = ",resultdt   = sysdate";
	    		sql2 += temp;	 
	    				
	    	}
	    	//
	    	//������
	    	temp = ",checkmoney   = '"+checkmoney+"'";
    		sql2 += temp;
    		//
	    	temp = " where optcheck_id = '"+checkid+"'";
	    	sql2 += temp;
	    	//
	    }
		//
	    //
	    String checkideaid = tableinfoquery.getseqfromname("XOPTCHECKIDEA_ID");
	    String sql1 = "insert into biz_t_optcheckidea " +
	      		"(optcheckidea_id,optcheck_id,depth,appideaman,apparea,rebegdt,reenddt,appresult,apptime,checkoper,checkdt) " +
	      		"values " +
	      		"("+checkideaid+","+checkid+",'"+empdepth+"','"+repman+"','"+repidea+"',to_date('"+repbegdt+"','yyyy-mm-dd'),to_date('"+rependdt+"','yyyy-mm-dd'),'"+reptype+"',to_date('"+repdt+"','yyyy-mm-dd'),'"+empid+"',sysdate)";
		//
		//log.debug("sql2:"+sql2);
		//log.debug("sql1:"+sql1);
	    
	    //SQLִ�б�ʶ
	    boolean boolrep = false;
	    //
  	  	Connection conn = null;                 //����Connection����
  	  	PreparedStatement pstmt = null;         //����PreparedStatement����
  	  	try {
  	  		conn = DBUtils.getConnection();     //��ȡ���ݿ�����
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql2);//����sql����PreparedStatement            
  	  		pstmt.execute();              //ִ��  
  	  		pstmt = conn.prepareStatement(sql1);//����sql����PreparedStatement            
  	  		pstmt.execute();              //ִ��  	  		 
  	  		conn.commit();                      //�ر�
  	  		//�ɹ���ʶ
  	  		boolrep=true;
  	  		//������ʾ
            if(preportflag.equals("1")){//�û�������λ����������ȷ��Ȩ��
            	if(empdepth.equals(enddepth)){//��������
            		srep = "��������ɹ�,�������Ϊ�������!";
            	}else{
            		if(reptype.equals(stemptype)){//���º˲�
            			if(empdepth.equals(onedepth)){
            				srep = "��������ɹ�,�����������º˲�!";
            			}else{
            				srep = "��������ɹ�,�����˻����º˲�!";
            			}
            		}else{
            			srep = "��������ɹ�,���������ϼ�����!";
            		}
            	}
            }else{
            	srep = "��������ɹ�!";
            }
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "������������ʧ��!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //�ر�PreparedStatement
  	  		//DBUtils.close(conn);                //�ر�����
  	  	}
  	  	//�ɹ���ʶ
  	  	if(boolrep){
    		//���������ͥ���Ա����ԭ��
    		insertOneCheckIdeaReqType(checkideaid,reqtype);
    		//
  	  	}
  	  	//
    	return srep;
    }
    /**
     * ������ͥ���߳�Ա����ʩ��
     * @param hashmap
     * @return
     */
    public String setOneCheckChildIdea(HashMap hashmap) {
    	String srep = "",sql1 = "",sql2 = "",childideaid = "";
    	//��������
	    ConstantDefine constantdefine = new ConstantDefine();
	    //����ʩ��������
	    PolicyChildCheckMatch policychildcheckmatch = new PolicyChildCheckMatch();
    	//
		String empid = hashmap.get("empid").toString();//�û�ID
		String empdepth = hashmap.get("empdepth").toString();//�û���������
		String repdt = hashmap.get("repdt").toString();//����ʱ��
		//����
		String checkchildid = hashmap.get("checkchildid").toString();//��������ʩ��������ID
		String checkchildmoney = hashmap.get("checkchildmoney").toString();//��������ʩ������������
		//
		String tempfild = "";
		if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){//��������
	    	tempfild = "itemmoney1";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�����
	    	tempfild = "itemmoney2";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){//���ؼ���
	    	tempfild = "itemmoney3";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�оּ���
	    	tempfild = "itemmoney4";
	    }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ������
	    	tempfild = "itemmoney5";
	    }
	    //��������ʩ��
		sql1 = "update biz_t_optcheckchild " +
				"set itemmoney = '"+checkchildmoney+"'" +
					","+tempfild+" = '"+checkchildmoney+"'" +
					",itemresultoper = '"+empid+"'" +
					",itemresultdt = sysdate " +
				"where optcheckchild_id = '"+checkchildid+"'";  //���µ���״̬SQL
		//��������ʩ�����
		//ȡ�÷���ʩ�����û��������ID
		childideaid = policychildcheckmatch.getPolicyChildChickIdeas(empid,checkchildid);
	    if(childideaid.equals("")){	    	
	    	sql2 = "insert into biz_t_optcheckchildidea " +
					"(optcheckchildidea_id,optcheckchild_id,depth,childmoney,childapptime,childcheckoper,childcheckdt,status) " +
					"values " +
					"(xoptcheckchildidea_id.nextval,'"+checkchildid+"','"+empdepth+"','"+checkchildmoney+"',to_date('"+repdt+"','yyyy-mm-dd'),'"+empid+"',sysdate,'1')";  //���µ���״̬SQL
	    }else{
	    	sql2 = "update biz_t_optcheckchildidea " +
					    "set childmoney = '"+checkchildmoney+"'," +
					    	"childapptime = to_date('"+repdt+"','yyyy-mm-dd')," +
					    	"childcheckdt = sysdate " +
					"where optcheckchildidea_id = '"+childideaid+"'";  //���µ���״̬SQL
	    }
		//log.debug(sql1);
		//log.debug(sql2);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    pstmt = conn.prepareStatement(sql2);//����sql����PreparedStatement            
		    pstmt.execute();              //ִ��
		    conn.commit();                //�ر�		   
		    srep = "���·���ʩ�������������ɹ�!";
		} catch (SQLException e) {			
			try {
				srep = "���·���ʩ������������ʧ��!";
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
     * ���������������ԭ��
     * @param checkideaid
     * @param reqtype
     * @return
     */
    public String insertOneCheckIdeaReqType(String checkideaid,String reqtype){
    	String srep = "",sql = "",typeid = "";
	  	
	  	String [] atypeid = reqtype.split(",");
		int ilength = atypeid.length;
	  	for (int h=0;h<ilength;h++){		  		
	  		typeid = atypeid[h];
	  		//
		    sql = "insert into biz_t_optchecktype " +
		      		"(optchecktype_id,optcheckidea_id,reqtype) " +
		      		"values " +
		      		"(xoptchecktype_id.nextval,"+checkideaid+",'"+typeid+"')";
	    	  
		  	//log.debug(sql);
	        
			Connection conn = null;                 //����Connection����
			PreparedStatement pstmt = null;         //����PreparedStatement����
			try {
			    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			    conn.setAutoCommit(false);
			    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
			    pstmt.execute();              //ִ��  
			    conn.commit();                      //�ر�	            
			    srep = "�����������ԭ������ɹ�!";
			} catch (SQLException e) {
			  try {
			    srep = "�����������ԭ�����ʧ��!";
			      conn.rollback();
			  } catch (SQLException e1) {          
			      e1.printStackTrace();
			  }
			} finally {
			    DBUtils.close(pstmt);               //�ر�PreparedStatement
			    //DBUtils.close(conn);                //�ر�����
			}
	  	}		  	
	  	return srep;
  	}
}
