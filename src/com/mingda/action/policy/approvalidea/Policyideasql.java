package com.mingda.action.policy.approvalidea;

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
	 * 生成意见页面表格
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
  		shtml.append("<td height='25'>家庭编号</td>");        
        shtml.append("<td height='25'>姓名</td>");
        shtml.append("<td height='25'>上期救助金</td>");
        shtml.append("<td height='25'>拟发救助金</td>");
        shtml.append("<td height='25' width='120'>审批详情</td>");
        shtml.append("<td height='25' width='120'>业务相关信息</td>");
        shtml.append("</tr>");
        //=============================th
        //=============================tr
        //log.debug(sql);
	    Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        int irow = 0;
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            //
            while (rs.next()) {
            	//单双行
				int mathrow = irow%2;				
				if(mathrow == 1){//背景颜色
					shtml.append("<tr style = 'background: #F2F5F7;'>");						
				}else{
					shtml.append("<tr>");
				}
				String optid = rs.getString("optcheck_id");
				String fmid = rs.getString("family_id");				
				String fmno = rs.getString("familyno");				
				String mmid = rs.getString("member_id");
				//
				//家庭编号
				shtml.append("<td height='25' class='colValue pointer' title = '查看家庭信息' " +
						"onclick= \"infoaction('"+fmid+"')\">"+fmno+"</td>");
				shtml.append("<td height='25' class='colValue'>"+rs.getString("membername")+"</td>");
				shtml.append("<td height='25' class='colValue'>"+rs.getString("recheckmoney")+"</td>");
				//
				String money = rs.getString("checkmoney");
				//拟发救助金
				if("1".equals(jaccmode)){		//允许更新救助金
					if("1".equals(jonecheck)){		//第一级审批机构
						shtml.append("<td height='25' class='colValue'>" 
								+ "<input style = 'text-align: right;' type='text' size = '8' name = 'money[]' " 
								+ "value = '"+money+"' onchange = \"ChangMoneyRow(this,'"+optid+"')\">" 
								+ "</td>");
					}else{							//不是第一级审批机构
						shtml.append("<td height='25' class='colValue'>"+money+"</td>");
					}					
				}else{							//不允许更新救助金
					shtml.append("<td height='25' class='colValue'>"+money+"</td>");
				}
				shtml.append("<td height='25' class='colValue pointer'  " +
						"onclick= \"GetCheckIdeaFlow('"+fmid+"')\">审批详情</td>");
				shtml.append("<td height='25' class='colValue pointer'  " +
						"onclick= \"GetCheckPolicyInfosHtml('"+fmid+"','"+mmid+"')\">业务相关</td>");
				shtml.append("</tr>");
          		//
          		irow++;
            }
        } catch (SQLException e) {
            log.debug(e.toString());            
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        
        //=============================tr        
        shtml.append("</table>");
        //=============================================table
        return shtml;
	}
}
