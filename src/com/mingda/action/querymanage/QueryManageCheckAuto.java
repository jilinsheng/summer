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
	 * 获取业务自动筛选家庭或者成员
	 * @param hashmap
	 * @return
	 */
	public StringBuffer GetAutoCheckFamilySql(HashMap hashmap) {
		 Connection conn = null;                 //声明Connection对象
	        PreparedStatement pstmt = null;         //声明PreparedStatement对象
	        ResultSet rs = null;                    //声明ResultSet对象
		StringBuffer shtml= new StringBuffer("");
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//业务审批标识处理
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();
		//业务审批查询处理
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		/**通用的SQL解析参数**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**特殊的处理参数**/
		String tdept = hashmap.get("tdept").toString();
		String torder = hashmap.get("torder").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		/**变量定义**/
		String tempsql = "",physql = "",tempphysql = "";
		//第一级审批机构
		String onedepth = policymanagecheckquery.getPolicyOneFlowFromId(tpolicy);
		String empdepth = tableinfoquery.getempdepth(tempid);
		//
		if("0".equals(onedepth)){
			//政策业务没有设置审批机构
			shtml.append("0");
			return shtml;
		}
		//
		if(!empdepth.equals(onedepth)){
			//不是第一级审批机构
			shtml.append("-1");
			return shtml;
		}
		//业务核算类型
		String acctype = policymanagecheckmanage.getPolicyAccTypeFlag(tpolicy);	
		//逻辑表处理
		String sql = "",allsql = "",xmlth = "",groupby = "";
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",mastername = "",masterid = "",paperid = "",membername = "",deptid = "",deptname = ""; 
		String fmid = "",fmno = "",mname = "",mperid = "",fname = "",fperid = "",fdeptid = "",fdeptname = "";
		//
		boolean brow = false;
		//家庭信息
		tftable = "INFO_T_FAMILY";//家庭表    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//成员信息
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		
		tfield = "FAMILY_ID";//家庭ID
		fmid = tfield;
		id = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "FAMILYNO";//家庭编号
		fmno = tfield;
		familyid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MASTERNAME";//户主姓名
		fname = tfield;
		mastername = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "MASTERPAPERID";//户主证件号码
		fperid = tfield;
		masterid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MEMBERNAME";//姓名
		mname = tfield;
		membername = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		
		tfield = "PAPERID";//证件号码
		mperid = tfield;
		paperid = tmember +"."+tableinfoquery.getfieldlocicfromphysic(tmtable,tfield);
		
		tfield = "ORGANIZATION_ID";//家庭机构ID
		fdeptid = tfield;
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		fdeptname = "FULLNAME";
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//家庭机构
		//
		if("1".equals(acctype)){
			//成员核算
			//查询字段
		    tselect =  id+","+familyid+","+membername+","+paperid;
		}else{
			//家庭核算
			//查询字段
		    tselect =  id+","+familyid+","+mastername+","+masterid;
		}
	    
	    //
	    //
		if("1".equals(acctype)){
			//成员核算
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tfamily + "," + tmember + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + tmember + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
		}else{
			//家庭核算
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tfamily + "," + "SYS_T_ORGANIZATION";
			}else{
				tfrom = tfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
			} 
		}
		
		//查询条件
		if(null == twhere || twhere.equals("")){				
			
		}else{
			twhere += "  and ";
		}
		//带机构查询
		twhere += " INFO_T_FAMILY.ORGANIZATION_ID = SYS_T_ORGANIZATION.ORGANIZATION_ID ";
		//带机构查询
		String temp1 = twhere.substring(0,twhere.length());  
		//组织机构逻辑名称
		String temp2 = tableinfoquery.getfieldlocicfromphysic("INFO_T_FAMILY","ORGANIZATION_ID");    		
		int iend = temp1.indexOf(temp2);//是否存在组织机构					
		if(iend < 0){
			twhere += "and SYS_T_ORGANIZATION.ORGANIZATION_ID LIKE '"+tdept+"%'";
		}	
		//家庭在用状态
		twhere += " and INFO_T_FAMILY.STATUS = '1'";
		//业务查询
		if(null == tpolicy || tpolicy.equals("-1")){//[无]业务选择
			//
			//XML列头
			xmlth = tselect ;
		}else{			
			//XML列头
			xmlth = tselect;
			//符合政策业务标准条件			
			tempsql = "select standard_id,physql from doper_t_standard " +
							"where flag = '1' and policy_id = '"+tpolicy+"' " +
								"order by planmoney desc";   //定义SQL语句
	        //log.debug(sql);
	             
	        try {
	            conn = DBUtils.getConnection();     //获取数据库连接
	            pstmt = conn.prepareStatement(tempsql); //根据sql创建PreparedStatement   
	            rs = pstmt.executeQuery();            
	            while (rs.next()) {
	            	//存在记录
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
	            DBUtils.close(rs);                  //关闭结果集
	            DBUtils.close(pstmt);               //关闭PreparedStatement
	            //DBUtils.close(conn);                //关闭连接
	        }
	        
		}
		//XML列头
		xmlth = xmlth + "," + deptid + ",家庭表.所属";
		//查询字段
	    tselect = tselect + "," + deptid + "," + deptname;
	    //不存在政策业务标准SQL设置
	    if(!brow){
	    	shtml.append("-2");
			return shtml;
	    }
	    //处理结果
	    log.debug("test count 2");
		sql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0","1",torder,conn).toString();
		//
		//开始处理符合政策业务标准语句
		//
		if("1".equals(acctype)){
			//成员核算
			//查询字段
			tselect = fmid+","+fmno+","+mname+","+mperid+","+fdeptid+","+fdeptname;		
		}else{
			//家庭核算
			//查询字段
			tselect = fmid+","+fmno+","+fname+","+fperid+","+fdeptid+","+fdeptname;		
		}
		
        tfrom 	= "(select fm.* from ("+sql+") fm,("+physql+") phy where fm.family_id = phy.family_id)";
        groupby = tselect;
        
		//
		String ssql = "";
		ssql = "select "+tselect+" from ("+tfrom+") tfm group by "+groupby;
		//
		if(torder.equals("0")){//不排序[家庭编号]
    		
    	}else if(torder.equals("1")){//家庭编号排序[家庭编号]
    		ssql = ssql + " order by nlssort(FAMILYNO, 'NLS_SORT=SCHINESE_PINYIN_M') ";
    	}else{//参数排序[locigordermode]
    		int ibeg = torder.indexOf("asc");//排序字段出现位置
    		if(ibeg>=0){
    			String ordername = torder.substring(0,ibeg);
    			String ordertype = torder.substring(ibeg,torder.length());
    			ordername = ordername.trim();
    			int itbeg = ordername.indexOf(".");//第一次出现位置
    			if(itbeg>=0){
	    	    	String stname = ordername.substring(0, itbeg);
	    	    	String sfname = ordername.substring(itbeg+1, ordername.length());
	    	    	String stemptn = tableinfoquery.gettableidfromphysic(stname);
	    	    	if(stemptn.equals("")||stemptn == null){
	    	    		ssql = ssql + " order by "+ torder;
	    	    	}else{
		    	    	String sftype = tableinfoquery.getfieldtypefromphysicname(stname,sfname);		    	    	
		    	    	if(sftype.equals("0")){
		    	    		//字符串类型
		    	    		ssql = ssql + " order by nlssort("+ sfname+ ", 'NLS_SORT=SCHINESE_PINYIN_M') " + ordertype;
		    	    	}else{
		    	    		ssql = ssql + " order by "+ sfname + " "+ ordertype;
		    	    	}
	    	    	}
    	    	}else{
    	    		ssql = ssql + " order by "+ torder;
    	    	}		    			
    		}
    		ibeg = torder.indexOf("desc");//排序字段出现位置
    		if(ibeg>=0){
    			String ordername = torder.substring(0,ibeg);
    			String ordertype = torder.substring(ibeg,torder.length());
    			ordername = ordername.trim();
    			int itbeg = ordername.indexOf(".");//第一次出现位置
    			if(itbeg>=0){
	    	    	String stname = ordername.substring(0, itbeg);
	    	    	String sfname = ordername.substring(itbeg+1, ordername.length());
	    	    	String stemptn = tableinfoquery.gettableidfromphysic(stname);
	    	    	if(stemptn.equals("")||stemptn == null){
	    	    		ssql = ssql + " order by "+ torder;
	    	    	}else{
		    	    	String sftype = tableinfoquery.getfieldtypefromphysicname(stname,sfname);		    	    	
		    	    	if(sftype.equals("0")){
		    	    		//字符串类型
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
		//总记录数
		String countnum = tableinfoquery.getresultcountfromsql(ssql);
		//分页
		ssql = "select * from (select mytab.*, rownum row_num from ("+ssql+") mytab) where row_num between "+tbegpage+" and  "+tendpage;
	    //
		//
		shtml.append(getResultForXml(xmlth,ssql,countnum));
	    
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
	/**
     * 由列名称
     * 查询物理SQL语句
     * SQL记录数
     * 生成XML
     * @param xmlth
     * @param sql
     * @param sqlresultcount
     * @return
     */
    public String getResultForXml(String xmlth,String sql,String sqlresultcount) {
    	String srep = "-1";//无查询结果或者错误    	
	    String columname = xmlth;
	    
	    //log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        
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
        //表格记录总数
        Element eCount=data.addElement("count");
        Element eCountChild =eCount.addElement("num");
        eCountChild.setText(sqlresultcount);              
        
        //
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //表格
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
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
        
        return srep;
    }
}
