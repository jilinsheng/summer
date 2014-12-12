<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<style type="text/css">
<!--
#list {
	padding: 4px;
	border: 3px double #f5a89a;
}

#emp {
	padding: 4px;
	border: 3px double #f5a89a;
}

.legend {
	font-size: 12px;
	font-weight: lighter;
	color: #000000;
}

lable {
	width: 90px;
	text-align: center;
}
-->
</style>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>信息查看</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>

<body>
	<br />
	<logic:present name="info" scope="request">
		<fieldset id="list">
			<legend class="legend">
				日志信息
			</legend>

			<table class="table6" cellpadding="0" cellspacing="0" width="98%"
				align="center">

				<bean:write name="info" filter="false" />
			</table>
			<br />
			<table class="table7" cellpadding="0" cellspacing="0" width="98%"
				align="center">
				<caption align="left" style="color:red">
					<logic:present name="title" scope="request">
						<bean:write name="title" scope="request" />
					</logic:present>
				</caption>
				<tr>
					<th>
						字段
					</th>
					<th>
						修改前
					</th>
					<th>
						修改后
					</th>
				</tr>
					<bean:write name="log" filter="false" />
			</table>
		</fieldset>
	</logic:present>
</body>
</html:html>
