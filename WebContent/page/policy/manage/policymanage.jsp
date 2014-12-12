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
    
    <title>政策业务定制管理</title>
    
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
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>	
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	
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

	<script type="text/javascript">	
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//	
		var empid = "";        //当前登录用户编号 
		var deptid = "";       //当前登录机构
		//
		var seldeptid;			//选中机构
		var seldeptname;
		var seldeptfullname;
		//
		var selquerydeptmode = "sql";//sql标准选择机构 acc核算选择机构
		// 
		var currPolicyId = "";				//当前业务编号
		var currPolicyName = "";				//当前业务名称
		
		//		
		var currStandardId = "";			    //当前标准编号
		var currStandardDept = "";				//当前标准所属机构
		var currStandardDesc = "";			    //当前标准描述	
		var currStandardMoney = "0";			//当前标准计划金额
		var currStandardAccDesc = "";			//当前标准描述	
		//
		var currDeptId = "";					  //当前机构编号
		var currDeptName = "";					  //当前机构编号
		var currDeptMoney = "0";				  //当前标准金额
		var currDeptAccDesc = "";				  //当前核算公式描述
		
		//取得业务列表
		function GetPolicysHtml() {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicysHtml"             //action参数
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	policylists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>业务属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	policyitems.innerHTML = html;
			    	
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
					//取得业务属性
					GetPolicyItemHtml(currPolicyId);				    					      		
		        }
		    );
		}
		//取得业务属性
		function GetPolicyItemHtml(sid) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicyItemHtml",             //action参数
		            sid: sid         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	policyitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//更新业务
		function SavePolicy(mode){
			//编辑状态
			var EditType = mode;
			//属性
			var pid      = "",pmenu = "",pname = "",pdescr = "",pnote ="",pcycnum  = "";
			var pobjtype = "",ptype = "",pacctu = "",pcycle="",pisprint = "",pbegdt = "",pacctype = "";
		    //
			//
			pmenu = $("#pmenu").val();
			pname = $("#pname").val(); //获取业务名称	
		    pdescr = $("#pdescr").val();
		    pnote = $("#pnote").val();
		    pcycnum = $("#pcycnum").val();
		    
		    pobjtype = $("#pobjtype").val();
		    ptype = $("#ptype").val();
		    pacctu = $("#pacctu").val();
		    pcycle = $("#pcycle").val();		    
		    pbegdt = $("#pbegdt").val();
		    
		    var isprn = document.getElementById("pisprint");
		    if(isprn.checked == false){
		      pisprint = "0";
		    }else{
		      pisprint = "1";
		    }
		   	//
		   	//选择家庭或者成员核算
		    var isrdaccf = document.getElementById("rdaccf");
		    var isrdaccm = document.getElementById("rdaccm");
		    if(isrdaccf.checked == true){	    		
	      	 	pacctype = "0";//家庭核算	               
	      	}else if(isrdaccm.checked == true){
	      		pacctype = "1";//成员核算 
	      	}
		    //为空校验
		    if(pmenu==""){		      
			  DisplayResult("救助业务不能为空!"); //显示操作结果
		      return;
		    }
		    if(pname==""){		      	      
			  DisplayResult("业务名称不能为空!"); //显示操作结果
		      return;
		    }		    	
		    if(pobjtype==""){		      
			  DisplayResult("业务分类不能为空!"); //显示操作结果
		      return;
		    }
		    if(ptype==""){		      
			  DisplayResult("救助类型不能为空!"); //显示操作结果
		      return;
		    }
		    if(pacctu==""){		      
			  DisplayResult("发放机构不能为空!"); //显示操作结果
		      return;
		    }
		    if(pcycle==""){		     
			  DisplayResult("结算周期不能为空!"); //显示操作结果
		      return;
		    }
		    
		    if(pbegdt==""){		      
			  DisplayResult("业务日期不能为空!"); //显示操作结果
		      return;
		    }
		    if(pcycnum==""){		      
			  DisplayResult("业务周期不能为空!"); //显示操作结果
		      return;
		    }
		    
		    //
		    if(EditType=="addpolicy"){
		    	pid = "";			//获取业务编号 
		    	//确认
			    if (!confirm("是否确定新增新业务？")) {
			        return;
			    }			    
		    }else if(EditType=="editpolicy"){
		    	pid = currPolicyId;     //获取业务编号	
			    if(pid==""){			     
				  DisplayResult("没有选择业务,无法更新!"); //显示操作结果
			      return;
			    }
		    	//确认
			    if (!confirm("是否确定更新["+pname+"]该业务属性？")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }
		    //
		  	DisplayPageStatus();		  	
		  	//
		    $.post("page/policy/manage/PolicyManageServlet", //服务器目标页面
		        {
		            action: "updatePolicy",                  //action参数
		            EditType: EditType,				 //参数
		            pid: pid,                      //列表id参数
		            pmenu: pmenu,				 //参数
		            pname: pname,				 //参数
		            pdescr: pdescr,				 //参数
		            pnote: pnote,				 //参数
		            pcycnum: pcycnum,				 //参数		            
		            pobjtype: pobjtype,				 //参数
		            ptype: ptype,				 //参数
		            pacctu: pacctu,				 //参数
		            pacctype: pacctype,			 //参数
		            pcycle: pcycle,				 //参数
		            pisprint: pisprint,				 //参数
		            pbegdt: pbegdt				 //参数
		        },
		        function(result) {                      //回调函数
		        	HiddenPageStatus();	 		        	
		            DisplayResult(result);              //显示操作结果
		            //取得分类施保列表
					GetPolicysHtml();
		        }
		    );
		}		
		//更新业务状态
		function updatePolicyStatus(Id,Name,Status) {
			//
			var smse = "";
			if(Status=="0"){
				smse = "停用";
			}else{
				smse = "启用";
			}
		    //进行业务的非空判断
		    if (Id != "") {		    	
		    	//停用前确认
			    if (!confirm("是否确定"+smse+"["+Name+"]该业务状态？")) {
			        return;
			    }
			    //
			  	DisplayPageStatus();
			  	//
			    $.post("page/policy/manage/PolicyManageServlet",                   //服务器目标页面
			        {
			            action: "updatePolicyStatus",                  //action参数
			            Id: Id,                      //列表id参数
			            Status: Status				 //参数
			        },
			        function(result) {                      //回调函数
			        	//显示提示信息div
						DisplayResult(result);	
						//
						//取得分类施保列表
						GetPolicysHtml();		            
			        }
			    );
		    } else {		        
		        DisplayResult("没有选择业务,无法更新!"); //显示操作结果		       
		    }
		}
		//获取政策标准列表
		function GetPolicySqlsHtml(PId,PName) {
			//
			var JDeptid = "";	//查询机构ID
			//
			JDeptid = $("#sqdeptid").val();
			if("" == JDeptid || null == JDeptid){
				JDeptid = deptid;
			}
		  	DisplayPageStatus();
		  	//
		    $.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
		        {
		            action: "getPolicySqlsHtml",                  //action参数
		            PId: PId,
		            PName: PName, 
		            JDeptid: JDeptid		                                                 
		        },
		        function(result) {   //回调函数	
		          	HiddenPageStatus();	          
		          	//
		        	policysqllists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>标准属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	policysqlitems.innerHTML = html;
			    	
			    	//JS表格排序
					new TableSorter("listsstb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					//
					//取得业务属性
					GetPolicySqlItemHtml(currStandardId);         	          	          		          		                                                  
		        }
		    ); 
		}
		//取得业务属性
		function GetPolicySqlItemHtml(Id) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicySqlItemHtml",             //action参数
		            Id: Id         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	policysqlitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//更新标准状态
		function updateStandardStatus(Id,Name,Status) {
		    //
			var smse = "";
			if(Status=="0"){
				smse = "停用";
			}else{
				smse = "启用";
			}		    
		    if (Id != "") {		    	
		    	//停用前确认
			    if (!confirm("是否确定"+smse+"["+Name+"]该标准状态？")) {
			        return;
			    }
			    //
			  	DisplayPageStatus();
			  	//
			    $.post("page/policy/manage/PolicyManageServlet",//服务器目标页面
			        {
			            action: "updateStandardStatus",//action参数
			            Id: Id,                      //列表id参数
			            Status: Status				 //参数
			        },
			        function(result) {                      //回调函数
			        	HiddenPageStatus();	 			            
			            DisplayResult(result);              //显示操作结果
			            //获取政策标准列表
			            GetPolicySqlsHtml(currPolicyId,currPolicyName);
			            			            		            
			        }
			    );
		    } else {		        
		        DisplayResult("没有选择标准,无法更新!"); //显示操作结果		       
		    }
		}
		//更新标准
		function SaveStandard(mode){
			//编辑状态
			var EditType = mode;
			
			//属性			
			var pid = "",pname = "",sid = "",sdescr = "",sdeptid = "",sdeptname = "";
			var splanmoney = "",splandesc="",ssuperpolicy="",snotpolicy="";			
			
			//
			pid = currPolicyId;	
		    pname = currPolicyName;	 
		    
		    // 
		    sdescr = $("#sdescr").val();; //获取描述		    		    
		    splanmoney = $("#splanmoney").val();
		    splandesc = $("#splandesc").val();
		    ssuperpolicy = $("#ssuperpolicy").val();
		    snotpolicy = $("#snotpolicy").val();
		    //		    			    
		    sdeptid = $("#sdeptid").val();		    		    
		    sdeptname = $("#sdeptname").val();
		    //为空校验
		    if(sdeptname==""){		      
			  DisplayResult("所属机构不能为空!"); //显示操作结果
		      return;
		    }
		    //为空校验
		    if(sdescr==""){		     
			  DisplayResult("标准名称不能为空!"); //显示操作结果
		      return;
		    }
		    if(splanmoney==""){		      
			  DisplayResult("标准数额不能为空!"); //显示操作结果
		      return;
		    }
		    //
		    if(EditType=="addstandard"){
		    	
		    	sid = "";					   //获取编号	
		    	//确认
			    if (!confirm("是否确定新增["+pname+"]标准？")) {
			        return;
			    }			    
		    }else if(EditType=="editstandard"){
		    	sid = currStandardId;     //获取编号	
			    if(sid==""){			      
				  DisplayResult("没有选择标准,无法更新!"); //显示操作结果
			      return;
			    }
		    	//确认
			    if (!confirm("是否确定更新["+pname+"]标准["+sdescr+"]属性？")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }		    
		    //
		  	DisplayPageStatus();
		  	//
		    $.post("page/policy/manage/PolicyManageServlet", //服务器目标页面
		        {
		            action: "updateStandard",                  //action参数
		            EditType: EditType,				 //参数
		            pid: pid,                      //列表id参数
		            sid: sid,				 //参数
		            sdescr: sdescr,				 //参数
		            sdeptid: sdeptid,
		            splanmoney: splanmoney,				 //参数
		            splandesc: splandesc,				 //参数
		            ssuperpolicy: ssuperpolicy,
		            snotpolicy: snotpolicy		            				            
		        },
		        function(result) {                      //回调函数
		        	HiddenPageStatus();
		            DisplayResult(result);              //显示操作结果
		            //获取政策标准列表
	            	GetPolicySqlsHtml(currPolicyId,currPolicyName); 
		        }
		    );
		}
		//获取机构标准列表
		function GetPolicyDeptsHtml(SId,SDeptName,SName,SMoney,SAccDesc) {
			//
		  	DisplayPageStatus();
		  	//
		    $.post("page/policy/manage/PolicyManageServlet",      //服务器页面地址
		        {
		            action: "getPolicyDeptsHtml",                  //action参数
		            SId: SId,
		            SDeptName: SDeptName,
		            SName: SName,
		            SMoney: SMoney,
		            SAccDesc: SAccDesc                                      
		        },
		        function(result) {   //回调函数	
		          	HiddenPageStatus();	          
		          	//
		        	policydeptlists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>标准属性</legend>";	  			
			    	html += "</fieldset>"; 
			    	//属性
			    	policydeptitems.innerHTML = html;
			    	
			    	//JS表格排序
					new TableSorter("listsdtb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					// 
					//取得机构标准属性
					GetPolicyDeptItemHtml(currDeptId);       	          	          		          		                                                  
		        }
		    ); 
		}
		//取得机构标准属性
		function GetPolicyDeptItemHtml(Id) {
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "getPolicyDeptItemHtml",             //action参数
		            Id: Id         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
		        	//属性
			    	policydeptitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//更新机构标准状态
		function updateDeptStatus(Id,Name,Status) {
			//
			var smse = "";
			if(Status=="0"){
				smse = "停用";
			}else{
				smse = "启用";
			}
		    //进行业务的非空判断
		    if (Id != "") {		    	
		    	//停用前确认
			    if (!confirm("是否确定"+smse+"["+Name+"]该机构标准状态？")) {
			        return;
			    }
			    $.post("page/policy/manage/PolicyManageServlet",//服务器目标页面
			        {
			            action: "updateDeptStatus",//action参数
			            Id: Id,                      //列表id参数
			            Status: Status				 //参数
			        },
			        function(result) {                      //回调函数			            
			            DisplayResult(result);              //显示操作结果
			            //获取机构标准列表
						GetPolicyDeptsHtml(currStandardId,currStandardDept,currStandardDesc,currStandardMoney,currStandardAccDesc);
						      
			        }
			    );
		    } else {		       
		        DisplayResult("没有选择机构标准,无法更新!"); //显示操作结果		       
		    }
		}
		//更新机构标准
		function SaveDept(mode){
			//
			sid = currStandardId;	
		    sname = currStandardDesc;			   
			//编辑状态
			var EditType = mode;
			
			var did = "",ddeptid = "",ddeptname = "",dcheckmoney = "",dcheckdesc="",daccdesc="";
			//
			//
			sid = currStandardId;	
		    sname = currStandardDesc;
		    //		    			    
		    ddeptid = $("#ddeptid").val();		    		    
		    ddeptname = $("#ddeptname").val();
		    dcheckmoney = $("#dcheckmoney").val();
		    dcheckdesc = $("#dcheckdesc").val();
		    daccdesc = $("#daccdesc").val();
		    //为空校验
		    if(ddeptname==""){		      
			  DisplayResult("所属机构不能为空!"); //显示操作结果
		      return;
		    }
		    if(dcheckmoney==""){		      
			  DisplayResult("标准数额不能为空!"); //显示操作结果
		      return;
		    }	
		    //	    
	     	if(EditType=="adddept"){
		    	did = "";					   //获取编号
		    	//确认
			    if (!confirm("是否确定新增["+sname+"]新机构标准？")) {
			        return;
			    }			    
		    }else if(EditType=="editdept"){
		    	did = currDeptId;     //获取编号	
			    if(did==""){			      
				  DisplayResult("没有选择机构标准,无法更新!"); //显示操作结果
			      return;
			    }
		    	//确认
			    if (!confirm("是否确定更新["+sname+"]机构标准["+ddeptname+"]属性？")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }
   			//
   			$.post("page/policy/manage/PolicyManageServlet", //服务器目标页面
	        {
	            action: "updateDept",                  //action参数
	            EditType: EditType,				 //参数
	            sid: sid,                //列表id参数
	            did: did,				 //参数
	            ddeptid: ddeptid,				 //参数				            		            
	            dcheckmoney: dcheckmoney,				 //参数
	            dcheckdesc: dcheckdesc,				 //参数
				daccdesc: daccdesc				 //参数					            
	        },
	        function(result) {                      //回调函数				        	
	            DisplayResult(result);              //显示操作结果
	            //获取机构标准列表
				GetPolicyDeptsHtml(currStandardId,currStandardDept,currStandardDesc,currStandardMoney,currStandardAccDesc);
	        }
	    );
		}
		//选择所属业务
		function ChoiceSupPolicy() {
			var ispolicy;
			var spname = $("#ssuperpolicy").val();
			//
			ispolicy = document.getElementById("listspolicy");
			if(ispolicy ==null){
				return;
			}
			//
			var selpname = listspolicy.options[listspolicy.selectedIndex].text;
			var st = spname;
			//
			if(spname==""){
	      		$("#ssuperpolicy").val(selpname);
	      	}else{
		      	if(MatchMatch(st,selpname)==null){                              
		        	if (st.length>0){
		        		$("#ssuperpolicy").val(spname+","+selpname);           
		        	} 
		      	}
	      	} 		    
		}
		//置空所属业务
		function ClearSupPolicy() {
			$("#ssuperpolicy").val(""); 		    
		}
		//选择不属业务
		function ChoiceNotPolicy() {
			var ispolicy;
			var spname = $("#snotpolicy").val();
			//
			ispolicy = document.getElementById("listsnotpolicy");
			if(ispolicy ==null){
				return;
			}
			//
			var selpname = listsnotpolicy.options[listsnotpolicy.selectedIndex].text;
			var st = spname;
			//
			if(spname==""){
	      		$("#snotpolicy").val(selpname);
	      	}else{
		      	if(MatchMatch(st,selpname)==null){                              
		        	if (st.length>0){
		        		$("#snotpolicy").val(spname+","+selpname);           
		        	} 
		      	}
	      	} 		    
		}
		//置空不属业务
		function ClearNotPolicy() {
			$("#snotpolicy").val(""); 		    
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
			//取得业务保列表
			GetPolicysHtml();			
		}		
	</script>
	
  </head>
  
  <body onLoad="IniPage()">
  	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
  	
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  	<div id='resultstatusdiv'></div>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
    	<tr align="center" id = "policytr">
    		<td valign="top" width="50%"><div id = 'policylists'></div></td>	    		
    		<td valign="top"><div id = 'policyitems'></div></td>
    	</tr>
    	<tr align="center" id = "policysqltr">
    		<td valign="top" width="50%"><div id = 'policysqllists'></div></td>	    		
    		<td valign="top"><div id = 'policysqlitems'></div></td>
    	</tr>
    	<tr align="center" id = "policydepttr">
    		<td valign="top" width="50%"><div id = 'policydeptlists'></div></td>	    		
    		<td valign="top" width="50%"><div id = 'policydeptitems'></div></td>
    	</tr> 	
   	</table> 
  </body>
  <script type='text/javascript'>
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
	//上下步页面控制
	function GoMatchPage(mode){
		if(mode=="next"){
			$("#policytr").css({"display":"none"});
			$("#policysqltr").css({"display":"block"});
			//获取政策标准列表
			GetPolicySqlsHtml(currPolicyId,currPolicyName);
			//
		}else if(mode=="back"){
			$("#policytr").css({"display":"block"});
			$("#policysqltr").css({"display":"none"});
		}else if(mode=="nextsql"){
			$("#policydepttr").css({"display":"block"});
			$("#policysqltr").css({"display":"none"});
			//获取政策机构标准列表
			GetPolicyDeptsHtml(currStandardId,currStandardDept,currStandardDesc,currStandardMoney,currStandardAccDesc); 
		}else if(mode=="backsql"){
			$("#policysqltr").css({"display":"block"});
			$("#policydepttr").css({"display":"none"});
			//获取政策标准列表
	        GetPolicySqlsHtml(currPolicyId,currPolicyName); 
		}
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
		currPolicyId = id;
		currPolicyName = name;
		//
		//取得业务属性
		GetPolicyItemHtml(currPolicyId);
	}
	//单个操作
	function ChioceDoSql(src,id,deptname,name,money,accdesc){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		currStandardId = id;
		currStandardDept = deptname;
		currStandardDesc = name;
		currStandardMoney = money;
		currStandardAccDesc = accdesc;
		//
		//取得标准属性
		GetPolicySqlItemHtml(currStandardId);
	}
	//单个操作
	function ChioceDoDept(src,id,name,money,accdesc){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		currDeptId = id;
		currDeptName = name;
		currDeptMoney = money;
		currDeptAccDesc = accdesc;
		//
		//取得标准机构属性
		GetPolicyDeptItemHtml(currDeptId);
	}
	//业务审批流程定义
	function policyFlowDefine(){
		//
		if(currPolicyId==""||currPolicyId==null){
			DisplayResult("提示:没有选择当前业务!");				
			return;
		}		
		//var bWidth = document.body.clientWidth-30;  //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bWidth = document.body.clientWidth*0.6;  //背景宽度
		var bHeight= document.body.clientHeight*0.6; //背景高度
		var sArgu = "qpid="+currPolicyId+"&qpname="+currPolicyName+"";
		var oUrl = "/db/page/policy/manage/policycheckflow.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务审批流程定义","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//业务审批权限定义
	function policyCheckPower(){
		//
		if(currPolicyId==""||currPolicyId==null){
			DisplayResult("提示:没有选择当前业务!");				
			return;
		}		
		//var bWidth = document.body.clientWidth-30;  //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bWidth = document.body.clientWidth*0.6;  //背景宽度
		var bHeight= document.body.clientHeight*0.6; //背景高度
		var sArgu = "qpid="+currPolicyId+"&qpname="+currPolicyName+"";
		var oUrl = "/db/page/policy/manage/policycheckpower.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务审批权限定义","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//业务相关信息设置
	function policyInfo(){
		//
		if(currPolicyId==""||currPolicyId==null){
			DisplayResult("提示:没有选择当前业务!");				
			return;
		}		
		var bWidth = document.body.clientWidth-30;  //背景宽度
		//var bHeight= document.body.clientHeight-80; //背景高度
		var bHeight= 400; //背景高度
		var sArgu = "qpid="+currPolicyId+"&qpname="+currPolicyName+"";
		var oUrl = "/db/page/policy/manage/policymanageinfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务相关信息设置","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//打开业务标准设置页面
	function sqlaction(){		
		//
		var suppname = "",notpname = "",acctype = "";
		if(currStandardId==""){
			DisplayResult("没有选择业务标准,无法设置条件!");
			return; 
		}
		suppname= $("#ssuperpolicy").val();	
		notpname= $("#snotpolicy").val();		
		//
	   	//选择家庭或者成员核算
	    var isrdaccf = document.getElementById("rdaccf");
	    var isrdaccm = document.getElementById("rdaccm");
	    if(isrdaccf.checked == true){	    		
      	 	acctype = "family";	               
      	}else if(isrdaccm.checked == true){
      		acctype = "member";	 
      	}	
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var sArgu = "qid="+currStandardId+"&qname="+currStandardDesc+"&qsuppname="+suppname+"&qnotpname="+notpname+"&qacctype="+acctype+"";
		var oUrl = "/db/page/policy/manage/policymanagesql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"业务标准设置","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}	
	//打开机构标准设置页面
	function deptaction(){		
		//
		if(currDeptId==""){
			DisplayResult("没有选择机构标准,无法设置条件!");
			return; 
		}
		//
	   	//选择家庭或者成员核算
	    var isrdaccf = document.getElementById("rdaccf");
	    var isrdaccm = document.getElementById("rdaccm");
	    if(isrdaccf.checked == true){	    		
      	 	acctype = "family";	               
      	}else if(isrdaccm.checked == true){
      		acctype = "member";	 
      	}			
		var bWidth = document.body.clientWidth-30;   //背景宽度
		var bHeight= document.body.clientHeight-80; //背景高度		
		var sArgu = "qid="+currDeptId+"&qname="+currDeptName+"&qmoney="+currDeptMoney+"&qaccdesc="+currDeptAccDesc+"&qacctype="+acctype+"";					
		var oUrl = "/db/page/policy/manage/policymanagedeptsql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"核算公式设置","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
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
                    oWidth:250,                              //弹出窗口宽度
                    oHeight:280                              //弹出窗口高度
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
		
		$(function(){
	        //秀秀
	        $("button").css({"display":"block","margin":"10px 0","width":"120px"});
	        $("button:first").css("background","red");
		});
		//查询机构标准
		function queryDept(mode){
			selquerydeptmode = mode;
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
		    if(selquerydeptmode=="qsql"){
		    	//选择调用参数
			    $("#sqdeptid").val(seldeptid);
			    $("#sqdeptname").val(seldeptfullname);
		    }else if(selquerydeptmode=="sql"){
		    	//选择调用参数
			    $("#sdeptid").val(seldeptid);
			    $("#sdeptname").val(seldeptfullname);
		    }else{
		    	//选择调用参数
			    $("#ddeptid").val(seldeptid);
			    $("#ddeptname").val(seldeptfullname);
		    }
		    
			closedept();
		}
		//置空机构选择[DeptTreeServlet成生的方法]
		function cleardept(){
			if(selquerydeptmode=="qsql"){
		    	//选择调用参数
			    $("#sqdeptid").val("");
			    $("#sqdeptname").val("");
		    }else if(selquerydeptmode=="sql"){
		    	//选择调用参数
			    $("#sdeptid").val("");
			    $("#sdeptname").val("");
		    }else{
		    	//选择调用参数
			    $("#ddeptid").val("");
			    $("#ddeptname").val("");
		    }
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
</html>
