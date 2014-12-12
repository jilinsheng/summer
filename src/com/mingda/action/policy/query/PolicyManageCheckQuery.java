package com.mingda.action.policy.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;

public class PolicyManageCheckQuery {
	static Logger log = Logger.getLogger(PolicyManageCheckQuery.class);
	  /**
	   * ��ʽ������
	   * @param sdt
	   * @return
	   */
	  public String getformatdt(String sdt){
	    String srep = "";
	    Date date = null;    
	    SimpleDateFormat oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    if (sdt == null || sdt.equals("")) {
	    } else {
	      try {
	        date = (Date) oformat.parse(sdt);
	      } catch (ParseException e) {        
	        e.printStackTrace();
	      }
	      oformat = new SimpleDateFormat("yyyy-MM-dd");
	      srep = oformat.format(date);
	    }
	    return srep;
	  }
	  /**
	   * ��ʽ��ʱ��
	   * @param stime
	   * @return
	   */
	  public String getformattime(String stime){
	    String srep = "";
	    Date date = null;    
	    SimpleDateFormat oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    if (stime == null || stime.equals("")) {
	    } else {
	      try {
	        date = (Date) oformat.parse(stime);
	      } catch (ParseException e) {        
	        e.printStackTrace();
	      }
	      oformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      srep = oformat.format(date);
	    }
	    return srep;
	  }
    /**
     * ��ҵ����ȡ��ҵ������
     * @param name
     * @return
     */
    public String getPolicyNameFromId(String id) {
        String srep = "";
        String sql = "select policy_id,name from doper_t_policy where policy_id = '" + id + "'";   //����SQL���
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              srep = rs.getString("name");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
	/**
     * ��ҵ����ȡ��ҵ�����(0����1ũ��)
     * @param pid
     * @return
     */
    public String getPolicyObjTypeFromId(String pid){
    	String srep = "0";
    	String sql = "select objtype from doper_t_policy where flag = '1' and policy_id = '"+pid+"'";
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
            	//
            	srep = rs.getString("objtype");            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
    	return srep;
    }
    /**
     * ��ҵ������ȡ��ҵ����
     * @param name
     * @return
     */
    public String getPolicyIdFromName(String name) {
        String srep = "";
        String sql = "select policy_id,name from doper_t_policy where name = '" + name + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              srep = rs.getString("policy_id");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ȡ��ҵ����������Ϊ��һ�����Ļ�������
     * @param id
     * @return
     */
    public String getPolicyOneFlowFromId(String id) {
    	String srep = "0",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
    	//��������
        ConstantDefine constantdefine = new ConstantDefine();
        //
    	String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + id + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");
            	if(appstate1.equals("1")){srep = constantdefine.POLICYQUERYCODE_5;break; }
            	if(appstate2.equals("1")){srep = constantdefine.POLICYQUERYCODE_4;break; }
            	if(appstate3.equals("1")){srep = constantdefine.POLICYQUERYCODE_3;break; }
            	if(appstate4.equals("1")){srep = constantdefine.POLICYQUERYCODE_2;break; }
            	if(appstate5.equals("1")){srep = constantdefine.POLICYQUERYCODE_1;break; }
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }	
    	return srep;
    }
    /**
     * ȡ��ҵ����������Ϊ��������Ļ�������
     * @param id
     * @return
     */
    public String getPolicyEndFlowFromId(String id) {
    	String srep = "0",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
    	//��������
        ConstantDefine constantdefine = new ConstantDefine();
        //
    	String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + id + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");
            	if(appstate5.equals("1")){srep = constantdefine.POLICYQUERYCODE_1;break; }
            	if(appstate4.equals("1")){srep = constantdefine.POLICYQUERYCODE_2;break; }
            	if(appstate3.equals("1")){srep = constantdefine.POLICYQUERYCODE_3;break; }
            	if(appstate2.equals("1")){srep = constantdefine.POLICYQUERYCODE_4;break; }
            	if(appstate1.equals("1")){srep = constantdefine.POLICYQUERYCODE_5;break; }
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }	
    	return srep;
    }
    /**
     * ǰ��
     * ȡ������������һ����������
     * ���ػ�������[5����4�ֵ�3��2��1ʡ]
     * ����0��˵��������������������
     * @param id
     * @param empid
     * @return
     */
    public String getPolicyNextFlowFromId(String id,String empid) {
        String srep = "0",empdepth = "",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
        // ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
        //��������
        ConstantDefine constantdefine = new ConstantDefine();
        
        //
	    empdepth = tableinfoquery.getempdepth(empid);
	    srep = empdepth;
	    //
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + id + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");            	
            	if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){
            		if(appstate2.equals("0")){
            			if(appstate3.equals("0")){
            				if(appstate4.equals("0")){
            					if(appstate5.equals("0")){
            						
                        		}else{
                        			srep = constantdefine.POLICYQUERYCODE_1;
                        			break;
                        		}
                    		}else{
                    			srep = constantdefine.POLICYQUERYCODE_2;
                    			break;
                    		}
                		}else{
                			srep = constantdefine.POLICYQUERYCODE_3;
                			break;
                		}
            		}else{
            			srep = constantdefine.POLICYQUERYCODE_4;
            			break;
            		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){
	            	 if(appstate3.equals("0")){
         				if(appstate4.equals("0")){
         					if(appstate5.equals("0")){
         						
                     		}else{
                     			srep = constantdefine.POLICYQUERYCODE_1;
                     			break;
                     		}
                 		}else{
                 			srep = constantdefine.POLICYQUERYCODE_2;
                 			break;
                 		}
             		}else{
             			srep = constantdefine.POLICYQUERYCODE_3;
             			break;
             		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){
	            	 if(appstate4.equals("0")){
      					if(appstate5.equals("0")){
      						
                  		}else{
                  			srep = constantdefine.POLICYQUERYCODE_1;
                  			break;
                  		}
              		}else{
              			srep = constantdefine.POLICYQUERYCODE_2;
              			break;
              		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){
	            	if(appstate5.equals("0")){
	            		
               		}else{
               			srep = constantdefine.POLICYQUERYCODE_1;
               			break;
               		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){
	            	 	srep = constantdefine.POLICYQUERYCODE_1;
            			break;
	             }
              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ����
     * ȡ������������һ����������
     * ���ػ�������[5����4�ֵ�3��2��1ʡ]
     * ����0��˵��������������������
     * @param id
     * @param empid
     * @return
     */
    public String getPolicyBackFlowFromId(String id,String empid) {
        String srep = "0",empdepth = "",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
        // ���ѯ������
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
        //��������
        ConstantDefine constantdefine = new ConstantDefine();
        
        //
	    empdepth = tableinfoquery.getempdepth(empid);
	    srep = empdepth;
	    //
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + id + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");            	
            	if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){
            		if(appstate4.equals("0")){            			
            			if(appstate3.equals("0")){
            				if(appstate2.equals("0")){
            					if(appstate1.equals("0")){
            						srep = constantdefine.POLICYQUERYCODE_1;
            						break;
                        		}else{
                        			srep = constantdefine.POLICYQUERYCODE_5;
                        			break;
                        		}
                    		}else{
                    			srep = constantdefine.POLICYQUERYCODE_4;
                    			break;
                    		}
                		}else{
                			srep = constantdefine.POLICYQUERYCODE_3;
                			break;
                		}
            		}else{
            			srep = constantdefine.POLICYQUERYCODE_2;
            			break;
            		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){
	            	 if(appstate3.equals("0")){
         				if(appstate2.equals("0")){
         					if(appstate1.equals("0")){
         						srep = constantdefine.POLICYQUERYCODE_2;
         						break;
                     		}else{
                     			srep = constantdefine.POLICYQUERYCODE_5;
                     			break;
                     		}
                 		}else{
                 			srep = constantdefine.POLICYQUERYCODE_4;
                 			break;
                 		}
             		}else{
             			srep = constantdefine.POLICYQUERYCODE_3;
             			break;
             		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){
	            	 if(appstate2.equals("0")){
      					if(appstate1.equals("0")){
      						srep = constantdefine.POLICYQUERYCODE_3;
      						break;
                  		}else{
                  			srep = constantdefine.POLICYQUERYCODE_5;
                  			break;
                  		}
              		}else{
              			srep = constantdefine.POLICYQUERYCODE_4;
              			break;
              		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){
	            	if(appstate1.equals("0")){
	            		srep = constantdefine.POLICYQUERYCODE_4;
	            		break;
               		}else{
               			srep = constantdefine.POLICYQUERYCODE_5;
               			break;
               		}
	             }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){
	            	 	srep = constantdefine.POLICYQUERYCODE_5;
	            		break;
	             }
              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
	 * ȡ�����ڰ����ҵ��
	 * �������롢����
	 * ��ʶ�������ʶΪδ�������0����1δ����2
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String getPolicyAllCheckAccOptaccid(String pid,String empid){
		String  srep = "2",sflag = "";
    	String empdepid = "";
	 	//
    	// ���ѯ������
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//
    	String sql = "select optacc_id,accflag from biz_t_optacc " +
    			"where policy_id = '"+pid+"' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 " +
    							"order by accflag asc";
        //
    	//log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) { 
            	sflag = rs.getString("accflag");            	
            	if(sflag.equals("0")){
            		//����һ��δ�������ļ�¼ 
            		srep = rs.getString("optacc_id");
            		break;
            	}
            	//������Ҫ������������ҵ��            	
            	break;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }        
    	return srep;
    }  
    /**
     * ȡ��ҵ����������ԭ��(����ԭ����,�Ÿ���)
     * @param checkideapid
     * @return
     */
    public String getPolicyCheckMoreTypes(String checkideapid) {
    	String srep = "";
    	String sql = "",stemp = "";	    	
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	//
    	sql = "select reqtype from biz_t_optchecktype where optcheckidea_id = '"+checkideapid+"'";   //����SQL���
    	
       	//log.debug(sql);
        
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	stemp = rs.getString("reqtype");
            	stemp = tableinfoquery.getdiscionaryfromid(stemp);
            	if(srep.equals("")){
            		srep = stemp;
            	}else{
            		srep += ","+stemp;
            	}            	
            }
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
    /**
     * ��ҵ���źͻ�������ȡ���������̱�ʶ
     * @param pid
     * @param deptpath
     * @return
     */
    public String getPolicyFlowFlagFromId(String pid,String deptpath) {
        String srep = "0";
        //��������
        ConstantDefine constantdefine = new ConstantDefine();
        
        String dpath = deptpath;
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + pid + "'";   //����SQL���
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              if(dpath.equals(constantdefine.POLICYQUERYCODE_5)){
                srep = rs.getString("appstate1");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_4)){
                srep = rs.getString("appstate2");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_3)){
                srep = rs.getString("appstate3");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_2)){
                srep = rs.getString("appstate4");
              }else if(dpath.equals(constantdefine.POLICYQUERYCODE_1)){
                srep = rs.getString("appstate5");
              }
              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ȡ�ø�ҵ��ü�ͥ��������������ͥID
     * @param empid
     * @param pid
     * @param fmid
     * @return
     */
    public String getPolicyIsRmCheckId(String empid,String pid,String fmid) {
    	String srep = "";
        String empdepth = "",nextdepth = "",backdepth = "";
        String empcheckflag = "",nextcheckflag = "",newresult = "",remresult = "";
        // ���ѯ������
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	
	    //��������
        ConstantDefine constantdefine = new ConstantDefine();
        //
        empdepth = tableinfoquery.getempdepth(empid);
        backdepth = getPolicyBackFlowFromId(pid, empid);
        nextdepth = getPolicyNextFlowFromId(pid, empid);
        //
        newresult = constantdefine.POLICYCHECKCODE_NEW;
        remresult = constantdefine.POLICYCHECKCODE_RENEW;
        //
        if(empdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	empcheckflag = "checkflag1";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	empcheckflag = "checkflag2";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	empcheckflag = "checkflag3";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	empcheckflag = "checkflag4";
        }else if(empdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	empcheckflag = "checkflag5";
        }
        //
        //
        if(nextdepth.equals(constantdefine.POLICYQUERYCODE_5)){
        	nextcheckflag = "checkflag1";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_4)){
        	nextcheckflag = "checkflag2";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_3)){
        	nextcheckflag = "checkflag3";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_2)){
        	nextcheckflag = "checkflag4";
        }else if(nextdepth.equals(constantdefine.POLICYQUERYCODE_1)){
        	nextcheckflag = "checkflag5";
        }
        //
        String stemp = "";
        String sql = "select optcheck_id from biz_t_optcheck " +
        		"where policy_id = '" + pid + "' and family_id = '" + fmid + "' ";
        //
		if(empdepth.equals(nextdepth)){
			stemp = "and (" +
							"(ifover = '"+empdepth+"' and ("+empcheckflag+" <> '"+newresult+"')) " +
						")";   //����SQL���
			sql = sql + stemp;
		}else{
			stemp = "and (" +
							"(ifover = '"+empdepth+"' and ("+empcheckflag+" <> '"+newresult+"')) " +
							"or " +
							"(ifover = '"+nextdepth+"' and ("+nextcheckflag+" = '"+newresult+"' or "+nextcheckflag+" = '"+remresult+"'))" +
						")";   //����SQL���
			sql = sql + stemp;
		}
        				
        //log.debug(sql);
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("optcheck_id");   
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        return srep;
    }
    /**
     * ����ҵ��������ѡ��
     * 0��������[��]1������û��[��]
     * @param sname
     * @param stype
     * @return
     */
    public StringBuffer getPolicyChoice(String sname,String stype){
      StringBuffer shtml= new StringBuffer("");
      String id = "",name = "";
      
      //��������
      ConstantDefine constantdefine = new ConstantDefine();
        
      String sql = "select policy_id, name from doper_t_policy where flag = '1' order by policy_id ";
      
      //log.debug(sql);
      shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
      	if(stype.equals("0")){//��������[��]
      		//[��ѡ]    ѡ��
            id = constantdefine.NOTSELECT_ID;
            name = constantdefine.NOTSELECT_NAME;
            shtml.append("<option value=\""+id+"\">"+name+"</option>");
     	}else if(stype.equals("1")){//������û��[��]
     		
     	}      	
        //
        Connection conn = null;                 //����Connection����
        PreparedStatement pstmt = null;         //����PreparedStatement����
        ResultSet rs = null;                    //����ResultSet����
        try {
            conn = DBUtils.getConnection();     //��ȡ���ݿ�����
            pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
            //����
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
              id = rs.getString("policy_id");
              name = rs.getString("name");
              shtml.append("<option value=\""+id+"\">"+name+"</option>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //�رս����
            DBUtils.close(pstmt);               //�ر�PreparedStatement
            //DBUtils.close(conn);                //�ر�����
        }
        //
      shtml.append("</select>");
      //log.debug(shtml);
      return shtml;
    }
    /**
     * ȡ��ҵ��������׼ѡ��������
     * @param sname
     * @return
     */
    public StringBuffer getPolicyCheckChoice(String sname){
      StringBuffer shtml= new StringBuffer("");
      String t1 = "",t2 = "",t3 = "",t4 = "",t5 = "",t6 = "",t7 = "",t8= "",t9 = "",t10 = "";
      String t1n = "",t2n = "",t3n = "",t4n = "",t5n = "",t6n = "",t7n = "",t8n = "",t9n = "",t10n = "";
      //��������
        ConstantDefine constantdefine = new ConstantDefine();
        t1 = constantdefine.POLICYNEWCHECKCODE_NOTALL;
        t2 = constantdefine.POLICYNEWCHECKCODE_ALL;
        t3 = constantdefine.POLICYNEWCHECKCODE_NEW;
        t4 = constantdefine.POLICYNEWCHECKCODE_OLE;
        t5 = constantdefine.POLICYNEWCHECKCODE_TOP;
        t6 = constantdefine.POLICYNEWCHECKCODE_BOM;        
        t7 = constantdefine.POLICYNEWCHECKCODE_REM;
        t8 = constantdefine.POLICYNEWCHECKCODE_STOP;
        t1n = constantdefine.POLICYNEWCHECK_NOTALL;
        t2n = constantdefine.POLICYNEWCHECK_ALL;
        t3n = constantdefine.POLICYNEWCHECK_NEW;
        t4n = constantdefine.POLICYNEWCHECK_OLE;
        t5n = constantdefine.POLICYNEWCHECK_TOP;
        t6n = constantdefine.POLICYNEWCHECK_BOM;
        t7n = constantdefine.POLICYNEWCHECK_REM;
        t8n = constantdefine.POLICYNEWCHECK_STOP;
        
      shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
        shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
        shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        shtml.append("<option value='"+t6+"'>"+t6n+"</option>");        
        shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        shtml.append("<option value='"+t8+"'>"+t8n+"</option>");
          
      shtml.append("</select>");
      return shtml;
    }
    /**
     * ȡ��ҵ����������������ѡ��
     0��������[��]1������û��[��]
     * @param sname
     * @param pid
     * @param stype
     * @return
     */
    public StringBuffer getPolicyFlowChoice(String sname,String pid,String stype){
      StringBuffer shtml= new StringBuffer("");
      String t1 = "",t2 = "",t3 = "",t4 = "",t5 = "",t6 = "",t7 = "",t8 = "";
      String t1n = "",t2n = "",t3n = "",t4n = "",t5n = "",t6n = "",t7n = "",t8n = "";
      String tflowflag = "0";
      //��������
        ConstantDefine constantdefine = new ConstantDefine();
        t1 = constantdefine.POLICYQUERYCODE_11; 
        t2 = constantdefine.POLICYQUERYCODE_10; 
        t3 = constantdefine.POLICYQUERYCODE_T;
        t4 = constantdefine.POLICYQUERYCODE_5;        
        t5 = constantdefine.POLICYQUERYCODE_4;
        t6 = constantdefine.POLICYQUERYCODE_3;
        t7 = constantdefine.POLICYQUERYCODE_2;
        t8 = constantdefine.POLICYQUERYCODE_1;        
        t1n = constantdefine.POLICYQUERY_11; 
        t2n = constantdefine.POLICYQUERY_10; 
        t3n = constantdefine.POLICYQUERY_T;
        t4n = constantdefine.POLICYQUERY_5;        
        t5n = constantdefine.POLICYQUERY_4;
        t6n = constantdefine.POLICYQUERY_3;
        t7n = constantdefine.POLICYQUERY_2;
        t8n = constantdefine.POLICYQUERY_1;
        
        
      shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
      	if(stype.equals("0")){//��������[��]
     		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
     	}else if(stype.equals("1")){//������û��[��]
     		
     	}  
        shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        //
        tflowflag = getPolicyFlowFlagFromId(pid,constantdefine.POLICYQUERYCODE_5);
        if(tflowflag==null || tflowflag.equals("")){
          
        }else{
          if(tflowflag.equals("1")){//��Ҫ��������
            shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
          }
        }
        tflowflag = getPolicyFlowFlagFromId(pid,constantdefine.POLICYQUERYCODE_4);
        if(tflowflag==null || tflowflag.equals("")){
          
        }else{
          if(tflowflag.equals("1")){//��Ҫ�ֵ�����
            shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
          }
        }
        tflowflag = getPolicyFlowFlagFromId(pid,constantdefine.POLICYQUERYCODE_3);
        if(tflowflag==null || tflowflag.equals("")){
          
        }else{
          if(tflowflag.equals("1")){//��Ҫ������
            shtml.append("<option value='"+t6+"'>"+t6n+"</option>");
          }
        }
        tflowflag = getPolicyFlowFlagFromId(pid,constantdefine.POLICYQUERYCODE_2);
        if(tflowflag==null || tflowflag.equals("")){
          
        }else{
          if(tflowflag.equals("1")){//��Ҫ��(��)����
            shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
          }
        }
        tflowflag = getPolicyFlowFlagFromId(pid,constantdefine.POLICYQUERYCODE_1);
        if(tflowflag==null || tflowflag.equals("")){
          
        }else{
          if(tflowflag.equals("1")){//��Ҫʡ������
            shtml.append("<option value='"+t8+"'>"+t8n+"</option>");
          }
        }
      shtml.append("</select>");
      return shtml;
    }
    /**
     * ȡ��ҵ���������������ѡ��
     * 0��������[��]1������û��[��]
     * @param sname
     * @param stype
     * @return
     */
    public StringBuffer getPolicyResultChoice(String sname,String stype){
    	StringBuffer shtml= new StringBuffer("");
    	String t1 = "",t2 = "",t3 = "",t4 = "",t5 = "",t6 = "",t7 = "",t8 = "",t9 = "",t10 = "";
    	String t1n = "",t2n = "",t3n = "",t4n = "",t5n = "",t6n = "",t7n = "",t8n = "",t9n = "",t10n = "";
    	//��������
        ConstantDefine constantdefine = new ConstantDefine();
        t1 = constantdefine.POLICYCHECKCODE_NOTALL; 
        t2 = constantdefine.POLICYCHECKCODE_ALL; 
        t3 = constantdefine.POLICYCHECKCODE_NEW;        
        t4 = constantdefine.POLICYCHECKCODE_OK;
        t5 = constantdefine.POLICYCHECKCODE_RENEW;
        t6 = constantdefine.POLICYCHECKCODE_MOVE;
        t7 = constantdefine.POLICYCHECKCODE_OLE;        
        t8 = constantdefine.POLICYCHECKCODE_STOP;
        t9 = constantdefine.POLICYCHECKCODE_REM;
        t10 = constantdefine.POLICYCHECKCODE_NOTOK;
        
        
        t1n = constantdefine.POLICYCHECK_NOTALL; 
        t2n = constantdefine.POLICYCHECK_ALL; 
        t3n = constantdefine.POLICYCHECK_NEW;        
        t4n = constantdefine.POLICYCHECK_OK;
        t5n = constantdefine.POLICYCHECK_RENEW;
        t6n = constantdefine.POLICYCHECK_MOVE;
        t7n = constantdefine.POLICYCHECK_OLE;        
        t8n = constantdefine.POLICYCHECK_STOP;
        t9n = constantdefine.POLICYCHECK_REM;
        t10n = constantdefine.POLICYCHECK_NOTOK;
        
      shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
	      if(stype.equals("0")){//��������[��]
	   		shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
	   	}else if(stype.equals("1")){//������û��[��]
	   		
	   	} 
        shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
        shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        shtml.append("<option value='"+t6+"'>"+t6n+"</option>");
        shtml.append("<option value='"+t7+"'>"+t7n+"</option>");
        shtml.append("<option value='"+t8+"'>"+t8n+"</option>");
        shtml.append("<option value='"+t9+"'>"+t9n+"</option>");
        shtml.append("<option value='"+t10+"'>"+t10n+"</option>");
      shtml.append("</select>");
      return shtml;
    }
    /**
     * ȡ������ҵ��ѡ��������
     * @param sname
     * @return
     */
    public StringBuffer getPolicyFlagChoice(String sname){
      StringBuffer shtml= new StringBuffer("");
      String t1 = "",t2 = "",t3 = "",t4 = "",t5 = "",t6 = "";
      String t1n = "",t2n = "",t3n = "",t4n = "",t5n = "",t6n = "";
      //��������
        ConstantDefine constantdefine = new ConstantDefine();
        t1 = constantdefine.POLICYFLAGCODE_NOTALL;
        t2 = constantdefine.POLICYFLAGCODE_ALL;
        t3 = constantdefine.POLICYFLAGCODE_IS;
        t4 = constantdefine.POLICYFLAGCODE_NOT;
        t5 = constantdefine.POLICYFLAGCODE_NEW;
        t6 = constantdefine.POLICYFLAGCODE_STOP;
        t1n = constantdefine.POLICYFLAG_NOTALL;
        t2n = constantdefine.POLICYFLAG_ALL;
        t3n = constantdefine.POLICYFLAG_IS;
        t4n = constantdefine.POLICYFLAG_NOT;
        t5n = constantdefine.POLICYFLAG_NEW;
        t6n = constantdefine.POLICYFLAG_STOP;
      shtml.append("<select id=\""+sname+"\" style = \"width:100%;font-size:12px\">");
        shtml.append("<option value='"+t1+"'>"+t1n+"</option>");
        shtml.append("<option value='"+t2+"'>"+t2n+"</option>");
        shtml.append("<option value='"+t3+"'>"+t3n+"</option>");
        shtml.append("<option value='"+t4+"'>"+t4n+"</option>");
        shtml.append("<option value='"+t5+"'>"+t5n+"</option>");
        shtml.append("<option value='"+t6+"'>"+t6n+"</option>");
      shtml.append("</select>");
      return shtml;
    }
    /**
     * ȡ������LI
     * @param sname
     * @param smode
     * @return
     */
    public StringBuffer getPolicyAutoUlLi(String sname,String smode){
        StringBuffer shtml= new StringBuffer("");
        String t1 = "",t2 = "",t3 = "",t4 = "",t5 = "";
        String t1n = "",t2n = "",t3n = "",t4n = "",t5n = "";
        String sli = "li";
        //��������
        ConstantDefine constantdefine = new ConstantDefine();
       
        //
        t1 = constantdefine.POLICYAOUTCHECKCODE_NEWCHECK;
        t2 = constantdefine.POLICYAOUTCHECKCODE_OLECHECK;
        t3 = constantdefine.POLICYAOUTCHECKCODE_MONEY;
        t4 = constantdefine.POLICYAOUTCHECKCODE_NOMONEY;
        t5 = constantdefine.POLICYAOUTCHECKCODE_REMCHECK;
        //
        t1n = constantdefine.POLICYAOUTCHECK_NEWCHECK;
        t2n = constantdefine.POLICYAOUTCHECK_OLECHECK;
        t3n = constantdefine.POLICYAOUTCHECK_MONEY;
        t4n = constantdefine.POLICYAOUTCHECK_NOMONEY;
        t5n = constantdefine.POLICYAOUTCHECK_REMCHECK;
        
        //
        shtml.append("<ul id='"+sname+"' class='menuauto'>");
        if(smode.equals("check")){
        	shtml.append("<li id='"+sli+t1+"' class='default'> "+t1n+" </li>");
            shtml.append("<li id='"+sli+t2+"' class='default'> "+t2n+" </li>");  
        }else if(smode.equals("result")){
        	shtml.append("<li id='"+sli+t3+"' class='default'> "+t3n+" </li>");
            shtml.append("<li id='"+sli+t4+"' class='default'> "+t4n+" </li>"); 
            shtml.append("<li id='"+sli+t5+"' class='default'> "+t5n+" </li>"); 
        }              
        shtml.append("</ul>");
        return shtml;      
    }
    /**
     * ȡ��������׼LI
     * @param sname
     * @param smode
     * @return
     */
    public StringBuffer getPolicyCheckUlLi(String sname,String smode,String sinfomode){
		StringBuffer shtml= new StringBuffer("");
		String t1 = "",t2 = "";
		String t11 = "",t12= "",t13 = "",t14 = "",t15 = "",t16 = "",t17 = "";
		String t21 = "",t22 = "",t23 = "",t24 = "",t25 = "",t26 = "";
		String t31 = "",t32 = "",t33 = "",t34 = "";
		String t41 = "";
		//
		String tn1 = "",tn2 = "";
		String tn11 = "",tn12= "",tn13 = "",tn14 = "",tn15 = "",tn16 = "",tn17 = "";
		String tn21 = "",tn22 = "",tn23 = "",tn24 = "",tn25 = "",tn26 = "";
		String tn31 = "",tn32 = "",tn33 = "",tn34 = "";
		String tn41 = "";
		//��������
        ConstantDefine constantdefine = new ConstantDefine();
        t1 = constantdefine.POLICYNEWCHECKCODE_NEWNOCHECK;       
        t2 = constantdefine.POLICYNEWCHECKCODE_NEWRECHECK;        
        
        tn1 = constantdefine.POLICYNEWCHECK_NEWNOCHECK;       
        tn2 = constantdefine.POLICYNEWCHECK_NEWRECHECK;
        
        //
        t11 = constantdefine.POLICYNEWCHECKCODE_NEWTOP;
        t12 = constantdefine.POLICYNEWCHECKCODE_NEWBOM;
        t13 = constantdefine.POLICYNEWCHECKCODE_NEWOLE;
        t14 = constantdefine.POLICYNEWCHECKCODE_NEWSTOP;
        t15 = constantdefine.POLICYNEWCHECKCODE_NEWREM; 
        t16 = constantdefine.POLICYNEWCHECKCODE_NEWREMCHECK;        
        t17 = constantdefine.POLICYNEWCHECKCODE_NEWNOMATCH;
        
        tn11 = constantdefine.POLICYNEWCHECK_NEWTOP;
        tn12 = constantdefine.POLICYNEWCHECK_NEWBOM;
        tn13 = constantdefine.POLICYNEWCHECK_NEWOLE;
        tn14 = constantdefine.POLICYNEWCHECK_NEWSTOP;
        tn15 = constantdefine.POLICYNEWCHECK_NEWREM;
        tn16 = constantdefine.POLICYNEWCHECK_NEWREMCHECK;        
        tn17 = constantdefine.POLICYNEWCHECK_NEWNOMATCH;
        //
        t21 = constantdefine.POLICYNEWRESULTCODE_NEW;
        t22 = constantdefine.POLICYNEWRESULTCODE_OLE;
        t23 = constantdefine.POLICYNEWRESULTCODE_TOP;
        t24 = constantdefine.POLICYNEWRESULTCODE_BOM;
        t25 = constantdefine.POLICYNEWRESULTCODE_MOVE;
        t26 = constantdefine.POLICYNEWRESULTCODE_REM;
        //
        tn21 = constantdefine.POLICYNEWRESULT_NEW;
        tn22 = constantdefine.POLICYNEWRESULT_OLE;
        tn23 = constantdefine.POLICYNEWRESULT_TOP;
        tn24 = constantdefine.POLICYNEWRESULT_BOM;
        tn25 = constantdefine.POLICYNEWRESULT_MOVE;
        tn26 = constantdefine.POLICYNEWRESULT_REM;       
        //
        t31 = constantdefine.POLICYNEWRESULTCODE_STOP;
        t32 = constantdefine.POLICYNEWRESULTCODE_DEL;  
        t33 = constantdefine.POLICYNEWRESULTCODE_NOMATCH;
        t34 = constantdefine.POLICYNEWRESULTCODE_MATCHNORES;
        //
        tn31 = constantdefine.POLICYNEWRESULT_STOP;
        tn32 = constantdefine.POLICYNEWRESULT_DEL;       
        tn33 = constantdefine.POLICYNEWRESULT_NOMATCH;
        tn34 = constantdefine.POLICYNEWRESULT_MATCHNORES;
               
        //
        t41 = constantdefine.POLICYNEWRESULTCODE_REMCHECK;
        //
        tn41 = constantdefine.POLICYNEWRESULT_REMCHECK;
        //
        String sli = "li";
        //
        shtml.append("<ul id='"+sname+"' class='menu'>");
        if(smode.equals("check")){
        	if(sinfomode.equals("infocheck")){
        		shtml.append("<li id='"+sli+t1+"' class='default'> "+tn1+" </li>");
            	shtml.append("<li id='"+sli+t2+"' class='default'> "+tn2+" </li>");           	
        	}else if(sinfomode.equals("inforesult")){
        		shtml.append("<li id='"+sli+t11+"' class='default'> "+tn11+" </li>");
            	shtml.append("<li id='"+sli+t12+"' class='default'> "+tn12+" </li>");  
            	shtml.append("<li id='"+sli+t13+"' class='default'> "+tn13+" </li>");
            	shtml.append("<li id='"+sli+t14+"' class='default'> "+tn14+" </li>");
            	shtml.append("<li id='"+sli+t15+"' class='default'> "+tn15+" </li>");
            	shtml.append("<li id='"+sli+t16+"' class='default'> "+tn16+" </li>");
            	shtml.append("<li id='"+sli+t17+"' class='default'> "+tn17+" </li>");
        	}        	
        }else if(smode.equals("result")){
        	if(sinfomode.equals("infocheck")){
        		shtml.append("<li id='"+sli+t21+"' class='default'> "+tn21+" </li>");
            	shtml.append("<li id='"+sli+t22+"' class='default'> "+tn22+" </li>");  
            	shtml.append("<li id='"+sli+t23+"' class='default'> "+tn23+" </li>");
            	shtml.append("<li id='"+sli+t24+"' class='default'> "+tn24+" </li>");
            	shtml.append("<li id='"+sli+t25+"' class='default'> "+tn25+" </li>");
            	shtml.append("<li id='"+sli+t26+"' class='default'> "+tn26+" </li>");
        	}else if(sinfomode.equals("inforesult")){
        		shtml.append("<li id='"+sli+t31+"' class='default'> "+tn31+" </li>");
        		shtml.append("<li id='"+sli+t32+"' class='default'> "+tn32+" </li>");
        		shtml.append("<li id='"+sli+t33+"' class='default'> "+tn33+" </li>");
        		shtml.append("<li id='"+sli+t34+"' class='default'> "+tn34+" </li>");
        	}else if(sinfomode.equals("remcheck")){
        		shtml.append("<li id='"+sli+t41+"' class='default'> "+tn41+" </li>");      		
        	}        	
        }  
        shtml.append("</ul>");
        return shtml;      
    }   

    /**
	 * ����������ǲ��Ǹ��û�����
	 * @param ideaid
	 * @param empid
	 * @return
	 */
	public boolean existsEmpCheckIdea(String ideaid,String empid){
		boolean  brep = false;		
		//
		String sql = "select optcheckidea_id from biz_t_optcheckidea " +
					"where optcheckidea_id = '"+ideaid+"' and checkoper = '"+empid+"'";  //SQL
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				brep = true;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		return brep;
	}
	/**
	 * ���û�������һ���������
	 * @param checkid
	 * @param empid
	 * @return
	 */
	public boolean existsEmpCheckMoreIdea(String checkid,String empid){
		boolean  brep = false;
		int irow = 0;
		//
		String sql = "select optcheckidea_id from biz_t_optcheckidea " +
					"where status = '1' and optcheck_id = '"+checkid+"' and checkoper = '"+empid+"'";  //SQL
		//log.debug(sql);
		Connection conn = null;                 //����Connection����
		PreparedStatement pstmt = null;         //����PreparedStatement����
		ResultSet rs = null;                    //����ResultSet����
		try {
			conn = DBUtils.getConnection();     //��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql); //����sql����PreparedStatement
			//���ò���
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				irow ++;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //�رս����
		    DBUtils.close(pstmt);               //�ر�PreparedStatement
		    //DBUtils.close(conn);                //�ر�����
		}
		//��¼����1
		if(irow>1){
			brep = true;
		}
		return brep;
	}
}
