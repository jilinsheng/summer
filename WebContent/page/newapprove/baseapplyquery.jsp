<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base>
<title>ũ��̶���������ҳ��</title>
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
		��ѯ��Ŀ�� <select name="type">
			<option value="all">ȫ��</option>
			<option value="mastername">��������</option>
			<option value="paperid">���֤��</option>
			<option value="famno">��ͥ���</option>
		</select> &nbsp;��&nbsp;ѯ&nbsp;ֵ�� <input type="text" name="value" value=""
			size="12" /> &nbsp;�������ͣ� <select name="sal">
			<option value="">ȫ��</option>
			<option value="876">�ص㻧һ��</option>
			<option value="877">�ص㻧����</option>
			<option value="878">�ص㻧����</option>
			<option value="873">һ�㻧һ��</option>
			<option value="874">һ�㻧����</option>
			<option value="875">һ�㻧����</option>
		</select>&nbsp;�������ͣ�
		<select name="moneyflag">
		<option value="">ȫ��</option> 
		<option value="1">����</option> 
		<option value="3">����</option> 
		<option value="2">˳��</option> 
		 <option value="4">����</option> 
		<option value="5">���ڴ���</option> 
		 <option value="6">��ͣ</option> 
		<option value="7">�����</option> 
		</select>
		&nbsp;������ <select name="onno">
			<c:forEach items="${requestScope.orglist}" var="rs">
				<option value="${rs.organizationId}">${rs.orgname }</option>
			</c:forEach>
		</select> &nbsp; <input type="submit" value="��ѯ" class="btn" /> &nbsp;
		<button onclick="exportXLS()" class="btn">����excel</button>
	</form>
	<table align="center" width="100%" cellspacing="0" cellpadding="0"
		class="table7">
		<tr><th>����</th>
			<th>��ͥ���</th>
			<th>��������</th>
			<th>֤������</th>
			<th>�����˿�</th>
			<th>�˾�����</th>
			<th>���㱣�Ͻ�</th>
			<th>��������</th>
			<th>������Ϣ</th>
			<th>��������</th>
			<th>��ǰ״̬</th>
		</tr>
		<c:forEach items="${requestScope.pas}" var="rss">
			<tr id ="bb${rss.optcheckId}">
			<td>
			<input type="radio" name="aradio" onclick="a('${rss.optcheckId}')"/>
			</td>
				<td>${rss.familyno}</td>
				<td>${rss.mastername}</td>
				<td>${rss.masterpaperid}</td>
				<td>${rss.ensurecount}��</td>
				<td>${rss.avgincome}Ԫ <c:if
						test="${rss.linemoney-rss.avgincome>=0}">&nbsp;&nbsp;
						<font color="green">(��&nbsp;&nbsp;��)</font>
					</c:if> <c:if test="${rss.linemoney-rss.avgincome<0}">
						&nbsp;&nbsp;<font color="red">(������)</font>
					</c:if>
				</td>
				<td>${rss.countmoney}Ԫ</td>
				<td><c:if test="${rss.moneyflag =='1'}">����</c:if> <c:if
						test="${rss.moneyflag =='3'}">����</c:if> <c:if
						test="${rss.moneyflag =='2'}">˳��</c:if> <c:if
						test="${rss.moneyflag =='4'}">����</c:if> <c:if
						test="${rss.moneyflag =='5'}">���ڴ���</c:if> <c:if
						test="${rss.moneyflag =='6'}">��ͣ</c:if> <c:if
						test="${rss.moneyflag =='7'}">�����</c:if></td>
				<td>�壺<U><font color="red"><c:if
								test="${rss.checkflag1 =='1'}">������</c:if> <c:if
								test="${rss.checkflag1 =='2'}">ͬ�����</c:if> <c:if
								test="${rss.checkflag1 =='3'}">���º˲�</c:if> <c:if
								test="${rss.checkflag1 =='4'}">����</c:if> <c:if
								test="${rss.checkflag1 =='5'}">����</c:if> <c:if
								test="${rss.checkflag1 =='6'}">��ͣ</c:if> <c:if
								test="${rss.checkflag1 =='7'}">�ָ�</c:if> <c:if
								test="${rss.checkflag1 =='8'}">��ֹ</c:if>
								<c:if test="${rss.checkflag1 =='9'}">��ͬ�����</c:if></font></U>&nbsp;&nbsp;���� ��<U><font
						color="red"><c:if test="${rss.checkflag2 =='1'}">������</c:if>
							<c:if test="${rss.checkflag2 =='2'}">ͬ�����</c:if> <c:if
								test="${rss.checkflag2 =='3'}">���º˲�</c:if> <c:if
								test="${rss.checkflag2 =='4'}">����</c:if> <c:if
								test="${rss.checkflag2 =='5'}">����</c:if> <c:if
								test="${rss.checkflag2 =='6'}">��ͣ</c:if> <c:if
								test="${rss.checkflag2 =='7'}">�ָ�</c:if> <c:if
								test="${rss.checkflag2=='8'}">��ֹ</c:if>
								<c:if test="${rss.checkflag2 =='9'}">��ͬ�����</c:if></font></U>&nbsp;&nbsp;���� ��<U><font
						color="red"><c:if test="${rss.checkflag3 =='1'}">������</c:if>
							<c:if test="${rss.checkflag3 =='2'}">ͬ�����</c:if> <c:if
								test="${rss.checkflag3 =='3'}">���º˲�</c:if> <c:if
								test="${rss.checkflag3 =='4'}">����</c:if> <c:if
								test="${rss.checkflag3 =='5'}">����</c:if> <c:if
								test="${rss.checkflag3 =='6'}">��ͣ</c:if> <c:if
								test="${rss.checkflag3 =='7'}">�ָ�</c:if> <c:if
								test="${rss.checkflag3 =='8'}">��ֹ</c:if>
								<c:if test="${rss.checkflag3 =='9'}">��ͬ�����</c:if>
								</font></U></td>
				<td><c:if test="${rss.saltype == '876'}">�ص㻧һ��</c:if> <c:if
						test="${rss.saltype == '877'}">�ص㻧����</c:if> <c:if
						test="${rss.saltype == '878'}">�ص㻧����</c:if> <c:if
						test="${rss.saltype == '873'}">һ�㻧һ��</c:if> <c:if
						test="${rss.saltype == '874'}">һ�㻧����</c:if> <c:if
						test="${rss.saltype == '875'}">һ�㻧����</c:if></td>
				<td><c:if test="${rss.flag=='1'}">��ͨ��</c:if> <c:if
						test="${rss.flag=='2'}">�ڱ���</c:if> <c:if test="${rss.flag=='3'}">ͣ����</c:if>
					<c:if test="${rss.flag==null}">��ͨ��</c:if> , <c:if
						test="${rss.money==null}"></c:if> <c:if test="${rss.money!=null}"> ${rss.money}Ԫ</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<p align="right">
		<c:out value="${requestScope.toolsmenu}" escapeXml="false"></c:out>
	</p>
</body>
</html>