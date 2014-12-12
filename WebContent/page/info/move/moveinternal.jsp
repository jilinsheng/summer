<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>区内迁移</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
	<logic:present name="html" scope="request">
		<fieldset id="list">
			<legend class="legend">
				区内迁移
			</legend>
			<form name="inputForm" action="moveinternal.do" method="post">
				<bean:write name="html" scope="request" filter="false" />
				<button onclick="save()">
					完成保存
				</button>
			</form>
		</fieldset>
	</logic:present>
	<logic:present name="result" scope="request">
			<p align="center" style="font-size:14px;color:red"><bean:write name="result" scope="request" /></p>
		</logic:present>
	<span id="waiting" style="display: none">正在读取数据......</span>
</body>
</html:html>
<script type="text/javascript">
		<!-- 
			function readnext(oldorg){
			
				var url = '/db/page/info/move/readnextorg.do';
				$('waiting').style.display="block";
				var pars='orgid='+oldorg+"&oldid="+inputForm.orgid.value;
		        var myAjax = new Ajax.Request(
						        url,{
						        method: 'post',
						        parameters: pars,
						        onComplete: showresult
						}
				);
			}
			function showresult(response){
				$('aaa').innerHTML= response.responseText;
				$('waiting').style.display="none";
				//inputForm.registeredaddress.value=inputForm.street.options[inputForm.street.options.selectedIndex].text;
				//inputForm.familyaddress.value=inputForm.street.options[inputForm.street.options.selectedIndex].text;
			}
			function save(){
				var c=  $('community').value;
				if(c==""){
					alert("村屯（社区）未选择");
					return false;
				}else{
					if(confirm("确认保存?")){
						inputForm.submit();
					}
				}
			}
			function read(){
				inputForm.registeredaddress.value=inputForm.fullname.value+inputForm.street.options[inputForm.street.options.selectedIndex].text+inputForm.community.options[inputForm.community.options.selectedIndex].text;
				inputForm.familyaddress.value=inputForm.fullname.value+inputForm.street.options[inputForm.street.options.selectedIndex].text+inputForm.community.options[inputForm.community.options.selectedIndex].text;
			}
		-->
	</script>
