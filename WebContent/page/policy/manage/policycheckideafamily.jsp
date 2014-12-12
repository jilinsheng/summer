<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%@page import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//
	//
	//接收qmode
	String reqmode = request.getParameter("qmode");	
	if (reqmode == null) {
		//默认为空
		reqmode = "edit";    //编辑状态
	}
	//接收qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//默认为空
		reqpid = "";    //空业务编号
	}	
	//接收qfmid
	String reqfmid= request.getParameter("qfmid");		
	if (reqfmid == null) {
		//默认为空
		reqfmid = "";    //空家庭ID
	}
	//
	//接收qcheckmode
	String reqcheckmode= request.getParameter("qcheckmode");		
	if (reqcheckmode == null) {
		//默认为空
		reqcheckmode = "";    //空查询审批标准模式
	}
	//
	//接收查询表qselsql
	String reqselsql = request.getParameter("qselsql");	
	reqselsql = new String(reqselsql.getBytes("ISO8859_1"), "UTF-8");//解码
	if (reqselsql == null) {
		//默认为空
		reqselsql = "";    //空sql
	}	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>家庭审批意见填写管理</title>
    
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
		.pointer {
			cursor: pointer;
		}
		.tab {
			width: 100%;		
			margin: 0 auto;
			overflow: hidden;				
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
		.rowField {
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
		-->
	</style>	
	<script type="text/javascript">
		var mynewdt,mynewy,mynewm,mynewd;
		var myDate = new Date();
		    		myDate.getYear();       //获取当前年份(2位)
		    mynewy =myDate.getFullYear();   //获取完整的年份(4位,1970-????)
		    mynewm =myDate.getMonth()+1;      //获取当前月份(0-11,0代表1月)
		    mynewd =myDate.getDate();       //获取当前日(1-31)
		    	    myDate.getDay();        //获取当前星期X(0-6,0代表星期天)
				    myDate.getTime();       //获取当前时间(从1970.1.1开始的毫秒数)
				    myDate.getHours();      //获取当前小时数(0-23)
				    myDate.getMinutes();    //获取当前分钟数(0-59)
				    myDate.getSeconds();    //获取当前秒数(0-59)
				    myDate.getMilliseconds();   //获取当前毫秒数(0-999)
			mynewdt=myDate.toLocaleDateString();    //获取当前日期
				    var mytime=myDate.toLocaleTimeString();    //获取当前时间
				    myDate.toLocaleString( );       //获取日期与时间
	</script>
	<script type="text/javascript">	
		//
		//
		var empid = "";        //当前登录用户编号 
		var deptid = "";       //当前登录机构
		//
		var pid = "";		   //当前处理业务ID 
		var fmids = "";		   //当前处理家庭ID
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		var selcheckmode = "111";//审批页面标签(新增)
		var selsql = "";			//审批页面SQL语句
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
		var reqtype = "";
		var reptype = "";
		var repman = "";
		var repidea = "";
		var repdt = "";
		var repbegdt = "";
		var rependdt = "";
		var idsmoneys ="";			//审批ID和救助金
		var idschildmoneys ="";		//分类施保审批ID和分类施保救助金
		//
		//正则表达式匹配
	    //返回字符串数组
	    function MatchMatch(str,s)
	    {
			var r, re; // 声明变量。
			//g （全文查找出现的所有 pattern） 
			//i （忽略大小写） 
			//m （多行查找）
			//re   =   /s/gi;
			re = new RegExp(s,"gi"); // 创建正则表达式对象。
			r = str.match(re); // 在字符串 s 中查找匹配。             
			return(r);  
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
		function DisplayResult(info,outflag) {
	    	vleft = document.body.clientWidth/2-100;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度  	
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //获取提示信息div
		    resultstatusdiv.html(info);                       //设置div内文本		    
		    $(resultstatusdiv).fadeIn();                      //淡入信息
		    if(outflag!="1"){
		    	setTimeout("HiddenResult()",2000);          //2秒后隐藏信息
		    }
		}		
		//隐藏提示信息div
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //淡出信息
		}
		//
		//初始化页面
	    function IniPage(){	
	    	//	    	   
			empid = "<%=empno%>"; 	//当前登录用户编号
			deptid = "<%=onno%>";    //当前登录机构 
			//
			pid = "<%=reqpid%>";				//当前处理业务ID 
			fmids = "<%=reqfmid%>";				//当前处理家庭ID 
			mode = "<%=reqmode%>";				//当前审批模式还是查看模式
			selcheckmode = "<%=reqcheckmode%>";	//审批页面标签
			selsql = "<%=reqselsql%>";			//审批页面SQL语句
			//			
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			//			
			//取得业务审批状态标识
			GetCheckPolicyFlagsXml();
			
		}
	</script>
  </head>
  
  <body  onload = "IniPage()">
  	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>  
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  	<div id='resultstatusdiv'></div>
  	<fieldset id = "checkitems" class='list'>
		<legend class='legend'>意见填写管理</legend>			
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  		<tr align="center">
    		<td valign="top" class = "myspace"><div id="checkideacon"></div></td>	
    	</tr>
  	</table> 	
   	</fieldset>
  	<fieldset id = "familyitems" class='list'>
		<legend class='legend'>审批家庭信息</legend>			
  		<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div id="familycon"></div></td>	
	    	</tr>		    		   				    	    	
    	</table> 	
   	</fieldset>
   	<div id = "policyinfodiv"></div>
  </body>
</html>
<script type="text/javascript">
	//获取各级审批意见
	function GetMoreCheckIdeaHtml(checkid,memname){
		//
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var sArgu = "qpid="+pid+"&qcheckid="+checkid+"&qmode=check";
		var oUrl = "/db/page/policy/manage/policycheckideahtml.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭评议详细 姓名:"+memname+"","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");		
	}
	//获取分类施保各级审批意见
	function GetChildCheckIdeaHtml(checkid,memname){
		//
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var sArgu = "qpid="+pid+"&qcheckid="+checkid+"&qmode=child";
		var oUrl = "/db/page/policy/manage/policycheckideahtml.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"分类评议详细 姓名:"+memname+"","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");		
	}		
</script>
<script type="text/javascript">	
	//添加致困原因
	function addDictValue(){
		//(致困原因:115)
		UpdateDiscionary('115');
	}
	//更新字典值
	function UpdateDiscionary(discid){
		//
		if(discid==""||discid==null){
			DisplayResult("提示:没有选择字典!");				
			return;
		}		
		var bWidth = document.body.clientWidth-30;  //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bHeight= 300; //背景高度
		var sArgu = "qid="+discid+"";
		var oUrl = "/db/page/policy/manage/policydiscionary.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"字典类型管理&UpdatePolicyRequestTypeChoice()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//添加评议人
	function addCheckPerson(){
	    var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/policy/manage/policycheckperson.jsp?";
		jBox.open("iframe-jBoxID","iframe",oUrl,"审批评议人管理&UpdateCheckPersonChoice()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		
	}
	//更新致困原因
	function UpdatePolicyRequestTypeChoice(){
		//致困原因
		GetPolicyRequestTypeChoice("requesttypechoice","liststype");
		if(mode!="edit"){//查看重新评议状态
	    	//
	    	$("#checkideatxt").get()[0].focus();
	    }
	}
	//更新评议人
	function UpdateCheckPersonChoice(){
		//评议人
		GetCheckPersonChoice("ideamanchoice","listsideaman");
		if(mode!="edit"){//查看重新评议状态
	    	//
	    	$("#checkideatxt").get()[0].focus();
	    }	
	}
</script>
<script type="text/javascript">
	<!-- 
	//取得审批已经填写表单Html
	function GetCheckIdeaHtml(){
	  //
	  $.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
	        {
	            action: "getCheckIdeaHtml" //action参数  
	        },
	        function(result) {   //回调函数
	        	checkideacon.innerHTML = result;
	        	//
	        	if(mode=="edit"){//查看重新评议状态
			    	//
			    	$("#checkitems").css({"display":"none"});
			    	//取得业务家庭审批列表
					GetFamilyCheckItemsTable();
			    }else if(mode=="add"){//新评议状态
			    	$("#checkitems").css({"display":"block"});
			    	$("#checkideatxt").get()[0].focus();			    	
					//更新致困原因
			    	UpdatePolicyRequestTypeChoice();	
			    	//更新评议人
			    	UpdateCheckPersonChoice();		    	
			    	//审批结果
			    	GetPolicyCheckIdeaChoice("checkideachoice","listsidea",selcheckmode);
			    	//取得业务家庭审批列表
					GetFamilyCheckItemsTable();
			    }else if(mode=="all"){//全部评议状态
			    	$("#checkitems").css({"display":"block"});
			    	$("#checkideatxt").get()[0].focus();
			    	//更新致困原因
			    	UpdatePolicyRequestTypeChoice();	
			    	//更新评议人
			    	UpdateCheckPersonChoice();		    	
			    	//审批结果
			    	GetPolicyCheckIdeaChoice("checkideachoice","listsidea",selcheckmode);
			    	//
			    	$("#checkideas").css({"display":"none"});	
			    	$("#familyitems").css({"display":"none"});			    	
			    }	       	          	                                         
	        }
	    );        
	}	
	//取得业务致困原因分类选择下拉框
	function GetPolicyRequestTypeChoice(pardivid,sname){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	//  
	    $.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
	        {
	            action: "getPolicyRequestTypeChoice", //action参数
	            sname: sname //参数                              
	        },
	        function(result) {   //回调函数
	          	pardiv.innerHTML = result;			       	          	                                         
	        }
	    );        
	}
	//取得评议人选择下拉框
	function GetCheckPersonChoice(pardivid,sname){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	//
	    $.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
	        {
	            action: "getCheckPersonChoice", //action参数
	            sname: sname, //参数
	            empid: empid                              
	        },
	        function(result) {   //回调函数
	          	pardiv.innerHTML = result;
	          	//
	          	if(result != ""){
	          		$("#"+sname).val(empid);
	          	}				       	          	                                         
	        }
	    );        
	}
	//取得业务本级审批意见选择下拉框
	function GetPolicyCheckIdeaChoice(pardivid,sname,smode){
	  	//   
	  	var pardiv = document.getElementById(pardivid);
	  	
	    $.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
	        {
	            action: "getPolicyCheckIdeaChoice", //action参数
	            sname: sname, //参数
	            smode: smode                              
	        },
	        function(result) {   //回调函数
	          	pardiv.innerHTML = result;	
	        }	          	
	    );        
	}
	//选择审批意见结果
	function SetIdeaResult(){
		var sval = $("#listsidea").val();		          									
		if(sval=="2"){//同意救助
			$("#redatetr").css({"display":"block"});			          										
		}else{
			$("#redatetr").css({"display":"none"});
		}
		if(sval=="2" || sval=="4" || sval=="7"){//同意救助、渐退、恢复		          										
			$("#checktypetr").css({"display":"block"});			          										
		}else{
			$("#checktypetr").css({"display":"none"});
		}
	}
</script>
<script type="text/javascript">
	//取得业务审批状态标识
	function GetCheckPolicyFlagsXml(){
	  	//
	    $.post("/db/page/policy/manage/policycheckidea.do",      //服务器页面地址
	        {
	            action: "getCheckPolicyFlagsXml", //action参数
	            pid: pid, //参数
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
		//取得审批已经填写表单Html
		GetCheckIdeaHtml();
	}
</script>
<script type="text/javascript">
	//选择评议人
	function ChoiceIdeaman() {
		var isideaman;
		var ideaman = $("#checkideaman").val();
		//
		isideaman = document.getElementById("listsideaman");
		if(isideaman ==null){
			alert("请添加评议人!");
			return;
		}
		var sideaman = listsideaman.options[listsideaman.selectedIndex].text;
		var si = ideaman;
		//
		if(ideaman==""){
     		$("#checkideaman").val(sideaman);	
      	}else{
	      	if(MatchMatch(si,sideaman)==null){                              
	        	if (si.length>0){
	        		$("#checkideaman").val(ideaman+","+sideaman);          
	        	} 
	      	}
      	} 		
	}
	//置空评议人
	function ClearIdeaman() {
		$("#checkideaman").val(""); 
	}
	//选择原因
	function ChoiceType() {
		var istype;
		var type = $("#checktypes").val();
		var typeid = $("#checktypesid").val();
		//
		istype = document.getElementById("liststype");
		if(istype ==null){
			alert("请添加致困原因!");
			return;
		}
		//
		var stypeid = $("#liststype").val();
		var stype = liststype.options[liststype.selectedIndex].text;
		var st = type;
		//
		if(type==""){
      		$("#checktypes").val(stype);
      		$("#checktypesid").val(stypeid);	
      	}else{
	      	if(MatchMatch(st,stype)==null){                              
	        	if (st.length>0){
	        		$("#checktypes").val(type+","+stype);
	        		$("#checktypesid").val(typeid+","+stypeid);	            
	        	} 
	      	}
      	} 		    
	}
	//置空原因
	function ClearType() {
		$("#checktypesid").val(""); 
		$("#checktypes").val("");    		    
	}	
	//救助期限无限期
	function SetEndDt() {
		var ischeckb,enddt;
		ischeckb = document.getElementById("checkb");
		if(ischeckb.checked==0){
			$("#reenddt").val(""); 
		}else{
			enddt = parseInt(mynewy)+60;
			enddt += "-" + mynewm;
			enddt += "-" + mynewd;
			$("#reenddt").val(enddt); 
		}
	}

</script>
<script type="text/javascript">
	//取得业务家庭审批列表
	function GetFamilyCheckItemsTable(){
	  	//
	  	var smode = checkmoneyflag;//审批用户可以修改救助金标识
	  	//
	    $.post("/db/page/policy/manage/policycheckideafamily.do",      //服务器页面地址
	        {
	            action: "getFamilyCheckItemsTable", //action参数
	            pid: pid, //参数
	            fmids: fmids,
	            mode: smode,
	            empid: empid                              
	        },
	        function(result) {   //回调函数
	        	//
	        	var pardiv = document.getElementById("familycon");
	          	pardiv.innerHTML = result;	          	
	        }	          	
	    );        
	}
	//显示、隐藏分类施保详细
	function ViewChildtb(src,crid){
		var sdisplay = $("#"+crid).css("display");		
		if(sdisplay=="none"){
			//
			var trs =  document.getElementsByTagName("tr");		
			for(var i=0; i<trs.length;i++){
				var childtrid = trs[i].id;
				if(childtrid.length>7){
					var childid = childtrid.substring(0,7);									
					if(childid == "childtr"){						
						$("#"+childtrid).css({"display":"none"});
					}
				}	
			}
			//
			$("#"+crid).css({"display":"block"});
			//
			var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "imgs"){
					imgs[i].src="/db/page/images/membermax.jpg";
				}
			}
			src.src="/db/page/images/membermin.jpg";
			src.alt="收缩分类施保列表";			
		}else{
			$("#"+crid).css({"display":"none"});
			//
			var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "imgs"){
					imgs[i].src="/db/page/images/membermax.jpg";
				}
			}
			src.src="/db/page/images/membermax.jpg";
			src.alt="展开分类施保列表";	
		}
	}
	//显示家庭相关救助
	function ViewPolicys(fmid){
		alert("显示家庭相关救助情况");
	}
	//移除审批
	function MoveDel(ftrid){		
		//
		var d = document.getElementById(ftrid);   
		  if (d && confirm("您要移除?")){
			  //删除家庭行 
			  var p;  
			  if(p=d.parentNode){   
			  	p.removeChild(d);			  	   
			  }
		  }
	}
	//移除审批
	function MoveDelChild(ftrid,ptrid,ctrid){		
		//
		var d = document.getElementById(ftrid);   
		  if (d && confirm("您要移除?")){			 
			  //删除家庭行 
			  var p;  
			  if(p=d.parentNode){   
			  	p.removeChild(d);			  	   
			  }
			  //删除业务相关行
			  var pp;
			  var pd = document.getElementById(ptrid);
			  if(pp=pd.parentNode){   
			  	pp.removeChild(pd);			  	   
			  }  
			  //删除分类施保行
			  var ppp;
			  var ppd = document.getElementById(ctrid);
			  if(ppp=ppd.parentNode){   
			  	ppp.removeChild(ppd);			  	   
			  } 
		  }
	}
	//汇总家庭基本救助金
	function SumMoneyFamily(fmid,checkid,childflag){		
		//家庭拟发救助金
		var countmoney = 0;		
		if("1"==childflag){//分类施保标识
			//基本救助金
			var usermoney = document.getElementById("usermoney"+checkid).value;
			var fmoney = parseInt(usermoney);
			//
			var fmspan = document.getElementById("usermoney"+fmid);
			fmspan.innerHTML = fmoney;	
			//分类施保
			var childmoney = document.getElementById("allchildmoney"+fmid).innerText;
			var cmoney = parseInt(childmoney);
			//拟发救助金
			countmoney = fmoney + cmoney;
		}else{
			//基本救助金
			var usermoney = document.getElementById("usermoney"+checkid).value;
			var fmoney = parseInt(usermoney);
			//拟发救助金
			countmoney = fmoney;
		}
		//拟发救助金
		var countspan = document.getElementById("countmoney"+fmid);
		countspan.innerHTML = countmoney;			
	}	
	//汇总家庭分类施保救助金
	function SumChildMoneyFamily(fmid){
		//
		var Objs;		
		//分类施保列表				
		Objs = document.getElementById("childstb"+fmid);		
		var inputs =  Objs.getElementsByTagName("input");
		var summoney = 0;					
		for(var i=0; i<inputs.length;i++){
			var moneyid = inputs[i].id;
			var moneyval = inputs[i].value;
			summoney += parseInt(moneyval);		
		}
		//家庭分类施保	
		var parspan = document.getElementById("allchildmoney"+fmid);
		parspan.innerHTML = summoney;
		//基本救助金
		var usermoney = document.getElementById("usermoney"+fmid).innerText;
		var fmoney = parseInt(usermoney);
		//分类施保
		var childmoney = document.getElementById("allchildmoney"+fmid).innerText;
		var cmoney = parseInt(childmoney);
		//拟发救助金
		countmoney = fmoney + cmoney;
		var countspan = document.getElementById("countmoney"+fmid);
		countspan.innerHTML = countmoney;
	}
	
</script>
<script type="text/javascript">
	//单个操作
	function ChioceDo(src,checkid,memname){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		//获取各级审批意见
		GetMoreCheckIdeaHtml(checkid,memname);
	}
	//单个操作
	function ChioceDoChild(src,checkid,memname){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";		
		//获取分类施保各级审批意见
		GetChildCheckIdeaHtml(checkid,memname);
	}
</script>
<script type="text/javascript">
	//获取家庭政策业务相关详细信息
	function GetCheckPolicyInfosHtml(fmid,memid,fmname){
	    //
		//var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var bWidth= 450; //背景宽度
		var sArgu = "qpid="+pid+"&qfmid="+fmid+"&qmemid="+memid+"&qmemname="+fmname+"&qacctype="+acctypeflag;
		var oUrl = "/db/page/policy/manage/policycheckinfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"户主："+fmname+"  业务相关信息详细","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
</script>
<script type="text/javascript">
	//获取家庭政策业务相关标准
	function GetCheckPolicySqlsHtml(checkid,fmname){		
		//显示页面状态div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //服务器页面地址
	        {
	            action: "getCheckPolicySqlsHtml",             //action参数
	            checkid: checkid
	        },
	        function(result) {                    	//回调函数
	        	//隐藏页面状态div
				HiddenPageStatus();	
				//
				var pardiv = document.getElementById("policyinfodiv");
				pardiv.innerHTML = result;
				//	
				//var bWidth = document.body.clientWidth-30;  //背景宽度
				var bHeight= document.body.clientHeight-80; //背景高度
				var bWidth= 300; //背景宽度
				//var bHeight= 300; //背景高度
				jBox.open("div-jBoxID","div","policyinfodiv","姓名:"+fmname+" 业务相关标准&ClosePolicyDiv()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");	        		    					      		
	        }
	    );
	}
	//获取家庭政策业务相关核算公式
	function GetCheckPolicyAccsHtml(checkid,fmname){		
		//显示页面状态div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //服务器页面地址
	        {
	            action: "getCheckPolicyAccsHtml",             //action参数
	            checkid: checkid
	        },
	        function(result) {                    	//回调函数
	        	//隐藏页面状态div
				HiddenPageStatus();	
				//
				var pardiv = document.getElementById("policyinfodiv");
				pardiv.innerHTML = result;
				//	
				//var bWidth = document.body.clientWidth-30;  //背景宽度
				var bHeight= document.body.clientHeight-80; //背景高度
				var bWidth= 300; //背景宽度
				//var bHeight= 300; //背景高度
				jBox.open("div-jBoxID","div","policyinfodiv","姓名:"+fmname+" 业务核算公式&ClosePolicyDiv()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");	        		    					      		
	        }
	    );
	}
	//获取家庭政策业务分类施保相关标准
	function GetCheckPolicyChildSqlsHtml(checkid,fmname){		
		//显示页面状态div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //服务器页面地址
	        {
	            action: "getCheckPolicyChildSqlsHtml",             //action参数
	            checkid: checkid
	        },
	        function(result) {                    	//回调函数
	        	//隐藏页面状态div
				HiddenPageStatus();	
				//
				var pardiv = document.getElementById("policyinfodiv");
				pardiv.innerHTML = result;
				//	
				//var bWidth = document.body.clientWidth-30;  //背景宽度
				var bHeight= document.body.clientHeight-80; //背景高度
				var bWidth= 300; //背景宽度
				//var bHeight= 300; //背景高度
				jBox.open("div-jBoxID","div","policyinfodiv","名称:"+fmname+" 相关标准&ClosePolicyDiv()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");	        		    					      		
	        }
	    );
	}
	//关闭处理相关
	function ClosePolicyDiv(){
		var pardiv = document.getElementById("policyinfodiv");
		pardiv.innerHTML = "";
	}
</script>
<script type="text/javascript">
	//单个/批量审批业务	
	function SetMoreCheckIdea(){
		var mess = "";
		//
		reqtype = $("#checktypesid").val();
		reptype = $("#listsidea").val();
		repman = $("#checkideaman").val();
		repidea = $("#checkideatxt").val();
		repdt = $("#checkideadt").val();
		repbegdt = $("#rebegdt").val();
		rependdt = $("#reenddt").val();
		//
		//
		var sval = $("#listsidea").val();				          									
		if(sval=="2" || sval=="4" || sval=="7"){//同意救助、渐退、恢复
			//			
			if(reqtype == ""){
				alert("没有选择致困原因!");
				return;
			}
		}		
		//				
		if(repman == ""){
			alert("没有填写评议人!");
			return;
		}
		//		
		if(repdt == ""){
			alert("没有填写评议日期!");
			return;
		}
		if(sval=="2"){//同意救助
			//		
			if(repbegdt == ""){
				alert("没有填写救助开始日期!");
				return;
			}
			if(rependdt == ""){
				alert("没有填写救助结束日期!");
				return;
			}
		}				
		//			
		if(repidea == ""){
			alert("没有填写意见!");
			return;
		}
		//	
		//确认提示
		if(sval=="3"){//重新核查
			//当前业务第一级审批标识
			if(oneflowflag == "1"){
				mess = "审批名单将重新核查,是否确定审批?";
			}else{
				mess = "审批名单将退回重新核查,是否确定审批?";
			}			
		}else{
			if(preportflag=="1"){//确定名单提交审批标识
				if(endflowflag=="1"){//当前业务最后一级审批标识
					mess = "意见将拟为终审意见,是否确定审批?";
				}else{
					mess = "意见将提交上级,是否确定审批?";			
				}			
			}else{
				mess = "是否确定审批?";
			}
		}
		//开始审批
       	if(mode=="edit"){//查看重新评议状态
	    	
	    }else if(mode=="add"){//新评议状态
	    	//单个/批量审批业务	
			SetMoreCheckIdeaFamilys(mess);
	    }else if(mode=="all"){//全部评议状态
	    	//全部审批业务	
			SetAllCheckIdeaFamily(mess);		    	
	    }	
	}
	//
	//单个/批量审批业务	
	function SetMoreCheckIdeaFamilys(mess){
		//整理审批表ID和救助金
		idsmoneys = "";
		var inputs =  document.getElementsByTagName("input");								
		for(var i=0; i<inputs.length;i++){
			var inputid = inputs[i].id;
			var inputval = inputs[i].value;
			if(inputid.length>9){
				var tinputid = inputid.substring(0,9);	
				var einputid = inputid.substring(9,inputid.length);
				if(tinputid=="usermoney"){
					if(idsmoneys==""){
						idsmoneys = einputid+","+inputval;
					}else{
						idsmoneys += ";"+einputid+","+inputval;
					}
				}
			}		
		}
		//整理审批表ID和救助金
		idschildmoneys = "";
		//
		var inputschild =  document.getElementsByTagName("input");								
		for(var i=0; i<inputschild.length;i++){
			var inputid = inputschild[i].id;
			var inputval = inputschild[i].value;
			if(inputid.length>10){
				var tinputid = inputid.substring(0,10);	
				var einputid = inputid.substring(10,inputid.length);
				if(tinputid=="childmoney"){
					if(idschildmoneys==""){
						idschildmoneys = einputid+","+inputval;
					}else{
						idschildmoneys += ";"+einputid+","+inputval;
					}
				}
			}
					
		}
		//是否存在审批记录
		if(idsmoneys != ""){
			//
		    if (!confirm(mess)) {
		        return;
		    }
			//    
			DisplayPageStatus(); 
		 	//
		   	$.post("/db/page/policy/manage/policycheckmanage.do",      //服务器页面地址
		       	{
		           	action: "setMoreCheckIdea" , //action参数 
		           	pid: pid,		            
		           	allaccflag: allaccflag,
		           	preportflag: preportflag,
		           	empid: empid,
		           	reqtype: reqtype,
		           	reptype: reptype,
		           	repman: repman,
		           	repidea: repidea,
		           	repdt: repdt,
		           	repbegdt: repbegdt,
		           	rependdt: rependdt,
		           	idsmoneys: idsmoneys,
		           	idschildmoneys: idschildmoneys                             
		       	},
		      	function(result) {   //回调函数
		      		//加载数据完毕
	   				HiddenPageStatus();
			    	//
	   				$("#btncheck").attr("disabled", "disabled");
	   				DisplayResult(result,"1");
	   				//			    		                    
		     	}
		   );
		}
	}
	//全部审批业务	
	function SetAllCheckIdeaFamily(mess){
		//
   		if (!confirm(mess)) {
       		return;
   		}
		//    
		DisplayPageStatus(); 
		//
  		$.post("/db/page/policy/manage/policycheckmanage.do",      //服务器页面地址
	      	{
	          	action: "setAllCheckIdeaFamily" , //action参数 
	          	pid: pid,		            
	          	allaccflag: allaccflag,
	          	preportflag: preportflag,
	          	empid: empid,
	          	reqtype: reqtype,
	          	reptype: reptype,
	          	repman: repman,
	          	repidea: repidea,
	          	repdt: repdt,
	          	repbegdt: repbegdt,
	          	rependdt: rependdt,
	          	selsql: selsql                             
	      	},
	     	function(result) {   //回调函数
	     		//加载数据完毕
	 			HiddenPageStatus();
	    		//
	 			$("#btncheck").attr("disabled", "disabled");
	 			DisplayResult(result,"1");
	 			//			    		                    
	    	}
  		);
	}
</script>
