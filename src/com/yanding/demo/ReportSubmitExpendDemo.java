package com.yanding.demo;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.syssoft.report.ReportSubmitDataUserExpand;
import com.yanding.db.ConnectionFactory;
/**
 * <p>Title: 报表提交数据的Demo</p>
 * <p>Description: 本例子说明如何使用报表提交数据扩展类来获得报表提交过来的数据</p>
 */

public class ReportSubmitExpendDemo extends ReportSubmitDataUserExpand
{
  
	static Logger log = Logger.getLogger(ReportSubmitExpendDemo.class);
	private static ConnectionFactory DBConn = new ConnectionFactory(); //初是化数据库连接类

  /**
   * 继承的数据处理方法
   */
  public void DoReportSubmitData()
  {
    if ((this.SubmitData == null) || (this.SubmitReport == null)) return;

/*

    log.debug("开始处理["+this.SubmitReport.getReportName()
                       + "----"+this.SubmitReport.getReportDisplayName()+"]提交的数据");
*/
    //初始化提交的数据
    this.SubmitData.FistSubmitData();
    //循环处理数据
    while (this.SubmitData.HasmoreSubmitData()) {
      String key = this.SubmitData.getSubmitDataKey();
      Object[] fields = this.SubmitData.getSubmitDataFields();
      log.debug("关键字为：" + key);
      for (int i=0;i<fields.length;i++) {
       String fieldname = (String)fields[i];
       String fieldvalue = this.SubmitData.getSubmitDataValue(fieldname);
       log.debug("          字段名：" + fieldname +"   值："+fieldvalue);
      }

      //到下一个报表数据行
      this.SubmitData.NextSubmitData();
    }
/*
    log.debug("结束处理["+this.SubmitReport.getReportName()
                       + "----"+this.SubmitReport.getReportDisplayName()+"]提交的数据");
*/

    //下面处理被提交的数据
    if (this.SubmitReport.getReportName().equalsIgnoreCase("Sys_Online_Order"))
    {
      try
      {
        String OrderID = Sys_Online_Order();
        //设置成功信息到浏览端
        this.setReportSubmitResult(true);
        //设置浏览端显示的信息
        this.setReportSubmitMessage(true, "成功处理在线填单功能，感谢你的支持。");
        //设置不再显示提交按钮
        this.setReportSubmitParam("Report_Control_SubmitInfo", Boolean.FALSE);
        //设置浏览端不再可输入
        this.setReportSubmitParam("Report_Control_DisableWrite", Boolean.TRUE);

        /*

        //设置浏览端需要刷新显示的报表，如果不需要浏览端刷新报表则设置为False，如果
        //设置成True则必须调用 setRefshReportBody 方法
        this.setReportSubmitParam("Report_Control_RefshReport", Boolean.TRUE);

        //生成新的报表数据
        SysReportEngineFace  Engine = new SysReportEngine();
        SysReportsParams params = new SysReportsParams();
        Engine.setParams((SysReportsParamsFace)params);
        params.putReportparam("OrderID",OrderID);
        Engine.insertReport(this.SubmitReport.getReportName());
        //Engine.insertReport("Sys_User_Info_Report");
        Engine.outEncodeData();
        //设置报表刷新数据
        this.setRefshReportBody(Engine.getoutEncodeData());

        */
      }catch(Exception ex)
      {
        this.setReportSubmitResult(false);
        this.setReportSubmitMessage(true, "处理错误，详细信息如下 \n" + ex.getMessage());
        this.setReportSubmitParam("Report_Control_SubmitInfo", Boolean.TRUE);
        this.setReportSubmitParam("Report_Control_DisableWrite", Boolean.FALSE);
      }



    } else {
      this.setReportSubmitParam("Report_Control_SubmitInfo", Boolean.TRUE);
      this.setReportSubmitMessage(true, "成功处理数据提交功能，感谢你的支持。");
    }
  }

  private String Sys_Online_Order() throws Exception
  {
    String UserName, UserDuty, shuliang, Tel, SendID, jiaofushijian,
        jiaofudidian, FromSee, Email, CompanyName, banben, zhiwu;

    //初始化提交的数据
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
      throw new Exception("订购数量必须为数字。");
    }

    if ((shul < 0)  || (shul > 3))
      throw new Exception("订购数量必须在1到2之间的数字");

    if ((Email.indexOf("@") <= 0))
      throw new Exception("Email 是无效的格式");

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
      throw new Exception("数据错误，请重新检查！");
    }

  }
}