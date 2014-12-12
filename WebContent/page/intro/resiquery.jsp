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
		<base>

		<title>创建家庭页面说明</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link href="../css/style.css" rel="stylesheet" type="text/css">
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
			居民查询
		</p>
		<ul class="styul">
			<li>
				填写信息查询
			</li>
			<li>
				可以根据字段信息对结果查询
			</li>
		</ul>
	</body>
</html>