<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base>

<title>低保综合情况统计表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
</head>
<body>
<table width="100%" border="1" align="center" cellpadding="0"
	cellspacing="0" class="table7">
	<caption>低保综合情况统计表</caption>
	<tr>
		<td rowspan="3">地区<br />
		<br />
		项目</td>
		<td colspan="7">总体情况</td>
		<td colspan="5">重点对象情况</td>
		<td colspan="5">一般对象情况</td>
	</tr>
	<tr>
		<td>农业人口数</td>
		<td>低保户数</td>
		<td>低保人数</td>
		<td>占农业人口比例</td>
		<td>发放资金数额</td>
		<td>人均补助水平</td>
		<td>保障标准</td>
		<td>低保户数</td>
		<td>低保人数</td>
		<td>占对象总数比例</td>
		<td>发放资金数额</td>
		<td>人均补助水平</td>
		<td>低保户数</td>
		<td>低保人数</td>
		<td>占对象总数比例</td>
		<td>发放资金数额</td>
		<td>人均补助水平</td>
	</tr>
	<tr>
		<td>（人）</td>
		<td>（户）</td>
		<td>（人）</td>
		<td>（%）</td>
		<td>（元）</td>
		<td>（季/元）</td>
		<td>（元）</td>
		<td>（户）</td>
		<td>（人）</td>
		<td>（%）</td>
		<td>（元）</td>
		<td>（季/元）</td>
		<td>（户）</td>
		<td>（人）</td>
		<td>（%）</td>
		<td>（元）</td>
		<td>（季/元）</td>
	</tr>
	<c:forEach var="rs" items="${requestScope.statlist}">
		<tr>
			<td>${rs.oname}</td>
			<td>${rs.renkou}</td>
			<td>${rs.hs}</td>
			<td>${rs.ss}</td>
			<td>${rs.bl1}</td>
			<td>${rs.zm}</td>
			<td>${rs.bl2}</td>
			<td>${rs.biaozhun}</td>
			<td>${rs.zdhs}</td>
			<td>${rs.zdss}</td>
			<td>${rs.zdbl1}</td>
			<td>${rs.zdzm}</td>
			<td>${rs.zdbl2}</td>
			<td>${rs.ybhs}</td>
			<td>${rs.ybss}</td>
			<td>${rs.ybbl1}</td>
			<td>${rs.ybzm}</td>
			<td>${rs.ybbl2}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
