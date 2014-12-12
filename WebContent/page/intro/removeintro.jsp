<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
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

		<title>注销家庭</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
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
			注销家庭及恢复
		</p>
		<ul class="styul">
			<li>
				通过左侧查询窗口，得出家庭列表。
			</li>
			<li>
				点击"<img src="../images/check1.jpg"></img>"标记，注销家庭。
			</li>
			<li>
				填写注销理由，点击按钮保存信息。
			</li>
			<li>
				可以通过菜单上"恢复注销居民"，恢复已经注销的家庭。
			</li>
		</ul>
	</body>
</html>
