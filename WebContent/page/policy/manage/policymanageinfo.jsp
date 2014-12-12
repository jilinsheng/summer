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
	//接收qname
	String reqpname= request.getParameter("qpname");
	reqpname = new String(reqpname.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqpname == null) {
		//默认为空
		reqpname = "";    //空名称
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>政策业务相关信息设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/db/page/js/seqfun.js"></script>
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>	
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
	
	<style type="text/css">	
		body {
			margin: 0;
			padding: 0;
			font-family: "宋体";
			font-size: 12px;
		}		
		.sqltable{
			color: #6BA6FF;
			font-size:12px;
		}
		#infotree{
			border-top:1px solid #43ACB2;
			border-left:1px solid #43ACB2;
			border-bottom:1px solid #43ACB2;
			border-right:1px solid #43ACB2;
			width:100%;
			height:220px; 
			overflow:scroll;
			font-size:12px;
		}
		.titles{
			line-height: 2;			
			color: #000000;
			font-size:12px;
			text-align: center;
			width:100%;
			height:14;
			background: #F5A89A;
		}
		.status0 {	
			text-decoration:line-through;
			color:#999;
		}
		.pointer {
			cursor:pointer;
		}
		.operation {
			font-size:12px;			
			color:#660099;
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
			background: #F5A89A;
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
	</style>
	<script type="text/javascript">	
		//
		var empid = "";        //当前登录用户编号 
		var deptid = "";        //当前登录机构
		
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		var pid = "";        //当前业务ID
		var pname = "";      //当前业务名称
		//
		//取得业务相关信息列表
		function GetPolicyInfosHtml() {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //服务器页面地址
		        {
		            action: "getPolicyInfosHtml",             //action参数
		            pid: pid 
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	policyinfolists.innerHTML = result; 		        		    					      		
		        }
		    );
		}
		//插入业务相关信息列表
		function InsertPolicyInfo(fid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //服务器页面地址
		        {
		            action: "insertPolicyInfo",             //action参数
		            pid: pid,
		            fid: fid 
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();
					DisplayResult(result);              //显示操作结果	
					//取得业务相关信息列表
					GetPolicyInfosHtml();						        		    					      		
		        }
		    );
		}
		//更新业务相关信息列状态
		function UpdatePolicyInfoStatus(sid,tname,fname,status) {
		    //
			var smse = "";
			if(status=="0"){
				smse = "停用";
			}else{
				smse = "启用";
			}
		    //停用前确认
		    if (!confirm("是否确定"+smse+"表["+tname+"]字段["+fname+"]业务相关信息设置？")) {
		        return;
		    }
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //服务器页面地址
		        {
		            action: "updatePolicyInfoStatus",             //action参数
		            sid: sid,
		            status: status
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();
					DisplayResult(result);              //显示操作结果
					//取得业务相关信息列表
					GetPolicyInfosHtml();							        		    					      		
		        }
		    );
		}
		//删除业务相关信息列状态
		function DeletePolicyInfo(sid,tname,fname) {
		    //停用前确认
		    if (!confirm("是否确定删除表["+tname+"]字段["+fname+"]业务相关信息设置？")) {
		        return;
		    }
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //服务器页面地址
		        {
		            action: "deletePolicyInfo",             //action参数
		            sid: sid
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					DisplayResult(result);              //显示操作结果
					//取得业务相关信息列表
					GetPolicyInfosHtml();					        		    					      		
		        }
		    );
		}
		//取得基本家庭信息项
		function GetInfoTree(){
			//创建基本家庭信息项
			var ulr = "page/info/search/TableTreeServlet";
			$("#infotree").empty();                //清空现有列表
			loadrootTree('infotree',ulr,'infotree','root');	
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
			empid = "<%=empno%>";  		//当前登录用户编号 
			deptid = "<%=onno%>";    	//当前登录机构 
			//
			pid = "<%=reqpid%>";        //当前业务ID
			pname = "<%=reqpname%>";    //当前业务名称
			//
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			//基本家庭信息项
			GetInfoTree();	
			//取得业务相关信息列表
			GetPolicyInfosHtml();	
		}		
	</script>
  </head>
  
  <body onLoad="IniPage()">
    	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top">
	    			<fieldset  class='list'>
	    			<legend  class='legend'>相关信息设置</legend>
	    				<span class = 'titles'>家庭信息项</span>    	  
				        <div id="infotree" align="left"></div>				        
	    			</fieldset>	
				</td>
	    		<td valign="top" width="50%">
	    			<fieldset  class='list'>
	    			<legend  class='legend'>[<%=reqpname%>]相关信息列表</legend>	    				 	  
				        <div id="policyinfolists"></div>				        
	    			</fieldset>	
	    		</td>
	    	</tr>	    	
    	</table> 
  </body>
</html>
<script type="text/javascript">
	//=================================选择查询操作BEG==============================
	//选择表节点[TableTreeServlet成生的方法]
	function seltableclick(tid,tname){
		//alert(tid+tname);			
	}		
	//选择字段节点[TableTreeServlet成生的方法]
	function selfieldclick(tid,tname,tfullname,tfieldmode){
		//alert(tid+tname+tfullname+tfieldmode);			
		//添加前确认
	    if (!confirm("是否确定添加["+tfullname+"]业务相关信息设置？")) {
	        return;
	    }
	    //插入业务相关信息列表
		InsertPolicyInfo(tid);		
	}
	//扩子表[TableTreeServlet成生的方法]
	function addchild(tid){
		//alert(tid);
		
	}
	//扩字段[TableTreeServlet成生的方法]
	function addfield(tid){
		//alert(tid);
		
	}	
	//=================================选择查询操作END==============================
</script>
