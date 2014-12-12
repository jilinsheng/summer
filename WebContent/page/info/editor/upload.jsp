<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
String type= (String)request.getAttribute("type");
String url=(String)request.getAttribute("url");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>文件上传</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #DEEFFF;
}
-->
</style>
		<link href="/db/page/css/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<html:form action="/page/info/editor/upload"
			enctype="multipart/form-data" method="post" target="_self">
			<input type="hidden" name="origin" value="<%=url%>" />
			<logic:present name="str" scope="request">
				<table width="100%" border="0" class="table1" cellpadding="0"
					cellspacing="0">
					<bean:write name="str" scope="request" filter="false" />
					<tr>
						<th>
							上传文件
						</th>
						<td><html:file property="file"  onchange="review()"></html:file>
						</td>
					</tr>
				</table>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="savebnt"><input type="button" value="保存" onclick="formcommit()"/><input type="hidden" name="type" value="<%=type%>"/>
					</span>&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="divResult" style="color: red"></span>	
				</p>
				<div id="pic" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);width:400px;height:300px;">
				<logic:present name="url" scope="request">
					<img src="<%=url%>" width="400" height="300"/>
				</logic:present>
				</div>
				<div id="sp" style="display:none">
				</div>
			</logic:present>
		</html:form>
	</body>
</html>
<script language="javascript" type="text/javascript">
<!-- 
	function review(){
		var file= document.getElementById("file").value;
		//alert(file);
		var suffix = file.lastIndexOf('.');
		var suf=file.substr(suffix);
		//alert(suf);
		if(suf=='.mpg'||suf=='.wmv'){
			uploadForm.type.value="2";
		alert('视频');
		document.getElementById("pic").style.display='none';
		}else if(suf=='.jpg'||suf=='.bmp'||suf=='.gif'||suf=='png'){
			uploadForm.type.value="1";
			document.getElementById("pic").style.display='block';
			Preview(file);
		}else{
		uploadForm.type.value="0";
			document.getElementById("pic").style.display='none';
			alert('不支持这个文件类型');
		}
	} 
	function Preview(file){
		document.getElementById("pic").innerHTML=""; 
		document.getElementById("pic").filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = file;
	}
	function formcommit(){
		//alert(uploadForm.type.value);
	if(uploadForm.type.value==0){
		alert("没有选定的文件");
		}else{
		uploadForm.submit();
		}
	}
-->
</script>
<%
if(type.equals("1")){

}else if(type.equals("2")){
out.println("<script>document.getElementById(\"pic\").style.display='none';</script>");
out.println("<script>document.getElementById(\"sp\").style.display='block';</script>");
out.println("<script>document.getElementById(\"sp\").innerHTML='<a href=\"/db/page/info/card/view.do?filename="+url+"\"  target=\"_blank\">浏览视频文件</a>'</script>");
}else{
out.println("<script>document.getElementById(\"pic\").style.display='none';</script>");
} %>