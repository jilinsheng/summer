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
	<title>人员管理</title>
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
			人员列表
		</legend>
		<logic:present name="list" scope="request">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="table7">
				<tr>
					<th>
						操作
					</th>
					<th>
						姓名
					</th>
					<th>
						单位
					</th>
					<th>
						岗位
					</th>
					<th>
						工号
					</th>
					<th>
						电话
					</th>
					<th>
						角色
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
								复位密码
							</button>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<button onclick="add()" class="btn">
				新增
			</button>
		</logic:present>

	</fieldset>
	<fieldset id="emp" style="display: none">
		<legend class="legend">
			人员信息
		</legend>
		<div id="employee"></div>
	</fieldset>
	<span id="waiting" style="display: none"> 正在读取数据...... </span>
</body>
</html:html>
<script type="text/javascript">
	<!--
		function add(){
			var url = '/db/page/system/employeeinit.do';
			$('waiting').innerHTML="正在读取数据......";
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
					        onComplete: function(){alert("密码已经更改为1234567");}
					        }
					        );
		}
		function save(){
			
			$('waiting').innerHTML="正在保存数据......";
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
					        alert("保存成功");
					       if(response.responseText=="0"){
					        window.location.reload();}
					        }
					        }
					        );
		}
		function checkaccount(obj){	
		$('waiting').innerHTML="正在读取数据......";
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
					         alert("用户名重复");
					         $('b').style.display="none";
					         $('waiting').style.display="none";
					       }
					       }
					        }
					        );
		}
	-->
</script>