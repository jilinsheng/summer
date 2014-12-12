package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

public class QueryManageInfo {
	//��������
	static Logger log = Logger.getLogger(QueryManageInfo.class);
	ConstantDefine constantdefine = new ConstantDefine();
	
	/**
	 * ��������ҵ���ͥ��ѯ
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public StringBuffer GetExeFamilySql(HashMap hashmap) {
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
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		String tpolicysta = hashmap.get("tpolicysta").toString();
		/**��������**/		
		//�߼�����
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",masterid = "",mastername = "",salmoney = "",population = "",dessaltype = "",deptid = "",deptname = ""; 
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
		
		tfield = "SALMONEY";//���Ͻ��
		salmoney = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "DESSALTYPE";//��������
		dessaltype = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "ORGANIZATION_ID";//��ͥ����ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//��ͥ����
		//
		//��ѯ�ֶ�
		String tempselect = id+","+familyid+","+mastername+","+masterid+","+population+","+salmoney+","+dessaltype+","+deptid;
		//��ͥ��̬�ֶ�
		String [] selectname = tselect.split(",");
		String tempname = "";
    	int ilength = selectname.length;
	    for (int h=0;h<ilength;h++){
	    	tempname = selectname[h];	    	
	    	//
	    	int iend = tempselect.indexOf(tempname);//�Ƿ����					
			if(iend < 0){
				tempselect = tempselect + "," + tempname;				
			}		    	
	    }
	    tselect = tempselect + "," + deptname;
	    
	    //
		//��ѯ��
		if(null == tfrom || tfrom.equals("")){
			tfrom = tfamily + "," + "SYS_T_ORGANIZATION";
		}else{
			tfrom = tfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
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
		
		if(!tpolicy.equals("-1")){//ҵ��ѡ��	
			if("1".equals(tpolicysta)){		//�������ڱ���
				twhere += " and exists (SELECT chk.family_id "
						+ " FROM (SELECT bz.family_id FROM biz_t_optcheck bz "
						+ " WHERE bz.policy_id = '" + tpolicy + "' " 
						+ " AND ((bz.result = 2 AND bz.moneyflag = 1) "
						+ " OR (bz.moneyflag IN (2, 3, 4, 7) AND bz.result <> 8)) "
						+ " GROUP BY bz.family_id) chk, "
						+ " info_t_family fam "
						+ " WHERE fam.family_id = chk.family_id AND fam.status = '1'  "
						+ " AND fam.organization_id LIKE '" + tdept + "%' and INFO_T_FAMILY.FAMILY_ID = chk.family_id )";
			}
			/*
			if(!"0".equals(tpolicysta)){	//��ͥҵ��״̬
				//��ѯ��
				tfrom = "BIZ_T_FAMILYSTATUS," + tfrom;
				//����
				twhere = "INFO_T_FAMILY.FAMILY_ID = BIZ_T_FAMILYSTATUS.FAMILY_ID and " + twhere;
				twhere = "BIZ_T_FAMILYSTATUS.POLICY_ID = '"+tpolicy+"' and " + twhere;
				twhere = "BIZ_T_FAMILYSTATUS.FLAG = '"+tpolicysta+"' and " + twhere;
			}
			*/
		}		
		//		
		//
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 log.debug("test count 7");
		shtml.append(tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"1",torder,conn));
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
	/**
	 * ��������Ա��ѯ
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public StringBuffer GetExeMemberSql(HashMap hashmap) {
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
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		String tpolicysta = hashmap.get("tpolicysta").toString();
		/**��������**/		
		//�߼�����
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",membermasterid = "",membername = "",relmaster = "",dessaltype = "",deptid = "",deptname = ""; 
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
		
		tfield = "MEMBERNAME";//��Ա����
		membername = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield); 
		
		tfield = "PAPERID";//��Ա֤������
		membermasterid = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);	
		 		
		tfield = "RELMASTER";//�뻧����ϵ
		relmaster = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield); 
		
		tfield = "DESSALTYPE";//��������
		dessaltype = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "ORGANIZATION_ID";//��ͥ����ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//��ͥ����
		//
		//��ѯ�ֶ�
		String tempselect = id+","+familyid+","+membername+","+membermasterid+","+relmaster+","+dessaltype+","+deptid;
		//��ͥ��̬�ֶ�
		String [] selectname = tselect.split(",");
		String tempname = "";
    	int ilength = selectname.length;
	    for (int h=0;h<ilength;h++){
	    	tempname = selectname[h];	    	
	    	//
	    	int iend = tempselect.indexOf(tempname);//�Ƿ����					
			if(iend < 0){
				tempselect = tempselect + "," + tempname;				
			}		    	
	    }
	    tselect = tempselect + "," + deptname;
	    //
	    //
		//��ѯ��
		if(null == tfrom || tfrom.equals("")){
			tfrom = tfamily + "," + tmember + "," + "SYS_T_ORGANIZATION";
		}else{
			tfrom = tfrom + "," + tfamily + "," + tmember + "," + "SYS_T_ORGANIZATION";
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
		
		if(!tpolicy.equals("-1")){//ҵ��ѡ��	
			if("1".equals(tpolicysta)){		//�������ڱ���
				twhere += " and exists (SELECT chk.family_id "
						+ " FROM (SELECT bz.family_id FROM biz_t_optcheck bz "
						+ " WHERE bz.policy_id = '" + tpolicy + "' " 
						+ " AND ((bz.result = 2 AND bz.moneyflag = 1) "
						+ " OR (bz.moneyflag IN (2, 3, 4, 7) AND bz.result <> 8)) "
						+ " GROUP BY bz.family_id) chk, "
						+ " info_t_family fam "
						+ " WHERE fam.family_id = chk.family_id AND fam.status = '1'  "
						+ " AND fam.organization_id LIKE '" + tdept + "%' and INFO_T_FAMILY.FAMILY_ID = chk.family_id )";
			}
			/*  
			if(!"0".equals(tpolicysta)){
				//��ͥҵ��״̬
				//��ѯ��
				tfrom = "BIZ_T_FAMILYSTATUS," + tfrom;
				//����
				twhere = "INFO_T_FAMILY.FAMILY_ID = BIZ_T_FAMILYSTATUS.FAMILY_ID and " + twhere;
				twhere = "BIZ_T_FAMILYSTATUS.POLICY_ID = '"+tpolicy+"' and " + twhere;
				twhere = "BIZ_T_FAMILYSTATUS.FLAG = '"+tpolicysta+"' and " + twhere;
				
			}
			*/
		}		
		//
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 log.debug("test count 8");
		shtml.append(tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"1",torder,conn));
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
}
