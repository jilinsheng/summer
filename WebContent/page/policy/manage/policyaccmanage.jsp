<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//�Ӳ�ѯ�����qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "";    //��ҵ����
	}
	//�Ӳ�ѯ�����qpname
	String reqpname = request.getParameter("qpname");
	reqpname = new String(reqpname.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqpname == null) {
		//Ĭ��Ϊ��
		reqpname = "";    //��ҵ������
	}
	//����qmode
	String reqmode= request.getParameter("qmode");		
	if (reqmode == null) {
		//Ĭ��Ϊ��
		reqmode = "edit";    //��ģʽ
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
    
    <title>ҵ������������</title>
    
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
			font-family: "����";
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
  <SCRIPT LANGUAGE="JavaScript">
	var mynewdt,mynewy,mynewm,mynewd;
	var myDate = new Date();
	    		myDate.getYear();       //��ȡ��ǰ���(2λ)
	    mynewy =myDate.getFullYear();   //��ȡ���������(4λ,1970-????)
	    mynewm =myDate.getMonth()+1;      //��ȡ��ǰ�·�(0-11,0����1��)
	    mynewd =myDate.getDate();       //��ȡ��ǰ��(1-31)
	    	    myDate.getDay();        //��ȡ��ǰ����X(0-6,0����������)
			    myDate.getTime();       //��ȡ��ǰʱ��(��1970.1.1��ʼ�ĺ�����)
			    myDate.getHours();      //��ȡ��ǰСʱ��(0-23)
			    myDate.getMinutes();    //��ȡ��ǰ������(0-59)
			    myDate.getSeconds();    //��ȡ��ǰ����(0-59)
			    myDate.getMilliseconds();   //��ȡ��ǰ������(0-999)
		mynewdt=myDate.toLocaleDateString();    //��ȡ��ǰ����
			    var mytime=myDate.toLocaleTimeString();    //��ȡ��ǰʱ��
			    myDate.toLocaleString( );       //��ȡ������ʱ��
  </SCRIPT>
  <script  type="text/javascript">
		//
		var empid = "";        	//��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼����
		var pid = "";			//��ǰҵ����
		var pname = "";			//��ǰҵ������
		var mode = "";			//���������������ģʽ
		//
		//		    
		var vleft = 0;  	//�������
		var vtop= 0; 		//�����߶�		
		//
		//
		//��ȡ�����б�
	    function GetPolicyAccXmls(){  
	    	var pardiv;
		    var tbegpage,tendpage;
		    var tdeptid,tempid,tpid,tyear,tbegmonth,tendmonth,tbegdt,tenddt;
		    //
		    tbegpage = selbegpage;
		    tendpage = selendpage;
		    //
		    tdeptid = deptid;
		    tempid = empid;
		    tpid = pid;
		    //
		    pardiv = document.getElementById("queryyear");
		    if(null == pardiv){
		    	tyear = "";
		    }else{
		    	tyear = $("#queryyear").val();
		    }
		    pardiv = document.getElementById("querybegmonth");
		    if(null == pardiv){
		    	tbegmonth = "";
		    }else{
		    	tbegmonth = $("#querybegmonth").val();		    	
		    }
		    pardiv = document.getElementById("queryendmonth");
		    if(null == pardiv){
		    	tendmonth = "";
		    }else{
		    	tendmonth = $("#queryendmonth").val();		    	
		    }
		    tbegdt = $("#begdt").val();
		    tenddt = $("#enddt").val();
		    //		    
			DisplayPageStatus(); 
			//
		    //
		    $.post("/db/page/policy/acc/policyaccquery.do",      //������ҳ���ַ
		        {
		            action: "getPolicyAccXmls", //action����
		            tbegpage: tbegpage,
		            tendpage: tendpage,  //������ҳ
		            tdeptid: tdeptid,
		            tempid: tempid,
		            tpid: tpid,
		            tyear: tyear,
		            tbegmonth: tbegmonth,
		            tendmonth: tendmonth,		           
		            tbegdt: tbegdt,
		            tenddt: tenddt
		        },
		        function(result) {   //�ص�����	        	
		        	//�����������
      				HiddenPageStatus();         	
		  			//		  		
		          	ShowData(result);                          
		    });       
	    }
		//ҵ�������������������
		function SetPolicyAllAccFlag(mode){
			var sms = "";
			if(mode=="1"){
				sms = "�������";
			}else{
				sms = "�����������";
			}
			//ȷ��
		    if (!confirm("�Ƿ�ȷ��["+sms+"]��ҵ��")) {
		        return;
		    }
		  	// 
		  	//��������    
			//DisplayPageStatus(); 
			//��ʾ�������		    		
			DisplayResult("��������,��ˢ��ҳ����Ժ�...","1");
		 	//     
			$.post("/db/page/policy/acc/policyaccedit.do",      //������ҳ���ַ
			    {
			        action: "setPolicyAllAccFlag", //action����
			        pid: pid,
			        empid: empid,
			        mode: mode                             
			    },
			    function(result) {   //�ص�����	
			    	//�����������
	   				//HiddenPageStatus();  
	   				//    	
			    	//��ʾ�������		    		
				    DisplayResult(result,"0");
				    //��ʼ��ҳ��
	     			IniPage();				    	      	                                         
			    }
			);			        
		}
		//
		//ɾ����ǰҵ�����������ݺ��·�
		function DeletePolicyAllCheckAccItems(){
			var tyear,tmonth;
			tyear = document.getElementById("divaccyear").innerHTML;
			tmonth = document.getElementById("divaccmonth").innerHTML;
			//
			if(pid=="" || pid == null){
				alert("��ǰҵ�񲻴���!");
				return;
			}
			//ȷ��
		    if (!confirm("�Ƿ�ȷ��ɾ����ҵ��������ڣ�")) {
		        return;
		    }
			//��������    
			DisplayPageStatus(); 
		 	//
		 	//      
			$.post("/db/page/policy/acc/policyaccedit.do",      //������ҳ���ַ
			    {
			        action: "deletePolicyAllCheckAccItems", //action����
			        pid: pid,                            
			        empid: empid,
			        tyear: tyear,
			        tmonth: tmonth
			    },
			    function(result) {   //�ص�����
			    	//�����������
    				HiddenPageStatus();  
    				//			    		    	
			    	//��ʾ�������		    		
				    DisplayResult(result,"0"); 
				    //��ʼ��ҳ��
	     			IniPage();       						      	                                         
			    }
			); 
		}
		//
		//������ǰҵ�����������ݺ��·�
		function SetPolicyAllCheckAccItems(){
			var tyear = "",tmonth = "";
			
		    //
			tyear = $("#newaccyear").val();
			tmonth = $("#newaccmonth").val();
			if(pid=="" || pid == null){
				alert("��ǰҵ�񲻴���!");
				return;
			}
			if(tyear=="" || tyear == null){
				alert("��������������ݲ���Ϊ��!");
				return;
			}
			if(tmonth=="" || tmonth == null){
				alert("�������������·ݲ���Ϊ��!");
				return;
			}
		  	//
		  	//ȷ��
		    if (!confirm("�Ƿ�ȷ��������ҵ��������ڣ�")) {
		        return;
		    } 		  	
		  	//
		  	//��������    
			DisplayPageStatus(); 
		 	//      
			$.post("/db/page/policy/acc/policyaccedit.do",      //������ҳ���ַ
			    {
			        action: "setPolicyAllCheckAccItems", //action����
			        pid: pid,                            
			        empid: empid,
			        tyear: tyear,
			        tmonth: tmonth
			    },
			    function(result) {   //�ص�����
			    	//�����������
    				HiddenPageStatus();  
    				//			    		    	
			    	//��ʾ�������		    		
				    DisplayResult(result,"0"); 
				    //��ʼ��ҳ��
	     			IniPage();       						      	                                         
			    }
			);        
		}	
		//
		//ȡ����������ѡ��
		function GetSelectYear(pardivid,sname){
		  	//
		  	//��������    
			//DisplayPageStatus(); 
		 	//      
			$.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
			    {
			        action: "getSelectYear", //action����
			        sname: sname			                                
			    },
			    function(result) {   //�ص�����	
			    	//�����������
    				//HiddenPageStatus();  
    				//    						    	
			    	if(sname == "newaccyear"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewy);
			    	}else if(sname == "queryyear"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewy);
			    	}		      	                                         
			    }
			);        
		}
		//
		//ȡ����������ѡ��
		function GetSelectMonth(pardivid,sname){
		  	//
		  	//��������    
			//DisplayPageStatus(); 
		 	//      
			$.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
			    {
			        action: "getSelectMonth", //action����
			        sname: sname			                                
			    },
			    function(result) {   //�ص�����	
			    	//�����������
    				//HiddenPageStatus();  
    				//
			    	if(sname == "newaccmonth"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewm);	
			    	}else if(sname == "querybegmonth"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewm);	
			    	}else if(sname == "queryendmonth"){
			    		var pardiv = document.getElementById(pardivid);	
			    		pardiv.innerHTML = result;
			    		$("#"+sname).val(mynewm);
			    		//ȡ��ҵ����㷵��XML��ʽ		  
						GetPolicyAccXmls();		
			    	}			    			    		          						      	                                         
			    }
			);        
		}		
		//
		//��ȡ��ǰҵ����������������
		function GetPolicyAllCheckAccYearItems() {
		 	//			
		    $.post("/db/page/policy/acc/policyaccedit.do",      //������ҳ���ַ
			    {
			        action: "getPolicyAllCheckAccYearItems", //action����
			        pid: pid,                            
			        empid: empid
			    },
			    function(result) {   //�ص�����	    	    				
	   				//
	   				if(result==""||result==null){    				
	   					$("#accmanagetb").css({"display":"none"});
	   					$("#newaccmanagetb").css({"display":"block"});
	   				}else{
	   					$("#accmanagetb").css({"display":"block"});
	   					$("#newaccmanagetb").css({"display":"none"});
	   					divaccyear.innerHTML=result;
	   				}			    	                          
			    }
			); 
		}
		//
		//��ȡ��ǰҵ����������·�����
		function GetPolicyAllCheckAccMonthItems() {			
		 	//
			$.post("/db/page/policy/acc/policyaccedit.do",      //������ҳ���ַ
			    {
			        action: "getPolicyAllCheckAccMonthItems", //action����
			        pid: pid,                            
			        empid: empid
			    },
			    function(result) {   //�ص����� 
	   				//
	   				if(result==""||result==null){    				
	   					$("#accmanagetb").css({"display":"none"});
	   					$("#newaccmanagetb").css({"display":"block"});
	   				}else{  
	   					$("#accmanagetb").css({"display":"block"});
	   					$("#newaccmanagetb").css({"display":"none"});  				
	   					divaccmonth.innerHTML=result;
	   				}                      
			    }
			); 
		}
		//��ȡ�Ƿ���ڵ�ǰҵ������������ڴ���ı�ʶ
		function GetPolicyAllCheckAccNoOverFlag() {
		 	//			
		    $.post("/db/page/policy/acc/policyaccedit.do",      //������ҳ���ַ
			    {
			        action: "getPolicyAllCheckAccNoOverFlag", //action����
			        pid: pid,                            
			        empid: empid
			    },
			    function(result) {   //�ص�����			    	    				
	   				//
	   				if(result=="1"){
	   					//��ʾ�������		    		
				    	DisplayResult("��ǰҵ������������ڴ���,����ʱˢ��ҳ��!","1"); 
				    	$("#btnacc").attr("disabled", "disabled");
						$("#btndelacc").attr("disabled", "disabled");
						$("#btnnewacc").attr("disabled", "disabled");
	   				}else{
	   					$("#btnacc").attr("disabled", "");
						$("#btndelacc").attr("disabled", "");
						$("#btnnewacc").attr("disabled", "");
	   				}			    	                          
			    }
			); 
		}
		//		
		//��ʾҳ��״̬div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/3;  //�������	    	
			vtop= document.body.clientHeight/2-20; //�����߶� 
	    	//
	    	$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
		    var pagestatusdiv = $("#pagestatusdiv");            //��ȡ��ʾ��Ϣdiv		   		    
		    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
		}		
		//����ҳ��״̬div
		function HiddenPageStatus() {
		    $("#pagestatusdiv").fadeOut();                  //������Ϣ
		}
		//��ʾ��ʾ��Ϣdiv
		function DisplayResult(info,smode) {
	    	vleft = document.body.clientWidth/3;  //�������
			vtop= document.body.clientHeight/2; //�����߶� 
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //��ȡ��ʾ��Ϣdiv
		    resultstatusdiv.html(info);                       //����div���ı�		    
		    $(resultstatusdiv).fadeIn();                      //������Ϣ
		    if(smode=="0"){
		    	setTimeout("HiddenResult()",2000);  			//2���������Ϣ
		    }else{
		    	//һֱ��ʾ
		    }		    
		}		
		//������ʾ��Ϣdiv
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //������Ϣ
		}
		//
		//
		//��ʼ��ҳ��
	    function IniPage(){	
	    	//   
			empid = "<%=empno%>"; 	//��ǰ��¼�û����
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			vleft = document.body.clientWidth/3;  //�������
			vtop= document.body.clientHeight/2; //�����߶� 
			//
			pid = "<%=reqpid%>";
			pname = "<%=reqpname%>";
			mode = "<%=reqmode%>";
			//
			//
			selbegpage = 0;		//��ҳ��ʼ
		 	selendpage = sqlpagenum;//��ҳ����		 	
		 	//
			//
			//��ȡ��ǰҵ����������������
			GetPolicyAllCheckAccYearItems();
			//��ȡ��ǰҵ����������·�����
			GetPolicyAllCheckAccMonthItems();
			//ȡ�������½�����������ѡ��
			GetSelectYear("divnewaccyear","newaccyear");
			//ȡ�������½�������������ѡ��
			GetSelectMonth("divnewaccmonth","newaccmonth");
			//��ȡ�Ƿ���ڵ�ǰҵ������������ڴ���ı�ʶ
		    GetPolicyAllCheckAccNoOverFlag();
			//
			if(mode=="edit"){
				$("#newaccmanagetb").css({"display":"none"});
				$("#accmanagetb").css({"display":"block"});
			}else if(mode=="add"){
				$("#newaccmanagetb").css({"display":"block"});
				$("#accmanagetb").css({"display":"none"});
			}
			//ȡ�ò�ѯ������������ѡ��
			GetSelectYear("divqueryyear","queryyear");
			//ȡ�ò�ѯ���㿪ʼ��������ѡ��
			GetSelectMonth("divquerybegmonth","querybegmonth");	
			//ȡ�ò�ѯ���������������ѡ��
			GetSelectMonth("divqueryendmonth","queryendmonth");	
	    }		
  </script>		
  </head>
  
  <body onload = "IniPage()">  
  	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
  	
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  	<div id='resultstatusdiv'></div>
  	<fieldset  class='list'  id = "accmanagetb">
		<legend  class='legend'>[<%=reqpname%>]ҵ���������</legend>
		<table align="center" width="60%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center" style="background-color:#ECECEC;">
	    		<td width = "120px" style="color: #000000;text-align:center;font-size:12px;">��ǰ���:</td>
	    		<td width ="20%" class = "pageTtems" style="font-size:12px;font-weight: bold;"><div id = "divaccyear"></div></td>
	    		<td width = "120px" style="color: #000000;text-align:center;font-size:12px;">��ǰ�·�:</td>
	    		<td width = "15%" class = "pageTtems" style="font-size:12px;font-weight: bold;"><div id = "divaccmonth"></div></td>	  		
	    		<td style="color: #000000;text-align:right;font-size:12px;">
	    			<div align="right">
	    				<button id = "btnacc" class = 'btn' onclick="SetPolicyAllAccFlag('1')">ҵ���������</button>
	    				<button id = "btndelacc" class = 'btn' onclick="DeletePolicyAllCheckAccItems()">ɾ��</button>	    			
	    			</div>
	    		</td>
	    	</tr>
	   	</table>	  			
    </fieldset>
   	<fieldset  class='list'  id = "newaccmanagetb">
		<legend  class='legend'>[<%=reqpname%>]����ҵ���½�������</legend>
		<table align="center" width="60%" border="0" cellspacing="0" cellpadding="0">	    	
	    	<tr align="center">
	    		<td width = "120px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">�½������:</td>
	    		<td	width = "20%"><div id = "divnewaccyear"></div></td>    		
	    		<td width = "120px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">�½����·�:</td>
	    		<td width = "15%"><div id = "divnewaccmonth"></div></td>    		
	    		<td style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">
	    			<div align="right">
	    				<button id = "btnnewacc" class = 'btn' onclick="SetPolicyAllCheckAccItems()">����ҵ���½�������</button>	    			
		    						    			    			
	    			</div>
	    		</td>
	    	</tr>
	   	</table>	  			
    </fieldset>
   	<fieldset  class='list' id = "accmanagetb">
		<legend  class='legend'>[<%=reqpname%>]ҵ��������ڲ�ѯ����</legend>
		<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">	    	
	    	<tr align="center" valign='middle' height='23' style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">
	    		<td>�������� ���:</td>
	    		<td width = "60"><div id = "divqueryyear"></div></td>
	    		<td>�·�:</td>
	    		<td width = "40"><div id = "divquerybegmonth"></div></td>
	    		<td>��:</td>
	    		<td width = "40"><div id = "divqueryendmonth"></div></td>
	    		<td width = "100"></td>
	    		<td align="right">��������:</td>
	    		<td align="left"><input id='begdt' type='text' size='13' onFocus='setday(this)'/></td>
	    		<td align="right">��:</td>
	    		<td align="left"><input id='enddt' type='text' size='13' onFocus='setday(this)'/></td>
	    		<td><button class = 'btn' onclick="GetPolicyAccXmls()">��ѯ</button></td>
	    	</tr>
	   	</table>
	   	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div class="requestcon" id="requestcon"> </div></td>	
	    	</tr>	    		    	
    	</table>		  			
    </fieldset>
  		
  </body>
</html>
<script type="text/javascript">
	//
	var xmldata;	//XML����ʵ��
	//
	var colnum = 0,rownum =0;//����/����		
	//
	var sqlcount = "0";//�ܼ�¼��
	var sqlolepagecount = "0";//����ҳ��
	var sqlolepageselect = "";//��ҳ��ѡ��������
	var sqlpagecount = "0";//��ҳ��
	var sqlpagenum = "18";//ÿҳ��¼��
	//
	var sqlselpage = 1;//ѡ��ҳ
	//
	var selbegpage = 0;		//��ҳ��ʼ
	var selendpage = sqlpagenum;//��ҳ����
	//
	//
	var sqlbegdt = "";//��ʼ����
	var sqlenddt = "";//��������
	//
		//
	function ShowData(data){
		var xmlDoc;
		//ȡ����ͷ
		var html= "";		
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//�ܼ�¼��
			var countarr=root.selectNodes("/data/count/num");
			sqlcount =countarr.item(0).firstChild.data;
			//��ҳ��
			var j1 = sqlcount/sqlpagenum;
			var j2=Math.round(j1);
			if(j1>j2){
				sqlpagecount = j2+1;
			}else if(j1<=j2){
				sqlpagecount = j2;
			}
				
			//������
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
				var temp ="<td height='25'>"+ss[1]+"</td>";
				html=html+temp;
				
			}		
			html=html+"</tr>";
			//
			//ȡ����¼ֵ
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
				var temp="<tr ";						
					//��˫��
					var mathrow = i%2;				
					if(mathrow == 1){//������ɫ
						temp +=" style = 'background: #F2F5F7;'>";	
					}else{
						temp +=">";	
					}						
					for (var j=1;j<row.length;j++){
						var temp1="<td height='23' class='colValue'>"+row.item(j).firstChild.data+"</td>";
						temp=temp+temp1;						
					}								
				temp=temp+"</tr>";
				
				//
				html=html+temp
				//				
			}
			//			
			html=html+"</table>";
			//
			//�Ƿ��м�¼
			if(rownum==0){
				html += "<table width='100%' cellpadding='0' cellspacing='0'>"
					html += "<tr>";				
						html += "<td height='25' class='colValue'>�޲�ѯ���</td>";
					html += "</tr>";
				html=html+"</table>";
			}
			//
			//
			//	
			html += "<table width='100%' cellpadding='0' cellspacing='0'>"
				html += "<tr class='colField'>";				
				html += "<td height='25'>";
					html += "��["+sqlcount+"]����¼  ��["+sqlpagecount+"]ҳ  ";	
					if(sqlselpage==1){
						html += "<span>��ҳ</span> ";
						html += "<span>��һҳ</span>�0�2";
					}else{
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('1')\">��ҳ</span> ";
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('2')\">��һҳ</span>�0�2";
					}
					if(sqlselpage==sqlpagecount){	
						html += "<span>��һҳ</span> ";
						html += "<span>βҳ</span>�0�2";
					}else{
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('3')\">��һҳ</span> ";
						html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('4')\">βҳ</span> ";
					}					
					html += "ת��";				
					  	/*
					  	html += "<select id = 'selectpage' onchange =\"ExePage()\">";
						for(var ipage =1;ipage<=sqlpagecount;ipage++){
					  		html += "<option value=\""+ipage+"\">��"+ipage+"ҳ</option>";
					  	}					
						html += "</select>";
						*/
						//ѡ��ҳ����ѡ���
						html += "<span id = \"spanpages\"></span> ";
					html += "  ÿҳ<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>����¼</td>";
				html += "</tr>";
			html=html+"</table>";			
			//
			//			
			requestcon.innerHTML=html;
			//
			//
			//��ҳѡ��������			
			spanpages.innerHTML=GetPageGo();
			//	
			//ѡ��ҳ
			$("#selectpage").val(sqlselpage);
			//ÿҳ��ʾ����
			$("#divpagerow").val(sqlpagenum);
			//
			//
			//
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
			//		
		}
		else{			
			requestcon.innerHTML="�޲�ѯ���";
	
		}
	}
	//ѡ��ҳ������
	function GetPageSelect(mode){
		var html;
		if(mode=="0"){//�͵ķ�ҳѡ��������
			sqlolepagecount = sqlpagecount;
			//
			return sqlolepageselect;
		}else{
			sqlolepagecount = sqlpagecount;			
			//
			html = "<select id = 'selectpage' onchange =\"ExePage()\">";
			for(var ipage =1;ipage<=sqlpagecount;ipage++){
		  		html += "<option value=\""+ipage+"\">��"+ipage+"ҳ</option>";
		  	}					
			html += "</select>";
			//
			sqlolepageselect = html;
			//			
		}
		return sqlolepageselect;
	}
	//ѡ��ҳ�ı���
	function GetPageGo(){
		var html;
		html = "��<input type='text' id = 'selectpage' size = '3'>ҳ</input>";
		html += "<input type='button' value='Go' onclick=\"ExePage()\"></input>";
		return html;
	}
	//����ѡ��ҳ��ѯ
	function ExePage(){
		var stpage = $("#selectpage").val();
		sqlselpage = parseInt(stpage);
		//�����ҳ����
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;	
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//ȡ��ҵ����㷵��XML��ʽ		  
		GetPolicyAccXmls();		
	}
	//ѡ��ҳ��ѯ
	function ExePageFlag(pageflag){	
		//
		//
		var flag = "1";//��ҳ��ʶ
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//��ҳ
			if(sqlselpage==1){				
				alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//��ҳ
			if(sqlselpage==1){
				alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//��ҳ
			if(sqlselpage==sqlpagecount){
				alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//ҳβ
			if(sqlselpage==sqlpagecount){
				alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlpagecount;			
		}
		
		//�����ҳ����
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;		
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//ȡ��ҵ����㷵��XML��ʽ		  
		GetPolicyAccXmls();		
	}
	//����ҳ��ʾ����
	function ChangPageRow(src){
		//
		sqlpagenum = src.value;
		//
		selbegpage = 0;		//��ҳ��ʼ
	 	selendpage = sqlpagenum;//��ҳ����
	 	sqlolepagecount = 0;//�ɷ�ҳѡ��������
	 	sqlselpage = 1;//��ʼ��һҳ
		//
		//ȡ��ҵ����㷵��XML��ʽ		  
		GetPolicyAccXmls();
	}
</script>
