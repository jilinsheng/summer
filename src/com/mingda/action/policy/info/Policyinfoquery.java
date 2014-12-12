package com.mingda.action.policy.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;
import com.mingda.common.page.PageView;

public class Policyinfoquery {
	static Logger log = Logger.getLogger(Policyinfoquery.class);
	/**
	 * 获取政策业务相关信息详细信息
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getCheckPolicyInfosHtml(HashMap hashmap) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	PageView pv = new PageView();
    	//
    	String pid = hashmap.get("pid").toString();    	
    	String fmid = hashmap.get("fmid").toString();
    	String memid = hashmap.get("memid").toString();
    	//
    	Document dictionary = (Document) hashmap.get("dictionary");
    	//
    	//业务核算类型
    	PolicyAccQuery policyaccquery = new PolicyAccQuery();
		String acctype = policyaccquery.getPolicyAccTypeFlag(pid);
		//
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		
		//
		HashMap hashmapsql = new HashMap();
		hashmapsql = getCheckPolicyInfosSql(pid,fmid,memid,acctype);
		String physql = hashmapsql.get("physql").toString();
		String thcol = hashmapsql.get("thcol").toString();
		String tdisc = hashmapsql.get("tdisc").toString();
		//
		if("".equals(physql)||"-1".equals(physql)){
			//
			
		}else{
			//动态列名
			String [] athcol = thcol.split(",");
			String [] atdisc = tdisc.split(",");
	    	int ileng = athcol.length;
	    	int ilengd = atdisc.length;
	    	//
	    	String sql = physql;
			//log.debug("sql:"+sql);
			Connection conn = null;                 //声明Connection对象
		    PreparedStatement pstmt = null;         //声明PreparedStatement对象
		    ResultSet rs = null;                    //声明ResultSet对象
		    try {
		        conn = DBUtils.getConnection();     //获取数据库连接
		        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	html += "<tr class='rowFieldInfo'>";
						temp ="<td height='23'>属性</td>";
						html +=temp;
						temp ="<td height='23'>属性值</td>";
						html +=temp;			
					html +="</tr>";
		        	for (int h=0;h<ileng;h++){
		        		String tempthn = athcol[h];	
		        		String tempthv = "";
		        		//是否字典值
		        		boolean bisdisc = false;
		        		for (int j=0;j<ilengd;j++){
		        			String tempdisc = atdisc[j];
		        			String temprs = new Integer(h+1).toString();		        			
		        			if(tempdisc.equals(temprs)){
		        				bisdisc = true;
		        			}
		        		}
		        		//字典值		        			
	        			if(bisdisc){	        				
	        				tempthv = rs.getString(h+1);
	        				tempthv = pv.getDictionartHandle().getDictsortToValue(dictionary,tempthv);
	        				if(null == tempthv || "".equals(tempthv)){
	        					tempthv = "无";
	        				}
	        			}else{
	        				tempthv = rs.getString(h+1);
	        			}
	        			
		        		html += "<tr>";
			    			temp ="<td height='23' class='colValue' style='color: #660099;'>"+tempthn+"</td>";
			    			html +=temp;
			    			temp ="<td height='23' class='colValue'>"+tempthv+"</td>";
			    			html +=temp;			
			    		html +="</tr>";
				    }
		        }
		    } catch (SQLException e) {
		        log.debug(e.toString());
		    } finally {
		        DBUtils.close(rs);                  //关闭结果集
		        DBUtils.close(pstmt);               //关闭PreparedStatement
		        DBUtils.close(conn);                //关闭连接
		    }
		}
		
		
        //
	    html +="</table>";        
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
	/**
	 * 获取成生查询政策业务相关信息查询QL语句
	 * @param pid
	 * @param fmid
	 * @param memid
	 * @param acctype
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public HashMap getCheckPolicyInfosSql(String pid,String fmid,String memid,String acctype){
		String sql = "",temp = "",physql = "",thcol = "",tdisc = "";
		HashMap hashmap = new HashMap();
		boolean bdeptflag = false;
		String tselect = "",tfrom = "",twhere = "",tempfrom = "",tfieldname = "",disccol = "";
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//常量定义
        ConstantDefine constantdefine = new ConstantDefine();
		//
		String tftable,tmtable,tfield,tfamily,tmember,tdept;
		String deptid = "",deptname = "",memname = "",memnames = "";
		//家庭信息
		tftable = "INFO_T_FAMILY";//家庭表    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//成员信息
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		//
		tfield = "MEMBERNAME";//姓名
		memname = tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		memnames = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		//机构表
		tdept = "SYS_T_ORGANIZATION";  
		//
		tfield = "ORGANIZATION_ID";//家庭机构ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//家庭机构
		//查询表
		tfrom = tmember + "," + tfamily;		
		//
        sql = "select c.policyinfo_id,c.status," +
        		"b.logicname as tablename," +
        		"a.logicname as fieldname," +
        		"b.logicname || '.' || a.logicname as fieldnames," +
        		"a.fieldtype " +
        		"from sys_t_field a,sys_t_table b,doper_t_policyinfo c " +
        		"where a.table_id = b.table_id and a.field_id = c.field_id " +
        			"and a.status = '1' and c.status = '1' and c.policy_id = '"+pid+"' " +
        		"order by a.logicname,b.logicname";   //定义SQL语句
		//log.debug("sql:"+sql);
		Connection conn = null;                 //声明Connection对象
	    PreparedStatement pstmt = null;         //声明PreparedStatement对象
	    ResultSet rs = null;                    //声明ResultSet对象
	    try {
	        conn = DBUtils.getConnection();     //获取数据库连接
	        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	temp = rs.getString("tablename");
	        	if(null != temp){
	        		if(null == tempfrom || "".equals(tempfrom)){
	        			tempfrom = temp;
	        		}else{
	        			tempfrom += ","+temp;
	        		}
	        	}
	        	temp = rs.getString("fieldnames");
	        	if(null != temp){	        		
	        		if(deptid.equals(temp)){
	        			//机构查询
	        			bdeptflag = true; 
	        		}else{
	        			//查询字段
	        			if(null == tselect || "".equals(tselect)){
	        				tselect = temp;
        	    		}else{
        	    			tselect += ","+temp;
        	    		}	        			
	        			//
	        			String fieldname = rs.getString("fieldname");
	        			if(null != fieldname){
	        				//查询列名称
	        				if(null == tfieldname || "".equals(tfieldname)){
	        					tfieldname = fieldname;
	        	    		}else{
	        	    			tfieldname += ","+fieldname;
	        	    		}	        				
	        			}
	        			//字典值
	        			String type = rs.getString("fieldtype");
	    	        	if(null != type){
	    	        		//字典值列
	    	        		if("4".equals(type)){	    	        			
	    	        			if(null == disccol || "".equals(disccol)){
	    	        				disccol = temp;
	    		        		}else{
	    		        			disccol += ","+temp;
	    		        		}
	    	        		}
	    	        	}
	        		}	        		
	        	}
	        }
	    } catch (SQLException e) {
	        log.debug(e.toString());
	    } finally {
	        DBUtils.close(rs);                  //关闭结果集
	        DBUtils.close(pstmt);               //关闭PreparedStatement
	        DBUtils.close(conn);                //关闭连接
	    }
	    //查询机构
	    if(bdeptflag){
	    	//
			if(null == tselect || "".equals(tselect)){
    			tselect = deptname;
    		}else{
    			tselect += ","+deptname;
    		}
	    	if(null == tempfrom || "".equals(tempfrom)){
	    		tempfrom = tdept;
			}else{
				tempfrom += ","+tdept;
			}
			if(null == twhere || "".equals(twhere)){
				twhere = "INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID";
			}else{
				twhere += " and "+"INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID";
			}
			//列名称
			if(null == tfieldname || "".equals(tfieldname)){
				tfieldname = "所属";
    		}else{
    			tfieldname += ","+"所属";
    		}
	    }
	    //家庭动态表(中文)
		String [] tfromname = tempfrom.split(",");
		String tempname = "";
		boolean bisflag = false;
    	int ileng1 = tfromname.length;
	    for (int h=0;h<ileng1;h++){
	    	tempname = tfromname[h];
	    	//默认表
	    	String [] fromname = tfrom.split(",");
	    	int ilen2 = fromname.length;
		    for (int j=0;j<ilen2;j++){
		    	String tname = fromname[j];	    	
		    	if(tempname.equals(tname)){
		    		bisflag = true;
		    	}
		    }
		    //存在默认表
		    if(bisflag){
		    	tfrom = tfrom + "," + tempname;				
			}	
	    }
	    //条件查询
	    if("1".equals(acctype)){
	    	//成员核算
	    	//
		    //动态查询字段
			String [] aselect = tselect.split(",");
			String tempsname = "";
			boolean bisselect = false;
	    	int ileng = aselect.length;
		    for (int h=0;h<ileng;h++){
		    	tempsname = aselect[h];
		    	//默认查询字段
		    	if(memnames.equals(tempsname)){
		    		bisselect = true;
		    	} 	
		    }
		    //存在默认查询字段
		    if(!bisselect){
		    	//查询字段
		    	tselect = memnames + "," + tselect;	
		    	//查询列名
				tfieldname = memname + "," + tfieldname;	
			}
		    //
		    //条件查询
		    if(null == memid || "".equals(memid)){
		    	if(null == twhere || "".equals(twhere)){
					twhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
				}else{
					twhere += " and "+"INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
				}
		    }else{
		    	if(null == twhere || "".equals(twhere)){
					twhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"' and INFO_T_MEMBER.MEMBER_ID = '"+memid+"'";
				}else{
					twhere += " and "+"INFO_T_FAMILY.FAMILY_ID = '"+fmid+"' and INFO_T_MEMBER.MEMBER_ID = '"+memid+"'";
				}
		    }	    	
	    }else{
	    	//家庭核算
	    	if(null == twhere || "".equals(twhere)){
				twhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
			}else{
				twhere += " and "+"INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
			}
	    }
	    
	    //
	    //列名称
	    thcol = tfieldname;
	    //字典值列序号
		String [] tcname = tselect.split(",");
		String tempcname = "";
    	int ilengc = tcname.length;
	    for (int h=0;h<ilengc;h++){
	    	tempcname = tcname[h];
	    	//
	    	String [] discname = disccol.split(",");
	    	int ilen = discname.length;
		    for (int j=0;j<ilen;j++){
		    	String tname = discname[j];	    	
		    	if(tempcname.equals(tname)){
		    		String tempdisc = new Integer(h+1).toString(); 
		    		if(null == tdisc || "".equals(tdisc)){
		    			tdisc = tempdisc;
	        		}else{
	        			tdisc += ","+tempdisc;
	        		}
		    	}
		    }
	    }	    
	    //处理结果
	    String tmode = constantdefine.REPLACESQL_RES;
	    log.debug("test count 3");
	    physql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1","0",conn).toString();
	    //	    
	    //log.debug("physql:"+physql);
	    //log.debug("thcol:"+thcol);
	    //log.debug("tdisc:"+tdisc);
	    //
	    hashmap.put("physql", physql);
	    hashmap.put("thcol", thcol);
	    hashmap.put("tdisc", tdisc);
	    //
		return hashmap;
	}
	/**
	 * 获取审批家庭或者成员业务标准SQL
	 * @param checkid
	 * @return
	 */
	public StringBuffer getCheckPolicySqlsHtml(String checkid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",sql = "";
    	//
    	html += "<fieldset class='list'>";
    	html += "<legend class='legend'>政策业务标准SQL</legend>";			
	    	html += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		    	html += "<tr>";
		    	html += "<td valign='top' class = 'myspace'>";
		    	//
		    	sql = "select locsql from biz_t_optchecksql where optcheck_id = '"+checkid+"'";
		    	//log.debug("sql:"+sql);
				Connection conn = null;                 //声明Connection对象
			    PreparedStatement pstmt = null;         //声明PreparedStatement对象
			    ResultSet rs = null;                    //声明ResultSet对象
			    try {
			        conn = DBUtils.getConnection();     //获取数据库连接
			        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
			        rs = pstmt.executeQuery();
			        while (rs.next()) { 
			        	temp = rs.getString("locsql");
			        }
			    } catch (SQLException e) {
			        log.debug(e.toString());
			    } finally {
			        DBUtils.close(rs);                  //关闭结果集
			        DBUtils.close(pstmt);               //关闭PreparedStatement
			        DBUtils.close(conn);                //关闭连接
			    }
			    //
			    if(null == temp){
			    	html += "无";
			    }else{
			    	//
			    	int ibeg = temp.indexOf("where");//条件按where出现位置
			    	if(ibeg != -1){			    		
			    		temp = temp.substring(ibeg+5, temp.length());
			    	}			    	
			    	html += temp;
			    }
			    html += "</td>";	
		    	html += "</tr>";
			html += "</table>"; 	
		html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
	/**
	 * 获取审批家庭或者成员业务核算SQL
	 * @param checkid
	 * @return
	 */
	public StringBuffer getCheckPolicyAccsHtml(String checkid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",sql = "";
    	//
    	//
    	html += "<fieldset class='list'>";
    	html += "<legend class='legend'>政策业务核算公式SQL</legend>";			
	    	html += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		    	html += "<tr>";
		    	html += "<td valign='top' class = 'myspace'>";
		    	//
		    	sql = "select acclocsql from biz_t_optchecksql where optcheck_id = '"+checkid+"'";
		    	//log.debug("sql:"+sql);
				Connection conn = null;                 //声明Connection对象
			    PreparedStatement pstmt = null;         //声明PreparedStatement对象
			    ResultSet rs = null;                    //声明ResultSet对象
			    try {
			        conn = DBUtils.getConnection();     //获取数据库连接
			        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
			        rs = pstmt.executeQuery();
			        while (rs.next()) { 
			        	temp = rs.getString("acclocsql");
			        }
			    } catch (SQLException e) {
			        log.debug(e.toString());
			    } finally {
			        DBUtils.close(rs);                  //关闭结果集
			        DBUtils.close(pstmt);               //关闭PreparedStatement
			        DBUtils.close(conn);                //关闭连接
			    }
			    //
			    if(null == temp){
			    	html += "无";
			    }else{
			    	//
			    	int iend = temp.indexOf("from");//按from出现位置
			    	if(iend != -1){			    		
			    		temp = temp.substring(0, iend);
			    		int ibeg = temp.indexOf(",");//按,出现位置
				    	if(ibeg != -1){			    		
				    		temp = temp.substring(ibeg+1,temp.length());
				    		int ibegas = temp.indexOf("as");//按as出现位置
					    	if(ibegas != -1){			    		
					    		temp = temp.substring(0,ibegas);					    		
					    	}
				    	}	
			    	}			    	
			    	html += temp;
			    }
			    html += "</td>";	
		    	html += "</tr>";
			html += "</table>"; 	
		html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
	/**
	 * 获取审批家庭或者成员业务分类施保标准SQL
	 * @param checkid
	 * @return
	 */
	public StringBuffer getCheckPolicyChildSqlsHtml(String checkid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",sql = "";
    	//
    	html += "<fieldset class='list'>";
    	html += "<legend class='legend'>政策业务分类施保标准SQL</legend>";			
	    	html += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		    	html += "<tr>";
		    	html += "<td valign='top' class = 'myspace'>";
		    	//
		    	sql = "select locsql from biz_t_optcheckchildsql where optcheckchild_id = '"+checkid+"'";
		    	//log.debug("sql:"+sql);
				Connection conn = null;                 //声明Connection对象
			    PreparedStatement pstmt = null;         //声明PreparedStatement对象
			    ResultSet rs = null;                    //声明ResultSet对象
			    try {
			        conn = DBUtils.getConnection();     //获取数据库连接
			        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
			        rs = pstmt.executeQuery();
			        while (rs.next()) { 
			        	temp = rs.getString("locsql");
			        }
			    } catch (SQLException e) {
			        log.debug(e.toString());
			    } finally {
			        DBUtils.close(rs);                  //关闭结果集
			        DBUtils.close(pstmt);               //关闭PreparedStatement
			        DBUtils.close(conn);                //关闭连接
			    }
			    //
			    if(null == temp){
			    	html += "无";
			    }else{
			    	//
			    	int ibeg = temp.indexOf("where");//条件按where出现位置
			    	if(ibeg != -1){			    		
			    		temp = temp.substring(ibeg+5, temp.length());
			    	}			    	
			    	html += temp;
			    }
			    html += "</td>";	
		    	html += "</tr>";
			html += "</table>"; 	
		html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
}
