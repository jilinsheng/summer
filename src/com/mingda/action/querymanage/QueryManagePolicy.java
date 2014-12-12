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
	//��������
	ConstantDefine constantdefine = new ConstantDefine();
	/**
	 * ����ҵ���׼SQL
	 * @param hashmap
	 * @param conn 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public StringBuffer GetSqlPolicyStandard(HashMap hashmap, Connection conn) {
		StringBuffer shtml= new StringBuffer("");
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
		/**ͨ�õ�SQL��������**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**����Ĵ������**/
		String tpname = hashmap.get("ssuperpolicy").toString();
		String tnotpname = hashmap.get("snotpolicy").toString();
		String ttype = hashmap.get("ttype").toString();
		/**��������**/
		//ҵ������
		String stempflag = constantdefine.POLICYFLAGCODE_IS;
		//
		//��Ա��Ϣ
		String tfname = "INFO_T_FAMILY";    		
		String tftable = tableinfoquery.gettablelocicfromphysic(tfname);
		String tmname = "INFO_T_MEMBER";    		
		String tmtable = tableinfoquery.gettablelocicfromphysic(tmname);
		//
		//��ͥ���߳�Ա����
		if(ttype.equals("family")){
			//��ѯ�ֶ�
			tselect = "INFO_T_FAMILY.FAMILY_ID";	
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = tftable;
			}else{
				tfrom = tfrom + "," + tftable;
			} 
		}else{
			//��ѯ�ֶ�
			tselect = "INFO_T_FAMILY.FAMILY_ID,INFO_T_MEMBER.MEMBER_ID";
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = tmtable + "," + tftable;
			}else{
				tfrom = tfrom + "," + tmtable + "," + tftable;
			} 
		}
		//��ѯ����
		if(null == twhere || twhere.equals("")){
			//��ͥ����״̬
			twhere = " INFO_T_FAMILY.STATUS = '1'";
		}else{
			//��ͥ����״̬
			twhere += " and INFO_T_FAMILY.STATUS = '1'";
		}		
		//
		String stemp = "",pname = "",pid = "";		
		//����ҵ��====================================		
		if(null == tpname || tpname.equals("")){
			//û������ҵ��
		}else{    		
			//
			//ҵ����
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
	    	    //����ҵ��
				String tmp = "select family_id "
					+ "from BIZ_T_FAMILYSTATUS "
					+ "where BIZ_T_FAMILYSTATUS.POLICY_ID in ("+stemp+") "
					+ "and BIZ_T_FAMILYSTATUS.FLAG = '"+stempflag+"' ";
	    	    //
	    	    twhere += " and  exists " + "(" + tmp + " and INFO_T_FAMILY.FAMILY_ID = BIZ_T_FAMILYSTATUS.FAMILY_ID)";
    	    }    	    
		}
		//����ҵ��=================================	    		
		if(null == tnotpname || tnotpname.equals("")){
			//û�в���ҵ��
		}else{
			//
			//ҵ����
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
	    	    //����ҵ��
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
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
	/**
	 * ���㹫ʽ
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public StringBuffer GetSqlPolicyAcc(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		/**ͨ�õ�SQL��������**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**����Ĵ������**/
		String ttype = hashmap.get("ttype").toString();
		String selwhere = hashmap.get("selwhere").toString();
		/**��������**/		
		//��Ա��Ϣ
		String tfname = "INFO_T_FAMILY";    		
		String tftable = tableinfoquery.gettablelocicfromphysic(tfname);
		String tmname = "INFO_T_MEMBER";    		
		String tmtable = tableinfoquery.gettablelocicfromphysic(tmname);
		//
		//��ͥ���߳�Ա����
		if(ttype.equals("family")){
			//��ѯ�ֶ�
			tselect = "INFO_T_FAMILY.FAMILY_ID as familyid,"+selwhere+" as acccount ";	
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = tftable;
			}else{
				tfrom = tfrom + "," + tftable;
			} 
		}else{
			//��ѯ�ֶ�
			tselect = "INFO_T_FAMILY.FAMILY_ID as familyid,INFO_T_MEMBER.MEMBER_ID as memberid,"+selwhere+" as acccount ";	
			//��ѯ��
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
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
    /**
     * ��ȡҵ������ҳ��
     * @param id
     * @return
     */
    public StringBuffer getPolicyItemsHtml(String id,Connection conn) {
    	StringBuffer shtml= new StringBuffer("");
    	String sdesc = "",sname = "",sdate = "";
    	//����ҵ���ѯ������
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//
    	String sql ="select  name, descr,begdt, note from doper_t_policy where policy_id = '"+id+"'";
    	//log.debug(sql);
    	shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
        shtml.append("<tr align='center' class='colField'>");
          shtml.append("<td>����</td>");
          shtml.append("<td>��ʼ����</td>"); 
          	shtml.append("<td>��ϸ����</td>");          	           
          shtml.append("</tr>");
	       // Connection conn = null;                 //����Connection����
	        PreparedStatement pstmt = null;         //����PreparedStatement����
	        ResultSet rs = null;                    //����ResultSet����
	        try {
	            conn =ConnectionManager.getConnection();     //��ȡ���ݿ�����
	            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
	        	ConnectionManager.closeQuietly(rs);                  //�رս����
	        	ConnectionManager.closeQuietly(pstmt);               //�ر�PreparedStatement
	            //DBUtils.close(conn);                //�ر�����
	        }
        shtml.append("</table>");
    	return shtml;
    }
    /**
     * ȡ��ҵ�����е��α��Html
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
          shtml.append("<td>��������</td>");
          	shtml.append("<td>��׼����</td>");
          	shtml.append("<td>��׼����</td>");            
          shtml.append("</tr>");
	        Connection conn = null;                 //����Connection����
	        PreparedStatement pstmt = null;         //����PreparedStatement����
	        ResultSet rs = null;                    //����ResultSet����
	        try {
	            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
	            DBUtils.close(rs);                  //�رս����
	            DBUtils.close(pstmt);               //�ر�PreparedStatement
	            //DBUtils.close(conn);                //�ر�����
	        }
        shtml.append("</table>");
    	return shtml;
    }
    /**
     * ȡ��ҵ�񵵴����л�����׼���Html
     * @param id
     * @return
     */
    public StringBuffer getPolicyStandardDeptsHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String stemp = "";
    	String sql = "select checkmoney,checkdesc,accdesc,fullname " +
        "from doper_t_standarddept a,sys_t_organization b " +
        "where a.organization_id = b.organization_id and standard_id = '"+id+"' order by b.organization_id";   //����SQL���
    	//log.debug(sql);
    	shtml.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
        shtml.append("<tr align='center' class='colField'>");
          shtml.append("<td>��������</td>");
          	shtml.append("<td>��׼����</th>");
          	shtml.append("<td>��׼����</td>"); 
          	shtml.append("<td>���㹫ʽ����</td>"); 
          shtml.append("</tr>");
	        Connection conn = null;                 //����Connection����
	        PreparedStatement pstmt = null;         //����PreparedStatement����
	        ResultSet rs = null;                    //����ResultSet����
	        try {
	            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
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
	            DBUtils.close(rs);                  //�رս����
	            DBUtils.close(pstmt);               //�ر�PreparedStatement
	            //DBUtils.close(conn);                //�ر�����
	        }
        shtml.append("</table>");
    	return shtml;
    }
}
