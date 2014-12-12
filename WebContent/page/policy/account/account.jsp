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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'account.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>		
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>	
	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>	
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
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
	var sqlcount = 0;//总记录数
	var sqlpagecount = 0;//总页数
	var sqlpagenum = 10;//每页记录数
	//
	var sqlselpage = 1;//选择页
	//
	var selbegpage = 0;		//分页开始
	var selendpage = sqlpagenum;//分页结束
	
	//
	//获取业务选择下拉框
	function getPolicySelect(sname,mode){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "getPolicySelect",             //action参数
	            sname: sname,
	            mode: mode
	        },
	        function(result) {                    	//回调函数
	        	if(mode == "new"){
	        		selectnewpid.innerHTML = "业务"+result;
	        	}else{
	        		selectpid.innerHTML = "业务"+result;	 
		        	//获取业务正执行批次
		  			getPolicyAccTable();       	
		        	//单击事件		      	
					$("#"+sname).change(
						function(){
							//获取业务正执行批次
		  					getPolicyAccTable();
						}
					);
	        	}	        	
	        }
	    );
	}
	//定额复选
	function ChbMoney() {
	    //
	  	if(chbmy.checked == true){
	  		$("#inputmy").attr("disabled", "");
	  	}else{
	  		$("#inputmy").attr("disabled", "disabled");
	  	} 
	}
	//获取业务结算批次
	function getPolicyAccTable(){
		//
		var selpid = $("#pidlist").val();
		var selacc = $("#acclist").val();
		//		
		var selyear = $("#syear").val();
		var selmonth = $("#smonth").val();
		var seldesc = $("#inputdesc").val();
		var selbegdt = $("#inputbegdt").val();
		var selenddt = $("#inputenddt").val();
		//所属机构
		var selqdeptid = $("#inputdeptid").val();	
		var selqdept = $("#inputdept").val();
		if(selqdept == ""){
			selqdeptid = "";
		}
		//秀秀屏蔽月份查询(查询月份框隐藏)
		selmonth = "";
		//
		seldesc = encodeURI(seldesc);
		//
		spanstatus.innerHTML = "查询数据,请稍候...";
		//加载数据		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "getPolicyAccTable",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jacc: selacc,
	            jyear: selyear,
	            jmonth: selmonth,
	            jdesc: seldesc,
	            jbegdt: selbegdt,
	            jenddt: selenddt,
	            jbegpage: selbegpage,
	            jendpage: selendpage,
	            jqdeptid: selqdeptid
	        },
	        function(result) {                    	//回调函数
	        	//加载数据完毕
     			HiddenPageStatus();
	        	//解析XML	 
	        	ShowData(result);	        		
	        }
	    );
	}
	//添加业务结算批次
	function addPolicyNowAcc(){
		//
		var selpid = $("#newpidlist").val();
		//
		var selyear = $("#snewyear").val();
		var selmonth = $("#snewmonth").val();
		var seldesc = $("#newdesc").val();
		var selbegdt = $("#newbegdt").val();
		var selenddt = $("#newenddt").val();
		//
		var selmoneyflag = "0";
		if(chbmy.checked == true){
	  		selmoneyflag = "1";
	  	}else{
	  		selmoneyflag = "0";
	  	}
		var selmoney = $("#inputmy").val();
		
		if(selbegdt == ""){
			alert("没有填写日期!");
			return;
		}
		if(selenddt == ""){
			alert("没有填写日期!");
			return;
		}
		if (!confirm("是否确定新增?")) {
	        return;
	    }
	    //描述
	    if (null == seldesc || "" == seldesc){
	    	seldesc = $("#newpidlist").find("option:selected").text();
	    }
		//
		seldesc = encodeURI(seldesc);
		//
		//
		spanstatus.innerHTML = "生成批次,请稍候...";
		//加载数据		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "addPolicyNowAcc",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jyear: selyear,
	            jmonth: selmonth,
	            jdesc: seldesc,
	            jbegdt: selbegdt,
	            jenddt: selenddt,
	            jmoneyflag: selmoneyflag,
	            jmoney: selmoney
	        },
	        function(result) {                    	//回调函数
	        	//加载数据完毕
     			HiddenPageStatus();
 				//
	        	alert(result);
	        	//获取业务正执行批次
	  			getPolicyAccTable();		        		
	        }
	    );
	}
	//删除业务结算批次
	function delPolicyNowAcc(accid){
		//
		var selpid = $("#pidlist").val();
		if (!confirm("是否确定删除?")) {
	        return;
	    }
		//
		spanstatus.innerHTML = "删除批次,请稍候...";
		//加载数据		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "delPolicyNowAcc",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jaccid: accid
	        },
	        function(result) {                    	//回调函数
	        	//加载数据完毕
     			HiddenPageStatus();
 				//
	        	alert(result);	
	        	//获取业务正执行批次
	  			getPolicyAccTable();	        		
	        }
	    );
	}
	//业务结算批次开始结算
	function accPolicyNowAcc(accid){
		//
		var selpid = $("#pidlist").val();
		if (!confirm("是否确定结算?")) {
	        return;
	    }
		//
		spanstatus.innerHTML = "结算批次,请稍候...";
		//加载数据		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "accPolicyNowAcc",             //action参数
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jaccid: accid
	        },
	        function(result) {                    	//回调函数
	        	//加载数据完毕
     			HiddenPageStatus();
 				//
	        	alert(result);	
	        	//获取业务正执行批次
	  			getPolicyAccTable();	        		
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
	//
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		//建立年月下拉选择框
		CreateSelect();
		$("#inputmy").attr("disabled", "disabled");
		//获取业务选择下拉框
		getPolicySelect("newpidlist","new");
		//获取业务选择下拉框
		getPolicySelect("pidlist","query");			
		//分页栏
	  	Pagination();
	}
  </script>	
  <body  onload = "IniPage()"> 
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img><span id = 'spanstatus'>查询数据请稍等...</span></div> 
  		<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = 'background:F2F5F7;'>
	  		<tr valign='middle' >	  			
		  		<td style = 'font-size:13px;'>
			  		<fieldset><legend>批次添加</legend>  		
			  			<table border='0' cellpadding='0' cellspacing='0'  width='640'align='center'>	  							
			  				<tr valign='middle' >
			  					<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectnewpid'></div>							  												  					
							  	</td>	
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectnewyear'></div>						  												  					
							  	</td>
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectnewmonth'></div>							  												  					
							  	</td>
							  	<td align='center' valign='middle' style = 'font-size:13px;display:none;'>
							  		<input type='checkbox' name='chbmy' id='chbmy' onclick="ChbMoney()">定额
				  					金额<input style = 'text-align: right;' type="text" id = "inputmy" size = '6' value = '0'></input>(元)	  												  												  					
							  	</td>
							</tr>
							<tr valign='middle' >	
								<td align='left' valign='middle' style = 'font-size:13px;' colspan = '3'>
				  					描述<input type="text" id = "newdesc" size = '45'></input>					  												  					
							  	</td>  					
							  	<td align='right' valign='middle' style = 'font-size:13px;'>
				  					日期<input type="text" id = "newbegdt" size = '10'  onFocus='setday(this)'></input>	
				  					至<input type="text" id = "newenddt" size = '10'  onFocus='setday(this)'></input>
				  					<button class = 'btn' onclick="addPolicyNowAcc()"> 新  增 </button>				  												  					
							  	</td>						  	
							</tr>					  			
				  		</table>
			  		</fieldset>
		  		</td>
  			</tr>
	  		<tr valign='middle' >	  			
		  		<td style = 'font-size:13px;'>
			  		<fieldset><legend>批次查询</legend>
				  		<table border='0' cellpadding='0' cellspacing='0' width='640' align='center'>	  			
			  				<tr valign='middle' >
			  					<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectpid'></div>							  												  					
							  	</td>	
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectyear'></div>						  												  					
							  	</td>
							  	<td align='left' valign='middle' style = 'font-size:13px;display:none;'>
				  					<div id = 'selectmonth'></div>							  												  					
							  	</td>							  	
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
							  		状态<select id='acclist' style = "font-size:12px;width = '84';" onchange="getPolicyAccTable()">
							  			<option value='-1'>全部</option> 
							  			<option value='0'>未结算</option> 
							  			<option value='1'>正结算</option> 
							  			<option value='2'>结算完毕</option> 
							  		</select> 												  												  					
							  	</td>
							  	<td align='right' valign='middle' style = 'font-size:13px;'>
				  					所属<input type="text" id = "inputdept" size = '31' onclick="queryDept()"></input>	
				  					<input type="text" id = "inputdeptid" style = 'display:none;'></input>					  												  					
							  	</td>
							</tr>
							<tr valign='middle' >				  				
				  				<td align='left' valign='middle' style = 'font-size:13px;' colspan = '3'>
				  					描述<input type="text" id = "inputdesc" size = '46'></input>						  												  					
							  	</td>	
							  	<td align='right' valign='middle' style = 'font-size:13px;'>
				  					日期<input type="text" id = "inputbegdt" size = '10'  onFocus='setday(this)'></input>
				  					至<input type="text" id = "inputenddt" size = '10'  onFocus='setday(this)'></input>	
				  					<button class = 'btn' id = 'btnquery' onclick="getPolicyAccTable()"> 查  询 </button>					  												  					
							  	</td>
			  				</tr>	  			
				  		</table>
				  	</fieldset>
			  	</td>
		  	</tr>
  		</table>
  		<fieldset><legend>查询列表</legend>
	  		<div align='center' id = 'queryacctb'></div>
    		<div align='center' id = "paginationtb"></div> 
    	</fieldset>
  </body>
</html>
<script  type="text/javascript">
	//分页栏
	function Pagination(){	
		//
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";				
			html += "<td height='25'>";
				html += "共["+sqlcount+"]条记录  共["+sqlpagecount+"]页  ";	
				if(sqlselpage==1){
					html += "<span>首页</span> ";
					html += "<span>上一页</span> ";
				}else{
					html += "<span class = 'pointer' onclick=\"ExePage('beg')\">首页</span> ";
					html += "<span class = 'pointer' onclick=\"ExePage('back')\">上一页</span> ";
				}
				if(sqlselpage==sqlpagecount){	
					html += "<span>下一页</span> ";
					html += "<span>尾页</span> ";
				}else{
					html += "<span class = 'pointer' onclick=\"ExePage('next')\">下一页</span> ";
					html += "<span class = 'pointer' onclick=\"ExePage('end')\">尾页</span> ";
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
		//获取业务正执行批次
	  	getPolicyAccTable();
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
		//获取业务正执行批次
	  	getPolicyAccTable();
	}	
</script>
<script  type="text/javascript">
	//建立年月下拉选择框
	function CreateSelect(){
		var yhtml = "<select id='syear' style = \"font-size:12px;width = '64';\">";
		var mhtml = "<select id='smonth' style = \"font-size:12px;width = '42';\">";
		for(var i = 1998;i<=2050;i++){
			yhtml += "<option value='"+i+"'>"+i+"</option>";
		}		
		yhtml += "</select>";
	  	selectyear.innerHTML = yhtml+"年";
	  	for(var i = 1;i<=12;i++){
			mhtml += "<option value='"+i+"'>"+i+"</option>";
		}
	  	mhtml += "</select>";
		selectmonth.innerHTML = mhtml+"月";
		//
		var yhtml = "<select id='snewyear' style = \"font-size:12px;width = '64';\">";
		var mhtml = "<select id='snewmonth' style = \"font-size:12px;width = '42';\">";
		for(var i = 1998;i<=2050;i++){
			yhtml += "<option value='"+i+"'>"+i+"</option>";
		}		
		yhtml += "</select>";
	  	selectnewyear.innerHTML = yhtml+"年";
	  	//
	  	for(var i = 1;i<=12;i++){
			mhtml += "<option value='"+i+"'>"+i+"</option>";
		}
	  	mhtml += "</select>";
		selectnewmonth.innerHTML = mhtml+"月";
		//
		//获取系统日期
		getSysTimeFormatDate();
	}
	//获取系统日期
	function getSysTimeFormatDate(){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "getSysTimeFormatDate"             //action参数
	        },
	        function(result) {                    	//回调函数
	        	$("#inputenddt").val(result);
	        	$("#newenddt").val(result);
	        	var sysy = result.substring(0,4);	
	        	var sysm = result.substring(6,7);
	        	var sysd = result.substring(9,10);
	        	$("#syear").val(sysy);    
	        	$("#smonth").val(sysm); 
	        	$("#snewyear").val(sysy);    
	        	$("#snewmonth").val(sysm); 	        	   	
	        }
	    );
	}
	//解析XML文件
	function ShowData(data){
		var xmlDoc;	
		var xtable;	
		//
		//		
		sqlcount=0;
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
			//审批名单表格
			var familyarr=root.selectNodes("/data/table/elements");
			xtable =familyarr.item(0).firstChild.data;
			queryacctb.innerHTML=xtable;
			//
			//JS表格排序
			new TableSorter("acctb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/
		}else{
			
		}
		//
	  	//分页栏
	  	Pagination();
	}
</script>
<script type='text/javascript'>
	//=================================AJAX模态窗体=================================
	(function($){
	$.openWindow = function(options){
	        var defaults = {
                   title:"title",                           //标题
                   content:"Content",                       //显示内容
                   loadUrl:"",                              //调用url
                   otype:"0",                               //调用类别
                   bColor:"#777",                           //背景色
                   //bWidth:(document.body.clientWidth-50)+"px",   //背景宽度
                   bWidth:0+"px",   //背景宽度
                   bHeight:document.body.clientHeight+"px", //背景高度
                   oColor:"#FFF",                           //弹出窗口颜色
                   oWidth:350,                              //弹出窗口宽度
                   oHeight:380                              //弹出窗口高度
           	};
	        $.extend(defaults,options);
	        		       
	        //秀秀
	        var cbtn = "<div id='cbtn'><img src='/db/page/images/close.gif'/></div><span id = 'stitle'>"+defaults.title+"</span><div>";
	        var odiv = "<div id='odiv'>"+cbtn+"<div id='content'></div></div>";
	        var bdiv = "<div id='bdiv'></div>";		        
	        if(!($("#bdiv").length))$("body").append(bdiv);
	        if(!($("#odiv").length))$("body").append(odiv);	
	        if(defaults.otype=="0"){
	        	//打开连接
	        	$("#content").load(defaults.loadUrl);
	        	//CSS
	        	$("#cbtn>img").css({"cursor":"pointer","float":"right"}).click(function(){$("#bdiv").remove();$("#odiv").remove();});	
	        }else if(defaults.otype=="1"){
	        	//打开机构选择
	        	$("#content").empty();                //清空现有列表
	        	loadrootTree('content','page/info/search/DeptTreeServlet',deptid,'root');
	        	//CSS
	        	$("#cbtn>img").css({"cursor":"pointer","float":"right"}).click(function(){closedept();});
	        }     
	        //CSS		        
	        $("#cbtn").css({"font-size":"12px","background":"#F2F3F7","color":"#777","padding":"5px 5px"});
	        $("#stitle").css({"font-size":"12px","background":"#F2F3F7","color":"#777","padding":"5px 5px"});
	        $("#content").css({"font-size":"14px","padding":"10px 10px","overflow-x":"hidden","overflow-y":"auto","width":defaults.oWidth+"px","height":defaults.oHeight+"px"});
	        $("#odiv").css({"background":defaults.oColor,"width":defaults.oWidth+"px","border":"1px black solid","z-index":"9999","position":"absolute","top":"10px","left":(document.body.clientWidth-defaults.oWidth)/2+"px"});
	        $("#bdiv").css({"background":defaults.bColor,"width":defaults.bWidth,"height":defaults.bHeight,"z-index":"9998","position":"absolute","filter":"alpha(opacity:90)","left":10,"top":0});
	};
	})(jQuery);	
	
	//查询机构标准
	function queryDept(){
    	$.openWindow({"title":"机构选择","otype":"1"});		    			
	}
	
	//机构选中[DeptTreeServlet成生的方法]
	function seldeptclick(id,name,fullname){
	   //选中机构	   
	   $("#selname").html("选中:["+name+"]");
	   seldeptid = id;
	   seldeptname = name;
	   seldeptfullname = fullname;
	   //
	   stitle.innerHTML = "选中:"+seldeptfullname;
	}
	//机构选择确定[DeptTreeServlet成生的方法]
	function okdept(){		    
		//机构标准选择
		if(seldeptid==null) return;
		/*
		if (!confirm("是否确定选择？\n["+seldeptname+"]")) {
	        return;
	    }
	    */
	    //选择调用参数
	    $("#inputdeptid").val(seldeptid);
	    $("#inputdept").val(seldeptfullname);	    
		closedept();
	}
	//置空机构选择[DeptTreeServlet成生的方法]
	function cleardept(){
		//选择调用参数
	    $("#inputdeptid").val("");
	    $("#inputdept").val("");	
	    //
		closedept();	
	}
	//关闭机构选择[DeptTreeServlet成生的方法]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();			
	}
	//
	//=================================AJAX模态窗体=================================
</script>