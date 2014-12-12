<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = "/usr/upload";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String a = request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>‰Ø¿¿</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>
	<body>
		<c:if test="${annex.remark=='Õº∆¨'}">
			<img src="<%=basePath%>${annex.path}"></img>
		</c:if>
		<c:if test="${annex.remark==' ”∆µ'}">
			<div>
				<object id="vid"
					classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width=427
					height=300>
					<param name="_ExtentX" value="11298">
					<param name="_ExtentY" value="7938">
					<param name="AUTOSTART" value="-1">
					<param name="SHUFFLE" value="0">
					<param name="PREFETCH" value="0">
					<param name="NOLABELS" value="-1">
					<param name="SRC" value="rtsp://<%=a %>${annex.path}";>
					<param name="CONTROLS" value="Imagewindow">
					<param name="CONSOLE" value="clip1">
					<param name="LOOP" value="0">
					<param name="NUMLOOP" value="0">
					<param name="CENTER" value="0">
					<param name="MAINTAINASPECT" value="0">
					<param name="BACKGROUNDCOLOR" value="#000000">
				</object>
				<object id="vid2"
					classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width=427
					height=30>
					<param name="_ExtentX" value="11298">
					<param name="_ExtentY" value="794">
					<param name="AUTOSTART" value="-1">
					<param name="SHUFFLE" value="0">
					<param name="PREFETCH" value="0">
					<param name="NOLABELS" value="-1">
					<param name="SRC" value="rtsp://<%=a %>${annex.path}";>
					<param name="CONTROLS" value="ControlPanel">
					<param name="CONSOLE" value="clip1">
					<param name="LOOP" value="0">
					<param name="NUMLOOP" value="0">
					<param name="CENTER" value="0">
					<param name="MAINTAINASPECT" value="0">
					<param name="BACKGROUNDCOLOR" value="#000000">
				</object>
			</div>
		</c:if>
	</body>
</html>
