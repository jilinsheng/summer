<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="org.dom4j.Document"%>
<%@page import="com.mingda.common.dictionary.DictionaryHandle"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�½���ͥ��Ϣ¼��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
		<link rel="stylesheet" href="/db/page/css/style.css" type="text/css"></link>
	-->

		<script type="text/javascript" src="../../js/Validator.js"></script>
		<style>
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: no-repeat;
	background-color: #FCDAD5;
}

body,td,th {
	font-size: 12px;
}

td,th {
	height: 26px;
}

.STYLE1 {
	color: #000000;
}

.STYLE2 {
	color: #000000
}
-->
</style>
	</head>
	<body style="overflow: hidden">
		<form id="inputForm" action="../../info/editor/editorquery.do"
			method="post" name="inputForm">
			<table border="0" cellpadding="0" cellspacing="0" width="99%"
				align="center">
				<tr>
					<td colspan="2" height="25" align="center"
						background="../../images/desktop_right_bg.gif">
						<span class="STYLE1">�½���ͥ��Ϣ¼��</span>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0" width="400" align="center">
							<tr>
								<td align="center" valign="middle" width="120">
									<span class="STYLE2">��&nbsp;&nbsp;&nbsp;&nbsp;�� </span>
								</td>
								<td align="center" valign="middle" width="280">
									<input name="rname" type="text" style="width:100%" />
								</td>
							</tr>
							<tr>
								<td align="center" valign="middle" width="120">
									<span class="STYLE2">֤������</span>
								</td>
								<td align="center" valign="middle">
									<select name="pt" style="width:100%">
										<%
											Document dict = (Document) pageContext.getServletContext()
													.getAttribute("dictionary");
											DictionaryHandle dh = new DictionaryHandle();
											out.println(dh.getDictsortToOption(dict, "270", "390"));
										%>
									</select>
								</td>
							</tr>
							<tr>
								<td align="center" valign="middle" width="120">
									<span class="STYLE2">֤������</span>
								</td>
								<td align="center" valign="middle">
									<input name="paperid" type="text"  style="width:100%"/>
								</td>
							</tr>
							<tr>
								<td align="center" valign="middle" colspan="2">
									<input type="button" value="�����¼�ͥ" onClick="checkform()" />
								</td>
							</tr>

						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script language="javascript" type="text/javascript">
<!--
	function checkform(){
		if(inputForm.rname.value==""||inputForm.paperid.value==""||inputForm.pt.value==""){
			alert("������֤�����͡�֤���Ų���Ϊ�� ");
			return;
		}
		if (!CheckChinese("����",inputForm.rname,true,30)){ return ;}
		
		if(inputForm.pt.value=="390"){
			if(CheckIdCard('���֤��',inputForm.paperid)){
				inputForm.submit();
		}
		
		}else{
			inputForm.submit();
			}

		}
-->
</script>