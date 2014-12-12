<%@ page contentType="text/html; charset=GB18030" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%
			String url = request.getParameter("url");
			if (url == null) {
				url = "javascript:#";
			} else if (url.indexOf("?") >= 0) {
				url = url + "&";
			} else {
				url = url + "?";
			}
		%>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>结果处理页面</title>
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<body>
		<div id="data">
		</div>
		<input type="button"  value="增加新家庭" onclick="locationpage()"/>
	</body>
</html>
<script type="text/JavaScript">
<!--
function locationpage(num){
	if(num==null||num==""){
		num="";
	}
	var url ="<%=url%>";
	url =url+"nodeId="+num
	MM_goToURL('parent.frames[\'content\']',url);
}
function MM_goToURL() { //v3.0
	var i, args=MM_goToURL.arguments;
	for (i=0; i<(args.length-1); i+=2) 
	eval(args[i]+".location='"+args[i+1]+"'");
}
//-->
</script>