<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>�½���֯����</title>
	<style type="text/css">
    <!--
      body {
      margin-left: 0px;
      margin-top: 0px;
      margin-right: 0px;
      margin-bottom: 0px;
      }
    -->
  </style>
	<link href="/db/css/CSS111.CSS" rel="stylesheet" type="text/css" />
    <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css" />
</head>
<body>
	<html:form action="/system/organ/saveNewOrganAction.do" scope="request"
		target="_self">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30">
					�½���֯����
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="25" colspan="2">
								<strong>ֱ���ϼ�����</strong>
							</td>
						</tr>
						<tr>
							<td width="17%" height="25">
								�������
							</td>
							<td width="83%">
								<bean:write name="onno" />
								<%
									String onno = (String) request.getAttribute("onno");
									String onlevel = (String) request.getAttribute("onlevel");
								%>
								<html:hidden property="onfatherid" value="<%=onno%>" />
								<html:hidden property="onlevel" value="<%=onlevel%>" />
							</td>
						</tr>
						<tr>
							<td height="25">
								��������
							</td>
							<td>
								<bean:write name="onname" />
							</td>
						</tr>
						<tr>
							<td height="25">
								����ȫ��
							</td>
							<td height="25">
								<logic:present name="onallname" scope="request">
									<bean:write name="onallname" />
								</logic:present>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<strong>�½���֯����</strong>
							</td>
						</tr>
						<tr>
							<td height="25">
								�������
							</td>
							<td>
								<html:text property="txtonno" readonly="true" />
							</td>
						</tr>
						<tr>
							<td height="25">
								��������
							</td>
							<td>
								<html:text property="txtonname" onkeyup="setallname()"
									onchange="setallname()" onclick="setallname()" />
							</td>
						</tr>
						<tr>
							<td height="25">
								����ȫ��
							</td>
							<td>
								<html:hidden property="operate" value="new" />
								<html:hidden property="txtonallnamehidden" />
								<html:text property="txtonallname" size="40" readonly="true" />
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<strong>�����³�ʼʹ����Ա</strong>
							</td>
						</tr>
						<tr>
							<td height="25">
								����
							</td>
							<td>
								<html:text property="txtusername" />
							</td>
						</tr>
						<tr>
							<td height="25">
								��λ
							</td>
							<td>
								<html:select property="selectpost">
									<logic:present name="postarr" scope="request">
										<html:options collection="postarr" property="dicName"
											labelProperty="dicName" />
									</logic:present>
								</html:select>
							</td>
						</tr>
						<tr>
							<td height="25">
								��¼�û���
							</td>
							<td>
								<html:text property="txtloginname" />
							</td>
						</tr>
						<tr>
							<td height="25">
								����
							</td>
							<td>
								<html:password property="txtpassword" />
								(���Ȳ���С��6)
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<html:submit value="����" property="save" onclick="setallname()" />
					</div>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
<script type="">
  function setallname(){
	saveNewOrganActionForm.txtonallname.value=saveNewOrganActionForm.txtonallnamehidden.value + saveNewOrganActionForm.txtonname.value
  }
  </script>
