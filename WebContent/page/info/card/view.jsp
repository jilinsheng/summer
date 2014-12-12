<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>¸½¼þä¯ÀÀ</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>
	<%
		String filename = (String) request.getAttribute("filename");
	%>
	<body>
		<div id="wmv">
			<object id="NSPlay" width=400 height=400
				classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95"
				codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715"
				standby="Loading Microsoft Windows Media Player components..."
				type="application/x-oleobject" align="left" hspace="5">
				<param name="AutoRewind" value=1>
				<param name="FileName" value="<%=basePath%>/upload/<%=filename%>">
				<param name="ShowControls" value="1">
				<param name="ShowPositionControls" value="0">
				<param name="ShowAudioControls" value="1">
				<param name="ShowTracker" value="0">
				<param name="ShowDisplay" value="0">
				<param name="ShowStatusBar" value="0">
				<param name="ShowGotoBar" value="0">
				<param name="ShowCaptioning" value="0">
				<param name="AutoStart" value=1>
				<param name="Volume" value="-2500">
				<param name="AnimationAtStart" value="0">
				<param name="TransparentAtStart" value="0">
				<param name="AllowChangeDisplaySize" value="0">
				<param name="AllowScan" value="0">
				<param name="EnableContextMenu" value="0">
				<param name="ClickToPlay" value="0">
				<embed src="1" width="400" height="400" hspace="5" autostart="1"
					align="left" autorewind="1" filename="" showcontrols="1"
					showpositioncontrols="0" showaudiocontrols="1" showtracker="0"
					showdisplay="0" showstatusbar="0" showgotobar="0"
					showcaptioning="0" volume="-2500" animationatstart="0"
					transparentatstart="0" allowchangedisplaysize="0" allowscan="0"
					enablecontextmenu="0" clicktoplay="0"></embed>
			</object>
		</div>
	</body>
</html>
