<%
/**
 *	$�ļ�: ReportEngine.jsp $
 *	$�汾: 2.0.4 $
 *	$Date: 2009/07/24 01:44:11 $
 */
%>

<%@ page import="java.io.*,java.util.*,
                 com.syssoft.report.*,
		 com.syssoft.report.data.*,
		 com.syssoft.report.util.SysReportEncodeUtil" %>
<%@ taglib uri="/WEB-INF/sysreport_tag.tld" prefix="sysreport" %>
<%
         Enumeration paramN=request.getParameterNames();
         String ParamValues = null;
         
         /**
         * �����µı������������
         **/
 	       SysReportEngine engine = new SysReportEngine(request,response);
 	       SysReportsParams params = new SysReportsParams();
 	       
 	       //�õ��������ڵ���Ŀ���ƣ����Ϊ�����������Ŀ�в���
 	       String ReportProjectName = request.getParameter("Sys_ProjectName");
 	       if ((ReportProjectName == null) || (ReportProjectName.length() <=0))
 	        ReportProjectName = null;
 	       
 	 
         while( paramN.hasMoreElements() ) {
          String name=(String)paramN.nextElement();
          String[] Values = request.getParameterValues(name);

          name = name.toLowerCase();
          ParamValues = null;
          if (name.equalsIgnoreCase("Sys_ReportName")) {
            for (int i=0; i<=Values.length-1; i++) {
             engine.insertReport(ReportProjectName,Values[i]);
            }
          } else {
            if (name.startsWith("sys_")) {
              for (int i=0; i<=Values.length-1; i++) {
               if (ParamValues == null)
                ParamValues = SysReportEncodeUtil.ISOToGB(Values[i]);
               else
                ParamValues = ParamValues + ","
                     + SysReportEncodeUtil.ISOToGB(Values[i]);
              }
              name = name.substring(4);
              params.putReportparam(name,ParamValues);
            }
          }
        }
	engine.setParams((SysReportsParamsFace)params);
	/**
	* ���뱨��
	**/
	engine.BindReports();
%>

<html>
<head>
  <title>�׶����-���ޱ���MAX Reports��Ʒ��ʾ</title>
</head>

<body  Leftmargin=0 topmargin=0  rightmargin="0" bottommargin="0"> 
<table width="100%" height="100%">
 <tr height="100%"><td valign="top">
     <sysreport:ReportView />
 </td>
 </tr>
</table>
</body>
</html>