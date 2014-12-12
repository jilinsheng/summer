package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
import com.mingda.common.ConstantDefine;

public class PolicyManageEdit {
	static Logger log = Logger.getLogger(PolicyManageEdit.class);
	/**
     * 取得业务列表
     * @return
     */
    public StringBuffer getPolicysHtml() {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String sid = "",sname = "",sflag = "",smenuty = ""; 
    	//常量定义
    	ConstantDefine constantdefine = new ConstantDefine();
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>业务列表</legend>";
		//
		html += "<button class = 'btn' onclick=\"GetPolicyItemHtml('')\">新增业务</button>"; 
		//
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>名称</td>";
			html +=temp;
			temp ="<td height='23'>救助业务分类</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select policy_id, name,flag,menutype from doper_t_policy order by flag desc";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	sid = rs.getString("policy_id");
            	sname = rs.getString("name");
            	sflag = rs.getString("flag");
            	smenuty = rs.getString("menutype");
                
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+sid+"','"+sname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					if(smenuty.equals(constantdefine.POLICYMENUCODE_0)){
						smenuty = 	constantdefine.POLICYMENU_0;					
					}else if(smenuty.equals(constantdefine.POLICYMENUCODE_1)){
						smenuty = 	constantdefine.POLICYMENU_1;	
					}
					else if(smenuty.equals(constantdefine.POLICYMENUCODE_2)){
						smenuty = 	constantdefine.POLICYMENU_2;	
					}
					else if(smenuty.equals(constantdefine.POLICYMENUCODE_3)){
						smenuty = 	constantdefine.POLICYMENU_3;	
					}
					html += "<td height='23' class='colValue'>"+smenuty+"</td>";
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updatePolicyStatus('" + sid + "','" + sname + "','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updatePolicyStatus('" + sid + "','" + sname + "','1')\">启用</button></td>";	
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
	    	html += "<button class = 'btn' onclick=\"GetPolicyItemHtml('')\">新增业务</button>"; 
	    html += "</fieldset>";
	       
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * 业务属性
     * @param pid
     * @return
     */
    public StringBuffer getPolicyItemHtml(String pid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",stitle = "",temp = "",mode = "";
    	String spid = "",sql = "";
    	String objtype = "",menutype = "",name = "",descr = "",type = "",acctu = "",acctype = "";
    	String cycle = "",cycnum = "",isprint = "",begdt = "",note = "",flag = "";
    	//
    	//政策业务查询处理类
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
	  	//
    	if(pid.equals("")){
    		mode = "addpolicy";
    		stitle = "[新增]";
    	}else{
    		mode = "editpolicy";
    		//
        	sql = "select policy_id,objtype,menutype,name,descr,type,acctype," +
        			"acctu,cycle,cycnum,isprint,begdt,note,flag " +
        			"from doper_t_policy where policy_id = '" + pid + "'";   //定义SQL语句
        	//log.debug(sql);
            Connection conn = null;                 //声明Connection对象
            PreparedStatement pstmt = null;         //声明PreparedStatement对象
            ResultSet rs = null;                    //声明ResultSet对象
            try {
                conn = DBUtils.getConnection();     //获取数据库连接
                pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	spid = rs.getString("policy_id");
                	objtype = rs.getString("objtype");
                	menutype = rs.getString("menutype");
                	//
                	name = rs.getString("name");
                	stitle = "["+name+"]";
                	//
                	descr = rs.getString("descr");
                	type = rs.getString("type");
                	acctype = rs.getString("acctype");
                	acctu = rs.getString("acctu"); 
                	cycle = rs.getString("cycle");
                	cycnum = rs.getString("cycnum");
                	isprint = rs.getString("isprint"); 
                	begdt = rs.getString("begdt");
                	note = rs.getString("note");
                	flag = rs.getString("flag"); 
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
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"业务属性</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>所属业务</td>";				
			html += "<td><div id = 'divpmenu'>";						
			    	temp = getPolicyChoiceHtml("pmenu","-2",menutype).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>核算类型</td>";
			html += "<td style = 'text-align:center;font-size:12px;'>" ;
				if(acctype.equals("1")){//成员核算
					html += "<input type='radio' name = 'rdacc' id = 'rdaccf'>家庭</>"; 
					html += "<input type='radio' name = 'rdacc' id = 'rdaccm' checked>成员</>"; 
				}else{
					html += "<input type='radio' name = 'rdacc' id = 'rdaccf' checked>家庭</>"; 
					html += "<input type='radio' name = 'rdacc' id = 'rdaccm'>成员</>"; 					
				}
				
			html += "</td>";
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>名称</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'pname' value = '"+name+"'></input></td>";
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>业务分类</td>";				
			html += "<td><div id = 'divpobjtype'>";						
			    	temp = getPolicyChoiceHtml("pobjtype","-1",objtype).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>救助类型</td>";				
			html += "<td><div id = 'divptype'>";						
			    	temp = getPolicyChoiceHtml("ptype","111",type).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>实施机构</td>";				
			html += "<td><div id = 'divpacctu'>";						
			    	temp = getPolicyChoiceHtml("pacctu","112",acctu).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>结算周期</td>";				
			html += "<td><div id = 'divpcycle'>";						
			    	temp = getPolicyChoiceHtml("pcycle","113",cycle).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//		    
		html +="<tr>";
			//日期格式化
			if(begdt.equals("") || begdt == null){
				begdt = "";
			}else{
				String tempdt = begdt;
		        begdt = policymanagecheckquery.getformatdt(tempdt);
			}    
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>开始日期</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'pbegdt' value = '"+begdt+"' onFocus= 'setday(this)'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>是否打证</td>";
			if(isprint.equals("1")){
				html += "<td style = 'text-align:center;font-size:12px;'><input type='checkbox' id='pisprint' checked >需要打证</input></td>";
			}else{
				html += "<td style = 'text-align:center;font-size:12px;'><input type='checkbox' id='pisprint'>需要打证</input></td>";
			}
			
		html +="</tr>";		
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>周期(月)</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'pcycnum' value = '"+cycnum+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>详细描述</td>";
			html += "<td><textarea rows='3' style = 'width:100%' type='text' id = 'pdescr' >"+descr+"</textarea></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>备注</td>";
			html += "<td><textarea rows='2' style = 'width:100%' type='text' id = 'pnote' >"+note+"</textarea></td>";
		html +="</tr>";
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("addpolicy")){//新增
	    	html += "<button class = 'btn' onclick=\"SavePolicy('"+mode+"')\">保存</button>"; 	   		
    	}else if(mode.equals("editpolicy")){//编辑
    		//在用时可以设置
	    	if(flag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"policyInfo()\">相关信息</button>";
	    		html += "<button class = 'btn' onclick=\"policyFlowDefine()\">审批流程</button>";	    		
	    		html += "<button class = 'btn' onclick=\"policyCheckPower()\">审批权限</button>";
	    		html += "<button class = 'btn' onclick=\"SavePolicy('"+mode+"')\">保存</button>";
	    		html += "<button class = 'btn' onclick=\"GoMatchPage('next')\">下一步>>标准设置</button>";  
	    	}		    	
    	}
    	//	
    	html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	   
        return shtml;
    }
    /**
     * 由业务ID和名称取得标准列表
     * @param pid
     * @param pname
     * @return
     */
    public StringBuffer getPolicySqlsHtml(String pid,String pname,String jdeptid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String sid = "",sdept = "",sname = "",sflag = "",smoney = "",saccdesc = "",sphysql = ""; 
    	//	    
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>["+pname+"]标准列表</legend>";
		//
		html += "<button class = 'btn' onclick=\"GoMatchPage('back')\">"+pname+"<<上一步</button>"; 
	    html += "<button class = 'btn' onclick=\"GetPolicySqlItemHtml('')\">新增业务标准</button>";
	    //
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html +="<tr style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>";
			html += "<td width='100px'>所属机构</td>";
			html += "<td colspan = '5'  class='colValue'>";
			html += "<input style = 'width:100%' type='text' id = 'sqdeptname' value = '' onclick=\"queryDept('qsql')\"></input>";
			html += "<input type='text' id='sqdeptid' style='display:none' value = ''></input>" ;
			html += "</td>";
			html += "<td width='100px'  class='colValue'>" ;
			html += "<button class = 'btn' onclick=\"GetPolicySqlsHtml('"+pid+"','"+pname+"')\">查询</button>" ;
			html += "</td>";
		html +="</tr>";
		html +="</table>";
		html += "<table id = 'listsstb' width='100%' cellpadding='0' cellspacing='0'>";		
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>所属机构</td>";
			html +=temp;
			temp ="<td height='23'>标准名称</td>";
			html +=temp;
			temp ="<td height='23'>标准数额</td>";
			html +=temp;
			temp ="<td height='23'>设置标准</td>";
			html +=temp;
			temp ="<td height='23'>核算公式</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select " 
        	+ "standard_id," 
        	+ "descr,flag," 
        	+ "planmoney," 
        	+ "plandesc," 
        	+ "physql," 
        	+ "b.organization_id," 
        	+ "b.fullname " 
        	+ "from doper_t_standard a, sys_t_organization b " 
        	+ "where a.organization_id = b.organization_id " 
        	+ "and policy_id = '"+pid+"' " 
        	+ "and a.organization_id like '"+jdeptid+"%' "
        	+ "order by a.organization_id," 
        	+ "planmoney," 
        	+ "nlssort(descr, 'NLS_SORT=SCHINESE_PINYIN_M') desc";   //定义SQL语句
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
            	sid = rs.getString("standard_id");
            	sdept = rs.getString("fullname");
            	sname = rs.getString("descr");
            	sflag = rs.getString("flag");
            	smoney = rs.getString("planmoney");
            	saccdesc = rs.getString("plandesc");
            	sphysql = rs.getString("physql");
            	
                //
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDoSql(this,'"+sid+"','"+sdept+"','"+sname+"','"+smoney+"','"+saccdesc+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sdept+"</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+smoney+"</td>";
					//是否设置标准
					if("".equals(sphysql) || null == sphysql){
						html += "<td height='23' class='colValue status"+sflag+"'><span style = 'color: #6BA6FF;'>无</span></td>";
					}else{
						html += "<td height='23' class='colValue status"+sflag+"'><span>有</span></td>";
					}
					//是否至少设置一个机构核算公式
	            	boolean bdeptacc = existsPolicyDeptAccFromStandardID(sid);
	            	if(!bdeptacc){
						html += "<td height='23' class='colValue status"+sflag+"'><span style = 'color: #6BA6FF;'>无</span></td>";
					}else{
						html += "<td height='23' class='colValue status"+sflag+"'><span>有</span></td>";
					}
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateStandardStatus('"+sid+"','"+pname+"','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateStandardStatus('"+sid+"','"+pname+"','1')\">启用</button></td>";	
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
	    	html += "<button class = 'btn' onclick=\"GoMatchPage('back')\">"+pname+"<<上一步</button>"; 
	 	    html += "<button class = 'btn' onclick=\"GetPolicySqlItemHtml('')\">新增业务标准</button>";	 	    
	 	    //
	    html += "</fieldset>";
	    //
	   
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * 获取标准属性
     * @param id
     * @return
     */
    public StringBuffer getPolicySqlItemHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",stitle = "",temp = "",mode = "";
    	String sql = "",sid = "",sdeptname = "",sdeptid = "",sname = "",sflag = "",splanmoney = "",splandesc = "",superpolicy = "",snotpolicy = ""; 
	  	//
    	if(id.equals("")){
    		mode = "addstandard";
    		stitle = "[新增]";
    	}else{
    		mode = "editstandard";
    		//
    		sql = "select standard_id,"
    	       + "policy_id,"
    	       + "descr,"
    	       + "physql,"
    	       + "locsql,"
    	       + "planmoney,"
    	       + "plandesc,"
    	       + "flag,"
    	       + "superpolicy,"    	       
    	       + "notpolicy,"
    	       + "b.organization_id,"
    	       + "b.fullname "
    	       + "from doper_t_standard a, sys_t_organization b "
    	       + "where a.organization_id = b.organization_id " 
    	       + "and standard_id = '" + id + "' ";   //定义SQL语句
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
                	sid = rs.getString("standard_id");
                	sname = rs.getString("descr");
                	stitle = "["+sname+"]";
                	sdeptname = rs.getString("fullname");
                	sdeptid= rs.getString("organization_id");
                	superpolicy = rs.getString("superpolicy");
                	snotpolicy = rs.getString("notpolicy");
                	splanmoney = rs.getString("planmoney");
                	splandesc = rs.getString("plandesc");
                	sflag = rs.getString("flag");
                }
            } catch (SQLException e) {
                log.debug(e.toString());
            } finally {
                DBUtils.close(rs);                  //关闭结果集
                DBUtils.close(pstmt);               //关闭PreparedStatement
                //DBUtils.close(conn);                //关闭连接
            }
    	}
    	if(null == superpolicy || superpolicy.equals("") || "null".equals(superpolicy)){
    		superpolicy = "";
		}
    	if(null == splanmoney || splanmoney.equals("") || "null".equals(splanmoney)){
    		splanmoney = "";
		}
    	if(null == splandesc || splandesc.equals("") || "null".equals(splandesc)){
    		splandesc = "";
		}
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"标准属性</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>所属机构</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'sdeptname' value = '"+sdeptname+"' onclick=\"queryDept('sql')\"></input>";
			html += "<input type='text' id='sdeptid' style='display:none' value = '"+sdeptid+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>所属业务</td>";
			html += "<td><div>"+getPolicyChoiceSelHtml("listspolicy").toString();
			html += "<button class = 'btn' onclick=\"ChoiceSupPolicy()\">添加</button>";
			html += "<button class = 'btn' onclick=\"ClearSupPolicy()\">置空</button>";
			html += "<input style = 'width:100%' type='text' id = 'ssuperpolicy' value = '"+superpolicy+"' onclick=''></input></div></td>";
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>不属业务</td>";
			html += "<td><div>"+getPolicyChoiceSelHtml("listsnotpolicy").toString();
			html += "<button class = 'btn' onclick=\"ChoiceNotPolicy()\">添加</button>";
			html += "<button class = 'btn' onclick=\"ClearNotPolicy()\">置空</button>";
			if(null == snotpolicy){
				snotpolicy = "";
			}
			html += "<input style = 'width:100%' type='text' id = 'snotpolicy' value = '"+snotpolicy+"' onclick=''></input></div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>标准名称</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'sdescr' value = '"+sname+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>标准数额</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'splanmoney' value = '"+splanmoney+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>标准描述</td>";
			html += "<td><textarea rows='3' style = 'width:100%' type='text' id = 'splandesc' >"+splandesc+"</textarea></td>";
		html +="</tr>";
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("addstandard")){//新增
	    	html += "<button class = 'btn' onclick=\"SaveStandard('"+mode+"')\">保存</button>"; 	   		
    	}else if(mode.equals("editstandard")){//编辑
    		//在用时可以设置
	    	if(sflag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"sqlaction()\">标准设置</button>";
	    		html += "<button class = 'btn' onclick=\"SaveStandard('"+mode+"')\">保存</button>";
	    		if(existsPolicyStrandardSql(sid)){
	    			html += "<button class = 'btn' onclick=\"GoMatchPage('nextsql')\">下一步>>机构标准设置</button>";
	    		}	    		  
	    	}		    	
    	}
    	//	
    	html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	   
        return shtml;
    }
    /**
     * 取得机构标准列表
     * @param sid
     * @param sname
     * @param smoney
     * @param saccdesc
     * @return
     */
    public StringBuffer getPolicyDeptsHtml(String sid,String deptname,String sname,String smoney,String saccdesc) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String did = "",dname = "",dflag = "",dmoney = "",ddesc = ""; 
    	
		//    	
		//
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>["+sname+"]机构标准列表</legend>";
		//
		//
	    html += "<button class = 'btn' onclick=\"GoMatchPage('backsql')\">"+sname+"<<上一步</button>"; 
	    html += "<button class = 'btn' onclick=\"GetPolicyDeptItemHtml('')\">新增机构标准</button>";
	    
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";			
			temp ="<td height='23'>所属机构</td>";
			html +=temp;
			temp ="<td height='23'>标准名称</td>";
			html +=temp;
			temp ="<td height='23'>标准数额</td>";
			html +=temp;
			temp ="<td height='23'>标准描述</td>";
			html +=temp;
		html +="</tr>";
		html += "<tr>";
		temp ="<td height='23' class='colValue'>"+deptname+"</td>";
		html +=temp;
		temp ="<td height='23' class='colValue'>"+sname+"</td>";
		html +=temp;		
		temp ="<td height='23' class='colValue'>"+smoney+"</td>";
		html +=temp;
		temp ="<td height='23' class='colValue'>"+saccdesc+"</td>";
		html +=temp;
		html +="</tr>";
		html +="</table>";
		//
		html += "<table id = 'listsdtb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>机构名称</td>";
			html +=temp;
			temp ="<td height='23'>标准数额</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select standarddept_id,fullname,a.checkmoney,a.checkdesc,a.accdesc,a.flag " +
		        "from doper_t_standarddept a,sys_t_organization b " +
		        "where a.organization_id = b.organization_id and standard_id = '"+sid+"' order by b.organization_id";   //定义SQL语句
        
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
            	did = rs.getString("standarddept_id");
            	dname = rs.getString("fullname");
            	dmoney = rs.getString("checkmoney");
            	ddesc = rs.getString("accdesc");
            	dflag = rs.getString("flag");            	
            	
                //
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDoDept(this,'"+did+"','"+dname+"','"+dmoney+"','"+ddesc+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+dflag+"'>"+dname+"</td>";
					html += "<td height='23' class='colValue'>"+dmoney+"</td>";
					if(dflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDeptStatus('"+did+"','"+dname+"','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDeptStatus('"+did+"','"+dname+"','1')\">启用</button></td>";	
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
		    html += "<button class = 'btn' onclick=\"GoMatchPage('backsql')\">"+sname+"<<上一步</button>"; 
		    html += "<button class = 'btn' onclick=\"GetPolicyDeptItemHtml('')\">新增机构标准</button>";
		    //
	    html += "</fieldset>";
	    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * 获取标准机构属性
     * @param id
     * @return
     */
    public StringBuffer getPolicyDeptItemHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",stitle = "",temp = "",mode = "";
    	String sql = "",did = "",ddeptid = "",dname = "",ddesc = "",dmoney = "",daccdesc = "",daccexp = "",dflag = "";  
	  	//
    	if(id.equals("")){
    		mode = "adddept";
    		stitle = "[新增]";
    	}else{
    		mode = "editdept";
    		//
        	sql = "select standarddept_id,a.organization_id,fullname,checkmoney,checkdesc,accexplocsql,accexp,accdesc,a.flag " +
               "from doper_t_standarddept a,sys_t_organization b  " +
               "where a.organization_id = b.organization_id and standarddept_id = '" + id + "'";   //定义SQL语句
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
                	did = rs.getString("standarddept_id");
                	ddeptid = rs.getString("organization_id");
                	dname = rs.getString("fullname");
                	stitle = "["+dname+"]";
                	ddesc = rs.getString("checkdesc");
                	dmoney = rs.getString("checkmoney");                	
                	daccdesc = rs.getString("accdesc");
                	daccexp = rs.getString("accexp");
                	dflag = rs.getString("flag");
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
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"机构标准属性</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>所属机构</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'ddeptname' value = '"+dname+"' onclick=\"queryDept('acc')\"></input>";
			html += "<input type='text' id='ddeptid' style='display:none' value = '"+ddeptid+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>数额描述</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'dcheckdesc' value = '"+ddesc+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>标准数额</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'dcheckmoney' value = '"+dmoney+"'></input></td>";
		html +="</tr>";
		//		
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>核算公式描述</td>";
			html += "<td><textarea style='width:100%' rows='4' id='daccdesc'>"+daccdesc+"</textarea></td>";
		html +="</tr>";
		//
		//		
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("adddept")){//新增
	    	html += "<button class = 'btn' onclick=\"SaveDept('"+mode+"')\">保存</button>"; 	   		
    	}else if(mode.equals("editdept")){//编辑
    		//在用时可以设置
	    	if(dflag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"deptaction()\">核算公式设置</button>";
	    		html += "<button class = 'btn' onclick=\"SaveDept('"+mode+"')\">保存</button>";  
	    	}		    	
    	}
    	//	
    	html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	   
        return shtml;
    }
    /**
     * 取得业务属性类型下拉框
     * @param sname
     * @param discid
     * @return
     */
    public StringBuffer getPolicyChoiceHtml(String sname,String discid,String seldiscid){
        StringBuffer shtml= new StringBuffer("");
        String id = "",name = "";
        
        //常量定义
        ConstantDefine constantdefine = new ConstantDefine();
        if(discid.equals(constantdefine.POLICYMENUCODE)){//救助业务       
            shtml.append("<select id=\""+sname+"\" style = 'width:100%'>");
            if(seldiscid.equals(constantdefine.POLICYMENUCODE_0)){
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_0+"\"selected>"+constantdefine.POLICYMENU_0+"</option>");
            }else{
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_0+"\">"+constantdefine.POLICYMENU_0+"</option>");
            }
            if(seldiscid.equals(constantdefine.POLICYMENUCODE_1)){
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_1+"\"selected>"+constantdefine.POLICYMENU_1+"</option>");
            }else{
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_1+"\">"+constantdefine.POLICYMENU_1+"</option>");
            }
            if(seldiscid.equals(constantdefine.POLICYMENUCODE_2)){
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_2+"\"selected>"+constantdefine.POLICYMENU_2+"</option>");
            }else{
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_2+"\">"+constantdefine.POLICYMENU_2+"</option>");
            }
            if(seldiscid.equals(constantdefine.POLICYMENUCODE_3)){
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_3+"\"selected>"+constantdefine.POLICYMENU_3+"</option>");
            }else{
            	shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_3+"\">"+constantdefine.POLICYMENU_3+"</option>");
            }
            shtml.append("</select>");
        }else if(discid.equals(constantdefine.POLICYOBJTYCODE)){//业务分类        
 	        shtml.append("<select id=\""+sname+"\" style = 'width:100%'>");
 	        if(seldiscid.equals(constantdefine.POLICYOBJTYCODE_0)){
 	        	shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_0+"\"selected>"+constantdefine.POLICYOBJTY_0+"</option>");
 	        }else{
           		shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_0+"\">"+constantdefine.POLICYOBJTY_0+"</option>");
 	        }
 	        if(seldiscid.equals(constantdefine.POLICYOBJTYCODE_1)){
	        	shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_1+"\"selected>"+constantdefine.POLICYOBJTY_1+"</option>");
	        }else{
          		shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_1+"\">"+constantdefine.POLICYOBJTY_1+"</option>");
	        }
 	        if(seldiscid.equals(constantdefine.POLICYOBJTYCODE_2)){
	        	shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_2+"\"selected>"+constantdefine.POLICYOBJTY_2+"</option>");
	        }else{
         		shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_2+"\">"+constantdefine.POLICYOBJTY_2+"</option>");
	        }
 	        shtml.append("</select>");
        }else{//数据库字典
         String sql = "select dictionary_id,item from sys_t_dictionary  " +
                 "where status = '1' and dictsort_id = '"+discid+"' order by sequence";
          
          	//
         	shtml.append("<select id=\""+sname+"\" style = 'width:100%'>");
         	Connection conn = null;                 //声明Connection对象
         	PreparedStatement pstmt = null;         //声明PreparedStatement对象
         	ResultSet rs = null;                    //声明ResultSet对象
         	try {
         		conn = DBUtils.getConnection();     //获取数据库连接
         		pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
         		//参数
         		rs = pstmt.executeQuery();
              
         		while (rs.next()) {  
         			id = rs.getString("dictionary_id");
         			name = rs.getString("item");
         			if(id.equals(seldiscid)){
         				shtml.append("<option value='"+id+"' selected>"+name+"</option>");
         			}else{
         				shtml.append("<option value='"+id+"'>"+name+"</option>");
         			}                
         		}
         	} catch (SQLException e) {
         		log.debug(e.toString());
         	} finally {
         		DBUtils.close(rs);                  //关闭结果集
         		DBUtils.close(pstmt);               //关闭PreparedStatement
         		//DBUtils.close(conn);                //关闭连接
         	}
          	shtml.append("</select>");
        }
        return shtml;
    }    
    /**
     * 取得业务选择名称下拉框
     * @param sname
     * @return
     */
    public StringBuffer getPolicyChoiceSelHtml(String sname){
        StringBuffer shtml= new StringBuffer("");
        String id = "",name = "";
        
        String sql = "select policy_id, name from doper_t_policy where flag = '1' order by policy_id";      
        //log.debug(sql);
        shtml.append("<select id=\""+sname+"\" style = \"font-size:12px\">"); 
        //
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
        	conn = DBUtils.getConnection();     //获取数据库连接
        	pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
        	//参数
        	rs = pstmt.executeQuery();
  
        	while (rs.next()) {  
        		id = rs.getString("policy_id");
        		name = rs.getString("name");
        		shtml.append("<option value=\""+name+"\">"+name+"</option>");
        	}
        } catch (SQLException e) {
        	log.debug(e.toString());
        } finally {
        	DBUtils.close(rs);                  //关闭结果集
        	DBUtils.close(pstmt);               //关闭PreparedStatement
        	//DBUtils.close(conn);                //关闭连接
        }
        shtml.append("</select>");       
        return shtml;
    }
    /**
     * 取得业务标准SQL语句
     * @param id
     * @param mode
     * @return
     */
    public String getPolicySqlItem(String id,String mode) {
        String srep = "";
        String sql = "select physql, locsql from doper_t_standard where standard_id = '" + id + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	if(mode.equals("loc")){
            		srep = rs.getString("locsql");
            	}else if(mode.equals("phy")){
            		srep = rs.getString("physql");
            	}              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     * 更新业务标准SQL条件
     * @param Id
     * @param PhySql
     * @param LocSql
     * @return
     */
    public String updatePolicySql(String Id,String PhySql,String LocSql) {
		String srep = "";
		//单引号转义去掉左右空格
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update doper_t_standard set " +
				"physql = '"+PhySql+"'," +
				"locsql = '"+LocSql+"' where standard_id = '"+Id+"'";  //更新标准状态SQL
		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.executeUpdate();              //执行
		    conn.commit();                      //关闭
		    srep = "更新标准保存操作成功!";
		} catch (SQLException e) {
			srep = "更新标准保存操作失败!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
    /**
     * 取得机构标准SQL语句
     * @param id
     * @param mode
     * 特殊：逻辑语句和核算公式逻辑表达式用;隔开
     * 页面注意处理!!!
     * @return
     */
    public String getPolicyDeptSqlItem(String id,String mode) {
        String srep = "";
        String sql = "select accexpphysql, accexplocsql,accexp from doper_t_standarddept where standarddept_id = '" + id + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	if(mode.equals("loc")){
            		srep = rs.getString("accexplocsql");
            		srep += ";"+rs.getString("accexp");
            	}else if(mode.equals("phy")){
            		srep = rs.getString("accexpphysql");
            	}              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     * 更新业务标准SQL条件
     * @param Id
     * @param PhySql
     * @param LocSql
     * @param LocExp
     * @return
     */
    public String updatePolicyDeptSql(String Id,String PhySql,String LocSql,String LocExp) {
		String srep = "";
		//单引号转义去掉左右空格
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update doper_t_standarddept set " +
				"accexpphysql = '"+PhySql+"'," +
				"accexplocsql = '"+LocSql+"'," +
				"accexp = '"+LocExp+"' " +
				"where standarddept_id = '"+Id+"'";  //更新机构标准状态SQL
		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.executeUpdate();              //执行
		    conn.commit();                      //关闭
		    srep = "更新标准保存操作成功!";
		} catch (SQLException e) {
			srep = "更新标准保存操作失败!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
    /**
     * 是否已经设置标准
     * @param parid
     * @return
     */
    public boolean existsPolicyStrandardSql(String parid){
		boolean  brep = false;
		
		String sql = "";
		sql = "select standard_id from doper_t_standard where physql is not null and  standard_id = '"+parid+"'";
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**********************************************************
     * 业务更新相关操作
     **********************************************************/
    /**
     * 更新业务状态
     * @param ID
     * @param Status
     */
    String updatePolicyStatus(String Id,String Status) {
      String srep = "";
        String sql  = "update doper_t_policy set flag = '"+Status+"' where policy_id = '"+Id+"'";  //更新业务状态SQL
        String sql2 = "update doper_t_standard set flag = '"+Status+"' where policy_id = '"+Id+"'";  //更新档次状态SQL
        String sql3 = "update (select a.flag from doper_t_standarddept a,doper_t_standard b,doper_t_policy c " +
                    "where a.standard_id = b.standard_id and b.policy_id = c.policy_id  and c.policy_id = '"+Id+"') set flag = '"+Status+"' ";   //更新机构状态SQL语句
        //log.debug(sql);
        //log.debug(sql2);
        //log.debug(sql3);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            pstmt = conn.prepareStatement(sql2);//根据sql2创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            pstmt = conn.prepareStatement(sql3);//根据sql3创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            conn.commit();                      //关闭
            //
            //更新业务菜单状态
            updatePolicyMenuStatus(Id,Status);
            //
            if("0".equals(Status)){
              srep = "停用业务操作成功!";
            }else{
              srep = "启用业务操作成功!";
            }
        } catch (SQLException e) {
          try {
            if("0".equals(Status)){
                srep = "停用业务操作失败!";
              }else{
                srep = "启用业务操作失败!";
              }  
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
    /**
     * 更新业务菜单状态
     * @param Id
     * @param Status
     * @return
     */
    String updatePolicyMenuStatus(String Id,String Status) {
    	String srep="",mid = "";
    	//
    	mid = getPolicyMenuId(Id);//业务菜单关系取得菜单ID
    	if(mid.equals("")||mid==null){
    		//
    	}else{
    		//更新业务菜单
            String sql1 = "update sys_t_privilege set status = '"+Status+"' where privilege_id = '"+mid+"'";  //更新SQL
            String sql2 = "update sys_t_privilege set status = '"+Status+"' where parentprivilegeid = '"+mid+"'";  //更新SQL
            Connection conn = null;                 //声明Connection对象
            PreparedStatement pstmt = null;         //声明PreparedStatement对象
            try {
                conn = DBUtils.getConnection();     //获取数据库连接
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql1);//根据sql1创建PreparedStatement            
                pstmt.executeUpdate();              //执行    
                pstmt = conn.prepareStatement(sql2);//根据sql2创建PreparedStatement            
                pstmt.executeUpdate();              //执行
                conn.commit();                      //关闭
                //
                if("0".equals(Status)){
                    srep = "停用业务菜单操作成功!";
                }else{
                    srep = "启用业务菜单操作成功!";
                }
            } catch (SQLException e) {
            	try {
            		if("0".equals(Status)){
                        srep = "停用业务菜单操作失败!";
                    }else{
                        srep = "启用业务菜单操作失败!";
                    }
            		conn.rollback();
            	} catch (SQLException e1) {           
            		e1.printStackTrace();
            	}
            } finally {
                DBUtils.close(pstmt);               //关闭PreparedStatement
                //DBUtils.close(conn);                //关闭连接
            }
    	}
    	return srep;
    }
    /**
     * 是否存在业务菜单关系
     * 返回菜单ID
     * @param pid
     * @return
     */
    public String getPolicyMenuId(String pid){
    	String srep = "";
    	
    	//
    	String sql = "select privilege_id from doper_t_policymenu " +
    			"where policy_id = '"+pid+"' ";
        //
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //参数
            rs = pstmt.executeQuery();
            
            if (rs.next()) {  
            	srep = rs.getString("privilege_id");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }        
    	return srep;
    }
    /**
     * 新增业务
     * @param name
     * @return
     */
    public boolean existsPolicy(String name){
		boolean  brep = false;
		String sql = "";
		
		sql = "select POLICY_ID from DOPER_T_POLICY where NAME = '"+name+"' ";
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 更新业务
     * @param id
     * @param name
     * @return
     */
    public boolean existsUpdatePolicy(String id,String name){
		boolean  brep = false;
		String sql = "",stmp= "";
		
		sql = "select POLICY_ID from DOPER_T_POLICY where NAME = '"+name+"' ";
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				stmp = rs.getString(1);
				if(!id.equals(stmp)){
					brep = true;
				}
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 新增档次验证
     * @param money
     * @param parid
     * @param deptid
     * @return
     */
    public boolean existsStrandard(String money, String parid,String deptid){
		boolean  brep = false;
		String sql = "";
		sql = "select STANDARD_ID from DOPER_T_STANDARD " +
			"where planmoney = '"+money+"' and POLICY_ID = '"+parid+"' and ORGANIZATION_ID = '"+deptid+"'";  //SQL
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 更新档次核算校验
     * @param id
     * @param parid
     * @param deptid
     * @param mode
     * @return
     */
    public boolean existsUpdateStrandard(String id,String money, String parid,String deptid){
		boolean  brep = false;
		String sql = "",stmp = "";
		sql = "select STANDARD_ID from DOPER_T_STANDARD " +
			"where planmoney = '"+money+"' and POLICY_ID= '"+parid+"' and ORGANIZATION_ID = '"+deptid+"'";  //SQL
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				stmp = rs.getString(1);
				if(!id.equals(stmp)){
					brep = true;
				}
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 新增机构核算验证
     * @param parid
     * @param deptid
     * @return
     */
    public boolean existsDept(String parid,String deptid){
		boolean  brep = false;
		String sql = "";
		sql = "select STANDARDDEPT_ID from DOPER_T_STANDARDDEPT " +
			"where STANDARD_ID = '"+parid+"' and ORGANIZATION_ID = '"+deptid+"'";  //SQL
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 更新机构核算校验
     * @param id
     * @param parid
     * @param deptid
     * @return
     */
    public boolean existsUpdateDept(String id,String parid,String deptid){
		boolean  brep = false;
		String sql = "",stmp = "";
		sql = "select STANDARDDEPT_ID from DOPER_T_STANDARDDEPT " +
			"where STANDARD_ID = '"+parid+"' and ORGANIZATION_ID = '"+deptid+"'";  //SQL
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				stmp = rs.getString(1);
				if(!id.equals(stmp)){
					brep = true;
				}
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    
    
    
    /**
     * 更新业务操作
     * @param Sql
     * @param EditType
     * @param Pid
     * @param Pmenu
     * @return
     */
    String updatePolicy(String Sql,String EditType,String Pid,String Pmenu) {
        String srep = "",pname = "";
        
          //log.debug(Sql);
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(Sql); //根据sql创建PreparedStatement   
              pstmt.execute();
              conn.commit(); 
              
              if("addpolicy".equals(EditType)){
                //                
                //取得业务名称
                pname = getPolicyNameFromId(Pid);
                //更新业务菜单
                addPolicyMenus(Pid,pname,Pmenu);
                //
                srep = "新增业务操作成功!";
                //
              }else if("editpolicy".equals(EditType)){
            	  //                
                  //取得业务名称
                  pname = getPolicyNameFromId(Pid);
                  //更新业务菜单
                  updatePolicyMenu(Pid,pname,Pmenu);
                  //
                srep = "修改业务操作成功!";
              }
          } catch (SQLException e) {
            try {
              if("addpolicy".equals(EditType)){
                    srep = "新增业务操作失败!";
                  }else if("editpolicy".equals(EditType)){
                    srep = "修改业务操作失败!";
                  }
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
    /**
     * 由业务编号取得业务名称
     * @param name
     * @return
     */
    public String getPolicyNameFromId(String id) {
        String srep = "";
        String sql = "select policy_id,name from doper_t_policy where policy_id = '" + id + "'";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              srep = rs.getString("name");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     *添加业务菜单
     * @param Pd
     * @param Name
     * @return
     */
    String addPolicyMenus(String Id,String Name,String MenuId) {
    	String srep= "",pid = "",menuid = "",parmenuid = "",pname = "";    	
    	String stempname = "",smname="",scname="",saname = "",snname = "",sqname = "",srname = "";    	
    	String stempurl = "",smurl="",scurl="",saurl = "",snurl = "",squrl = "",srurl = "";
    	//
    	String qmodemid="4",qmodecid="5",qmodeaid = "6",qmodenid = "7",qmodeqid = "8",qmoderid = "9";
    	// 表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	// 
      	pid = Id;
      	smname = "走访调查";
      	scname = "业务审批";
      	saname = "公    示";
      	snname = "异议处理";      	
      	sqname = "业务查询";
      	srname = "统计分析";
     	
      	smurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodemid+"&qpolicy="+pid;
      	scurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodecid+"&qpolicy="+pid;
      	saurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeaid+"&qpolicy="+pid;
      	snurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodenid+"&qpolicy="+pid;
      	squrl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeqid+"&qpolicy="+pid;
      	srurl= "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmoderid+"&qpolicy="+pid;
		//
      	//取得成生菜单表ID
		parmenuid = MenuId;//业务菜单父ID
		menuid = tableinfoquery.getseqfromname("XPRIVILEGE_ID");//业务菜单ID
		//		
		pid = Id;
		pname = Name;
		//更新业务菜单关系
		String sql0  = "insert into doper_t_policymenu (policymenu_id, policy_id, privilege_id) values (xpolicymenu_id.nextval,'"+pid+"','"+menuid+"')";  //更新SQL
		
		//更新业务菜单
        String sql1 = "insert into sys_t_privilege (privilege_id,parentprivilegeid,code,displayname,detail,url," +
        				"sequence,depth,status,target,nav,isleaf) " +
        				"values " +
        				"('"+menuid+"','"+parmenuid+"','0"+menuid+"','"+pname+"','"+pname+"','#'," +
        				"'.0"+parmenuid+".0"+menuid+".','2','1','fm_subMenu','业务操作>>"+pname+"','0')";  //更新SQL
        
        
        //log.debug("业务菜单:"+sql1);
        //log.debug("业务菜单关系:"+sql0);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql1);//根据sql2创建PreparedStatement            
            pstmt.execute();              //执行            
            //关系表
            pstmt = conn.prepareStatement(sql0);//根据sql1创建PreparedStatement            
            pstmt.execute();              //执行
            //            
            //
            conn.commit();                      //关闭
            //
          	//走访调查
            updatePolicyMenuItems(Name,MenuId,menuid,smname,smurl);
            //业务审批
            updatePolicyMenuItems(Name,MenuId,menuid,scname,scurl);
//            //公    示
//            updatePolicyMenuItems(Name,MenuId,menuid,saname,saurl);
//            //异议处理
//            updatePolicyMenuItems(Name,MenuId,menuid,snname,snurl);
//            //业务查询
//            updatePolicyMenuItems(Name,MenuId,menuid,sqname,squrl);
//            //统计分析
//            updatePolicyMenuItems(Name,MenuId,menuid,srname,srurl);
//            //评 议 人
//            stempname = "评 议 人";
//            stempurl = "/db/page/operatingzone.jsp?url=/db/page/policy/manage/policycheckperson.jsp&ifquery=2";
//            updatePolicyMenuItems(Name,MenuId,menuid,stempname,stempurl);
            //
            srep = "新增业务菜单操作成功!";
        } catch (SQLException e) {
        	log.debug(e.toString());
        	try {
        		srep = "新增业务菜单操作失败!";
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
    /**
     * 更新业务菜单关系
     * @param Id
     * @param Name
     * @param MenuId
     * @return
     */
    String updatePolicyMenu(String Id,String Name,String MenuId) {
    	String srep = "",menuid = "",pid = "";
    	
    	String stempname = "",smname="",scname="",saname = "",snname = "",sqname = "",srname = "";
    	String stempurl = "",smurl="",scurl="",saurl = "",snurl = "",squrl = "",srurl = "";
    	//
    	String qmodemid="4",qmodecid="5",qmodeaid = "6",qmodenid = "7",qmodeqid = "8",qmoderid = "9";
    	//
    	pid = Id;
    	//
      	smname = "走访调查";
      	scname = "业务审批";
      	saname = "公    示";
      	snname = "异议处理";      	
      	sqname = "业务查询";
      	srname = "统计分析";
      	//
      	smurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodemid+"&qpolicy="+pid;
      	scurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodecid+"&qpolicy="+pid;
      	saurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeaid+"&qpolicy="+pid;
      	snurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodenid+"&qpolicy="+pid;
      	squrl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeqid+"&qpolicy="+pid;
      	srurl= "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmoderid+"&qpolicy="+pid;
		//	
      	//
      	menuid = getPolicyMenuId(Id);//业务菜单关系取得菜单ID
    	if(menuid.equals("")||menuid==null){
    		addPolicyMenus(Id,Name,MenuId);//添加业务菜单关系
    	}else{
    		
    		//更新业务菜单
            String sql0 = "update sys_t_privilege set displayname = '"+Name+"',sequence = '.0"+MenuId+".0"+menuid+".',detail = '"+Name+"', nav = '业务操作>>"+Name+"',parentprivilegeid = '"+MenuId+"' where privilege_id = '"+menuid+"'";  //更新SQL
            //log.debug("更新业务菜单:"+ sql0);
            Connection conn = null;                 //声明Connection对象
            PreparedStatement pstmt = null;         //声明PreparedStatement对象
            try {
                conn = DBUtils.getConnection();     //获取数据库连接
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql0);//根据sql1创建PreparedStatement            
                pstmt.executeUpdate();              //执行
                
                conn.commit();                      //关闭
                //
              	//走访调查
                updatePolicyMenuItems(Name,MenuId,menuid,smname,smurl);
                //业务审批
                updatePolicyMenuItems(Name,MenuId,menuid,scname,scurl);
//                //公    示
//                updatePolicyMenuItems(Name,MenuId,menuid,saname,saurl);
//                //异议处理
//                updatePolicyMenuItems(Name,MenuId,menuid,snname,snurl);
//                //业务查询
//                updatePolicyMenuItems(Name,MenuId,menuid,sqname,squrl);
//                //统计分析
//                updatePolicyMenuItems(Name,MenuId,menuid,srname,srurl);
//                //评 议 人
//                stempname = "评 议 人";
//                stempurl = "/db/page/operatingzone.jsp?url=/db/page/policy/manage/policycheckperson.jsp&ifquery=2";
//                updatePolicyMenuItems(Name,MenuId,menuid,stempname,stempurl);
                //
                srep = "更新业务菜单操作成功!";
            } catch (SQLException e) {
            	log.debug(e.toString());
            	try {
            		srep = "更新业务菜单操作失败!";
            		conn.rollback();
            	} catch (SQLException e1) {           
            		e1.printStackTrace();
            	}
            } finally {
                DBUtils.close(pstmt);               //关闭PreparedStatement
                //DBUtils.close(conn);                //关闭连接
            }
    	}
    	return srep;
    }
    /**
     * 更新没有查询模式的菜单项
     * @param Name
     * @param MenuId
     * @param PMenuId
     * @param Sname
     * @return
     */
    String updatePolicyMenuItems(String Name,String MenuId,String PMenuId,String Sname,String Surl) {
    	String srep= "",menuid = "",parmenuid = "",pname = "",sid = "",surl = "",sname = "";
    	String sql = "";
    	// 表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	// 
      	parmenuid = MenuId;//业务菜单父编号 
      	menuid = PMenuId;//业务菜单编号
      	pname = Name;
      	sname = Sname;
      	surl = Surl;
      	//
      	//
		sid = getPolicyMenuIdFromParIDAndName(menuid,sname);
    	if(sid.equals("")||sid == null){        		
    		sid = tableinfoquery.getseqfromname("XPRIVILEGE_ID");    		
    		//
            sql = "insert into sys_t_privilege (privilege_id,parentprivilegeid,code,displayname,detail,url," +
            				"sequence,depth,status,target,nav,isleaf) " +
            				"values " +
            				"('"+sid+"','"+menuid+"','0"+sid+"','"+sname+"','"+sname+"','"+surl+"'," +
    						"'.0"+parmenuid+".0"+menuid+".0"+sid+".','3','1','main','业务操作>>"+pname+">>"+sname+"','1')";  //更新SQL
    	}else{
    		//
            sql = "update sys_t_privilege set nav = '业务操作>>"+pname+">>"+sname+"',sequence = '.0"+parmenuid+".0"+menuid+".0"+sid+".' where privilege_id = '"+sid+"'";  //更新SQL
    	}
    	//
    	//log.debug("更新业务子菜单:"+ sql);
    	Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//根据sql1创建PreparedStatement            
            pstmt.execute();              		//执行
            conn.commit();                      //关闭
            //
            srep = "更新业务菜单操作成功!";
        } catch (SQLException e) {
        	log.debug(e.toString());
        	try {
        		srep = "更新业务菜单操作失败!";
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
    /**
     * 由上级菜单ID和菜单名称取得菜单ID
     * @param parid
     * @param name
     * @return
     */
    public String getPolicyMenuIdFromParIDAndName(String parid,String name){
    	String srep = "";
    	
    	//
    	String sql = "select privilege_id from sys_t_privilege " +
    			"where parentprivilegeid = '"+parid+"' and displayname = '"+name+"'";
        //
    	//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //参数
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
            	srep = rs.getString("privilege_id");
            	break; 
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }        
    	return srep;
    }
    /**
     * 取得审批流程定义
     * @param pid
     * @return
     */
    String getPolicyFlowItem(String pid) {
        JSONArray array = new JSONArray();      //定义JSON数组
        String sql = "select checkflow_id,policy_id,accdept,useraccflag,checkflag,alllimit,appstate1,limit1,appstate2,limit2,appstate3,limit3,appstate4,limit4,appstate5,limit5 " +
            "from biz_t_checkflow  where policy_id = '"+pid+"'";   //定义SQL语句
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //遍历结果集，给JSON数组中加入JSONObject
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("pcheckflowid", rs.getString("checkflow_id"));
                obj.put("paccdept", rs.getString("accdept"));
                obj.put("puseraccflag", rs.getString("useraccflag"));
                obj.put("pcheckflag", rs.getString("checkflag"));
                obj.put("palllimit", rs.getString("alllimit"));
                obj.put("pappstate1", rs.getString("appstate1"));
                obj.put("plimit1", rs.getString("limit1"));
                obj.put("pappstate2", rs.getString("appstate2"));
                obj.put("plimit2", rs.getString("limit2"));
                obj.put("pappstate3", rs.getString("appstate3"));
                obj.put("plimit3", rs.getString("limit3"));
                obj.put("pappstate4", rs.getString("appstate4"));
                obj.put("plimit4", rs.getString("limit4"));
                obj.put("pappstate5", rs.getString("appstate5"));
                obj.put("plimit5", rs.getString("limit5"));
               
                array.add(obj);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return array.toString();
    }
    /**
     * 更新政策
     * add新增edit编辑
     * @param sql
     * @param edittype
     * @return
     */

    String updatePolicyStrandardDept(String Sql,String EditType) {
      String srep = "";
      
      	//log.debug(Sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(Sql); //根据sql创建PreparedStatement   
            pstmt.execute();
            conn.commit(); 
            if("addpolicyflow".equals(EditType)){
              srep = "新增业务审批流程操作成功!";
            }else if("editpolicyflow".equals(EditType)){
              srep = "修改业务审批流程操作成功!";
            }else if("addstandard".equals(EditType)){
              srep = "新增档次操作成功!";
            }else if("editstandard".equals(EditType)){
              srep = "修改档次操作成功!";
            }else if("adddept".equals(EditType)){
              srep = "新增机构操作成功!";
            }else if("editdept".equals(EditType)){
              srep = "修改机构操作成功!";
            }            
        } catch (SQLException e) {
        	try {
        	  if("addpolicyflow".equals(EditType)){
                  srep = "新增业务审批流程操作失败!";
                }else if("editpolicyflow".equals(EditType)){
                  srep = "修改业务审批流程操作失败!";
                }else if("addstandard".equals(EditType)){
                  srep = "新增档次操作失败!";
                }else if("editstandard".equals(EditType)){
                  srep = "修改档次操作失败!";
                }else if("adddept".equals(EditType)){
                  srep = "新增机构操作失败,可能公式不合法!";
                }else if("editdept".equals(EditType)){
                  srep = "修改机构操作失败,可能公式不合法!";
                }
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
    /**
     * 更新档次状态
     * @param Id
     * @param Status
     * @return
     */
    String updateStandardStatus(String Id,String Status) {
      String srep = "";
        String sql2 = "update doper_t_standard set flag = '"+Status+"' where standard_id = '"+Id+"'";  //更新档次状态SQL
        String sql3 = "update (select a.flag from doper_t_standarddept a,doper_t_standard b " +
                    "where a.standard_id = b.standard_id and b.standard_id = '"+Id+"') set flag = '"+Status+"' ";   //更新机构状态SQL语句
        //log.debug(sql2);
        //log.debug(sql3);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql2);//根据sql2创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            pstmt = conn.prepareStatement(sql3);//根据sql3创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            conn.commit();                      //关闭
            if("0".equals(Status)){
              srep = "停用档次操作成功!";
            }else{
              srep = "启用档次操作成功!";
            }
        } catch (SQLException e) {
          try {
            if("0".equals(Status)){
                srep = "停用档次操作失败!";
              }else{
                srep = "启用档次操作失败!";
              }  
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
    /**
     * 更新机构状态
     * @param Id
     * @param Status
     * @return
     */
    String updateDeptStatus(String Id,String Status) {
      String srep = "";
        String sql = "update doper_t_standarddept set flag = '"+Status+"' where standarddept_id = '"+Id+"'";  //更新档次状态SQL

        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//根据sql2创建PreparedStatement            
            pstmt.executeUpdate();              //执行
            conn.commit();                      //关闭
            if("0".equals(Status)){
              srep = "停用所属机构操作成功!";
            }else{
              srep = "启用所属机构操作成功!";
            }
        } catch (SQLException e) {
          try {
            if("0".equals(Status)){
                srep = "停用所属机构操作失败!";
              }else{
                srep = "启用所属机构操作失败!";
              }  
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
    /**
     * 校验该档次是否至少设置一个机构核算公式
     * @param sid
     * @return
     */
    public boolean existsPolicyDeptAccFromStandardID(String sid){
    	boolean brep = false;
    	
    	//
    	String sql = "select t.accexp " 
    		+ "from doper_t_standarddept t " 
    		+ "where t.accexp is not null " 
    		+ "and t.standard_id = '"+sid+"'";
        //
    	//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //参数
            rs = pstmt.executeQuery();            
            if (rs.next()) {  
            	brep = true;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }        
    	return brep;
    }
}
