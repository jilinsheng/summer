<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>表管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
<!--
body {
	margin: 3;
	margin-top: 0;
	padding: 0;
	background: #DEEFFF;
	font-family: "宋体";
	font-size: 12px;
}

input {
	font-family: "宋体";
	font-size: 12px;
}

.pointer {
	cursor: pointer;
}

.itemstyle {
	font-size: 12px;
	color: #6BA6FF;
}

.tablexiu {
	border-collapse: collapse;
	border: 1px solid #999;
	border-width: 1px 0 0 1px;
	margin: 2px 0 2px 0;
	text-align: center;
}

.tablexiu td {
	height: 23px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	font-size: 12px;
	background-color: #FFF;
	text-align: center;
}

.tablexiu th {
	height: 20px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	text-align: center;
	font-size: 12px;
	text-align: center;
	font-weight: bold;
	color: #FFFFFF;
	background-color: #6BA6FF;
}

#resultdiv {
	position: absolute;
	left: 5%;
	top: 10%;
	z-index: 2;
	background-color: #FFCCCC;
	color: #FF0099;
	font-size: 12px;
	display: none;
}
-->
</style>
		<script type="text/javascript">
		//
		//
		//
		//
		var myobj = window.dialogArguments;
		var qtableid = myobj[0];
	    var qmode = myobj[1];
		//
		//
		//打开表字段设置窗体
		function CallFieldDialog()
		{
			var myobj2 = new Array();
			myobj2[0] = qtableid;	
			myobj2[1] = "edit";	
			showModalDialog("/db/page/info/tablemanage/fieldmanage.jsp",myobj2,"status:false;dialogWidth:500px;dialogHeight:400px");
		}	
	</script>
	</head>

	<body>
		<table class="tablexiu" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<th colspan='4'>
					属性描述
				</th>
			</tr>
			<tr align="center" class='itemstyle'>
				<td width="20%">
					<span>父表名称</span>
				</td>
				<td width="30%">
					<input style="width: 100%; text-align: right;" type="text" id=""></input>
				</td>
				<td width="20%">
					<span>父表物理名</span>
				</td>
				<td width="30%">
					<input style="width: 100%; text-align: right;" type="text" id=""></input>
				</td>
			</tr>
			<tr align="center" class='itemstyle'>
				<td>
					<span>表名称</span>
				</td>
				<td>
					<input style="width: 100%; text-align: right;" type="text" id=""></input>
				</td>
				<td>
					<span>表物理名</span>
				</td>
				<td>
					<input style="width: 100%; text-align: right;" type="text" id=""></input>
				</td>
			</tr>
			<tr align="center" class='itemstyle'>
				<td>
					<span>表描述</span>
				</td>
				<td colspan='3'>
					<input style="width: 100%; text-align: right;" type="text" id=""></input>
				</td>
			</tr>
			<tr align="center" class='itemstyle'>
				<td>
					<span>可扩展表</span>
				</td>
				<td>
					<input type="checkbox" id=""></input>
					可以
				</td>
				<td>
					<span>可扩展字段</span>
				</td>
				<td>
					<input type="checkbox" id=""></input>
					可以
				</td>
			</tr>
			<tr align="center" class='itemstyle'>
				<td>
					<span>排序</span>
				</td>
				<td>
					<input style="width: 100%; text-align: right;" type="text" id=""></input>
				</td>
				<td>
					<span>在用状态</span>
				</td>
				<td>
					<input type="checkbox" id=""></input>
					在用
				</td>
			</tr>
			<tr align="center" class='itemstyle'>
				<td colspan='2'>
					<span id='' class='pointer' onclick="CallFieldDialog()">[表字段设置]</span>
				</td>
				<td colspan='2'>
					<input type='button' value='保存' onclick=''></input>
				</td>
			</tr>
		</table>
	</body>
</html>
