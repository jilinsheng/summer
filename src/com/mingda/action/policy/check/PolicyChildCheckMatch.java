package com.mingda.action.policy.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;

/**
 * 分类施保
 * 符合条件处理
 * 符合标准处理
 * @author xiu
 *
 */
public class PolicyChildCheckMatch {
	static Logger log = Logger.getLogger(PolicyChildCheckMatch.class);
    /**
     * 设置单个家庭或者成员分类施保审批表
     * @param hashmap
     * @return
     */
	public String SetPolciyChildMatchOne(HashMap hashmap){
    	String  srep = "0";
    	String checkchildid = "",physql = "",locsql = "";
    	//
	    String pid = hashmap.get("pid").toString();
		String checkid = hashmap.get("checkid").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//是否存在分类施保
		if(!existsPolicyChildFromPid(pid)){
			return srep;
		}
		//
		String sql = "select a.policychild_id,a.sqltype from doper_t_policychild a,doper_t_policy b " +
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
				String childpid = rs.getString("policychild_id"); 
				String sqltype = rs.getString("sqltype");
				if(null != childpid){
					if("2".equals(sqltype)){
						//成员核算
						HashMap  hashmapmem = new HashMap();
						hashmapmem = getPolicyChildMemberSql(childpid,memid);
						physql = hashmapmem.get("physql").toString();
						locsql = hashmapmem.get("locsql").toString();
						//是否符合分类施保标准
						if(!"".equals(physql)){
							//更新分类施保审批表
					    	checkchildid = GetCheckChildMember(childpid,memid);					    	
							if("".equals(checkchildid)){//不存在								
								//添加分类施保审批家庭成员记录
								HashMap hashmapinsert = new HashMap();
								hashmapinsert.put("checkid", checkid);
								hashmapinsert.put("childpid", childpid);
								hashmapinsert.put("fmid", fmid);
								hashmapinsert.put("memid", memid);
								hashmapinsert.put("physql", physql);
								hashmapinsert.put("locsql", locsql);
								//
								insertPolicyChildCheck(hashmapinsert);
							}else{//存在分类施保审批记录
								//更新
								updatePolicyChildCheck(checkchildid,physql,locsql);
							}							
						}else{
							//不符合标准
							//删除分类施保审批家庭成员记录
							deletePolicyChildCheckMember(childpid,memid);					    	
						}						
					}else{
						//家庭核算
						HashMap  hashmapfm = new HashMap();
						hashmapfm = getPolicyChildFamilySql(childpid,fmid);
						physql = hashmapfm.get("physql").toString();
						locsql = hashmapfm.get("locsql").toString();
						//是否符合分类施保标准
						if(!"".equals(physql)){
							//更新分类施保审批表
							checkchildid = GetCheckChildFamily(childpid,fmid);
							if("".equals(checkchildid)){//不存在								
								//添加分类施保审批家庭记录
								HashMap hashmapinsert = new HashMap();
								hashmapinsert.put("checkid", checkid);
								hashmapinsert.put("childpid", childpid);
								hashmapinsert.put("fmid", fmid);
								hashmapinsert.put("memid", memid);
								hashmapinsert.put("physql", physql);
								hashmapinsert.put("locsql", locsql);
								//
								insertPolicyChildCheck(hashmapinsert);
							}else{//存在分类施保审批记录
								//更新
								updatePolicyChildCheck(checkchildid,physql,locsql);
							}
						}else{
							//不符合标准
							//删除分类施保审批家庭记录
							deletePolicyChildCheckFamily(childpid,fmid);
						}
					}
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
     * 家庭是否符合分类施保标准
     * @param childpid
     * @param fmid
     * @return
     */
    public HashMap getPolicyChildFamilySql(String childpid,String fmid) {
    	HashMap  hashmap = new HashMap();
    	String physql = "",locsql = "",status = "0";
    	//
        String sql = "select physql,locsql,status from doper_t_policychildsql " +
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
            	physql = rs.getString("physql");
            	locsql = rs.getString("locsql");
            	status = rs.getString("status");
            	//
            	if(status.equals("0")){//停用
            		break;
            	}
            	if(null == physql || "".equals(physql)){//没有设置标准条件
            		break;
            	}            	
            	//检验SQL条件设置是否符合条件
            	boolean brep = existsFamilyIdFromPolicyChild(physql,fmid);
            	if(brep){//符合条件
            		break;
            	}else{
            		physql = "";
            		locsql = "";
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
    	hashmap.put("physql", physql);
    	hashmap.put("locsql", locsql);
    	//
        return hashmap;
    }

    /**
     * 成员是否符合分类施保标准
     * @param childpid
     * @param memid
     * @return
     */
    public HashMap getPolicyChildMemberSql(String childpid,String memid) {
    	HashMap  hashmap = new HashMap();
    	String physql = "",locsql = "",status = "";
    	//
    	//
        String sql = "select physql,locsql,status from doper_t_policychildsql " +
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
            	physql = rs.getString("physql");
            	locsql = rs.getString("locsql");
            	status = rs.getString("status");
            	//
            	if(status.equals("0")){//停用
            		break;
            	}
            	if(null == physql || "".equals(physql)){//没有设置标准条件
            		break;
            	}            	
            	//检验SQL条件设置是否符合条件
            	boolean brep = existsMemberIdFromPolicyChild(physql,memid);
            	if(brep){//符合条件            		
            		break;
            	}else{
            		physql = "";
            		locsql = "";
            	}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            //DBUtils.close(conn);                //关闭连接
        }
        //
    	hashmap.put("physql", physql);
    	hashmap.put("locsql", locsql);
    	//
        return hashmap;
    }
    /**
     * 插入分类施保审批表信息
     * @param hashmap
     * @return
     */
    public String insertPolicyChildCheck(HashMap hashmap) {
    	String srep = "",schildcheckid = "";
		String sql1 = "",sql2 = "";
		String checkid = hashmap.get("checkid").toString();
		String childpid = hashmap.get("childpid").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		String physql = hashmap.get("physql").toString();
		String locsql = hashmap.get("locsql").toString();
		// 表查询处理类
	    TableInfoQuery tableinfoquery = new TableInfoQuery();
	    //单引号转义去掉左右空格
		physql = physql.replace("'","''");
		locsql = locsql.replace("'","''");
	    //
		schildcheckid = tableinfoquery.getseqfromname("XOPTCHECKCHILD_ID");
		sql1 = "insert into biz_t_optcheckchild " +
				"(optcheckchild_id, policychild_id,optcheck_id,  family_id, member_id) " +
				"values " +
				"('"+schildcheckid+"','"+childpid+"','"+checkid+"','"+fmid+"','"+memid+"')";  //更新档次状态SQL
		
		sql2 = "insert into biz_t_optcheckchildsql " +
				"(optcheckchildsql_id,optcheckchild_id,physql,locsql) " +
				"values (xoptcheckchildsql_id.nextval,'"+schildcheckid+"','"+physql+"','"+locsql+"')";
		//log.debug(sql);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    conn.commit();                     //关闭		    
		    srep = "插入审批分类施保操作成功!";
		} catch (SQLException e) {
			try {
				srep = "插入审批分类施保操作失败!";
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
     * 更新审批分类施保标准
     * @param checkchildid
     * @param physql
     * @param locsql
     * @return
     */
    public String updatePolicyChildCheck(String checkchildid,String physql,String locsql) {
    	String srep = "",sql = "";
    	//单引号转义去掉左右空格
		physql = physql.replace("'","''");
		locsql = locsql.replace("'","''");
	    //
    	//
    	sql = "update biz_t_optcheckchildsql " +
    			"set physql = '"+physql+"'," +
    			"locsql = '"+locsql+"' " +
    			"where optcheckchild_id = '"+checkchildid+"'";
    	//log.debug(sql);
	      
  	  	Connection conn = null;                 //声明Connection对象
  	  	PreparedStatement pstmt = null;         //声明PreparedStatement对象
  	  	try {
  	  		conn = DBUtils.getConnection();     //获取数据库连接
  	  		conn.setAutoCommit(false);
  	  		pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
  	  		pstmt.execute();              //执行  	  		 
  	  		conn.commit();                      //关闭
  	  		//
  	  		srep = "更新审批分类施保标准操作成功!";
          
  	  	} catch (SQLException e) {
  	  		try {
  	  			srep = "更新审批分类施保标准操作失败!";
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
     * 删除分类施保审批家庭记录
     * @param childpid
     * @param fmid
     * @return
     */
    public String deletePolicyChildCheckFamily(String childpid,String fmid) {
    	String srep = "";
		String sql1 = "",sql2 = "",sql3 = "";
		
		sql1 = "delete biz_t_optcheckchildidea " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and family_id ='"+fmid+"')";  //更新状态SQL
		sql2 = "delete biz_t_optcheckchildsql " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and family_id ='"+fmid+"')";  //更新状态SQL
		sql3 = "delete biz_t_optcheckchild where policychild_id = '"+childpid+"' and family_id ='"+fmid+"'";  //更新状态SQL
		//log.debug(sql1);
		//log.debug(sql2);
		//log.debug(sql3);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql3);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    conn.commit();                     //关闭		    
		    srep = "删除分类施保操作成功!";
		} catch (SQLException e) {			
			try {
				srep = "删除分类施保操作失败!";
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
     * 删除分类施保审批家庭成员记录
     * @param childpid
     * @param memid
     * @return
     */
    public String deletePolicyChildCheckMember(String childpid,String memid) {
    	String srep = "";
		String sql1 = "",sql2 = "",sql3 = "";
		
		sql1 = "delete biz_t_optcheckchildidea " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and member_id ='"+memid+"')";  //更新状态SQL
		sql2 = "delete biz_t_optcheckchildsql " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild  " +
				"where policychild_id = '"+childpid+"' and member_id ='"+memid+"')";  //更新状态SQL
		sql3 = "delete biz_t_optcheckchild where policychild_id = '"+childpid+"' and member_id ='"+memid+"'";  //更新状态SQL
		//log.debug(sql1);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql3);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    conn.commit();                     //关闭		    
		    srep = "删除分类施保操作成功!";
		} catch (SQLException e) {			
			try {
				srep = "删除分类施保操作失败!";
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
     * 删除分类施保审批家庭记录
     * @param hashmap
     * @return
     */
    public String deletePolicyChildCheck(HashMap hashmap) {
    	String srep = "";
		String sql1 = "",sql2 = "",sql3 = "";
		//
	    String pid = hashmap.get("pid").toString();
		String fmid = hashmap.get("fmid").toString();
		String memid = hashmap.get("memid").toString();
		//
		sql1 = "delete biz_t_optcheckchildidea " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild a,doper_t_policychild b " +
				"where a.policychild_id = b.policychild_id and b.policy_id = '"+pid+"' " +
						"and family_id ='"+fmid+"' and member_id ='"+memid+"')";  //更新状态SQL
		sql2 = "delete biz_t_optcheckchildsql " +
				"where optcheckchild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild a,doper_t_policychild b " +
				"where a.policychild_id = b.policychild_id and b.policy_id = '"+pid+"' " +
						"and family_id ='"+fmid+"' and member_id ='"+memid+"')";  //更新状态SQL
		sql3 = "delete biz_t_optcheckchild " +
				"where policychild_id in " +
				"(select optcheckchild_id from biz_t_optcheckchild a,doper_t_policychild b " +
				"where a.policychild_id = b.policychild_id and b.policy_id = '"+pid+"' " +
						"and family_id ='"+fmid+"' and member_id ='"+memid+"')";  //更新状态SQL
		//log.debug(sql1);
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
		    conn = DBUtils.getConnection();     //获取数据库连接
		    conn.setAutoCommit(false);
		    pstmt = conn.prepareStatement(sql1);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql2);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    pstmt = conn.prepareStatement(sql3);//根据sql创建PreparedStatement            
		    pstmt.execute();              //执行
		    conn.commit();                     //关闭		    
		    srep = "删除分类施保操作成功!";
		} catch (SQLException e) {
			try {
				srep = "删除分类施保操作失败!";
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
	 * 取得分类施保该用户审批意见ID
	 * @param empid
	 * @param childid
	 * @return
	 */
	public String getPolicyChildChickIdeas(String empid,String childid) {
        String srep = "";
        String sql = "select optcheckchildidea_id from biz_t_optcheckchildidea " +
        		"where optcheckchild_id = '"+childid+"' and childcheckoper = '"+empid+"'";   //定义SQL语句
        
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	srep = rs.getString("optcheckchildidea_id");        
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
     * 取得分类施保的审批家庭
     * @param childpid
     * @param fmid
     * @return
     */
    public String GetCheckChildFamily(String childpid,String fmid){
    	String srep = "";
		
		String sql = "select optcheckchild_id,optcheck_id from biz_t_optcheckchild " +
				"where policychild_id = '"+childpid+"' and family_id = '"+fmid+"'";   //定义SQL语句
		
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
				srep = rs.getString("optcheckchild_id");
				if(null == srep){
					srep = "";
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
     * 取得分类施保的审批家庭成员
     * @param childpid
     * @param memid
     * @return
     */
    public String GetCheckChildMember(String childpid,String memid){
		String srep = "";
		String sql = "select optcheckchild_id,optcheck_id from biz_t_optcheckchild " +
				"where policychild_id = '"+childpid+"' and member_id = '"+memid+"'";   //定义SQL语句
		
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
				srep = rs.getString("optcheckchild_id");
				if(null == srep){
					srep = "";
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
    /**
     * 成员是否符合分类施保条件
     * @param physql
     * @param memid
     * @return
     */
    public boolean existsMemberIdFromPolicyChild(String physql,String memid){
		boolean  brep = false;
		
		String sql = physql + " and  INFO_T_MEMBER.MEMBER_ID = '"+memid+"'";   //定义SQL语句
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
    public boolean existsPolicyChildFromPid(String pid){
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
}
