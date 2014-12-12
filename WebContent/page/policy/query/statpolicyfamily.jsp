<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>统计分析</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<script type="text/javascript">
	<!-- 
		function checkform(){
			var flag=false;
			
			if(inputform.monthname.value==''){
			alert("请选择月份!");
				flag= false;
			}else{
				flag= true;
			}
			return flag;
		}	
	-->
	</script>
	<body>
		<br>
		<form name="inputform" action="statPolicyFamily.do" method="post" onsubmit="return checkform()">
			<input type="hidden" name="policyId" value="${requestScope.policyId}" />
			选择月份：
			<select id ="monthname" name="monthname">
			<option  value="" selected="selected">未选择...</option>
				<c:forEach items="${requestScope.monlist}" var="rs">
					<option value="${rs.accyear}${rs.accmonth}">
						${rs.accyear}年${rs.accmonth}月
					</option>
				</c:forEach>
			</select>
			&nbsp; 所属：
			<select  name="orgid">
				<c:forEach items="${requestScope.orglist}" var="rs">
					<option value="${rs.organizationId}">
						${rs.orgname }
					</option>
				</c:forEach>
			</select>
			&nbsp;
			<input type="submit" value="查询" class="btn"/>
			&nbsp;
			<button onclick="exportXLS()" class="btn">
				导出excel
			</button>
		</form>
		<c:if test="${requestScope.monthname!=null}">
			<p align="center">
				${fn:substring(requestScope.monthname, 0,
				4)}年${fn:substring(requestScope.monthname, 4, 6)}月统计
			</p>
			<table align="center" width="100%" cellspacing="0" cellpadding="0"
				class="table7">
				<tr>
					<th rowspan="3">
						地区					</th>
					<th rowspan="3">
						户数					</th>
					<th rowspan="3">
						人数					</th>
					<th rowspan="3">
						总金额					</th>
					<th colspan="21">
						其中					</th>
				</tr>
				<tr>
				  <th colspan="3"> 重点户一档 </th>
				  <th colspan="3"> 重点户二档 </th>
				  <th colspan="3"> 重点户三档 </th>
				  <th colspan="3"> 一般户一档 </th>
				  <th colspan="3"> 一般户二档 </th>
				  <th colspan="3"> 一般户三档 </th>
				   <th colspan="3"> 无保障类型 </th>
			  </tr>
				<tr>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
					<th>户</th>
					<th>人</th>
					<th>金额</th>
				</tr>
				<c:out value="${requestScope.html}" escapeXml="false"></c:out>
			</table>
		</c:if>
	</body>
</html>
<script>
	function exportXLS() {
		window
				.open(
						"../../system/exportfile/exportExcel.do",
						"",
						"height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
</script>