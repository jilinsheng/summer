<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<style>
/*简单定义了一下全局*/
body {
	background-color: #006FC0;
	position: absolute;
	padding: 3px;
	margin: 0px;
	font-size: 12px;
}

td {
	font-size: 12px;
	font-family: "宋体";
	color: #FFFFFF;
}
</style>
</head>
<body style="border-top: 1px solid #FFFFFF;">
	<table width="100%">
		<tr>
			<td valign="middle" align="left" width="50%">
				欢迎您：
				<bean:write name="employee" property="sysTOrganization.fullname"
					scope="session" />
				&nbsp;
				<bean:write name="employee" property="sysTEmpext.name" scope="session" />
			</td>
			<td align="right" valign="middle" width="50%">
				<div id="webtime">
					<script>setInterval("webtime.innerHTML=new Date().toLocaleString();",1000);</script>
				</div>
			</td>
		</tr>
	</table>
</body>
</html:html>
<%--<div id="webjx"><script>setInterval("webjx.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);</script></div>--%>
