<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>接收文件</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	</head>
	<script type="text/javascript">
		<!-- 
			function delfile(i){
				i.parentNode.outerHTML='';
			}
			function add(){
				temp='<input name="file" type="file" id="file1" size="40"/><img style="padding-right:2px" src="../images/del.gif" onclick ="delfile(this)"></img>';
				var newRadioButton = document.createElement("div");
				dfile1.insertBefore(newRadioButton);
				newRadioButton.innerHTML=temp;
				//newRadioButton.childNodes[0].click();
			}
			function edit(){
				 var filestrs = document.getElementsByName('file');
				 var fso;
				 var fsize=0;
				 fso=new ActiveXObject("Scripting.FileSystemObject");
				 for(var i = 0 ; i<filestrs .length; i++){
					var f=fso.GetFile(filestrs[i].value);
					fsize =f.size+fsize;
				 }
				 if(fsize>30*1024*1024){
					 alert("上传文件太大");
					 return false;
					 
					}
				if('' ==form1.title.value){
					alert("请填写标题");
					return false;
				}else if (''==form1.context.value){
					alert("请填写内容");
					return false;
				}else if (''== form1.employee.value){
					alert("没有选择接收人员");
					} else{
					return true;
				}
			}
			var vorgid;
			function selPerson(oSel){
				var	orgid =oSel.options[oSel.selectedIndex].value;
				if(""==orgid){
					}else{
				vorgid =orgid;
				getChilds(orgid);
				getEmployees(orgid);
				}
			}
			function getChilds(orgid){
				var url = 'getChilds.do';
				var pars = 'orgid='+orgid;
				var myAjax = new Ajax.Request(url, {
					method :'post',
					parameters :pars,
					onComplete :showgetChilds
				});
			}
			function showgetChilds(response){
				 var l= vorgid.length/2+1;
				 var temp='';
				 for(var i=1;i<l;i++){
					temp +=$('zone'+i).outerHTML;
					}
				$('pri').innerHTML =temp +response.responseText;
			}
			function getEmployees(orgid){
				var url = 'getEmployees.do';
				var pars = 'orgid='+orgid;
				var myAjax = new Ajax.Request(url, {
					method :'post',
					parameters :pars,
					onComplete :showgetEmployees
				});
			}
			function showgetEmployees(response){
				$('emp').innerHTML =response.responseText;
			}
		-->
	</script>
	<body>
		<br>
		<form name="form1" method="post" action="transferview.do"
			enctype="multipart/form-data" onsubmit=" return edit()">
			<table width="98%" cellpadding="0" cellspacing="0" border="0"
				class="table7">
				<tr height="24">
					<th width="140">
						标题
					</th>
					<td>
						<div align="left">
							<input name="title" type="text" style="width: 100%">
						</div>
					</td>
				</tr>
				<tr>
					<th width="141">
						内容
					</th>
					<td>
						<div align="left">
							<textarea name="context" rows="5" style="width: 100%"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th height="143">
						附件
					</th>
					<td>
						<div align="left" style="height: 20px">
							<button onclick="add()">
								添加附件
							</button>
							&nbsp;&nbsp;&nbsp;&nbsp;(上传文件总容量不能超过30兆)
						</div>
						<br>
						<div align="left" style="height: 20px; display: block" id="dfile1">
						</div>
					</td>
				</tr>
				<tr>
					<th height="143">
						选择接收人
					</th>
					<td>
						<div id="pri" align="left">
							<span id="zone1"> <select name="sel"
									onchange="selPerson(this)">
									<option value="">
										未选择...
									</option>
									<option value="22">
										吉林省
									</option>
								</select></span>
						</div>
						<br>
						<div id ="emp" align="left">
						
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input value="发 送" type="submit" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
