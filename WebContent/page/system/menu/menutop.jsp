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
		<title>My JSP 'menutop.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<%
		String menuid = request.getParameter("menuid");
		String pid = request.getParameter("pid");
		String type = request.getParameter("type");
	%>
	<body>
		<table width="100%" cellpadding="0" cellspacing="0" width="100%"
			class="table7">
			<tr>
				<th align="left">
					<%
						if ("0".equals(type)) {
					%>
					<a href="menuinit.do?menuid=<%=menuid%>&pid=<%=pid%>"
						target="bottom">修改当前菜单</a>
					<%
						}
					%>
					&nbsp;&nbsp;
					<a href="menuinit.do?menuid=<%=menuid%>&pid=-2" target="bottom">新建下级菜单</a>&nbsp;&nbsp;
					<a href="menuchild.do?menuid=<%=menuid%>" target="bottom">调整子菜单顺序</a>
				</th>
			</tr>
		</table>
	</body>
</html>
