<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>单条日志查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	<script type="text/javascript" src="../js/Calendar2.js"></script>
</head>
<body>
	<br />
	<form action="mysyslog.do" method="post">
		开始时间：
		<input type="text" name="btime" value="" onfocus="setday(this)" />
		&nbsp;&nbsp;&nbsp;&nbsp; 结束时间：
		<input type="text" name="etime" value="" onfocus="setday(this)" />
		&nbsp;&nbsp;
		<input type="submit" value="查询" />
	</form>
	<br />
	<logic:present name="arr" scope="request">
		<table width="98%" cellspacing="0" cellpadding="0" class="table7">
			<tr>
				<th>
					操作时间
				</th>
				<th>
					操作内容
				</th>
				<th>
					IP地址
				</th>
			</tr>
			<logic:iterate id="rs" name="arr" scope="request">
				<tr>
					<td>
						<bean:write name="rs" property="logsort"/>
					</td>
					<td>
						<bean:write name="rs" property="content" />
					</td>
					<td>
						<bean:write name="rs" property="ipaddress" />
					</td>
				</tr>
			</logic:iterate>
		</table>
		<bean:write name="toolsmenu" filter="false" />
	</logic:present>
</body>
</html:html>
