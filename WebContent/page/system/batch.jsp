<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ page import="org.apache.struts.Globals" %>

<script language="JavaScript" type="text/javascript"> 
 function refreshParent() { 
 window.opener.location.href = window.opener.location.href; 
 if (window.opener.progressWindow) { window.opener.progressWindow.close(); 
 } window.close(); 
 }
 </script>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>������Ϣά��</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="../js/Calendar.js"></script>
	<link href="../css/CSS.CSS" rel="stylesheet" type="text/css">
</head>

<%-- <body onblur="this.focus();">--%>
<body>
	<script language="javascript">
  var cdr = new Calendar("cdr");
  document.write(cdr);
  cdr.showMoreDay = true;
</script>
	<html:form action="/system/batchsave.do" method="post">
		<input type="hidden" name="<%=Constants.TOKEN_KEY%>"   value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/>
		<table width="100%">
			<tr>
				<td width="168">
					ҵ������
				</td>
				<td width="616">
					<html:hidden property="soid" name="batch" />
					<html:hidden property="sbid" name="batch" />
					<bean:write name="batch" property="soname" />
					<logic:present name="dis" scope="request">
					&nbsp;���&nbsp;<html:text property="remark" size="10" />
					</logic:present>
				</td>
			</tr>
			<tr>
				<td>
					ѡ���·�
				</td>
				<td>
					<html:select property="selectmonth" name="batch">
						<html:options name="arr" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					���ο�ʼʱ��
				</td>
				<td>
					<html:text property="begintime" name="batch"
						onfocus="cdr.show(this);" />
				</td>
			</tr>
			<tr>
				<td>
					���ν���ʱ��
				</td>
				<td>
					<html:text property="endtime" name="batch"
						onfocus="cdr.show(this);" />
				</td>
			</tr>
			<tr>
				<td>
					���α��ϱ�׼
				</td>
				<td>
					<html:text property="value" name="batch" />
				</td>
			</tr>
			<tr>
				<td>
					���Ͻ���㷽ʽ
				</td>
				<td>
					<html:select property="countunit" name="batch">
						<html:option key="���˲���" value="���˲���" />
						<html:option key="���˶���" value="���˶���" />
						<html:option key="��������" value="��������" />
						<html:option key="��������" value="��������" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<html:submit value="����" />
					&nbsp;
					<html:button value="�ر�" property="" onclick="refreshParent()" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
