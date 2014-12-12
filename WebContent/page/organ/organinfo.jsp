<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>机构迁移后信息</title>

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

	<body>
		<br>
		迁移前信息
		<br>
		<table class="table7" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th>
					机构名称
				</th>
				<th>
					户数
				</th>
				<th>
					人数
				</th>
			</tr>
			<c:forEach items="${requestScope.blist}" var="rs">
				<tr>
					<td>
						<c:out value="${rs[0]}"></c:out>
					</td>
					<td>
						<c:out value="${rs[1]}"></c:out>
					</td>
					<td>
						<c:out value="${rs[2]}"></c:out>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		迁移后信息
		<br>
		<table class="table7" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th>
					机构名称
				</th>
				<th>
					户数
				</th>
				<th>
					人数
				</th>
			</tr>
			<c:forEach items="${requestScope.elist}" var="rs">
				<tr>
					<td>
						${rs[0]}
					</td>
					<td>
						${rs[1]}
					</td>
					<td>
						${rs[2]}
					</td>
				</tr>
			</c:forEach>
		</table>
		<p align="center">
		${requestScope.resultstr}
		</p>
	</body>
</html>
