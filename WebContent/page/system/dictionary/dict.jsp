<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>字典类别</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
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
	</head>

	<body>
		<fieldset id="list">
			<legend class="legend">
				字典类别
			</legend>
		<form name="inputForm" action="dictsortsave.do" method="post"><br>
			<input type="hidden" name="dictid"
				value="${requestScope.dict.dictsortId}" />
			字典类别名称:
			<input type="text" name="dsname" value="${requestScope.dict.name}" />
			<br><br>
			<input type="submit" value="保存"/>
		</form>
		</fieldset>
	</body>
</html>
