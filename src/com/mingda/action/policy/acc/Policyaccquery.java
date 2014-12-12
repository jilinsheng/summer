package com.mingda.action.policy.acc;

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
import com.mingda.action.policy.query.PolicyManageCheckQuery;

public class Policyaccquery {
	
	static Logger log = Logger.getLogger(Policyaccquery.class);
	public StringBuffer getPolicyAccXmls(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		String temp = "",sql = "",count = "0",xmlth = "";		
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		//
		/**ͨ�õ�SQL��������**/
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**����Ĵ������**/
		String tdeptid = hashmap.get("tdeptid").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpid = hashmap.get("tpid").toString();
		/**�û��Ĵ������**/
		String tyear = hashmap.get("tyear").toString();
		String tbegmonth = hashmap.get("tbegmonth").toString();
		String tendmonth = hashmap.get("tendmonth").toString();
		String tbegdt = hashmap.get("tbegdt").toString();
		String tenddt = hashmap.get("tenddt").toString();
		//
		sql = "select b.name,a.accyear,a.accmonth," +
				"a.accflag,a.accdt,a.accoper,a.accoverflag,a.accoverdt," +
				"a.begoper,a.begdt " +
				"from biz_t_optacc a,doper_t_policy b " +
				"where a.policy_id = b.policy_id " +
				"and a.organization_id like '"+tdeptid+"%'" +
				"and b.policy_id = '"+tpid+"' "; 
				
		//
		if(!"".equals(tyear)){
			temp = " and a.accyear = "+tyear+"";
			sql += temp;
		}
		if(!"".equals(tbegmonth)){
			temp = " and a.accmonth >= "+tbegmonth+"";
			sql += temp;
		}
		if(!"".equals(tendmonth)){
			temp = " and a.accmonth <= "+tendmonth+"";
			sql += temp;
		}
		if(!"".equals(tbegdt)){
			temp = " and to_char(a.accdt,'yyyy-mm-dd') >= '"+tbegdt+"'";
			sql += temp;
		}
		if(!"".equals(tenddt)){
			temp = " and to_char(a.accdt,'yyyy-mm-dd') <= '"+tenddt+"'";
			sql += temp;
		}
		//log.debug(sql);
		//
		xmlth = "�����.ҵ������,�����.���,�����.�·�,�����.�����ʶ,�����.����ʱ��";
		xmlth += ",�����.������,�����.������ɱ�ʶ,�����.�����������,�����.������,�����.��������";
		//
		count = tableinfophysql.getcountfromsql(sql);
		//
		sql = "select * from (select mytab.*, rownum row_num from ("+sql+") mytab) where row_num between "+tbegpage+" and  "+tendpage;
		//
		shtml.append(getPolicyAccXmlsForSql(xmlth,sql,count));
		return shtml;
	}
	/**
	 * ����XML�ļ�
	 * @param txmlth
	 * @param sql
	 * @param sqlresultcount
	 * @return
	 */
	public String getPolicyAccXmlsForSql(String txmlth,String sql,String sqlresultcount) {
    	String srep = "";
    	// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//��������������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
    	String[] columu_name = null;      
        int columnCount =0;       
        //
        columu_name = txmlth.split(",");
        columnCount = columu_name.length;
    	//
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
        //�����
        Element eHead=data.addElement("ehead");
        for (int i = 0; i < columnCount; i++) {              
        	//��ѯ�ֶ�����
      	  	Element eFHeadChild =eHead.addElement("cell");
            eFHeadChild.setText(columu_name[i]);
            eFHeadChild.attributeValue("id",columu_name[i]);             
        }
        //���ʵ��
        Element datas=data.addElement("list");
        //log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery(); 
            while (rs.next()) {             
            	Element entity=datas.addElement("entity");
            	//
            	boolean bisacc = false;//�����ʶ
            	for (int i = 0; i < columnCount; i++) {
	            	Element col=entity.addElement("col");
	            	String tname = rs.getString(i+1);
	            	if(i==3){
	            		//�����ʶ
	            		if(null == tname){
		            		tname = "δ����";                
		            	}else{
		            		if("1".equals(tname)){
		            			tname = "�ѽ���";
		            			bisacc = true;
		            		}else{
		            			tname = "δ����";
		            		}
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==4){
	            		//��������
	            		if(null == tname){
		            		tname = "��";                
		            	}else{
		            		tname = policymanagecheckquery.getformattime(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==5){
	            		//������
	            		if(null == tname){
		            		tname = "��";                
		            	}else{
		            		tname = tableinfoquery.getempname(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==6){
	            		//������ɱ�ʶ
	            		if(null == tname){
		            		tname = "δ����";                
		            	}else{
		            		if(bisacc){
		            			if("1".equals(tname)){
			            			tname = "�������";
			            		}else{
			            			tname = "���ڴ���";
			            		}
		            		}else{
		            			tname = "δ����";
		            		}	            	
		            	}	            			
	            		col.setText(tname);
	            	}else if(i==7){
	            		//�����������
	            		if(null == tname){
		            		tname = "��";                
		            	}else{
		            		tname = policymanagecheckquery.getformattime(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==8){
	            		//������
	            		if(null == tname){
		            		tname = "��";                
		            	}else{
		            		tname = tableinfoquery.getempname(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==9){
	            		//��������	
	            		if(null == tname){
		            		tname = "��";                
		            	}else{
		            		tname = policymanagecheckquery.getformatdt(tname);
		            	}	                    
	            		col.setText(tname);
	            	}else{
	            		if(null == tname){
		            		tname = "��";                
		            	}
	            		col.setText(tname);
	            	}
            	}
            }        
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
           // DBUtils.close(conn);                //�ر�����
        }
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML()+"   :xiuxiuxiuxiux:    "+doc.asXML());
        srep = node.asXML();
        //      
        return srep;
	}
}
