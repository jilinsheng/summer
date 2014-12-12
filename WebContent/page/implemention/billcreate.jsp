<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ page import="org.apache.struts.Globals" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>发放账单生成</title>

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
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="200px" valign="top">
					<table width="100%" cellpadding="0" cellspacing="0" class="table7">
						<tr>
							<th colspan="2">
								选择已结算的批次
							</th>
						</tr>
						<c:forEach var="rs" items="${requestScope.overlist}">
							<tr>
								<td id="month<c:out value="${rs.optaccId}"></c:out>">
									<c:out value="${rs.accyear}"></c:out>年<c:out value="${rs.accmonth}"></c:out>月<c:out value="${rs.doperTPolicy.name}"></c:out>
								</td>
								<td>
									<input type="checkbox" name="acc" value="<c:out value="${rs.optaccId}"></c:out>" onclick="selectmonths(this)"/>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td width="3px">
					&nbsp;
				</td>
				<td valign="top" align="left">
				<form name="inputform" action ="billmonthsave.do" method="post" onsubmit=" return checkmonth()";>
				<input name="monthid" type="hidden" value=""/>
				<input type="hidden" name="<%=Constants.TOKEN_KEY%>"   value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/>
					<table width="100%" cellpadding="0" cellspacing="0" class="table7">
						<tr>
							<th>
								生成发放月份
							</th>
						</tr>
						<tr>
							<td>
								<div align="left">&nbsp;&nbsp;选择实施月份：
									<select id="monthname" name="monthname">
										<c:forEach var="rs" items="${requestScope.months}">
											<option value="<c:out value="${rs.item}"></c:out>">
												<c:out value="${rs.item}"></c:out>
											</option>
										</c:forEach>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div align="left">&nbsp;&nbsp;已结算业务：<span id="monthview"></span><input id="monthhidden" name="monthhidden" type="hidden" value=""/></div>
							</td>
						</tr>
						<tr>
							<td>
								<div align="left"><input type="submit" value="计算发放数据"/></div>
							</td>
						</tr>
					</table>
					</form>
					<br />
					<table width="100%" cellpadding="0" cellspacing="0" class="table7">
						<tr>
							<th>
								发放月份列表
							</th>
						</tr>
						<c:forEach var="rs" items="${requestScope.monthdonelist}">
							<tr>
								<td>
									<div align="left">
										&nbsp;&nbsp;发放月份：<c:out value="${fn:substring(rs.monname,0,4)}年${fn:substring(rs.monname,4,6)}月"/>
										<a target="_self" href="billmonthdel.do?monthid=<c:out value="${rs.monthId}"></c:out>" >删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a target="_self" href="billcreatefileview.do?monthid=<c:out value="${rs.monthId}"></c:out>" >生成文件</a>
										<c:out value="${requestScope.delstr}"></c:out>
									</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
		function selectmonths(obj){
			var cid= obj.value;
			var td= document.getElementById('month'+cid);
			var content =td.innerText;
			var accs = document.getElementsByName('acc');
			var tempstr='';
			var viewstr='';
			for(var i=0;i<accs.length;i++){
				if(accs[i].checked){
					viewstr =viewstr +" "+document.getElementById('month'+accs[i].value).innerText;
					tempstr =tempstr+accs[i].value+',';
				}
			}
			var monthview = document.getElementById('monthview');
			monthview.innerText =viewstr;
			document.getElementById('monthhidden').innerText=tempstr;
		}
		function checkmonth(){
			if(document.getElementById('monthname').value==''||'未选择'==document.getElementById('monthname').value){
				alert('未选择发放月份名称');
				return false;
			}
			if(document.getElementById('monthhidden').value==''){
				alert('未选择已经结算的业务');
				return false;
			}
			return true;
		}
	</script>
</html>
