package com.mingda.action.policy.acc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.mingda.action.info.search.DBUtils;
import com.mingda.action.info.search.TableInfoQuery;
import com.mingda.action.policy.query.PolicyManageCheckQuery;
public class Policyaccedit {
	static Logger log = Logger.getLogger(Policyaccedit.class);
	/**
	 * 本机构是否已经业务终审结算
	 * 是否处理完毕
	 * (计算机后台过程处理完毕标识)
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String getPolicyAllCheckAccNoOverFlag(String pid,String empid){
		String  srep = "0";
		String empdepid = "";
	 	//
    	// 表查询处理类
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//
    	String sql = "select optacc_id,accflag from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and accoverflag = '0' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 " +
    							"order by accflag asc";
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
				srep = "1";
				break;
			}			        
		} catch (SQLException e) {
			log.debug(e.toString());
		} finally {
		    DBUtils.close(rs);                  //关闭结果集
		    DBUtils.close(pstmt);               //关闭PreparedStatement
		  //  DBUtils.close(conn);                //关闭连接
		}
		return srep;
	}
	/**
	 * 当前业务做终审结算或者撤消终审结算
	 * @param empid
	 * @param pid
	 * @param mode
	 * @return
	 */
	public String setPolicyAllAccFlag(String empid,String pid,String mode){
		String srep = "",soptaccid = "";
	    //政策业务查询处理类
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();		
	    //
    	//是否存在正在办理的业务终审结算编号
    	soptaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	if(soptaccid.equals("2")){
    		srep = "当前业务未办理,操作失败!";
    		return srep;    		
    	}
    	//撤消终审结算或者终审结算
    	if(mode.equals("1")){//终审结算   
	    	//	
    	}else if(mode.equals("0")){//撤消终审结算 
    		//不许撤消终审结算
    		return "不许撤消终审结算";
    	}
    	//
    	//
	    String sql  = "update biz_t_optacc set accflag = '" +mode+"',accdt = sysdate,accoper = '"+empid+"'  " +
						"where optacc_id = '"+soptaccid+"'";
		//log.debug(sql);
		
		Connection conn = null;                 //声明Connection对象
		PreparedStatement pstmt = null;         //声明PreparedStatement对象
		try {
			conn = DBUtils.getConnection();     //获取数据库连接
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
			pstmt.executeUpdate();              //执行            
			conn.commit();                      //关闭
			if(mode.equals("1")){
				srep = "终审结算操作成功!";
			}else{
				srep = "撤消终审结算操作成功!";
			}			
		} catch (SQLException e) {
			try {
				if(mode.equals("1")){
			    	srep = "终审结算操作失败!";
			    }else{
			    	srep = "撤消终审结算操作失败!";
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
	 * 新增当前业务终审结算年份和月份
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public String setPolicyAllCheckAccItems(String pid,String empid,String year,String month){
		String srep = "",empdepid = "",soptaccid = "";			
	 	//
		// 表查询处理类
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//政策业务查询处理类
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();
    	//是否存在正在办理的业务终审结算编号
    	soptaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	if(soptaccid.equals("2")){
    		//可以添加新业务终审结算记录
    		//
        	empdepid = tableinfoquery.getempdepid(empid);
    		//当前年份月份是否存在
    		if(existsPolicyAllCheckAccItems(pid,empid,year,month)){
    			srep = "终审结算日期已经存在,正在使用!";
    		}else{
    			String sql  = "insert into biz_t_optacc " +
    					"(optacc_id, policy_id, organization_id, accyear, accmonth,begoper,begdt)" +
    					"values " +
    					"(xoptacc_id.nextval,'"+pid+"','"+empdepid+"','"+year+"','"+month+"','"+empid+"',sysdate)";  //SQL
    		    //log.debug(sql);
    		    
    		    Connection conn = null;                 //声明Connection对象
    		    PreparedStatement pstmt = null;         //声明PreparedStatement对象
    		    try {
    		        conn = DBUtils.getConnection();     //获取数据库连接
    		        conn.setAutoCommit(false);
    		        pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
    		        pstmt.execute();              //执行            
    		        conn.commit();                      //关闭
    		        srep = "生成新终审结算日期操作成功!";
    		    } catch (SQLException e) {
    		    	try {
    		    		srep = "生成新终审结算日期操作失败!";
    		            conn.rollback();
    		        } catch (SQLException e1) {          
    		            e1.printStackTrace();
    		        }
    		    } finally {
    		        DBUtils.close(pstmt);               //关闭PreparedStatement
    		      //  DBUtils.close(conn);                //关闭连接
    		    }
    		}   		
    	}else{
    		srep = "当前业务还有未终审结算办理,操作失败!";
    		return srep; 
    	}    	
		return srep;
	}
	/**
	 * 删除当前业务终审结算年份和月份
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public String deletePolicyAllCheckAccItems(String pid,String empid,String year,String month){
		String srep = "",empdepid = "";			
	 	//
		// 表查询处理类
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//当前年份月份是否存在
		if(existsPolicyAllCheckAccItemsAcc(pid,empid,year,month)){
			srep = "终审结算日期已经存在,并且已经终审结算!";
		}else{
			String sql  = "delete biz_t_optacc where accflag = '0' and policy_id = '"+pid+"' " +
					"and accyear = '"+year+"' and accmonth = '"+month+"'  " +
					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0";  //SQL
		    //log.debug(sql);
		    
		    Connection conn = null;                 //声明Connection对象
		    PreparedStatement pstmt = null;         //声明PreparedStatement对象
		    try {
		        conn = DBUtils.getConnection();     //获取数据库连接
		        conn.setAutoCommit(false);
		        pstmt = conn.prepareStatement(sql);//根据sql创建PreparedStatement            
		        pstmt.execute();              //执行            
		        conn.commit();                      //关闭
		        srep = "删除终审结算日期操作成功!";
		    } catch (SQLException e) {
		    	try {
		    		srep = "删除终审结算日期操作失败!";
		            conn.rollback();
		        } catch (SQLException e1) {          
		            e1.printStackTrace();
		        }
		    } finally {
		        DBUtils.close(pstmt);               //关闭PreparedStatement
		      //  DBUtils.close(conn);                //关闭连接
		    }
		}    	
		return srep;
	}
  /**
   * 取得当前业务终审结算年份和月份
   * @param pid
   * @param empid
   * @return
   */
	public String getPolicyAllCheckAccYearItems(String pid,String empid){		
		String srep = "",optaccid = "";
		//政策业务查询处理类
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();    	
    	optaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	//不存在办理业务周期
    	if(optaccid.equals("2")){
    		return srep;
    	}
    	String sql = "select optacc_id,accyear,accmonth from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and optacc_id = '"+optaccid+"'";
        //
    	//log.debug("sql:"+sql);
        Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
           
            while (rs.next()) {                
            	srep = rs.getString("accyear");                
                break;
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
           // DBUtils.close(conn);                //关闭连接
        }
        return srep;
	}
	/**
	 * 取得当前业务终审结算月份
	 * @param pid
	 * @param empid
	 * @return
	 */
	public String getPolicyAllCheckAccMonthItems(String pid,String empid){		
		String srep = "",optaccid = "";
		//政策业务查询处理类
	    PolicyManageCheckQuery policymanagecheckquery = new PolicyManageCheckQuery();     	
    	optaccid = policymanagecheckquery.getPolicyAllCheckAccOptaccid(pid,empid);
    	//不存在办理业务周期
    	if(optaccid.equals("2")){
    		return srep;
    	}
    	String sql = "select optacc_id,accyear,accmonth from biz_t_optacc " +
    					"where policy_id = '"+pid+"' and optacc_id = '"+optaccid+"'";
    	//log.debug(sql);
    	Connection conn = null;                 //声明Connection对象
        PreparedStatement pstmt = null;         //声明PreparedStatement对象
        ResultSet rs = null;                    //声明ResultSet对象
        try {
            conn = DBUtils.getConnection();     //获取数据库连接
            pstmt = conn.prepareStatement(sql); //根据sql创建PreparedStatement   
            rs = pstmt.executeQuery();
           
            while (rs.next()) {                
            	srep = rs.getString("accmonth");
            	break;                
            }
        } catch (SQLException e) {
            log.debug(e.toString());
        } finally {
            DBUtils.close(rs);                  //关闭结果集
            DBUtils.close(pstmt);               //关闭PreparedStatement
           // DBUtils.close(conn);                //关闭连接
        }        
        return srep;
	}
	/**
	 * 判断是否存在新的业务终审结算年份和月份
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean existsPolicyAllCheckAccItems(String pid,String empid,String year,String month){
		boolean  brep = false;
    	String empdepid = ""; 	 	
	 	//
    	// 表查询处理类
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//终审结算标识
    	String sql = "select optacc_id from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and accyear = '"+year+"' and accmonth = '"+month+"' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 order by accflag asc";
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
	 * 判断是否存在新的业务终审结算年份和月份并且已经终审结算
	 * @param pid
	 * @param empid
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean existsPolicyAllCheckAccItemsAcc(String pid,String empid,String year,String month){
		boolean  brep = false;
    	String empdepid = ""; 	 	
	 	//
    	// 表查询处理类
    	TableInfoQuery tableinfoquery = new TableInfoQuery();
    	//
    	empdepid = tableinfoquery.getempdepid(empid);
    	//终审结算标识
    	String sql = "select optacc_id from biz_t_optacc " +
    			"where policy_id = '"+pid+"' and accyear = '"+year+"' and accmonth = '"+month+"' and accflag ='1' " +
    					"and instr('#' || '"+empdepid+"', '#' || organization_id) > 0 order by accflag asc";
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
