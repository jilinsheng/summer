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
	 * 更新业务救助金
	 * 新增名单(救助金)
	 * 更新名单(救助金)
	 * 删除名单(救助金)
	 * @param hashmap
	 * @return
	 */
	public StringBuffer updateListMonery(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//业务查询
		PolicyQuery policyquery = new PolicyQuery();
		//
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jempid = hashmap.get("jempid").toString();
		String jdeptid = hashmap.get("jdeptid").toString();
        String jdepth = hashmap.get("jdepth").toString();
        String jpid = hashmap.get("jpid").toString();
        //
        //需要计算救助金标识
		String accmode = policyquery.getPolicyUserAccFlag(jpid);
		//业务核算类型
		String acctype = policyaccquery.getPolicyAccTypeFlag(jpid);
		//第一级审批机构
		String onedepth = policyquery.getPolicyOneFlowFromId(jpid);
        //结算批次标识
        HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
        String optid = hashmapaccflag.get("optid").toString();
        String accflag = hashmapaccflag.get("accflag").toString();
        String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
        String accmoney = hashmapaccflag.get("accmoney").toString();
        //
        if("-1".equals(accflag)){							//无结算批次        	
        	shtml.append("该业务未执行处理,等待建立结算信息!");
        }else if("0".equals(accflag)){						//新结算批次        	
        	//审批权限标识
            HashMap hashmappower = new HashMap();        
            hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid);            
            //
    		String onecheck = hashmappower.get("onecheck").toString();
    		String usercheck = hashmappower.get("usercheck").toString();
    		//
    		if("1".equals(usercheck)){
    			//用户审批权限
    			if("1".equals(onecheck)){
        			//第一级别审批
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
    				//档次机构ID
    				String sdeptid = getStandardDeptID(jpid,jdeptid);
    				if("".equals(sdeptid)){
    					shtml.append("该业务没有设置标准!");
    					return shtml;
    				}
    				hashmapacc.put("sdeptid", sdeptid);
    				//==============================================//    				    				
    				//清理新增救助名单
    				delListMonery(hashmapacc);
    				//更新不达标救助名单
    				upoldListMonery(hashmapacc);
    				//清理顺延救助名单
    				deloldListMonery(hashmapacc);
    				//更新救助名单
    				upListMonery(hashmapacc);    				    				
    				//增加救助名单
    				addListMonery(hashmapacc);
    				//更新家庭信息变更标识
    				updateFamilyIsChang(hashmapacc);
    				//
    				//shtml.append("更新该业务救助名单,处理救助金完成!");
        		}
    		}else{
    			shtml.append("该用户没有此业务审批权限,无法更新名单!");
    		}    		
        }else if("1".equals(accflag)){						//正在结算批次
        	
        	shtml.append("该业务正在执行结算处理,请等待!");
        }else if("2".equals(accflag)){						//结算完成批次
        	
        	shtml.append("该业务结算完毕,等待建立新的结算信息!");
        }
		return shtml;
	}
	/**
	 * 清理新增救助名单
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
		//档次机构ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//获取该档次该机构所以联合SQL语句		
		newphysql = getStandardAllSql(jpid,jdeptid,sdeptid);
		//
    	//审批表(家庭核算)
    	String sqlfamily = "select a.family_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag = '1' "
    		+ "and b.familyno like '"+jdeptid+"%'";
    	//审批表(成员核算)
    	String sqlmember = "select a.family_id,a.member_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag = '1' "
    		+ "and b.b.familynolike '"+jdeptid+"%'";
    	//
    	//
    	//删除审批名单SQL
    	if("0".equals(acctype)){						//家庭核算
    		//删除审批表SQL语句           		
			//家庭核算	            	    
    		newsql = "delete biz_t_optcheck opt where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id)";
		}else{										   //成员核算
			//删除审批表SQL语句           		
			//成员核算	            	    
    		newsql = "delete biz_t_optcheck opt where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id  and opt.member_id = stan.member_id)";
		}            	
    	//log.debug("newsql:"+newsql);
    	//执行
    	PublicComm pubilccomm = new PublicComm();
    	pubilccomm.ExeSQLFromUserSQL(newsql);
        return shtml;
	}
	/**
	 * 清理顺延救助名单
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
		//档次机构ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//获取该档次该机构所以联合SQL语句		
		newphysql = getStandardAllSql(jpid,jdeptid,sdeptid);
		//
		//
    	//审批表(家庭核算)
    	String sqlfamily = "select a.family_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag in (2,3,4) "
    		+ "and b.familyno like '"+jdeptid+"%'";
    	//审批表(成员核算)
    	String sqlmember = "select a.family_id,a.member_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and a.moneyflag in (2,3,4) "
    		+ "and b.familyno like '"+jdeptid+"%'";
    	//
    	//================================================不达标=============================================================
    	//更新审批名单SQL
    	if("0".equals(acctype)){						//家庭核算
    		//更新审批表SQL语句          		
			//家庭核算	            	    
    		newsql = "update biz_t_optcheck opt set moneyflag = '7',opt.checkmoney = '0',opt.sqlmoney = '0',physql = '',locsql = '',accphysql = '',acclocsql = '' " 
    			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id)";
		}else{										   //成员核算
			//更新审批表SQL语句      		
			//成员核算	            	    
    		newsql = "update biz_t_optcheck opt set moneyflag = '7',opt.checkmoney = '0',opt.sqlmoney = '0',physql = '',locsql = '',accphysql = '',acclocsql = '' " 
    			+ "where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
    			+ "and not exists (select * from ("+newphysql+") stan where opt.family_id = stan.family_id  and opt.member_id = stan.member_id)";
		} 
    	//================================================不达标=============================================================
    	//log.debug("newsql:"+newsql);
    	//执行
    	PublicComm pubilccomm = new PublicComm();
    	pubilccomm.ExeSQLFromUserSQL(newsql);
        return shtml;
	}
	/**
	 * 更新顺延不达标救助名单
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
		//档次机构ID
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
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
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
            	//审批表(家庭核算)
            	String sqlfamily = "select a.family_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and a.moneyflag = '7' "
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//审批表(成员核算)
            	String sqlmember = "select a.family_id,a.member_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and a.moneyflag  = '7' "
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//
            	//================================================不达标转达标=============================================================
            	//
            	if("1".equals(accmoneyflag)){				//用户定额救助金===========================================
            		//更新审批名单SQL
                	if("0".equals(acctype)){						//家庭核算
                		//更新审批表SQL语句
                		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";  
                		//单引号转义去掉左右空格
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
        				//家庭核算	            	    
                		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
                			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
                			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
                			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
        			}else{										   //成员核算
        				//更新审批表SQL语句
                		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
                		//单引号转义去掉左右空格
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
        				//成员核算	            	    
                		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
                			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
                			+ "where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
                			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
        			} 
            	}else{										//用户不定额救助金===========================================
            		HashMap hashmapacc = getDeptAccPHYSQL(standardid,jdeptid);
                	String accphysql = hashmapacc.get("accphysql").toString();
                	String acclocsql = hashmapacc.get("acclocsql").toString();
                	if(!"".equals(accphysql)){	//无机构核算金额
	            		//删除审批名单SQL
		            	if("0".equals(acctype)){						//家庭核算
		            		//删除审批表SQL语句
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
		            		//单引号转义去掉左右空格
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//家庭核算
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id ";
		            		//单引号转义去掉左右空格
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		            		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+") " 
		            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
		            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
		    			}else{										   //成员核算
		    				//删除审批表SQL语句
		            		newphysql = physql+ " and info_t_family.familyno like '"+jdeptid+"%'"; 
		            		//单引号转义去掉左右空格
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//成员核算	   
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id and memberid = opt.member_id ";
		            		//单引号转义去掉左右空格
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		            		newsql = "update biz_t_optcheck opt set moneyflag = '2',opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+") " 
		            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
		            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
		    			}
                	}
            	}
            	
            	//================================================不达标转达标=============================================================
            	//log.debug("newsql:"+newsql);
            	//执行
            	PublicComm pubilccomm = new PublicComm();
            	pubilccomm.ExeSQLFromUserSQL(newsql);
            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return shtml;
	}
	/**
	 * 更新救助名单
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
		//档次机构ID
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
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
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
            	//审批表(家庭核算)
            	String sqlfamily = "select a.family_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//审批表(成员核算)
            	String sqlmember = "select a.family_id,a.member_id " 
            		+ "from biz_t_optcheck a,info_t_family b " 
            		+ "where a.family_id = b.family_id " 
            		+ "and a.policy_id = '"+jpid+"' " 
            		+ "and a.ifover = '"+onedepth+"' " 
            		+ "and b.organization_id like '"+jdeptid+"%'";
            	//
            	//
            	if("1".equals(accmoneyflag)){				//用户定额救助金===========================================
	            	//删除审批名单SQL
	            	if("0".equals(acctype)){						//家庭核算
	            		//删除审批表SQL语句
	            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
	            		//单引号转义去掉左右空格
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	    				//家庭核算	            	    
	            		newsql = "update biz_t_optcheck opt set opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
	            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
	            			+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
	            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
	    			}else{										   //成员核算
	    				//删除审批表SQL语句
	            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
	            		//单引号转义去掉左右空格
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	    				//成员核算	            	    
	            		newsql = "update biz_t_optcheck opt set opt.checkmoney = '"+accmoney+"',opt.sqlmoney = '"+accmoney+"' " 
	            			+ ",physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '',acclocsql = '' " 
	            			+ "where exists ("+sqlmember+" and opt.family_id = a.family_id and opt.member_id = a.member_id ) " 
	            			+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id  and opt.member_id = info_t_family.member_id)";
	    			} 
            	}else{										//用户不定额救助金===========================================
            		HashMap hashmapacc = getDeptAccPHYSQL(standardid,jdeptid);
                	String accphysql = hashmapacc.get("accphysql").toString();
                	String acclocsql = hashmapacc.get("acclocsql").toString();
                	if(!"".equals(accphysql)){	//无机构核算金额
	            		//删除审批名单SQL
		            	if("0".equals(acctype)){						//家庭核算
		            		//删除审批表SQL语句
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
		            		//单引号转义去掉左右空格
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//家庭核算
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id ";
		            		//单引号转义去掉左右空格
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
			        		//
			        		newsql = "update biz_t_optcheck opt set  ";
//			        		if("0".equals(accmode)){				//用户不可以修改救助金
//			        			newsql += "opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+"), ";
//			        		}
			        		newsql += "opt.checkmoney = ("+matchsql+"),opt.sqlmoney = ("+matchsql+"), ";
		            		newsql += "physql = '"+physql+"',locsql = '"+locsql+"',accphysql = '"+accphysql+"',acclocsql = '"+acclocsql+"' " 
		            				+ "where exists ("+sqlfamily+" and opt.family_id = a.family_id ) " 
		            				+ "and exists ("+newphysql+" and opt.family_id = info_t_family.family_id)";
		    			}else{										   //成员核算
		    				//删除审批表SQL语句
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'"; 
		            		//单引号转义去掉左右空格
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		    				//成员核算	   
		            		matchsql = "select acccount from ("+ accphysql +") where familyid = opt.family_id and memberid = opt.member_id ";
		            		//单引号转义去掉左右空格
		            		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
			        		//
			        		newsql = "update biz_t_optcheck opt set  ";
//			        		if("0".equals(accmode)){				//用户不可以修改救助金
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
            	//执行
            	PublicComm pubilccomm = new PublicComm();
            	pubilccomm.ExeSQLFromUserSQL(newsql);
            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return shtml;
	}
	/**
	 * 新增救助名单	
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
		//档次机构ID
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
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
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
            	//审批表(家庭核算)
            	String sqlfamily = "select a.family_id " +
            			"from biz_t_optcheck a,info_t_family b " +
            			"where a.family_id = b.family_id and a.policy_id = '"+jpid+"' and b.organization_id like '"+jdeptid+"%'";
            	//审批表(成员核算)
            	String sqlmember = "select a.family_id,a.member_id " +
            			"from biz_t_optcheck a,info_t_family b " +
            			"where a.family_id = b.family_id and a.policy_id = '"+jpid+"' and b.organization_id like '"+jdeptid+"%'";
            	//
            	//
            	if("1".equals(accmoneyflag)){				//用户定额救助金===========================================
            		//生成新审批名单SQL
	            	if("0".equals(acctype)){
	            		//生成审批表SQL语句
	            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
	            		//单引号转义去掉左右空格
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	            	    matchsql = "select c.family_id,f.masterid,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1','"+accmoney+"','"+onedepth+"','"+physql+"','"+locsql+"','"+accmoney+"','"+jempid+"',sysdate " 
	            	    		+ " from (" + newphysql + ") c,info_t_family f ";
	            	    matchsql += " where not exists (" + sqlfamily + " and b.family_id = c.family_id and b.family_id = f.family_id) " 
	            	    		+ "and c.family_id = f.family_id";
	    				//家庭核算	            	    
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) " + matchsql ;
	    			}else{
	    				//生成审批表SQL语句
	    				newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
	    				//单引号转义去掉左右空格
		            	physql = physql.replace("'","''");
		        		locsql = locsql.replace("'","''");
	    				matchsql = "select c.family_id,c.member_id,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1','"+accmoney+"','"+onedepth+"','"+physql+"','"+locsql+"','"+accmoney+"','"+jempid+"',sysdate " 
	    						+ " from (" + newphysql + ") c ";
	            	    matchsql += " where not exists (" + sqlmember + " and b.family_id = c.family_id ) ";
	    				//成员核算	            	    
	            		newsql = "insert into biz_t_optcheck " +
	            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,sqlmoney,optoper,optdt) " + matchsql ;	    				
	    			}	            	
            	}else{										//用户不定额救助金==========================================
            		HashMap hashmapacc = getDeptAccPHYSQL(standardid,jdeptid);
                	String accphysql = hashmapacc.get("accphysql").toString();
                	String acclocsql = hashmapacc.get("acclocsql").toString();
                	//生成新审批名单SQL
                	if(!"".equals(accphysql)){	//无机构核算金额	
		            	if("0".equals(acctype)){			//家庭核算	
		            		//生成审批表SQL语句
		            		newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
		            		//单引号转义去掉左右空格
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
		            		            			
	            			newaccphysql = accphysql;
		            		//单引号转义去掉左右空格
			        		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		            	    matchsql = "select c.family_id,f.masterid,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1',d.acccount,'"+onedepth+"','"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"',d.acccount,'"+jempid+"',sysdate " 
		            	    		+ " from (" + newphysql + ") c,(" + newaccphysql + ") d,info_t_family f ";
		            	    matchsql += " where not exists (" + sqlfamily + " and b.family_id = c.family_id and b.family_id = d.familyid and b.family_id = f.family_id ) " 
			            	    	+ "and c.family_id = d.familyid and c.family_id = f.family_id and d.familyid = f.family_id";
		            	    //家庭核算	            	    
		            		newsql = "insert into biz_t_optcheck " +
		            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,accphysql,acclocsql,sqlmoney,optoper,optdt) " + matchsql ;
		            			            		
		    			}else{								//成员核算
		    				//生成审批表SQL语句
		    				newphysql = physql+ " and info_t_family.organization_id like '"+jdeptid+"%'";
		    				//单引号转义去掉左右空格
			            	physql = physql.replace("'","''");
			        		locsql = locsql.replace("'","''");
	            			newaccphysql = accphysql;
		            		//单引号转义去掉左右空格
			        		accphysql = accphysql.replace("'","''");
			        		acclocsql = acclocsql.replace("'","''");
		    				matchsql = "select c.family_id,c.member_id,xoptcheck_id.nextval,'"+jpid+"','"+acctype+"','1',d.acccount,'"+onedepth+"','"+physql+"','"+locsql+"','"+accphysql+"','"+acclocsql+"',d.acccount,'"+jempid+"',sysdate " 
		    							+ " from (" + newphysql + ") c,(" + newaccphysql + ") d ";
		            	    matchsql += " where not exists (" + sqlmember + " and b.family_id = c.family_id and b.family_id = d.familyid ) " 
		            	    		+ "and c.family_id = d.familyid ";
		            	    //成员核算	            	    
		            		newsql = "insert into biz_t_optcheck " +
		            				"(family_id,member_id,optcheck_id,policy_id,acctype,moneyflag,checkmoney,ifover,physql,locsql,accphysql,acclocsql,sqlmoney,optoper,optdt) " + matchsql ;
		    			}	    				
	    			}	            	
            	}
            	//log.debug("matchsql:"+matchsql);
            	//log.debug("newsql:"+newsql);
            	//执行
            	PublicComm pubilccomm = new PublicComm();
            	pubilccomm.ExeSQLFromUserSQL(newsql);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return shtml;
	}
	/**
	 * 更新家庭变更标识
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
		//档次机构ID
		String sdeptid = hashmap.get("sdeptid").toString();
		//
		//审批表(家庭核算)
    	String sqlfamily = "select a.family_id " 
    		+ "from biz_t_optcheck a,info_t_family b " 
    		+ "where a.family_id = b.family_id " 
    		+ "and a.policy_id = '"+jpid+"' " 
    		+ "and a.ifover = '"+onedepth+"' " 
    		+ "and b.organization_id like '"+jdeptid+"%'";
    	//
        String sql = "update info_t_family fm set fm.ischange = '0' "
        	+ "where fm.ischange = '1' and exists ("+sqlfamily+" and fm.family_id = a.family_id )";
        //执行
    	PublicComm pubilccomm = new PublicComm();
    	pubilccomm.ExeSQLFromUserSQL(sql);
        //log.debug(sql);
        return srep;
	}
	/**
	 * 获取标准的当前机构的核算公式信息
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
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {	
            	hashmap.put("accphysql", rs.getString("accexpphysql"));
            	hashmap.put("acclocsql", rs.getString("accexplocsql"));
            	irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        if(irow == 0){
        	hashmap.put("accphysql", "");
        	hashmap.put("acclocsql", "");
        }
		return hashmap;
	}
	/**
	 * 获取档次机构ID
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
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	sdeptid = rs.getString("organization_id");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return sdeptid;
	}
	/**
	 * 获取当前业务所以该机构所有档次标准联合SQL语句
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
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
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
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //log.debug(srep);
        return srep;
	}
}
