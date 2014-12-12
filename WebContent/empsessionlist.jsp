<%@ page language="java" import="java.util.*,com.mingda.entity.SysTEmployee" pageEncoding="GB18030"%>
<%
List<SysTEmployee> list = com.mingda.common.listener.StoreFactory.getStore()
			.getUsers();

	request.setAttribute("stat", list);
	request.setAttribute("statsize", list.size());
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<style>
<!--
body        ,table {
	margin: 3;
	margin-top: 0;
	padding: 0;
	background: #FFFFFF;
	font-family: "宋体";
	font-size: 12px;
}

.table7 {
	border-collapse: collapse;
	border: 1px solid #999999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
}

.table7 td {
	height: 23px;
	border: 1px solid #999999;
	border-width: 1px 0 0 1px;
	margin: 2px 0 2px 0;
	font-size: 12px;
	background-color: #FFF;
	text-align: center;
}

.table7 th {
	height: 23px;
	border: 1px solid #999999;
	border-width: 1px 0 0 1px;
	margin: 2px 0 2px 0;
	text-align: center;
	font-size: 12px;
	font-weight: lighter;
	background-color: #f5a89a;
}
-->
</style>
<html>
	<head>
		<title>在线用户列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>
	<body>
		<br />
		<table width="80%" align="center" class="table7">
			<caption align="left">
				登录用户列表&nbsp;&nbsp;&nbsp;&nbsp;在线人数:${requestScope.statsize}人
			</caption>
			<tr>
				<th>
					用户名称
				</th>
				<th>
					用户账号
				</th>
				<th>
					岗位
				</th>
				<th>
					所属
				</th>
			</tr>
			<logic:present name="stat" scope="request">
				<logic:iterate id="rs" name="stat" scope="request">
					<tr>
						<td>
							<bean:write name="rs" property="sysTEmpext.name" />
						</td>
						<td>
							<bean:write name="rs" property="account" />
						</td>
						<td>
							<bean:write name="rs" property="sysTPost.name" />
						</td>
						<td>
							<bean:write name="rs" property="sysTOrganization.fullname" />
						</td>
					</tr>
				</logic:iterate>
			</logic:present>
		</table>
	</body>
</html>