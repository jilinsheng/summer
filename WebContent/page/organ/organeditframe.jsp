<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%String organizationId =request.getParameter("organizationId"); %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>机构管理框架页面</title>
	</head>
	<frameset rows="24,*" frameborder="no" border="0" framespacing="0">
		<frame src="organtop.jsp?organizationId=<%= organizationId%>" name="top"
			scrolling="no" noresize="noresize" id="top" title="top">
		<frame src="organdetail.do?organizationId=<%= organizationId%>" name="bottom" id="bottom" title="bottom">
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>
