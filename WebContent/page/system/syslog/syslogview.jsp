<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>²é¿´</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="../../css/tree.css" type="text/css"></link>
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
  </head>
  
  <body>
  <div>
   <logic:present name="cap" scope="request">
  <bean:write name="cap" scope="request"/>
  </logic:present>
  </div>
  <logic:present name="table" scope="request">
  <bean:write name="table" scope="request" filter="false"/>
  </logic:present>
  </body>
</html:html>
<script type="text/javascript">
<!--
 function changdis(num){
 	var td =document.getElementById("c"+num);
 	if(td.style.display=="none"){
 	td.style.display="block";
 	}else{
 	td.style.display="none";
 	}
 }
//-->
</script>
