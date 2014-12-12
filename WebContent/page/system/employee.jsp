<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	String organizationId = (String) request
			.getAttribute("organizationId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>��Ա����</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
	<br />
	<fieldset id="list">
		<legend class="legend">
			��Ա�б�
		</legend>
		<logic:present name="list" scope="request">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="table7">
				<tr>
					<th>
						����
					</th>
					<th>
						����
					</th>
					<th>
						��λ
					</th>
					<th>
						��λ
					</th>
					<th>
						����
					</th>
					<th>
						�绰
					</th>
					<th>
						��ɫ
					</th>
					<th></th>
				</tr>
				<logic:iterate id="rs" name="list" scope="request">
					<tr>
						<td>
							<input type="radio" name="radio"
								value="<bean:write name="rs" property="employeeId"/>"
								onclick="checkemplooyee(this)" />
						</td>
						<td>
							<bean:write name="rs" property="sysTEmpext.name" />
						</td>
						<td>
							<bean:write name="organname" scope="request" />
						</td>
						<td>
							<bean:write name="rs" property="sysTPost.name" />
						</td>
						<td>
							<bean:write name="rs" property="sysTEmpext.workno" />
						</td>
						<td>
							<bean:write name="rs" property="sysTEmpext.officephone1" />
						</td>
						<td>
							<logic:iterate id="crs" property="sysTRoles" name="rs">
								<bean:write name="crs" property="name" />
							</logic:iterate>
						</td>
						<td>
							<button
								onclick="resetpwd(<bean:write name="rs" property="employeeId"/>)"
								class="btn">
								��λ����
							</button>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<button onclick="add()" class="btn">
				����
			</button>
		</logic:present>

	</fieldset>
	<fieldset id="emp" style="display: none">
		<legend class="legend">
			��Ա��Ϣ
		</legend>
		<div id="employee"></div>
	</fieldset>
	<span id="waiting" style="display: none"> ���ڶ�ȡ����...... </span>
</body>
</html:html>
<script type="text/javascript">
	<!--
		function add(){
			var url = '/db/page/system/employeeinit.do';
			$('waiting').innerHTML="���ڶ�ȡ����......";
			$('waiting').style.display="block";
			var pars='organizationId='+<%=organizationId%>+'&empid=';
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: showresultadd
					        }
					        );
					  
		}
		function checkemplooyee(obj){
			var url = '/db/page/system/employeeinit.do';
			$('waiting').style.display="block";
			var pars='organizationId='+<%=organizationId%>+'&empid='+obj.value;
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
			$('employee').innerHTML=response.responseText;
			$('emp').style.display="block";
			$('waiting').style.display="none";
		}
		function showresultadd(response){
		//alert(response.responseText);
			$('employee').innerHTML=response.responseText;
			$('emp').style.display="block";
			$('waiting').style.display="none";
			  $('b').style.display="none";
			
		}
		function resetpwd(num){
			var url = '/db/page/system/resetpwd.do';
			var pars='empid='+num;
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: function(){alert("�����Ѿ�����Ϊ1234567");}
					        }
					        );
		}
		function save(){
			
			$('waiting').innerHTML="���ڱ�������......";
			$('waiting').style.display="block";
			
			var url = '/db/page/system/employeesave.do';
			var oForm =document.getElementById("inputForm");
        	var pars=Form.serialize(oForm);
        
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: function(response){
					        $('waiting').style.display="none";
					        alert("����ɹ�");
					       if(response.responseText=="0"){
					        window.location.reload();}
					        }
					        }
					        );
		}
		function checkaccount(obj){	
		$('waiting').innerHTML="���ڶ�ȡ����......";
			$('waiting').style.display="block";
		 $('b').style.display="none";
			var url = '/db/page/system/checkaccount.do';
        	var pars="account="+obj.value;
        
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: function(response){     
					       if(response.responseText=="0"){
					          $('b').style.display="block";
					          $('waiting').style.display="none";
					       }else{
					         alert("�û����ظ�");
					         $('b').style.display="none";
					         $('waiting').style.display="none";
					       }
					       }
					        }
					        );
		}
	-->
</script>