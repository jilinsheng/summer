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
	function addpolicy(oid) {
		var a = document.getElementById("a").value;
		var b = document.getElementById("b").value;
		var c = document.getElementById("p").value;
		//c=encodeURI(c);
		if (a == "" || b == "" || c == "") {
			alert('请选择开始时间、结束时间和业务名称 ');
		} else {
			var url = 'batch.do';
			var pars = "jbegdt=" + a + "&jenddt=" + b + "&oid=" + oid + "&sa="
					+ c;
			var myAjax = new Ajax.Request(url, {
				method : 'post',
				parameters : pars,
				onComplete : addpolicyback
			});
		}

	}
	function addpolicyback(response) {
		var o = document.getElementById('bb' + response.responseText);
		o.disabled = true;
	}

	function donepolicy(accid, oid) {

		var url = 'batchdone.do';
		var pars = "jaccid=" + accid + "&oid=" + oid;
		alert(pars);
		var myAjax = new Ajax.Request(url, {
			method : 'post',
			parameters : pars,
			onComplete : donepolicyback
		});
		var o = document.getElementById('bc'+oid);
		o.disabled = true;
	}
	function donepolicyback(response) {
		alert(response.responseText);
	}
	function cancelpolicy(oid){
		window.showModalDialog('batchcancelinit.do?oid='+oid,'','dialogWidth:500px;dialogHeight:600px;help:no;Scroll=no;status=no;center=yes');
	}
</script>
<body style="overflow-y: scroll; overflow-x: hidden;">
<table align="center" width="90%" cellspacing="0" cellpadding="0"
	class="table7">
	<caption>农村固定保障建立业务</caption>
	<tr>
		<td colspan="6">业务名称： <input id="p" type="text" value="" />&nbsp;&nbsp;&nbsp;&nbsp;
		开始时间： <input id="a" onfocus="setday(this)">
		&nbsp;&nbsp;&nbsp;&nbsp;结束时间： <input id="b" onfocus="setday(this)"></td>
		<td>
		<button onclick="window.location.reload();">刷新</button>
		</td>
	</tr>
	<tr>
		<th>地区</th>
		<th>业务名称</th>
		<th>业务描述</th>
		<th>开始时间</th>
		<th>结束时间</th>
		<th>业务状态</th>
		<th>操作</th>
	</tr>
	<c:forEach var="rs" items="${requestScope.list}">
		<tr>
			<td><c:out value="${rs.sysTOrganization.orgname}"></c:out></td>
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
			<td><c:if test="${rs.optaccId==null}">
				<button
					id="bb<c:out value="${rs.sysTOrganization.organizationId}"></c:out>"
					type="button"
					onclick="addpolicy('<c:out value="${rs.sysTOrganization.organizationId}"></c:out>')">新建</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
				<button
					id="bb<c:out value="${rs.sysTOrganization.organizationId}"></c:out>"
					type="button"
					onclick="cancelpolicy('<c:out value="${rs.sysTOrganization.organizationId}"></c:out>')">撤销</button>
			</c:if> <c:if test="${rs.accflag==0}">
				<button
					id="bc<c:out value="${rs.sysTOrganization.organizationId}"></c:out>"
					type="button"
					onclick="donepolicy('<c:out value="${rs.optaccId}"></c:out>','${rs.sysTOrganization.organizationId}')">结算</button>
			</c:if></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
