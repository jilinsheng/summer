<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<script type="">
function qq(){
if(employeeActionForm.checkbox.checked==true){
employeeActionForm.empLoginname.disabled=false;
employeeActionForm.empLoginname.className="t2";
employeeActionForm.empPassword.disabled=false;
employeeActionForm.empPassword.className="t2";
}
else{
employeeActionForm.empLoginname.disabled=true;
employeeActionForm.empLoginname.className="t1";
employeeActionForm.empPassword.disabled=true;
employeeActionForm.empPassword.className="t1";
}
}
</script>
<script language="javascript" type="text/javascript">
function msg(){
var ayear,amouth,aday;
var idcard=employeeActionForm.empcardid.value;
var sexe="δѡ��";
switch(idcard.length){
case 15:
var ayear="19"+ idcard.substr(6,2);
var amouth=idcard.substr(8,2);
var aday=idcard.substr(10,2);
var sexe=idcard.substr(14,1);
var all1=ayear+"-"+amouth+"-"+aday;
employeeActionForm.empBirthday.value=all1;
break;
case 18:
var ayear=idcard.substr(6,4);
var amouth=idcard.substr(10,2);
var aday=idcard.substr(12,2);
var sexe=idcard.substr(16,1);
var all1=ayear+"-"+amouth+"-"+aday;
employeeActionForm.empBirthday.value=all1;
break;
}
if(sexe%2){
employeeActionForm.empSex.value="��";
}
else{
employeeActionForm.empSex.value="Ů";
}
}
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>�ޱ����ĵ�</title>
	<script language="javascript" src="/db/js/Calendar.js"></script>
	<link href="../../css/CSS.CSS" rel="stylesheet" type="text/css" />
</head>
<body onload="qq()">
	<script language="javascript">
  var cdr = new Calendar("cdr");
  document.write(cdr);
  cdr.showMoreDay = true;
</script>
	<html:form action="/system/organ/employeeAction.do?qq=1" method="POST"
		target="empmainFrame">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30">
					&nbsp;
					<html:img width="13" height="13" src="/db/images/dot6.gif" />
					������Ա��Ϣά��
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="1" cellspacing="0"
						bordercolorlight="#C0C0C0" bordercolordark="#ffffff"
						bgcolor="#ffffff">
						<tr>
							<td height="25" colspan="5"
								background="../../images/taltitle.gif">
								<logic:present name="onname" scope="request">
									<div align="center">
										[
										<bean:write name="onname" />
										]������Ա�б�
									</div>
								</logic:present>
							</td>
						</tr>
						<tr>
							<td width="18%" height="25">
								<div align="center">
									����
								</div>
							</td>
							<td width="15%">
								<div align="center">
									ְ��
								</div>
							</td>
							<td width="36%">
								<div align="center">
									�칫�绰
								</div>
							</td>
							<td width="12%">
								<div align="center">
									ϵͳȨ��
								</div>
							</td>
							<td width="19%">
								<div align="center">
									����
								</div>
							</td>
						</tr>
						<%
						String onno = (String) request.getAttribute("onnon");
						%>
						<logic:present name="arr" scope="request">
							<%
							String[] a = (String[]) request.getAttribute("empidn");
							%>
							<logic:iterate id="arr1" indexId="ind" name="arr" scope="request">
								<tr>
									<td height="23">
										<div align="center">
											<bean:write name="arr1" property="empName" />
										</div>
									</td>
									<td>
										<div align="center">
											<bean:write name="arr1" property="empPost" />
										</div>
									</td>
									<td>
										<div align="center">
											&nbsp;
											<bean:write name="arr1" property="empLinkmode" />
											&nbsp;
										</div>
									</td>
									<td>
										<div align="center">
											<bean:write name="arr1" property="empUsesys" />
										</div>
									</td>
									<td>
										<div align="center">
											<%
														java.lang.Integer i = (Integer) pageContext
														.getAttribute("ind");
														String empid = a[i.intValue()];
														java.util.HashMap map = new java.util.HashMap();
														map.put("empid", empid);
														map.put("selectonno", onno);
														pageContext.setAttribute("map", map);
											%>
											<html:link action="/system/organ/employeeInitAction.do?qq=2"
												name="map" target="empmainFrame">�޸�</html:link>
											<logic:equal value="T" name="arr1" property="empState">
												<html:link action="/system/organ/employeeInitAction.do?qq=3"
													name="map" target="empmainFrame">ͣ��</html:link>
											</logic:equal>
											<logic:equal value="F" name="arr1" property="empState">
												<html:link action="/system/organ/employeeInitAction.do?qq=4"
													name="map" target="empmainFrame">�ָ�</html:link>
											</logic:equal>
										</div>
									</td>
								</tr>
							</logic:iterate>
						</logic:present>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="1" cellspacing="0"
						bordercolorlight="#C0C0C0" bordercolordark="#ffffff"
						bgcolor="#ffffff">
						<tr>
							<td height="25" colspan="4"
								background="../../images/taltitle.gif">
								<div align="center">
									������Ա��Ϣά��
								</div>
							</td>
						</tr>
						<tr>
							<td width="16%" height="25">
								<div align="center">
									����
								</div>
							</td>
							<td width="34%">
								<html:text property="empName" name="empdto" />
							</td>
							<td width="16%">
								<div align="center">
									ְ��
								</div>
							</td>
							<td width="34%">
								<html:select property="empPost" name="empdto">
									<%
									if (onno.length() == 10) {
									%>
									<html:option value="a001">����</html:option>
									<%
										}
										if (onno.length() == 8) {
									%>
									<html:option value="a002">�ֵ�</html:option>
									<%
										}
										if (onno.length() == 6) {
									%>
									<html:option value="a003">��/��</html:option>
									<%
										}
										if (onno.length() == 4) {
									%>
									<html:option value="a004">��</html:option>
									<%
										}
										if (onno.length() == 2) {
									%>
									<html:option value="a005">ʡ</html:option>
									<%
									}
									%>
								</html:select>
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									���֤��
								</div>
							</td>
							<td>
								<html:text property="empcardid" name="empdto" onchange="msg()" />
							</td>
							<td>
								<div align="center">
									��������
								</div>
							</td>
							<td>
								<html:text property="empBirthday" name="empdto"
									onfocus="cdr.show(this);" readonly="true" />
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									�Ա�
								</div>
							</td>
							<td>
								<html:radio value="��" property="empSex" name="empdto" />
								��
								<html:radio value="Ů" property="empSex" name="empdto" />
								Ů
							</td>
							<td>
								<div align="center">
									��ַ
								</div>
							</td>
							<td>
								<html:text property="empAddress" name="empdto" />
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									��ϵ��ʽ
								</div>
							</td>
							<td>
								<html:text property="empLinkmode" name="empdto" />
							</td>
							<td>
								<div align="center">
									Email
								</div>
							</td>
							<td>
								<html:text property="empEmail" name="empdto" />
							</td>
						</tr>
						<tr>
							<td height="25" colspan="4">
								<div align="center">
									<html:checkbox value="T" property="checkbox" name="empdto"
										onclick="qq()" />
									ʹ��ϵͳȨ��
								</div>
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									��¼����
								</div>
							</td>
							<td>
								<html:text property="empLoginname" name="empdto" />
							</td>
							<td>
								<div align="center">
									��¼����
								</div>
							</td>
							<td>
								<html:text property="empPassword" name="empdto" />
							</td>
						</tr>
						<tr>
							<td height="25" colspan="4">
								<div align="center">
									<html:submit value="����" property="Submit" />
									<html:hidden property="empId" name="empdto" />
									<html:hidden property="tsts" name="empdto" />
									<html:hidden property="onno" name="empdto" />
								</div>
								<div align="center">
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
