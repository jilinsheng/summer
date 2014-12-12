<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>

	<body>
		<table width="90%" cellspacing="0" cellpadding="0" class="table7">
			<tr>
				<th>
					业务名称
				</th>
				<th>
					月份
				</th>
				<th>
					保障金额
				</th>
			</tr>
			<c:forEach var="rs" items="${requestScope.list}">
				<tr>
					<td>
						${rs[2]}
					</td>
					<td>
						${rs[6]} 年 ${rs[7]}月
					</td>
					<td>
						${rs[5]}
					</td>
				</tr>
			</c:forEach>
		</table>
		<p align="center">
			<button onclick="javascript: window.close()">
				关闭
			</button>
		</p>
	</body>
</html>
