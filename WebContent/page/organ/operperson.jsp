<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%String title=(String)request.getAttribute("title"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�༭<%=title%>Ա</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
		<style type="text/css">
<!--
a {
	text-decoration: none;
}
-->
</style>
		<script type="text/javascript" src="../js/Validator.js"></script>
		<script type="text/javascript">
	function checkform() {
	if(
		!CheckText("����", inputform.name, true, 20)){
		return false;
		}
		return true;
	}
</script>
	</head>
	<body>
		<br>
		<%=title%>�б�
		<br>
		<table width="640" border="0" class="table7" cellpadding="0"
			cellspacing="0">
			<tr>
				<th scope="col">
					����
				</th>
				<th scope="col">
					�칫������
				</th>
				<th scope="col">
					�칫�ҵ绰
				</th>
				<th scope="col">
					����
				</th>
			</tr>
			<c:forEach var="rs" items="${requestScope.operpesons}">
				<tr>
					<td>
						${rs.name}
					</td>
					<td>
						${rs.officename}
					</td>
					<td>
						${rs.officephone}
					</td>
					<td>
						<a
							href="operpersoninit.do?type=M&personid=${rs.optreviewpersonId}"
							target="_self"><img src="../images/edit.gif" alt="�޸�"
								border="1" /> </a>
						<a
							href="operpersoninit.do?type=D&personid=${rs.optreviewpersonId}"
							target="_self"><img src="../images/del.gif" alt="ɾ��"
								border="1" /> </a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<button class="btn" onclick="window.location.reload('operpersoninit.do')">
			�½�<%=title%>Ա
		</button>
		<div style="color: #red" align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:out value="${str}"></c:out>
		</div>
		<form action="operpersonsave.do" method="post" name="inputform"
			onsubmit="return checkform()">
			<input type="hidden" name="optreviewpersonId"
				value="${optreviewpersonId}" />
			<c:out value="${pagehtml}" escapeXml="false"></c:out>
			<div align="left">
				<input type="submit" value="����<%=title%>Ա" class="btn" />
			</div>
		</form>
	</body>
</html>
