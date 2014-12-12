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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>基本信息分类管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page"><meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
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
		var empid = "";        //当前登录用户编号 
		var deptid = "";       //当前登录机构
		//
		var selid = "";	//选中ID
		var selname = "";//选中名称
		var selfid = "";//选中分类字段ID
		
		//获取分类列表
		function GetClasssHtml() {
			var mode;			
			if (rdf.checked == true){
				//家庭
				mode = "family";			
							
			}else{
				//成员
				mode = "member";	
			}
			//显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",      //服务器页面地址
		        {
		            action: "getClasssHtml",                  //action参数
		            mode: mode				   //参数                                          
		        },
		        function(result) {   //回调函数	
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
					classlists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>分类属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	classitems.innerHTML = html;
			    	
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
		        }
		    );    
		}
		//取得分类属性
		function GetClassItemHtml(sid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //服务器页面地址
		        {
		            action: "getClassItemHtml",             //action参数
		            sid: sid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	classitems.innerHTML = result;			    	 	       		
		        }
		    );
		}
		//停用分类
		function delClass(classId,classFId){
		    //删除前确认		    
		    if (!confirm("确定停用该分类？")) {
		        return;
		    }
		    //显示页面状态div
			DisplayPageStatus();	
			//	    
		    $.post("page/info/search/TableInfoServlet",                   //服务器目标页面
		        {
		            action: "delClassType",                  //action参数
		            classId: classId,                      //id参数
		            classFId: classFId
		        },
		        function(result) {                      //回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
		        	DisplayResult(result);
		        	//获取分类列表
					GetClasssHtml();
									 
		        }
		    );
		}
		//启用分类
		function reeditClass(classId,classFId){
		    //删除前确认
		    if (!confirm("确定启用该分类？")) {
		        return;
		    }	
		    //显示页面状态div
			DisplayPageStatus();	
			//    
		    $.post("page/info/search/TableInfoServlet",                   //服务器目标页面
		        {
		            action: "reeditClass",                  //action参数
		            classId: classId,                      //id参数
		            classFId: classFId
		        },
		        function(result) {                      //回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
		        	DisplayResult(result);
		        	//获取分类列表
					GetClasssHtml();
		        }
		    );
		}		
		//新增分类
		function SaveClass(mode){
			var classType,classId,classFId,className,classDesc;			
			if (rdf.checked == true){
				//家庭
				classType = "family";
			}else{
				//成员
				classType = "member";
			}
			className = $("#cname").val();
			classDesc = $("#cdesc").val();	
			//
			if (className.length<=0){return;}	
			//
			if(mode=="addclass"){
				classId = "";			//获取编号 
				classFId = "";
		    	//确认
			    if (!confirm("是否确定新增新分类？")) {
			        return;
			    }	
			}else if(mode=="editclass"){
				classId = selid;     //获取编号	
				classFId = selfid;
			    if(classId==""){			     
				  DisplayResult("没有选择分类,无法更新!"); //显示操作结果
			      return;
			    }
		    	//确认
			    if (!confirm("是否确定更新分类属性？")) {
			        return;
			    }	
			}	
		   	//显示页面状态div
			DisplayPageStatus();
			//    
		    $.post("page/info/search/TableInfoServlet",                   //服务器目标页面
		        {
		            action: "updateClass",                  //action参数
		            mode: mode,
		            classId: classId,
		            classFId: classFId,
		            classType: classType,
		            className: className,                      //参数
		            classDesc: classDesc		            
		        },
		        function(result) {                      //回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
		        	DisplayResult(result);
		        	//获取分类列表
					GetClasssHtml();  
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
			//当前登录用户编号    
			empid = "<%=empno%>";     
			//登录机构    
			deptid = "<%=onno%>";    //当前登录机构 
			//
			//
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			//获取分类列表
			GetClasssHtml();		
		}		
	</script>
  </head>
  
  <body  onLoad="IniPage()">
    <div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  	<div id='resultstatusdiv'></div>
  	<div align="center" style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>
        <input type="radio" name="rdgresult" id="rdf" value="rdf" onclick='GetClasssHtml()' checked>家庭分类</input>	             	
	    <input type="radio" name="rdgresult" id="rdm" value="rdm" onclick='GetClasssHtml()'>成员分类</input>
    </div>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
    	<tr align="center" id = "classtr">
    		<td valign="top" width="50%"><div id = 'classlists'></div></td>	    		
    		<td valign="top"><div id = 'classitems'></div></td>
    	</tr>
    	  		
   	</table> 
  </body>
</html>
<script type="text/javascript">	
	//单个操作
	function ChioceDo(src,id,name,fid){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		selid = id;
		selname = name;
		selfid = fid;
		//
		//取得业务属性
		GetClassItemHtml(selid);
	}
	//刷新系统数据
	function refsysinfo(){
		//前确认		    
	    if (!confirm("确定刷新系统数据？")) {
	        return;
	    }	
	    //
	    DisplayPageStatus();
	    //	    
	    $.post("/db/page/common/Log4jServlet",        //服务器目标页面
	        {
	            action: "action"                  //action参数
	        },
	        function(result) {                      //回调函数
		        //加载数据完毕
	      		HiddenPageStatus();
	      		//
	        	DisplayResult("刷新系统数据完成");									 
	        }
	    );
	}
	//打开分类条件设置页面
	function sqlaction(){
		var qmode = ""; 
		//
		if(selid==""){
			DisplayResult("没有选择分类,无法设置条件!");
			return; 
		}			
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		//选择家庭或者成员
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    if(isrdf.checked == true){	    		
      	 	qmode = "family";             
      	}else if(isrdm.checked == true){
      		qmode = "member";    
      	}
		var sArgu = "qid="+selid+"&qname="+selname+"&qmode="+qmode+"";		
		var oUrl = "/db/page/info/classmanage/classmanagesql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"分类条件设置","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//	
</script>
