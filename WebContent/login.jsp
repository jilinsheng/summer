<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>

<%
java.net.InetAddress   localhost   =   java.net.InetAddress.getLocalHost();
String a = request.getRequestURL().substring(0,request.getRequestURL().length()-request.getRequestURI().length());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>����ʡ����ۺϾ���������Ϣϵͳ#<%=localhost.getLocalHost()%>#<%=a%>#</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

body,td,th {
	font-family: ����;
	font-size: 12px;
	color: #FFFFFF
}
-->
</style>
	</head>
	<script type="text/javascript">
		function asubmit(){ 
			var type=loginForm.select.value;
			if('1'==type){
				loginForm.action='http://10.1.3.14/db/login.do';
				//alert(loginForm.action);
			}else{
				loginForm.action='/db/login.do';
				//alert(loginForm.action);
			}
		}
</script>
	<body onload="asubmit()">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="middle">
					<form name="loginForm" action="/db/login.do" method="post">
						<table width="99%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									&nbsp;

								</td>
								<td>
									&nbsp;

								</td>
								<td>
									&nbsp;

								</td>
							</tr>
							<tr>
								<td>
									&nbsp;

								</td>
								<td background="images/login_new.jpg" width="640" height="480">
									<table width="99%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td width="100" height="24">
												&nbsp;
											</td>
											<td width="100">
												&nbsp;
											</td>
											<td width="70">
												ҵ�����ͣ�
											</td>
											<td width="164">
												<select name="select" onblur="asubmit()">
													<option value="1">���еͱ�</option>
													<option value="2" selected="selected">ũ��ͱ�</option>
												</select>
											</td>
											<td width="100">
												&nbsp;
											</td>
											<td width="100">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												��&nbsp;��&nbsp;����
											</td>
											<td>
												<input type="text" name="user" style="width=100%"
													tabindex="2" />
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												��&nbsp;&nbsp;&nbsp;&nbsp;�룺
											</td>
											<td>
												<input type="password" name="pass" tabindex="3"
													style="width=100%" />
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												<div align="center">
													<input type="submit" name="Submit" value="��¼" />
													&nbsp;&nbsp; &nbsp; &nbsp;
													<input type="button" name="close" value="�˳�"
														onClick="window.close();" />
												</div>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2" align="center" valign="middle">
												����ʡ����ͱ�����
												<br>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="24">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td colspan="2" align="center" valign="middle">
												������������������ι�˾
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
									</table>

								</td>
								<td>
									&nbsp;

								</td>
							</tr>
							<tr>
								<td>
									&nbsp;

								</td>
								<td align="center">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
