<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'currentnav.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
body {
	background: #006FC0;
	margin: 0px;
	position: 0px;
	font-size: 12px;
}

.submenucap {
	color: #FFFFFF;
	font-size: 12px;
	background-color: #006FC0;
	background-image: url(/db/page/images/submenuarrow.gif);
	background-repeat: no-repeat;
	background-position: 0px center;
	height: 20px;
	padding: 0 0 0 26;
	margin: 0px;
	width: 284px;
}

.submenucap1 {
	color: #FFFFFF;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	background-color: #35A9FF;
	background-repeat: no-repeat;
	background-position: 0px center;
	height: 20px;
}

a:link {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

a:visited {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

a:hover {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

a:active {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}
</style>
	</head>
	<body>
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td class="submenucap">
					当前位置:<span id="currentnav">
					</span>
				</td>
				<td width="22">
                <img src="images/queryico.gif" alt="查询器">
				</td>
			</tr>
		</table>
	</body>
</html>
