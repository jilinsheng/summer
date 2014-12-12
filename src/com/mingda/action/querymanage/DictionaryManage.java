package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;
import com.mingda.common.log4j.Log4jApp;

/**
 * �ֵ������
 * @author xiu
 *
 */
public class DictionaryManage {
	static Logger log = Logger.getLogger(DictionaryManage.class);
	
	public StringBuffer getDiscsHtml(String discid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",discname = "";
    	String sid = "",sname = "",sflag = "";
    	// ���ѯ������
		TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//�ֵ�����
    	discname = tableinfoquery.getdiscfromdiscid(discid);
    		
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>["+discname+"]�б�</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;			
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>״̬</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select dictionary_id,item,status from sys_t_dictionary where dictsort_id = '"+discid+"' order by sequence";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	sid = rs.getString("dictionary_id");
            	sname = rs.getString("item");
            	sflag = rs.getString("status");
                
                html +="<tr>";		
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+sid+"','"+sname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDiscStatus('" + sid + "','" + sname + "','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDiscStatus('" + sid + "','" + sname + "','1')\">����</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"GetDiscItemHtml('')\">�����ֵ�����</button>";    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
	
    public StringBuffer getDiscItemHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",stitle = "",temp = "",mode = "";
    	String sql = "",did = "",dname = "",dflag = "";  
	  	//
    	if(id.equals("")){
    		mode = "adddisc";
    		stitle = "[����]";
    	}else{
    		mode = "editdisc";
    		//
        	sql = "select dictionary_id,item,status from sys_t_dictionary where dictionary_id = '" + id + "'";   //����SQL���
        	//log.debug(sql);
            Connection conn = null;                 //����Connection����
            PreparedStatement pstmt = null;         //����PreparedStatement����
            ResultSet rs = null;                    //����ResultSet����
            try {
                conn = DBUtils.getConnection();     //��ȡ���ݿ�����
                pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	//
                	did = rs.getString("dictionary_id");
                	dname = rs.getString("item");
                	stitle = "["+dname+"]"; 
                	dflag = rs.getString("status");
                }
            } catch (SQLException e) {
                log.debug(e.toString());
            } finally {
                DBUtils.close(rs);                  //�رս����
                DBUtils.close(pstmt);               //�ر�PreparedStatement
                //DBUtils.close(conn);                //�ر�����
            }
    	}
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"�ֵ�����</legend>";
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>����</td>";
			html +=temp;
			temp ="<td height='23'>����ֵ</td>";
			html +=temp;
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'dname' value = '"+dname+"'></input>";
			html += "<input type='text' id='did' style='display:none' value = '"+did+"'></input></td>";
		html +="</tr>";
		//
		
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("adddisc")){//����
	    	html += "<button class = 'btn' onclick=\"SaveDisc('"+mode+"')\">����</button>"; 	   		
    	}else if(mode.equals("editdisc")){//�༭
    		//����ʱ��������
	    	if(dflag.equals("1")){	    		
	    		html += "<button class = 'btn' onclick=\"SaveDisc('"+mode+"')\">����</button>";  
	    	}		    	
    	}
    	//	
    	html += "</fieldset>";
    	//
	    shtml.append(html);
	    //log.debug(shtml);
	   
        return shtml;
    }	 	
    /**
     * �����ֵ�����
     * @param sname
     * @param sdiscid
     * @return
     */
	public String updateDisc(String mode,String did,String dname,String pdid) {
    	String srep = "",sid = "";
    	String sql = "";
    	//���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
    	if(existsDiscionary(dname,pdid)){
    		srep = "�������Ѿ�����!";
    		return srep;
    	}
    	if("adddisc".equals(mode)){
    		sid = tableinfoquery.getseqfromname("XDICTIONARY_ID");
            sql = "insert into sys_t_dictionary " +
            		"(dictionary_id,dictsort_id,item,sequence,status,code) " +
            		"values " +
            		"("+sid+",'"+pdid+"','"+dname+"','"+sid+"','1','"+sid+"')"; 
    	}else if("editdisc".equals(mode)){
    		sql = "update sys_t_dictionary set item = '"+dname+"' where dictionary_id = '"+did+"'";
    	}else{
    		return srep;
    	}
    	  
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement            
            pstmt.execute();          	//ִ��
            conn.commit();
            if("adddisc".equals(mode)){
            	srep = "����ֵ�ֵ�����ɹ�!";
            }else{
            	srep = "�����ֵ�ֵ�����ɹ�!";
            }
        } catch (SQLException e) {
        	try {
        		if("adddisc".equals(mode)){
                	srep = "����ֵ�ֵ����ʧ��!";
                }else{
                	srep = "�����ֵ�ֵ����ʧ��!";
                }
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
	/**
	 * �����ֵ�״̬
	 * @param did
	 * @param status
	 * @return
	 */
	public String updateDiscStatus(String did,String status) {
    	String srep = "",sid = "";
    	String sql = "";
    	
    	sql = "update sys_t_dictionary set status = '"+status+"' where dictionary_id = '"+did+"'";
    	  
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement            
            pstmt.execute();          	//ִ��
            conn.commit();
            //
            if("0".equals(status)){
              srep = "ͣ���ֵ����Ͳ����ɹ�!";
            }else{
              srep = "�����ֵ����Ͳ����ɹ�!";
            }
        } catch (SQLException e) {
        	try {
        		if("0".equals(status)){
                    srep = "ͣ���ֵ����Ͳ���ʧ��!";
        		}else{
        			srep = "�����ֵ����Ͳ���ʧ��!";
        		} 
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
	/**
     * ����ֵ�ֵ
     * @param sname
     * @param sdiscid
     * @return
     */
	public String addDiscionary(String sname,String sdiscid) {
    	String srep = "",sid = "";
    	//���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
    	if(existsDiscionary(sname,sdiscid)){
    		srep = "�������Ѿ�����!";
    		return srep;
    	}
    	sid = tableinfoquery.getseqfromname("XDICTIONARY_ID");
        String sql = "insert into sys_t_dictionary " +
        		"(dictionary_id,dictsort_id,item,sequence,status,code) " +
        		"values " +
        		"("+sid+",'"+sdiscid+"','"+sname+"','"+sid+"','1','"+sid+"')";   
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement            
            pstmt.execute();          	//ִ��
            conn.commit();
            srep = "����ֵ�ֵ�����ɹ�!";
        } catch (SQLException e) {
        	try {
        		srep = "����ֵ�ֵ����ʧ��!";
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
    /**
     * �Ƿ�����ֵ�ֵ
     * @param sname
     * @param sdiscid
     * @return
     */
	public boolean existsDiscionary(String sname,String sdiscid) {
    	boolean brep = false;
        String sql = "select dictionary_id from sys_t_dictionary where dictsort_id = '"+sdiscid+"' and item = '"+sname+"'";   
        
        Connection conn = null;                 //����Connection����
	    PreparedStatement pstmt = null;         //����PreparedStatement����
	    ResultSet rs = null;                    //����ResultSet����
	    try {
	        conn = DBUtils.getConnection();     //��ȡ���ݿ�����
	        pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
	        //���ò���
	        rs = pstmt.executeQuery();
	        while (rs.next()) {	
	        	//����
	        	brep = true;
	        }
	    } catch (SQLException e) {
	        Log4jApp.logger(e.toString());
	    } finally {
	        DBUtils.close(rs);                  //�رս����
	        DBUtils.close(pstmt);               //�ر�PreparedStatement
	        //DBUtils.close(conn);                //�ر�����
	    }		    
        return brep;
    }
	/**
	 * ȡ���ֵ�������ѡ��
	 * @param sname
	 * @param discid
	 * @return
	 */
	public StringBuffer getDiscionarySelect(String sname,String discid){
		StringBuffer shtml= new StringBuffer("");
		String tid = "",tname = "";
		String sql = "select dictionary_id,item from sys_t_dictionary  " +
        		"where status = '1' and dictsort_id = '"+discid+"' order by sequence";

		//log.debug(sql);
		shtml.append("<select id=\""+sname+"\" style = 'width:100%'>");
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
		    conn = DBUtils.getConnection();     //��ȡ���ݿ�����
		    pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
		    //���ò���
		    rs = pstmt.executeQuery();
		    
		    while (rs.next()) {  
		    	tid = rs.getString("dictionary_id");
		    	tname = rs.getString("item");
		    	shtml.append("<option value=\""+tid+"\">"+tname+"</option>");
		    }
		} catch (SQLException e) {
		    log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		shtml.append("</select>");
		return shtml;
	}
	/**
   * ҵ����������ѡ���б�
   * @param hname
   * @param hclass
   * @param discid
   * @return
   */
    public StringBuffer getPolicySelect(String hname,String hclass,String discid){
      StringBuffer shtml= new StringBuffer("");
      String id = "",name = "";
      //��������
       ConstantDefine constantdefine = new ConstantDefine();
       if(discid.equals(constantdefine.POLICYMENUCODE)){//����ҵ��       
           shtml.append("<select id=\""+hname+"\" class =\""+hclass+"\">");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_0+"\">"+constantdefine.POLICYMENU_0+"</option>");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_1+"\">"+constantdefine.POLICYMENU_1+"</option>");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_2+"\">"+constantdefine.POLICYMENU_2+"</option>");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_3+"\">"+constantdefine.POLICYMENU_3+"</option>");
           shtml.append("</select>");
       }else if(discid.equals(constantdefine.POLICYOBJTYCODE)){//ҵ�����        
	        shtml.append("<select id=\""+hname+"\" class =\""+hclass+"\">");
	        shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_0+"\">"+constantdefine.POLICYOBJTY_0+"</option>");
	        shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_1+"\">"+constantdefine.POLICYOBJTY_1+"</option>");
	        shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_2+"\">"+constantdefine.POLICYOBJTY_2+"</option>");
	        shtml.append("</select>");
      }else{//���ݿ��ֵ�
        String sql = "select dictionary_id,item from sys_t_dictionary  " +
                "where status = '1' and dictsort_id = '"+discid+"' order by sequence";
        
        //log.debug(sql);
        shtml.append("<select id=\""+hname+"\" class =\""+hclass+"\">");
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //���ò���
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
              id = rs.getString("dictionary_id");
              name = rs.getString("item");
              shtml.append("<option value=\""+id+"\">"+name+"</option>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        shtml.append("</select>");
      }    
      //log.debug(shtml);
      return shtml;
    }
}
