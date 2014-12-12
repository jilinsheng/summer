<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base>
<title>农村固定保障审批页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
</head>
<script type="text/javascript">
	function a(optcheckId){
		var url ="baseapplyinit.do?policyId=21&optcheckId="+optcheckId;
	    var feature = "dialogWidth:750px;dialogHeight:650px;center:yes;status:no;help:no";
	    showModalDialog(url,window,feature);
	   var objtr= document.getElementById('bb'+optcheckId);
	   objtr.style.color ='blue';
	}
</script>
<body>
	<br>
	<div align="left">
		<c:out value="${requestScope.str}"></c:out>
	</div>
	<br>
	<form name="inputform" action="baseapplyquery.do" method="post">
		<input type="hidden" name="policyId" value="21" />
		查询项目： <select name="type">
			<option value="all">全部</option>
			<option value="mastername">户主姓名</option>
			<option value="paperid">身份证号</option>
			<option value="famno">家庭编号</option>
		</select> &nbsp;查&nbsp;询&nbsp;值： <input type="text" name="value" value=""
			size="12" /> &nbsp;保障类型： <select name="sal">
			<option value="">全部</option>
			<option value="876">重点户一档</option>
			<option value="877">重点户二档</option>
			<option value="878">重点户三档</option>
			<option value="873">一般户一档</option>
			<option value="874">一般户二档</option>
			<option value="875">一般户三档</option>
		</select>&nbsp;审批类型：
		<select name="moneyflag">
		<option value="">全部</option> 
		<option value="1">新增</option> 
		<option value="3">调高</option> 
		<option value="2">顺延</option> 
		 <option value="4">降低</option> 
		<option value="5">到期待续</option> 
		 <option value="6">暂停</option> 
		<option value="7">不达标</option> 
		</select>
		&nbsp;所属： <select name="onno">
			<c:forEach items="${requestScope.orglist}" var="rs">
				<option value="${rs.organizationId}">${rs.orgname }</option>
			</c:forEach>
		</select> &nbsp; <input type="submit" value="查询" class="btn" /> &nbsp;
		<button onclick="exportXLS()" class="btn">导出excel</button>
	</form>
	<table align="center" width="100%" cellspacing="0" cellpadding="0"
		class="table7">
		<tr><th>操作</th>
			<th>家庭编号</th>
			<th>户主姓名</th>
			<th>证件号码</th>
			<th>保障人口</th>
			<th>人均收入</th>
			<th>计算保障金</th>
			<th>审批类型</th>
			<th>审批信息</th>
			<th>保障类型</th>
			<th>当前状态</th>
		</tr>
		<c:forEach items="${requestScope.pas}" var="rss">
			<tr id ="bb${rss.optcheckId}">
			<td>
			<input type="radio" name="aradio" onclick="a('${rss.optcheckId}')"/>
			</td>
				<td>${rss.familyno}</td>
				<td>${rss.mastername}</td>
				<td>${rss.masterpaperid}</td>
				<td>${rss.ensurecount}人</td>
				<td>${rss.avgincome}元 <c:if
						test="${rss.linemoney-rss.avgincome>=0}">&nbsp;&nbsp;
						<font color="green">(符&nbsp;&nbsp;合)</font>
					</c:if> <c:if test="${rss.linemoney-rss.avgincome<0}">
						&nbsp;&nbsp;<font color="red">(不符合)</font>
					</c:if>
				</td>
				<td>${rss.countmoney}元</td>
				<td><c:if test="${rss.moneyflag =='1'}">新增</c:if> <c:if
						test="${rss.moneyflag =='3'}">调高</c:if> <c:if
						test="${rss.moneyflag =='2'}">顺延</c:if> <c:if
						test="${rss.moneyflag =='4'}">降低</c:if> <c:if
						test="${rss.moneyflag =='5'}">到期待续</c:if> <c:if
						test="${rss.moneyflag =='6'}">暂停</c:if> <c:if
						test="${rss.moneyflag =='7'}">不达标</c:if></td>
				<td>村：<U><font color="red"><c:if
								test="${rss.checkflag1 =='1'}">审批中</c:if> <c:if
								test="${rss.checkflag1 =='2'}">同意救助</c:if> <c:if
								test="${rss.checkflag1 =='3'}">重新核查</c:if> <c:if
								test="${rss.checkflag1 =='4'}">渐退</c:if> <c:if
								test="${rss.checkflag1 =='5'}">延续</c:if> <c:if
								test="${rss.checkflag1 =='6'}">暂停</c:if> <c:if
								test="${rss.checkflag1 =='7'}">恢复</c:if> <c:if
								test="${rss.checkflag1 =='8'}">终止</c:if>
								<c:if test="${rss.checkflag1 =='9'}">不同意救助</c:if></font></U>&nbsp;&nbsp;乡镇 ：<U><font
						color="red"><c:if test="${rss.checkflag2 =='1'}">审批中</c:if>
							<c:if test="${rss.checkflag2 =='2'}">同意救助</c:if> <c:if
								test="${rss.checkflag2 =='3'}">重新核查</c:if> <c:if
								test="${rss.checkflag2 =='4'}">渐退</c:if> <c:if
								test="${rss.checkflag2 =='5'}">延续</c:if> <c:if
								test="${rss.checkflag2 =='6'}">暂停</c:if> <c:if
								test="${rss.checkflag2 =='7'}">恢复</c:if> <c:if
								test="${rss.checkflag2=='8'}">终止</c:if>
								<c:if test="${rss.checkflag2 =='9'}">不同意救助</c:if></font></U>&nbsp;&nbsp;区县 ：<U><font
						color="red"><c:if test="${rss.checkflag3 =='1'}">审批中</c:if>
							<c:if test="${rss.checkflag3 =='2'}">同意救助</c:if> <c:if
								test="${rss.checkflag3 =='3'}">重新核查</c:if> <c:if
								test="${rss.checkflag3 =='4'}">渐退</c:if> <c:if
								test="${rss.checkflag3 =='5'}">延续</c:if> <c:if
								test="${rss.checkflag3 =='6'}">暂停</c:if> <c:if
								test="${rss.checkflag3 =='7'}">恢复</c:if> <c:if
								test="${rss.checkflag3 =='8'}">终止</c:if>
								<c:if test="${rss.checkflag3 =='9'}">不同意救助</c:if>
								</font></U></td>
				<td><c:if test="${rss.saltype == '876'}">重点户一档</c:if> <c:if
						test="${rss.saltype == '877'}">重点户二档</c:if> <c:if
						test="${rss.saltype == '878'}">重点户三档</c:if> <c:if
						test="${rss.saltype == '873'}">一般户一档</c:if> <c:if
						test="${rss.saltype == '874'}">一般户二档</c:if> <c:if
						test="${rss.saltype == '875'}">一般户三档</c:if></td>
				<td><c:if test="${rss.flag=='1'}">普通户</c:if> <c:if
						test="${rss.flag=='2'}">在保户</c:if> <c:if test="${rss.flag=='3'}">停保户</c:if>
					<c:if test="${rss.flag==null}">普通户</c:if> , <c:if
						test="${rss.money==null}"></c:if> <c:if test="${rss.money!=null}"> ${rss.money}元</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<p align="right">
		<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
	</p>
</body>
</html>