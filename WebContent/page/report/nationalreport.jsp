<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base>

<title>�ͱ��ۺ����ͳ�Ʊ�</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
</head>
<body>
<table width="100%" border="1" align="center" cellpadding="0"
	cellspacing="0" class="table7">
	<caption>�ͱ��ۺ����ͳ�Ʊ�</caption>
	<tr>
		<td rowspan="3">����<br />
		<br />
		��Ŀ</td>
		<td colspan="7">�������</td>
		<td colspan="5">�ص�������</td>
		<td colspan="5">һ��������</td>
	</tr>
	<tr>
		<td>ũҵ�˿���</td>
		<td>�ͱ�����</td>
		<td>�ͱ�����</td>
		<td>ռũҵ�˿ڱ���</td>
		<td>�����ʽ�����</td>
		<td>�˾�����ˮƽ</td>
		<td>���ϱ�׼</td>
		<td>�ͱ�����</td>
		<td>�ͱ�����</td>
		<td>ռ������������</td>
		<td>�����ʽ�����</td>
		<td>�˾�����ˮƽ</td>
		<td>�ͱ�����</td>
		<td>�ͱ�����</td>
		<td>ռ������������</td>
		<td>�����ʽ�����</td>
		<td>�˾�����ˮƽ</td>
	</tr>
	<tr>
		<td>���ˣ�</td>
		<td>������</td>
		<td>���ˣ�</td>
		<td>��%��</td>
		<td>��Ԫ��</td>
		<td>����/Ԫ��</td>
		<td>��Ԫ��</td>
		<td>������</td>
		<td>���ˣ�</td>
		<td>��%��</td>
		<td>��Ԫ��</td>
		<td>����/Ԫ��</td>
		<td>������</td>
		<td>���ˣ�</td>
		<td>��%��</td>
		<td>��Ԫ��</td>
		<td>����/Ԫ��</td>
	</tr>
	<c:forEach var="rs" items="${requestScope.statlist}">
		<tr>
			<td>${rs.oname}</td>
			<td>${rs.renkou}</td>
			<td>${rs.hs}</td>
			<td>${rs.ss}</td>
			<td>${rs.bl1}</td>
			<td>${rs.zm}</td>
			<td>${rs.bl2}</td>
			<td>${rs.biaozhun}</td>
			<td>${rs.zdhs}</td>
			<td>${rs.zdss}</td>
			<td>${rs.zdbl1}</td>
			<td>${rs.zdzm}</td>
			<td>${rs.zdbl2}</td>
			<td>${rs.ybhs}</td>
			<td>${rs.ybss}</td>
			<td>${rs.ybbl1}</td>
			<td>${rs.ybzm}</td>
			<td>${rs.ybbl2}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
