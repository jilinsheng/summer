<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	//接收字典ID
	String reqid = request.getParameter("qid");		
	if (reqid == null) {
		//默认为空
		reqid = "";    //空
	}	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>字典属性管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>	
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	<style type="text/css">	
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
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		var discid = "";//ID
		//
		var discchildid = "";//字典属性ID
		//
		//取得字典属性列表
		function GetDiscsHtml() {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //服务器页面地址
		        {
		            action: "getDiscsHtml",             //action参数
		            discid: discid
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	disclists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>字典属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	discitems.innerHTML = html;
			    	
			    	//JS表格排序
					new TableSorter("liststb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					//
					//取得字典属性
					GetDiscItemHtml(discchildid);				    					      		
		        }
		    );
		}
		//取得字典属性
		function GetDiscItemHtml(sid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //服务器页面地址
		        {
		            action: "getDiscItemHtml",             //action参数
		            sid: sid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	discitems.innerHTML = result;
			    	$("#dname").get()[0].focus(); 	       		
		        }
		    );
		}
		//更新字典类型
		function SaveDisc(mode){
			//编辑状态
			var EditType = mode;
			
			//属性			
			var pdid = "",did = "",dname = "";
			
			//
			pdid = discid;	
		    //		    		    
		    dname = $("#dname").val();
		    //为空校验
		    if(dname==""){		     
			  DisplayResult("名称不能为空!"); //显示操作结果
		      return;
		    }		    
		    //
		    if(EditType=="adddisc"){		    	
		    	did = "";					   //获取编号	
		    	//确认
			    if (!confirm("是否确定新增字典属性？")) {
			        return;
			    }			    
		    }else if(EditType=="editdisc"){
		    	did = $("#did").val();
			    if(did==""){			      
				  DisplayResult("没有选择字典类型,无法更新!"); //显示操作结果
			      return;
			    }
		    	//确认
			    if (!confirm("是否确定更新["+dname+"]属性？")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }		    
		    //
		  	DisplayPageStatus();
		  	//
		    $.post("page/info/search/TableInfoServlet", //服务器目标页面
		        {
		            action: "updateDisc",                  //action参数
		            EditType: EditType,				 //参数
		            did: did,                      //列表id参数
		            dname: dname,				 //参数
		            pdid: pdid			            
		        },
		        function(result) {                      //回调函数
		        	HiddenPageStatus();
		            DisplayResult(result);              //显示操作结果
		            //取得字典属性列表
					GetDiscsHtml(); 
		        }
		    );
		}
		//更新标准状态
		function updateDiscStatus(Id,Name,Status) {
		    //
			var smse = "";
			if(Status=="0"){
				smse = "停用";
			}else{
				smse = "启用";
			}		    
		    if (Id != "") {		    	
		    	//停用前确认
			    if (!confirm("是否确定"+smse+"["+Name+"]该字典状态？")) {
			        return;
			    }
			    //
			  	DisplayPageStatus();
			  	//
			    $.post("page/info/search/TableInfoServlet",//服务器目标页面
			        {
			            action: "updateDiscStatus",//action参数
			            did: Id,                      //列表id参数
			            status: Status				 //参数
			        },
			        function(result) {                      //回调函数
			        	HiddenPageStatus();	 			            
			            DisplayResult(result);              //显示操作结果
			            //取得字典属性列表
						GetDiscsHtml();			            			            		            
			        }
			    );
		    } else {		        
		        DisplayResult("没有选择字典,无法更新!"); //显示操作结果		       
		    }
		}
		//单个操作
		function ChioceDo(src,id){
			//
			var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "img"){
					imgs[i].src="/db/page/images/check1.jpg";
				}
			}
			src.src="/db/page/images/check2.jpg";
			discchildid = id;
			//
			//取得字典属性
			GetDiscItemHtml(discchildid);	
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
			//字典ID    
			discid = "<%=reqid%>"; 
			//
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			//取得字典属性列表
			GetDiscsHtml();			
		}		
	</script>	
  </head>
  
  <body onLoad="IniPage()">
    <div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  	<div id='resultstatusdiv'></div>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
    	<tr align="center" id = "disctr">
    		<td valign="top" width="50%"><div id = 'disclists'></div></td>	    		
    		<td valign="top"><div id = 'discitems'></div></td>
    	</tr>    		
   	</table>
  </body>
</html>
