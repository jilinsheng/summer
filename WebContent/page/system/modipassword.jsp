<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<title>�޸�����</title>
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

.field {
	font-size: 9pt;
	font-weight: 300;
	color: #000066;
}

.value {
	font-size: 9pt;
	font-weight: 300;
	color: #000000;
}
-->
</style>
<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script></head>
<body>
	<br />
	<fieldset id="list">
		<legend class="legend">
			�޸�����
		</legend>
		<form action="modipassword.do" name="inputForm" id="inputForm">
			<div id="delfam">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="60" height="30" scope="row">
							������
						</td>
						<td>
							<input id="oldpwd" name="oldpwd" type="password"
								onchange="checkoldpwd(this)" />
						</td>
					</tr>
					<tr>
						<td height="25" scope="row">
							������
						</td>
						<td>
							<input name="newpwd" type="password" id="newpwd" />
						</td>
					</tr>
					<tr>
						<td height="25" scope="row">
							ȷ������
						</td>
						<td>
							<input name="confirmpwd" type="password" id="confirmpwd" />
						</td>
					</tr>
				</table>
			</div>
			<div>
				<button id="btn" onclick="checkform()">
					����
				</button>
			</div>
		</form>
	</fieldset>
	<span id="waiting"></span>
</body>
</html:html>
<script type="text/javascript">
	<!--
	function checkform(){
		var newpwd= inputForm.newpwd.value;
		var confirmpwd =inputForm.confirmpwd.value;
		if(newpwd==null||newpwd==""){
			alert("�����벻��Ϊ��");
			return false;
		}
		if(confirmpwd==null||confirmpwd.value==""){
			alert("ȷ�����벻��Ϊ��");
			return false;
		}
		if(confirmpwd!=newpwd){
			alert("��������ȷ�����벻һ��");
			
		}else{
			if(confirm("��ȷ���޸�����!")){ 
				inputForm.submit();
			}else{
				return false;
			}
		}
	}		
		function checkoldpwd(obj){
			var url = '/db/page/system/checkoldpwd.do';
			$('waiting').innerHTML="������֤ԭ����......";
			$('waiting').style.display="block";
        	var pars="oldpwd="+obj.value;
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: showresult
					       }
					       );
					  
		}
		function showresult(response){
			//alert(response.responseText);
			if(response.responseText=="1"){
				$('btn').style.display="block";
			}else {
				alert("ԭ������֤��һ��");
				$('btn').style.display="none";
			}
			$('waiting').style.display="none";
		}
	-->
</script>