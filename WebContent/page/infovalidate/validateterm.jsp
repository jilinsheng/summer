<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>验证条件类型</title>
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
		<div style="height: 38px">
			<br>
			居民信息验证
			<br>
		</div>
		<table width="100%" cellpadding="0" cellspacing="0" border="0"
			class="table7">
			<tr>
				<th>
					验证名称
				</th>
			</tr>
			<!--
			<c:forEach items="${requestScope.list}" var="rs">
				<tr>
				<td><c:out value="${rs.name}"></c:out>
					</td>
					<td>
						<c:out value="${rs.itemdesc}"></c:out>
					</td>
					<td>
					<button class="btn" onclick="validateexecute('<c:out value="${rs.validatetermId}"></c:out>')">验证
					</button>
						  <input value="<c:out value="${rs.validatetermId}"></c:out>"
							name="radio" type="button"
							onclick="validateexecute('<c:out value="${rs.validatetermId}"></c:out>')" />
					</td>
					
				</tr>
			</c:forEach>
			
			-->
			<tr>
				<td>
					<select id="a" name="a">
						<c:forEach items="${requestScope.list}" var="rs">
							<option value="${rs.validatetermId}">
								${rs.name}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><button class="btn" onclick="validateexecute()">验证
					</button></td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
	<!-- 
		function validateexecute(){
			var  termId =a.options[a.selectedIndex].value;
			if(confirm('确定执行此验证?')){
				parent.frames['leftFrame'].location.reload('validateexecute.do?termId='+termId);
			}
		}
	-->
</script>
</html>