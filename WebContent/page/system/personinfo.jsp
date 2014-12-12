<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>个人信息维护</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	<style type="text/css">
<!--
#list {
	padding: 4px;
	border: 3px double #f5a89a;
}

#emp {
	padding: 4px;
	border: 3px double #f5a89a;
}

.legend {
	font-size: 12px;
	font-weight: lighter;
	color: #000000;
}

lable {
	width: 90px;
	text-align: center;
}
-->
</style>
</head>

<body>
	&nbsp;&nbsp;&nbsp;
	<br>
	<fieldset id="list">
		<legend class="legend">
			个人信息设置
		</legend>
		<logic:present name="employee" scope="request">
			<form name="inputForm" action="personinfo.do" method="post">
				<input name="employeeId" type="hidden"
					value="<bean:write name="employee" property="employeeId"/>" />
				<input name="empextId" type="hidden"
					value="<bean:write name="employee" property="sysTEmpext.empextId"/>" />
				<input name="status" type="hidden"
					value="<bean:write name="employee" property="sysTEmpext.status"/>" />
				<table width="90%" cellspacing="0" cellpadding="0" align="center">
					<tr>
						<td>
							工号
						</td>
						<td>
							<input style="width: 180px" name="workno" type="text"
								value="<bean:write name="employee" property="sysTEmpext.workno"/>" />
						</td>
						<td>
							姓名
						</td>
						<td>
							<input style="width: 180px" name="name" type="text"
								value="<bean:write name="employee" property="sysTEmpext.name"/>" />
						</td>
					</tr>
					<tr>
						<td>
							身份证号
						</td>
						<td>
							<input style="width: 180px" name="paperid" type="text"
								value="<bean:write name="employee" property="sysTEmpext.paperid"/>" />
						</td>
						<td>
							性别
						</td>
						<td>
							<select name="sex" style="width: 180px">
								<logic:present name="sexs" scope="request">
									<bean:write name="sexs" filter="false" />
								</logic:present>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							民族
						</td>
						<td>
							<select name="nation" style="width: 180px">
								<logic:present name="nations" scope="request">
									<bean:write name="nations" filter="false" />
								</logic:present>
							</select>
						</td>
						<td>
							电子邮箱
						</td>
						<td>
							<input name="email" type="text" style="width: 180px"
								value="<bean:write name="employee" property="sysTEmpext.email"/>" />
						</td>
					</tr>
					<tr>
						<td>
							办公电话
						</td>
						<td>
							<input name="officephone1" type="text" style="width: 180px"
								value="<bean:write name="employee" property="sysTEmpext.officephone1"/>" />
						</td>
						<td>
							传真电话
						</td>
						<td>
							<input name="officephone2" type="text" style="width: 180px"
								value="<bean:write name="employee" property="sysTEmpext.officephone2"/>" />
						</td>
					</tr>
					<tr>
						<td>
							住宅电话
						</td>
						<td>
							<input name="homephone" type="text" style="width: 180px"
								value="<bean:write name="employee" property="sysTEmpext.homephone"/>" />
						</td>
						<td>
							手机
						</td>
						<td>
							<input name="mobilephone" type="text" style="width: 180px"
								value="<bean:write name="employee" property="sysTEmpext.mobilephone"/>" />
						</td>
					</tr>
					<tr>
						<td colspan="4" height="30" align="center" valign="middle">
							<button onclick="save()">
								保存信息
							</button>
						</td>
					</tr>
				</table>
			</form>
		</logic:present>
	</fieldset>
</body>
</html:html>
<script type="text/javascript">
	<!--
		function save(){
			var sex = inputForm.sex.value;
			var nation =inputForm.nation.value;
			if(sex==""){
				alert("性别未选择");
				return
			}
			if(nation==""){
				alert("民族未选择");
				return
			}
			if(confirm("你确认保存信息？")){
				inputForm.submit();
			}
		}
	//-->
</script>
