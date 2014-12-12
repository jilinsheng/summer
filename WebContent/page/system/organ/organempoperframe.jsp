<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
<title>无标题文档</title>
</head>
<%String selectonno = request.getParameter("onno");%>
<frameset rows="44,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="oneOrganAction.do?selectonno=<%=selectonno%>" name="topFrame" scrolling="no" noresize="noresize" title="topFrame"/>
  <frame src="employeeInitAction.do?selectonno=<%=selectonno%>&qq=1" name="empmainFrame" title="empmainFrame"/>
</frameset>
</html>
