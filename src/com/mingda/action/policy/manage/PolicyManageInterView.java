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
	 * 获取家庭走访记录信息
	 * @param viewid
	 * @return
	 */
	public String getInterviewIdeaItem(String viewid){
		//政策业务查询处理类
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
		//
		JSONArray array = new JSONArray();      //定义JSON数组
        String sql = "select interview_id,family_id,a.organization_id,b.fullname,person,type,result,viewdt,operid,operdt " +
        		"from biz_t_interview a,sys_t_organization b " +
        		"where a.organization_id = b.organization_id and interview_id = '"+viewid+"'";   //定义SQL语句
      	//log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //遍历结果集，给JSON数组中加入JSONObject
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("iid", rs.getString("interview_id"));
                obj.put("ifmid", rs.getString("family_id"));
                obj.put("ideptname", rs.getString("fullname"));
                obj.put("iperson", rs.getString("person"));
                obj.put("itype", rs.getString("type"));
                obj.put("iresult", rs.getString("result"));
                //日期格式化
                String tempdt = rs.getString("viewdt");
                tempdt = policymanagecheckquery.getformatdt(tempdt);
                obj.put("idt", tempdt);
                array.add(obj);
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return array.toString();
	}
	/**
	 * 设置家庭走访记录
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
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
            pstmt.execute();              //执行            
            conn.commit();                      //关闭
            srep = "添加走访记录操作成功!";
        } catch (SQLException e) {
        	try {
        		srep = "添加走访记录操作失败!";
	            conn.rollback();
	        } catch (SQLException e1) {          
	            e1.printStackTrace();
	        }
        } finally {
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
		return srep;
	}
}
