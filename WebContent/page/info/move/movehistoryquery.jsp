<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
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
		<title>迁移历史查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<body>
		<br>
		<form name="inputform" action="movehistoryquery.do" method="post">
			查询项目：
			<select name="type">
				<option value="all" selected>
					全部
				</option>
				<option value="mastername">
					户主姓名
				</option>
				<option value="paperid">
					户主证件号
				</option>
				<option value="famno">
					家庭编号
				</option>
			</select>
			&nbsp;查&nbsp;询&nbsp;值：
			<input type="text" name="value" value="" size="12" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="查询" />
		</form>
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>
					家庭编号
				</th>
				<th>
					户主姓名
				</th>
				<th>
					证件号
				</th>
				<th>
					原所属
				</th>
				<th>
					迁往
				</th>
				<th>
					迁移状态
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
	</body>
</html>
