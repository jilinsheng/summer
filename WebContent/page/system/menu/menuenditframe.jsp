<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>菜单管理框架页面</title>
	</head>
	<%
		String menuid = request.getParameter("menuid");
		String pid = request.getParameter("pid");
		String type=request.getParameter("type");
	%>
	<frameset rows="24,*" frameborder="no" border="0" framespacing="0">
		<frame src="menutop.jsp?menuid=<%=menuid%>&pid=<%=pid%>&type=<%=type %>" name="top"
			scrolling="no" noresize="noresize" id="top" title="top">
		<frame src="menudetail.jsp" name="bottom" id="bottom" title="bottom">
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>
