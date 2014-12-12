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

		<title>证件验证</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>

	<body>
		<br>
		<form action="papervaliquery.do" method="post">
			选择证件类型：
			<select name="papertype">
				<option value="2">
					农村低保证
				</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;验证码：
			<input name="valicode" value="" size="50" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="查询" />
		</form>
		<br>
		<c:if test="${'ok'==requestScope.result}">
		家庭编号：
		<c:out value="${requestScope.familyno}"></c:out>
		&nbsp;&nbsp;&nbsp;&nbsp;户主姓名：
		<c:out value="${requestScope.mastername}"></c:out>
			<br>
			<br>
			<table width="80%" cellpadding="0" cellspacing="0" class="table7"
				align="center">
				<tr>
					<th>
						证件名称
					</th>
					<th>
						证件状态
					</th>
				</tr>
				<tr>
					<td>
						<c:out value="${requestScope.papername}"></c:out>
					</td>
					<td>
						<c:if test="${'1'==requestScope.flag}">在用</c:if>
						<c:if test="${'0'==requestScope.flag}">作废</c:if>
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${'ok'!=requestScope.result}">
			<br>
			<br>
			<p align="center">
				<c:out value="${requestScope.result}"></c:out>
			</p>
		</c:if>
	</body>
</html>
