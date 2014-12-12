<%
/**
 *	$ÎÄ¼þ: ReportEngine.jsp $
 *	$°æ±¾: 2.0.4 $
 *	$Date: 2009/07/24 01:44:11 $
 */
%>

<%@ page import="java.io.*,java.util.*,
                 com.syssoft.report.*,
		 com.syssoft.report.data.*,
		 com.syssoft.report.util.SysReportEncodeUtil" %>
<%
	Enumeration paramN=request.getParameterNames();
        SysReportsParams params = new SysReportsParams();
	while( paramN.hasMoreElements() ) {
	    String name=(String)paramN.nextElement();
	    String value=(String)request.getParameter(name);
	    value = SysReportEncodeUtil.ISOToGB(value);

	    params.putReportparam(name,value);
	}
	SysReportEngine engine = new SysReportEngine(request,response);
	engine.setParams(params);
	String ReportName = params.getReportparam("ReportName");
 	
 	String ReportProjectName = request.getParameter("Sys_ProjectName");
 	if ((ReportProjectName == null) || (ReportProjectName.length() <=0))
 	 ReportProjectName = null;
	
	engine.insertReport(ReportProjectName,ReportName);
	out.write(engine.toStringBuffer().toString());
%>