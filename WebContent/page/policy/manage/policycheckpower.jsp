<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.net.URLDecoder"%>
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
    
    <title>ҵ������Ȩ�޶���</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	
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
		.tab {
			width: 100%;		
			margin: 0 auto;
			overflow: hidden;				
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
		-->
	</style>
	<script type="text/javascript">
		<!--
		//
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼����
		//
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		var policyid = "<%=reqpid%>";
		var policyname = "<%=reqpname%>";
		
		
		//��ȡҵ��������ɫȨ���б�
		function GetPolicyPostsPower() {
			//		  	
		    $.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
		        {
		            action: "getPolicyPostsPower",                  //action����
		            pid: policyid,
		            empid: empid                                        
		        },
		        function(result) {   //�ص�����	
		          	//��ɫ�б�������Ȩ��
					ShowPostPower(result);		          			          		          	          		          		                                                  
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
		//��ʾ��ʾ��Ϣdiv
		function DisplayResult(info) {
	    	vleft = document.body.clientWidth/2-100;  //�������
			vtop= document.body.clientHeight/2; //�����߶�  	
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
	    	//��ǰ��¼�û����    
			empid = "<%=empno%>";     
			//��¼����    
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			vleft = document.body.clientWidth/2-100;  //�������
			vtop= document.body.clientHeight/2; //�����߶�  	
			//
	    	//��ȡҵ��������ɫȨ���б�
			GetPolicyPostsPower();
	    }
		-->
	</script>
	
  </head>
  
  <body onload="IniPage()">  	
	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  	<div id='resultstatusdiv'></div> 
  	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
    	<tr align="center">
    		<td valign="top" class = "myspace"><div id="policycon"> </div></td>	
    	</tr>		    		   				    	    	
   	</table>
  	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		  		
    	<tr align="center">
    		<td valign="top" class = "myspace"><div id="postpowerscon"> </div></td>	
    	</tr>		    		   				    	    	
   	</table>
  </body>
</html>
<script  type="text/javascript">
	//��ɫ�б�������Ȩ��
	function ShowPostPower(sdata){		
		var html,temp;
		var spostid,spostname,checkflag,checkmoreflag,reportflag;
		var checkflagid,checkmoreflagid,reportflagid;		
		//��ǰҵ��
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
		html += "<tr class='mybackground colValue'>";
			temp ="<td height='23'>��ǰҵ��["+policyname+"]�û���λ����Ȩ�޶���</td>";
			html +=temp;
		html +="</tr>";			
		//
    	html +="</table>";
    	//
    	policycon.innerHTML = html;
    	//
    	//��ǰ��ɫȨ��
		html = "<table id = 'posttb' width='100%' cellpadding='0' cellspacing='0'>"
		html += "<tr class='colField'>";
			temp ="<td height='23'>��λ����(�û���λ)</td>";
			html +=temp;
			temp ="<td height='23'>����(����)</td>";
			html +=temp;			
			temp ="<td height='23'>����(����)</td>";
			html +=temp;
			temp ="<td height='23'>����(ȷ��)</td>";
			html +=temp;
			//temp ="<td height='23'>����</td>";
			//html +=temp;
		html +="</tr>";	
		//���ת����JSONArray	        	
   		var json = eval(sdata);
		//         
        $(json).each(function(i) {      //�����������	
        	//
        	spostid = json[i].pid;
        	spostname = json[i].pname;
        	checkflag = json[i].pcheckflag;
        	checkmoreflag = json[i].pcheckmoreflag;
        	reportflag = json[i].preportflag;
        	//
        	checkflagid = "checkflag"+ spostid;
        	checkmoreflagid = "checkmoreflag"+ spostid;
        	reportflagid = "reportflag"+ spostid;
        	//        	
        	//
			html +="<tr>";		
	    	//����ֵ	
			temp="<td height='23' class='colValue'>"+spostname+"";
			html +=temp;
			temp="<input style='display:none;' type='text' id='postid'"+spostid+"></input></td>";
			html +=temp;
			if(checkflag=="0"){
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkflagid+"' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}else{
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkflagid+"' checked = 'checked' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}
			if(checkmoreflag=="0"){
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkmoreflagid+"' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}else{
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+checkmoreflagid+"' checked = 'checked' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}
			if(reportflag=="0"){
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+reportflagid+"' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}else{
				temp="<td height='23' class='colValue'><input type='checkbox' id='"+reportflagid+"' checked = 'checked' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
				html +=temp;
			}
			//			
			//temp="<td height='23' class='colValue'><input id = 'btnok'"+spostid+" type='button' value='ȷ��' onclick=\"SetPolicyPostsPower('"+spostid+"','"+checkflagid+"','"+checkmoreflagid+"','"+reportflagid+"')\"></input></td>";
			//html +=temp;	
	    	html +="</tr>";
        });		
    	//
    	html +="</table>";    	
    	//
    	postpowerscon.innerHTML = html;
    	//
		//JS�������
		new TableSorter("posttb");	
		/*
		new TableSorter("tb2", 0, 2 , 5, 6);
		new TableSorter("tb3").OnSorted = function(c, t)
		{
			alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
		}
		*/	
	}
	//����ҵ��������ɫȨ���б�
	function SetPolicyPostsPower(postid,checkflag,checkmoreflag,reportflag) {
		var stemp;
		var scheckflag = "0",scheckmoreflag = "0",sreportflag = "0";
		//	
		stemp = document.getElementById(checkflag);		
	    if(stemp.checked == false){
	      scheckflag = "0";
	    }else{
	      scheckflag = "1";
	    }
	    stemp = document.getElementById(checkmoreflag);	    
	    if(stemp.checked == false){
	      scheckmoreflag = "0";
	    }else{
	      scheckmoreflag = "1";
	    }
	    stemp = document.getElementById(reportflag);    
	    if(stemp.checked == false){
	      sreportflag = "0";
	    }else{
	      sreportflag = "1";
	    }
	    
	    //				  	
	    $.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
	        {
	            action: "setPolicyPostPowers",                  //action����
	            pid: policyid,
	            postid: postid,
	            checkflag: scheckflag,
	            checkmoreflag: scheckmoreflag,
	            reportflag: sreportflag                                         
	        },
	        function(result) {   //�ص�����	
	          	//��ʾ�������		    		
			    DisplayResult(result);		   		          			          		          	          		          		                                                  
	        }
	    ); 
	}
</script>