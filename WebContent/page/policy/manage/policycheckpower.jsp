<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.net.URLDecoder"%>
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
	//	
	//接收qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//默认为空
		reqpid = "-1";    //空业务编号
	}
	//接收qpname
	String reqpname= request.getParameter("qpname");
	reqpname = new String(reqpname.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqpname == null) {
		//默认为空
		reqpname = "";    //空业务名称
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务审批权限定义</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	
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
		.tab {
			width: 100%;		
			margin: 0 auto;
			overflow: hidden;				
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
	<script type="text/javascript">
		<!--
		//
		var empid = "";        //当前登录用户编号 
		var deptid = "";        //当前登录机构
		//
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		var policyid = "<%=reqpid%>";
		var policyname = "<%=reqpname%>";
		
		
		//获取业务审批角色权限列表
		function GetPolicyPostsPower() {
			//		  	
		    $.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
		        {
		            action: "getPolicyPostsPower",                  //action参数
		            pid: policyid,
		            empid: empid                                        
		        },
		        function(result) {   //回调函数	
		          	//角色列表定义审批权限
					ShowPostPower(result);		          			          		          	          		          		                                                  
		        }
		    ); 
		}
		//显示页面状态div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度   	
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
	    	//当前登录用户编号    
			empid = "<%=empno%>";     
			//登录机构    
			deptid = "<%=onno%>";    //当前登录机构 
			//
			vleft = document.body.clientWidth/2-100;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度  	
			//
	    	//获取业务审批角色权限列表
			GetPolicyPostsPower();
	    }
		-->
	</script>
	
  </head>
  
  <body onload="IniPage()">  	
	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  	<div id='resultstatusdiv'></div> 
  	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
    	<tr align="center">
    		<td valign="top" class = "myspace"><div id="policycon"> </div></td>	
    	</tr>		    		   				    	    	
   	</table>
  	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
    	<tr align="center">
    		<td valign="top" class = "myspace"><div id="postpowerscon"> </div></td>	
    	</tr>		    		   				    	    	
   	</table>
  </body>
</html>
<script  type="text/javascript">
	//角色列表定义审批权限
	function ShowPostPower(sdata){		
		var html,temp;
		var spostid,spostname,checkflag,checkmoreflag,reportflag;
		var checkflagid,checkmoreflagid,reportflagid;		
		//当前业务
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
		html += "<tr class='mybackground colValue'>";
			temp ="<td height='23'>当前业务["+policyname+"]用户岗位审批权限定义</td>";
			html +=temp;
		html +="</tr>";			
		//
    	html +="</table>";
    	//
    	policycon.innerHTML = html;
    	//
    	//当前角色权限
		html = "<table id = 'posttb' width='100%' cellpadding='0' cellspacing='0'>"
		html += "<tr class='colField'>";
			temp ="<td height='23'>岗位名称(用户岗位)</td>";
			html +=temp;
			temp ="<td height='23'>审批(允许)</td>";
			html +=temp;			
			temp ="<td height='23'>审批(批量)</td>";
			html +=temp;
			temp ="<td height='23'>审批(确认)</td>";
			html +=temp;
			//temp ="<td height='23'>操作</td>";
			//html +=temp;
		html +="</tr>";	
		//结果转换成JSONArray	        	
   		var json = eval(sdata);
		//         
        $(json).each(function(i) {      //遍历结果数组	
        	//
        	spostid = json[i].pid;
        	spostname = json[i].pname;
        	checkflag = json[i].pcheckflag;
        	checkmoreflag = json[i].pcheckmoreflag;
        	reportflag = json[i].preportflag;
        	//
        	checkflagid = "checkflag"+ spostid;
        	checkmoreflagid = "checkmoreflag"+ spostid;
        	reportflagid = "reportflag"+ spostid;
        	//        	
        	//
			html +="<tr>";		
	    	//各列值	
			temp="<td height='23' class='colValue'>"+spostname+"";
			html +=temp;
			temp="<input style='display:none;' type='text' id='postid'"+spostid+"></input></td>";
			html +=temp;
			if(checkflag=="0"){
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkflagid+"' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}else{
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkflagid+"' checked = 'checked' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}
			if(checkmoreflag=="0"){
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkmoreflagid+"' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}else{
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkmoreflagid+"' checked = 'checked' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}
			if(reportflag=="0"){
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+reportflagid+"' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}else{
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+reportflagid+"' checked = 'checked' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}
			//			
			//temp="<td height='23' class='colValue'><input id = 'btnok'"+spostid+" type='button' value='确定' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
			//html +=temp;	
	    	html +="</tr>";
        });		
    	//
    	html +="</table>";    	
    	//
    	postpowerscon.innerHTML = html;
    	//
		//JS表格排序
		new TableSorter("posttb");	
		/*
		new TableSorter("tb2", 0, 2 , 5, 6);
		new TableSorter("tb3").OnSorted = function(c, t)
		{
			alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
		}
		*/	
	}
	//设置业务审批角色权限列表
	function SetPolicyPostsPower(postid,checkflag,checkmoreflag,reportflag) {
		var stemp;
		var scheckflag = "0",scheckmoreflag = "0",sreportflag = "0";
		//	
		stemp = document.getElementById(checkflag);		
	    if(stemp.checked == false){
	      scheckflag = "0";
	    }else{
	      scheckflag = "1";
	    }
	    stemp = document.getElementById(checkmoreflag);	    
	    if(stemp.checked == false){
	      scheckmoreflag = "0";
	    }else{
	      scheckmoreflag = "1";
	    }
	    stemp = document.getElementById(reportflag);    
	    if(stemp.checked == false){
	      sreportflag = "0";
	    }else{
	      sreportflag = "1";
	    }
	    
	    //				  	
	    $.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
	        {
	            action: "setPolicyPostPowers",                  //action参数
	            pid: policyid,
	            postid: postid,
	            checkflag: scheckflag,
	            checkmoreflag: scheckmoreflag,
	            reportflag: sreportflag                                         
	        },
	        function(result) {   //回调函数	
	          	//显示操作结果		    		
			    DisplayResult(result);		   		          			          		          	          		          		                                                  
	        }
	    ); 
	}
</script>