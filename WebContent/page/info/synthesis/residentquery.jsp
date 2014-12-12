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
		<title>家庭信息查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript">
		function check(obj){
					var url = 'residentdict.do';
					var pars='column='+obj.options[obj.selectedIndex].value;
			        var myAjax = new Ajax.Request(
							        url,{
							        method: 'post',
							        parameters: pars,
							        onComplete: showfindchild
							}
					);
				}
				function showfindchild(response){
					$('value').innerHTML= response.responseText;
				}
				function vieworder(){
					inputform.submit();
				}
		</script>
		<script type="text/javascript" src="../../js/TableSorter.js"></script>
	</head>
	<body>
		<br>
		<form name="inputform" action="residentquery.do?o=1" method="post">
			<input type="hidden" name="sql"
				value="<c:out value="${requestScope.sql}"/>" />
			<c:out value="${requestScope.formxml}" escapeXml="false"></c:out>
			<input type="submit" value="查询" />&nbsp;&nbsp;<button onclick="exportXLS()">
				导出excel
			</button><table id="tcol" align="center" width="100%" cellspacing="0"
				cellpadding="0" class="table7">
				<tr>
					<c:out value="${requestScope.thead}" escapeXml="false"></c:out>
				</tr>
				<c:out value="${requestScope.html}" escapeXml="false"></c:out>
			</table>
			<p align="right">
				<c:out value="${requestScope.tr}" escapeXml="false">&nbsp;</c:out>
				<c:out value="${requestScope.orderxml}" escapeXml="false"></c:out>
				<c:out value="${requestScope.radioxml}" escapeXml="false"></c:out>
				&nbsp;
				<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
			</p>
		</form>
	</body>
</html>
<script type="text/javascript">
	new TableSorter("tcol");
	</script>
	<script>
	function exportXLS() {
		window
				.open(
						"../../system/exportfile/exportExcel.do",
						"",
						"height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
</script>