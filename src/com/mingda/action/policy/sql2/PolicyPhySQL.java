package com.mingda.action.policy.sql2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;

public class PolicyPhySQL {
	//常量定义
	static Logger log = Logger.getLogger(PolicyPhySQL.class);
	ConstantDefine constantdefine = new ConstantDefine();
	
	public HashMap getCheckPolicyPhySql(HashMap hashmap,Connection conn) {
		HashMap rephashmap = new HashMap();
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		//
		/**通用的SQL解析参数**/
		String jmode = hashmap.get("jmode").toString();
		String jselect = hashmap.get("jselect").toString();
		String jfrom = hashmap.get("jfrom").toString();
		String jwhere = hashmap.get("jwhere").toString();
		String jbegpage = hashmap.get("jbegpage").toString();
		String jendpage = hashmap.get("jendpage").toString();
		//
		String jorder = hashmap.get("jorder").toString();
		/**特殊的处理参数**/
		String jdeptid = hashmap.get("jdeptid").toString();		
		String jdepth = hashmap.get("jdepth").toString();	
		String jempid = hashmap.get("jempid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jtabid = hashmap.get("jtabid").toString();
		//
		String jtabquery = hashmap.get("jtabquery").toString();
		String jtabidea = hashmap.get("jtabidea").toString();
		//
		String jfmno = hashmap.get("jfmno").toString();
		String jfmname = hashmap.get("jfmname").toString();
		String jfmlike = hashmap.get("jfmlike").toString();
		//
		
		/**变量定义**/		
		//逻辑表处理
		String sql = "",groupby = "",stemp = "",sxmlth = "";
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",mastername = "",ensurecount = "",consultincome = "",deptid = "",deptname = ""; 
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
		
		tfield = "ENSURECOUNT";//保障人口
		ensurecount = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "CONSULTINCOME";//计算收入
		consultincome = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);   
				
		tfield = "ORGANIZATION_ID";//家庭机构ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//家庭机构
		//
		//查询字段
		//家庭ID,家庭编号,户主姓名,保障人口,计算收入
		String tempselect = id+","+familyid+","+mastername + "," +ensurecount + "," + consultincome;
		//家庭动态字段
		if(null != jselect){
			String [] selectname = jselect.split(",");
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
		}		
	    //=================================================================================================默认查询BEG
	    //查询字段=======================
	    jselect = tempselect;
	    //
		//查询表=========================	    
		if(null == jfrom || jfrom.equals("")){	//======表名重复在处理物理SQL语句时会处理掉的
			jfrom = tfamily + "," + "SYS_T_ORGANIZATION";
		}else{
			jfrom = jfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
		} 
		//查询条件======================
		//查询条件
		if(null == jwhere || jwhere.equals("")){				
			
		}else{
			jwhere += "  and ";
		}
		//带机构查询
		jwhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";//带机构查询关系
		//
		String temp1 = jwhere.substring(0,jwhere.length()); 
		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");//组织机构逻辑名称    		
		int iend = temp1.indexOf(temp2);//是否存在组织机构					
		if(iend < 0){
			jwhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+jdeptid+"%'";
		}	
		//家庭在用状态
		jwhere += " and INFO_T_FAMILY.STATUS = '1'";
	    //
	    //=================================================================================================默认查询END
	    //
	    String smoneyflag = "BIZ_T_OPTCHECK.MONEYFLAG";			//救助状态
	    //
	    String scheckflow = constantdefine.POLICYQUERYCODE_5;	//默认社区
    	String scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG5";		//默认社区
    	//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //社区
    		scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG1";
    		scheckflow = constantdefine.POLICYQUERYCODE_5;
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG2";
    		scheckflow = constantdefine.POLICYQUERYCODE_4;
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG3";
    		scheckflow = constantdefine.POLICYQUERYCODE_3;
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG4";
    		scheckflow = constantdefine.POLICYQUERYCODE_2;
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		scheckflag = "BIZ_T_OPTCHECK.CHECKFLAG5";
    		scheckflow = constantdefine.POLICYQUERYCODE_1;
    	}	    
		//
    	String ssaltype = "SALTYPE";		//保障类型
    	String sischang = "ISCHANGE";  		//信息变化标识
	    //=================================================================================================业务查询BEG
    	//XML头描述======================
    	sxmlth = jselect;
    	sxmlth += ",家庭表.保障类型";
    	sxmlth += ",审批表.上期救助金";
    	sxmlth += ",审批表.拟发救助金";
    	sxmlth += ",审批表.救助状态";
    	sxmlth += ",审批表.评议结果";
    	sxmlth += ",审批表.所属";
		sxmlth += ",审批表.所属ID";
		sxmlth += ",家庭表.信息变化标识";
		//
	    //查询字段=====================================
		jselect += ","+ssaltype; 	//保障类型
		jselect += ",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY";	
		jselect += ",SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
		jselect += ","+smoneyflag; 	//救助状态
    	jselect += ","+scheckflag; 	//各级审批结果
    	jselect += "," + deptname;	//机构name
    	jselect += "," + deptid;	//机构id  
    	jselect += "," + sischang;	//信息变化标识
		//查询表========================================
		jfrom  += ",BIZ_T_OPTCHECK ";
		//查询条件======================================
		jwhere += " and INFO_T_FAMILY.FAMILY_ID = BIZ_T_OPTCHECK.FAMILY_ID and BIZ_T_OPTCHECK.POLICY_ID = '"+jpid+"' ";
		//
		
		//MONEYFLAG 1新增2顺延3调高4降低5待续6暂停7不达标
		//MONEYFLAG 审批意见:1未审批2同意救助3重新审批4 5 6 7 8取消救助9不同意救助
		//==================================tab========================================
		if(jtabid.equals("0")){		//查询标签新增审批
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG = '"+constantdefine.POLICYCHECKMONEYCODE_NEW+"' " 
				+ "and "+scheckflag +" = '"+constantdefine.POLICYCHECKCODE_NEW+"' ";
			jwhere += " and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		}else if(jtabid.equals("1")){//查询标签调整复查
			jwhere += " and (BIZ_T_OPTCHECK.MONEYFLAG = '"+constantdefine.POLICYCHECKMONEYCODE_TOP + "' " 
				+ "or BIZ_T_OPTCHECK.MONEYFLAG = '"+constantdefine.POLICYCHECKMONEYCODE_BOM + "') " 
				+ "and "+scheckflag +" = '"+constantdefine.POLICYCHECKCODE_NEW+"' ";
			jwhere += " and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		}else if(jtabid.equals("2")){//查询标签不达标准
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG = '"+constantdefine.POLICYCHECKMONEYCODE_NOT + "' ";
			jwhere += " and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		}else if(jtabid.equals("3")){//查询标签重新审批
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG <> '"+constantdefine.POLICYCHECKMONEYCODE_NOT + "' " 
				+ "and "+scheckflag +" = '"+constantdefine.POLICYCHECKCODE_RENEW+"' ";
			jwhere += " and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "'";
		}else if(jtabid.equals("4")){//查询标签上期顺延
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG = '"+constantdefine.POLICYCHECKMONEYCODE_OLE+"' ";
		}else if(jtabid.equals("5")){//查询标签已审名单
			jwhere += " and "+scheckflag +" <> '"+constantdefine.POLICYCHECKCODE_NEW+"' " 
				+ " and  BIZ_T_OPTCHECK.MONEYFLAG <> '"+constantdefine.POLICYCHECKMONEYCODE_OLE +"'";
		}else if(jtabid.equals("6")){//查询标签全部名单
//			jwhere += " and ((BIZ_T_OPTCHECK.MONEYFLAG <> '"+constantdefine.POLICYCHECKMONEYCODE_OLE+"' " 
//			+ "and BIZ_T_OPTCHECK.IFOVER = '" + scheckflow + "') " 
//			+ "or BIZ_T_OPTCHECK.MONEYFLAG = '"+constantdefine.POLICYCHECKMONEYCODE_OLE +"') ";
		}
		//================================tab==========================================
		//================================tabitems=====================================
		//过滤查询
		if(!"".equals(jfmno)){
			jwhere += " and INFO_T_FAMILY.FAMILYNO = '"+jfmno+"'";
		}
		if(!"".equals(jfmname)){
			if("1".equals(jfmlike)){
				jwhere += " and INFO_T_FAMILY.MASTERNAME like '%"+jfmname+"%'";
			}else{
				jwhere += " and INFO_T_FAMILY.MASTERNAME = '"+jfmname+"'";
			}			
		}
		//救助状态
		if(!"0".equals(jtabquery)){//0全部;1新增;2调高;3顺延;4降低;7不达标;
			jwhere += " and BIZ_T_OPTCHECK.MONEYFLAG = '"+jtabquery+"' ";
		}
		//审批结果
		if(!"0".equals(jtabidea)){//0全部;1未审批;2同意救助;3不同意救助;8重新审批;9取消救助
			jwhere += " and "+scheckflag + " = '"+jtabidea+"' ";
		}		
		//================================tabitems=====================================		
		//分组字段=======================
	    groupby = tempselect;
	    groupby += "," + ssaltype;
	    groupby += ",BIZ_T_OPTCHECK.RECHECKMONEY";
	    groupby += ", BIZ_T_OPTCHECK.COUNTMONEY";
	    groupby += "," + smoneyflag;
	    groupby += "," + scheckflag;	    
	    groupby += "," + deptname;
	    groupby += "," + deptid;
	    groupby += "," + sischang;
	    //=================================================================================================业务查询END	    
	    //综合SQL语句
	    log.debug("test count 6");
	    sql = tableinfophysql.getphysql(jselect, jfrom, jwhere,jmode,jbegpage,jendpage,groupby,jorder,conn).toString();
	    //
	    //汇总信息===================
	    String countsql = tableinfophysql.getphysql(jselect, jfrom, jwhere,jmode,"0","0",groupby,jorder,conn).toString();
	    HashMap hmcount = getCheckCountFromSQL(countsql);
	    String sqlcount = hmcount.get("sqlcount").toString();
	    String sumpopcount = hmcount.get("sumpopcount").toString();
	    String summoney = hmcount.get("summoney").toString();
	    String sumolemoney = hmcount.get("sumolemoney").toString();
	    String sumnewmoney = hmcount.get("sumnewmoney").toString();
	    //
	    //log.debug("sql:" + sql);
	    //log.debug("countsql:" + countsql);
	    //返回值=====================
	    rephashmap.put("sql", sql.toString());
	    rephashmap.put("countsql", countsql.toString());
	    rephashmap.put("xmlth", sxmlth.toString());
	    //
	    rephashmap.put("sqlcount", sqlcount.toString());
	    rephashmap.put("sumpopcount", sumpopcount.toString());
	    rephashmap.put("summoney", summoney.toString());
	    rephashmap.put("sumolemoney", sumolemoney.toString());
	    rephashmap.put("sumnewmoney", sumnewmoney.toString());
	    //
		return rephashmap;
	}
	
	/**
	 * 获取汇总信息项目
	 * @param sql
	 * @return
	 */
	public HashMap getCheckCountFromSQL(String sql){
		HashMap hashmap = new HashMap();
    	String sptype = "",stemp = "";
    	boolean brow = false;
    	String sqlcount = "0",sumpopcount ="0",summoney = "0",sumolemoney = "0",sumnewmoney = "0";
    	//添加保障人口、计算收入、上期救助金、拟发救助金
    	stemp = "count(*) ca,sum(ENSURECOUNT) cb,sum(CONSULTINCOME) cc,sum(RECHECKMONEY) cd,sum(COUNTMONEY) ce";
    	sql = "select "+stemp+" from (" +sql + ")";   //定义SQL语句 
    	//log.debug("sql:"+sql);
    	//Log4jApp.logger("sql:"+sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
		    //设置参数
		    rs = pstmt.executeQuery();
		    while (rs.next()) {	 
		    	
		    	stemp = rs.getString(1);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sqlcount", stemp);
		    	//
		    	stemp = rs.getString(2);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumpopcount", stemp);
		    	//
		    	stemp = rs.getString(3);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("summoney", stemp);
		    	//
		    	stemp = rs.getString(4);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumolemoney", stemp);
		    	//
		    	stemp = rs.getString(5);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumnewmoney", stemp);
		    	//
		    	brow = true;
		    }
		} catch (SQLException e) {
		    Log4jApp.logger(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		//无记录处理
    	if(!brow){
    		hashmap.put("sqlcount", 0);
    		hashmap.put("sumpopcount", 0);
    		hashmap.put("summoney", 0);
    		hashmap.put("sumolemoney", 0);
    		hashmap.put("sumnewmoney", 0);
    	}
    	return hashmap;    	
    }
	/**
	 * 获取汇总信息项目
	 * @param sql
	 * @return
	 */
	public HashMap getIdeaCountFromSQL(String sql){
		HashMap hashmap = new HashMap();
    	String stemp = "";
    	boolean brow = false;
    	String sqlcount = "0",sumolemoney = "0",sumnewmoney = "0";
    	//添加保障人口、计算收入、上期救助金、拟发救助金
    	stemp = "count(*) ca,sum(recheckmoney) cd,sum(checkmoney) ce";
    	sql = "select "+stemp+" from (" +sql + ")";   //定义SQL语句 
    	//log.debug("sql:"+sql);
    	//Log4jApp.logger("sql:"+sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
		    //设置参数
		    rs = pstmt.executeQuery();
		    while (rs.next()) {	 
		    	
		    	stemp = rs.getString(1);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sqlcount", stemp);
		    	//
		    	stemp = rs.getString(2);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumolemoney", stemp);
		    	//
		    	stemp = rs.getString(3);
		    	if(null == stemp){stemp = "0";}
		    	hashmap.put("sumnewmoney", stemp);
		    	//
		    	brow = true;
		    }
		} catch (SQLException e) {
		    Log4jApp.logger(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		//无记录处理
    	if(!brow){
    		hashmap.put("sqlcount", 0);
    		hashmap.put("sumolemoney", 0);
    		hashmap.put("sumnewmoney", 0);
    	}
    	return hashmap;    	
    }
}
