<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//
	//
	//从查询表接收qmode
	String reqmode = request.getParameter("qmode");	
	if (reqmode == null) {
		//默认为空
		reqmode = "edit";    //编辑状态
	}
	//从查询表接收qid
	String reqid= request.getParameter("qid");		
	if (reqid == null) {
		//默认为空
		reqid = "";    //空ID
	}	
	//从查询表接收qfmno
	String reqfmno = request.getParameter("qfmno");	
	if (reqfmno == null) {
		//默认为空
		reqfmno = "";    //空家庭编号
	}
	//从查询表接收qid
	String reqfmname= request.getParameter("qfmname");
	reqfmname = new String(reqfmname.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqfmname == null) {
		//默认为空
		reqfmname = "";    //空户主姓名
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
    
    <title>走访记录填写管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery.js"></script>
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
	
	<style type="text/css">
 <!--
  .table4 {
		border-collapse: collapse;
		border: 1px solid #999;
		border-width: 1px 0 0 1px;
		margin: 2px 0 2px 0;
		text-align: center;
	}
	
	.table4 td {
		height: 23px;
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		font-size: 12px;
		background-color: #FFF;
		text-align: center;
	}
	
	.table4 th {
		height: 20px;		
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		text-align: center;
		font-size: 12px;		
		font-weight: 600;
		color: #FFFFFF;
		background-color: #6BA6FF;
	}
	.tablexiu {
		border-collapse: collapse;
		border: 1px solid #999;
		border-width: 1px 0 0 1px;
		margin: 2px 0 2px 0;
		text-align: left;
		background-color: #fff
	}
	
	.tablexiu td {
		height: 20px;
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		font-size: 12px;
		text-align: left;
		background-color: #fff
	}
	
	.tablexiu th {
		height: 20px;		
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		text-align: center;
		font-size: 12px;
		color: #000000;
		background: url('/db/page/images/titmember.gif');
	}
	.pointer {
		cursor:pointer;
	}
	body {
		margin: 0;
		padding: 0;
		font-family: "宋体";
		font-size: 12px;
	}
	#pagestatusdiv{		
		z-index:2;		
		font-weight: bold;
		color: #FF0099;
		font-size:16px;
		display:none;
	}
	#resultstatusdiv{		
		z-index:2;
		font-weight: bold;
		color: #FF0099;
		font-size:16px;
		display:none;
	}
	-->
  </style>
  <script  type="text/javascript">
		//
		//
		var empid = "";        //当前登录用户编号 
		var deptid = "";        //当前登录机构 
		//
		var mode = "",fmid = "",viewid = "",fmno = "",fmname = "";	
		//		
		//获取走访记录属性
		function GetInterviewIdeaItem() {		
			//		    
			DisplayPageStatus(); 
			//	
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getInterviewIdeaItem",             //action参数		            
		            viewid: viewid		            
		        },
		        function(result) {                    //回调函数    
	        		//加载数据完毕
     					HiddenPageStatus();  
     					//      					 
     					//结果转换成JSONArray	        	
        			var json = eval(result); 
    					//   
	                $(json).each(function(i) {      //遍历结果数组		                
		                //
		                viewid = json[i].iid;
		                fmid = json[i].ifmid;
		                $("#viewperson").val(json[i].iperson);     //获取		                	    
					    $("#viewdt").val(json[i].idt);     //获取				    
					    $("#viewresult").val(json[i].iresult);     //获取
					    $("#viewtype").val(json[i].itype);     //获取
					    $("#viewdept").val(json[i].ideptname);     //获取			    		                   
		            });		            
		         }   
		    );
		}
		//设置走访记录属性	
		function SetInterviewIdeaItem(){
			//
			var iperson = "",idt = "",iresult = "",itype = "",ideptid = "",operid = "";
			//
			iperson = $("#viewperson").val();
			idt = $("#viewdt").val();
			iresult = $("#viewresult").val();
			itype = $("#viewtype").val();
			ideptid = $("#viewdeptid").val();
			operid = empid;
			
			//			
			if(fmid ==""){
				alert("没有选择家庭!");
				return;
			}
			//
			if(iperson == ""){
				alert("没有填写走访人!");
				return;
			}
			//						
			if(idt == ""){
				alert("没有填写日期!");
				return;
			}
			//						
			if(ideptid == ""){
				alert("没有填写走访机构!");
				return;
			}
			//			
			if(iresult == ""){
				alert("没有填写走访结果!");
				return;
			}
			//确认
		    if (!confirm("是否确定添加走访记录？")) {
		        return;
		    }
			//
			//		    
			DisplayPageStatus(); 
		 	//
		   	$.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
		       	{
		           	action: "setInterviewIdeaItem" , //action参数         
		           	fmid: fmid,
		           	iperson: iperson,
		           	idt: idt,
		           	iresult: iresult,
		           	itype: itype,
		           	ideptid: ideptid,
		           	operid: operid                             
		       	},
		      	function(result) {   //回调函数
		      		//加载数据完毕
      				HiddenPageStatus(); 
      				//
      				$("#btnrequest").attr("disabled", "disabled");
      				DisplayResult(result);			  		     	                      
		     }
		   );
		}
		
		//显示页面状态div
		function DisplayPageStatus() {
		    var pagestatusdiv = $("#pagestatusdiv");            //获取提示信息div		        
		    $(pagestatusdiv).fadeIn();                      //淡入信息		    
		}		
		//隐藏页面状态div
		function HiddenPageStatus() {
		    $("#pagestatusdiv").fadeOut();                  //淡出信息
		}
		//显示提示信息div
		function DisplayResult(info) {
		    var resultstatusdiv = $("#resultstatusdiv");            //获取提示信息div
		    resultstatusdiv.html(info);                       //设置div内文本		    
		    $(resultstatusdiv).fadeIn();                      //淡入信息
		    //setTimeout("HiddenResult()",2000);          //2秒后隐藏信息
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
			mode = "<%=reqmode%>";		 //模式			
			if(mode=="edit"){//编辑状态
		    	//申请ID必须为单个	
		    	viewid = "<%=reqid%>";		 //ID   	
		    	$("#btnrequest").css({"display":"none"});		    	
		    	//获取走访记录属性
				GetInterviewIdeaItem();
		    }else{
				//家庭ID必须为单个
		    	fmid = "<%=reqid%>";		 //ID		    		    
		    } 
		    fmno = "<%=reqfmno%>";		 //家庭编号
		    fmname = "<%=reqfmname%>";		 //户主姓名 	 	
			//
			$("#viewperson").get()[0].focus(); 	   
	    }		
  </script> 
  </head>
  
  <body onload = "IniPage()">
  		<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>  		
  		
  		<table class="tablexiu" width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td height = '23'style="color: #000000;background: url('/db/page/images/titmember.gif');text-align:center;font-size:12px;">当前走访记录填写:家庭编码[<%=reqfmno%>]户主姓名[<%=reqfmname%>]</td>
	   		</tr>
   		</table>  
  		<table class="tablexiu" width="100%" border="0" cellspacing="0" cellpadding="0">	    	
	    	<tr align="center">
	    		<td width = "100px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">走访人:</td>
	    		<td width="30%"><input style = "width:100%" type="text" id = "viewperson"></input></td>
	    		<td width = "80px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">走访日期:</td>
	    		<td><input style = "width:100%" type="text" name="requestideadt" id="viewdt" onFocus="setday(this)"></input></td>
	    	</tr>
	    	<tr align="center">
	    		<td width = "100px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">走访内容:</td>
	    		<td width="30%"><input style = "width:100%" type="text" id = "viewtype"></input></td>
	    		<td width = "80px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">走访机构:</td>
	    		<td><div style = "width:100%"><input style = "width:100%" type="text" id = "viewdept" onclick="queryDept()"></input><input style = "display:none" type="text" id = "viewdeptid"></input></div></td>
	    	</tr>	    	    	
	    	<tr align="center">
	    		<td width = "100px" style="color: #6000000;background-color:#ECECEC;text-align:right;font-size:12px;">走访结果填写:</td>
	    		<td colspan = "3"><textarea style = "width:100%" rows="5" id = "viewresult"></textarea></td>
	    	</tr>    	
    	</table>
    	<div align="center">
    		<input id = "btnrequest" type="button" value=" 确 定 "onclick="SetInterviewIdeaItem()"></input>
    		<!-- <input id = "btnclose" type="button" value=" 关 闭 "onclick="window.close();"></input> -->
    	</div>    	
    
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table>
   		<table width="100%" border="0" cellspacing="0" cellpadding="0">
   		<tr align="center">
   			<td><div align="center" id='resultstatusdiv'></div></td>
   		</tr>
  	</table>    		
  </body>
</html>
<script type="text/javascript">
  	//=================================AJAX模态窗体=================================	
	var seldeptid;		//选中机构
	var seldeptname;
	var seldeptfullname;	
	//	
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
                   oWidth:250,                              //弹出窗口宽度
                   oHeight:220                              //弹出窗口高度
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
	
	$(function(){
        //秀秀
        $("button").css({"display":"block","margin":"10px 0","width":"120px"});
        $("button:first").css("background","red");
	});
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
	    $("#viewdeptid").val(seldeptid);
	    $("#viewdept").val(seldeptfullname);	
		closedept();
	}
	//置空机构选择[DeptTreeServlet成生的方法]
	function cleardept(){
		//选择调用参数
	    $("#viewdeptid").val("");
	    $("#viewdept").val("");	
	    //
		closedept();	
	}
	//关闭机构选择[DeptTreeServlet成生的方法]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();
	}		
	//=================================AJAX模态窗体=================================
</script>
