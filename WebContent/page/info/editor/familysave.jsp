<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>新建家庭</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript" src="../../js/Calendar2.js"></script>
	<script type="text/javascript" src="../../js/Validator.js"></script>
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>
<body style="overflow: hidden">
	<br />
	<form id="inputForm" class="inputForm">
		<fieldset>
			<legend>
				录入新家庭
			</legend>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<bean:write name="familyhtml" filter="false" />
			</table>
			<p>
				<span id="divResult" style="color: #000000"></span>
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="保存进入家庭信息修改页面" onclick="saveFamily('inputForm')" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button onclick="parentClose()">
					退出而不保存信息
				</button>
			</p>
		</fieldset>
	</form>
</body>
</html:html>
<script type="text/javascript">
<!--
	function checkForm(oForm){
		var pt = document.getElementById("MEMBER/PAPERTYPE");
		var els= oForm.elements;
		var j=els.length-1;
		for(var i=0;i<j;i++){
			var iObj=els[i];
			var oAtt = iObj.attributes;
			var ruleo= oAtt.getNamedItem("rule");
			if(ruleo!=null){
			var rule =ruleo.value;
		if(rule==9){
		}else{
			var title= oAtt.getNamedItem("title").value;
		if(rule==5){
			if (!CheckDate(title,iObj,true)) return title;
		}else if(rule==6){
			if (!CheckChinese(title,iObj,true,30)) return title;
		}else if(rule==7){
			if(!CheckNumber(title,iObj,true)) return title;
		}else if(rule==8){
			if(pt=="390"){
				if (!CheckIdCard(title,iObj)) return title;}
			}
		}
	}
	}
	return 'ok';
	}
	//保存家庭信息
	function saveFamily(inputForm){
		var url = '../../info/family/FamilySaveServlet';
        var oForm =document.getElementsByName(inputForm)[0];
        var pars=Form.serialize(oForm);
        var result=checkForm(oForm);
        if(result=="ok"){
         $('divResult').style.visibility ="visible";
         $('divResult').innerHTML ="<img src=\"../../images/loading.gif\" alt=\"进度条\" />正在保存...";
        }else{
         $('divResult').style.visibility ="visible";
         $('divResult').innerHTML ="项目:<"+result+">验证未通过!";
         return false;
        }
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'post',
        parameters: pars,
        onComplete: viewinfo
        });
        }
	function viewinfo(response){
 		var s= response.responseText.split(",");
 		if(s[0]=='保存成功'){
 			$('divResult').innerHTML = s[0];
 	 		$('divResult').style.visibility ="visible";
 	 		var url ="" ;
 	 		//window.location.replace("../../info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId="+ s[1]);
 	 		window.location.replace("../../info/neweditor/editorframe.jsp?nodeId="+ s[1]);
 	 	}
 	 	else{
 	 		$('divResult').innerHTML = s[0];
 	 	}
	}
	function parentClose(){
		window.location.reload("../../intro/addfamily.jsp");
	}
-->
</script>