<%@ page language="java" pageEncoding="GB18030"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mingda.entity.*"%>
<%@page import="java.net.URI"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta   http-equiv="Content-Type"   content="text/html;   charset=GB18030">
		<style>
body {
	background-color: #FFFFFF; padding : 0;
	margin: 0px;
	font-size: 12px;
	line-height: 1.7;
	font-family: Verdana, "宋体", Arial;
	list-style: none;
	scrollbar-face-color: #ECECEC;
	padding: 0;
}

a {
	display: block;
	border-bottom: 0px solid #fff;
	width: 100%;
	padding-top: 2px;
	color: #FFFFFF;
	text-decoration: none;
}

a:link,a:visited {
	color: #000000;
}

a:hover,a:active {
	color: #FFFFFF;
}

ul {
	line-height: 20px;
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	color: #000000;
	width: 100%;
	margin-left: 0px;
	padding: 0px;
	list-style-type: none;
	border: 0;
}

li {
	width: 100%;
	line-height: 20px;
}

.layer1 {
	cursor: hand;
	background-image: url(images/menubg1.gif);
	width: 100%;
	border-bottom: 1px solid #fff;
	padding-top: 2px;
	padding-left: 10px;
	color: #000000;
}

.layer2 {
	cursor: hand;
	background-image: url(images/menubg2.gif);
	width: 100%;
	border-bottom: 1px solid #fff;
	padding-top: 2px;
	padding-left: 0px;
	color: #000000;
}

.layer3 {
	background-image: url(images/menubg3.gif);
	cursor: hand;
	width: 100%;
	border-bottom: 1px solid #fff;
	padding-top: 2px;
	padding-left: 0px;
	color: #FFFFFF;
}

.Closed {
	display: none;
}

.Opened {
	display: block;
}
</style>
<script type="text/javascript">
	function ChangeStatus(o){
		//alert("c"+o.parentNode.id.substr(1));
		var ulobj=document.getElementById("c"+o.parentNode.id.substr(1));
		
	if (ulobj){
	var ulp= ulobj.parentNode.parentNode;
		var aNodeList =ulp.getElementsByTagName("UL");
		for(var i=0;i<aNodeList.length;i++){
			var temp =aNodeList(i);
		temp.className = "Closed";
		}
		
		
			if (ulobj.className == "Opened"){
				ulobj.className = "Closed";
			}else{
				ulobj.className = "Opened";
			}
		} 
	}
</script>
		<title>系统菜单</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta   http-equiv="Content-Type"   content="text/html;   charset=GB18030">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
		<script type="text/javascript" src="js/dtree.js"></script>
	</head>

	<body style="overflow-y: scroll; overflow-x: hidden;">
		<div style="position: relative; top: 0;" id="TextScroll">
			<%
				out.println(request.getAttribute("sysmenu").toString());
			%>
		</div>
	</body>
</html>

