<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%@page import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//
	//
	//����qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "";    //��ҵ����
	}	
	//����qcheckid
	String reqcheckid= request.getParameter("qcheckid");		
	if (reqcheckid == null) {
		//Ĭ��Ϊ��
		reqcheckid = "";    //������ID
	}
	//����qmode
	String reqmode= request.getParameter("qmode");		
	if (reqmode == null) {
		//Ĭ��Ϊ��
		reqmode = "check";    //��(check�������ID,child����ʩ���������ID)
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�������</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
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
		.pointer {
			cursor: pointer;
		}
		.tab {
			width: 100%;		
			margin: 0 auto;
			overflow: hidden;				
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
		.rowField {
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
			background: #F5A89A;
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
		.pointer {
			cursor: pointer;
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
		-->
	</style>
	<script type="text/javascript">
		//
		//
		var empid = "";        	//��ǰ��¼�û���� 
		var deptid = "";       	//��ǰ��¼����
		//
		var pid = "";		   	//��ǰ����ҵ��ID 
		var checkid = "";		//��ǰ�����ͥID
		var mode = "check";		//check�������ID,child����ʩ���������ID
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		//��ȡ���������������
		function GetPolicyMoreCheckIdeaHtml(pardivid,checkid) {
			//��ͥID����Ϊ����
			var pardiv = document.getElementById(pardivid);
			//��������    
			DisplayPageStatus(); 
		 	//
			//			
		    $.post("/db/page/policy/manage/policycheckidea.do",            //������ҳ���ַ
		        {
		            action: "getPolicyMoreCheckIdeaHtml",             //action����		           
		            empid: empid,
		            pid: pid,              //����
		            checkid: checkid
		            
		        },
		        function(result) {                    //�ص�����    
		        		//�����������
      					HiddenPageStatus();  
      					//
      					pardiv.innerHTML = result;			            
		         }   
		    );
		}
		//�����������	
		function RemoveIdea(checkid,ideaid){
			//
			//ȷ��
		    if (!confirm("�Ƿ�ȷ���������������")) {
		        return;
		    }
			//
			$.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
		        {
		            action: "removeIdea", //action����
		            checkid: checkid, //����
		            ideaid: ideaid, //����
		            empid: empid                              
		        },
		        function(result) {   //�ص�����
		          	DisplayResult(result,"0");	
		          	//ȡ�ø����������
					GetPolicyMoreCheckIdeaHtml("checkideascon",checkid);		       	          	                                         
		        }
		    );
	    }
	    //��ȡ����ʩ�����������������
		function GetPolicyChildCheckIdeaHtml(pardivid,checkid) {
			//��ͥID����Ϊ����
			var pardiv = document.getElementById(pardivid);
			//��������    
			DisplayPageStatus(); 
		 	//
			//			
		    $.post("/db/page/policy/manage/policycheckidea.do",            //������ҳ���ַ
		        {
		            action: "getPolicyChildCheckIdeasHtml",             //action����	
		            checkchildid: checkid
		            
		        },
		        function(result) {                    //�ص�����    
		        		//�����������
      					HiddenPageStatus();  
      					//
      					pardiv.innerHTML = result;			            
		         }   
		    );
		}
		//��ʾҳ��״̬div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/2-50;  //�������
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
		function DisplayResult(info,outflag) {
	    	vleft = document.body.clientWidth/2-100;  //�������
			vtop= document.body.clientHeight/2; //�����߶�  	
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //��ȡ��ʾ��Ϣdiv
		    resultstatusdiv.html(info);                       //����div���ı�		    
		    $(resultstatusdiv).fadeIn();                      //������Ϣ
		    if(outflag!="1"){
		    	setTimeout("HiddenResult()",2000);          //2���������Ϣ
		    }
		}		
		//������ʾ��Ϣdiv
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //������Ϣ
		}
		//��ʼ��ҳ��
	    function IniPage(){	
	    	//	    	   
			empid = "<%=empno%>"; 	//��ǰ��¼�û����
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			pid = "<%=reqpid%>";	//��ǰ����ҵ��ID 
			checkid = "<%=reqcheckid%>";	//��ǰ�����ͥID 
			mode = "<%=reqmode%>";	//check�������ID,child����ʩ���������ID
			//
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			if(mode=="check"){
				//ȡ�ø����������
				GetPolicyMoreCheckIdeaHtml("checkideascon",checkid);
			}else if(mode=="child"){
				//ȡ�ø����������
				GetPolicyChildCheckIdeaHtml("checkideascon",checkid);
			}
			
		}
	</script>

  </head>
  
  <body onload = "IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div>
    	<table id = "checkideahtmltb" class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div id="checkideascon"> </div></td>	
	    	</tr>		    				    		   			  	    	
	   	</table>
  </body>
</html>
