<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>ũ��ͱ���ʾ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<body>
		<br>
		<form name="inputform" action="noticequery.do" method="post">
			<input type="hidden" name="policyId" value="${requestScope.policyId}" />
			��ѯ��Ŀ��
			<select name="type">
				<option value="all">
					ȫ��
				</option>
				<option value="mastername">
					��������
				</option>
				<option value="paperid">
					���֤��
				</option>
				<option value="famno">
					��ͥ���
				</option>
			</select>
			&nbsp;��&nbsp;ѯ&nbsp;ֵ��
			<input type="text" name="value" value="" size="12" />
			&nbsp;�������ͣ�
			<select name="sal">
				<option value="">
					ȫ��
				</option>
				<option value="876">
					�ص㻧һ��
				</option>
				<option value="877">
					�ص㻧����
				</option>
				<option value="878">
					�ص㻧����
				</option>
				<option value="873">
					һ�㻧һ��
				</option>
				<option value="874">
					һ�㻧����
				</option>
				<option value="875">
					һ�㻧����
				</option>
				<option value="879">
					�ޱ������ͻ�
				</option>
			</select>
			&nbsp;������
			<select name="orgid">
				<c:forEach items="${requestScope.orglist}" var="rs">
					<option value="${rs.organizationId}">
						${rs.orgname }
					</option>
				</c:forEach>
			</select>
			&nbsp;
			<input type="submit" value="��ѯ" class="btn"/>
			&nbsp;
			<button onclick="exportXLS()" class="btn">
				����excel
			</button>
		</form>
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<c:out value="${requestScope.head}" escapeXml="false"></c:out>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<p align="right">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</p>
	</body>
</html>
<script>
	function exportXLS() {
		window
				.open(
						"../../system/exportfile/exportExcel.do",
						"",
						"height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
</script>
