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
	String reqpid = request.getParameter("qpid");
	//
	String reqfmids = request.getParameter("qfmids");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>家庭审批流程</title>
    
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
	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
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
  	var pid = "";		//当前业务ID
  	//
	var fmids = "";		//家庭编号ID
  	//

	//	
	
	//获取查询XML
	function getIdeaFlowForXml(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "getIdeaFlowForXml",            //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: pid,
	            jfmids: fmids
	            
	        },
	        function(result) {                    	//回调函数
	        	ideaflowtb.innerHTML = result; 	        	
	        }
	    );
	}
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		pid =  "<%=reqpid%>";		 			//当前业务ID
		//	
		fmids = "<%=reqfmids%>";		 		//查询家庭ID
		//
		//获取查询XML
		getIdeaFlowForXml();
	}
  </script>
  <body  onload = "IniPage()">
    	<fieldset><legend>审批流程信息</legend>
	  		<div id = "ideaflowtb"></div>
	  	</fieldset>
  </body>
</html>
<script type="text/javascript">
	//打开审批流程详细页面
	function GetCheckIdeaFlowDesr(jpid,optdepth,optid){
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		var sArgu = "qpid="+jpid+"&qoptdepth="+optdepth+"&qoptid="+optid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideaflowdesr.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭审批流程详细","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>