<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTPrivilege"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Element"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统二级菜单</title>
		<style type="text/css">
body {
	background: #006FC0;
	margin: 0px;
	position: 0px;
	font-size: 12px;
}

.submenucap {
	color: #FFFFFF;
	font-weight: 600;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	background-color: #006FC0;
	background-image: url(images/submenuarrow.gif);
	background-repeat: no-repeat;
	background-position: 0px center;
	height: 22px;
	padding: 0 0 0 26;
	margin: 0px;
	width: 284px;
}

.submenucap1 {
	color: #FFFFFF;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	background-color: #006FC0;
	background-repeat: no-repeat;
	background-position: 0px center;
	height: 22px;
}

a:link {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

a:visited {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

a:hover {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

a:active {
	text-decoration: none;
	color: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}
</style>
	</head>
	<body>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr height="20">
				<%
					Document doc = (Document) request.getAttribute("subMenu");
					if (doc != null) {
						Iterator it = doc.getRootElement().elementIterator();
						while (it.hasNext()) {
							out.println(((Element) it.next()).asXML());
						}
					}
				%>
			</tr>
		</table>
	</body>
</html>
<script>
	<!-- 
		function writernav(str){
			//window.parent.frames['leftFrame'].currentnav.innerHTML=str;
		}
	-->
</script>