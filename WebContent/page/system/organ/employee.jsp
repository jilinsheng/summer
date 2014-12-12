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
var sexe="未选择";
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
employeeActionForm.empSex.value="男";
}
else{
employeeActionForm.empSex.value="女";
}
}
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>无标题文档</title>
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
					工作人员信息维护
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
										]工作人员列表
									</div>
								</logic:present>
							</td>
						</tr>
						<tr>
							<td width="18%" height="25">
								<div align="center">
									姓名
								</div>
							</td>
							<td width="15%">
								<div align="center">
									职务
								</div>
							</td>
							<td width="36%">
								<div align="center">
									办公电话
								</div>
							</td>
							<td width="12%">
								<div align="center">
									系统权限
								</div>
							</td>
							<td width="19%">
								<div align="center">
									操作
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
												name="map" target="empmainFrame">修改</html:link>
											<logic:equal value="T" name="arr1" property="empState">
												<html:link action="/system/organ/employeeInitAction.do?qq=3"
													name="map" target="empmainFrame">停用</html:link>
											</logic:equal>
											<logic:equal value="F" name="arr1" property="empState">
												<html:link action="/system/organ/employeeInitAction.do?qq=4"
													name="map" target="empmainFrame">恢复</html:link>
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
									工作人员信息维护
								</div>
							</td>
						</tr>
						<tr>
							<td width="16%" height="25">
								<div align="center">
									姓名
								</div>
							</td>
							<td width="34%">
								<html:text property="empName" name="empdto" />
							</td>
							<td width="16%">
								<div align="center">
									职务
								</div>
							</td>
							<td width="34%">
								<html:select property="empPost" name="empdto">
									<%
									if (onno.length() == 10) {
									%>
									<html:option value="a001">社区</html:option>
									<%
										}
										if (onno.length() == 8) {
									%>
									<html:option value="a002">街道</html:option>
									<%
										}
										if (onno.length() == 6) {
									%>
									<html:option value="a003">区/县</html:option>
									<%
										}
										if (onno.length() == 4) {
									%>
									<html:option value="a004">市</html:option>
									<%
										}
										if (onno.length() == 2) {
									%>
									<html:option value="a005">省</html:option>
									<%
									}
									%>
								</html:select>
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									身份证号
								</div>
							</td>
							<td>
								<html:text property="empcardid" name="empdto" onchange="msg()" />
							</td>
							<td>
								<div align="center">
									出生日期
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
									性别
								</div>
							</td>
							<td>
								<html:radio value="男" property="empSex" name="empdto" />
								男
								<html:radio value="女" property="empSex" name="empdto" />
								女
							</td>
							<td>
								<div align="center">
									地址
								</div>
							</td>
							<td>
								<html:text property="empAddress" name="empdto" />
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									联系方式
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
									使用系统权限
								</div>
							</td>
						</tr>
						<tr>
							<td height="25">
								<div align="center">
									登录名称
								</div>
							</td>
							<td>
								<html:text property="empLoginname" name="empdto" />
							</td>
							<td>
								<div align="center">
									登录密码
								</div>
							</td>
							<td>
								<html:text property="empPassword" name="empdto" />
							</td>
						</tr>
						<tr>
							<td height="25" colspan="4">
								<div align="center">
									<html:submit value="保存" property="Submit" />
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
