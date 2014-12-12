package com.yanding.demo;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.syssoft.report.ReportEngineUserExpand;
import com.syssoft.report.SysReportsParams;

/**
 * <p>Title: �Ա����������չ</p>
 * <p>Description: ����̳�������չ�ࣺcom.syssoft.report.ReportEngineUserExpand
                   ʵ�����¼������ܣ�<br>
                   1���Դ������Ķ�ʱ���б������������Ķ�ʱ���б����Լ���ʷ���ݵĴ��� <br>
                   2���Դ����������ݻ��汨���������������ݻ��汨���Լ���ʷ���ݵĴ��� <br>

                   ���ڲ��������Ķ�ʱ���б����Լ��������������ݻ��汨��
                                           �������Ҫ����ÿ�ε���ʷ��������Բ��̳д��ࣻ<br>
                   ȱʡ���̳�����£�
                             a����ʱ���У������ϴ����ɵ���ʷ����
                             b�����ݻ��棺���Ѿ�������ʷ���ݵ��κ�һ����������������µ�����
    </p>
 */

/**
 *   ��ʱ���������ݻ��汨���������������ʷID(�磺this.ReportHisid Ϊ null)����
 *   a����ʱ���У�ÿ�ζ�ʱ���н�����ݻ��Ա���Ӣ������Ϊ�ļ���������ʷ����
 *   b�����ݻ��棺��һ�����н���Ա���Ӣ������Ϊ�ļ���������ʷ���ݣ��´�����
 *      ���û������ this.useCachereport = false ��ȡ�ϴ����ɵ���ʷ���ݣ�
 *      �����ѯ�µ����ݡ�
 */

public class ReportsEngineExpandDemo extends ReportEngineUserExpand
{
	static Logger log = Logger.getLogger(ReportsEngineExpandDemo.class);
	/**
  * ReportEngineUserExpand �ھ��е�public����
  * public SysReportEngineFace ReportEngine �� ����������
  * public SysReportsParamsFace ReportParams ����������࣬���е�ǰ��������в���
  * public SysReportInfo PrepareReport�������˵�ǰ���õı������ϸ��Ϣ�����Եõ���������ơ�·�����Ƿ�ʱ����Ϣ���ɲμ���SysReportInfo API����
  * public boolean useCachereport ���ھ������ݻ���ı��������Ƿ���Ҫʹ�û����ļ�
  * public String ReportHisid ��ʷID��������Ķ�ʱ���е��ļ����ƣ����ļ����ƵĹ����ǣ��������� �� ReportHisid
  **/

  /**
  * �ڱ�������֮ǰ�Ķ���
  * ������Reportname ��Ҫ����ı��������
  * ����������� ReportParams ����ȡ��ı��������
          �жϱ�������Ӷ������Ƿ���Ҫ�»�����ߵõ������ʷID
  * ����㷵�ص���ʷIDΪ�գ����ȡ����Ӣ�����Ƶ���ʷ����,����������������
  *
  * �������ݹ����� Reportname ��Ϊ�˼�����ʷ�汾����ǰ�汾����ͨ�� PrepareReport �õ�������Ϣ
  **/
 public void preparedReportBefore(String Reportname)
 {
   if ((Reportname == null) || (Reportname.length() <=0)) return;
   if (this.PrepareReport == null) return;

   String nowReportName = this.PrepareReport.getReportName();

   /**
    * �޲����Ķ�ʱ���б�����ϣ������ÿ���Զ����ɵ���ʷ�����Ա��ڿͻ��Ժ�
    * ����֮ǰ����ʷ����
    * ���ű�����һ���±���ÿ��������һ��
    */
   if (nowReportName.equalsIgnoreCase("����Ӣ������"))
   {
     //�õ���ǰ���·�
     Calendar calender = Calendar.getInstance();
     calender.setTime(new Date());
     int nowmonth = calender.get(Calendar.MONTH) + 1;

     /**
      * ���ñ���ǰ�������ʷIDΪ nowmonth�������ǰ�·ݱ�����ʷ�������������
      * �µ���ʷ����,�������ȡ��ǰ�·�����
      */
     this.ReportHisid = "NowMonth_" + Integer.toString(nowmonth);

   }
   /**
    * �������Ķ�ʱ���б������ڶ�ʱ���б����޷���ҳ���ȡ����Ҫ�Ĳ�����
    * ���Ա����ڴ��ֶ����������ݸ���������
    */
   else if (nowReportName.equalsIgnoreCase("����Ӣ������"))
   {
     if (this.ReportParams == null) {
       //������������Ϊ���������µĲ�����
       this.ReportParams = new SysReportsParams();
       this.ReportEngine.setParams(this.ReportParams);
     }

     /**
      * ���ö�ʱ���б�������Ҫ�Ĳ��������������ֱ�����ã����������ݿ�õ�
      * �������ļ��еõ���
      */
     this.ReportParams.putReportparam("��Ĳ�������","��Ĳ���ֵ");

     /**
      * �������ݱ������ʷID����������û����õ���ʷID�Ѿ������򲻻���ݵ�ǰ��
      * �����õ�����Ҫ�����ݡ�
      *
      * Ϊȷ����ʷID������Ĺ�������Ψһ�ԣ�����ȥ������в���ֵ�ĺͣ��ַ�����ӣ��Ľ��
      * �ַ�����һ��MD5����Ϊ��ȡ��ʷID�Ĺؼ��֡�
      */

     //����Ϊ��ȡ������Ҫ�Ĳ�����ֵ���ַ�����ӵĽ��
     //................
     //................
     //String ParamValuesMd5 = MD5.getMd5string(.......);
     //��������ݿ�����ļ��в��Ҵ�MD5����Ӧ����ʷID
     //this.ReportHisid = �����ݿ�����ļ��в��Ҳ���ֵMD5���Ӧ����ʷID;
   }
   /**
    * �������������ݻ��汨�����붨ʱ���б������������
    * a����ʱ���б����ڶ�ʱ����ʱ������ʷ�Ƿ���ڶ�����������ʷ����
    * b�����ݻ��汨����û������ this.useCachereport = false �������ֻҪ����
    *    this.ReportHisid ����ʷ������ȡ��ʷ���ݣ����������µ�����
    * c����ʱ���б�����ϵͳ�Զ��������У����ݻ��汨�����û����ҳ�����ӣ��ֶ���
    *    �������С�
    */
   else if (nowReportName.equalsIgnoreCase("����Ӣ������"))
   {
     /**
      * �붨ʱ����һ���������������ʷID��Ҳ���Բ�������ʷID��
      * ��������ʷID������ʷID��ѯ��ʷ���ݣ�������ȡ��ʷ���ݣ��������������µ�����
      *
      * ��������ʷID������ʷIDȱʡΪ����Ӣ�����ơ�
      */
   }
   else if (nowReportName.equalsIgnoreCase("����Ӣ������"))
   {
     if (this.ReportParams == null) {
       //������������Ϊ���������µĲ�����
       this.ReportParams = new SysReportsParams();
       this.ReportEngine.setParams(this.ReportParams);
     }

     /**
      * ���ݻ���Ĳ�����ҳ�����Ѿ����룬�˴����Եõ�ҳ������Ĳ���ֵ
      */
     String ParamValue = this.ReportParams.getReportparam("��Ĳ�������");

     /**
      * Ϊȷ����ʷID������Ĺ�������Ψһ�ԣ�����ȥ������в���ֵ�ĺͣ��ַ�����ӣ��Ľ��
      * �ַ�����һ��MD5����Ϊ��ȡ��ʷID�Ĺؼ��֡�
      */
     //����Ϊ��ȡ������Ҫ�Ĳ�����ֵ���ַ�����ӵĽ��
     //................
     //................
     //String ParamValuesMd5 = MD5.getMd5string(.......);
     //��������ݿ�����ļ��в��Ҵ�MD5����Ӧ����ʷID
     //this.ReportHisid = �����ݿ�����ļ��в��Ҳ���ֵMD5���Ӧ����ʷID;
   }

 }

 /**
 * �ڱ�������֮��Ķ���
 * ������Reportname ��Ҫ����ı��������
 * ����������� ReportParams ����ȡ��ı���������Ӷ������µ���ʷID��
           ��������µ���ʷID���浽������ݿ⡣
 * ����㷵�ص���ʷIDΪ�գ����ȡ����Ӣ������Ϊ��ʷ����
 **/
 public void preparedReportAfter(String Reportname)
 {
   if (this.PrepareReport == null) return;

   String nowReportName = this.PrepareReport.getReportName();
   /**
    * ��������Ǳ������н�����ķ���������Ҫ���������ɵ�ǰһ�β�ѯ����ʷID���ύ
    * ���������棬�԰����������水����ʷID������ʷ���ݡ�
    *
    * ������õ���ʷIDΪ�Ѿ����ڵ���ʷID���򲻻ᱣ�浱ǰ�������ݡ�
    */

   /**
    * ����ֻ�Դ����������ݻ��汨��Ϊ��������������ʽһ����
    */
   if (nowReportName.equalsIgnoreCase("����Ӣ������"))
   {
     /**
      * ���ݻ���Ĳ�����ҳ�����Ѿ����룬�˴����Եõ�ҳ������Ĳ���ֵ
      */
     String ParamValue = this.ReportParams.getReportparam("��Ĳ�������");

     /**
      * Ϊȷ����ʷID������Ĺ�������Ψһ�ԣ�����ȥ������в���ֵ�ĺͣ��ַ�����ӣ��Ľ��
      * �ַ�����һ��MD5����Ϊ��ȡ��ʷID�Ĺؼ��֡�
      */
     //����Ϊ��ȡ������Ҫ�Ĳ�����ֵ���ַ�����ӵĽ��
     //................
     //................
     //String ParamValuesMd5 = MD5.getMd5string(.......);
     //this.ReportHisid = ������ʷID���統ǰ��ʱ�䣺Long.toString(System.currentTimeMillis());
     //���浱ǰ��ʷID��MD5�뵽���ݿ⣬�Ա����´��������ʱ��ȡ��ʷID
     //.........................
     // .........................
   }
 }

 /**
  * �������������ʺ������б�����������������ʺ��ڶ�ʱ���л������ݻ��屨��
  *
  */

 /**
 * �ڱ�������֮��Ķ���
 * ���HTML�ı�����������Խ��䱣���ڴ����Ϸ��͸�ĳ���ˡ��ļ���׺.html;.htm
 *
 * ֵ��ע����ǣ����������������������û���߼��ϵı�Ȼ��ϵ������˵��û���κι�ϵ��
 * ��������ṩһ��������;�����û����԰ѱ�����������һ��Html�ļ��ڣ��Է��͸�
 * �쵼���Ļ�����Ϊһ�����ݡ�
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
  * �������к��״̬��Ϣ
  */
 public void reportRunState(boolean sucess,String Message)
 {
   if (this.PrepareReport == null) return;

   log.debug("������չ���������Ϣ������:" + this.PrepareReport.getReportDisplayName()
                      +" ���н����" + sucess +"  ��ϸ��" + Message);
 }


}