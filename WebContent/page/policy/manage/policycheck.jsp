<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	//ҵ��ѡ��
	String reqpolicy = request.getParameter("qpolicy");	
	
	if (reqpolicy == null) {
		//Ĭ��Ϊ��
		reqpolicy = "-1";    //ѡ��ҵ��[��ѡ]
	}
	//
	//����ҵ������URLDecoder
	//�Ӳ�ѯ����ղ�ѯ�ֶ�select
	String reqselect = request.getParameter("qselect");
	reqselect = new String(reqselect.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqselect == null) {
		//Ĭ��Ϊ��
		reqselect = "";    //�ֶ�
	}
	//�Ӳ�ѯ����ղ�ѯ��from
	String reqfrom = request.getParameter("qfrom");
	reqfrom = new String(reqfrom.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqfrom == null) {
		//Ĭ��Ϊ��
		reqfrom = "";    //�ձ�
	}
	//�Ӳ�ѯ����ղ�ѯ����where
	String reqwhere= request.getParameter("qwhere");
	reqwhere = new String(reqwhere.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqwhere == null) {
		//Ĭ��Ϊ��
		reqwhere = "";    //������
	}
	//�Ӳ�ѯ����ղ�ѯ����order
	String reqorder= request.getParameter("qorder");
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
    
    <title>ҵ����������</title>
    
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
	.mystyle {	
		line-height: 2;	
		font-size: 12px;
		color: #6BA6FF;
		height: 20px;		
	}
	.mytabdefault{	
		line-height: 2;	
		font-size: 12px;
		color: #6BA6FF;
		height: 20px;		
	}
	.mytabactive{	
		line-height: 2;	
		font-size: 12px;
		color: #000000;
		height: 20px;		
	}
	.tab {
		width: 100%;		
		margin: 0 auto;
		overflow: hidden;				
	}
	.menuauto, .menuauto li {
		margin: 0;
		padding: 0;
		height: 23px;
		list-style: none;
		overflow: hidden;
		text-align: center;					
	}
	.menuauto {
		border-bottom: 1px solid #DEEFFF;		
	}
	.menuauto .default {
		width: 86px;
		float: left;
		font-size: 13px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		background: url('/db/page/images/checkdefault.jpg') no-repeat;
	}
	.menuauto .active {
		width: 86px;
		float: left;
		font-size: 13px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		color: #FFFFFF;
		background: url('/db/page/images/checkactive.jpg') no-repeat;
	}
	
	.menu, .menu li {
		margin: 0;
		padding: 0;
		height: 20px;
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
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		background: url('/db/page/images/checkdefault.jpg') no-repeat;
	}
	.menu .active {
		width: 86px;
		float: left;
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		color: #FFFFFF;
		background: url('/db/page/images/checkactive.jpg') no-repeat;
	}
	.btn { 
		BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 
	    #002D96 0px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; FILTER: 
	    progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
	    StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 
	    1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 0px; PADDING-BOTTOM: 0px;
	    BORDER-BOTTOM: #002D96 1px solid;
	    height:21;
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
		color: #6BA6FF;	
	}
	.pageTtems {
		color: #660099;		
	}
	.pointer {
		cursor: pointer;
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
  <script  type="text/javascript">
		//
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼����
		
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		var selselect = "";    	//ѡ���ѯ����ֶ�
		var selfrom = "";    	//ѡ���ѯ�����
		var selwhere = "";    	//ѡ���ѯ�������
		var selorder = "";    //ѡ���ѯ�������
		//
		var seltabmode = "spancheckitem";//�����ǩspancheckitem�����ǩspanresultitem
		var selautomode = "check";//��ǩ����check����result���
		//
		var selcheckmode = "infocheck";//������ǩ����infocheck���������ĸ�����ǩinforesult�������������ǩinforecheck����������������ǩ
		//		
		var selautotabid = "101";//�����ඨ��(Ĭ��:��������)
		var selautotabname = "";
		//
		var selchecktabid = "111";//��������(Ĭ��:δ����)
		var selchecktabname = "";
		//
		//
		var selpolicy = "-1";    	//ѡ��ҵ��
		var selpolicyname = "";		//ҵ������
	    //
		var xmldata;	//XML����ʵ��
		var selallfmid = "";//��ҳ������ͥID[�м���,���Ÿ���]
		//
		//
	    //
		var colnum = 0,rownum =0;//����/����
		//
		var sqlcount = "0";//�ܼ�¼��
		var sqlpagecount = "0";//��ҳ��		
		var sqlpagenum = "14";//ÿҳ��¼��
		//
		var sqlselpage = 1;//ѡ��ҳ
		//
		var selbegpage = 0;		//��ҳ��ʼ
		var selendpage = sqlpagenum;//��ҳ����
		//
		//
		var selsql = "";			//��ǰ��ѯSQL���
		//
	    var selexport = "0";		//�������ݱ�ʶ(0��1��������)
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
		//
  </script>
  <script  type="text/javascript">	
  	//����ת��[�߼�����ת����������]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,tmode,tbegpage,tendpage;
	    var tdept;  
	    var tpolicy,tchecktabid,tautotabid;   
	    var tchoiceflag,isallcb;   
	    		   
	    //      
	    tselect = selselect;  
	    tfrom = selfrom;
	    twhere = selwhere;
	    //����
    	var soid = "",soname = "";
    	var isrdasc = document.getElementById("rdasc");
    	var isrddesc = document.getElementById("rddesc");	    	
    	if(isrdasc.checked == true){	
    		soname = $("#orderbylist").val();		    	
	    	selorder = 	soname + " asc"; 
      	}else if(isrddesc.checked == true){
      		soname = $("#orderbylist").val();		    	
	    	selorder = 	soname + " desc";  
      	}	
	    torder = selorder;	    
	    //������ѯģʽ��������ģ�鶨��tmode
	    tmode = "1";//ģʽ0ִ�в�ѯ1��ȡSQL���
	    tbegpage = selbegpage;
	    tendpage = selendpage;
	    //����
	    tdept = deptid; 
	    //
	    tempid = empid;	    
	    
	    //ҵ��ѡ��		    		          		
	    tpolicy = selpolicy;
	    //ҵ��ѡ�
	    tautotabid = selautotabid;		    
	    tchecktabid = selchecktabid;		    
	    //
	    //
	    //ȫѡ
	    selsql = "";
	    tchoiceflag = "page";
	    //
		isallcb = document.getElementById("checkallb");
		if(isallcb==null){
			tchoiceflag = "page";
		}else{
		    if(isallcb.checked== 1){
		    	tchoiceflag = "all";
		    }else{
		    	tchoiceflag = "page";
		    }
	    }
	    
	    //�������ݱ�ʶ(0��1��������)
	    if(selexport == "1"){
	    	tchoiceflag = "export";
	    }			    
	    //	
	    mode = "exefamilychecksql";
	    //
	    //
	    //��������		    
		DisplayPageStatus(); 
		//
		//���ø���ǩ
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
	  	$("#spanresultitem").attr("disabled", "disabled");
		//
		//
	    $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
	        {
	            action: "getphysql", //action����
	            mode: mode,   //����
	            tselect: tselect,   //����
	            tfrom: tfrom,   //����
	            twhere: twhere,   //����
	            torder: torder, //����
	            tmode: tmode,
	            tbegpage: tbegpage,
	            tendpage: tendpage,  //������ҳ 
	            tdept: tdept,
	            tempid: tempid,		            
	            tpolicy: tpolicy,
	            tautotabid: tautotabid,
	            tchecktabid: tchecktabid,
	            tchoiceflag: tchoiceflag         
	        },
	        function(result) {   //�ص�����
	        	//�����������
     			HiddenPageStatus();
     			//
				//���ø���ǩ
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//  	        	
	  			//
	          	if(result=="-1"){                
	             	//��ѯ��䲻�Ϸ�          
	          	}else{
	            	if(mode=="exefamilychecksql"){
		            	//ҵ��������ͥ��ѯȡ���physql 		            	
		            	if(tchoiceflag== "all"){
		            		//ȫѡSQL���
					    	selsql = result;						    	
					    }else if(tchoiceflag == "export"){
					    	//�������ݱ�ʶ(0��1��������)
	    					selexport = "0";
	    					tchoiceflag = "page";
	    					//
	    					ExportXlsOpen(result);					    	
					    }else{
					    	//alert(result);		              			  		
	          				ShowData(result);		          										    	
					    } 
		            }			             
	           }                              
	    });
	    //�������ݱ�ʶ(0��1��������)
		selexport = "0";      
    }
    
	//�������������������
	function RemoveCheckIdea(sempid,spid,sfmid){
		//				
		//ȷ��
	    if (!confirm("�Ƿ�ȷ�������ü�ͥ���������")) {
	        return;
	    }			
		$.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
		    {
		        action: "removeCheckIdea", //action����
		        empid: sempid,
		        pid: spid,			        
		        fmid: sfmid                             
		    },
		    function(result) {   //�ص�����	
		    	//				   							    
		    	//��ʾ�������		    		
		        DisplayResult(result);		        
				//ȡ�������ѯ�����ִ�з���XML��ʽ		  
				GetPhySql();  				      	                                         
		    }
		);   
	}				
	//ˢ��ȫ������
	function UpdateMoneys(){
		//ȷ��
	    if (!confirm("�Ƿ�ȷ��ˢ��ȫ�����ݣ�")) {
	        return;
	    }
		//�������㵥����ͥ���߳�Ա�ⷢ������
    	UpdatePolciyMatchMore();	
	}
	//�����ͥ�������ҵ����������	
	function SetNewPolciyMatchMore(){		
		//
		pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>���������������Ե�...";			    
		DisplayPageStatus();
		//���ø���ǩ
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
 		$("#spanresultitem").attr("disabled", "disabled");
		//   
	 	//
	   	$.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
	       	{
	           	action: "setNewPolciyMatchMore" , //action���� 
	           	pid: selpolicy,			           	
	           	empid: empid                           
	       	},
	      	function(result) {   //�ص�����
	      		//�����������
     			HiddenPageStatus();
     			pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>��ѯ�������Ե�...";
     			//���ø���ǩ
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//ȡ��ҵ��������׼	
	    		GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode);			                      
	     	}
	   );
	  
	}
	//���㵥����ͥ���߳�Ա�ⷢ������
    function UpdatePolciyMatchOne(fmid){
    	//
		var pid = "";
		//
		pid= selpolicy;
		//
		pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>�����ⷢ���������Ե�...";			    
		DisplayPageStatus(); 
	    //
	    //���ø���ǩ
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
 		$("#spanresultitem").attr("disabled", "disabled");
		//   		
		$.post("page/policy/manage/PolicyManageServlet", //������ҳ���ַ
		    {
		        action: "updatePolciyMatchOne", //action����
		        empid: empid,
		        pid: pid,
		        fmid: fmid                          
		    },
		    function(result) {   //�ص�����	
		    	//
		    	//�����������
     			HiddenPageStatus(); 
     			pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>��ѯ�������Ե�...";
     			//���ø���ǩ
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//   
     			//ȡ��ҵ��������׼	
	    		GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode); 			      	                                         
		    }
		); 
    }
    //�������㵥����ͥ���߳�Ա�ⷢ������
    function UpdatePolciyMatchMore(){
    	//
		var pid = "";
		//
		pid= selpolicy;
		//
		pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>�����ⷢ���������Ե�...";			    
		DisplayPageStatus(); 
	    //
	    //���ø���ǩ
		$("#autolists").attr("disabled", "disabled");
		$("#checklists").attr("disabled", "disabled");
		$("#spancheckitem").attr("disabled", "disabled");
 		$("#spanresultitem").attr("disabled", "disabled");
		//   		
		$.post("page/policy/manage/PolicyManageServlet", //������ҳ���ַ
		    {
		        action: "updatePolciyMatchMore", //action����
		        empid: empid,
		        pid: pid                          
		    },
		    function(result) {   //�ص�����	
		    	//
		    	//�����������
     			HiddenPageStatus(); 
     			pagestatusdiv.innerHTML = "<img src=/db/page/images/loading.gif></img>��ѯ�������Ե�...";
     			//���ø���ǩ
				$("#autolists").attr("disabled", "");
				$("#checklists").attr("disabled", "");
				$("#spancheckitem").attr("disabled", "");
	  			$("#spanresultitem").attr("disabled", "");
				//   
     			//ȡ��ҵ��������׼	
	    		GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode); 			      	                                         
		    }
		); 
    }
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
		    	pardiv.innerHTML = "�����鿴:��ǰѡ��ҵ��["+result + "]��������";	
		    	//
		    	selpolicyname = result;	          						      	                                         
		    }
		);        
	}
	//ȡ�õ�ǰҳ����������
	function GetCheckPolicyOrder(pardivid,sname){
	  	//
	  	var pid = selpolicy;
	  	var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getCheckPolicyOrder", //action����
		        sname: sname,
		        empid: empid,
		        pid: pid
		    },
		    function(result) {   //�ص�����		    	
		      	pardiv.innerHTML = result;
		      	//�����¼�		      	
				$("#"+sname).change(function(){GetPhySql();});		      			      	                                         
		    }
		);        
	}
	//ȡ��ҵ��������׼
	function GetPolicyAutoUlLi(pardivid,sname,smode){
	  	//
	  	var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getPolicyAutoUlLi", //action����
		        sname: sname,
		        smode: smode   
		    },
		    function(result) {   //�ص�����		    	
		      	pardiv.innerHTML = result;
		      	//���ɱ�ǩ����¼�
				init(sname);
				//�����ͥ�������ҵ����������	
				SetNewPolciyMatchMore();				       		    		      							      		      						      	                                         
		    }
		);        
	}	
	//ȡ��ҵ��������׼
	function GetPolicyCheckUlLi(pardivid,sname,smode,sinfomode){
	  	//
	  	var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getPolicyCheckUlLi", //action����
		        sname: sname,
		        smode: smode,
		        sinfomode: sinfomode
		    },
		    function(result) {   //�ص�����		    	
		      	pardiv.innerHTML = result;
		      	//���ɱ�ǩ����¼�
				initcheck(sname);																			      	                                         
		    }
		);        
	}		
	//ȡ��������ǩ
	function GetPolicyInfoTab(mode){
	  	if(mode=="check"){//����
	  		$("#spancheckitem").css({"line-height": "2","font-size": "12px","color": "#000000","height": "20px"});
	  		$("#spanresultitem").css({"line-height": "2","font-size": "12px","color": "#6BA6FF","height": "20px"});
	  		//
	  		spancheckimg.innerHTML="<img src='/db/page/images/right.gif'/>";
	  		spanresultimg.innerHTML="";	
	  		//
	  		seltabmode = "spancheckitem";
	  		selautomode = "check";
	  		selcheckmode = "infocheck";
	  		//
	  		selautotabid="101"
	  		selchecktabid = "111";
	  		//
	  		  	
	  	}else if(mode=="result"){//���
	  		$("#spancheckitem").css({"line-height": "2","font-size": "12px","color": "#6BA6FF","height": "20px"});
	  		$("#spanresultitem").css({"line-height": "2","font-size": "12px","color": "#000000","height": "20px"});
	  		//
	  		spancheckimg.innerHTML="";
	  		spanresultimg.innerHTML="<img src='/db/page/images/right.gif'/>";
	  		//
	  		seltabmode = "spanresultitem";
	  		selautomode= "result";
	  		selcheckmode = "infocheck";	
	  		//
	  		selautotabid="200"
	  		selchecktabid = "211";
	  		//
	  				  		   		   		
	  	}
	  	//ȡ��ȫѡ
		var isallcb = document.getElementById("checkallb");
		if(isallcb==null){
			
		}else if(isallcb.checked== 1){
	    	isallcb.checked = false;  							    	
	    } 
	    //
	  	//ȡ��ҵ��������׼
		GetPolicyAutoUlLi("choicepolicyauto","autolists",selautomode);
		  				
	}
	//��ʾҳ��״̬div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2;  //�������
		//vtop= document.body.clientHeight/2; //�����߶�
		vtop= $("#spanpolicyitem").offset().top+28;      	
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
    	vleft = document.body.clientWidth/2;  //�������
		//vtop= document.body.clientHeight/2; //�����߶�
		vtop= $("#spanpolicyitem").offset().top+28;    	
    	//
    	$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
	    var resultstatusdiv = $("#resultstatusdiv");            //��ȡ��ʾ��Ϣdiv
	    resultstatusdiv.html(info);                       //����div���ı�		    
	    $(resultstatusdiv).fadeIn();                      //������Ϣ
	    setTimeout("HiddenResult()",2000);          //2���������Ϣ
	}		
	//������ʾ��Ϣdiv
	function HiddenResult() {
	    $("#resultstatusdiv").fadeOut();                  //������Ϣ
	}
	//		
	//��ʼ��ҳ��
    function IniPage(){	
    	//
    	vleft = document.body.clientWidth/2;  //�������
		//vtop= document.body.clientHeight/2; //�����߶�
		vtop= $("#spanpolicyitem").offset().top+28;     	
    	//
    	//��ǰ��¼�û����    
		empid = "<%=empno%>";     
		//��¼����    
		deptid = "<%=onno%>";    //��ǰ��¼���� 
		//
		//ѡ��ҵ���ѯ	
		selpolicy = "<%=reqpolicy%>";		 //ҵ��ѡ��			
		//
		selselect = "<%=reqselect%>";		 //��ѯѡ���ֶ�
		selfrom = "<%=reqfrom%>";		 //��ѯѡ���
		selwhere = "<%=reqwhere%>";		 //��ѯѡ������
		selorder = "<%=reqorder%>";		 //��ѯѡ������	
		//		
		//ҵ������
        GetPolicyName("spanpolicyitem");
        //
        //ȡ��ҵ������״̬��ʶ
		GetCheckPolicyFlagsXml();	        
		//       		    
    }
  </script>
  
  </head>
  <body onload = "IniPage()">	
    <div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>��ѯ�������Ե�...</div> 
  	<div id='resultstatusdiv'></div> 
  	<table width='100%' cellpadding='0' cellspacing='0' class='tab'>
  		<tr class = 'mybackground'>
  			<td width='70%'>  				
  				<span class = "pointer mystyle" id = "spanpolicyitem" onclick="CallStandardsDialog()"></span>
  			</td>  			
  			<td width = "84px" align="right"> 
  				<span id = "spancheckimg"></span> 				
  				<span class = "pointer mytabactive" id = "spancheckitem" onclick="GetPolicyInfoTab('check')">������������</span>
  			</td>  			
  			<td width = "84px" align="left">
  				<span id = "spanresultimg"></span>
  				<span class = "pointer mytabdefault" id = "spanresultitem" onclick="GetPolicyInfoTab('result')">����������</span>
  			</td>
	   	</tr>	   	   
	   	<tr>
	   		<td class = "myspace" colspan = "3"><div id = 'choicepolicyauto'></div></td>
	   	</tr>
	   	<tr>
	   		<td valign="top" class = "myspace" colspan = "3"><div id = 'choicepolicycheck'></div></td>
	   	</tr>
	   	<tr>
	   		<td valign="top" class = "myspace" colspan = "3"><div id='checkresultcon'> </div></td>
	   	</tr>
	   			   	  	
	</table>
	
  	<table width='100%' cellpadding='0' cellspacing='0'>  		
  		<tr class = "mystyle mybackground" >  
  			<td width = "122px" align="left" height='23'>
  				<div  align="left" id = "divboxgroup"></div>  				
  			</td>  			
  			<td height='23' align="center">
  				<span>
  					<input type="radio" name="rd" id="rdasc" value="rdasc" onclick='GetPhySql();' checked/>����
  					<input type="radio" name="rd" id="rddesc" value="rddesc" onclick='GetPhySql();' />����
  				</span> 
  				<span id = "spanorderby"></span>
  				<span id = "spanupdatemoneys"></span>		
  			</td>  			
  			<td width = "210px" height='23'>
  				<div align="right" id = "divbtngroup" ></div>
  			</td>
  		</tr>   				   	  	
	</table>
  </body>
</html>
<script  type="text/javascript">
	//
	function init(ids){
		document.getElementById(ids).getElementsByTagName('li')[0].className='active';			
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
			//2Ϊ����li-1ΪPolicyQueryServlet.java���ɱ�ǩ
			var tid,tname;
			tid =  obj.id;
			tname = obj.firstChild.nodeValue;
			tid = tid.substring(2,tid.length);	
			//
			selautotabid = tid;
		  	selautotabname = tname;	  
			//
			if(selautotabid=="101"){
				selcheckmode = "infocheck";				
		  		selchecktabid = "111";
			}else if(selautotabid=="102"){
				selcheckmode = "inforesult";
				selchecktabid = "121";
			}else if(selautotabid=="200"){				
				selcheckmode = "infocheck";
				selchecktabid = "211";
			}else if(selautotabid=="201"){
				selcheckmode = "inforesult";
				selchecktabid = "221";
			}else if(selautotabid=="202"){
				selcheckmode = "remcheck";
				selchecktabid = "231";
			} 
			//ȡ��ҵ��������׼
			GetPolicyCheckUlLi("choicepolicycheck","checklists",selautomode,selcheckmode);	
			//
			//	  		
		}
	}
	//
	function initcheck(ids){
		document.getElementById(ids).getElementsByTagName('li')[0].className='active';					
		document.getElementById(ids).onclick=function(){onmousOverCheck(ids);}//�������Ч��	
		//
		selbegpage = 0;		//��ҳ��ʼ
	 	selendpage = sqlpagenum;//��ҳ����
	 	sqlolepagecount = 0;//�ɷ�ҳѡ��������
	 	sqlselpage = 1;//��ʼ��һҳ		  	
	  	//����ת��[�߼�����ת����������]
    	GetPhySql();			
	}
	function onmousOverCheck(ids){		
		o = o || window.event;
		var obj=o.target || o.srcElement;
		if (obj.tagName=='LI'){
		  	if (obj.className=='active'){
		  		
		  	}else{
		  		var o=document.getElementById(ids).getElementsByTagName('li');
			  	for (var i=0;i<=o.length-1;i++){o[i].className='default'}
			  	obj.className='active';
		  	}		  	
		  	//
		  	//2Ϊ����li-1ΪPolicyQueryServlet.java���ɱ�ǩ
		  	var tid,tname;
		  	tid =  obj.id;
		  	tname = obj.firstChild.nodeValue;
		  	tid = tid.substring(2,tid.length);		  
		  	//
		  	selchecktabid = tid;
		  	selchecktabname = tname;
		  	//
			var isallcb = document.getElementById("checkallb");
			if(isallcb==null){
				
			}else if(isallcb.checked== 1){
		    	isallcb.checked = false;  							    	
		    } 
		    //
			selbegpage = 0;		//��ҳ��ʼ
		 	selendpage = sqlpagenum;//��ҳ����
		 	sqlolepagecount = 0;//�ɷ�ҳѡ��������
		 	sqlselpage = 1;//��ʼ��һҳ		  	
		  	//����ת��[�߼�����ת����������]
	    	GetPhySql();
		}
	}	
</script>
<script type="text/javascript">
	//
	//
	function ShowData(data){
		var xmlDoc;
		//ȡ����ͷ
		var html= "",fmid = "",fmno = "",fmname = "",fmolemoney = "0";fmmoney = "0";		
		//
		var sumfamily ="0",sumpopcount ="0",summoney = "0",sumolemoney = "0",sumnewmoney = "0";
		//
		var recheckflag = "0";
		//		
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
			//��ͥ��������
			sumfamily = sqlcount;
			//�����˿ڻ���		
			var sumpopcountarr=root.selectNodes("/data/sumpopcount/count");
			sumpopcount =sumpopcountarr.item(0).firstChild.data;			 
			//�����������
			var summoneyarr=root.selectNodes("/data/summoney/money");
			summoney =summoneyarr.item(0).firstChild.data;	
			//���ھ��������			
			var sumolemoneyarr=root.selectNodes("/data/sumolemoney/money");
			sumolemoney =sumolemoneyarr.item(0).firstChild.data;			
			//�ⷢ���������
			var sumnewmoneyarr=root.selectNodes("/data/sumnewmoney/money");
			sumnewmoney =sumnewmoneyarr.item(0).firstChild.data;
			//
			//���ҳ		
			checkresultcon.innerHTML="�޲�ѯ���";
			//�ް�ť����
			divbtngroup.innerHTML ="";
			//������߽��
			divboxgroup.innerHTML = ""; 
			//
			//
			//XML��������ͥ��.��ͥ���,��ͥ��.��ͥ����,��ͥ��.��������,������.�����˿�,������.��������,������.���ھ�����,������.�ⷢ������,������.������,��ͥ��.��������,��ͥ��.����
			//������
			var headarr=root.selectNodes("/data/ehead/cell");
			//
			//headarr.length+1��ʾ��ز�����
			colnum = headarr.length+1;
			//
			html += "<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";			
			//
			//������߽��
			if(seltabmode=="spanresultitem"){					
				
			}else if(seltabmode=="spancheckitem"){
				if(selchecktabid=="123"){//˳��
					//��������(ͨ���޸���Ϣ)	
				}else{
					html += "<td>����</td>";
				}
			}
			//
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				if(i==9){//����ID
					//����ID����
				}else{
					var temp ="<td height='23'>"+ss[1]+"</td>";
					html=html+temp;
				}				
			}
			html += "<td>��ز���</td>";
			html=html+"</tr>";
			//
			//ȡ����¼ֵ
			xmldata = root.selectNodes("/data/list/entity");
			//
			//
			var rows=root.selectNodes("/data/list/entity");						
			//
			rownum = rows.length;
			//��������ͥ��.��ͥ���,��ͥ��.��ͥ����,��ͥ��.��������,������.�����˿�,������.��������,������.���ھ�����,������.�ⷢ������,������.������,��ͥ��.��������,��ͥ��.����
			//���һ������������������(����XML��ͷ)
			//
			for(var i=0;i<rows.length;i++){
				var row = rows.item(i).childNodes;
				//
				fmid = row.item(0).firstChild.data;//��ͥID
				fmno = row.item(1).firstChild.data;//��ͥ���
				fmname = row.item(2).firstChild.data;//��ͥ��������				
				fmmoney = row.item(5).firstChild.data;//��ͥ�ⷢ������
				fmolemoney = row.item(6).firstChild.data;//��ͥ���ھ�����
				//row.length-3//���������
				recheckflag = row.item(10).firstChild.data;//��ͥ��������������������ʶ				
				//		
				var temp="<tr ";						
				//��˫��
				var mathrow = i%2;				
				if(mathrow == 1){//������ɫ
					temp +=" style = 'background: #F2F5F7;'>";	
				}else{
					temp +=">";	
				}			
					//����ֵ
					//������߽��
					//
					divbtngroup.innerHTML ="";
					divboxgroup.innerHTML = "";   
			  		//������
			  		//
			  		if(seltabmode=="spanresultitem"){//�����������	
						//ˢ��ȫ������
						var parspan = document.getElementById("spanupdatemoneys");
						parspan.innerHTML = "";
						//���ز�����
						//
						var temphtml = "<!--<button class = 'btn' onclick=\"ExportXls()\">����Excel</button>-->";						
						//
						if(flowflag=="1"){//������������
							if(pcheckflag=="1"){//�û���λ��������
								if(preportflag=="1"){//����ȷ����������
									if(endflowflag=="1"){//����������
										if(allaccflag=="0"){//ҵ��δ�������
											temphtml += "<button class = 'btn' onclick=\"CallAllAccDialog('edit')\">�������</button>";											
										}else{
											temphtml += "<button class = 'btn' onclick=\"CallAllAccDialog('add')\">�����������</button>";											
										}  
									}	
								}	
							}							
						}
						divbtngroup.innerHTML = temphtml;						
						divboxgroup.innerHTML = "";   
					}else if(seltabmode=="spancheckitem"){//������������
						//ˢ��ȫ������
						var parspan = document.getElementById("spanupdatemoneys");
						if(oneflowflag == "1"){//��һ������������ʶ
							parspan.innerHTML = "<button class = 'btn' onclick=\"UpdateMoneys()\">ˢ��ȫ������</button>";
						}else{
							parspan.innerHTML = "";
						}						
						//
						if(selchecktabid=="123"){//˳��
							//��������(ͨ���޸���Ϣ)							
						}else{						
							temp += "<td width='32px'height='23' class='colValue'>";
							//ҵ���Ƿ��������
							if(allaccflag=="0"){//ҵ��δ�������
								//ҵ���Ƿ���������
								if(checkmoreflag=="0"){//����������������
									//�û����Ƿ����ҵ����������
									if(flowflag=="0"){//��������������										
										temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
										//
										divbtngroup.innerHTML ="��ҵ����Ҫ���û���������";
										divboxgroup.innerHTML = "";										
									}else{//������������
										//�û���λ����Ȩ��
										if(pcheckflag=="0"){//�û���λ����������
											temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
											//
											divbtngroup.innerHTML ="���û���λû�и�ҵ������Ȩ��";
											divboxgroup.innerHTML = "";
										}else{//�û���λ��������											
											if(pcheckmoreflag=="0"){//�û���λ��������������
												temp += "<input type='radio' name='checkrd' onclick=\"CheckNow(this,'"+fmid+"')\" />";
											}else{//�û���λ������������
												temp += "<input type='checkbox' name='checkb' id='checkb"+i+"'/>";
												//
												var temphtml = "<input type='checkbox' id='checkpageb' onclick='CheckPage()' /><span>��ҳȫѡ</span>";
												temphtml += "<input type='checkbox' id='checkallb' onclick='CheckAll()' /><span>ȫѡ</span>";
												//													
												divbtngroup.innerHTML = "<button class = 'btn' onclick=\"CheckAllNow()\">��������</button>";
												divboxgroup.innerHTML = temphtml;
											}
										}										
									}									
								}else{//��������������
									//�û����Ƿ����ҵ����������
									if(flowflag=="0"){//��������������
										temp += "<input type='checkbox' name='checkb' id='checkb"+i+"' disabled = 'disabled' />";
										//
										divbtngroup.innerHTML ="��ҵ����Ҫ���û���������";
										divboxgroup.innerHTML = "";											
									}else{//������������
										//�û���λ����Ȩ��
										if(pcheckflag=="0"){//�û���λ����������
											temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
											//
											divbtngroup.innerHTML ="���û���λû�и�ҵ������Ȩ��";
											divboxgroup.innerHTML = "";
										}else{//�û���λ��������
											temp += "<input type='checkbox' name='checkb' id='checkb"+i+"'/>";
											//
											var temphtml = "<input type='checkbox' id='checkpageb' onclick='CheckPage()' /><span>��ҳȫѡ</span>";
											temphtml += "<input type='checkbox' id='checkallb' onclick='CheckAll()' /><span>ȫѡ</span>";
											//
											divbtngroup.innerHTML = "<button class = 'btn' onclick=\"CheckAllNow()\">��������</button>";
											divboxgroup.innerHTML = temphtml;
										}
																				
									}									
								}
							}else if(allaccflag=="1"){//ҵ���Ѿ��������
								//ҵ���Ƿ���������
								if(checkmoreflag=="0"){//����������������
									temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
								}else{//��������������
									temp += "<input type='checkbox' name='checkb' id='checkb"+i+"' disabled = 'disabled' />";
								}
								//
								divbtngroup.innerHTML ="��ҵ���Ѿ��������,������������";
								divboxgroup.innerHTML = "";   
						  		//
							}else if(allaccflag=="2"){//ҵ���ܰ���
								//ҵ���Ƿ���������
								if(checkmoreflag=="0"){//����������������
									temp += "<input type='radio' name='checkrd' disabled = 'disabled' />";
								}else{//��������������
									temp += "<input type='checkbox' name='checkb' id='checkb"+i+"' disabled = 'disabled' />";
								}
								//
								divbtngroup.innerHTML ="��ҵ��δ����,������������";
								divboxgroup.innerHTML = "";   
						  		//								
							}														
							temp += "</td>";
						}						
					}
					//��ʾ��									
					for (var j=1;j<row.length;j++){						
						if(j==1){//��ͥ�����
							var temp1="<td height='23' class='colValue'>"+
								"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
							temp=temp+temp1;
						}else if(j==7){//���������(7���������)														
							if(selchecktabid=="123" || selchecktabid=="212"){//˳��
								var temp1="<td height='23' class='colValue'><span>˳��</span></td>"
								temp=temp+temp1;
							}else{
								var temp1="<td height='23' class='colValue'>"+
									"<span class = 'pointer colTtems' onclick= \"CallCheckIdeaDialog('edit','"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
								temp=temp+temp1;
							}							
						}else if(j==8){//����ID������(8����ID��)	
						
						}else if(j==10){//�Ƿ���Ա�������������������ʶ��(10��ͥ��������������������ʶ��)	
						
						}else{
							var temp1="<td height='23' class='colValue'>"+
							row.item(j).firstChild.data+"</td>";
							temp=temp+temp1;
						}
					}
					//
					if(seltabmode=="spancheckitem"){
						temp += "<td width='64px' height='23' class='colValue'>";
							if(oneflowflag=="1"){//ҵ���һ������������ʶ
								temp += "<span  class = 'pointer'><img src='/db/page/images/checkfamily.png' alt= '�༭��ͥ' onclick= \"infoeditaction('"+fmid+"')\"/>&nbsp;</span>";										
							}
							temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '�߷õǼ�' onclick=\"CallInterviewDialog("+fmid+")\"/>&nbsp;</span>";
						temp += "</td>";
					}else if(seltabmode=="spanresultitem"){
						if(selchecktabid=="123" || selchecktabid=="212"){//˳��
							temp += "<td width='64px' height='23' class='colValue'>";
								temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '�߷õǼ�' onclick=\"CallInterviewDialog("+fmid+")\"/>&nbsp;</span>";
							temp += "</td>";
						}else{	
							temp += "<td width='64px' height='23' class='colValue'>";						
								if(recheckflag=="1"){//��ͥ�������Գ���������������ʶ
									if(preportflag=="1"){//����ȷ����������
										temp += "<span  class = 'pointer'><img src='/db/page/images/close.gif' alt= '��������' onclick=\"RemoveCheckIdea('"+empid+"','"+selpolicy+"','"+fmid+"')\"/></span>";
									}												
								}	
								temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '�߷õǼ�' onclick=\"CallInterviewDialog("+fmid+")\"/>&nbsp;</span>";
							temp += "</td>";						
						}	
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
				//
				sumfamily = "0";
				sumpopcount ="0";
				summoney = "0";
				sumolemoney = "0";
				sumnewmoney = "0";
				//
				html += "<table width='100%' cellpadding='0' cellspacing='0'>"
					html += "<tr>";	
					//
					if(flowflag=="0"){//��������������
						html += "<td height='25' class='colValue'>��ҵ����Ҫ���û���������</td>";
						//
						divbtngroup.innerHTML = "��ҵ����Ҫ���û���������";
						divboxgroup.innerHTML ="";	
					}else if(flowflag=="1"){//������������
						//
						if(allaccflag=="1"){//ҵ���Ѿ��������
							html += "<td height='25' class='colValue'>��ҵ���Ѿ��������,������������</td>";
							//
							if(seltabmode=="spanresultitem"){//�����������
								if(endflowflag=="1"){//����������
									var tempbtnhtml = "<button class = 'btn' onclick=\"CallAllAccDialog('add')\">�����������</button>";							
									divbtngroup.innerHTML = tempbtnhtml;
								}else{
									divbtngroup.innerHTML ="";
								}
							}else{
								divbtngroup.innerHTML ="��ҵ���Ѿ��������,������������";
							}
							divboxgroup.innerHTML = "";
						}else if(allaccflag=="2"){//ҵ���ܰ���
							html += "<td height='25' class='colValue'>��ҵ��δ����,������������</td>";
							//
							if(seltabmode=="spanresultitem"){//�����������
								if(endflowflag=="1"){//����������
									var tempbtnhtml = "<button class = 'btn' onclick=\"CallAllAccDialog('add')\">�����������</button>";						
									divbtngroup.innerHTML = tempbtnhtml;
								}else{
									divbtngroup.innerHTML ="";	
								}	
							}else{
								divbtngroup.innerHTML ="��ҵ��δ����,������������";	
							}					
							divboxgroup.innerHTML = ""; 
						}else{
							html += "<td height='25' class='colValue'>�޲�ѯ���</td>";
						} 
					}else{						
						html += "<td height='25' class='colValue'>�޲�ѯ���</td>";				
					}
					html += "</tr>";
				html += "</table>"; 
		  		//		  		
			}				
			//	
			html += "<table width='100%' cellpadding='0' cellspacing='0'>"
				//
				html += "<tr class='colField'>";
					html += "<td height='18' class='pageTtems' >";
		  				html += "<span>�ܼ�ͥ����[<span id = 'spansumfamily'></span>]</span>";		  				
		  			html += "</td>";
					html += "<td height='18' class='pageTtems' >";
		  				html += "<span>�ܱ����˿�[<span id = 'spansumpopcount'></span>]</span>";		  				
		  			html += "</td>";
		  			html += "<td height='18' class='pageTtems' >";
		  				html += "<span>�ܼ�������[<span id = 'spansummoney'></span>]</span>";
		  			html += "</td>";
		  			html += "<td height='18' class='pageTtems' >";
			   			html += "<span>�����ھ�����[<span id = 'spansumolemoney'></span>]</span>";
		  			html += "</td>";
		  			html += "<td height='18' class='pageTtems' >";
			   			html += "<span>���ⷢ������[<span id = 'spansumnewmoney'></span>]</span>";
		  			html += "</td>";
				html += "</tr>";
				html += "<tr class='colField'>";									
					html += "<td colspan = '5' height='23'>";
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
							//ѡ��ҳ����ѡ���
							html += "<span id = \"spanpages\"></span> ";
						html += "  ÿҳ<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>����¼";
					html += "</td>";
				html += "</tr>";
			html += "</table>";
			//			
			//���ҳ
			checkresultcon.innerHTML=html;
			//			
			spansumfamily.innerHTML=sumfamily;			
			spansumpopcount.innerHTML=sumpopcount;
			spansummoney.innerHTML=summoney;
			spansumolemoney.innerHTML=sumolemoney;
			spansumnewmoney.innerHTML=sumnewmoney;
			//
			//

			spanpages.innerHTML=GetPageGo();
			//	
			//ѡ��ҳ
			$("#selectpage").val(sqlselpage);
			//ÿҳ��ʾ����
			$("#divpagerow").val(sqlpagenum);
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
			//			
			//���ҳ		
			checkresultcon.innerHTML="�޲�ѯ���";
			//�ް�ť����
			divbtngroup.innerHTML ="";
			//������߽��
			divboxgroup.innerHTML = ""; 
			//
	
		}		
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
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();		
	}
	//ѡ��ҳ��ѯ
	function ExePageFlag(pageflag){		
		//
		var flag = "1";//��ҳ��ʶ
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//��ҳ
			if(sqlselpage==1){
				//	    
				DisplayResult("�Ѿ�����ҳ!!!"); 		   
			    //   
				//alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//��ҳ
			if(sqlselpage==1){
				//		    
				DisplayResult("�Ѿ�����ҳ!!!"); 		   
			    //  
				//alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//��ҳ
			if(sqlselpage==sqlpagecount){
				//
				DisplayResult("�Ѿ���βҳ!!!"); 		   
			    // 
				//alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//ҳβ
			if(sqlselpage==sqlpagecount){
				//
				DisplayResult("�Ѿ���βҳ!!!"); 		   
			    // 
				//alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlpagecount;			
		}
		
		//�����ҳ����
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;		
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();		
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
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
	}
</script>
<script type="text/javascript">
	//ҳȫ��ѡ��
	function CheckPage(){
		//ҳȫ��ѡ��
		var ispagecb,iscb,isallcb;
		//
		//����������
		selsql = "";
		//
		isallcb = document.getElementById("checkallb");
		if(isallcb!=null){
			checkallb.checked = false;
		}
		//
		ispagecb = document.getElementById("checkpageb");
		if(ispagecb.checked==0){
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				if(iscb.disabled==1){					
					//�Ѿ�ѡ��
				}else{
					iscb.checked = 0;
				}				
			}			
		}else if(ispagecb.checked==1){			
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				if(iscb.disabled==1){					
					//�Ѿ�ѡ��
				}else{
					iscb.checked = 1;
				}					
			}								
		}		
	}
	//ȫ��ѡ��
	function CheckAll(){
		//ȫ��ѡ��
		var isallcb;
		//
		checkpageb.checked = false;
		//
		isallcb = document.getElementById("checkallb");
		if(isallcb.checked==1){	
			//ȡ�������ѯ�����ִ�з���XML��ʽ		  
			GetPhySql();
			//
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				iscb.checked = 1;					
			}
		}else{
			//����������
			selsql = "";
			//
			for(var i=0;i<rownum;i++){
				iscb = document.getElementById("checkb"+i);
				iscb.checked = 0;					
			}
		}	
	}
	//������������
	function CheckNow(src,fmid){
		//��������
		if(src.checked==1){
			//������ͥID
			selallfmid = fmid;				
			//�������������			
	 		CallCheckIdeaDialog("add",selallfmid);			
		}
	}
	//ѡ����������
	function CheckAllNow(){
		//��������
		var iscb,fmrow,fmid,fmno,fmname,fmolemoney,fmmoney;
		
		//��ֹѡ��
		selallfmid = "";
		//
		for(var i=0;i<rownum;i++){
			iscb = document.getElementById("checkb"+i);							
			if(iscb.checked == 1){				
				if(iscb.disabled==1){					
					//�Ѿ�����
				}else{												
					//���ѡ���ͥID
					fmrow = xmldata.item(i).childNodes;						
					fmid = fmrow.item(0).firstChild.data;//��һ��Ϊ��ͥID
					fmno = fmrow.item(1).firstChild.data;//��ͥ���
					fmname = fmrow.item(2).firstChild.data;//��ͥ��������
					fmolemoney = fmrow.item(5).firstChild.data;//��ͥ���ھ�����
					fmmoney = fmrow.item(6).firstChild.data;//��ͥ�ⷢ������
					//
					if(selallfmid=="" || selallfmid == null ){
						selallfmid = fmid;
					}else{
						selallfmid +=  "," + fmid;
					}	
				}	
			}				
		}		
		//
		//�������������		
		CallCheckIdeaDialog("add",selallfmid);					
	}
</script>
<script type="text/javascript">	
	//�������������
	function CallCheckIdeaDialog(mode,fmid)
	{
		var pid,isallcb,mess,smode;
		//
		pid= selpolicy;
		smode = "edit";
		//
		if(mode=="add"){
			//ȫѡ
			isallcb = document.getElementById("checkallb");
			if(isallcb==null){
		    	if(pid=="" || fmid ==""){		
			    	DisplayResult("û��ѡ����ؼ�ͥ!");	
					return;	
				}
				mess = "";
		    	selsql="";
				smode = "add";
			}else if(isallcb.checked== 1){
				//
				if(oneflowflag=="1"){//��һ������������ʶ
					if(checkmoneyflag=="1"){//�û����޸ľ������ʶ
						DisplayResult("������д������,������ȫ������!");	
						return;	
		    		}
				}
		    	if(selsql==""){
		    		DisplayResult("û��ѡ����ؼ�ͥ!");	
					return;	
		    	}
		    	mess = "[ȫѡ]";
		    	smode = "all";		    							    	
		    }else{
		    	if(pid=="" || fmid ==""){		
			    	DisplayResult("û��ѡ����ؼ�ͥ!");	
					return;	
				}
				mess = "";
		    	selsql="";
				smode = "add";
		    }
		   
		}
		//�����ַ�ת������(�Ա�ҳ�����)
		selsql = encodeURIComponent(selsql);		
		//
		var bWidth = document.body.clientWidth-30;  //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bHeight= 400; //�����߶�
		var sArgu = "qmode="+smode+"&qpid="+selpolicy+"&qfmid="+fmid+"";
		sArgu += "&qselsql="+selsql+"&qcheckmode="+selchecktabid;		
		if(acctypeflag=="1"){		
			var oUrl = "/db/page/policy/manage/policycheckideamember.jsp?"+sArgu+"";
			jBox.open("iframe-jBoxID","iframe",oUrl,"��Ա���������д����&GetPhySql()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}else{
			var oUrl = "/db/page/policy/manage/policycheckideafamily.jsp?"+sArgu+"";
			jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ���������д����&GetPhySql()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
		//����������
		selsql = "";			
	}
	//��ҵ�񵵴α�׼����
	function CallStandardsDialog()
	{
		if(selpolicy=="-1"){//[��]ѡ��ҵ��		  		
	  		return;		  		
	  	}
		var bWidth = document.body.clientWidth-30;   //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bHeight= 400; //�����߶�
		var sArgu = "qpid="+selpolicy+"";
		var oUrl = "/db/page/policy/manage/policystandards.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ�񵵴α�׼��ϸ","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		
	}
	//���߷ü�¼��ѯҳ��
	function CallInterviewDialog(fmid){		
		var afrom = "",awhere = "",aorder = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var sArgu = "qfrom="+afrom+"&qwhere="+awhere+"&qorder="+aorder+"";
		var oUrl = "/db/page/policy/manage/policyinterview.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ�߷ü�¼�Ǽ�","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}	
	//�򿪼�ͥ�鿴ҳ��
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣ��Ƭ","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//	
	//�򿪼�ͥ�༭ҳ��
	function infoeditaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣά��&AfterInfoEditAction("+afmid+")","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//��������㴰��
	function CallAllAccDialog(mode)
	{
		if(selpolicy=="-1"){//[��]ѡ��ҵ��		  		
	  		return;		  		
	  	}
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var sArgu = "qpid="+selpolicy+"&qpname="+selpolicyname+"&qmode="+mode+"";
		var oUrl = "/db/page/policy/manage/policyaccmanage.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ������������&GetPhySql()","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//�򿪱༭��ͥ��Ϣ֮��ִ�д�����
	function AfterInfoEditAction(fmid)
	{
		//���㵥����ͥ���߳�Ա�ⷢ������
    	UpdatePolciyMatchOne(fmid);
	}
	//		
</script>
<script type="text/javascript">
	//ȡ��ҵ������״̬��ʶ
	function GetCheckPolicyFlagsXml(){
	  	//
	    $.post("/db/page/policy/manage/policycheckidea.do",      //������ҳ���ַ
	        {
	            action: "getCheckPolicyFlagsXml", //action����
	            pid: selpolicy, //����
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
		//ȡ��ҵ������״̬��ʼ����
		//ȡ��ҳ������������
		GetCheckPolicyOrder("spanorderby","orderbylist");
		//
	    spancheckimg.innerHTML="<img src='/db/page/images/right.gif'/>";	     
        //ȡ��ҵ��������׼
		GetPolicyAutoUlLi("choicepolicyauto","autolists",selautomode);	
	}
</script>	
<script type="text/javascript">
	//�򿪵�������
	function ExportXls(){
		//�������ݱ�ʶ(0��1��������)
		selexport = "1";
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
	}
	//��ȡxmlth��sql��;�Ÿ���
	function ExportXlsOpen(data){
		//
		if(data=="1"){
 			window.open("<%=path%>/page/system/exportfile/exportExcel.do","","height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
 		}
		//�������ݱ�ʶ(0��1��������)
		selexport = "0";
	}	
</script>
