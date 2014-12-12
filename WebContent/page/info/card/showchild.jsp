<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>显示详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	color: #000000;
	background-color: #f7f7f7;
}

table {
	border-collapse: collapse;
	border: 1px solid #999;
	border-width: 1px 0 0 1px;
	margin: 2px 0 2px 0;
	text-align: center;
}

 td {
	height: 23px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	font-size: 12px;
	background-color: #FFF;
	text-align: center;
}

 th {
	height: 23px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	text-align: center;
	font-size: 12px;
	text-align: center;
	background-color: #C9DDFE;
	color:#000000
}

table caption {
	width: auto;
	text-align: center;
	background-color: #A8CCEA;
	padding: 0px;
	margin: 0px;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.familymenu {
	border: solid 0px;
	border-color: #86CFEE;
	height: 18px;
	padding: 0;
	margin: 0
}

.tablelist th {
	color: #ffffff;
	font-size: 12px;
	font-style: normal;
	height: 20px;
	padding-left: 4px;
	background-color:#0066cc;
	width:120px;
}

.tablelist td {
	color: #000000;
	font-size: 12px;
	font-style: normal;
	padding-left: 4px;
	height: 20px;
}

.familymenu li {
	float: left;
	width: 90px;
	text-align: center;
	cursor: hand;
	height: 18px;
	background-color: #A8CCEA
}

.familymenu ul {
	list-style: none;
	padding: 0px;
	margin: 0px;
	height: 18px;
	width: 100%;
	background-color: #A8CCEA;
}

.listdivc {
	height: 18px;
	font-size: 12px;
	padding: 0px;
	margin: 0px;
}

.listdiv {
	height: 18px;
	font-size: 12px;
	padding: 0;
	margin: 0;
	display: block
}

.btn {
	BORDER-RIGHT: #86CFEE 1px solid;
	PADDING-RIGHT: 0px;
	BORDER-TOP: #86CFEE 1px solid;
	PADDING-LEFT: 0px;
	FONT-SIZE: 12px;
	FILTER: progid :                         DXImageTransform . 
		                 
		     Microsoft .      
		     
		
		  
		
		 
		   
		
		Gradient(GradientType =                         0, StartColorStr =    
		               
		      #FFFFFF, EndColorStr =            
		            #9DBCEA);
	BORDER-LEFT: #86CFEE 1px solid;
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 0px;
	PADDING-BOTTOM: 0px;
	BORDER-BOTTOM: #86CFEE 1px solid
}

#memberother ul {
	margin: 0 0 0 5px;
}

#memberother img.s {
	width: 34px;
	height: 18px;
}

#memberother .Opened img.s {
	background: url(/db/page/images/tree/opened.gif) no-repeat 0 1px;
}

#memberother .Closed img.s {
	background: url(/db/page/images/tree/closed.gif) no-repeat 0 1px;
}

#memberother .Child img.s {
	background: url(/db/page/images/tree/child.gif) no-repeat 13px 2px;
}

#memberother {
	float: left;
	width: 100%;
	border: 1px solid #99BEEF;
	color: inherit;
	margin: 1px;
	padding: 1px;
}
-->
</style>
  <body>
  <p><bean:write filter="false" name="html"/></p>
  <p><input value="关闭" type="button" onclick="window.close()"/></p>
  </body>
</html>
