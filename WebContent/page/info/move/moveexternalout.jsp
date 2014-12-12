<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>迁出区外</title>

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
				迁出
			</legend>
			<form name="inputForm" action="moveexternalout.do" method="post">
				<bean:write name="html" scope="request" filter="false" />
				<button onclick="save()">
					完成保存
				</button>
			</form>
		</fieldset>
	</logic:present>
	<logic:present name="result" scope="request">
			<p align="center"><bean:write name="result" scope="request" /></p>
		</logic:present>
	<span id="waiting" style="display: none">正在读取数据......</span>
	<div id ="ll"></div>
</body>
</html:html>
<script type="text/javascript">
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
			}
			function save(){
				var c=  $('county').value;
				if(c==""){
					alert("县（市、区）未选择");
					return false;
				}else{
					if(confirm("确认保存?")){
						inputForm.submit();
					}
				}
			}
			function getNextorg(orgid,oldorgid){
				var url = '/db/page/info/move/readnextorg1.do';
				$('waiting').style.display="block";
				var pars='orgid='+orgid+"&oldid="+oldorgid;
		        var myAjax = new Ajax.Request(
						        url,{
						        method: 'post',
						        parameters: pars,
						        onComplete: getNextorgresult
						}
				);
			}
			function getNextorgresult(response){
				var a = response.responseText;
				alert(a);
				$('ll').innerHTML= '<select id=\"county\" name=\"county\"> '+a+'</select>';
				$('waiting').style.display="none";
			}
	</script>

