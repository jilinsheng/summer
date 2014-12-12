<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>操作区域</title>

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
		<br>
		<table width="100%" cellpadding="0" cellspacing="0" class="table7"
			border="0">
			<tr>
				<th width="20%">
					机构名称:
				</th>
				<td>
					${requestScope.organ.orgname}
				</td>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
	</body>
</html>
