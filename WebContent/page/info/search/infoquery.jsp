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
	String reqmode = request.getParameter("qmode");
	String reqpolicy = request.getParameter("qpolicy");
	if (reqpolicy == null) {
		reqpolicy = "-1"; //无选择业务
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查询管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/xiustyle.css" rel="stylesheet" type="text/css">
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	<script type="text/javascript" src="page/js/Calendar2.js"></script>
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
  </head>
  <script  type="text/javascript">
	//
	var empid = "",deptid = "",depth = "";
	//
	var selmode = "0";      //调用模块参数
	var selpid = "";		//选择查询业务ID
	var selpsta = "0";		//选择查询业务状态
	var selselect = "";    	//选择查询结果字段
	var selfrom = "";    	//选择查询结果表
	var selwhere = "";    	//选择查询结果条件
	var selorder = "";    	//选择查询结果排序
	//
	//条件转换[逻辑条件转换物理条件]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,torder,tmode,tbegpage,tendpage; 
	    //      
	    tselect = JHshTrim(selselect);  
	    tfrom   = JHshTrim(selfrom);
	    twhere  = JHshTrim(selwhere);
	    //
	    torder  = JHshTrim(selorder);
	    // 
	    tmode 	 = "1";	//模式0执行查询1提取SQL语句
	    tbegpage = "0";
	    tendpage = "0";
	    
	    //
	    //选择家庭或者成员
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    if(isrdf.checked == true){	    		
      	 	mode = "exefamilysql";              
      	}else if(isrdm.checked == true){
      		mode = "exemembersql";  
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
	            tdept: deptid,
	            tempid: empid,	           
	            tpolicy: selpid,
	            tpolicysta: selpsta             
	        },
	        function(result) {   //回调函数
	        	//加载数据完毕
      			HiddenPageStatus();  	        	
	  			//
	          	if(result=="-1"){                
	             	//查询语句不合法          
	          	}else{
	          		//打开相关窗体
					CallPage(result);
	          	}                             
	    	});        
    }
	//获取查询选项
	function getQueryExpHtml(){
		//
		//加载数据		
		DisplayPageStatus();
		//
	    $.post("/db/page/query/querypage.do",            //服务器页面地址
	        {
	            action: "getQueryExpHtml",             //action参数
	            jempid: empid
	        },
	        function(result) {                    	//回调函数
	        	//加载数据完毕
      			HiddenPageStatus();  	        	
	  			//
	        	divquery.innerHTML = result; 
	        }
	    );
	}
	
	//获取业务选择下拉框
	function getPolicySelect(sname){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //服务器页面地址
	        {
	            action: "getPolicySelect",             //action参数
	            sname: sname
	        },
	        function(result) {                    	//回调函数
	        	divpolicy.innerHTML = result; 
	        	//业务选择校验
	        	if(selpid != "-1"){
	        		$("#"+sname).val(selpid);
					$("#"+sname).attr("disabled", "disabled");
					$("#stalist").attr("disabled", "disabled");
				} 	
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
	//初始化
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		selmode  = "<%=reqmode%>";		 //当前查询窗体模式为信息查询code
		selpid = "<%=reqpolicy%>";	 	 //业务选择
		//		
		//获取业务选择下拉框
		getPolicySelect("pidlist");	
		//获取查询选项
		getQueryExpHtml();
		//根据查询转向模式
		var tmpmode = selmode;
		if(Number(tmpmode) >= 4){
			//业务查询
			$("#rdm").attr("disabled", "disabled");
		}else{
			//信息查询
			$("#rdm").attr("disabled", "");	
		}
	}
  </script>
  <body onload = "IniPage()"  style = "background: #FCDAD5;overflow-x:hidden;overflow-y:scroll;">
		<fieldset>
			<legend>
				查询结果选项
			</legend>
			<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = "font-size: 12px;">				
				<tr align='center' >
					<td>
						<input type='radio' name='rdr' id='rdf' checked='checked'
							onclick='ChoiceResult()'></input>
						<span style="color: #6BA6FF;">家庭显示</span>
						<input type='radio' name='rdr' id='rdm' onclick='ChoiceResult()'></input>
						<span style="color: #6BA6FF;">成员显示</span>
					</td>					
				</tr>
			</table>			
		</fieldset>
		<div id = 'divquery'></div>
		<fieldset>
			<legend>
				查询业务选项
			</legend>
			<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = "font-size: 12px;">
				<tr>
					<td width='50%' align='center'>
						<div id = 'divpolicy'></div>
					</td>
					<td  align='center'>
						<select id='stalist' style = "font-size:12px;">
							<option  value='0'>全部</option>							
							<!-- <option  value='1'>普通户</option> -->
							<!-- <option  value='2'>在保户</option> -->
							<!-- <option  value='3'>停保户</option> -->
							<!-- <option  value='4'>暂停户</option> -->
							<option  value='1'>在保户</option>
						</select>
					</td>
				</tr>				
			</table>
		</fieldset>		
		
		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = "font-size: 12px;">
			<tr align='center' >				
				<td>
					<button title = '查询' class='btn' id='btnquery' onclick="getQueryExpSQL()">查   询</button>										
				</td>
				<td width='40'>
					<span class='pointer' title = '更多查询设置' style="color: #6BA6FF;" onclick='CallIniDialog()'>[设置]</span>											
				</td>
			</tr>
		</table>
		<div align="center" id='pagestatusdiv'><img src="/db/page/images/loadingtiao.gif"></img></div>
		
		<form id="resultaction">
			<input type="hidden" name="sql" />
			<input type="hidden" name="type" />
			<input type="hidden" name="type1" />
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
	</body>
</html>
<script  type="text/javascript">
	//选择查询显示结果
	function ChoiceResult(){
		if(rdf.checked == true){
			$("#fmlist").css({"display":"block"});	    		
      		$("#mmlist").css({"display":"none"});       			            
      	}else if(rdm.checked == true){
      		$("#fmlist").css({"display":"none"}); 
      		$("#mmlist").css({"display":"block"});
      	}
	}
	//打开更多设置窗体
	function CallIniDialog(){
		var myobj = new Array();
		myobj[0] = selmode;		
		showModalDialog("/db/page/querymanage/infoqueryini.jsp",myobj,"status:false;dialogWidth:600px;dialogHeight:500px");
		
		//刷新页面
		//获取查询选项
		getQueryExpHtml();
	}
	//打开相关窗体
	function CallPage(sql){
		var tmpmode = selmode;
		if(Number(tmpmode) >= 4){
			//业务查询
			policyaction(selmode);
		}else{
			//信息查询
			resultaction(sql)	
		}
	}
	//构造查询SQL信息
	function getQueryExpSQL(){
		//页面对象检索
		var jname = "",jid = "",jvalue = "",jtable = "",jfield = "",jexpvalue = "",jdeptid = "";
		//
		selselect = "";    	//选择查询结果字段
		selfrom = "";    	//选择查询结果表
		selwhere = "";    	//选择查询结果条件
		selorder = "";    	//选择查询结果排序
		//
		
		//
		for (i=0;i<document.all.length;i++) {			
			jname = document.all[i].name;
			//是否查询选择对象
			if(jname=="fmqv[]" 
				|| jname=="mmqv[]" 
				|| jname=="dv[]" 
				|| jname=="qv[]")
			{
				//初始化选择机构查询ID
				jdeptid = "";
				//获取页面对象信息
				jid = document.all[i].id;
				jvalue = document.all[i].value;
				//	
				jtable = document.getElementById("t"+jid).value;	
				jfield = document.getElementById("f"+jid).value;
				jexpvalue = document.getElementById("v"+jid).value;
				//
				//============构造查询语句======================
				if (jname=="fmqv[]") {
					//家庭表对象				
				    if(rdf.checked == true){		//家庭查询
				    	//查询字段                         
				      	var sf = selselect;
				      	if(MatchMatch(sf,jfield)==null){                              
				        	if (sf.length>0){ 
				          		jfield += ",";		            
				        	}
				        	//查询字段
				        	selselect = jfield + selselect;  
				        	//查询表                          
				      		var s = selfrom;
				      		if(MatchMatch(s,jtable)==null){                                
				        		if (s.length>0){            
				          			jtable += ",";	
				        		}
				        		//查询表
				        		selfrom = jtable + selfrom;  
				      		}
				      	}
				    }
				}else if (jname=="mmqv[]") {
					//成员表对象
					if(rdm.checked == true){		//成员查询
						//查询字段                         
				      	var sf = selselect;
				      	if(MatchMatch(sf,jfield)==null){                              
				        	if (sf.length>0){ 
				          		jfield += ",";		            
				        	}
				        	//查询字段
				        	selselect = jfield + selselect;  
				        	//查询表                          
				      		var s = selfrom;
				      		if(MatchMatch(s,jtable)==null){                                
				        		if (s.length>0){            
				          			jtable += ",";	
				        		}
				        		//查询表
				        		selfrom = jtable + selfrom;  
				      		}
				      	}				      	
				    }
				}else if (jname=="dv[]") {
					//机构对象
					jdeptid = document.getElementById("d"+jid).value;										
				} else if (jname=="qv[]") {
					//其他对象
				}
				//条件值不为空
		      	if(jvalue.length>0){
		      		//机构查询校验		      		
		      		if(jdeptid.length>0){		//是机构查询条件
		      			jvalue = jdeptid;
		      		}
		      		//查询表                          
		      		var s = selfrom;
		      		if(MatchMatch(s,jtable)==null){                                
		        		if (s.length>0){            
		          			jtable += ",";	
		        		}
		        		//查询表
		        		selfrom = jtable + selfrom;  
		      		}
		      		//常量定义有☆定义
		      		var trvalue = "☆";                  
		      		//替换条件值            
		      		var n = MatchReplace(jexpvalue,trvalue,jvalue);
		      		//查询条件
			    	if(selwhere.length>0){
			      		selwhere += " and "+ n;
			    	}else{
			      		selwhere = n;  
			    	}
		      	}				
				//============构造查询语句======================
			}
		}
		//
		//=================排序====================
		var tmpname = "",tmporder = "asc";
		
    	if(rdf.checked == true){			//显示家庭
    		tmpname = $("#fmlist").val();
      	}else if(rdm.checked == true){	//显示成员
      		tmpname = $("#mmlist").val();
      	}
      	//
      	if(rdoup.checked == true){			//升序
    		tmporder = "asc";
      	}else if(rdodown.checked == true){	//降序
      		tmporder = "desc";
      	}
		//
      	selorder = tmpname + " " + tmporder;
      	//=================排序====================
      	//=================业务查询================
      	selpid = $("#pidlist").val();
      	selpsta = $("#stalist").val();
      	//=================业务查询================
		//
		//条件转换[逻辑条件转换物理条件]
    	GetPhySql();
	}
</script>
<script type='text/javascript'>
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
    //==================================字符去掉首尾空格BEG=========================
  
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
    //=================================字符去掉首尾空格END===================
</script>
<script type='text/javascript'>
	//=================================AJAX模态窗体=========================
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
                   oWidth:200,                              //弹出窗口宽度
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
	//
	//var inputseldeptid = "";	//选中的机构选择框ID
	//查询机构标准
	function queryDept(src){
    	$.openWindow({"title":"机构选择","otype":"1"});
    	inputseldeptid = src.id;  
    	//隐藏select
      	hidediv();  		    			
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
		
	    //选择调用参数
	    document.getElementById("d"+inputseldeptid).value = seldeptid;
	    document.getElementById(inputseldeptid).value = seldeptfullname;
	    //
		closedept();
	}
	//置空机构选择[DeptTreeServlet成生的方法]
	function cleardept(){
		//选择调用参数
	    document.getElementById("d"+inputseldeptid).value = "";
	    document.getElementById(inputseldeptid).value = "";		
	    //
		closedept();	
	}
	//关闭机构选择[DeptTreeServlet成生的方法]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();
		//显示select
	  	showdiv();			
	}
	//
	//隐藏select
  	function hidediv(){ 
	  	//隐藏信息查询选择下拉框
	  	var Els=document.getElementsByTagName("select");
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
	  	//显示信息查询选择下拉框
	  	var Els=document.getElementsByTagName("select");
		var iLen = Els.length;    
		for(var i=0;i<iLen;i++){            
		  var oAtt = Els[i].attributes;
		  var ObjId= oAtt.getNamedItem("id").value;
		  $("#"+ObjId).css({"display":"block"});		 
		}
		//选择查询显示结果
		ChoiceResult();
  	}  
	//=================================AJAX模态窗体=========================
</script>
<%
	String url = request.getParameter("url");
	String checkbox=request.getParameter("checkbox");
	String resi =request.getParameter("resi");
%>
<script type='text/javascript'>	
	//转向到结果处理action
	function resultaction(sql){
		var resi ="<%=resi%>";
		var oForm =document.getElementById('resultaction');
		var rdf = document.getElementById("rdf");
		var rdm= document.getElementById("rdm");
		var rd ="";
		if(rdf.checked==true){
			rd="rdf";
		}
		if(rdm.checked==true){
			rd="rdm";
		}
		oForm.type.value=rd;
		oForm.type1.value=rd;
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
		oForm.submit();
	}
	//业务处理页面
	function policyaction(amode){
		var oForm =document.getElementById('policyaction');	
		oForm.qpolicy.value=selpid;
		oForm.qselect.value=selselect;
		oForm.qfrom.value=selfrom;
		oForm.qwhere.value=selwhere;
		oForm.qorder.value=selorder;
		oForm.target="oper";
		if(amode=="4"){//qmode=4[业务走访]
			oForm.action ="/db/page/policy/manage/policyinterview.jsp";	
		}else if(amode=="5"){//qmode=5[业务审批]
			oForm.action ="/db/page/policy/approval/approval.jsp";
		}else if(amode=="6"){//qmode=6[公    示]		
			
		}else if(amode=="7"){//qmode=7[异议处理]
			
		}else if(amode=="8"){//qmode=8[业务查询]
					
		}else if(amode=="9"){//qmode=9[统计分析]
			
		}else if(amode=="10"){//qmode=10[业务筛选名单]
			
		}
		parent.frames.operating.cols="*,0";
		oForm.submit();
	}
</script>