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
     * ȡ��ҵ���б�
     * @return
     */
    public StringBuffer getPolicysHtml() {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String sid = "",sname = "",sflag = "",smenuty = ""; 
    	//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>ҵ���б�</legend>";
		//
		html += "<button class = 'btn' onclick=\"GetPolicyItemHtml('')\">����ҵ��</button>"; 
		//
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ҵ�����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select policy_id, name,flag,menutype from doper_t_policy order by flag desc";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	sid = rs.getString("policy_id");
            	sname = rs.getString("name");
            	sflag = rs.getString("flag");
            	smenuty = rs.getString("menutype");
                
                html +="<tr>";		
		    		//����ֵ	
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
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updatePolicyStatus('" + sid + "','" + sname + "','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updatePolicyStatus('" + sid + "','" + sname + "','1')\">����</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
	    	html +="</table>"; 
	    	html += "<button class = 'btn' onclick=\"GetPolicyItemHtml('')\">����ҵ��</button>"; 
	    html += "</fieldset>";
	       
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * ҵ������
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
    	//����ҵ���ѯ������
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
	  	//
    	if(pid.equals("")){
    		mode = "addpolicy";
    		stitle = "[����]";
    	}else{
    		mode = "editpolicy";
    		//
        	sql = "select policy_id,objtype,menutype,name,descr,type,acctype," +
        			"acctu,cycle,cycnum,isprint,begdt,note,flag " +
        			"from doper_t_policy where policy_id = '" + pid + "'";   //����SQL���
        	//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
                DBUtils.close(rs);                  //�رս����
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                //DBUtils.close(conn);                //�ر�����
            }
    	}	
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"ҵ������</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����ҵ��</td>";				
			html += "<td><div id = 'divpmenu'>";						
			    	temp = getPolicyChoiceHtml("pmenu","-2",menutype).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";
			html += "<td style = 'text-align:center;font-size:12px;'>" ;
				if(acctype.equals("1")){//��Ա����
					html += "<input type='radio' name = 'rdacc' id = 'rdaccf'>��ͥ</>"; 
					html += "<input type='radio' name = 'rdacc' id = 'rdaccm' checked>��Ա</>"; 
				}else{
					html += "<input type='radio' name = 'rdacc' id = 'rdaccf' checked>��ͥ</>"; 
					html += "<input type='radio' name = 'rdacc' id = 'rdaccm'>��Ա</>"; 					
				}
				
			html += "</td>";
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'pname' value = '"+name+"'></input></td>";
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>ҵ�����</td>";				
			html += "<td><div id = 'divpobjtype'>";						
			    	temp = getPolicyChoiceHtml("pobjtype","-1",objtype).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";				
			html += "<td><div id = 'divptype'>";						
			    	temp = getPolicyChoiceHtml("ptype","111",type).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>ʵʩ����</td>";				
			html += "<td><div id = 'divpacctu'>";						
			    	temp = getPolicyChoiceHtml("pacctu","112",acctu).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";				
			html += "<td><div id = 'divpcycle'>";						
			    	temp = getPolicyChoiceHtml("pcycle","113",cycle).toString();
				    html += temp;					    
		    html +=	"</div></td>";
		html +="</tr>";
		//
		//		    
		html +="<tr>";
			//���ڸ�ʽ��
			if(begdt.equals("") || begdt == null){
				begdt = "";
			}else{
				String tempdt = begdt;
		        begdt = policymanagecheckquery.getformatdt(tempdt);
			}    
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ʼ����</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'pbegdt' value = '"+begdt+"' onFocus= 'setday(this)'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>�Ƿ��֤</td>";
			if(isprint.equals("1")){
				html += "<td style = 'text-align:center;font-size:12px;'><input type='checkbox' id='pisprint' checked >��Ҫ��֤</input></td>";
			}else{
				html += "<td style = 'text-align:center;font-size:12px;'><input type='checkbox' id='pisprint'>��Ҫ��֤</input></td>";
			}
			
		html +="</tr>";		
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����(��)</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'pcycnum' value = '"+cycnum+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ϸ����</td>";
			html += "<td><textarea rows='3' style = 'width:100%' type='text' id = 'pdescr' >"+descr+"</textarea></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ע</td>";
			html += "<td><textarea rows='2' style = 'width:100%' type='text' id = 'pnote' >"+note+"</textarea></td>";
		html +="</tr>";
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("addpolicy")){//����
	    	html += "<button class = 'btn' onclick=\"SavePolicy('"+mode+"')\">����</button>"; 	   		
    	}else if(mode.equals("editpolicy")){//�༭
    		//����ʱ��������
	    	if(flag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"policyInfo()\">�����Ϣ</button>";
	    		html += "<button class = 'btn' onclick=\"policyFlowDefine()\">��������</button>";	    		
	    		html += "<button class = 'btn' onclick=\"policyCheckPower()\">����Ȩ��</button>";
	    		html += "<button class = 'btn' onclick=\"SavePolicy('"+mode+"')\">����</button>";
	    		html += "<button class = 'btn' onclick=\"GoMatchPage('next')\">��һ��>>��׼����</button>";  
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
     * ��ҵ��ID������ȡ�ñ�׼�б�
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
		html += "<legend  class='legend'>["+pname+"]��׼�б�</legend>";
		//
		html += "<button class = 'btn' onclick=\"GoMatchPage('back')\">"+pname+"<<��һ��</button>"; 
	    html += "<button class = 'btn' onclick=\"GetPolicySqlItemHtml('')\">����ҵ���׼</button>";
	    //
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html +="<tr style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>";
			html += "<td width='100px'>��������</td>";
			html += "<td colspan = '5'  class='colValue'>";
			html += "<input style = 'width:100%' type='text' id = 'sqdeptname' value = '' onclick=\"queryDept('qsql')\"></input>";
			html += "<input type='text' id='sqdeptid' style='display:none' value = ''></input>" ;
			html += "</td>";
			html += "<td width='100px'  class='colValue'>" ;
			html += "<button class = 'btn' onclick=\"GetPolicySqlsHtml('"+pid+"','"+pname+"')\">��ѯ</button>" ;
			html += "</td>";
		html +="</tr>";
		html +="</table>";
		html += "<table id = 'listsstb' width='100%' cellpadding='0' cellspacing='0'>";		
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>��������</td>";
			html +=temp;
			temp ="<td height='23'>��׼����</td>";
			html +=temp;
			temp ="<td height='23'>��׼����</td>";
			html +=temp;
			temp ="<td height='23'>���ñ�׼</td>";
			html +=temp;
			temp ="<td height='23'>���㹫ʽ</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
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
        	+ "nlssort(descr, 'NLS_SORT=SCHINESE_PINYIN_M') desc";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDoSql(this,'"+sid+"','"+sdept+"','"+sname+"','"+smoney+"','"+saccdesc+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sdept+"</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+smoney+"</td>";
					//�Ƿ����ñ�׼
					if("".equals(sphysql) || null == sphysql){
						html += "<td height='23' class='colValue status"+sflag+"'><span style = 'color: #6BA6FF;'>��</span></td>";
					}else{
						html += "<td height='23' class='colValue status"+sflag+"'><span>��</span></td>";
					}
					//�Ƿ���������һ���������㹫ʽ
	            	boolean bdeptacc = existsPolicyDeptAccFromStandardID(sid);
	            	if(!bdeptacc){
						html += "<td height='23' class='colValue status"+sflag+"'><span style = 'color: #6BA6FF;'>��</span></td>";
					}else{
						html += "<td height='23' class='colValue status"+sflag+"'><span>��</span></td>";
					}
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateStandardStatus('"+sid+"','"+pname+"','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateStandardStatus('"+sid+"','"+pname+"','1')\">����</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
	    	html +="</table>"; 
	    	html += "<button class = 'btn' onclick=\"GoMatchPage('back')\">"+pname+"<<��һ��</button>"; 
	 	    html += "<button class = 'btn' onclick=\"GetPolicySqlItemHtml('')\">����ҵ���׼</button>";	 	    
	 	    //
	    html += "</fieldset>";
	    //
	   
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * ��ȡ��׼����
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
    		stitle = "[����]";
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
    	       + "and standard_id = '" + id + "' ";   //����SQL���
        	//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
                DBUtils.close(rs);                  //�رս����
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                //DBUtils.close(conn);                //�ر�����
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
    	html += "<legend  class='legend'>"+stitle+"��׼����</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'sdeptname' value = '"+sdeptname+"' onclick=\"queryDept('sql')\"></input>";
			html += "<input type='text' id='sdeptid' style='display:none' value = '"+sdeptid+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>����ҵ��</td>";
			html += "<td><div>"+getPolicyChoiceSelHtml("listspolicy").toString();
			html += "<button class = 'btn' onclick=\"ChoiceSupPolicy()\">���</button>";
			html += "<button class = 'btn' onclick=\"ClearSupPolicy()\">�ÿ�</button>";
			html += "<input style = 'width:100%' type='text' id = 'ssuperpolicy' value = '"+superpolicy+"' onclick=''></input></div></td>";
		html +="</tr>";
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>����ҵ��</td>";
			html += "<td><div>"+getPolicyChoiceSelHtml("listsnotpolicy").toString();
			html += "<button class = 'btn' onclick=\"ChoiceNotPolicy()\">���</button>";
			html += "<button class = 'btn' onclick=\"ClearNotPolicy()\">�ÿ�</button>";
			if(null == snotpolicy){
				snotpolicy = "";
			}
			html += "<input style = 'width:100%' type='text' id = 'snotpolicy' value = '"+snotpolicy+"' onclick=''></input></div></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��׼����</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'sdescr' value = '"+sname+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��׼����</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'splanmoney' value = '"+splanmoney+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��׼����</td>";
			html += "<td><textarea rows='3' style = 'width:100%' type='text' id = 'splandesc' >"+splandesc+"</textarea></td>";
		html +="</tr>";
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("addstandard")){//����
	    	html += "<button class = 'btn' onclick=\"SaveStandard('"+mode+"')\">����</button>"; 	   		
    	}else if(mode.equals("editstandard")){//�༭
    		//����ʱ��������
	    	if(sflag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"sqlaction()\">��׼����</button>";
	    		html += "<button class = 'btn' onclick=\"SaveStandard('"+mode+"')\">����</button>";
	    		if(existsPolicyStrandardSql(sid)){
	    			html += "<button class = 'btn' onclick=\"GoMatchPage('nextsql')\">��һ��>>������׼����</button>";
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
     * ȡ�û�����׼�б�
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
		html += "<legend  class='legend'>["+sname+"]������׼�б�</legend>";
		//
		//
	    html += "<button class = 'btn' onclick=\"GoMatchPage('backsql')\">"+sname+"<<��һ��</button>"; 
	    html += "<button class = 'btn' onclick=\"GetPolicyDeptItemHtml('')\">����������׼</button>";
	    
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";			
			temp ="<td height='23'>��������</td>";
			html +=temp;
			temp ="<td height='23'>��׼����</td>";
			html +=temp;
			temp ="<td height='23'>��׼����</td>";
			html +=temp;
			temp ="<td height='23'>��׼����</td>";
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
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>��������</td>";
			html +=temp;
			temp ="<td height='23'>��׼����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select standarddept_id,fullname,a.checkmoney,a.checkdesc,a.accdesc,a.flag " +
		        "from doper_t_standarddept a,sys_t_organization b " +
		        "where a.organization_id = b.organization_id and standard_id = '"+sid+"' order by b.organization_id";   //����SQL���
        
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDoDept(this,'"+did+"','"+dname+"','"+dmoney+"','"+ddesc+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+dflag+"'>"+dname+"</td>";
					html += "<td height='23' class='colValue'>"+dmoney+"</td>";
					if(dflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDeptStatus('"+did+"','"+dname+"','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDeptStatus('"+did+"','"+dname+"','1')\">����</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
	    	html +="</table>";
	    	//
		    html += "<button class = 'btn' onclick=\"GoMatchPage('backsql')\">"+sname+"<<��һ��</button>"; 
		    html += "<button class = 'btn' onclick=\"GetPolicyDeptItemHtml('')\">����������׼</button>";
		    //
	    html += "</fieldset>";
	    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * ��ȡ��׼��������
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
    		stitle = "[����]";
    	}else{
    		mode = "editdept";
    		//
        	sql = "select standarddept_id,a.organization_id,fullname,checkmoney,checkdesc,accexplocsql,accexp,accdesc,a.flag " +
               "from doper_t_standarddept a,sys_t_organization b  " +
               "where a.organization_id = b.organization_id and standarddept_id = '" + id + "'";   //����SQL���
        	//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
                DBUtils.close(rs);                  //�رս����
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                //DBUtils.close(conn);                //�ر�����
            }
    	}
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"������׼����</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'ddeptname' value = '"+dname+"' onclick=\"queryDept('acc')\"></input>";
			html += "<input type='text' id='ddeptid' style='display:none' value = '"+ddeptid+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��������</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'dcheckdesc' value = '"+ddesc+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��׼����</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'dcheckmoney' value = '"+dmoney+"'></input></td>";
		html +="</tr>";
		//		
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>���㹫ʽ����</td>";
			html += "<td><textarea style='width:100%' rows='4' id='daccdesc'>"+daccdesc+"</textarea></td>";
		html +="</tr>";
		//
		//		
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("adddept")){//����
	    	html += "<button class = 'btn' onclick=\"SaveDept('"+mode+"')\">����</button>"; 	   		
    	}else if(mode.equals("editdept")){//�༭
    		//����ʱ��������
	    	if(dflag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"deptaction()\">���㹫ʽ����</button>";
	    		html += "<button class = 'btn' onclick=\"SaveDept('"+mode+"')\">����</button>";  
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
     * ȡ��ҵ����������������
     * @param sname
     * @param discid
     * @return
     */
    public StringBuffer getPolicyChoiceHtml(String sname,String discid,String seldiscid){
        StringBuffer shtml= new StringBuffer("");
        String id = "",name = "";
        
        //��������
        ConstantDefine constantdefine = new ConstantDefine();
        if(discid.equals(constantdefine.POLICYMENUCODE)){//����ҵ��       
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
        }else if(discid.equals(constantdefine.POLICYOBJTYCODE)){//ҵ�����        
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
        }else{//���ݿ��ֵ�
         String sql = "select dictionary_id,item from sys_t_dictionary  " +
                 "where status = '1' and dictsort_id = '"+discid+"' order by sequence";
          
          	//
         	shtml.append("<select id=\""+sname+"\" style = 'width:100%'>");
         	Connection conn = null;                 //����Connection����
         	PreparedStatement pstmt = null;         //����PreparedStatement����
         	ResultSet rs = null;                    //����ResultSet����
         	try {
         		conn = DBUtils.getConnection();     //��ȡ���ݿ�����
         		pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
         		//����
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
         		DBUtils.close(rs);                  //�رս����
         		DBUtils.close(pstmt);               //�ر�PreparedStatement
         		//DBUtils.close(conn);                //�ر�����
         	}
          	shtml.append("</select>");
        }
        return shtml;
    }    
    /**
     * ȡ��ҵ��ѡ������������
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
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
        	conn = DBUtils.getConnection();     //��ȡ���ݿ�����
        	pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
        	//����
        	rs = pstmt.executeQuery();
  
        	while (rs.next()) {  
        		id = rs.getString("policy_id");
        		name = rs.getString("name");
        		shtml.append("<option value=\""+name+"\">"+name+"</option>");
        	}
        } catch (SQLException e) {
        	log.debug(e.toString());
        } finally {
        	DBUtils.close(rs);                  //�رս����
        	DBUtils.close(pstmt);               //�ر�PreparedStatement
        	//DBUtils.close(conn);                //�ر�����
        }
        shtml.append("</select>");       
        return shtml;
    }
    /**
     * ȡ��ҵ���׼SQL���
     * @param id
     * @param mode
     * @return
     */
    public String getPolicySqlItem(String id,String mode) {
        String srep = "";
        String sql = "select physql, locsql from doper_t_standard where standard_id = '" + id + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ����ҵ���׼SQL����
     * @param Id
     * @param PhySql
     * @param LocSql
     * @return
     */
    public String updatePolicySql(String Id,String PhySql,String LocSql) {
		String srep = "";
		//������ת��ȥ�����ҿո�
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update doper_t_standard set " +
				"physql = '"+PhySql+"'," +
				"locsql = '"+LocSql+"' where standard_id = '"+Id+"'";  //���±�׼״̬SQL
		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		    pstmt.executeUpdate();              //ִ��
		    conn.commit();                      //�ر�
		    srep = "���±�׼��������ɹ�!";
		} catch (SQLException e) {
			srep = "���±�׼�������ʧ��!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
    /**
     * ȡ�û�����׼SQL���
     * @param id
     * @param mode
     * ���⣺�߼����ͺ��㹫ʽ�߼����ʽ��;����
     * ҳ��ע�⴦��!!!
     * @return
     */
    public String getPolicyDeptSqlItem(String id,String mode) {
        String srep = "";
        String sql = "select accexpphysql, accexplocsql,accexp from doper_t_standarddept where standarddept_id = '" + id + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ����ҵ���׼SQL����
     * @param Id
     * @param PhySql
     * @param LocSql
     * @param LocExp
     * @return
     */
    public String updatePolicyDeptSql(String Id,String PhySql,String LocSql,String LocExp) {
		String srep = "";
		//������ת��ȥ�����ҿո�
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update doper_t_standarddept set " +
				"accexpphysql = '"+PhySql+"'," +
				"accexplocsql = '"+LocSql+"'," +
				"accexp = '"+LocExp+"' " +
				"where standarddept_id = '"+Id+"'";  //���»�����׼״̬SQL
		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
		    pstmt.executeUpdate();              //ִ��
		    conn.commit();                      //�ر�
		    srep = "���±�׼��������ɹ�!";
		} catch (SQLException e) {
			srep = "���±�׼�������ʧ��!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return srep;
	}
    /**
     * �Ƿ��Ѿ����ñ�׼
     * @param parid
     * @return
     */
    public boolean existsPolicyStrandardSql(String parid){
		boolean  brep = false;
		
		String sql = "";
		sql = "select standard_id from doper_t_standard where physql is not null and  standard_id = '"+parid+"'";
		//		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    /**********************************************************
     * ҵ�������ز���
     **********************************************************/
    /**
     * ����ҵ��״̬
     * @param ID
     * @param Status
     */
    String updatePolicyStatus(String Id,String Status) {
      String srep = "";
        String sql  = "update doper_t_policy set flag = '"+Status+"' where policy_id = '"+Id+"'";  //����ҵ��״̬SQL
        String sql2 = "update doper_t_standard set flag = '"+Status+"' where policy_id = '"+Id+"'";  //���µ���״̬SQL
        String sql3 = "update (select a.flag from doper_t_standarddept a,doper_t_standard b,doper_t_policy c " +
                    "where a.standard_id = b.standard_id and b.policy_id = c.policy_id  and c.policy_id = '"+Id+"') set flag = '"+Status+"' ";   //���»���״̬SQL���
        //log.debug(sql);
        //log.debug(sql2);
        //log.debug(sql3);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
            pstmt.executeUpdate();              //ִ��
            pstmt = conn.prepareStatement(sql2);//����sql2����PreparedStatement            
            pstmt.executeUpdate();              //ִ��
            pstmt = conn.prepareStatement(sql3);//����sql3����PreparedStatement            
            pstmt.executeUpdate();              //ִ��
            conn.commit();                      //�ر�
            //
            //����ҵ��˵�״̬
            updatePolicyMenuStatus(Id,Status);
            //
            if("0".equals(Status)){
              srep = "ͣ��ҵ������ɹ�!";
            }else{
              srep = "����ҵ������ɹ�!";
            }
        } catch (SQLException e) {
          try {
            if("0".equals(Status)){
                srep = "ͣ��ҵ�����ʧ��!";
              }else{
                srep = "����ҵ�����ʧ��!";
              }  
        conn.rollback();
      } catch (SQLException e1) {           
        e1.printStackTrace();
      }
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ����ҵ��˵�״̬
     * @param Id
     * @param Status
     * @return
     */
    String updatePolicyMenuStatus(String Id,String Status) {
    	String srep="",mid = "";
    	//
    	mid = getPolicyMenuId(Id);//ҵ��˵���ϵȡ�ò˵�ID
    	if(mid.equals("")||mid==null){
    		//
    	}else{
    		//����ҵ��˵�
            String sql1 = "update sys_t_privilege set status = '"+Status+"' where privilege_id = '"+mid+"'";  //����SQL
            String sql2 = "update sys_t_privilege set status = '"+Status+"' where parentprivilegeid = '"+mid+"'";  //����SQL
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql1);//����sql1����PreparedStatement            
                pstmt.executeUpdate();              //ִ��    
                pstmt = conn.prepareStatement(sql2);//����sql2����PreparedStatement            
                pstmt.executeUpdate();              //ִ��
                conn.commit();                      //�ر�
                //
                if("0".equals(Status)){
                    srep = "ͣ��ҵ��˵������ɹ�!";
                }else{
                    srep = "����ҵ��˵������ɹ�!";
                }
            } catch (SQLException e) {
            	try {
            		if("0".equals(Status)){
                        srep = "ͣ��ҵ��˵�����ʧ��!";
                    }else{
                        srep = "����ҵ��˵�����ʧ��!";
                    }
            		conn.rollback();
            	} catch (SQLException e1) {           
            		e1.printStackTrace();
            	}
            } finally {
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                //DBUtils.close(conn);                //�ر�����
            }
    	}
    	return srep;
    }
    /**
     * �Ƿ����ҵ��˵���ϵ
     * ���ز˵�ID
     * @param pid
     * @return
     */
    public String getPolicyMenuId(String pid){
    	String srep = "";
    	
    	//
    	String sql = "select privilege_id from doper_t_policymenu " +
    			"where policy_id = '"+pid+"' ";
        //
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            if (rs.next()) {  
            	srep = rs.getString("privilege_id");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }        
    	return srep;
    }
    /**
     * ����ҵ��
     * @param name
     * @return
     */
    public boolean existsPolicy(String name){
		boolean  brep = false;
		String sql = "";
		
		sql = "select POLICY_ID from DOPER_T_POLICY where NAME = '"+name+"' ";
		//		
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    /**
     * ����ҵ��
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
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
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
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    /**
     * ����������֤
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
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    /**
     * ���µ��κ���У��
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
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
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
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    /**
     * ��������������֤
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
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			if (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    /**
     * ���»�������У��
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
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
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
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
    
    
    
    /**
     * ����ҵ�����
     * @param Sql
     * @param EditType
     * @param Pid
     * @param Pmenu
     * @return
     */
    String updatePolicy(String Sql,String EditType,String Pid,String Pmenu) {
        String srep = "",pname = "";
        
          //log.debug(Sql);
          Connection conn = null;                 //����Connection����
          PreparedStatement pstmt = null;         //����PreparedStatement����
          try {
              conn = DBUtils.getConnection();     //��ȡ���ݿ�����
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(Sql); //����sql����PreparedStatement   
              pstmt.execute();
              conn.commit(); 
              
              if("addpolicy".equals(EditType)){
                //                
                //ȡ��ҵ������
                pname = getPolicyNameFromId(Pid);
                //����ҵ��˵�
                addPolicyMenus(Pid,pname,Pmenu);
                //
                srep = "����ҵ������ɹ�!";
                //
              }else if("editpolicy".equals(EditType)){
            	  //                
                  //ȡ��ҵ������
                  pname = getPolicyNameFromId(Pid);
                  //����ҵ��˵�
                  updatePolicyMenu(Pid,pname,Pmenu);
                  //
                srep = "�޸�ҵ������ɹ�!";
              }
          } catch (SQLException e) {
            try {
              if("addpolicy".equals(EditType)){
                    srep = "����ҵ�����ʧ��!";
                  }else if("editpolicy".equals(EditType)){
                    srep = "�޸�ҵ�����ʧ��!";
                  }
          conn.rollback();
        } catch (SQLException e1) {          
          e1.printStackTrace();
        }
          } finally {
              DBUtils.close(pstmt);               //�ر�PreparedStatement
              //DBUtils.close(conn);                //�ر�����
          }
          return srep;
      }
    /**
     * ��ҵ����ȡ��ҵ������
     * @param name
     * @return
     */
    public String getPolicyNameFromId(String id) {
        String srep = "";
        String sql = "select policy_id,name from doper_t_policy where policy_id = '" + id + "'";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              srep = rs.getString("name");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     *���ҵ��˵�
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
    	// ���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	// 
      	pid = Id;
      	smname = "�߷õ���";
      	scname = "ҵ������";
      	saname = "��    ʾ";
      	snname = "���鴦��";      	
      	sqname = "ҵ���ѯ";
      	srname = "ͳ�Ʒ���";
     	
      	smurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodemid+"&qpolicy="+pid;
      	scurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodecid+"&qpolicy="+pid;
      	saurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeaid+"&qpolicy="+pid;
      	snurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodenid+"&qpolicy="+pid;
      	squrl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeqid+"&qpolicy="+pid;
      	srurl= "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmoderid+"&qpolicy="+pid;
		//
      	//ȡ�ó����˵���ID
		parmenuid = MenuId;//ҵ��˵���ID
		menuid = tableinfoquery.getseqfromname("XPRIVILEGE_ID");//ҵ��˵�ID
		//		
		pid = Id;
		pname = Name;
		//����ҵ��˵���ϵ
		String sql0  = "insert into doper_t_policymenu (policymenu_id, policy_id, privilege_id) values (xpolicymenu_id.nextval,'"+pid+"','"+menuid+"')";  //����SQL
		
		//����ҵ��˵�
        String sql1 = "insert into sys_t_privilege (privilege_id,parentprivilegeid,code,displayname,detail,url," +
        				"sequence,depth,status,target,nav,isleaf) " +
        				"values " +
        				"('"+menuid+"','"+parmenuid+"','0"+menuid+"','"+pname+"','"+pname+"','#'," +
        				"'.0"+parmenuid+".0"+menuid+".','2','1','fm_subMenu','ҵ�����>>"+pname+"','0')";  //����SQL
        
        
        //log.debug("ҵ��˵�:"+sql1);
        //log.debug("ҵ��˵���ϵ:"+sql0);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql1);//����sql2����PreparedStatement            
            pstmt.execute();              //ִ��            
            //��ϵ��
            pstmt = conn.prepareStatement(sql0);//����sql1����PreparedStatement            
            pstmt.execute();              //ִ��
            //            
            //
            conn.commit();                      //�ر�
            //
          	//�߷õ���
            updatePolicyMenuItems(Name,MenuId,menuid,smname,smurl);
            //ҵ������
            updatePolicyMenuItems(Name,MenuId,menuid,scname,scurl);
//            //��    ʾ
//            updatePolicyMenuItems(Name,MenuId,menuid,saname,saurl);
//            //���鴦��
//            updatePolicyMenuItems(Name,MenuId,menuid,snname,snurl);
//            //ҵ���ѯ
//            updatePolicyMenuItems(Name,MenuId,menuid,sqname,squrl);
//            //ͳ�Ʒ���
//            updatePolicyMenuItems(Name,MenuId,menuid,srname,srurl);
//            //�� �� ��
//            stempname = "�� �� ��";
//            stempurl = "/db/page/operatingzone.jsp?url=/db/page/policy/manage/policycheckperson.jsp&ifquery=2";
//            updatePolicyMenuItems(Name,MenuId,menuid,stempname,stempurl);
            //
            srep = "����ҵ��˵������ɹ�!";
        } catch (SQLException e) {
        	log.debug(e.toString());
        	try {
        		srep = "����ҵ��˵�����ʧ��!";
        		conn.rollback();
        	} catch (SQLException e1) {           
        		e1.printStackTrace();
        	}
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
    	
    	return srep;
    }
    /**
     * ����ҵ��˵���ϵ
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
      	smname = "�߷õ���";
      	scname = "ҵ������";
      	saname = "��    ʾ";
      	snname = "���鴦��";      	
      	sqname = "ҵ���ѯ";
      	srname = "ͳ�Ʒ���";
      	//
      	smurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodemid+"&qpolicy="+pid;
      	scurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodecid+"&qpolicy="+pid;
      	saurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeaid+"&qpolicy="+pid;
      	snurl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodenid+"&qpolicy="+pid;
      	squrl = "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmodeqid+"&qpolicy="+pid;
      	srurl= "/db/page/operatingzone.jsp?url=/db/page/intro/operpolicy.jsp&qmode="+qmoderid+"&qpolicy="+pid;
		//	
      	//
      	menuid = getPolicyMenuId(Id);//ҵ��˵���ϵȡ�ò˵�ID
    	if(menuid.equals("")||menuid==null){
    		addPolicyMenus(Id,Name,MenuId);//���ҵ��˵���ϵ
    	}else{
    		
    		//����ҵ��˵�
            String sql0 = "update sys_t_privilege set displayname = '"+Name+"',sequence = '.0"+MenuId+".0"+menuid+".',detail = '"+Name+"', nav = 'ҵ�����>>"+Name+"',parentprivilegeid = '"+MenuId+"' where privilege_id = '"+menuid+"'";  //����SQL
            //log.debug("����ҵ��˵�:"+ sql0);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql0);//����sql1����PreparedStatement            
                pstmt.executeUpdate();              //ִ��
                
                conn.commit();                      //�ر�
                //
              	//�߷õ���
                updatePolicyMenuItems(Name,MenuId,menuid,smname,smurl);
                //ҵ������
                updatePolicyMenuItems(Name,MenuId,menuid,scname,scurl);
//                //��    ʾ
//                updatePolicyMenuItems(Name,MenuId,menuid,saname,saurl);
//                //���鴦��
//                updatePolicyMenuItems(Name,MenuId,menuid,snname,snurl);
//                //ҵ���ѯ
//                updatePolicyMenuItems(Name,MenuId,menuid,sqname,squrl);
//                //ͳ�Ʒ���
//                updatePolicyMenuItems(Name,MenuId,menuid,srname,srurl);
//                //�� �� ��
//                stempname = "�� �� ��";
//                stempurl = "/db/page/operatingzone.jsp?url=/db/page/policy/manage/policycheckperson.jsp&ifquery=2";
//                updatePolicyMenuItems(Name,MenuId,menuid,stempname,stempurl);
                //
                srep = "����ҵ��˵������ɹ�!";
            } catch (SQLException e) {
            	log.debug(e.toString());
            	try {
            		srep = "����ҵ��˵�����ʧ��!";
            		conn.rollback();
            	} catch (SQLException e1) {           
            		e1.printStackTrace();
            	}
            } finally {
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                //DBUtils.close(conn);                //�ر�����
            }
    	}
    	return srep;
    }
    /**
     * ����û�в�ѯģʽ�Ĳ˵���
     * @param Name
     * @param MenuId
     * @param PMenuId
     * @param Sname
     * @return
     */
    String updatePolicyMenuItems(String Name,String MenuId,String PMenuId,String Sname,String Surl) {
    	String srep= "",menuid = "",parmenuid = "",pname = "",sid = "",surl = "",sname = "";
    	String sql = "";
    	// ���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	// 
      	parmenuid = MenuId;//ҵ��˵������ 
      	menuid = PMenuId;//ҵ��˵����
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
    						"'.0"+parmenuid+".0"+menuid+".0"+sid+".','3','1','main','ҵ�����>>"+pname+">>"+sname+"','1')";  //����SQL
    	}else{
    		//
            sql = "update sys_t_privilege set nav = 'ҵ�����>>"+pname+">>"+sname+"',sequence = '.0"+parmenuid+".0"+menuid+".0"+sid+".' where privilege_id = '"+sid+"'";  //����SQL
    	}
    	//
    	//log.debug("����ҵ���Ӳ˵�:"+ sql);
    	Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//����sql1����PreparedStatement            
            pstmt.execute();              		//ִ��
            conn.commit();                      //�ر�
            //
            srep = "����ҵ��˵������ɹ�!";
        } catch (SQLException e) {
        	log.debug(e.toString());
        	try {
        		srep = "����ҵ��˵�����ʧ��!";
        		conn.rollback();
        	} catch (SQLException e1) {           
        		e1.printStackTrace();
        	}
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
    	return srep;
    }
    /**
     * ���ϼ��˵�ID�Ͳ˵�����ȡ�ò˵�ID
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
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
            	srep = rs.getString("privilege_id");
            	break; 
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }        
    	return srep;
    }
    /**
     * ȡ���������̶���
     * @param pid
     * @return
     */
    String getPolicyFlowItem(String pid) {
        JSONArray array = new JSONArray();      //����JSON����
        String sql = "select checkflow_id,policy_id,accdept,useraccflag,checkflag,alllimit,appstate1,limit1,appstate2,limit2,appstate3,limit3,appstate4,limit4,appstate5,limit5 " +
            "from biz_t_checkflow  where policy_id = '"+pid+"'";   //����SQL���
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //�������������JSON�����м���JSONObject
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return array.toString();
    }
    /**
     * ��������
     * add����edit�༭
     * @param sql
     * @param edittype
     * @return
     */

    String updatePolicyStrandardDept(String Sql,String EditType) {
      String srep = "";
      
      	//log.debug(Sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(Sql); //����sql����PreparedStatement   
            pstmt.execute();
            conn.commit(); 
            if("addpolicyflow".equals(EditType)){
              srep = "����ҵ���������̲����ɹ�!";
            }else if("editpolicyflow".equals(EditType)){
              srep = "�޸�ҵ���������̲����ɹ�!";
            }else if("addstandard".equals(EditType)){
              srep = "�������β����ɹ�!";
            }else if("editstandard".equals(EditType)){
              srep = "�޸ĵ��β����ɹ�!";
            }else if("adddept".equals(EditType)){
              srep = "�������������ɹ�!";
            }else if("editdept".equals(EditType)){
              srep = "�޸Ļ��������ɹ�!";
            }            
        } catch (SQLException e) {
        	try {
        	  if("addpolicyflow".equals(EditType)){
                  srep = "����ҵ���������̲���ʧ��!";
                }else if("editpolicyflow".equals(EditType)){
                  srep = "�޸�ҵ���������̲���ʧ��!";
                }else if("addstandard".equals(EditType)){
                  srep = "�������β���ʧ��!";
                }else if("editstandard".equals(EditType)){
                  srep = "�޸ĵ��β���ʧ��!";
                }else if("adddept".equals(EditType)){
                  srep = "������������ʧ��,���ܹ�ʽ���Ϸ�!";
                }else if("editdept".equals(EditType)){
                  srep = "�޸Ļ�������ʧ��,���ܹ�ʽ���Ϸ�!";
                }
        conn.rollback();
      } catch (SQLException e1) {          
        e1.printStackTrace();
      }
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ���µ���״̬
     * @param Id
     * @param Status
     * @return
     */
    String updateStandardStatus(String Id,String Status) {
      String srep = "";
        String sql2 = "update doper_t_standard set flag = '"+Status+"' where standard_id = '"+Id+"'";  //���µ���״̬SQL
        String sql3 = "update (select a.flag from doper_t_standarddept a,doper_t_standard b " +
                    "where a.standard_id = b.standard_id and b.standard_id = '"+Id+"') set flag = '"+Status+"' ";   //���»���״̬SQL���
        //log.debug(sql2);
        //log.debug(sql3);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql2);//����sql2����PreparedStatement            
            pstmt.executeUpdate();              //ִ��
            pstmt = conn.prepareStatement(sql3);//����sql3����PreparedStatement            
            pstmt.executeUpdate();              //ִ��
            conn.commit();                      //�ر�
            if("0".equals(Status)){
              srep = "ͣ�õ��β����ɹ�!";
            }else{
              srep = "���õ��β����ɹ�!";
            }
        } catch (SQLException e) {
          try {
            if("0".equals(Status)){
                srep = "ͣ�õ��β���ʧ��!";
              }else{
                srep = "���õ��β���ʧ��!";
              }  
        conn.rollback();
      } catch (SQLException e1) {           
        e1.printStackTrace();
      }
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ���»���״̬
     * @param Id
     * @param Status
     * @return
     */
    String updateDeptStatus(String Id,String Status) {
      String srep = "";
        String sql = "update doper_t_standarddept set flag = '"+Status+"' where standarddept_id = '"+Id+"'";  //���µ���״̬SQL

        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//����sql2����PreparedStatement            
            pstmt.executeUpdate();              //ִ��
            conn.commit();                      //�ر�
            if("0".equals(Status)){
              srep = "ͣ���������������ɹ�!";
            }else{
              srep = "�����������������ɹ�!";
            }
        } catch (SQLException e) {
          try {
            if("0".equals(Status)){
                srep = "ͣ��������������ʧ��!";
              }else{
                srep = "����������������ʧ��!";
              }  
        conn.rollback();
      } catch (SQLException e1) {           
        e1.printStackTrace();
      }
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    } 
    /**
     * У��õ����Ƿ���������һ���������㹫ʽ
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
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();            
            if (rs.next()) {  
            	brep = true;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }        
    	return brep;
    }
}
