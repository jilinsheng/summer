package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.manage.PolicyManageCheckManage;
import com.mingda.action.policy.query.PolicyManageCheckQuery;

public class QueryManageCheckAuto {
	static Logger log = Logger.getLogger(QueryManageCheckAuto.class);
	/**
	 * ��ȡҵ���Զ�ɸѡ��ͥ���߳�Ա
	 * @param hashmap
	 * @return
	 */
	public StringBuffer GetAutoCheckFamilySql(HashMap hashmap) {
		 Connection conn = null;                 //����Connection����
	        PreparedStatement pstmt = null;         //����PreparedStatement����
	        ResultSet rs = null;                    //����ResultSet����
		StringBuffer shtml= new StringBuffer("");
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ��������ʶ����
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//ҵ��������ѯ����
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
		String tdept = hashmap.get("tdept").toString();
		String torder = hashmap.get("torder").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		/**��������**/
		String tempsql = "",physql = "",tempphysql = "";
		//��һ����������
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(tpolicy);
		String empdepth = tableinfoquery.getempdepth(tempid);
		//
		if("0".equals(onedepth)){
			//����ҵ��û��������������
			shtml.append("0");
			return shtml;
		}
		//
		if(!empdepth.equals(onedepth)){
			//���ǵ�һ����������
			shtml.append("-1");
			return shtml;
		}
		//ҵ���������
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(tpolicy);	
		//�߼�����
		String sql = "",allsql = "",xmlth = "",groupby = "";
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",mastername = "",masterid = "",paperid = "",membername = "",deptid = "",deptname = ""; 
		String fmid = "",fmno = "",mname = "",mperid = "",fname = "",fperid = "",fdeptid = "",fdeptname = "";
		//
		boolean brow = false;
		//��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";//��ͥ��    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//��Ա��Ϣ
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		
		tfield = "FAMILY_ID";//��ͥID
		fmid = tfield;
		id = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "FAMILYNO";//��ͥ���
		fmno = tfield;
		familyid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MASTERNAME";//��������
		fname = tfield;
		mastername = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "MASTERPAPERID";//����֤������
		fperid = tfield;
		masterid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MEMBERNAME";//����
		mname = tfield;
		membername = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		
		tfield = "PAPERID";//֤������
		mperid = tfield;
		paperid = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		
		tfield = "ORGANIZATION_ID";//��ͥ����ID
		fdeptid = tfield;
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		fdeptname = "FULLNAME";
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//��ͥ����
		//
		if("1".equals(acctype)){
			//��Ա����
			//��ѯ�ֶ�
		    tselect =  id+","+familyid+","+membername+","+paperid;
		}else{
			//��ͥ����
			//��ѯ�ֶ�
		    tselect =  id+","+familyid+","+mastername+","+masterid;
		}
	    
	    //
	    //
		if("1".equals(acctype)){
			//��Ա����
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = tfamily + "," + tmember + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
		}else{
			//��ͥ����
			//��ѯ��
			if(null == tfrom || tfrom.equals("")){
				tfrom = tfamily + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
		}
		
		//��ѯ����
		if(null == twhere || twhere.equals("")){				
			
		}else{
			twhere += "  and ";
		}
		//��������ѯ
		twhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
		//��������ѯ
		String temp1 = twhere.substring(0,twhere.length());  
		//��֯�����߼�����
		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
		int iend = temp1.indexOf(temp2);//�Ƿ������֯����					
		if(iend < 0){
			twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
		}	
		//��ͥ����״̬
		twhere += " and INFO_T_FAMILY.STATUS = '1'";
		//ҵ���ѯ
		if(null == tpolicy || tpolicy.equals("-1")){//[��]ҵ��ѡ��
			//
			//XML��ͷ
			xmlth = tselect ;
		}else{			
			//XML��ͷ
			xmlth = tselect;
			//��������ҵ���׼����			
			tempsql = "select standard_id,physql from doper_t_standard " +
							"where flag = '1' and policy_id = '"+tpolicy+"' " +
								"order by planmoney desc";   //����SQL���
	        //log.debug(sql);
	             
	        try {
	            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	            pstmt = conn.prepareStatement(tempsql); //����sql����PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {
	            	//���ڼ�¼
	            	brow = true;
	            	//
	            	tempphysql = rs.getString("physql");            	
	            	if(null != tempphysql){
	            		if("".equals(physql)){
	            			physql = tempphysql +" and INFO_T_FAMILY.ORGANIZATION_ID LIKE '"+tdept+"%' ";
	                	}else{
	                		physql += " UNION ALL "+tempphysql +" and INFO_T_FAMILY.ORGANIZATION_ID LIKE '"+tdept+"%' ";
	                	}   
	            	}
	            }
	        } catch (SQLException e) {
	            log.debug(e.toString());
	        } finally {
	            DBUtils.close(rs);                  //�رս����
	            DBUtils.close(pstmt);               //�ر�PreparedStatement
	            //DBUtils.close(conn);                //�ر�����
	        }
	        
		}
		//XML��ͷ
		xmlth = xmlth + "," + deptid + ",��ͥ��.����";
		//��ѯ�ֶ�
	    tselect = tselect + "," + deptid + "," + deptname;
	    //����������ҵ���׼SQL����
	    if(!brow){
	    	shtml.append("-2");
			return shtml;
	    }
	    //������
	    log.debug("test count 2");
		sql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1",torder,conn).toString();
		//
		//��ʼ�����������ҵ���׼���
		//
		if("1".equals(acctype)){
			//��Ա����
			//��ѯ�ֶ�
			tselect = fmid+","+fmno+","+mname+","+mperid+","+fdeptid+","+fdeptname;		
		}else{
			//��ͥ����
			//��ѯ�ֶ�
			tselect = fmid+","+fmno+","+fname+","+fperid+","+fdeptid+","+fdeptname;		
		}
		
        tfrom 	= "(select fm.* from ("+sql+") fm,("+physql+") phy where fm.family_id = phy.family_id)";
        groupby = tselect;
        
		//
		String ssql = "";
		ssql = "select "+tselect+" from ("+tfrom+") tfm group by "+groupby;
		//
		if(torder.equals("0")){//������[��ͥ���]
    		
    	}else if(torder.equals("1")){//��ͥ�������[��ͥ���]
    		ssql = ssql + " order by nlssort(FAMILYNO, 'NLS_SORT=SCHINESE_PINYIN_M') ";
    	}else{//��������[locigordermode]
    		int ibeg = torder.indexOf("asc");//�����ֶγ���λ��
    		if(ibeg>=0){
    			String ordername = torder.substring(0,ibeg);
    			String ordertype = torder.substring(ibeg,torder.length());
    			ordername = ordername.trim();
    			int itbeg = ordername.indexOf(".");//��һ�γ���λ��
    			if(itbeg>=0){
	    	    	String stname = ordername.substring(0, itbeg);
	    	    	String sfname = ordername.substring(itbeg+1, ordername.length());
	    	    	String stemptn = tableinfoquery.gettableidfromphysic(stname);
	    	    	if(stemptn.equals("")||stemptn == null){
	    	    		ssql = ssql + " order by "+ torder;
	    	    	}else{
		    	    	String sftype = tableinfoquery.getfieldtypefromphysicname(stname,sfname);		    	    	
		    	    	if(sftype.equals("0")){
		    	    		//�ַ�������
		    	    		ssql = ssql + " order by nlssort("+ sfname+ ", 'NLS_SORT=SCHINESE_PINYIN_M') " + ordertype;
		    	    	}else{
		    	    		ssql = ssql + " order by "+ sfname + " "+ ordertype;
		    	    	}
	    	    	}
    	    	}else{
    	    		ssql = ssql + " order by "+ torder;
    	    	}		    			
    		}
    		ibeg = torder.indexOf("desc");//�����ֶγ���λ��
    		if(ibeg>=0){
    			String ordername = torder.substring(0,ibeg);
    			String ordertype = torder.substring(ibeg,torder.length());
    			ordername = ordername.trim();
    			int itbeg = ordername.indexOf(".");//��һ�γ���λ��
    			if(itbeg>=0){
	    	    	String stname = ordername.substring(0, itbeg);
	    	    	String sfname = ordername.substring(itbeg+1, ordername.length());
	    	    	String stemptn = tableinfoquery.gettableidfromphysic(stname);
	    	    	if(stemptn.equals("")||stemptn == null){
	    	    		ssql = ssql + " order by "+ torder;
	    	    	}else{
		    	    	String sftype = tableinfoquery.getfieldtypefromphysicname(stname,sfname);		    	    	
		    	    	if(sftype.equals("0")){
		    	    		//�ַ�������
		    	    		ssql = ssql + " order by nlssort("+ sfname+ ", 'NLS_SORT=SCHINESE_PINYIN_M') " + ordertype;
		    	    	}else{
		    	    		ssql = ssql + " order by "+ sfname + " "+ ordertype;
		    	    	}		    			
	    	    	}
    			}else{
    	    		ssql = ssql + " order by "+ torder;
    	    	}
    		}
    	}
		//�ܼ�¼��
		String countnum = tableinfoquery.getresultcountfromsql(ssql);
		//��ҳ
		ssql = "select * from (select mytab.*, rownum row_num from ("+ssql+") mytab) where row_num between "+tbegpage+" and  "+tendpage;
	    //
		//
		shtml.append(getResultForXml(xmlth,ssql,countnum));
	    
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
	/**
     * ��������
     * ��ѯ����SQL���
     * SQL��¼��
     * ����XML
     * @param xmlth
     * @param sql
     * @param sqlresultcount
     * @return
     */
    public String getResultForXml(String xmlth,String sql,String sqlresultcount) {
    	String srep = "-1";//�޲�ѯ������ߴ���    	
	    String columname = xmlth;
	    
	    //log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        
        String[] columu_name = null;      
        int columnCount =0;       
        //
        columu_name = columname.split(",");
        columnCount = columu_name.length;
        
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //
        //����¼����
        Element eCount=data.addElement("count");
        Element eCountChild =eCount.addElement("num");
        eCountChild.setText(sqlresultcount);              
        
        //
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //���
            Element eHead=data.addElement("ehead");
            //
            for (int i = 0; i < columnCount; i++) {
            	//
            	Element eHeadChild =eHead.addElement("cell");
            	eHeadChild.setText(columu_name[i]);
            	eHeadChild.attributeValue("id",columu_name[i]);
            }
            Element datas=data.addElement("list");
            while (rs.next()) {             
            	Element entity=datas.addElement("entity");
            	for (int i = 0; i < columnCount; i++) {
            		Element col=entity.addElement("col");
            		String tname = rs.getString(i+1);                
            		if(tname==null || tname.length()<=0){
            			col.setText("0");                  
            		}else{
            			col.setText(tname);                    
            		} 
            	}
            }
            //
            Node node=  root.selectSingleNode("/root/data");
            //log.debug(node.asXML()+"   :xiuxiuxiuxiux:    "+doc.asXML());
            srep = node.asXML();
            //
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
        
        return srep;
    }
}
