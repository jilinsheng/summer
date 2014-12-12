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

		<title></title>

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
		<br />
		<div align="left">
			下载生成文件
		</div>
		<br />
		<table width="100%" cellpadding="0" cellspacing="0" border="1"
			class="table7">
			<c:forEach var="rs" items="${requestScope.filelist}">
				<tr>
					<td>
						<c:out value="${rs[3]}" />
					</td>
					<td>
						<a
							href="billcreatefile.do?orgname=${rs[3]}&type=1&orgid=${rs[4]}&monthid=${requestScope.monthid}&typename=${rs[0]}&defineid=${requestScope.defineid}">
							<c:out value="${rs[0]}"/>
						</a>
					</td>
					<td>
						<a
							href="billcreatefile.do?orgname=${rs[3]}&type=2&orgid=${rs[4]}&monthid=${requestScope.monthid}&typename=${rs[1]}&defineid=${requestScope.defineid}">
							<c:out value="${rs[1]}" />
						</a>
					</td>
					<td>
						<a
							href="billcreatefile.do?orgname=${rs[3]}&type=3&orgid=${rs[4]}&monthid=${requestScope.monthid}&typename=${rs[2]}&defineid=${requestScope.defineid}">
							<c:out value="${rs[2]}" /> </a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
