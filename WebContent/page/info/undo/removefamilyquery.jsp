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
		<title>�Ѿ�������ͥ��ѯ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<body>
		<br>
		<form name="inputform" action="removefamilyquery.do"
			method="post">
			��ѯ��Ŀ��
			<select name="type">
				<option value="all" selected="selected">
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
			&nbsp;&nbsp;&nbsp;&nbsp;
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
					֤����
				</th>
				<th>
					����
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
	</body>
</html>
