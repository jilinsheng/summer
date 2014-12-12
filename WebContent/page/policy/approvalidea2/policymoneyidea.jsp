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
	//
	String reqtabid = request.getParameter("qtabid");
	//
	String reqpid = request.getParameter("qpid");
	//
	String reqfmids = request.getParameter("qfmids");
	//
	String reqsqlcount = request.getParameter("qsqlcount");
	//
	String reqsumpopcount = request.getParameter("qsumpopcount");	
	//
	String reqsummoney= request.getParameter("qsummoney");	
	//
	String reqsumolemoney= request.getParameter("qsumolemoney");	
	//
	String reqsumnewmoney= request.getParameter("qsumnewmoney");
	//
	String reqaccmode= request.getParameter("qaccmode");
	//
	String reqonecheck= request.getParameter("qonecheck");	
	//
	String reqendcheck= request.getParameter("qendcheck");	
	//
	String reqreport= request.getParameter("qreport");	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>定额选中审批</title>
    
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
  	var tabid = "0";	//审批页标签ID
  	//
	var fmids = "";		//家庭编号ID
  	//
  	//	
  	var allsqlcount =0;
  	var allsumpopcount=0;
  	var allsummoney = 0;
	var allsumolemoney=0;
	var allsumnewmoney=0;
	//
	var accmode = "0";			//用户可以修改救助金标识
  	//
	var onecheck = "0";	//第一级审批机构
	var endcheck = "0";	//终审机构
	var report = "0";	//上报名单(确认名单标识)
	//	
  	var sqlcount = 0;	 		//总户数
	var sumpopcount = 0;		//总人口
	var summoney = 0;		 	//总收入
	var sumolemoney = 0;		//总上期救助金
	var sumnewmoney = 0;		//总拟发救助金
	//	
	//获取审批结果下拉选择框
	function getCheckIdeaSelect(){
		//
		var sname = "resultlist";
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "getCheckIdeaSelect",             //action参数
	            sname: sname,
	            stabid: tabid,
	            sonecheck: onecheck
	        },
	        function(result) {                    	//回调函数
	        	divresults.innerHTML = result;	        	
	        }
	    );
	}	
	//获取查询XML
	function getIdeaResultForXml(){
		//
		var taccmode = "0";	//设置为用户不可修改金额(获取名单)
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "getIdeaResultForXml",            //action参数
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jpid: pid,
	            jaccmode: taccmode,
	            jfmids: fmids
	            
	        },
	        function(result) {                    	//回调函数
	        	ShowData(result); 	        	
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
		tabid = "<%=reqtabid%>";		 		//审批标签ID	
		//	
		fmids = "<%=reqfmids%>";		 		//查询家庭ID
		//
		allsqlcount = "<%=reqsqlcount%>";	 		//总户数
		allsumpopcount = "<%=reqsumpopcount%>";	//总人口
		allsummoney = "<%=reqsummoney%>";		 	//总收入
		allsumolemoney = "<%=reqsumolemoney%>";	//总上期救助金
		allsumnewmoney = "<%=reqsumnewmoney%>";	//总拟发救助金
		//
		accmode = "<%=reqaccmode%>";			//用户可以修改救助金标识		
		//
		onecheck = "<%=reqonecheck%>";		 	//第一级审批机构
		endcheck =  "<%=reqendcheck%>";		 	//终审机构
		report = "<%=reqreport%>";		 		//上报名单(确认名单标识)			
		//
		//
		$("#btnok").get()[0].focus();
		//信息汇总栏
		IdeaInfoCount();		
		//获取审批结果下拉选择框
		getCheckIdeaSelect();	
		//获取系统日期
		getSysTimeFormatDate();
		//获取查询XML
		getIdeaResultForXml();		
	}
  </script>
  <body  onload = "IniPage()">
  		<fieldset><legend>汇总信息</legend>
	  		<div id = "ideainfocounttb"></div>
	  	</fieldset>
  		<fieldset><legend>审批意见</legend>    		
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'moreideatb'>
	  			<tr valign='middle' class='mybackground myspace'>	  				
	  				<td width = "120" align='center' valign='middle'>评议审批结果</td>
	  				<td align='left' valign='middle'>
	  					<div id = 'divresults'></div>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>评议审批日期</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'icheckdt' onFocus= 'setday(this)'></input>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>定额救助(元)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imoney' value = '0'></input>
	  				</td>
	  			</tr>
	  		</table>	  		
	  	</fieldset>
	  	<fieldset><legend>审批名单</legend>
	  		<div align='center' id = 'divideatb'></div>	  	
	  		<div id = "infocounttb"></div>
	  	</fieldset>
	  	<div align='center' id = 'divbtncheck' ><button class = 'btn' id = 'btnok' onclick="editCheckIdea()"> 确认评议审批 </button></div>
  </body>
</html>
<script  type="text/javascript">
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
	function IdeaInfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>";	
  			html += "<tr class='colField' style = 'border-top-width: 1px;'>";	
  				html += "<td height='23'>总家庭户数["+sqlcount+"]</td>";	
  				html += "<td height='23'>总保障人口["+sumpopcount+"]</td>";	
  				html += "<td height='23'>总计算收入["+ReplaceMoney(summoney)+"]</td>";	
  				html += "<td height='23'>总上期救助金["+ReplaceMoney(sumolemoney)+"]</td>";	
  				html += "<td height='23' style = 'color: #6BA6FF;'>总拟发救助金["+ReplaceMoney(sumnewmoney)+"]</td>";	
  			html += "</tr>";	
  		html += "</table>";			
		//		
		ideainfocounttb.innerHTML=html;
	}	
</script>
<script  type="text/javascript">
	//获取系统日期
	function getSysTimeFormatDate(){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "getSysTimeFormatDate"             //action参数
	        },
	        function(result) {                    	//回调函数
	        	$("#icheckdt").val(result);   	
	        }
	    );
	}
</script>
<script type="text/javascript">
	//审批确认
	function editCheckIdea(){
		//
		var jresult = $("#resultlist").val();
		var jcheckdt = $("#icheckdt").val();
		var jmoney = $("#imoney").val();
		//
		if(jmoney=="0"){return;}
		//
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "moneyCheckIdea",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jtabid: tabid,
	            jpid: pid,
	            jfmids: fmids,
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jreport: report,
	            jresult: jresult,
	            jcheckdt: jcheckdt,
	            jmoney: jmoney
	        },
	        function(result) {                    	//回调函数
	        	$("#divbtncheck").css({"display":"none"});
	        	alert(result);   	
	        }
	    );
	}
</script>
<script type="text/javascript">
	//
	//
	function ShowData(data){
		var xmlDoc;	
		var xtable;	
		//		
		sqlcount=0;		
		sumolemoney=0;
		sumnewmoney=0;		
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//总户数
			var countarr=root.selectNodes("/data/counts/sqlcount");
			sqlcount =countarr.item(0).firstChild.data;
			//上期救助金汇总			
			countarr=root.selectNodes("/data/counts/sumolemoney");
			sumolemoney =countarr.item(0).firstChild.data;			
			//拟发救助金汇总
			countarr=root.selectNodes("/data/counts/sumnewmoney");
			sumnewmoney =countarr.item(0).firstChild.data;
					
			//
			//审批名单表格
			var familyarr=root.selectNodes("/data/table/elements");
			xtable =familyarr.item(0).firstChild.data;
			divideatb.innerHTML=xtable;
			//
			//JS表格排序
			new TableSorter("ideatb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/
   						
		}else{	
			divideatb.innerHTML=data;
		}
		//信息汇总栏
		InfoCount();		
	}
	//信息汇总栏
	function InfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField' style = 'border-top-width: 1px;' >";				
				html += "<td height='23'>小计家庭户数["+sqlcount+"]</td>";
	  			html += "<td height='23'>小计上期救助金["+ReplaceMoney(sumolemoney)+"]</td>";	  			
	  			html += "<td height='23' style = 'color: #6BA6FF;'>小计拟发救助金["+ReplaceMoney(sumnewmoney)+"]</td>";
			html += "</tr>";
		html=html+"</table>";			
		//
		//			
		infocounttb.innerHTML=html;
	}
	//信息汇总栏
	function IdeaInfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>";	
  			html += "<tr class='colField' style = 'border-top-width: 1px;'>";	
  				html += "<td height='23'>总家庭户数["+allsqlcount+"]</td>";	
  				html += "<td height='23'>总保障人口["+allsumpopcount+"]</td>";	
  				html += "<td height='23'>总计算收入["+ReplaceMoney(allsummoney)+"]</td>";	
  				html += "<td height='23'>总上期救助金["+ReplaceMoney(allsumolemoney)+"]</td>";	
  				html += "<td height='23' style = 'color: #6BA6FF;'>总拟发救助金["+ReplaceMoney(allsumnewmoney)+"]</td>";	
  			html += "</tr>";	
  		html += "</table>";			
		//		
		ideainfocounttb.innerHTML=html;
	}	
</script>
