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
	
	String siontabid =(String) session.getAttribute("siontabid");
	if (null == siontabid) {
		siontabid = "6";	//Ĭ��Ϊȫ����ѯ
	}
%>
<%
	//ҵ��ѡ��
	String reqpid = request.getParameter("qpolicy");
	//����ҵ������URLDecoder
	//�Ӳ�ѯ����ղ�ѯ�ֶ�select
	String reqselect = request.getParameter("qselect");
	//�Ӳ�ѯ����ղ�ѯ��from
	String reqfrom = request.getParameter("qfrom");	
	//�Ӳ�ѯ����ղ�ѯ����where
	String reqwhere= request.getParameter("qwhere");	
	//�Ӳ�ѯ����ղ�ѯ����order
	String reqorder= request.getParameter("qorder");
	//
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "-1";    //ѡ��ҵ��[��ѡ]
	}
	reqselect = new String(reqselect.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqselect == null) {
		//Ĭ��Ϊ��
		reqselect = "";    //�ֶ�
	}
	reqfrom = new String(reqfrom.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqfrom == null) {
		//Ĭ��Ϊ��
		reqfrom = "";    //�ձ�
	}
	reqwhere = new String(reqwhere.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqwhere == null) {
		//Ĭ��Ϊ��
		reqwhere = "";    //������
	}
	reqorder = new String(reqorder.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqorder == null) {
		//Ĭ��Ϊ��
		reqorder = "";    //������
	}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ҵ��������ѯ����</title>
    
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
		body {
			margin: 0;
			padding: 0;
			font-family: "����";
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
			font-family: "����";
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
			font-family: "����";
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
	var selpid = "";
	var selselect = "";    	//ѡ���ѯ����ֶ�
	var selfrom = "";    	//ѡ���ѯ�����
	var selwhere = "";    	//ѡ���ѯ�������
	var selorder = "";    	//ѡ���ѯ�������
	//
	var seltabquery = "0";//0ȫ��;1����;2����;3˳��;4����;7ȡ��;
	var seltabidea = "0";//0ȫ��;1δ����;2ͬ�����;3��������;8ȡ������;9��ͬ�����	
	//
	var seltabid = "6";
	var seltabname = "ȫ������";
	//
	var sqlcount = 0;//�ܼ�¼��
	var sqlpagecount = 0;//��ҳ��
	var sqlpagenum = 18;//ÿҳ��¼��
	//
	var sqlselpage = 1;//ѡ��ҳ
	//
	var selbegpage = 0;		//��ҳ��ʼ
	var selendpage = sqlpagenum;//��ҳ����
	
	//
	var selsql = "";	//ѡ��SQL��ҳ���	
	var selallsql = "";	//ѡ��SQL���
	var selsqlth = "";	//ѡ��SQL���������
	//	
	var sumpopcount =0;
	var summoney=0;
	var sumolemoney=0;
	var sumnewmoney=0;
	//
	var accmode = 0;	//�û������޸ľ�������ʶ
	//
	var accflag =0;
	//
	var onecheck =0;
	var endcheck=0;
	var usercheck=0;
	var morecheck=0;
	var usermorecheck =0;
	var report=0;
	//
	var accdesc = "";
	//
	//���¾��� ����(������)
	function updateListMonery(){
		//
		spanstatus.innerHTML = "���¸�ҵ���������,���������!";
		//��������		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/relief/relieflist.do",            //������ҳ���ַ
	        {
	            action: "updateListMonery",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid
	        },
	        function(result) {                    	//�ص�����	        	
	        	//�����������
     			HiddenPageStatus();     			
     			//	        	
	        	//��ѯXML
				getCheckResultForXml("table","page");
	        }
	    );
	}		
	//��ȡ��������ѡ���
	function GetCheckPolicyOrderSelect(pardivid,sname){
		//
	  	var pardiv = document.getElementById(pardivid);
		//
	    $.post("/db/page/policy/approval/policycheckpage.do",            //������ҳ���ַ
	        {
	            action: "GetCheckPolicyOrderSelect",             //action����
	            sname: sname,
	            pid: selpid
	        },
	        function(result) {                    	//�ص�����
	        	pardiv.innerHTML = result;
	        	//�����¼�		      	
				$("#"+sname).change(
					function(){
						//��ȡ��ѯXML
	  					getCheckResultForXml("table","page");
					}
				);
	        }
	    );
	}
	//��ѯ����
	function QueryBtnOK(){
		//
	  	//��ȡ��ѯXML
	  	getCheckResultForXml("table","page");
	}		
	//��ȡ��ѯXML
	function getCheckResultForXml(mode,btnmode){
		//
		var jmode="table",jselect="",jfrom="",jwhere="",jbegpage="0",jendpage="0",jorder="";
		//
		var jsqlmode = mode;				//sql��ʽ(sql��ȡSQL���table��ȡ��ѯ���)
		var jfmno = "",jfmname = "",jfmlike = "1";	
		//		
		//     
	    jselect = "";	//ҵ���ѯʱ��ָ����ѯ�ֶ�  
	    jfrom = selfrom;
	    jwhere = selwhere;
	    
		jbegpage = selbegpage;
		jendpage = selendpage;
		//���˲�ѯ==============================================
		jfmno = $("#reqfmid").val();	//��ͥ���	
		jfmname = $("#reqfm").val();	//��������
		if(chbfm.checked == true){
			jfmlike = "1";
		}else{
			jfmlike = "0";
		}
		//
		var oq = document.getElementById("squery");	//����״̬
		if(oq!=null){
			seltabquery = $("#squery").val();
			if(seltabquery == null){
				seltabquery = "0";
			}			
		}else{
			seltabquery = "0";
		}
	  	var oi = document.getElementById("sidea");	//�������
		if(oi!=null){
			seltabidea = $("#sidea").val();
			if(seltabidea == null){
				seltabidea = "0";
			}
		}else{
			seltabidea = "0";
		}
		//
		//���˲�ѯ==============================================
		//�����ѯ==============================================
		//
		var ol = document.getElementById("orderbylist");
		
		if(rdgup.checked == true){
			if(ol!=null){	
	    		var soname = $("#orderbylist").val();		    	
		    	jorder = soname + " asc"; 
	    	}else{
	    		jorder = selorder;
	    	}
      	}else if(rdgdown.checked == true){
      		if(ol!=null){	
	      		var soname = $("#orderbylist").val();		    	
		    	jorder = soname + " desc";  
		    }else{
	    		jorder = selorder;
	    	}
      	}
      	//�����ѯ==============================================
      	//==================================================ת��
		jselect = encodeURI(jselect);
		jfrom = encodeURI(jfrom);
		jwhere = encodeURI(jwhere);
		jfmname = encodeURI(jfmname);
		//==================================================ת��
		//
		$("#menutabs").attr("disabled", "disabled");
		//��������		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/approval/policycheckpage.do", //������ҳ���ַ
	        {
	            action: "getCheckResultForXml",             //action����
	            jmode: jmode,
	            jselect: jselect,
	            jfrom: jfrom,
	            jwhere: jwhere,
	            jbegpage: jbegpage,
	            jendpage: jendpage,
	            jorder: jorder,
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jtabid: seltabid,
	            jtabquery: seltabquery,
	            jtabidea: seltabidea,
	            jfmno: jfmno,
	            jfmname: jfmname,
	            jfmlike: jfmlike,
	            jsqlmode: jsqlmode
	        },
	        function(result) {                    	//�ص�����
	        	//�����������
     			HiddenPageStatus();
     			ShowData(mode,result,btnmode);    
     			$("#menutabs").attr("disabled", ""); 			
	        }
	    );
	}
	//��ʾҳ��״̬div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2+80;  //�������
		//vtop= document.body.clientHeight/2; //�����߶�
		vtop= $("#menutabs").offset().top;      	
    	//
    	$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
	    var pagestatusdiv = $("#pagestatusdiv");        //��ȡ��ʾ��Ϣdiv		   		    
	    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
	}		
	//����ҳ��״̬div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //������Ϣ
	    spanstatus.innerHTML = "��ѯ�������Ե�...";
	}
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		//ѡ��ҵ���ѯ	
		selpid = "<%=reqpid%>";		 	 //ҵ��ѡ��			
		//
		selselect = "<%=reqselect%>";	 //��ѯѡ���ֶ�
		selfrom = "<%=reqfrom%>";		 //��ѯѡ���
		selwhere = "<%=reqwhere%>";		 //��ѯѡ������
		selorder = "<%=reqorder%>";		 //��ѯѡ������	
		//
		//��ȡ��������ѡ���
		GetCheckPolicyOrderSelect("spanorderby","orderbylist");
		
		//��ѯ��ǩ
		seltabid  = "<%=siontabid%>";	 //��ѯ��ǩID
		//��ǩ��
		init("menus");		
		//��ѯ��
		QueryPage(seltabid);
		//���¾��� ����(������)
		updateListMonery();					
	}
  </script>
  <body onload = "IniPage()">
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img><span id = 'spanstatus'>��ѯ�������Ե�...</span></div> 
    <table width='100%' cellpadding='0' cellspacing='0' class='tab'>
	   	<tr>
	   		<td class = "myspace" colspan = "3">
	   			<div id = "menutabs">
	   				<ul id="menus" class="menu">
	   					<li id="li6" class="default"> ȫ������ </li>	   					
				    	<li id="li0" class="default"> �������� </li>
				    	<li id='li1' class='default'> �������� </li>				    	
				    	<li id='li2' class='default'> ���������� </li>
				    	<li id='li3' class='default'> �������� </li>
				    	<li id='li4' class='default'> ����˳�� </li>
				    	<li id='li5' class='default'> �������� </li>				    	
				    </ul>
	   			</div>
	   		</td>
	   	</tr>
	   	<tr>
	   		<td class = "myspace" valign="top">
	   			<div id='checkresultcon'>
	   				<fieldset><legend>��ѯѡ��<span id = 'spanaccdesc'></span></legend>
				  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>
				  			<tr valign='middle'>				  				
				  				<td align='center' valign='middle' class='myspace'>
				  					<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>
				  						<tr valign='middle' style = 'display:none;'>							  				
							  				<td align='center' valign='middle' style = 'font-size:13px;' class='colValue'  colspan = '3'>
							  					��ͥ���<input type="text" id = "reqfmid" size = '15'></input>
							  					��������<input type="text" id = "reqfm"  size = '8'></input>
							  					<input type='checkbox' name='chbfm' id='chbfm' checked='checked'>ģ��							  												  					
							  				</td>					  				
							  			</tr>				  						
							  			<tr valign='middle'>								  										  							  				
							  				<td id = 'tdq' width = "160" align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span id = 'selectquery'></span>						  												  					
							  				</td>
							  				<td id = 'tdi' width = "160" align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span id = 'selectidea'></span>							  												  					
							  				</td>
							  				<td align='center' valign='middle' style = 'font-size:13px;' class='colValue'>
							  					<span style = "color: #6BA6FF;">����ѡ��</span>
							 					<span id = "spanorderby"></span> 					
							 					<input type='radio' name='rdg' id='rdgup' checked='checked' onclick='getCheckResultForXml("table","page");'></input><span  style = "color: #6BA6FF;">����</span>
												<input type='radio' name='rdg' id='rdgdown' onclick='getCheckResultForXml("table","page");'></input><span  style = "color: #6BA6FF;">����</span>
							  				</td>							  				
							  			</tr>							  			
				  					</table>
				  				</td>
				  				<td width = "80" align='center' class='myspace'>
				  					<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'> 
							  			<tr valign='middle'>
							  				<td align='right' valign='middle' class='myspace'>
							  					<button class = 'btn' onclick="QueryBtnOK()"> ��  ѯ </button>	
							  					<button class = 'btn' onclick='getCheckResultForXml("sql","export");'> ��  �� </button>
							  				</td>			  				
							  			</tr>
				  					</table>
				  				</td>
				  			</tr>
				  			
				  		</table>
				  	</fieldset>
				  	<div align='center' id = 'familytb'></div>		  	
	   			</div>	
	   			  			
	   		</td>
	   	</tr>
	   			   	  	
	</table>
	<div id = "infocounttb"></div>
	<div id = "paginationtb"></div> 
	<div id = "ideainfotb">
	    <fieldset><legend>��������</legend>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center'>
	  			<tr valign='middle'>	  						
					<td align='left' valign='middle' class='mybackground myspace'>	  					
	 					<input type="checkbox" id="chkPageAll" title="��ҳ[ȫѡ/ȡ��]" onclick="CheckPage()"></input>
	 					<span  style = "color: #6BA6FF;">��ҳ[ȫѡ/ȡ��]</span>						  					
					</td>				
					<td width = "480" align='right' valign='middle' class='mybackground myspace'>
						<button class = 'btn' id = 'btnpagemoney' onclick="CallMoreIdea()">����ѡ��</button>
	  					<button class = 'btn' id = 'btnmoney' onclick='getCheckResultForXml("sql","all");'>����ȫ��</button>
						<button class = 'btn' id = 'btnallpagemoney' onclick="CallMoneyIdea()">����������ѡ��</button>
						<button class = 'btn' id = 'btnallmoney' onclick='getCheckResultForXml("sql","allmoney");'>����������ȫ��</button>
					</td> 				
	  			</tr>
	  			
	  		</table>
	  	</fieldset>
  	</div>
  </body>
</html>
<script  type="text/javascript">
	//
	function init(ids){
		//document.getElementById(ids).getElementsByTagName('li')[0].className='active';
		document.getElementById('li'+seltabid).className='active';			
		document.getElementById(ids).onclick=function(){onmousOver(ids);}//�������Ч��					
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
			//
			var tid,tname;
			tid =  obj.id;
			tname = obj.firstChild.nodeValue;
			tid = tid.substring(2,tid.length);	
			//
			seltabid = tid;
		  	seltabname = tname;	
		  	//
		  	seltabquery = "0";//0ȫ��;1����;2����;3˳��;4����;7ȡ��;
			seltabidea = "0";//0ȫ��;1δ����;2ͬ�����;3��������;8ȡ������;9��ͬ�����	
		  	//��ѯ��
		  	QueryPage(seltabid);
		  	//
			selbegpage = 0;		//��ҳ��ʼ
		 	selendpage = sqlpagenum;//��ҳ����
		 	sqlselpage = 1;//��ʼ��һҳ
		  	//��ѯXML
		  	getCheckResultForXml("table","page");		  	
		}
	}	
	//��ѯ��
	function QueryPage(tab){	
		//
		var qall = "<option value='0'>ȫ��</option>";
		var qnew = "<option value='1'>����</option>";		
		var qold = "<option value='2'>˳��</option>";
		var qup = "<option value='3'>����</option>";
		var qdown = "<option value='4'>����</option>";
		var qdel = "<option value='7'>�����</option>";
		//
		var iall = "<option value='0'>ȫ��</option>";
		var inew = "<option value='1'>δ����</option>";
		var iok = "<option value='2'>ͬ�����</option>";
		var ire = "<option value='3'>��������</option>";
		var idel = "<option value='8'>ȡ������</option>";
		var inot = "<option value='9'>��ͬ�����</option>";		
				
		//
		var sqhtml = "<select id='squery' style = \"font-size:12px;width = '64';\" onchange=\"getCheckResultForXml('table','page');\">";
		var sihtml = "<select id='sidea' style = \"font-size:12px;width = '84';\" onchange=\"getCheckResultForXml('table','page');\">";
		//
		if(tab == "0"){
			$("#tdq").css({"display":"none"});
			$("#tdi").css({"display":"none"});			
	  	}else if(tab == "1"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"none"});	  	
			sqhtml += qall + qup + qdown;
	  	}else if(tab == "2"){
			$("#tdq").css({"display":"none"});	
	  		$("#tdi").css({"display":"block"});
			sihtml += iall + inew + ire + idel;
	  	}else if(tab == "3"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"none"});
	  		sqhtml += qall + qnew + qup + qdown;
	  	}else if(tab == "4"){
	  		$("#tdq").css({"display":"none"});	
	  		$("#tdi").css({"display":"none"});
	  	}else if(tab == "5"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"block"});
	  		sqhtml += qall + qnew + qup + qdown + qdel;
	  		sihtml += iall + iok + inot + ire + idel;
	  	}else if(tab == "6"){
	  		$("#tdq").css({"display":"block"});	
	  		$("#tdi").css({"display":"block"});
	  		sqhtml += qall + qnew + qold + qup + qdown + qdel;
	  		sihtml += iall + inew + iok + inot + ire + idel;
	  	}
	  	sqhtml += "</select>";
	  	selectquery.innerHTML = "����״̬"+sqhtml;
	  	sihtml += "</select>";
		selectidea.innerHTML = "�������"+sihtml; 
	}
	//��������������
	function CheckIdeaTab(){
		//�޲�ѯ��¼
		if(sqlcount==0){
			$("#ideainfotb").css({"display":"none"});
		}else{
			$("#ideainfotb").css({"display":"block"});
		}
		var smorecheck = "0";		
		if(usercheck == 1){
			smorecheck = "1";
			if(morecheck == 1){
				smorecheck = "2";
				if(usermorecheck != 1){
					smorecheck = "1";
				}
			}
		}
		//����������
		if(smorecheck != "2"){
			$("#ideainfotb").css({"display":"none"});
		}
		//����˳�ӻ�����������������ȫ������
		if(seltabid=="4" || seltabid=="5" || seltabid=="6"){
			$("#ideainfotb").css({"display":"none"});
		}
		//�������α�ʶ
		if(accflag != "0"){	//0δ����1������2��ɽ����ʶ
			$("#ideainfotb").css({"display":"none"});
		}
		if(onecheck == "1"){//��һ������
			//$("#btnmoney").css({"display":"none"});
			if(accmode == "0"){	//�û������޸ľ�����
				$("#btnallpagemoney").css({"display":"none"});
				$("#btnallmoney").css({"display":"none"});	
			}
		}else{
			$("#btnallpagemoney").css({"display":"none"});
			$("#btnallmoney").css({"display":"none"});	
		}
		//
		spanaccdesc.innerHTML = "&nbsp;&nbsp;"+accdesc;		
	}
	//��������ת��
	function ReplaceMoney(data){
		var s = data.toString();
        if(/[^0-9\.]/.test(s)) return "0.00";
        s=s.replace(/^(\d*)$/,"$1.");
        s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
        s=s.replace(".",",");
        var re=/(\d)(\d{3},)/;
        while(re.test(s))
                s=s.replace(re,"$1,$2");
        s=s.replace(/,(\d\d)$/,".$1");
        //return "��" + s.replace(/^\./,"0.");
        return s.replace(/^\./,"0.");       
    }
	
	//��Ϣ������
	function InfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='myspace' style = 'border-top-width: 1px;' >";				
				html += "<td height='23' align='center' >";
	  				html += "<span style = 'color: #6BA6FF;'>�ܼ�ͥ����["+sqlcount+"]</span>";		  				
	  			html += "</td>";
				html += "<td height='23' align='center' >";
	  				html += "<span style = 'color: #6BA6FF;'>�ܱ����˿�["+sumpopcount+"]</span>";		  				
	  			html += "</td>";
	  			html += "<td height='23' align='center' >";
	  				html += "<span style = 'color: #6BA6FF;'>�ܼ�������["+ReplaceMoney(summoney)+"]</span>";
	  			html += "</td>";
	  			html += "<td height='23' align='center' >";
		   			html += "<span style = 'color: #6BA6FF;'>�����ھ�����["+ReplaceMoney(sumolemoney)+"]</span>";
	  			html += "</td>";
	  			html += "<td height='23' align='center' >";
		   			html += "<span style = 'color: #6BA6FF;'>���ⷢ������["+ReplaceMoney(sumnewmoney)+"]</span>";
	  			html += "</td>";
			html += "</tr>";
		html=html+"</table>";			
		//
		//			
		infocounttb.innerHTML=html;
	}		
	//��ҳ��
	function Pagination(){	
		//
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";				
			html += "<td height='25'>";
				html += "��["+sqlcount+"]����¼  ��["+sqlpagecount+"]ҳ  ";	
				if(sqlselpage<=1){
					html += "<span title = '��ҳ'>��ҳ</span> ";
					html += "<span title = '��һҳ'>��һҳ</span>�0�2";
				}else{
					html += "<span title = '��ҳ' class = 'pointer' onclick=\"ExePage('beg')\">��ҳ</span> ";
					html += "<span title = '��һҳ' class = 'pointer' onclick=\"ExePage('back')\">��һҳ</span>�0�2";
				}
				if(sqlselpage==sqlpagecount || sqlpagecount <= 1){	
					html += "<span title = '��һҳ'>��һҳ</span> ";
					html += "<span title = 'βҳ'>βҳ</span>�0�2";
				}else{
					html += "<span title = '��һҳ' class = 'pointer' onclick=\"ExePage('next')\">��һҳ</span> ";
					html += "<span title = 'βҳ' class = 'pointer' onclick=\"ExePage('end')\">βҳ</span> ";
				}
				html += "ת�� ��<input type='text' id = 'selectpage' size = '3' value = '"+sqlselpage+"'" + ">ҳ</input> ";	
				html += "<input type='button' value='Go' onclick=\"ExePage('go')\"></input> ";	
				html += "<span id = \"spanpages\"></span> ";				
				html += "  ÿҳ<input type=\"text\" size = \"3\" id = \"divpagerow\" value = '"+sqlpagenum+"'" + " onblur = \"ChangPageRow(this)\"></input>����¼";
				html += "</td>";
			html += "</tr>";
		html=html+"</table>";			
		//
		//			
		paginationtb.innerHTML=html;
	}
	//����ѡ��ҳ��ѯ
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
		//�����ҳ����
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;	
		//alert("b: "+ selbegpage + " e: " + selendpage);	
		//��ѯXML
		getCheckResultForXml("table","page");
	}
	//����ҳ��ʾ����
	function ChangPageRow(src){
		//
		sqlpagenum = src.value;
		//
		selbegpage = 0;		//��ҳ��ʼ
	 	selendpage = sqlpagenum;//��ҳ����
	 	sqlselpage = 1;//��ʼ��һҳ
		//
		//��ѯXML
		getCheckResultForXml("table","page");
	}	
</script>
<script type="text/javascript">
	//ҳȫ��ѡ��/ȡ��
	function CheckPage()
	{  
		var chk = chkPageAll.checked;
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="cbx[]") {
				document.all[i].checked=chk;
				chkRow(document.all[i]);
			}
		}
	}
	//��ѡ��Ԫ���ɫ
	function chkRow(obj){
		var r = obj.parentElement.parentElement;
		if(obj.checked){ 
			r.style.backgroundColor="#E6E9F2";
		}else {
			if(r.rowIndex%2==1){
				r.style.backgroundColor="";
			}else{
				r.style.backgroundColor="#F5F5F5";
			}
		}
	}
	//�������ѡ��Ԫ���ɫ
	function chkTbRow(obj){
		var r = obj.parentElement.parentElement;
		if(obj.checked){ 
			r.style.backgroundColor="#E6E9F2";
		}else {
			if(r.rowIndex%2==1){
				r.style.backgroundColor="";
			}else{
				r.style.backgroundColor="#F5F5F5";
			}
		}
		//ȫѡȡ��
		chkPageAll.checked = false;
	}
</script>
<script type="text/javascript">
	//
	//
	function ShowData(mode,data,btnmode){
		var xmlDoc;	
		var xtable;
		//��ʼ��
		selsql="";
		selallsql="";
		selsqlth="";		
		//
		accflag = 0;
		//
		onecheck =0;
		endcheck=0;
		usercheck=0;
		morecheck=0;
		usermorecheck =0;
		report=0; 
		//
		accdesc = "";	
		//
		accmode = 0;
		//		
		sqlcount=0;
		sumpopcount =0;
		summoney=0;
		sumolemoney=0;
		sumnewmoney=0;	
		//ȫѡȡ��
		chkPageAll.checked = false;	
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//�ܻ���
			var countarr=root.selectNodes("/data/counts/sqlcount");
			sqlcount =countarr.item(0).firstChild.data;
			//��ҳ��
			var j1 = sqlcount/sqlpagenum;
			var j2=Math.round(j1);
			if(j1>j2){
				sqlpagecount = j2+1;
			}else if(j1<=j2){
				sqlpagecount = j2;
			}
			//�����˿ڻ���		
			countarr=root.selectNodes("/data/counts/sumpopcount");
			sumpopcount =countarr.item(0).firstChild.data;			 
			//�����������
			countarr=root.selectNodes("/data/counts/summoney");
			summoney =countarr.item(0).firstChild.data;	
			//���ھ��������			
			countarr=root.selectNodes("/data/counts/sumolemoney");
			sumolemoney =countarr.item(0).firstChild.data;			
			//�ⷢ���������
			countarr=root.selectNodes("/data/counts/sumnewmoney");
			sumnewmoney =countarr.item(0).firstChild.data;
			//==============��Ҫ����������ʶ====================
			var accmodearr=root.selectNodes("/data/accmode/accmode");
			accmode =accmodearr.item(0).firstChild.data;
			//==============��Ҫ����������ʶ====================
			//==============��ɽ����ʶ=========================			
			var accflagarr=root.selectNodes("/data/accflag/accflag");
			accflag =accflagarr.item(0).firstChild.data;	
			//
			var accdescarr=root.selectNodes("/data/accdesc/accdesc");
			accdesc =accdescarr.item(0).firstChild.data;
			//===================������ʶ��ʶ====================
			//��һ��������ʶ		
			var flagarr=root.selectNodes("/data/flags/onecheck");
			onecheck =flagarr.item(0).firstChild.data;			 
			//�����ʶ
			flagarr=root.selectNodes("/data/flags/endcheck");
			endcheck =flagarr.item(0).firstChild.data;	
			//�û�����Ȩ�ޱ�ʶ			
			flagarr=root.selectNodes("/data/flags/usercheck");
			usercheck =flagarr.item(0).firstChild.data;			
			//ҵ������������ʶ
			flagarr=root.selectNodes("/data/flags/morecheck");
			morecheck =flagarr.item(0).firstChild.data;	
			//�û�����������ʶ
			flagarr=root.selectNodes("/data/flags/usermorecheck");
			usermorecheck =flagarr.item(0).firstChild.data;
			//�û������ϱ�������ʶ
			flagarr=root.selectNodes("/data/flags/report");
			report =flagarr.item(0).firstChild.data;
			//===================������ʶ��ʶ====================
			//=================��ѯSQL���ģʽ====================
			if(mode == "sql"){	//����SQL���     			
     			//��ѯSQL��ҳ���		
				var sqlarr=root.selectNodes("/data/sql/sql");
				selsql =sqlarr.item(0).firstChild.data;			 
				//��ѯSQL���
				sqlarr=root.selectNodes("/data/sql/countsql");
				selallsql =sqlarr.item(0).firstChild.data;	
				//��ѯSQL���������
				sqlarr=root.selectNodes("/data/sql/xmlth");
				selsqlth =sqlarr.item(0).firstChild.data;			
   			}else{					//���ز�ѯ��� 
				//
				//�����������
				var familyarr=root.selectNodes("/data/table/elements");
				xtable =familyarr.item(0).firstChild.data;
				familytb.innerHTML=xtable;
				//
				//JS�������
				new TableSorter("requesttb");	
				/*
				new TableSorter("tb2", 0, 2 , 5, 6);
				new TableSorter("tb3").OnSorted = function(c, t)
				{
					alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
				}
				*/
   			}
   			//=================��ѯSQL���ģʽ====================			
		}else{	
			var thtml;
			thtml = "<table width='100%' cellpadding='0' cellspacing='0'>"
				thtml += "<tr class='colField'>";				
					thtml += "<td height='23' align='center'></td>";
				thtml += "</tr>";
				thtml += "<tr class='colValue'>";				
					thtml += "<td height='23' align='center' style = 'font-size:14px;color: #FF0099;'>"+data+"</td>";
				thtml += "</tr>";
			thtml=thtml+"</table>";
			familytb.innerHTML=thtml;
		}
		//=====================//
		//��Ϣ������
		InfoCount();
	  	//��ҳ��
	  	Pagination();
	  	//��������������
		CheckIdeaTab();
		//=====================//
		//��ѯ����ģʽ(pageҳ���ѯallȫ����ѯallmoney����ȫ����ѯexport������ѯ)
		//ȫѡ������ť��
		if(btnmode == "all"){
			//����ȫ��
			CallAllIdea();
		}else if(btnmode == "allmoney"){
			//��������ȫ��
			CallAllMoneyIdea();	
		}else if(btnmode == "export"){			
			//�����ļ�
			exportResult();
		}
	}	
</script>
<script type="text/javascript">
	//�򿪼�ͥ�鿴ҳ��
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣ��Ƭ","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//�򿪼�ͥ�༭ҳ��
	function infoeditaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/info/neweditor/editorframe.jsp?nodeName=FAMILY&nodeId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣά��&updateListMonery();","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//
	//��ȡ��ͥ����ҵ�������ϸ��Ϣ
	function GetCheckPolicyInfosHtml(fmid,memid){
	    //
		//var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var bWidth= 450; //�������
		var sArgu = "qpid="+selpid+"&qfmid="+fmid+"&qmemid="+memid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideainfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ�������Ϣ��ϸ","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
	//���߷ü�¼��ѯҳ��
	function CallInterviewDialog(fmid){		
		var afrom = "",awhere = "",aorder = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		var sArgu = "qfrom="+afrom+"&qwhere="+awhere+"&qorder="+aorder+"";
		var oUrl = "/db/page/policy/manage/policyinterview.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ�߷ü�¼�Ǽ�","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
<script type="text/javascript">
	//�򿪵�������ҳ��
	function CallOneIdea(fmid){	
		var mode = "one",pagetitle = "";	
		//	
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		//����
		pagetitle = "--" + seltabname;
		//
		var sArgu = "qtabid="+seltabid+"&qmode="+mode+"&qpid="+selpid+"&qfmids="+fmid+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policymoreidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��������"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//����������ҳ��
	function CallMoreIdea(){
		var fmids = "",mode = "more",pagetitle = "";
		//
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		//����
		pagetitle = "--" + seltabname;
		//
		//���ѡѡ��
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="cbx[]") {
				if(document.all[i].checked == true){
					if(fmids == ""){
						fmids = document.all[i].id;
					}else{
						fmids += "," + document.all[i].id;
					}					
				}
			}
		}
		if(fmids==""){return;}
		//
		var sArgu = "qtabid="+seltabid+"&qmode="+mode+"&qpid="+selpid+"&qfmids="+fmids+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policymoreidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��������"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//��ȫ������ҳ��
	function CallAllIdea(){
		var pagetitle = "",countsql = "";	
		if(selallsql == ""){return;}
		countsql = selallsql;	
		//	
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		//����
		pagetitle = "--" + seltabname;
		//
	  	//==================================================ת��
		//�����ַ�ת������(�Ա�ҳ�����)
		countsql = encodeURIComponent(countsql);
		//==================================================ת��
		var sArgu = "qtabid="+seltabid+"&qpid="+selpid+"&qcountsql="+countsql+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policyallidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"����ȫ��"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//�򿪶�������ҳ��
	function CallMoneyIdea(){
		//
		var fmids = "",pagetitle = "";
		//	
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		//����
		pagetitle = "--" + seltabname;
		//
	  	//���ѡѡ��
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="cbx[]") {
				if(document.all[i].checked == true){
					if(fmids == ""){
						fmids = document.all[i].id;
					}else{
						fmids += "," + document.all[i].id;
					}					
				}
			}
		}
		if(fmids==""){return;}
	  	//
		var sArgu = "qtabid="+seltabid+"&qpid="+selpid+"&qfmids="+fmids+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policymoneyidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"����������"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//�򿪶���ȫ������ҳ��
	function CallAllMoneyIdea(){
		//
		var fmids = "",pagetitle = "",countsql = "";		
		//	
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		//����
		pagetitle = "--" + seltabname;
		//
	  	if(selallsql == ""){return;}
  		countsql = selallsql;
  		//==================================================ת��
		//�����ַ�ת������(�Ա�ҳ�����)
		countsql = encodeURIComponent(countsql);
		//==================================================ת��
	  	//
		var sArgu = "qtabid="+seltabid+"&qpid="+selpid+"&qcountsql="+countsql+"&qsqlcount="+sqlcount+"&qsumpopcount="+sumpopcount
					+"&qsummoney="+summoney+"&qsumolemoney="+sumolemoney+"&qsumnewmoney="+sumnewmoney
					+"&qaccmode="+accmode+"&qonecheck="+onecheck+"&qendcheck="+endcheck+"&qreport="+report+"";
		var oUrl = "/db/page/policy/approvalidea/policyallmoneyidea.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"������ȫ������"+pagetitle+"&getCheckResultForXml('table','page')","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//���������������
	function DelIdea(fmid){
		//
		//
	    if (!confirm("�Ƿ�ȷ�������ü�ͥ�������?")) {
	        return;
	    }
	    $.post("/db/page/policy/approval/policycheckpage.do",            //������ҳ���ַ
	        {
	            action: "DelIdea",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jfmids: fmid,
	            jendcheck: endcheck,
	            jreport: report
	        },
	        function(result) {                    	//�ص�����
	        	alert(result);	
	        	//��ѯXML
				getCheckResultForXml("table","page");        	
	        }
	    );	    
	}
</script>
<script type="text/javascript">
	//����������ҳ��
	function GetCheckIdeaFlow(fmid){
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		var sArgu = "qfmids="+fmid+"&qpid="+selpid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideaflow.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��������","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
<script type="text/javascript">
	//�����ļ�
	function exportResult(){
		var jsqlth = "",jallsql = "";
		//��ͷ�Ͳ�ѯSQL���
		jsqlth = selsqlth;
		jallsql = selallsql;
		//==================================================ת��
		jsqlth = encodeURI(jsqlth);
		jallsql = encodeURI(jallsql);
		//==================================================ת��
		//
	    $.post("/db/page/policy/export/exportmanage.do",            //������ҳ���ַ
	        {
	            action: "exportResult",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jsqlth: jsqlth,
	            jallsql: jallsql
	        },
	        function(result) {                    	//�ص�����
     			//����׼������
     			if(result=="1"){		 			
		 			//var bWidth = document.body.clientWidth-30;   //�������
					//var bHeight= document.body.clientHeight-80; //�����߶�	
					var bWidth = 300;   //�������
					var bHeight= 200; //�����߶�	
					var oUrl = "/db/page/system/exportfile/exportExcel.do?";
					jBox.open("iframe-jBoxID","iframe",oUrl,"����Excel�ļ�","width="+bWidth+",height="+bHeight
						+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		 		}	
	        }
	    );
	}
</script>