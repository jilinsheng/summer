<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�����ϴ�</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<script type="text/javascript">
			function checkform(){
				if(""==inputform.annexname.value){
					alert('��д�ϴ��ļ�����');
					return false;
				} else if (""==inputform.annex.value){
					alert('ѡ���ϴ����ļ�');
					return false;
				}else {
					return true;
					}
			}
	</script>
	<body>
		<table width="99%" cellspacing="0" cellpadding="0" class="table8">
			<tr>
				<th>
					����
				</th>
				<th>
					����
				</th>
				<th colspan="2">
					����
				</th>
			</tr>
			<c:forEach var="rs" items="${annexlist}">
				<tr>
					<td>
						${rs.annexname}
					</td>
					<td>
						${rs.remark}
					</td>
					<td width="60">
					
					<a href="annexbrowser.do?annexId=${rs.annexId}" target="_blank">���</a>
					</td>
					<td width="60">
						<a href="annexinit.do?familyId=${familyId}&annexId=${rs.annexId}" onclick="return confirm('�Ƿ�ɾ���������� ��Ϣ?')">ɾ��</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<form name="inputform" action="annexsave.do" method="post" enctype="multipart/form-data" onsubmit="return checkform();">
			<input name="familyId" type="hidden" value="${familyId}"/>
			<table width="99%" cellspacing="0" cellpadding="0" class="table8">
				<tr>
					<th width="160">
						��������
					</th>
					<td>
						<input name="annexname" type="text" value="" />
					</td>
				</tr>
				<tr>
					<th width="160">
						ѡ���ļ�
					</th>
					<td>
						<input name="annex" type="file" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input value="�ϴ�" type="submit" style="width:120" class="btn"/>
					</td>
				</tr>
			</table>
		</form>
		<p align="center">
		${str}
		</p>
		<p align="center">
		֧�ֵ�ͼƬ��ʽ��.jpg .bmp .png<br>
		֧�ֵ���Ƶ��ʽ��.avi .rm .mpg
		</p>
	</body>
</html>
