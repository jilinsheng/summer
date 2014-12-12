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
 * 字典管理类
 * @author xiu
 *
 */
public class DictionaryManage {
	static Logger log = Logger.getLogger(DictionaryManage.class);
	
	public StringBuffer getDiscsHtml(String discid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",discname = "";
    	String sid = "",sname = "",sflag = "";
    	// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//字典名称
    	discname = tableinfoquery.getdiscfromdiscid(discid);
    		
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>["+discname+"]列表</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>名称</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select dictionary_id,item,status from sys_t_dictionary where dictsort_id = '"+discid+"' order by sequence";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//
            	sid = rs.getString("dictionary_id");
            	sname = rs.getString("item");
            	sflag = rs.getString("status");
                
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+sid+"','"+sname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+sflag+"'>"+sname+"</td>";
					
					if(sflag.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDiscStatus('" + sid + "','" + sname + "','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"updateDiscStatus('" + sid + "','" + sname + "','1')\">启用</button></td>";	
					}						
				html +="</tr>";
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"GetDiscItemHtml('')\">新增字典类型</button>";    
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
    		stitle = "[新增]";
    	}else{
    		mode = "editdisc";
    		//
        	sql = "select dictionary_id,item,status from sys_t_dictionary where dictionary_id = '" + id + "'";   //定义SQL语句
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
                	did = rs.getString("dictionary_id");
                	dname = rs.getString("item");
                	stitle = "["+dname+"]"; 
                	dflag = rs.getString("status");
                }
            } catch (SQLException e) {
                log.debug(e.toString());
            } finally {
                DBUtils.close(rs);                  //关闭结果集
                DBUtils.close(pstmt);               //关闭PreparedStatement
                //DBUtils.close(conn);                //关闭连接
            }
    	}
    	//
	    html = "<fieldset  class='list'>";
    	html += "<legend  class='legend'>"+stitle+"字典属性</legend>";
		html += "<table width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
		//
		//
		html +="<tr>";
			html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>名称</td>";
			html += "<td><input style = 'width:100%' type='text' id = 'dname' value = '"+dname+"'></input>";
			html += "<input type='text' id='did' style='display:none' value = '"+did+"'></input></td>";
		html +="</tr>";
		//
		
		//
		//
    	html +="</table>";
    	
    	if(mode.equals("adddisc")){//新增
	    	html += "<button class = 'btn' onclick=\"SaveDisc('"+mode+"')\">保存</button>"; 	   		
    	}else if(mode.equals("editdisc")){//编辑
    		//在用时可以设置
	    	if(dflag.equals("1")){	    		
	    		html += "<button class = 'btn' onclick=\"SaveDisc('"+mode+"')\">保存</button>";  
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
     * 更新字典类型
     * @param sname
     * @param sdiscid
     * @return
     */
	public String updateDisc(String mode,String did,String dname,String pdid) {
    	String srep = "",sid = "";
    	String sql = "";
    	//表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
    	if(existsDiscionary(dname,pdid)){
    		srep = "该名称已经存在!";
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
    	  
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement            
            pstmt.execute();          	//执行
            conn.commit();
            if("adddisc".equals(mode)){
            	srep = "添加字典值操作成功!";
            }else{
            	srep = "更新字典值操作成功!";
            }
        } catch (SQLException e) {
        	try {
        		if("adddisc".equals(mode)){
                	srep = "添加字典值操作失败!";
                }else{
                	srep = "更新字典值操作失败!";
                }
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
	/**
	 * 更新字典状态
	 * @param did
	 * @param status
	 * @return
	 */
	public String updateDiscStatus(String did,String status) {
    	String srep = "",sid = "";
    	String sql = "";
    	
    	sql = "update sys_t_dictionary set status = '"+status+"' where dictionary_id = '"+did+"'";
    	  
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement            
            pstmt.execute();          	//执行
            conn.commit();
            //
            if("0".equals(status)){
              srep = "停用字典类型操作成功!";
            }else{
              srep = "启用字典类型操作成功!";
            }
        } catch (SQLException e) {
        	try {
        		if("0".equals(status)){
                    srep = "停用字典类型操作失败!";
        		}else{
        			srep = "启用字典类型操作失败!";
        		} 
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
	/**
     * 添加字典值
     * @param sname
     * @param sdiscid
     * @return
     */
	public String addDiscionary(String sname,String sdiscid) {
    	String srep = "",sid = "";
    	//表查询处理类
      	TableInfoQuery tableinfoquery = new TableInfoQuery();
      	//
    	if(existsDiscionary(sname,sdiscid)){
    		srep = "该名称已经存在!";
    		return srep;
    	}
    	sid = tableinfoquery.getseqfromname("XDICTIONARY_ID");
        String sql = "insert into sys_t_dictionary " +
        		"(dictionary_id,dictsort_id,item,sequence,status,code) " +
        		"values " +
        		"("+sid+",'"+sdiscid+"','"+sname+"','"+sid+"','1','"+sid+"')";   
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement            
            pstmt.execute();          	//执行
            conn.commit();
            srep = "添加字典值操作成功!";
        } catch (SQLException e) {
        	try {
        		srep = "添加字典值操作失败!";
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
    /**
     * 是否存在字典值
     * @param sname
     * @param sdiscid
     * @return
     */
	public boolean existsDiscionary(String sname,String sdiscid) {
    	boolean brep = false;
        String sql = "select dictionary_id from sys_t_dictionary where dictsort_id = '"+sdiscid+"' and item = '"+sname+"'";   
        
        Connection conn = null;                 //声明Connection对象
	    PreparedStatement pstmt = null;         //声明PreparedStatement对象
	    ResultSet rs = null;                    //声明ResultSet对象
	    try {
	        conn = DBUtils.getConnection();     //获取数据库连接
	        pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
	        //设置参数
	        rs = pstmt.executeQuery();
	        while (rs.next()) {	
	        	//存在
	        	brep = true;
	        }
	    } catch (SQLException e) {
	        Log4jApp.logger(e.toString());
	    } finally {
	        DBUtils.close(rs);                  //关闭结果集
	        DBUtils.close(pstmt);               //关闭PreparedStatement
	        //DBUtils.close(conn);                //关闭连接
	    }		    
        return brep;
    }
	/**
	 * 取得字典下拉框选择
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
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		ResultSet rs = null;                    //声明ResultSet对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
		    //设置参数
		    rs = pstmt.executeQuery();
		    
		    while (rs.next()) {  
		    	tid = rs.getString("dictionary_id");
		    	tname = rs.getString("item");
		    	shtml.append("<option value=\""+tid+"\">"+tname+"</option>");
		    }
		} catch (SQLException e) {
		    log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		shtml.append("</select>");
		return shtml;
	}
	/**
   * 业务所有类型选择列表
   * @param hname
   * @param hclass
   * @param discid
   * @return
   */
    public StringBuffer getPolicySelect(String hname,String hclass,String discid){
      StringBuffer shtml= new StringBuffer("");
      String id = "",name = "";
      //常量定义
       ConstantDefine constantdefine = new ConstantDefine();
       if(discid.equals(constantdefine.POLICYMENUCODE)){//救助业务       
           shtml.append("<select id=\""+hname+"\" class =\""+hclass+"\">");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_0+"\">"+constantdefine.POLICYMENU_0+"</option>");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_1+"\">"+constantdefine.POLICYMENU_1+"</option>");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_2+"\">"+constantdefine.POLICYMENU_2+"</option>");
           shtml.append("<option value=\""+constantdefine.POLICYMENUCODE_3+"\">"+constantdefine.POLICYMENU_3+"</option>");
           shtml.append("</select>");
       }else if(discid.equals(constantdefine.POLICYOBJTYCODE)){//业务分类        
	        shtml.append("<select id=\""+hname+"\" class =\""+hclass+"\">");
	        shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_0+"\">"+constantdefine.POLICYOBJTY_0+"</option>");
	        shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_1+"\">"+constantdefine.POLICYOBJTY_1+"</option>");
	        shtml.append("<option value=\""+constantdefine.POLICYOBJTYCODE_2+"\">"+constantdefine.POLICYOBJTY_2+"</option>");
	        shtml.append("</select>");
      }else{//数据库字典
        String sql = "select dictionary_id,item from sys_t_dictionary  " +
                "where status = '1' and dictsort_id = '"+discid+"' order by sequence";
        
        //log.debug(sql);
        shtml.append("<select id=\""+hname+"\" class =\""+hclass+"\">");
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            //设置参数
            rs = pstmt.executeQuery();
            
            while (rs.next()) {  
              id = rs.getString("dictionary_id");
              name = rs.getString("item");
              shtml.append("<option value=\""+id+"\">"+name+"</option>");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        shtml.append("</select>");
      }    
      //log.debug(shtml);
      return shtml;
    }
}
