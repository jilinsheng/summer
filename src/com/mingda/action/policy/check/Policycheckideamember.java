package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

public class Policycheckideamember {
	static Logger log = Logger.getLogger(Policycheckideamember.class);
	/**
	 * 获取家庭审批列表
	 * @param pid
	 * @param fmids
	 * @param mode
	 * @param empid
	 * @return
	 */
	public StringBuffer getFamilyCheckItemsTable(String pid,String fmids,String mode,String empid) {
		StringBuffer shtml= new StringBuffer("");
		String html = "",temp = "",childflag = "0";
		int row = 0;	
		//分类施保处理类
		PolicyChildCheckMatch policychildcheckmatch = new PolicyChildCheckMatch();
		boolean bchild = policychildcheckmatch.existsPolicyChildFromPid(pid);
		//分类施保标识
		if(bchild){
			childflag = "1";
		}
		//
		html += "<table id = 'familystb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='rowField'>";
			temp ="<td height='23'>家庭编码</td>";
			html +=temp;			
			temp ="<td height='23'>户主姓名</td>";
			html +=temp;
			temp ="<td height='23'>救助人数</td>";
			html +=temp;
			temp ="<td height='23'>上期救助金</td>";
			html +=temp;
			temp ="<td height='23'>拟发救助金</td>";
			html +=temp;
			temp ="<td height='23'>基本救助金</td>";
			html +=temp;
			//存在分类施保
			if(bchild){
				temp ="<td height='23'>分类施保金</td>";
				html +=temp;
			}
			temp ="<td height='23'>救助情况</td>";
			html +=temp;
		html +="</tr>";
		//单个、批量家庭
		String [] safmid =  fmids.split(",");
		String sfmid = "";
		for(int i=0;i<safmid.length;i++){
			sfmid = safmid[i];
			//
			String sql = "select '1' as hs,sum('1') as zhs," +
	    		"sum(countmoney) as zje," +
	    		"sum(checkmoney) as czje," +
	    		"sum(checkchildmoney) as cczje," +
	    		"sum(recheckmoney) as rezje," +
	    		"b.family_id as fmid," +
	    		"b.familyno as fmno," +
	    		"b.mastername as fmname " +
	    		"from biz_t_optcheck a, info_t_family b " +
	    		"where a.family_id = b.family_id " +
	    		"and a.policy_id = '"+pid+"' and b.family_id = '"+sfmid+"'" +
	    		"group by b.family_id,b.familyno,b.mastername";   //定义SQL语句
    
		    Connection conn = null;                 //声明Connection对象
		    PreparedStatement pstmt = null;         //声明PreparedStatement对象
		    ResultSet rs = null;                    //声明ResultSet对象
		    try {
		        conn = DBUtils.getConnection();     //获取数据库连接
		        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	//
		        	String fmid = rs.getString("fmid");
		        	String fmno = rs.getString("fmno");
		        	String fmname = rs.getString("fmname");
		        	String zhs = rs.getString("zhs");
		        	String zje = rs.getString("zje");
		        	String czje = rs.getString("czje");
		        	String cczje = rs.getString("cczje");
		        	String rezje = rs.getString("rezje");
		            
					html +="<tr id = 'familytr"+fmid+"' style = 'background: #F2F5F7;'>";		
			    		//各列值	                	
						html += "<td height='23' class='colValue' >"+fmno+"</td>";
						html += "<td height='23' class='colValue' >"+fmname+"</td>";	
						html += "<td height='23' class='colValue' >"+zhs+"</td>";
						html += "<td height='23' class='colValue' >"+rezje+"</td>";
						//存在分类施保
						if(bchild){
							html += "<td height='23' class='colValue' ><span id = 'countmoney"+fmid+"'>"+zje+"</span></td>";
							html += "<td height='23' class='colValue' ><span id = 'allmoney"+fmid+"'>"+czje+"</span></td>";
							html += "<td height='23' class='colValue' ><span id = 'allchildmoney"+fmid+"'>"+cczje+"</span></td>";
						}else{
							html += "<td height='23' class='colValue' ><span id = 'countmoney"+fmid+"'>"+zje+"</span></td>";
							html += "<td height='23' class='colValue' ><span id = 'allmoney"+fmid+"'>"+czje+"</span></td>";
						}					
						html += "<td height='23' class='colValue pointer' style='color: #660099;' onclick=\"ViewPolicys('"+fmid+"')\">相关救助情况</td>";												
					html +="</tr>";
					//相关连接操作行
					html +="<tr height='10' class='pointer' id = 'policytr"+fmid+"'>";						
						//存在分类施保
						if(bchild){
							html += "<td width='46' class='colValue' style='text-align:left;'>";
								html += "<img id = 'imgs' src='/db/page/images/membermax.jpg' alt= '展开' onclick=\"ViewMemberChildtb(this,'membertr"+fmid+"','childtr"+fmid+"')\"/>";								
							html += "</td>";
							html += "<td colspan = '7' class='colValue' style='text-align:right;'>";
								html += "<img width='10' height='10' src='/db/page/images/close.gif' alt= '移除' onclick=\"MoveDelChild('familytr"+fmid+"','membertr"+fmid+"','policytr"+fmid+"','childtr"+fmid+"')\"/>";
							html += "</td>";
						}else{
							html += "<td width='46' class='colValue' style='text-align:left;'>";
								html += "<img id = 'imgs' src='/db/page/images/membermax.jpg' alt= '符合标准成员列表' onclick=\"ViewMembertb(this,'membertr"+fmid+"')\"/>";								
							html += "</td>";
							html += "<td colspan = '6' class='colValue' style='text-align:right;'>";
								html += "<img width='10' height='10' src='/db/page/images/close.gif' alt= '移除' onclick=\"MoveDel('familytr"+fmid+"','membertr"+fmid+"','policytr"+fmid+"')\"/>";
							html += "</td>";
						}
					html +="</tr>";
					//各成员情况
					StringBuffer memberhtml = getMemberCheckItemsTable(pid,fmid,mode,childflag);
					//存在分类施保
					if(bchild){
						//各家庭分类施保情况
						StringBuffer childhtml = getChildCheckItemsTable(pid,fmid,empid);
						if(row==0){
							html +="<tr id = 'membertr"+fmid+"'>";		
			    				//各成员值						
			            		html += "<td colspan = '8' class='colValue'>"+memberhtml+"</td>";						
			            	html +="</tr>";
			            	html +="<tr id = 'childtr"+fmid+"'>";		
			    				//各家庭分类施保值						
			            		html += "<td colspan = '8' class='colValue'>"+childhtml+"</td>";						
			            	html +="</tr>";
						}else{						
							html +="<tr id = 'membertr"+fmid+"' style='display:none;'>";	
				    			//各成员值	
			            		html += "<td colspan = '8' class='colValue'>"+memberhtml+"</td>";						
			            	html +="</tr>";
			            	html +="<tr id = 'childtr"+fmid+"' style='display:none;'>";	
			            		//各家庭分类施保值		
			            		html += "<td colspan = '8' class='colValue'>"+childhtml+"</td>";						
			            	html +="</tr>";
						}
					}else{
						if(row==0){
							html +="<tr id = 'membertr"+fmid+"'>";		
			    				//各成员值						
			            		html += "<td colspan = '7' class='colValue'>"+memberhtml+"</td>";						
			            	html +="</tr>";
						}else{						
							html +="<tr id = 'membertr"+fmid+"' style='display:none;'>";	
				    			//各成员值	
			            		html += "<td colspan = '7' class='colValue'>"+memberhtml+"</td>";						
			            	html +="</tr>";
						}
					}
					
		        	//记录序号
		        	row++;
		        }
		    } catch (SQLException e) {
		        log.debug(e.toString());
		    } finally {
		        DBUtils.close(rs);                  //关闭结果集
		        DBUtils.close(pstmt);               //关闭PreparedStatement
		        //DBUtils.close(conn);                //关闭连接
		    }
		}
        
        //
	    html +="</table>";  
	    //
	    shtml.append(html);
	    //log.debug(shtml);
		return shtml;
	}
	/**
	 * 获取单户家庭所有成员审批列表
	 * @param pid
	 * @param fmid
	 * @param mode
	 * @param schildflag
	 * @return
	 */
	public StringBuffer getMemberCheckItemsTable(String pid,String fmid,String mode,String childflag) {
		StringBuffer shtml= new StringBuffer("");
		String html = "",temp = "";
		//
		//		
		html += "<table id = 'memberstb"+fmid+"' width='90%' cellpadding='0' cellspacing='0'>";
		html += "<tr style='color: #6BA6FF;text-align:center;font-size:12px;'>";						
			temp ="<td height='18' colspan = '10'>符合救助标准成员列表</td>";
			html +=temp;
		html +="</tr>";
		html += "<tr style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>";						
			temp ="<td height='18'>操作</td>";
			html +=temp;
			temp ="<td height='18'>成员编码</td>";
			html +=temp;
			temp ="<td height='18'>姓名</td>";
			html +=temp;
			temp ="<td height='18'>年龄</td>";
			html +=temp;
			temp ="<td height='18'>上期救助金</td>";
			html +=temp;
			temp ="<td height='18'>拟发救助金</td>";
			html +=temp;
			temp ="<td height='18'>基本救助金</td>";
			html +=temp;
			temp ="<td height='18'>相关信息</td>";
			html +=temp;
			temp ="<td height='18'>救助标准</td>";
			html +=temp;
			temp ="<td height='18'>核算公式</td>";
			html +=temp;
		html +="</tr>";
		//
		//
        String sql = "select a.optcheck_id as checkid," +
        		"a.countmoney as zje," +
        		"a.checkmoney as czje," +
        		"a.recheckmoney as rezje," +
        		"b.member_id as memid," +
        		"b.membername as memname," +
        		"b.age as age " +
        		"from biz_t_optcheck a, info_t_member b " +
        		"where a.member_id = b.member_id " +
        		"and a.policy_id = '"+pid+"' and a.family_id = '"+fmid+"'";   //定义SQL语句
        
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
            	String checkid = rs.getString("checkid");
            	String memid = rs.getString("memid");
            	String memname = rs.getString("memname");
            	String age = rs.getString("age");
            	String zje = rs.getString("zje");
            	String czje = rs.getString("czje");
            	String rezje = rs.getString("rezje");
            	html +="<tr>";		
		    		//各列值
            		html += "<td height='23' class='colValue pointer'\"><img id = 'img' src='/db/page/images/check1.jpg' alt= '评议意见' onclick=\"ChioceDo(this,'"+checkid+"','"+memname+"')\"/></td>";
					html += "<td height='23' class='colValue'>"+memid+"</td>";
					html += "<td height='23' class='colValue'>"+memname+"</td>";
					html += "<td height='23' width='80' class='colValue'>"+age+"</td>";
					html += "<td height='23' width='80' class='colValue'>"+rezje+"</td>";
					html += "<td height='23' width='80' class='colValue'><span id = 'countmoney"+checkid+"'>"+zje+"</span></td>";
					//审批用户可以修改救助金标识
					//id = 'usercheck"+checkid+"'(用户)审批表ID
					//id = 'usermoney"+checkid+"'(用户)审批表金额
					if("1".equals(mode)){
						html += "<td height='23' width='80' class='colValue'>";
							html += "<span style='display:none;' id = 'usercheck"+checkid+"'></span>";
							html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+czje+"' id = 'usermoney"+checkid+"' onchange=\"SumMoneyFamily('"+fmid+"','"+checkid+"','"+childflag+"')\"></input>";
						html += "</td>";
					}else{
						html += "<td height='23' width='80' class='colValue'>";
							html += "<span style='display:none;' id = 'usercheck"+checkid+"'></span>";
							html += "<input size = '8' style = 'display:none;text-align:right;' type='text' value = '"+czje+"' id = 'usermoney"+checkid+"'></input>";
						html += ""+zje+"</td>";						
					}
					html += "<td height='23' width='50' class='colValue pointer'\">";
						html += "<img src='/db/page/images/policyinfo.jpg' alt= '相关信息' onclick=\"GetCheckPolicyInfosHtml('"+fmid+"','"+memid+"','"+memname+"')\"/>";
					html += "</td>";
					html += "<td height='23' width='50' class='colValue pointer'\">";
						html += "<img src='/db/page/images/policysql.jpg' alt= '救助标准' onclick=\"GetCheckPolicySqlsHtml('"+checkid+"','"+memname+"')\"/>";
					html += "</td>";
					html += "<td height='23' width='50' class='colValue pointer'\">";
						html += "<img src='/db/page/images/policyacc.jpg' alt= '核算公式' onclick=\"GetCheckPolicyAccsHtml('"+checkid+"','"+memname+"')\"/>";
					html += "</td>";
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
	    html +="</table>"; 
		//
	    shtml.append(html);
	    //log.debug(shtml);
		return shtml;
	}
	/**
	 * 获取家庭和成员分类施保审批列表
	 * @param pid
	 * @param fmid
	 * @param empid
	 * @return
	 */
	public StringBuffer getChildCheckItemsTable(String pid,String fmid,String empid) {
		StringBuffer shtml= new StringBuffer("");
		String html = "",temp = "";
		String empdepth = "";
		boolean brow = false;
		//
    	String sdeptname1 = "",sdeptname2 = "",sdeptname3 = "",sdeptname4 = "",sdeptname5 = "";
    	String dempth1 = "",dempth2 = "",dempth3 = "",dempth4 = "",dempth5 = "";
    	String sflow1 = "",sflow2 = "",sflow3 = "",sflow4 = "",sflow5 = "";
    	//
		//
		//常量定义
	    ConstantDefine constantdefine = new ConstantDefine();
	    // 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //业务审批查询处理类
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//本级机构
	    empdepth = tableinfoquery.getempdepth(empid);
	    //社区级别
    	sdeptname1 = constantdefine.POLICYQUERY_5;
    	dempth1 = constantdefine.POLICYQUERYCODE_5;
    	sflow1 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth1);
    	//街道级别
    	sdeptname2 = constantdefine.POLICYQUERY_4;
    	dempth2 = constantdefine.POLICYQUERYCODE_4;
    	sflow2 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth2);
    	//区级别
    	sdeptname3 = constantdefine.POLICYQUERY_3;
    	dempth3 = constantdefine.POLICYQUERYCODE_3;
    	sflow3 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth3);
    	//市级别
    	sdeptname4 = constantdefine.POLICYQUERY_2;
    	dempth4 = constantdefine.POLICYQUERYCODE_2;
    	sflow4 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth4);
    	//省级别
    	sdeptname5 = constantdefine.POLICYQUERY_1;
    	dempth5 = constantdefine.POLICYQUERYCODE_1;
    	sflow5 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth5); 
	    //
		//
		html += "<table id = 'childstb"+fmid+"' width='90%' cellpadding='0' cellspacing='0'>";
		html += "<tr style='color: #6BA6FF;text-align:center;font-size:12px;'>";						
			temp ="<td height='18' colspan = '10'>符合分类施保救助标准列表</td>";
			html +=temp;
		html +="</tr>";
		html += "<tr style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>";						
			temp ="<td height='18'>操作</td>";
			html +=temp;
			temp ="<td height='18'>分类施保名称</td>";
			html +=temp;
			temp ="<td height='18'>核算方式</td>";
			html +=temp;
			temp ="<td height='18'>姓名</td>";
			html +=temp;			
			if(sflow1.equals("1")){
				temp ="<td height='18'>"+sdeptname1+"</td>";
				html +=temp;
			}
			if(sflow2.equals("1")){
				temp ="<td height='18'>"+sdeptname2+"</td>";
				html +=temp;
			}
			if(sflow3.equals("1")){
				temp ="<td height='18'>"+sdeptname3+"</td>";
				html +=temp;
			}
			if(sflow4.equals("1")){
				temp ="<td height='18'>"+sdeptname4+"</td>";
				html +=temp;
			}
			if(sflow5.equals("1")){
				temp ="<td height='18'>"+sdeptname5+"</td>";
				html +=temp;
			}
		html +="</tr>";		
		//
        String sql = "select optcheckchild_id," +
        		"c.name," +
        		"c.sqltype," +
        		"a.membername," +
        		"itemmoney," +
        		"itemmoney1," +
        		"itemmoney2," +
        		"itemmoney3," +
        		"itemmoney4," +
        		"itemmoney5 " +
        		"from info_t_member a, biz_t_optcheckchild b, doper_t_policychild c " +
        		"where a.member_id = b.member_id " +
        		"and b.policychild_id = c.policychild_id " +
        		"and c.policy_id = '"+pid+"' " +
        		"and b.family_id = '"+fmid+"'";   //定义SQL语句
        
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
            	brow = true;
            	//
            	String checkid = rs.getString("optcheckchild_id");
            	String name = rs.getString("name");
            	String sqltype = rs.getString("sqltype");
            	String memname = rs.getString("membername");
            	String money = rs.getString("itemmoney");
            	String money1 = rs.getString("itemmoney1");
            	String money2 = rs.getString("itemmoney2");
            	String money3 = rs.getString("itemmoney3");
            	String money4 = rs.getString("itemmoney4");
            	String money5 = rs.getString("itemmoney5");
            	html +="<tr>";		
		    		//各列值
            		html += "<td height='23' class='colValue pointer'\"><img id = 'img' src='/db/page/images/check1.jpg' alt= '评议意见' onclick=\"ChioceDoChild(this,'"+checkid+"','"+memname+"')\"/></td>";
					html += "<td height='23' class='colValue pointer' style='color: #660099;' onclick=\"GetCheckPolicyChildSqlsHtml('"+checkid+"','"+name+"')\">"+name+"</td>";
					if("2".equals(sqltype)){
						sqltype = "成员核算";
					}else{
						sqltype = "家庭核算";						
					}
					html += "<td height='23' class='colValue'>"+sqltype+"</td>";
					html += "<td height='23' width='80' class='colValue'>"+memname+"</td>";
					//审批用户可以修改救助金标识
					//id = 'childcheck"+checkid+"'(用户)审批表ID
					//id = 'childmoney"+checkid+"'(用户)审批表金额
					//
					if(sflow1.equals("1")){
	                	if(empdepth.equals(dempth1)){
	                		html += "<td height='23' width='80' class='colValue'>";
		                		html += "<span style='display:none;' id = 'childcheck"+checkid+"'></span>";
								html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+money+"' id = 'childmoney"+checkid+"' onchange=\"SumChildMoneyFamily('"+fmid+"')\"></input>";
							html += "</td>";	
	                	}else{
	                		html += "<td height='23' width='80' class='colValue'>"+money1+"</td>";	
	                	}            	
	    			}
	                if(sflow2.equals("1")){
	                	if(empdepth.equals(dempth2)){
	                		html += "<td height='23' width='80' class='colValue'>";
		                		html += "<span style='display:none;' id = 'childcheck"+checkid+"'></span>";
								html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+money+"' id = 'childmoney"+checkid+"' onchange=\"SumChildMoneyFamily('"+fmid+"')\"></input>";
							html += "</td>";	
	                	}else{
	                		html += "<td height='23' width='80' class='colValue'>"+money2+"</td>";	
	                	}            	
	    			}
	    			if(sflow3.equals("1")){
	                	if(empdepth.equals(dempth3)){
	                		html += "<td height='23' width='80' class='colValue'>";
		                		html += "<span style='display:none;' id = 'childcheck"+checkid+"'></span>";
								html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+money+"' id = 'childmoney"+checkid+"' onchange=\"SumChildMoneyFamily('"+fmid+"')\"></input>";
							html += "</td>";	
	                	}else{
	                		html += "<td height='23' width='80' class='colValue'>"+money3+"</td>";	
	                	}            	
	    			}
	    			if(sflow4.equals("1")){
	                	if(empdepth.equals(dempth4)){
	                		html += "<td height='23' width='80' class='colValue'>";
		                		html += "<span style='display:none;' id = 'childcheck"+checkid+"'></span>";
								html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+money+"' id = 'childmoney"+checkid+"' onchange=\"SumChildMoneyFamily('"+fmid+"')\"></input>";
							html += "</td>";	
	                	}else{
	                		html += "<td height='23' width='80' class='colValue'>"+money4+"</td>";	
	                	}            	
	    			}
	    			if(sflow5.equals("1")){
	                	if(empdepth.equals(dempth5)){
	                		html += "<td height='23' width='80' class='colValue'>";
		                		html += "<span style='display:none;' id = 'childcheck"+checkid+"'></span>";
								html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+money+"' id = 'childmoney"+checkid+"' onchange=\"SumChildMoneyFamily('"+fmid+"')\"></input>";
							html += "</td>";	
	                	}else{
	                		html += "<td height='23' width='80' class='colValue'>"+money5+"</td>";	
	                	}            	
	    			}
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        
        //
	    html +="</table>";  
		//
	    //
	    if(brow){
	    	shtml.append(html);
	    }else{
	    	shtml.append("无满足分类施保标准记录!");
	    }
	    //log.debug(shtml);
		return shtml;
	}
}
