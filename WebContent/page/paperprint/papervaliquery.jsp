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

		<title>֤����֤</title>

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
			ѡ��֤�����ͣ�
			<select name="papertype">
				<option value="2">
					ũ��ͱ�֤
				</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;��֤�룺
			<input name="valicode" value="" size="50" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="��ѯ" />
		</form>
		<br>
		<c:if test="${'ok'==requestScope.result}">
		��ͥ��ţ�
		<c:out value="${requestScope.familyno}"></c:out>
		&nbsp;&nbsp;&nbsp;&nbsp;����������
		<c:out value="${requestScope.mastername}"></c:out>
			<br>
			<br>
			<table width="80%" cellpadding="0" cellspacing="0" class="table7"
				align="center">
				<tr>
					<th>
						֤������
					</th>
					<th>
						֤��״̬
					</th>
				</tr>
				<tr>
					<td>
						<c:out value="${requestScope.papername}"></c:out>
					</td>
					<td>
						<c:if test="${'1'==requestScope.flag}">����</c:if>
						<c:if test="${'0'==requestScope.flag}">����</c:if>
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
