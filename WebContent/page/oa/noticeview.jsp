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
		<title>通知</title>
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
				}else{
					return true;
				}
			}
		-->
	</script>
	<body>
		<br>
		<form name="form1" method="post" action="noticeview.do"
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
							<button onclick="add()">添加附件</button>&nbsp;&nbsp;&nbsp;&nbsp;(上传文件总容量不能超过30兆)
						</div>
						<br>
						<div align="left" style="height: 20px; display: block" id="dfile1">
						</div>
					</td>
				</tr>
				<tr>
					<th height="143">
						接收单位
					</th>
					<td>
						<div align="left">
							<c:forEach var="rs" items="${requestScope.organlist}">
								<input name="orgid" type="checkbox" value="<c:out value="${rs.organizationId}"></c:out>" /><c:out value="${rs.orgname}"></c:out>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input value="发送" type="submit"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
