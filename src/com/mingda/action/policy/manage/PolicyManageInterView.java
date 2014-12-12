package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.query.PolicyManageCheckQuery;

public class PolicyManageInterView {
	static Logger log = Logger.getLogger(PolicyManageInterView.class);
	/**
	 * ��ȡ��ͥ�߷ü�¼��Ϣ
	 * @param viewid
	 * @return
	 */
	public String getInterviewIdeaItem(String viewid){
		//����ҵ���ѯ������
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		JSONArray array = new JSONArray();      //����JSON����
        String sql = "select interview_id,family_id,a.organization_id,b.fullname,person,type,result,viewdt,operid,operdt " +
        		"from biz_t_interview a,sys_t_organization b " +
        		"where a.organization_id = b.organization_id and interview_id = '"+viewid+"'";   //����SQL���
      	//log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //�������������JSON�����м���JSONObject
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("iid", rs.getString("interview_id"));
                obj.put("ifmid", rs.getString("family_id"));
                obj.put("ideptname", rs.getString("fullname"));
                obj.put("iperson", rs.getString("person"));
                obj.put("itype", rs.getString("type"));
                obj.put("iresult", rs.getString("result"));
                //���ڸ�ʽ��
                String tempdt = rs.getString("viewdt");
                tempdt = policymanagecheckquery.getformatdt(tempdt);
                obj.put("idt", tempdt);
                array.add(obj);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return array.toString();
	}
	/**
	 * ���ü�ͥ�߷ü�¼
	 * @param fmid
	 * @param iperson
	 * @param idt
	 * @param iresult
	 * @param itype
	 * @param ideptid
	 * @param operid
	 * @return
	 */
	public String setInterviewIdeaItem(String fmid,
		      String iperson,String idt,String iresult,String itype,String ideptid,String operid){
		String  srep = "";
		//
		String sql = "insert into biz_t_interview " +
		"(interview_id, family_id, organization_id, person, type, result, viewdt, operid, operdt) " +
		"values  " +
		"(xinterview_id.nextval,'"+fmid+"','"+ideptid+"','"+iperson+"','"+itype+"','"+iresult+"',to_date('"+idt+"','yyyy-mm-dd'),'"+operid+"',sysdate)";
        //log.debug(sql);
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//����sql����PreparedStatement            
            pstmt.execute();              //ִ��            
            conn.commit();                      //�ر�
            srep = "����߷ü�¼�����ɹ�!";
        } catch (SQLException e) {
        	try {
        		srep = "����߷ü�¼����ʧ��!";
	            conn.rollback();
	        } catch (SQLException e1) {          
	            e1.printStackTrace();
	        }
        } finally {
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
		return srep;
	}
}
