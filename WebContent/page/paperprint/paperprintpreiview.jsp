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

		<title>֤����ӡԤ����ҳ��</title>

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
		function print(ptid , fid){
			var reason =document.getElementById('reason').value;
			window.location.reload('printpaperhandle.do?ptid='+ptid +'&fid='+fid+'&reason='+reason);
		}
  -->
  </script>
	<style type="text/css">
<!--
#list {
	padding: 4px;
	border: 3px double #f5a89a;
}

#emp {
	padding: 4px;
	border: 3px double #f5a89a;
}

.legend {
	font-size: 12px;
	font-weight: lighter;
	color: #000000;
}

lable {
	width: 90px;
	text-align: center;
}
-->
</style>
	<body>
		<fieldset id="list">
			<legend class="legend">
				��д��ӡԭ��
			</legend>
			<p>
				��ͥ��ţ�
				<c:out value="${requestScope.familyno}"></c:out>
			</p>
			<p>
				��&nbsp;&nbsp;&nbsp;&nbsp;����
				<c:out value="${requestScope.mastername}"></c:out>
			</p>
			<p>
				��&nbsp;&nbsp;&nbsp;&nbsp;�
				<c:out value="${requestScope.bizTFamilystatus.money}"></c:out>
			</p>
			<p>
				��֤ԭ��
				<input id="reason" name="reason" value="�״δ�ӡ" size="30" />
			</p>
			<p>
				<button
					onclick="print('<c:out value="${requestScope.ptid}"></c:out>','<c:out value="${requestScope.fid}"></c:out>')"
					class="btn">
					��ӡ֤��
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn" onclick="window.close()">
					&nbsp;��&nbsp;&nbsp;��&nbsp;
				</button>
			</p>
		</fieldset>
	</body>
</html>
