<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.common.SessionFactory"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="com.mingda.dao.BizTValidatetermDAO"%>
<%@page import="com.mingda.entity.BizTValidateterm"%>
<%
	BizTValidatetermDAO termdao = new BizTValidatetermDAO();
	List<BizTValidateterm> list = termdao.getTerms("1");
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>��Ϣ��֤�����ѯ</title>

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
		<br>
		<form action="infovalidatequery.do" method="post">
			��֤����:
			<select name="valtype">
				<option value="">
					ȫ��
				</option>
				<c:forEach items="${pageScope.list}" var="rs">
					<option value="<c:out value="${rs.validatetermId}"/>">
						<c:out value="${rs.name}" />
					</option>
				</c:forEach>
			</select>
			&nbsp;��ѯ����:
			<select name="term">
				<option value="all">
					ȫ��
				</option>
				<option value="mastername">
					��������
				</option>
				<option value="familyno">
					��ͥ���
				</option>
			</select>
			&nbsp;��ѯֵ:
			<input name="value" type="text" value="" />
			&nbsp;
			<input type="submit" value="��ѯ" />
		</form>
		<table width="100%" cellpadding="0" cellspacing="0" border="0"
			class="table7">
			<tr>
				<th>
					��ͥ���
				</th>
				<th>
					��������
				</th>
				<th>
					��֤����
				</th>
				<th>
					��ϸ����
				</th>
				<th>
					����
				</th>
				<c:if test="${requestScope.flag}">
					<th>
						����
					</th>
				</c:if>
			</tr>
			<c:forEach items="${requestScope.arr}" var="rs">
				<tr>
					<td>
						<a
							href="../info/card/newfamilyinfocard.do?entityId=${rs.familyId}"
							target="_blank"> <c:out value="${rs.inchurl}" /> </a>
					</td>
					<td>
						<a
							href="../info/card/newfamilyinfocard.do?entityId=${rs.familyId}"
							target="_blank"> <c:out value="${rs.membername}" /> </a>
					</td>

					<td>
						<c:out value="${rs.paperid}" />
					</td>
					<td>
						<c:out value="${rs.birthday}" />
					</td>
					<td>
						<c:out value="${rs.labor}" />
					</td>
					<c:if test="${requestScope.flag}">
						<td>
							<a
								href="/db/page/info/neweditor/editorframe.jsp?nodeId=${rs.familyId}"
								target="_blank">�޸� </a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<p align="center">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</p>
	</body>
</html>
<%
	SessionFactory.closeSession();
%>