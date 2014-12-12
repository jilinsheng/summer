<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
	String thno = employee.getSysTOrganization().getDepth().toString();
%>
<%
	
	String siontabid =(String) session.getAttribute("siontabid");
	if (null == siontabid) {
		siontabid = "0";	//默认为全部查询
	}
%>
<%
	//业务选择
	String reqpid = request.getParameter("qpolicy");
	//接收业务名称URLDecoder
	//从查询表接收查询字段select
	String reqselect = request.getParameter("qselect");
	//从查询表接收查询表from
	String reqfrom = request.getParameter("qfrom");	
	//从查询表接收查询条件where
	String reqwhere= request.getParameter("qwhere");	
	//从查询表接收查询排序order
	String reqorder= request.getParameter("qorder");
	//
	if (reqpid == null) {
		//默认为空
		reqpid = "-1";    //选择业务[不选]
	}
	reqselect = new String(reqselect.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqselect == null) {
		//默认为空
		reqselect = "";    //字段
	}
	reqfrom = new String(reqfrom.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqfrom == null) {
		//默认为空
		reqfrom = "";    //空表
	}
	reqwhere = new String(reqwhere.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqwhere == null) {
		//默认为空
		reqwhere = "";    //空条件
	}
	reqorder = new String(reqorder.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqorder == null) {
		//默认为空
		reqorder = "";    //空排序
	}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务审批查询管理</title>
    
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
		body {
			margin: 0;
			padding: 0;
			font-family: "宋体";
			font-size: 12px;
		} 
		.pointer {
			cursor: pointer;
			color: #6BA6FF;
		}
		.tab {
			width: 100%;		
			margin: 0 auto;
			overflow: hidden;				
		}
		.menu, .menu li {
			margin: 0;
			padding: 0;
			height: 23px;
			list-style: none;
			overflow: hidden;
			text-align: center;					
		}
		.menu {
			border-bottom: 1px solid #DEEFFF;		
		}
		.menu .default {
			width: 86px;
			float: left;
			font-size: 13px;
			line-height: 1.8;
			margin-left: 0px;
			cursor: pointer;		
			background: url('/db/page/images/checkdefault.jpg') no-repeat;
		}
		.menu .active {
			width: 86px;
			float: left;
			font-size: 13px;
			line-height: 1.8;
			margin-left: 0px;
			cursor: pointer;		
			color: #FFFFFF;
			background: url('/db/page/images/checkactive.jpg') no-repeat;
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
		.legend {
			font-size: 12px;
			font-weight: lighter;
			color: #000000;
			text-align: center;
		}
		.btn { 
			BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 
		    #002D96 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; FILTER: 
		    progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
		    StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 
		    1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 0px; PADDING-BOTTOM: 0px;
		    BORDER-BOTTOM: #002D96 1px solid
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
	<style type="text/css">
		.myspace{
			border-top:1px solid #DEEFFF;
			border-bottom:1px solid #DEEFFF;
			border-left:1px solid #DEEFFF;
			border-right:1px solid #DEEFFF;
			font-size:13px
		}
		.mybackground{
			color: #666666;
			background-color: #DEEFFF;
			font-size:12px;
		}
	</style>
  </head>
  <script  type="text/javascript">
	//
	var empid = "",deptid = "",depth = "";
	//
	var selpid = "";
	var selselect = "";    	//选择查询结果字段
	var selfrom = "";    	//选择查询结果表
	var selwhere = "";    	//选择查询结果条件
	var selorder = "";    	//选择查询结果排序
	//
	var seltabquery = "0";//0全部;1新增;2调高;3顺延;4降低;7取消;
	var seltabidea = "0";//0全部;1未审批;2同意救助;3重新审批;8取消救助;9不同意救助	
	var seltabbakidea = "0";//0全部;1未审批;2同意救助;3重新审批;8取消救助;9不同意救助	
	//
	var seltabid = "0";
	var seltabname = "全部名单";
	//
	var sqlcount = 0;//总记录数
	var sqlpagecount = 0;//总页数
	var sqlpagenum = 12;//每页记录数
	//
	var sqlselpage = 1;//选择页
	//
	var selbegpage = 0;		//分页开始
	var selendpage = sqlpagenum;//分页结束
	
	//
	var selsql = "";	//选择SQL分页语句	
	var selallsql = "";	//选择SQL语句
	var selsqlth = "";	//选择SQL语句列名称
	//	
	var sumpopcount =0;
	var summoney=0;
	var sumolemoney=0;
	var sumnewmoney=0;
	//
	var accmode = 0;	//用户可以修改救助金额标识
	//
	var accflag =0;
	//
	var onecheck =0;
	var endcheck=0;
	var usercheck=0;
	var morecheck=0;
	var usermorecheck =0;
	var report=0;
	//
	var accdesc = "";
	//
	//更新救助 名单(救助金)
	function updateListMonery(fmid){
		//
		spanstatus.innerHTML = "更新该业务救助名单,处理救助金!";
		//加载数据		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/relief/relieflist.do",            //服务器页面地址
	        {
	            action: "updateListMonery",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jfmid: fmid
	        },
	        function(result) {                    	//回调函数	        	
	        	//加载数据完毕
     			HiddenPageStatus();     			
     			//      	
	        	//查询XML
				getCheckResultForXml("table","page");
	        }
	    );
	}
	//获取保障类型下拉选择框
	function GetCheckPolicySalTypeSelect(pardivid,sname){
		//
	  	var pardiv = document.getElementById(pardivid);
		//
	    $.post("/db/page/policy/approval/policycheckpage.do",            //服务器页面地址
	        {
	            action: "GetCheckPolicySalTypeSelect",             //action参数
	            sname: sname
	        },
	        function(result) {                    	//回调函数
	        	pardiv.innerHTML = result;
	        	//单击事件		      	
				$("#"+sname).change(
					function(){
						//获取查询XML
	  					getCheckResultForXml("table","page");
					}
				);
	        }
	    );
	}		
	//获取排序下拉选择框
	function GetCheckPolicyOrderSelect(pardivid,sname){
		//
	  	var pardiv = document.getElementById(pardivid);
		//
	    $.post("/db/page/policy/approval/policycheckpage.do",            //服务器页面地址
	        {
	            action: "GetCheckPolicyOrderSelect",             //action参数
	            sname: sname,
	            pid: selpid
	        },
	        function(result) {                    	//回调函数
	        	pardiv.innerHTML = result;
	        	//单击事件		      	
				$("#"+sname).change(
					function(){
						//获取查询XML
	  					getCheckResultForXml("table","page");
					}
				);
	        }
	    );
	}
	//查询条件
	function QueryBtnOK(){
		//
	  	//获取查询XML
	  	getCheckResultForXml("table","page");
	}		
	//获取查询XML
	function getCheckResultForXml(mode,exemode){
		//
		var jmode="table",jselect="",jfrom="",jwhere="",jbegpage="0",jendpage="0",jorder="";
		//
		var jsqlmode = mode;				//sql方式(sql获取SQL语句table获取查询表格)
		var jsaltype = "0";					//保障类型
		//		
		//     
	    jselect = "";	//业务查询时候不指定查询字段  
	    jfrom = selfrom;
	    jwhere = selwhere;
	    
		jbegpage = selbegpage;
		jendpage = selendpage;
		//过滤查询==============================================		
		var oq = document.getElementById("squery");	//救助状态
		if(oq!=null){
			seltabquery = $("#squery").val();
			if(seltabquery == null){
				seltabquery = "0";
			}			
		}else{
			seltabquery = "0";
		}
	  	var obaki = document.getElementById("sbakidea");	//审批结果
		if(obaki!=null){
			seltabbakidea = $("#sbakidea").val();
			if(seltabbakidea == null){
				seltabbakidea = "0";
			}
		}else{
			seltabbakidea = "0";
		}
		var oi = document.getElementById("sidea");	//审批结果
		if(oi!=null){
			seltabidea = $("#sidea").val();
			if(seltabidea == null){
				seltabidea = "0";
			}
		}else{
			seltabidea = "0";
		}
		//
		//过滤查询==============================================
		//保障类型==============================================
		//
		var ot = document.getElementById("saltypelist");
		if(ot!=null){	
    		var soname = $("#saltypelist").val();		    	
	    	if(soname == null){
				soname = "0";
			}
	    	jsaltype = soname; 
    	}else{
    		jsaltype = "0";
    	}
      	//保障类型==============================================
		//排序查询==============================================
		//
		var ol = document.getElementById("orderbylist");
		
		if(rdgup.checked == true){
			if(ol!=null){	
	    		var soname = $("#orderbylist").val();		    	
		    	jorder = soname + " asc"; 
	    	}else{
	    		jorder = selorder;
	    	}
      	}else if(rdgdown.checked == true){
      		if(ol!=null){	
	      		var soname = $("#orderbylist").val();		    	
		    	jorder = soname + " desc";  
		    }else{
	    		jorder = selorder;
	    	}
      	}
      	//排序查询==============================================
      	//==================================================转码
		jselect = encodeURI(jselect);
		jfrom = encodeURI(jfrom);
		jwhere = encodeURI(jwhere);
		//==================================================转码
		//
		$("#menutabs").attr("disabled", "disabled");
		//加载数据		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/approval/policycheckpage.do", //服务器页面地址
	        {
	            action: "getCheckResultForXml",             //action参数
	            jmode: jmode,
	            jselect: jselect,
	            jfrom: jfrom,
	            jwhere: jwhere,
	            jbegpage: jbegpage,
	            jendpage: jendpage,
	            jorder: jorder,
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jtabid: seltabid,
	            jtabquery: seltabquery,
	            jtabidea: seltabidea,
	            jtabbakidea: seltabbakidea,
	            jsaltype: jsaltype,
	            jsqlmode: jsqlmode,
	            jexemode: exemode
	        },
	        function(result) {                    	//回调函数
	        	//加载数据完毕
     			HiddenPageStatus();
     			ShowData(mode,result,exemode);    
     			$("#menutabs").attr("disabled", ""); 			
	        }
	    );
	}
	//显示页面状态div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2+80;  //背景宽度
		//vtop= document.body.clientHeight/2; //背景高度
		vtop= $("#menutabs").offset().top;      	
    	//
    	$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
	    var pagestatusdiv = $("#pagestatusdiv");        //获取提示信息div		   		    
	    $(pagestatusdiv).fadeIn();                      //淡入信息		    
	}		
	//隐藏页面状态div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //淡出信息
	    spanstatus.innerHTML = "查询数据请稍等...";
	}
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		//选择业务查询	
		selpid = "<%=reqpid%>";		 	 //业务选择			
		//
		selselect = "<%=reqselect%>";	 //查询选择字段
		selfrom = "<%=reqfrom%>";		 //查询选择表
		selwhere = "<%=reqwhere%>";		 //查询选择条件
		selorder = "<%=reqorder%>";		 //查询选择排序	
		//
		//获取保障类型下拉选择框
		GetCheckPolicySalTypeSelect("selectsaltype","saltypelist");
		//获取排序下拉选择框
		GetCheckPolicyOrderSelect("spanorderby","orderbylist");
		
		//查询标签
		seltabid  = "<%=siontabid%>";	 //查询标签ID
		//标签栏
		init("menus");		
		//查询栏
		QueryPage(seltabid);
		//更新救助 名单(救助金)
		updateListMonery("all");					
	}
  </script>
  <body onload = "IniPage()">
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img><span id = 'spanstatus'>查询数据请稍等...</span></div> 
    <table width='100%' cellpadding='0' cellspacing='0' class='tab'>
	   	<tr>
	   		<td class = "myspace" colspan = "3">
	   			<div id = "menutabs">
	   				<ul id="menus" class="menu">
	   					<li id="li0" class="default"> 全部名单 </li>	   					
				    	<li id="li1" class="default"> 新增审批 </li>
				    	<li id='li2' class='default'> 调整复查 </li>
				    	<li id='li3' class='default'> 重新审批 </li>
				    	<li id='li4' class='default'> 已审名单 </li>				    	
				    </ul>
	   			</div>
	   		</td>
	   	</tr>
	   	<tr>
	   		<td class = "myspace" valign="top">
	   			<div id='checkresultcon'>
	   				<fieldset><legend><span id = 'spanaccdesc'></span></legend>
				  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>
				  			<tr valign='middle'>				  				
				  				<td align='center' valign='middle' class='myspace'>
				  					<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>				  									  						
							  			<tr valign='middle'>								  										  							  				
							  				<td id = 'tdq' width = "120" align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span id = 'selectquery'></span>						  												  					
							  				</td>
							  				<td id = 'tdbaki' width = "160" align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span id = 'selectbakidea'></span>							  												  					
							  				</td>
							  				<td id = 'tdi' width = "120" align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span id = 'selectidea'></span>							  												  					
							  				</td>
							  				<td id = 'tds' width = "160" align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span style = "color: #6BA6FF;">保障类型</span>
							  					<span id = 'selectsaltype'></span>							  												  					
							  				</td>
							  				<td align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span style = "color: #6BA6FF;">排序</span>
							 					<span id = "spanorderby"></span> 					
							 					<input type='radio' name='rdg' id='rdgup' checked='checked' onclick='getCheckResultForXml("table","page");'></input><span  style = "color: #6BA6FF;">升序</span>
												<input type='radio' name='rdg' id='rdgdown' onclick='getCheckResultForXml("table","page");'></input><span  style = "color: #6BA6FF;">降序</span>
							  				</td>							  				
							  			</tr>							  			
				  					</table>
				  				</td>
				  				<td width = "80" align='center' class='myspace'>
				  					<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'> 
							  			<tr valign='middle'>
							  				<td align='right' valign='middle' class='myspace'>
							  					<button class = 'btn' onclick="QueryBtnOK()"> 查  询 </button>	
							  					<button class = 'btn' onclick='getCheckResultForXml("sql","export");'> 导  出 </button>
							  				</td>			  				
							  			</tr>
				  					</table>
				  				</td>
				  			</tr>
				  			
				  		</table>
				  	</fieldset>
				  	<div align='center' id = 'familytb'></div>		  	
	   			</div>	
	   			  			
	   		</td>
	   	</tr>
	   			   	  	
	</table>
	<div id = "infocounttb"></div>
	<div id = "paginationtb"></div> 
	<div id = "ideainfotb">
	    <fieldset><legend>审批操作</legend>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>
	  			<tr valign='middle'>	  						
					<td align='left' valign='middle' class='mybackground myspace'>	  					
	 					<input type="checkbox" id="chkPageAll" title="本页[全选/取消]" onclick="CheckPage()"></input>
	 					<span  style = "color: #6BA6FF;">本页[全选/取消]</span>						  					
					</td>				
					<td width = "480" align='right' valign='middle' class='mybackground myspace'>
						<button class = 'btn' id = 'btnpagemoney' onclick="CallMoreIdea()">审批选中</button>
	  					<button class = 'btn' id = 'btnmoney' onclick='getCheckResultForXml("sql","all");'>审批全部</button>
						<button style = 'display:none;' class = 'btn' id = 'btnallpagemoney' onclick="CallMoneyIdea()">按定额审批选中</button>
						<button style = 'display:none;' class = 'btn' id = 'btnallmoney' onclick='getCheckResultForXml("sql","allmoney");'>按定额审批全部</button>
					</td> 				
	  			</tr>
	  			
	  		</table>
	  	</fieldset>
  	</div>
  </body>
</html>
<script  type="text/javascript">
	//
	function init(ids){
		//document.getElementById(ids).getElementsByTagName('li')[0].className='active';
		document.getElementById('li'+seltabid).className='active';			
		document.getElementById(ids).onclick=function(){onmousOver(ids);}//点击激发效果					
	}
	function onmousOver(ids){		
		o = o || window.event;
		var obj=o.target || o.srcElement;
		if (obj.tagName=='LI'){
			if (obj.className=='active')return;
			var o=document.getElementById(ids).getElementsByTagName('li');
			for (var i=0;i<=o.length-1;i++){o[i].className='default'}
			obj.className='active';
			//
			//
			var tid,tname;
			tid =  obj.id;
			tname = obj.firstChild.nodeValue;
			tid = tid.substring(2,tid.length);	
			//
			seltabid = tid;
		  	seltabname = tname;	
		  	//
		  	seltabquery = "0";//0全部;1新增;2调高;3顺延;4降低;7取消;
			seltabidea = "0";//0全部;1未审批;2同意救助;3重新审批;8取消救助;9不同意救助	
			seltabbakidea = "0";//0全部;1未审批;2同意救助;3重新审批;8取消救助;9不同意救助	
		  	//查询栏
		  	QueryPage(seltabid);
		  	//
			selbegpage = 0;		//分页开始
		 	selendpage = sqlpagenum;//分页结束
		 	sqlselpage = 1;//开始第一页
		  	//查询XML
		  	getCheckResultForXml("table","page");		  	
		}
	}	
	//查询栏
	function QueryPage(tab){	
		//
		var qall = "<option value='0'>全部</option>";
		var qnew = "<option value='1'>新增</option>";		
		var qold = "<option value='2'>顺延</option>";
		var qup = "<option value='3'>调高</option>";
		var qdown = "<option value='4'>降低</option>";
		var qdel = "<option value='7'>不符合条件</option>";
		//
		var iall = "<option value='0'>全部</option>";
		var inew = "<option value='1'>未评议</option>";
		var iok = "<option value='2'>同意救助</option>";
		var ire = "<option value='3'>重新审批</option>";
		var idel = "<option value='8'>取消救助</option>";
		var inot = "<option value='9'>不同意救助</option>";		
			
		//
		var sqhtml = "<select id='squery' style = \"font-size:12px;width = '84';\" onchange=\"getCheckResultForXml('table','page');\">";
		var sihtml = "<select id='sidea' style = \"font-size:12px;width = '84';\" onchange=\"getCheckResultForXml('table','page');\">";
		var sbakihtml = "<select id='sbakidea' style = \"font-size:12px;width = '84';\" onchange=\"getCheckResultForXml('table','page');\">";
		//
		if(tab == "0"){
			$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"block"});
	  		sqhtml += qall + qnew + qold + qup + qdown + qdel;
	  		sihtml += iall + inew + iok + ire + idel;
	  		$("#tdbaki").css({"display":"block"});
	  		sbakihtml += iall + inew + iok + ire + idel;					
	  	}else if(tab == "1"){
	  		$("#tdq").css({"display":"none"});
			$("#tdi").css({"display":"none"});
			
			$("#tdbaki").css({"display":"none"});
	  	}else if(tab == "2"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"block"});	  	
			sqhtml += qall + qold + qup + qdown + qdel;
			sihtml += iall + inew + iok + ire + idel;
			$("#tdbaki").css({"display":"block"});
			sbakihtml += iall + inew + iok + ire + idel;			
	  	}else if(tab == "3"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"none"});	  		
	  		sqhtml += qall + qnew + qold + qup + qdown + qdel;
	  		$("#tdbaki").css({"display":"none"});
	  	}else if(tab == "4"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"block"});	  		
	  		sqhtml += qall + qnew + qold + qup + qdown + qdel;
	  		sihtml += iall + iok + ire + idel;
	  		$("#tdbaki").css({"display":"block"});
	  		sbakihtml += iall + iok + ire + idel;
	  	}
	  	sqhtml += "</select>";
	  	selectquery.innerHTML = "救助"+sqhtml;
	  	//
	  	sbakihtml += "</select>";
		selectbakidea.innerHTML = "<span id = 'sitembakidea'></span>"+sbakihtml; 
		//
		sihtml += "</select>";		
		selectidea.innerHTML = "审批"+sihtml;
	}
	//批量审批操作栏
	function CheckIdeaTab(){
		//无查询记录
		if(sqlcount==0){
			$("#ideainfotb").css({"display":"none"});
		}else{
			$("#ideainfotb").css({"display":"block"});
		}
		var smorecheck = "0";		
		if(usercheck == 1){
			smorecheck = "1";
			if(morecheck == 1){
				smorecheck = "2";
				if(usermorecheck != 1){
					smorecheck = "1";
				}
			}
		}
		//批量审批栏
		if(smorecheck != "2"){
			$("#ideainfotb").css({"display":"none"});
		}
		//已审批名单或者重新审批名单或者全部名单
		if(seltabid=="0" || seltabid=="3" || seltabid=="4"){
			$("#ideainfotb").css({"display":"none"});
		}
		//结算批次标识
		if(accflag != "0"){	//0未结算1正结算2完成结算标识
			$("#ideainfotb").css({"display":"none"});
		}
		//
		sitembakidea.innerHTML = "下级审批";
		if(depth == "4"){
			sitembakidea.innerHTML = "村审批";
		}else if(depth == "3"){
			sitembakidea.innerHTML = "乡镇审批";
		}else if(depth == "2"){
			sitembakidea.innerHTML = "区县审批";
		}else if(depth == "1"){
			sitembakidea.innerHTML = "市审批";
		}
		//
		if(onecheck == "1"){//第一级审批
			$("#tdbaki").css({"display":"none"});
			if(accmode == "0"){	//用户不能修改救助金
				$("#btnallpagemoney").css({"display":"none"});
				$("#btnallmoney").css({"display":"none"});	
			}
		}else{
			$("#btnallpagemoney").css({"display":"none"});
			$("#btnallmoney").css({"display":"none"});	
		}
		//
		spanaccdesc.innerHTML = accdesc;	
	}
	//财务数据转换
	function ReplaceMoney(data){
		var s = data.toString();
        if(/[^0-9\.]/.test(s)) return "0.00";
        s=s.replace(/^(\d*)$/,"$1.");
        s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
        s=s.replace(".",",");
        var re=/(\d)(\d{3},)/;
        while(re.test(s))
                s=s.replace(re,"$1,$2");
        s=s.replace(/,(\d\d)$/,".$1");
        //return "￥" + s.replace(/^\./,"0.");
        return s.replace(/^\./,"0.");       
    }
	
	//信息汇总栏
	function InfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='myspace' style = 'border-top-width: 1px;' >";				
				html += "<td height='23' align='center' >";
	  				html += "<span style = 'color: #6BA6FF;'>总家庭户数["+sqlcount+"]</span>";		  				
	  			html += "</td>";
				html += "<td height='23' align='center' >";
	  				html += "<span style = 'color: #6BA6FF;'>总保障人口["+sumpopcount+"]</span>";		  				
	  			html += "</td>";
	  			html += "<td height='23' align='center' >";
	  				html += "<span style = 'color: #6BA6FF;'>总计算收入["+ReplaceMoney(summoney)+"]</span>";
	  			html += "</td>";
	  			html += "<td height='23' align='center' >";
		   			html += "<span style = 'color: #6BA6FF;'>总上期救助金["+ReplaceMoney(sumolemoney)+"]</span>";
	  			html += "</td>";
	  			html += "<td height='23' align='center' >";
		   			html += "<span style = 'color: #6BA6FF;'>总拟发救助金["+ReplaceMoney(sumnewmoney)+"]</span>";
	  			html += "</td>";
			html += "</tr>";
		html=html+"</table>";			
		//
		//			
		infocounttb.innerHTML=html;
	}		
	//分页栏
	function Pagination(){	
		//
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";				
			html += "<td height='25'>";
				html += "共["+sqlcount+"]条记录  共["+sqlpagecount+"]页  ";	
				if(sqlselpage<=1){
					html += "<span title = '首页'>首页</span> ";
					html += "<span title = '上一页'>上一页</span> ";
				}else{
					html += "<span title = '首页' class = 'pointer' onclick=\"ExePage('beg')\">首页</span> ";
					html += "<span title = '上一页' class = 'pointer' onclick=\"ExePage('back')\">上一页</span> ";
				}
				if(sqlselpage==sqlpagecount || sqlpagecount <= 1){	
					html += "<span title = '下一页'>下一页</span> ";
					html += "<span title = '尾页'>尾页</span> ";
				}else{
					html += "<span title = '下一页' class = 'pointer' onclick=\"ExePage('next')\">下一页</span> ";
					html += "<span title = '尾页' class = 'pointer' onclick=\"ExePage('end')\">尾页</span> ";
				}
				html += "转到 第<input type='text' id = 'selectpage' size = '3' value = '"+sqlselpage+"'" + ">页</input> ";	
				html += "<input type='button' value='Go' onclick=\"ExePage('go')\"></input> ";	
				html += "<span id = \"spanpages\"></span> ";				
				html += "  每页<input type=\"text\" size = \"3\" id = \"divpagerow\" value = '"+sqlpagenum+"'" + " onblur = \"ChangPageRow(this)\"></input>条记录";
				html += "</td>";
			html += "</tr>";
		html=html+"</table>";			
		//
		//			
		paginationtb.innerHTML=html;
	}
	//下拉选择页查询
	function ExePage(tab){
		if (tab == "go"){
			var stpage = $("#selectpage").val();
			sqlselpage = parseInt(stpage);
			$("#selectpage").val(sqlselpage);
		}else if (tab == "beg"){
			if(sqlselpage==1){				
				return;
			}
			sqlselpage = 1;	
			$("#selectpage").val(sqlselpage);
		}else if (tab == "back"){
			if(sqlselpage==1){				
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
			$("#selectpage").val(sqlselpage);
		}else if (tab == "next"){
			if(sqlselpage==sqlpagecount){				
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
			$("#selectpage").val(sqlselpage);
		}else if (tab == "end"){
			if(sqlselpage==sqlpagecount){				
				return;
			}
			sqlselpage = sqlpagecount;	
			$("#selectpage").val(sqlselpage);
		} 
		//计算分页数据
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;	
		//alert("b: "+ selbegpage + " e: " + selendpage);	
		//查询XML
		getCheckResultForXml("table","page");
	}
	//更新页显示行数
	function ChangPageRow(src){
		//
		sqlpagenum = src.value;
		//
		selbegpage = 0;		//分页开始
	 	selendpage = sqlpagenum;//分页结束
	 	sqlselpage = 1;//开始第一页
		//
		//查询XML
		getCheckResultForXml("table","page");
	}	
</script>
<script type="text/javascript">
	//页全部选中/取消
	function CheckPage()
	{  
		var chk = chkPageAll.checked;
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="cbx[]") {
				document.all[i].checked=chk;
				chkRow(document.all[i]);
			}
		}
	}
	//复选后单元格变色
	function chkRow(obj){
		var r = obj.parentElement.parentElement;
		if(obj.checked){ 
			r.style.backgroundColor="#E6E9F2";
		}else {
			if(r.rowIndex%2==1){
				r.style.backgroundColor="";
			}else{
				r.style.backgroundColor="#F5F5F5";
			}
		}
	}
	//表格点击复选后单元格变色
	function chkTbRow(obj){
		var r = obj.parentElement.parentElement;
		if(obj.checked){ 
			r.style.backgroundColor="#E6E9F2";
		}else {
			if(r.rowIndex%2==1){
				r.style.backgroundColor="";
			}else{
				r.style.backgroundColor="#F5F5F5";
			}
		}
		//全选取消
		chkPageAll.checked = false;
	}
</script>
<script type="text/javascript">
	//
	//
	function ShowData(mode,data,exemode){
		var xmlDoc;	
		var xtable;
		//初始化
		selsql="";
		selallsql="";
		selsqlth="";		
		//
		accflag = 0;
		//
		onecheck =0;
		endcheck=0;
		usercheck=0;
		morecheck=0;
		usermorecheck =0;
		report=0; 
		//
		accdesc = "";	
		//
		accmode = 0;
		//		
		sqlcount=0;
		sumpopcount =0;
		summoney=0;
		sumolemoney=0;
		sumnewmoney=0;	
		//全选取消
		chkPageAll.checked = false;	
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//总户数
			var countarr=root.selectNodes("/data/counts/sqlcount");
			sqlcount =countarr.item(0).firstChild.data;
			//总页数
			var j1 = sqlcount/sqlpagenum;
			var j2=Math.round(j1);
			if(j1>j2){
				sqlpagecount = j2+1;
			}else if(j1<=j2){
				sqlpagecount = j2;
			}
			//保障人口汇总		
			countarr=root.selectNodes("/data/counts/sumpopcount");
			sumpopcount =countarr.item(0).firstChild.data;			 
			//计算收入汇总
			countarr=root.selectNodes("/data/counts/summoney");
			summoney =countarr.item(0).firstChild.data;	
			//上期救助金汇总			
			countarr=root.selectNodes("/data/counts/sumolemoney");
			sumolemoney =countarr.item(0).firstChild.data;			
			//拟发救助金汇总
			countarr=root.selectNodes("/data/counts/sumnewmoney");
			sumnewmoney =countarr.item(0).firstChild.data;
			//==============需要计算救助金标识====================
			var accmodearr=root.selectNodes("/data/accmode/accmode");
			accmode =accmodearr.item(0).firstChild.data;
			//==============需要计算救助金标识====================
			//==============完成结算标识=========================			
			var accflagarr=root.selectNodes("/data/accflag/accflag");
			accflag =accflagarr.item(0).firstChild.data;	
			//
			var accdescarr=root.selectNodes("/data/accdesc/accdesc");
			accdesc =accdescarr.item(0).firstChild.data;
			//===================审批标识标识====================
			//第一级审批标识		
			var flagarr=root.selectNodes("/data/flags/onecheck");
			onecheck =flagarr.item(0).firstChild.data;			 
			//终审标识
			flagarr=root.selectNodes("/data/flags/endcheck");
			endcheck =flagarr.item(0).firstChild.data;	
			//用户审批权限标识			
			flagarr=root.selectNodes("/data/flags/usercheck");
			usercheck =flagarr.item(0).firstChild.data;			
			//业务批量审批标识
			flagarr=root.selectNodes("/data/flags/morecheck");
			morecheck =flagarr.item(0).firstChild.data;	
			//用户批量审批标识
			flagarr=root.selectNodes("/data/flags/usermorecheck");
			usermorecheck =flagarr.item(0).firstChild.data;
			//用户审批上报名单标识
			flagarr=root.selectNodes("/data/flags/report");
			report =flagarr.item(0).firstChild.data;
			//===================审批标识标识====================
			//=================查询SQL语句模式====================
			if(mode == "sql"){	//返回SQL语句     			
     			//查询SQL分页语句		
				var sqlarr=root.selectNodes("/data/sql/sql");
				selsql =sqlarr.item(0).firstChild.data;			 
				//查询SQL语句
				sqlarr=root.selectNodes("/data/sql/countsql");
				selallsql =sqlarr.item(0).firstChild.data;	
				//查询SQL语句列名称
				sqlarr=root.selectNodes("/data/sql/xmlth");
				selsqlth =sqlarr.item(0).firstChild.data;			
   			}else{					//返回查询结果 
				//
				//审批名单表格
				var familyarr=root.selectNodes("/data/table/elements");
				xtable =familyarr.item(0).firstChild.data;
				familytb.innerHTML=xtable;
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
   			}
   			//=================查询SQL语句模式====================			
		}else{	
			var thtml;
			thtml = "<table width='100%' cellpadding='0' cellspacing='0'>"
				thtml += "<tr class='colField'>";				
					thtml += "<td height='23' align='center'></td>";
				thtml += "</tr>";
				thtml += "<tr class='colValue'>";				
					thtml += "<td height='23' align='center' style = 'font-size:14px;color: #FF0099;'>"+data+"</td>";
				thtml += "</tr>";
			thtml=thtml+"</table>";
			familytb.innerHTML=thtml;
		}
		//=====================//
		//信息汇总栏
		InfoCount();
	  	//分页栏
	  	Pagination();
	  	//批量审批操作栏
		CheckIdeaTab();
		//=====================//
		//查询功能模式(page页面查询all全部查询allmoney定额全部查询export导出查询)
		//全选审批按钮项
		if(exemode == "all"){
			//审批全部
			CallAllIdea();
		}else if(exemode == "allmoney"){
			//定额审批全部
			CallAllMoneyIdea();	
		}else if(exemode == "export"){			
			//导出文件
			exportResult();
		}
	}	
</script>
<script type="text/javascript">
	//打开家庭查看页面
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息卡片","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开家庭编辑页面
	function infoeditaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/info/neweditor/editorframe.jsp?nodeName=FAMILY&nodeId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息维护&updateListMonery('"+afmid+"');","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//
	//获取家庭政策业务相关详细信息
	function GetCheckPolicyInfosHtml(fmid,memid){
	    //
		//var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var bWidth= 450; //背景宽度
		var sArgu = "qpid="+selpid+"&qfmid="+fmid+"&qmemid="+memid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideainfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务相关信息详细","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
	//打开走访记录查询页面
	function CallInterviewDialog(fmid){		
		var afrom = "",awhere = "",aorder = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		var sArgu = "qfrom="+afrom+"&qwhere="+awhere+"&qorder="+aorder+"";
		var oUrl = "/db/page/policy/manage/policyinterview.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭走访记录登记","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
<script type="text/javascript">
	//打开单户审批页面
	function CallOneIdea(fmid){	
		var mode = "one",pagetitle = "";	
		//	
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		//标题
		pagetitle = "--" + seltabname;
		//
		var sArgu = "qtabid="+seltabid+"&qmode="+mode+"&qpid="+selpid+"&qfmids="+fmid+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policymoreidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"单户审批"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开批量审批页面
	function CallMoreIdea(){
		var fmids = "",mode = "more",pagetitle = "";
		//
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		//标题
		pagetitle = "--" + seltabname;
		//
		//表格复选选中
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="cbx[]") {
				if(document.all[i].checked == true){
					if(fmids == ""){
						fmids = document.all[i].id;
					}else{
						fmids += "," + document.all[i].id;
					}					
				}
			}
		}
		if(fmids==""){return;}
		//
		var sArgu = "qtabid="+seltabid+"&qmode="+mode+"&qpid="+selpid+"&qfmids="+fmids+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policymoreidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"批量审批"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开全部审批页面
	function CallAllIdea(){
		var pagetitle = "",countsql = "";	
		if(selallsql == ""){return;}
		countsql = selallsql;	
		//	
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		//标题
		pagetitle = "--" + seltabname;
		//
	  	//==================================================转码
		//特殊字符转换传递(以便页面接收)
		countsql = encodeURIComponent(countsql);
		//==================================================转码
		var sArgu = "qtabid="+seltabid+"&qpid="+selpid+"&qcountsql="+countsql+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policyallidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"审批全部"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开定额审批页面
	function CallMoneyIdea(){
		//
		var fmids = "",pagetitle = "";
		//	
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		//标题
		pagetitle = "--" + seltabname;
		//
	  	//表格复选选中
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="cbx[]") {
				if(document.all[i].checked == true){
					if(fmids == ""){
						fmids = document.all[i].id;
					}else{
						fmids += "," + document.all[i].id;
					}					
				}
			}
		}
		if(fmids==""){return;}
	  	//
		var sArgu = "qtabid="+seltabid+"&qpid="+selpid+"&qfmids="+fmids+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policymoneyidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"按定额审批"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开定额全部审批页面
	function CallAllMoneyIdea(){
		//
		var fmids = "",pagetitle = "",countsql = "";		
		//	
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		//标题
		pagetitle = "--" + seltabname;
		//
	  	if(selallsql == ""){return;}
  		countsql = selallsql;
  		//==================================================转码
		//特殊字符转换传递(以便页面接收)
		countsql = encodeURIComponent(countsql);
		//==================================================转码
	  	//
		var sArgu = "qtabid="+seltabid+"&qpid="+selpid+"&qcountsql="+countsql+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policyallmoneyidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"按定额全部审批"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//单户撤销审批意见
	function DelIdea(fmid){
		//
		//
	    if (!confirm("是否确定撤销该家庭审批结果?")) {
	    	//恢复选中
			for (i=0;i<document.all.length;i++) {
				if (document.all[i].name=="rd[]") {
					document.all[i].checked = false
				}
			}
	        return;
	    }
	    $.post("/db/page/policy/approval/policycheckpage.do",            //服务器页面地址
	        {
	            action: "DelIdea",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jfmids: fmid,
	            jendcheck: endcheck,
	            jreport: report
	        },
	        function(result) {                    	//回调函数
	        	alert(result);	
	        	//查询XML
				getCheckResultForXml("table","page");        	
	        }
	    );	    
	}
</script>
<script type="text/javascript">
	//打开审批流程页面
	function GetCheckIdeaFlow(fmid){
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		var sArgu = "qfmids="+fmid+"&qpid="+selpid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideaflow.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭审批流程","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
<script type="text/javascript">
	//导出文件
	function exportResult(){
		var jsqlth = "",jallsql = "";
		//列头和查询SQL语句
		jsqlth = selsqlth;
		jallsql = selallsql;
		//==================================================转码
		jsqlth = encodeURI(jsqlth);
		jallsql = encodeURI(jallsql);
		//==================================================转码
		//
	    $.post("/db/page/policy/export/exportmanage.do",            //服务器页面地址
	        {
	            action: "exportResult",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jsqlth: jsqlth,
	            jallsql: jallsql
	        },
	        function(result) {                    	//回调函数
     			//导出准备就绪
     			if(result=="1"){		 			
		 			//var bWidth = document.body.clientWidth-30;   //背景宽度
					//var bHeight= document.body.clientHeight-80; //背景高度	
					var bWidth = 300;   //背景宽度
					var bHeight= 200; //背景高度	
					var oUrl = "/db/page/system/exportfile/exportExcel.do?";
					jBox.open("iframe-jBoxID","iframe",oUrl,"导出Excel文件","width="+bWidth+",height="+bHeight
						+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		 		}	
	        }
	    );
	}
</script>