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
    
    <title>审批评议人管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>	
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	
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
		var sexselectdiv = "";//性别选择框Html
		var faceselectdiv = "";//政治面貌选择框Html
		//
		selid = "";//选中ID
		selname = "";//选中名称
		//
		//取得审批评议人列表
		function GetCheckPersons() {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/querymanage/QueryManageServlet",            //服务器页面地址
		        {
		            action: "getCheckPersons",             //action参数
		            sdeptid: deptid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//审批评议人列表
					ShowDate(result); 					      		
		        }
		    );
		}
		//取得审批评议人属性
		function getCheckPersonItems(sid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/querymanage/QueryManageServlet",            //服务器页面地址
		        {
		            action: "getCheckPersonItems",             //action参数
		            sid: sid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//审批评议人属性
					ShowDateItems(result,"edit");        		
		        }
		    );
		}
		//更新审批评议人状态
		function UpdateCheckPersonStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "是否确定停用该评议人";
			}else{
				mess = "是否确定启用该评议人";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/querymanage/QueryManageServlet",            //服务器页面地址
		        {
		            action: "updateCheckPersonStatus",             //action参数
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);	
					//
					//取得审批评议人列表
					GetCheckPersons();					  		
		        }
		    );
		}
		//保存操作
		function SaveCheckPerson(mode){
			var vid,vname,vsex,vofficename,vofficephone,vface,vpost,vaddress,vstatus;
			var mess = "";
			//
			if(mode=="add"){//新增
				mess = "是否确定保存新增审批评议人";
			}else if(mode=="edit"){//编辑
				if(selid==""){
					mess = "没有选中审批评议人,无法保存";
					return;
				}
				mess = "是否确定保存更新审批评议人";
			}
			//
    		//各属性值
	    	vname = $("#vname").val();
	    	vsex = $("#sexselect").val();//性别下拉框
	    	vface = $("#faceselect").val();//政治面貌下拉框
	    	vofficename = $("#vofficename").val();
	    	vofficephone = $("#vofficephone").val();    	
	    	vpost = $("#vpost").val();
	    	vaddress = $("#vaddress").val();
	    	
    		if(vname==""){
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
		    $.post("page/querymanage/QueryManageServlet",            //服务器页面地址
		        {
		            action: "updateCheckPerson",             //action参数
		            mode: mode,
		            vdeptid: deptid,
		            vid: selid,
		            vname: vname,
		            vsex: vsex,
		            vface: vface,
		            vofficename: vofficename, 
		            vofficephone: vofficephone,
		            vpost: vpost,
		            vaddress: vaddress       
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);
					//
					//取得审批评议人列表
					GetCheckPersons();				  		
		        }
		    );
		}		
		
		//取得字典下拉框选择
		function GetDiscionarySelect(sname,sdiscid,smode) {
		    //			
		    $.post("page/querymanage/QueryManageServlet",      //服务器页面地址
		        {
		            action: "getDiscionarySelect", //action参数
		            sname: sname, //参数
		            sdiscid: sdiscid //参数	            	            
		        },
		        function(result) {   //回调函数
		        	if(smode=="sex"){//性别
		        		sexselectdiv = result;	
		        	}else if(smode=="face"){//政治面貌
		        		faceselectdiv = result;	
		        	}
		        		        		        	        	   	
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
			//取得字典下拉框选择
			GetDiscionarySelect("sexselect","1","sex");//性别
			//取得字典下拉框选择
			GetDiscionarySelect("faceselect","182","face");//政治面貌
			//取得审批评议人列表
			GetCheckPersons();
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">  		
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top" width="50%"><div id = 'checkpersonlists'></div></td>	    		
	    		<td valign="top"><div id = 'checkpersonitems'></div></td>
	    	</tr>	    	
    	</table>  
  </body>
</html>
<script type="text/javascript">	
	//审批评议人列表
	function ShowDate(sdate){		
		var html,temp;
		var vid,vname,vsex,vofficename,vofficephone,vstatus;
    	//
    	html = "<fieldset  class='list'>";
			html += "<legend  class='legend'>审批评议人属性</legend>";	  			
    	html += "</fieldset>"; 
    	//属性
    	checkpersonitems.innerHTML = html; 
    	//
    	html = "<fieldset  class='list'>";
			html += "<legend  class='legend'>审批评议人列表</legend>";
			html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
			html += "<tr class='colField'>";
				temp ="<td height='23'>操作</td>";
				html +=temp;
				temp ="<td height='23'>名称</td>";
				html +=temp;
				temp ="<td height='23'>性别</td>";
				html +=temp;
				temp ="<td height='23'>联系电话</td>";
				html +=temp;
				temp ="<td height='23'>单位名称</td>";
				html +=temp;
				temp ="<td height='23'>状态</td>";
				html +=temp;
			html +="</tr>";	
			//结果转换成JSONArray	        	
	   		var json = eval(sdate);
			//         
	        $(json).each(function(i) {      //遍历结果数组	
	        	//
	        	vid = json[i].vid;
	        	vname = json[i].vname;
	        	vsex = json[i].vsex;
	        	vofficename = json[i].vofficename;
	        	vofficephone = json[i].vofficephone;
	        	vstatus = json[i].vstatus;
	        	//
	        	if(vofficename==null){vofficename = "无";}
	        	if(vofficephone==null){vofficephone = "无";}	        	
	        	//
				html +="<tr>";		
		    		//各列值	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+vid+"','"+vname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+vstatus+"'>"+vname+"</td>";
					html += "<td height='23' class='colValue'>"+vsex+"</td>";
					html += "<td height='23' class='colValue'>"+vofficephone+"</td>";
					html += "<td height='23' class='colValue'>"+vofficename+"</td>";
					if(vstatus=="1"){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdateCheckPersonStatus('"+vid+"','0')\">停用</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdateCheckPersonStatus('"+vid+"','1')\">启用</button></td>";	
					}
							
		    	html +="</tr>";
	        });		
	    	//
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"ShowDateItems('','add')\">新增审批评议人</button>";    	
    	//列表
    	checkpersonlists.innerHTML = html;
    	//
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
	//审批评议人属性
	function ShowDateItems(sdate,mode){		
		var html,temp;
		var vid,vname,vsex,vofficename,vofficephone,vface,vpost,vaddress,vstatus;
    	// 
    	if(mode=="add"){//新增    		
    		selid = "";
    		selname = "";
    		var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "img"){
					imgs[i].src="/db/page/images/check1.jpg";
				}
			}
    	}else if(mode=="edit"){//编辑
    		
    	}   	    	
    	// 
		if(!sdate==""){
			//结果转换成JSONArray	        	
	   		var json = eval(sdate);
			//         
	        $(json).each(function(i) {      //遍历结果数组	
	        	//
	        	vid = json[i].vid;
	        	vname = json[i].vname;
	        	vsex = json[i].vsex;
	        	vofficename = json[i].vofficename;
	        	vofficephone = json[i].vofficephone;
	        	vface = json[i].vface;
	        	vpost = json[i].vpost;
	        	vaddress = json[i].vaddress;
	        	vstatus = json[i].vstatus; 	
	        	//	
	        });
		}		
    	//
	    html = "<fieldset  class='list'>";
	    	html += "<legend  class='legend'>审批评议人属性</legend>";
			html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
			html += "<tr class='colField'>";
				temp ="<td height='23'>属性</td>";
				html +=temp;
				temp ="<td height='23'>属性值</td>";
				html +=temp;
			html +="</tr>";				
				html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>名称</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vname'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>性别</td>";
					html += "<td><div id = 'sex'></div></td>";
		    	html +="</tr>";		    	
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>政治面貌</td>";
					html += "<td><div id = 'face'></div></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>单位名称</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vofficename'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>联系电话</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vofficephone'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>职务</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vpost'></input></td>";
		    	html +="</tr>";		    	
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>住址</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vaddress'></input></td>";
		    	html +="</tr>";		    	
	    	//
	    	html +="</table>";
	    	
	    	if(mode=="add"){//新增
		    	html += "<button class = 'btn' onclick=\"SaveCheckPerson('"+mode+"')\">保存</button>"; 	   		
	    	}else if(mode=="edit"){//编辑
	    		//在用时可以设置
		    	if(vstatus=="1"){
		    		html += "<button class = 'btn' onclick=\"SaveCheckPerson('"+mode+"')\">保存</button>";
		    	}
	    	}
	    //	
	    html += "</fieldset>";    	
    	//列表
    	checkpersonitems.innerHTML = html;
    	//性别选择下拉框
    	sex.innerHTML = sexselectdiv;
    	//政治面貌选择下拉框
    	face.innerHTML = faceselectdiv;
    	//各属性值
    	$("#vname").val(vname);
    	$("#sexselect").val(vsex);//性别下拉框
    	$("#faceselect").val(vface);//政治面貌下拉框
    	$("#vofficename").val(vofficename);
    	$("#vofficephone").val(vofficephone);    	
    	$("#vpost").val(vpost);
    	$("#vaddress").val(vaddress);
    	
	}
	//单个操作
	function ChioceDo(src,id,name){
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
		//
		//取得审批评议人属性
		getCheckPersonItems(selid);
	}
</script>