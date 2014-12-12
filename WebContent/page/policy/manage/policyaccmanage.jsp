<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//从查询表接收qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//默认为空
		reqpid = "";    //空业务编号
	}
	//从查询表接收qpname
	String reqpname = request.getParameter("qpname");
	reqpname = new String(reqpname.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqpname == null) {
		//默认为空
		reqpname = "";    //空业务名称
	}
	//接收qmode
	String reqmode= request.getParameter("qmode");		
	if (reqmode == null) {
		//默认为空
		reqmode = "edit";    //空模式
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
    
    <title>业务终审结算管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	
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
		.colTtems {		
			font-size: 12px;
			cursor: pointer;
			color: #6BA6FF;		
		}
		.pageTtems {
			color: #660099;	
			font-size: 12px;	
		}	
		.pointer {
			cursor: pointer;
		}
		.myspace{
			border-top:1px solid #DEEFFF;
			border-bottom:1px solid #DEEFFF;
			border-left:1px solid #DEEFFF;
			border-right:1px solid #DEEFFF;
			font-size:12px
		}
		.mybackground{
			background: url('/db/page/images/titmember.gif');
		}
		.mystyle {		
			font-size: 12px;
			font-weight: bold;	
			color: #666666;		
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
		-->
  </style>
  <SCRIPT LANGUAGE="JavaScript">
	var mynewdt,mynewy,mynewm,mynewd;
	var myDate = new Date();
	    		myDate.getYear();       //获取当前年份(2位)
	    mynewy =myDate.getFullYear();   //获取完整的年份(4位,1970-????)
	    mynewm =myDate.getMonth()+1;      //获取当前月份(0-11,0代表1月)
	    mynewd =myDate.getDate();       //获取当前日(1-31)
	    	    myDate.getDay();        //获取当前星期X(0-6,0代表星期天)
			    myDate.getTime();       //获取当前时间(从1970.1.1开始的毫秒数)
			    myDate.getHours();      //获取当前小时数(0-23)
			    myDate.getMinutes();    //获取当前分钟数(0-59)
			    myDate.getSeconds();    //获取当前秒数(0-59)
			    myDate.getMilliseconds();   //获取当前毫秒数(0-999)
		mynewdt=myDate.toLocaleDateString();    //获取当前日期
			    var mytime=myDate.toLocaleTimeString();    //获取当前时间
			    myDate.toLocaleString( );       //获取日期与时间
  </SCRIPT>
  <script  type="text/javascript">
		//
		var empid = "";        	//当前登录用户编号 
		var deptid = "";        //当前登录机构
		var pid = "";			//当前业务编号
		var pname = "";			//当前业务名称
		var mode = "";			//结算或者新增结算模式
		//
		//		    
		var vleft = 0;  	//背景宽度
		var vtop= 0; 		//背景高度		
		//
		//
		//获取结算列表
	    function GetPolicyAccXmls(){  
	    	var pardiv;
		    var tbegpage,tendpage;
		    var tdeptid,tempid,tpid,tyear,tbegmonth,tendmonth,tbegdt,tenddt;
		    //
		    tbegpage = selbegpage;
		    tendpage = selendpage;
		    //
		    tdeptid = deptid;
		    tempid = empid;
		    tpid = pid;
		    //
		    pardiv = document.getElementById("queryyear");
		    if(null == pardiv){
		    	tyear = "";
		    }else{
		    	tyear = $("#queryyear").val();
		    }
		    pardiv = document.getElementById("querybegmonth");
		    if(null == pardiv){
		    	tbegmonth = "";
		    }else{
		    	tbegmonth = $("#querybegmonth").val();		    	
		    }
		    pardiv = document.getElementById("queryendmonth");
		    if(null == pardiv){
		    	tendmonth = "";
		    }else{
		    	tendmonth = $("#queryendmonth").val();		    	
		    }
		    tbegdt = $("#begdt").val();
		    tenddt = $("#enddt").val();
		    //		    
			DisplayPageStatus(); 
			//
		    //
		    $.post("/db/page/policy/acc/policyaccquery.do",      //服务器页面地址
		        {
		            action: "getPolicyAccXmls", //action参数
		            tbegpage: tbegpage,
		            tendpage: tendpage,  //参数分页
		            tdeptid: tdeptid,
		            tempid: tempid,
		            tpid: tpid,
		            tyear: tyear,
		            tbegmonth: tbegmonth,
		            tendmonth: tendmonth,		           
		            tbegdt: tbegdt,
		            tenddt: tenddt
		        },
		        function(result) {   //回调函数	        	
		        	//加载数据完毕
      				HiddenPageStatus();         	
		  			//		  		
		          	ShowData(result);                          
		    });       
	    }
		//业务终审结算或撤消终审结算
		function SetPolicyAllAccFlag(mode){
			var sms = "";
			if(mode=="1"){
				sms = "终审结算";
			}else{
				sms = "撤消终审结算";
			}
			//确认
		    if (!confirm("是否确定["+sms+"]该业务？")) {
		        return;
		    }
		  	// 
		  	//加载数据    
			//DisplayPageStatus(); 
			//显示操作结果		    		
			DisplayResult("处理数据,请刷新页面或稍候...","1");
		 	//     
			$.post("/db/page/policy/acc/policyaccedit.do",      //服务器页面地址
			    {
			        action: "setPolicyAllAccFlag", //action参数
			        pid: pid,
			        empid: empid,
			        mode: mode                             
			    },
			    function(result) {   //回调函数	
			    	//加载数据完毕
	   				//HiddenPageStatus();  
	   				//    	
			    	//显示操作结果		    		
				    DisplayResult(result,"0");
				    //初始化页面
	     			IniPage();				    	      	                                         
			    }
			);			        
		}
		//
		//删除当前业务终审结算年份和月份
		function DeletePolicyAllCheckAccItems(){
			var tyear,tmonth;
			tyear = document.getElementById("divaccyear").innerHTML;
			tmonth = document.getElementById("divaccmonth").innerHTML;
			//
			if(pid=="" || pid == null){
				alert("当前业务不存在!");
				return;
			}
			//确认
		    if (!confirm("是否确定删除该业务结算周期？")) {
		        return;
		    }
			//加载数据    
			DisplayPageStatus(); 
		 	//
		 	//      
			$.post("/db/page/policy/acc/policyaccedit.do",      //服务器页面地址
			    {
			        action: "deletePolicyAllCheckAccItems", //action参数
			        pid: pid,                            
			        empid: empid,
			        tyear: tyear,
			        tmonth: tmonth
			    },
			    function(result) {   //回调函数
			    	//加载数据完毕
    				HiddenPageStatus();  
    				//			    		    	
			    	//显示操作结果		    		
				    DisplayResult(result,"0"); 
				    //初始化页面
	     			IniPage();       						      	                                         
			    }
			); 
		}
		//
		//新增当前业务终审结算年份和月份
		function SetPolicyAllCheckAccItems(){
			var tyear = "",tmonth = "";
			
		    //
			tyear = $("#newaccyear").val();
			tmonth = $("#newaccmonth").val();
			if(pid=="" || pid == null){
				alert("当前业务不存在!");
				return;
			}
			if(tyear=="" || tyear == null){
				alert("新增结算周期年份不能为空!");
				return;
			}
			if(tmonth=="" || tmonth == null){
				alert("新增结算周期月份不能为空!");
				return;
			}
		  	//
		  	//确认
		    if (!confirm("是否确定生成新业务结算周期？")) {
		        return;
		    } 		  	
		  	//
		  	//加载数据    
			DisplayPageStatus(); 
		 	//      
			$.post("/db/page/policy/acc/policyaccedit.do",      //服务器页面地址
			    {
			        action: "setPolicyAllCheckAccItems", //action参数
			        pid: pid,                            
			        empid: empid,
			        tyear: tyear,
			        tmonth: tmonth
			    },
			    function(result) {   //回调函数
			    	//加载数据完毕
    				HiddenPageStatus();  
    				//			    		    	
			    	//显示操作结果		    		
				    DisplayResult(result,"0"); 
				    //初始化页面
	     			IniPage();       						      	                                         
			    }
			);        
		}	
		//
		//取得年下拉框选择
		function GetSelectYear(pardivid,sname){
		  	//
		  	//加载数据    
			//DisplayPageStatus(); 
		 	//      
			$.post("page/querymanage/QueryManageServlet",      //服务器页面地址
			    {
			        action: "getSelectYear", //action参数
			        sname: sname			                                
			    },
			    function(result) {   //回调函数	
			    	//加载数据完毕
    				//HiddenPageStatus();  
    				//    						    	
			    	if(sname == "newaccyear"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewy);
			    	}else if(sname == "queryyear"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewy);
			    	}		      	                                         
			    }
			);        
		}
		//
		//取得月下拉框选择
		function GetSelectMonth(pardivid,sname){
		  	//
		  	//加载数据    
			//DisplayPageStatus(); 
		 	//      
			$.post("page/querymanage/QueryManageServlet",      //服务器页面地址
			    {
			        action: "getSelectMonth", //action参数
			        sname: sname			                                
			    },
			    function(result) {   //回调函数	
			    	//加载数据完毕
    				//HiddenPageStatus();  
    				//
			    	if(sname == "newaccmonth"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewm);	
			    	}else if(sname == "querybegmonth"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewm);	
			    	}else if(sname == "queryendmonth"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewm);
			    		//取得业务结算返回XML格式		  
						GetPolicyAccXmls();		
			    	}			    			    		          						      	                                         
			    }
			);        
		}		
		//
		//获取当前业务终审结算年份属性
		function GetPolicyAllCheckAccYearItems() {
		 	//			
		    $.post("/db/page/policy/acc/policyaccedit.do",      //服务器页面地址
			    {
			        action: "getPolicyAllCheckAccYearItems", //action参数
			        pid: pid,                            
			        empid: empid
			    },
			    function(result) {   //回调函数	    	    				
	   				//
	   				if(result==""||result==null){    				
	   					$("#accmanagetb").css({"display":"none"});
	   					$("#newaccmanagetb").css({"display":"block"});
	   				}else{
	   					$("#accmanagetb").css({"display":"block"});
	   					$("#newaccmanagetb").css({"display":"none"});
	   					divaccyear.innerHTML=result;
	   				}			    	                          
			    }
			); 
		}
		//
		//获取当前业务终审结算月份属性
		function GetPolicyAllCheckAccMonthItems() {			
		 	//
			$.post("/db/page/policy/acc/policyaccedit.do",      //服务器页面地址
			    {
			        action: "getPolicyAllCheckAccMonthItems", //action参数
			        pid: pid,                            
			        empid: empid
			    },
			    function(result) {   //回调函数 
	   				//
	   				if(result==""||result==null){    				
	   					$("#accmanagetb").css({"display":"none"});
	   					$("#newaccmanagetb").css({"display":"block"});
	   				}else{  
	   					$("#accmanagetb").css({"display":"block"});
	   					$("#newaccmanagetb").css({"display":"none"});  				
	   					divaccmonth.innerHTML=result;
	   				}                      
			    }
			); 
		}
		//获取是否存在当前业务终审结算正在处理的标识
		function GetPolicyAllCheckAccNoOverFlag() {
		 	//			
		    $.post("/db/page/policy/acc/policyaccedit.do",      //服务器页面地址
			    {
			        action: "getPolicyAllCheckAccNoOverFlag", //action参数
			        pid: pid,                            
			        empid: empid
			    },
			    function(result) {   //回调函数			    	    				
	   				//
	   				if(result=="1"){
	   					//显示操作结果		    		
				    	DisplayResult("当前业务终审结算正在处理,请随时刷新页面!","1"); 
				    	$("#btnacc").attr("disabled", "disabled");
						$("#btndelacc").attr("disabled", "disabled");
						$("#btnnewacc").attr("disabled", "disabled");
	   				}else{
	   					$("#btnacc").attr("disabled", "");
						$("#btndelacc").attr("disabled", "");
						$("#btnnewacc").attr("disabled", "");
	   				}			    	                          
			    }
			); 
		}
		//		
		//显示页面状态div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/3;  //背景宽度	    	
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
		function DisplayResult(info,smode) {
	    	vleft = document.body.clientWidth/3;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度 
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //获取提示信息div
		    resultstatusdiv.html(info);                       //设置div内文本		    
		    $(resultstatusdiv).fadeIn();                      //淡入信息
		    if(smode=="0"){
		    	setTimeout("HiddenResult()",2000);  			//2秒后隐藏信息
		    }else{
		    	//一直显示
		    }		    
		}		
		//隐藏提示信息div
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //淡出信息
		}
		//
		//
		//初始化页面
	    function IniPage(){	
	    	//   
			empid = "<%=empno%>"; 	//当前登录用户编号
			deptid = "<%=onno%>";    //当前登录机构 
			//
			vleft = document.body.clientWidth/3;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度 
			//
			pid = "<%=reqpid%>";
			pname = "<%=reqpname%>";
			mode = "<%=reqmode%>";
			//
			//
			selbegpage = 0;		//分页开始
		 	selendpage = sqlpagenum;//分页结束		 	
		 	//
			//
			//获取当前业务终审结算年份属性
			GetPolicyAllCheckAccYearItems();
			//获取当前业务终审结算月份属性
			GetPolicyAllCheckAccMonthItems();
			//取得生成新结算年下拉框选择
			GetSelectYear("divnewaccyear","newaccyear");
			//取得生成新结算年月下拉框选择
			GetSelectMonth("divnewaccmonth","newaccmonth");
			//获取是否存在当前业务终审结算正在处理的标识
		    GetPolicyAllCheckAccNoOverFlag();
			//
			if(mode=="edit"){
				$("#newaccmanagetb").css({"display":"none"});
				$("#accmanagetb").css({"display":"block"});
			}else if(mode=="add"){
				$("#newaccmanagetb").css({"display":"block"});
				$("#accmanagetb").css({"display":"none"});
			}
			//取得查询结算年下拉框选择
			GetSelectYear("divqueryyear","queryyear");
			//取得查询结算开始月下拉框选择
			GetSelectMonth("divquerybegmonth","querybegmonth");	
			//取得查询结算结束月下拉框选择
			GetSelectMonth("divqueryendmonth","queryendmonth");	
	    }		
  </script>		
  </head>
  
  <body onload = "IniPage()">  
  	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
  	
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  	<div id='resultstatusdiv'></div>
  	<fieldset  class='list'  id = "accmanagetb">
		<legend  class='legend'>[<%=reqpname%>]业务结算周期</legend>
		<table align="center" width="60%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center" style="background-color:#ECECEC;">
	    		<td width = "120px" style="color: #000000;text-align:center;font-size:12px;">当前年份:</td>
	    		<td width ="20%" class = "pageTtems" style="font-size:12px;font-weight: bold;"><div id = "divaccyear"></div></td>
	    		<td width = "120px" style="color: #000000;text-align:center;font-size:12px;">当前月份:</td>
	    		<td width = "15%" class = "pageTtems" style="font-size:12px;font-weight: bold;"><div id = "divaccmonth"></div></td>	  		
	    		<td style="color: #000000;text-align:right;font-size:12px;">
	    			<div align="right">
	    				<button id = "btnacc" class = 'btn' onclick="SetPolicyAllAccFlag('1')">业务终审结算</button>
	    				<button id = "btndelacc" class = 'btn' onclick="DeletePolicyAllCheckAccItems()">删除</button>	    			
	    			</div>
	    		</td>
	    	</tr>
	   	</table>	  			
    </fieldset>
   	<fieldset  class='list'  id = "newaccmanagetb">
		<legend  class='legend'>[<%=reqpname%>]生成业务新结算周期</legend>
		<table align="center" width="60%" border="0" cellspacing="0" cellpadding="0">	    	
	    	<tr align="center">
	    		<td width = "120px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">新结算年份:</td>
	    		<td	width = "20%"><div id = "divnewaccyear"></div></td>    		
	    		<td width = "120px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">新结算月份:</td>
	    		<td width = "15%"><div id = "divnewaccmonth"></div></td>    		
	    		<td style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">
	    			<div align="right">
	    				<button id = "btnnewacc" class = 'btn' onclick="SetPolicyAllCheckAccItems()">生成业务新结算周期</button>	    			
		    						    			    			
	    			</div>
	    		</td>
	    	</tr>
	   	</table>	  			
    </fieldset>
   	<fieldset  class='list' id = "accmanagetb">
		<legend  class='legend'>[<%=reqpname%>]业务结算周期查询管理</legend>
		<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">	    	
	    	<tr align="center" valign='middle' height='23' style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">
	    		<td>结算周期 年份:</td>
	    		<td width = "60"><div id = "divqueryyear"></div></td>
	    		<td>月份:</td>
	    		<td width = "40"><div id = "divquerybegmonth"></div></td>
	    		<td>至:</td>
	    		<td width = "40"><div id = "divqueryendmonth"></div></td>
	    		<td width = "100"></td>
	    		<td align="right">结算日期:</td>
	    		<td align="left"><input id='begdt' type='text' size='13' onFocus='setday(this)'/></td>
	    		<td align="right">至:</td>
	    		<td align="left"><input id='enddt' type='text' size='13' onFocus='setday(this)'/></td>
	    		<td><button class = 'btn' onclick="GetPolicyAccXmls()">查询</button></td>
	    	</tr>
	   	</table>
	   	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div class="requestcon" id="requestcon"> </div></td>	
	    	</tr>	    		    	
    	</table>		  			
    </fieldset>
  		
  </body>
</html>
<script type="text/javascript">
	//
	var xmldata;	//XML数据实体
	//
	var colnum = 0,rownum =0;//列数/行数		
	//
	var sqlcount = "0";//总记录数
	var sqlolepagecount = "0";//旧总页数
	var sqlolepageselect = "";//旧页数选择下拉框
	var sqlpagecount = "0";//总页数
	var sqlpagenum = "18";//每页记录数
	//
	var sqlselpage = 1;//选择页
	//
	var selbegpage = 0;		//分页开始
	var selendpage = sqlpagenum;//分页结束
	//
	//
	var sqlbegdt = "";//开始日期
	var sqlenddt = "";//结束日期
	//
		//
	function ShowData(data){
		var xmlDoc;
		//取出表头
		var html= "";		
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//总记录数
			var countarr=root.selectNodes("/data/count/num");
			sqlcount =countarr.item(0).firstChild.data;
			//总页数
			var j1 = sqlcount/sqlpagenum;
			var j2=Math.round(j1);
			if(j1>j2){
				sqlpagecount = j2+1;
			}else if(j1<=j2){
				sqlpagecount = j2;
			}
				
			//列名称
			var headarr=root.selectNodes("/data/ehead/cell");
			//
			colnum = headarr.length+1;
			//
			html += "<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";		
			
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				var temp ="<td height='25'>"+ss[1]+"</td>";
				html=html+temp;
				
			}		
			html=html+"</tr>";
			//
			//取出记录值
			xmldata = root.selectNodes("/data/list/entity");
			//
			//
			var rows=root.selectNodes("/data/list/entity");						
			//
			rownum = rows.length;
			//			
			for(var i=0;i<rows.length;i++){
				var row = rows.item(i).childNodes;									
				//
				var temp="<tr ";						
					//单双行
					var mathrow = i%2;				
					if(mathrow == 1){//背景颜色
						temp +=" style = 'background: #F2F5F7;'>";	
					}else{
						temp +=">";	
					}						
					for (var j=1;j<row.length;j++){
						var temp1="<td height='23' class='colValue'>"+row.item(j).firstChild.data+"</td>";
						temp=temp+temp1;						
					}								
				temp=temp+"</tr>";
				
				//
				html=html+temp
				//				
			}
			//			
			html=html+"</table>";
			//
			//是否有记录
			if(rownum==0){
				html += "<table width='100%' cellpadding='0' cellspacing='0'>"
					html += "<tr>";				
						html += "<td height='25' class='colValue'>无查询结果</td>";
					html += "</tr>";
				html=html+"</table>";
			}
			//
			//
			//	
			html += "<table width='100%' cellpadding='0' cellspacing='0'>"
				html += "<tr class='colField'>";				
				html += "<td height='25'>";
					html += "共["+sqlcount+"]条记录  共["+sqlpagecount+"]页  ";	
					if(sqlselpage==1){
						html += "<span>首页</span> ";
						html += "<span>上一页</span> ";
					}else{
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('1')\">首页</span> ";
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('2')\">上一页</span> ";
					}
					if(sqlselpage==sqlpagecount){	
						html += "<span>下一页</span> ";
						html += "<span>尾页</span> ";
					}else{
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('3')\">下一页</span> ";
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('4')\">尾页</span> ";
					}					
					html += "转到";				
					  	/*
					  	html += "<select id = 'selectpage' onchange =\"ExePage()\">";
						for(var ipage =1;ipage<=sqlpagecount;ipage++){
					  		html += "<option value=\""+ipage+"\">第"+ipage+"页</option>";
					  	}					
						html += "</select>";
						*/
						//选择页下拉选择框
						html += "<span id = \"spanpages\"></span> ";
					html += "  每页<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>条记录</td>";
				html += "</tr>";
			html=html+"</table>";			
			//
			//			
			requestcon.innerHTML=html;
			//
			//
			//分页选择下拉框			
			spanpages.innerHTML=GetPageGo();
			//	
			//选择页
			$("#selectpage").val(sqlselpage);
			//每页显示行数
			$("#divpagerow").val(sqlpagenum);
			//
			//
			//
			//
			//JS表格排序
			new TableSorter("requesttb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/	
			//		
		}
		else{			
			requestcon.innerHTML="无查询结果";
	
		}
	}
	//选项页下来框
	function GetPageSelect(mode){
		var html;
		if(mode=="0"){//就的分页选择下拉框
			sqlolepagecount = sqlpagecount;
			//
			return sqlolepageselect;
		}else{
			sqlolepagecount = sqlpagecount;			
			//
			html = "<select id = 'selectpage' onchange =\"ExePage()\">";
			for(var ipage =1;ipage<=sqlpagecount;ipage++){
		  		html += "<option value=\""+ipage+"\">第"+ipage+"页</option>";
		  	}					
			html += "</select>";
			//
			sqlolepageselect = html;
			//			
		}
		return sqlolepageselect;
	}
	//选项页文本框
	function GetPageGo(){
		var html;
		html = "第<input type='text' id = 'selectpage' size = '3'>页</input>";
		html += "<input type='button' value='Go' onclick=\"ExePage()\"></input>";
		return html;
	}
	//下拉选择页查询
	function ExePage(){
		var stpage = $("#selectpage").val();
		sqlselpage = parseInt(stpage);
		//计算分页数据
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;	
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//取得业务结算返回XML格式		  
		GetPolicyAccXmls();		
	}
	//选择页查询
	function ExePageFlag(pageflag){	
		//
		//
		var flag = "1";//首页标识
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//首页
			if(sqlselpage==1){				
				alert("已经是首页!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//上页
			if(sqlselpage==1){
				alert("已经是首页!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//下页
			if(sqlselpage==sqlpagecount){
				alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//页尾
			if(sqlselpage==sqlpagecount){
				alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlpagecount;			
		}
		
		//计算分页数据
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;		
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//取得业务结算返回XML格式		  
		GetPolicyAccXmls();		
	}
	//更新页显示行数
	function ChangPageRow(src){
		//
		sqlpagenum = src.value;
		//
		selbegpage = 0;		//分页开始
	 	selendpage = sqlpagenum;//分页结束
	 	sqlolepagecount = 0;//旧分页选择下拉框
	 	sqlselpage = 1;//开始第一页
		//
		//取得业务结算返回XML格式		  
		GetPolicyAccXmls();
	}
</script>
