<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base>
    
    <title>查看文件内容</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
  </head>
  
  <body>
   	<br>
		作者:
		<c:out value="${requestScope.ownername}"></c:out>
		<br>
		<br>
		<table width="98%" cellpadding="0" cellspacing="0" border="0"
			class="table7">
			<tr height="24">
				<th width="140">
					标题
				</th>
				<td>
					<div align="left">
						<c:out value="${requestScope.notice.title}"></c:out>
					</div>
				</td>
			</tr>
			<tr height="24">
				<th width="140">
					内容
				</th>
				<td>
					<div align="left">
						<c:out value="${requestScope.notice.content}"></c:out>
					</div>
				</td>
			</tr>
			<tr>
			<th width="140">
				接收人
			</th>
			<td>
				<div align="left">
				<c:out value="${requestScope.recename}"></c:out>
				</div>
			</td>
			</tr>
			<tr>
				<th width="140">
					附件信息
				</th>
				<td>
					<div align="left">
						<c:forEach var="rs" items="${requestScope.filelist}">
							<div style="padding: 4px">
								<c:set value="${rs.filename}" var="temp" scope="page" />
								<%
								String temp = (String) pageContext.getAttribute("temp");
							%>
								<%
								temp = temp.substring(temp.lastIndexOf("-") + 1);
								%>
								<a href="oafileview.do?fileid=<c:out value="${rs.fileId}"/>"
									target="_blank"> <%
								out.println(temp);
							%> </a>
							</div>
						</c:forEach>
					</div>
				</td>
			</tr>
		</table>
		<p align="center">
			<button onclick=javascript:window.close();>
				关闭
			</button>
		</p>
  </body>
</html>