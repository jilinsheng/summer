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
	//����qmode
	String reqmode = request.getParameter("qmode");	
	if (reqmode == null) {
		//Ĭ��Ϊ��
		reqmode = "edit";    //�༭״̬(edit,add,all)
	}
	//����qpid
	String reqpid= request.getParameter("qpid");		
	if (reqpid == null) {
		//Ĭ��Ϊ��
		reqpid = "";    //��ҵ����
	}	
	//����qfmid
	String reqfmid= request.getParameter("qfmid");		
	if (reqfmid == null) {
		//Ĭ��Ϊ��
		reqfmid = "";    //�ռ�ͥID
	}
	//
	//����qcheckmode
	String reqcheckmode= request.getParameter("qcheckmode");		
	if (reqcheckmode == null) {
		//Ĭ��Ϊ��
		reqcheckmode = "";    //�ղ�ѯ������׼ģʽ
	}
	//
	//���ղ�ѯ��qselsql
	String reqselsql = request.getParameter("qselsql");	
	reqselsql = new String(reqselsql.getBytes("ISO8859_1"), "UTF-8");//����
	if (reqselsql == null) {
		//Ĭ��Ϊ��
		reqselsql = "";    //��sql
	}	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��Ա���������д����</title>
    
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
	</script>
	<script type="text/javascript">	
		//
		//
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";       //��ǰ��¼����
		//
		var pid = "";		   //��ǰ����ҵ��ID 
		var fmids = "";		   //��ǰ�����ͥID
		var mode = "";		   //��ǰ����ģʽ���ǲ鿴ģʽ 
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		var selcheckmode = "111";	//����ҳ���ǩ(����)
		var selsql = "";			//����ҳ��SQL���
		//
		//ҵ��������ʶ
		var allaccflag = "0";		//��������ʶ
		var flowflag = "0";			//�������̱�ʶ
		var checkmoreflag = "0";	//����������ʶ
		var oneflowflag = "0";		//��һ������������ʶ
		var endflowflag = "0";		//�������������ʶ
		var pcheckflag = "0";		//��λ������ʶ
		var pcheckmoreflag = "0";	//��λ����������ʶ
		var preportflag = "0";		//��λȷ��������ʶ
		var checkmoneyflag = "0";	//�û����޸ľ������ʶ
		var acctypeflag = "0";		//�������ͱ�ʶ
		//
		var reqtype = "";
		var reptype = "";
		var repman = "";
		var repidea = "";
		var repdt = "";
		var repbegdt = "";
		var rependdt = "";
		var idsmoneys ="";			//����ID�;�����
		var idschildmoneys ="";		//����ʩ������ID�ͷ���ʩ��������
		//
		//������ʽƥ��
	    //�����ַ�������
	    function MatchMatch(str,s)
	    {
			var r, re; // ����������
			//g ��ȫ�Ĳ��ҳ��ֵ����� pattern�� 
			//i �����Դ�Сд�� 
			//m �����в��ң�
			//re   =   /s/gi;
			re = new RegExp(s,"gi"); // ����������ʽ����
			r = str.match(re); // ���ַ��� s �в���ƥ�䡣             
			return(r);  
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
		//
		//��ʼ��ҳ��
	    function IniPage(){	
	    	//	    	   
			empid = "<%=empno%>"; 	//��ǰ��¼�û����
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			pid = "<%=reqpid%>";				//��ǰ����ҵ��ID 
			fmids = "<%=reqfmid%>";				//��ǰ�����ͥID 
			mode = "<%=reqmode%>";				//��ǰ����ģʽ���ǲ鿴ģʽ
			selcheckmode = "<%=reqcheckmode%>";	//����ҳ���ǩ
			selsql = "<%=reqselsql%>";			//����ҳ��SQL���
			//			
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			//ȡ��ҵ������״̬��ʶ
			GetCheckPolicyFlagsXml();
		}
	</script>	
  </head>
  
  <body  onload = "IniPage()">
  	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>  
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  	<div id='resultstatusdiv'></div>
  	<fieldset id = "checkitems" class='list'>
		<legend class='legend'>�����д����</legend>			
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  		<tr align="center">
    		<td valign="top" class = "myspace"><div id="checkideacon"></div></td>	
    	</tr>
  	</table> 	
   	</fieldset>
  	<fieldset id = "familyitems" class='list'>
		<legend class='legend'>������ͥ��Ϣ</legend>			
  		<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div id="familycon"></div></td>	
	    	</tr>		    		   				    	    	
    	</table> 	
   	</fieldset>
   	<div id = "policyinfodiv"></div>
  </body>
</html>
<script type="text/javascript">	
	//�������ԭ��
	function addDictValue(){
		//(����ԭ��:115)
		UpdateDiscionary('115');
	}
	//�����ֵ�ֵ
	function UpdateDiscionary(discid){
		//
		if(discid==""||discid==null){
			DisplayResult("��ʾ:û��ѡ���ֵ�!");				
			return;
		}		
		var bWidth = document.body.clientWidth-30;  //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bHeight= 300; //�����߶�
		var sArgu = "qid="+discid+"";
		var oUrl = "/db/page/policy/manage/policydiscionary.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"�ֵ����͹���&UpdatePolicyRequestTypeChoice()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//���������
	function addCheckPerson(){
	    var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/policy/manage/policycheckperson.jsp?";
		jBox.open("iframe-jBoxID","iframe",oUrl,"���������˹���&UpdateCheckPersonChoice()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		
	}
	//��������ԭ��
	function UpdatePolicyRequestTypeChoice(){
		//����ԭ��
		GetPolicyRequestTypeChoice("requesttypechoice","liststype");
		if(mode!="edit"){//�鿴��������״̬
	    	//
	    	$("#checkideatxt").get()[0].focus();
	    }
	}
	//����������
	function UpdateCheckPersonChoice(){
		//������
		GetCheckPersonChoice("ideamanchoice","listsideaman");
		if(mode!="edit"){//�鿴��������״̬
	    	//
	    	$("#checkideatxt").get()[0].focus();
	    }	
	}
</script>
<script type="text/javascript">
	<!-- 
	//ȡ�������Ѿ���д��Html
	function GetCheckIdeaHtml(){
	  //
	  $.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
	        {
	            action: "getCheckIdeaHtml" //action����  
	        },
	        function(result) {   //�ص�����
	        	checkideacon.innerHTML = result;
	        	//
	        	if(mode=="edit"){//�鿴��������״̬
			    	//
			    	$("#checkitems").css({"display":"none"});
			    	//ȡ��ҵ���ͥ�����б�
					GetFamilyCheckItemsTable();
			    }else if(mode=="add"){//������״̬
			    	$("#checkitems").css({"display":"block"});
			    	$("#checkideatxt").get()[0].focus();			    	
					//��������ԭ��
			    	UpdatePolicyRequestTypeChoice();	
			    	//����������
			    	UpdateCheckPersonChoice();		    	
			    	//�������
			    	GetPolicyCheckIdeaChoice("checkideachoice","listsidea",selcheckmode);
			    	//ȡ��ҵ���ͥ�����б�
					GetFamilyCheckItemsTable();
			    }else if(mode=="all"){//ȫ������״̬
			    	$("#checkitems").css({"display":"block"});
			    	$("#checkideatxt").get()[0].focus();
			    	//��������ԭ��
			    	UpdatePolicyRequestTypeChoice();	
			    	//����������
			    	UpdateCheckPersonChoice();		    	
			    	//�������
			    	GetPolicyCheckIdeaChoice("checkideachoice","listsidea",selcheckmode);
			    	//
			    	$("#familyitems").css({"display":"none"});			    	
			    }	       	          	                                         
	        }
	    );        
	}	
	//ȡ��ҵ������ԭ�����ѡ��������
	function GetPolicyRequestTypeChoice(pardivid,sname){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	//  
	    $.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
	        {
	            action: "getPolicyRequestTypeChoice", //action����
	            sname: sname //����                              
	        },
	        function(result) {   //�ص�����
	          	pardiv.innerHTML = result;			       	          	                                         
	        }
	    );        
	}
	//ȡ��������ѡ��������
	function GetCheckPersonChoice(pardivid,sname){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	//
	    $.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
	        {
	            action: "getCheckPersonChoice", //action����
	            sname: sname, //����
	            empid: empid                              
	        },
	        function(result) {   //�ص�����
	          	pardiv.innerHTML = result;
	          	//
	          	if(result != ""){
	          		$("#"+sname).val(empid);
	          	}				       	          	                                         
	        }
	    );        
	}
	//ȡ��ҵ�񱾼��������ѡ��������
	function GetPolicyCheckIdeaChoice(pardivid,sname,smode){
	  	//   
	  	var pardiv = document.getElementById(pardivid);
	  	
	    $.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
	        {
	            action: "getPolicyCheckIdeaChoice", //action����
	            sname: sname, //����
	            smode: smode                              
	        },
	        function(result) {   //�ص�����
	          	pardiv.innerHTML = result;	
	        }	          	
	    );        
	}
	//ѡ������������
	function SetIdeaResult(){
		var sval = $("#listsidea").val();		          									
		if(sval=="2"){//ͬ�����
			$("#redatetr").css({"display":"block"});			          										
		}else{
			$("#redatetr").css({"display":"none"});
		}
		if(sval=="2" || sval=="4" || sval=="7"){//ͬ����������ˡ��ָ�		          										
			$("#checktypetr").css({"display":"block"});			          										
		}else{
			$("#checktypetr").css({"display":"none"});
		}
	}
</script>
<script type="text/javascript">
	//ȡ��ҵ������״̬��ʶ
	function GetCheckPolicyFlagsXml(){
	  	//
	    $.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
	        {
	            action: "getCheckPolicyFlagsXml", //action����
	            pid: pid, //����
	            empid: empid                              
	        },
	        function(result) {   //�ص�����
	        	//����ҵ������״̬��ʶ
	          	ShowFlagData(result);
	        }	          	
	    );        
	}
	//����ҵ������״̬��ʶ
	function ShowFlagData(data){
		var xmlDoc,tempArr;
		
		//		
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//��������ʶ
			tempArr = root.selectNodes("/data/allaccflag/flag");
			allaccflag = tempArr.item(0).firstChild.data;
			//�������̱�ʶ
			tempArr = root.selectNodes("/data/flowflag/flag");
			flowflag = tempArr.item(0).firstChild.data;
			//����������ʶ
			tempArr = root.selectNodes("/data/checkmoreflag/flag");
			checkmoreflag = tempArr.item(0).firstChild.data;
			//��һ������������ʶ
			tempArr = root.selectNodes("/data/oneflowflag/flag");
			oneflowflag = tempArr.item(0).firstChild.data;
			//�������������ʶ
			tempArr = root.selectNodes("/data/endflowflag/flag");
			endflowflag = tempArr.item(0).firstChild.data;
			//��λ������ʶ
			tempArr = root.selectNodes("/data/pcheckflag/flag");
			pcheckflag = tempArr.item(0).firstChild.data;
			//��λ����������ʶ
			tempArr = root.selectNodes("/data/pcheckmoreflag/flag");
			pcheckmoreflag = tempArr.item(0).firstChild.data;
			//��λȷ��������ʶ
			tempArr = root.selectNodes("/data/preportflag/flag");
			preportflag = tempArr.item(0).firstChild.data;
			//�û����޸ľ������ʶ
			tempArr = root.selectNodes("/data/checkmoneyflag/flag");
			checkmoneyflag = tempArr.item(0).firstChild.data;			
			//�������ͱ�ʶ
			tempArr = root.selectNodes("/data/acctypeflag/flag");
			acctypeflag = tempArr.item(0).firstChild.data;
			
		}else{
			//
			
		}
		//ȡ�������Ѿ���д��Html
		GetCheckIdeaHtml();
	}
</script>

<script type="text/javascript">
	//��ȡ�����������
	function GetMoreCheckIdeaHtml(checkid,memname){
		//
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var sArgu = "qpid="+pid+"&qcheckid="+checkid+"&qmode=check";
		var oUrl = "/db/page/policy/manage/policycheckideahtml.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��Ա������ϸ ����:"+memname+"","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");		
	}
	//��ȡ����ʩ�������������
	function GetChildCheckIdeaHtml(checkid,memname){
		//
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var sArgu = "qpid="+pid+"&qcheckid="+checkid+"&qmode=child";
		var oUrl = "/db/page/policy/manage/policycheckideahtml.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"����ʩ��������ϸ ����:"+memname+"","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");		
	}		
</script>
<script type="text/javascript">
	//ȡ��ҵ���ͥ�����б�
	function GetFamilyCheckItemsTable(){
	  	//
	  	var smode = checkmoneyflag;//�����û������޸ľ������ʶ
	  	//
	    $.post("/db/page/policy/manage/policycheckideamember.do",      //������ҳ���ַ
	        {
	            action: "getFamilyCheckItemsTable", //action����
	            pid: pid, //����
	            fmids: fmids,
	            mode: smode,
	            empid: empid                               
	        },
	        function(result) {   //�ص�����
	        	//
	        	var pardiv = document.getElementById("familycon");
	          	pardiv.innerHTML = result;	          	
	        }	          	
	    );        
	}
	//��ʾ�����س�Ա��ϸ
	function ViewMembertb(src,trid){
		var sdisplay = $("#"+trid).css("display");
		if(sdisplay=="none"){
			//
			var trs =  document.getElementsByTagName("tr");		
			for(var i=0; i<trs.length;i++){					
				var memtrid = trs[i].id;
				if(memtrid.length>8){
					var memid = memtrid.substring(0,8);										
					if(memid == "membertr"){						
						$("#"+memtrid).css({"display":"none"});
					}
				}	
			}			
			//		
			$("#"+trid).css({"display":"block"});
			src.src="/db/page/images/membermin.jpg";
			src.alt="���ط��ϱ�׼��Ա�б�";			
		}else{
			$("#"+trid).css({"display":"none"});
			src.src="/db/page/images/membermax.jpg";
			src.alt="��ʾ���ϱ�׼��Ա�б�";	
		}
	}
	//��ʾ�����س�Ա�б�ͷ���ʩ����ϸ
	function ViewMemberChildtb(src,mrid,crid){
		var sdisplay = $("#"+mrid).css("display");		
		if(sdisplay=="none"){
			//
			var trs =  document.getElementsByTagName("tr");		
			for(var i=0; i<trs.length;i++){					
				var memtrid = trs[i].id;
				if(memtrid.length>8){
					var memid = memtrid.substring(0,8);										
					if(memid == "membertr"){						
						$("#"+memtrid).css({"display":"none"});
					}
				}
				var childtrid = trs[i].id;
				if(childtrid.length>7){
					var childid = childtrid.substring(0,7);									
					if(childid == "childtr"){						
						$("#"+childtrid).css({"display":"none"});
					}
				}	
			}
			//
			//		
			$("#"+mrid).css({"display":"block"});
			$("#"+crid).css({"display":"block"});
			//
			var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "imgs"){
					imgs[i].src="/db/page/images/membermax.jpg";
				}
			}
			src.src="/db/page/images/membermin.jpg";
			src.alt="����";			
		}else{
			$("#"+mrid).css({"display":"none"});
			$("#"+crid).css({"display":"none"});
			//
			var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "imgs"){
					imgs[i].src="/db/page/images/membermax.jpg";
				}
			}
			src.src="/db/page/images/membermax.jpg";
			src.alt="չ��";	
		}
	}
	//��ʾ��ͥ��ؾ���
	function ViewPolicys(fmid){
		alert("��ʾ��ͥ��ؾ������");
	}
	//�Ƴ�����
	function MoveDel(ftrid,mtrid,ptrid){		
		//
		var d = document.getElementById(mtrid);   
		  if (d && confirm("��Ҫ�Ƴ�?")){
			  //ɾ����ͥ��Ա�� 
			  var p;  
			  if(p=d.parentNode){   
			  	p.removeChild(d);			  	   
			  }
			  //ɾ����ͥ��
			  var pp;
			  var pd = document.getElementById(ftrid);
			  if(pp=pd.parentNode){   
			  	pp.removeChild(pd);			  	   
			  } 
			  //ɾ��ҵ�������
			  var ppp;
			  var ppd = document.getElementById(ptrid);
			  if(ppp=ppd.parentNode){   
			  	ppp.removeChild(ppd);			  	   
			  }   
		  }
	}
	//�Ƴ�����
	function MoveDelChild(ftrid,mtrid,ptrid,ctrid){		
		//
		var d = document.getElementById(mtrid);   
		  if (d && confirm("��Ҫ�Ƴ�?")){
			  //ɾ����ͥ��Ա�� 
			  var p;  
			  if(p=d.parentNode){   
			  	p.removeChild(d);			  	   
			  }
			  //ɾ����ͥ��
			  var pp;
			  var pd = document.getElementById(ftrid);
			  if(pp=pd.parentNode){   
			  	pp.removeChild(pd);			  	   
			  } 
			  //ɾ��ҵ�������
			  var ppp;
			  var ppd = document.getElementById(ptrid);
			  if(ppp=ppd.parentNode){   
			  	ppp.removeChild(ppd);			  	   
			  }  
			  //ɾ������ʩ����
			  var pppp;
			  var pppd = document.getElementById(ctrid);
			  if(pppp=pppd.parentNode){   
			  	pppp.removeChild(pppd);			  	   
			  } 
		  }
	}
	//���ܼ�ͥ����������
	function SumMoneyFamily(fmid,checkid,childflag){
		var Objs;		
		//��Ա�б�				
		Objs = document.getElementById("memberstb"+fmid);		
		var inputs =  Objs.getElementsByTagName("input");
		var summoney = 0;					
		for(var i=0; i<inputs.length;i++){
			var moneyid = inputs[i].id;
			var moneyval = inputs[i].value;
			summoney += parseInt(moneyval);		
		}
		//��Ա�ⷢ������
		var memmoney = document.getElementById("usermoney"+checkid).value;
		var memcountspan = document.getElementById("countmoney"+checkid);
		memcountspan.innerHTML = memmoney;	
		//��ͥ����������	
		var parspan = document.getElementById("allmoney"+fmid);
		parspan.innerHTML = summoney;
		//��ͥ�ⷢ������
		var countmoney = 0;		
		if("1"==childflag){//����ʩ����ʶ
			var allmoney = document.getElementById("allmoney"+fmid).innerText;
			var fmoney = parseInt(allmoney);
			//����ʩ��
			var childmoney = document.getElementById("allchildmoney"+fmid).innerText;
			var cmoney = parseInt(childmoney);
			//�ⷢ������
			countmoney = fmoney + cmoney;
		}else{
			var allmoney = document.getElementById("allmoney"+fmid).innerText;
			var fmoney = parseInt(allmoney);
			//�ⷢ������
			countmoney = fmoney;
		}
		//�ⷢ������
		var countspan = document.getElementById("countmoney"+fmid);
		countspan.innerHTML = countmoney;			
	}
	//���ܼ�ͥ����ʩ��������
	function SumChildMoneyFamily(fmid){
		//
		var Objs;		
		//����ʩ���б�				
		Objs = document.getElementById("childstb"+fmid);		
		var inputs =  Objs.getElementsByTagName("input");
		var summoney = 0;					
		for(var i=0; i<inputs.length;i++){
			var moneyid = inputs[i].id;
			var moneyval = inputs[i].value;
			summoney += parseInt(moneyval);		
		}
		//��ͥ����ʩ��	
		var parspan = document.getElementById("allchildmoney"+fmid);
		parspan.innerHTML = summoney;
		//����������
		var allmoney = document.getElementById("allmoney"+fmid).innerText;
		var fmoney = parseInt(allmoney);
		//����ʩ��
		var childmoney = document.getElementById("allchildmoney"+fmid).innerText;
		var cmoney = parseInt(childmoney);
		//�ⷢ������
		countmoney = fmoney + cmoney;
		var countspan = document.getElementById("countmoney"+fmid);
		countspan.innerHTML = countmoney;
	}
</script>
<script type="text/javascript">
	//��������
	function ChioceDo(src,checkid,memname){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		//��ȡ�����������
		GetMoreCheckIdeaHtml(checkid,memname);
	}
	//��������
	function ChioceDoChild(src,checkid,memname){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		//��ȡ����ʩ�������������
		GetChildCheckIdeaHtml(checkid,memname);
	}
</script>
<script type="text/javascript">
	//ѡ��������
	function ChoiceIdeaman() {
		var isideaman;
		var ideaman = $("#checkideaman").val();
		//
		isideaman = document.getElementById("listsideaman");
		if(isideaman ==null){
			alert("�����������!");
			return;
		}
		var sideaman = listsideaman.options[listsideaman.selectedIndex].text;
		var si = ideaman;
		//
		if(ideaman==""){
     		$("#checkideaman").val(sideaman);	
      	}else{
	      	if(MatchMatch(si,sideaman)==null){                              
	        	if (si.length>0){
	        		$("#checkideaman").val(ideaman+","+sideaman);          
	        	} 
	      	}
      	} 		
	}
	//�ÿ�������
	function ClearIdeaman() {
		$("#checkideaman").val(""); 
	}
	//ѡ��ԭ��
	function ChoiceType() {
		var istype;
		var type = $("#checktypes").val();
		var typeid = $("#checktypesid").val();
		//
		istype = document.getElementById("liststype");
		if(istype ==null){
			alert("���������ԭ��!");
			return;
		}
		//
		var stypeid = $("#liststype").val();
		var stype = liststype.options[liststype.selectedIndex].text;
		var st = type;
		//
		if(type==""){
      		$("#checktypes").val(stype);
      		$("#checktypesid").val(stypeid);	
      	}else{
	      	if(MatchMatch(st,stype)==null){                              
	        	if (st.length>0){
	        		$("#checktypes").val(type+","+stype);
	        		$("#checktypesid").val(typeid+","+stypeid);	            
	        	} 
	      	}
      	} 		    
	}
	//�ÿ�ԭ��
	function ClearType() {
		$("#checktypesid").val(""); 
		$("#checktypes").val("");    		    
	}	
	//��������������
	function SetEndDt() {
		var ischeckb,enddt;
		ischeckb = document.getElementById("checkb");
		if(ischeckb.checked==0){
			$("#reenddt").val(""); 
		}else{
			enddt = parseInt(mynewy)+60;
			enddt += "-" + mynewm;
			enddt += "-" + mynewd;
			$("#reenddt").val(enddt); 
		}
	}
</script>
<script type="text/javascript">
	//��ȡ��ͥ����ҵ�������ϸ��Ϣ
	function GetCheckPolicyInfosHtml(fmid,memid,fmname){
	    //
		//var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var bWidth= 450; //�������
		var sArgu = "qpid="+pid+"&qfmid="+fmid+"&qmemid="+memid+"&qmemname="+fmname+"&qacctype="+acctypeflag;
		var oUrl = "/db/page/policy/manage/policycheckinfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"������"+fmname+"  ҵ�������Ϣ��ϸ","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
</script>
<script type="text/javascript">
	//��ȡ��ͥ����ҵ����ر�׼
	function GetCheckPolicySqlsHtml(checkid,fmname){		
		//��ʾҳ��״̬div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //������ҳ���ַ
	        {
	            action: "getCheckPolicySqlsHtml",             //action����
	            checkid: checkid
	        },
	        function(result) {                    	//�ص�����
	        	//����ҳ��״̬div
				HiddenPageStatus();	
				//
				var pardiv = document.getElementById("policyinfodiv");
				pardiv.innerHTML = result;
				//	
				//var bWidth = document.body.clientWidth-30;  //�������
				var bHeight= document.body.clientHeight-80; //�����߶�
				var bWidth= 300; //�������
				//var bHeight= 300; //�����߶�
				jBox.open("div-jBoxID","div","policyinfodiv","����:"+fmname+" ҵ����ر�׼&ClosePolicyDiv()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");	        		    					      		
	        }
	    );
	}
	//��ȡ��ͥ����ҵ����غ��㹫ʽ
	function GetCheckPolicyAccsHtml(checkid,fmname){		
		//��ʾҳ��״̬div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //������ҳ���ַ
	        {
	            action: "getCheckPolicyAccsHtml",             //action����
	            checkid: checkid
	        },
	        function(result) {                    	//�ص�����
	        	//����ҳ��״̬div
				HiddenPageStatus();	
				//
				var pardiv = document.getElementById("policyinfodiv");
				pardiv.innerHTML = result;
				//	
				//var bWidth = document.body.clientWidth-30;  //�������
				var bHeight= document.body.clientHeight-80; //�����߶�
				var bWidth= 300; //�������
				//var bHeight= 300; //�����߶�
				jBox.open("div-jBoxID","div","policyinfodiv","����:"+fmname+" ҵ����㹫ʽ&ClosePolicyDiv()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");	        		    					      		
	        }
	    );
	}
	//��ȡ��ͥ����ҵ�����ʩ����ر�׼
	function GetCheckPolicyChildSqlsHtml(checkid,fmname){		
		//��ʾҳ��״̬div
		DisplayPageStatus();
		//
	    $.post("/db/page/policy/info/policyinfoquery.do",            //������ҳ���ַ
	        {
	            action: "getCheckPolicyChildSqlsHtml",             //action����
	            checkid: checkid
	        },
	        function(result) {                    	//�ص�����
	        	//����ҳ��״̬div
				HiddenPageStatus();	
				//
				var pardiv = document.getElementById("policyinfodiv");
				pardiv.innerHTML = result;
				//	
				//var bWidth = document.body.clientWidth-30;  //�������
				var bHeight= document.body.clientHeight-80; //�����߶�
				var bWidth= 300; //�������
				//var bHeight= 300; //�����߶�
				jBox.open("div-jBoxID","div","policyinfodiv","����:"+fmname+" ��ر�׼&ClosePolicyDiv()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");	        		    					      		
	        }
	    );
	}
	//�رմ������
	function ClosePolicyDiv(){
		var pardiv = document.getElementById("policyinfodiv");
		pardiv.innerHTML = "";
	}
</script>
<script type="text/javascript">
	//����/��������ҵ��	
	function SetMoreCheckIdea(){
		var mess = "";
		//
		reqtype = $("#checktypesid").val();
		reptype = $("#listsidea").val();
		repman = $("#checkideaman").val();
		repidea = $("#checkideatxt").val();
		repdt = $("#checkideadt").val();
		repbegdt = $("#rebegdt").val();
		rependdt = $("#reenddt").val();
		//
		//
		var sval = $("#listsidea").val();				          									
		if(sval=="2" || sval=="4" || sval=="7"){//ͬ����������ˡ��ָ�
			//			
			if(reqtype == ""){
				alert("û��ѡ������ԭ��!");
				return;
			}
		}		
		//				
		if(repman == ""){
			alert("û����д������!");
			return;
		}
		//		
		if(repdt == ""){
			alert("û����д��������!");
			return;
		}
		if(sval=="2"){//ͬ�����
			//		
			if(repbegdt == ""){
				alert("û����д������ʼ����!");
				return;
			}
			if(rependdt == ""){
				alert("û����д������������!");
				return;
			}
		}				
		//			
		if(repidea == ""){
			alert("û����д���!");
			return;
		}
		//	
		//ȷ����ʾ
		if(sval=="3"){//���º˲�
			//��ǰҵ���һ��������ʶ
			if(oneflowflag == "1"){
				mess = "�������������º˲�,�Ƿ�ȷ������?";
			}else{
				mess = "�����������˻����º˲�,�Ƿ�ȷ������?";
			}			
		}else{
			if(preportflag=="1"){//ȷ�������ύ������ʶ
				if(endflowflag=="1"){//��ǰҵ�����һ��������ʶ
					mess = "�������Ϊ�������,�Ƿ�ȷ������?";
				}else{
					mess = "������ύ�ϼ�,�Ƿ�ȷ������?";			
				}			
			}else{
				mess = "�Ƿ�ȷ������?";
			}
		}
		//��ʼ����
       	if(mode=="edit"){//�鿴��������״̬
	    	
	    }else if(mode=="add"){//������״̬
	    	//����/��������ҵ��	
			SetMoreCheckIdeaFamilys(mess);
	    }else if(mode=="all"){//ȫ������״̬
	    	//ȫ������ҵ��	
			SetAllCheckIdeaFamily(mess);		    	
	    }	
	}
	//
	//����/��������ҵ��	
	function SetMoreCheckIdeaFamilys(mess){
		//����������ID�;�����
		idsmoneys = "";
		//
		var inputs =  document.getElementsByTagName("input");								
		for(var i=0; i<inputs.length;i++){
			var inputid = inputs[i].id;
			var inputval = inputs[i].value;
			if(inputid.length>9){
				var tinputid = inputid.substring(0,9);	
				var einputid = inputid.substring(9,inputid.length);
				if(tinputid=="usermoney"){
					if(idsmoneys==""){
						idsmoneys = einputid+","+inputval;
					}else{
						idsmoneys += ";"+einputid+","+inputval;
					}
				}
			}		
		}
		//����������ID�;�����
		idschildmoneys = "";
		//
		var inputschild =  document.getElementsByTagName("input");								
		for(var i=0; i<inputschild.length;i++){
			var inputid = inputschild[i].id;
			var inputval = inputschild[i].value;
			if(inputid.length>10){
				var tinputid = inputid.substring(0,10);	
				var einputid = inputid.substring(10,inputid.length);
				if(tinputid=="childmoney"){
					if(idschildmoneys==""){
						idschildmoneys = einputid+","+inputval;
					}else{
						idschildmoneys += ";"+einputid+","+inputval;
					}
				}
			}
					
		}
		//�Ƿ����������¼
		if(idsmoneys != ""){
			//
		    if (!confirm(mess)) {
		        return;
		    }
			//    
			DisplayPageStatus(); 
		 	//
		   	$.post("/db/page/policy/manage/policycheckmanage.do",      //������ҳ���ַ
		       	{
		           	action: "setMoreCheckIdea" , //action���� 
		           	pid: pid,		            
		           	allaccflag: allaccflag,
		           	preportflag: preportflag,
		           	empid: empid,
		           	reqtype: reqtype,
		           	reptype: reptype,
		           	repman: repman,
		           	repidea: repidea,
		           	repdt: repdt,
		           	repbegdt: repbegdt,
		           	rependdt: rependdt,
		           	idsmoneys: idsmoneys,
		           	idschildmoneys: idschildmoneys                             
		       	},
		      	function(result) {   //�ص�����
		      		//�����������
	   				HiddenPageStatus();
			    	//
	   				$("#btncheck").attr("disabled", "disabled");
	   				DisplayResult(result,"1");
	   				//			    		                    
		     	}
		   );
		}
	}
	//ȫ������ҵ��	
	function SetAllCheckIdeaFamily(mess){
		//
   		if (!confirm(mess)) {
       		return;
   		}
		//    
		DisplayPageStatus(); 
		//
  		$.post("/db/page/policy/manage/policycheckmanage.do",      //������ҳ���ַ
	      	{
	          	action: "setAllCheckIdeaFamily" , //action���� 
	          	pid: pid,		            
	          	allaccflag: allaccflag,
	          	preportflag: preportflag,
	          	empid: empid,
	          	reqtype: reqtype,
	          	reptype: reptype,
	          	repman: repman,
	          	repidea: repidea,
	          	repdt: repdt,
	          	repbegdt: repbegdt,
	          	rependdt: rependdt,
	          	selsql: selsql                             
	      	},
	     	function(result) {   //�ص�����
	     		//�����������
	 			HiddenPageStatus();
	    		//
	 			$("#btncheck").attr("disabled", "disabled");
	 			DisplayResult(result,"1");
	 			//			    		                    
	    	}
  		);
	}
</script>