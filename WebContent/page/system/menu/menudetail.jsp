<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>菜单项目管理说明</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<style type="text/css">
<!--
.styul {
	padding-left: 24px;
	word-spacing: inherit;
	line-height: 30px;
	list-style-type: decimal;
	color: #006699;
	font-size: 12px;
}
-->
</style>
	<body class="styul">
		<p>
			菜单项目管理说明
		</p>
		<ul class="styul">
			<li>
				修改菜单信息
			</li>
			<li>
				创建子菜单
			</li>
			<li>
				对子菜单排序
			</li>
		</ul>
	</body>
</html>

