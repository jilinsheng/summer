<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>软件测试报告</title>
		<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript" src="../js/Validator.js"></script>
		<script type="text/javascript">
		function checkform(){
			if(!CheckText("问题位置",$("location"),true,100)){ 
				return false;
			}	
			if(!CheckText("问题描述",$("content"),true,2000)){ 
				return false;
			}	
			if(!CheckText("修改建议",$("propose"),true,2000)){ 
				return false;
			} 
			return true;
		}
		function writemsg(){
			if(!checkform()) return false;
			var url = '/db/page/oa/writemsg.do';
			$('waiting').innerHTML="正在保存数据......";
			$('waiting').style.display="block";
			var oForm =$("inputForm");
        	var pars=Form.serialize(oForm);
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: showresult
					        }
					       );
					  
		}
		function showresult(response){
			alert(response.responseText); 
			$('waiting').style.display="none";
		}  
		</script>
	<link rel="stylesheet" href="../css/style.css" type="text/css"></link></head>
	<body>
		<form action="displaymsg.jsp" method="post" name="inputForm"
			id="inputForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table7">
				<tr>
					<td>
						<label>
							问题位置
							<input name="location" type="text" size="47" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>
							问题描述
							<textarea name="content" cols="45" rows="5"></textarea>
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<label>
							修改建议
							<textarea name="propose" cols="45" rows="5"></textarea>
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" name="button" value="提交" onClick="writemsg()">
						<input type="button" name="button2" id="button" value="关闭"
							onClick="javascript:window.close();">
					</td>
				</tr>
			</table>
		</form>
		<div id="waiting"></div>
	</body>
</html>
