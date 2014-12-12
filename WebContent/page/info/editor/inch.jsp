<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
//String path = request.getContextPath();
String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
String memberId =request.getParameter("memberId");
String filename =request.getParameter("filename");
%>
<script type="text/javascript">
<!--
	var filename="<%=filename%>";
	var path="<%=basePath%>";
//-->
</script>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>’’∆¨</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/style.css" rel="stylesheet" type="text/css">
</head>

<body STYLE='OVERFLOW: SCROLL; OVERFLOW-Y: HIDDEN'
	onload="getInchPath()">
	<html:form action="/page/info/editor/inchsave" method="post"
		enctype="multipart/form-data">
		<table width="110" border="1" cellspacing="0" cellpadding="0"
			class="table1">
			<tr>
				<td width="100" height="160">
					<div id="view"
						style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod =scale);width:90px;height:120px;">
						<img id="inchimg" src="/db/page/images/nopic.gif" alt="’’∆¨"
							width="90" height="120" border="0" />
					</div>
				</td>
			</tr>
			<tr>
				<td height="20">
					<html:file property="file" style="width:110px" onchange="review()"></html:file>
					<input id="id" name="id" type="hidden" value="<%=memberId%>" />
				</td>
			</tr>
		</table>
	</html:form>
	<input id="con" type="hidden" name="con" value="aa"
		onpropertychange="formsubmit()" />
</body>
</html:html>

<script language="javascript" type="text/javascript">
<!-- 
	function getInchPath(){
		var value= window.parent.document.getElementsByName("MEMBER/PICPATH")[0].value;
		if(value!=""){ 
			document.getElementById("inchimg").src=path+"/upload/inch"+value;
		}else if (filename!=""&&filename!="null") {
			document.getElementById("inchimg").src=path+"/upload/inch"+filename;
		}else {
			document.getElementById("inchimg").src="/db/page/images/nopic.gif";
		}
	}
	function review(){ 
		var file= document.getElementById("file").value;
		document.getElementById("view").innerHTML="";
		document.getElementById("view").filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = file;
	}
	function formsubmit(){
		document.uploadForm.submit();
	}
-->
</script>