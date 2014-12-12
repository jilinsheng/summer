<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>机构迁移确认</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>
	<script type="text/javascript">
		<!--
			function commit(type){
				inputform.type.value=type;
				inputform.submit();
			} 
		-->
	</script>
	<body>
		<br>
		<form name="inputform" action="organeditcommit.do" method="post">
			<input type="hidden" name="type" value="" />
			<table width="100%" class="table7" cellpadding="0" cellspacing="0">
				<tr>
					<th>
						迁移机构:
					</th>
					<td>
						<c:forEach items="${requestScope.olist}" var="rs">
							<c:out value="${rs[1]}" />
							<input type="hidden" name="oorgid" value="${rs[0]}" />
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>
						迁移至新机构:
					</th>
					<td>
						<c:forEach items="${requestScope.nlist}" var="rs">
							<c:out value="${rs[1]}" />
							<input type="hidden" name="norgid" value="${rs[0]}" />
						</c:forEach>
					</td>
				<tr>
					<td colspan="2">
						<c:if test="${requestScope.button==1}">
							<button onclick="commit(1)">
								确认
							</button>
						</c:if>
						<c:if test="${requestScope.button==0}">
							<button onclick="commit(0)">
								恢复
							</button>
						</c:if>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
