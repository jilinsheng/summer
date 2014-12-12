<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>文件传输浏览</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../js/Calendar2.js"></script>
	</head>

	<body>
		<br>
		<form name="inputform" action="transferrecequery.do" method="post">
			开始时间：
			<input name="btime" type="text" onfocus="setday(this)" size="12" />
			&nbsp;&nbsp;&nbsp;&nbsp; 结束时间：
			<input name="etime" type="text" onfocus="setday(this)" size="12" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input value="查询" type="submit" />
		</form>
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>
					标题
				</th>
				<th>
					作者
				</th>
				<th>
				接收人
				</th>
				<th>
					发布时间
				</th>
				<th>
					操作
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<div align="right">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</div>
	</body>
</html>