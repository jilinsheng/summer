<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%@page import="java.math.BigDecimal"%>
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
		<title>查看通知</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript">
		function receivenotice(noticeid){
			var url = 'recpit.do';
			var pars = 'noticeid='+noticeid;
			var myAjax = new Ajax.Request(url, {
				method :'post',
				parameters :pars,
				onComplete :showreceivenotice
			});
		}
		function showreceivenotice(response){
			alert(response.responseText);
		}
	</script>
	</head>
	<body>
		<br>
		作者:
		<c:out value="${requestScope.notice.author}"></c:out>
		<br>
		<br>
		<table width="98%" cellpadding="0" cellspacing="0" border="0"
			class="table7">
			<tr height="24">
				<th width="140">
					标题
				</th>
				<td>
					<div align="left">
						<c:out value="${requestScope.notice.title}"></c:out>
					</div>
				</td>
			</tr>
			<tr height="24">
				<th width="140">
					内容
				</th>
				<td>
					<div align="left">
						<c:out value="${requestScope.notice.content}"></c:out>
					</div>
				</td>
			</tr>
			<tr>
				<th width="140">
					附件信息
				</th>
				<td>
					<div align="left">
						<c:forEach var="rs" items="${requestScope.filelist}">
							<div style="padding: 4px">
								<c:set value="${rs.filename}" var="temp" scope="page" />
								<%
								String temp = (String) pageContext.getAttribute("temp");
							%>
								<%
								temp = temp.substring(temp.lastIndexOf("-") + 1);
								%>
								<a href="oafileview.do?fileid=<c:out value="${rs.fileId}"/>"
									target="_blank"> <%
								out.println(temp);
							%> </a>
							</div>
						</c:forEach>
					</div>
				</td>
			</tr>
			<c:set value="${requestScope.notice.auditor}" var="person"
				scope="page" />
			<%
				SysTEmployee employee =(SysTEmployee)session.getAttribute("employee");
				String empid =employee.getEmployeeId().toString();
				BigDecimal person =(BigDecimal)pageContext.getAttribute("person");
				if(person.toString().equals(empid)){
			%>
			<tr height="24">
				<th width="140">
					接收情况
				</th>
				<td>
					<div align="left">
						<c:forEach var="rs" items="${requestScope.reces}">
							<div style="padding: 4px">
								<c:out value="${rs.sysTOrganization.orgname}"></c:out>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<c:out value="${rs.person}"></c:out>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<c:if test="${rs.flag=='1'}">
						已接收
						</c:if>
								<c:if test="${rs.flag=='0'}">
						未接收
						</c:if>
							</div>
						</c:forEach>
					</div>
				</td>
			</tr>
			<%} else{%>
			<tr height="24">
				<th width="140">
					填写回执
				</th>
				<td>
				<div align="left" style="padding:4px">
					<button onclick="receivenotice('<c:out value="${requestScope.notice.noticeId}"/>')">我已经接收此通知</button>
				</div>
				</td>
			</tr>
			<%}%>
		</table>
		<p align="center">
			<button onclick=javascript:window.close();>
				关闭
			</button>
		</p>
	</body>
</html>
