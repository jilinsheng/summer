<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<html>
	<head>
		<title>机构编辑页面</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>

	</head>
	<body>
		<fieldset id="list">
			<legend class="legend">
				<c:out value="${requestScope.title}"></c:out>
			</legend>
			<form name="inputform" action="organsave.do" method="post"
				onsubmit="return checkform();">
				<input name="organizationId" type="hidden"
					value="${requestScope.organ.organizationId}" />
				<input name="parentorgid" type="hidden"
					value="${requestScope.organ.parentorgid}" />
				<input type="hidden" name="<%=Constants.TOKEN_KEY%>"
					value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
				<table width="100%" cellpadding="0" cellspacing="0" class="table7"
					border="0">
					<tr>
						<th width="20%">
							机构名称:
						</th>
						<td>
							<input style="width: 100%" type="text" name="orgname"
								value="${requestScope.organ.orgname}" />
						</td>
					</tr>
					<c:out value="${requestScope.html}" escapeXml="false"></c:out>
					<tr>
						<td colspan="2">
							<input type="submit" value="保存" />
						</td>
					</tr>
				</table>
			</form>
		</fieldset>
	</body>
	<script type="text/javascript">
	function checkform() {
		var orgname = inputform.orgname.value;
		if (orgname == "") {
			alert("机构名称或机构全称不能为空!");
			return false;
		} else {
			return true;
		}
		return false;
	}
</script>
</html>