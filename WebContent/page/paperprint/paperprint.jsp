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
		<title>家庭证件打印页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>
	<script type="text/javascript">
		<!-- 
			function openprint(ptid,fid){
			
				url="printpaperpreivew.do?ptid="+ptid+"&fid="+fid;

				window.showModalDialog(url,parent,"dialogHeight:500px;dialogWidth:600px;scrollbars:0;status:0;help:0;edge:sunken");
				window.location.reload(location.href);
			}	
			function openprintinfo(ptid,fid){
			
				url="printpaperbrowse.do?ptid="+ptid+"&fid="+fid;

				window.showModalDialog(url,parent,"dialogHeight:500px;dialogWidth:600px;scrollbars:0;status:0;help:0;edge:sunken");

			}	
		-->
	</script>
	<body>
		<br>
			家庭编号：<c:out value="${requestScope.familyno}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;户主姓名：<c:out value="${requestScope.mastername}"></c:out><br><br>
		<table width="80%" cellpadding="0" cellspacing="0" class="table7" align="center">
		<tr>
		<th>证件名称</th>
		<th>当前状态</th>
		<th>操作</th>
		</tr><c:forEach var="rs" items="${requestScope.paperlist}">
				<tr>
					<td>
						<c:out value="${rs[1]}"></c:out>
					</td>
					<td valign="middle">
						<c:if test="${rs[5]=='1'}">已经打印</c:if>
						<c:if test="${rs[5]==null}">未打印 </c:if>
						<c:if test="${rs[5]=='0'}">已作废</c:if>
					</td>
					<td>
						<c:if test="${rs[5]=='1'}"><button class="btn" onclick="openprintinfo('<c:out value="${rs[0]}"></c:out>','<c:out value="${requestScope.familyId}"></c:out>')">查看打印情况</button>&nbsp;&nbsp;</c:if>
						<c:if test="${rs[5]==null}"><button class="btn" onclick="openprint('<c:out value="${rs[0]}"></c:out>','<c:out value="${requestScope.familyId}"></c:out>')">首次打印</button>&nbsp;&nbsp;</c:if>
						<c:if test="${rs[5]=='0'}"><button class="btn" onclick="openprint('<c:out value="${rs[0]}"></c:out>','<c:out value="${requestScope.familyId}"></c:out>')">重新打印</button>&nbsp;&nbsp;<button class="btn" onclick="openprintinfo('<c:out value="${rs[0]}"></c:out>','<c:out value="${requestScope.familyId}"></c:out>')">查看打印情况</button></c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
