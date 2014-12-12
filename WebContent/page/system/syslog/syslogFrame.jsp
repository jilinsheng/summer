<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>人员管理框架页面</title>
</head>
<%String nodeName=(String)request.getParameter("nodeName");
String nodeId=(String)request.getParameter("nodeId");
 %>
<frameset cols="225,*" frameborder="no" border="0" framespacing="0">
  <frame src="syslogtable.do?nodeName=<%=nodeName %>&nodeId=<%=nodeId%>" name="leftFrame" scrolling="auto" noresize="noresize" id="leftFrame" title="leftFrame">
  <frame src="syslogview.jsp" name="mainFrame" id="mainFrame" title="mainFrame">
</frameset>
<noframes><body>
</body>
</noframes></html>
