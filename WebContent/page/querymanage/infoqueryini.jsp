<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session
			.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
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

		<title>��ѯѡ������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="/db/page/js/jquery.js"></script>
		<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
		<script type="text/javascript" src="/db/page/js/seqsort.js"></script>

	<style type="text/css">
		#movableNode {
			position: absolute;
		}
		/* ָʾ��ͷ����ʽ */
		#arrDestInditcator {
			position: absolute;
			display: none;
			width: 100px;
		}
		/* �б�������ʽ */
		#arrangableNodes,#movableNode ul {
			padding-left: 0px;
			margin-left: 0px;
			margin-top: 0px;
			padding-top: 0px;
		}
		/* �б�����ʽ */
		#arrangableNodes li,#movableNode li {
			list-style-type: none;
			cursor: default;
		}
		body {
			margin: 3;
			margin-top: 0;
			padding: 0;
			background: #FCDAD5;
			font-family: "����";
			font-size: 12px;
		}		
		.tableseq th {
			height: 20px;
			border: 1px solid #999;
			border-width: 0 1px 1px 0;
			margin: 2px 0 2px 0;
			text-align: center;
			font-size: 12px;
			text-align: center;
			font-weight: bold;
			color: #FFFFFF;
			background-color: #6BA6FF;
		}
		.pointer {
			cursor: pointer;
		}
		.caption {
			font-size: 12px;
			color: #660099;
		}
		.itemstyle {
			font-size: 12px;
			color: #6BA6FF;
		}
		.sqltable {
			color: #6BA6FF;
			font-size: 12px;
		}
		.bordercss {
			border-top: 1px solid #43ACB2;
			border-left: 1px solid #43ACB2;
			border-bottom: 1px solid #43ACB2;
			border-right: 1px solid #43ACB2;			
		}
		.licss {
			border-top: 1px solid buttonhighlight; 
			border-left: 1px solid buttonhighlight;  
			border-bottom: 1px solid buttonshadow;  
			border-right: 1px solid buttonshadow; 
			padding: 0;  
			cursor: hand;			  			
		}		
		#divqueryseq {
			border-top: 1px solid #43ACB2;
			border-left: 1px solid #43ACB2;
			border-bottom: 1px solid #43ACB2;
			border-right: 1px solid #43ACB2;
			font-size: 12px;
			height: 400px;				
		}		
		#infotree {
			border-top: 1px solid #43ACB2;
			border-left: 1px solid #43ACB2;
			border-bottom: 1px solid #43ACB2;
			border-right: 1px solid #43ACB2;
			width: 100%;
			height: 200px;
			overflow: scroll;
			font-size: 12px;
		}
		#sqlsexp {
			width: 100%;
			font-size: 12px;
		}
		#resultdiv{
			position:absolute;
			left:60%; 
			top:30%;
			z-index:2;
			background-color: #FFCCCC;
			color: #FF0099;
			font-size:12px;
			display:none;
		}
		#pagestatusdiv{
			position:absolute;
			left:60%; 
			top:30%;			
			z-index:2;
			background-color: #FFCCCC;
			font-weight: bold;
			color: #FF0099;
			font-size:14px;
			display:none;
		}
	</style>
	<script type="text/javascript"> 
  		var empid = ""; 	//��¼�û����	
  		var qmode = "";        //��ǰ��ѯ����ģʽ  		
  		  
  		var selfieldtype = "";        //ѡ���ֶ�������		
  		
		//ȡ�ò�ѯ�����б�
		function GetQuerySeq(pardivid,empid){
		  	//
		  	var pardiv = document.getElementById(pardivid);
		  	
		    $.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "getQueryExpSeq", //action����
		            empid: empid //����	                                          
		        },
		        function(result) {   //�ص�����		          
		          pardiv.innerHTML = result;
		          //��ʼ����ק����
		    	  initArrangableNodes();		          		          		                                                  
		        }
		    );       
		}
		
		//�����ѯ��ʾ˳��
		function saveQuerySeq(){
		    // 
		    if (!confirm("ȷ�����棿")) {		    	
		        return;
		    }     
		    var nodes = document.getElementById("arrangableNodes").getElementsByTagName('li');
		    var seqnew = "";
		    for(var i=1; i<=nodes.length; i++){
		        if (i > 1) {
		            seqnew = seqnew + ",";
		        }
		        seqnew = seqnew + nodes[i-1].id + "_" + i;
		    }
		  
		    $.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "saveQuerySeq", //action����
		            seqnew: seqnew, //����   
		            empid: empid //����                           
		        },
		        function(result) {   //�ص�����
		        	$("#resultdiv").css({"left":"5%","top":"10%"});
			        DisplayResult(result);              //��ʾ�������
		            //ȡ�ò�ѯ�����б�
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );        
		}
		
		//ɾ�����в�ѯ����
		function delAllQuerySeq(parid){
			if (!confirm("ȷ��ɾ��ȫ���û����ã�")) {		    	
		        return;
		    }
		    $.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "delAllQuerySeq", //action����
		            parid: parid, //����   
		            empid: empid //����                              
		        },
		        function(result) {   //�ص�����
		        	$("#resultdiv").css({"left":"5%","top":"10%"});
			        DisplayResult(result);              //��ʾ�������
		            //ȡ�ò�ѯ�����б�
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );  			
		}
		//ɾ����ѯ����
		function delQuerySeq(id){
			if (!confirm("ȷ��ɾ���û����ã�")) {		    	
		        return;
		    }
			$.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "delQuerySeq", //action����
		            id: id, //����   
		            empid: empid //����                                  
		        },
		        function(result) {   //�ص�����
		        	$("#resultdiv").css({"left":"5%","top":"10%"});
			        DisplayResult(result);              //��ʾ�������
		            //ȡ�ò�ѯ�����б�
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );  
		}
		//��Ӳ�ѯ����
		function addQuerySeq(){			
			var ttype = "";
			var tinfo = "";
			var texp = "";
			
		    ttype = selfieldtype;
		    tinfo = $("#inputinfo").val();
		    texp = $("#inputexp").val();
		    if(tinfo.length<0 || texp.length<0 || tinfo =="" || texp ==""){
		    	return;	
		    } 
		    if (!confirm("ȷ����ӣ�")) {		    	
		        return;
		    }
			$.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "addQuerySeq", //action����
		            empid: empid, //����            
		            tinfo: tinfo,
		            texp: texp,
		            ttype: ttype                             
		        },
		        function(result) {   //�ص�����
		        	$("#resultdiv").css({"left":"55%","top":"10%"});
			        DisplayResult(result);              //��ʾ�������
		            //ȡ�ò�ѯ�����б�
					GetQuerySeq("divqueryseq",empid);                                        
		        }
		    );
		     
		}
		     
		//��ʾ��ʾ��Ϣdiv
		function DisplayResult(info) {
		    var resultdiv = $("#resultdiv");            //��ȡ��ʾ��Ϣdiv
		    resultdiv.html(info);                       //����div���ı�		    
		    $(resultdiv).fadeIn();                      //������Ϣ
		    setTimeout("HiddenResult()",2000);          //2���������Ϣ
		}		
		//������ʾ��Ϣdiv
		function HiddenResult() {
		    $("#resultdiv").fadeOut();                  //������Ϣ
		}
		//��ʾҳ��״̬div
		function DisplayPageStatus(info) {
		    var pagestatusdiv = $("#pagestatusdiv");            //��ȡ��ʾ��Ϣdiv
		    pagestatusdiv.html(info);                       //����div���ı�		    
		    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
		}		
		//����ҳ��״̬div
		function HiddenPageStatus() {
		    $("#pagestatusdiv").fadeOut();                  //������Ϣ
		}
		
		//ȡ�û�����ͥ��Ϣ��
	    function GetInfoTree(){
	      //����������ͥ��Ϣ��
	      var ulr = "page/info/search/TableTreeServlet";
	      loadrootTree('infotree',ulr,'infotree','root');  
	    }
	    //ȡ�������������
	    function GetExpSelect(tfieldtype){
	      //
	        $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
	            {
	                action: "getexpselect" , //action���� 
	                fieldtype: tfieldtype                                 
	            },
	            function(result) {   //�ص�����
	              divexp.innerHTML = result;                           
	            }
	        );        
	    }
		//��ʼ��ҳ��
	    function IniPage(){	
	    	$("#pagestatusdiv").css({"left":"55%","top":"10%"});
	    	DisplayPageStatus("��������,���Ժ�...");    	   	
	    	//��ǰ��¼�û����    
		    empid = <%=empno%>;
		    
		    /*	
		    //��ѯ����ģʽ
		    var myobj = window.dialogArguments;
	    	qmode = myobj[0];
	    	*/	    		    		 		    
	    	//ȡ�ò�ѯ�����б�
			GetQuerySeq("divqueryseq",empid);
			//������ͥ��Ϣ��
      		GetInfoTree();
      		//�����
      		GetExpSelect("-5");
      		//		
			
      		//�����������
      		HiddenPageStatus();
	    }
	</script>
	</head>

	<body onload="IniPage()">
		<div id='resultdiv'></div>
		<div id='pagestatusdiv'></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"class="tableseq">
			<tr>
				<td width="40%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"class="tableseq">
						<tr>
							<td valign="middle">
								<span class='itemstyle'>��ͥ��Ϣ��</span>
								<div id="infotree" align="left"></div>
							</td>
						</tr>
						<tr>
							<td valign="middle">
								<span class='itemstyle'>�����</span>
								<div id="divexp" align="left"></div>
							</td>
						</tr>
						<tr>
							<td width="100%" valign="top">
								<span class='itemstyle'>��Ϣ��:</span>
								<input id='inputinfo' type="text" style='width: 100%'readonly="readonly"></input>
							</td>
						</tr>
						<tr>
							<td width="100%" valign="top">
								<span class='itemstyle'>�����:</span>
								<input id='inputexp' type="text" style='width: 100%'readonly="readonly"></input>
							</td>
						</tr>
						<tr>
							<td valign="middle">
								<div align="center" class = 'bordercss'>
									<input type="button" onclick="addQuerySeq()" value="�����Ϣ��ѯ����"></input>
								</div>
							</td>
						</tr>
			
					</table>	
				</td>
				<td width="60%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"class="tableseq">
						<tr>
							<th colspan = "1">��ѯѡ�������б�</th>							
							<td width="100%" valign="top">
								<span class='caption'>��ѯѡ�������б�</span>
								<div id="divqueryseq"></div>
							</td>
						</tr>						
					</table>					
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
			//ѡ���ֶ�������
			selfieldtype = 	tfieldmode;		
			//����
           	if(tfieldmode=="-4"){
           		$("#inputinfo").val(tfullname);	
           		$("#inputexp").val("��ƥ��");							
           	}
           	//������
           	else if(tfieldmode=="-3"){           		
		    	$("#inputinfo").val(tfullname);		    	         		
           	}
           	//��ֵ��
           	else if(tfieldmode=="-2"){           						
           		$("#inputinfo").val(tfullname);
           	}
           	//����
           	else if(tfieldmode=="-1"){
           		$("#inputinfo").val(tfullname);
           	}
           	//�ַ���
           	else if(tfieldmode=="0"){           		
           		$("#inputinfo").val(tfullname);
           	}
           	//�ֵ�ֵ	
           	else{
           		$("#inputinfo").val(tfullname);
           		$("#inputexp").val("����");							
           	}
           	//�����
			GetExpSelect(tfieldmode);					
		}
		//���ӱ�[TableTreeServlet�����ķ���]
		function addchild(tid){
			//alert(tid);	
		}
		//���ֶ�[TableTreeServlet�����ķ���]
		function addfield(tid){
			//alert(tid);	
		}
		//ѡ���ѯ��׼
		function selectclickexp(){
			sexp = sqlsexp.options[sqlsexp.selectedIndex].text;
			$("#inputexp").val(sexp);
		} 
		//=================================ѡ���ѯ����END==============================
</script>