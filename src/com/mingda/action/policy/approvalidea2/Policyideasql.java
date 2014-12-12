package com.mingda.action.policy.approvalidea2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;

public class Policyideasql {
	static Logger log = Logger.getLogger(Policyideasql.class);
	/**
	 * �������ҳ����
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getIdeaPageTable(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
        //
		String sql = hashmap.get("sql").toString();
		String jaccmode = hashmap.get("jaccmode").toString();
		String jonecheck = hashmap.get("jonecheck").toString();
		String jendcheck = hashmap.get("jendcheck").toString();
		//
        shtml.append("<table id = 'ideatb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
  		shtml.append("<tr class='colField'>");
  		shtml.append("<td height='25'>��ͥ���</td>");        
        shtml.append("<td height='25'>����</td>");
        shtml.append("<td height='25'>���ھ�����</td>");
        shtml.append("<td height='25'>�ⷢ������</td>");
        shtml.append("<td height='25' width='120'>��������</td>");
        shtml.append("<td height='25' width='120'>ҵ�������Ϣ</td>");
        shtml.append("</tr>");
        //=============================th
        //=============================tr
        //log.debug(sql);
	    Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        int irow = 0;
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	//��˫��
				int mathrow = irow%2;				
				if(mathrow == 1){//������ɫ
					shtml.append("<tr style = 'background: #F2F5F7;'>");						
				}else{
					shtml.append("<tr>");
				}
				String optid = rs.getString("optcheck_id");
				String fmid = rs.getString("family_id");				
				String fmno = rs.getString("familyno");
				String mmid = rs.getString("member_id");
				
				//
				//��ͥ���
				shtml.append("<td height='25' class='colValue pointer' title = '�鿴��ͥ��Ϣ' " +
						"onclick= \"infoaction('"+fmid+"')\">"+fmno+"</td>");
				shtml.append("<td height='25' class='colValue'>"+rs.getString("membername")+"</td>");
				shtml.append("<td height='25' class='colValue'>"+rs.getString("recheckmoney")+"</td>");
				//
				String money = rs.getString("checkmoney");
				//�ⷢ������
				if("1".equals(jaccmode)){		//������¾�����
					if("1".equals(jonecheck)){		//��һ����������
						shtml.append("<td height='25' class='colValue'>" 
								+ "<input style = 'text-align: right;' type='text' size = '8' name = 'money[]' " 
								+ "value = '"+money+"' onchange = \"ChangMoneyRow(this,'"+optid+"')\">" 
								+ "</td>");
					}else{							//���ǵ�һ����������
						shtml.append("<td height='25' class='colValue'>"+money+"</td>");
					}					
				}else{							//��������¾�����
					shtml.append("<td height='25' class='colValue'>"+money+"</td>");
				}
				shtml.append("<td height='25' class='colValue pointer'  " +
						"onclick= \"GetCheckIdeaFlow('"+fmid+"')\">��������</td>");
				shtml.append("<td height='25' class='colValue pointer'  " +
						"onclick= \"GetCheckPolicyInfosHtml('"+fmid+"','"+mmid+"')\">ҵ�����</td>");
				shtml.append("</tr>");
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
        
        //=============================tr        
        shtml.append("</table>");
        //=============================================table
        return shtml;
	}
}
