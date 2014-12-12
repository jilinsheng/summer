package com.mingda.action.policy.check;

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
public class Policycheckinfo {
	static Logger log = Logger.getLogger(Policycheckinfo.class);
	/**
	 * ����ҵ���׼
	 * ��ͥ���߳�Ա
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String SetNewPolciyMatchMore(String pid,String empid) {		
		String srep = "";
		// ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ��������ѯ����
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();	
		//
		log.debug(System.currentTimeMillis());
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
        String deptid = tableinfoquery.getempdepid(empid);
    	
	    //����������
        AddCheckMore(pid,empid,onedepth,deptid);
        //���ɷ���ʩ��������
        AddCheckChildMore(pid,deptid);
		//����ҵ���������ϱ�׼��ʶ
        UpdateCheckMatchSqlFlag(pid,deptid);
        
        
        
        log.debug(System.currentTimeMillis());

        //
        return srep;
    } 
	/**
	 * ����������
	 * @param pid
	 * @param empid
	 * @param onedepth
	 * @param deptid
	 * @return
	 */
	public String AddCheckMore(String pid,String empid,String onedepth,String deptid) {
		String srep = "";
		String sql = "",matchsql = "",newsql = "",matchsqlsql = "";
		String accmode = "0",acctype = "0",moneyflag = "1",moneyaout = "1";
		String physql = "",accphysql = "",locsql = "",acclocsql = "";
		String tselect = "",tfrom = "",twhere = "",aselect = "",afrom = "",awhere = "";
		String tempselect = "",tempfrom = "",tempwhere = "";
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//��������
    	ConstantDefine constantdefine = new ConstantDefine();		
		//��Ҫ����������ʶ
		accmode = policymanagecheckmanage.getPolicyUserAccFlag(pid);
		//ҵ���������
		acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);
		//������ͥ
		moneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
		//���ϱ�׼
		moneyaout = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;
		//
		int ibeg=0,iend=0;
		//
		/****************
		accmode = "0";
		�����
		*****************/
		accmode = "0";
		
		if("1".equals(accmode)){
			//����Ҫ���������
			sql = "select physql,locsql from doper_t_standard " +
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
	            	locsql = rs.getString("locsql"); 
	            	physql = rs.getString("physql"); 
	        		ibeg = physql.indexOf("from");//��һ�γ���λ��
	            	iend = physql.indexOf("where");//��һ�γ���λ��
	            	if(ibeg == -1){
	            		return srep;
	            	}	            	
	        		tselect = physql.substring(0, ibeg);	        		
	        		if(iend > 0){
	        			tfrom = physql.substring(ibeg+5, iend);
	        			twhere = physql.substring(iend+5, physql.length());
	        		}else{
	            		tfrom = physql.substring(ibeg+5, physql.length());
	            	}	        		
	        		//log.debug("tselect:"+tselect+"tfrom:"+tfrom +"twhere:" + twhere);
	        		//====================================���ɱ�׼�ͺ���SQL���BEG==================================//
            	    tempselect = tselect;
            	    tempfrom = "INFO_T_MEMBER,INFO_T_FAMILY";
            	    tempwhere = "INFO_T_MEMBER.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID";
            		//���β�ѯ��
            	    String snewtflag = "F";
            	    String [] asfrom = tfrom.split(",");
            	    int itflen = asfrom.length;	    
            	    for (int y=0;y<itflen;y++){
            	    	String sfrom = asfrom[y].trim();
            	    	//��ֹ��������ͬ����INFO_T_FAMILYCLASS,INFO_T_FAMILY
            	    	//��ͬ����ΪINFO_T_FAMILY
            	    	String [] anewfrom = tempfrom.split(",");
            	    	int inewtlen = anewfrom.length;
            	    	//��ʼ��
            	    	snewtflag = "F";
            	    	for (int j=0;j<inewtlen;j++){	    		
            	    		String stfrom = anewfrom[j].trim();
            	    		if(stfrom.equals(sfrom)){
            	    			snewtflag = "T";
            	    		}
            	    	}
            	    	//��������ͬ
            	    	if(snewtflag.equals("F")){
            	    		if(tempfrom.equals("")){
            	    			tempfrom = sfrom;
                			}else{
                				tempfrom += "," + sfrom;
                			}
            	    	}	    	
            	    }            	    
            	    
            	    if (null == twhere || "".equals(twhere)){
        	    		tempwhere += " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";;
        	    	}else{
        	    		tempwhere += " and " + twhere + " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";
        	    	}
   
            	    //====================================���ɱ�׼�ͺ���SQL���END==================================//  
            	    //====================================����������SQL���BEG==================================//
            	    //������(��ͥ����)
                	String sqlfamily = "select a.family_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//������(��Ա����)
                	String sqlmember = "select a.family_id,a.member_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//
            	    //��������������SQL
	            	if("0".equals(acctype)){
	            		//����������SQL���
	            		tempwhere += " and INFO_T_MEMBER.MEMBER_ID = INFO_T_FAMILY.MASTERID ";
	            	    matchsql = tempselect +",INFO_T_FAMILY.MASTERID,xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += " and not exists (" + sqlfamily + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	    				//��ͥ����	            	    
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;	            		
	    			}else{	    				
	    				//����������SQL���
	            	    matchsql = tempselect +",xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += "  and not exists (" + sqlmember + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	            	    //��Ա����
	            	    newsql = "insert into biz_t_optcheck " +
        				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;
	    			}
	            	//log.debug("matchsql:"+matchsql);
	            	//log.debug("newsql:"+newsql);
	            	//��������������
	            	ExeSQLFromUserSQL(newsql);
	            	//====================================����������SQL���END==================================//
	            	//==================================================���ɱ�׼����BEG==============================//
	            	//����������������ϱ�׼
	            	//������ת��ȥ�����ҿո�
	            	physql = physql.replace("'","''");
	        		locsql = locsql.replace("'","''");
	        		//
	        		tempselect = "xoptchecksql_id.nextval,optcheck_id,'"+physql+"','"+locsql+"'";
	        		matchsqlsql = "select " + tempselect +" " +
		        			"from biz_t_optcheck a,info_t_family b " +
		        			"where a.family_id = b.family_id and a.matchsqlflag = '0' and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
					matchsqlsql = matchsqlsql + " and exists ( select info_t_family.family_id from " + tempfrom  + " where " + tempwhere + " and b.family_id = info_t_family.family_id )";
					newsql = "insert into biz_t_optchecksql " +
							"(optchecksql_id,optcheck_id,physql,locsql) " + matchsqlsql ;	        		
	        		
	            	//log.debug("matchsqlsql:"+matchsqlsql);
	            	//log.debug("newsql:"+newsql);
	            	//�����������׼SQL��¼
					ExeSQLFromUserSQL(newsql);
	            	//==================================================���ɱ�׼����END==============================//
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //�رս����
	            DBUtils.close(pstmt);               //�ر�PreparedStatement
	            //DBUtils.close(conn);                //�ر�����
	        }            
		}else{
			//��Ҫ���������
			sql = "select a.accexpphysql,a.accexplocsql,b.physql,b.locsql from doper_t_standarddept a, doper_t_standard b " +
					"where a.standard_id = b.standard_id and a.flag = '1' and instr('#' || '"+ deptid +"', '#' || organization_id) > 0 " +
					"and policy_id = '"+ pid +"' and accexpphysql is not null " +
					"order by b.planmoney desc";
			//
			
	        //log.debug(sql);
	        Connection conn = null;                 //����Connection����
	        PreparedStatement pstmt = null;         //����PreparedStatement����
	        ResultSet rs = null;                    //����ResultSet����
	        try {
	            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {	            	
	        		//��׼SQL
	            	locsql = rs.getString("locsql"); 
	            	acclocsql  = rs.getString("accexplocsql"); 
	            	//
	        		physql = rs.getString("physql"); 
	        		ibeg = physql.indexOf("from");//��һ�γ���λ��
	            	iend = physql.indexOf("where");//��һ�γ���λ��
	            	if(ibeg == -1){
	            		return srep;
	            	}	            	
	        		tselect = physql.substring(0, ibeg);	        		
	        		if(iend > 0){
	        			tfrom = physql.substring(ibeg+5, iend);
	        			twhere = physql.substring(iend+5, physql.length());
	        		}else{
	            		tfrom = physql.substring(ibeg+5, physql.length());
	            	}	        		
	        		//log.debug("tselect:"+tselect+"tfrom:"+tfrom +"twhere:" + twhere);
	        		//���㹫ʽSQL
	            	accphysql = rs.getString("accexpphysql"); 	            	
	            	ibeg = accphysql.indexOf("from");//��һ�γ���λ��
	            	iend = accphysql.indexOf("where");//��һ�γ���λ��
	            	if(ibeg == -1){
	            		return srep;
	            	}
	            	aselect = accphysql.substring(0, ibeg);
	        		
	        		if(iend > 0){
	        			afrom = accphysql.substring(ibeg+5, iend);
	        			awhere = accphysql.substring(iend+5, accphysql.length());
	            	}else{
	            		afrom = accphysql.substring(ibeg+5, accphysql.length());
	            	}
	        		
	        		//log.debug("aselect:"+aselect+"afrom:"+afrom +"awhere:" + awhere);
	        		
	        		//====================================���ɱ�׼�ͺ���SQL���BEG==================================//
            	    tempselect = aselect;
            	    tempfrom = "INFO_T_MEMBER,INFO_T_FAMILY";
            	    tempwhere = "INFO_T_MEMBER.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID";
            		//���β�ѯ��
            	    String snewtflag = "F";
            	    String [] asfrom = tfrom.split(",");
            	    int itflen = asfrom.length;	    
            	    for (int y=0;y<itflen;y++){
            	    	String sfrom = asfrom[y].trim();
            	    	//��ֹ��������ͬ����INFO_T_FAMILYCLASS,INFO_T_FAMILY
            	    	//��ͬ����ΪINFO_T_FAMILY
            	    	String [] anewfrom = tempfrom.split(",");
            	    	int inewtlen = anewfrom.length;
            	    	//��ʼ��
            	    	snewtflag = "F";
            	    	for (int j=0;j<inewtlen;j++){	    		
            	    		String stfrom = anewfrom[j].trim();
            	    		if(stfrom.equals(sfrom)){
            	    			snewtflag = "T";
            	    		}
            	    	}
            	    	//��������ͬ
            	    	if(snewtflag.equals("F")){
            	    		if(tempfrom.equals("")){
            	    			tempfrom = sfrom;
                			}else{
                				tempfrom += "," + sfrom;
                			}
            	    	}	    	
            	    }
            	    //�����ѯ��
            	    snewtflag = "F";
            	    asfrom = afrom.split(",");
            	    itflen = asfrom.length;	    
            	    for (int y=0;y<itflen;y++){
            	    	String sfrom = asfrom[y].trim();;
            	    	//��ֹ��������ͬ����INFO_T_FAMILYCLASS,INFO_T_FAMILY
            	    	//��ͬ����ΪINFO_T_FAMILY
            	    	String [] anewfrom = tempfrom.split(",");
            	    	int inewtlen = anewfrom.length;
            	    	//��ʼ��
            	    	snewtflag = "F";
            	    	for (int j=0;j<inewtlen;j++){	    		
            	    		String stfrom = anewfrom[j].trim();
            	    		if(stfrom.equals(sfrom)){
            	    			snewtflag = "T";
            	    		}
            	    	}
            	    	//��������ͬ
            	    	if(snewtflag.equals("F")){
            	    		if(tempfrom.equals("")){
            	    			tempfrom = sfrom;
                			}else{
                				tempfrom += "," + sfrom;
                			}
            	    	}	    	
            	    }
            	    if (null == awhere || "".equals(awhere)){
            	    	if (null == twhere || "".equals(twhere)){
            	    		tempwhere += " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";
            	    	}else{
            	    		tempwhere += " and " + twhere + " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";
            	    	}            	    	
            	    }else{
            	    	if (null == twhere || "".equals(twhere)){
            	    		tempwhere += " and " + awhere + " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";
            	    	}else{
            	    		tempwhere += " and " + twhere + " and " + awhere + " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";
            	    	}            	    	
            	    }
            	    //====================================���ɱ�׼�ͺ���SQL���END==================================//  
            	    //====================================����������SQL���BEG==================================//
            	    //������(��ͥ����)
                	String sqlfamily = "select a.family_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//������(��Ա����)
                	String sqlmember = "select a.family_id,a.member_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//
            	    //��������������SQL
	            	if("0".equals(acctype)){
	            		//����������SQL���
	            		tempwhere += " and INFO_T_MEMBER.MEMBER_ID = INFO_T_FAMILY.MASTERID ";
	            	    matchsql = tempselect +",INFO_T_FAMILY.MASTERID,xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += " and not exists (" + sqlfamily + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	    				//��ͥ����
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,checkmoney,member_id,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;
	    			}else{	    				
	    				//����������SQL���
	            	    matchsql = tempselect +",xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += "  and not exists (" + sqlmember + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	            	    //��Ա����
	            	    newsql = "insert into biz_t_optcheck " +
        				"(family_id,member_id,checkmoney,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;
	    			}
	            	//log.debug("matchsql:"+matchsql);
	            	//log.debug("newsql:"+newsql);
	            	//��������������
	            	ExeSQLFromUserSQL(newsql);
	            	//
	            	//====================================����������SQL���END==================================//
	            	//==================================================���ɱ�׼����BEG==============================//
	            	//����������������ϱ�׼
	            	//������ת��ȥ�����ҿո�
	            	physql = physql.replace("'","''");
	        		locsql = locsql.replace("'","''");
	        		accphysql = accphysql.replace("'","''");
	        		acclocsql = acclocsql.replace("'","''");
	        		//
	        		tempselect = "xoptchecksql_id.nextval,optcheck_id,'"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"'";
	        		matchsqlsql = "select " + tempselect +" " +
		        			"from biz_t_optcheck a,info_t_family b " +
		        			"where a.family_id = b.family_id and a.matchsqlflag = '0' and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
					matchsqlsql = matchsqlsql + " and exists ( select info_t_family.family_id from " + tempfrom  + " where " + tempwhere + " and b.family_id = info_t_family.family_id )";
					newsql = "insert into biz_t_optchecksql " +
							"(optchecksql_id,optcheck_id,physql,locsql,accphysql,acclocsql) " + matchsqlsql ;	        		
	        		
	            	//log.debug("matchsqlsql:"+matchsqlsql);
	            	//log.debug("newsql:"+newsql);
	            	//�����������׼SQL��¼
					ExeSQLFromUserSQL(newsql);	
					 log.debug(newsql);
	            	//==================================================���ɱ�׼����END==============================//
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //�رս����
	            DBUtils.close(pstmt);               //�ر�PreparedStatement
	            //DBUtils.close(conn);                //�ر�����
	        }
		}
		return srep;
	}
	/**
	 * ���ɷ���ʩ��������
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public String AddCheckChildMore(String pid,String deptid) {
		String srep = "",policychild = "",sqltype = "";
		String tempsql = "",physql="",locsql = "",matchsql = "",newsql = "";
		String tselect = "",tfrom = "",twhere = "";
		String tempselect = "",tempfrom = "",tempwhere = "";
		int ibeg=0,iend=0;
		//��������ҵ�����ʩ����׼����			
		tempsql = "select b.policychild_id,a.physql,a.locsql,b.sqltype " +
				"from doper_t_policychildsql a,doper_t_policychild b,doper_t_policy c " +
				"where a.status = '1' and b.policy_id = c.policy_id and a.physql is not null " +
				"and b.status = '1' and a.policychild_id = b.policychild_id  and c.policy_id = '"+pid+"'";   //����SQL���
		
        //log.debug(tempsql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(tempsql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	policychild = rs.getString("policychild_id");
            	physql = rs.getString("physql"); 
            	locsql = rs.getString("locsql"); 
            	sqltype = rs.getString("sqltype"); 
            	//
            	ibeg = physql.indexOf("from");//��һ�γ���λ��
            	iend = physql.indexOf("where");//��һ�γ���λ��
            	if(ibeg == -1){
            		return srep;
            	}	            	
        		tselect = physql.substring(0, ibeg);	        		
        		if(iend > 0){
        			tfrom = physql.substring(ibeg+5, iend);
        			twhere = physql.substring(iend+5, physql.length());
        		}else{
            		tfrom = physql.substring(ibeg+5, physql.length());
            	}	        		
        		//log.debug("tselect:"+tselect+"tfrom:"+tfrom +"twhere:" + twhere);
        		//====================================���ɱ�׼�ͺ���SQL���BEG==================================//
        	    tempselect = tselect;
        	    tempfrom = "INFO_T_MEMBER,INFO_T_FAMILY,BIZ_T_OPTCHECK";
        	    tempwhere = "INFO_T_MEMBER.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID and INFO_T_FAMILY.FAMILY_ID = BIZ_T_OPTCHECK.FAMILY_ID";
        		//���β�ѯ��
        	    String snewtflag = "F";
        	    String [] asfrom = tfrom.split(",");
        	    int itflen = asfrom.length;	    
        	    for (int y=0;y<itflen;y++){
        	    	String sfrom = asfrom[y].trim();
        	    	//��ֹ��������ͬ����INFO_T_FAMILYCLASS,INFO_T_FAMILY
        	    	//��ͬ����ΪINFO_T_FAMILY
        	    	String [] anewfrom = tempfrom.split(",");
        	    	int inewtlen = anewfrom.length;
        	    	//��ʼ��
        	    	snewtflag = "F";
        	    	for (int j=0;j<inewtlen;j++){	    		
        	    		String stfrom = anewfrom[j].trim();
        	    		if(stfrom.equals(sfrom)){
        	    			snewtflag = "T";
        	    		}
        	    	}
        	    	//��������ͬ
        	    	if(snewtflag.equals("F")){
        	    		if(tempfrom.equals("")){
        	    			tempfrom = sfrom;
            			}else{
            				tempfrom += "," + sfrom;
            			}
        	    	}	    	
        	    }            	    
        	    
        	    if (null == twhere || "".equals(twhere)){
    	    		tempwhere += " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";;
    	    	}else{
    	    		tempwhere += " and " + twhere + " and INFO_T_FAMILY.Organization_Id like '"+ deptid +"%'";
    	    	}
        	    //����ʩ������        	   
        	    if("2".equals(sqltype)){
        	    	//��Ա����
        	    	tempwhere += " and BIZ_T_OPTCHECK.MEMBER_ID = INFO_T_MEMBER.MEMBER_ID ";
        	    	tempwhere += " and BIZ_T_OPTCHECK.POLICY_ID = '"+pid+"' and BIZ_T_OPTCHECK.MATCHSQLFLAG = '0' ";
        	    	matchsql = tempselect +",xoptcheckchild_id.nextval,BIZ_T_OPTCHECK.OPTCHECK_ID,'0','"+policychild+"' " + " from " + tempfrom  + " where " + tempwhere;
            	    matchsql += " and not exists ( select optc.optcheck_id from biz_t_optcheckchild optc " +
            	    		" where optc.family_id = INFO_T_FAMILY.FAMILY_ID and optc.member_id = INFO_T_MEMBER.MEMBER_ID " +
            	    		" and optc.policychild_id = '"+policychild+"' and optc.optcheck_id = BIZ_T_OPTCHECK.OPTCHECK_ID )";
        	    }else{
        	    	//��ͥ����
        	    	tempwhere += " and INFO_T_MEMBER.MEMBER_ID = INFO_T_FAMILY.MASTERID ";
        	    	tempwhere += " and BIZ_T_OPTCHECK.MEMBER_ID = INFO_T_FAMILY.MASTERID ";
        	    	tempwhere += " and BIZ_T_OPTCHECK.POLICY_ID = '"+pid+"' and BIZ_T_OPTCHECK.MATCHSQLFLAG = '0' ";
        	    	matchsql = tempselect +",INFO_T_FAMILY.MASTERID,xoptcheckchild_id.nextval,BIZ_T_OPTCHECK.OPTCHECK_ID,'0','"+policychild+"' " + " from " + tempfrom  + " where " + tempwhere;
            	    matchsql += " and not exists ( select optc.optcheck_id from biz_t_optcheckchild optc " +
            	    		" where optc.family_id = INFO_T_FAMILY.FAMILY_ID and optc.member_id = INFO_T_FAMILY.MASTERID " +
            	    		" and optc.policychild_id = '"+policychild+"' and optc.optcheck_id = BIZ_T_OPTCHECK.OPTCHECK_ID )";
        	    }
            	newsql = "insert into biz_t_optcheckchild " +
						"(family_id,member_id,optcheckchild_id,optcheck_id,matchsqlflag,policychild_id) " + matchsql ;	
            	//log.debug("matchsql:"+matchsql);
            	//log.debug("newsql:"+newsql);
            	//���ɷ���ʩ��������
				ExeSQLFromUserSQL(newsql);
            	//==================================================���ɱ�׼����BEG==============================//
            	//������ת��ȥ�����ҿո�
            	physql = physql.replace("'","''");
        		locsql = locsql.replace("'","''");
        		//
        		//
        		matchsql = "select xoptcheckchildsql_id.nextval,optcheckchild_id,'"+physql+"','"+locsql+"' " +
	        			"from biz_t_optcheck a,info_t_family b,biz_t_optcheckchild c " +
	        			"where a.family_id = b.family_id and a.optcheck_id = c.optcheck_id and c.matchsqlflag = '0' " +
						"and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%' ";
				newsql = "insert into biz_t_optcheckchildsql " +
						"(optcheckchildsql_id,optcheckchild_id,physql,locsql) " + matchsql ;        		
				//log.debug("matchsql:"+matchsql);
            	//log.debug("newsql:"+newsql);
            	//���ɷ���ʩ����������ϱ�׼
				ExeSQLFromUserSQL(newsql);
            	//==================================================���ɱ�׼����END==============================//   
            	//
            	//
            	//���·���ʩ����������ϱ�׼��ʶ
            	UpdateCheckChildMatchSqlFlag(pid,deptid);
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
	 * ����ҵ���������ϱ�׼��ʶ
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public String UpdateCheckMatchSqlFlag(String pid,String deptid) {
		String srep = "",matchsqlflag = "";
		//������
    	matchsqlflag = "update biz_t_optcheck t set t.matchsqlflag = '1'  " +
    			"where t.matchsqlflag = '0' " +
    			"and exists (select a.family_id " +
    						"from biz_t_optcheck a,info_t_family b " +
    						"where a.family_id = b.family_id and a.matchsqlflag = '0' " +
    						"and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%' " +
    						"and a.optcheck_id = t.optcheck_id)";
    	//log.debug("matchsqlflag:"+matchsqlflag);
    	//����ҵ���������ϱ�׼��ʶ
    	ExeSQLFromUserSQL(matchsqlflag);
    	
		return srep;
	}
	/**
	 * ���·���ʩ����������ϱ�׼��ʶ
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public String UpdateCheckChildMatchSqlFlag(String pid,String deptid) {
		String srep = "",matchsqlflag = "";
		//������
    	matchsqlflag = "update biz_t_optcheckchild t set t.matchsqlflag = '1'  " +
    			"where t.matchsqlflag = '0' " +
    			"and exists (select a.optcheck_id " +
							"from biz_t_optcheck a,info_t_family b,biz_t_optcheckchild c " +
							"where a.family_id = b.family_id and a.optcheck_id = c.optcheck_id and c.matchsqlflag = '0' " +
							"and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%' " +
							"and a.optcheck_id = t.optcheck_id)";
    	//log.debug("matchsqlflag:"+matchsqlflag);
    	//���·���ʩ����������ϱ�׼��ʶ
    	ExeSQLFromUserSQL(matchsqlflag);
    	
		return srep;
	}
	
	/**
	 * �����
	 * @param sql
	 * @return
	 */
	public String ExeSQLFromUserSQL(String sql) {
		String srep = "";
		//
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
  	  		srep = "�����ɹ�!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "����ʧ��!";
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
}
