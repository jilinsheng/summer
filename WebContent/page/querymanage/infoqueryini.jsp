<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session
			.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>

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

		<title>查询选项设置</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="/db/page/js/jquery.js"></script>
		<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
		<script type="text/javascript" src="/db/page/js/seqsort.js"></script>

	<style type="text/css">
		#movableNode {
			position: absolute;
		}
		/* 指示箭头的样式 */
		#arrDestInditcator {
			position: absolute;
			display: none;
			width: 100px;
		}
		/* 列表整体样式 */
		#arrangableNodes,#movableNode ul {
			padding-left: 0px;
			margin-left: 0px;
			margin-top: 0px;
			padding-top: 0px;
		}
		/* 列表项样式 */
		#arrangableNodes li,#movableNode li {
			list-style-type: none;
			cursor: default;
		}
		body {
			margin: 3;
			margin-top: 0;
			padding: 0;
			background: #FCDAD5;
			font-family: "宋体";
			font-size: 12px;
		}		
		.tableseq th {
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
		.pointer {
			cursor: pointer;
		}
		.caption {
			font-size: 12px;
			color: #660099;
		}
		.itemstyle {
			font-size: 12px;
			color: #6BA6FF;
		}
		.sqltable {
			color: #6BA6FF;
			font-size: 12px;
		}
		.bordercss {
			border-top: 1px solid #43ACB2;
			border-left: 1px solid #43ACB2;
			border-bottom: 1px solid #43ACB2;
			border-right: 1px solid #43ACB2;			
		}
		.licss {
			border-top: 1px solid buttonhighlight; 
			border-left: 1px solid buttonhighlight;  
			border-bottom: 1px solid buttonshadow;  
			border-right: 1px solid buttonshadow; 
			padding: 0;  
			cursor: hand;			  			
		}		
		#divqueryseq {
			border-top: 1px solid #43ACB2;
			border-left: 1px solid #43ACB2;
			border-bottom: 1px solid #43ACB2;
			border-right: 1px solid #43ACB2;
			font-size: 12px;
			height: 400px;				
		}		
		#infotree {
			border-top: 1px solid #43ACB2;
			border-left: 1px solid #43ACB2;
			border-bottom: 1px solid #43ACB2;
			border-right: 1px solid #43ACB2;
			width: 100%;
			height: 200px;
			overflow: scroll;
			font-size: 12px;
		}
		#sqlsexp {
			width: 100%;
			font-size: 12px;
		}
		#resultdiv{
			position:absolute;
			left:60%; 
			top:30%;
			z-index:2;
			background-color: #FFCCCC;
			color: #FF0099;
			font-size:12px;
			display:none;
		}
		#pagestatusdiv{
			position:absolute;
			left:60%; 
			top:30%;			
			z-index:2;
			background-color: #FFCCCC;
			font-weight: bold;
			color: #FF0099;
			font-size:14px;
			display:none;
		}
	</style>
	<script type="text/javascript"> 
  		var empid = ""; 	//登录用户编号	
  		var qmode = "";        //当前查询窗体模式  		
  		  
  		var selfieldtype = "";        //选择字段新类型		
  		
		//取得查询设置列表
		function GetQuerySeq(pardivid,empid){
		  	//
		  	var pardiv = document.getElementById(pardivid);
		  	
		    $.post("page/querymanage/QueryManageServlet",      //服务器页面地址
		        {
		            action: "getQueryExpSeq", //action参数
		            empid: empid //参数	                                          
		        },
		        function(result) {   //回调函数		          
		          pardiv.innerHTML = result;
		          //初始化拖拽函数
		    	  initArrangableNodes();		          		          		                                                  
		        }
		    );       
		}
		
		//保存查询显示顺序
		function saveQuerySeq(){
		    // 
		    if (!confirm("确定保存？")) {		    	
		        return;
		    }     
		    var nodes = document.getElementById("arrangableNodes").getElementsByTagName('li');
		    var seqnew = "";
		    for(var i=1; i<=nodes.length; i++){
		        if (i > 1) {
		            seqnew = seqnew + ",";
		        }
		        seqnew = seqnew + nodes[i-1].id + "_" + i;
		    }
		  
		    $.post("page/querymanage/QueryManageServlet",      //服务器页面地址
		        {
		            action: "saveQuerySeq", //action参数
		            seqnew: seqnew, //参数   
		            empid: empid //参数                           
		        },
		        function(result) {   //回调函数
		        	$("#resultdiv").css({"left":"5%","top":"10%"});
			        DisplayResult(result);              //显示操作结果
		            //取得查询设置列表
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );        
		}
		
		//删除所有查询设置
		function delAllQuerySeq(parid){
			if (!confirm("确定删除全部用户设置？")) {		    	
		        return;
		    }
		    $.post("page/querymanage/QueryManageServlet",      //服务器页面地址
		        {
		            action: "delAllQuerySeq", //action参数
		            parid: parid, //参数   
		            empid: empid //参数                              
		        },
		        function(result) {   //回调函数
		        	$("#resultdiv").css({"left":"5%","top":"10%"});
			        DisplayResult(result);              //显示操作结果
		            //取得查询设置列表
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );  			
		}
		//删除查询设置
		function delQuerySeq(id){
			if (!confirm("确定删除用户设置？")) {		    	
		        return;
		    }
			$.post("page/querymanage/QueryManageServlet",      //服务器页面地址
		        {
		            action: "delQuerySeq", //action参数
		            id: id, //参数   
		            empid: empid //参数                                  
		        },
		        function(result) {   //回调函数
		        	$("#resultdiv").css({"left":"5%","top":"10%"});
			        DisplayResult(result);              //显示操作结果
		            //取得查询设置列表
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );  
		}
		//添加查询设置
		function addQuerySeq(){			
			var ttype = "";
			var tinfo = "";
			var texp = "";
			
		    ttype = selfieldtype;
		    tinfo = $("#inputinfo").val();
		    texp = $("#inputexp").val();
		    if(tinfo.length<0 || texp.length<0 || tinfo =="" || texp ==""){
		    	return;	
		    } 
		    if (!confirm("确定添加？")) {		    	
		        return;
		    }
			$.post("page/querymanage/QueryManageServlet",      //服务器页面地址
		        {
		            action: "addQuerySeq", //action参数
		            empid: empid, //参数            
		            tinfo: tinfo,
		            texp: texp,
		            ttype: ttype                             
		        },
		        function(result) {   //回调函数
		        	$("#resultdiv").css({"left":"55%","top":"10%"});
			        DisplayResult(result);              //显示操作结果
		            //取得查询设置列表
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );
		     
		}
		     
		//显示提示信息div
		function DisplayResult(info) {
		    var resultdiv = $("#resultdiv");            //获取提示信息div
		    resultdiv.html(info);                       //设置div内文本		    
		    $(resultdiv).fadeIn();                      //淡入信息
		    setTimeout("HiddenResult()",2000);          //2秒后隐藏信息
		}		
		//隐藏提示信息div
		function HiddenResult() {
		    $("#resultdiv").fadeOut();                  //淡出信息
		}
		//显示页面状态div
		function DisplayPageStatus(info) {
		    var pagestatusdiv = $("#pagestatusdiv");            //获取提示信息div
		    pagestatusdiv.html(info);                       //设置div内文本		    
		    $(pagestatusdiv).fadeIn();                      //淡入信息		    
		}		
		//隐藏页面状态div
		function HiddenPageStatus() {
		    $("#pagestatusdiv").fadeOut();                  //淡出信息
		}
		
		//取得基本家庭信息项
	    function GetInfoTree(){
	      //创建基本家庭信息项
	      var ulr = "page/info/search/TableTreeServlet";
	      loadrootTree('infotree',ulr,'infotree','root');  
	    }
	    //取得运算符下拉框
	    function GetExpSelect(tfieldtype){
	      //
	        $.post("page/info/search/TableInfoServlet",      //服务器页面地址
	            {
	                action: "getexpselect" , //action参数 
	                fieldtype: tfieldtype                                 
	            },
	            function(result) {   //回调函数
	              divexp.innerHTML = result;                           
	            }
	        );        
	    }
		//初始化页面
	    function IniPage(){	
	    	$("#pagestatusdiv").css({"left":"55%","top":"10%"});
	    	DisplayPageStatus("加载数据,请稍后...");    	   	
	    	//当前登录用户编号    
		    empid = <%=empno%>;
		    
		    /*	
		    //查询窗体模式
		    var myobj = window.dialogArguments;
	    	qmode = myobj[0];
	    	*/	    		    		 		    
	    	//取得查询设置列表
			GetQuerySeq("divqueryseq",empid);
			//基本家庭信息项
      		GetInfoTree();
      		//运算符
      		GetExpSelect("-5");
      		//		
			
      		//加载数据完毕
      		HiddenPageStatus();
	    }
	</script>
	</head>

	<body onload="IniPage()">
		<div id='resultdiv'></div>
		<div id='pagestatusdiv'></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"class="tableseq">
			<tr>
				<td width="40%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"class="tableseq">
						<tr>
							<td valign="middle">
								<span class='itemstyle'>家庭信息项</span>
								<div id="infotree" align="left"></div>
							</td>
						</tr>
						<tr>
							<td valign="middle">
								<span class='itemstyle'>运算符</span>
								<div id="divexp" align="left"></div>
							</td>
						</tr>
						<tr>
							<td width="100%" valign="top">
								<span class='itemstyle'>信息项:</span>
								<input id='inputinfo' type="text" style='width: 100%'readonly="readonly"></input>
							</td>
						</tr>
						<tr>
							<td width="100%" valign="top">
								<span class='itemstyle'>运算符:</span>
								<input id='inputexp' type="text" style='width: 100%'readonly="readonly"></input>
							</td>
						</tr>
						<tr>
							<td valign="middle">
								<div align="center" class = 'bordercss'>
									<input type="button" onclick="addQuerySeq()" value="添加信息查询设置"></input>
								</div>
							</td>
						</tr>
			
					</table>	
				</td>
				<td width="60%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"class="tableseq">
						<tr>
							<th colspan = "1">查询选项设置列表</th>							
							<td width="100%" valign="top">
								<span class='caption'>查询选项设置列表</span>
								<div id="divqueryseq"></div>
							</td>
						</tr>						
					</table>					
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
			//选择字段新类型
			selfieldtype = 	tfieldmode;		
			//机构
           	if(tfieldmode=="-4"){
           		$("#inputinfo").val(tfullname);	
           		$("#inputexp").val("左匹配");							
           	}
           	//日期型
           	else if(tfieldmode=="-3"){           		
		    	$("#inputinfo").val(tfullname);		    	         		
           	}
           	//数值型
           	else if(tfieldmode=="-2"){           						
           		$("#inputinfo").val(tfullname);
           	}
           	//整型
           	else if(tfieldmode=="-1"){
           		$("#inputinfo").val(tfullname);
           	}
           	//字符型
           	else if(tfieldmode=="0"){           		
           		$("#inputinfo").val(tfullname);
           	}
           	//字典值	
           	else{
           		$("#inputinfo").val(tfullname);
           		$("#inputexp").val("等于");							
           	}
           	//运算符
			GetExpSelect(tfieldmode);					
		}
		//扩子表[TableTreeServlet成生的方法]
		function addchild(tid){
			//alert(tid);	
		}
		//扩字段[TableTreeServlet成生的方法]
		function addfield(tid){
			//alert(tid);	
		}
		//选择查询标准
		function selectclickexp(){
			sexp = sqlsexp.options[sqlsexp.selectedIndex].text;
			$("#inputexp").val(sexp);
		} 
		//=================================选择查询操作END==============================
</script>