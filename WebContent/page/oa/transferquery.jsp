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

		<title>�ļ��������</title>

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
		<form name="inputform" action="transferquery.do" method="post">
			��ʼʱ�䣺
			<input name="btime" type="text" onfocus="setday(this)" size="12" />
			&nbsp;&nbsp;&nbsp;&nbsp; ����ʱ�䣺
			<input name="etime" type="text" onfocus="setday(this)" size="12" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input value="��ѯ" type="submit" />
		</form>
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>
					����
				</th>
				<th>
					����
				</th>
				<th>
				������
				</th>
				<th>
					����ʱ��
				</th>
				<th>
					����
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<div align="right">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</div>
	</body>
</html>