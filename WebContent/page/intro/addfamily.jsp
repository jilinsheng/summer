<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base>
    
    <title>创建家庭页面说明</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="../css/style.css" rel="stylesheet" type="text/css">
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
<p>新建家庭</p>
<ul class="styul">
<li>输入姓名、身份证号码，如果通过验证，这两个信息将作为户主信息保留。</li>
<li>点击查询按钮，通过姓名、身份证号验证</li>
<li>如果没有符合条件成员，进入创建新家庭页面。</li>
<li>如果有符合条件的家庭，进入查询结果页面，通过点击家庭编号浏览此家庭的详细信息。</li>
</ul>
</body>
</html>
