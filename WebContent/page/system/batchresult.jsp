<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>


<script language="JavaScript" type="text/javascript"> 
 function refreshParent() { 
 window.opener.location.href = window.opener.location.href; 
 if (window.opener.progressWindow) { window.opener.progressWindow.close(); 
 } window.close(); 
 }
 </script>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>批次信息维护</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="../js/Calendar.js"></script>
	<link href="../css/CSS.CSS" rel="stylesheet" type="text/css">
</head>

<%-- <body onblur="this.focus();">--%>
<body>
	<p align="center">
		<br>
		&nbsp;
		<br>
		<logic:present name="result" scope="request">
			<bean:write name="result" scope="request" />
		</logic:present>
		<logic:present name="str" scope="request">
			<bean:write name="str" scope="request" />
		</logic:present>
		&nbsp;
		<br>
		&nbsp;
		<input type='button' name="cl" value="关闭" onclick="refreshParent()" />
		<br>
		<br>
		<br>
	</p>



</body>
</html:html>
