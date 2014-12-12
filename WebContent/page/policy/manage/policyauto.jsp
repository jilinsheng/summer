<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//业务选择
	String reqpolicy = request.getParameter("qpolicy");	
	
	if (reqpolicy == null) {
		//默认为空
		reqpolicy = "-1";    //选择业务[不选]
	}
	//
	//接收业务名称URLDecoder
	//从查询表接收查询字段select
	String reqselect = request.getParameter("qselect");
	reqselect = new String(reqselect.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqselect == null) {
		//默认为空
		reqselect = "";    //字段
	}
	//从查询表接收查询表from
	String reqfrom = request.getParameter("qfrom");
	reqfrom = new String(reqfrom.getBytes("ISO8859_1"), "GB18030");//解码	
	if (reqfrom == null) {
		//默认为空
		reqfrom = "";    //空表
	}
	//从查询表接收查询条件where
	String reqwhere= request.getParameter("qwhere");
	reqwhere = new String(reqwhere.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqwhere == null) {
		//默认为空
		reqwhere = "";    //空条件
	}
	//从查询表接收查询排序order
	String reqorder= request.getParameter("qorder");
	reqorder = new String(reqorder.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqorder == null) {
		//默认为空
		reqorder = "";    //空排序
	}	
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>业务自动筛选管理</title>
    
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
	
	<style type="text/css">
 <!--
  	body {
		margin: 0;
		padding: 0;
		font-family: "宋体";
		font-size: 12px;
	}
	a{
		text-decoration: none;
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
	.colTtems {		
		font-size: 12px;
		cursor: pointer;
		color: #6BA6FF;		
	}
	.pageTtems {
		color: #660099;		
	}
	.pointer {
		cursor: pointer;
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
	.mystyle {	
		line-height: 2;	
		font-size: 12px;
		color: #6BA6FF;
		height: 20px;		
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
  
  <script  type="text/javascript">
	//
	//		    
	var vleft = 0;  //背景宽度
	var vtop= 0; //背景高度
		
	var empid = "";        //当前登录用户编号 
	var deptid = "";        //当前登录机构 
	//
	var selmode = "2";		//当前用户选择生成名单模式//常量类定义
	//
	var selselect = "";    	//选择查询结果字段
	var selfrom = "";    	//选择查询结果表
	var selwhere = "";    	//选择查询结果条件
	var selorder = "";    //选择查询结果排序
	
	//
	var selpolicy = "-1";    	//选择业务
	//
	//
	var xmldata;	//XML数据实体
	//
	var colnum = 0,rownum =0;//列数/行数		
	//
	var sqlcount = "0";//总记录数
	var sqlolepagecount = "0";//旧总页数
	var sqlolepageselect = "";//旧页数选择下拉框
	var sqlpagecount = "0";//总页数
	var sqlpagenum = "16";//每页记录数
	//
	var sqlselpage = 1;//选择页
	//
	var selbegpage = 0;		//分页开始
	var selendpage = sqlpagenum;//分页结束	
	
	//
	//		
	//条件转换[逻辑条件转换物理条件]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,tmode,tbegpage,tendpage;
	    var tdept;  
	    var tpolicy; 
	    //
	    //			    
		DisplayPageStatus(); 
	    //		   
	    //      
	    tselect = selselect;  
	    tfrom = selfrom;
	    twhere = selwhere;
	    torder = selorder;	    
	    //解析查询模式常量定义模块定义tmode
	    tmode = "1";//模式0执行查询1提取SQL语句
	    tbegpage = selbegpage;
	    tendpage = selendpage;
	    //机构
	    tdept = deptid; 
	    //
	    tempid = empid;
	    //业务选择		    		          		
	    tpolicy = selpolicy;
	    //
	    //
	    $.post("page/info/search/TableInfoServlet",      //服务器页面地址
	        {
	            action: "getphysql", //action参数
	            mode: "getAutoCheckFamilySql",
	            tselect: tselect,   //参数
	            tfrom: tfrom,   //参数
	            twhere: twhere,   //参数
	            torder: torder, //参数
	            tmode: tmode,
	            tbegpage: tbegpage,
	            tendpage: tendpage,  //参数分页 
	            tdept: tdept,
	            tempid: tempid,	            
	            tpolicy: tpolicy         
	        },
	        function(result) {   //回调函数		        	
	        	//加载数据完毕
     			HiddenPageStatus();  	        	
	  			//
	          	if(result=="0"){
	          		//查询语句不合法 
	             	requestcon.innerHTML="当前政策业务没有设置审批流程!";	
	          	}else if(result=="-1"){                 
	             	//查询语句不合法 
	             	requestcon.innerHTML="当前登录用户机构不是该业务第一级审批机构!";	             	      
	          	}else if(result=="-2"){ 
	          		//查询语句不合法 
	             	requestcon.innerHTML="当前政策业务没有设置标准!";	
	          	}else{
	            	ShowData(result); 
	          }                             
	    });       
    }
	//
	//取得当前业务名称
	function GetPolicyName(pardivid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	var pid = selpolicy;
	  	if(pid=="-1"){//[无]选择业务
	  		pardiv.innerHTML = "当前业务:不选";
	  		return;		  		
	  	}
	  	//
	  	$("#"+pardivid).empty();//清空		  	
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyName", //action参数
		        pid: pid                            
		    },
		    function(result) {   //回调函数		    	
		    	pardiv.innerHTML = "当前选中业务["+result + "]政策描述";          	  					      	                                         
		    }
		);        
	}		
	//取得业务选择
	function GetCheckPolicyChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getCheckPolicyChoice", //action参数
		        sname: sname //参数                              
		    },
		    function(result) {   //回调函数		    	
		      	pardiv.innerHTML = result;
		      	//业务选择页面参数传进来		          	        	
		      	if(selpolicy!="-1"){		      				
		      		$("#"+sname).val(selpolicy);			      		
		      	}else{
		      		selpolicy = $("#"+sname).val();		      		
		      	}
		      	//业务名称
				GetPolicyName("spanpolicyitem");
				//
				//条件转换[逻辑条件转换物理条件]
    			GetPhySql();
    			//
				//单击事件
				$("#"+sname).change(function(){
												selpolicy = $("#"+sname).val();
												GetPolicyName("spanpolicyitem");
												//
												selbegpage = 0;		//分页开始
											 	selendpage = sqlpagenum;//分页结束
											 	//
												//取得物理查询语句且执行返回XML格式		  
												GetPhySql();
											  }
									);	
				      	                                         
		    }
		);        
	}		
	
	//显示页面状态div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2;  //背景宽度
		//vtop= document.body.clientHeight/2; //背景高度
		vtop= $("#spanpolicyitem").offset().top+20;   	
    	//
    	//$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
	    var pagestatusdiv = $("#pagestatusdiv");            //获取提示信息div		   		    
	    $(pagestatusdiv).fadeIn();                      //淡入信息		    
	}		
	//隐藏页面状态div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //淡出信息
	}			
	//初始化页面
    function IniPage(){
	    //
    	vleft = document.body.clientWidth/2;  //背景宽度
		//vtop= document.body.clientHeight/2; //背景高度
		vtop= $("#spanpolicyitem").offset().top+20;			
    	//当前登录用户编号    
		empid = "<%=empno%>";     
		//登录机构    
		deptid = "<%=onno%>";    //当前登录机构 
		//
		//选择业务查询	
		selpolicy = "<%=reqpolicy%>";		 //业务选择
		//
		selselect = "<%=reqselect%>";		 //查询选择字段
		selfrom = "<%=reqfrom%>";		 //查询选择表
		selwhere = "<%=reqwhere%>";		 //查询选择条件	
		selorder = "<%=reqorder%>";		 //查询选择排序
		//
	    //业务选择
        GetCheckPolicyChoice("choicepolicy","listpolicy");		
	   		        	   
    }
  </script>    
  </head>
  
  <body onload = "IniPage()"> 
  		<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr class = 'mybackground'>	   		
	   			<td valign="middle" align="right" class = "mystyle">业务选择:</td>
	   			<td valign="middle" align="left" width="20%" class = "mystyle"><div id = "choicepolicy"></div></td>
	   			<td valign="middle" align="left" class = "mystyle">
	   				<span  class = "pointer colTtems" id = "spanpolicyitem" onclick="CallStandardsDialog()"></span>
	   			</td>	   			
	   		</tr>	   		
	    	<tr align="center">
	    		<td valign="top" colspan = "3" class = "myspace"><div id="requestcon"> </div></td>	
	    	</tr>	    	    	
    	</table>
    	<!-- <div align="center"><input id = "btnclose" type="button" value=" 关 闭 "onclick="window.close();"></input></div> -->
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div></td>
	   		</tr>
   		</table> 
  </body>
</html>
<script type="text/javascript">
	//
	//
	function ShowData(data){
		var xmlDoc;
		//取出表头
		var html= "",fmid = "";		
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//总记录数
			var countarr=root.selectNodes("/data/count/num");
			sqlcount =countarr.item(0).firstChild.data;
			//总页数
			var j1 = sqlcount/sqlpagenum;
			var j2=Math.round(j1);
			if(j1>j2){
				sqlpagecount = j2+1;
			}else if(j1<=j2){
				sqlpagecount = j2;
			}
				
			//列名称
			var headarr=root.selectNodes("/data/ehead/cell");
			//
			colnum = headarr.length+1;
			//
			html += "<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				if(i==headarr.length-2){
					//隐藏机构ID
				}else{
					var temp ="<td height='23'>"+ss[1]+"</td>";
					html=html+temp;
				}
				
			}
			html += "<td>相关操作</td>";
			html=html+"</tr>";
			//
			//取出记录值
			xmldata = root.selectNodes("/data/list/entity");
			//
			//
			var rows=root.selectNodes("/data/list/entity");						
			//
			rownum = rows.length;
			//
			for(var i=0;i<rows.length;i++){
				var row = rows.item(i).childNodes;
				//
				fmid = row.item(0).firstChild.data;
				//									
				//		
				var temp="<tr ";						
				//单双行
				var mathrow = i%2;				
				if(mathrow == 1){//背景颜色
					temp +=" style = 'background: #F2F5F7;'>";	
				}else{
					temp +=">";	
				}			
					//各列值	
					for (var j=1;j<row.length;j++){
						if(j==1){
							//家庭编号列
							var temp1="<td height='23' class='colValue'>"+
								"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
							temp=temp+temp1;
						}else if(j==row.length-2){
							//隐藏机构ID
						}else{
							var temp1="<td height='23' class='colValue'>"+
							row.item(j).firstChild.data+"</td>";
							temp=temp+temp1;
						}
					}
					//
					temp += "<td width='88px' height='23' class='colValue'>";
						temp += "<span  class = 'pointer'><img src='/db/page/images/checkfamily.png' alt= '编辑家庭' onclick= \"infoeditaction('"+fmid+"')\"/>&nbsp;</span>";						
						temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '走访调查' onclick=\"CallInterviewDialog('"+fmid+"')\"/>&nbsp;</span>";											
					temp += "</td>";
				temp=temp+"</tr>";
				
				//
				html=html+temp
				//
				
			}
			//			
			html=html+"</table>";
			//
			//是否有记录
			if(rownum==0){
				html += "<table width='100%' cellpadding='0' cellspacing='0'>"
					html += "<tr>";				
						html += "<td height='25' class='colValue'>无查询结果</td>";
					html += "</tr>";
				html=html+"</table>";
			}
			//
			//			
			//	
			html += "<table width='100%' cellpadding='0' cellspacing='0'>"
			
			html += "<tr class='colField'>";				
			html += "<td height='23'>";
				html += "共["+sqlcount+"]条记录  共["+sqlpagecount+"]页  ";					
				if(sqlselpage==1){
					html += "<span>首页</span> ";
					html += "<span>上一页</span> ";
				}else{
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('1')\">首页</span> ";
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('2')\">上一页</span> ";
				}
				if(sqlselpage==sqlpagecount){	
					html += "<span>下一页</span> ";
					html += "<span>尾页</span> ";
				}else{
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('3')\">下一页</span> ";
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('4')\">尾页</span> ";
				}	
				html += "转到";
					//选择页下拉选择框
					html += "<span id = \"spanpages\"></span> ";
				html += "  每页<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>条记录</td>";
			html += "</tr>";		
			
			html=html+"</table>";			
			//
			
			//
			requestcon.innerHTML=html;
			
			//分页选择下拉框			
			spanpages.innerHTML=GetPageGo();
			//	
			//选择页
			$("#selectpage").val(sqlselpage);
			//每页显示行数
			$("#divpagerow").val(sqlpagenum);
			//
			//
			//
			//
			//JS表格排序
			new TableSorter("requesttb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/	
			//		
		}
		else{			
			requestcon.innerHTML="无查询结果";			
	
		}
	}
	//选项页文本框
	function GetPageGo(){
		var html;
		html = "第<input type='text' id = 'selectpage' size = '3'>页</input>";
		html += "<input type='button' value='Go' onclick=\"ExePage()\"></input>";
		return html;
	}
	//下拉选择页查询
	function ExePage(){
		var stpage = $("#selectpage").val();
		sqlselpage = parseInt(stpage);
		//计算分页数据
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;	
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();		
	}
	//选择页查询
	function ExePageFlag(pageflag){	
		//
		//
	    
		$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		//	
		//
		var flag = "1";//首页标识
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//首页
			if(sqlselpage==1){
				//			    
				DisplayResult("已经是首页!!!"); 		   
			    //  
				//alert("已经是首页!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//上页
			if(sqlselpage==1){
				//	
				DisplayResult("已经是首页!!!"); 		   
			    //  
				//alert("已经是首页!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//下页
			if(sqlselpage==sqlpagecount){
				//	
				DisplayResult("已经是尾页!!!"); 		   
			    // 
				//alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//页尾
			if(sqlselpage==sqlpagecount){
				//	   
				DisplayResult("已经是尾页!!!"); 		   
			    // 
				//alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlpagecount;			
		}
		
		//计算分页数据
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;		
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();		
	}
	//更新页显示行数
	function ChangPageRow(src){
		//
		sqlpagenum = src.value;
		//
		selbegpage = 0;		//分页开始
	 	selendpage = sqlpagenum;//分页结束
	 	sqlolepagecount = 0;//旧分页选择下拉框
	 	sqlselpage = 1;//开始第一页
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
	}
</script>
<script type="text/javascript">
	//打开业务档次标准窗体
	function CallStandardsDialog()
	{
		/*
		var myobj = new Array();
		myobj[0] = selpolicy;
		if(selpolicy=="-1"){//[无]选择业务		  		
	  		return;		  		
	  	}				
		showModalDialog("/db/page/policy/manage/policystandards.jsp",myobj,"status:false;dialogWidth:700px;dialogHeight:400px");
		*/
		if(selpolicy=="-1"){//[无]选择业务		  		
	  		return;		  		
	  	}
		var bWidth = document.body.clientWidth-30;   //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bHeight= 400; //背景高度
		var sArgu = "qpid="+selpolicy+"";
		var oUrl = "/db/page/policy/manage/policystandards.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务档次标准明细","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		
	}
	//打开困难申请查询页面
	function CallRequestDialog(fmid){
		var afrom = "INFO_T_FAMILY",awhere = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		requestaction(afrom,awhere);
	}
	//打开走访记录查询页面
	function CallInterviewDialog(fmid){		
		var afrom = "",awhere = "",aorder = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var sArgu = "qfrom="+afrom+"&qwhere="+awhere+"&qorder="+aorder+"";
		var oUrl = "/db/page/policy/manage/policyinterview.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭走访记录登记","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开家庭信息页面
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息卡片","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
	//打开家庭编辑页面
	function infoeditaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var oUrl = "/db/page/info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"家庭信息维护","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//		
</script>
