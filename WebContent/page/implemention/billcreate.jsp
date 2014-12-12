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
		<title>�����˵�����</title>

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
								ѡ���ѽ��������
							</th>
						</tr>
						<c:forEach var="rs" items="${requestScope.overlist}">
							<tr>
								<td id="month<c:out value="${rs.optaccId}"></c:out>">
									<c:out value="${rs.accyear}"></c:out>��<c:out value="${rs.accmonth}"></c:out>��<c:out value="${rs.doperTPolicy.name}"></c:out>
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
								���ɷ����·�
							</th>
						</tr>
						<tr>
							<td>
								<div align="left">&nbsp;&nbsp;ѡ��ʵʩ�·ݣ�
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
								<div align="left">&nbsp;&nbsp;�ѽ���ҵ��<span id="monthview"></span><input id="monthhidden" name="monthhidden" type="hidden" value=""/></div>
							</td>
						</tr>
						<tr>
							<td>
								<div align="left"><input type="submit" value="���㷢������"/></div>
							</td>
						</tr>
					</table>
					</form>
					<br />
					<table width="100%" cellpadding="0" cellspacing="0" class="table7">
						<tr>
							<th>
								�����·��б�
							</th>
						</tr>
						<c:forEach var="rs" items="${requestScope.monthdonelist}">
							<tr>
								<td>
									<div align="left">
										&nbsp;&nbsp;�����·ݣ�<c:out value="${fn:substring(rs.monname,0,4)}��${fn:substring(rs.monname,4,6)}��"/>
										<a target="_self" href="billmonthdel.do?monthid=<c:out value="${rs.monthId}"></c:out>" >ɾ��</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a target="_self" href="billcreatefileview.do?monthid=<c:out value="${rs.monthId}"></c:out>" >�����ļ�</a>
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
			if(document.getElementById('monthname').value==''||'δѡ��'==document.getElementById('monthname').value){
				alert('δѡ�񷢷��·�����');
				return false;
			}
			if(document.getElementById('monthhidden').value==''){
				alert('δѡ���Ѿ������ҵ��');
				return false;
			}
			return true;
		}
	</script>
</html>
