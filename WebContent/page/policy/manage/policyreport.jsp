<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%	
	//
	//
	//从查询表接收qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//默认为空
		reqpid = "";    //空业务编号
	}	
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务统计管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	
	<style type="text/css">
 <!--
  	.myspace{
		border-top:1px solid ;
		border-bottom:1px solid ;
		border-left:1px solid ;
		border-right:1px solid ;
		
		font-size:12px
	}
	.mystyle {		
		font-size: 12px;
		font-weight: bold;	
		color: #666666;
		background: #FCDAD5;
	}
  	.colField {
		font-family: "宋体";
		font-size: 9pt;
		font-weight: 600;
		text-align: center;
		border-top-width: 1px;
		border-right-width: 0px;
		border-bottom-width: 0px;
		border-left-width: 1px;
		border-top-style: solid;
		border-right-style: solid;
		border-bottom-style: solid;
		border-left-style: solid;
		color: #000000;
		background-color: #F5A89A;
	}	
	.colValue {
		font-family: "宋体";
		font-size: 9pt;
		text-align: center;
		border-top-width: 1px;
		border-right-width: 0px;
		border-bottom-width: 0px;
		border-left-width: 1px;
		border-top-style: solid;
		border-right-style: solid;
		border-bottom-style: solid;
		border-left-style: solid;
	}
	.pointer {
		cursor:pointer;
	}
	body {
		margin: 3;
		margin-top: 0;
		padding: 0;
		background: #FCDAD5;
		font-family: "宋体";
		font-size: 12px;
	}
	#pagestatusdiv{		
		z-index:2;
		font-weight: bold;
		color: #FF0099;
		font-size:16px;
		display:none;
	}
	-->
  </style>
  
  <script  type="text/javascript">
	//
	var selpolicy = "";//当前业务编号
	//
	//取得当前业务名称
	function GetPolicyName(pardivid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	var pid = selpolicy;
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyName", //action参数
		        pid: pid                            
		    },
		    function(result) {   //回调函数		    	
		    	pardiv.innerHTML = result;	          						      	                                         
		    }
		);        
	}
	
	
	//显示页面状态div
	function DisplayPageStatus() {
	    var pagestatusdiv = $("#pagestatusdiv");            //获取提示信息div	    	    
	    $(pagestatusdiv).fadeIn();                      //淡入信息		    
	}		
	//隐藏页面状态div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //淡出信息
	}			
	//初始化页面
    function IniPage(){
    	
	    selpolicy = "<%=reqpid%>";		 //编号 
	   		        	   
    }
  </script>    
  </head>
  
  <body onload = "IniPage()"> 
  		业务统计管理
    	<!-- <div align="center"><input id = "btnclose" type="button" value=" 关 闭 "onclick="window.close();"></input></div> -->
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table> 
  </body>
</html>
