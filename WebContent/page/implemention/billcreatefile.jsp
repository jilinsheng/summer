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

		<title>�ļ�����</title>

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
		<span>�����˵���ѯ</span>
		<br />
		<form action="billcreatefileview.do" method="post">
			��ѯ��Ŀ��
			<select name="type" style="width: 140px">
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
			&nbsp;
			<input type="submit" value="��ѯ" />
			<input name="monthid" type="hidden"
				value="<c:out value="${requestScope.monthid}"/>" />
				 <button onclick="exportXLS()">
				����excel
			</button>
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
				<th>�����˺�</th>
				<th>
					���
				</th>
				<th>
					����
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
							ѡ�������ļ���ʽ��
						</td>
						<td>
							<select name="define">
								<option value="">
									δѡ��...
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
							ѡ�������
						</td>
						<td>
							<c:forEach var="rs" items="${requestScope.organlist}">
								<input type="checkbox" name="organid"
									value="<c:out value="${rs.organizationId}"/>" />
								<input name="orgname" type="hidden"
									value="<c:out value="${rs.orgname}"></c:out>" />
								<c:out value="${rs.orgname}"></c:out>��
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="�����ļ�" class="btn" />
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
