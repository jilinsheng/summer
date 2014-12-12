<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>

<%
java.net.InetAddress   localhost   =   java.net.InetAddress.getLocalHost();
String a = request.getRequestURL().substring(0,request.getRequestURL().length()-request.getRequestURI().length());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>吉林省社会综合救助管理信息系统#<%=localhost.getLocalHost()%>#<%=a%>#</title>

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
	font-family: 宋体;
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
												业务类型：
											</td>
											<td width="164">
												<select name="select" onblur="asubmit()">
													<option value="1">城市低保</option>
													<option value="2" selected="selected">农村低保</option>
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
												用&nbsp;户&nbsp;名：
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
												密&nbsp;&nbsp;&nbsp;&nbsp;码：
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
													<input type="submit" name="Submit" value="登录" />
													&nbsp;&nbsp; &nbsp; &nbsp;
													<input type="button" name="close" value="退出"
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
												吉林省城乡低保中心
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
												吉林明达软件有限责任公司
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
