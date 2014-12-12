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

public class Policycheckideafamily {
	static Logger log = Logger.getLogger(Policycheckideafamily.class);
	/**
	 * ��ȡ��ͥ�����б�
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
		//����ʩ��������
		PolicyChildCheckMatch policychildcheckmatch = new PolicyChildCheckMatch();
		boolean bchild = policychildcheckmatch.existsPolicyChildFromPid(pid);
		//����ʩ����ʶ
		if(bchild){
			childflag = "1";
		}
		//
		html += "<table id = 'familystb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='rowField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>��ͥ����</td>";
			html +=temp;			
			temp ="<td height='23'>��������</td>";
			html +=temp;			
			temp ="<td height='23'>���ھ�����</td>";
			html +=temp;
			temp ="<td height='23'>�ⷢ������</td>";
			html +=temp;
			temp ="<td height='23'>����������</td>";
			html +=temp;			
			//���ڷ���ʩ��
			if(bchild){
				temp ="<td height='23'>����ʩ����</td>";
				html +=temp;
			}
			temp ="<td height='23' colspan = '3'>��ز���</td>";
			html +=temp;
			temp ="<td height='23'>�������</td>";
			html +=temp;
			//���ڷ���ʩ��
			if(!bchild){				
				temp ="<td height='23'>�Ƴ�</td>";
				html +=temp;
			}
		html +="</tr>";
		//������������ͥ
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
	    		"a.optcheck_id as checkid," +
	    		"b.family_id as fmid," +
	    		"b.familyno as fmno," +
	    		"b.masterid as memid," +
	    		"b.mastername as fmname " +
	    		"from biz_t_optcheck a, info_t_family b " +
	    		"where a.family_id = b.family_id " +
	    		"and a.policy_id = '"+pid+"' and b.family_id = '"+sfmid+"'" +
	    		"group by a.optcheck_id,b.family_id,b.familyno,b.masterid,b.mastername";   //����SQL���
    
		    //log.debug("sql:"+sql);
			Connection conn = null;                 //����Connection����
		    PreparedStatement pstmt = null;         //����PreparedStatement����
		    ResultSet rs = null;                    //����ResultSet����
		    try {
		        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	//
		        	String checkid = rs.getString("checkid");
		        	String fmid = rs.getString("fmid");
		        	String fmno = rs.getString("fmno");
		        	String memid = rs.getString("memid");
		        	String fmname = rs.getString("fmname");
		        	String zhs = rs.getString("zhs");
		        	String zje = rs.getString("zje");
		        	String czje = rs.getString("czje");
		        	String cczje = rs.getString("cczje");
		        	String rezje = rs.getString("rezje");
		        	//
		            html +="<tr id = 'familytr"+fmid+"'>";		
			    		//����ֵ	  
		            	html += "<td height='23' class='colValue pointer'\"><img id = 'img' src='/db/page/images/check1.jpg' alt= '�������' onclick=\"ChioceDo(this,'"+checkid+"','"+fmname+"')\"/></td>";
						html += "<td height='23' class='colValue'\">"+fmno+"</td>";
						html += "<td height='23' class='colValue'\">"+fmname+"</td>";
						html += "<td height='23' width='80' class='colValue'\">"+rezje+"</td>";
						html += "<td height='23' width='80' class='colValue' ><span id = 'countmoney"+fmid+"'>"+zje+"</span></td>";
						if("1".equals(mode)){
							html += "<td height='23' width='80' class='colValue'>";
								html += "<span style='display:none;' id = 'usercheck"+checkid+"'></span>";
								html += "<span style='display:none;' id = 'usermoney"+fmid+"'>"+czje+"</span>";
								html += "<input size = '8' style = 'text-align:right;' type='text' value = '"+czje+"' id = 'usermoney"+checkid+"'\" onchange=\"SumMoneyFamily('"+fmid+"','"+checkid+"','"+childflag+"')\"></input>";
							html += "</td>";
						}else{
							html += "<td height='23' width='80' class='colValue'>";
								html += "<span style='display:none;' id = 'usercheck"+checkid+"'></span>";
								html += "<span style='display:none;' id = 'usermoney"+fmid+"'>"+czje+"</span>";
								html += "<input size = '8' style = 'display:none;text-align:right;' type='text' value = '"+czje+"' id = 'usermoney"+checkid+"'></input>";
							html += ""+czje+"</td>";						
						}
						//���ڷ���ʩ��
						if(bchild){
							html += "<td height='23' class='colValue' ><span id = 'allchildmoney"+fmid+"'>"+cczje+"</span></td>";
						}						
						html += "<td height='23' width='23' class='colValue pointer'>";
							html += "<img src='/db/page/images/policyinfo.jpg' alt= '�����Ϣ' onclick=\"GetCheckPolicyInfosHtml('"+fmid+"','"+memid+"','"+fmname+"')\"/>";
						html += "</td>";
							html += "<td height='23' width='23' class='colValue pointer'>";
							html += "<img src='/db/page/images/policysql.jpg' alt= '������׼' onclick=\"GetCheckPolicySqlsHtml('"+checkid+"','"+fmname+"')\"/>";
						html += "</td>";
						html += "<td height='23' width='23' class='colValue pointer'>";
							html += "<img src='/db/page/images/policyacc.jpg' alt= '���㹫ʽ' onclick=\"GetCheckPolicyAccsHtml('"+checkid+"','"+fmname+"')\"/>";
						html += "</td>";
						html += "<td height='23' class='colValue pointer' style='color: #660099;' onclick=\"ViewPolicys('"+fmid+"')\">��ؾ������</td>";
						//���ڷ���ʩ��
						if(!bchild){
							html += "<td height='23' class='colValue pointer'>";
								html += "<img width='12' height='12' src='/db/page/images/close.gif' alt= '�Ƴ�' onclick=\"MoveDel('familytr"+fmid+"')\"/>";
							html += "</td>";
						}						
					html +="</tr>";					
					//���ڷ���ʩ��
					if(bchild){
						//����ͥ����ʩ�����
						StringBuffer childhtml = getChildCheckItemsTable(pid,fmid,empid);
						//������Ӳ�����
						html +="<tr height='10' class='pointer' id = 'policytr"+fmid+"'>";						
							//���ڷ���ʩ��
							if(bchild){
								html += "<td width='46' class='colValue' style='text-align:left;'>";
									html += "<img id = 'imgs' src='/db/page/images/membermax.jpg' alt= '����ʩ���б�' onclick=\"ViewChildtb(this,'childtr"+fmid+"')\"/>";								
								html += "</td>";
								html += "<td colspan = '10' class='colValue' style='text-align:right;'>";
									html += "<img width='10' height='10' src='/db/page/images/close.gif' alt= '�Ƴ�' onclick=\"MoveDelChild('familytr"+fmid+"','policytr"+fmid+"','childtr"+fmid+"')\"/>";									
								html += "</td>";
							}
						html +="</tr>";
						//����ʩ���б�
						if(row==0){							
			            	html +="<tr id = 'childtr"+fmid+"'>";		
			    				//����ͥ����ʩ��ֵ						
			            		html += "<td colspan = '11' class='colValue'>"+childhtml+"</td>";						
			            	html +="</tr>";
						}else{
			            	html +="<tr id = 'childtr"+fmid+"' style='display:none;'>";	
			            		//����ͥ����ʩ��ֵ		
			            		html += "<td colspan = '11' class='colValue'>"+childhtml+"</td>";						
			            	html +="</tr>";
						}
					}
										
		        	//��¼���
		        	row++;
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
	    html +="</table>";  
	    //
	    shtml.append(html);
	    //log.debug(shtml);
		return shtml;
	}
	/**
	 * ��ȡ��ͥ�ͳ�Ա����ʩ�������б�
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
		//��������
	    ConstantDefine constantdefine = new ConstantDefine();
	    // ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //ҵ��������ѯ������
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//��������
	    empdepth = tableinfoquery.getempdepth(empid);
	    //��������
    	sdeptname1 = constantdefine.POLICYQUERY_5;
    	dempth1 = constantdefine.POLICYQUERYCODE_5;
    	sflow1 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth1);
    	//�ֵ�����
    	sdeptname2 = constantdefine.POLICYQUERY_4;
    	dempth2 = constantdefine.POLICYQUERYCODE_4;
    	sflow2 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth2);
    	//������
    	sdeptname3 = constantdefine.POLICYQUERY_3;
    	dempth3 = constantdefine.POLICYQUERYCODE_3;
    	sflow3 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth3);
    	//�м���
    	sdeptname4 = constantdefine.POLICYQUERY_2;
    	dempth4 = constantdefine.POLICYQUERYCODE_2;
    	sflow4 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth4);
    	//ʡ����
    	sdeptname5 = constantdefine.POLICYQUERY_1;
    	dempth5 = constantdefine.POLICYQUERYCODE_1;
    	sflow5 = policymanagecheckquery.getPolicyFlowFlagFromId(pid,dempth5); 
	    //
		//
		html += "<table id = 'childstb"+fmid+"' width='90%' cellpadding='0' cellspacing='0'>";
		
		html += "<tr style='color: #6BA6FF;text-align:center;font-size:12px;'>";						
			temp ="<td height='18' colspan = '10'>���Ϸ���ʩ��������׼�б�</td>";
			html +=temp;
		html +="</tr>";
		
		html += "<tr style='color: #6BA6FF;background-color:#ECECEC;text-align:center;font-size:12px;'>";						
			temp ="<td height='18'>����</td>";
			html +=temp;
			temp ="<td height='18'>����ʩ������</td>";
			html +=temp;
			temp ="<td height='18'>���㷽ʽ</td>";
			html +=temp;
			temp ="<td height='18'>����</td>";
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
        		"and b.family_id = '"+fmid+"'";   //����SQL���
        
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
		    		//����ֵ
            		html += "<td height='23' class='colValue pointer'\"><img id = 'img' src='/db/page/images/check1.jpg' alt= '�������' onclick=\"ChioceDoChild(this,'"+checkid+"','"+memname+"')\"/></td>";
					html += "<td height='23' class='colValue pointer' style='color: #660099;' onclick=\"GetCheckPolicyChildSqlsHtml('"+checkid+"','"+name+"')\">"+name+"</td>";
					if("2".equals(sqltype)){
						sqltype = "��Ա����";						
					}else{
						sqltype = "��ͥ����";
					}
					html += "<td height='23' class='colValue'>"+sqltype+"</td>";
					html += "<td height='23' width='80' class='colValue'>"+memname+"</td>";
					//�����û������޸ľ������ʶ
					//id = 'childcheck"+checkid+"'(�û�)������ID
					//id = 'childmoney"+checkid+"'(�û�)��������
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
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        
        //
	    html +="</table>";  
		//
	    if(brow){
	    	shtml.append(html);
	    }else{
	    	shtml.append("���������ʩ����׼��¼!");
	    }
	    
	    //log.debug(shtml);
		return shtml;
	}
}
