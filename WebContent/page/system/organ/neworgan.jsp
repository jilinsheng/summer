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
	<link href="/db/css/CSS111.CSS" rel="stylesheet" type="text/css" />
    <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css" />
</head>
<body>
	<html:form action="/system/organ/saveNewOrganAction.do" scope="request"
		target="_self">
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
									String onlevel = (String) request.getAttribute("onlevel");
								%>
								<html:hidden property="onfatherid" value="<%=onno%>" />
								<html:hidden property="onlevel" value="<%=onlevel%>" />
							</td>
						</tr>
						<tr>
							<td height="25">
								机构名称
							</td>
							<td>
								<bean:write name="onname" />
							</td>
						</tr>
						<tr>
							<td height="25">
								机构全称
							</td>
							<td height="25">
								<logic:present name="onallname" scope="request">
									<bean:write name="onallname" />
								</logic:present>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<strong>新建组织机构</strong>
							</td>
						</tr>
						<tr>
							<td height="25">
								机构编号
							</td>
							<td>
								<html:text property="txtonno" readonly="true" />
							</td>
						</tr>
						<tr>
							<td height="25">
								机构名称
							</td>
							<td>
								<html:text property="txtonname" onkeyup="setallname()"
									onchange="setallname()" onclick="setallname()" />
							</td>
						</tr>
						<tr>
							<td height="25">
								机构全称
							</td>
							<td>
								<html:hidden property="operate" value="new" />
								<html:hidden property="txtonallnamehidden" />
								<html:text property="txtonallname" size="40" readonly="true" />
							</td>
						</tr>
						<tr>
							<td height="25" colspan="2">
								<strong>机构下初始使用人员</strong>
							</td>
						</tr>
						<tr>
							<td height="25">
								姓名
							</td>
							<td>
								<html:text property="txtusername" />
							</td>
						</tr>
						<tr>
							<td height="25">
								岗位
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
								登录用户名
							</td>
							<td>
								<html:text property="txtloginname" />
							</td>
						</tr>
						<tr>
							<td height="25">
								密码
							</td>
							<td>
								<html:password property="txtpassword" />
								(长度不可小于6)
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
