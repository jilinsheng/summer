<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%	
	//
	//
	//�Ӳ�ѯ�����qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "";    //��ҵ����
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
    
    <title>ҵ��ʾ����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	
	<style type="text/css">
 <!--
  	.myspace{
		border-top:1px solid ;
		border-bottom:1px solid ;
		border-left:1px solid ;
		border-right:1px solid ;
		
		font-size:12px
	}
	.mystyle {		
		font-size: 12px;
		font-weight: bold;	
		color: #666666;
		background: #FCDAD5;
	}
  	.colField {
		font-family: "����";
		font-size: 9pt;
		font-weight: 600;
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
		background-color: #F5A89A;
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
		cursor:pointer;
	}
	body {
		margin: 3;
		margin-top: 0;
		padding: 0;
		background: #FCDAD5;
		font-family: "����";
		font-size: 12px;
	}
	#pagestatusdiv{		
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
	var selpolicy = "";//��ǰҵ����
	//
	//ȡ�õ�ǰҵ������
	function GetPolicyName(pardivid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	var pid = selpolicy;
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getPolicyName", //action����
		        pid: pid                            
		    },
		    function(result) {   //�ص�����		    	
		    	pardiv.innerHTML = result;	          						      	                                         
		    }
		);        
	}
	
	
	//��ʾҳ��״̬div
	function DisplayPageStatus() {
	    var pagestatusdiv = $("#pagestatusdiv");            //��ȡ��ʾ��Ϣdiv	    	    
	    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
	}		
	//����ҳ��״̬div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //������Ϣ
	}			
	//��ʼ��ҳ��
    function IniPage(){
    	
	    selpolicy = "<%=reqpid%>";		 //��� 
	   		        	   
    }
  </script>    
  </head>
  
  <body onload = "IniPage()"> 
  		ҵ��ʾ����
    	<!-- <div align="center"><input id = "btnclose" type="button" value=" �� �� "onclick="window.close();"></input></div> -->
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table> 
  </body>
</html>
