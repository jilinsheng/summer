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
	<title>新建组织机构</title>
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
    <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css" />
</head>
<body>
	<html:form action="/system/organ/saveModiOrganAction.do"
		scope="request">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30">
					新建组织机构
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="25" colspan="2">
								<strong>直属上级机构</strong>
							</td>
						</tr>
						<tr>
							<td width="17%" height="25">
								机构编号
							</td>
							<td width="83%">
								<bean:write name="onno" />
								<%
									String onno = (String) request.getAttribute("onno");
									String onname = (String) request.getAttribute("onname");
									String onallname = (String) request.getAttribute("onallname");
								%>
								<html:hidden property="txtonno" value="<%=onno%>" />
							</td>
						</tr>
						<tr>
							<td height="25">
								机构名称
							</td>
							<td>
								<html:text property="txtonname" value="<%=onname%>"
									onchange="setallname()" onclick="setallname()"
									onkeyup="setallname()" />
							</td>
						</tr>
						<tr>
							<td height="25">
								机构全称
							</td>
							<td height="25">
								<html:hidden property="operate" value="edit" />
								<html:hidden property="txtonallnamehidden" />
								<html:text property="txtonallname" value="<%=onallname%>"
									readonly="true" size="40" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<html:submit value="保存" property="save" onclick="setallname()" />
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
