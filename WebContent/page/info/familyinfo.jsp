<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>家庭信息结构树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<html:form action="/page/info/infoinit.do" method="post">
查询条件：<html:select property="term" style="width=100%">
			<html:option value="familyno">家庭编号</html:option>
			<html:option value="mastername">姓名</html:option>
			<html:option value="masterpaperid">身份证号</html:option>
		</html:select>
		<br>
	查询值：<html:text property="value" size="10" style="width=100%" />
		<html:submit value="查询" />
		<html:button property="create" value="创建新家庭" />
	</html:form>
	<logic:present name="list" scope="request">
		<logic:iterate id="rs" name="list" indexId="ind">
			<div id="<bean:write name="rs" property="familyno"/>">
				<img alt="展开" src="../images/plus.png"
					onclick="viewinfo(<bean:write name="rs" property="familyId"/>,101)" />
				<bean:write name="rs" property="familyno" />
			</div>
		</logic:iterate>
	</logic:present>
</body>
</html:html>
