package com.mingda.action.policy.find;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.common.ConstantDefine;

public class PolicyQuery {
	static Logger log = Logger.getLogger(PolicyQuery.class);
	/**
	 * 由业务编号获取业务描述
	 * @param pid
	 * @return
	 */
	public String getPolicyDescFromId(String pid) {
		String srep = "";
		String sql = "select descr " + "from doper_t_policy "
				+ "where flag = '1' and policy_id = '" + pid + "' ";
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
		    log.debug("更新保障单0007"+sql);
			rs = pstmt.executeQuery();
			//            
			if (rs.next()) {
				srep = rs.getString("descr");
			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}
	/**
     * 由业务编号取得业务类别(0城市1农村)
     * @param pid
     * @return
     */
    public String getPolicyObjTypeFromId(String pid){
    	String srep = "0";
    	String sql = "select " 
    		+ "objtype " 
    		+ "from doper_t_policy " 
    		+ "where flag = '1' and policy_id = '"+pid+"' ";
    	//log.debug(sql);
    	Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement 
            log.debug("更新保障单0008"+sql);
            rs = pstmt.executeQuery();
            //            
            if (rs.next()) {
            	//
            	srep = rs.getString("objtype");            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
    	return srep;
    }
    /**
     * 获取业务是否强行执行标准条件
     * @param pid
     * @return
     */
    public String getPolicyStandardType(String pid) {
    	String srep = "0",sup = "",nup = "";
        
        String sql = "select superpolicy,notpolicy from doper_t_standard where flag=1 and  policy_id = '" + pid + "' ";   //定义SQL语句
        log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            log.debug("更新保障单0009"+sql);
            rs = pstmt.executeQuery();            
            while (rs.next()) {
            	sup = rs.getString("superpolicy");
            	nup = rs.getString("notpolicy");
            	//log.debug("sup:"+sup);
            	//log.debug("nup:"+nup);
            	if(null != nup || null != nup){
            		srep="1";
            	}         	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        log.debug("srep:"+srep);
        return srep;
	}
    /**
	 * 获取业务是否可以填写救助金标识
	 * 0不允许1允许更新
	 * @param pid
	 * @return
	 */
	public String getPolicyUserAccFlag(String pid) {
    	String srep = "0";
        
        String sql = "select useraccflag from biz_t_checkflow where policy_id = '" + pid + "' ";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            log.debug("更新保障单0010"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	srep = rs.getString("useraccflag");            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        return srep;
	}
	/**
	 * 获取用户审批权限信息
	 * @param pid
	 * @param empid
	 * @return
	 */
	public HashMap getPolicyCheckFlags(String depth,String empid,String pid) {
    	HashMap hashmap = new HashMap();
    	boolean brow = false;
    	String oneflag = "0",endflag = "0";
    	//获取是否是第一审批机构
    	if(depth.equals(getPolicyOneFlowFromId(pid))){
    		oneflag = "1";
    	}
    	//获取是否是终审审批机构
    	if(depth.equals(getPolicyEndFlowFromId(pid))){
    		endflag = "1";
    	}
    	//
    	hashmap.put("onecheck", oneflag);    
    	hashmap.put("endcheck", endflag); 
    	//
    	String sql = "select "     		
    		+ "a.checkflag checkflag," 
    		+ "b.checkflag morecheck," 
    		+ "a.checkmoreflag usermorecheck," 
    		+ "a.reportflag reportflag " 
    		+ "from biz_t_checkpower a,biz_t_checkflow b,sys_t_employee c,sys_t_empext d " 
    		+ "where a.policy_id = b.policy_id and a.post_id = c.post_id and c.employee_id = d.employee_id " 
    		+ "and d.empext_id = '"+empid+"' and a.policy_id = '"+pid+"' ";   //定义SQL语句
        
        log.debug("更新保障单0001"+sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement  
            log.debug("更新保障单0011"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	hashmap.put("usercheck", rs.getString(1));    
            	hashmap.put("morecheck", rs.getString(2)); 
            	hashmap.put("usermorecheck", rs.getString(3)); 
            	hashmap.put("report", rs.getString(4)); 
            	brow = true;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        if(!brow){
        	hashmap.put("usercheck",0);    
        	hashmap.put("morecheck",0); 
        	hashmap.put("usermorecheck",0); 
        	hashmap.put("report",0); 
        }
    	return hashmap;
    }

    /**
     * 取得业务审批进度为第一审批的机构级别
     * @param id
     * @return
     */
    public String getPolicyOneFlowFromId(String id) {
    	String srep = "0",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
    	//常量定义
        ConstantDefine constantdefine = new ConstantDefine();
        //
    	String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 " 
    		+ "from biz_t_checkflow " 
    		+ "where policy_id = '" + id + "' ";   //定义SQL语句
        log.debug("更新保障单0002"+sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement
            log.debug("更新保障单0013"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");
            	if (appstate1.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_5;
				} else if (appstate2.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_4;
				} else if (appstate3.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_3;
				} else if (appstate4.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_2;
				} else if (appstate5.equals("1")){
					srep = constantdefine.POLICYQUERYCODE_1;
				}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }	
    	return srep;
    }
    /**
     * 取得业务审批进度为最后审批的机构级别
     * @param id
     * @return
     */
    public String getPolicyEndFlowFromId(String id) {
    	String srep = "0",appstate1 = "",appstate2 = "",appstate3 = "",appstate4 = "",appstate5 = "";
    	//常量定义
        ConstantDefine constantdefine = new ConstantDefine();
        //
    	String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 " 
    		+ "from biz_t_checkflow " 
    		+ "where policy_id = '" + id + "' ";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement 
            log.debug("更新保障单0003"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	appstate1 = rs.getString("appstate1");
            	appstate2 = rs.getString("appstate2");
            	appstate3 = rs.getString("appstate3");
            	appstate4 = rs.getString("appstate4");
            	appstate5 = rs.getString("appstate5");
            	if (appstate5.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_1;
				} else if (appstate4.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_2;
				} else if (appstate3.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_3;
				} else if (appstate2.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_4;
				} else if (appstate1.equals("1")) {
					srep = constantdefine.POLICYQUERYCODE_5;
				}
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }	
    	return srep;
    }
    /**
     * 前进
     * 取得审批流程下一个机构级别
     * 返回机构级别[5社区4街道3区2市1省]
     * 返回0就说明可以终审和终审结算了
     * @param id
     * @param empid
     * @return
     */
    public String getPolicyNextFlowFromId(String id, String empid) {
		String srep = "0", empdepth = "", appstate1 = "", appstate2 = "", appstate3 = "", appstate4 = "", appstate5 = "";
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		//
		empdepth = tableinfoquery.getempdepth(empid);
		srep = empdepth;
		//
		String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 "
				+ "from biz_t_checkflow " + "where policy_id = '" + id + "' "; // 定义SQL语句
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
		    log.debug("更新保障单0004"+sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				appstate1 = rs.getString("appstate1");
				appstate2 = rs.getString("appstate2");
				appstate3 = rs.getString("appstate3");
				appstate4 = rs.getString("appstate4");
				appstate5 = rs.getString("appstate5");
				if (empdepth.equals(constantdefine.POLICYQUERYCODE_5)) {
					srep = constantdefine.POLICYQUERYCODE_5;
					if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_4)) {
					srep = constantdefine.POLICYQUERYCODE_4;
					if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_3)) {
					srep = constantdefine.POLICYQUERYCODE_3;
					if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_2)) {
					srep = constantdefine.POLICYQUERYCODE_2;
					if ("1".equals(appstate5)) {
						srep = constantdefine.POLICYQUERYCODE_1;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_1)) {
					srep = constantdefine.POLICYQUERYCODE_1;
				}

			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}
    /**
     * 后退
     * 取得审批流程上一个机构级别
     * 返回机构级别[5社区4街道3区2市1省]
     * 返回0就说明可以终审和终审结算了
     * @param id
     * @param empid
     * @return
     */
    public String getPolicyBackFlowFromId(String id,String empid) {
        String srep = "0", empdepth = "", appstate1 = "", appstate2 = "", appstate3 = "", appstate4 = "", appstate5 = "";
		// 表查询处理类
		TableInfoQuery tableinfoquery = new TableInfoQuery();
		// 常量定义
		ConstantDefine constantdefine = new ConstantDefine();

		//
		empdepth = tableinfoquery.getempdepth(empid);
		srep = empdepth;
		//
		String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 "
				+ "from biz_t_checkflow " + "where policy_id = '" + id + "' "; // 定义SQL语句
		// log.debug(sql);
		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象
		try {
			conn = DBUtils.getConnection(); // 获取数据库连接
			pstmt = conn.prepareStatement(sql); // 根据sql创建PreparedStatement
		    log.debug("更新保障单0005"+sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				appstate1 = rs.getString("appstate1");
				appstate2 = rs.getString("appstate2");
				appstate3 = rs.getString("appstate3");
				appstate4 = rs.getString("appstate4");
				appstate5 = rs.getString("appstate5");
				if (empdepth.equals(constantdefine.POLICYQUERYCODE_1)) {
					srep = constantdefine.POLICYQUERYCODE_1;
					if ("1".equals(appstate4)) {
						srep = constantdefine.POLICYQUERYCODE_2;
					} else if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_2)) {
					srep = constantdefine.POLICYQUERYCODE_2;
					if ("1".equals(appstate3)) {
						srep = constantdefine.POLICYQUERYCODE_3;
					} else if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_3)) {
					srep = constantdefine.POLICYQUERYCODE_3;
					if ("1".equals(appstate2)) {
						srep = constantdefine.POLICYQUERYCODE_4;
					} else if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_4)) {
					srep = constantdefine.POLICYQUERYCODE_4;
					if ("1".equals(appstate1)) {
						srep = constantdefine.POLICYQUERYCODE_5;
					}
				} else if (empdepth.equals(constantdefine.POLICYQUERYCODE_5)) {
					srep = constantdefine.POLICYQUERYCODE_5;
				}

			}
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			DBUtils.close(conn); // 关闭连接
		}
		return srep;
	}
    /**
     * 获取机构审批流程
     * @param pid
     * @return
     */
    public HashMap getPolicyFlowsFromId(String pid) {
    	HashMap  hashmap = new HashMap();
        
        String depth1 = "0",depth2 = "0",depth3 = "0",depth4 = "0",depth5 = "0";
        String sql = "select appstate1,appstate2,appstate3,appstate4,appstate5 from biz_t_checkflow where policy_id = '" + pid + "'";   //定义SQL语句
        //log.debug(sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement 
            log.debug("更新保障单0006"+sql);
            rs = pstmt.executeQuery();            
            if (rs.next()) {
            	depth1 = rs.getString("appstate1");
            	depth2 = rs.getString("appstate2");
            	depth3 = rs.getString("appstate3");
            	depth4 = rs.getString("appstate4");
            	depth5 = rs.getString("appstate5");            	
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
            DBUtils.close(conn);                //关闭连接
        }
        //
        hashmap.put("depth1", depth1);
        hashmap.put("depth2", depth2);
        hashmap.put("depth3", depth3);
        hashmap.put("depth4", depth4);
        hashmap.put("depth5", depth5);
        return hashmap;
    }    
}
