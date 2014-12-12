<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
<script type="text/javascript" src="../../js/Calendar2.js"></script>
<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
</head>
<script type="text/javascript">
	function cancelpolicy(accid, oid) {

		var url = 'batchcancel.do';
		var pars = "jaccid=" + accid + "&oid=" + oid;
		var myAjax = new Ajax.Request(url, {
			method : 'post',
			parameters : pars,
			onComplete : cancelpolicyback
		});
	}
	function cancelpolicyback(response) {
		alert(response.responseText);
	}
</script>
<body style="overflow-y: scroll; overflow-x: hidden;">
<c:out value="${info}"></c:out>
<table align="center" width="90%" cellspacing="0" cellpadding="0"
	class="table7">
	<caption>取消农村固定保障业务</caption>
	<tr>
		<th>业务名称</th>
		<th>业务描述</th>
		<th>开始时间</th>
		<th>结束时间</th>
		<th>业务状态</th>
		<th>操作</th>
	</tr>
	<c:forEach var="rs" items="${requestScope.list}">
		<tr>
			<td><c:choose>
				<c:when test="${rs.optaccId==null}">
				</c:when>
				<c:otherwise>
					<c:out value="${rs.accyear}"></c:out>年 <c:out
						value="${rs.accmonth}"></c:out>月
			</c:otherwise>
			</c:choose></td>
			<td><c:out value="${rs.accdesc}"></c:out></td>
			<td><c:out value="${rs.accbegdt}"></c:out></td>
			<td><c:out value="${rs.accenddt}"></c:out></td>
			<td><c:if test="${rs.accflag==0}">
			处理中
			</c:if> <c:if test="${rs.accflag==1}">
			结算中
			</c:if> <c:if test="${rs.accflag==2}">
			结算完毕
			</c:if></td>
			<td><c:if test="${rs.accflag==2}">
				<button
					id="bc<c:out value="${rs.sysTOrganization.organizationId}"></c:out>"
					type="button"
					onclick="cancelpolicy('<c:out value="${rs.optaccId}"></c:out>','${rs.sysTOrganization.organizationId}')">撤销</button>
			</c:if></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
