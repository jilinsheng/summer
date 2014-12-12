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

		<title>查看证件打印情况</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>

	<body>
		<br>
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
				<th>
					打印时间
				</th>
			</tr>
			<c:forEach var="rs" items="${requestScope.rlist}">
				<tr>
					<td>
						<c:out value="${rs.implTPapertype.papername}"></c:out>
					</td>
					<td valign="middle">
						<c:if test="${rs.flag==1}">
						在用
						</c:if>
						<c:if test="${rs.flag==0}">
						作废
						</c:if>
					</td>
					<td>
						<c:out value="${rs.opttime}"></c:out>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
