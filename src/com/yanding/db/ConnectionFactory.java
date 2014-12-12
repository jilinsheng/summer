package com.yanding.db;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.mingda.common.SessionFactory;
import com.syssoft.report.DBConnFace;

/**
 * 继承接口  com.syssoft.report.DBConnFace 为报表获取数据库连接做准备
 */

public class ConnectionFactory implements DBConnFace{
	static Logger logger = Logger.getLogger(ConnectionFactory.class);
//  private DBConnectionManage webDB = DBConnectionManage.getInstance();

  /**
   * 继承接口 DBConnFace 方法获取数据库连接
   * @param connName 数据库名称，此名称与设计期内定义的数据库连接名称对应
   * @return 数据库连接
   */
  @SuppressWarnings("deprecation")
public Connection getConnection(String connName)
  {
 // Connection conn = webDB.getConnection(connName);
	  //打开session
	 Connection conn = com.mingda.common.SessionFactory.getSession().connection();	  
    return conn;
  }

  /**
   * 继承接口 DBConnFace 方法释放数据库连接
   * @param connName 数据库名称，此名称与设计期内定义的数据库连接名称对应
   * @param connection  数据库连接
   */
  public void freeConnection(String connName,Connection connection)
  {
    //webDB.freeConnection(connName,connection);
  }

  /**
   * 得到数据库链接
   * @return 数据库连接
   */
  public Connection getConnection(String ProjectName,String connName)
  {
    return null;
  }

  /**
   * 返回数据库链接，报表引擎使用完毕链接后将调用此方法，以便于用户将链接放置入连接池等操作。
   * @param conn 连接
   */
  public void freeConnection(String ProjectName,String connName,Connection conn)
  {	
		SessionFactory.getSession().close();
	}

}
