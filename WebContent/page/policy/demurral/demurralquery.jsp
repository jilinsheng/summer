<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>���鴦��</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<body>
		<br>
		<form name="inputform" action="demurralquery.do" method="post">
		<input type="hidden" name="policyId" value="${requestScope.policyId}" />
			��ѯ��Ŀ��
			<select name="type">
				<option value="all" selected>
					ȫ��
				</option>
				<option value="mastername">
					��������
				</option>
				<option value="paperid">
					����֤����
				</option>
				<option value="famno">
					��ͥ���
				</option>
			</select>
			&nbsp;��&nbsp;ѯ&nbsp;ֵ��
			<input type="text" name="value" value="" size="12" />
			&nbsp; ������
			<select name="orgid">
				<c:forEach items="${requestScope.orglist}" var="rs">
					<option value="${rs.organizationId}">
						${rs.orgname }
					</option>
				</c:forEach>
			</select>
			&nbsp;
			<input type="submit" value="��ѯ" />
		</form>
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>
					��ͥ���
				</th>
				<th>
					��������
				</th>
				<th>
					�ܽ��
				</th>
				<th>
					����
				</th>
				<th>
					�Ƿ�������
				</th>
				<th>
					����
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<p align="right">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</p>
	</body>
</html>
