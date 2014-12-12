package com.mingda.action.info.classmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;

public class InfoClassManage {
	static Logger log = Logger.getLogger(InfoClassManage.class);
	/**
	 * 取得分类列表
	 * @param mode
	 * @return
	 */
    public StringBuffer getClasssHtml(String mode) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String sid = "",sname = "",sdesc = "",sflag = "",sfid = ""; 
    	//
    	String sql = "";
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>分类列表</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>名称</td>";
			html +=temp;
			temp ="<td height='23'>描述</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
		if(mode.equals("family")){
    		//家庭分类
    		sql = "select a.classtype_id,b.logicname,a.explains,a.status,b.field_id " +
    				"from sys_t_classtype a,sys_t_field b,sys_t_table c " +
    					"where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_FAMILYCLASS'";   //定义SQL语句
    	}else{
    		//成员分类
    		sql = "select a.classtype_id,b.logicname,a.explains,a.status,b.field_id " +
					"from sys_t_classtype a,sys_t_field b,sys_t_table c " +
						"where a.field_id = b.field_id and b.table_id = c.table_id and c.physicalname = 'INFO_T_MEMBERCLASS'";   //定义SQL语句
    	}
		//
		
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	//
            	sid = rs.getString("classtype_id");
            	sname = rs.getString("logicname");
            	sdesc = rs.getString("explains");
            	sflag = rs.getString("status");
            	sfid = rs.getString("field_id");
            	//
                
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+sid+"','"+sname+"','"+sfid+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					html += "<td height='23' class='colValue'>"+sdesc+"</td>";
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"delClass('" + sid + "','" + sfid + "')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"reeditClass('" + sid + "','" + sfid + "')\">启用</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        //
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"GetClassItemHtml('')\">新增分类</button>";    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * 取得分类属性
     * @param id
     * @return
     */
    @SuppressWarnings("unused")
	public StringBuffer getClassItemHtml(String id) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",stitle = "",temp = "",mode = "";
    	String sql = "";
    	String sid = "",sname = "",sdesc = "",sflag = "",sfid = ""; 
    	//    	
    	if(id.equals("")){
    		mode = "addclass";   		
    		stitle = "[新增]";
    	}else{
    		mode = "editclass"; 
    		//
    		sql = "select a.classtype_id,b.logicname,a.explains,a.status,b.field_id " +
			"from sys_t_classtype a,sys_t_field b,sys_t_table c " +
				"where a.field_id = b.field_id and b.table_id = c.table_id and a.classtype_id = '"+id+"'";   //定义SQL语句
    		
        	//log.debug(sql);
            Connection conn = null;                 //声明Connection对象
            PreparedStatement pstmt = null;         //声明PreparedStatement对象
            ResultSet rs = null;                    //声明ResultSet对象
            try {
                conn = DBUtils.getConnection();     //获取数据库连接
                pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	//
                	sid = rs.getString("classtype_id");
                	sname = rs.getString("logicname");
                	sdesc = rs.getString("explains");
                	sflag = rs.getString("status");
                	sfid = rs.getString("field_id");
                	//
                	stitle = "["+sname+"]";
                	//                	
                }
            } catch (SQLException e) {
                log.debug(e.toString());
            } finally {
                DBUtils.close(rs);                  //关闭结果集
                DBUtils.close(pstmt);               //关闭PreparedStatement
                DBUtils.close(conn);                //关闭连接
            }
    	}	
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"分类属性</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
		//		
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>分类名称</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'cname' value = '"+sname+"'></input></td>";
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>分类描述</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'cdesc' value = '"+sdesc+"'></input></td>";
		html +="</tr>";
		//
		//
    	html +="</table>";
    	
    	
    	if(mode.equals("addclass")){//新增
	    	html += "<button class = 'btn' onclick=\"SaveClass('"+mode+"')\">保存</button>"; 	   		
    	}else if(mode.equals("editclass")){//编辑
    		//在用时可以设置
	    	if(sflag.equals("1")){
	    		html += "<button class = 'btn' onclick=\"sqlaction()\">条件设置</button>";
	    		html += "<button class = 'btn' onclick=\"SaveClass('"+mode+"')\">保存</button>";
	    		//是否已经设置条件
	    		if(existsClassSql(sid)){
	    			html += "<button class = 'btn' onclick=\"refsysinfo()\">刷新系统数据</button>"; 
	    		}	    		 
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
     * 是否已经设置分类条件
     * @param parid
     * @return
     */
    public boolean existsClassSql(String parid){
		boolean  brep = false;
		
		String sql = "";
		sql = "select classtype_id, field_id,physql, logicsql from sys_t_classtype where  physql is not null and  classtype_id = '"+parid+"'";
		//		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
			//设置参数
			rs = pstmt.executeQuery(); 
			while (rs.next()){ 
				brep = true;
				break;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
}
