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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>���ౣ�Ϲ���</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>	
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	
    <style type="text/css">	
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
	</style>
	<script type="text/javascript">	
		//
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼����
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		selid = "";//ѡ��ID
		selname = "";//ѡ������
		selmoneytype = "";//ѡ�к������÷�ʽ
		//
		selsqlid = "";//ѡ�б�׼ID
		selsqlname = "";//ѡ�б�׼����
		//
		//ȡ�÷���ʩ���б�
		function GetPolicyChildsHtml() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyChildsHtml"             //action����
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	policychildlists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>����ʩ������</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	policychilditems.innerHTML = html;
			    	
			    	//JS�������
					new TableSorter("liststb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					//				    					      		
		        }
		    );
		}
		//ȡ�÷���ʩ������
		function GetPolicyChildItemHtml(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyChildItemHtml",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	policychilditems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//���·���ʩ��״̬
		function UpdatePoilcyChildStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "�Ƿ�ȷ��ͣ�ø÷���ʩ��";
			}else{
				mess = "�Ƿ�ȷ�����ø÷���ʩ��";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "updatePoilcyChildStatus",             //action����
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);	
					//
					//ȡ�÷���ʩ���б�
					GetPolicyChildsHtml();						  		
		        }
		    );
		}
		//�������
		function SavePolicyChild(mode){
			var cpid,cpropid,cname,cdesc,csqltype,cmoneytype,cstatus;
			var mess = "";
			//
			if(mode=="add"){//����
				mess = "�Ƿ�ȷ��������������ʩ��";
			}else if(mode=="edit"){//�༭
				if(selid==""){
					mess = "û��ѡ�з���ʩ��,�޷�����";
					return;
				}
				mess = "�Ƿ�ȷ��������·���ʩ��";
			}
			//
    		//������ֵ
	    	cname = $("#cname").val();
	    	cpropid = $("#policyselect").val();//ҵ������������
	    	cdesc = $("#cdesc").val();	    	
	    	//ѡ���ͥ���߳�Ա
		    var isrdf = document.getElementById("rdf");
		    var isrdm = document.getElementById("rdm");
		    if(isrdf.checked == true){	    		
	      	 	csqltype = "1";             
	      	}else if(isrdm.checked == true){
	      		csqltype = "2";   
	      	}
	      	//���㷽ʽ1�ֵ���2�ٷֱ�3��Χֵ
		    cmoneytype = "1";   
	      	//
    		if(cname==""){
    			//��ʾ��ʾ��Ϣdiv
				DisplayResult("û����д����!");
				return;
    		}		
			//
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "savePolicyChild",             //action����
		            mode: mode,
		            cpid: selid,
		            cname: cname,
		            cpropid: cpropid,
		            cdesc: cdesc,
		            csqltype: csqltype,
		            cmoneytype: cmoneytype       
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);
					//
					//ȡ�÷���ʩ���б�
					GetPolicyChildsHtml();				  		
		        }
		    );
		}
		//���²�ҳ�����
		function GoMatchPage(mode){
			if(mode=="next"){
				$("#policychildtr").css({"display":"none"});
				$("#policychildsqltr").css({"display":"block"});
				//ȡ�÷���ʩ���б�
				GetPolicyChildSqlsHtml();
				//
			}else if(mode=="back"){
				$("#policychildtr").css({"display":"block"});
				$("#policychildsqltr").css({"display":"none"});
			}
		}
				
		//ȡ�÷���ʩ����׼�б�
		function GetPolicyChildSqlsHtml() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyChildSqlsHtml",             //action����
		            sid: selid,
		            sname: selname
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	policychildsqllists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>����ʩ����׼����</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	policychildsqlitems.innerHTML = html;
			    	
			    	//JS�������
					new TableSorter("liststb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					//				    					      		
		        }
		    );
		}
		//ȡ�÷���ʩ����׼����
		function GetPolicyChildSqlItemHtml(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyChildSqlItemHtml",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	policychildsqlitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//���·���ʩ����׼״̬
		function UpdatePoilcyChildSqlStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "�Ƿ�ȷ��ͣ�ø÷���ʩ����׼";
			}else{
				mess = "�Ƿ�ȷ�����ø÷���ʩ����׼";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "updatePoilcyChildSqlStatus",             //action����
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);	
					//
					//ȡ�÷���ʩ����׼�б�
					GetPolicyChildSqlsHtml(selid);						  		
		        }
		    );
		}
		//�������
		function SavePolicyChildSql(mode){
			var vsid,vprosid,vname,vdesc,vstatus;
			var mess = "";
			//
			if(mode=="add"){//����
				mess = "�Ƿ�ȷ��������������ʩ����׼";
			}else if(mode=="edit"){//�༭
				if(selsqlid==""){
					mess = "û��ѡ�з���ʩ����׼,�޷�����";
					return;
				}
				mess = "�Ƿ�ȷ��������·���ʩ����׼";
			}
			//
    		//������ֵ
	    	vname = $("#vname").val();
	    	vdesc = $("#vdesc").val();	    	
	    	
    		if(vname==""){
    			//��ʾ��ʾ��Ϣdiv
				DisplayResult("û����д��׼����!");
				return;
    		}		
			//
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "savePolicyChildSql",             //action����
		            mode: mode,
		            vsid: selsqlid,
		            vname: vname,
		            vprosid: selid,
		            vdesc: vdesc      
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);
					//
					//ȡ�÷���ʩ����׼�б�
					GetPolicyChildSqlsHtml(selid);				  		
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
			//��ǰ��¼�û����    
			empid = "<%=empno%>";     
			//��¼����    
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			//
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			//ȡ�÷���ʩ���б�
			GetPolicyChildsHtml();			
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">  		
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center" id = "policychildtr">
	    		<td valign="top" width="50%"><div id = 'policychildlists'></div></td>	    		
	    		<td valign="top"><div id = 'policychilditems'></div></td>
	    	</tr>
	    	<tr align="center" id = "policychildsqltr">
	    		<td valign="top" width="50%"><div id = 'policychildsqllists'></div></td>	    		
	    		<td valign="top"><div id = 'policychildsqlitems'></div></td>
	    	</tr>
    	</table>  
  </body>
</html>
<script type='text/javascript'>
	//��������
	function ChioceDo(src,id,name,type){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		selid = id;
		selname = name;
		selmoneytype = type;
		//
		//ȡ�÷���ʩ������
		GetPolicyChildItemHtml(selid);
	}
	//��������
	function ChioceDoSql(src,id,name){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		selsqlid = id;
		selsqlname = name;
		//
		//ȡ�÷���ʩ����׼����
		GetPolicyChildSqlItemHtml(selsqlid);
	}
	//�򿪷��ౣ�ϱ�׼����ҳ��
	function sqlaction(){
		var qmode = ""; 
		//
		if(selid==""){
			DisplayResult("û��ѡ����ౣ��,�޷���������!");
			return; 
		}			
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		//ѡ���ͥ���߳�Ա
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    if(isrdf.checked == true){	    		
      	 	qmode = "family";             
      	}else if(isrdm.checked == true){
      		qmode = "member";    
      	}
		var sArgu = "qid="+selsqlid+"&qname="+selsqlname+"&qmode="+qmode+"";		
		var oUrl = "/db/page/policy/manage/policymanagechildsql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"���ౣ�ϱ�׼����","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
