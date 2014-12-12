package com.yanding.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.syssoft.report.DataSourceUserExpand;
import com.syssoft.report.data.DataSourceRecordFace;
import com.syssoft.report.data.ReportRecordChange;
import com.yanding.db.ConnectionFactory;

/**
 * <p>Title: 扩展数据源的字段演示列子</p>
 * <p>Description:
 *    扩展数据源。这个Demo针对复杂数据源[复杂的含义是采用一般的SQL语句很难，
 *              或者根本无法得到想要的数据集合]
 *    如果用户只需要对数据源中的某个字段进行扩展，可以直接继承类ReportRecordChange，然后在
 *        报表设计器内指定“数据源辅助类”即可。
 *    请参阅：炎鼎报表设计器帮助文档，其中包含一些数据源处理技巧，非常方便、快捷。
 * </p>
 * <p>Company: 炎鼎软件</p>
 */

/**
 * 本列的基本数据结构来自一个SQL语句，然后在此SQL语句结构上怎加一个字段，并通过执行其他方法得到此字段的值。
 * 如没有可利用的基本结构，可以在报表设计器内自行定义数据结构。更多的请参考帮助。
 */
public class ExpandFieldDemo  extends DataSourceUserExpand
{
	static Logger log = Logger.getLogger(ExpandFieldDemo.class);
	private ConnectionFactory DBConn = new ConnectionFactory(); //初是化数据库连接类

  public void SourceUserExpand() {
    /**
     * DataEngine 是报表引擎传递来的数据源引擎。
     * 包含数据源操作，数据源参数操作，获取页面参数等。
     */
    /**
     * 增加扩展字段的方法：
     * 1、在设计器内直接增加字段
     * 2、在本处调用以下语句
     *  DataEngine.insertField((DataSourceFieldFace)new DataSourceField("fieldname","String",20,null));
     */

    ExectData recordex = new ExectData();
    DataEngine.setRecordExpand(recordex);
  }

  class ExectData extends ReportRecordChange {

    public void ChangRecord() {
      DataSourceRecordFace recorddata = getRecord(); //得到当前的记录
      String OrgID = recorddata.getFieldValue("OrgID");

      /**
       * 以下为执行其他SQL语句，不同的用户可采用自己的数据库操作基类来执行。
       * 本列采用标准方法。
       */
      String SQL = "select c.climename from Sys_Clime_Info c,Sys_Org_Clime oc "
          + " where oc.climeid=c.climeid and oc.orgid=" + OrgID;
      String ORGClimes = "";

      String connectionName = this.DataEngine.getDatabaseConnectionName();
      try {
        Connection conn = DBConn.getConnection(connectionName);
        if (conn == null)
          throw new Exception("报表无法获取数据库连接！");

        PreparedStatement pstate = conn.prepareStatement(SQL);
        try {
          //执行
          pstate.execute();
          ResultSet resulset = pstate.getResultSet();
          try {

            while (resulset.next()) {
              String value = resulset.getString(1);
              if (ORGClimes == "")
                ORGClimes = value;
              else
                ORGClimes = ORGClimes + "," + value;
            }
          }
          catch (Exception ex) {
            ex.printStackTrace();
            if (resulset != null)
              resulset.close();
            throw new Exception(ex.getMessage());
          }
          if (resulset != null)
            resulset.close();
        }
        catch (Exception exx) {
          exx.printStackTrace();
          if (pstate != null)
            pstate.close();
          DBConn.freeConnection(connectionName,conn);
          throw new Exception(exx.getMessage());
        }

        if (pstate != null)
          pstate.close();
        DBConn.freeConnection(connectionName,conn);
      }
      catch (Exception ex) {
        log.debug("Error:" + ex);
      }

      recorddata.setFieldValue("ORGClimes",ORGClimes);
    }
  }
}