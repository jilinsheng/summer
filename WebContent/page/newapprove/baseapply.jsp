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
<title>ũ��̶���������</title>
</head>
<body>
	<form name="inputform" action="baseapply.do" method="post">
		<input type="hidden" name="policyId" value="21" /> <input
			type="hidden" name="optcheckId" value="${pa.optcheckId}" /> <input
			type="hidden" name="familyId" value="${pa.familyId}" />
		<table align="center" width="100%" cellspacing="0" cellpadding="0"
			class="table7">
			<tr>
				<th>��ͥ���</th>
				<td><c:out value="${pa.familyno}"></c:out></td>
				<th>��������</th>
				<td><c:out value="${pa.mastername}"></c:out></td>
				<th>֤������</th>
				<td><c:out value="${pa.masterpaperid}"></c:out></td>
			</tr>
			<tr>
				<th>�����˿�</th>
				<td><c:out value="${pa.ensurecount}"></c:out></td>
				<th>�˾�����</th>
				<td><c:out value="${pa.avgincome}"></c:out>Ԫ <c:if
						test="${pa.linemoney-pa.avgincome>=0}">&nbsp;&nbsp;
						<font color="green">(��&nbsp;&nbsp;��)</font>
					</c:if> <c:if test="${pa.linemoney-pa.avgincome<0}">
						&nbsp;&nbsp;<font color="red">(������)</font>
					</c:if></td>
				<th>��ǰ���Ͻ�</th>
				<td><c:out value="${pa.money}"></c:out>Ԫ</td>
			</tr>
			<tr>
				<th>��ǰ״̬</th>
				<td><c:if test="${pa.flag=='1'}">��ͨ��</c:if> <c:if
						test="${pa.flag=='2'}">�ڱ���</c:if> <c:if test="${pa.flag=='3'}">ͣ����</c:if>
					<c:if test="${pa.flag==null}">��ͨ��</c:if></td>
				<th>��������</th>
				<td><c:if test="${pa.saltype == '876'}">�ص㻧һ��</c:if> <c:if
						test="${pa.saltype == '877'}">�ص㻧����</c:if> <c:if
						test="${pa.saltype == '878'}">�ص㻧����</c:if> <c:if
						test="${pa.saltype == '873'}">һ�㻧һ��</c:if> <c:if
						test="${pa.saltype == '874'}">һ�㻧����</c:if> <c:if
						test="${pa.saltype == '875'}">һ�㻧����</c:if>&nbsp;��׼��<c:out
						value="${pa.planmoney}"></c:out>Ԫ</td>
				<th>��ǰ���Ͻ�ʽ</th>
				<td><c:out value="${pa.ensurecount}"></c:out>x<c:out
						value="${pa.planmoney}"></c:out>Ԫ=<c:out value="${pa.countmoney}"></c:out>Ԫ
				</td>
			</tr>
			<tr>
				<th>��������</th>
				<td colspan="5"><c:if test="${pa.moneyflag =='1'}">����</c:if> <c:if
						test="${pa.moneyflag =='3'}">����</c:if> <c:if
						test="${pa.moneyflag =='2'}">˳��</c:if> <c:if
						test="${pa.moneyflag =='4'}">����</c:if> <c:if
						test="${pa.moneyflag =='5'}">���ڴ���</c:if> <c:if
						test="${pa.moneyflag =='6'}">��ͣ</c:if> <c:if
						test="${pa.moneyflag =='7'}">�����</c:if></td>
			</tr>
			<tr>
				<th>������Ϣ</th>
				<td colspan="5">(1).�弶������<U><font color="red"><c:if
								test="${pa.checkflag1 =='1'}">������</c:if> <c:if
								test="${pa.checkflag1 =='2'}">ͬ�����</c:if> <c:if
								test="${pa.checkflag1 =='3'}">���º˲�</c:if> <c:if
								test="${pa.checkflag1 =='4'}">����</c:if> <c:if
								test="${pa.checkflag1 =='5'}">����</c:if> <c:if
								test="${pa.checkflag1 =='6'}">��ͣ</c:if> <c:if
								test="${pa.checkflag1 =='7'}">�ָ�</c:if> <c:if
								test="${pa.checkflag1 =='8'}">��ֹ</c:if>
								<c:if test="${pa.checkflag3 =='9'}">��ͬ�����</c:if>
								</font></U>(2).����������<U><font
						color="red"><c:if test="${pa.checkflag2 =='1'}">������</c:if>
							<c:if test="${pa.checkflag2 =='2'}">ͬ�����</c:if> <c:if
								test="${pa.checkflag2 =='3'}">���º˲�</c:if> <c:if
								test="${pa.checkflag2 =='4'}">����</c:if> <c:if
								test="${pa.checkflag2 =='5'}">����</c:if> <c:if
								test="${pa.checkflag2 =='6'}">��ͣ</c:if> <c:if
								test="${pa.checkflag2 =='7'}">�ָ�</c:if> <c:if
								test="${pa.checkflag2=='8'}">��ֹ</c:if>
								<c:if test="${pa.checkflag3 =='9'}">��ͬ�����</c:if>
								</font></U>(3).����������<U><font
						color="red"><c:if test="${pa.checkflag3 =='1'}">������</c:if>
							<c:if test="${pa.checkflag3 =='2'}">ͬ�����</c:if> <c:if
								test="${pa.checkflag3 =='3'}">���º˲�</c:if> <c:if
								test="${pa.checkflag3 =='4'}">����</c:if> <c:if
								test="${pa.checkflag3 =='5'}">����</c:if> <c:if
								test="${pa.checkflag3 =='6'}">��ͣ</c:if> <c:if
								test="${pa.checkflag3 =='7'}">�ָ�</c:if> <c:if
								test="${pa.checkflag3 =='8'}">��ֹ</c:if>
								 <c:if test="${pa.checkflag3 =='9'}">��ͬ�����</c:if>
								</font></U>
				</td>
			</tr>
			<tr>
			<th>�������</th>
			<td>
			<input name="money" type="text" readonly="readonly" value="<c:out value="${pa.countmoney}"></c:out>">Ԫ
			</td>
				<th>ѡ���������</th>
				<td><select name="checkflag">
						<option value="2">ͬ�����</option>
						<option value="3">���º˲�</option>
						<option value="4">����</option>
						<option value="5">����</option>
						<option value="6">��ͣ</option>
						<option value="7">�ָ�</option>
						<option value="8">��ֹ</option>
						<!--<option value="9">��ͬ�����</option>-->
				</select></td>
				<td><input type="submit" value="����" /></td><td><button type="button" onclick="window.close()">�ر�</button></td>
			</tr>
		</table>

	</form>
</body>
</html>