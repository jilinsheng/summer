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
	 * ��ȡ����ҵ�������Ϣ��ϸ��Ϣ
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
    	//ҵ���������
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
			//��̬����
			String [] athcol = thcol.split(",");
			String [] atdisc = tdisc.split(",");
	    	int ileng = athcol.length;
	    	int ilengd = atdisc.length;
	    	//
	    	String sql = physql;
			//log.debug("sql:"+sql);
			Connection conn = null;                 //����Connection����
		    PreparedStatement pstmt = null;         //����PreparedStatement����
		    ResultSet rs = null;                    //����ResultSet����
		    try {
		        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	html += "<tr class='rowFieldInfo'>";
						temp ="<td height='23'>����</td>";
						html +=temp;
						temp ="<td height='23'>����ֵ</td>";
						html +=temp;			
					html +="</tr>";
		        	for (int h=0;h<ileng;h++){
		        		String tempthn = athcol[h];	
		        		String tempthv = "";
		        		//�Ƿ��ֵ�ֵ
		        		boolean bisdisc = false;
		        		for (int j=0;j<ilengd;j++){
		        			String tempdisc = atdisc[j];
		        			String temprs = new Integer(h+1).toString();		        			
		        			if(tempdisc.equals(temprs)){
		        				bisdisc = true;
		        			}
		        		}
		        		//�ֵ�ֵ		        			
	        			if(bisdisc){	        				
	        				tempthv = rs.getString(h+1);
	        				tempthv = pv.getDictionartHandle().getDictsortToValue(dictionary,tempthv);
	        				if(null == tempthv || "".equals(tempthv)){
	        					tempthv = "��";
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
		        DBUtils.close(rs);                  //�رս����
		        DBUtils.close(pstmt);               //�ر�PreparedStatement
		        DBUtils.close(conn);                //�ر�����
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
	 * ��ȡ������ѯ����ҵ�������Ϣ��ѯQL���
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
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//��������
        ConstantDefine constantdefine = new ConstantDefine();
		//
		String tftable,tmtable,tfield,tfamily,tmember,tdept;
		String deptid = "",deptname = "",memname = "",memnames = "";
		//��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";//��ͥ��    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//��Ա��Ϣ
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		//
		tfield = "MEMBERNAME";//����
		memname = tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		memnames = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		//������
		tdept = "SYS_T_ORGANIZATION";  
		//
		tfield = "ORGANIZATION_ID";//��ͥ����ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//��ͥ����
		//��ѯ��
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
        		"order by a.logicname,b.logicname";   //����SQL���
		//log.debug("sql:"+sql);
		Connection conn = null;                 //����Connection����
	    PreparedStatement pstmt = null;         //����PreparedStatement����
	    ResultSet rs = null;                    //����ResultSet����
	    try {
	        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
	        			//������ѯ
	        			bdeptflag = true; 
	        		}else{
	        			//��ѯ�ֶ�
	        			if(null == tselect || "".equals(tselect)){
	        				tselect = temp;
        	    		}else{
        	    			tselect += ","+temp;
        	    		}	        			
	        			//
	        			String fieldname = rs.getString("fieldname");
	        			if(null != fieldname){
	        				//��ѯ������
	        				if(null == tfieldname || "".equals(tfieldname)){
	        					tfieldname = fieldname;
	        	    		}else{
	        	    			tfieldname += ","+fieldname;
	        	    		}	        				
	        			}
	        			//�ֵ�ֵ
	        			String type = rs.getString("fieldtype");
	    	        	if(null != type){
	    	        		//�ֵ�ֵ��
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
	        DBUtils.close(rs);                  //�رս����
	        DBUtils.close(pstmt);               //�ر�PreparedStatement
	        DBUtils.close(conn);                //�ر�����
	    }
	    //��ѯ����
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
			//������
			if(null == tfieldname || "".equals(tfieldname)){
				tfieldname = "����";
    		}else{
    			tfieldname += ","+"����";
    		}
	    }
	    //��ͥ��̬��(����)
		String [] tfromname = tempfrom.split(",");
		String tempname = "";
		boolean bisflag = false;
    	int ileng1 = tfromname.length;
	    for (int h=0;h<ileng1;h++){
	    	tempname = tfromname[h];
	    	//Ĭ�ϱ�
	    	String [] fromname = tfrom.split(",");
	    	int ilen2 = fromname.length;
		    for (int j=0;j<ilen2;j++){
		    	String tname = fromname[j];	    	
		    	if(tempname.equals(tname)){
		    		bisflag = true;
		    	}
		    }
		    //����Ĭ�ϱ�
		    if(bisflag){
		    	tfrom = tfrom + "," + tempname;				
			}	
	    }
	    //������ѯ
	    if("1".equals(acctype)){
	    	//��Ա����
	    	//
		    //��̬��ѯ�ֶ�
			String [] aselect = tselect.split(",");
			String tempsname = "";
			boolean bisselect = false;
	    	int ileng = aselect.length;
		    for (int h=0;h<ileng;h++){
		    	tempsname = aselect[h];
		    	//Ĭ�ϲ�ѯ�ֶ�
		    	if(memnames.equals(tempsname)){
		    		bisselect = true;
		    	} 	
		    }
		    //����Ĭ�ϲ�ѯ�ֶ�
		    if(!bisselect){
		    	//��ѯ�ֶ�
		    	tselect = memnames + "," + tselect;	
		    	//��ѯ����
				tfieldname = memname + "," + tfieldname;	
			}
		    //
		    //������ѯ
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
	    	//��ͥ����
	    	if(null == twhere || "".equals(twhere)){
				twhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
			}else{
				twhere += " and "+"INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
			}
	    }
	    
	    //
	    //������
	    thcol = tfieldname;
	    //�ֵ�ֵ�����
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
	    //������
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
	 * ��ȡ������ͥ���߳�Աҵ���׼SQL
	 * @param checkid
	 * @return
	 */
	public StringBuffer getCheckPolicySqlsHtml(String checkid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",sql = "";
    	//
    	html += "<fieldset class='list'>";
    	html += "<legend class='legend'>����ҵ���׼SQL</legend>";			
	    	html += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		    	html += "<tr>";
		    	html += "<td valign='top' class = 'myspace'>";
		    	//
		    	sql = "select locsql from biz_t_optchecksql where optcheck_id = '"+checkid+"'";
		    	//log.debug("sql:"+sql);
				Connection conn = null;                 //����Connection����
			    PreparedStatement pstmt = null;         //����PreparedStatement����
			    ResultSet rs = null;                    //����ResultSet����
			    try {
			        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
			        rs = pstmt.executeQuery();
			        while (rs.next()) { 
			        	temp = rs.getString("locsql");
			        }
			    } catch (SQLException e) {
			        log.debug(e.toString());
			    } finally {
			        DBUtils.close(rs);                  //�رս����
			        DBUtils.close(pstmt);               //�ر�PreparedStatement
			        DBUtils.close(conn);                //�ر�����
			    }
			    //
			    if(null == temp){
			    	html += "��";
			    }else{
			    	//
			    	int ibeg = temp.indexOf("where");//������where����λ��
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
	 * ��ȡ������ͥ���߳�Աҵ�����SQL
	 * @param checkid
	 * @return
	 */
	public StringBuffer getCheckPolicyAccsHtml(String checkid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",sql = "";
    	//
    	//
    	html += "<fieldset class='list'>";
    	html += "<legend class='legend'>����ҵ����㹫ʽSQL</legend>";			
	    	html += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		    	html += "<tr>";
		    	html += "<td valign='top' class = 'myspace'>";
		    	//
		    	sql = "select acclocsql from biz_t_optchecksql where optcheck_id = '"+checkid+"'";
		    	//log.debug("sql:"+sql);
				Connection conn = null;                 //����Connection����
			    PreparedStatement pstmt = null;         //����PreparedStatement����
			    ResultSet rs = null;                    //����ResultSet����
			    try {
			        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
			        rs = pstmt.executeQuery();
			        while (rs.next()) { 
			        	temp = rs.getString("acclocsql");
			        }
			    } catch (SQLException e) {
			        log.debug(e.toString());
			    } finally {
			        DBUtils.close(rs);                  //�رս����
			        DBUtils.close(pstmt);               //�ر�PreparedStatement
			        DBUtils.close(conn);                //�ر�����
			    }
			    //
			    if(null == temp){
			    	html += "��";
			    }else{
			    	//
			    	int iend = temp.indexOf("from");//��from����λ��
			    	if(iend != -1){			    		
			    		temp = temp.substring(0, iend);
			    		int ibeg = temp.indexOf(",");//��,����λ��
				    	if(ibeg != -1){			    		
				    		temp = temp.substring(ibeg+1,temp.length());
				    		int ibegas = temp.indexOf("as");//��as����λ��
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
	 * ��ȡ������ͥ���߳�Աҵ�����ʩ����׼SQL
	 * @param checkid
	 * @return
	 */
	public StringBuffer getCheckPolicyChildSqlsHtml(String checkid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",sql = "";
    	//
    	html += "<fieldset class='list'>";
    	html += "<legend class='legend'>����ҵ�����ʩ����׼SQL</legend>";			
	    	html += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		    	html += "<tr>";
		    	html += "<td valign='top' class = 'myspace'>";
		    	//
		    	sql = "select locsql from biz_t_optcheckchildsql where optcheckchild_id = '"+checkid+"'";
		    	//log.debug("sql:"+sql);
				Connection conn = null;                 //����Connection����
			    PreparedStatement pstmt = null;         //����PreparedStatement����
			    ResultSet rs = null;                    //����ResultSet����
			    try {
			        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
			        rs = pstmt.executeQuery();
			        while (rs.next()) { 
			        	temp = rs.getString("locsql");
			        }
			    } catch (SQLException e) {
			        log.debug(e.toString());
			    } finally {
			        DBUtils.close(rs);                  //�رս����
			        DBUtils.close(pstmt);               //�ر�PreparedStatement
			        DBUtils.close(conn);                //�ر�����
			    }
			    //
			    if(null == temp){
			    	html += "��";
			    }else{
			    	//
			    	int ibeg = temp.indexOf("where");//������where����λ��
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
