<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>菜单树形列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<link rel="stylesheet" href="../../css/dtree.css" type="text/css"></link>
</head>
<script type="text/javascript" src="../../js/dtree.js"></script>
<body>
	<logic:present name="html" scope="request">
		<bean:write name="html" scope="request" filter="false" />
	</logic:present>
</body>
</html:html>
<script type="text/javascript">
	<!-- 
		function ctlchild(id,pid,type){
			parent.frames['mainFrame'].location.reload('menuenditframe.jsp?'+"menuid="+id+"&pid="+pid+"&type="+type);
		}
	-->
</script>