<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>业务查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
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
		<form name="inputform" action="queryPolicyFamily.do" method="post" onsubmit="return checkform();">
			<input type="hidden" name="policyId" value="${requestScope.policyId}" />
			选择月份：
			<select id="monthname" name="monthname">
			<option value="" selected="selected">未选择...</option>
				<c:forEach items="${requestScope.monlist}" var="rs">
					<option value="${rs.accyear}${rs.accmonth}">
						${rs.accyear}年${rs.accmonth}月
					</option>
				</c:forEach>
			</select>
			查询项目：
			<select name="type">
				<option value="all">
					全部
				</option>
				<option value="mastername">
					户主姓名
				</option>
				<option value="paperid">
					身份证号
				</option>
				<option value="famno">
					家庭编号
				</option>
			</select>
			&nbsp;查&nbsp;询&nbsp;值：
			<input type="text" name="value" value="" size="12" />
			&nbsp;保障类型：
			<select name="sal">
				<option value="">
					全部
				</option>
				<option value="876">
					重点户一档
				</option>
				<option value="877">
					重点户二档
				</option>
				<option value="878">
					重点户三档
				</option>
				<option value="873">
					一般户一档
				</option>
				<option value="874">
					一般户二档
				</option>
				<option value="875">
					一般户三档
				</option>
				<option value="879">
					无保障类型户
				</option>
			</select>
			&nbsp; 所属：
			<select name="orgid">
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
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>
					家庭编号
				</th>
				<th>
					户主姓名
				</th>
				<th>证件类型</th>
				<th>
					证件号码
				</th>
				<th>
					总金额
				</th>
				<th>
					保障类型
				</th>
			</tr>
			<c:out value="${requestScope.html}" escapeXml="false"></c:out>
		</table>
		<p align="right">
			<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
		</p>
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