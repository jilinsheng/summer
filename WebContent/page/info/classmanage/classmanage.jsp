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
    
    <title>������Ϣ�������</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page"><meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
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
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//	
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";       //��ǰ��¼����
		//
		var selid = "";	//ѡ��ID
		var selname = "";//ѡ������
		var selfid = "";//ѡ�з����ֶ�ID
		
		//��ȡ�����б�
		function GetClasssHtml() {
			var mode;			
			if (rdf.checked == true){
				//��ͥ
				mode = "family";			
							
			}else{
				//��Ա
				mode = "member";	
			}
			//��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
		        {
		            action: "getClasssHtml",                  //action����
		            mode: mode				   //����                                          
		        },
		        function(result) {   //�ص�����	
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
					classlists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>��������</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	classitems.innerHTML = html;
			    	
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
		//ȡ�÷�������
		function GetClassItemHtml(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //������ҳ���ַ
		        {
		            action: "getClassItemHtml",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	classitems.innerHTML = result;			    	 	       		
		        }
		    );
		}
		//ͣ�÷���
		function delClass(classId,classFId){
		    //ɾ��ǰȷ��		    
		    if (!confirm("ȷ��ͣ�ø÷��ࣿ")) {
		        return;
		    }
		    //��ʾҳ��״̬div
			DisplayPageStatus();	
			//	    
		    $.post("page/info/search/TableInfoServlet",                   //������Ŀ��ҳ��
		        {
		            action: "delClassType",                  //action����
		            classId: classId,                      //id����
		            classFId: classFId
		        },
		        function(result) {                      //�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
		        	DisplayResult(result);
		        	//��ȡ�����б�
					GetClasssHtml();
									 
		        }
		    );
		}
		//���÷���
		function reeditClass(classId,classFId){
		    //ɾ��ǰȷ��
		    if (!confirm("ȷ�����ø÷��ࣿ")) {
		        return;
		    }	
		    //��ʾҳ��״̬div
			DisplayPageStatus();	
			//    
		    $.post("page/info/search/TableInfoServlet",                   //������Ŀ��ҳ��
		        {
		            action: "reeditClass",                  //action����
		            classId: classId,                      //id����
		            classFId: classFId
		        },
		        function(result) {                      //�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
		        	DisplayResult(result);
		        	//��ȡ�����б�
					GetClasssHtml();
		        }
		    );
		}		
		//��������
		function SaveClass(mode){
			var classType,classId,classFId,className,classDesc;			
			if (rdf.checked == true){
				//��ͥ
				classType = "family";
			}else{
				//��Ա
				classType = "member";
			}
			className = $("#cname").val();
			classDesc = $("#cdesc").val();	
			//
			if (className.length<=0){return;}	
			//
			if(mode=="addclass"){
				classId = "";			//��ȡ��� 
				classFId = "";
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ�������·��ࣿ")) {
			        return;
			    }	
			}else if(mode=="editclass"){
				classId = selid;     //��ȡ���	
				classFId = selfid;
			    if(classId==""){			     
				  DisplayResult("û��ѡ�����,�޷�����!"); //��ʾ�������
			      return;
			    }
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ�����·������ԣ�")) {
			        return;
			    }	
			}	
		   	//��ʾҳ��״̬div
			DisplayPageStatus();
			//    
		    $.post("page/info/search/TableInfoServlet",                   //������Ŀ��ҳ��
		        {
		            action: "updateClass",                  //action����
		            mode: mode,
		            classId: classId,
		            classFId: classFId,
		            classType: classType,
		            className: className,                      //����
		            classDesc: classDesc		            
		        },
		        function(result) {                      //�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
		        	DisplayResult(result);
		        	//��ȡ�����б�
					GetClasssHtml();  
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
			//��ȡ�����б�
			GetClasssHtml();		
		}		
	</script>
  </head>
  
  <body  onLoad="IniPage()">
    <div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  	<div id='resultstatusdiv'></div>
  	<div align="center" style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>
        <input type="radio" name="rdgresult" id="rdf" value="rdf" onclick='GetClasssHtml()' checked>��ͥ����</input>	             	
	    <input type="radio" name="rdgresult" id="rdm" value="rdm" onclick='GetClasssHtml()'>��Ա����</input>
    </div>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
    	<tr align="center" id = "classtr">
    		<td valign="top" width="50%"><div id = 'classlists'></div></td>	    		
    		<td valign="top"><div id = 'classitems'></div></td>
    	</tr>
    	  		
   	</table> 
  </body>
</html>
<script type="text/javascript">	
	//��������
	function ChioceDo(src,id,name,fid){
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
		selfid = fid;
		//
		//ȡ��ҵ������
		GetClassItemHtml(selid);
	}
	//ˢ��ϵͳ����
	function refsysinfo(){
		//ǰȷ��		    
	    if (!confirm("ȷ��ˢ��ϵͳ���ݣ�")) {
	        return;
	    }	
	    //
	    DisplayPageStatus();
	    //	    
	    $.post("/db/page/common/Log4jServlet",        //������Ŀ��ҳ��
	        {
	            action: "action"                  //action����
	        },
	        function(result) {                      //�ص�����
		        //�����������
	      		HiddenPageStatus();
	      		//
	        	DisplayResult("ˢ��ϵͳ�������");									 
	        }
	    );
	}
	//�򿪷�����������ҳ��
	function sqlaction(){
		var qmode = ""; 
		//
		if(selid==""){
			DisplayResult("û��ѡ�����,�޷���������!");
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
		var sArgu = "qid="+selid+"&qname="+selname+"&qmode="+qmode+"";		
		var oUrl = "/db/page/info/classmanage/classmanagesql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"������������","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//	
</script>
