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
 * <p>Title: ��չ����Դ���ֶ���ʾ����</p>
 * <p>Description:
 *    ��չ����Դ�����Demo��Ը�������Դ[���ӵĺ����ǲ���һ���SQL�����ѣ�
 *              ���߸����޷��õ���Ҫ�����ݼ���]
 *    ����û�ֻ��Ҫ������Դ�е�ĳ���ֶν�����չ������ֱ�Ӽ̳���ReportRecordChange��Ȼ����
 *        �����������ָ��������Դ�����ࡱ���ɡ�
 *    ����ģ��׶���������������ĵ������а���һЩ����Դ�����ɣ��ǳ����㡢��ݡ�
 * </p>
 * <p>Company: �׶����</p>
 */

/**
 * ���еĻ������ݽṹ����һ��SQL��䣬Ȼ���ڴ�SQL���ṹ������һ���ֶΣ���ͨ��ִ�����������õ����ֶε�ֵ��
 * ��û�п����õĻ����ṹ�������ڱ�������������ж������ݽṹ���������ο�������
 */
public class ExpandFieldDemo  extends DataSourceUserExpand
{
	static Logger log = Logger.getLogger(ExpandFieldDemo.class);
	private ConnectionFactory DBConn = new ConnectionFactory(); //���ǻ����ݿ�������

  public void SourceUserExpand() {
    /**
     * DataEngine �Ǳ������洫����������Դ���档
     * ��������Դ����������Դ������������ȡҳ������ȡ�
     */
    /**
     * ������չ�ֶεķ�����
     * 1�����������ֱ�������ֶ�
     * 2���ڱ��������������
     *  DataEngine.insertField((DataSourceFieldFace)new DataSourceField("fieldname","String",20,null));
     */

    ExectData recordex = new ExectData();
    DataEngine.setRecordExpand(recordex);
  }

  class ExectData extends ReportRecordChange {

    public void ChangRecord() {
      DataSourceRecordFace recorddata = getRecord(); //�õ���ǰ�ļ�¼
      String OrgID = recorddata.getFieldValue("OrgID");

      /**
       * ����Ϊִ������SQL��䣬��ͬ���û��ɲ����Լ������ݿ����������ִ�С�
       * ���в��ñ�׼������
       */
      String SQL = "select c.climename from Sys_Clime_Info c,Sys_Org_Clime oc "
          + " where oc.climeid=c.climeid and oc.orgid=" + OrgID;
      String ORGClimes = "";

      String connectionName = this.DataEngine.getDatabaseConnectionName();
      try {
        Connection conn = DBConn.getConnection(connectionName);
        if (conn == null)
          throw new Exception("�����޷���ȡ���ݿ����ӣ�");

        PreparedStatement pstate = conn.prepareStatement(SQL);
        try {
          //ִ��
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