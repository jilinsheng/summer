<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>报表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	</head>
	<body>
		<br>
		<div align="left">
			<c:out value="${requestScope.str}"></c:out>
		</div>
		<br>
		<form name="inputform" action="jasper.do" method="post"
			target="_blank">选择月份：
			<select name="month">
				<c:forEach items="${requestScope.monlist}" var="rs">
					<option value="${rs.accyear}${rs.accmonth}">
						${rs.accyear}年${rs.accmonth}月
					</option>
				</c:forEach>
			</select>
			&nbsp;&nbsp;
			<input type="submit" value="统计" />
		</form>
	</body>
</html>
