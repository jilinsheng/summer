package com.mingda.action.policy.approval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.policy.comm.PublicMoney;
import com.mingda.common.page.PageView;

public class Policychecksql {
	static Logger log = Logger.getLogger(Policychecksql.class);
	/**
	 * ��������ҳ����
	 * @param hashmap
	 * @return
	 */
	public StringBuffer getCheckPageTable(HashMap hashmap) {
		StringBuffer shtml= new StringBuffer("");
		//
		Policycheckpage policycheckpage = new Policycheckpage();
		//========�ֵ����ʹ���===========//
		PageView pv = new PageView();
		//�ֵ�ֵ
    	Document dictionary = (Document) hashmap.get("dictionary");
    	//
		//========�ֵ����ʹ���===========//
		//
		String jtabid = hashmap.get("jtabid").toString();
		//
		String sql = hashmap.get("sql").toString();
		String xmlth = hashmap.get("xmlth").toString();
		//
		String accflag = hashmap.get("accflag").toString();
		//
		String onecheck = hashmap.get("onecheck").toString();
		String endcheck = hashmap.get("endcheck").toString();
		String usercheck = hashmap.get("usercheck").toString();
		String morecheck = hashmap.get("morecheck").toString();
		String usermorecheck = hashmap.get("usermorecheck").toString();
		String report = hashmap.get("report").toString();
		//
		String smorecheck = "0";		
		if(usercheck.equals("1")){
			smorecheck = "1";
			if(morecheck.equals("1")){
				smorecheck = "2";
				if(!usermorecheck.equals("1")){
					smorecheck = "1";
				}
			}
		}
		//===================������========================================================================
		//��ͥID,��ͥ���,��������,�����˿�,��������,��������,���ھ�����,�ⷢ������,����״̬,������,����,����ID,��Ϣ�仯��ʶ
		//����[����ID]��
		//===================������========================================================================
		//=============================================table
		String[] columu_name = null;      
        int columnCount =0;       
        //
        columu_name = xmlth.split(",");
        columnCount = columu_name.length;
        //
        shtml.append("<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>");  		  	
		//=============================th
  		shtml.append("<tr class='colField'>");
  		if(jtabid.equals("0")){	//ȫ��������ǩ��ѯ
  			//��
  		}else if(jtabid.equals("3")){	//��������������ǩ��ѯ
  			shtml.append("<td height='25'>����</td>");
  		}else if(jtabid.equals("4")){	//������������ǩ��ѯ
  			shtml.append("<td height='25'>����</td>");
  		}else{
  			shtml.append("<td height='25'>����</td>");
  		}  		
        for (int i = 1; i < columnCount-2; i++) {    //����[����ID]��          
        	//��ѯ�ֶ�������
        	columu_name[i] = columu_name[i].replace(".",",");
        	String[] th_name = columu_name[i].split(",");
        	String thname = th_name[1];
        	shtml.append("<td height='25'>"+thname+"</td>");         
        }
        shtml.append("<td height='25'>��ز���</td>");
        shtml.append("</tr>");
        //=============================th
        //=============================tr
        //log.debug(xmlth);
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
            	String fmid = "",fmno = "",ischange = "0";
            	//
            	fmid = rs.getString(1);
            	fmno = rs.getString(2);
            	ischange = rs.getString(13);
            	//��˫��
				int mathrow = irow%2;				
				if(mathrow == 1){//������ɫ
					shtml.append("<tr style = 'background: #F2F5F7;'>");						
				}else{
					shtml.append("<tr>");
				}
				//��Ϣ��
				if("2".equals(accflag)){	//�������״̬
					if(jtabid.equals("0")){	//ȫ��������ǩ��ѯ
			  			//��
			  		}else if(jtabid.equals("3")){	//��������������ǩ��ѯ
			  			shtml.append("<td height='25' class='colValue'>" 
								+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
				  		}else if(jtabid.equals("4")){	//������������ǩ��ѯ
			  			shtml.append("<td height='25' class='colValue'>" 
							+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
			  		}else{
			  			shtml.append("<td height='25' class='colValue'>" 
							+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
			  		}					
				}else{						//δ����״̬
					if(jtabid.equals("0")){			//ȫ��������ǩ��ѯ
						//��
					}else if(jtabid.equals("3")){	//��������������ǩ��ѯ
						if(smorecheck.equals("0")){		//������Ȩ��
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
						}else{							//������Ȩ��
							//���������������
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' onclick= \"DelIdea('"+fmid+"')\"/></td>");
						}
					}else if(jtabid.equals("4")){	//������������ǩ��ѯ
						if(smorecheck.equals("0")){		//������Ȩ��
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
						}else{							//������Ȩ��
							//���������������
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' onclick= \"DelIdea('"+fmid+"')\"/></td>");
						}
					}else{							//������ǩ��ѯ
						if(smorecheck.equals("0")){		//������Ȩ��
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' disabled = 'disabled' /></td>");
						}else if(smorecheck.equals("1")){//��������
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='radio' name='rd[]' onclick= \"CallOneIdea('"+fmid+"')\"/></td>");
						}else if(smorecheck.equals("2")){//��������
							shtml.append("<td height='25' class='colValue'>" 
									+ "<input type='checkbox' name='cbx[]' id = '"+fmid+"' onclick= \"chkTbRow(this)\"/></td>");
						}
		            }
				}				
				//��ͥ���
				shtml.append("<td height='25' class='colValue pointer' title = '�鿴��ͥ��Ϣ' " 
					+ "onclick= \"infoaction('"+fmid+"')\">"+fmno+"</td>");
				//
          		for (int i = 2; i < columnCount-2; i++) {	//����[����ID]��
          			//��ʼ�������ֵ
          			if(i==5){			//��������
          				String tem = rs.getString(i+1);
          				tem = pv.getDictionartHandle().getDictsortToValue(dictionary,tem);
        				if(null == tem || "".equals(tem)){
        					tem = "��";
        				}
        				//��Ϣ�б䶯
        				if("1".equals(ischange)){
        					shtml.append("<td height='25' class='colValue' style = 'color: red;'>"+ tem +"</td>");
        				}else{
        					shtml.append("<td height='25' class='colValue' >"+ tem +"</td>");
        				}
          					            		
          			}else if(i==6){			//���ھ�����
          				String tem = PublicMoney.parseMoney(rs.getString(i+1));
          				shtml.append("<td height='25' class='colValue'>"+ tem +"</td>");	            		
          			}else if(i==7){		//�ⷢ������
          				String tem = PublicMoney.parseMoney(rs.getString(i+1));
          				shtml.append("<td height='25' class='colValue'>"+ tem +"</td>");	             		
          			}else if(i==8){		//����״̬
          				shtml.append("<td height='25' class='colValue'  style = 'color: #6BA6FF;'>"
	            			+ policycheckpage.repalcePolicyMoneyFlag(rs.getString(i+1))+"</td>");	            		
          			}else if(i==9){		//�������
          				shtml.append("<td height='25' class='colValue pointer' title = '������ϸ' " 
      						+ "onclick= \"GetCheckIdeaFlow('"+fmid+"')\">"
	            			+ policycheckpage.repalcePolicyCheckFlag(rs.getString(i+1))+"</td>");
          			}else{          				
          				shtml.append("<td height='25' class='colValue'>"+rs.getString(i+1)+"</td>");
                	}         
                }
          		//��ز�����
          		shtml.append("<td height='25' class='colValue'>" 
          				+ "<span  class = 'pointer'>" 
          				+ "<img src='/db/page/images/checkrepidea.png' alt= '�����Ϣ' " 
          				+ "onclick=\"GetCheckPolicyInfosHtml('"+fmid+"','')\"/> "
          				+ "<img src='/db/page/images/checkreqidea.png' alt= '�߷õǼ�' " 
          				+ "onclick=\"CallInterviewDialog("+fmid+")\"/>");
          				//�༭��ͥ��Ϣ
		          		if("1".equals(onecheck)){	//��һ����������
		          			shtml.append("&nbsp;<img src='/db/page/images/checkfamily.png' alt= '�༭��ͥ' " 
		          				+ "onclick= \"infoeditaction('"+fmid+"')\"/>");
		          		}
		          		shtml.append("</span>");
		        shtml.append("</td>");
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
