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
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		//
		/**通用的SQL解析参数**/
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**特殊的处理参数**/
		String tdeptid = hashmap.get("tdeptid").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpid = hashmap.get("tpid").toString();
		/**用户的处理参数**/
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
		xmlth = "结算表.业务名称,结算表.年份,结算表.月份,结算表.结算标识,结算表.结算时间";
		xmlth += ",结算表.结算人,结算表.结算完成标识,结算表.结算完成日期,结算表.建立人,结算表.建立日期";
		//
		count = tableinfophysql.getcountfromsql(sql);
		//
		sql = "select * from (select mytab.*, rownum row_num from ("+sql+") mytab) where row_num between "+tbegpage+" and  "+tendpage;
		//
		shtml.append(getPolicyAccXmlsForSql(xmlth,sql,count));
		return shtml;
	}
	/**
	 * 生成XML文件
	 * @param txmlth
	 * @param sql
	 * @param sqlresultcount
	 * @return
	 */
	public String getPolicyAccXmlsForSql(String txmlth,String sql,String sqlresultcount) {
    	String srep = "";
    	// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//政策审批处理类
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
        //表格记录总数
        Element eCount=data.addElement("count");
        Element eCountChild =eCount.addElement("num");
        eCountChild.setText(sqlresultcount); 
        //
        //表格列
        Element eHead=data.addElement("ehead");
        for (int i = 0; i < columnCount; i++) {              
        	//查询字段列名
      	  	Element eFHeadChild =eHead.addElement("cell");
            eFHeadChild.setText(columu_name[i]);
            eFHeadChild.attributeValue("id",columu_name[i]);             
        }
        //表格实体
        Element datas=data.addElement("list");
        //log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery(); 
            while (rs.next()) {             
            	Element entity=datas.addElement("entity");
            	//
            	boolean bisacc = false;//结算标识
            	for (int i = 0; i < columnCount; i++) {
	            	Element col=entity.addElement("col");
	            	String tname = rs.getString(i+1);
	            	if(i==3){
	            		//结算标识
	            		if(null == tname){
		            		tname = "未结算";                
		            	}else{
		            		if("1".equals(tname)){
		            			tname = "已结算";
		            			bisacc = true;
		            		}else{
		            			tname = "未结算";
		            		}
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==4){
	            		//结算日期
	            		if(null == tname){
		            		tname = "无";                
		            	}else{
		            		tname = policymanagecheckquery.getformattime(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==5){
	            		//结算人
	            		if(null == tname){
		            		tname = "无";                
		            	}else{
		            		tname = tableinfoquery.getempname(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==6){
	            		//结算完成标识
	            		if(null == tname){
		            		tname = "未结算";                
		            	}else{
		            		if(bisacc){
		            			if("1".equals(tname)){
			            			tname = "处理完成";
			            		}else{
			            			tname = "正在处理";
			            		}
		            		}else{
		            			tname = "未结算";
		            		}	            	
		            	}	            			
	            		col.setText(tname);
	            	}else if(i==7){
	            		//结算完成日期
	            		if(null == tname){
		            		tname = "无";                
		            	}else{
		            		tname = policymanagecheckquery.getformattime(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==8){
	            		//建立人
	            		if(null == tname){
		            		tname = "无";                
		            	}else{
		            		tname = tableinfoquery.getempname(tname);
		            	}	            		
	            		col.setText(tname);
	            	}else if(i==9){
	            		//建立日期	
	            		if(null == tname){
		            		tname = "无";                
		            	}else{
		            		tname = policymanagecheckquery.getformatdt(tname);
		            	}	                    
	            		col.setText(tname);
	            	}else{
	            		if(null == tname){
		            		tname = "无";                
		            	}
	            		col.setText(tname);
	            	}
            	}
            }        
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
           // DBUtils.close(conn);                //关闭连接
        }
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML()+"   :xiuxiuxiuxiux:    "+doc.asXML());
        srep = node.asXML();
        //      
        return srep;
	}
}
