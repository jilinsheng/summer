<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
SysTEmployee employee =(SysTEmployee)session.getAttribute("employee");
String onno = employee.getSysTOrganization().getOrganizationId();
String empno = employee.getEmployeeId().toString();
%>
<%	
	//	
	//接收qid
	String reqid= request.getParameter("qid");		
	if (reqid == null) {
		//默认为空
		reqid = "";    //空编号
	}
	//接收qname
	String reqname= request.getParameter("qname");
	reqname = new String(reqname.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqname == null) {
		//默认为空
		reqname = "";    //空名称
	}
	//接收qmoney
	String reqmoney= request.getParameter("qmoney");	
	if (reqmoney == null) {
		//默认为空
		reqmoney = "0";    //空金额
	}
	//接收qaccdesc
	String reqaccdesc= request.getParameter("qaccdesc");
	reqaccdesc = new String(reqaccdesc.getBytes("ISO8859_1"), "GB18030");//解码		
	if (reqaccdesc == null) {
		//默认为空
		reqaccdesc = "";    //空核算公式描述
	}
	//接收qacctype
	String reqacctype= request.getParameter("qacctype");		
	if (reqacctype == null) {
		//默认为空
		reqacctype = "family";    //家庭核算
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
    
    <title>核算公式设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/db/page/js/seqfun.js"></script>
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>	
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
	
	
    <style type="text/css">	
		body {
			margin: 0;
			padding: 0;
			font-family: "宋体";
			font-size: 12px;
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
			border-bottom-width: 1px;
			border-left-width: 1px;
			border-top-style: solid;
			border-right-style: solid;
			border-bottom-style: solid;
			border-left-style: solid;
		}
		.sqltable{
			color: #6BA6FF;
			font-size:12px;
		}		
		#sqlwhere{
			border-top:1px solid #43ACB2;
			border-left:1px solid #43ACB2;
			border-bottom:1px solid #43ACB2;
			border-right:1px solid #43ACB2;
			width:100%;
			height:303px;
			font-size:12px;
		}		
		#infotree{
			border-top:1px solid #43ACB2;
			border-left:1px solid #43ACB2;
			border-bottom:1px solid #43ACB2;
			border-right:1px solid #43ACB2;
			width:100%;
			height:200px; 
			overflow:scroll;
			font-size:12px;
		}		
		#sqlsexp{
			width:100%;
			font-size:12px;
		}
		.titles{
			line-height: 2;			
			color: #000000;
			font-size:12px;
			text-align: center;
			width:100%;
			height:18;
			background: #F5A89A;
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
		var selid = "";		//编号
		var selname = "";	//名称
		var selmoney = "0";		//数额
		var selaccdesc = "";	//核算公式描述
		var seltype = "family";//核算类型
		//		    
		var vleft = 0;  //背景宽度
		var vtop= 0; //背景高度
		//
		var empid = "";        //当前登录用户编号 		
		var deptid = "";		    //当前登录机构	
		var deptfieldname = "";		//当前机构字段			
		var seldeptid;		//选中机构
		var seldeptname;
		var seldeptfullname;
		
		var discid = "";			//当前字典编号
		var discname = "";			//当前字典名称
		var seldiscid;		//选中字典值
		var seldiscname;
		var seldiscfullname;
		var discfieldname = "";		//当前字典字段
		
		//
		var selselect = "";		//选择查询结果字段
		var selfrom = "";		//选择查询结果表
		var selwhere = "";		//选择查询结果条件
		//
		//设置物理SQL语句是否合法
		function TestPhySql(mode) {			
			var sql = $("#sqlwhere").val();	 
			if(sql=="" || sql == null){
				DisplayResult("提示:没有填写核算公式!");
				$("#sqlwhere").get()[0].focus();
				return;
			} 
			var s = sql;
			var ss = "as acccount";
			if(MatchMatch(s,ss)==null){	
				var mess = "提示!\n核算公式标准SQL语句填写格式为:\n";
				mess += "select info_t_family.family_id as familyid,\n";
				mess += "XXXXXX as acccount \n";
				mess += "from YYYYY\n";
				mess += "XXXXXX:核算公式!\n";
				mess += "YYYYY:查询表和表关系条件,中间有where隔开!\n";
				alert(mess);
				return;	
			}	  
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //服务器页面地址
		        {
		            action: "testphysql",             //action参数
		            sql: sql         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					// 
					if (result=="-1"){
		        		DisplayResult("提示:条件可能不合法!");
		        	}else{
		        		if(mode=="test"){//测试结果
		        			if(result=="0"){		        		
		        				DisplayResult("提示:条件定制合法,但无查询结果!");		        		
		        	 		}else{
		        				DisplayResult("提示:条件定制合法!");
		        			} 
		        		}else if(mode=="getsql"){//取结果physql		        			
		        			//取得结果后保存
		        			SaveDeptSql(sql,sql,"");
		        		}	
		        		
		        	}  		
		        }
		    );
		}
		//条件转换[逻辑条件转换物理条件]
		function GetPhySql(mode){		
			var tselect,tfrom,twhere,tmode,tbegpage,tendpage,ttype;		
			
			//核算公式
		   	selwhere = $("#sqlwhere").val();
		   	//查询结果
		   	//家庭或者成员模式
	      	ttype = seltype;
	      	if(ttype== "family"){
	      		//家庭
			   	//结果字段[家庭ID familyid,核定标准 acccount]
			    selselect = "info_t_family.family_id as familyid,"+selwhere+" as acccount ";			    
	      	}else{
	      		var tempwhere = "";
	      		//结果字段[家庭ID familyid,核定标准 acccount]
			    selselect = "info_t_family.family_id as familyid,info_t_member.member_id as memberid,"+selwhere+" as acccount ";			    			    
	      	}		   
		    //
		    tselect = JHshTrim(selselect);	
			tfrom = JHshTrim(selfrom);
			//
			twhere = "";//不需要条件
			//
			if(selwhere==""|| selwhere == null){
				DisplayResult("提示:没有填写核算公式!");
				$("#sqlwhere").get()[0].focus();
				return;
			}
			//解析查询模式常量定义模块定义tmode
			if(mode=="test"){//测试结果
				tmode = "0";
			}else{			//sql语句
				tmode = "1";
			}
	      	tbegpage = "0";
	      	tendpage = "0";		
			//
		    $.post("page/info/search/TableInfoServlet",      //服务器页面地址
		        {
		            action: "getphysql", //action参数
		            mode: "getsqlpolicydeptacc",   //参数
		            tselect: tselect,   //参数
		            tfrom: tfrom,   //参数
		            twhere: twhere,   //参数
		            tmode: tmode,
	                tbegpage: tbegpage,
	                tendpage: tendpage,  //参数分页
	                ttype: ttype,
	                selwhere: selwhere		            
		        },
		        function(result) {   //回调函数
		        	if (result=="-1"){
		        		DisplayResult("提示:条件可能不合法!");
		        	}else{		        		
		        		if(mode=="test"){//测试结果
		        			if(result=="0"){		        		
		        				DisplayResult("提示:条件定制合法,但无查询结果!");		        		
		        	 		}else{
		        				DisplayResult("提示:条件定制合法!");
		        			} 
		        		}else if(mode=="getsql"){//取结果physql	        			
		        			//取得结果后保存		        			
		        			var locsql = "select "+ tselect+" from "+tfrom; 			
		        			//
		        			SaveDeptSql(result,locsql,selwhere);
		        		}	        		
		        	}		        	        	   	
		        }
		    );    		
		}
		//保存操作
		function SaveDeptSql(physql,locsql,selexp){
			var sphysql = "",slocsql = "";			
			//
			sphysql = physql;			
    		slocsql = locsql;    			
			//
		    if (!confirm("确定保存["+selname+"]核算公式?")) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //服务器页面地址
		        {
		            action: "updatePolicyDeptSql",             //action参数
		            sid: selid,
		            sphysql: sphysql,
		            slocsql: slocsql,
		            selexp: selexp        
		        },
		        function(result) {                    	//回调函数
		        	//显示提示信息div
					DisplayResult(result);										  		
		        }
		    );
		}		
		
		//取得机构标准SQL属性
		function GetPolicyDeptSqlItem() {
			var mode = "";
		    //清空条件
			ResetSql();
			//选择逻辑语句或者物理语句
		    var isrdc = document.getElementById("rdc");
		    var isrdp = document.getElementById("rdp");
		    if(isrdc.checked == true){	    		
	      	 	mode = "loc";  
	      	 	$("#matchtd").css({"display":"block"});              
	      	}else if(isrdp.checked == true){
	      		mode = "phy";
	      		$("#matchtd").css({"display":"none"}); 
	      	}	
		    //显示页面状态div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",   //服务器页面地址
		        {
		            action: "getPolicyDeptSqlItem",             //action参数
		            sid: selid,
		            mode: mode         
		        },
		        function(result) {                    	//回调函数
		        	//隐藏页面状态div
					HiddenPageStatus();	
					//
					if(result!="null"){
						if(mode=="loc"){
							//处理逻辑表达式
							var alocsql = result.split(";");			
							var locsql = alocsql[0];
							var locexp = alocsql[1];
							document.getElementById("sqlwhere").innerHTML=locexp;//避免特殊字符
							//逻辑sql语句处理		
							SetLogicSql(locsql);
						}else if(mode=="phy"){
							//$("#sqlwhere").val(result);
							document.getElementById("sqlwhere").innerHTML=result;//避免特殊字符
						} 
						    
					} 		
		        }
		    );
		}
		//设置条件值
		function SetExpValue(){			
			var value = expvalue.value;	
			value = JHshTrim(value);			
		    //新增前确认
		    if (value.length<=0){return;}
		    insertAtCaret(sqlwhere, " '"+value+"'");	
		}
		
		//测试查询语句
		function TestSql() {
			//选择逻辑语句或者物理语句
		    var isrdc = document.getElementById("rdc");
		    var isrdp = document.getElementById("rdp");
		    if(isrdc.checked == true){	    		
	      	 	GetPhySql("test");	               
	      	}else if(isrdp.checked == true){
	      		TestPhySql("test");	  
	      	}			
			          	            
		}
		//保存查询语句
		function SaveSql() {
			//选择逻辑语句或者物理语句
		    var isrdc = document.getElementById("rdc");
		    var isrdp = document.getElementById("rdp");
		    if(isrdc.checked == true){	    		
	      	 	GetPhySql("getsql");               
	      	}else if(isrdp.checked == true){
	      		TestPhySql("getsql");     
	      	}			
				             	            
		}
		//重置SQL语句的合理性
		function ResetSql() {
			//条件
			$("#sqlwhere").val("");			
			$("#sqlwhere").get()[0].focus();
			storeCaret(sqlwhere);				            
            //
			selselect = "";
			selfrom = "";
			selwhere = "";				             	            
		}
		//取得基本家庭信息项
		function GetInfoTree(){
			//创建基本家庭信息项
			var ulr = "page/info/search/TableTreeServlet";
			$("#infotree").empty();                //清空现有列表
			loadrootTree('infotree',ulr,'infotree','root');	
		}
		//取得运算符下拉框
		function GetExpSelect(tfieldtype){
			//
		    $.post("page/info/search/TableInfoServlet",      //服务器页面地址
		        {
		            action: "getexpselect" , //action参数 
                	fieldtype: tfieldtype   		            	            
		        },
		        function(result) {   //回调函数
		        	divexp.innerHTML = result;	        	        	   	
		        }
		    );    		
		}
		//显示页面状态div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/1.8;  //背景宽度	    	
			//vtop= document.body.clientHeight/2-20; //背景高度 
			vtop = "80"; 
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
	    	vleft = document.body.clientWidth/1.8;  //背景宽度
			//vtop= document.body.clientHeight/2; //背景高度 
			vtop = "100"; 	
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
		//
		//初始化页面
		function IniPage(){	
			//当前登录用户编号    
			empid = "<%=empno%>";     
			//登录机构    
			deptid = "<%=onno%>";    //当前登录机构 
			//
			selid = "<%=reqid%>";    //编号 
			selname = "<%=reqname%>";    //名称
			selmoney = "<%=reqmoney%>";    //数额 
			selaccdesc = "<%=reqaccdesc%>";    //核算公式描述
			seltype = "<%=reqacctype%>";    //核算类型
			//
    		vleft = document.body.clientWidth/2;  //背景宽度
			vtop= document.body.clientHeight/2; //背景高度
			//
			//基本家庭信息项
			GetInfoTree();			
			//			
			//运算符
			GetExpSelect("0");
			//取得机构标准SQL属性
			GetPolicyDeptSqlItem();
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>稍等...</div> 
  		<div id='resultstatusdiv'></div> 
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr height='23' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>
	    		<td>机构名称</td>
	    		<td>标准数额</td>
	    		<td>核算公式描述</td>
	    	</tr>
	    	<tr height='23'>
	    		<td class = 'colValue'><%=reqname%></td>
	    		<td class = 'colValue'><%=reqmoney%></td>
	    		<td class = 'colValue'><%=reqaccdesc%></td>
	    	</tr>
	    </table>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top" width="40%"  id = "matchtd">
	    			<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
				    	<tr align="center">
				    		<td valign="top" width="40%">
				    			<fieldset  class='list'>
				    			<legend  class='legend'>核算公式设置</legend>
				    				<span class = 'titles'>家庭信息项</span>    	  
							        <div id="infotree" align="left"></div>
							        <span class = 'titles'>运算符</span>    	  
							        <div id = "divexp" align="left"></div>
							        <div align="center" id = "divexpvalue" align="left">
							        	<label class = "titles">条件值:
							        		<input  type="text" id="expvalue" ></input>
							            	<input type='button' value='确定' name="BtnExp" onclick='SetExpValue()'></input>
							            </label>
							        </div>
				    			</fieldset>	
				    		</td> 
				    	</tr>	    	
			    	</table> 
	    		</td>
	    		<td valign="top" width="60%">	    		
	    			<fieldset  class='list'>
	    			<legend  class='legend'>[<%=reqname%>]核算公式属性</legend>  
	    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    					<tr align="center" class = 'titles'>	
	    						<td valign="top">
	    							<input type="radio" name="rd" id="rdc" value="rdc" onclick="GetPolicyDeptSqlItem()" checked />选择条件语句
									<input type="radio" name="rd" id="rdp" value="rdp" onclick="GetPolicyDeptSqlItem()" />标准SQL语句
	    						</td>
	    					</tr>	    					
					    	<tr align="center">
					    		<td valign="top">
					    			<textarea name="sqlwhere" rows="6" id="sqlwhere" 
						        				onSelect="storeCaret(this);" 
						        				onClick="storeCaret(this);" 
						        				onKeyUp="storeCaret(this);">
						          	</textarea>
					    		</td>	
					    	</tr>					    	 	
				    	</table>				    	 
			          	<div class = "titles">
		      				<input type='button' value='清空' onclick='ResetSql()'></input>            	
						  	<input type='button' value='校验' onclick='TestSql()'></input>	      	  
						  	<input type='button' value='重置' onclick="GetPolicyDeptSqlItem()"></input>
						  	<input type='button' value='保存' onclick='SaveSql()'></input>
			            </div>		          	
	    			</fieldset>	
	    		</td>
	    	</tr>
	    </table>
  		
  </body>
</html>
<script type="text/javascript">
	//逻辑sql语句处理		
	function SetLogicSql(str)
	{
		//正则表达式处理逻辑SQL语句
		var r; // 声明变量。
		var s = "select";
		var f = "from";
		var w = "where";
		var ilen,iselect,ifrom,iwhere;
		//
		selselect = "";	
		selfrom = "";	
		selwhere = "";
		//
		if (str == null){
			DisplayResult("条件没有填写,无法解析!");	
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");			
			return false;		
		}
		ilen = str.length;
		r = MatchSearch(str,s);
		if (r<0){
			DisplayResult("条件编写错误,无法解析!");
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");					
			return false;
		}
		//select位置
		//6表示select长度
		iselect = r+6;
		ifrom = MatchSearch(str,f);
		if (ifrom<iselect){		
			DisplayResult("条件编写错误,无法解析!");	
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");	
			return false;	
		}else{
			selselect = JHshTrim(str.substring(iselect,ifrom-1));	
		}
		//where位置(不一定有条件呢?)
		iwhere = MatchSearch(str,w);
		if (iwhere>=ifrom){			
			//4表示from长度
			//5表示where长度
			selfrom = JHshTrim(str.substring(ifrom+4,iwhere-1));
			sqlwhere.value = JHshTrim(str.substring(iwhere+5,ilen));	
		}else{
			if(ifrom>0){
				//4表示from长度
				//5表示where长度
				selfrom = JHshTrim(str.substring(ifrom+4,ilen));
			}
		}
	}
	//=================================选择运算符BEG================================
	//选择查询字段
	function SelectField(tfullname){
		var sfield;
		var s;
		var ilen;
		var stable;			
		var stablevalue;
		//选择字段
		sfield = tfullname;			
		
		//取得字段前面的表描述名
		var atable = sfield.split(".");			
		stable = atable[0];	
		//查询表													
		s = selfrom;
		if(MatchMatch(s,stable)==null){																
			if (s.length>0){						
				stable = "," +stable;
			}
			//查询表
			selfrom = selfrom + stable;	
		}	
		//条件字段
		s = sqlwhere.value;
		if (s.length>0){						
			sfield = " " +sfield;
		}															
		insertAtCaret(sqlwhere, sfield);																		
	}
	//选择核算公式
	function SelectExp(){
		var sexp;
		sexp = sqlsexp.options[sqlsexp.selectedIndex].value;
		if (sexp=='a'){sexp = ">"}	
		if (sexp=='b'){sexp = "<"}
		if (sexp=='c'){sexp = "\'\'"}
		if (sexp=='d'){sexp = "\"\""}
		if (sexp=='likel'){sexp = "\' %\'"}
        if (sexp=='liker'){sexp = "\'% \'"}
        if (sexp=='likec'){sexp = "\'% %\'"}
		sexp = " " +sexp;			
		insertAtCaret(sqlwhere, sexp);	
	}		
	//=================================选择运算符END================================	
	//=================================选择查询操作BEG==============================
	//选择表节点[TableTreeServlet成生的方法]
	function seltableclick(tid,tname){
		//alert(tid+tname);
		CallTableDialog(tid,"edit");		
	}
	//选择字段节点[TableTreeServlet成生的方法]
	function selfieldclick(tid,tname,tfullname,tfieldmode){
		//alert(tid+tname+tfullname+tfieldmode);			
		//机构
         if(tfieldmode=="-4"){           		
         	//alert("机构");
         	alert("机构不能计算");      		
         }
         //日期型
         else if(tfieldmode=="-3"){  
         	//运算符
			GetExpSelect(tfieldmode);          		           		
         	//选择公式
			alert("日期型不能计算");              		
         }           	
         //数值型
         else if(tfieldmode=="-2"){
         	//运算符
			GetExpSelect(tfieldmode);
         	SelectField(tfullname);
         }
         //整型
         else if(tfieldmode=="-1"){
         	//运算符
			GetExpSelect(tfieldmode);
         	SelectField(tfullname);
         }
         //字符型
         else if(tfieldmode=="0"){
         	//运算符
			alert("字符型不能计算"); 	
         }
         //字典值	
         else{
         	//打开字典
         	alert("字典值不能计算"); 
         }					
	}
	//扩子表[TableTreeServlet成生的方法]
	function addchild(tid){
		//alert(tid);
		CallTableDialog(tid,"add");	
	}
	//扩字段[TableTreeServlet成生的方法]
	function addfield(tid){
		//alert(tid);
		CallFieldDialog(tid,"add");	
	}
	//选择核算公式
	function selectclickexp(){
		SelectExp();
	}
	//=================================选择查询操作END==============================
</script>
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
	    SelectField(deptfieldname);
		insertAtCaret(sqlwhere, " like '"+seldeptid+"%'");
		closedept();
	}
	//置空机构选择[DeptTreeServlet成生的方法]
	function cleardept(){		
		closedept();	
	}
	//关闭机构选择[DeptTreeServlet成生的方法]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();
		showdiv();	
	}
	//字典选中[DiscTreeServlet成生的方法]
	function seldiscclick(id,name,fullname){
	   //选中字典
	   $("#selname").html("选中:"+fullname+"");
	   seldiscid = id;
	   seldiscname = name;
	   seldiscfullname = fullname;
	}
	//字典选择确定[DiscTreeServlet成生的方法]
	function okdisc(){
		//字典选择
		if(seldiscid==null) return;
		if (!confirm("是否确定选择？\n"+seldiscfullname+"")) {
	        return;
	    }	    
	    if(discfieldname!=""){
	        //选择字段
	        SelectField(discfieldname);
	    	insertAtCaret(sqlwhere, " = "+seldiscfullname);
	    }
		closedisc();
	}	
	//关闭字典选择[DiscTreeServlet成生的方法]
	function closedisc(){
		$("#bdiv").remove();
		$("#odiv").remove();
		showdiv();	
	}
	//隐藏select
	function hidediv(){			
		$("#sqlsexp").css({"display":"none"});
	}
	//显示select
	function showdiv(){			
		$("#sqlsexp").css({"display":"block"});	
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
                    oWidth:220,                              //弹出窗口宽度
                    oHeight:300                              //弹出窗口高度
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
	        }else if(defaults.otype=="2"){
	        	//打开字典选择
	        	var ulr = "page/info/search/DiscTreeServlet";
	        	loadrootTree('content',ulr,discid,'root');
	        	//CSS
	        	$("#cbtn>img").css({"cursor":"pointer","float":"right"}).click(function(){closedisc();});
	        }else if(defaults.otype=="3"){
	        	
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
        //秀秀		
        $("button").css({"display":"block","margin":"10px 0","width":"120px"});
        $("button:first").css("background","red");
	});
	//查询机构条件
	function queryDept(){
    	$.openWindow({"title":"机构选择","otype":"1"});
		hidediv();				
	}
	//查询选择字典
	function queryDisc(){			
		if(discname==""){
		  $.openWindow({"title":"字典选择","otype":"2"});
		}else{
		  $.openWindow({"title":discname+"-字典选择","otype":"2"});
		}	    	
		hidediv();
	}
	//=================================AJAX模态窗体=================================
</script>
