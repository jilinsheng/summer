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
    
    <title>��Ϣ�Ϸ���֤</title>
    
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
	
	<script type="text/javascript" src="/db/page/js/seqfun.js"></script>
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
		selid = "";//ѡ��ID
		selname = "";//ѡ������
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		var selselect = "";		//ѡ���ѯ����ֶ�
		var selfrom = "";		//ѡ���ѯ�����
		var selwhere = "";		//ѡ���ѯ�������
		
		//
		var typeselectdiv = "";//����������
		
		
		//ȡ����Ϣ�Ϸ���֤�б�
		function GetInfoValidates() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "getInfoValidates"             //action����         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//��Ϣ�Ϸ���֤�б�
					ShowDate(result);        		
		        }
		    );
		}
		//ȡ����Ϣ�Ϸ���֤����
		function getInfoValidateItems(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "getInfoValidateItems",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//��Ϣ�Ϸ���֤����
					ShowDateItems(result,"edit");        		
		        }
		    );
		}
		//������Ϣ�Ϸ���֤״̬
		function UpdateValidateStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "�Ƿ�ȷ��ͣ�ø���֤";
			}else{
				mess = "�Ƿ�ȷ�����ø���֤";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "updateValidateStatus",             //action����
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);	
					//
					//
					//ȡ����Ϣ�Ϸ���֤�б�
					GetInfoValidates(); 					  		
		        }
		    );
		}
		//�������
		function SaveValidate(mode){
			var sname = "",sdesc = "",stype = "";
			var mess = "";
			//
			if(mode=="add"){//����
				mess = "�Ƿ�ȷ������������Ϣ��֤";
			}else if(mode=="edit"){//�༭
				if(selid==""){
					mess = "û��ѡ����Ϣ��֤,�޷�����";
					return;
				}
				mess = "�Ƿ�ȷ�����������Ϣ��֤";
			}
			//
			sname = $("#vname").val();	
    		sdesc = $("#vdesc").val();
    		stype = $("#typeselect").val();//����������
    		if(sname==""){
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
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "updateValidate",             //action����
		            mode: mode,
		            sid: selid,
		            sname: sname,
		            sdesc: sdesc,
		            stype: stype        
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);
					//
					//ȡ����Ϣ�Ϸ���֤�б�
					GetInfoValidates();					  		
		        }
		    );
		}
		//ȡ���ֵ�������ѡ��
		function GetDiscionarySelect(sname,sdiscid) {
		    //			
		    $.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "getDiscionarySelect", //action����
		            sname: sname, //����
		            sdiscid: sdiscid //����	            	            
		        },
		        function(result) {   //�ص�����
		        	typeselectdiv = result;	        		        	        	   	
		        }
		    );    		
		}		
		//��ʾҳ��״̬div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/1.5;  //�������	    	
			//vtop= document.body.clientHeight/2-20; //�����߶� 
			vtop = "80"; 
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
	    	vleft = document.body.clientWidth/1.5;  //�������
			//vtop= document.body.clientHeight/2; //�����߶� 
			vtop = "100"; 	
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
			//ȡ����Ϣ�Ϸ���֤�б�
			GetInfoValidates();
			//ȡ���ֵ�������ѡ��
			GetDiscionarySelect("typeselect","350");//����
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div> 
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top" width="40%"><div id = 'validatelists'></div></td>	    		
	    		<td valign="top"><div id = 'validateitems'></div></td>	
	    	</tr>	    	
    	</table>
  		
  </body>
</html>
<script type="text/javascript">		
	//��Ϣ�Ϸ���֤�б�
	function ShowDate(sdate){		
		var html,temp;
		var vid,vname,vdesc,vtype,vphysql,vlocsql,vstatus;
    	//
    	html = "<fieldset  class='list'>";
			html += "<legend  class='legend'>��Ϣ�Ϸ���֤����</legend>";	  			
    	html += "</fieldset>"; 
    	//����
    	validateitems.innerHTML = html; 
    	//
    	html = "<fieldset  class='list'>";
			html += "<legend  class='legend'>��Ϣ�Ϸ���֤�б�</legend>";
			html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";
				temp ="<td height='23'>����</td>";
				html +=temp;
				temp ="<td height='23'>��֤����</td>";
				html +=temp;
				temp ="<td height='23'>״̬</td>";
				html +=temp;
			html +="</tr>";	
			//���ת����JSONArray	        	
	   		var json = eval(sdate);
			//         
	        $(json).each(function(i) {      //�����������	
	        	//
	        	vid = json[i].vid;
	        	vname = json[i].vname;
	        	vdesc = json[i].vdesc;
	        	vtype = json[i].vtype;
	        	vphysql = json[i].vphysql;
	        	vlocsql = json[i].vlocsql;
	        	vstatus = json[i].vstatus; 	
	        	//
				html +="<tr>";		
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+vid+"','"+vname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+vstatus+"'>"+vname+"</td>";
					if(vstatus=="1"){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdateValidateStatus('"+vid+"','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdateValidateStatus('"+vid+"','1')\">����</button></td>";	
					}
							
		    	html +="</tr>";
	        });		
	    	//
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"ShowDateItems('','add')\">������Ϣ�Ϸ���֤</button>";    	
    	//�б�
    	validatelists.innerHTML = html;
    	//
		//JS�������
		new TableSorter("liststb");	
		/*
		new TableSorter("tb2", 0, 2 , 5, 6);
		new TableSorter("tb3").OnSorted = function(c, t)
		{
			alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
		}
		*/	
	}
	//��Ϣ�Ϸ���֤����
	function ShowDateItems(sdate,mode){		
		var html,temp;
		var vid,vname,vdesc,vtype,vphysql,vlocsql,vstatus;
    	//    	 
    	if(mode=="add"){//����    		
    		selid = "";
    		selname = "";
    		var imgs =  document.getElementsByTagName("img");		
			for(var i=0; i<imgs.length;i++){			
				if(imgs[i].id == "img"){
					imgs[i].src="/db/page/images/check1.jpg";
				}
			}
    	}else if(mode=="edit"){//�༭
    		
    	}   	    	
    	//    	
		if(!sdate==""){
			//���ת����JSONArray	        	
	   		var json = eval(sdate);
			//         
	        $(json).each(function(i) {      //�����������	
	        	//
	        	vid = json[i].vid;
	        	vname = json[i].vname;
	        	vdesc = json[i].vdesc;
	        	vtype = json[i].vtype;
	        	vphysql = json[i].vphysql;
	        	vlocsql = json[i].vlocsql;
	        	vstatus = json[i].vstatus; 	
	        	
	        });
		}				
    	//
	    html = "<fieldset  class='list'>";
	    	if(mode=="add"){//����    		
	    		html += "<legend  class='legend'>[����]��Ϣ�Ϸ���֤����</legend>";
	    	}else if(mode=="edit"){//�༭
	    		html += "<legend  class='legend'>["+selname+"]��Ϣ�Ϸ���֤����</legend>";
	    	} 
	    	
			html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";
				temp ="<td height='23'>����</td>";
				html +=temp;
				temp ="<td height='23'>����ֵ</td>";
				html +=temp;
			html +="</tr>";
				html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����</td>";
					html += "<td><div id = 'type'></div></td>";
		    	html +="</tr>";	
				html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��֤����</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vname'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ϸ����</td>";
					html += "<td><textarea style = 'width:100%' rows='5' id = 'vdesc'></textarea></td>";
		    	html +="</tr>";		    	
	    	//
	    	html +="</table>";
	    	if(mode=="add"){//����
		    	html += "<button class = 'btn' onclick=\"SaveValidate('"+mode+"')\">����</button>"; 	   		
	    	}else if(mode=="edit"){//�༭
	    		//����ʱ��������
		    	if(vstatus=="1"){		    		 
		    		html += "<button class = 'btn' onclick=\"sqlaction()\">��������</button>";
		    		html += "<button class = 'btn' onclick=\"SaveValidate('"+mode+"')\">����</button>";
		    	}
	    	}
	    //	
	    html += "</fieldset>"; 
    	//�б�
    	validateitems.innerHTML = html;
    	//����ѡ��������
    	type.innerHTML = typeselectdiv; 
    	//������ֵ
    	$("#vname").val(vname);	
    	$("#vdesc").val(vdesc);
    	$("#typeselect").val(vtype);//����������
    	$("#vname").get()[0].focus();
    	
	}
	//��������
	function ChioceDo(src,id,name){
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
		//
		//ȡ����Ϣ�Ϸ���֤����
		getInfoValidateItems(selid);
	}
	//����Ϣ��֤����ҳ��
	function sqlaction(){
		//
		if(selid==""){
			DisplayResult("û��ѡ����Ϣ�Ϸ���֤,�޷���������!");
			return; 
		}			
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var sArgu = "qid="+selid+"&qname="+selname+"";		
		var oUrl = "/db/page/system/infovalidate/validatemanagesql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"��Ϣ�Ϸ���֤��������&getInfoValidateItems('"+selid+"')","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
