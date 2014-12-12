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
    
    <title>分类保障管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
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
	</style>
	<script type="text/javascript">	
		//
		var empid = "";        //当前登录用户编号 
		var deptid = "";        //当前登录机构
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		selid = "";//选中ID
		selname = "";//选中名称
		selmoneytype = "";//选中核算设置方式
		//
		selsqlid = "";//选中标准ID
		selsqlname = "";//选中标准名称
		//
		//取得分类施保列表
		function GetPolicyChildsHtml() {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicyChildsHtml"             //action参数
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	policychildlists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>分类施保属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	policychilditems.innerHTML = html;
			    	
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
		//取得分类施保属性
		function GetPolicyChildItemHtml(sid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicyChildItemHtml",             //action参数
		            sid: sid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	policychilditems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//更新分类施保状态
		function UpdatePoilcyChildStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "是否确定停用该分类施保";
			}else{
				mess = "是否确定启用该分类施保";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "updatePoilcyChildStatus",             //action参数
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);	
					//
					//取得分类施保列表
					GetPolicyChildsHtml();						  		
		        }
		    );
		}
		//保存操作
		function SavePolicyChild(mode){
			var cpid,cpropid,cname,cdesc,csqltype,cmoneytype,cstatus;
			var mess = "";
			//
			if(mode=="add"){//新增
				mess = "是否确定保存新增分类施保";
			}else if(mode=="edit"){//编辑
				if(selid==""){
					mess = "没有选中分类施保,无法保存";
					return;
				}
				mess = "是否确定保存更新分类施保";
			}
			//
    		//各属性值
	    	cname = $("#cname").val();
	    	cpropid = $("#policyselect").val();//业务所属下拉框
	    	cdesc = $("#cdesc").val();	    	
	    	//选择家庭或者成员
		    var isrdf = document.getElementById("rdf");
		    var isrdm = document.getElementById("rdm");
		    if(isrdf.checked == true){	    		
	      	 	csqltype = "1";             
	      	}else if(isrdm.checked == true){
	      		csqltype = "2";   
	      	}
	      	//核算方式1分档次2百分比3范围值
		    cmoneytype = "1";   
	      	//
    		if(cname==""){
    			//显示提示信息div
				DisplayResult("没有填写名称!");
				return;
    		}		
			//
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "savePolicyChild",             //action参数
		            mode: mode,
		            cpid: selid,
		            cname: cname,
		            cpropid: cpropid,
		            cdesc: cdesc,
		            csqltype: csqltype,
		            cmoneytype: cmoneytype       
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);
					//
					//取得分类施保列表
					GetPolicyChildsHtml();				  		
		        }
		    );
		}
		//上下步页面控制
		function GoMatchPage(mode){
			if(mode=="next"){
				$("#policychildtr").css({"display":"none"});
				$("#policychildsqltr").css({"display":"block"});
				//取得分类施保列表
				GetPolicyChildSqlsHtml();
				//
			}else if(mode=="back"){
				$("#policychildtr").css({"display":"block"});
				$("#policychildsqltr").css({"display":"none"});
			}
		}
				
		//取得分类施保标准列表
		function GetPolicyChildSqlsHtml() {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicyChildSqlsHtml",             //action参数
		            sid: selid,
		            sname: selname
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	policychildsqllists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>分类施保标准属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	policychildsqlitems.innerHTML = html;
			    	
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
		//取得分类施保标准属性
		function GetPolicyChildSqlItemHtml(sid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicyChildSqlItemHtml",             //action参数
		            sid: sid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	policychildsqlitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//更新分类施保标准状态
		function UpdatePoilcyChildSqlStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "是否确定停用该分类施保标准";
			}else{
				mess = "是否确定启用该分类施保标准";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "updatePoilcyChildSqlStatus",             //action参数
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);	
					//
					//取得分类施保标准列表
					GetPolicyChildSqlsHtml(selid);						  		
		        }
		    );
		}
		//保存操作
		function SavePolicyChildSql(mode){
			var vsid,vprosid,vname,vdesc,vstatus;
			var mess = "";
			//
			if(mode=="add"){//新增
				mess = "是否确定保存新增分类施保标准";
			}else if(mode=="edit"){//编辑
				if(selsqlid==""){
					mess = "没有选中分类施保标准,无法保存";
					return;
				}
				mess = "是否确定保存更新分类施保标准";
			}
			//
    		//各属性值
	    	vname = $("#vname").val();
	    	vdesc = $("#vdesc").val();	    	
	    	
    		if(vname==""){
    			//显示提示信息div
				DisplayResult("没有填写标准名称!");
				return;
    		}		
			//
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "savePolicyChildSql",             //action参数
		            mode: mode,
		            vsid: selsqlid,
		            vname: vname,
		            vprosid: selid,
		            vdesc: vdesc      
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);
					//
					//取得分类施保标准列表
					GetPolicyChildSqlsHtml(selid);				  		
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
			//取得分类施保列表
			GetPolicyChildsHtml();			
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">  		
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center" id = "policychildtr">
	    		<td valign="top" width="50%"><div id = 'policychildlists'></div></td>	    		
	    		<td valign="top"><div id = 'policychilditems'></div></td>
	    	</tr>
	    	<tr align="center" id = "policychildsqltr">
	    		<td valign="top" width="50%"><div id = 'policychildsqllists'></div></td>	    		
	    		<td valign="top"><div id = 'policychildsqlitems'></div></td>
	    	</tr>
    	</table>  
  </body>
</html>
<script type='text/javascript'>
	//单个操作
	function ChioceDo(src,id,name,type){
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
		selmoneytype = type;
		//
		//取得分类施保属性
		GetPolicyChildItemHtml(selid);
	}
	//单个操作
	function ChioceDoSql(src,id,name){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		selsqlid = id;
		selsqlname = name;
		//
		//取得分类施保标准属性
		GetPolicyChildSqlItemHtml(selsqlid);
	}
	//打开分类保障标准设置页面
	function sqlaction(){
		var qmode = ""; 
		//
		if(selid==""){
			DisplayResult("没有选择分类保障,无法设置条件!");
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
		var sArgu = "qid="+selsqlid+"&qname="+selsqlname+"&qmode="+qmode+"";		
		var oUrl = "/db/page/policy/manage/policymanagechildsql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"分类保障标准设置","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
