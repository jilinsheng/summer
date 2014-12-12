<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>恢复注销的家庭</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<script type="text/javascript">
	  <!--
	  		function winclose(){
			  window.opener.location.href = window.opener.location.href; 
			  if (window.opener.progressWindow) {
				  window.opener.progressWindow.close();
				  }
			   window.close(); 
	  		} 
	  -->
  </script>
  <style type="text/css">
	body{
		color:red;
	}
	</style>
	<body>
		<p align="center">恢复家庭信息</p>
		<p align="center">
			<c:forEach items="${requestScope.list}" var="rs">
				姓名：<c:out value="${rs[0]}"></c:out>
				证件类型：<c:out value="${rs[1]}"></c:out>
				证件号码：<c:out value="${rs[2]}"></c:out>
				<br>
				<br>
			</c:forEach>
			<c:out value="${requestScope.resultstr}" />
		</p>
		<p align="center">
			<button onclick="winclose()" class="bnt">
				关闭
			</button>
		</p>
	</body>
</html>
