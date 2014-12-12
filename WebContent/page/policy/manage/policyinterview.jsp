<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
	//接收业务名称URLDecoder
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>走访记录管理</title>
    
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
	.menu, .menu li {
		margin: 0;
		padding: 0;
		height: 20px;
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
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		background: url('/db/page/images/checkdefault.jpg') no-repeat;
	}
	.menu .active {
		width: 86px;
		float: left;
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		color: #FFFFFF;
		background: url('/db/page/images/checkactive.jpg') no-repeat;
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
		font-size: 12px;	
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
		background: url('/db/page/images/titmember.gif');
	}
	.mystyle {		
		font-size: 12px;
		font-weight: bold;	
		color: #666666;		
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
		var empid = "";        //当前登录用户编号 
		var deptid = "";        //当前登录机构 		
		//
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度		
		//
		var selselect = "";    	//选择查询结果字段
		var selfrom = "";    	//选择查询结果表
		var selwhere = "";    	//选择查询结果条件
		var selorder = "";    //选择查询结果排序
		//
		var selfmid = "";	//选择家庭ID
		//
		var seltabwhere = "";    	//选择家庭查询走访
		//
		var xmldata;	//XML数据实体
		//
		var colnum = 0,rownum =0;//列数/行数		
		//
		var sqlcount = "0";//总记录数
		var sqlolepagecount = "0";//旧总页数
		var sqlolepageselect = "";//旧页数选择下拉框
		var sqlpagecount = "0";//总页数
		var sqlpagenum = "18";//每页记录数
		//
		var sqlselpage = 1;//选择页
		//
		var selbegpage = 0;		//分页开始
		var selendpage = sqlpagenum;//分页结束
		//
		//
		var sqltabid = "1";//选择标签ID
		//
		var sqlbegdt = "";//开始日期
		var sqlenddt = "";//结束日期
		//
		
		//条件转换[逻辑条件转换物理条件]
	    function GetPhySql(){	    	
	    	var mode;    
		    var tselect,tfrom,twhere,torder,tmode,tbegpage,tendpage;
		    var tdept,ttabid,tbegdt,tenddt;  
		    //      
		    tselect = selselect;  
		    tfrom = selfrom;
		    twhere = selwhere;
		    torder = selorder;    
		    //家庭走访查询
		    twhere += seltabwhere;	
		    //解析查询模式常量定义模块定义tmode
		    tmode = "1";//模式0执行查询1提取SQL语句
		    tbegpage = selbegpage;
		    tendpage = selendpage;
		    //机构
		    tdept = deptid;
		    //
		    ttabid = sqltabid;
		    tbegdt = sqlbegdt;
		    tenddt = sqlenddt;
		    //
		    mode = "interviewfamilysql";
		    //
		    //		    
			DisplayPageStatus(); 
			//
		    //
		    $.post("page/info/search/TableInfoServlet",      //服务器页面地址
		        {
		            action: "getphysql", //action参数
		            mode: mode,   //参数
		            tselect: tselect,   //参数
		            tfrom: tfrom,   //参数
		            twhere: twhere,   //参数
		            torder: torder, //参数
		            tmode: tmode,
		            tbegpage: tbegpage,
		            tendpage: tendpage,  //参数分页
		            tdept: tdept,
		            ttabid: ttabid,
		            tbegdt: tbegdt,
		            tenddt: tenddt
		        },
		        function(result) {   //回调函数	        	
		        	//加载数据完毕
      				HiddenPageStatus();  	        	
		  			//	        	
		  			//
		          	if(result=="-1"){                
		             	//查询语句不合法          
		          	}else{
		            	if(mode=="interviewfamilysql"){
			            	//走访记录家庭查询取结果physql                  
			              	//			  		
		          			ShowData(result); 		          			
			            }
		          }                             
		    });       
	    }
	   
	    //显示页面状态div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/2+30;  //背景宽度
			//vtop= document.body.clientHeight/2; //背景高度
			vtop= 0;
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
			//
	    	vleft = document.body.clientWidth/2+30;  //背景宽度
			//vtop= document.body.clientHeight/2; //背景高度
			vtop= 0;
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
		//打开走访记录窗体
		function CallIntrViewIdeaDialog(mode,qid,fmno,fmname)
		{
			//单个家庭ID
			var bWidth = document.body.clientWidth-30;  //背景宽度
			//var bHeight= document.body.clientHeight-80; //背景高度
			var bHeight= 300; //背景高度
			var sArgu = "qmode="+mode+"&qid="+qid+"&qfmno="+fmno+"&qfmname="+fmname+"";
			var oUrl = "/db/page/policy/manage/policyinterviewidea.jsp?"+sArgu+"";
			jBox.open("iframe-jBoxID","iframe",oUrl,"家庭走访记录登记","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
				
		}
		//初始化页面
	    function IniPage(){	
	    	//当前登录用户编号    
			empid = "<%=empno%>";     
			//登录机构    
			deptid = "<%=onno%>";    //当前登录机构 
			//
			//
	    	vleft = document.body.clientWidth/2+30;  //背景宽度
			//vtop= document.body.clientHeight/2; //背景高度
			vtop= 0;
			//
			selfrom = "<%=reqfrom%>";		 //查询选择表
			selwhere = "<%=reqwhere%>";		 //查询选择条件	
			selorder = "<%=reqorder%>";		 //查询选择排序
		 	//
			selbegpage = 0;		//分页开始
		 	selendpage = sqlpagenum;//分页结束		 	
		 	//
			//查询显示页面
			QueryInfoTab();			 			
	    }
  	</script>
  </head>
  
  <body onload = "IniPage()">
  		<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
  
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div> 
  		
    	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr>
	   			<td valign="top" class = "myspace" >
	   				<div id = "choicefamilyrequest"></div>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td valign="top" class = "myspace">
	   				<div id = "divrequestdt"></div>			   		
				</td>
	   		</tr>	   		
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div class="requestcon" id="requestcon"> </div></td>	
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
		var html= "",fmid = "",fmno = "",fmname = "";		
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
			if(sqltabid=="2"){//走访查询选中
				html += "<td height='25'>详情</td>";
			}else{
				html += "<td height='25'>走访</td>";
			}
			
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				if(sqltabid=="2"){//走访查询选中
					if(i==3){//走访记录ID列隐藏
						var temp ="<td style='display:none' height='23'>"+ss[1]+"</td>";
						html=html+temp;
					}else{
						var temp ="<td height='25'>"+ss[1]+"</td>";
						html=html+temp;
					}
				}else{
					if(i==5){//机构编号列
						var temp ="<td style='display:none' height='23'>"+ss[1]+"</td>";
						html=html+temp;
					}else if(i==6){//机构名称FULLNAME列
						var temp ="<td height='25'>所属</td>";
						html=html+temp;
					}else{
						var temp ="<td height='25'>"+ss[1]+"</td>";
						html=html+temp;
					}
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
				fmid = row.item(0).firstChild.data;//家庭ID
				fmno = row.item(1).firstChild.data;//家庭编号
				fmname = row.item(2).firstChild.data;//户主姓名
				//
				//
				if(sqltabid=="2"){//走访查询选中
					viewid = row.item(3).firstChild.data;
				}
				//										
				//
				var temp="<tr>";
					if(sqltabid=="2"){//走访查询选中
						temp += "<td width='32px'height='23' class='colValue'>";						
							temp += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"RequestNow(this,'"+viewid+"','"+fmno+"','"+fmname+"','edit')\" />";									
						temp += "</td>";
					}else{
						temp += "<td width='32px'height='23' class='colValue'>";						
							temp += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"RequestNow(this,'"+fmid+"','"+fmno+"','"+fmname+"','add')\" />";									
						temp += "</td>";
					}				
					
					for (var j=1;j<row.length;j++){
						if(sqltabid=="2"){//走访查询选中
							if(j==1){//家庭编号列
								var temp1="<td height='23' class='colValue'>"+
									"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
								temp=temp+temp1;
							}else if(j==3){//走访记录ID列隐藏
								var temp1="<td  style='display:none' height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}else{
								var temp1="<td height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}
						}else{
							if(j==1){//家庭编号列
								var temp1="<td height='23' class='colValue'>"+
									"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
								temp=temp+temp1;
							}else if(j==5){//机构编号列隐藏
								var temp1="<td  style='display:none' height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}else{
								var temp1="<td height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}
						}
						
					}
					//
					temp += "<td width='64px' height='23' class='colValue'>";
						//temp += "<span  class = 'pointer'><img src='/db/page/images/checkfamily.png' alt= '编辑家庭' onclick= \"infoeditaction('"+fmid+"')\"/>&nbsp;</span>";						
						if(sqltabid=="2"){//走访查询选中
							//
						}else{
							temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '走访记录' onclick=\"CallRequestDialog("+fmid+")\"/>&nbsp;</span>";
						}						
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
				html += "<td height='25'>";
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
					  	/*
					  	html += "<select id = 'selectpage' onchange =\"ExePage()\">";
						for(var ipage =1;ipage<=sqlpagecount;ipage++){
					  		html += "<option value=\""+ipage+"\">第"+ipage+"页</option>";
					  	}					
						html += "</select>";
						*/
						//选择页下拉选择框
						html += "<span id = \"spanpages\"></span> ";
					html += "  每页<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>条记录</td>";
				html += "</tr>";
			html=html+"</table>";			
			//
			//			
			requestcon.innerHTML=html;
			//
			//
			//分页选择下拉框
			/*
			if(sqlolepagecount==sqlpagecount){
				spanpages.innerHTML=GetPageSelect("0");
			}else{				
				spanpages.innerHTML=GetPageSelect("1");							
			}
			*/
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
	//选项页下来框
	function GetPageSelect(mode){
		var html;
		if(mode=="0"){//就的分页选择下拉框
			sqlolepagecount = sqlpagecount;
			//
			return sqlolepageselect;
		}else{
			sqlolepagecount = sqlpagecount;			
			//
			html = "<select id = 'selectpage' onchange =\"ExePage()\">";
			for(var ipage =1;ipage<=sqlpagecount;ipage++){
		  		html += "<option value=\""+ipage+"\">第"+ipage+"页</option>";
		  	}					
			html += "</select>";
			//
			sqlolepageselect = html;
			//			
		}
		return sqlolepageselect;
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
		var flag = "1";//首页标识
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//首页
			if(sqlselpage==1){				
				alert("已经是首页!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//上页
			if(sqlselpage==1){
				alert("已经是首页!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//下页
			if(sqlselpage==sqlpagecount){
				alert("已经是尾页!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//页尾
			if(sqlselpage==sqlpagecount){
				alert("已经是尾页!!!");
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
		//
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
	}
</script>
<script type="text/javascript">	
	//
	//日期查询
	function ChoiceDate(){
		sqlbegdt = $("#begdt").val();
		sqlenddt = $("#enddt").val();		
		if(sqlbegdt==""||sqlenddt==""){
			return;
		}
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
	}	
	//单个操作
	function RequestNow(src,fmid,fmno,fmname,mode){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		
		//打开走访记录窗体
 		CallIntrViewIdeaDialog(mode,fmid,fmno,fmname);
	}	
	//打开走访记录查询页面
	function CallRequestDialog(fmid){
		if(selwhere==""){
			seltabwhere ="INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		}else{
			seltabwhere =" and INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		}
		sqltabid = "2";//走访记录查询选中
		//
		document.getElementById("interviewlists").getElementsByTagName('li')[0].className='default';
		document.getElementById("interviewlists").getElementsByTagName('li')[1].className='active';
		//各个标签页选中
		QueryInterView(sqltabid);
	  	selbegpage = 0;		//分页开始
 	  	selendpage = sqlpagenum;//分页结束
 	  	sqlselpage = 1;//开始第一页
 	  	sqlpagenum = "16";//每页记录数	
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
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
<script type="text/javascript">
	//
	//查询显示页面
	function QueryInfoTab(){
		var html = "";
		html = "<ul id='interviewlists' class='menu'>";
		html += "<li id='li1' class='default'>走访登记</li>";
		html += "<li id='li2' class='default'>走访查询</li>";
		html += "</ul>";
		//			
		choicefamilyrequest.innerHTML=html;
		//
		init("interviewlists");
	}
	//各个标签页选中
	function QueryInterView(stabid){
		var html = "";
		if(stabid=="2"){//走访查询选中
			html = "<fieldset>";				
				html += "<table border='0' cellpadding='0' cellspacing='0' width='60%' align='center'>";
					html += "<tr class = 'pageTtems' valign='middle'>";
						html += "<td align='center' valign='middle'>";
							html += "<span>走访日期:</span>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<input id='begdt' type='text' size='15' onFocus='setday(this)'/>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<span>至:</span>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<input id='enddt' type='text' size='15' onFocus='setday(this)'/>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<input type='button' value='查询' onclick='ChoiceDate()'/>";
						html += "</td>";
					html += "</tr>";			
				html += "</table>";			
			html += "</fieldset>";
			//
			requestcon.innerHTML="";			
			divrequestdt.innerHTML=html;	
			//
			sqlpagenum = "16";//每页记录数			
			//
		}else{
			//
			requestcon.innerHTML="";			
			divrequestdt.innerHTML="";	
			//
			sqlpagenum = "18";//每页记录数
			sqlbegdt = "";//开始日期
			sqlenddt = "";//结束日期
			//
			seltabwhere = "";//家庭走访查询
		}			
	}
</script>
<script  type="text/javascript">
	//
	function init(ids){
		document.getElementById(ids).getElementsByTagName('li')[0].className='active';			
		document.getElementById(ids).onclick=function(){onmousOver(ids);}//点击激发效果
		//
	  	selbegpage = 0;		//分页开始
 	  	selendpage = sqlpagenum;//分页结束
 	  	sqlselpage = 1;//开始第一页
		//
		//默认标签选中
		sqltabid = "1";		
		//取得物理查询语句且执行返回XML格式		  
		GetPhySql();
	}
	function onmousOver(ids){		
		o = o || window.event;
		var obj=o.target || o.srcElement;
		if (obj.tagName=='LI'){
		  if (obj.className=='active')return;
		  var o=document.getElementById(ids).getElementsByTagName('li');
		  for (var i=0;i<=o.length-1;i++){o[i].className='default'}
		  obj.className='active';
		  //
		  //2为长度li-1为标签
		  var tid =  obj.id;
		  tid = tid.substring(2,tid.length);
		  //
		  sqltabid = tid;
		  //
		  //各个标签页选中
		  QueryInterView(sqltabid);
		  //
		  selbegpage = 0;		//分页开始
	 	  selendpage = sqlpagenum;//分页结束
	 	  sqlselpage = 1;//开始第一页
		  //取得物理查询语句且执行返回XML格式		  
		  GetPhySql();
		}
	}
</script>
