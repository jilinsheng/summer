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
	String reqmode = request.getParameter("qmode");
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
    
    <title>评议审批</title>
    
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
  	var mode = "one";	//单户审批(one/more)
  	var pid = "";		//当前业务ID
  	var tabid = "0";	//审批页标签ID
  	var fmid = "";		//家庭ID
  	//	
  	var allsqlcount =0;
  	var allsumpopcount=0;
	var allsumolemoney=0;
	var allsumnewmoney=0;
	//
	var accmode = "0";	//用户可以修改救助金标识
	//
	var onecheck = "0";	//第一级审批机构
	var endcheck = "0";	//终审机构
	var report = "0";	//上报名单(确认名单标识)
	//	
  	var sqlcount =0;
	var sumolemoney=0;
	var sumnewmoney=0;
	//
	//获取致困原因复选
	function getRequestTypeCheckBox(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "getRequestTypeCheckBox"            //action参数
	        },
	        function(result) {                    	//回调函数
	        	divqtypes.innerHTML = result;	        	
	        }
	    );
	}
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
	//获取评议人复选
	function getRequestPersonCheckBox(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "getRequestPersonCheckBox",            //action参数
	            deptid: deptid
	        },
	        function(result) {                    	//回调函数
	        	divimans.innerHTML = result;	        	
	        }
	    );
	}
	//获取查询XML
	function getIdeaResultForXml(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "getIdeaResultForXml",            //action参数
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jpid: pid,
	            jaccmode: accmode,
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
		mode = "<%=reqmode%>";		 			//单户审批(one/more)
		pid =  "<%=reqpid%>";		 			//当前业务ID
		tabid = "<%=reqtabid%>";		 		//审批标签ID	
		//	
		fmids = "<%=reqfmids%>";		 			//家庭ID			
		//
		allsqlcount = "<%=reqsqlcount%>";	 		//总户数
		allsumpopcount = "<%=reqsumpopcount%>";	//总人口
		allsummoney = "<%=reqsummoney%>";		 	//总收入
		allsumolemoney = "<%=reqsumolemoney%>";	//总上期救助金
		allsumnewmoney = "<%=reqsumnewmoney%>";	//总拟发救助金
		//
		accmode = "<%=reqaccmode%>";		 	//用户修改救助金标识
		//
		onecheck = "<%=reqonecheck%>";		 	//第一级审批机构
		endcheck =  "<%=reqendcheck%>";		 	//终审机构
		report = "<%=reqreport%>";		 		//上报名单(确认名单标识)	
		//
		//
		if(onecheck != "1"){	//不是第一级审批
			spanideaman.innerHTML="评议审批人";
			$("#oneideatb").css({"display":"none"});			
		}else{			
			spanideaman.innerHTML="评议居民代表";
		}
		if(mode!="one"){		//不是单户审批
			$("#oneideatb").css({"display":"none"});
			$("#ideastb").css({"display":"none"});
			$("#ideamanstb").css({"display":"none"});
			//
			$("#btnok").get()[0].focus();
		}else{
			//
			$("#idesc").get()[0].focus();
		}
		
		//信息汇总栏
		IdeaInfoCount();
		//获取致困原因复选
		//getRequestTypeCheckBox();
		//获取审批结果下拉选择框
		getCheckIdeaSelect();
		//获取评议人复选
		getRequestPersonCheckBox();	
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
    		<!--
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'queryideatb'>  
	  			<tr valign='middle'>
	  				<td width = "120" align='center' valign='middle' class='mybackground myspace'>致困原因</td>
	  				<td align='left' valign='middle' class='myspace' colspan = '7'>
	  					<div id = 'divqtypes'></div>
	  				</td>
	  			</tr>	  			
	  		</table>
	  		-->
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'moreideatb'>
	  			<tr valign='middle' class='mybackground myspace' id = 'tridt'>
	  				<td width = "120" align='center' valign='middle'>救助开始日期</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'ibegdt' onFocus= 'setday(this)'></input>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>救助结束日期</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'ienddt' onFocus= 'setday(this)'></input>
	  				</td>
	  			</tr>	
	  			<tr valign='middle' class='mybackground myspace'>
	  				<td width = "120" align='center' valign='middle'>评议审批结果</td>
	  				<td align='left' valign='middle'>
	  					<div id = 'divresults'></div>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>评议审批日期</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'icheckdt' onFocus= 'setday(this)'></input>
	  				</td>
	  			</tr>
	  		</table>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'ideastb'>
	  			<tr valign='middle' class='mybackground myspace'>
	  				<td width = "120" align='center' valign='middle'>评议审批意见</td>
	  				<td align='left' valign='middle'>
	  					<textarea rows='2' style = 'width:100%' id = 'idesc'></textarea>
	  				</td>
	  			</tr>
	  		</table>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'oneideatb'>	  			  		
	  			<tr valign='middle' class='mybackground myspace'>
	  				<td width = "120" align='center' valign='middle'>居民代表(人)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imanall' value = '0'></input>
					</td>				
	  				<td width = "120" align='right' valign='middle'>同意(人)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imanok' value = '0'></input>
					</td>
					<td width = "120" align='right' valign='middle'>弃权(人)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imandel'  value = '0'></input>
					</td>
	  				<td width = "120" align='right' valign='middle'>不同意(人)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imannot' value = '0'></input>
					</td>
	  			</tr>
	  		</table>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'ideamanstb'>	  			
	  			<tr valign='middle'>
	  				<td width = "120" align='center' valign='middle' class='mybackground myspace'><span id = 'spanideaman'></span></td>
	  				<td align='left' height='23' valign='middle' class='myspace'>
	  					<div id = 'divimans'></div>
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
	//获取系统日期
	function getSysTimeFormatDate(){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "getSysTimeFormatDate"             //action参数
	        },
	        function(result) {                    	//回调函数
	        	$("#ibegdt").val(result);	
	        	$("#ienddt").val(result);
	        	$("#icheckdt").val(result);   	
	        }
	    );
	    //
	    $("#tridt").css({"display":"none"});
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
  				html += "<td height='23' align='center' >总家庭户数["+allsqlcount+"]</td>";	
  				html += "<td height='23' align='center' >总保障人口["+allsumpopcount+"]</td>";	
  				html += "<td height='23' align='center' >总计算收入["+ReplaceMoney(allsummoney)+"]</td>";	
  				html += "<td height='23' align='center' >总上期救助金["+ReplaceMoney(allsumolemoney)+"]</td>";	
  				html += "<td height='23' align='center' style = 'color: #6BA6FF;'>总拟发救助金["+ReplaceMoney(allsumnewmoney)+"]</td>";	
  			html += "</tr>";	
  		html += "</table>";			
		//		
		ideainfocounttb.innerHTML=html;
	}	
</script>
<script type="text/javascript">
	//更新救助金
	function ChangMoneyRow(src,optid){
		var tpagemoney = 0;
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="money[]") {
				tpagemoney += Number(document.all[i].value);
			}
		}
		allsumnewmoney = Number(allsumnewmoney) - Number(sumnewmoney) + Number(tpagemoney);
		sumnewmoney = Number(tpagemoney);
		//信息汇总栏
		InfoCount();
		//信息汇总栏
		IdeaInfoCount();
		//更新单个救助金
		updateMoneyFromOptID(optid,src.value);	
	}
	//更新救助金
	function updateMoneyFromOptID(optid,money){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: "updateMoneyFromOptID",             //action参数
	            joptid: optid,
	            jmoney: money
	        },
	        function(result) {                    	//回调函数
	        	alert(result);   	
	        }
	    );
	}
</script>
<script type="text/javascript">
	//审批确认
	function editCheckIdea(){
		//
		var actionmode = "addCheckIdea";
		//
		var jdesc = "",jmanids = "";
		var jmanall ="0",jmanok = "0",jmandel = "0",jmannot = "0";		
		//
		var jbegdt = $("#ibegdt").val();
		var jenddt = $("#ienddt").val();
		//
		var jresult = $("#resultlist").val();
		var jcheckdt = $("#icheckdt").val();
		//单户审批
		if(mode == "one"){				//单户审批
			//
			actionmode = "addCheckIdea";
			//		
			jdesc = $("#idesc").val();
			jmanids = "";//评议人
			//
			for (i=0;i<document.all.length;i++) {
				if (document.all[i].name=="cbxp[]") {
					if(document.all[i].checked == true){
						var tmpid = document.all[i].id;
						tmpid = tmpid.substring(1,tmpid.length);	//id有q连接
						if(jmanids == ""){
							jmanids = tmpid;
						}else{
							jmanids += "," + tmpid;
						}
					}
				}
			}
			//
			if(onecheck != "1"){	//不是第一级审批
				jmanall = "0";
				jmanok = "0";
				jmandel = "0";
				jmannot = "0";
			}else{					//是第一级审批
				jmanall = $("#imanall").val();
				jmanok = $("#imanok").val();
				jmandel = $("#imandel").val();
				jmannot = $("#imannot").val();
			}
			//==================================================转码
			jdesc = encodeURI(jdesc);		
			//==================================================转码
		}else{				//批量审批
			actionmode = "updateCheckIdea";
		}
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //服务器页面地址
	        {
	            action: actionmode,             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jtabid: tabid,
	            jpid: pid,
	            jfmids: fmids,
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jreport: report,
	            jbegdt: jbegdt,
	            jenddt: jenddt,
	            jresult: jresult,
	            jcheckdt: jcheckdt,
	            jdesc: jdesc,
	            jmanall: jmanall,
	            jmanok: jmanok,
	            jmandel: jmandel,
	            jmannot: jmannot,
	            jmanids: jmanids
	        },
	        function(result) {                    	//回调函数
	        	$("#divbtncheck").css({"display":"none"});
	        	alert(result);   	
	        }
	    );
		//
	}
</script>
<script type="text/javascript">
	//打开家庭查看页面
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息卡片&AfterIframeOpen()","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}	
</script>
<script type="text/javascript">
	//打开审批流程页面
	function GetCheckIdeaFlow(fmid){
		var bWidth = document.body.clientWidth-30;   	//背景宽度
		var bHeight= document.body.clientHeight-80; 	//背景高度
		var sArgu = "qfmids="+fmid+"&qpid="+pid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideaflow.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭审批流程&AfterIframeOpen()","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
<script type="text/javascript">
	//获取家庭政策业务相关详细信息
	function GetCheckPolicyInfosHtml(fmid,memid){
	    //
		//var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var bWidth= 450; //背景宽度
		var sArgu = "qpid="+pid+"&qfmid="+fmid+"&qmemid="+memid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideainfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务相关信息详细&AfterIframeOpen()","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
</script>
<script type="text/javascript">
	//激活窗口按钮
	function AfterIframeOpen(){
		if(mode!="one"){		//不是单户审批			
			$("#btnok").get()[0].focus();
		}else{
			$("#idesc").get()[0].focus();
		}
	}
</script>
