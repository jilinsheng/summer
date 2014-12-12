<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	//业务选择
	String reqpolicy = request.getParameter("qpolicy");	
	
	if (reqpolicy == null) {
		//默认为空
		reqpolicy = "-1";    //选择业务[不选]
	}
	//
	//接收业务名称URLDecoder
	//从查询表接收查询字段select
	String reqselect = request.getParameter("qselect");
	reqselect = new String(reqselect.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqselect == null) {
		//默认为空
		reqselect = "";    //字段
	}
	//从查询表接收查询表from
	String reqfrom = request.getParameter("qfrom");
	reqfrom = new String(reqfrom.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqfrom == null) {
		//默认为空
		reqfrom = "";    //空表
	}
	//从查询表接收查询条件where
	String reqwhere= request.getParameter("qwhere");
	reqwhere = new String(reqwhere.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqwhere == null) {
		//默认为空
		reqwhere = "";    //空条件
	}
	//从查询表接收查询排序order
	String reqorder= request.getParameter("qorder");
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
    
    <title>业务审批管理</title>
    
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
	.mystyle {	
		line-height: 2;	
		font-size: 12px;
		color: #6BA6FF;
		height: 20px;		
	}
	.mytabdefault{	
		line-height: 2;	
		font-size: 12px;
		color: #6BA6FF;
		height: 20px;		
	}
	.mytabactive{	
		line-height: 2;	
		font-size: 12px;
		color: #000000;
		height: 20px;		
	}
	.tab {
		width: 100%;		
		margin: 0 auto;
		overflow: hidden;				
	}
	.menuauto, .menuauto li {
		margin: 0;
		padding: 0;
		height: 23px;
		list-style: none;
		overflow: hidden;
		text-align: center;					
	}
	.menuauto {
		border-bottom: 1px solid #DEEFFF;		
	}
	.menuauto .default {
		width: 86px;
		float: left;
		font-size: 13px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		background: url('/db/page/images/checkdefault.jpg') no-repeat;
	}
	.menuauto .active {
		width: 86px;
		float: left;
		font-size: 13px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		color: #FFFFFF;
		background: url('/db/page/images/checkactive.jpg') no-repeat;
	}
	
	.menu, .menu li {
		margin: 0;
		padding: 0;
		height: 20px;
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
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		background: url('/db/page/images/checkdefault.jpg') no-repeat;
	}
	.menu .active {
		width: 86px;
		float: left;
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		color: #FFFFFF;
		background: url('/db/page/images/checkactive.jpg') no-repeat;
	}
	.btn { 
		BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 
	    #002D96 0px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; FILTER: 
	    progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
	    StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 
	    1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 0px; PADDING-BOTTOM: 0px;
	    BORDER-BOTTOM: #002D96 1px solid;
	    height:21;
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
		color: #6BA6FF;	
	}
	.pageTtems {
		color: #660099;		
	}
	.pointer {
		cursor: pointer;
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
  <script  type="text/javascript">
		//
		var empid = "";        //当前登录用户编号 
		var deptid = "";        //当前登录机构
		
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		var selselect = "";    	//选择查询结果字段
		var selfrom = "";    	//选择查询结果表
		var selwhere = "";    	//选择查询结果条件
		var selorder = "";    //选择查询结果排序
		//
		var seltabmode = "spancheckitem";//评议标签spancheckitem结果标签spanresultitem
		var selautomode = "check";//标签参数check评议result结果
		//
		var selcheckmode = "infocheck";//名单标签参数infocheck评议名单的各个标签inforesult结果名单各个标签inforecheck重评议名单各个标签
		//		
		var selautotabid = "101";//常量类定义(默认:新增审批)
		var selautotabname = "";
		//
		var selchecktabid = "111";//常量定义(默认:未评议)
		var selchecktabname = "";
		//
		//
		var selpolicy = "-1";    	//选择业务
		var selpolicyname = "";		//业务名称
	    //
		var xmldata;	//XML数据实体
		var selallfmid = "";//本页审批家庭ID[中间用,逗号隔开]
		//
		//
	    //
		var colnum = 0,rownum =0;//列数/行数
		//
		var sqlcount = "0";//总记录数
		var sqlpagecount = "0";//总页数		
		var sqlpagenum = "14";//每页记录数
		//
		var sqlselpage = 1;//选择页
		//
		var selbegpage = 0;		//分页开始
		var selendpage = sqlpagenum;//分页结束
		//
		//
		var selsql = "";			//当前查询SQL语句
		//
	    var selexport = "0";		//导出数据标识(0不1导出数据)
		//
		//业务审批标识
		var allaccflag = "0";		//终审结算标识
		var flowflag = "0";			//审批流程标识
		var checkmoreflag = "0";	//批量审批标识
		var oneflowflag = "0";		//第一级审批机构标识
		var endflowflag = "0";		//最后级审批机构标识
		var pcheckflag = "0";		//岗位审批标识
		var pcheckmoreflag = "0";	//岗位批量审批标识
		var preportflag = "0";		//岗位确认审批标识
		var checkmoneyflag = "0";	//用户可修改救助金标识
		var acctypeflag = "0";		//核算类型标识
		//
		//
  </script>
  <script  type="text/javascript">	
  	//条件转换[逻辑条件转换物理条件]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,tmode,tbegpage,tendpage;
	    var tdept;  
	    var tpolicy,tchecktabid,tautotabid;   
	    var tchoiceflag,isallcb;   
	    		   
	    //      
	    tselect = selselect;  
	    tfrom = selfrom;
	    twhere = selwhere;
	    //排序
    	var soid = "",soname = "";
    	var isrdasc = document.getElementById("rdasc");
    	var isrddesc = document.getElementById("rddesc");	    	
    	if(isrdasc.checked == true){	
    		soname = $("#orderbylist").val();		    	
	    	selorder = 	soname + " asc"; 
      	}else if(isrddesc.checked == true){
      		soname = $("#orderbylist").val();		    	
	    	selorder = 	soname + " desc";  
      	}	
	    torder = selorder;	    
	    //解析查询模式常量定义模块定义tmode
	    tmode = "1";//模式0执行查询1提取SQL语句
	    tbegpage = selbegpage;
	    tendpage = selendpage;
	    //机构
	    tdept = deptid; 
	    //
	    tempid = empid;	    
	    
	    //业务选择		    		          		
	    tpolicy = selpolicy;
	    //业务选项卡
	    tautotabid = selautotabid;		    
	    tchecktabid = selchecktabid;		    
	    //
	    //
	    //全选
	    selsql = "";
	    tchoiceflag = "page";
	    //
		isallcb = document.getElementById("checkallb");
		if(isallcb==null){
			tchoiceflag = "page";
		}else{
		    if(isallcb.checked== 1){
		    	tchoiceflag = "all";
		    }else{
		    	tchoiceflag = "page";
		    }
	    }
	    
	    //导出数据标识(0不1导出数据)
	    if(selexport == "1"){
	    	tchoiceflag = "export";
	    }			    
	    //	
	    mode = "exefamilychecksql";
	    //
	    //
	    //加载数据		    
		DisplayPageStatus(); 
		//
		//禁用各标签
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
	  	$("#spanresultitem").attr("disabled", "disabled");
		//
		//
	    $.post("page/info/search/TableInfoServlet",      //服务器页面地址
	        {
	            action: "getphysql", //action参数
	            mode: mode,   //参数
	            tselect: tselect,   //参数
	            tfrom: tfrom,   //参数
	            twhere: twhere,   //参数
	            torder: torder, //参数
	            tmode: tmode,
	            tbegpage: tbegpage,
	            tendpage: tendpage,  //参数分页 
	            tdept: tdept,
	            tempid: tempid,		            
	            tpolicy: tpolicy,
	            tautotabid: tautotabid,
	            tchecktabid: tchecktabid,
	            tchoiceflag: tchoiceflag         
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
     			HiddenPageStatus();
     			//
				//启用各标签
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//  	        	
	  			//
	          	if(result=="-1"){                
	             	//查询语句不合法          
	          	}else{
	            	if(mode=="exefamilychecksql"){
		            	//业务审批家庭查询取结果physql 		            	
		            	if(tchoiceflag== "all"){
		            		//全选SQL语句
					    	selsql = result;						    	
					    }else if(tchoiceflag == "export"){
					    	//导出数据标识(0不1导出数据)
	    					selexport = "0";
	    					tchoiceflag = "page";
	    					//
	    					ExportXlsOpen(result);					    	
					    }else{
					    	//alert(result);		              			  		
	          				ShowData(result);		          										    	
					    } 
		            }			             
	           }                              
	    });
	    //导出数据标识(0不1导出数据)
		selexport = "0";      
    }
    
	//单个撤消审批意见操作
	function RemoveCheckIdea(sempid,spid,sfmid){
		//				
		//确认
	    if (!confirm("是否确定撤消该家庭审批意见？")) {
	        return;
	    }			
		$.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
		    {
		        action: "removeCheckIdea", //action参数
		        empid: sempid,
		        pid: spid,			        
		        fmid: sfmid                             
		    },
		    function(result) {   //回调函数	
		    	//				   							    
		    	//显示操作结果		    		
		        DisplayResult(result);		        
				//取得物理查询语句且执行返回XML格式		  
				GetPhySql();  				      	                                         
		    }
		);   
	}				
	//刷新全部数据
	function UpdateMoneys(){
		//确认
	    if (!confirm("是否确定刷新全部数据？")) {
	        return;
	    }
		//批量计算单个家庭或者成员拟发救助金
    	UpdatePolciyMatchMore();	
	}
	//多个家庭添加生成业务审批名单	
	function SetNewPolciyMatchMore(){		
		//
		pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>生成审批名单请稍等...";			    
		DisplayPageStatus();
		//禁用各标签
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
 		$("#spanresultitem").attr("disabled", "disabled");
		//   
	 	//
	   	$.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
	       	{
	           	action: "setNewPolciyMatchMore" , //action参数 
	           	pid: selpolicy,			           	
	           	empid: empid                           
	       	},
	      	function(result) {   //回调函数
	      		//加载数据完毕
     			HiddenPageStatus();
     			pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>查询数据请稍等...";
     			//启用各标签
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//取得业务审批标准	
	    		GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode);			                      
	     	}
	   );
	  
	}
	//计算单个家庭或者成员拟发救助金
    function UpdatePolciyMatchOne(fmid){
    	//
		var pid = "";
		//
		pid= selpolicy;
		//
		pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>更新拟发救助金请稍等...";			    
		DisplayPageStatus(); 
	    //
	    //禁用各标签
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
 		$("#spanresultitem").attr("disabled", "disabled");
		//   		
		$.post("page/policy/manage/PolicyManageServlet", //服务器页面地址
		    {
		        action: "updatePolciyMatchOne", //action参数
		        empid: empid,
		        pid: pid,
		        fmid: fmid                          
		    },
		    function(result) {   //回调函数	
		    	//
		    	//加载数据完毕
     			HiddenPageStatus(); 
     			pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>查询数据请稍等...";
     			//启用各标签
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//   
     			//取得业务审批标准	
	    		GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode); 			      	                                         
		    }
		); 
    }
    //批量计算单个家庭或者成员拟发救助金
    function UpdatePolciyMatchMore(){
    	//
		var pid = "";
		//
		pid= selpolicy;
		//
		pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>更新拟发救助金请稍等...";			    
		DisplayPageStatus(); 
	    //
	    //禁用各标签
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
 		$("#spanresultitem").attr("disabled", "disabled");
		//   		
		$.post("page/policy/manage/PolicyManageServlet", //服务器页面地址
		    {
		        action: "updatePolciyMatchMore", //action参数
		        empid: empid,
		        pid: pid                          
		    },
		    function(result) {   //回调函数	
		    	//
		    	//加载数据完毕
     			HiddenPageStatus(); 
     			pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>查询数据请稍等...";
     			//启用各标签
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//   
     			//取得业务审批标准	
	    		GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode); 			      	                                         
		    }
		); 
    }
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
		    	pardiv.innerHTML = "单击查看:当前选中业务["+result + "]政策描述";	
		    	//
		    	selpolicyname = result;	          						      	                                         
		    }
		);        
	}
	//取得当前页排序下拉框
	function GetCheckPolicyOrder(pardivid,sname){
	  	//
	  	var pid = selpolicy;
	  	var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getCheckPolicyOrder", //action参数
		        sname: sname,
		        empid: empid,
		        pid: pid
		    },
		    function(result) {   //回调函数		    	
		      	pardiv.innerHTML = result;
		      	//单击事件		      	
				$("#"+sname).change(function(){GetPhySql();});		      			      	                                         
		    }
		);        
	}
	//取得业务审批标准
	function GetPolicyAutoUlLi(pardivid,sname,smode){
	  	//
	  	var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyAutoUlLi", //action参数
		        sname: sname,
		        smode: smode   
		    },
		    function(result) {   //回调函数		    	
		      	pardiv.innerHTML = result;
		      	//生成标签点击事件
				init(sname);
				//多个家庭添加生成业务审批名单	
				SetNewPolciyMatchMore();				       		    		      							      		      						      	                                         
		    }
		);        
	}	
	//取得业务审批标准
	function GetPolicyCheckUlLi(pardivid,sname,smode,sinfomode){
	  	//
	  	var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyCheckUlLi", //action参数
		        sname: sname,
		        smode: smode,
		        sinfomode: sinfomode
		    },
		    function(result) {   //回调函数		    	
		      	pardiv.innerHTML = result;
		      	//生成标签点击事件
				initcheck(sname);																			      	                                         
		    }
		);        
	}		
	//取得名单标签
	function GetPolicyInfoTab(mode){
	  	if(mode=="check"){//审批
	  		$("#spancheckitem").css({"line-height": "2","font-size": "12px","color": "#000000","height": "20px"});
	  		$("#spanresultitem").css({"line-height": "2","font-size": "12px","color": "#6BA6FF","height": "20px"});
	  		//
	  		spancheckimg.innerHTML="<img src='/db/page/images/right.gif'/>";
	  		spanresultimg.innerHTML="";	
	  		//
	  		seltabmode = "spancheckitem";
	  		selautomode = "check";
	  		selcheckmode = "infocheck";
	  		//
	  		selautotabid="101"
	  		selchecktabid = "111";
	  		//
	  		  	
	  	}else if(mode=="result"){//结果
	  		$("#spancheckitem").css({"line-height": "2","font-size": "12px","color": "#6BA6FF","height": "20px"});
	  		$("#spanresultitem").css({"line-height": "2","font-size": "12px","color": "#000000","height": "20px"});
	  		//
	  		spancheckimg.innerHTML="";
	  		spanresultimg.innerHTML="<img src='/db/page/images/right.gif'/>";
	  		//
	  		seltabmode = "spanresultitem";
	  		selautomode= "result";
	  		selcheckmode = "infocheck";	
	  		//
	  		selautotabid="200"
	  		selchecktabid = "211";
	  		//
	  				  		   		   		
	  	}
	  	//取消全选
		var isallcb = document.getElementById("checkallb");
		if(isallcb==null){
			
		}else if(isallcb.checked== 1){
	    	isallcb.checked = false;  							    	
	    } 
	    //
	  	//取得业务审批标准
		GetPolicyAutoUlLi("choicepolicyauto","autolists",selautomode);
		  				
	}
	//显示页面状态div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2;  //背景宽度
		//vtop= document.body.clientHeight/2; //背景高度
		vtop= $("#spanpolicyitem").offset().top+28;      	
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
    	vleft = document.body.clientWidth/2;  //背景宽度
		//vtop= document.body.clientHeight/2; //背景高度
		vtop= $("#spanpolicyitem").offset().top+28;    	
    	//
    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
	    var resultstatusdiv = $("#resultstatusdiv");            //获取提示信息div
	    resultstatusdiv.html(info);                       //设置div内文本		    
	    $(resultstatusdiv).fadeIn();                      //淡入信息
	    setTimeout("HiddenResult()",2000);          //2秒后隐藏信息
	}		
	//隐藏提示信息div
	function HiddenResult() {
	    $("#resultstatusdiv").fadeOut();                  //淡出信息
	}
	//		
	//初始化页面
    function IniPage(){	
    	//
    	vleft = document.body.clientWidth/2;  //背景宽度
		//vtop= document.body.clientHeight/2; //背景高度
		vtop= $("#spanpolicyitem").offset().top+28;     	
    	//
    	//当前登录用户编号    
		empid = "<%=empno%>";     
		//登录机构    
		deptid = "<%=onno%>";    //当前登录机构 
		//
		//选择业务查询	
		selpolicy = "<%=reqpolicy%>";		 //业务选择			
		//
		selselect = "<%=reqselect%>";		 //查询选择字段
		selfrom = "<%=reqfrom%>";		 //查询选择表
		selwhere = "<%=reqwhere%>";		 //查询选择条件
		selorder = "<%=reqorder%>";		 //查询选择排序	
		//		
		//业务名称
        GetPolicyName("spanpolicyitem");
        //
        //取得业务审批状态标识
		GetCheckPolicyFlagsXml();	        
		//       		    
    }
  </script>
  
  </head>
  <body onload = "IniPage()">	
    <div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>查询数据请稍等...</div> 
  	<div id='resultstatusdiv'></div> 
  	<table width='100%' cellpadding='0' cellspacing='0' class='tab'>
  		<tr class = 'mybackground'>
  			<td width='70%'>  				
  				<span class = "pointer mystyle" id = "spanpolicyitem" onclick="CallStandardsDialog()"></span>
  			</td>  			
  			<td width = "84px" align="right"> 
  				<span id = "spancheckimg"></span> 				
  				<span class = "pointer mytabactive" id = "spancheckitem" onclick="GetPolicyInfoTab('check')">评议审批操作</span>
  			</td>  			
  			<td width = "84px" align="left">
  				<span id = "spanresultimg"></span>
  				<span class = "pointer mytabdefault" id = "spanresultitem" onclick="GetPolicyInfoTab('result')">评议结果名单</span>
  			</td>
	   	</tr>	   	   
	   	<tr>
	   		<td class = "myspace" colspan = "3"><div id = 'choicepolicyauto'></div></td>
	   	</tr>
	   	<tr>
	   		<td valign="top" class = "myspace" colspan = "3"><div id = 'choicepolicycheck'></div></td>
	   	</tr>
	   	<tr>
	   		<td valign="top" class = "myspace" colspan = "3"><div id='checkresultcon'> </div></td>
	   	</tr>
	   			   	  	
	</table>
	
  	<table width='100%' cellpadding='0' cellspacing='0'>  		
  		<tr class = "mystyle mybackground" >  
  			<td width = "122px" align="left" height='23'>
  				<div  align="left" id = "divboxgroup"></div>  				
  			</td>  			
  			<td height='23' align="center">
  				<span>
  					<input type="radio" name="rd" id="rdasc" value="rdasc" onclick='GetPhySql();' checked/>升序
  					<input type="radio" name="rd" id="rddesc" value="rddesc" onclick='GetPhySql();' />降序
  				</span> 
  				<span id = "spanorderby"></span>
  				<span id = "spanupdatemoneys"></span>		
  			</td>  			
  			<td width = "210px" height='23'>
  				<div align="right" id = "divbtngroup" ></div>
  			</td>
  		</tr>   				   	  	
	</table>
  </body>
</html>
<script  type="text/javascript">
	//
	function init(ids){
		document.getElementById(ids).getElementsByTagName('li')[0].className='active';			
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
			//2为长度li-1为PolicyQueryServlet.java生成标签
			var tid,tname;
			tid =  obj.id;
			tname = obj.firstChild.nodeValue;
			tid = tid.substring(2,tid.length);	
			//
			selautotabid = tid;
		  	selautotabname = tname;	  
			//
			if(selautotabid=="101"){
				selcheckmode = "infocheck";				
		  		selchecktabid = "111";
			}else if(selautotabid=="102"){
				selcheckmode = "inforesult";
				selchecktabid = "121";
			}else if(selautotabid=="200"){				
				selcheckmode = "infocheck";
				selchecktabid = "211";
			}else if(selautotabid=="201"){
				selcheckmode = "inforesult";
				selchecktabid = "221";
			}else if(selautotabid=="202"){
				selcheckmode = "remcheck";
				selchecktabid = "231";
			} 
			//取得业务审批标准
			GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode);	
			//
			//	  		
		}
	}
	//
	function initcheck(ids){
		document.getElementById(ids).getElementsByTagName('li')[0].className='active';					
		document.getElementById(ids).onclick=function(){onmousOverCheck(ids);}//点击激发效果	
		//
		selbegpage = 0;		//分页开始
	 	selendpage = sqlpagenum;//分页结束
	 	sqlolepagecount = 0;//旧分页选择下拉框
	 	sqlselpage = 1;//开始第一页		  	
	  	//条件转换[逻辑条件转换物理条件]
    	GetPhySql();			
	}
	function onmousOverCheck(ids){		
		o = o || window.event;
		var obj=o.target || o.srcElement;
		if (obj.tagName=='LI'){
		  	if (obj.className=='active'){
		  		
		  	}else{
		  		var o=document.getElementById(ids).getElementsByTagName('li');
			  	for (var i=0;i<=o.length-1;i++){o[i].className='default'}
			  	obj.className='active';
		  	}		  	
		  	//
		  	//2为长度li-1为PolicyQueryServlet.java生成标签
		  	var tid,tname;
		  	tid =  obj.id;
		  	tname = obj.firstChild.nodeValue;
		  	tid = tid.substring(2,tid.length);		  
		  	//
		  	selchecktabid = tid;
		  	selchecktabname = tname;
		  	//
			var isallcb = document.getElementById("checkallb");
			if(isallcb==null){
				
			}else if(isallcb.checked== 1){
		    	isallcb.checked = false;  							    	
		    } 
		    //
			selbegpage = 0;		//分页开始
		 	selendpage = sqlpagenum;//分页结束
		 	sqlolepagecount = 0;//旧分页选择下拉框
		 	sqlselpage = 1;//开始第一页		  	
		  	//条件转换[逻辑条件转换物理条件]
	    	GetPhySql();
		}
	}	
</script>
<script type="text/javascript">
	//
	//
	function ShowData(data){
		var xmlDoc;
		//取出表头
		var html= "",fmid = "",fmno = "",fmname = "",fmolemoney = "0";fmmoney = "0";		
		//
		var sumfamily ="0",sumpopcount ="0",summoney = "0",sumolemoney = "0",sumnewmoney = "0";
		//
		var recheckflag = "0";
		//		
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
			//家庭户数汇总
			sumfamily = sqlcount;
			//保障人口汇总		
			var sumpopcountarr=root.selectNodes("/data/sumpopcount/count");
			sumpopcount =sumpopcountarr.item(0).firstChild.data;			 
			//计算收入汇总
			var summoneyarr=root.selectNodes("/data/summoney/money");
			summoney =summoneyarr.item(0).firstChild.data;	
			//上期救助金汇总			
			var sumolemoneyarr=root.selectNodes("/data/sumolemoney/money");
			sumolemoney =sumolemoneyarr.item(0).firstChild.data;			
			//拟发救助金汇总
			var sumnewmoneyarr=root.selectNodes("/data/sumnewmoney/money");
			sumnewmoney =sumnewmoneyarr.item(0).firstChild.data;
			//
			//结果页		
			checkresultcon.innerHTML="无查询结果";
			//无按钮操作
			divbtngroup.innerHTML ="";
			//评议或者结果
			divboxgroup.innerHTML = ""; 
			//
			//
			//XML列名：家庭表.家庭编号,家庭表.家庭编码,家庭表.户主姓名,审批表.保障人口,审批表.计算收入,审批表.上期救助金,审批表.拟发救助金,审批表.评议结果,家庭表.户籍机构,家庭表.所属
			//列名称
			var headarr=root.selectNodes("/data/ehead/cell");
			//
			//headarr.length+1显示相关操作项
			colnum = headarr.length+1;
			//
			html += "<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";			
			//
			//评议或者结果
			if(seltabmode=="spanresultitem"){					
				
			}else if(seltabmode=="spancheckitem"){
				if(selchecktabid=="123"){//顺延
					//不做审批(通过修改信息)	
				}else{
					html += "<td>操作</td>";
				}
			}
			//
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				if(i==9){//机构ID
					//机构ID隐藏
				}else{
					var temp ="<td height='23'>"+ss[1]+"</td>";
					html=html+temp;
				}				
			}
			html += "<td>相关操作</td>";
			html=html+"</tr>";
			//
			//取出记录值
			xmldata = root.selectNodes("/data/list/entity");
			//
			//
			var rows=root.selectNodes("/data/list/entity");						
			//
			rownum = rows.length;
			//列名：家庭表.家庭编号,家庭表.家庭编码,家庭表.户主姓名,审批表.保障人口,审批表.计算收入,审批表.上期救助金,审批表.拟发救助金,审批表.评议结果,家庭表.户籍机构,家庭表.所属
			//最后一列列名：撤消重审列(不属XML列头)
			//
			for(var i=0;i<rows.length;i++){
				var row = rows.item(i).childNodes;
				//
				fmid = row.item(0).firstChild.data;//家庭ID
				fmno = row.item(1).firstChild.data;//家庭编号
				fmname = row.item(2).firstChild.data;//家庭户主姓名				
				fmmoney = row.item(5).firstChild.data;//家庭拟发救助金
				fmolemoney = row.item(6).firstChild.data;//家庭上期救助金
				//row.length-3//审批结果列
				recheckflag = row.item(10).firstChild.data;//家庭本级撤消审批意见重审标识				
				//		
				var temp="<tr ";						
				//单双行
				var mathrow = i%2;				
				if(mathrow == 1){//背景颜色
					temp +=" style = 'background: #F2F5F7;'>";	
				}else{
					temp +=">";	
				}			
					//各列值
					//评议或者结果
					//
					divbtngroup.innerHTML ="";
					divboxgroup.innerHTML = "";   
			  		//操作列
			  		//
			  		if(seltabmode=="spanresultitem"){//评议名单结果	
						//刷新全部数据
						var parspan = document.getElementById("spanupdatemoneys");
						parspan.innerHTML = "";
						//隐藏操作列
						//
						var temphtml = "<!--<button class = 'btn' onclick=\"ExportXls()\">导出Excel</button>-->";						
						//
						if(flowflag=="1"){//存在审批流程
							if(pcheckflag=="1"){//用户岗位允许审批
								if(preportflag=="1"){//允许确认审批名单
									if(endflowflag=="1"){//是终审流程
										if(allaccflag=="0"){//业务未终审结算
											temphtml += "<button class = 'btn' onclick=\"CallAllAccDialog('edit')\">终审结算</button>";											
										}else{
											temphtml += "<button class = 'btn' onclick=\"CallAllAccDialog('add')\">设置终审结算</button>";											
										}  
									}	
								}	
							}							
						}
						divbtngroup.innerHTML = temphtml;						
						divboxgroup.innerHTML = "";   
					}else if(seltabmode=="spancheckitem"){//评议审批操作
						//刷新全部数据
						var parspan = document.getElementById("spanupdatemoneys");
						if(oneflowflag == "1"){//第一级审批机构标识
							parspan.innerHTML = "<button class = 'btn' onclick=\"UpdateMoneys()\">刷新全部数据</button>";
						}else{
							parspan.innerHTML = "";
						}						
						//
						if(selchecktabid=="123"){//顺延
							//不做审批(通过修改信息)							
						}else{						
							temp += "<td width='32px'height='23' class='colValue'>";
							//业务是否终审结算
							if(allaccflag=="0"){//业务未终审结算
								//业务是否批量审批
								if(checkmoreflag=="0"){//不能批量审批流程
									//该机构是否存在业务审批流程
									if(flowflag=="0"){//不存在审批流程										
										temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
										//
										divbtngroup.innerHTML ="该业务不需要该用户机构审批";
										divboxgroup.innerHTML = "";										
									}else{//存在审批流程
										//用户岗位审批权限
										if(pcheckflag=="0"){//用户岗位不允许审批
											temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
											//
											divbtngroup.innerHTML ="此用户岗位没有该业务审批权限";
											divboxgroup.innerHTML = "";
										}else{//用户岗位允许审批											
											if(pcheckmoreflag=="0"){//用户岗位不允许批量审批
												temp += "<input type='radio' name='checkrd' onclick=\"CheckNow(this,'"+fmid+"')\" />";
											}else{//用户岗位允许批量审批
												temp += "<input type='checkbox' name='checkb' id='checkb"+i+"'/>";
												//
												var temphtml = "<input type='checkbox' id='checkpageb' onclick='CheckPage()' /><span>本页全选</span>";
												temphtml += "<input type='checkbox' id='checkallb' onclick='CheckAll()' /><span>全选</span>";
												//													
												divbtngroup.innerHTML = "<button class = 'btn' onclick=\"CheckAllNow()\">评议审批</button>";
												divboxgroup.innerHTML = temphtml;
											}
										}										
									}									
								}else{//能批量审批流程
									//该机构是否存在业务审批流程
									if(flowflag=="0"){//不存在审批流程
										temp += "<input type='checkbox' name='checkb' id='checkb"+i+"' disabled = 'disabled' />";
										//
										divbtngroup.innerHTML ="该业务不需要该用户机构审批";
										divboxgroup.innerHTML = "";											
									}else{//存在审批流程
										//用户岗位审批权限
										if(pcheckflag=="0"){//用户岗位不允许审批
											temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
											//
											divbtngroup.innerHTML ="此用户岗位没有该业务审批权限";
											divboxgroup.innerHTML = "";
										}else{//用户岗位允许审批
											temp += "<input type='checkbox' name='checkb' id='checkb"+i+"'/>";
											//
											var temphtml = "<input type='checkbox' id='checkpageb' onclick='CheckPage()' /><span>本页全选</span>";
											temphtml += "<input type='checkbox' id='checkallb' onclick='CheckAll()' /><span>全选</span>";
											//
											divbtngroup.innerHTML = "<button class = 'btn' onclick=\"CheckAllNow()\">评议审批</button>";
											divboxgroup.innerHTML = temphtml;
										}
																				
									}									
								}
							}else if(allaccflag=="1"){//业务已经终审结算
								//业务是否批量审批
								if(checkmoreflag=="0"){//不能批量审批流程
									temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
								}else{//能批量审批流程
									temp += "<input type='checkbox' name='checkb' id='checkb"+i+"' disabled = 'disabled' />";
								}
								//
								divbtngroup.innerHTML ="该业务已经终审结算,不需评议审批";
								divboxgroup.innerHTML = "";   
						  		//
							}else if(allaccflag=="2"){//业务不能办理
								//业务是否批量审批
								if(checkmoreflag=="0"){//不能批量审批流程
									temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
								}else{//能批量审批流程
									temp += "<input type='checkbox' name='checkb' id='checkb"+i+"' disabled = 'disabled' />";
								}
								//
								divbtngroup.innerHTML ="该业务未办理,不能评议审批";
								divboxgroup.innerHTML = "";   
						  		//								
							}														
							temp += "</td>";
						}						
					}
					//显示列									
					for (var j=1;j<row.length;j++){						
						if(j==1){//家庭编号列
							var temp1="<td height='23' class='colValue'>"+
								"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
							temp=temp+temp1;
						}else if(j==7){//审批结果列(7审批结果列)														
							if(selchecktabid=="123" || selchecktabid=="212"){//顺延
								var temp1="<td height='23' class='colValue'><span>顺延</span></td>"
								temp=temp+temp1;
							}else{
								var temp1="<td height='23' class='colValue'>"+
									"<span class = 'pointer colTtems' onclick= \"CallCheckIdeaDialog('edit','"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
								temp=temp+temp1;
							}							
						}else if(j==8){//机构ID列隐藏(8机构ID列)	
						
						}else if(j==10){//是否可以本级撤消审批意见重审标识列(10家庭本级撤消审批意见重审标识列)	
						
						}else{
							var temp1="<td height='23' class='colValue'>"+
							row.item(j).firstChild.data+"</td>";
							temp=temp+temp1;
						}
					}
					//
					if(seltabmode=="spancheckitem"){
						temp += "<td width='64px' height='23' class='colValue'>";
							if(oneflowflag=="1"){//业务第一级审批机构标识
								temp += "<span  class = 'pointer'><img src='/db/page/images/checkfamily.png' alt= '编辑家庭' onclick= \"infoeditaction('"+fmid+"')\"/>&nbsp;</span>";										
							}
							temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '走访登记' onclick=\"CallInterviewDialog("+fmid+")\"/>&nbsp;</span>";
						temp += "</td>";
					}else if(seltabmode=="spanresultitem"){
						if(selchecktabid=="123" || selchecktabid=="212"){//顺延
							temp += "<td width='64px' height='23' class='colValue'>";
								temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '走访登记' onclick=\"CallInterviewDialog("+fmid+")\"/>&nbsp;</span>";
							temp += "</td>";
						}else{	
							temp += "<td width='64px' height='23' class='colValue'>";						
								if(recheckflag=="1"){//家庭本级可以撤消审批意见重审标识
									if(preportflag=="1"){//允许确认审批名单
										temp += "<span  class = 'pointer'><img src='/db/page/images/close.gif' alt= '撤消审批' onclick=\"RemoveCheckIdea('"+empid+"','"+selpolicy+"','"+fmid+"')\"/></span>";
									}												
								}	
								temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '走访登记' onclick=\"CallInterviewDialog("+fmid+")\"/>&nbsp;</span>";
							temp += "</td>";						
						}	
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
				//
				sumfamily = "0";
				sumpopcount ="0";
				summoney = "0";
				sumolemoney = "0";
				sumnewmoney = "0";
				//
				html += "<table width='100%' cellpadding='0' cellspacing='0'>"
					html += "<tr>";	
					//
					if(flowflag=="0"){//不存在审批流程
						html += "<td height='25' class='colValue'>该业务不需要该用户机构审批</td>";
						//
						divbtngroup.innerHTML = "该业务不需要该用户机构审批";
						divboxgroup.innerHTML ="";	
					}else if(flowflag=="1"){//存在审批流程
						//
						if(allaccflag=="1"){//业务已经终审结算
							html += "<td height='25' class='colValue'>该业务已经终审结算,不需评议审批</td>";
							//
							if(seltabmode=="spanresultitem"){//评议名单结果
								if(endflowflag=="1"){//是终审流程
									var tempbtnhtml = "<button class = 'btn' onclick=\"CallAllAccDialog('add')\">设置终审结算</button>";							
									divbtngroup.innerHTML = tempbtnhtml;
								}else{
									divbtngroup.innerHTML ="";
								}
							}else{
								divbtngroup.innerHTML ="该业务已经终审结算,不需评议审批";
							}
							divboxgroup.innerHTML = "";
						}else if(allaccflag=="2"){//业务不能办理
							html += "<td height='25' class='colValue'>该业务未办理,不能评议审批</td>";
							//
							if(seltabmode=="spanresultitem"){//评议名单结果
								if(endflowflag=="1"){//是终审流程
									var tempbtnhtml = "<button class = 'btn' onclick=\"CallAllAccDialog('add')\">设置终审结算</button>";						
									divbtngroup.innerHTML = tempbtnhtml;
								}else{
									divbtngroup.innerHTML ="";	
								}	
							}else{
								divbtngroup.innerHTML ="该业务未办理,不能评议审批";	
							}					
							divboxgroup.innerHTML = ""; 
						}else{
							html += "<td height='25' class='colValue'>无查询结果</td>";
						} 
					}else{						
						html += "<td height='25' class='colValue'>无查询结果</td>";				
					}
					html += "</tr>";
				html += "</table>"; 
		  		//		  		
			}				
			//	
			html += "<table width='100%' cellpadding='0' cellspacing='0'>"
				//
				html += "<tr class='colField'>";
					html += "<td height='18' class='pageTtems' >";
		  				html += "<span>总家庭户数[<span id = 'spansumfamily'></span>]</span>";		  				
		  			html += "</td>";
					html += "<td height='18' class='pageTtems' >";
		  				html += "<span>总保障人口[<span id = 'spansumpopcount'></span>]</span>";		  				
		  			html += "</td>";
		  			html += "<td height='18' class='pageTtems' >";
		  				html += "<span>总计算收入[<span id = 'spansummoney'></span>]</span>";
		  			html += "</td>";
		  			html += "<td height='18' class='pageTtems' >";
			   			html += "<span>总上期救助金[<span id = 'spansumolemoney'></span>]</span>";
		  			html += "</td>";
		  			html += "<td height='18' class='pageTtems' >";
			   			html += "<span>总拟发救助金[<span id = 'spansumnewmoney'></span>]</span>";
		  			html += "</td>";
				html += "</tr>";
				html += "<tr class='colField'>";									
					html += "<td colspan = '5' height='23'>";
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
							//选择页下拉选择框
							html += "<span id = \"spanpages\"></span> ";
						html += "  每页<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>条记录";
					html += "</td>";
				html += "</tr>";
			html += "</table>";
			//			
			//结果页
			checkresultcon.innerHTML=html;
			//			
			spansumfamily.innerHTML=sumfamily;			
			spansumpopcount.innerHTML=sumpopcount;
			spansummoney.innerHTML=summoney;
			spansumolemoney.innerHTML=sumolemoney;
			spansumnewmoney.innerHTML=sumnewmoney;
			//
			//

			spanpages.innerHTML=GetPageGo();
			//	
			//选择页
			$("#selectpage").val(sqlselpage);
			//每页显示行数
			$("#divpagerow").val(sqlpagenum);
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
			//			
			//结果页		
			checkresultcon.innerHTML="无查询结果";
			//无按钮操作
			divbtngroup.innerHTML ="";
			//评议或者结果
			divboxgroup.innerHTML = ""; 
			//
	
		}		
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
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();		
	}
	//选择页查询
	function ExePageFlag(pageflag){		
		//
		var flag = "1";//首页标识
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//首页
			if(sqlselpage==1){
				//	    
				DisplayResult("已经是首页!!!"); 		   
			    //   
				//alert("已经是首页!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//上页
			if(sqlselpage==1){
				//		    
				DisplayResult("已经是首页!!!"); 		   
			    //  
				//alert("已经是首页!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//下页
			if(sqlselpage==sqlpagecount){
				//
				DisplayResult("已经是尾页!!!"); 		   
			    // 
				//alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//页尾
			if(sqlselpage==sqlpagecount){
				//
				DisplayResult("已经是尾页!!!"); 		   
			    // 
				//alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlpagecount;			
		}
		
		//计算分页数据
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;		
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();		
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
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
	}
</script>
<script type="text/javascript">
	//页全部选中
	function CheckPage(){
		//页全部选中
		var ispagecb,iscb,isallcb;
		//
		//清空条件语句
		selsql = "";
		//
		isallcb = document.getElementById("checkallb");
		if(isallcb!=null){
			checkallb.checked = false;
		}
		//
		ispagecb = document.getElementById("checkpageb");
		if(ispagecb.checked==0){
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				if(iscb.disabled==1){					
					//已经选中
				}else{
					iscb.checked = 0;
				}				
			}			
		}else if(ispagecb.checked==1){			
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				if(iscb.disabled==1){					
					//已经选中
				}else{
					iscb.checked = 1;
				}					
			}								
		}		
	}
	//全部选中
	function CheckAll(){
		//全部选中
		var isallcb;
		//
		checkpageb.checked = false;
		//
		isallcb = document.getElementById("checkallb");
		if(isallcb.checked==1){	
			//取得物理查询语句且执行返回XML格式		  
			GetPhySql();
			//
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				iscb.checked = 1;					
			}
		}else{
			//清空条件语句
			selsql = "";
			//
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				iscb.checked = 0;					
			}
		}	
	}
	//单个审批操作
	function CheckNow(src,fmid){
		//单个审批
		if(src.checked==1){
			//单个家庭ID
			selallfmid = fmid;				
			//打开审批意见窗体			
	 		CallCheckIdeaDialog("add",selallfmid);			
		}
	}
	//选中批量审批
	function CheckAllNow(){
		//批量审批
		var iscb,fmrow,fmid,fmno,fmname,fmolemoney,fmmoney;
		
		//终止选择
		selallfmid = "";
		//
		for(var i=0;i<rownum;i++){
			iscb = document.getElementById("checkb"+i);							
			if(iscb.checked == 1){				
				if(iscb.disabled==1){					
					//已经审批
				}else{												
					//多个选择家庭ID
					fmrow = xmldata.item(i).childNodes;						
					fmid = fmrow.item(0).firstChild.data;//第一列为家庭ID
					fmno = fmrow.item(1).firstChild.data;//家庭编号
					fmname = fmrow.item(2).firstChild.data;//家庭户主姓名
					fmolemoney = fmrow.item(5).firstChild.data;//家庭上期救助金
					fmmoney = fmrow.item(6).firstChild.data;//家庭拟发救助金
					//
					if(selallfmid=="" || selallfmid == null ){
						selallfmid = fmid;
					}else{
						selallfmid +=  "," + fmid;
					}	
				}	
			}				
		}		
		//
		//打开审批意见窗体		
		CallCheckIdeaDialog("add",selallfmid);					
	}
</script>
<script type="text/javascript">	
	//打开审批意见窗体
	function CallCheckIdeaDialog(mode,fmid)
	{
		var pid,isallcb,mess,smode;
		//
		pid= selpolicy;
		smode = "edit";
		//
		if(mode=="add"){
			//全选
			isallcb = document.getElementById("checkallb");
			if(isallcb==null){
		    	if(pid=="" || fmid ==""){		
			    	DisplayResult("没有选择相关家庭!");	
					return;	
				}
				mess = "";
		    	selsql="";
				smode = "add";
			}else if(isallcb.checked== 1){
				//
				if(oneflowflag=="1"){//第一级审批机构标识
					if(checkmoneyflag=="1"){//用户可修改救助金标识
						DisplayResult("单户填写救助金,不允许全部审批!");	
						return;	
		    		}
				}
		    	if(selsql==""){
		    		DisplayResult("没有选择相关家庭!");	
					return;	
		    	}
		    	mess = "[全选]";
		    	smode = "all";		    							    	
		    }else{
		    	if(pid=="" || fmid ==""){		
			    	DisplayResult("没有选择相关家庭!");	
					return;	
				}
				mess = "";
		    	selsql="";
				smode = "add";
		    }
		   
		}
		//特殊字符转换传递(以便页面接收)
		selsql = encodeURIComponent(selsql);		
		//
		var bWidth = document.body.clientWidth-30;  //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bHeight= 400; //背景高度
		var sArgu = "qmode="+smode+"&qpid="+selpolicy+"&qfmid="+fmid+"";
		sArgu += "&qselsql="+selsql+"&qcheckmode="+selchecktabid;		
		if(acctypeflag=="1"){		
			var oUrl = "/db/page/policy/manage/policycheckideamember.jsp?"+sArgu+"";
			jBox.open("iframe-jBoxID","iframe",oUrl,"成员审批意见填写管理&GetPhySql()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}else{
			var oUrl = "/db/page/policy/manage/policycheckideafamily.jsp?"+sArgu+"";
			jBox.open("iframe-jBoxID","iframe",oUrl,"家庭审批意见填写管理&GetPhySql()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
		//清空条件语句
		selsql = "";			
	}
	//打开业务档次标准窗体
	function CallStandardsDialog()
	{
		if(selpolicy=="-1"){//[无]选择业务		  		
	  		return;		  		
	  	}
		var bWidth = document.body.clientWidth-30;   //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bHeight= 400; //背景高度
		var sArgu = "qpid="+selpolicy+"";
		var oUrl = "/db/page/policy/manage/policystandards.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务档次标准明细","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		
	}
	//打开走访记录查询页面
	function CallInterviewDialog(fmid){		
		var afrom = "",awhere = "",aorder = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var sArgu = "qfrom="+afrom+"&qwhere="+awhere+"&qorder="+aorder+"";
		var oUrl = "/db/page/policy/manage/policyinterview.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭走访记录登记","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}	
	//打开家庭查看页面
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息卡片","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//	
	//打开家庭编辑页面
	function infoeditaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息维护&AfterInfoEditAction("+afmid+")","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开终审结算窗体
	function CallAllAccDialog(mode)
	{
		if(selpolicy=="-1"){//[无]选择业务		  		
	  		return;		  		
	  	}
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var sArgu = "qpid="+selpolicy+"&qpname="+selpolicyname+"&qmode="+mode+"";
		var oUrl = "/db/page/policy/manage/policyaccmanage.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务终审结算管理&GetPhySql()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开编辑家庭信息之后执行处理函数
	function AfterInfoEditAction(fmid)
	{
		//计算单个家庭或者成员拟发救助金
    	UpdatePolciyMatchOne(fmid);
	}
	//		
</script>
<script type="text/javascript">
	//取得业务审批状态标识
	function GetCheckPolicyFlagsXml(){
	  	//
	    $.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
	        {
	            action: "getCheckPolicyFlagsXml", //action参数
	            pid: selpolicy, //参数
	            empid: empid                              
	        },
	        function(result) {   //回调函数
	        	//处理业务审批状态标识
	          	ShowFlagData(result);
	        }	          	
	    );        
	}
	//处理业务审批状态标识
	function ShowFlagData(data){
		var xmlDoc,tempArr;
		
		//		
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//终审结算标识
			tempArr = root.selectNodes("/data/allaccflag/flag");
			allaccflag = tempArr.item(0).firstChild.data;
			//审批流程标识
			tempArr = root.selectNodes("/data/flowflag/flag");
			flowflag = tempArr.item(0).firstChild.data;
			//批量审批标识
			tempArr = root.selectNodes("/data/checkmoreflag/flag");
			checkmoreflag = tempArr.item(0).firstChild.data;
			//第一级审批机构标识
			tempArr = root.selectNodes("/data/oneflowflag/flag");
			oneflowflag = tempArr.item(0).firstChild.data;
			//最后级审批机构标识
			tempArr = root.selectNodes("/data/endflowflag/flag");
			endflowflag = tempArr.item(0).firstChild.data;
			//岗位审批标识
			tempArr = root.selectNodes("/data/pcheckflag/flag");
			pcheckflag = tempArr.item(0).firstChild.data;
			//岗位批量审批标识
			tempArr = root.selectNodes("/data/pcheckmoreflag/flag");
			pcheckmoreflag = tempArr.item(0).firstChild.data;
			//岗位确认审批标识
			tempArr = root.selectNodes("/data/preportflag/flag");
			preportflag = tempArr.item(0).firstChild.data;
			//用户可修改救助金标识
			tempArr = root.selectNodes("/data/checkmoneyflag/flag");
			checkmoneyflag = tempArr.item(0).firstChild.data;
			//核算类型标识
			tempArr = root.selectNodes("/data/acctypeflag/flag");
			acctypeflag = tempArr.item(0).firstChild.data;
		}else{
			//
			
		}
		//取得业务所有状态后开始处理
		//取得页面排序下拉框
		GetCheckPolicyOrder("spanorderby","orderbylist");
		//
	    spancheckimg.innerHTML="<img src='/db/page/images/right.gif'/>";	     
        //取得业务审批标准
		GetPolicyAutoUlLi("choicepolicyauto","autolists",selautomode);	
	}
</script>	
<script type="text/javascript">
	//打开导出处理
	function ExportXls(){
		//导出数据标识(0不1导出数据)
		selexport = "1";
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
	}
	//获取xmlth和sql用;号隔开
	function ExportXlsOpen(data){
		//
		if(data=="1"){
 			window.open("<%=path%>/page/system/exportfile/exportExcel.do","","height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
 		}
		//导出数据标识(0不1导出数据)
		selexport = "0";
	}	
</script>
