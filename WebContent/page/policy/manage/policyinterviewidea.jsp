<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//
	//
	//�Ӳ�ѯ�����qmode
	String reqmode = request.getParameter("qmode");	
	if (reqmode == null) {
		//Ĭ��Ϊ��
		reqmode = "edit";    //�༭״̬
	}
	//�Ӳ�ѯ�����qid
	String reqid= request.getParameter("qid");		
	if (reqid == null) {
		//Ĭ��Ϊ��
		reqid = "";    //��ID
	}	
	//�Ӳ�ѯ�����qfmno
	String reqfmno = request.getParameter("qfmno");	
	if (reqfmno == null) {
		//Ĭ��Ϊ��
		reqfmno = "";    //�ռ�ͥ���
	}
	//�Ӳ�ѯ�����qid
	String reqfmname= request.getParameter("qfmname");
	reqfmname = new String(reqfmname.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqfmname == null) {
		//Ĭ��Ϊ��
		reqfmname = "";    //�ջ�������
	}	
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�߷ü�¼��д����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="/db/page/js/jquery.js"></script>
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
	
	<style type="text/css">
 <!--
  .table4 {
		border-collapse: collapse;
		border: 1px solid #999;
		border-width: 1px 0 0 1px;
		margin: 2px 0 2px 0;
		text-align: center;
	}
	
	.table4 td {
		height: 23px;
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		font-size: 12px;
		background-color: #FFF;
		text-align: center;
	}
	
	.table4 th {
		height: 20px;		
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		text-align: center;
		font-size: 12px;		
		font-weight: 600;
		color: #FFFFFF;
		background-color: #6BA6FF;
	}
	.tablexiu {
		border-collapse: collapse;
		border: 1px solid #999;
		border-width: 1px 0 0 1px;
		margin: 2px 0 2px 0;
		text-align: left;
		background-color: #fff
	}
	
	.tablexiu td {
		height: 20px;
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		font-size: 12px;
		text-align: left;
		background-color: #fff
	}
	
	.tablexiu th {
		height: 20px;		
		border: 1px solid #999;
		border-width: 0 1px 1px 0;
		margin: 2px 0 2px 0;
		text-align: center;
		font-size: 12px;
		color: #000000;
		background: url('/db/page/images/titmember.gif');
	}
	.pointer {
		cursor:pointer;
	}
	body {
		margin: 0;
		padding: 0;
		font-family: "����";
		font-size: 12px;
	}
	#pagestatusdiv{		
		z-index:2;		
		font-weight: bold;
		color: #FF0099;
		font-size:16px;
		display:none;
	}
	#resultstatusdiv{		
		z-index:2;
		font-weight: bold;
		color: #FF0099;
		font-size:16px;
		display:none;
	}
	-->
  </style>
  <script  type="text/javascript">
		//
		//
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼���� 
		//
		var mode = "",fmid = "",viewid = "",fmno = "",fmname = "";	
		//		
		//��ȡ�߷ü�¼����
		function GetInterviewIdeaItem() {		
			//		    
			DisplayPageStatus(); 
			//	
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getInterviewIdeaItem",             //action����		            
		            viewid: viewid		            
		        },
		        function(result) {                    //�ص�����    
	        		//�����������
     					HiddenPageStatus();  
     					//      					 
     					//���ת����JSONArray	        	
        			var json = eval(result); 
    					//   
	                $(json).each(function(i) {      //�����������		                
		                //
		                viewid = json[i].iid;
		                fmid = json[i].ifmid;
		                $("#viewperson").val(json[i].iperson);     //��ȡ		                	    
					    $("#viewdt").val(json[i].idt);     //��ȡ				    
					    $("#viewresult").val(json[i].iresult);     //��ȡ
					    $("#viewtype").val(json[i].itype);     //��ȡ
					    $("#viewdept").val(json[i].ideptname);     //��ȡ			    		                   
		            });		            
		         }   
		    );
		}
		//�����߷ü�¼����	
		function SetInterviewIdeaItem(){
			//
			var iperson = "",idt = "",iresult = "",itype = "",ideptid = "",operid = "";
			//
			iperson = $("#viewperson").val();
			idt = $("#viewdt").val();
			iresult = $("#viewresult").val();
			itype = $("#viewtype").val();
			ideptid = $("#viewdeptid").val();
			operid = empid;
			
			//			
			if(fmid ==""){
				alert("û��ѡ���ͥ!");
				return;
			}
			//
			if(iperson == ""){
				alert("û����д�߷���!");
				return;
			}
			//						
			if(idt == ""){
				alert("û����д����!");
				return;
			}
			//						
			if(ideptid == ""){
				alert("û����д�߷û���!");
				return;
			}
			//			
			if(iresult == ""){
				alert("û����д�߷ý��!");
				return;
			}
			//ȷ��
		    if (!confirm("�Ƿ�ȷ������߷ü�¼��")) {
		        return;
		    }
			//
			//		    
			DisplayPageStatus(); 
		 	//
		   	$.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
		       	{
		           	action: "setInterviewIdeaItem" , //action����         
		           	fmid: fmid,
		           	iperson: iperson,
		           	idt: idt,
		           	iresult: iresult,
		           	itype: itype,
		           	ideptid: ideptid,
		           	operid: operid                             
		       	},
		      	function(result) {   //�ص�����
		      		//�����������
      				HiddenPageStatus(); 
      				//
      				$("#btnrequest").attr("disabled", "disabled");
      				DisplayResult(result);			  		     	                      
		     }
		   );
		}
		
		//��ʾҳ��״̬div
		function DisplayPageStatus() {
		    var pagestatusdiv = $("#pagestatusdiv");            //��ȡ��ʾ��Ϣdiv		        
		    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
		}		
		//����ҳ��״̬div
		function HiddenPageStatus() {
		    $("#pagestatusdiv").fadeOut();                  //������Ϣ
		}
		//��ʾ��ʾ��Ϣdiv
		function DisplayResult(info) {
		    var resultstatusdiv = $("#resultstatusdiv");            //��ȡ��ʾ��Ϣdiv
		    resultstatusdiv.html(info);                       //����div���ı�		    
		    $(resultstatusdiv).fadeIn();                      //������Ϣ
		    //setTimeout("HiddenResult()",2000);          //2���������Ϣ
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
			mode = "<%=reqmode%>";		 //ģʽ			
			if(mode=="edit"){//�༭״̬
		    	//����ID����Ϊ����	
		    	viewid = "<%=reqid%>";		 //ID   	
		    	$("#btnrequest").css({"display":"none"});		    	
		    	//��ȡ�߷ü�¼����
				GetInterviewIdeaItem();
		    }else{
				//��ͥID����Ϊ����
		    	fmid = "<%=reqid%>";		 //ID		    		    
		    } 
		    fmno = "<%=reqfmno%>";		 //��ͥ���
		    fmname = "<%=reqfmname%>";		 //�������� 	 	
			//
			$("#viewperson").get()[0].focus(); 	   
	    }		
  </script> 
  </head>
  
  <body onload = "IniPage()">
  		<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>  		
  		
  		<table class="tablexiu" width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td height = '23'style="color: #000000;background: url('/db/page/images/titmember.gif');text-align:center;font-size:12px;">��ǰ�߷ü�¼��д:��ͥ����[<%=reqfmno%>]��������[<%=reqfmname%>]</td>
	   		</tr>
   		</table>  
  		<table class="tablexiu" width="100%" border="0" cellspacing="0" cellpadding="0">	    	
	    	<tr align="center">
	    		<td width = "100px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">�߷���:</td>
	    		<td width="30%"><input style = "width:100%" type="text" id = "viewperson"></input></td>
	    		<td width = "80px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">�߷�����:</td>
	    		<td><input style = "width:100%" type="text" name="requestideadt" id="viewdt" onFocus="setday(this)"></input></td>
	    	</tr>
	    	<tr align="center">
	    		<td width = "100px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">�߷�����:</td>
	    		<td width="30%"><input style = "width:100%" type="text" id = "viewtype"></input></td>
	    		<td width = "80px" style="color: #000000;background-color:#ECECEC;text-align:right;font-size:12px;">�߷û���:</td>
	    		<td><div style = "width:100%"><input style = "width:100%" type="text" id = "viewdept" onclick="queryDept()"></input><input style = "display:none" type="text" id = "viewdeptid"></input></div></td>
	    	</tr>	    	    	
	    	<tr align="center">
	    		<td width = "100px" style="color: #6000000;background-color:#ECECEC;text-align:right;font-size:12px;">�߷ý����д:</td>
	    		<td colspan = "3"><textarea style = "width:100%" rows="5" id = "viewresult"></textarea></td>
	    	</tr>    	
    	</table>
    	<div align="center">
    		<input id = "btnrequest" type="button" value=" ȷ �� "onclick="SetInterviewIdeaItem()"></input>
    		<!-- <input id = "btnclose" type="button" value=" �� �� "onclick="window.close();"></input> -->
    	</div>    	
    
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table>
   		<table width="100%" border="0" cellspacing="0" cellpadding="0">
   		<tr align="center">
   			<td><div align="center" id='resultstatusdiv'></div></td>
   		</tr>
  	</table>    		
  </body>
</html>
<script type="text/javascript">
  	//=================================AJAXģ̬����=================================	
	var seldeptid;		//ѡ�л���
	var seldeptname;
	var seldeptfullname;	
	//	
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
                   oWidth:250,                              //�������ڿ��
                   oHeight:220                              //�������ڸ߶�
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
	
	$(function(){
        //����
        $("button").css({"display":"block","margin":"10px 0","width":"120px"});
        $("button:first").css("background","red");
	});
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
	    $("#viewdeptid").val(seldeptid);
	    $("#viewdept").val(seldeptfullname);	
		closedept();
	}
	//�ÿջ���ѡ��[DeptTreeServlet�����ķ���]
	function cleardept(){
		//ѡ����ò���
	    $("#viewdeptid").val("");
	    $("#viewdept").val("");	
	    //
		closedept();	
	}
	//�رջ���ѡ��[DeptTreeServlet�����ķ���]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();
	}		
	//=================================AJAXģ̬����=================================
</script>
