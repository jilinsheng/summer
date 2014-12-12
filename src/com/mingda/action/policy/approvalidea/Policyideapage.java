package com.mingda.action.policy.approvalidea;

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
import com.mingda.action.policy.approval.Policycheckpage;
import com.mingda.action.policy.approval2.Policychecksql;
import com.mingda.action.policy.comm.PublicComm;
import com.mingda.action.policy.find.PolicyAccQuery;
import com.mingda.action.policy.find.PolicyQuery;
import com.mingda.action.policy.sql.PolicyPhySQL;
import com.mingda.common.ConstantDefine;

public class Policyideapage {
	static Logger log = Logger.getLogger(Policyideapage.class);
	/**
	 * ��ȡ�������������
	 * @param sname
	 * @param stabid
	 * @return
	 */
	public StringBuffer getCheckIdeaSelect(String sname,String stabid,String sonecheck){
		StringBuffer shtml= new StringBuffer("");
		//
		String iall = "<option value='0'>ȫ��</option>";
		String inew = "<option value='1'>δ����</option>";
		String iok = "<option value='2'>ͬ�����</option>";		
		String ire = "<option value='3'>��������</option>";
		String idel = "<option value='8'>ȡ������</option>";
		String inot = "<option value='9'>��ͬ�����</option>";
		//
		shtml.append("<select id=\""+sname+"\" style = \"font-size:12px\">");
		//
		if("1".equals(stabid)){				//����
			shtml.append(iok);			
			if(!"1".equals(sonecheck)){	//��һ��������������ѡ��(��������)
				shtml.append(ire);
			}
		}else if("2".equals(stabid)){		//��������
			shtml.append(iok);			
			shtml.append(idel);
			if(!"1".equals(sonecheck)){	//��һ��������������ѡ��(��������)
				shtml.append(ire);
			}
		}
		//
		shtml.append("</select>");
		return shtml;
	}
	/**
     * ȡ������ԭ����ิѡѡ��
     * �ֵ����Ϊ[115]
     * @param sname
     * @return
     */
    public StringBuffer getRequestTypeCheckBox(){
    	StringBuffer shtml = new StringBuffer("");
		String id = "", name = "";
		int irow = 0;
		String sql = "select  " 
			+ "dictionary_id," 
			+ "item " 
			+ "from sys_t_dictionary " 
			+ "where dictsort_id = '115' and status = '1'";
		// log.debug(sql);
		//
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ����
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getString("dictionary_id");
				name = rs.getString("item");
				shtml.append("<input type='checkbox' name = 'cbxq[]' id='q" + id + "' title=" + name + ">" + name + "</input>");
				irow++;
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
		}
		if(irow==0){
			shtml.append("�޼�¼!");
		}
		return shtml;
    }
    /**
     * ��ȡ�û���������
     * @param deptid
     * @return
     */
    public StringBuffer getRequestPersonCheckBox(String deptid){
    	StringBuffer shtml = new StringBuffer("");
    	int irow = 0;
		String id = "", name = "";
		String sql = "select " 
			+ "optreviewperson_id," 
			+ "name " 
			+ "from biz_t_optreviewperson " 
			+ "where status = '1' and organization_id = '"+deptid+"'";
		// log.debug(sql);
		//
		Connection conn = null; // ����Connection����
		PreparedStatement pstmt = null; // ����PreparedStatement����
		ResultSet rs = null; // ����ResultSet����
		try {
			conn = DBUtils.getConnection(); // ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); // ����sql����PreparedStatement
			// ����
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getString("optreviewperson_id");
				name = rs.getString("name");
				shtml.append("<input type='checkbox' name = 'cbxp[]' id='p" + id + "' title=" + name + ">" + name + "</input>");
				irow++;
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // �رս����
			DBUtils.close(pstmt); // �ر�PreparedStatement
			//DBUtils.close(conn); // �ر�����
		}
		if(irow==0){
			shtml.append("�޼�¼!");
		}
		return shtml;
    }
    /**
     * ���µ���������
     * @param optid
     * @param money
     * @return
     */
    public StringBuffer updateMoneyFromOptID(String optid,String money){
    	StringBuffer shtml= new StringBuffer("");
    	String sql = "update biz_t_optcheck	"
    		+ "set checkmoney = '"+money+"' "
    		+ "where optcheck_id = '"+optid+"'";
    	PublicComm pubilccomm = new PublicComm();
    	shtml.append(pubilccomm.ExeSQLFromUserSQL(sql));
    	return shtml;
    }
	/**
	 * ����XML�ļ�
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaResultForXml(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		String sql = "";
		//
		PolicyPhySQL policyphysql = new PolicyPhySQL();
		Policyideasql policyideasql = new Policyideasql();
		PolicyAccQuery policyaccquery = new PolicyAccQuery();
		//
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jpid = hashmap.get("jpid").toString();
		String jaccmode = hashmap.get("jaccmode").toString();
		String jfmids = hashmap.get("jfmids").toString();
		//
		// ҵ���������
	    String acctype = policyaccquery.getPolicyAccTypeFlag(jpid);
		//
	    if ("1".equals(acctype)) { // ��Ա����
	    	sql = "select " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "b.member_id,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "a.membername "
	        	+ "from info_t_member a, biz_t_optcheck b,info_t_family f "
	        	+ "where a.member_id = b.member_id " 
	        	+ "and a.family_id = f.family_id " 
	        	+ "and b.family_id = f.family_id "
	        	+ "and b.policy_id = '"+jpid+"' "
	        	+ "and b.family_id in ("+jfmids+") " 
	        	+ "group by " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "b.member_id,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "a.membername ";
	    }else{					   // ��ͥ����
	    	sql = "select " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "f.masterid member_id,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "f.mastername membername "
	        	+ "from biz_t_optcheck b,info_t_family f "
	        	+ "where b.family_id = f.family_id "
	        	+ "and b.policy_id = '"+jpid+"' "
	        	+ "and b.family_id in ("+jfmids+") " 
	        	+ "group by " 
	        	+ "optcheck_id,"
	        	+ "policy_id,"
	        	+ "b.family_id,"  
	        	+ "f.masterid ,"  
	        	+ "f.familyno,"  
	        	+ "recheckmoney,"
	        	+ "checkmoney,"
	        	+ "f.mastername ";
	    }	
	    //log.debug(sql);
		//��ȡ������Ϣ
		HashMap hmcount = policyphysql.getIdeaCountFromSQL(sql);
	    String sqlcount = hmcount.get("sqlcount").toString();
	    String sumolemoney = hmcount.get("sumolemoney").toString();
	    String sumnewmoney = hmcount.get("sumnewmoney").toString();
	    //
	    //�����������
        HashMap hashmapsql = new HashMap();
        hashmapsql.put("sql", sql);
        hashmapsql.put("jaccmode", jaccmode);
        hashmapsql.put("jonecheck", jonecheck);
        hashmapsql.put("jendcheck", jendcheck);
        String stable = policyideasql.getIdeaPageTable(hashmapsql).toString();
		//============================================================xml==============================
        //
		Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GB18030");
        Element root = doc.addElement("root");
        Element data = root.addElement("data");
        //
        //=================================================================��ѯ����BEG 
        Element eCount=data.addElement("counts");
        //����¼����
        Element eCountChild =eCount.addElement("sqlcount");
        eCountChild.setText(sqlcount);
        //������ھ��������
        eCountChild =eCount.addElement("sumolemoney");
        eCountChild.setText(sumolemoney); 
        //����ⷢ���������
        eCountChild =eCount.addElement("sumnewmoney");
        eCountChild.setText(sumnewmoney); 
        //=================================================================��ѯ����END
        //=================================================================�������
        Element eTable=data.addElement("table");
        Element eTableChild =eTable.addElement("elements");
        eTableChild.setText(stable);
        //=================================================================�������
        //
        Node node=  root.selectSingleNode("/root/data");
        //log.debug(node.asXML());
        shtml.append(node.asXML());
        //============================================================xml==============================
		return shtml;
	}
	/**
	 * У������
	 * @param mode
	 * @param hashmap
	 * @return
	 */
	public StringBuffer validationCheckIdea(String mode,HashMap hashmap){
    	StringBuffer shtml= new StringBuffer("");
    	String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
        //
        String jpid = hashmap.get("jpid").toString();
        ////�������α�ʶ
		PolicyAccQuery policyaccquery = new PolicyAccQuery();    	
        HashMap hashmapaccflag = policyaccquery.getPolicyAccInfo(jpid, jdeptid);
        String optid = hashmapaccflag.get("optid").toString();
        String accflag = hashmapaccflag.get("accflag").toString();
        String accmoneyflag = hashmapaccflag.get("accmoneyflag").toString();
        String accmoney = hashmapaccflag.get("accmoney").toString();
        //
		if ("-1".equals(accflag)) { // �޽�������
			shtml.append("��ҵ��δִ�д���,�ȴ�����������Ϣ!");
			return shtml;
		} else if ("0".equals(accflag)) { // �½�������
			if ("addCheckIdea".equals(mode)) {
				shtml.append(addCheckIdea(hashmap));
			} else if ("updateCheckIdea".equals(mode)) {
				shtml.append(updateCheckIdea(hashmap));
			} else if ("allCheckIdea".equals(mode)) {
				shtml.append(allCheckIdea(hashmap));
			} else if ("moneyCheckIdea".equals(mode)) {
				shtml.append(moneyCheckIdea(hashmap));
			} else if ("allMoneyCheckIdea".equals(mode)) {
				shtml.append(allMoneyCheckIdea(hashmap));
			}
			//���±���״̬
			UpdateEndSalType(mode,hashmap);
			
		} else if ("1".equals(accflag)) { // ���ڽ�������
			shtml.append("��ҵ������ִ�н��㴦��,��ȴ�!");
			return shtml;
		} else if ("2".equals(accflag)) { // �����������
			shtml.append("��ҵ��������,�ȴ������µĽ�����Ϣ!");
			return shtml;
		}
		return shtml;
    }
	/**
	 * ����ũ��ͱ���������
	 * @param mode
	 * @param hashmap	 
	 * @return
	 */
	public StringBuffer UpdateEndSalType(String mode,HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		String sql = "",jfmids = "",jallsql = "";
		//
		String jpid = hashmap.get("jpid").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		
		
		//
		if("21".equals(jpid)){		//ָ��ũ��ͱ�
			if("1".equals(jendcheck)){	//��������
				if ("addCheckIdea".equals(mode)) {
					jfmids = hashmap.get("jfmids").toString();					
					sql = "update INFO_T_FAMILY set desSalType = SalType where family_id in ("+jfmids+") ";
				} else if ("updateCheckIdea".equals(mode)) {
					jfmids = hashmap.get("jfmids").toString();
					sql = "update INFO_T_FAMILY set desSalType = SalType where family_id in ("+jfmids+") ";
				} else if ("allCheckIdea".equals(mode)) {
					jallsql = hashmap.get("jallsql").toString();
					sql = "update INFO_T_FAMILY fm set fm.desSalType = fm.SalType where  exists (select t.family_id from ("+jallsql+") t where fm.family_id = t.family_id) ";
				} else if ("moneyCheckIdea".equals(mode)) {
					jfmids = hashmap.get("jfmids").toString();
					sql = "update INFO_T_FAMILY set desSalType = SalType where family_id in ("+jfmids+") ";
				} else if ("allMoneyCheckIdea".equals(mode)) {
					jallsql = hashmap.get("jallsql").toString();
					sql = "update INFO_T_FAMILY fm set fm.desSalType = fm.SalType where  exists (select t.family_id from ("+jallsql+") t where fm.family_id = t.family_id) ";
				}
				//log.debug(sql);
				PublicComm pubilccomm = new PublicComm();
		    	shtml.append("���±���״̬:"+pubilccomm.ExeSQLFromUserSQL(sql));
			}
		}
		
		return shtml;
    }
	/**
	 * ��������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer addCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jbegdt = hashmap.get("jbegdt").toString();
		String jenddt = hashmap.get("jenddt").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		String jdesc = hashmap.get("jdesc").toString();
		String jmanall = hashmap.get("jmanall").toString();
		String jmanok = hashmap.get("jmanok").toString();
		String jmandel = hashmap.get("jmandel").toString();
		String jmannot = hashmap.get("jmannot").toString();
		String jmanids = hashmap.get("jmanids").toString();
		//
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";
		//�������
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//�ϱ�����(ȷ������)
			//����
			usql = "update biz_t_optcheck opt set ";
			//
			//�������У��
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//��������(�������)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//����
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//������������(�������)
				if("1".equals(jendcheck)){	//��������
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ "rebegdt = to_date('"+jbegdt+"','yyyy-mm-dd'),"
						+ "reenddt = to_date('"+jenddt+"','yyyy-mm-dd')," 
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//�¼�����	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//����
			usql += "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") ";
			//
			if("1".equals(jtabid)){				//������ѯģ��
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("����������:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}
		//��ѯ���
		String sql = "select " 
			+ "xoptcheckidea_id.nextval," 
			+ "t.optcheck_id, "	
			+ "'"+jmanids+"',"
			+ "'"+jmandel+"',"
			+ "'"+jmanok+"',"
			+ "'"+jmanall+"',"
			+ "'"+jmannot+"',"
			+ "'"+jresult+"',"
			+ "'"+jdesc+"',"
			+ "to_date('"+jbegdt+"','yyyy-mm-dd')," 
			+ "to_date('"+jenddt+"','yyyy-mm-dd')," 
			+ "to_date('"+jcheckdt+"','yyyy-mm-dd'),"
			+ "'��������',"
			+ "'"+jdepth+"',"
			+ "'"+jempid+"',"
			+ "sysdate,"
			+ "'1'"
			+ "from biz_t_optcheck t " 
			+ "where t.policy_id = '"+jpid+"' " 
			+ "and t.family_id in ("+jfmids+") ";
		//��������Ϊ0
		if("1".equals(jtabid)){				//������ѯģ��
			sql += " and checkmoney > 0 ";
		}
		//�������
		String isql = "insert into biz_t_optcheckidea "
			+ "(optcheckidea_id,"
			+ "optcheck_id,"
			+ "appideaman,"
			+ "appmandelnum,"
			+ "appmanoknum,"
			+ "appmannum,"
			+ "appmannotnum,"
			+ "appresult,"
			+ "apparea,"
			+ "rebegdt,"
			+ "reenddt,"
			+ "apptime,"
			+ "note,"
			+ "depth,"
			+ "checkoper,"
			+ "checkdt,"
			+ "status) " + sql;
		
		//log.debug(isql);
		
		PublicComm pubilccomm = new PublicComm();
    	shtml.append("����������:"+pubilccomm.ExeSQLFromUserSQL(isql));
		return shtml;
	}
	/**
	 * ѡ������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer updateCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jbegdt = hashmap.get("jbegdt").toString();
		String jenddt = hashmap.get("jenddt").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		//
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";
		//�������
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//�ϱ�����(ȷ������)
			//����
			usql = "update biz_t_optcheck opt set ";
			//
			//�������У��
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//��������(�������)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//����
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//������������(�������)
				if("1".equals(jendcheck)){	//��������
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ "rebegdt = to_date('"+jbegdt+"','yyyy-mm-dd'),"
						+ "reenddt = to_date('"+jenddt+"','yyyy-mm-dd')," 
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//�¼�����	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//����
			usql += "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") ";
			//��������Ϊ0
			if("1".equals(jtabid)){				//������ѯģ��
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("����������:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * ȫ������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer allCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jallsql = hashmap.get("jallsql").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		//
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";	
		//�������
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//�ϱ�����(ȷ������)
			//����
			usql = "update biz_t_optcheck  opt set ";
			//
			//�������У��
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//��������(�������)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//����
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//������������(�������)
				if("1".equals(jendcheck)){	//��������
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//�¼�����	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//����
			usql += "where policy_id = '"+jpid+"' " 
				+ "and exists (select * from ("+jallsql+") t where opt.family_id = t.family_id) ";
			//��������Ϊ0
			if("1".equals(jtabid)){				//������ѯģ��
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("����������:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * ����ѡ������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer moneyCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		String jmoney = hashmap.get("jmoney").toString();
		//
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";	
		//�������
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//�ϱ�����(ȷ������)
			//����
			usql = "update biz_t_optcheck opt set ";
			//
			//�������У��
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//��������(�������)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//����
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//������������(�������)
				if("1".equals(jendcheck)){	//��������
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "checkmoney = '"+jmoney+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//�¼�����	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ "checkmoney = '"+jmoney+"',"
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//����
			usql += "where policy_id = '"+jpid+"' and family_id in ("+jfmids+") ";
			//��������Ϊ0
			if("1".equals(jtabid)){				//������ѯģ��
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("����������:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * ����ȫ������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer allMoneyCheckIdea(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//
        String jtabid = hashmap.get("jtabid").toString();
		String jpid = hashmap.get("jpid").toString();
		String jallsql = hashmap.get("jallsql").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		String jreport = hashmap.get("jreport").toString();
		String jresult = hashmap.get("jresult").toString();
		String jcheckdt = hashmap.get("jcheckdt").toString();
		String jmoney = hashmap.get("jmoney").toString();
		//
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		String sifover = "4",scheckflag = "checkflag2";
		String sbackifover = "5",sbackcheckflag = "checkflag1";	
		//�������
		String usql = "";
		//
    	if(jdepth.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
    		scheckflag = "checkflag1";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
    		scheckflag = "checkflag2";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_3)){//����
    		scheckflag = "checkflag3";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
    		scheckflag = "checkflag4";
    	}else if(jdepth.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
    		scheckflag = "checkflag5";
    	}
		if("1".equals(jreport)){	//�ϱ�����(ȷ������)
			//����
			usql = "update biz_t_optcheck opt set ";
			//
			//�������У��
			if(constantdefine.POLICYCHECKCODE_RENEW.equals(jresult)){//��������(�������)
				PolicyQuery policyquery = new PolicyQuery();
				sbackifover = policyquery.getPolicyBackFlowFromId(jpid, jempid);
				if(sbackifover.equals(constantdefine.POLICYQUERYCODE_5)){	  //����
					sbackcheckflag = "checkflag1";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_4)){//�ֵ�
		    		sbackcheckflag = "checkflag2";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_3)){//����
		    		sbackcheckflag = "checkflag3";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_2)){//�о�
		    		sbackcheckflag = "checkflag4";
		    	}else if(sbackifover.equals(constantdefine.POLICYQUERYCODE_1)){//ʡ��
		    		sbackcheckflag = "checkflag5";
		    	}
				//
				usql += "ifover = '"+sbackifover+"'," 
					+ "result = '1',"
					+ "resultoper = '',"
					+ sbackcheckflag + " = '" + jresult + "' ";
			}else{					//������������(�������)
				if("1".equals(jendcheck)){	//��������
					sifover = jdepth;
					usql += "ifover = '"+sifover+"',"
						+ "checkmoney = '"+jmoney+"',"
						+ "result = '"+jresult+"',"
						+ "resultoper = '"+jempid+"',"
						+ "resultdt = to_date('"+jcheckdt+"','yyyy-mm-dd'),"
						+ scheckflag + " = '" + jresult + "' ";
				}else{						//�¼�����	
					PolicyQuery policyquery = new PolicyQuery();
					sifover = policyquery.getPolicyNextFlowFromId(jpid, jempid);
					usql += "ifover = '"+sifover+"'," 
						+ "checkmoney = '"+jmoney+"',"
						+ scheckflag + " = '" + jresult + "' ";
				}
			}
			//����
			usql += "where policy_id = '"+jpid+"' " 
				+ "and exists (select * from ("+jallsql+") t where opt.family_id = t.family_id) ";
			//��������Ϊ0
			if("1".equals(jtabid)){				//������ѯģ��
				usql += " and checkmoney > 0 ";
			}
			//log.debug(usql);
			
			PublicComm pubilccomm = new PublicComm();
	    	shtml.append("����������:"+pubilccomm.ExeSQLFromUserSQL(usql));
		}		
		return shtml;
	}
	/**
	 * ��ȡ��������
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaFlowForXml(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		String jdeptid =hashmap.get("jdeptid").toString();
		String jdepth = hashmap.get("jdepth").toString();
        String jempid = hashmap.get("jempid").toString();
		//        
		String jpid = hashmap.get("jpid").toString();
		String jfmids = hashmap.get("jfmids").toString();
		//��������
		ConstantDefine constantdefine = new ConstantDefine();
		PolicyQuery policyquery = new PolicyQuery();
		Policycheckpage policycheckpage = new Policycheckpage();
		//����Ȩ�ޱ�ʶ    
        HashMap hashmappower = policyquery.getPolicyCheckFlags(jdepth, jempid, jpid); 
        String onecheck = hashmappower.get("onecheck").toString();
        String endcheck = hashmappower.get("endcheck").toString();
        String usercheck = hashmappower.get("usercheck").toString();
        String morecheck = hashmappower.get("morecheck").toString();
        String usermorecheck = hashmappower.get("usermorecheck").toString();
        String report = hashmappower.get("report").toString();
		//��������		
		HashMap hashmapflow = policyquery.getPolicyFlowsFromId(jpid);
		String depth1 = hashmapflow.get("depth1").toString();
		String depth2 = hashmapflow.get("depth2").toString();
		String depth3 = hashmapflow.get("depth3").toString();
		String depth4 = hashmapflow.get("depth4").toString();
		String depth5 = hashmapflow.get("depth5").toString();
		
		//
		String optid = "";
		String checkflag1 = "1";
		String checkflag2 = "1";
		String checkflag3 = "1";
		String checkflag4 = "1";
		String checkflag5 = "1";
		String sql = "select t.optcheck_id," 
			+ "t.checkflag1," 
			+ "t.checkflag2," 
			+ "t.checkflag3," 
			+ "t.checkflag4," 
			+ "t.checkflag5 "
			+ "from biz_t_optcheck t "
			+ "where t.family_id = '"+jfmids+"' "
			+ "and t.policy_id = '"+jpid+"' "
			+ "order by t.member_id";
		//
		//=============================================table
		shtml.append("<table id = 'ideaflowtb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
//  		shtml.append("<tr class='colField'>");
//  		shtml.append("<td height='25'>��������</td>");
//  		shtml.append("<td height='25'>�������</td>");
//  		shtml.append("<td height='25'>��ϸ</td>");
//  		shtml.append("</tr>");
  		//=============================th
        //=============================tr
        //log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //
            if (rs.next()) {
            	optid = rs.getString("optcheck_id");
            	checkflag1 = rs.getString("checkflag1");
            	checkflag2 = rs.getString("checkflag2");
            	checkflag3 = rs.getString("checkflag3");
            	checkflag4 = rs.getString("checkflag4");
            	checkflag5 = rs.getString("checkflag5");
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            DBUtils.close(conn);                //�ر�����
        }
        //
        
		//
        //����
        if("1".equals(depth1)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_5+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag1)+"</td>");
      		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_5 +"','"+optid+"')\">��ϸ</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>�޵���������ϸ��Ϣ</td>");
      		}
    		shtml.append("</tr>");
    		//
    		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
	    		//������ϸ
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_5);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
      		//
        }
        //�ֵ�
        if("1".equals(depth2)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_4+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag2)+"</td>");
      		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_4 +"','"+optid+"')\">��ϸ</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>�޵���������ϸ��Ϣ</td>");
      		}
    		shtml.append("</tr>");
    		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
	    		//������ϸ
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_4);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //����
        if("1".equals(depth3)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_3+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag3)+"</td>");
      		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_3 +"','"+optid+"')\">��ϸ</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>�޵���������ϸ��Ϣ</td>");
      		}
    		shtml.append("</tr>");
    		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
	    		//������ϸ
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_3);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //��
        if("1".equals(depth4)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_2+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag4)+"</td>");
      		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_2 +"','"+optid+"')\">��ϸ</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>�޵���������ϸ��Ϣ</td>");
      		}
    		shtml.append("</tr>");
    		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
	    		//������ϸ
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_2);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //ʡ
        if("1".equals(depth5)){
        	shtml.append("<tr style='background-color:#F5A89A;'>");
            shtml.append("<td height='25' class='colValue'>"+constantdefine.POLICYQUERY_1+"</td>");
      		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(checkflag5)+"</td>");
      		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
      			shtml.append("<td height='25' class='colValue pointer'  " +
          			"onclick= \"GetCheckIdeaFlowDesr('"+jpid+"','"+constantdefine.POLICYQUERYCODE_1 +"','"+optid+"')\">��ϸ</td>");
      		}else{
      			shtml.append("<td height='25' class='colValue'>�޵���������ϸ��Ϣ</td>");
      		}
    		shtml.append("</tr>");
    		//������ϸ��Ϣ
      		if("0".equals(morecheck)){	//ҵ������������ʶ
	    		//������ϸ
	    		HashMap hashmapidea = new HashMap();
	    		//
	    		hashmapidea.put("jpid", jpid);	
	    		hashmapidea.put("joptdepth", constantdefine.POLICYQUERYCODE_1);
	    		hashmapidea.put("joptid", optid);
	    		//
	    		shtml.append("<tr>");
	    		shtml.append("<td colspan = '3'>"+getIdeaFlowDesrForXml(hashmapidea)+"</td>");
	    		shtml.append("</tr>");
      		}
        }
        //=============================tr        
        shtml.append("</table>");
        //=============================================table
		return shtml;
	}
	/**
	 * ������ϸ��Ϣ
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaFlowDesrForXml(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		Policycheckpage policycheckpage = new Policycheckpage();
		PolicyQuery policyquery = new PolicyQuery();
		//
		String jpid = hashmap.get("jpid").toString();	
		String joptdepth = hashmap.get("joptdepth").toString();		   
		String joptid = hashmap.get("joptid").toString();
		//
		//��һ��������
		String onedepth = policyquery.getPolicyOneFlowFromId(jpid);
		//		
		String sql = "select optcheckidea_id,"
			+ "optcheck_id,"
			+"appideaman,"
			+"appmandelnum,"
			+"appmanoknum,"
			+"appmannum,"
			+"appmannotnum,"
			+"appresult,"
			+"apparea,"
			+"to_char(apptime, 'yyyy-mm-dd') apptime,"
			+"emp.name "
			+"from biz_t_optcheckidea opt, sys_t_empext emp "
			+"where opt.checkoper = emp.employee_id "
			+"and opt.optcheck_id = '"+joptid+"' ";
		//
		sql += "and opt.depth = '"+joptdepth+"' " ;
		sql += "order by apptime desc";
		//
		//=============================================table
		shtml.append("<table id = 'ideaflowdesctb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
  		shtml.append("<tr style='text-align: center;font-size:9pt;background-color:#F2F5F7;'>");
  		//
  		shtml.append("<td height='25'>�������</td>");
  		shtml.append("<td height='25'>��������</td>");
  		shtml.append("<td height='25'>�������</td>");
  		if(joptdepth.equals(onedepth)){		//��һ��������
  			shtml.append("<td height='25'>�����������</td>");
  	  		shtml.append("<td height='25'>ͬ������</td>");
  	  		shtml.append("<td height='25'>��Ȩ����</td>");
  	  		shtml.append("<td height='25'>��ͬ������</td>");
  	  		shtml.append("<td height='25'>�������</td>");
  		}else{							//���ǵ�һ��������
  			shtml.append("<td height='25'>����������</td>");
  		}
  		shtml.append("<td height='25'>������</td>");
  		//
  		shtml.append("</tr>");
  		//=============================th
  		int irow = 0;
  		//log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	//=============================tr 
            	shtml.append("<tr>");
                //
          		shtml.append("<td height='25' class='colValue'>"+policycheckpage.repalcePolicyCheckFlag(rs.getString("appresult"))+"</td>");
          		shtml.append("<td height='25' class='colValue'>"+rs.getString("apptime")+"</td>");
          		shtml.append("<td height='25' class='colValue'>"+rs.getString("apparea")+"</td>");
          		if(joptdepth.equals(onedepth)){	//��һ��������
          			shtml.append("<td height='25' class='colValue'>"+rs.getString("appmannum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+rs.getString("appmanoknum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+rs.getString("appmandelnum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+rs.getString("appmannotnum")+"</td>");
              		shtml.append("<td height='25' class='colValue'>"+getIdeaPerson(rs.getString("appideaman"))+"</td>");
          		}else{							//���ǵ�һ��������
          			shtml.append("<td height='25' class='colValue'>"+getIdeaPerson(rs.getString("appideaman"))+"</td>");
          		}
          		shtml.append("<td height='25' class='colValue'>"+rs.getString("name")+"</td>");
          		//
        		shtml.append("</tr>");
            	//=============================tr   
        		irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        } 
        if(irow==0){
        	shtml.append("<tr>");
        	shtml.append("<td height='25' class='colValue' colspan = '9'>�޵���������ϸ��Ϣ</td>");
        	shtml.append("</tr>");
        }
        shtml.append("</table>");
        //=============================================table
        //
		return shtml;
	}
	/**
	 * �����������
	 * @param ids
	 * @return
	 */
	public StringBuffer getIdeaPerson(String ids) {
		StringBuffer shtml= new StringBuffer("");
		int irow = 0;
		String sql = "select name " 
			+ "from biz_t_optreviewperson t " 
			+ "where optreviewperson_id in ("+ids+")";
		
		//log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	if(irow==0){
            		shtml.append(rs.getString("name"));
            	}else{
            		shtml.append("," + rs.getString("name"));
            	}
            	//
            	irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        } 
        if(irow==0){
        	shtml.append("��");
        }
		return shtml;
	}
}
