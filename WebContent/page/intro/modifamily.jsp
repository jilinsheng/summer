<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base>
    
    <title>家庭信息修改说明</title>
    
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
<style type="text/css">
<!--
.styul{
	padding-left:24px;
	word-spacing:inherit;
	line-height: 30px;
	list-style-type:decimal;
	color:#006699;
	font-size:12px;
}
-->
</style>
<body class="styul">
<p>信息维护</p>
<ul class="styul">
<li>通过左侧查询窗口，得出家庭列表。</li>
<li>点击"<img src="../images/check1.jpg"></img>"标记，对此户家庭进行信息维护。</li>
</ul>
</body>
</html>
