<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>菜单编辑区</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
	<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script><link
		rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>
<body>
	<fieldset id="list">
		<legend class="legend">
			菜单项目信息
		</legend>
		<form action="menusave.do" method="post">
			<br />
			<input name="privilegeId" type="hidden"
				value="${requestScope.privi.privilegeId}" />
			<input name="parentprivilegeid" type="hidden"
				value="${requestScope.privi.parentprivilegeid}" />
			<table width="100%" class="table7" cellpadding="0" cellspacing="0">
				<tr>
					<th width="180">
						题目
					</th>
					<td>
						<input name="displayname"
							value="${requestScope.privi.displayname}" type="text"
							style="width:100%" />
					</td>
				</tr>
				<tr>
					<th width="180">
						连接内容
					</th>
					<td>
						<textarea name="url" style="width:100%" rows="4">${requestScope.privi.url}</textarea>
					</td>
				</tr>
				<tr>
					<th width="180">
						目标
					</th>
					<td>
						<input name="target" value="${requestScope.privi.target}"
							type="text" style="width:100%" />
					</td>
				</tr>
				<tr>
					<th width="180">
						备注说明
					</th>
					<td>
						<textarea style="width:100%" name="detail" rows="4">${requestScope.privi.detail}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="保存" /> 
					</td>
				</tr>
			</table>
		</form>
	</fieldset>
</body>
</html:html>
