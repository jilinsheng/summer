<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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

		<title>证件重新打印</title>

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
<script type="text/javascript">
<!-- 
	function blankout(prcid,fid,ptid){
		url="paperprintredohandle.do?prcid="+prcid+"&ptid="+ptid+"&fid="+fid;
		var flag=false;
		window.showModalDialog(url,flag,"dialogHeight:500px;dialogWidth:600px;scrollbars:0;status:0;help:0;edge:sunken");
		if(confirm("是否重新打印?")){
		var a ="paperprint.do?prcid="+prcid+"&ptid="+ptid+"&fid="+fid;
			window.location.reload(a); 
			}else {window.location.reload(location.href)}; 
	}
-->
</script>
	<body>
		<br>
		家庭编号：
		<c:out value="${requestScope.familyno}"></c:out>
		&nbsp;&nbsp;&nbsp;&nbsp;户主姓名：
		<c:out value="${requestScope.mastername}"></c:out>
		<br>
		<br>
		<table width="80%" cellpadding="0" cellspacing="0" class="table7"
			align="center">
			<tr>
				<th>
					证件名称
				</th>
				<th>
					打印时间
				</th>
				<th>
					操作
				</th>
			</tr>
			<c:forEach var="rs" items="${requestScope.uses}">
				<tr>
					<td>
						<c:out value="${rs.implTPapertype.papername}"></c:out>
					</td>
					<td>
						<c:out value="${rs.opttime}"></c:out>
					</td>
					<td>
					<button class="btn" onclick="blankout('${rs.paperrecordId}','${rs.familyId}','${rs.implTPapertype.papertypeId}')">作废</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
