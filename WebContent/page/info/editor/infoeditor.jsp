<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>信息编辑页面</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" href="../../css/style.css" type="text/css"></link></head>
<body>
	<form action="/db/page/info/editor/infoFormSave.do" method="post"
		name="editor">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">
					信息编辑
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<bean:write name="formdoc" filter="false" />
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="Submit" value="保存" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
