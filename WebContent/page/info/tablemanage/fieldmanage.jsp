<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�ֶι���</title>
    
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
			font-family: "����";
			font-size: 12px;
		}
		input {			
			font-family: "����";
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
		#resultdiv{
			position:absolute;
			left:5%; 
			top:10%;
			z-index:2;
			background-color: #FFCCCC;
			color: #FF0099;
			font-size:12px;
			display:none;
		}
		-->
	</style>
	<script type="text/javascript">
		//
		//
		var myobj = window.dialogArguments;
		var qtableid = myobj[0];
	    var qmode = myobj[1];
		//
	</script>
  </head>
  
  <body>
  	<table class="tablexiu" width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr valign="top">
	    	<td width="20%">
	    		<span>�ֶ������б�</span>
	    	</td>
	    	<td>
	    		<table class="tablexiu"width="100%" border="0" cellspacing="0" cellpadding="0">
			    	<tr>
			    	<th colspan = '4'>����������</th>
			    	</tr>
			    	<tr align="center" class = 'itemstyle'>
			    		<td width="20%"><span>�ֶ�����</span></td>
				        <td width="30%"><input style="width:100%;text-align:right;" type="text" id=""></input></td>
				        <td width="20%"><span>�ֶ�������</span></td>
				        <td width="30%"><input style="width:100%;text-align:right;" type="text" id=""></input></td>
			    	</tr>
			    	<tr align="center" class = 'itemstyle'>
			    		<td><span>�ֶ�����</span></td>
				        <td colspan = '3'><input style="width:100%;text-align:right;" type="text" id=""></input></td>	        
			    	</tr>
			    	<tr align="center" class = 'itemstyle'>
			    		<td><span>�ֶ�����</span></td>
				        <td><input style="width:100%;text-align:right;" type="text" id=""></input></td>
				        <td><span>�ֶγ���</span></td>
				        <td><input style="width:100%;text-align:right;" type="text" id=""></input></td>
			    	</tr>
			    	<tr align="center" class = 'itemstyle'>
			    		<td><span>С��λ��</span></td>
				        <td><input style="width:100%;text-align:right;" type="text" id=""></input></td>
				        <td><span>Ĭ��ֵ</span></td>
				        <td><input style="width:100%;text-align:right;" type="text" id=""></input></td>
			    	</tr>    	
			    	<tr align="center" class = 'itemstyle'>
			    		<td><span>�ֵ�����</span></td>
				        <td><input style="width:100%;text-align:right;" type="text" id=""></input></td>
				        <td><span>�Ƿ�ɿ�</span></td>
				        <td><input type="checkbox" id=""></input>�ɿ�</td>
			    	</tr>
			    	<tr align="center" class = 'itemstyle'>
			    		<td><span>����</span></td>
				        <td><input style="width:100%;text-align:right;" type="text" id=""></input></td>
				        <td><span>����״̬</span></td>
				        <td><input type="checkbox" id=""></input>����</td>
			    	</tr>
			    </table>
	    	</td>    	
    	</tr>
    </table>
    
  </body>
</html>
