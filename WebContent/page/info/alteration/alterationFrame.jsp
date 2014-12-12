<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/page/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>户主变更信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="吉林省,低保,管理系统">
		<meta http-equiv="description" content="吉林省最低生活保障管理信息系统">

	</head>
 <%String qmode = request.getParameter("qmode"); %>
 <%String url="/db/page/info/alteration/changemasterinit.do"; %>
<frameset rows="*" cols="187,8,*" framespacing="0" id="fullcol">
			<frame src="/db/page/info/search/infoquery.jsp?qmode=<%=qmode %>&url=<%=url %>" scrolling="no" noresize="noresize"
				title="系统主菜单" frameborder="yes" border="1" />
			<frame src="/db/page/conleft.jsp" scrolling="no" noresize="noresize"
				title="点击此处显示或隐藏左侧菜单" name="leftcon" frameborder="no" border="0" />
			<frame src="/db/page/welcome.jsp" name="operatingzone" id="operatingzone"
				title="系统操作区域" frameborder="yes" border="1" />
		</frameset>
<noframes><body>
</body>
</noframes>
</html>
