<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
SysTEmployee employee =(SysTEmployee)session.getAttribute("employee");
String onno = employee.getSysTOrganization().getOrganizationId();
String empno = employee.getEmployeeId().toString();
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%	
	//	
	//接收qid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//默认为空
		reqpid = "";    //空编号
	}
	//接收
	String reqfmid= request.getParameter("qfmid");		
	if (reqfmid == null) {
		//默认为空
		reqfmid = "";   
	}
	//接收
	String reqmemid= request.getParameter("qmemid");		
	if (reqmemid == null) {
		//默认为空
		reqmemid = "";  
	}	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>政策业务相关详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	<style type="text/css">
	 	<!--
		body {
			margin: 0;
			padding: 0;
			font-family: "宋体";
			font-size: 12px;
		}
		.rowFieldInfo {
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
				background: #ECECEC;
			}
		.rowField {
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
			background: #F5A89A;
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
			background: url('/db/page/images/titmember.gif');
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
			cursor: pointer;
		}
		.SortDescCss{
			background-image:url(/db/page/images/tabledesc.gif);
			background-repeat:no-repeat;
			background-position:right center;
		}
		.SortAscCss{
			background-image:url(/db/page/images/tableasc.gif);
			background-repeat:no-repeat;
			background-position:right center;
		}		
		#pagestatusdiv{
			position:absolute;
			z-index:2;
			color: #FF0099;
			font-weight: bold;
			font-size:14px;
			display:none;
		}
		#resultstatusdiv{
			border-top: 1px solid buttonhighlight; 
			border-left: 1px solid buttonhighlight;  
			border-bottom: 1px solid buttonshadow;  
			border-right: 1px solid buttonshadow; 
			position:absolute;
			z-index:2;
			font-weight: bold;
			color: #FF0099;
			font-size:14px;
			text-align:center;
			background-color: #FFCCCC;
			display:none;
		}
		-->
	</style>		
	<script type="text/javascript">
		var pid = "";				//当前处理业务ID 
		var fmid = "";				//当前处理家庭ID 
		var memid = "";				//当前处理成员ID
		
		
		//显示页面状态div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/2-50;  //背景宽度
			vtop= document.body.clientHeight/2-20; //背景高度   	
	    	//
	    	$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
		    var pagestatusdiv = $("#pagestatusdiv");            //获取提示信息div		   		    
		    $(pagestatusdiv).fadeIn();                      //淡入信息		    
		}		
		//隐藏页面状态div
		function HiddenPageStatus() {
		    $("#pagestatusdiv").fadeOut();                  //淡出信息
		}
		//显示提示信息div
		function DisplayResult(info) {
	    	vleft = document.body.clientWidth/2-100;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度  	
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //获取提示信息div
		    resultstatusdiv.html(info);                       //设置div内文本		    
		    $(resultstatusdiv).fadeIn();                      //淡入信息
		    setTimeout("HiddenResult()",2000);  			//2秒后隐藏信息
		}		
		//隐藏提示信息div
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //淡出信息
		}
		//
		//初始化页面
	    function IniPage(){	
	    	//	    	   
			empid = "<%=empno%>"; 	//当前登录用户编号
			deptid = "<%=onno%>";    //当前登录机构 
			//
			pid = "<%=reqpid%>";				//当前处理业务ID 
			fmid = "<%=reqfmid%>";				//当前处理家庭ID 
			memid = "<%=reqmemid%>";			//当前处理成员ID 
			//			
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			//			
			//获取家庭政策业务相关详细信息
			GetCheckPolicyInfosHtml(fmid,memid);
			
		}
	</script>
  </head>
  
  <body onload = "IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div>
    	<div id='policyinfodiv'></div>
  </body>
</html>
<script type="text/javascript">
	//获取家庭政策业务相关详细信息
	function GetCheckPolicyInfosHtml(fmid,memid){
		//		
		var pardiv = document.getElementById("policyinfodiv");
		//显示页面状态div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //服务器页面地址
	        {
	            action: "getCheckPolicyInfosHtml",             //action参数
	            pid: pid, 
	            fmid: fmid,
	            memid: memid
	        },
	        function(result) {                    	//回调函数
	        	//隐藏页面状态div
				HiddenPageStatus();	
				//
				pardiv.innerHTML = result;				 		
	        }
	    );
	}
</script>