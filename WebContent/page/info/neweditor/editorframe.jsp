<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String familyId= request.getParameter("nodeId");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>家庭信息维护</title>
</head>
<frameset rows="27,*" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src="editormenu.do?familyId=<%=familyId%>" name="topFrame"  noresize="noresize" scrolling="no" id="topFrame" title="topFrame" />
  <frame src="familybaseinit.do?familyId=<%=familyId%>" name="mainFrame" id="mainFrame" title="mainFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>