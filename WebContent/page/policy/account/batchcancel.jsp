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
	<caption>ȡ��ũ��̶�����ҵ��</caption>
	<tr>
		<th>ҵ������</th>
		<th>ҵ������</th>
		<th>��ʼʱ��</th>
		<th>����ʱ��</th>
		<th>ҵ��״̬</th>
		<th>����</th>
	</tr>
	<c:forEach var="rs" items="${requestScope.list}">
		<tr>
			<td><c:choose>
				<c:when test="${rs.optaccId==null}">
				</c:when>
				<c:otherwise>
					<c:out value="${rs.accyear}"></c:out>�� <c:out
						value="${rs.accmonth}"></c:out>��
			</c:otherwise>
			</c:choose></td>
			<td><c:out value="${rs.accdesc}"></c:out></td>
			<td><c:out value="${rs.accbegdt}"></c:out></td>
			<td><c:out value="${rs.accenddt}"></c:out></td>
			<td><c:if test="${rs.accflag==0}">
			������
			</c:if> <c:if test="${rs.accflag==1}">
			������
			</c:if> <c:if test="${rs.accflag==2}">
			�������
			</c:if></td>
			<td><c:if test="${rs.accflag==2}">
				<button
					id="bc<c:out value="${rs.sysTOrganization.organizationId}"></c:out>"
					type="button"
					onclick="cancelpolicy('<c:out value="${rs.optaccId}"></c:out>','${rs.sysTOrganization.organizationId}')">����</button>
			</c:if></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
