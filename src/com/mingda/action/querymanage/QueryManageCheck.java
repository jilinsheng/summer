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
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

public class QueryManageCheck {
	static Logger log = Logger.getLogger(QueryManageCheck.class);
	//��������
	ConstantDefine constantdefine = new ConstantDefine();
	
	/**
	 * ��ͥ������ѯ
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StringBuffer GetExeCheckFamilySql(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//���ѯ��䴦����
		TableInfoPhySQL tableinfophysql = new TableInfoPhySQL();
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		
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
		String tautotabid = hashmap.get("tautotabid").toString();
		String tchecktabid = hashmap.get("tchecktabid").toString();
		String tchoiceflag = hashmap.get("tchoiceflag").toString();
		/**��������**/		
		//�߼�����
		String sql = "",allsql = "",xmlth = "",sumfield = "",groupby = "";
		String empth = "",stemp = "";
		String tftable,tmtable,tfield,tfamily,tmember;
		String id = "",familyid = "",masterid = "",mastername = "",population = "",deptid = "",deptname = ""; 
		//��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";//��ͥ��    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//��Ա��Ϣ
		tmtable = "INFO_T_MEMBER";    		
		tmember = tableinfoquery.gettablelocicfromphysic(tmtable);
		
		tfield = "FAMILY_ID";//��ͥID
		id = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "FAMILYNO";//��ͥ���
		familyid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		
		tfield = "MASTERNAME";//��������
		mastername = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "MASTERPAPERID";//����֤������
		masterid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		tfield = "POPULATION";//��ͥ�˿�
		population = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);    		
		
		tfield = "ORGANIZATION_ID";//��ͥ����ID
		deptid = tfamily +"."+tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		
		deptname = "SYS_T_ORGANIZATION.FULLNAME";//��ͥ����
		//
		//��ѯ�ֶ�
		String tempselect = id+","+familyid+","+mastername;
		//��ͥ��̬�ֶ�
		String [] selectname = tselect.split(",");
		String tempname = "";
    	int ilength = selectname.length;
	    for (int h=0;h<ilength;h++){
	    	tempname = selectname[h];	    	
	    	//
	    	int iend = tempselect.indexOf(tempname);//�Ƿ����					
			if(iend < 0){
				tempselect = tempselect + "," + tempname;				
			}		    	
	    }
	    //
	    //��ѯ�ֶ�
	    tselect = tempselect;
	    //�����ֶ�
	    groupby = tempselect;
	    //
	    //
		//��ѯ��
		if(null == tfrom || tfrom.equals("")){
			tfrom = tfamily + "," + "SYS_T_ORGANIZATION";
		}else{
			tfrom = tfrom + "," + tfamily + "," + "SYS_T_ORGANIZATION";
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
			xmlth = GetPolciyCheckXnlTh(tselect).toString();
			//��ѯ�ֶ�
			tselect = GetPolciyCheckSelect(tselect,tpolicy,tempid).toString();
		    //
			//�����ֶ�
		    groupby = GetPolciyCheckGroupby(groupby,tpolicy,tempid).toString();
			//��ѯ��
			tfrom = GetPolciyCheckFrom(tfrom).toString();
			//����
			//				
			HashMap hashmapwhere = new HashMap();
			hashmapwhere.put("twhere", twhere);
			hashmapwhere.put("tempid", tempid);
			hashmapwhere.put("tpolicy", tpolicy);
			hashmapwhere.put("tautotabid", tautotabid);
			hashmapwhere.put("tchecktabid", tchecktabid);
			//
			twhere = GetPolciyCheckWhere(hashmapwhere).toString();			
		}
		//XML��ͷ
		xmlth = xmlth + "," + deptid + ",��ͥ��.����";
		//��ѯ�ֶ�
	    tselect = tselect + "," + deptid + "," + deptname;
	    //�����ֶ�
	    groupby = groupby + "," + deptid + "," + deptname;
	    //
	    //������
	    Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
			log.debug("test count 5");
		sql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,tbegpage,tendpage,groupby,torder,conn).toString();
		allsql = tableinfophysql.getphysql(tselect, tfrom, twhere,tmode,"0","0",groupby,torder,conn).toString();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}finally{
			try {
				if(null!=conn)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sumfield = tableinfoquery.getsumfieldfromsql(allsql,tpolicy);		
		//
		//
		//��ѯȫ��(����SQL���)
		if(tchoiceflag.equals("all")){
			shtml.append(allsql);
		}else if(tchoiceflag.equals("export")){
			shtml.append(xmlth+";"+allsql);			
		}else{
			shtml.append(getCheckResultForXml(xmlth,sql,sumfield,tpolicy,tempid));
		}		
		//
		//log.debug("sql:"+sql);
		//log.debug("allsql:"+allsql);
		//log.debug("���:"+shtml);
		//
		return shtml;
	}
	
	
	
	
	/**
	 * ȡ��ҵ��������ѯXML��ͷ
	 * @param tselect
	 * @return
	 */
	public StringBuffer GetPolciyCheckXnlTh(String tselect) {
		StringBuffer shtml= new StringBuffer("");
		//
		//XML��ͷ
		String xmlth = tselect + "," + "������.�����˿�,������.��������,������.���ھ�����,������.�ⷢ������,������.������";
		shtml.append(xmlth);
		return shtml;
	}
	/**
	 * ȡ��ҵ��������ѯ�ֶ�
	 * @param tselect
	 * @param tpolicy
	 * @param tempid
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer GetPolciyCheckSelect(String tselect,String tpolicy,String tempid) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",empth = "";
		//
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//��ӱ����˿ڡ��������롢���ھ������ⷢ������(�ֳ��С�ũ��)
		
		String sptype = policymanagecheckquery.getPolicyObjTypeFromId(tpolicy);
    	if(sptype.equals("0")){//���б���
    		if(tselect.equals("")||tselect==null){
    			tselect = "INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    			tselect += ",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}else{
    			tselect = tselect +","+"INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    			tselect = tselect +",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}	
    	}else if(sptype.equals("1")){//ũ�屣��
    		if(tselect.equals("")||tselect==null){
    			tselect = "INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    			tselect += ",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}else{
    			tselect = tselect +","+"INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    			tselect = tselect +",SUM(BIZ_T_OPTCHECK.RECHECKMONEY) AS RECHECKMONEY,SUM(BIZ_T_OPTCHECK.COUNTMONEY) AS COUNTMONEY";
    		}	
    	}
    	//
    	
    	//    	
    	//�����������������ʱ��
    	empth = tableinfoquery.getempdepth(tempid);
    	//
    	if(empth.equals(constantdefine.POLICYQUERYCODE_5)){//����
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG1";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG2";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG3";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG4";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG5";
    	}
    	tselect = tselect +","+stemp;
    	//
    	shtml.append(tselect);
		return shtml;
	}
	/**
	 * ��ȡ�����ѯ���
	 * @param tgroupby
	 * @param tpolicy
	 * @param tempid
	 * @return
	 */
	@SuppressWarnings("static-access")
	public StringBuffer GetPolciyCheckGroupby(String tgroupby,String tpolicy,String tempid) {
		StringBuffer shtml= new StringBuffer("");
		String stemp = "",empth = "";
		//
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//ҵ������
		PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//��ӱ����˿ڡ��������롢���ھ������ⷢ������(�ֳ��С�ũ��)
		
		String sptype = policymanagecheckquery.getPolicyObjTypeFromId(tpolicy);
    	if(sptype.equals("0")){//���б���
    		if(tgroupby.equals("")||tgroupby==null){
    			tgroupby = "INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    		}else{
    			tgroupby = tgroupby +","+"INFO_T_FAMILY.ENSURECOUNT,INFO_T_FAMILY.CONSULTINCOME";
    		}	
    	}else if(sptype.equals("1")){//ũ�屣��
    		if(tgroupby.equals("")||tgroupby==null){
    			tgroupby = "INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    		}else{
    			tgroupby = tgroupby +","+"INFO_T_FAMILY.ENSURECOUNT2,INFO_T_FAMILY.CONSULTINCOME2";
    		}	
    	}
    	//
    	
    	//    	
    	//�����������������ʱ��
    	empth = tableinfoquery.getempdepth(tempid);
    	//
    	if(empth.equals(constantdefine.POLICYQUERYCODE_5)){//����
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG1";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG2";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG3";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG4";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		stemp = "BIZ_T_OPTCHECK.CHECKFLAG5";
    	}
    	tgroupby = tgroupby +","+stemp;
    	//
    	shtml.append(tgroupby);
		return shtml;
	}
	/**
	 * ȡ��ҵ��������ѯ��
	 * @param tfrom
	 * @return
	 */
	public StringBuffer GetPolciyCheckFrom(String tfrom) {
		StringBuffer shtml= new StringBuffer("");
		//
		//��ѯ��
		if(null == tfrom || tfrom.equals("")){
			tfrom =  "BIZ_T_OPTCHECK";
		}else{
			tfrom =  "BIZ_T_OPTCHECK," + tfrom;
		} 
		//
    	shtml.append(tfrom);
		return shtml;
	}
	/**
	 * ȡ��ҵ��������ѯ����
	 * @param hashmap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public StringBuffer GetPolciyCheckWhere(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
		String twhere = hashmap.get("twhere").toString();
		String tempid = hashmap.get("tempid").toString();
		String tpolicy = hashmap.get("tpolicy").toString();
		String tautotabid = hashmap.get("tautotabid").toString();
		String tchecktabid = hashmap.get("tchecktabid").toString();
		//				
		String empth = "",stemp1 = "",stemp2 = "";
    	String smoneyflag = "",sresult = "",sresultflag = "",sifover,sautoflag = "";
    	
    	//��������
    	ConstantDefine constantdefine = new ConstantDefine();
    	//
    	empth = tableinfoquery.getempdepth(tempid);
    	//
    	if(empth.equals(constantdefine.POLICYQUERYCODE_5)){//����
    		sresult = "CHECKFLAG1";    		
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		sresult = "CHECKFLAG2";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		sresult = "CHECKFLAG3";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		sresult = "CHECKFLAG4";
    	}else if(empth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		sresult = "CHECKFLAG5";
    	}
    	//
    	sifover = empth;
    	//
    	//��ѯ����
		if(twhere.equals("")||twhere==null){
			twhere = "BIZ_T_OPTCHECK.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID " +
					"and BIZ_T_OPTCHECK.POLICY_ID = '"+tpolicy+"' ";
		}else{
			twhere = "BIZ_T_OPTCHECK.FAMILY_ID = INFO_T_FAMILY.FAMILY_ID " +
					"and BIZ_T_OPTCHECK.POLICY_ID = '"+tpolicy+"' and " + twhere;
		}
		
		//
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_NEWCHECK;//��������
		if(tautotabid.equals(stemp1)){	
			//��������
			twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
			//
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWNOCHECK;//����
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}			
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWRECHECK;//���º˲�
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
		}
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_OLECHECK;//�ڱ�������		
		if(tautotabid.equals(stemp1)){
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWSTOP;//���ڴ���
			if(tchecktabid.equals(stemp2)){
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_REM;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWREM;//������ͣ
			if(tchecktabid.equals(stemp2)){
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_STOP;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWTOP;//����
			if(tchecktabid.equals(stemp2)){
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_TOP;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWBOM;//����
			if(tchecktabid.equals(stemp2)){
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_BOM;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWOLE;//˳��
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_OLE;				
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWREMCHECK;//���º˲�
			if(tchecktabid.equals(stemp2)){
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG <> '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;				
			}
			stemp2 = constantdefine.POLICYNEWCHECKCODE_NEWNOMATCH;//�����
			if(tchecktabid.equals(stemp2)){
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_NOMATCH;//�����ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;				
			}
		}
		//
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_MONEY;//�ⷢ������		
		if(tautotabid.equals(stemp1)){
			stemp2 = constantdefine.POLICYNEWRESULTCODE_NEW;//����
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_NEW;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_OK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_OLE;//˳��
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_OLE;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;				
			}	
			stemp2 = constantdefine.POLICYNEWRESULTCODE_TOP;//����
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_TOP;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_OK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_BOM;//����
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_BOM;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG = '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_OK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_MOVE;//����
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_MOVE;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_REM;//�ָ�
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_REM;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
		}
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_NOMONEY;//����������	
		if(tautotabid.equals(stemp1)){
			stemp2 = constantdefine.POLICYNEWRESULTCODE_STOP;//��ͣ
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_STOP;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_DEL;//��ֹ
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_ISMATCH;//���ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_NOTOK;
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_NOMATCH;//�����
			if(tchecktabid.equals(stemp2)){
				sautoflag = constantdefine.POLICYAOUTCHECKCODE_NOMATCH;//�����ϱ�׼
				twhere = "BIZ_T_OPTCHECK.MONEYAOUT = '"+sautoflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_NEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" <> '"+sresultflag+"' and " + twhere;
			}
			stemp2 = constantdefine.POLICYNEWRESULTCODE_MATCHNORES;//δ����
			if(tchecktabid.equals(stemp2)){	
				//��������
				twhere = "BIZ_T_OPTCHECK.IFOVER = '"+sifover+"' and " + twhere;
				smoneyflag = constantdefine.POLICYNEWCHECKCODE_OLE;
				twhere = "BIZ_T_OPTCHECK.MONEYFLAG <> '"+smoneyflag+"' and " + twhere;
				sresultflag = constantdefine.POLICYCHECKCODE_NEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
			
		}
		stemp1 = constantdefine.POLICYAOUTCHECKCODE_REMCHECK;//���º˲�����	
		if(tautotabid.equals(stemp1)){			
			stemp2 = constantdefine.POLICYNEWRESULTCODE_REMCHECK;//���º˲�
			if(tchecktabid.equals(stemp2)){					
				sresultflag = constantdefine.POLICYCHECKCODE_RENEW;				
				twhere = "BIZ_T_OPTCHECK."+sresult+" = '"+sresultflag+"' and " + twhere;
			}
		}
		//
		shtml.append(twhere);
		//
		return shtml;
	}
	
	/**
     * ����XML
     * ��������
     * ��ѯ����SQL���
     * SQL��¼��
     * ����XML
     * XML��һ��:������(0�ɲ���1���ɲ���)
     * XML�ڶ���:��ͥID(�������ݿ��ѯ)
     * @param tselect
     * @param sql
     * @param sumfield
     * @param spid
     * @param empid
     * @return
     */
    @SuppressWarnings("unused")
	public String getCheckResultForXml(String txmlth,String sql,String sumfield,String spid,String empid) {
    	String srep = "-1";//�޲�ѯ������ߴ���
    	String sqlresultcount = "0",sumpopcount ="0",summoney = "0",sumolemoney = "0",sumnewmoney = "0";
    		
		//��������������
		PolicyManageCheckManage policymanagecheckmanage = new PolicyManageCheckManage();		
		
		//
	    String columname = txmlth;
	    
	    //��Ӽ�ͥ�����������˿ڡ��������롢���ھ������ⷢ������(�ֳ��С�ũ��)
	    String[] asumfield = sumfield.split(","); 
	    if(asumfield.length>1){
	    	sqlresultcount = asumfield[0];
	 	    sumpopcount = asumfield[1];
	 		summoney = asumfield[2];
	 		sumolemoney = asumfield[3];
	 		sumnewmoney = asumfield[4];	 
	    }
	    //
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
        //������˿�����
        Element eSumPopcount=data.addElement("sumpopcount");
        Element eSumeSumPopcountChild =eSumPopcount.addElement("count");
        eSumeSumPopcountChild.setText(sumpopcount); 
        //�������������
        Element eSumMoney=data.addElement("summoney");
        Element eSumMoneyChild =eSumMoney.addElement("money");
        eSumMoneyChild.setText(summoney);         
        //
        //������ھ��������
        Element eSumOleMoney=data.addElement("sumolemoney");
        Element eSumOleMoneyChild =eSumOleMoney.addElement("money");
        eSumOleMoneyChild.setText(sumolemoney);
        //����ⷢ���������
        Element eSumNewMoney=data.addElement("sumnewmoney");
        Element eSumNewMoneyChild =eSumNewMoney.addElement("money");
        eSumNewMoneyChild.setText(sumnewmoney); 
        //
        
        //
        //log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //
            //�����
            Element eHead=data.addElement("ehead");           
            
            //
            for (int i = 0; i < columnCount; i++) {              
            	//��ѯ�ֶ���
            	//
          	  	Element eFHeadChild =eHead.addElement("cell");
                eFHeadChild.setText(columu_name[i]);
                eFHeadChild.attributeValue("id",columu_name[i]);             
            }
            //���ʵ��
            Element datas=data.addElement("list");
            String tfmid = "",sflag = "";
            while (rs.next()) {             
            	Element entity=datas.addElement("entity");
              	tfmid = rs.getString(1);// ��ͥID
              	//
	          	//��ͥ��Ϣ
	          	//XML��һ��:��ͥID
              	for (int i = 0; i < columnCount; i++) {
              		//
              		if(i==columnCount-3){
            			//�������ת��            		
            			Element col=entity.addElement("col");
    	            	String tname = rs.getString(i+1);                
    	            	if(tname==null || tname.length()<=0){
    	            		col.setText("δ����");                  
    	            	}else{
    	            		//
    	            		tname = policymanagecheckmanage.repalcePolicyCheckFlag(tname);
    	            		col.setText(tname);                    
    	            	}  
            		}else{
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
	          	//���Գ������������ʶ(���������ڱ��������ϼ�δ����������º˲���ɳ��������������)            		
	      		Element col=entity.addElement("col");
	      		String tflag = policymanagecheckmanage.getPolicyIsRmCheckFlag(empid, spid, tfmid);
	      		col.setText(tflag); 
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
