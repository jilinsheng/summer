<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%	
	//	
	//����qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "-1";    //��ҵ����
	}
	//����qpname
	String reqpname= request.getParameter("qpname");
	reqpname = new String(reqpname.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqpname == null) {
		//Ĭ��Ϊ��
		reqpname = "";    //��ҵ������
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ҵ���������̶���</title>
    
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
		body {
			margin: 3;
			margin-top: 0;
			padding: 0;			
			font-family: "����";
			font-size: 12px;
		}
		input {			
			font-family: "����";
			font-size: 12px;
		}
		.pointer {
			cursor: pointer;
		}
		/** ������ʽ **/
		.itemstyle {
			font-size: 12px;
			color: #000000;
		}
		.tablexiu {
			border-collapse: collapse;
			border: 1px solid #999;
			border-width: 1px 0 0 1px;
			margin: 2px 0 2px 0;
			text-align: left;
			background-color: #FCDAD5
		}
		
		.tablexiu td {
			height: 20px;
			border: 1px solid #999;
			border-width: 0 1px 1px 0;
			margin: 2px 0 2px 0;
			font-size: 12px;
			text-align: left;
			background-color: #FCDAD5
		}
		
		.tablexiu th {
			height: 20px;		
			border: 1px solid #999;
			border-width: 0 1px 1px 0;
			margin: 2px 0 2px 0;
			text-align: center;
			font-size: 12px;
			color: #000000;
			background: url('/db/page/images/newscenter.gif');
		}

		#resultdiv{
			z-index:2;			
			color: #FF0099;
			font-size:14px;
			align: center;
			display:none;
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
		-->
	</style>
	<script type="text/javascript">
		<!--
		var policyid = "<%=reqpid%>";
		var policyname = "<%=reqpname%>";
		var selid = "";
		
		//��ȡҵ��������������
		function getPolicyFlowItem(Id) {
			var ttemp ="";
			//			
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyFlowItem",             //action����
		            Id: Id              //ҵ������
		            
		        },
		        function(result) {                    //�ص�����   
		        	//���ת����JSONArray	        	
	        		var json = eval(result); 
     				//       
		        	$(json).each(function(i) {      //�����������
						//
						selid = json[i].pcheckflowid;						
						//
						$("#palllimit").val(json[i].palllimit);					     
					    //
					    ttemp = json[i].pappstate1;
					    if(ttemp=='0'){
					      pappstate1.checked = false;
					    }else{
					      pappstate1.checked = true;
					    }
					    $("#plimit1").val(json[i].plimit1);					    				    
					    ttemp = json[i].pappstate2;
					    if(ttemp=='0'){
					      pappstate2.checked = false;
					    }else{
					      pappstate2.checked = true;
					    }
					    $("#plimit2").val(json[i].plimit2);				    
					    ttemp = json[i].pappstate3;
					    if(ttemp=='0'){
					      pappstate3.checked = false;
					    }else{
					      pappstate3.checked = true;
					    }
					    $("#plimit3").val(json[i].plimit3);				    
					    ttemp = json[i].pappstate4;
					    if(ttemp=='0'){
					      pappstate4.checked = false;
					    }else{
					      pappstate4.checked = true;
					    }
					    $("#plimit4").val(json[i].plimit4);					    
					    ttemp = json[i].pappstate5;
					    if(ttemp=='0'){
					      pappstate5.checked = false;
					    }else{
					      pappstate5.checked = true;
					    }
					    $("#plimit5").val(json[i].plimit5);
					    //�������[5������4���ֵ�3����2����1��ʡ]�ͻ���������ͬ
					    var accdept = json[i].paccdept;
					    if(accdept=="5"){
					    	paccdept1.checked = true;
					    }else if(accdept=="4"){
					    	paccdept2.checked = true;
					    }else if(accdept=="3"){
					    	paccdept3.checked = true;
					    }else if(accdept=="2"){
					    	paccdept4.checked = true;
					    }else if(accdept=="1"){
					    	paccdept5.checked = true;
					    }					                           
					    //���Ը�����������ʶ0������1����
					    ttemp = json[i].puseraccflag;
					    if(ttemp=="1"){
					    	useraccflag.checked = true;
					    }else{
					    	useraccflag.checked = false;
					    }
					    //��������������ʶ0������1����
					    ttemp = json[i].pcheckflag;
					    if(ttemp=="1"){
					    	checkflag.checked = true;
					    }else{
					    	checkflag.checked = false;
					    }
		            });		            
		         }   
		    );
		}
		//����ҵ����������
		function updatePolicyFlow(){
			//
			var EditType;
			var ptemp;
			var id,pid,paccdept,puseraccflag,pcheckflag,palllimit,plimit1,plimit2,plimit3,plimit4,plimit5;
			var pappstate1,pappstate2,pappstate3,pappstate4,pappstate5;
			//
			id = selid;
			pid = policyid;
			palllimit = $("#palllimit").val();
			plimit1 = $("#plimit1").val();
			plimit2 = $("#plimit2").val();
			plimit3 = $("#plimit3").val();
			plimit4 = $("#plimit4").val();
			plimit5 = $("#plimit5").val();
		    //
		    ptemp = document.getElementById("pappstate1");
		    if(ptemp.checked == false){
		      pappstate1 = "0";
		    }else{
		      pappstate1 = "1";
		    }
		    ptemp = document.getElementById("pappstate2");
		    if(ptemp.checked == false){
		      pappstate2 = "0";
		    }else{
		      pappstate2 = "1";
		    }
		    ptemp = document.getElementById("pappstate3");
		    if(ptemp.checked == false){
		      pappstate3 = "0";
		    }else{
		      pappstate3 = "1";
		    }
		    ptemp = document.getElementById("pappstate4");
		    if(ptemp.checked == false){
		      pappstate4 = "0";
		    }else{
		      pappstate4 = "1";
		    }
		    ptemp = document.getElementById("pappstate5");
		    if(ptemp.checked == false){
		      pappstate5 = "0";
		    }else{
		      pappstate5 = "1";
		    }
		    //
		    //�������[5������4���ֵ�3����2����1��ʡ]�ͻ���������ͬ
		    ptemp = document.getElementById("paccdept1");
		    if(ptemp.checked == true){
		    	paccdept = "5";
		    }
		    ptemp = document.getElementById("paccdept2");
		    if(ptemp.checked == true){
		    	paccdept = "4";
		    }
		    ptemp = document.getElementById("paccdept3");
		    if(ptemp.checked == true){
		    	paccdept = "3";
		    }
		    ptemp = document.getElementById("paccdept4");
		    if(ptemp.checked == true){
		    	paccdept = "2";
		    }
		    ptemp = document.getElementById("paccdept5");
		    if(ptemp.checked == true){
		    	paccdept = "1";
		    }
		    //������д�������
		    ptemp = document.getElementById("useraccflag");
		    if(ptemp.checked == true){
		    	puseraccflag = "1";
		    }else{
		      	puseraccflag = "0";
		    }
		    //������������
		    ptemp = document.getElementById("checkflag");
		    if(ptemp.checked == true){
		    	pcheckflag = "1";
		    }else{
		      	pcheckflag = "0";
		    }
		    //		    
		    if(id==null || id == ""){
		    	EditType = "addpolicyflow";
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������ҵ��["+policyname+"]�������̣�")) {
			        return;
			    }
		    }else{
		    	EditType = "editpolicyflow";
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+policyname+"]�������̣�")) {
			        return;
			    }
		    }
		    //
		    //
			$.post("page/policy/manage/PolicyManageServlet", //������Ŀ��ҳ��
		        {
		            action: "updatePolicyFlow",                  //action����
		            EditType: EditType,
		            id: id,		            
		            pid: pid,  
		            paccdept: paccdept,
		            puseraccflag: puseraccflag,	
		            pcheckflag: pcheckflag,	           
		            palllimit: palllimit,
		            plimit1: plimit1,
		            plimit2: plimit2,
		            plimit3: plimit3,
		            plimit4: plimit4,
		            plimit5: plimit5,
		            pappstate1: pappstate1,
		            pappstate2: pappstate2,
		            pappstate3: pappstate3,
		            pappstate4: pappstate4,
		            pappstate5: pappstate5
		        },
		        function(result) {                      //�ص�����		        	
		            DisplayResult(result);              //��ʾ�������
		            getPolicyFlowItem(pid); 
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
		//��ʼ��ҳ��
	    function IniPage(){
	    	//
			$("#btnok").get()[0].focus();
	    	//ȡ��ҵ��������������
	    	getPolicyFlowItem(policyid);
	    }
		-->
	</script>
	
  </head>
  
  <body onload="IniPage()">
  	<table class="tablexiu" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr height = '23' align="center">
   			<th style="color: #000000;">��ǰҵ��[<%=reqpname%>]�������̶���</th>
    	</tr>  
   	</table>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr height = '21' align="center" class='colField' style="background-color:#F5A89A;">
    		<td>����</td>
    		<td>����(��Ҫ)</td>
    		<td>����(����)</td>
    		<td>��ʱ(Сʱ)</td>			    		
    	</tr>	    	
    	<tr align="center">
    		<td class='colValue'>����(��)</td>
    		<td class='colValue'><input type="checkbox" id="pappstate1"></input></td>
    		<td class='colValue'><input type="radio" name="rd" id="paccdept1"></input></td>
    		<td width = "80px" class='colValue' style="border-right-width:1px;"><input style="width:100%;text-align:right;" type="text" id="plimit1"></input></td>
    	</tr>
    	<tr align="center">
    		<td class='colValue'>�ֵ�(����)</td>
    		<td class='colValue'><input type="checkbox" id="pappstate2"></input></td>
    		<td class='colValue'><input type="radio" name="rd" id="paccdept2"></input></td>
    		<td class='colValue' style="border-right-width:1px;"><input style="width:100%;text-align:right;" type="text" id="plimit2"></input></td>
    	</tr> 
    	<tr align="center">
    		<td class='colValue'>��(��)</td>
    		<td class='colValue'><input type="checkbox" id="pappstate3"></input></td>
    		<td class='colValue'><input type="radio" name="rd" id="paccdept3"></input></td>
    		<td class='colValue' style="border-right-width:1px;"><input style="width:100%;text-align:right;" type="text" id="plimit3"></input></td>
    	</tr>  
    	<tr align="center">
    		<td class='colValue'>��(��)</td>
    		<td class='colValue'><input type="checkbox" id="pappstate4"></input></td>
    		<td class='colValue'><input type="radio" name="rd" id="paccdept4"></input></td>
    		<td class='colValue' style="border-right-width:1px;"><input style="width:100%;text-align:right;" type="text" id="plimit4"></input></td>
    	</tr> 
    	<tr align="center">
    		<td class='colValue'>ʡ��</td>
    		<td class='colValue'><input type="checkbox" id="pappstate5"></input></td>
    		<td class='colValue'><input type="radio" name="rd" id="paccdept5"></input></td>
    		<td class='colValue' style="border-right-width:1px;"><input style="width:100%;text-align:right;" type="text" id="plimit5"></input></td>
    	</tr> 
    	<tr align="center">
    		<td colspan = '2' class='colValue'>
    			<input type="checkbox" id="useraccflag"></input>�޸ľ�����(����)
    			<input type="checkbox" id="checkflag"></input>��������(����)
    		</td>
    		<td class='colValue'>(��)��ʱ(Сʱ)</td>
	        <td class='colValue' style="border-right-width:1px;"><input style="width:100%;text-align:right;" type="text" id="palllimit"></input></td>	        
	    </tr>  
	    <tr align="center" class='colValue'>			    		
	    	<td colspan = '4' class='colValue' style="border-bottom-width:1px;border-right-width:1px;">
	    		<input type='button' id = 'btnok' value='����ҵ����������' onclick='updatePolicyFlow()'></input>
	    	</td>				        	        
	    </tr>  	
   	</table>    
	<div id='resultdiv' align="center"></div> 
  </body>
</html>
