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
	//����qname
	String reqpname= request.getParameter("qpname");
	reqpname = new String(reqpname.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqpname == null) {
		//Ĭ��Ϊ��
		reqpname = "";    //������
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>����ҵ�������Ϣ����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
			font-family: "����";
			font-size: 12px;
		}		
		.sqltable{
			color: #6BA6FF;
			font-size:12px;
		}
		#infotree{
			border-top:1px solid #43ACB2;
			border-left:1px solid #43ACB2;
			border-bottom:1px solid #43ACB2;
			border-right:1px solid #43ACB2;
			width:100%;
			height:220px; 
			overflow:scroll;
			font-size:12px;
		}
		.titles{
			line-height: 2;			
			color: #000000;
			font-size:12px;
			text-align: center;
			width:100%;
			height:14;
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
			background: #F5A89A;
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
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼����
		
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		var pid = "";        //��ǰҵ��ID
		var pname = "";      //��ǰҵ������
		//
		//ȡ��ҵ�������Ϣ�б�
		function GetPolicyInfosHtml() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //������ҳ���ַ
		        {
		            action: "getPolicyInfosHtml",             //action����
		            pid: pid 
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	policyinfolists.innerHTML = result; 		        		    					      		
		        }
		    );
		}
		//����ҵ�������Ϣ�б�
		function InsertPolicyInfo(fid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //������ҳ���ַ
		        {
		            action: "insertPolicyInfo",             //action����
		            pid: pid,
		            fid: fid 
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();
					DisplayResult(result);              //��ʾ�������	
					//ȡ��ҵ�������Ϣ�б�
					GetPolicyInfosHtml();						        		    					      		
		        }
		    );
		}
		//����ҵ�������Ϣ��״̬
		function UpdatePolicyInfoStatus(sid,tname,fname,status) {
		    //
			var smse = "";
			if(status=="0"){
				smse = "ͣ��";
			}else{
				smse = "����";
			}
		    //ͣ��ǰȷ��
		    if (!confirm("�Ƿ�ȷ��"+smse+"��["+tname+"]�ֶ�["+fname+"]ҵ�������Ϣ���ã�")) {
		        return;
		    }
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //������ҳ���ַ
		        {
		            action: "updatePolicyInfoStatus",             //action����
		            sid: sid,
		            status: status
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();
					DisplayResult(result);              //��ʾ�������
					//ȡ��ҵ�������Ϣ�б�
					GetPolicyInfosHtml();							        		    					      		
		        }
		    );
		}
		//ɾ��ҵ�������Ϣ��״̬
		function DeletePolicyInfo(sid,tname,fname) {
		    //ͣ��ǰȷ��
		    if (!confirm("�Ƿ�ȷ��ɾ����["+tname+"]�ֶ�["+fname+"]ҵ�������Ϣ���ã�")) {
		        return;
		    }
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("/db/page/policy/info/policyinfoedit.do",            //������ҳ���ַ
		        {
		            action: "deletePolicyInfo",             //action����
		            sid: sid
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					DisplayResult(result);              //��ʾ�������
					//ȡ��ҵ�������Ϣ�б�
					GetPolicyInfosHtml();					        		    					      		
		        }
		    );
		}
		//ȡ�û�����ͥ��Ϣ��
		function GetInfoTree(){
			//����������ͥ��Ϣ��
			var ulr = "page/info/search/TableTreeServlet";
			$("#infotree").empty();                //��������б�
			loadrootTree('infotree',ulr,'infotree','root');	
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
			empid = "<%=empno%>";  		//��ǰ��¼�û���� 
			deptid = "<%=onno%>";    	//��ǰ��¼���� 
			//
			pid = "<%=reqpid%>";        //��ǰҵ��ID
			pname = "<%=reqpname%>";    //��ǰҵ������
			//
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			//������ͥ��Ϣ��
			GetInfoTree();	
			//ȡ��ҵ�������Ϣ�б�
			GetPolicyInfosHtml();	
		}		
	</script>
  </head>
  
  <body onLoad="IniPage()">
    	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top">
	    			<fieldset  class='list'>
	    			<legend  class='legend'>�����Ϣ����</legend>
	    				<span class = 'titles'>��ͥ��Ϣ��</span>    	  
				        <div id="infotree" align="left"></div>				        
	    			</fieldset>	
				</td>
	    		<td valign="top" width="50%">
	    			<fieldset  class='list'>
	    			<legend  class='legend'>[<%=reqpname%>]�����Ϣ�б�</legend>	    				 	  
				        <div id="policyinfolists"></div>				        
	    			</fieldset>	
	    		</td>
	    	</tr>	    	
    	</table> 
  </body>
</html>
<script type="text/javascript">
	//=================================ѡ���ѯ����BEG==============================
	//ѡ���ڵ�[TableTreeServlet�����ķ���]
	function seltableclick(tid,tname){
		//alert(tid+tname);			
	}		
	//ѡ���ֶνڵ�[TableTreeServlet�����ķ���]
	function selfieldclick(tid,tname,tfullname,tfieldmode){
		//alert(tid+tname+tfullname+tfieldmode);			
		//���ǰȷ��
	    if (!confirm("�Ƿ�ȷ�����["+tfullname+"]ҵ�������Ϣ���ã�")) {
	        return;
	    }
	    //����ҵ�������Ϣ�б�
		InsertPolicyInfo(tid);		
	}
	//���ӱ�[TableTreeServlet�����ķ���]
	function addchild(tid){
		//alert(tid);
		
	}
	//���ֶ�[TableTreeServlet�����ķ���]
	function addfield(tid){
		//alert(tid);
		
	}	
	//=================================ѡ���ѯ����END==============================
</script>
