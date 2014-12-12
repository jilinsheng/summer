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
		<title>救助发放查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>
	<body>
		<br>
		<form name="inputform" action="implementionmonthquery.do"
			method="post">
			选择月份：
			<select name="monthname">
				<c:forEach items="${requestScope.monlist}" var="rs">
					<option value="${rs.monname}">
						${rs.detail}
					</option>
				</c:forEach>
			</select>
			查询项目：
			<select name="type">
				<option value="all" selected="selected">
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
			&nbsp; 所属：
			<select name="orgid">
				<c:forEach items="${requestScope.orglist}" var="rs">
					<option value="${rs.organizationId}">
						${rs.orgname }
					</option>
				</c:forEach>
			</select>
			&nbsp;
			<input type="submit" value="查询" /> <button onclick="exportXLS()">
				导出excel
			</button></form>
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
				银行账号
				</th>
				<th>
					金额
				</th>
				<th>
					操作
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<p align="right">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</p>
	</body>
</html>
<script>
	function exportXLS() {
		window
				.open(
						"../system/exportfile/exportExcel.do",
						"",
						"height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
</script>
