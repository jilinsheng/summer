<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.dao.SysTOrganizationDAO"%>
<%@page import="com.mingda.entity.SysTOrganization"%>
<%@page import="com.mingda.common.SessionFactory"%>
<%
	String organizationId = request.getParameter("organizationId");
	if (null == organizationId || "".equals(organizationId)) {
		organizationId = "99999999";
	}
	SysTOrganizationDAO odao = new SysTOrganizationDAO();
	SysTOrganization organ = odao.findById(organizationId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��������˵�</title>
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
		<table width="100%" cellpadding="0" cellspacing="0" border="0"
			class="table7">
			<%
				if (null == organ) {
			%>
			<tr>
				<th align="left">
					��ǰѡ��Ļ�����
				</th>
			</tr>
			<%
				} else {
					String oname = organ.getOrgname();
			%>
			<tr>
				<th align="left">
					��ǰѡ��Ļ�����<%=oname%>
					&nbsp;&nbsp;
					<a href="organinit.do?type=edit&organizationId=<%=organizationId%>"
						target="bottom">�޸Ļ�����Ϣ</a>&nbsp;&nbsp;
						<%if(organizationId.length()!=10){ %>
					<a href="organinit.do?type=add&organizationId=<%=organizationId%>"
						target="bottom">�½��¼�����</a>&nbsp;&nbsp;<%} %>
					<a
						href="organstopinit.do?type=add&organizationId=
						<%=organizationId%>"
						target="bottom">ͣ���������� </a>
				</th>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>
<%--
	SessionFactory.closeSession();
--%>
