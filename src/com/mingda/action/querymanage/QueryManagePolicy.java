package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

public class QueryManagePolicy {
	static Logger log = Logger.getLogger(QueryManagePolicy.class);
	//常量定义
	ConstantDefine constantdefine = new ConstantDefine();
	/**
	 * 设置业务标准SQL
	 * @param hashmap
	 * @param conn 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public StringBuffer GetSqlPolicyStandard(HashMap hashmap, Connection conn) {
		StringBuffer shtml= new StringBuffer("");
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务处理类
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
		/**通用的SQL解析参数**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**特殊的处理参数**/
		String tpname = hashmap.get("ssuperpolicy").toString();
		String tnotpname = hashmap.get("snotpolicy").toString();
		String ttype = hashmap.get("ttype").toString();
		/**变量定义**/
		//业务所属
		String stempflag = constantdefine.POLICYFLAGCODE_IS;
		//
		//成员信息
		String tfname = "INFO_T_FAMILY";    		
		String tftable = tableinfoquery.gettablelocicfromphysic(tfname);
		String tmname = "INFO_T_MEMBER";    		
		String tmtable = tableinfoquery.gettablelocicfromphysic(tmname);
		//
		//家庭或者成员设置
		if(ttype.equals("family")){
			//查询字段
			tselect = "INFO_T_FAMILY.FAMILY_ID";	
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tftable;
			}else{
				tfrom = tfrom + "," + tftable;
			} 
		}else{
			//查询字段
			tselect = "INFO_T_FAMILY.FAMILY_ID,INFO_T_MEMBER.MEMBER_ID";
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tmtable + "," + tftable;
			}else{
				tfrom = tfrom + "," + tmtable + "," + tftable;
			} 
		}
		//查询条件
		if(null == twhere || twhere.equals("")){
			//家庭在用状态
			twhere = " INFO_T_FAMILY.STATUS = '1'";
		}else{
			//家庭在用状态
			twhere += " and INFO_T_FAMILY.STATUS = '1'";
		}		
		//
		String stemp = "",pname = "",pid = "";		
		//所属业务====================================		
		if(null == tpname || tpname.equals("")){
			//没有所属业务
		}else{    		
			//
			//业务编号
			String [] atname = tpname.split(",");
    	    int ilength = atname.length;
    	    if(ilength>0){
	    	    for (int r=0;r<ilength;r++){
	    	    	pname = atname[r];
	    	    	pid = policymanagecheckquery.getPolicyIdFromName(pname);	
	    	    	if(r==0){
	    	    		stemp = pid;
	    	    	}else{
	    	    		stemp = pid + "," + stemp;
	    	    	}	    	    	
	    	    }
	    	    //所属业务
				String tmp = "select family_id "
					+ "from BIZ_T_FAMILYSTATUS "
					+ "where BIZ_T_FAMILYSTATUS.POLICY_ID in ("+stemp+") "
					+ "and BIZ_T_FAMILYSTATUS.FLAG = '"+stempflag+"' ";
	    	    //
	    	    twhere += " and  exists " + "(" + tmp + " and INFO_T_FAMILY.FAMILY_ID = BIZ_T_FAMILYSTATUS.FAMILY_ID)";
    	    }    	    
		}
		//不属业务=================================	    		
		if(null == tnotpname || tnotpname.equals("")){
			//没有不属业务
		}else{
			//
			//业务编号
			String [] atname = tnotpname.split(",");
		    int ilength = atname.length;
		    if(ilength>0){
	    	    for (int r=0;r<ilength;r++){
	    	    	pname = atname[r];
	    	    	pid = policymanagecheckquery.getPolicyIdFromName(pname);	
	    	    	if(r==0){
	    	    		stemp = pid;
	    	    	}else{
	    	    		stemp = pid + "," + stemp;
	    	    	}	    	    	
	    	    } 
	    	    //不属业务
				String tmp = "select family_id "
					+ "from BIZ_T_FAMILYSTATUS "
					+ "where BIZ_T_FAMILYSTATUS.POLICY_ID in ("+stemp+") "
					+ "and BIZ_T_FAMILYSTATUS.FLAG = '"+stempflag+"' ";
	    	    //
				twhere += " and  not exists " + "(" + tmp + " and INFO_T_FAMILY.FAMILY_ID = BIZ_T_FAMILYSTATUS.FAMILY_ID)";
		    }
		}
		//
		 log.debug("test count 11");
		shtml.append(tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"0","0",conn));
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
	/**
	 * 核算公式
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public StringBuffer GetSqlPolicyAcc(HashMap hashmap) {
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
		String ttype = hashmap.get("ttype").toString();
		String selwhere = hashmap.get("selwhere").toString();
		/**变量定义**/		
		//成员信息
		String tfname = "INFO_T_FAMILY";    		
		String tftable = tableinfoquery.gettablelocicfromphysic(tfname);
		String tmname = "INFO_T_MEMBER";    		
		String tmtable = tableinfoquery.gettablelocicfromphysic(tmname);
		//
		//家庭或者成员设置
		if(ttype.equals("family")){
			//查询字段
			tselect = "INFO_T_FAMILY.FAMILY_ID as familyid,"+selwhere+" as acccount ";	
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tftable;
			}else{
				tfrom = tfrom + "," + tftable;
			} 
		}else{
			//查询字段
			tselect = "INFO_T_FAMILY.FAMILY_ID as familyid,INFO_T_MEMBER.MEMBER_ID as memberid,"+selwhere+" as acccount ";	
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tmtable + "," + tftable;
			}else{
				tfrom = tfrom + "," + tmtable + "," + tftable;
			} 
		}
	 
		//
		try {
			 log.debug("test count 9");
			shtml.append(tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"0","0",ConnectionManager.getConnection()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
    /**
     * 获取业务属性页面
     * @param id
     * @return
     */
    public StringBuffer getPolicyItemsHtml(String id,Connection conn) {
    	StringBuffer shtml= new StringBuffer("");
    	String sdesc = "",sname = "",sdate = "";
    	//政策业务查询处理类
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
    	String sql ="select  name, descr,begdt, note from doper_t_policy where policy_id = '"+id+"'";
    	//log.debug(sql);
    	shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
        shtml.append("<tr align='center' class='colField'>");
          shtml.append("<td>名称</td>");
          shtml.append("<td>开始日期</td>"); 
          	shtml.append("<td>详细描述</td>");          	           
          shtml.append("</tr>");
	       // Connection conn = null;                 //声明Connection对象
	        PreparedStatement pstmt = null;         //声明PreparedStatement对象
	        ResultSet rs = null;                    //声明ResultSet对象
	        try {
	            conn =ConnectionManager.getConnection();     //获取数据库连接
	            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {	            	
	            	shtml.append("<tr>");	            		
		            	sname = rs.getString("name");
		            	sdesc = rs.getString("descr");
		            	sdate = rs.getString("begdt");                
		            	sdate = policymanagecheckquery.getformatdt(sdate);
		            	//
		            	shtml.append("<td height='23' class='colValue' width='20%'>"+sname+"</td>");
		            	shtml.append("<td height='23' class='colValue' width='10%'>"+sdate+"</td>");
		            	shtml.append("<td height='23' class='colValue' >"+sdesc+"</td>");		            	
	            	shtml.append("</tr>");
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	        	ConnectionManager.closeQuietly(rs);                  //关闭结果集
	        	ConnectionManager.closeQuietly(pstmt);               //关闭PreparedStatement
	            //DBUtils.close(conn);                //关闭连接
	        }
        shtml.append("</table>");
    	return shtml;
    }
    /**
     * 取得业务所有档次表格Html
     * @param id
     * @return
     */
    public StringBuffer getPolicyStandardsHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String sdesc = "",splanmoney = "",splandesc = "";
    	String sql ="select standard_id,b.descr,planmoney,plandesc " +
	    			"from doper_t_policy a,doper_t_standard b " +
	    			"where a.policy_id = b.policy_id and a.policy_id ='"+id+"' order by planmoney desc";
    	//log.debug(sql);
    	shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
        shtml.append("<tr align='center' class='colField'>");
          shtml.append("<td>档次描述</td>");
          	shtml.append("<td>标准数额</td>");
          	shtml.append("<td>标准描述</td>");            
          shtml.append("</tr>");
	        Connection conn = null;                 //声明Connection对象
	        PreparedStatement pstmt = null;         //声明PreparedStatement对象
	        ResultSet rs = null;                    //声明ResultSet对象
	        try {
	            conn = DBUtils.getConnection();     //获取数据库连接
	            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {
	            	String sid = rs.getString("standard_id");
	            	shtml.append("<tr>");	            		
		            	sdesc = rs.getString("descr");
		            	splanmoney = rs.getString("planmoney");
		            	splandesc = rs.getString("plandesc");
		            	shtml.append("<td height='23' class='colValue' width='20%'><span class = 'pointer' style = 'color: #6BA6FF;' onclick=\"GetPolicyStandardDepts('"+sid+"')\">"+sdesc+"</span></td>");
		            	shtml.append("<td height='23' class='colValue' width='10%'>"+splanmoney+"</td>");
		            	shtml.append("<td height='23' class='colValue'>"+splandesc+"</td>");
	            	shtml.append("</tr>");
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //关闭结果集
	            DBUtils.close(pstmt);               //关闭PreparedStatement
	            //DBUtils.close(conn);                //关闭连接
	        }
        shtml.append("</table>");
    	return shtml;
    }
    /**
     * 取得业务档次所有机构标准表格Html
     * @param id
     * @return
     */
    public StringBuffer getPolicyStandardDeptsHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String stemp = "";
    	String sql = "select checkmoney,checkdesc,accdesc,fullname " +
        "from doper_t_standarddept a,sys_t_organization b " +
        "where a.organization_id = b.organization_id and standard_id = '"+id+"' order by b.organization_id";   //定义SQL语句
    	//log.debug(sql);
    	shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
        shtml.append("<tr align='center' class='colField'>");
          shtml.append("<td>所属机构</td>");
          	shtml.append("<td>标准数额</th>");
          	shtml.append("<td>标准描述</td>"); 
          	shtml.append("<td>核算公式描述</td>"); 
          shtml.append("</tr>");
	        Connection conn = null;                 //声明Connection对象
	        PreparedStatement pstmt = null;         //声明PreparedStatement对象
	        ResultSet rs = null;                    //声明ResultSet对象
	        try {
	            conn = DBUtils.getConnection();     //获取数据库连接
	            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {
	            	shtml.append("<tr>");
		            	stemp = rs.getString("fullname");	
		            	shtml.append("<td height='23' class='colValue' width='30%'>"+stemp+"</td>");
		            	stemp= rs.getString("checkmoney");
		            	shtml.append("<td height='23' class='colValue' width='10%'>"+stemp+"</td>");
		            	stemp = rs.getString("checkdesc");
		            	shtml.append("<td height='23' class='colValue' width='10%'>"+stemp+"</td>");
		            	stemp = rs.getString("accdesc");
		            	shtml.append("<td height='23' class='colValue' >"+stemp+"</td>");
	            	shtml.append("</tr>");
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //关闭结果集
	            DBUtils.close(pstmt);               //关闭PreparedStatement
	            //DBUtils.close(conn);                //关闭连接
	        }
        shtml.append("</table>");
    	return shtml;
    }
}
