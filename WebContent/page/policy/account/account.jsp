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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'account.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>		
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>	
	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>	
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
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
	var sqlcount = 0;//�ܼ�¼��
	var sqlpagecount = 0;//��ҳ��
	var sqlpagenum = 10;//ÿҳ��¼��
	//
	var sqlselpage = 1;//ѡ��ҳ
	//
	var selbegpage = 0;		//��ҳ��ʼ
	var selendpage = sqlpagenum;//��ҳ����
	
	//
	//��ȡҵ��ѡ��������
	function getPolicySelect(sname,mode){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "getPolicySelect",             //action����
	            sname: sname,
	            mode: mode
	        },
	        function(result) {                    	//�ص�����
	        	if(mode == "new"){
	        		selectnewpid.innerHTML = "ҵ��"+result;
	        	}else{
	        		selectpid.innerHTML = "ҵ��"+result;	 
		        	//��ȡҵ����ִ������
		  			getPolicyAccTable();       	
		        	//�����¼�		      	
					$("#"+sname).change(
						function(){
							//��ȡҵ����ִ������
		  					getPolicyAccTable();
						}
					);
	        	}	        	
	        }
	    );
	}
	//���ѡ
	function ChbMoney() {
	    //
	  	if(chbmy.checked == true){
	  		$("#inputmy").attr("disabled", "");
	  	}else{
	  		$("#inputmy").attr("disabled", "disabled");
	  	} 
	}
	//��ȡҵ���������
	function getPolicyAccTable(){
		//
		var selpid = $("#pidlist").val();
		var selacc = $("#acclist").val();
		//		
		var selyear = $("#syear").val();
		var selmonth = $("#smonth").val();
		var seldesc = $("#inputdesc").val();
		var selbegdt = $("#inputbegdt").val();
		var selenddt = $("#inputenddt").val();
		//��������
		var selqdeptid = $("#inputdeptid").val();	
		var selqdept = $("#inputdept").val();
		if(selqdept == ""){
			selqdeptid = "";
		}
		//���������·ݲ�ѯ(��ѯ�·ݿ�����)
		selmonth = "";
		//
		seldesc = encodeURI(seldesc);
		//
		spanstatus.innerHTML = "��ѯ����,���Ժ�...";
		//��������		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "getPolicyAccTable",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jacc: selacc,
	            jyear: selyear,
	            jmonth: selmonth,
	            jdesc: seldesc,
	            jbegdt: selbegdt,
	            jenddt: selenddt,
	            jbegpage: selbegpage,
	            jendpage: selendpage,
	            jqdeptid: selqdeptid
	        },
	        function(result) {                    	//�ص�����
	        	//�����������
     			HiddenPageStatus();
	        	//����XML	 
	        	ShowData(result);	        		
	        }
	    );
	}
	//���ҵ���������
	function addPolicyNowAcc(){
		//
		var selpid = $("#newpidlist").val();
		//
		var selyear = $("#snewyear").val();
		var selmonth = $("#snewmonth").val();
		var seldesc = $("#newdesc").val();
		var selbegdt = $("#newbegdt").val();
		var selenddt = $("#newenddt").val();
		//
		var selmoneyflag = "0";
		if(chbmy.checked == true){
	  		selmoneyflag = "1";
	  	}else{
	  		selmoneyflag = "0";
	  	}
		var selmoney = $("#inputmy").val();
		
		if(selbegdt == ""){
			alert("û����д����!");
			return;
		}
		if(selenddt == ""){
			alert("û����д����!");
			return;
		}
		if (!confirm("�Ƿ�ȷ������?")) {
	        return;
	    }
	    //����
	    if (null == seldesc || "" == seldesc){
	    	seldesc = $("#newpidlist").find("option:selected").text();
	    }
		//
		seldesc = encodeURI(seldesc);
		//
		//
		spanstatus.innerHTML = "��������,���Ժ�...";
		//��������		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "addPolicyNowAcc",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jyear: selyear,
	            jmonth: selmonth,
	            jdesc: seldesc,
	            jbegdt: selbegdt,
	            jenddt: selenddt,
	            jmoneyflag: selmoneyflag,
	            jmoney: selmoney
	        },
	        function(result) {                    	//�ص�����
	        	//�����������
     			HiddenPageStatus();
 				//
	        	alert(result);
	        	//��ȡҵ����ִ������
	  			getPolicyAccTable();		        		
	        }
	    );
	}
	//ɾ��ҵ���������
	function delPolicyNowAcc(accid){
		//
		var selpid = $("#pidlist").val();
		if (!confirm("�Ƿ�ȷ��ɾ��?")) {
	        return;
	    }
		//
		spanstatus.innerHTML = "ɾ������,���Ժ�...";
		//��������		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "delPolicyNowAcc",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jaccid: accid
	        },
	        function(result) {                    	//�ص�����
	        	//�����������
     			HiddenPageStatus();
 				//
	        	alert(result);	
	        	//��ȡҵ����ִ������
	  			getPolicyAccTable();	        		
	        }
	    );
	}
	//ҵ��������ο�ʼ����
	function accPolicyNowAcc(accid){
		//
		var selpid = $("#pidlist").val();
		if (!confirm("�Ƿ�ȷ������?")) {
	        return;
	    }
		//
		spanstatus.innerHTML = "��������,���Ժ�...";
		//��������		    
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "accPolicyNowAcc",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jpid: selpid,
	            jaccid: accid
	        },
	        function(result) {                    	//�ص�����
	        	//�����������
     			HiddenPageStatus();
 				//
	        	alert(result);	
	        	//��ȡҵ����ִ������
	  			getPolicyAccTable();	        		
	        }
	    );
	}
	//��ʾҳ��״̬div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2;  //�������
		vtop= document.body.clientHeight/2; //�����߶�
    	//
    	$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
	    var pagestatusdiv = $("#pagestatusdiv");            //��ȡ��ʾ��Ϣdiv		   		    
	    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
	}		
	//����ҳ��״̬div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //������Ϣ
	}
	//
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		//������������ѡ���
		CreateSelect();
		$("#inputmy").attr("disabled", "disabled");
		//��ȡҵ��ѡ��������
		getPolicySelect("newpidlist","new");
		//��ȡҵ��ѡ��������
		getPolicySelect("pidlist","query");			
		//��ҳ��
	  	Pagination();
	}
  </script>	
  <body  onload = "IniPage()"> 
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img><span id = 'spanstatus'>��ѯ�������Ե�...</span></div> 
  		<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = 'background:F2F5F7;'>
	  		<tr valign='middle' >	  			
		  		<td style = 'font-size:13px;'>
			  		<fieldset><legend>�������</legend>  		
			  			<table border='0' cellpadding='0' cellspacing='0'  width='640'align='center'>	  							
			  				<tr valign='middle' >
			  					<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectnewpid'></div>							  												  					
							  	</td>	
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectnewyear'></div>						  												  					
							  	</td>
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectnewmonth'></div>							  												  					
							  	</td>
							  	<td align='center' valign='middle' style = 'font-size:13px;display:none;'>
							  		<input type='checkbox' name='chbmy' id='chbmy' onclick="ChbMoney()">����
				  					���<input style = 'text-align: right;' type="text" id = "inputmy" size = '6' value = '0'></input>(Ԫ)	  												  												  					
							  	</td>
							</tr>
							<tr valign='middle' >	
								<td align='left' valign='middle' style = 'font-size:13px;' colspan = '3'>
				  					����<input type="text" id = "newdesc" size = '45'></input>					  												  					
							  	</td>  					
							  	<td align='right' valign='middle' style = 'font-size:13px;'>
				  					����<input type="text" id = "newbegdt" size = '10'  onFocus='setday(this)'></input>	
				  					��<input type="text" id = "newenddt" size = '10'  onFocus='setday(this)'></input>
				  					<button class = 'btn' onclick="addPolicyNowAcc()"> ��  �� </button>				  												  					
							  	</td>						  	
							</tr>					  			
				  		</table>
			  		</fieldset>
		  		</td>
  			</tr>
	  		<tr valign='middle' >	  			
		  		<td style = 'font-size:13px;'>
			  		<fieldset><legend>���β�ѯ</legend>
				  		<table border='0' cellpadding='0' cellspacing='0' width='640' align='center'>	  			
			  				<tr valign='middle' >
			  					<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectpid'></div>							  												  					
							  	</td>	
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
				  					<div id = 'selectyear'></div>						  												  					
							  	</td>
							  	<td align='left' valign='middle' style = 'font-size:13px;display:none;'>
				  					<div id = 'selectmonth'></div>							  												  					
							  	</td>							  	
							  	<td align='left' valign='middle' style = 'font-size:13px;'>
							  		״̬<select id='acclist' style = "font-size:12px;width = '84';" onchange="getPolicyAccTable()">
							  			<option value='-1'>ȫ��</option> 
							  			<option value='0'>δ����</option> 
							  			<option value='1'>������</option> 
							  			<option value='2'>�������</option> 
							  		</select> 												  												  					
							  	</td>
							  	<td align='right' valign='middle' style = 'font-size:13px;'>
				  					����<input type="text" id = "inputdept" size = '31' onclick="queryDept()"></input>	
				  					<input type="text" id = "inputdeptid" style = 'display:none;'></input>					  												  					
							  	</td>
							</tr>
							<tr valign='middle' >				  				
				  				<td align='left' valign='middle' style = 'font-size:13px;' colspan = '3'>
				  					����<input type="text" id = "inputdesc" size = '46'></input>						  												  					
							  	</td>	
							  	<td align='right' valign='middle' style = 'font-size:13px;'>
				  					����<input type="text" id = "inputbegdt" size = '10'  onFocus='setday(this)'></input>
				  					��<input type="text" id = "inputenddt" size = '10'  onFocus='setday(this)'></input>	
				  					<button class = 'btn' id = 'btnquery' onclick="getPolicyAccTable()"> ��  ѯ </button>					  												  					
							  	</td>
			  				</tr>	  			
				  		</table>
				  	</fieldset>
			  	</td>
		  	</tr>
  		</table>
  		<fieldset><legend>��ѯ�б�</legend>
	  		<div align='center' id = 'queryacctb'></div>
    		<div align='center' id = "paginationtb"></div> 
    	</fieldset>
  </body>
</html>
<script  type="text/javascript">
	//��ҳ��
	function Pagination(){	
		//
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";				
			html += "<td height='25'>";
				html += "��["+sqlcount+"]����¼  ��["+sqlpagecount+"]ҳ  ";	
				if(sqlselpage==1){
					html += "<span>��ҳ</span> ";
					html += "<span>��һҳ</span>�0�2";
				}else{
					html += "<span class = 'pointer' onclick=\"ExePage('beg')\">��ҳ</span> ";
					html += "<span class = 'pointer' onclick=\"ExePage('back')\">��һҳ</span>�0�2";
				}
				if(sqlselpage==sqlpagecount){	
					html += "<span>��һҳ</span> ";
					html += "<span>βҳ</span>�0�2";
				}else{
					html += "<span class = 'pointer' onclick=\"ExePage('next')\">��һҳ</span> ";
					html += "<span class = 'pointer' onclick=\"ExePage('end')\">βҳ</span> ";
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
		//��ȡҵ����ִ������
	  	getPolicyAccTable();
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
		//��ȡҵ����ִ������
	  	getPolicyAccTable();
	}	
</script>
<script  type="text/javascript">
	//������������ѡ���
	function CreateSelect(){
		var yhtml = "<select id='syear' style = \"font-size:12px;width = '64';\">";
		var mhtml = "<select id='smonth' style = \"font-size:12px;width = '42';\">";
		for(var i = 1998;i<=2050;i++){
			yhtml += "<option value='"+i+"'>"+i+"</option>";
		}		
		yhtml += "</select>";
	  	selectyear.innerHTML = yhtml+"��";
	  	for(var i = 1;i<=12;i++){
			mhtml += "<option value='"+i+"'>"+i+"</option>";
		}
	  	mhtml += "</select>";
		selectmonth.innerHTML = mhtml+"��";
		//
		var yhtml = "<select id='snewyear' style = \"font-size:12px;width = '64';\">";
		var mhtml = "<select id='snewmonth' style = \"font-size:12px;width = '42';\">";
		for(var i = 1998;i<=2050;i++){
			yhtml += "<option value='"+i+"'>"+i+"</option>";
		}		
		yhtml += "</select>";
	  	selectnewyear.innerHTML = yhtml+"��";
	  	//
	  	for(var i = 1;i<=12;i++){
			mhtml += "<option value='"+i+"'>"+i+"</option>";
		}
	  	mhtml += "</select>";
		selectnewmonth.innerHTML = mhtml+"��";
		//
		//��ȡϵͳ����
		getSysTimeFormatDate();
	}
	//��ȡϵͳ����
	function getSysTimeFormatDate(){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "getSysTimeFormatDate"             //action����
	        },
	        function(result) {                    	//�ص�����
	        	$("#inputenddt").val(result);
	        	$("#newenddt").val(result);
	        	var sysy = result.substring(0,4);	
	        	var sysm = result.substring(6,7);
	        	var sysd = result.substring(9,10);
	        	$("#syear").val(sysy);    
	        	$("#smonth").val(sysm); 
	        	$("#snewyear").val(sysy);    
	        	$("#snewmonth").val(sysm); 	        	   	
	        }
	    );
	}
	//����XML�ļ�
	function ShowData(data){
		var xmlDoc;	
		var xtable;	
		//
		//		
		sqlcount=0;
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
			//�����������
			var familyarr=root.selectNodes("/data/table/elements");
			xtable =familyarr.item(0).firstChild.data;
			queryacctb.innerHTML=xtable;
			//
			//JS�������
			new TableSorter("acctb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/
		}else{
			
		}
		//
	  	//��ҳ��
	  	Pagination();
	}
</script>
<script type='text/javascript'>
	//=================================AJAXģ̬����=================================
	(function($){
	$.openWindow = function(options){
	        var defaults = {
                   title:"title",                           //����
                   content:"Content",                       //��ʾ����
                   loadUrl:"",                              //����url
                   otype:"0",                               //�������
                   bColor:"#777",                           //����ɫ
                   //bWidth:(document.body.clientWidth-50)+"px",   //�������
                   bWidth:0+"px",   //�������
                   bHeight:document.body.clientHeight+"px", //�����߶�
                   oColor:"#FFF",                           //����������ɫ
                   oWidth:350,                              //�������ڿ��
                   oHeight:380                              //�������ڸ߶�
           	};
	        $.extend(defaults,options);
	        		       
	        //����
	        var cbtn = "<div id='cbtn'><img src='/db/page/images/close.gif'/></div><span id = 'stitle'>"+defaults.title+"</span><div>";
	        var odiv = "<div id='odiv'>"+cbtn+"<div id='content'></div></div>";
	        var bdiv = "<div id='bdiv'></div>";		        
	        if(!($("#bdiv").length))$("body").append(bdiv);
	        if(!($("#odiv").length))$("body").append(odiv);	
	        if(defaults.otype=="0"){
	        	//������
	        	$("#content").load(defaults.loadUrl);
	        	//CSS
	        	$("#cbtn>img").css({"cursor":"pointer","float":"right"}).click(function(){$("#bdiv").remove();$("#odiv").remove();});	
	        }else if(defaults.otype=="1"){
	        	//�򿪻���ѡ��
	        	$("#content").empty();                //��������б�
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
	
	//��ѯ������׼
	function queryDept(){
    	$.openWindow({"title":"����ѡ��","otype":"1"});		    			
	}
	
	//����ѡ��[DeptTreeServlet�����ķ���]
	function seldeptclick(id,name,fullname){
	   //ѡ�л���	   
	   $("#selname").html("ѡ��:["+name+"]");
	   seldeptid = id;
	   seldeptname = name;
	   seldeptfullname = fullname;
	   //
	   stitle.innerHTML = "ѡ��:"+seldeptfullname;
	}
	//����ѡ��ȷ��[DeptTreeServlet�����ķ���]
	function okdept(){		    
		//������׼ѡ��
		if(seldeptid==null) return;
		/*
		if (!confirm("�Ƿ�ȷ��ѡ��\n["+seldeptname+"]")) {
	        return;
	    }
	    */
	    //ѡ����ò���
	    $("#inputdeptid").val(seldeptid);
	    $("#inputdept").val(seldeptfullname);	    
		closedept();
	}
	//�ÿջ���ѡ��[DeptTreeServlet�����ķ���]
	function cleardept(){
		//ѡ����ò���
	    $("#inputdeptid").val("");
	    $("#inputdept").val("");	
	    //
		closedept();	
	}
	//�رջ���ѡ��[DeptTreeServlet�����ķ���]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();			
	}
	//
	//=================================AJAXģ̬����=================================
</script>