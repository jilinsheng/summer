<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>选择单位</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
</head>
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
<body>
	<br />
	<fieldset id="list">
		<legend class="legend">
			查询单位
		</legend>
		<form name="inputForm" action="unitquery.do" method="post">
			单位名称：
			<input type="text" name="unitname" />
			&nbsp;
			<button onclick="unitquery()">
				查询
			</button>
		</form>

	</fieldset>
	<logic:present name="arr" scope="request">
		<table width="100%" cellpadding="0" cellspacing="0" class="table7">
			<tr>
				<th>
					操作
				</th>
				<th>
					单位名称
				</th>
				<th>
					单位地址
				</th>
				<th>
					修改
				</th>
			</tr>
			<logic:iterate id="rs" name="arr" scope="request">
				<tr>
					<td>
						<input type="radio" name="radio"
							value="<bean:write name="rs" property="unitId"/>"
							onclick="selectunit(this)" />
					</td>
					<td id="td<bean:write name="rs" property="unitId"/>">
						<bean:write name="rs" property="unitname" />
					</td>
					<td>
						<bean:write name="rs" property="address" />
					</td>
					<td>
						<a
							href="unitsaveinit.do?unitid=<bean:write name="rs" property="unitId"/>"
							target="_self">修改 </a>
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="3">
					<bean:write name="toolsmenu" filter="false" />
				</td>
			</tr>
		</table>
	</logic:present>
	<logic:present name="unitform" scope="request">
		<fieldset id="list">
			<legend class="legend">
				添加新单位
			</legend>
			<form name="unitform" action="unitsave.do" method="post" onsubmit="return(checkform(this));">
				<bean:write name="unitform" filter="false" />
			</form>
		</fieldset>
	</logic:present>
	<script type="text/javascript">
		<!-- 
			function unitquery(){
				if(""==inputForm.unitname.value){
					alert("请输入单位名称");
				}else{
					inputForm.submit();
				}
			}
			function selectunit(obj){
				var oSelect = window.opener.document.getElementById("RESUME/MAINWORKPLACE");
				//getElementById("RESUME/MAINWORKPLACE");
				//getElementsByName("RESUME/MAINWORKPLACE")[0]
				var txt = document.getElementById("td"+obj.value).firstChild.nodeValue;
				oSelect.innerHTML=txt+"<input type=\"hidden\" name=\"RESUME/MAINWORKPLACE\" value=\""+obj.value+"\"/>";
				//var oOption =window.opener.document.createElement("OPTION");
				//oOption.text=txt;
				//oOption.value=obj.value;
				//oSelect.add(oOption);
			}
			function checkunit(){
				var url = '/db/page/system/unit/checkunit.do';
				var pars="unitname="+unitform.unitname.value;
				var myAjax = new Ajax.Request(
					url,{
						method: 'post',
						parameters: pars,
						onComplete: showeditinit
					}
				);
			}
			function showeditinit(response){
				var t =response.responseText;
				if(t==1){
					alert("单位名称已经存在，请查询选择填入");
					$('insbnt').style.display="block";
					}else{
					$('insbnt').style.display="block";
					}
				
			}
			function checkform(cform){
				var industry = cform.industry.value;
				var unittype = cform.unittype.value;
				var runstate = cform.runstate.value;
				if(industry==""){
					alert("请选择行业类型! ");
					return false;
				}
				if(unittype==""){
					alert("请选择企业类型! ");
					return false;
				}
				if(runstate==""){
					alert("请选择运营状态! ");
					return false;
				}
				return true;
			}
		-->
	</script>
</body>
</html:html>