<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	//�����ֵ�ID
	String reqid = request.getParameter("qid");		
	if (reqid == null) {
		//Ĭ��Ϊ��
		reqid = "";    //��
	}	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�ֵ����Թ���</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
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
		var discid = "";//ID
		//
		var discchildid = "";//�ֵ�����ID
		//
		//ȡ���ֵ������б�
		function GetDiscsHtml() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //������ҳ���ַ
		        {
		            action: "getDiscsHtml",             //action����
		            discid: discid
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	disclists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>�ֵ�����</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	discitems.innerHTML = html;
			    	
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
					//ȡ���ֵ�����
					GetDiscItemHtml(discchildid);				    					      		
		        }
		    );
		}
		//ȡ���ֵ�����
		function GetDiscItemHtml(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //������ҳ���ַ
		        {
		            action: "getDiscItemHtml",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	discitems.innerHTML = result;
			    	$("#dname").get()[0].focus(); 	       		
		        }
		    );
		}
		//�����ֵ�����
		function SaveDisc(mode){
			//�༭״̬
			var EditType = mode;
			
			//����			
			var pdid = "",did = "",dname = "";
			
			//
			pdid = discid;	
		    //		    		    
		    dname = $("#dname").val();
		    //Ϊ��У��
		    if(dname==""){		     
			  DisplayResult("���Ʋ���Ϊ��!"); //��ʾ�������
		      return;
		    }		    
		    //
		    if(EditType=="adddisc"){		    	
		    	did = "";					   //��ȡ���	
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ�������ֵ����ԣ�")) {
			        return;
			    }			    
		    }else if(EditType=="editdisc"){
		    	did = $("#did").val();
			    if(did==""){			      
				  DisplayResult("û��ѡ���ֵ�����,�޷�����!"); //��ʾ�������
			      return;
			    }
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+dname+"]���ԣ�")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }		    
		    //
		  	DisplayPageStatus();
		  	//
		    $.post("page/info/search/TableInfoServlet", //������Ŀ��ҳ��
		        {
		            action: "updateDisc",                  //action����
		            EditType: EditType,				 //����
		            did: did,                      //�б�id����
		            dname: dname,				 //����
		            pdid: pdid			            
		        },
		        function(result) {                      //�ص�����
		        	HiddenPageStatus();
		            DisplayResult(result);              //��ʾ�������
		            //ȡ���ֵ������б�
					GetDiscsHtml(); 
		        }
		    );
		}
		//���±�׼״̬
		function updateDiscStatus(Id,Name,Status) {
		    //
			var smse = "";
			if(Status=="0"){
				smse = "ͣ��";
			}else{
				smse = "����";
			}		    
		    if (Id != "") {		    	
		    	//ͣ��ǰȷ��
			    if (!confirm("�Ƿ�ȷ��"+smse+"["+Name+"]���ֵ�״̬��")) {
			        return;
			    }
			    //
			  	DisplayPageStatus();
			  	//
			    $.post("page/info/search/TableInfoServlet",//������Ŀ��ҳ��
			        {
			            action: "updateDiscStatus",//action����
			            did: Id,                      //�б�id����
			            status: Status				 //����
			        },
			        function(result) {                      //�ص�����
			        	HiddenPageStatus();	 			            
			            DisplayResult(result);              //��ʾ�������
			            //ȡ���ֵ������б�
						GetDiscsHtml();			            			            		            
			        }
			    );
		    } else {		        
		        DisplayResult("û��ѡ���ֵ�,�޷�����!"); //��ʾ�������		       
		    }
		}
		//��������
		function ChioceDo(src,id){
			//
			var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "img"){
					imgs[i].src="/db/page/images/check1.jpg";
				}
			}
			src.src="/db/page/images/check2.jpg";
			discchildid = id;
			//
			//ȡ���ֵ�����
			GetDiscItemHtml(discchildid);	
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
			//�ֵ�ID    
			discid = "<%=reqid%>"; 
			//
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			//ȡ���ֵ������б�
			GetDiscsHtml();			
		}		
	</script>	
  </head>
  
  <body onLoad="IniPage()">
    <div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  	<div id='resultstatusdiv'></div>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
    	<tr align="center" id = "disctr">
    		<td valign="top" width="50%"><div id = 'disclists'></div></td>	    		
    		<td valign="top"><div id = 'discitems'></div></td>
    	</tr>    		
   	</table>
  </body>
</html>
