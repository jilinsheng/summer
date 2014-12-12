package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.TableInfoPhySQL;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.myjdbc.ConnectionManager;

public class QueryManageChild {
	static Logger log = Logger.getLogger(QueryManageChild.class);
	/**
	 * 获取分类施保标准SQL
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public StringBuffer GetSqlPolicyChild(HashMap hashmap) {
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuffer shtml= new StringBuffer("");
		//表查询语句处理类
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
		/**通用的SQL解析参数**/
		String tmode = hashmap.get("tmode").toString();
		String tselect = hashmap.get("tselect").toString();
		String tfrom = hashmap.get("tfrom").toString();
		String twhere = hashmap.get("twhere").toString();
		String tbegpage = hashmap.get("tbegpage").toString();
		String tendpage = hashmap.get("tendpage").toString();
		/**特殊的处理参数**/
		String ttype = hashmap.get("ttype").toString();
		/**变量定义**/
		//
		//成员信息
		String tfname = "INFO_T_FAMILY";    		
		String tftable = tableinfoquery.gettablelocicfromphysic(tfname);
		String tmname = "INFO_T_MEMBER";    		
		String tmtable = tableinfoquery.gettablelocicfromphysic(tmname);
		//
		//家庭或者成员设置
		if(ttype.equals("family")){
			//查询字段
			tselect = "INFO_T_FAMILY.FAMILY_ID";	
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tftable;
			}else{
				tfrom = tfrom + "," + tftable;
			} 
		}else{
			//查询字段
			tselect = "INFO_T_FAMILY.FAMILY_ID,INFO_T_MEMBER.MEMBER_ID";
			//查询表
			if(null == tfrom || tfrom.equals("")){
				tfrom = tmtable + "," + tftable;
			}else{
				tfrom = tfrom + "," + tmtable + "," + tftable;
			} 
		}
		//查询条件
		if(null == twhere || twhere.equals("")){
			//家庭在用状态
			twhere = " INFO_T_FAMILY.STATUS = '1'";
		}else{
			//家庭在用状态
			twhere += " and INFO_T_FAMILY.STATUS = '1'";
		}		
		
		
		//
		 log.debug("test count 10");
		shtml.append(tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"0","0",conn));
		//log.debug("结果:"+shtml);
		//
		return shtml;
	}
}
