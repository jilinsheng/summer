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
	 * 走访调查查询
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
		String ttabid = hashmap.get("ttabid").toString();
		String tbegdt = hashmap.get("tbegdt").toString();
		String tenddt = hashmap.get("tenddt").toString();
		//
		/**变量定义**/
		//XML处理
		String xmlth = "",sql = "",countsql ="",countnum = "0";
		//逻辑表处理
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",masterid = "",mastername = "",population = "",deptid = "",deptname = ""; 
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
		
		tfield = "ORGANIZATION_ID";//家庭机构ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//家庭机构
		//
		//
		if(ttabid.equals("1")){//走访登记
			//查询字段
			tselect = id+","+familyid+","+mastername+","+masterid+","+population+","+deptid + "," + deptname;
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
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
			//XML列头
			xmlth = tselect;
			 log.debug("test count 12");
			countsql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1",torder,conn).toString();
			countnum = tableinfoquery.getresultcountfromsql(countsql);
			//
		}else if(ttabid.equals("2")){//走访查询
			//家庭走访记录表
			String viewid = "BIZ_T_INTERVIEW.INTERVIEW_ID";
			String viewdt = "to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd')";	
			String viewresult = "BIZ_T_INTERVIEW.RESULT";		
			String viewperson = "BIZ_T_INTERVIEW.PERSON";
			String viewdeptname = "SYS_T_ORGANIZATION.FULLNAME";
			//查询字段
			tselect = id+","+familyid+","+mastername+ ","+viewid+","+viewdt+","+viewresult+","+viewperson+","+viewdeptname;
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = "BIZ_T_INTERVIEW" + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + "BIZ_T_INTERVIEW" + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
			//查询条件
			if(null == twhere || twhere.equals("")){				
				
			}else{
				twhere += "  and ";
			}
			//
			//带机构查询
			twhere += " BIZ_T_INTERVIEW.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
			//带机构查询
    		String temp1 = twhere.substring(0,twhere.length());  
    		//组织机构逻辑名称
    		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
			int iend = temp1.indexOf(temp2);//是否存在组织机构					
			if(iend < 0){
				twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
			}	
			twhere += " and BIZ_T_INTERVIEW.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID ";
			//走访日期
			if(!tbegdt.equals("") && !tenddt.equals("")){//日期查询
				//
				twhere += " and to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd') >=  '"+tbegdt+"'";
	      		//
				twhere += " and  to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd') <=  '"+tenddt+"'";
		    	
			}
			//家庭在用状态
			twhere += " and INFO_T_FAMILY.STATUS = '1'";
			//XML列头
			xmlth = id+","+familyid+","+mastername+","+"走访记录表.走访ID,走访记录表.走访日期,走访记录表.走访内容,走访记录.走访人,机构表.走访机构";
			
			countsql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1","to_char(BIZ_T_INTERVIEW.VIEWDT,'yyyy-mm-dd') desc" ,conn).toString();
			countnum = tableinfoquery.getresultcountfromsql(countsql);
		}
		//
		sql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"1",torder,conn).toString();
		
		//
		
		shtml.append(tableinfoquery.getInfoResultForXml(xmlth,sql,countnum));
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
}
