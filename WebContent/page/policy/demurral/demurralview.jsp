<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>���鴦��</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
<script type="text/javascript">
	function hi1(){
		inputform.style.display="none";
		id1.style.display="block";
	}
	function hi2(){
		id1.style.display="none";
		inputform.style.display="block";
	}
</script>
	<body>
		<table width="100%" border="1" cellpadding="0" cellspacing="0"
			bordercolordark="#FFFFFF" bordercolorlight="#999999">
			<c:forEach var="rs" items="${requestScope.delist}">
				<tr>
					<td width="86%" bgcolor="#F3F3F3">
						<div align="left">
							����ʱ�䣺${rs.publishdate} �����ˣ�${rs.approvetype}
						</div>
					</td>
					<td width="80" height="26">
						<div align="right">
							<a href="demurraldel.do?deid=${rs.demurralId}"> ɾ�� </a>
						</div>
					</td>
				</tr>
				<tr>
					<td height="107" colspan="2">
						<p>
							�������ݣ�${rs.content}
						</p>
						<p>
							����������ݣ�${rs.approve}
						</p>
				</tr>
			</c:forEach>
		</table>
		<form id="inputform" style="display:none" action="demurralsave.do" method="post">
			<input name="ssid" type="hidden" value="${requestScope.ssid}" />
			<table width="100%" class="table7">
				<tr>
					<th width="100">
						�������ݣ�
					</th>
					<td>
						<textarea style="width:100%" rows="3" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<th width="100">
						����������ݣ�
					</th>
					<td>
						<textarea style="width:100%" rows="3" name="approve"></textarea>
					</td>
				</tr>
				<tr>
				<td colspan="2" align="center">
				<input type="submit"   class="btn" value="����"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn" onclick="hi1()">�ر�</button>
				</td>
				</tr>
			</table>
		</form>
		<div id="id1" align="center">
			<button class="btn" onclick="hi2()">
				�����������
			</button>
		</div>
	</body>
</html>
