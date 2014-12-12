package com.mingda.action.policy.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;

public class PolicyManageChild {
	static Logger log = Logger.getLogger(PolicyManageChild.class);
	
	/**
     * 取得业务分类施保列表
     * @param pid
     * @return
     */
    public StringBuffer getPolicyChildsHtml() {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String cpid = "",cpropid = "",cpropname = "",cname = "",cdesc = "",csqltype = "",cmoneytype = "",cstatus = "";
    	//政策业务查询处理类
    	PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>分类施保属性列表</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>名称</td>";
			html +=temp;
			temp ="<td height='23'>所属业务</td>";
			html +=temp;
			temp ="<td height='23'>详细描述</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select policychild_id,policy_id,name,policydesc,sqltype,moneytype,status " +
        		"from doper_t_policychild order by policy_id";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cpid = rs.getString("policychild_id");
                cpropid = rs.getString("policy_id");
                cpropname = policymanagecheckquery.getPolicyNameFromId(cpropid);
                cname = rs.getString("name");
                cdesc = rs.getString("policydesc");
                csqltype = rs.getString("sqltype");
                cmoneytype = rs.getString("moneytype");
                cstatus = rs.getString("status");
                
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+cpid+"','"+cname+"','"+cmoneytype+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+cstatus+"'>"+cname+"</td>";
					html += "<td height='23' class='colValue'>"+cpropname+"</td>";
					html += "<td height='23' class='colValue'>"+cdesc+"</td>";
					if(cstatus.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildStatus('"+cpid+"','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildStatus('"+cpid+"','1')\">启用</button></td>";	
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
	    html += "<button class = 'btn' onclick=\"GetPolicyChildItemHtml('')\">新增分类施保</button>";    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * 取得分类施保属性
     * @param childpid
     * @return
     */
    public StringBuffer getPolicyChildItemHtml(String childpid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",mode = "";
    	String cpid = "",cpropid = "",cpropname = "",cname = "",cdesc = "",csqltype = "",cmoneytype = "",cstatus = "";
    	//
    	if(childpid.equals("")){
    		mode = "add";
    		cpropname = "[新增]";
    	}else{
    		mode = "edit";
    		cpropname = getPolicyChildNameFromId(childpid);
    		cpropname = "["+cpropname+"]";
    		//
            String sql = "select policychild_id,policy_id,name,policydesc,sqltype,moneytype,status " +
            		"from doper_t_policychild  where policychild_id = '" + childpid + "'";   //定义SQL语句
            
    		//
    		//log.debug(sql);
            Connection conn = null;                 //声明Connection对象
            PreparedStatement pstmt = null;         //声明PreparedStatement对象
            ResultSet rs = null;                    //声明ResultSet对象
            try {
                conn = DBUtils.getConnection();     //获取数据库连接
                pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	cpid = rs.getString("policychild_id");
                    cpropid = rs.getString("policy_id");
                    cname = rs.getString("name");
                    cdesc = rs.getString("policydesc");
                    csqltype = rs.getString("sqltype");
                    cmoneytype = rs.getString("moneytype");
                    cstatus = rs.getString("status");  	
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
    	html += "<legend  class='legend'>"+cpropname+"分类施保属性</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
	        //
			html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>标准计算方式</td>";
				html += "<td align='center' style='font-size:12px;'>";
				if(csqltype.equals("1")){//家庭	
					html += "<input type='radio' name='rd' id='rdf' checked />家庭计算";
					html += "<input type='radio' name='rd' id='rdm' />成员计算";
				}else if(csqltype.equals("2")){//成员
					html += "<input type='radio' name='rd' id='rdf' />家庭计算";
					html += "<input type='radio' name='rd' id='rdm' checked />成员计算";
				}else{
					html += "<input type='radio' name='rd' id='rdf' checked />家庭计算";
					html += "<input type='radio' name='rd' id='rdm' />成员计算";
				}
				html += "</td>";
			html +="</tr>";
			html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>所属业务</td>";				
				html += "<td><div id = 'policy'>";						
				    	temp = getPolicyChildChoiceHtml("policyselect",cpropid,cpid).toString();
					    html += temp;					    
			    html +=	"</div></td>";
			html +="</tr>";
			
	        html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>名称</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'cname' value = '"+cname+"'></input></td>";
	    	html +="</tr>";
	    	html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>详细描述</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'cdesc' value = '"+cdesc+"'></input></td>";
	    	html +="</tr>";
	    		    	
	    		    	    
	        //
	    	html +="</table>";
	    	
	    	if(mode.equals("add")){//新增
		    	html += "<button class = 'btn' onclick=\"SavePolicyChild('"+mode+"')\">保存</button>"; 	   		
	    	}else if(mode.equals("edit")){//编辑
	    		//在用时可以设置
		    	if(cstatus.equals("1")){
		    		html += "<button class = 'btn' onclick=\"SavePolicyChild('"+mode+"')\">保存</button>";
		    		html += "<button class = 'btn' onclick=\"GoMatchPage('next')\">下一步>>筛选标准设置</button>";  
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
     * 更新分类施保状态
     * @param Id
     * @param Status
     * @return
     */
    public String updatePoilcyChildStatus(String Id,String Status) {
        String srep = "";
          String sql = "update doper_t_policychild set status = '"+Status+"' where policychild_id = '"+Id+"'";  //更新档次状态SQL

          //log.debug(sql);
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(sql);//根据sql2创建PreparedStatement            
              pstmt.executeUpdate();              //执行
              conn.commit();                      //关闭
              if("0".equals(Status)){
                srep = "停用分类施保操作成功!";
              }else{
                srep = "启用分类施保操作成功!";
              }
          } catch (SQLException e) {
        	  if("0".equals(Status)){
                  srep = "停用分类施保操作失败!";
              }else{
                  srep = "启用分类施保操作失败!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //关闭PreparedStatement
              //DBUtils.close(conn);                //关闭连接
          }
          return srep;
     } 
    /**
     * 更新分类施保
     * @param sql
     * @param mode
     * @return
     */
    public String savePolicyChild(String sql,String mode) {
        String srep = "";
        
        //log.debug(sql);
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
              pstmt.execute();
              conn.commit(); 
              if("add".equals(mode)){
                srep = "新增分类施保操作成功!";
              }else if("edit".equals(mode)){
                srep = "修改分类施保操作成功!";
              }     
          } catch (SQLException e) {
        	  if("add".equals(mode)){
                  srep = "新增分类施保操作失败!";
              }else if("edit".equals(mode)){
                  srep = "修改分类施保操作失败!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //关闭PreparedStatement
              //DBUtils.close(conn);                //关闭连接
          }
          return srep;
     }
    /**
     * 取得业务分类施保标准列表
     * @param pid
     * @param sname
     * @return
     */
    public StringBuffer getPolicyChildSqlsHtml(String sid,String sname) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "";
    	String vsid = "",vprosid = "",vname = "",vdesc = "",vstatus = "";
    	
    	html = "<fieldset  class='list'>";
		html += "<legend  class='legend'>["+sname+"]标准列表</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>操作</td>";
			html +=temp;			
			temp ="<td height='23'>标准名称</td>";
			html +=temp;
			temp ="<td height='23'>详细描述</td>";
			html +=temp;
			temp ="<td height='23'>状态</td>";
			html +=temp;
		html +="</tr>";
		//
        String sql = "select policychildsql_id,policychild_id,name,sqldesc,physql,locsql,status  " +
        		"from doper_t_policychildsql where policychild_id = '"+sid+"'";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vsid = rs.getString("policychildsql_id");
                vname = rs.getString("name");
                vdesc = rs.getString("sqldesc");
                vstatus = rs.getString("status");
                
                html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDoSql(this,'"+vsid+"','"+vname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+vstatus+"'>"+vname+"</td>";
					html += "<td height='23' class='colValue'>"+vdesc+"</td>";
					if(vstatus.equals("1")){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildSqlStatus('"+vsid+"','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdatePoilcyChildSqlStatus('"+vsid+"','1')\">启用</button></td>";	
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
	    //
	    html += "<button class = 'btn' onclick=\"GoMatchPage('back')\">"+sname+"<<上一步</button>"; 
	    html += "<button class = 'btn' onclick=\"GetPolicyChildSqlItemHtml('')\">新增筛选标准</button>";
	    
	    //
	    shtml.append(html);
	    //log.debug(shtml);
	    //
        return shtml;
    }
    /**
     * 取得分类施保标准属性
     * @param sqlsid
     * @return
     */
    public StringBuffer getPolicyChildSqlItemHtml(String sqlsid) {
    	StringBuffer shtml= new StringBuffer("");
    	String html = "",temp = "",mode = "";
    	String vsid = "",vprosid = "",vproname = "",vname = "",vdesc = "",vstatus = "";
    	//
    	if(sqlsid.equals("")){
    		mode = "add";
    		vproname = "[新增]";
    	}else{
    		mode = "edit";
    		vproname = getPolicyChildSqlNameFromId(sqlsid);
    		vproname = "["+vproname+"]";
    		//
            String sql = "select policychildsql_id,policychild_id,name,sqldesc,physql,locsql,status  " +
        		"from doper_t_policychildsql where policychildsql_id = '"+sqlsid+"'";   //定义SQL语句
            
    		//
    		//log.debug(sql);
            Connection conn = null;                 //声明Connection对象
            PreparedStatement pstmt = null;         //声明PreparedStatement对象
            ResultSet rs = null;                    //声明ResultSet对象
            try {
                conn = DBUtils.getConnection();     //获取数据库连接
                pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
                rs = pstmt.executeQuery();
                while (rs.next()) {
                	vsid = rs.getString("policychildsql_id");
                    vname = rs.getString("name");
                    vdesc = rs.getString("sqldesc");
                    vstatus = rs.getString("status"); 	
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
    	html += "<legend  class='legend'>"+vproname+"标准属性</legend>";
		html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
		html += "<tr class='colField'>";
			temp ="<td height='23'>属性</td>";
			html +=temp;
			temp ="<td height='23'>属性值</td>";
			html +=temp;
		html +="</tr>";
	     	//各列值		
			
	        html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>标准名称</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'vname' value = '"+vname+"'></input></td>";
			html +="</tr>";	    	
	    	
	    	html +="<tr>";
				html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>详细描述</td>";
				html += "<td><input style = 'width:100%' type='text' id = 'vdesc' value = '"+vdesc+"'></input></td>";
	    	html +="</tr>";
	    		    	
	    		    	    
	        //
	    	html +="</table>";
	    	
	    	if(mode.equals("add")){//新增
		    	html += "<button class = 'btn' onclick=\"SavePolicyChildSql('"+mode+"')\">保存</button>"; 	   		
	    	}else if(mode.equals("edit")){//编辑
	    		//在用时可以设置
		    	if(vstatus.equals("1")){
		    		html += "<button class = 'btn' onclick=\"sqlaction()\">筛选标准条件</button>";  
		    		html += "<button class = 'btn' onclick=\"SavePolicyChildSql('"+mode+"')\">保存</button>";		    		
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
     * 更新分类施保标准状态
     * @param Id
     * @param Status
     * @return
     */
    public String updatePoilcyChildSqlStatus(String Id,String Status) {
        String srep = "";
          String sql = "update doper_t_policychildsql set status = '"+Status+"' where policychildsql_id = '"+Id+"'";  //更新档次状态SQL

          //log.debug(sql);
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(sql);//根据sql2创建PreparedStatement            
              pstmt.executeUpdate();              //执行
              conn.commit();                      //关闭
              if("0".equals(Status)){
                srep = "停用分类施保标准操作成功!";
              }else{
                srep = "启用分类施保标准操作成功!";
              }
          } catch (SQLException e) {
        	  if("0".equals(Status)){
                  srep = "停用分类施保标准操作失败!";
              }else{
                  srep = "启用分类施保标准操作失败!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //关闭PreparedStatement
              //DBUtils.close(conn);                //关闭连接
          }
          return srep;
     }
    /**
     * 更新分类施保标准
     * @param sql
     * @param mode
     * @return
     */
    public String savePolicyChildSql(String sql,String mode) {
        String srep = "";
        
        //log.debug(sql);
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              conn.setAutoCommit(false);
              pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
              pstmt.execute();
              conn.commit(); 
              if("add".equals(mode)){
                srep = "新增分类施保标准操作成功!";
              }else if("edit".equals(mode)){
                srep = "修改分类施保标准操作成功!";
              }     
          } catch (SQLException e) {
        	  if("add".equals(mode)){
                  srep = "新增分类施保标准操作失败!";
              }else if("edit".equals(mode)){
                  srep = "修改分类施保标准操作失败!";
              }  
              log.debug(e.toString());
          } finally {
              DBUtils.close(pstmt);               //关闭PreparedStatement
              //DBUtils.close(conn);                //关闭连接
          }
          return srep;
     }
    /**
     * 取得分类施保标准SQL语句
     * @param id
     * @param mode
     * @return
     */
    public String getPolicyChildSqlItem(String id,String mode) {
        String srep = "";
        String sql = "select physql, locsql from doper_t_policychildsql where policychildsql_id = '" + id + "'";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	if(mode.equals("loc")){
            		srep = rs.getString("locsql");
            	}else if(mode.equals("phy")){
            		srep = rs.getString("physql");
            	}              
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     * 更新分类施保标准SQL条件
     * @param Id
     * @param PhySql
     * @param LocSql
     * @return
     */
    public String updatePolicyChildSql(String Id,String PhySql,String LocSql) {
		String srep = "";
		//单引号转义去掉左右空格
		PhySql = PhySql.replace("'","''");
		LocSql = LocSql.replace("'","''");
		
		String sql = "update doper_t_policychildsql set " +
				"physql = '"+PhySql+"'," +
				"locsql = '"+LocSql+"' where policychildsql_id = '"+Id+"'";  //更新档次状态SQL
		
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		    pstmt.executeUpdate();              //执行
		    conn.commit();                      //关闭
		    srep = "更新标准保存操作成功!";
		} catch (SQLException e) {
			srep = "更新标准保存操作失败!";
			log.debug(e.toString());
		} finally {
			DBUtils.close(pstmt);               //关闭PreparedStatement
		    //DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
    /**
     * 由分类施保ID取得分类施保名称
     * @param name
     * @return
     */
    public String getPolicyChildNameFromId(String id) {
        String srep = "";
        String sql = "select policychild_id,name from doper_t_policychild where policychild_id = '" + id + "'";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              srep = rs.getString("name");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     * 由分类施保ID取得分类施保标准名称
     * @param name
     * @return
     */
    public String getPolicyChildSqlNameFromId(String id) {
        String srep = "";
        String sql = "select policychildsql_id,name from doper_t_policychildsql where policychildsql_id = '" + id + "'";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
              srep = rs.getString("name");
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return srep;
    }
    /**
     * 取得分类施业务所属选择
     * @param sname
     * @param cpropid
     * @param cpid
     * @return
     */
    public StringBuffer getPolicyChildChoiceHtml(String sname,String cpropid,String cpid){
        StringBuffer shtml= new StringBuffer("");
        String id = "",name = "";
        
        String sql = "select policy_id, name from doper_t_policy where flag = '1' order by policy_id";      
        //log.debug(sql);
        //
		if(existsPolicyChildSQLFromId(cpid)){
			shtml.append("<select id='"+sname+"' style = 'width:100%;font-size:12px;' disabled = 'disabled'>"); 
		}else{
			shtml.append("<select id='"+sname+"' style = 'width:100%;font-size:12px;'>"); 
		}
          
          //
          Connection conn = null;                 //声明Connection对象
          PreparedStatement pstmt = null;         //声明PreparedStatement对象
          ResultSet rs = null;                    //声明ResultSet对象
          try {
              conn = DBUtils.getConnection();     //获取数据库连接
              pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
              //参数
              rs = pstmt.executeQuery();
              
              while (rs.next()) {  
                id = rs.getString("policy_id");
                name = rs.getString("name");
                if(id.equals(cpropid)){
                	shtml.append("<option value='"+id+"' selected>"+name+"</option>");
                }else{
                	shtml.append("<option value='"+id+"'>"+name+"</option>");
                }                
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
     * 是否存在分类施保
     * @param parid
     * @param name
     * @return
     */
    public boolean existsPolicyChildFromName(String parid,String name){
		boolean  brep = false;
		
		String sql = "select policychild_id,name from doper_t_policychild " +
				"where name = '" + name + "' and policy_id = '"+parid+"'";   //定义SQL语句
		
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
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 是否存在分类施保标准
     * @param parid
     * @param name
     * @return
     */
    public boolean existsPolicyChildSql(String parid,String name){
		boolean  brep = false;
		
		String sql = "select policychildsql_id,name from doper_t_policychildsql " +
				"where name = '" + name + "' and policychild_id = '"+parid+"'";   //定义SQL语句
		
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
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 该标准是否存在设置好的筛选条件SQL语句
     * @param id
     * @return
     */
    public boolean existsPolicyChildPhySql(String id){
		boolean  brep = false;
		
		String sql = "select policychildsql_id,physql from doper_t_policychildsql " +
				"where policychildsql_id = '"+id+"' and physql is null";   //定义SQL语句
		
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
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}

    /**
     * 是否存在分类施保设置
     * @param pid
     * @return
     */
    public boolean existsPolicyChildFromId(String pid){
		boolean  brep = false;
		
		String sql = "select a.policychild_id from doper_t_policychild a,doper_t_policy b " +
				"where a.status = '1' and a.policy_id = b.policy_id and b.policy_id = '"+pid+"'";   //定义SQL语句
		
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
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 分类施保是否已经设置标准条件
     * @param childpid
     * @return
     */
    public boolean existsPolicyChildSQLFromId(String childpid){
		boolean  brep = false;
		
		String sql = "select policychildsql_id from doper_t_policychildsql where policychild_id = '"+childpid+"'";   //定义SQL语句
		
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
		    //DBUtils.close(conn);                //关闭连接
		}
		return brep;
	}
    /**
     * 家庭是否符合分类施保标准
     * @param childpid
     * @return
     */
    public boolean getPolicyChildFamilyFlag(String childpid,String fmid) {
    	boolean  brep = false;
    	String sphysql = "",sstatus = "";
        String sql = "select physql,status from doper_t_policychildsql " +
        		"where physql is not null " +
        		"and policychild_id = '"+childpid+"' ";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	sphysql = rs.getString("physql");
            	sstatus = rs.getString("status");
            	//
            	if(sstatus.equals("0")){//停用
            		break;
            	}
            	
            	//检验SQL条件设置是否符合条件
            	brep = existsFamilyIdFromPolicyChild(sphysql,fmid);
            	if(brep){//不符合条件
            		break;
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        return brep;
    }
    /**
     * 家庭是否符合分类施保条件
     * @param physql
     * @param fmid
     * @return
     */
    public boolean existsFamilyIdFromPolicyChild(String physql,String fmid){
		boolean  brep = false;
		
		String sql = physql + " and  INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";   //定义SQL语句
		
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
		    //DBUtils.close(conn);                //关闭连接
		}
		
		return brep;
	}
}
