<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>����</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>
	<body>
		<br>
		<div align="left">
			<c:out value="${requestScope.str}"></c:out>
		</div>
		<br>
		<form name="inputform" action="report.do" method="post"
			target="_blank">
			ѡ�񱨱�:
			<select name="reportname">
				<option value="colligate">�ͱ��ۺ����ͳ�Ʊ�</option>
				<option value="specialization">�ض���������ͳ�Ʊ�</option>
			</select>
			&nbsp;&nbsp;ѡ���·ݣ�
			<select name="month">
				<c:forEach items="${requestScope.monlist}" var="rs">
					<option value="${rs.accyear}${rs.accmonth}">
						${rs.accyear}��${rs.accmonth}��
					</option>
				</c:forEach>
			</select>
			&nbsp;&nbsp;
			<input type="submit" value="ͳ��" />
		</form>
	</body>
</html>
