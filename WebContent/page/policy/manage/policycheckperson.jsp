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
    
    <title>���������˹���</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>	
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	
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
		var sexselectdiv = "";//�Ա�ѡ���Html
		var faceselectdiv = "";//������òѡ���Html
		//
		selid = "";//ѡ��ID
		selname = "";//ѡ������
		//
		//ȡ�������������б�
		function GetCheckPersons() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "getCheckPersons",             //action����
		            sdeptid: deptid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//�����������б�
					ShowDate(result); 					      		
		        }
		    );
		}
		//ȡ����������������
		function getCheckPersonItems(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "getCheckPersonItems",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//��������������
					ShowDateItems(result,"edit");        		
		        }
		    );
		}
		//��������������״̬
		function UpdateCheckPersonStatus(sid,status){
			var mess = "";
			//
			if(status=="0"){
				mess = "�Ƿ�ȷ��ͣ�ø�������";
			}else{
				mess = "�Ƿ�ȷ�����ø�������";
			}
		    if (!confirm(mess)) {
		        return;
		    }
			//
			//
		    $.post("page/querymanage/QueryManageServlet",            //������ҳ���ַ
		        {
		            action: "updateCheckPersonStatus",             //action����
		            sid: sid,
		            status: status         
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);	
					//
					//ȡ�������������б�
					GetCheckPersons();					  		
		        }
		    );
		}
		//�������
		function SaveCheckPerson(mode){
			var vid,vname,vsex,vofficename,vofficephone,vface,vpost,vaddress,vstatus;
			var mess = "";
			//
			if(mode=="add"){//����
				mess = "�Ƿ�ȷ��������������������";
			}else if(mode=="edit"){//�༭
				if(selid==""){
					mess = "û��ѡ������������,�޷�����";
					return;
				}
				mess = "�Ƿ�ȷ�������������������";
			}
			//
    		//������ֵ
	    	vname = $("#vname").val();
	    	vsex = $("#sexselect").val();//�Ա�������
	    	vface = $("#faceselect").val();//������ò������
	    	vofficename = $("#vofficename").val();
	    	vofficephone = $("#vofficephone").val();    	
	    	vpost = $("#vpost").val();
	    	vaddress = $("#vaddress").val();
	    	
    		if(vname==""){
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
		            action: "updateCheckPerson",             //action����
		            mode: mode,
		            vdeptid: deptid,
		            vid: selid,
		            vname: vname,
		            vsex: vsex,
		            vface: vface,
		            vofficename: vofficename, 
		            vofficephone: vofficephone,
		            vpost: vpost,
		            vaddress: vaddress       
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);
					//
					//ȡ�������������б�
					GetCheckPersons();				  		
		        }
		    );
		}		
		
		//ȡ���ֵ�������ѡ��
		function GetDiscionarySelect(sname,sdiscid,smode) {
		    //			
		    $.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
		        {
		            action: "getDiscionarySelect", //action����
		            sname: sname, //����
		            sdiscid: sdiscid //����	            	            
		        },
		        function(result) {   //�ص�����
		        	if(smode=="sex"){//�Ա�
		        		sexselectdiv = result;	
		        	}else if(smode=="face"){//������ò
		        		faceselectdiv = result;	
		        	}
		        		        		        	        	   	
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
			//ȡ���ֵ�������ѡ��
			GetDiscionarySelect("sexselect","1","sex");//�Ա�
			//ȡ���ֵ�������ѡ��
			GetDiscionarySelect("faceselect","182","face");//������ò
			//ȡ�������������б�
			GetCheckPersons();
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">  		
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top" width="50%"><div id = 'checkpersonlists'></div></td>	    		
	    		<td valign="top"><div id = 'checkpersonitems'></div></td>
	    	</tr>	    	
    	</table>  
  </body>
</html>
<script type="text/javascript">	
	//�����������б�
	function ShowDate(sdate){		
		var html,temp;
		var vid,vname,vsex,vofficename,vofficephone,vstatus;
    	//
    	html = "<fieldset  class='list'>";
			html += "<legend  class='legend'>��������������</legend>";	  			
    	html += "</fieldset>"; 
    	//����
    	checkpersonitems.innerHTML = html; 
    	//
    	html = "<fieldset  class='list'>";
			html += "<legend  class='legend'>�����������б�</legend>";
			html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
			html += "<tr class='colField'>";
				temp ="<td height='23'>����</td>";
				html +=temp;
				temp ="<td height='23'>����</td>";
				html +=temp;
				temp ="<td height='23'>�Ա�</td>";
				html +=temp;
				temp ="<td height='23'>��ϵ�绰</td>";
				html +=temp;
				temp ="<td height='23'>��λ����</td>";
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
	        	vsex = json[i].vsex;
	        	vofficename = json[i].vofficename;
	        	vofficephone = json[i].vofficephone;
	        	vstatus = json[i].vstatus;
	        	//
	        	if(vofficename==null){vofficename = "��";}
	        	if(vofficephone==null){vofficephone = "��";}	        	
	        	//
				html +="<tr>";		
		    		//����ֵ	
					html += "<td width='32px' height='23' class='colValue'>";						
						html += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"ChioceDo(this,'"+vid+"','"+vname+"')\" />";									
					html += "</td>";
					html += "<td height='23' class='colValue status"+vstatus+"'>"+vname+"</td>";
					html += "<td height='23' class='colValue'>"+vsex+"</td>";
					html += "<td height='23' class='colValue'>"+vofficephone+"</td>";
					html += "<td height='23' class='colValue'>"+vofficename+"</td>";
					if(vstatus=="1"){
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdateCheckPersonStatus('"+vid+"','0')\">ͣ��</button></td>";	
					}else{
						html += "<td width='32px' height='23' class='colValue'><button class = 'btn' onclick=\"UpdateCheckPersonStatus('"+vid+"','1')\">����</button></td>";	
					}
							
		    	html +="</tr>";
	        });		
	    	//
	    	html +="</table>"; 
	    html += "</fieldset>";
	    html += "<button class = 'btn' onclick=\"ShowDateItems('','add')\">��������������</button>";    	
    	//�б�
    	checkpersonlists.innerHTML = html;
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
		//			
	}
	//��������������
	function ShowDateItems(sdate,mode){		
		var html,temp;
		var vid,vname,vsex,vofficename,vofficephone,vface,vpost,vaddress,vstatus;
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
	        	vsex = json[i].vsex;
	        	vofficename = json[i].vofficename;
	        	vofficephone = json[i].vofficephone;
	        	vface = json[i].vface;
	        	vpost = json[i].vpost;
	        	vaddress = json[i].vaddress;
	        	vstatus = json[i].vstatus; 	
	        	//	
	        });
		}		
    	//
	    html = "<fieldset  class='list'>";
	    	html += "<legend  class='legend'>��������������</legend>";
			html += "<table id = 'liststb' width='100%' cellpadding='0' cellspacing='0'>";
			html += "<tr class='colField'>";
				temp ="<td height='23'>����</td>";
				html +=temp;
				temp ="<td height='23'>����ֵ</td>";
				html +=temp;
			html +="</tr>";				
				html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>����</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vname'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>�Ա�</td>";
					html += "<td><div id = 'sex'></div></td>";
		    	html +="</tr>";		    	
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>������ò</td>";
					html += "<td><div id = 'face'></div></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��λ����</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vofficename'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>��ϵ�绰</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vofficephone'></input></td>";
		    	html +="</tr>";
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>ְ��</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vpost'></input></td>";
		    	html +="</tr>";		    	
		    	html +="<tr>";
					html += "<td width='100px' style='color: #000000;background-color:#ECECEC;text-align:center;font-size:12px;'>סַ</td>";
					html += "<td><input style = 'width:100%' type='text' id = 'vaddress'></input></td>";
		    	html +="</tr>";		    	
	    	//
	    	html +="</table>";
	    	
	    	if(mode=="add"){//����
		    	html += "<button class = 'btn' onclick=\"SaveCheckPerson('"+mode+"')\">����</button>"; 	   		
	    	}else if(mode=="edit"){//�༭
	    		//����ʱ��������
		    	if(vstatus=="1"){
		    		html += "<button class = 'btn' onclick=\"SaveCheckPerson('"+mode+"')\">����</button>";
		    	}
	    	}
	    //	
	    html += "</fieldset>";    	
    	//�б�
    	checkpersonitems.innerHTML = html;
    	//�Ա�ѡ��������
    	sex.innerHTML = sexselectdiv;
    	//������òѡ��������
    	face.innerHTML = faceselectdiv;
    	//������ֵ
    	$("#vname").val(vname);
    	$("#sexselect").val(vsex);//�Ա�������
    	$("#faceselect").val(vface);//������ò������
    	$("#vofficename").val(vofficename);
    	$("#vofficephone").val(vofficephone);    	
    	$("#vpost").val(vpost);
    	$("#vaddress").val(vaddress);
    	
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
		//ȡ����������������
		getCheckPersonItems(selid);
	}
</script>