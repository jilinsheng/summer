<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%@page import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//
	//
	//接收qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//默认为空
		reqpid = "";    //空业务编号
	}	
	//接收qcheckid
	String reqcheckid= request.getParameter("qcheckid");		
	if (reqcheckid == null) {
		//默认为空
		reqcheckid = "";    //空审批ID
	}
	//接收qmode
	String reqmode= request.getParameter("qmode");		
	if (reqmode == null) {
		//默认为空
		reqmode = "check";    //空(check审批意见ID,child分类施保审批意见ID)
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>审批意见</title>
    
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
		.myspace{
			border-top:1px solid #DEEFFF;
			border-bottom:1px solid #DEEFFF;
			border-left:1px solid #DEEFFF;
			border-right:1px solid #DEEFFF;
			font-size:12px
		}
		.mybackground{
			background: url('/db/page/images/newscenter.gif');
		}
		.pointer {
			cursor: pointer;
		}
		.tab {
			width: 100%;		
			margin: 0 auto;
			overflow: hidden;				
		}
		.list {
			padding: 4px;
			border: 3px double #f5a89a;
		}		
		.legend {
			font-size: 12px;
			font-weight: lighter;
			color: #000000;
		}
		.btn { 
			BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 
		    #002D96 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; FILTER: 
		    progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
		    StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 
		    1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 0px; PADDING-BOTTOM: 0px;
		    BORDER-BOTTOM: #002D96 1px solid
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
		//
		//
		var empid = "";        	//当前登录用户编号 
		var deptid = "";       	//当前登录机构
		//
		var pid = "";		   	//当前处理业务ID 
		var checkid = "";		//当前处理家庭ID
		var mode = "check";		//check审批意见ID,child分类施保审批意见ID
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		//获取各级审批意见属性
		function GetPolicyMoreCheckIdeaHtml(pardivid,checkid) {
			//家庭ID必须为单个
			var pardiv = document.getElementById(pardivid);
			//加载数据    
			DisplayPageStatus(); 
		 	//
			//			
		    $.post("/db/page/policy/manage/policycheckidea.do",            //服务器页面地址
		        {
		            action: "getPolicyMoreCheckIdeaHtml",             //action参数		           
		            empid: empid,
		            pid: pid,              //参数
		            checkid: checkid
		            
		        },
		        function(result) {                    //回调函数    
		        		//加载数据完毕
      					HiddenPageStatus();  
      					//
      					pardiv.innerHTML = result;			            
		         }   
		    );
		}
		//作废评议意见	
		function RemoveIdea(checkid,ideaid){
			//
			//确认
		    if (!confirm("是否确定作废审批意见？")) {
		        return;
		    }
			//
			$.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
		        {
		            action: "removeIdea", //action参数
		            checkid: checkid, //参数
		            ideaid: ideaid, //参数
		            empid: empid                              
		        },
		        function(result) {   //回调函数
		          	DisplayResult(result,"0");	
		          	//取得各级审批意见
					GetPolicyMoreCheckIdeaHtml("checkideascon",checkid);		       	          	                                         
		        }
		    );
	    }
	    //获取分类施保各级审批意见属性
		function GetPolicyChildCheckIdeaHtml(pardivid,checkid) {
			//家庭ID必须为单个
			var pardiv = document.getElementById(pardivid);
			//加载数据    
			DisplayPageStatus(); 
		 	//
			//			
		    $.post("/db/page/policy/manage/policycheckidea.do",            //服务器页面地址
		        {
		            action: "getPolicyChildCheckIdeasHtml",             //action参数	
		            checkchildid: checkid
		            
		        },
		        function(result) {                    //回调函数    
		        		//加载数据完毕
      					HiddenPageStatus();  
      					//
      					pardiv.innerHTML = result;			            
		         }   
		    );
		}
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
		function DisplayResult(info,outflag) {
	    	vleft = document.body.clientWidth/2-100;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度  	
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //获取提示信息div
		    resultstatusdiv.html(info);                       //设置div内文本		    
		    $(resultstatusdiv).fadeIn();                      //淡入信息
		    if(outflag!="1"){
		    	setTimeout("HiddenResult()",2000);          //2秒后隐藏信息
		    }
		}		
		//隐藏提示信息div
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //淡出信息
		}
		//初始化页面
	    function IniPage(){	
	    	//	    	   
			empid = "<%=empno%>"; 	//当前登录用户编号
			deptid = "<%=onno%>";    //当前登录机构 
			//
			pid = "<%=reqpid%>";	//当前处理业务ID 
			checkid = "<%=reqcheckid%>";	//当前处理家庭ID 
			mode = "<%=reqmode%>";	//check审批意见ID,child分类施保审批意见ID
			//
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			if(mode=="check"){
				//取得各级审批意见
				GetPolicyMoreCheckIdeaHtml("checkideascon",checkid);
			}else if(mode=="child"){
				//取得各级审批意见
				GetPolicyChildCheckIdeaHtml("checkideascon",checkid);
			}
			
		}
	</script>

  </head>
  
  <body onload = "IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div>
    	<table id = "checkideahtmltb" class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div id="checkideascon"> </div></td>	
	    	</tr>		    				    		   			  	    	
	   	</table>
  </body>
</html>
