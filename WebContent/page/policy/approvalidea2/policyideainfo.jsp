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
<%	
	//	
	//����qid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "";    //�ձ��
	}
	//����
	String reqfmid= request.getParameter("qfmid");		
	if (reqfmid == null) {
		//Ĭ��Ϊ��
		reqfmid = "";   
	}
	//����
	String reqmemid= request.getParameter("qmemid");		
	if (reqmemid == null) {
		//Ĭ��Ϊ��
		reqmemid = "";  
	}	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>����ҵ�������ϸ��Ϣ</title>
    
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
		.rowFieldInfo {
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
				background: #ECECEC;
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
		var pid = "";				//��ǰ����ҵ��ID 
		var fmid = "";				//��ǰ�����ͥID 
		var memid = "";				//��ǰ�����ԱID
		
		
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
		function DisplayResult(info) {
	    	vleft = document.body.clientWidth/2-100;  //�������
			vtop= document.body.clientHeight/2; //�����߶�  	
	    	//
	    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		    var resultstatusdiv = $("#resultstatusdiv");            //��ȡ��ʾ��Ϣdiv
		    resultstatusdiv.html(info);                       //����div���ı�		    
		    $(resultstatusdiv).fadeIn();                      //������Ϣ
		    setTimeout("HiddenResult()",2000);  			//2���������Ϣ
		}		
		//������ʾ��Ϣdiv
		function HiddenResult() {
		    $("#resultstatusdiv").fadeOut();                  //������Ϣ
		}
		//
		//��ʼ��ҳ��
	    function IniPage(){	
	    	//	    	   
			empid = "<%=empno%>"; 	//��ǰ��¼�û����
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			pid = "<%=reqpid%>";				//��ǰ����ҵ��ID 
			fmid = "<%=reqfmid%>";				//��ǰ�����ͥID 
			memid = "<%=reqmemid%>";			//��ǰ�����ԱID 
			//			
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			//			
			//��ȡ��ͥ����ҵ�������ϸ��Ϣ
			GetCheckPolicyInfosHtml(fmid,memid);
			
		}
	</script>
  </head>
  
  <body onload = "IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div>
    	<div id='policyinfodiv'></div>
  </body>
</html>
<script type="text/javascript">
	//��ȡ��ͥ����ҵ�������ϸ��Ϣ
	function GetCheckPolicyInfosHtml(fmid,memid){
		//		
		var pardiv = document.getElementById("policyinfodiv");
		//��ʾҳ��״̬div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //������ҳ���ַ
	        {
	            action: "getCheckPolicyInfosHtml",             //action����
	            pid: pid, 
	            fmid: fmid,
	            memid: memid
	        },
	        function(result) {                    	//�ص�����
	        	//����ҳ��״̬div
				HiddenPageStatus();	
				//
				pardiv.innerHTML = result;				 		
	        }
	    );
	}
</script>