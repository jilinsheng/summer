<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>权限管理</title>

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
.rolediv {
	border: 1px solid #F5A89A;
}
</style>
	<script type="text/javascript" src="../js/dtree.js"></script>
	<link rel="stylesheet" href="../css/dtree.css" type="text/css"></link>
</head>
<body>
	<table width="99%" cellspacing="3" cellpadding="0" border="0">
		<tr>
			<td valign="top">
				<logic:present name="roles" scope="request">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="table7">
						<tr>
							<th colspan="3">
								角色列表
							</th>
						</tr>
						<logic:iterate id="rs" name="roles" scope="request">
							<tr id="<bean:write name="rs" property="roleId" />">
								<td>
									<logic:present name="cid" scope="request">
										<logic:equal value="${requestScope.cid}" name="rs"
											property="roleId">
											<input id="v<bean:write name="rs" property="roleId" />" type="radio" name="radio" checked="checked" onclick="editinit(<bean:write name="rs" property="roleId" />)"/>
										</logic:equal>
										<logic:notEqual value="${requestScope.cid}" name="rs"
											property="roleId">
											<input id="v<bean:write name="rs" property="roleId" />" type="radio" name="radio" onclick="editinit(<bean:write name="rs" property="roleId" />)"/>
										</logic:notEqual>
									</logic:present>
									<logic:notPresent name="cid" scope="request">
										<input id="v<bean:write name="rs" property="roleId" />" type="radio" name="radio" onclick="editinit(<bean:write name="rs" property="roleId" />)"/>
									</logic:notPresent>
								</td>
								<td id="title<bean:write name="rs" property="roleId" />">
									<bean:write name="rs" property="name" />
								</td>
								<td id="role<bean:write name="rs" property="roleId" />">
									<%--<button class="btn"
										onclick="editinit(<bean:write name="rs" property="roleId" />)">
										修改
									</button>
									--%><logic:equal value="1" name="rs" property="status">
										<button class="btn"
											onclick="changstatus(<bean:write name="rs" property="roleId" />,0)">
											停用
										</button>
									</logic:equal>
									<logic:equal value="0" name="rs" property="status">
										<button class="btn"
											onclick="changstatus(<bean:write name="rs" property="roleId" />,1)">
											恢复
										</button>
									</logic:equal>
								</td>
							</tr>
						</logic:iterate>
					</table>
					<button class="btn" onclick="editinit(-1)">
						新建角色
					</button>
				</logic:present>
				<span id="waiting" style="display: none"></span>
			</td>
			<!--  <td width="50%" id="menuitems" valign="top">
				<form id="inputForm">
				</form>
			</td>-->
		</tr>
	</table>
</body>
</html:html>
<script type="text/javascript">
<!--
	var id;
	function changstatus(roleId,status){
		id=roleId;
		var url = '/db/page/system/rolesave.do';
		$('waiting').innerHTML="正在保存数据......";
		$('waiting').style.display="block";
		var pars="roleId="+roleId+"&status="+status;
		var myAjax = new Ajax.Request(
			url,{
				method: 'post',
				parameters: pars,
				onComplete: showresult
				}
			);
	}
	function edit(){
		var url = '/db/page/system/role.do';
		$('waiting').style.display="block";
		var pars='';
		var myAjax = new Ajax.Request(
			url,{
				method: 'post',
				parameters: pars,
				onComplete: showresult
				}
			);
	}
	function editinit(roleId){
	if(roleId==-1){
	}else{
	$('v'+roleId).click();}
	parent.frames['mainFrame'].location.reload('roleinit.do?'+"roleId="+roleId);
	}
	
	function editinitbak(roleId){
		id=roleId;
		var url = '/db/page/system/roleinit.do';
		$('waiting').style.display="block";
		$('waiting').innerHTML="正在读取数据......";
		var pars="roleId="+roleId;
		var myAjax = new Ajax.Request(
			url,{
				method: 'post',
				parameters: pars,
				onComplete: showeditinit
			}
		);
	}
	function showresult(response){
		$('role'+id).innerHTML=response.responseText;
		$('waiting').style.display="none";
	}
	function showeditinit(response){
		$('inputForm').innerHTML=response.responseText;
		$('waiting').style.display="none";
	}
	
		
//-->
</script>