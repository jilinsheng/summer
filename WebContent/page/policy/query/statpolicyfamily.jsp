<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>ͳ�Ʒ���</title>
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
			alert("��ѡ���·�!");
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
			ѡ���·ݣ�
			<select id ="monthname" name="monthname">
			<option  value="" selected="selected">δѡ��...</option>
				<c:forEach items="${requestScope.monlist}" var="rs">
					<option value="${rs.accyear}${rs.accmonth}">
						${rs.accyear}��${rs.accmonth}��
					</option>
				</c:forEach>
			</select>
			&nbsp; ������
			<select  name="orgid">
				<c:forEach items="${requestScope.orglist}" var="rs">
					<option value="${rs.organizationId}">
						${rs.orgname }
					</option>
				</c:forEach>
			</select>
			&nbsp;
			<input type="submit" value="��ѯ" class="btn"/>
			&nbsp;
			<button onclick="exportXLS()" class="btn">
				����excel
			</button>
		</form>
		<c:if test="${requestScope.monthname!=null}">
			<p align="center">
				${fn:substring(requestScope.monthname, 0,
				4)}��${fn:substring(requestScope.monthname, 4, 6)}��ͳ��
			</p>
			<table align="center" width="100%" cellspacing="0" cellpadding="0"
				class="table7">
				<tr>
					<th rowspan="3">
						����					</th>
					<th rowspan="3">
						����					</th>
					<th rowspan="3">
						����					</th>
					<th rowspan="3">
						�ܽ��					</th>
					<th colspan="21">
						����					</th>
				</tr>
				<tr>
				  <th colspan="3"> �ص㻧һ�� </th>
				  <th colspan="3"> �ص㻧���� </th>
				  <th colspan="3"> �ص㻧���� </th>
				  <th colspan="3"> һ�㻧һ�� </th>
				  <th colspan="3"> һ�㻧���� </th>
				  <th colspan="3"> һ�㻧���� </th>
				   <th colspan="3"> �ޱ������� </th>
			  </tr>
				<tr>
					<th>��</th>
					<th>��</th>
					<th>���</th>
					<th>��</th>
					<th>��</th>
					<th>���</th>
					<th>��</th>
					<th>��</th>
					<th>���</th>
					<th>��</th>
					<th>��</th>
					<th>���</th>
					<th>��</th>
					<th>��</th>
					<th>���</th>
					<th>��</th>
					<th>��</th>
					<th>���</th>
					<th>��</th>
					<th>��</th>
					<th>���</th>
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