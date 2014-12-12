<%@ page language="java" pageEncoding="GB2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>ÏµÍ³µÇ³ö</title>
</head>
<%
	if (session.getAttribute("employee") != null) {
			session.removeAttribute("employee");
		}
%>
<body>
	<form action="login.jsp" target="_top">
	</form>
	<script language="javascript"> 
		document.forms[0].submit(); 
	</script>
</body>
</html:html>
