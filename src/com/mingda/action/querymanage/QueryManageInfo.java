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
	//常量定义
	static Logger log = Logger.getLogger(QueryManageInfo.class);
	ConstantDefine constantdefine = new ConstantDefine();
	
	/**
	 * 含机构和业务家庭查询
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public StringBuffer GetExeFamilySql(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		/**通用的SQL解析参数**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**特殊的处理参数**/
		String tdept = hashmap.get("tdept").toString();
		String torder = hashmap.get("torder").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		String tpolicysta = hashmap.get("tpolicysta").toString();
		/**变量定义**/		
		//逻辑表处理
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",masterid = "",mastername = "",salmoney = "",population = "",dessaltype = "",deptid = "",deptname = ""; 
		//家庭信息
		tftable = "INFO_T_FAMILY";//家庭表    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//成员信息
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		
		tfield = "FAMILY_ID";//家庭ID
		id = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "FAMILYNO";//家庭编号
		familyid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MASTERNAME";//户主姓名
		mastername = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "MASTERPAPERID";//户主证件号码
		masterid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "POPULATION";//家庭人口
		population = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);  
		
		tfield = "SALMONEY";//保障金额
		salmoney = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "DESSALTYPE";//保障类型
		dessaltype = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "ORGANIZATION_ID";//家庭机构ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//家庭机构
		//
		//查询字段
		String tempselect = id+","+familyid+","+mastername+","+masterid+","+population+","+salmoney+","+dessaltype+","+deptid;
		//家庭动态字段
		String [] selectname = tselect.split(",");
		String tempname = "";
    	int ilength = selectname.length;
	    for (int h=0;h<ilength;h++){
	    	tempname = selectname[h];	    	
	    	//
	    	int iend = tempselect.indexOf(tempname);//是否存在					
			if(iend < 0){
				tempselect = tempselect + "," + tempname;				
			}		    	
	    }
	    tselect = tempselect + "," + deptname;
	    
	    //
		//查询表
		if(null == tfrom || tfrom.equals("")){
			tfrom = tfamily + "," + "SYS_T_ORGANIZATION";
		}else{
			tfrom = tfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
		} 
		//查询条件
		if(null == twhere || twhere.equals("")){				
			
		}else{
			twhere += "  and ";
		}
		//带机构查询
		twhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
		//带机构查询
		String temp1 = twhere.substring(0,twhere.length());  
		//组织机构逻辑名称
		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
		int iend = temp1.indexOf(temp2);//是否存在组织机构					
		if(iend < 0){
			twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
		}	
		//家庭在用状态
		twhere += " and INFO_T_FAMILY.STATUS = '1'";
		
		if(!tpolicy.equals("-1")){//业务选择	
			if("1".equals(tpolicysta)){		//审批中在保户
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
			if(!"0".equals(tpolicysta)){	//家庭业务状态
				//查询表
				tfrom = "BIZ_T_FAMILYSTATUS," + tfrom;
				//条件
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
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
	/**
	 * 含机构成员查询
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public StringBuffer GetExeMemberSql(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		/**通用的SQL解析参数**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**特殊的处理参数**/
		String tdept = hashmap.get("tdept").toString();
		String torder = hashmap.get("torder").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		String tpolicysta = hashmap.get("tpolicysta").toString();
		/**变量定义**/		
		//逻辑表处理
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",membermasterid = "",membername = "",relmaster = "",dessaltype = "",deptid = "",deptname = ""; 
		//家庭信息
		tftable = "INFO_T_FAMILY";//家庭表    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//成员信息
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		
		tfield = "FAMILY_ID";//家庭ID
		id = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "FAMILYNO";//家庭编号
		familyid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MEMBERNAME";//成员名称
		membername = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield); 
		
		tfield = "PAPERID";//成员证件号码
		membermasterid = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);	
		 		
		tfield = "RELMASTER";//与户主关系
		relmaster = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield); 
		
		tfield = "DESSALTYPE";//保障类型
		dessaltype = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "ORGANIZATION_ID";//家庭机构ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//家庭机构
		//
		//查询字段
		String tempselect = id+","+familyid+","+membername+","+membermasterid+","+relmaster+","+dessaltype+","+deptid;
		//家庭动态字段
		String [] selectname = tselect.split(",");
		String tempname = "";
    	int ilength = selectname.length;
	    for (int h=0;h<ilength;h++){
	    	tempname = selectname[h];	    	
	    	//
	    	int iend = tempselect.indexOf(tempname);//是否存在					
			if(iend < 0){
				tempselect = tempselect + "," + tempname;				
			}		    	
	    }
	    tselect = tempselect + "," + deptname;
	    //
	    //
		//查询表
		if(null == tfrom || tfrom.equals("")){
			tfrom = tfamily + "," + tmember + "," + "SYS_T_ORGANIZATION";
		}else{
			tfrom = tfrom + "," + tfamily + "," + tmember + "," + "SYS_T_ORGANIZATION";
		} 
		//查询条件
		if(null == twhere || twhere.equals("")){				
			
		}else{
			twhere += "  and ";
		}
		//带机构查询
		twhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
		//带机构查询
		String temp1 = twhere.substring(0,twhere.length());  
		//组织机构逻辑名称
		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
		int iend = temp1.indexOf(temp2);//是否存在组织机构					
		if(iend < 0){
			twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
		}	
		//家庭在用状态
		twhere += " and INFO_T_FAMILY.STATUS = '1'";
		
		if(!tpolicy.equals("-1")){//业务选择	
			if("1".equals(tpolicysta)){		//审批中在保户
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
				//家庭业务状态
				//查询表
				tfrom = "BIZ_T_FAMILYSTATUS," + tfrom;
				//条件
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
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
}
