package com.yanding.demo;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.syssoft.report.ReportSubmitDataUserExpand;
import com.yanding.db.ConnectionFactory;
/**
 * <p>Title: �����ύ���ݵ�Demo</p>
 * <p>Description: ������˵�����ʹ�ñ����ύ������չ������ñ����ύ����������</p>
 */

public class ReportSubmitExpendDemo extends ReportSubmitDataUserExpand
{
  
	static Logger log = Logger.getLogger(ReportSubmitExpendDemo.class);
	private static ConnectionFactory DBConn = new ConnectionFactory(); //���ǻ����ݿ�������

  /**
   * �̳е����ݴ�����
   */
  public void DoReportSubmitData()
  {
    if ((this.SubmitData == null) || (this.SubmitReport == null)) return;

/*

    log.debug("��ʼ����["+this.SubmitReport.getReportName()
                       + "----"+this.SubmitReport.getReportDisplayName()+"]�ύ������");
*/
    //��ʼ���ύ������
    this.SubmitData.FistSubmitData();
    //ѭ����������
    while (this.SubmitData.HasmoreSubmitData()) {
      String key = this.SubmitData.getSubmitDataKey();
      Object[] fields = this.SubmitData.getSubmitDataFields();
      log.debug("�ؼ���Ϊ��" + key);
      for (int i=0;i<fields.length;i++) {
       String fieldname = (String)fields[i];
       String fieldvalue = this.SubmitData.getSubmitDataValue(fieldname);
       log.debug("          �ֶ�����" + fieldname +"   ֵ��"+fieldvalue);
      }

      //����һ������������
      this.SubmitData.NextSubmitData();
    }
/*
    log.debug("��������["+this.SubmitReport.getReportName()
                       + "----"+this.SubmitReport.getReportDisplayName()+"]�ύ������");
*/

    //���洦���ύ������
    if (this.SubmitReport.getReportName().equalsIgnoreCase("Sys_Online_Order"))
    {
      try
      {
        String OrderID = Sys_Online_Order();
        //���óɹ���Ϣ�������
        this.setReportSubmitResult(true);
        //�����������ʾ����Ϣ
        this.setReportSubmitMessage(true, "�ɹ�������������ܣ���л���֧�֡�");
        //���ò�����ʾ�ύ��ť
        this.setReportSubmitParam("Report_Control_SubmitInfo", Boolean.FALSE);
        //��������˲��ٿ�����
        this.setReportSubmitParam("Report_Control_DisableWrite", Boolean.TRUE);

        /*

        //�����������Ҫˢ����ʾ�ı����������Ҫ�����ˢ�±���������ΪFalse�����
        //���ó�True�������� setRefshReportBody ����
        this.setReportSubmitParam("Report_Control_RefshReport", Boolean.TRUE);

        //�����µı�������
        SysReportEngineFace  Engine = new SysReportEngine();
        SysReportsParams params = new SysReportsParams();
        Engine.setParams((SysReportsParamsFace)params);
        params.putReportparam("OrderID",OrderID);
        Engine.insertReport(this.SubmitReport.getReportName());
        //Engine.insertReport("Sys_User_Info_Report");
        Engine.outEncodeData();
        //���ñ���ˢ������
        this.setRefshReportBody(Engine.getoutEncodeData());

        */
      }catch(Exception ex)
      {
        this.setReportSubmitResult(false);
        this.setReportSubmitMessage(true, "���������ϸ��Ϣ���� \n" + ex.getMessage());
        this.setReportSubmitParam("Report_Control_SubmitInfo", Boolean.TRUE);
        this.setReportSubmitParam("Report_Control_DisableWrite", Boolean.FALSE);
      }



    } else {
      this.setReportSubmitParam("Report_Control_SubmitInfo", Boolean.TRUE);
      this.setReportSubmitMessage(true, "�ɹ����������ύ���ܣ���л���֧�֡�");
    }
  }

  private String Sys_Online_Order() throws Exception
  {
    String UserName, UserDuty, shuliang, Tel, SendID, jiaofushijian,
        jiaofudidian, FromSee, Email, CompanyName, banben, zhiwu;

    //��ʼ���ύ������
    this.SubmitData.FistSubmitData();
    UserName = this.SubmitData.getSubmitDataValue("UserName");
    UserDuty = this.SubmitData.getSubmitDataValue("UserDuty");
    shuliang = this.SubmitData.getSubmitDataValue("shuliang");
    Tel = this.SubmitData.getSubmitDataValue("Tel");
    SendID = this.SubmitData.getSubmitDataValue("SendID");
    jiaofushijian = this.SubmitData.getSubmitDataValue("jiaofushijian");
    jiaofudidian = this.SubmitData.getSubmitDataValue("jiaofudidian");
    FromSee = this.SubmitData.getSubmitDataValue("FromSee");
    Email = this.SubmitData.getSubmitDataValue("Email");
    CompanyName = this.SubmitData.getSubmitDataValue("CompanyName");
    banben = this.SubmitData.getSubmitDataValue("banben");
    zhiwu = this.SubmitData.getSubmitDataValue("zhiwu");

    int shul = 0;

    try
    {
      shul = Integer.parseInt(shuliang);
    }catch(Exception ex)
    {
      throw new Exception("������������Ϊ���֡�");
    }

    if ((shul < 0)  || (shul > 3))
      throw new Exception("��������������1��2֮�������");

    if ((Email.indexOf("@") <= 0))
      throw new Exception("Email ����Ч�ĸ�ʽ");

    try {
      Connection conn = DBConn.getConnection("default");
      Statement m_Stmt = conn.createStatement();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String datetime = format.format(new Date());


      String sql = "insert into Sys_Online_Order("
       + "UserName, UserDuty, shuliang, Tel, SendID, jiaofushijian,"
       + "jiaofudidian, FromSee, Email, CompanyName, banben, zhiwu)"
       +"  values('"
       + UserName + "','"+UserDuty+"',"+shul+",'" +Tel +"','"+ SendID +"','"
       + jiaofushijian + "','"+jiaofudidian+"',"+FromSee+",'" +Email +"','"+ CompanyName +"','"
       + banben + "','"+zhiwu+"')";

      m_Stmt.executeUpdate(sql);
      m_Stmt.close();
      DBConn.freeConnection("default",conn);
      return SendID;
    }catch(Exception ex) {
      throw new Exception("���ݴ��������¼�飡");
    }

  }
}