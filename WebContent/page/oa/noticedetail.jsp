<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base>
    
    <title>通知公告</title>
    
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
	margin: 0;
	padding: 0;
	font-family: "宋体";
	font-size: 12px;
}

.leftstyle {
	background: url(../images/newsleft.gif) no-repeat;
	background-position: left;
	width: 140px;
	height: 25px;
}

.rightstyle {
	background: url(../images/newsright.gif) no-repeat;
	background-position: right;
	height: 25px;
}

.centerstyle {
	background: url(../images/newscenter.gif);
	background-repeat: repeat;
}

.borderstyle {
	border-width: 0px 1px 1px 1px;
	border-style: solid;
	border-color: #C2523A;
}

.captionstyle {
	background: url(../images/leftbtn.gif) no-repeat;
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	font-weight: 600;
	padding-left: 15px;
	padding-top: 3px;
}

.more {
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	font-weight: 400;
}

.content {
	padding-left: 18px;
	padding-top: 3px;
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	background-image: url(../images/index_left1_46.gif);
	background-repeat: no-repeat;
	background-position: 3px;
}

a:link {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}
    </style>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="486" class="leftstyle">
							<span class="captionstyle">通知&amp;公告</span>
						</td>
						<td width="60%" background="center.gif" class="centerstyle">
							<div class="rightstyle"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="borderstyle">
							<table width="100%" cellpadding="0" cellspacing="0">
							<c:out value="${requestScope.notciehtml}" escapeXml="false"></c:out>
							</table>
						</td>
					</tr>
				</table>
  </body>
</html>
