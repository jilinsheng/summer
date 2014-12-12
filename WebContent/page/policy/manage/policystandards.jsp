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
    
    <title>业务档次标准明细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery.js"></script>
	
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
	//取得当前业务属性
	function GetPolicyItemsHtml(pardivid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	var pid = selpolicy;
	  	//    
		DisplayPageStatus(); 
	 	//
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyItemsHtml", //action参数
		        pid: pid                            
		    },
		    function(result) {   //回调函数
		    	//加载数据完毕
      			HiddenPageStatus(); 
      			//		    	
		    	pardiv.innerHTML = result;	
		    	//取得业务档次标准明细
				GetPolicyStandardsHtml("policystandards");	    		          	 					      	                                         
		    }
		);        
	}
	//取得当前业务档次标准
	function GetPolicyStandardsHtml(pardivid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	var pid = selpolicy;	  	
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyStandardsHtml", //action参数
		        pid: pid                            
		    },
		    function(result) {   //回调函数			    	
      			//		    	
		    	pardiv.innerHTML = result;
		    	//alert(result);		          	 					      	                                         
		    }
		);        
	}
	//取得当前业务档次所有所属机构标准PolicyQueryServlet生成方法
	function GetPolicyStandardDepts(sid){
		//
		GetPolicyStandardDeptsHtml("policystandarddepts",sid);  	        
	}	
	//取得当前业务档次所有所属机构标准
	function GetPolicyStandardDeptsHtml(pardivid,sid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);	
	  	//    
		DisplayPageStatus(); 
	 	//  	
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyStandardDeptsHtml", //action参数
		        sid: sid                            
		    },
		    function(result) {   //回调函数
		    	//加载数据完毕
      			HiddenPageStatus(); 
      			//			    	
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
    	/*	
    	//模式窗体
		var myobj = window.dialogArguments;
	    selpolicy = myobj[0];
	    */
	    selpolicy = "<%=reqpid%>";		 //编号 
	    //
	    //业务名称
		GetPolicyName("policyth");
		//取得当前业务属性
		GetPolicyItemsHtml("policyitems");			        	   
    }
  </script>    
  </head>
  
  <body onload = "IniPage()"> 
  		<table class = "myspace" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<th><div class = "mystyle" id = "policyth"></div></th>
	    	</tr>
	    	<tr align="center">
	    		<td><div id = "policyitems"></div></td>
	    	</tr>
    	</table> 	
    	  		
  		<table class = "myspace" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<th><div class = "mystyle">业务档次</div></th>
	    	</tr>
	    	<tr align="center">
	    		<td><div id = "policystandards"></div></td>
	    	</tr>
    	</table>   	
    	
    	<table class = "myspace" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<th><div class = "mystyle">机构标准</div></th>
	    	</tr> 	    
	    	<tr align="center">
	    		<td><div id = "policystandarddepts"></div></td>
	    	</tr>
    	</table>    		
    	<!-- <div align="center"><input id = "btnclose" type="button" value=" 关 闭 "onclick="window.close();"></input></div> -->
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table> 
  </body>
</html>
