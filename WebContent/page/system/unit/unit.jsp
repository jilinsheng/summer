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
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>

<body>
	<logic:present name="str" scope="request">
		<bean:write name="str" scope="request" />
	</logic:present>
	<logic:present name="unit" scope="request">
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
			</tr>
			<tr>
				<td>
					<input type="radio" name="radio"
						value="<bean:write name="unit" property="unitId"/>"
						onclick="selectunit(this)" />
				</td>
				<td id="td<bean:write name="unit" property="unitId"/>">
					<bean:write name="unit" property="unitname" />
				</td>
				<td>
					<bean:write name="unit" property="address" />
				</td>
			</tr>
		</table>
	</logic:present>
</body>
</html:html>
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
			function checkunit(obj){
				
			}
		-->
	</script>