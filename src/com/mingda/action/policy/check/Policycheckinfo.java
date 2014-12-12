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
 * 政策业务
 * 符合条件处理
 * 符合标准处理
 * @author xiu
 *
 */
public class Policycheckinfo {
	static Logger log = Logger.getLogger(Policycheckinfo.class);
	/**
	 * 符合业务标准
	 * 家庭或者成员
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String SetNewPolciyMatchMore(String pid,String empid) {		
		String srep = "";
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批查询处理
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();	
		//
		log.debug(System.currentTimeMillis());
		//第一级审批机构
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(pid);
		String empdepth = tableinfoquery.getempdepth(empid);
		if(!empdepth.equals(onedepth)){
			//不是第一级审批机构
			srep = "登录机构不是业务第一级审批机构,无法生成名单!";
			return srep;
		}
		//业务是否已经终审结算标识
        String isallaccflag = policymanagecheckmanage.getPolicyAllAccFlag(pid,empid);
        if(isallaccflag.equals("1")){//已经终审结算
        	srep = "业务已经终审结算,无法计算拟发金额!";
    		return srep;
        }else if(isallaccflag.equals("2")){
        	srep = "业务未办理,无法计算拟发金额!";
    		return srep;
        }
		//用户机构
        String deptid = tableinfoquery.getempdepid(empid);
    	
	    //新增审批表
        AddCheckMore(pid,empid,onedepth,deptid);
        //生成分类施保审批表
        AddCheckChildMore(pid,deptid);
		//更新业务审批符合标准标识
        UpdateCheckMatchSqlFlag(pid,deptid);
        
        
        
        log.debug(System.currentTimeMillis());

        //
        return srep;
    } 
	/**
	 * 新增审批表
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
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();		
		//需要计算救助金标识
		accmode = policymanagecheckmanage.getPolicyUserAccFlag(pid);
		//业务核算类型
		acctype = policymanagecheckmanage.getPolicyAccTypeFlag(pid);
		//新增家庭
		moneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
		//符合标准
		moneyaout = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;
		//
		int ibeg=0,iend=0;
		//
		/****************
		accmode = "0";
		秀测试
		*****************/
		accmode = "0";
		
		if("1".equals(accmode)){
			//不需要计算救助金
			sql = "select physql,locsql from doper_t_standard " +
						"where flag = '1' and policy_id = '"+pid+"' " +
							"order by planmoney desc";   //定义SQL语句
			//log.debug(sql);
	        Connection conn = null;                 //声明Connection对象
	        PreparedStatement pstmt = null;         //声明PreparedStatement对象
	        ResultSet rs = null;                    //声明ResultSet对象
	        try {
	            conn = DBUtils.getConnection();     //获取数据库连接
	            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {
	            	locsql = rs.getString("locsql"); 
	            	physql = rs.getString("physql"); 
	        		ibeg = physql.indexOf("from");//第一次出现位置
	            	iend = physql.indexOf("where");//第一次出现位置
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
	        		//====================================生成标准和核算SQL语句BEG==================================//
            	    tempselect = tselect;
            	    tempfrom = "INFO_T_MEMBER,INFO_T_FAMILY";
            	    tempwhere = "INFO_T_MEMBER.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID";
            		//档次查询表
            	    String snewtflag = "F";
            	    String [] asfrom = tfrom.split(",");
            	    int itflen = asfrom.length;	    
            	    for (int y=0;y<itflen;y++){
            	    	String sfrom = asfrom[y].trim();
            	    	//防止表名有相同特征INFO_T_FAMILYCLASS,INFO_T_FAMILY
            	    	//相同特征为INFO_T_FAMILY
            	    	String [] anewfrom = tempfrom.split(",");
            	    	int inewtlen = anewfrom.length;
            	    	//初始化
            	    	snewtflag = "F";
            	    	for (int j=0;j<inewtlen;j++){	    		
            	    		String stfrom = anewfrom[j].trim();
            	    		if(stfrom.equals(sfrom)){
            	    			snewtflag = "T";
            	    		}
            	    	}
            	    	//不存在相同
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
   
            	    //====================================生成标准和核算SQL语句END==================================//  
            	    //====================================生成审批表SQL语句BEG==================================//
            	    //审批表(家庭核算)
                	String sqlfamily = "select a.family_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//审批表(成员核算)
                	String sqlmember = "select a.family_id,a.member_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//
            	    //生成新审批名单SQL
	            	if("0".equals(acctype)){
	            		//生成审批表SQL语句
	            		tempwhere += " and INFO_T_MEMBER.MEMBER_ID = INFO_T_FAMILY.MASTERID ";
	            	    matchsql = tempselect +",INFO_T_FAMILY.MASTERID,xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += " and not exists (" + sqlfamily + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	    				//家庭核算	            	    
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;	            		
	    			}else{	    				
	    				//生成审批表SQL语句
	            	    matchsql = tempselect +",xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += "  and not exists (" + sqlmember + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	            	    //成员核算
	            	    newsql = "insert into biz_t_optcheck " +
        				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;
	    			}
	            	//log.debug("matchsql:"+matchsql);
	            	//log.debug("newsql:"+newsql);
	            	//批量生成审批表
	            	ExeSQLFromUserSQL(newsql);
	            	//====================================生成审批表SQL语句END==================================//
	            	//==================================================生成标准语句表BEG==============================//
	            	//批量生成审批表符合标准
	            	//单引号转义去掉左右空格
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
	            	//生成审批表标准SQL记录
					ExeSQLFromUserSQL(newsql);
	            	//==================================================生成标准语句表END==============================//
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //关闭结果集
	            DBUtils.close(pstmt);               //关闭PreparedStatement
	            //DBUtils.close(conn);                //关闭连接
	        }            
		}else{
			//需要计算救助金
			sql = "select a.accexpphysql,a.accexplocsql,b.physql,b.locsql from doper_t_standarddept a, doper_t_standard b " +
					"where a.standard_id = b.standard_id and a.flag = '1' and instr('#' || '"+ deptid +"', '#' || organization_id) > 0 " +
					"and policy_id = '"+ pid +"' and accexpphysql is not null " +
					"order by b.planmoney desc";
			//
			
	        //log.debug(sql);
	        Connection conn = null;                 //声明Connection对象
	        PreparedStatement pstmt = null;         //声明PreparedStatement对象
	        ResultSet rs = null;                    //声明ResultSet对象
	        try {
	            conn = DBUtils.getConnection();     //获取数据库连接
	            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {	            	
	        		//标准SQL
	            	locsql = rs.getString("locsql"); 
	            	acclocsql  = rs.getString("accexplocsql"); 
	            	//
	        		physql = rs.getString("physql"); 
	        		ibeg = physql.indexOf("from");//第一次出现位置
	            	iend = physql.indexOf("where");//第一次出现位置
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
	        		//核算公式SQL
	            	accphysql = rs.getString("accexpphysql"); 	            	
	            	ibeg = accphysql.indexOf("from");//第一次出现位置
	            	iend = accphysql.indexOf("where");//第一次出现位置
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
	        		
	        		//====================================生成标准和核算SQL语句BEG==================================//
            	    tempselect = aselect;
            	    tempfrom = "INFO_T_MEMBER,INFO_T_FAMILY";
            	    tempwhere = "INFO_T_MEMBER.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID";
            		//档次查询表
            	    String snewtflag = "F";
            	    String [] asfrom = tfrom.split(",");
            	    int itflen = asfrom.length;	    
            	    for (int y=0;y<itflen;y++){
            	    	String sfrom = asfrom[y].trim();
            	    	//防止表名有相同特征INFO_T_FAMILYCLASS,INFO_T_FAMILY
            	    	//相同特征为INFO_T_FAMILY
            	    	String [] anewfrom = tempfrom.split(",");
            	    	int inewtlen = anewfrom.length;
            	    	//初始化
            	    	snewtflag = "F";
            	    	for (int j=0;j<inewtlen;j++){	    		
            	    		String stfrom = anewfrom[j].trim();
            	    		if(stfrom.equals(sfrom)){
            	    			snewtflag = "T";
            	    		}
            	    	}
            	    	//不存在相同
            	    	if(snewtflag.equals("F")){
            	    		if(tempfrom.equals("")){
            	    			tempfrom = sfrom;
                			}else{
                				tempfrom += "," + sfrom;
                			}
            	    	}	    	
            	    }
            	    //核算查询表
            	    snewtflag = "F";
            	    asfrom = afrom.split(",");
            	    itflen = asfrom.length;	    
            	    for (int y=0;y<itflen;y++){
            	    	String sfrom = asfrom[y].trim();;
            	    	//防止表名有相同特征INFO_T_FAMILYCLASS,INFO_T_FAMILY
            	    	//相同特征为INFO_T_FAMILY
            	    	String [] anewfrom = tempfrom.split(",");
            	    	int inewtlen = anewfrom.length;
            	    	//初始化
            	    	snewtflag = "F";
            	    	for (int j=0;j<inewtlen;j++){	    		
            	    		String stfrom = anewfrom[j].trim();
            	    		if(stfrom.equals(sfrom)){
            	    			snewtflag = "T";
            	    		}
            	    	}
            	    	//不存在相同
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
            	    //====================================生成标准和核算SQL语句END==================================//  
            	    //====================================生成审批表SQL语句BEG==================================//
            	    //审批表(家庭核算)
                	String sqlfamily = "select a.family_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//审批表(成员核算)
                	String sqlmember = "select a.family_id,a.member_id " +
                			"from biz_t_optcheck a,info_t_family b " +
                			"where a.family_id = b.family_id and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%'";
                	//
            	    //生成新审批名单SQL
	            	if("0".equals(acctype)){
	            		//生成审批表SQL语句
	            		tempwhere += " and INFO_T_MEMBER.MEMBER_ID = INFO_T_FAMILY.MASTERID ";
	            	    matchsql = tempselect +",INFO_T_FAMILY.MASTERID,xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += " and not exists (" + sqlfamily + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	    				//家庭核算
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,checkmoney,member_id,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;
	    			}else{	    				
	    				//生成审批表SQL语句
	            	    matchsql = tempselect +",xoptcheck_id.nextval,'"+pid+"','"+acctype+"','"+moneyflag+"','"+moneyaout+"','"+onedepth+"','"+empid+"',sysdate,'0'" + " from " + tempfrom  + " where " + tempwhere;
	            	    matchsql += "  and not exists (" + sqlmember + " and INFO_T_FAMILY.FAMILY_ID = b.family_id)";
	            	    //成员核算
	            	    newsql = "insert into biz_t_optcheck " +
        				"(family_id,member_id,checkmoney,optcheck_id,policy_id,acctype,moneyflag,moneyaout,ifover,optoper,optdt,matchsqlflag) " + matchsql ;
	    			}
	            	//log.debug("matchsql:"+matchsql);
	            	//log.debug("newsql:"+newsql);
	            	//批量生成审批表
	            	ExeSQLFromUserSQL(newsql);
	            	//
	            	//====================================生成审批表SQL语句END==================================//
	            	//==================================================生成标准语句表BEG==============================//
	            	//批量生成审批表符合标准
	            	//单引号转义去掉左右空格
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
	            	//生成审批表标准SQL记录
					ExeSQLFromUserSQL(newsql);	
					 log.debug(newsql);
	            	//==================================================生成标准语句表END==============================//
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //关闭结果集
	            DBUtils.close(pstmt);               //关闭PreparedStatement
	            //DBUtils.close(conn);                //关闭连接
	        }
		}
		return srep;
	}
	/**
	 * 生成分类施保审批表
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
		//符合政策业务分类施保标准条件			
		tempsql = "select b.policychild_id,a.physql,a.locsql,b.sqltype " +
				"from doper_t_policychildsql a,doper_t_policychild b,doper_t_policy c " +
				"where a.status = '1' and b.policy_id = c.policy_id and a.physql is not null " +
				"and b.status = '1' and a.policychild_id = b.policychild_id  and c.policy_id = '"+pid+"'";   //定义SQL语句
		
        //log.debug(tempsql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(tempsql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	policychild = rs.getString("policychild_id");
            	physql = rs.getString("physql"); 
            	locsql = rs.getString("locsql"); 
            	sqltype = rs.getString("sqltype"); 
            	//
            	ibeg = physql.indexOf("from");//第一次出现位置
            	iend = physql.indexOf("where");//第一次出现位置
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
        		//====================================生成标准和核算SQL语句BEG==================================//
        	    tempselect = tselect;
        	    tempfrom = "INFO_T_MEMBER,INFO_T_FAMILY,BIZ_T_OPTCHECK";
        	    tempwhere = "INFO_T_MEMBER.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID and INFO_T_FAMILY.FAMILY_ID = BIZ_T_OPTCHECK.FAMILY_ID";
        		//档次查询表
        	    String snewtflag = "F";
        	    String [] asfrom = tfrom.split(",");
        	    int itflen = asfrom.length;	    
        	    for (int y=0;y<itflen;y++){
        	    	String sfrom = asfrom[y].trim();
        	    	//防止表名有相同特征INFO_T_FAMILYCLASS,INFO_T_FAMILY
        	    	//相同特征为INFO_T_FAMILY
        	    	String [] anewfrom = tempfrom.split(",");
        	    	int inewtlen = anewfrom.length;
        	    	//初始化
        	    	snewtflag = "F";
        	    	for (int j=0;j<inewtlen;j++){	    		
        	    		String stfrom = anewfrom[j].trim();
        	    		if(stfrom.equals(sfrom)){
        	    			snewtflag = "T";
        	    		}
        	    	}
        	    	//不存在相同
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
        	    //分类施保核算        	   
        	    if("2".equals(sqltype)){
        	    	//成员核算
        	    	tempwhere += " and BIZ_T_OPTCHECK.MEMBER_ID = INFO_T_MEMBER.MEMBER_ID ";
        	    	tempwhere += " and BIZ_T_OPTCHECK.POLICY_ID = '"+pid+"' and BIZ_T_OPTCHECK.MATCHSQLFLAG = '0' ";
        	    	matchsql = tempselect +",xoptcheckchild_id.nextval,BIZ_T_OPTCHECK.OPTCHECK_ID,'0','"+policychild+"' " + " from " + tempfrom  + " where " + tempwhere;
            	    matchsql += " and not exists ( select optc.optcheck_id from biz_t_optcheckchild optc " +
            	    		" where optc.family_id = INFO_T_FAMILY.FAMILY_ID and optc.member_id = INFO_T_MEMBER.MEMBER_ID " +
            	    		" and optc.policychild_id = '"+policychild+"' and optc.optcheck_id = BIZ_T_OPTCHECK.OPTCHECK_ID )";
        	    }else{
        	    	//家庭核算
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
            	//生成分类施保审批表
				ExeSQLFromUserSQL(newsql);
            	//==================================================生成标准语句表BEG==============================//
            	//单引号转义去掉左右空格
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
            	//生成分类施保审批表符合标准
				ExeSQLFromUserSQL(newsql);
            	//==================================================生成标准语句表END==============================//   
            	//
            	//
            	//更新分类施保审批表符合标准标识
            	UpdateCheckChildMatchSqlFlag(pid,deptid);
            }
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
	/**
	 * 更新业务审批符合标准标识
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public String UpdateCheckMatchSqlFlag(String pid,String deptid) {
		String srep = "",matchsqlflag = "";
		//审批表
    	matchsqlflag = "update biz_t_optcheck t set t.matchsqlflag = '1'  " +
    			"where t.matchsqlflag = '0' " +
    			"and exists (select a.family_id " +
    						"from biz_t_optcheck a,info_t_family b " +
    						"where a.family_id = b.family_id and a.matchsqlflag = '0' " +
    						"and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%' " +
    						"and a.optcheck_id = t.optcheck_id)";
    	//log.debug("matchsqlflag:"+matchsqlflag);
    	//更新业务审批符合标准标识
    	ExeSQLFromUserSQL(matchsqlflag);
    	
		return srep;
	}
	/**
	 * 更新分类施保审批表符合标准标识
	 * @param pid
	 * @param deptid
	 * @return
	 */
	public String UpdateCheckChildMatchSqlFlag(String pid,String deptid) {
		String srep = "",matchsqlflag = "";
		//审批表
    	matchsqlflag = "update biz_t_optcheckchild t set t.matchsqlflag = '1'  " +
    			"where t.matchsqlflag = '0' " +
    			"and exists (select a.optcheck_id " +
							"from biz_t_optcheck a,info_t_family b,biz_t_optcheckchild c " +
							"where a.family_id = b.family_id and a.optcheck_id = c.optcheck_id and c.matchsqlflag = '0' " +
							"and a.policy_id = '"+pid+"' and b.organization_id like '"+deptid+"%' " +
							"and a.optcheck_id = t.optcheck_id)";
    	//log.debug("matchsqlflag:"+matchsqlflag);
    	//更新分类施保审批表符合标准标识
    	ExeSQLFromUserSQL(matchsqlflag);
    	
		return srep;
	}
	
	/**
	 * 处理表
	 * @param sql
	 * @return
	 */
	public String ExeSQLFromUserSQL(String sql) {
		String srep = "";
		//
		//log.debug(sql);
	      
  	  	Connection conn = null;                 //声明Connection对象
  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
  	  	try {
  	  		conn = DBUtils.getConnection();     //获取数据库连接
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  	  		 
  	  		conn.commit();                      //关闭
  	  		//
  	  		srep = "操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "操作失败!";
  	  			conn.rollback();
  	  		} catch (SQLException e1) {          
  	  			e1.printStackTrace();
  	  		}
  	  	} finally {
  	  		DBUtils.close(pstmt);               //关闭PreparedStatement
  	  		//DBUtils.close(conn);                //关闭连接
  	  	}
        return srep;
	}
}
