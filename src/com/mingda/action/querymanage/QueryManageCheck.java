package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageCheckManage;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

public class QueryManageCheck {
	static Logger log = Logger.getLogger(QueryManageCheck.class);
	//常量定义
	ConstantDefine constantdefine = new ConstantDefine();
	
	/**
	 * 家庭审批查询
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StringBuffer GetExeCheckFamilySql(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		//
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
		String tautotabid = hashmap.get("tautotabid").toString();
		String tchecktabid = hashmap.get("tchecktabid").toString();
		String tchoiceflag = hashmap.get("tchoiceflag").toString();
		/**变量定义**/		
		//逻辑表处理
		String sql = "",allsql = "",xmlth = "",sumfield = "",groupby = "";
		String empth = "",stemp = "";
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
		//查询字段
		String tempselect = id+","+familyid+","+mastername;
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
	    //
	    //查询字段
	    tselect = tempselect;
	    //分组字段
	    groupby = tempselect;
	    //
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
		//业务查询
		if(null == tpolicy || tpolicy.equals("-1")){//[无]业务选择
			//
			//XML列头
			xmlth = tselect ;
		}else{			
			//XML列头
			xmlth = GetPolciyCheckXnlTh(tselect).toString();
			//查询字段
			tselect = GetPolciyCheckSelect(tselect,tpolicy,tempid).toString();
		    //
			//分组字段
		    groupby = GetPolciyCheckGroupby(groupby,tpolicy,tempid).toString();
			//查询表
			tfrom = GetPolciyCheckFrom(tfrom).toString();
			//条件
			//				
			HashMap hashmapwhere = new HashMap();
			hashmapwhere.put("twhere", twhere);
			hashmapwhere.put("tempid", tempid);
			hashmapwhere.put("tpolicy", tpolicy);
			hashmapwhere.put("tautotabid", tautotabid);
			hashmapwhere.put("tchecktabid", tchecktabid);
			//
			twhere = GetPolciyCheckWhere(hashmapwhere).toString();			
		}
		//XML列头
		xmlth = xmlth + "," + deptid + ",家庭表.所属";
		//查询字段
	    tselect = tselect + "," + deptid + "," + deptname;
	    //分组字段
	    groupby = groupby + "," + deptid + "," + deptname;
	    //
	    //处理结果
	    Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
			log.debug("test count 5");
		sql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,groupby,torder,conn).toString();
		allsql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0",groupby,torder,conn).toString();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}finally{
			try {
				if(null!=conn)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sumfield = tableinfoquery.getsumfieldfromsql(allsql,tpolicy);		
		//
		//
		//查询全部(返回SQL语句)
		if(tchoiceflag.equals("all")){
			shtml.append(allsql);
		}else if(tchoiceflag.equals("export")){
			shtml.append(xmlth+";"+allsql);			
		}else{
			shtml.append(getCheckResultForXml(xmlth,sql,sumfield,tpolicy,tempid));
		}		
		//
		//log.debug("sql:"+sql);
		//log.debug("allsql:"+allsql);
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
	
	
	
	
	/**
	 * 取得业务审批查询XML列头
	 * @param tselect
	 * @return
	 */
	public StringBuffer GetPolciyCheckXnlTh(String tselect) {
		StringBuffer shtml= new StringBuffer("");
		//
		//XML列头
		String xmlth = tselect + "," + "审批表.保障人口,审批表.计算收入,审批表.上期救助金,审批表.拟发救助金,审批表.评议结果";
		shtml.append(xmlth);
		return shtml;
	}
	/**
	 * 取得业务审批查询字段
	 * @param tselect
	 * @param tpolicy
	 * @param tempid
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer GetPolciyCheckSelect(String tselect,String tpolicy,String tempid) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",empth = "";
		//
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//添加保障人口、计算收入、上期救助金、拟发救助金(分城市、农村)
		
		String sptype = policymanagecheckquery.getPolicyObjTypeFromId(tpolicy);
    	if(sptype.equals("0")){//城市保障
    		if(tselect.equals("")||tselect==null){
    			tselect = "INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    			tselect += ",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}else{
    			tselect = tselect +","+"INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    			tselect = tselect +",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}	
    	}else if(sptype.equals("1")){//农村保障
    		if(tselect.equals("")||tselect==null){
    			tselect = "INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    			tselect += ",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}else{
    			tselect = tselect +","+"INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    			tselect = tselect +",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}	
    	}
    	//
    	
    	//    	
    	//各级审批结果和审批时间
    	empth = tableinfoquery.getempdepth(tempid);
    	//
    	if(empth.equals(constantdefine.POLICYQUERYCODE_5)){//社区
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG1";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG2";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG3";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG4";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG5";
    	}
    	tselect = tselect +","+stemp;
    	//
    	shtml.append(tselect);
		return shtml;
	}
	/**
	 * 获取分组查询语句
	 * @param tgroupby
	 * @param tpolicy
	 * @param tempid
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer GetPolciyCheckGroupby(String tgroupby,String tpolicy,String tempid) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",empth = "";
		//
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//添加保障人口、计算收入、上期救助金、拟发救助金(分城市、农村)
		
		String sptype = policymanagecheckquery.getPolicyObjTypeFromId(tpolicy);
    	if(sptype.equals("0")){//城市保障
    		if(tgroupby.equals("")||tgroupby==null){
    			tgroupby = "INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    		}else{
    			tgroupby = tgroupby +","+"INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    		}	
    	}else if(sptype.equals("1")){//农村保障
    		if(tgroupby.equals("")||tgroupby==null){
    			tgroupby = "INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    		}else{
    			tgroupby = tgroupby +","+"INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    		}	
    	}
    	//
    	
    	//    	
    	//各级审批结果和审批时间
    	empth = tableinfoquery.getempdepth(tempid);
    	//
    	if(empth.equals(constantdefine.POLICYQUERYCODE_5)){//社区
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG1";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG2";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG3";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG4";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG5";
    	}
    	tgroupby = tgroupby +","+stemp;
    	//
    	shtml.append(tgroupby);
		return shtml;
	}
	/**
	 * 取得业务审批查询表
	 * @param tfrom
	 * @return
	 */
	public StringBuffer GetPolciyCheckFrom(String tfrom) {
		StringBuffer shtml= new StringBuffer("");
		//
		//查询表
		if(null == tfrom || tfrom.equals("")){
			tfrom =  "BIZ_T_OPTCHECK";
		}else{
			tfrom =  "BIZ_T_OPTCHECK," + tfrom;
		} 
		//
    	shtml.append(tfrom);
		return shtml;
	}
	/**
	 * 取得业务审批查询条件
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public StringBuffer GetPolciyCheckWhere(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		String twhere = hashmap.get("twhere").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		String tautotabid = hashmap.get("tautotabid").toString();
		String tchecktabid = hashmap.get("tchecktabid").toString();
		//				
		String empth = "",stemp1 = "",stemp2 = "";
    	String smoneyflag = "",sresult = "",sresultflag = "",sifover,sautoflag = "";
    	
    	//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	//
    	empth = tableinfoquery.getempdepth(tempid);
    	//
    	if(empth.equals(constantdefine.POLICYQUERYCODE_5)){//社区
    		sresult = "CHECKFLAG1";    		
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_4)){//街道
    		sresult = "CHECKFLAG2";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_3)){//区县
    		sresult = "CHECKFLAG3";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_2)){//市局
    		sresult = "CHECKFLAG4";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_1)){//省厅
    		sresult = "CHECKFLAG5";
    	}
    	//
    	sifover = empth;
    	//
    	//查询条件
		if(twhere.equals("")||twhere==null){
			twhere = "BIZ_T_OPTCHECK.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID " +
					"and BIZ_T_OPTCHECK.POLICY_ID = '"+tpolicy+"' ";
		}else{
			twhere = "BIZ_T_OPTCHECK.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID " +
					"and BIZ_T_OPTCHECK.POLICY_ID = '"+tpolicy+"' and " + twhere;
		}
		
		//
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_NEWCHECK;//新增评议
		if(tautotabid.equals(stemp1)){	
			//审批进度
			twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
			//
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWNOCHECK;//新增
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}			
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWRECHECK;//重新核查
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
		}
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_OLECHECK;//在保户复审		
		if(tautotabid.equals(stemp1)){
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWSTOP;//到期待续
			if(tchecktabid.equals(stemp2)){
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_REM;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWREM;//上期暂停
			if(tchecktabid.equals(stemp2)){
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_STOP;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWTOP;//调高
			if(tchecktabid.equals(stemp2)){
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_TOP;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWBOM;//降低
			if(tchecktabid.equals(stemp2)){
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_BOM;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWOLE;//顺延
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_OLE;				
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWREMCHECK;//重新核查
			if(tchecktabid.equals(stemp2)){
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG <> '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;				
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWNOMATCH;//不达标
			if(tchecktabid.equals(stemp2)){
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_NOMATCH;//不符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;				
			}
		}
		//
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_MONEY;//拟发放名单		
		if(tautotabid.equals(stemp1)){
			stemp2 = constantdefine.POLICYNEWRESULTCODE_NEW;//新增
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_OK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_OLE;//顺延
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_OLE;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;				
			}	
			stemp2 = constantdefine.POLICYNEWRESULTCODE_TOP;//调高
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_TOP;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_OK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_BOM;//降低
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_BOM;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_OK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_MOVE;//渐退
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_MOVE;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_REM;//恢复
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_REM;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
		}
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_NOMONEY;//不发放名单	
		if(tautotabid.equals(stemp1)){
			stemp2 = constantdefine.POLICYNEWRESULTCODE_STOP;//暂停
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_STOP;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_DEL;//终止
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_NOTOK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_NOMATCH;//不达标
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_NOMATCH;//不符合标准
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_NEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_MATCHNORES;//未评议
			if(tchecktabid.equals(stemp2)){	
				//审批进度
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_OLE;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG <> '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_NEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			
		}
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_REMCHECK;//重新核查名单	
		if(tautotabid.equals(stemp1)){			
			stemp2 = constantdefine.POLICYNEWRESULTCODE_REMCHECK;//重新核查
			if(tchecktabid.equals(stemp2)){					
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
		}
		//
		shtml.append(twhere);
		//
		return shtml;
	}
	
	/**
     * 审批XML
     * 由列名称
     * 查询物理SQL语句
     * SQL记录数
     * 生成XML
     * XML第一列:操作项(0可操作1不可操作)
     * XML第二列:家庭ID(必须数据库查询)
     * @param tselect
     * @param sql
     * @param sumfield
     * @param spid
     * @param empid
     * @return
     */
    @SuppressWarnings("unused")
	public String getCheckResultForXml(String txmlth,String sql,String sumfield,String spid,String empid) {
    	String srep = "-1";//无查询结果或者错误
    	String sqlresultcount = "0",sumpopcount ="0",summoney = "0",sumolemoney = "0",sumnewmoney = "0";
    		
		//政策审批处理类
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();		
		
		//
	    String columname = txmlth;
	    
	    //添加家庭户数、保障人口、计算收入、上期救助金、拟发救助金(分城市、农村)
	    String[] asumfield = sumfield.split(","); 
	    if(asumfield.length>1){
	    	sqlresultcount = asumfield[0];
	 	    sumpopcount = asumfield[1];
	 		summoney = asumfield[2];
	 		sumolemoney = asumfield[3];
	 		sumnewmoney = asumfield[4];	 
	    }
	    //
        String[] columu_name = null;      
        int columnCount =0;       
        //
        columu_name = columname.split(",");
        columnCount = columu_name.length;
        
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //
        //表格记录总数
        Element eCount=data.addElement("count");
        Element eCountChild =eCount.addElement("num");
        eCountChild.setText(sqlresultcount); 
        //表格保障人口总数
        Element eSumPopcount=data.addElement("sumpopcount");
        Element eSumeSumPopcountChild =eSumPopcount.addElement("count");
        eSumeSumPopcountChild.setText(sumpopcount); 
        //表格计算收入汇总
        Element eSumMoney=data.addElement("summoney");
        Element eSumMoneyChild =eSumMoney.addElement("money");
        eSumMoneyChild.setText(summoney);         
        //
        //表格上期救助金汇总
        Element eSumOleMoney=data.addElement("sumolemoney");
        Element eSumOleMoneyChild =eSumOleMoney.addElement("money");
        eSumOleMoneyChild.setText(sumolemoney);
        //表格拟发救助金汇总
        Element eSumNewMoney=data.addElement("sumnewmoney");
        Element eSumNewMoneyChild =eSumNewMoney.addElement("money");
        eSumNewMoneyChild.setText(sumnewmoney); 
        //
        
        //
        //log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //
            //表格列
            Element eHead=data.addElement("ehead");           
            
            //
            for (int i = 0; i < columnCount; i++) {              
            	//查询字段列
            	//
          	  	Element eFHeadChild =eHead.addElement("cell");
                eFHeadChild.setText(columu_name[i]);
                eFHeadChild.attributeValue("id",columu_name[i]);             
            }
            //表格实体
            Element datas=data.addElement("list");
            String tfmid = "",sflag = "";
            while (rs.next()) {             
            	Element entity=datas.addElement("entity");
              	tfmid = rs.getString(1);// 家庭ID
              	//
	          	//家庭信息
	          	//XML第一列:家庭ID
              	for (int i = 0; i < columnCount; i++) {
              		//
              		if(i==columnCount-3){
            			//审批结果转换            		
            			Element col=entity.addElement("col");
    	            	String tname = rs.getString(i+1);                
    	            	if(tname==null || tname.length()<=0){
    	            		col.setText("未评议");                  
    	            	}else{
    	            		//
    	            		tname = policymanagecheckmanage.repalcePolicyCheckFlag(tname);
    	            		col.setText(tname);                    
    	            	}  
            		}else{
            			Element col=entity.addElement("col");
    	            	String tname = rs.getString(i+1);                
    	            	if(tname==null || tname.length()<=0){
    	            		col.setText("0");                  
    	            	}else{
    	            		col.setText(tname);                    
    	            	}  
            		}            	 
              	}
              	//
	          	//可以撤消审批意见标识(审批进度在本级或者上级未评议或者重新核查均可撤消审批意见重审)            		
	      		Element col=entity.addElement("col");
	      		String tflag = policymanagecheckmanage.getPolicyIsRmCheckFlag(empid, spid, tfmid);
	      		col.setText(tflag); 
            }
            //
            Node node=  root.selectSingleNode("/root/data");
            //log.debug(node.asXML()+"   :xiuxiuxiuxiux:    "+doc.asXML());
            srep = node.asXML();
            //
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //        
        return srep;
    }
}
