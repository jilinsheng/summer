package com.yanding.db;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.mingda.common.SessionFactory;
import com.syssoft.report.DBConnFace;

/**
 * �̳нӿ�  com.syssoft.report.DBConnFace Ϊ�����ȡ���ݿ�������׼��
 */

public class ConnectionFactory implements DBConnFace{
	static Logger logger = Logger.getLogger(ConnectionFactory.class);
//  private DBConnectionManage webDB = DBConnectionManage.getInstance();

  /**
   * �̳нӿ� DBConnFace ������ȡ���ݿ�����
   * @param connName ���ݿ����ƣ���������������ڶ�������ݿ��������ƶ�Ӧ
   * @return ���ݿ�����
   */
  @SuppressWarnings("deprecation")
public Connection getConnection(String connName)
  {
 // Connection conn = webDB.getConnection(connName);
	  //��session
	 Connection conn = com.mingda.common.SessionFactory.getSession().connection();	  
    return conn;
  }

  /**
   * �̳нӿ� DBConnFace �����ͷ����ݿ�����
   * @param connName ���ݿ����ƣ���������������ڶ�������ݿ��������ƶ�Ӧ
   * @param connection  ���ݿ�����
   */
  public void freeConnection(String connName,Connection connection)
  {
    //webDB.freeConnection(connName,connection);
  }

  /**
   * �õ����ݿ�����
   * @return ���ݿ�����
   */
  public Connection getConnection(String ProjectName,String connName)
  {
    return null;
  }

  /**
   * �������ݿ����ӣ���������ʹ��������Ӻ󽫵��ô˷������Ա����û������ӷ��������ӳصȲ�����
   * @param conn ����
   */
  public void freeConnection(String ProjectName,String connName,Connection conn)
  {	
		SessionFactory.getSession().close();
	}

}
