<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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

		<title>文件生成</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>
	<body>
		<br />
		<span>发放账单查询</span>
		<br />
		<form action="billcreatefileview.do" method="post">
			查询项目：
			<select name="type" style="width: 140px">
				<option value="all" selected="selected">
					全部
				</option>
				<option value="mastername">
					户主姓名
				</option>
				<option value="paperid">
					户主证件号
				</option>
				<option value="famno">
					家庭编号
				</option>
			</select>
			&nbsp;查&nbsp;询&nbsp;值：
			<input type="text" name="value" value="" size="12" />
			&nbsp;
			<input type="submit" value="查询" />
			<input name="monthid" type="hidden"
				value="<c:out value="${requestScope.monthid}"/>" />
				 <button onclick="exportXLS()">
				导出excel
			</button>
		</form>
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>
					家庭编号
				</th>
				<th>
					户主姓名
				</th>
				<th>
					证件号
				</th>
				<th>银行账号</th>
				<th>
					金额
				</th>
				<th>
					操作
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<p align="right">
			<c:out value="${requestScope.title}" escapeXml="false"></c:out><c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</p>
		<div align="center">
			<form action="billctreatfilepriview.do" method="post">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tr>
						<td>
							<input type="hidden" name="monthid"
								value="<c:out value="${requestScope.monthid}"/>" />
							选择生成文件格式：
						</td>
						<td>
							<select name="define">
								<option value="">
									未选择...
								</option>
								<c:forEach var="rs" items="${requestScope.defines}">
									<option value="${rs[0]}">
										<c:out value="${rs[1]}"></c:out>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							选择机构：
						</td>
						<td>
							<c:forEach var="rs" items="${requestScope.organlist}">
								<input type="checkbox" name="organid"
									value="<c:out value="${rs.organizationId}"/>" />
								<input name="orgname" type="hidden"
									value="<c:out value="${rs.orgname}"></c:out>" />
								<c:out value="${rs.orgname}"></c:out>、
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="生成文件" class="btn" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
<script>
	function exportXLS() {
		window
				.open(
						"../system/exportfile/exportExcel.do",
						"",
						"height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
</script>
