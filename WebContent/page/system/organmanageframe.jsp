<%@ page language="java" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>选择机构操作框架</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>
	<%
	String url = (String) request.getParameter("url");
	%>
	<frameset rows="*" cols="200,*" framespacing="0" frameborder="no"
		border="0">
		<frame src="system/organlist.do?url=<%=url%>" name="organlist"
			scrolling="no" noresize="noresize" />
		<frame src="" name="organ" />
	</frameset>
</html>
