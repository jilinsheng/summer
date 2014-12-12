package com.yanding.demo;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.syssoft.report.ReportEngineUserExpand;
import com.syssoft.report.SysReportsParams;

/**
 * <p>Title: 对报表引擎的扩展</p>
 * <p>Description: 本类继承引擎扩展类：com.syssoft.report.ReportEngineUserExpand
                   实现以下几个功能：<br>
                   1、对带参数的定时运行报表、不带参数的定时运行报表、以及历史数据的处理 <br>
                   2、对带参数的数据缓存报表、不带参数的数据缓存报表、以及历史数据的处理 <br>

                   对于不带参数的定时运行报表以及不带参数的数据缓存报表，
                                           如果不需要保存每次的历史数据则可以不继承此类；<br>
                   缺省不继承情况下：
                             a、定时运行：覆盖上次生成的历史数据
                             b、数据缓存：对已经生成历史数据的任何一次请求均不再生成新的数据
    </p>
 */

/**
 *   定时运行与数据缓存报表如果不设置其历史ID(如：this.ReportHisid 为 null)，则
 *   a、定时运行：每次定时运行结果数据会以报表英文名称为文件名保存历史数据
 *   b、数据缓存：第一次运行结果以报表英文名称为文件名保存历史数据，下次运行
 *      如果没有设置 this.useCachereport = false 则取上次生成的历史数据，
 *      不会查询新的数据。
 */

public class ReportsEngineExpandDemo extends ReportEngineUserExpand
{
	static Logger log = Logger.getLogger(ReportsEngineExpandDemo.class);
	/**
  * ReportEngineUserExpand 内具有的public变量
  * public SysReportEngineFace ReportEngine ： 报表引擎类
  * public SysReportsParamsFace ReportParams ：报表参数类，内有当前报表的所有参数
  * public SysReportInfo PrepareReport：描述了当前调用的报表的详细信息，可以得到报表的名称、路径、是否定时等信息，可参见类SysReportInfo API描述
  * public boolean useCachereport 对于具有数据缓冲的报表现在是否需要使用缓冲文件
  * public String ReportHisid 历史ID，即保存的定时运行的文件名称，其文件名称的规则是：报表名称 ＋ ReportHisid
  **/

  /**
  * 在报表被编译之前的动作
  * 参数：Reportname 将要处理的报表的名称
  * 可以在这根据 ReportParams 来获取你的报表参数，
          判断报表参数从而决定是否需要新缓冲或者得到你的历史ID
  * 如果你返回的历史ID为空，则会取报表英文名称的历史档案,不存在则重新生成
  *
  * 方法传递过来的 Reportname 是为了兼容历史版本，当前版本可以通过 PrepareReport 得到报表信息
  **/
 public void preparedReportBefore(String Reportname)
 {
   if ((Reportname == null) || (Reportname.length() <=0)) return;
   if (this.PrepareReport == null) return;

   String nowReportName = this.PrepareReport.getReportName();

   /**
    * 无参数的定时运行报表。我希望保存每次自动生成的历史数据以便于客户以后
    * 查阅之前的历史报表。
    * 这张报表是一个月报，每个月生成一次
    */
   if (nowReportName.equalsIgnoreCase("报表英文名称"))
   {
     //得到当前的月份
     Calendar calender = Calendar.getInstance();
     calender.setTime(new Date());
     int nowmonth = calender.get(Calendar.MONTH) + 1;

     /**
      * 设置报表当前浏览的历史ID为 nowmonth，如果当前月份报表历史不存在则会生成
      * 新的历史数据,否则则获取当前月份数据
      */
     this.ReportHisid = "NowMonth_" + Integer.toString(nowmonth);

   }
   /**
    * 带参数的定时运行报表，由于定时运行报表无法从页面获取所需要的参数，
    * 所以必须在此手动将参数传递给报表引擎
    */
   else if (nowReportName.equalsIgnoreCase("报表英文名称"))
   {
     if (this.ReportParams == null) {
       //如果报表参数类为空则生成新的参数类
       this.ReportParams = new SysReportsParams();
       this.ReportEngine.setParams(this.ReportParams);
     }

     /**
      * 设置定时运行报表所需要的参数，其参数可以直接设置，或从你的数据库得到
      * 或从你的文件中得到。
      */
     this.ReportParams.putReportparam("你的参数名称","你的参数值");

     /**
      * 设置数据保存的历史ID，如果不设置或设置的历史ID已经存在则不会根据当前的
      * 参数得到所需要的数据。
      *
      * 为确保历史ID与参数的关联性与唯一性，可以去获得所有参数值的和（字符串相加）的结果
      * 字符串的一个MD5码作为获取历史ID的关键字。
      */

     //以下为获取所有需要的参数的值的字符串相加的结果
     //................
     //................
     //String ParamValuesMd5 = MD5.getMd5string(.......);
     //从你的数据库或者文件中查找此MD5串对应的历史ID
     //this.ReportHisid = 从数据库或者文件中查找参数值MD5码对应的历史ID;
   }
   /**
    * 以下两个是数据缓存报表，其与定时运行报表的区别在于
    * a、定时运行报表在定时运行时无论历史是否存在都重新生成历史数据
    * b、数据缓存报表在没有设置 this.useCachereport = false 的情况下只要发现
    *    this.ReportHisid 的历史数据则取历史数据，不再生成新的数据
    * c、定时运行报表由系统自动激活运行，数据缓存报表由用户点击页面链接（手动）
    *    激活运行。
    */
   else if (nowReportName.equalsIgnoreCase("报表英文名称"))
   {
     /**
      * 与定时运行一样，你可以设置历史ID，也可以不设置历史ID。
      * 设置了历史ID则按照历史ID查询历史数据，存在则取历史数据，不存在则生成新的数据
      *
      * 不设置历史ID，则历史ID缺省为报表英文名称。
      */
   }
   else if (nowReportName.equalsIgnoreCase("报表英文名称"))
   {
     if (this.ReportParams == null) {
       //如果报表参数类为空则生成新的参数类
       this.ReportParams = new SysReportsParams();
       this.ReportEngine.setParams(this.ReportParams);
     }

     /**
      * 数据缓存的参数在页面内已经输入，此处可以得到页面输入的参数值
      */
     String ParamValue = this.ReportParams.getReportparam("你的参数名称");

     /**
      * 为确保历史ID与参数的关联性与唯一性，可以去获得所有参数值的和（字符串相加）的结果
      * 字符串的一个MD5码作为获取历史ID的关键字。
      */
     //以下为获取所有需要的参数的值的字符串相加的结果
     //................
     //................
     //String ParamValuesMd5 = MD5.getMd5string(.......);
     //从你的数据库或者文件中查找此MD5串对应的历史ID
     //this.ReportHisid = 从数据库或者文件中查找参数值MD5码对应的历史ID;
   }

 }

 /**
 * 在报表被编译之后的动作
 * 参数：Reportname 将要处理的报表的名称
 * 可以在这根据 ReportParams 来获取你的报表参数，从而生成新的历史ID，
           并把你的新的历史ID保存到你的数据库。
 * 如果你返回的历史ID为空，则会取报表英文名称为历史档案
 **/
 public void preparedReportAfter(String Reportname)
 {
   if (this.PrepareReport == null) return;

   String nowReportName = this.PrepareReport.getReportName();
   /**
    * 这个方法是报表运行结束后的方法，其主要作用是生成当前一次查询的历史ID，提交
    * 给报表引擎，以帮助报表引擎按照历史ID保存历史数据。
    *
    * 如果设置的历史ID为已经存在的历史ID，则不会保存当前报表数据。
    */

   /**
    * 以下只以带参数的数据缓存报表为例，其他报表处理方式一样。
    */
   if (nowReportName.equalsIgnoreCase("报表英文名称"))
   {
     /**
      * 数据缓存的参数在页面内已经输入，此处可以得到页面输入的参数值
      */
     String ParamValue = this.ReportParams.getReportparam("你的参数名称");

     /**
      * 为确保历史ID与参数的关联性与唯一性，可以去获得所有参数值的和（字符串相加）的结果
      * 字符串的一个MD5码作为获取历史ID的关键字。
      */
     //以下为获取所有需要的参数的值的字符串相加的结果
     //................
     //................
     //String ParamValuesMd5 = MD5.getMd5string(.......);
     //this.ReportHisid = 生成历史ID，如当前的时间：Long.toString(System.currentTimeMillis());
     //保存当前历史ID与MD5码到数据库，以便于下次浏览报表时获取历史ID
     //.........................
     // .........................
   }
 }

 /**
  * 以下两个方法适合于所有报表，上面的两个方法适合于定时运行或者数据缓冲报表
  *
  */

 /**
 * 在报表被编译之后的动作
 * 输出HTML的报表流，你可以将其保存在磁盘上发送给某个人。文件后缀.html;.htm
 *
 * 值得注意的是：这个方法与以上两个方法没有逻辑上的必然联系，可以说是没有任何关系。
 * 这个方法提供一个便利的途径让用户可以把报表结果保存在一个Html文件内，以发送给
 * 领导查阅或者作为一个备份。
 **/
 public void outpreparedReporttoHtml(ByteArrayOutputStream out)
 {
   try
   {

     /*
     FileOutputStream file = new FileOutputStream("C:/ReportEngineViewer.html");
     file.write(out.toByteArray());
     file.close();
     */
    this.ReportEngine.reportRunState();
   }catch(Exception ex) {
   }
 }

 /**
  * 报表运行后的状态信息
  */
 public void reportRunState(boolean sucess,String Message)
 {
   if (this.PrepareReport == null) return;

   log.debug("引擎扩展类输出的信息：报表:" + this.PrepareReport.getReportDisplayName()
                      +" 运行结果：" + sucess +"  详细：" + Message);
 }


}