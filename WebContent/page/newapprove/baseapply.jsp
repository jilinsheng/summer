<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=gb18030">
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
<title>农村固定保障申请</title>
</head>
<body>
	<form name="inputform" action="baseapply.do" method="post">
		<input type="hidden" name="policyId" value="21" /> <input
			type="hidden" name="optcheckId" value="${pa.optcheckId}" /> <input
			type="hidden" name="familyId" value="${pa.familyId}" />
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>家庭编号</th>
				<td><c:out value="${pa.familyno}"></c:out></td>
				<th>户主姓名</th>
				<td><c:out value="${pa.mastername}"></c:out></td>
				<th>证件号码</th>
				<td><c:out value="${pa.masterpaperid}"></c:out></td>
			</tr>
			<tr>
				<th>保障人口</th>
				<td><c:out value="${pa.ensurecount}"></c:out></td>
				<th>人均收入</th>
				<td><c:out value="${pa.avgincome}"></c:out>元 <c:if
						test="${pa.linemoney-pa.avgincome>=0}">&nbsp;&nbsp;
						<font color="green">(符&nbsp;&nbsp;合)</font>
					</c:if> <c:if test="${pa.linemoney-pa.avgincome<0}">
						&nbsp;&nbsp;<font color="red">(不符合)</font>
					</c:if></td>
				<th>当前保障金</th>
				<td><c:out value="${pa.money}"></c:out>元</td>
			</tr>
			<tr>
				<th>当前状态</th>
				<td><c:if test="${pa.flag=='1'}">普通户</c:if> <c:if
						test="${pa.flag=='2'}">在保户</c:if> <c:if test="${pa.flag=='3'}">停保户</c:if>
					<c:if test="${pa.flag==null}">普通户</c:if></td>
				<th>保障类型</th>
				<td><c:if test="${pa.saltype == '876'}">重点户一档</c:if> <c:if
						test="${pa.saltype == '877'}">重点户二档</c:if> <c:if
						test="${pa.saltype == '878'}">重点户三档</c:if> <c:if
						test="${pa.saltype == '873'}">一般户一档</c:if> <c:if
						test="${pa.saltype == '874'}">一般户二档</c:if> <c:if
						test="${pa.saltype == '875'}">一般户三档</c:if>&nbsp;标准：<c:out
						value="${pa.planmoney}"></c:out>元</td>
				<th>当前保障金公式</th>
				<td><c:out value="${pa.ensurecount}"></c:out>x<c:out
						value="${pa.planmoney}"></c:out>元=<c:out value="${pa.countmoney}"></c:out>元
				</td>
			</tr>
			<tr>
				<th>审批类型</th>
				<td colspan="5"><c:if test="${pa.moneyflag =='1'}">新增</c:if> <c:if
						test="${pa.moneyflag =='3'}">调高</c:if> <c:if
						test="${pa.moneyflag =='2'}">顺延</c:if> <c:if
						test="${pa.moneyflag =='4'}">降低</c:if> <c:if
						test="${pa.moneyflag =='5'}">到期待续</c:if> <c:if
						test="${pa.moneyflag =='6'}">暂停</c:if> <c:if
						test="${pa.moneyflag =='7'}">不达标</c:if></td>
			</tr>
			<tr>
				<th>审批信息</th>
				<td colspan="5">(1).村级审批：<U><font color="red"><c:if
								test="${pa.checkflag1 =='1'}">审批中</c:if> <c:if
								test="${pa.checkflag1 =='2'}">同意救助</c:if> <c:if
								test="${pa.checkflag1 =='3'}">重新核查</c:if> <c:if
								test="${pa.checkflag1 =='4'}">渐退</c:if> <c:if
								test="${pa.checkflag1 =='5'}">延续</c:if> <c:if
								test="${pa.checkflag1 =='6'}">暂停</c:if> <c:if
								test="${pa.checkflag1 =='7'}">恢复</c:if> <c:if
								test="${pa.checkflag1 =='8'}">终止</c:if>
								<c:if test="${pa.checkflag3 =='9'}">不同意救助</c:if>
								</font></U>(2).乡镇审批：<U><font
						color="red"><c:if test="${pa.checkflag2 =='1'}">审批中</c:if>
							<c:if test="${pa.checkflag2 =='2'}">同意救助</c:if> <c:if
								test="${pa.checkflag2 =='3'}">重新核查</c:if> <c:if
								test="${pa.checkflag2 =='4'}">渐退</c:if> <c:if
								test="${pa.checkflag2 =='5'}">延续</c:if> <c:if
								test="${pa.checkflag2 =='6'}">暂停</c:if> <c:if
								test="${pa.checkflag2 =='7'}">恢复</c:if> <c:if
								test="${pa.checkflag2=='8'}">终止</c:if>
								<c:if test="${pa.checkflag3 =='9'}">不同意救助</c:if>
								</font></U>(3).区县审批：<U><font
						color="red"><c:if test="${pa.checkflag3 =='1'}">审批中</c:if>
							<c:if test="${pa.checkflag3 =='2'}">同意救助</c:if> <c:if
								test="${pa.checkflag3 =='3'}">重新核查</c:if> <c:if
								test="${pa.checkflag3 =='4'}">渐退</c:if> <c:if
								test="${pa.checkflag3 =='5'}">延续</c:if> <c:if
								test="${pa.checkflag3 =='6'}">暂停</c:if> <c:if
								test="${pa.checkflag3 =='7'}">恢复</c:if> <c:if
								test="${pa.checkflag3 =='8'}">终止</c:if>
								 <c:if test="${pa.checkflag3 =='9'}">不同意救助</c:if>
								</font></U>
				</td>
			</tr>
			<tr>
			<th>审批金额</th>
			<td>
			<input name="money" type="text" readonly="readonly" value="<c:out value="${pa.countmoney}"></c:out>">元
			</td>
				<th>选择审批结果</th>
				<td><select name="checkflag">
						<option value="2">同意救助</option>
						<option value="3">重新核查</option>
						<option value="4">渐退</option>
						<option value="5">延续</option>
						<option value="6">暂停</option>
						<option value="7">恢复</option>
						<option value="8">终止</option>
						<!--<option value="9">不同意救助</option>-->
				</select></td>
				<td><input type="submit" value="保存" /></td><td><button type="button" onclick="window.close()">关闭</button></td>
			</tr>
		</table>

	</form>
</body>
</html>