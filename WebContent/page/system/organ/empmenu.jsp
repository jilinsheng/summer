<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=GBK"%>
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>�ޱ����ĵ�</title>
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
	<table width="100%" height="30" border="0" cellpadding="0"
		cellspacing="0">
		<logic:present name="organ" scope="request">
			<tr>
				<td width="57%">
					[
					<bean:write name="organ" property="onId" />
					��
					<bean:write name="organ" property="onName" />
					]
				</td>
				<td>
					<div align="center">
						<html:link href="/db/system/organ/newOrganAction.do"
							paramName="organ" paramId="selectonno" paramProperty="onNo"
							target="empmainFrame">������������</html:link>
					</div>
				</td>
				<td>
					<div align="center">
						<html:link href="/db/system/organ/modiOrganAction.do"
							paramName="organ" paramId="selectonno" paramProperty="onNo"
							target="empmainFrame">�޸Ļ���</html:link>
					</div>
				</td>
				<td>
					<div align="center">
						<html:link href="/db/system/organ/employeeInitAction.do?qq=1"
							paramName="organ" paramId="selectonno" paramProperty="onNo"
							target="empmainFrame">��Ա����</html:link>
					</div>
				</td>
			</tr>
		</logic:present>
	</table>
</body>
</html:html>
