package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.myjdbc.ConnectionManager;

public class QueryManageInterView {
	static Logger log = Logger.getLogger(QueryManageInterView.class);
	/**
	 * �߷õ����ѯ
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public StringBuffer InterViewFamilySql(HashMap hashmap) {
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuffer shtml= new StringBuffer("");
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		/**ͨ�õ�SQL��������**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**����Ĵ������**/
		String tdept = hashmap.get("tdept").toString();
		String torder = hashmap.get("torder").toString();
		String ttabid = hashmap.get("ttabid").toString();
		String tbegdt = hashmap.get("tbegdt").toString();
		String tenddt = hashmap.get("tenddt").toString();
		//
		/**��������**/
		//XML����
		String xmlth = "",sql = "",countsql ="",countnum = "0";
		//�߼�����
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",masterid = "",mastername = "",population = "",deptid = "",deptname = ""; 
		//��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";//��ͥ��    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//��Ա��Ϣ
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		
		tfield = "FAMILY_ID";//��ͥID
		id = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "FAMILYNO";//��ͥ���
		familyid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MASTERNAME";//��������
		mastername = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "MASTERPAPERID";//����֤������
		masterid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "POPULATION";//��ͥ�˿�
		population = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);    		
		
		tfield = "ORGANIZATION_ID";//��ͥ����ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//��ͥ����
		//
		//
		if(ttabid.equals("1")){//�߷õǼ�
			//��ѯ�ֶ�
			tselect = id+","+familyid+","+mastername+","+masterid+","+population+","+deptid + "," + deptname;
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
			//��ѯ����
			if(null == twhere || twhere.equals("")){				
				
			}else{
				twhere += "  and ";
			}
			//��������ѯ
			twhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
			//��������ѯ
    		String temp1 = twhere.substring(0,twhere.length());  
    		//��֯�����߼�����
    		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
			int iend = temp1.indexOf(temp2);//�Ƿ������֯����					
			if(iend < 0){
				twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
			}	
			//��ͥ����״̬
			twhere += " and INFO_T_FAMILY.STATUS = '1'";
			//XML��ͷ
			xmlth = tselect;
			 log.debug("test count 12");
			countsql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1",torder,conn).toString();
			countnum = tableinfoquery.getresultcountfromsql(countsql);
			//
		}else if(ttabid.equals("2")){//�߷ò�ѯ
			//��ͥ�߷ü�¼��
			String viewid = "BIZ_T_INTERVIEW.INTERVIEW_ID";
			String viewdt = "to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd')";	
			String viewresult = "BIZ_T_INTERVIEW.RESULT";		
			String viewperson = "BIZ_T_INTERVIEW.PERSON";
			String viewdeptname = "SYS_T_ORGANIZATION.FULLNAME";
			//��ѯ�ֶ�
			tselect = id+","+familyid+","+mastername+ ","+viewid+","+viewdt+","+viewresult+","+viewperson+","+viewdeptname;
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = "BIZ_T_INTERVIEW" + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + "BIZ_T_INTERVIEW" + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
			//��ѯ����
			if(null == twhere || twhere.equals("")){				
				
			}else{
				twhere += "  and ";
			}
			//
			//��������ѯ
			twhere += " BIZ_T_INTERVIEW.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
			//��������ѯ
    		String temp1 = twhere.substring(0,twhere.length());  
    		//��֯�����߼�����
    		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
			int iend = temp1.indexOf(temp2);//�Ƿ������֯����					
			if(iend < 0){
				twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
			}	
			twhere += " and BIZ_T_INTERVIEW.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID ";
			//�߷�����
			if(!tbegdt.equals("") && !tenddt.equals("")){//���ڲ�ѯ
				//
				twhere += " and to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd') >=  '"+tbegdt+"'";
	      		//
				twhere += " and  to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd') <=  '"+tenddt+"'";
		    	
			}
			//��ͥ����״̬
			twhere += " and INFO_T_FAMILY.STATUS = '1'";
			//XML��ͷ
			xmlth = id+","+familyid+","+mastername+","+"�߷ü�¼��.�߷�ID,�߷ü�¼��.�߷�����,�߷ü�¼��.�߷�����,�߷ü�¼.�߷���,������.�߷û���";
			
			countsql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1","to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd') desc" ,conn).toString();
			countnum = tableinfoquery.getresultcountfromsql(countsql);
		}
		//
		sql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"1",torder,conn).toString();
		
		//
		
		shtml.append(tableinfoquery.getInfoResultForXml(xmlth,sql,countnum));
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
}
