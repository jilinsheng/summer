<%@ page language="java" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>统计报表</title>
	</head>
	<%
	String reportname = request.getParameter("reportname");
	String stid=request.getParameter("stid");
	%>
	<frameset rows="50,*" cols="*" framespacing="0" frameborder="no"
		border="0">
		<frame src="reportinit.do?reportname=<%=reportname%>&stid=<%=stid%>" name="topFrame"
			scrolling="yes" noresize>
		<frame src="../welcome.html" name="down">
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>
