package com.mingda.action.policy.approval2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;
import com.mingda.action.policy.sql2.PolicyPhySQL;
import com.mingda.common.ConstantDefine;
import com.mingda.common.myjdbc.ConnectionManager;

public class Policycheckpage {
	   /**
	 * ת���������Ϊ����˵��
	 * @param checkid
	 * @return
	 */
	public String repalcePolicyCheckFlag(String checkid){
		String srep = "";
		//��������
	    ConstantDefine constantdefine = new ConstantDefine();
	    if(checkid.equals(constantdefine.POLICYCHECKCODE_NEW)){
	    	srep = constantdefine.POLICYCHECK_NEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_OK)){
	    	srep = constantdefine.POLICYCHECK_OK;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_RENEW)){
	    	srep = constantdefine.POLICYCHECK_RENEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_MOVE)){
	    	srep = constantdefine.POLICYCHECK_MOVE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_OLE)){
	    	srep = constantdefine.POLICYCHECK_OLE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_STOP)){
	    	srep = constantdefine.POLICYCHECK_STOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_REM)){
	    	srep = constantdefine.POLICYCHECK_REM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_NOTOK)){
	    	srep = constantdefine.POLICYCHECK_NOTOK;
	    }else if(checkid.equals(constantdefine.POLICYCHECKCODE_NOT)){
	    	srep = constantdefine.POLICYCHECK_NOT;
	    }
		return srep;
	}
	/**
	 * �滻����״̬
	 * @param checkid
	 * @return
	 */
	public String repalcePolicyMoneyFlag(String checkid){
		String srep = "";
		//��������
	    ConstantDefine constantdefine = new ConstantDefine();
	    if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_NEW)){
	    	srep = constantdefine.POLICYCHECKMONEY_NEW;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_OLE)){
	    	srep = constantdefine.POLICYCHECKMONEY_OLE;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_TOP)){
	    	srep = constantdefine.POLICYCHECKMONEY_TOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_BOM)){
	    	srep = constantdefine.POLICYCHECKMONEY_BOM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_REM)){
	    	srep = constantdefine.POLICYCHECKMONEY_REM;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_STOP)){
	    	srep = constantdefine.POLICYCHECKMONEY_STOP;
	    }else if(checkid.equals(constantdefine.POLICYCHECKMONEYCODE_NOT)){
	    	srep = constantdefine.POLICYCHECKMONEY_NOT;
	    }
		return srep;
	}
	/**
	 * ��ȡҵ����������ѡ���
	 * @param sname
	 * @param pid
	 * @return
	 */
	public StringBuffer getCheckPolicyOrderSelect(String sname,String pid){
		StringBuffer shtml= new StringBuffer("");
		String tftable,tfield,tfamily;
        String id = "",familyid = "",mastername = "",saltype = "",deptid = ""; 
        
        String stemp = "";
        //���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
		//
        
        
		//��ӱ����˿ڡ��������롢���ھ������ⷢ������(�ֳ��С�ũ��)
		//
    	//
		shtml.append("<select id=\""+sname+"\" style = \"font-size:12px\">");
		//��ͥ��Ϣ
		tftable = "INFO_T_FAMILY";//��ͥ��    		
		tfamily = tableinfoquery.gettablelocicfromphysic(tftable);
		//
		//
		tfield = "FAMILYNO";//��ͥ���
		familyid = tableinfoquery.getfieldlocicfromphysic(tftable,tfield); 
		//
		stemp = "INFO_T_FAMILY.FAMILYNO";
		shtml.append("<option value=\""+stemp+"\">"+familyid+"</option>");
		
		tfield = "MASTERNAME";//��������
		mastername = tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		//
		stemp = "INFO_T_FAMILY.MASTERNAME";
		shtml.append("<option value=\""+stemp+"\">"+mastername+"</option>");
		//	
		tfield = "SALTYPE";//��������
		saltype = tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		stemp = "INFO_T_FAMILY.SALTYPE";
		shtml.append("<option value=\""+stemp+"\">��������</option>");
		//
		tfield = "ORGANIZATION_ID";//��ͥ����
		deptid = tableinfoquery.getfieldlocicfromphysic(tftable,tfield);
		//
		stemp = "INFO_T_FAMILY.ORGANIZATION_ID";
		shtml.append("<option value=\""+stemp+"\">"+deptid+"</option>");		
    	//
		stemp = "INFO_T_FAMILY.ENSURECOUNT";
		shtml.append("<option value=\""+stemp+"\">�����˿�</option>");
		stemp = "INFO_T_FAMILY.CONSULTINCOME";
		shtml.append("<option value=\""+stemp+"\">��������</option>");
		//
		//����ɻ����ֶ�
		stemp = "RECHECKMONEY";
		shtml.append("<option value=\""+stemp+"\">���ھ�����</option>");
		stemp = "COUNTMONEY";
		shtml.append("<option value=\""+stemp+"\">�ⷢ������</option>");
		//
		shtml.append("</select>");
		return shtml;
	}
	/**
	 * ����XML�ļ�
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getCheckResultForXml(HashMap hashmap) {
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuffer shtml= new StringBuffer("");
		PolicyPhySQL policyphysql = new PolicyPhySQL();
		Policychecksql policychecksql = new Policychecksql();
		PolicyQuery policyquery = new PolicyQuery();		
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        String jpid = hashmap.get("jpid").toString();
        //
        String jtabid = hashmap.get("jtabid").toString();
        //=================SQL��ȡ��ʽ================
        String jsqlmode = hashmap.get("jsqlmode").toString();
        //=================SQL��ȡ��ʽ================    
        //  
        //�ֵ�ֵ
    	Document dictionary = (Document) hashmap.get("dictionary");
    	//
        //
        //����Ȩ�ޱ�ʶ
        HashMap hashmappower = new HashMap();        
        hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid); 
        String onecheck = hashmappower.get("onecheck").toString();
        String endcheck = hashmappower.get("endcheck").toString();
        String usercheck = hashmappower.get("usercheck").toString();
        String morecheck = hashmappower.get("morecheck").toString();
        String usermorecheck = hashmappower.get("usermorecheck").toString();
        String report = hashmappower.get("report").toString();
		//�������α�ʶ
        HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
        String optid = hashmapaccflag.get("optid").toString();
        String accflag = hashmapaccflag.get("accflag").toString();
        String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
        String accmoney = hashmapaccflag.get("accmoney").toString();
        //��Ҫ����������ʶ
		String jaccmode = policyquery.getPolicyUserAccFlag(jpid);
		//����������Ϣ
        String accdesc = policyaccquery.getNowPolicyAcc(optid);
        //==============================================У��=========================================
        if("0".equals(usercheck)){							//������Ȩ��
        	shtml.append("���û��޸�ҵ������Ȩ��,����������Ϣ!");
        	return shtml;
        }
        //==============================================================//
        if("-1".equals(accflag)){							//�޽�������        	
        	shtml.append("��ҵ��δִ�д���,�ȴ�����������Ϣ!");
        	return shtml;
        }else if("0".equals(accflag)){						//�½�������        	
        	
        }else if("1".equals(accflag)){						//���ڽ�������        	
        	shtml.append(accdesc);
        	shtml.append("  ��ʾ:��ҵ������ִ�н��㴦��,��ȴ�!");
        	return shtml;
        }else if("2".equals(accflag)){						//�����������        	
        	
        }
        //==============================================У��=========================================
        HashMap hashmapinfo = new HashMap();
        hashmapinfo = policyphysql.getCheckPolicyPhySql(hashmap,conn);
        String sql = hashmapinfo.get("sql").toString();
        String countsql = hashmapinfo.get("countsql").toString();
        String xmlth = hashmapinfo.get("xmlth").toString();
		//============================================================xml==============================
        //
		Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //=========================��Ҫ����������ʶ=====================
        Element eAccmode=data.addElement("accmode");
        //��ҳSQL���
        Element eAccmodeChild =eAccmode.addElement("accmode");
        eAccmodeChild.setText(jaccmode);
        //=========================��Ҫ����������ʶ=====================
        //==============SQL��Ϣ==========================================
        Element eSql=data.addElement("sql");
        //��ҳSQL���
        Element eSqlChild =eSql.addElement("sql");
        eSqlChild.setText(sql);
        //SQL���
        eSqlChild =eSql.addElement("countsql");
        eSqlChild.setText(countsql);
        //SQL���������
        eSqlChild =eSql.addElement("xmlth");
        eSqlChild.setText(xmlth);
        //==============SQL��Ϣ==========================================
        //=================================================================��ѯ����BEG 
        String sqlcount = hashmapinfo.get("sqlcount").toString();
        String sumpopcount = hashmapinfo.get("sumpopcount").toString();
        //��ʽ����������
        String summoney = hashmapinfo.get("summoney").toString();	
        String sumolemoney = hashmapinfo.get("sumolemoney").toString();
        String sumnewmoney = hashmapinfo.get("sumnewmoney").toString();
         
        Element eCount=data.addElement("counts");
        //����¼����
        Element eCountChild =eCount.addElement("sqlcount");
        eCountChild.setText(sqlcount);
        //������˿�����
        eCountChild =eCount.addElement("sumpopcount");
        eCountChild.setText(sumpopcount); 
        //�������������
        eCountChild =eCount.addElement("summoney");
        eCountChild.setText(summoney); 
        //������ھ��������
        eCountChild =eCount.addElement("sumolemoney");
        eCountChild.setText(sumolemoney); 
        //����ⷢ���������
        eCountChild =eCount.addElement("sumnewmoney");
        eCountChild.setText(sumnewmoney); 
        //=================================================================��ѯ����END 
        //=================================================================�������BEG
        Element eAccFlag=data.addElement("accflag");        
        Element eAccFlagChild =eAccFlag.addElement("accflag");
        eAccFlagChild.setText(accflag);
        //
        Element eAccDesc=data.addElement("accdesc");        
        Element eAccDescChild =eAccDesc.addElement("accdesc");
        eAccDescChild.setText(accdesc);
        //=================================================================�������END
        //=================================================================������ʶBEG
        Element eFlag=data.addElement("flags");        
        Element eFlagChild =eFlag.addElement("onecheck");
        eFlagChild.setText(onecheck);
        eFlagChild =eFlag.addElement("endcheck");
        eFlagChild.setText(endcheck);
        eFlagChild =eFlag.addElement("usercheck");
        eFlagChild.setText(usercheck);
        eFlagChild =eFlag.addElement("morecheck");
        eFlagChild.setText(morecheck);
        eFlagChild =eFlag.addElement("usermorecheck");
        eFlagChild.setText(usermorecheck);
        eFlagChild =eFlag.addElement("report");
        eFlagChild.setText(report);
        //��ȡ���
        //
        HashMap hashmaptable = new HashMap();
        //
        hashmaptable.put("jtabid", jtabid);
        //
        hashmaptable.put("sql", sql);
        hashmaptable.put("countsql", countsql);
        hashmaptable.put("xmlth", xmlth);
        //
        hashmaptable.put("accflag", accflag);
        //
        hashmaptable.put("onecheck", onecheck);
        hashmaptable.put("endcheck", endcheck);
        hashmaptable.put("usercheck", usercheck);
        hashmaptable.put("morecheck", morecheck);
        hashmaptable.put("usermorecheck", usermorecheck);
        hashmaptable.put("report", report);
        //�ֵ�ֵ�б�
        hashmaptable.put("dictionary",dictionary);
        //=================================================================������ʶEND
        //
        //=================================================================������BEG
        if(!"sql".equals(jsqlmode)){
            String stable = policychecksql.getCheckPageTable(hashmaptable).toString();
            Element eTable=data.addElement("table");
            Element eTableChild =eTable.addElement("elements");
            eTableChild.setText(stable);
    	}
        //=================================================================������END
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML());
        shtml.append(node.asXML());
        //============================================================xml==============================
		return shtml;
	}
	/**
	 * �����������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer DelIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "5",scheckflag = "checkflag1";
		String snextifover = "4",snextcheckflag = "checkflag2";
		String sql = "";
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        String jpid = hashmap.get("jpid").toString();        
        String jfmids = hashmap.get("jfmids").toString();
        String jendcheck = hashmap.get("jendcheck").toString();
        String jreport = hashmap.get("jreport").toString();
        //
        
		if ("1".equals(jreport)) { // �ϱ�����(ȷ������)
			if ("1".equals(jendcheck)) { // �������
				sifover = jdepth;
				//
				if (sifover.equals(constantdefine.POLICYQUERYCODE_5)) { // ����
					scheckflag = "checkflag1";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_4)) {// �ֵ�
					scheckflag = "checkflag2";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_3)) {// ����
					scheckflag = "checkflag3";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_2)) {// �о�
					scheckflag = "checkflag4";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_1)) {// ʡ��
					scheckflag = "checkflag5";
				}
				//����Ϊδ����״̬
				sql = "update biz_t_optcheck opt "
					+ "set result = '1',"
					+ "resultoper = '"+jempid+"',"
					+ "resultdt = sysdate,"
					+ scheckflag + " = '1' "
					+ "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") " 
					+ "and ifover = '"+sifover+"'";		//��������״̬
			} else { 					// �����������
				sifover = jdepth;
				PolicyQuery policyquery = new PolicyQuery();
				snextifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
				//
				if (sifover.equals(constantdefine.POLICYQUERYCODE_5)) { // ����
					scheckflag = "checkflag1";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_4)) {// �ֵ�
					scheckflag = "checkflag2";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_3)) {// ����
					scheckflag = "checkflag3";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_2)) {// �о�
					scheckflag = "checkflag4";
				} else if (sifover.equals(constantdefine.POLICYQUERYCODE_1)) {// ʡ��
					scheckflag = "checkflag5";
				}
				if (snextifover.equals(constantdefine.POLICYQUERYCODE_5)) { // ����
					snextcheckflag = "checkflag1";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_4)) {// �ֵ�
					snextcheckflag = "checkflag2";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_3)) {// ����
					snextcheckflag = "checkflag3";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_2)) {// �о�
					snextcheckflag = "checkflag4";
				} else if (snextifover.equals(constantdefine.POLICYQUERYCODE_1)) {// ʡ��
					snextcheckflag = "checkflag5";
				}
				//����Ϊδ����״̬
				sql = "update biz_t_optcheck opt "
					+ "set ifover = '"+sifover+"'," 
					+ "result = '1',"
					+ "resultoper = '"+jempid+"',"
					+ "resultdt = sysdate,"
					+ scheckflag + " = '1' "
					+ "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") " 
					+ "and (ifover = '"+sifover+"' or (ifover = '"+snextifover+"' and "+snextcheckflag+" = '1'))";//�ϼ�Ϊδ����״̬
			}
			//log.debug(sql);
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append(pubilccomm.ExeSQLFromUserSQL(sql));
	    	shtml.append(",������ܳ���(ԭ��:�ϼ��Ѿ���������)!");
		}
		return shtml;
	}
}
