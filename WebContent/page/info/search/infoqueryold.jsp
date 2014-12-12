<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session
			.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%
	//查询窗体模式
	String reqmode = request.getParameter("qmode");
	if (reqmode == null) {
		//默认为信息查询
		reqmode = "1";//常量定义类
	}
	//业务选择
	String reqpolicy = request.getParameter("qpolicy");

	if (reqpolicy == null) {
		//默认为空
		reqpolicy = "-1"; //选择业务[不选]
	}
	//业务审批标准选择
	String reqcheck = request.getParameter("qcheck");
	if (reqcheck == null) {
		//默认为空
		reqcheck = "-1"; //选择业务审批标准[不选]
	}	
	//业务审批进度选择
	String reqflow = request.getParameter("qflow");
	if (reqflow == null) {
		//默认为空
		reqflow = "-1"; //选择业务审批进度[不选]
	}
	//业务终审选择
	String reqresult = request.getParameter("qresult");
	if (reqresult == null) {
		//默认为空
		reqresult = "-1"; //选择业务终审[不选]
	}
	//业务所属选择
	String reqflag = request.getParameter("qflag");
	if (reqflag == null) {
		//默认为空
		reqflag = "-1"; //选择业务所属[不选]
	}
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>居民信息查询</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->

	<script type="text/javascript" src="page/js/jquery.js"></script>
	<script type="text/javascript" src="page/js/dynamictree.js"></script>

<style type="text/css">
<!--
body {
	margin: 0;
	margin-top: 0;
	padding: 0;
	background: #FCDAD5;
	font-family: "宋体";
	font-size: 12px;
}

input {
	font-family: "宋体";
	font-size: 12px;
}

.pointer {
	cursor: pointer;
}

.caption {
	font-size: 12px;
	color: #6BA6FF;
}

.itemstyle {
	font-size: 12px;
	color: #6BA6FF;
}

.queryform {
	line-height: 2;	
	height: 20px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	text-align: center;
	font-size: 12px;
	color: #660099;	
	background: url('/db/page/images/newscenter.gif');
}

#pagestatusdiv {	
	z-index: 2;	
	font-weight: bold;
	color: #FF0099;
	font-size: 14px;
	display: none;
}
-->
</style>

  <script type="text/javascript">  
    //创建机构树机构      
    var mode;      //调用模块参数
    var empid = "";        //当前登录用户编号 
    var qmode = "";        //当前查询设置窗体模式code   
            
    var deptid = "";        //当前登录机构 
    var deptfieldname = "";    //当前机构字段     
    var deptobjid = "";      //当前机构选中对象    
    var seldeptid;    //选中机构
    var seldeptname;
    var seldeptfullname;
        
    var selselect = "";    //选择查询结果字段
    var selfrom = "";    //选择查询结果表
    var selwhere = "";    //选择查询结果条件
    var selorder = "";    //选择查询结果排序
    
    //业务查询下拉框ID 
    var listpolicy = "selectpolicy";//业务选择下拉框ID
    var listpolicycheck = "selectpolicycheck";//业务审批标准选择下拉框ID    
    var listpolicyflow = "selectpolicyflow";//业务审批进度选择下拉框ID
    var listpolicyresult = "selectpolicyresult";//业务终审选择下拉框ID
    var listpolicyflag = "selectpolicyflag";//业务所属选择下拉框ID
    
    
    var selpolicy = "";    	//选择业务
    var selpolicycheck = "";    //选择业务审批标准    
    var selpolicyflow = "";    //选择业务审批进度
    var selpolicyresult = "";    //选择业务终审
    var selpolicyflag = "";    //选择业务所属
    
    
    //=================================正则表达式BEG================================
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
    //正则表达式查找
    //返回第一个匹配位置
    function MatchSearch(str,s)
    {
      var r, re; // 声明变量。
      if (str == null){
        return;    
      }
      //g （全文查找出现的所有 pattern） 
      //i （忽略大小写） 
      //m （多行查找）
      //re   =   /s/gi;  
      re = new RegExp(s,"gi"); // 创建正则表达式对象。
      r = str.search(re); // 在字符串 str 中查找匹配。
      return (r);  //匹配的位置
    }
    //正则表达式转换
    //返回替换后的新字符串
    function MatchReplace(str,olestr,newstr)
    {
      var r, re; // 声明变量。      
      //g （全文查找出现的所有 pattern） 
      //i （忽略大小写） 
      //m （多行查找）
      //re   =   /olestr/gi;      
      re   =  new RegExp(olestr,"gi"); // 创建正则表达式对象。                 
      r = str.replace(re,newstr);
      return(r);     
    }
    //=================================正则表达式END================================
    
    //==================================字符去掉首尾空格BEG=================================
  
    //1本函数用于对sString字符串进行前空格截除    
    function  JHshLTrim(sString){         
      var   sStr,i,iStart,sResult="";  
      sStr = sString.split("");   
      iStart = -1   ;   
      for (i=0;i<sStr.length;i++){   
        if (sStr[i]!=" "){   
          iStart = i;   
          break;   
        }   
      }   
      if (iStart == -1){  
        //表示sString中的所有字符均是空格,则返回空串  
        return "";
      }          
      else {   
        return sString.substring(iStart)   ;
      }   
    } 
    //2   本函数用于对sString字符串进行后空格截除
    function JHshRTrim(sString){     
      var  sStr,i,sResult = "",sTemp = "";   
        
        if (sString.length  == 0) { 
          return   "";
        }   //   参数sString是空串   
      sStr =  sString.split(""); 
      //将字符串进行倒序  
      for (i = sStr.length - 1;i>=0;i--)   
      {     
        sResult = sResult + sStr[i];     
      } 
      //进行字符串前空格截除   
      sTemp = JHshLTrim(sResult);    
      if (sTemp == "") {
        return  "";
      }     
      sStr  =  sTemp.split("");   
      sResult  = ""; 
      //将经处理后的字符串再进行倒序  
      for (i = sStr.length - 1;i >= 0;i--)   
      {   
        sResult = sResult + sStr[i];   
      }   
      return  sResult;   
    }  
    //3本函数用于对sString字符串进行前后空格截除
    function JHshTrim(sString)   
    {   
      var strTmp;  
      strTmp = JHshRTrim(JHshLTrim(sString));
      return strTmp;   
    }
    //=================================字符去掉首尾空格END===========================
    //执行查询
    function ExeSQL(){   
		var oForm,Els,i,iLen,oAtt,ObjId;
		var tvalueid,tqueryid,ttableid,tVvalue,tQvalue,tTvalue,tRvalue,ttxt,tid;
		var tfieldid,tFieldvalue;
		//查询语句
		selselect = "";
		selfrom = "";
		selwhere = ""; 
		
		//QueryManageServlet.java生成信息查询queryinfoform  
		oForm =document.getElementById("queryinfoform");		
		Els= oForm.elements;
		iLen = Els.length;    
		for(i=iLen-1;i>=0;i--){            
		  	oAtt = Els[i].attributes;
		  	ObjId= oAtt.getNamedItem("id").value;
		  	if(ObjId.length>5){//query+id 和value+id和table+id和field+id
		    	ttxt =  ObjId.substring(0,5);  
		    	tid =  ObjId.substring(5,ObjId.length);
		    
		    	//QueryManageServlet.java和QueryManage.java生成DIV		    
		    	if(ttxt=="query" || ttxt=="qdept" || ttxt=="qfmid"|| ttxt=="qmmid"){
			      	tvalueid =   "value"+tid;
			      	ttableid =   "table"+tid;
			      	tfieldid =   "field"+tid;
			      	//
			      	if(ttxt=="qfmid"){//家庭表字段
			        	tqueryid =   "qfmid"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;		       
			      	}else if(ttxt=="qmmid"){//成员表字段
			      		tqueryid =   "qmmid"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;
			      	}else if(ttxt=="query"){//
			      		tqueryid =   "query"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;
			      	}else if(ttxt=="qdept"){//机构特殊处理
			        	tqueryid =   "qdept"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;
			        	if(tQvalue.length>0){
			        		tQvalue =seldeptid;	
			        	}else{
			        		tQvalue = "";	
			        	}  
			        	//		        
			      	}
			      	//alert("id: "+tqueryid+"val: "+tvalueid+"tab: "+ttableid+"field: "+tfieldid);
			      	tVvalue =document.getElementById(tvalueid).value;
			      	tTvalue =document.getElementById(ttableid).value;
			      	tFieldvalue =document.getElementById(tfieldid).value;
			      	//
			      	tQvalue = JHshTrim(tQvalue);
			      	tVvalue = JHshTrim(tVvalue);
			      	tTvalue = JHshTrim(tTvalue);
			      	tFieldvalue = JHshTrim(tFieldvalue);
			      	//
			      
			      	//alert(" :"+tQvalue+"  : "+tVvalue+"  :"+tTvalue+"  :"+tFieldvalue);
			      	//选择家庭或者成员
				    var isrdf = document.getElementById("rdf");
				    var isrdm = document.getElementById("rdm");
				    //
			      	if(qmode=="0" || qmode=="1"){//机构信息和业务审批和业务所属查询	    	
			          	//家庭查询
			          	if(isrdf.checked == true){	
			          		if(ttxt=="qfmid"){//家庭表字段
			          			//查询字段                         
						      	var sf = selselect;
						      	if(MatchMatch(sf,tFieldvalue)==null){                              
						        	if (sf.length>0){ 
						          	tFieldvalue = tFieldvalue + ",";		            
						        	}
						        	//查询字段
						        	selselect = tFieldvalue + selselect;  
						      	}		
			          		}    		
			      	 		//条件值不为空
					      	if(tQvalue.length>0){
					      		//查询表                          
					      		var s = selfrom;
					      		if(MatchMatch(s,tTvalue)==null){                                
					        		if (s.length>0){            
					          		tTvalue = tTvalue + ",";
					        		}
					        		//查询表
					        		selfrom = tTvalue + selfrom;  
					      		}					      		
					      		//条件	
					      		//常量定义有☆定义
					      		tRvalue = "☆";                  
					      		//替换条件值            
					      		var n = MatchReplace(tVvalue,tRvalue,tQvalue);
					      		//
						    	if(selwhere.length>0){
						      		selwhere = selwhere +" and "+ n;
						    	}else{
						      		selwhere = n;  
						    	}
					      	}	             
			      	  	}else if(isrdm.checked == true){
			      			if(ttxt=="qmmid"){//成员表字段
			          			//查询字段                         
						      	var sf = selselect;
						      	if(MatchMatch(sf,tFieldvalue)==null){                              
						        	if (sf.length>0){ 
						          	tFieldvalue = tFieldvalue + ",";		            
						        	}
						        	//查询字段
						        	selselect = tFieldvalue + selselect;  
						      	}		
			          		} 
					      	//条件		      
					      	//条件值不为空
					      	if(tQvalue.length>0){
					      		//查询表                          
						      	var s = selfrom;
						      	if(MatchMatch(s,tTvalue)==null){                                
						        	if (s.length>0){            
						          	tTvalue = tTvalue + ",";
						        	}
						        	//查询表
						        	selfrom = tTvalue + selfrom;  
						      	}
					      		//常量定义有☆定义
					      		tRvalue = "☆";                  
					      		//替换条件值            
					      		var n = MatchReplace(tVvalue,tRvalue,tQvalue);
					      		//
						    	if(selwhere.length>0){
						      		selwhere = selwhere +" and "+ n;
						    	}else{
						      		selwhere = n;  
						    	}
						  	} 	
			      	  	}			      	  	
			      	}else if(qmode=="2"){//机构信息和业务审批查询	
			    	
			      	}else if(qmode=="3"){//机构信息和业务所属查询
			    		
			      	}else if(qmode=="4"||qmode=="5"||qmode=="6"||qmode=="7"||qmode=="8"||qmode=="9"||qmode=="10"){//机构信息和业务所属查询	    	 	
			    	  	//条件值不为空
				      	if(tQvalue.length>0){
				      		//查询表                          
				      		var s = selfrom;
				      		if(MatchMatch(s,tTvalue)==null){                                
				        		if (s.length>0){            
				          		tTvalue = tTvalue + ",";
				        		}
				        		//查询表
				        		selfrom = tTvalue + selfrom;  
				      		}
				      		//条件	
				      		//常量定义有☆定义
				      		tRvalue = "☆";                  
				      		//替换条件值            
				      		var n = MatchReplace(tVvalue,tRvalue,tQvalue);
				      		//
					    	if(selwhere.length>0){
					      		selwhere = selwhere +" and "+ n;
					    	}else{
					      		selwhere = n;  
					    	}
				      	}
			      	}		      	 
				}     
		  	}      
    	}
    	//排序
    	var soid = "",soname = "";
    	if(isrdf.checked == true){	
    		soname = $("#forderitemquery").val();
	    	soid = $("#orderquery").val();
	    	selorder = 	soname + " " + soid; 
      	}else if(isrdm.checked == true){
      		soname = $("#morderitemquery").val();
	    	soid = $("#orderquery").val();
	    	selorder = 	soname + " " + soid;  
      	}
    	
    	//	    
	    //alert("select:"+selselect+"from:"+selfrom+"where:"+selwhere+" order "+selorder);
	    //
	    //
	    //查询条件转换处理
	    GetPhySql();
    }
    
    //条件转换[逻辑条件转换物理条件]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,torder,tmode,tbegpage,tendpage;
	    var tdept;  
	    var tpolicy,tpolicycheck,tpolicyflow,tpolicyresult,tpolicyflag;    
	    //      
	    tselect = JHshTrim(selselect);  
	    tfrom = JHshTrim(selfrom);
	    twhere = JHshTrim(selwhere);
	    //
	    torder = JHshTrim(selorder);
	    //	    
	    //解析查询模式常量定义模块定义tmode
	    tmode = "1";//模式0执行查询1提取SQL语句
	    tbegpage = "0";
	    tendpage = "0";
	    //机构
	    tdept = deptid; 
	    //
	    tempid = empid;
	    
	    //业务选择
	    var olistpolicy = listpolicy;		          		
	    tpolicy = $("#"+olistpolicy).val();
	    //业务审批
	    var olistpolicycheck = listpolicycheck;
	    tpolicycheck = $("#"+olistpolicycheck).val();	   
	    var olistpolicyflow = listpolicyflow;
	    tpolicyflow = $("#"+olistpolicyflow).val();
	    var olistpolicyresult = listpolicyresult;
	    tpolicyresult = $("#"+olistpolicyresult).val();
	    //业务所属
	    var olistpolicyflag = listpolicyflag;
	    tpolicyflag = $("#"+olistpolicyflag).val();
	    //
	    //选择家庭或者成员
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    //查询窗口模式在常量定义类有定义
	    if(qmode=="0"){//信息查询[居民信息编辑]	    	
	    	if(isrdf.checked == true){	    		
	      	 	mode = "exeallfamilysql";              
	      	}else if(isrdm.checked == true){
	      		mode = "exeallmembersql";  
	      	} 	
	    }else if(qmode=="1"){//机构信息和业务审批和业务所属查询	    	
	    	if(isrdf.checked == true){	    		
	      	 	mode = "exefamilysql";              
	      	}else if(isrdm.checked == true){
	      		mode = "exemembersql";  
	      	} 	
	    }else if(qmode=="2"){//机构信息和业务审批查询	
	    	mode = "exefamilyoperchecksql";
	    	return;
	    }else if(qmode=="3"){//机构信息和业务所属查询
	    	mode = "exefamilyoperflagsql";
	    	return;    	
	    }else if(qmode=="4"||qmode=="5"||qmode=="6"||qmode=="7"||qmode=="8"||qmode=="9"||qmode=="10"){//机构信息和业务审批查询	
	    	//转到业务操作页面
	    	//window.parent.operatingzone.location.href="/db/page/policy/manage/policyrequest.jsp"+"?qpolicy="+tpolicy+"&qfrom="+tfrom+"&qwhere="+twhere;
	    	policyaction(qmode,tpolicy,tselect,tfrom,twhere,torder);
	    	return;
	    }
	    //加载数据		
		DisplayPageStatus();        
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
	            tempid: tempid,	           
	            tpolicy: tpolicy,
	            tpolicycheck: tpolicycheck,
	            tpolicyflow: tpolicyflow,
	            tpolicyresult: tpolicyresult,
	            tpolicyflag: tpolicyflag               
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
      			HiddenPageStatus();  	        	
	  			//
	          	if(result=="-1"){                
	             	//查询语句不合法          
	          	}else{
	            	if(mode=="exefamilysql"){
	            		//信息家庭查询取结果physql                  
		             	var fs= window.dialogArguments;		                
		                resultaction(result);
		            }else if(mode=="exemembersql"){
		            	//信息成员查询取结果physql                  
		              	var fs= window.dialogArguments;		                
		                resultaction(result);
		      		}else if(mode=="exeallfamilysql"){
	            		//信息全局家庭查询取结果physql                  
		             	
		            }else if(mode=="exeallmembersql"){
		            	//信息全局成员查询取结果physql                  
		              	
		      		}
	          	}                             
	    	});        
    }    

	//取得业务选择
	function GetPolicyChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//加载数据		
		DisplayPageStatus();
		//
		$.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
		    {
		        action: "getPolicyChoice", //action参数
		        sname: sname //参数                              
		    },
		    function(result) {   //回调函数
		    	//加载数据完毕
      			HiddenPageStatus();  	        	
	  			//		    	
		      	pardiv.innerHTML = result;
		      	//业务选择页面参数传进来		          	        	
		      	if(selpolicy!="-1"){		      				
		      		$("#"+sname).val(selpolicy);
		      		$("#"+sname).attr("disabled", "disabled");
		      	}
				//单击事件
				$("#"+sname).change(function(){ChoicePolicyName();});	      	                                         
		    }
		);        
	}
	//取得业务审批标准选择下拉框
	function GetPolicyCheckChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//加载数据		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
	        {
	            action: "getPolicyCheckChoice", //action参数
	            sname: sname //参数                              
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result; 
	          	//业务审批标准选择页面参数传进来		          	         	
	          	if(selpolicycheck!="-1"){	          		
	          		$("#"+sname).val(selpolicycheck);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}                              
	        }
	    );        
	}	
	//取得业务审批进度选择下拉框
	function GetPolicyFlowChoice(pardivid,sname,pid){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  //	  
	  //
	  	//加载数据		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
	        {
	            action: "getPolicyFlowChoice", //action参数
	            sname: sname, //参数
	            pid: pid                              
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result;	          		          	
	          	//业务终审选择页面参数传进来		          		          	
	          	if(selpolicyflow!="-1"){	          				
	          		$("#"+sname).val(selpolicyflow);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}	          	                                        
	        }
	    );        
	}
	//取得业务终审选择下拉框
	function GetPolicyResultChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//加载数据		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
	        {
	            action: "getPolicyResultChoice", //action参数
	            sname: sname //参数                              
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result;
	          	//业务终审选择页面参数传进来		          		          	
	          	if(selpolicyresult!="-1"){	          				
	          		$("#"+sname).val(selpolicyresult);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}	          	                                        
	        }
	    );        
	}
	//取得业务所属选择下拉框
	function GetPolicyFlagChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//加载数据		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //服务器页面地址
	        {
	            action: "getPolicyFlagChoice", //action参数
	            sname: sname //参数                              
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result;
	          	//业务所属选择页面参数传进来			      			      	
	          	if(selpolicyflag!="-1"){	          				
	          		$("#"+sname).val(selpolicyflag);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}	          	                                        
	        }
	    );        
	}	
	//取得用户查询页面
	function GetQueryExpHtml(empid,tmode){
	  //
	  	//加载数据		
		DisplayPageStatus();
		//
	    $.post("page/querymanage/QueryManageServlet",      //服务器页面地址
	        {
	            action: "getQueryExpHtml" , //action参数 
	            empid: empid                             
	        },
	        function(result) {   //回调函数
	        	//	        	
	          	divquery.innerHTML = result; 
	          	//加载数据完毕
      			HiddenPageStatus();  	        	
	  			//	  			 
	          	//选择家庭或者成员
			    ChoiceResult();                    
	        }
	    );        
	}
	//选择显示结果
	function ChoiceResult(){
		//选择家庭或者成员
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    //  
	    if(isrdf.checked == true){	    		
      		$("#mbtd").css({"display":"none"}); 
      		$("#fmtd").css({"display":"block"});	            
      	}else if(isrdm.checked == true){
      		$("#fmtd").css({"display":"none"});
      		$("#mbtd").css({"display":"block"});
      	} 
	}
	//选择业务
	function ChoicePolicyName(){
		var olistpolicy = listpolicy;
		var olistpolicycheck = listpolicycheck;	
		var olistpolicyflow = listpolicyflow;
		var olistpolicyresult = listpolicyresult;
		var olistpolicyflag = listpolicyflag;
		var vpolicyid = $("#"+olistpolicy).val();
		//
		//
		if(vpolicyid=="-1"){//选中[无]
			//业务选项均选中[无]
			$("#"+olistpolicycheck).val("-1");			
			$("#"+olistpolicyflow).val("-1");
			$("#"+olistpolicyresult).val("-1");
			$("#"+olistpolicyflag).val("-1");
			//	
			//[无]业务选择时隐藏所有业务查询选项
			HiddenChoiceList();		
		}else{
			//显示所有业务查询选项
			DisplayChoiceList(vpolicyid);			
		}
		//
		//业务审批进度选择
        GetPolicyFlowChoice("choicepolicyflow",listpolicyflow,vpolicyid); 
        //		
	}
	//显示业务查询选项设置
	function DisplayChoiceList(policyid){
		//
		if(qmode=="0"){//信息查询[全局居民信息查询]
			//
			//[无]业务选择时隐藏所有业务查询选项
			HiddenChoiceList();	
			//
			//
			$("#queryoperform").css({"display":"none"});
			//
			$("#choicepolicy").css({"display":"none"});
			$("#spanpolicyitem").css({"display":"none"});
			$("#spanpolicy").css({"display":"none"});
			//		
		}else if(qmode=="1"){//机构信息和业务审批和业务所属查询
			//		
			if(policyid == null || policyid=="-1"){
				//[无]业务选择时隐藏所有业务查询选项
				HiddenChoiceList();
			}else{
				ShowChoiceList();
			}
			//
			//
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
		}else if(qmode=="2"){//机构信息和业务审批查询
			
			//		
			if(policyid == null || policyid=="-1"){
				//[无]业务选择时隐藏所有业务查询选项
				HiddenChoiceList();
			}else{
				//
				ShowChoiceList();
				//
				$("#choicepolicyflag").css({"display":"none"});	
				$("#spanpolicyflagitem").css({"display":"none"});				
				$("#spanpolicyflag").css({"display":"none"});
				//						
			}
			//
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
			//		
			$("#rdm").css({"display":"none"});	
			$("#spanrdm").css({"display":"none"});
			//
		}else if(qmode=="3"){//机构信息和业务所属查询
			//
			
			//		
			if(policyid == null || policyid=="-1"){
				//[无]业务选择时隐藏所有业务查询选项
				HiddenChoiceList();
			}else{
				//
				ShowChoiceList();
				//	
				$("#choicepolicycheck").css({"display":"none"});				
				$("#choicepolicyflow").css({"display":"none"});
				$("#choicepolicyresult").css({"display":"none"});				
				$("#spanpolicycheckitem").css({"display":"none"});				
				$("#spanpolicycheck").css({"display":"none"});
				
				$("#spanpolicyflow").css({"display":"none"});
				$("#spanpolicyresult").css({"display":"none"});
				//
			}
			//
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
			//		
			$("#rdf").css({"display":"none"});	
			$("#spanrdf").css({"display":"none"});
			//		
			$("#rdm").css({"display":"none"});	
			$("#spanrdm").css({"display":"none"});
			//
		}else if(qmode=="4"){//机构信息
			//		
			if(policyid == null || policyid=="-1"){
				//[无]业务选择时隐藏所有业务查询选项
				HiddenChoiceList();
			}else{
				ShowChoiceList();
			}
			//
			//
			$("#queryoperform").css({"display":"none"});
			//
			$("#choicepolicy").css({"display":"none"});
			$("#spanpolicyitem").css({"display":"none"});
			$("#spanpolicy").css({"display":"none"});
		}else if(qmode=="5" ||qmode=="6" ||qmode=="7"||qmode=="8"||qmode=="9"){//机构信息和业务选择查询
			
			//		
			if(policyid == null || policyid=="-1"){
				//[无]业务选择时隐藏所有业务查询选项
				HiddenChoiceList();
			}else{
				//无业务审批和业务所属选择
				HiddenChoiceList();
			}
			//			
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
			//
			//		
			$("#rdf").css({"display":"none"});	
			$("#spanrdf").css({"display":"none"});
			//			
			$("#rdm").css({"display":"none"});	
			$("#spanrdm").css({"display":"none"});
			//
		}else if(qmode=="10"){//机构信息
			//		
			if(policyid == null || policyid=="-1"){
				//[无]业务选择时隐藏所有业务查询选项
				HiddenChoiceList();
			}else{
				ShowChoiceList();
			}
			//
			//
			$("#queryoperform").css({"display":"none"});
			//
			$("#choicepolicy").css({"display":"none"});
			$("#spanpolicyitem").css({"display":"none"});
			$("#spanpolicy").css({"display":"none"});
		}		
	}
	//显示有选择业务查询选项设置
	function ShowChoiceList() {
		//
		$("#choicepolicycheck").css({"display":"block"});		
		$("#choicepolicyflow").css({"display":"block"});
		$("#choicepolicyresult").css({"display":"block"});		
		$("#spanpolicycheckitem").css({"display":"block"});		
		$("#spanpolicycheck").css({"display":"block"});
		
		$("#spanpolicyflow").css({"display":"block"});
		$("#spanpolicyresult").css({"display":"block"});	
		//
		$("#choicepolicyflag").css({"display":"block"});	
		$("#spanpolicyflagitem").css({"display":"block"});		
		$("#spanpolicyflag").css({"display":"block"});
		//
		//==============================xiu临时关闭=======================
		$("#spanpolicycheckitem").css({"display":"none"});	
		$("#trcheck").css({"display":"none"});				
		$("#trcheckflow").css({"display":"none"});
		$("#trcheckresult").css({"display":"none"});	
		//==============================xiu临时关闭=======================
				
	}
	//隐藏有无业务查询选项设置
	function HiddenChoiceList() {	
		//
		$("#choicepolicycheck").css({"display":"none"});
		
		$("#choicepolicyflow").css({"display":"none"});
		$("#choicepolicyresult").css({"display":"none"});		
		$("#spanpolicycheckitem").css({"display":"none"});		
		$("#spanpolicycheck").css({"display":"none"});
		
		$("#spanpolicyflow").css({"display":"none"});
		$("#spanpolicyresult").css({"display":"none"});	
		//
		$("#choicepolicyflag").css({"display":"none"});	
		$("#spanpolicyflagitem").css({"display":"none"});		
		$("#spanpolicyflag").css({"display":"none"});
		//
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
	//打开更多设置窗体
	function CallIniDialog()
	{		
		var myobj = new Array();
		myobj[0] = qmode;		
		showModalDialog("/db/page/querymanage/infoqueryini.jsp",myobj,"status:false;dialogWidth:500px;dialogHeight:400px");
		
		//刷新页面
		//取得查询设置页面  
		GetQueryExpHtml(empid,qmode);
	}
	//打开更多设置窗体
	function CallAllIniDialog()
	{		
		showModalDialog("/db/page/info/search/infoquerynew.jsp","","status:false;dialogWidth:220px;dialogHeight:400px");		
	}    
    //初始化页面
    function IniPage(){    	
		//当前登录用户编号    
		empid = "<%=empno%>";     
		//登录机构    
		deptid = "<%=onno%>";    //当前登录机构 
		//
		//查询窗体[从request接过来]
		qmode  = "<%=reqmode%>";		 //当前查询窗体模式为信息查询code		
		//取得查询设置页面  
		GetQueryExpHtml(empid,qmode);		
		//
		//选择业务查询	
		selpolicy = "<%=reqpolicy%>";		 //业务选择
		selpolicycheck = "<%=reqcheck%>";		 //业务审批标准选择
		selpolicyflow = "<%=reqflow%>";		 //业务审批进度选择
		selpolicyresult = "<%=reqresult%>";		 //业务终审选择
		selpolicyflag = "<%=reqflag%>";		 //业务所属选择		
		//
		//业务选择
        GetPolicyChoice("choicepolicy",listpolicy);        
        //业务审批标准选择
        GetPolicyCheckChoice("choicepolicycheck",listpolicycheck);        
        //业务审批进度选择
        GetPolicyFlowChoice("choicepolicyflow",listpolicyflow,selpolicy);
        //业务终审选择
        GetPolicyResultChoice("choicepolicyresult",listpolicyresult);
        //业务所属选择
        GetPolicyFlagChoice("choicepolicyflag",listpolicyflag);
        //
        //显示所有业务查询选项
		DisplayChoiceList(selpolicy);
        //
      	      	
      	/***************************************
      	*[qmode]查询模式
      	*[0]全局查询
      	*[1]机构信息和业务审批和业务所属组合查询
      	*[2]机构信息和业务审批查询
      	*[3]机构信息和业务所属查询
      	****************************************/    	
    }
    //=================================页面操作END==================================
  </script>
	</head>
	<body onload="IniPage()" style="overflow-x:hidden;overflow-y:scroll">
		<script type="text/javascript" src="page/js/Calendar2.js"></script>
		<div id ="handlezone" style="display:block">
		<div id="divquery"></div>
		
		<div id="queryoperform">
			<table width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr class="queryform"  align="center">
					<td colspan='2'>业务查询选项</td>
				</tr>
				<tr align="center">
					<td colspan='2'>
						<span id='spanpolicyitem' class='caption'>业务列表选项</span>
					</td>
				</tr>
				<tr align="center">
					<td width="35%" valign="middle">
						<span id='spanpolicy' class='itemstyle' style="width: 100%">业务选择</span>
					</td>
					<td width="65%">
						<div id='choicepolicy' style="width: 100%"></div>
					</td>
				<tr align="center">
					<td colspan='2'>
						<span id='spanpolicycheckitem' class='caption'>业务评议选项</span>
					</td>
				</tr>
				<tr align="center" id = 'trcheck'>
					<td width="35%" valign="middle">
						<span id='spanpolicycheck' class='itemstyle' style="width: 100%">评议名单</span>
					</td>
					<td width="65%">
						<div id='choicepolicycheck' style="width: 100%"></div>
					</td>
				</tr>				
				<tr align="center" id = 'trcheckflow'>
					<td width="35%" valign="middle">
						<span id='spanpolicyflow' class='itemstyle' style="width: 100%">评议进度</span>
					</td>
					<td width="65%">
						<div id='choicepolicyflow' style="width: 100%"></div>
					</td>
				</tr>
				<tr align="center" id = 'trcheckresult'>
					<td width="35%" valign="middle">
						<span id='spanpolicyresult' class='itemstyle' style="width: 100%">终审评议</span>
					</td>
					<td width="65%">
						<div id='choicepolicyresult' style="width: 100%"></div>
					</td>
				</tr>
				<tr align="center">
					<td colspan='2'>
						<span id='spanpolicyflagitem' class='caption'>业务所属选项</span>
					</td>
				</tr>
				<tr align="center">
					<td width="35%" valign="middle">
						<span id='spanpolicyflag' class='itemstyle' style="width: 100%">所属选择</span>
					</td>
					<td width="65%">
						<div id='choicepolicyflag' style="width: 100%"></div>
					</td>
				</tr>
			</table>
		</div>
		<fieldset id = "resultfieldset">
			<legend>查询显示</legend>	
			<div align="center">
				<input type="radio" name="rd" id="rdf" value="rdf" onclick='ChoiceResult()' checked>
				<span id='spanrdf'>显示家庭</span>
				</input>
				<input type="radio" name="rd" id="rdm" value="rdm" onclick='ChoiceResult()'>
				<span id='spanrdm'>显示成员</span>
				</input>
			</div>
			<div align="center">
				<span class='pointer itemstyle' id="editexpitem"
					onclick='CallIniDialog()'>[更多设置]</span>
				<span class='pointer itemstyle' id="dyqueryitem" onclick='CallAllIniDialog()'>[综合查询]</span>			
			</div>
		</fieldset>
		<div align="center">
			<input id="btnexe" type='button' value=' 查 询 ' onclick='ExeSQL()'></input>
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table>  


		<form id="resultaction">
			<input type="hidden" name="sql" />
			<input type="hidden" name="type" />
			<input type="hidden" name="url" />
			<input type="hidden" name="checkbox" />
		</form>
		<form id="policyaction">
			<input type="hidden" name="qpolicy" />
			<input type="hidden" name="qselect" />
			<input type="hidden" name="qfrom" />
			<input type="hidden" name="qwhere" />
			<input type="hidden" name="qorder" />			
		</form>
		</div>
	</body>
</html>

<script type="text/javascript">
  //=================================模态窗体选择BEG==================================
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
    //所属机构选择
    if(seldeptid==null) return;
	    /*
	    if (!confirm("是否确定选择？\n["+seldeptname+"]")) {
	    	return;
	    }
	    */
      	//选中字典输入框choicedept长度10位
      	var cdept = deptobjid.substring(10,deptobjid.length);      
      	$("#"+cdept).val(seldeptname);
    	closedept();
  	}
  	//置空机构选择[DeptTreeServlet成生的方法]
	function cleardept(){
		//选择调用参数
	    var cdept = deptobjid.substring(10,deptobjid.length);      
      	$("#"+cdept).val("");
    	closedept();	
	}
  	//关闭机构选择[DeptTreeServlet成生的方法]
  	function closedept(){
    	$("#bdiv").remove();
    	$("#odiv").remove();
    	showdiv();  
  	}
  	//隐藏select
  	function hidediv(){ 
	  	//
		HiddenChoiceList();
		
		//
		$("#queryoperform").css({"display":"none"});
		//
		$("#choicepolicy").css({"display":"none"});
		$("#spanpolicyitem").css({"display":"none"});
		$("#spanpolicy").css({"display":"none"});
			 
	  	//隐藏信息查询选择下拉框
	  	var Els=document.getElementById("queryinfoform").getElementsByTagName("select");
		var iLen = Els.length;    
		for(var i=0;i<iLen;i++){            
		  var oAtt = Els[i].attributes;
		  var ObjId= oAtt.getNamedItem("id").value;
		  $("#"+ObjId).css({"display":"none"});		 
		}
  	}
  	//显示select
  	function showdiv(){
	  	//
	  	var olistpolicy = listpolicy;  	
		var vpolicyid = $("#"+olistpolicy).val();  	
	  	//显示信息查询选择下拉框
	  	var Els=document.getElementById("queryinfoform").getElementsByTagName("select");
		var iLen = Els.length;    
		for(var i=0;i<iLen;i++){            
		  var oAtt = Els[i].attributes;
		  var ObjId= oAtt.getNamedItem("id").value;
		  $("#"+ObjId).css({"display":"block"});		 
		}
		DisplayChoiceList(vpolicyid);
	  	//
  	}  
  	//生成部门选择对象[TableInfoServlet成生的方法]
  	function choicedept(objid){
    	deptobjid = "choicedept"+objid; 
  	}   
 	//=================================模态窗体选择END==================================    
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
                    oWidth:180,                              //弹出窗口宽度
                    oHeight:450                              //弹出窗口高度
            };
            $.extend(defaults,options);
                       
            //秀秀
            var cbtn = "<div id='cbtn'><img src='/db/page/images/close.gif'/><span id = 'stitle'>"+defaults.title+"</span></div><div>";
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
            $("#content").css({"font-size":"14px","padding":"1px 1px","overflow-x":"hidden","overflow-y":"auto","width":defaults.oWidth+"px","height":defaults.oHeight+"px"});
            $("#odiv").css({"background":defaults.oColor,"width":defaults.oWidth+"px","border":"1px black solid","z-index":"9999","position":"absolute","top":"1px","left":(document.body.clientWidth-defaults.oWidth)/2+"px"});
            $("#bdiv").css({"background":defaults.bColor,"width":defaults.bWidth,"height":defaults.bHeight,"z-index":"9998","position":"absolute","filter":"alpha(opacity:90)","left":10,"top":0});
    };
    })(jQuery);
    
    $(function(){         
  
          $("button").css({"display":"block","margin":"10px 0","width":"120px"});
          $("button:first").css("background","red");
          
          
    });
	//查询机构标准
	function queryDept(){
	  $.openWindow({"title":"机构选择","otype":"1"});
		hidediv();        
	}
    //=================================AJAX模态窗体=================================    
</script>

<%
	String url = request.getParameter("url");
	String checkbox=request.getParameter("checkbox");
	String resi =request.getParameter("resi");
%>
<script type="text/JavaScript">
<!--new 转向到结果处理action
function resultaction(sql){
	var resi ="<%=resi%>";
	var oForm =document.getElementById('resultaction');
	var rdf = document.getElementById("rdf");
	var rdm= document.getElementById("rdm");
	var rd ="";
	if(rdf.checked==true){
		rd=rdf.value;
	}
	if(rdm.checked==true){
		rd=rdm.value;
	}
	oForm.type.value=rd;
	oForm.sql.value=sql;
	oForm.url.value="<%=url%>";
	oForm.checkbox.value="<%=checkbox%>";
	if(1==resi){
		oForm.target="oper";
		oForm.action ="/db/page/info/synthesis/residentquery.do";
	}else{
		oForm.target="query";
		oForm.action ="/db/page/querymanage/infoqueryresult.do";
	}
	//oForm.target="query";<link rel="stylesheet" href="../synthesis/dtree.css" type="text/css"></link>
	//oForm.action ="/db/page/querymanage/infoqueryresult.do";
	oForm.submit();
}
function policyaction(amode,apolicy,aselect,afrom,awhere,aorder){
	var oForm =document.getElementById('policyaction');	
	oForm.qpolicy.value=apolicy;
	oForm.qselect.value=aselect;
	oForm.qfrom.value=afrom;
	oForm.qwhere.value=awhere;
	oForm.qorder.value=aorder;
	oForm.target="oper";
	if(amode=="4"){//qmode=4[业务走访]
		oForm.action ="/db/page/policy/manage/policyinterview.jsp";	
	}else if(amode=="5"){//qmode=5[业务审批]
		//oForm.action ="/db/page/policy/manage/policycheck.jsp";
		oForm.action ="/db/page/policy/approval/approval.jsp";
	}else if(amode=="6"){//qmode=6[公    示]		
		oForm.action ="/db/page/policy/manage/policyarret.jsp";
	}else if(amode=="7"){//qmode=7[异议处理]
		oForm.action ="/db/page/policy/manage/policycheck.jsp";
		oForm.action ="/db/page/policy/manage/policyrecheck.jsp";
	}else if(amode=="8"){//qmode=8[业务查询]
		oForm.action ="/db/page/policy/manage/policyquery.jsp";		
	}else if(amode=="9"){//qmode=9[统计分析]
		oForm.action ="/db/page/policy/manage/policyreport.jsp";
	}else if(amode=="10"){//qmode=10[业务筛选名单]
		oForm.action ="/db/page/policy/manage/policyauto.jsp";
	}
	parent.frames.operating.cols="*,0";
	oForm.submit();
}
//-->
</script>
