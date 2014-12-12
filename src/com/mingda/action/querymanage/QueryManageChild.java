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
	 * ��ȡ����ʩ����׼SQL
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
		/**��������**/
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
		 log.debug("test count 10");
		shtml.append(tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,"0","0",conn));
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
}
