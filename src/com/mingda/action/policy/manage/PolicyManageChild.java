package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;

public class PolicyManageChild {
	static Logger log = Logger.getLogger(PolicyManageChild.class);
	
	/**
     * ȡ��ҵ�����ʩ���б�
     * @param pid
     * @return
     */
    public StringBuffer getPolicyChildsHtml() {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String cpid = "",cpropid = "",cpropname = "",cname = "",cdesc = "",csqltype = "",cmoneytype = "",cstatus = "";
    	//����ҵ���ѯ������
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>����ʩ�������б�</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ҵ��</td>";
			html +=temp;
			temp ="<td height='23'>��ϸ����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select policychild_id,policy_id,name,policydesc,sqltype,moneytype,status " +
        		"from doper_t_policychild order by policy_id";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cpid = rs.getString("policychild_id");
                cpropid = rs.getString("policy_id");
                cpropname = policymanagecheckquery.getPolicyNameFromId(cpropid);
                cname = rs.getString("name");
                cdesc = rs.getString("policydesc");
                csqltype = rs.getString("sqltype");
                cmoneytype = rs.getString("moneytype");
                cstatus = rs.getString("status");
                
                html +="<tr>";		
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+cpid+"','"+cname+"','"+cmoneytype+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+cstatus+"'>"+cname+"</td>";
					html += "<td height='23' class='colValue'>"+cpropname+"</td>";
					html += "<td height='23' class='colValue'>"+cdesc+"</td>";
					if(cstatus.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildStatus('"+cpid+"','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildStatus('"+cpid+"','1')\">����</button></td>";	
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
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"GetPolicyChildItemHtml('')\">��������ʩ��</button>";    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * ȡ�÷���ʩ������
     * @param childpid
     * @return
     */
    public StringBuffer getPolicyChildItemHtml(String childpid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",mode = "";
    	String cpid = "",cpropid = "",cpropname = "",cname = "",cdesc = "",csqltype = "",cmoneytype = "",cstatus = "";
    	//
    	if(childpid.equals("")){
    		mode = "add";
    		cpropname = "[����]";
    	}else{
    		mode = "edit";
    		cpropname = getPolicyChildNameFromId(childpid);
    		cpropname = "["+cpropname+"]";
    		//
            String sql = "select policychild_id,policy_id,name,policydesc,sqltype,moneytype,status " +
            		"from doper_t_policychild  where policychild_id = '" + childpid + "'";   //����SQL���
            
    		//
    		//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	cpid = rs.getString("policychild_id");
                    cpropid = rs.getString("policy_id");
                    cname = rs.getString("name");
                    cdesc = rs.getString("policydesc");
                    csqltype = rs.getString("sqltype");
                    cmoneytype = rs.getString("moneytype");
                    cstatus = rs.getString("status");  	
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
    	html += "<legend  class='legend'>"+cpropname+"����ʩ������</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
	        //
			html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��׼���㷽ʽ</td>";
				html += "<td align='center' style='font-size:12px;'>";
				if(csqltype.equals("1")){//��ͥ	
					html += "<input type='radio' name='rd' id='rdf' checked />��ͥ����";
					html += "<input type='radio' name='rd' id='rdm' />��Ա����";
				}else if(csqltype.equals("2")){//��Ա
					html += "<input type='radio' name='rd' id='rdf' />��ͥ����";
					html += "<input type='radio' name='rd' id='rdm' checked />��Ա����";
				}else{
					html += "<input type='radio' name='rd' id='rdf' checked />��ͥ����";
					html += "<input type='radio' name='rd' id='rdm' />��Ա����";
				}
				html += "</td>";
			html +="</tr>";
			html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����ҵ��</td>";				
				html += "<td><div id = 'policy'>";						
				    	temp = getPolicyChildChoiceHtml("policyselect",cpropid,cpid).toString();
					    html += temp;					    
			    html +=	"</div></td>";
			html +="</tr>";
			
	        html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'cname' value = '"+cname+"'></input></td>";
	    	html +="</tr>";
	    	html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ϸ����</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'cdesc' value = '"+cdesc+"'></input></td>";
	    	html +="</tr>";
	    		    	
	    		    	    
	        //
	    	html +="</table>";
	    	
	    	if(mode.equals("add")){//����
		    	html += "<button class = 'btn' onclick=\"SavePolicyChild('"+mode+"')\">����</button>"; 	   		
	    	}else if(mode.equals("edit")){//�༭
	    		//����ʱ��������
		    	if(cstatus.equals("1")){
		    		html += "<button class = 'btn' onclick=\"SavePolicyChild('"+mode+"')\">����</button>";
		    		html += "<button class = 'btn' onclick=\"GoMatchPage('next')\">��һ��>>ɸѡ��׼����</button>";  
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
     * ���·���ʩ��״̬
     * @param Id
     * @param Status
     * @return
     */
    public String updatePoilcyChildStatus(String Id,String Status) {
        String srep = "";
          String sql = "update doper_t_policychild set status = '"+Status+"' where policychild_id = '"+Id+"'";  //���µ���״̬SQL

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
                srep = "ͣ�÷���ʩ�������ɹ�!";
              }else{
                srep = "���÷���ʩ�������ɹ�!";
              }
          } catch (SQLException e) {
        	  if("0".equals(Status)){
                  srep = "ͣ�÷���ʩ������ʧ��!";
              }else{
                  srep = "���÷���ʩ������ʧ��!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //�ر�PreparedStatement
              //DBUtils.close(conn);                //�ر�����
          }
          return srep;
     } 
    /**
     * ���·���ʩ��
     * @param sql
     * @param mode
     * @return
     */
    public String savePolicyChild(String sql,String mode) {
        String srep = "";
        
        //log.debug(sql);
          Connection conn = null;                 //����Connection����
          PreparedStatement pstmt = null;         //����PreparedStatement����
          try {
              conn = DBUtils.getConnection();     //��ȡ���ݿ�����
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
              pstmt.execute();
              conn.commit(); 
              if("add".equals(mode)){
                srep = "��������ʩ�������ɹ�!";
              }else if("edit".equals(mode)){
                srep = "�޸ķ���ʩ�������ɹ�!";
              }     
          } catch (SQLException e) {
        	  if("add".equals(mode)){
                  srep = "��������ʩ������ʧ��!";
              }else if("edit".equals(mode)){
                  srep = "�޸ķ���ʩ������ʧ��!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //�ر�PreparedStatement
              //DBUtils.close(conn);                //�ر�����
          }
          return srep;
     }
    /**
     * ȡ��ҵ�����ʩ����׼�б�
     * @param pid
     * @param sname
     * @return
     */
    public StringBuffer getPolicyChildSqlsHtml(String sid,String sname) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String vsid = "",vprosid = "",vname = "",vdesc = "",vstatus = "";
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>["+sname+"]��׼�б�</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>��׼����</td>";
			html +=temp;
			temp ="<td height='23'>��ϸ����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select policychildsql_id,policychild_id,name,sqldesc,physql,locsql,status  " +
        		"from doper_t_policychildsql where policychild_id = '"+sid+"'";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vsid = rs.getString("policychildsql_id");
                vname = rs.getString("name");
                vdesc = rs.getString("sqldesc");
                vstatus = rs.getString("status");
                
                html +="<tr>";		
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDoSql(this,'"+vsid+"','"+vname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+vstatus+"'>"+vname+"</td>";
					html += "<td height='23' class='colValue'>"+vdesc+"</td>";
					if(vstatus.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildSqlStatus('"+vsid+"','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildSqlStatus('"+vsid+"','1')\">����</button></td>";	
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
	    html += "</fieldset>";
	    //
	    html += "<button class = 'btn' onclick=\"GoMatchPage('back')\">"+sname+"<<��һ��</button>"; 
	    html += "<button class = 'btn' onclick=\"GetPolicyChildSqlItemHtml('')\">����ɸѡ��׼</button>";
	    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * ȡ�÷���ʩ����׼����
     * @param sqlsid
     * @return
     */
    public StringBuffer getPolicyChildSqlItemHtml(String sqlsid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",mode = "";
    	String vsid = "",vprosid = "",vproname = "",vname = "",vdesc = "",vstatus = "";
    	//
    	if(sqlsid.equals("")){
    		mode = "add";
    		vproname = "[����]";
    	}else{
    		mode = "edit";
    		vproname = getPolicyChildSqlNameFromId(sqlsid);
    		vproname = "["+vproname+"]";
    		//
            String sql = "select policychildsql_id,policychild_id,name,sqldesc,physql,locsql,status  " +
        		"from doper_t_policychildsql where policychildsql_id = '"+sqlsid+"'";   //����SQL���
            
    		//
    		//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	vsid = rs.getString("policychildsql_id");
                    vname = rs.getString("name");
                    vdesc = rs.getString("sqldesc");
                    vstatus = rs.getString("status"); 	
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
    	html += "<legend  class='legend'>"+vproname+"��׼����</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
	     	//����ֵ		
			
	        html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��׼����</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'vname' value = '"+vname+"'></input></td>";
			html +="</tr>";	    	
	    	
	    	html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ϸ����</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'vdesc' value = '"+vdesc+"'></input></td>";
	    	html +="</tr>";
	    		    	
	    		    	    
	        //
	    	html +="</table>";
	    	
	    	if(mode.equals("add")){//����
		    	html += "<button class = 'btn' onclick=\"SavePolicyChildSql('"+mode+"')\">����</button>"; 	   		
	    	}else if(mode.equals("edit")){//�༭
	    		//����ʱ��������
		    	if(vstatus.equals("1")){
		    		html += "<button class = 'btn' onclick=\"sqlaction()\">ɸѡ��׼����</button>";  
		    		html += "<button class = 'btn' onclick=\"SavePolicyChildSql('"+mode+"')\">����</button>";		    		
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
     * ���·���ʩ����׼״̬
     * @param Id
     * @param Status
     * @return
     */
    public String updatePoilcyChildSqlStatus(String Id,String Status) {
        String srep = "";
          String sql = "update doper_t_policychildsql set status = '"+Status+"' where policychildsql_id = '"+Id+"'";  //���µ���״̬SQL

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
                srep = "ͣ�÷���ʩ����׼�����ɹ�!";
              }else{
                srep = "���÷���ʩ����׼�����ɹ�!";
              }
          } catch (SQLException e) {
        	  if("0".equals(Status)){
                  srep = "ͣ�÷���ʩ����׼����ʧ��!";
              }else{
                  srep = "���÷���ʩ����׼����ʧ��!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //�ر�PreparedStatement
              //DBUtils.close(conn);                //�ر�����
          }
          return srep;
     }
    /**
     * ���·���ʩ����׼
     * @param sql
     * @param mode
     * @return
     */
    public String savePolicyChildSql(String sql,String mode) {
        String srep = "";
        
        //log.debug(sql);
          Connection conn = null;                 //����Connection����
          PreparedStatement pstmt = null;         //����PreparedStatement����
          try {
              conn = DBUtils.getConnection();     //��ȡ���ݿ�����
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
              pstmt.execute();
              conn.commit(); 
              if("add".equals(mode)){
                srep = "��������ʩ����׼�����ɹ�!";
              }else if("edit".equals(mode)){
                srep = "�޸ķ���ʩ����׼�����ɹ�!";
              }     
          } catch (SQLException e) {
        	  if("add".equals(mode)){
                  srep = "��������ʩ����׼����ʧ��!";
              }else if("edit".equals(mode)){
                  srep = "�޸ķ���ʩ����׼����ʧ��!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //�ر�PreparedStatement
              //DBUtils.close(conn);                //�ر�����
          }
          return srep;
     }
    /**
     * ȡ�÷���ʩ����׼SQL���
     * @param id
     * @param mode
     * @return
     */
    public String getPolicyChildSqlItem(String id,String mode) {
        String srep = "";
        String sql = "select physql, locsql from doper_t_policychildsql where policychildsql_id = '" + id + "'";   //����SQL���
        
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
     * ���·���ʩ����׼SQL����
     * @param Id
     * @param PhySql
     * @param LocSql
     * @return
     */
    public String updatePolicyChildSql(String Id,String PhySql,String LocSql) {
		String srep = "";
		//������ת��ȥ�����ҿո�
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update doper_t_policychildsql set " +
				"physql = '"+PhySql+"'," +
				"locsql = '"+LocSql+"' where policychildsql_id = '"+Id+"'";  //���µ���״̬SQL
		
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
     * �ɷ���ʩ��IDȡ�÷���ʩ������
     * @param name
     * @return
     */
    public String getPolicyChildNameFromId(String id) {
        String srep = "";
        String sql = "select policychild_id,name from doper_t_policychild where policychild_id = '" + id + "'";   //����SQL���
        
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
     * �ɷ���ʩ��IDȡ�÷���ʩ����׼����
     * @param name
     * @return
     */
    public String getPolicyChildSqlNameFromId(String id) {
        String srep = "";
        String sql = "select policychildsql_id,name from doper_t_policychildsql where policychildsql_id = '" + id + "'";   //����SQL���
        
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
     * ȡ�÷���ʩҵ������ѡ��
     * @param sname
     * @param cpropid
     * @param cpid
     * @return
     */
    public StringBuffer getPolicyChildChoiceHtml(String sname,String cpropid,String cpid){
        StringBuffer shtml= new StringBuffer("");
        String id = "",name = "";
        
        String sql = "select policy_id, name from doper_t_policy where flag = '1' order by policy_id";      
        //log.debug(sql);
        //
		if(existsPolicyChildSQLFromId(cpid)){
			shtml.append("<select id='"+sname+"' style = 'width:100%;font-size:12px;' disabled = 'disabled'>"); 
		}else{
			shtml.append("<select id='"+sname+"' style = 'width:100%;font-size:12px;'>"); 
		}
          
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
                if(id.equals(cpropid)){
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
        return shtml;
    }
    /**
     * �Ƿ���ڷ���ʩ��
     * @param parid
     * @param name
     * @return
     */
    public boolean existsPolicyChildFromName(String parid,String name){
		boolean  brep = false;
		
		String sql = "select policychild_id,name from doper_t_policychild " +
				"where name = '" + name + "' and policy_id = '"+parid+"'";   //����SQL���
		
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
				brep = true;
				break;
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
     * �Ƿ���ڷ���ʩ����׼
     * @param parid
     * @param name
     * @return
     */
    public boolean existsPolicyChildSql(String parid,String name){
		boolean  brep = false;
		
		String sql = "select policychildsql_id,name from doper_t_policychildsql " +
				"where name = '" + name + "' and policychild_id = '"+parid+"'";   //����SQL���
		
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
				brep = true;
				break;
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
     * �ñ�׼�Ƿ�������úõ�ɸѡ����SQL���
     * @param id
     * @return
     */
    public boolean existsPolicyChildPhySql(String id){
		boolean  brep = false;
		
		String sql = "select policychildsql_id,physql from doper_t_policychildsql " +
				"where policychildsql_id = '"+id+"' and physql is null";   //����SQL���
		
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
				brep = true;
				break;
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
     * �Ƿ���ڷ���ʩ������
     * @param pid
     * @return
     */
    public boolean existsPolicyChildFromId(String pid){
		boolean  brep = false;
		
		String sql = "select a.policychild_id from doper_t_policychild a,doper_t_policy b " +
				"where a.status = '1' and a.policy_id = b.policy_id and b.policy_id = '"+pid+"'";   //����SQL���
		
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
				brep = true;
				break;
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
     * ����ʩ���Ƿ��Ѿ����ñ�׼����
     * @param childpid
     * @return
     */
    public boolean existsPolicyChildSQLFromId(String childpid){
		boolean  brep = false;
		
		String sql = "select policychildsql_id from doper_t_policychildsql where policychild_id = '"+childpid+"'";   //����SQL���
		
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
				brep = true;
				break;
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
     * ��ͥ�Ƿ���Ϸ���ʩ����׼
     * @param childpid
     * @return
     */
    public boolean getPolicyChildFamilyFlag(String childpid,String fmid) {
    	boolean  brep = false;
    	String sphysql = "",sstatus = "";
        String sql = "select physql,status from doper_t_policychildsql " +
        		"where physql is not null " +
        		"and policychild_id = '"+childpid+"' ";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	sphysql = rs.getString("physql");
            	sstatus = rs.getString("status");
            	//
            	if(sstatus.equals("0")){//ͣ��
            		break;
            	}
            	
            	//����SQL���������Ƿ��������
            	brep = existsFamilyIdFromPolicyChild(sphysql,fmid);
            	if(brep){//����������
            		break;
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
     * ��ͥ�Ƿ���Ϸ���ʩ������
     * @param physql
     * @param fmid
     * @return
     */
    public boolean existsFamilyIdFromPolicyChild(String physql,String fmid){
		boolean  brep = false;
		
		String sql = physql + " and  INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";   //����SQL���
		
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
				brep = true;
				break;
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
