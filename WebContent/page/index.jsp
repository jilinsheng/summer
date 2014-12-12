<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/page/";
			java.net.InetAddress   localhost   =   java.net.InetAddress.getLocalHost();
			String a = request.getRequestURL().substring(0,request.getRequestURL().length()-request.getRequestURI().length());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<TITLE>吉林省农村低保信息管理系统 #<%=localhost.getLocalHost()%><%=a%>#</TITLE>
		<!--吉林市城乡社会救助管理信息系统   -->
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="吉林省,低保,管理系统">
		<meta http-equiv="description" content="吉林省最低生活保障管理信息系统">
	</head>
	<frameset rows="60,8,*" cols="*" frameborder="no" border="0"
		framespacing="0" id="fullrow">
		<frame src="top.jsp" scrolling="no" noresize="noresize" />
		<frame src="contop.jsp" scrolling="no" noresize="noresize"
			title="点击此处显示或隐藏顶部" name="topcon" />
		<frameset cols="150,8,*" frameborder="no" border="0" id="fullcol">
			<frame src="readMenu.do">
			<frame src="conleft.jsp" scrolling="no" noresize="noresize"
				title="点击此处显示或隐藏顶部" name="leftcon">
			<frame id="opreratingzone" name="opreratingzone" src="oa/welcome.do"
				scrolling="no" noresize="noresize">
		</frameset>
	</frameset>
	<noframes></noframes>
</html>
