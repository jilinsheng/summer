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
    
    <title>����ҵ���ƹ���</title>
    
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
		var seldeptid;			//ѡ�л���
		var seldeptname;
		var seldeptfullname;
		//
		var selquerydeptmode = "sql";//sql��׼ѡ����� acc����ѡ�����
		// 
		var currPolicyId = "";				//��ǰҵ����
		var currPolicyName = "";				//��ǰҵ������
		
		//		
		var currStandardId = "";			    //��ǰ��׼���
		var currStandardDept = "";				//��ǰ��׼��������
		var currStandardDesc = "";			    //��ǰ��׼����	
		var currStandardMoney = "0";			//��ǰ��׼�ƻ����
		var currStandardAccDesc = "";			//��ǰ��׼����	
		//
		var currDeptId = "";					  //��ǰ�������
		var currDeptName = "";					  //��ǰ�������
		var currDeptMoney = "0";				  //��ǰ��׼���
		var currDeptAccDesc = "";				  //��ǰ���㹫ʽ����
		
		//ȡ��ҵ���б�
		function GetPolicysHtml() {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicysHtml"             //action����
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	policylists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>ҵ������</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	policyitems.innerHTML = html;
			    	
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
					//ȡ��ҵ������
					GetPolicyItemHtml(currPolicyId);				    					      		
		        }
		    );
		}
		//ȡ��ҵ������
		function GetPolicyItemHtml(sid) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyItemHtml",             //action����
		            sid: sid         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	policyitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//����ҵ��
		function SavePolicy(mode){
			//�༭״̬
			var EditType = mode;
			//����
			var pid      = "",pmenu = "",pname = "",pdescr = "",pnote ="",pcycnum  = "";
			var pobjtype = "",ptype = "",pacctu = "",pcycle="",pisprint = "",pbegdt = "",pacctype = "";
		    //
			//
			pmenu = $("#pmenu").val();
			pname = $("#pname").val(); //��ȡҵ������	
		    pdescr = $("#pdescr").val();
		    pnote = $("#pnote").val();
		    pcycnum = $("#pcycnum").val();
		    
		    pobjtype = $("#pobjtype").val();
		    ptype = $("#ptype").val();
		    pacctu = $("#pacctu").val();
		    pcycle = $("#pcycle").val();		    
		    pbegdt = $("#pbegdt").val();
		    
		    var isprn = document.getElementById("pisprint");
		    if(isprn.checked == false){
		      pisprint = "0";
		    }else{
		      pisprint = "1";
		    }
		   	//
		   	//ѡ���ͥ���߳�Ա����
		    var isrdaccf = document.getElementById("rdaccf");
		    var isrdaccm = document.getElementById("rdaccm");
		    if(isrdaccf.checked == true){	    		
	      	 	pacctype = "0";//��ͥ����	               
	      	}else if(isrdaccm.checked == true){
	      		pacctype = "1";//��Ա���� 
	      	}
		    //Ϊ��У��
		    if(pmenu==""){		      
			  DisplayResult("����ҵ����Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(pname==""){		      	      
			  DisplayResult("ҵ�����Ʋ���Ϊ��!"); //��ʾ�������
		      return;
		    }		    	
		    if(pobjtype==""){		      
			  DisplayResult("ҵ����಻��Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(ptype==""){		      
			  DisplayResult("�������Ͳ���Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(pacctu==""){		      
			  DisplayResult("���Ż�������Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(pcycle==""){		     
			  DisplayResult("�������ڲ���Ϊ��!"); //��ʾ�������
		      return;
		    }
		    
		    if(pbegdt==""){		      
			  DisplayResult("ҵ�����ڲ���Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(pcycnum==""){		      
			  DisplayResult("ҵ�����ڲ���Ϊ��!"); //��ʾ�������
		      return;
		    }
		    
		    //
		    if(EditType=="addpolicy"){
		    	pid = "";			//��ȡҵ���� 
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ��������ҵ��")) {
			        return;
			    }			    
		    }else if(EditType=="editpolicy"){
		    	pid = currPolicyId;     //��ȡҵ����	
			    if(pid==""){			     
				  DisplayResult("û��ѡ��ҵ��,�޷�����!"); //��ʾ�������
			      return;
			    }
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+pname+"]��ҵ�����ԣ�")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }
		    //
		  	DisplayPageStatus();		  	
		  	//
		    $.post("page/policy/manage/PolicyManageServlet", //������Ŀ��ҳ��
		        {
		            action: "updatePolicy",                  //action����
		            EditType: EditType,				 //����
		            pid: pid,                      //�б�id����
		            pmenu: pmenu,				 //����
		            pname: pname,				 //����
		            pdescr: pdescr,				 //����
		            pnote: pnote,				 //����
		            pcycnum: pcycnum,				 //����		            
		            pobjtype: pobjtype,				 //����
		            ptype: ptype,				 //����
		            pacctu: pacctu,				 //����
		            pacctype: pacctype,			 //����
		            pcycle: pcycle,				 //����
		            pisprint: pisprint,				 //����
		            pbegdt: pbegdt				 //����
		        },
		        function(result) {                      //�ص�����
		        	HiddenPageStatus();	 		        	
		            DisplayResult(result);              //��ʾ�������
		            //ȡ�÷���ʩ���б�
					GetPolicysHtml();
		        }
		    );
		}		
		//����ҵ��״̬
		function updatePolicyStatus(Id,Name,Status) {
			//
			var smse = "";
			if(Status=="0"){
				smse = "ͣ��";
			}else{
				smse = "����";
			}
		    //����ҵ��ķǿ��ж�
		    if (Id != "") {		    	
		    	//ͣ��ǰȷ��
			    if (!confirm("�Ƿ�ȷ��"+smse+"["+Name+"]��ҵ��״̬��")) {
			        return;
			    }
			    //
			  	DisplayPageStatus();
			  	//
			    $.post("page/policy/manage/PolicyManageServlet",                   //������Ŀ��ҳ��
			        {
			            action: "updatePolicyStatus",                  //action����
			            Id: Id,                      //�б�id����
			            Status: Status				 //����
			        },
			        function(result) {                      //�ص�����
			        	//��ʾ��ʾ��Ϣdiv
						DisplayResult(result);	
						//
						//ȡ�÷���ʩ���б�
						GetPolicysHtml();		            
			        }
			    );
		    } else {		        
		        DisplayResult("û��ѡ��ҵ��,�޷�����!"); //��ʾ�������		       
		    }
		}
		//��ȡ���߱�׼�б�
		function GetPolicySqlsHtml(PId,PName) {
			//
			var JDeptid = "";	//��ѯ����ID
			//
			JDeptid = $("#sqdeptid").val();
			if("" == JDeptid || null == JDeptid){
				JDeptid = deptid;
			}
		  	DisplayPageStatus();
		  	//
		    $.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
		        {
		            action: "getPolicySqlsHtml",                  //action����
		            PId: PId,
		            PName: PName, 
		            JDeptid: JDeptid		                                                 
		        },
		        function(result) {   //�ص�����	
		          	HiddenPageStatus();	          
		          	//
		        	policysqllists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>��׼����</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	policysqlitems.innerHTML = html;
			    	
			    	//JS�������
					new TableSorter("listsstb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					//
					//ȡ��ҵ������
					GetPolicySqlItemHtml(currStandardId);         	          	          		          		                                                  
		        }
		    ); 
		}
		//ȡ��ҵ������
		function GetPolicySqlItemHtml(Id) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicySqlItemHtml",             //action����
		            Id: Id         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	policysqlitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//���±�׼״̬
		function updateStandardStatus(Id,Name,Status) {
		    //
			var smse = "";
			if(Status=="0"){
				smse = "ͣ��";
			}else{
				smse = "����";
			}		    
		    if (Id != "") {		    	
		    	//ͣ��ǰȷ��
			    if (!confirm("�Ƿ�ȷ��"+smse+"["+Name+"]�ñ�׼״̬��")) {
			        return;
			    }
			    //
			  	DisplayPageStatus();
			  	//
			    $.post("page/policy/manage/PolicyManageServlet",//������Ŀ��ҳ��
			        {
			            action: "updateStandardStatus",//action����
			            Id: Id,                      //�б�id����
			            Status: Status				 //����
			        },
			        function(result) {                      //�ص�����
			        	HiddenPageStatus();	 			            
			            DisplayResult(result);              //��ʾ�������
			            //��ȡ���߱�׼�б�
			            GetPolicySqlsHtml(currPolicyId,currPolicyName);
			            			            		            
			        }
			    );
		    } else {		        
		        DisplayResult("û��ѡ���׼,�޷�����!"); //��ʾ�������		       
		    }
		}
		//���±�׼
		function SaveStandard(mode){
			//�༭״̬
			var EditType = mode;
			
			//����			
			var pid = "",pname = "",sid = "",sdescr = "",sdeptid = "",sdeptname = "";
			var splanmoney = "",splandesc="",ssuperpolicy="",snotpolicy="";			
			
			//
			pid = currPolicyId;	
		    pname = currPolicyName;	 
		    
		    // 
		    sdescr = $("#sdescr").val();; //��ȡ����		    		    
		    splanmoney = $("#splanmoney").val();
		    splandesc = $("#splandesc").val();
		    ssuperpolicy = $("#ssuperpolicy").val();
		    snotpolicy = $("#snotpolicy").val();
		    //		    			    
		    sdeptid = $("#sdeptid").val();		    		    
		    sdeptname = $("#sdeptname").val();
		    //Ϊ��У��
		    if(sdeptname==""){		      
			  DisplayResult("������������Ϊ��!"); //��ʾ�������
		      return;
		    }
		    //Ϊ��У��
		    if(sdescr==""){		     
			  DisplayResult("��׼���Ʋ���Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(splanmoney==""){		      
			  DisplayResult("��׼�����Ϊ��!"); //��ʾ�������
		      return;
		    }
		    //
		    if(EditType=="addstandard"){
		    	
		    	sid = "";					   //��ȡ���	
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+pname+"]��׼��")) {
			        return;
			    }			    
		    }else if(EditType=="editstandard"){
		    	sid = currStandardId;     //��ȡ���	
			    if(sid==""){			      
				  DisplayResult("û��ѡ���׼,�޷�����!"); //��ʾ�������
			      return;
			    }
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+pname+"]��׼["+sdescr+"]���ԣ�")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }		    
		    //
		  	DisplayPageStatus();
		  	//
		    $.post("page/policy/manage/PolicyManageServlet", //������Ŀ��ҳ��
		        {
		            action: "updateStandard",                  //action����
		            EditType: EditType,				 //����
		            pid: pid,                      //�б�id����
		            sid: sid,				 //����
		            sdescr: sdescr,				 //����
		            sdeptid: sdeptid,
		            splanmoney: splanmoney,				 //����
		            splandesc: splandesc,				 //����
		            ssuperpolicy: ssuperpolicy,
		            snotpolicy: snotpolicy		            				            
		        },
		        function(result) {                      //�ص�����
		        	HiddenPageStatus();
		            DisplayResult(result);              //��ʾ�������
		            //��ȡ���߱�׼�б�
	            	GetPolicySqlsHtml(currPolicyId,currPolicyName); 
		        }
		    );
		}
		//��ȡ������׼�б�
		function GetPolicyDeptsHtml(SId,SDeptName,SName,SMoney,SAccDesc) {
			//
		  	DisplayPageStatus();
		  	//
		    $.post("page/policy/manage/PolicyManageServlet",      //������ҳ���ַ
		        {
		            action: "getPolicyDeptsHtml",                  //action����
		            SId: SId,
		            SDeptName: SDeptName,
		            SName: SName,
		            SMoney: SMoney,
		            SAccDesc: SAccDesc                                      
		        },
		        function(result) {   //�ص�����	
		          	HiddenPageStatus();	          
		          	//
		        	policydeptlists.innerHTML = result; 
		        	//
			    	var html = "<fieldset  class='list'>";
						html += "<legend  class='legend'>��׼����</legend>";	  			
			    	html += "</fieldset>"; 
			    	//����
			    	policydeptitems.innerHTML = html;
			    	
			    	//JS�������
					new TableSorter("listsdtb");	
					/*
					new TableSorter("tb2", 0, 2 , 5, 6);
					new TableSorter("tb3").OnSorted = function(c, t)
					{
						alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
					}
					*/
					// 
					//ȡ�û�����׼����
					GetPolicyDeptItemHtml(currDeptId);       	          	          		          		                                                  
		        }
		    ); 
		}
		//ȡ�û�����׼����
		function GetPolicyDeptItemHtml(Id) {
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "getPolicyDeptItemHtml",             //action����
		            Id: Id         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
		        	//����
			    	policydeptitems.innerHTML = result;
			    	 	       		
		        }
		    );
		}
		//���»�����׼״̬
		function updateDeptStatus(Id,Name,Status) {
			//
			var smse = "";
			if(Status=="0"){
				smse = "ͣ��";
			}else{
				smse = "����";
			}
		    //����ҵ��ķǿ��ж�
		    if (Id != "") {		    	
		    	//ͣ��ǰȷ��
			    if (!confirm("�Ƿ�ȷ��"+smse+"["+Name+"]�û�����׼״̬��")) {
			        return;
			    }
			    $.post("page/policy/manage/PolicyManageServlet",//������Ŀ��ҳ��
			        {
			            action: "updateDeptStatus",//action����
			            Id: Id,                      //�б�id����
			            Status: Status				 //����
			        },
			        function(result) {                      //�ص�����			            
			            DisplayResult(result);              //��ʾ�������
			            //��ȡ������׼�б�
						GetPolicyDeptsHtml(currStandardId,currStandardDept,currStandardDesc,currStandardMoney,currStandardAccDesc);
						      
			        }
			    );
		    } else {		       
		        DisplayResult("û��ѡ�������׼,�޷�����!"); //��ʾ�������		       
		    }
		}
		//���»�����׼
		function SaveDept(mode){
			//
			sid = currStandardId;	
		    sname = currStandardDesc;			   
			//�༭״̬
			var EditType = mode;
			
			var did = "",ddeptid = "",ddeptname = "",dcheckmoney = "",dcheckdesc="",daccdesc="";
			//
			//
			sid = currStandardId;	
		    sname = currStandardDesc;
		    //		    			    
		    ddeptid = $("#ddeptid").val();		    		    
		    ddeptname = $("#ddeptname").val();
		    dcheckmoney = $("#dcheckmoney").val();
		    dcheckdesc = $("#dcheckdesc").val();
		    daccdesc = $("#daccdesc").val();
		    //Ϊ��У��
		    if(ddeptname==""){		      
			  DisplayResult("������������Ϊ��!"); //��ʾ�������
		      return;
		    }
		    if(dcheckmoney==""){		      
			  DisplayResult("��׼�����Ϊ��!"); //��ʾ�������
		      return;
		    }	
		    //	    
	     	if(EditType=="adddept"){
		    	did = "";					   //��ȡ���
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+sname+"]�»�����׼��")) {
			        return;
			    }			    
		    }else if(EditType=="editdept"){
		    	did = currDeptId;     //��ȡ���	
			    if(did==""){			      
				  DisplayResult("û��ѡ�������׼,�޷�����!"); //��ʾ�������
			      return;
			    }
		    	//ȷ��
			    if (!confirm("�Ƿ�ȷ������["+sname+"]������׼["+ddeptname+"]���ԣ�")) {
			        return;
			    }			    
		    }else{
		    	//
		    	return;
		    }
   			//
   			$.post("page/policy/manage/PolicyManageServlet", //������Ŀ��ҳ��
	        {
	            action: "updateDept",                  //action����
	            EditType: EditType,				 //����
	            sid: sid,                //�б�id����
	            did: did,				 //����
	            ddeptid: ddeptid,				 //����				            		            
	            dcheckmoney: dcheckmoney,				 //����
	            dcheckdesc: dcheckdesc,				 //����
				daccdesc: daccdesc				 //����					            
	        },
	        function(result) {                      //�ص�����				        	
	            DisplayResult(result);              //��ʾ�������
	            //��ȡ������׼�б�
				GetPolicyDeptsHtml(currStandardId,currStandardDept,currStandardDesc,currStandardMoney,currStandardAccDesc);
	        }
	    );
		}
		//ѡ������ҵ��
		function ChoiceSupPolicy() {
			var ispolicy;
			var spname = $("#ssuperpolicy").val();
			//
			ispolicy = document.getElementById("listspolicy");
			if(ispolicy ==null){
				return;
			}
			//
			var selpname = listspolicy.options[listspolicy.selectedIndex].text;
			var st = spname;
			//
			if(spname==""){
	      		$("#ssuperpolicy").val(selpname);
	      	}else{
		      	if(MatchMatch(st,selpname)==null){                              
		        	if (st.length>0){
		        		$("#ssuperpolicy").val(spname+","+selpname);           
		        	} 
		      	}
	      	} 		    
		}
		//�ÿ�����ҵ��
		function ClearSupPolicy() {
			$("#ssuperpolicy").val(""); 		    
		}
		//ѡ����ҵ��
		function ChoiceNotPolicy() {
			var ispolicy;
			var spname = $("#snotpolicy").val();
			//
			ispolicy = document.getElementById("listsnotpolicy");
			if(ispolicy ==null){
				return;
			}
			//
			var selpname = listsnotpolicy.options[listsnotpolicy.selectedIndex].text;
			var st = spname;
			//
			if(spname==""){
	      		$("#snotpolicy").val(selpname);
	      	}else{
		      	if(MatchMatch(st,selpname)==null){                              
		        	if (st.length>0){
		        		$("#snotpolicy").val(spname+","+selpname);           
		        	} 
		      	}
	      	} 		    
		}
		//�ÿղ���ҵ��
		function ClearNotPolicy() {
			$("#snotpolicy").val(""); 		    
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
			//ȡ��ҵ���б�
			GetPolicysHtml();			
		}		
	</script>
	
  </head>
  
  <body onLoad="IniPage()">
  	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
  	
  	<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  	<div id='resultstatusdiv'></div>
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
    	<tr align="center" id = "policytr">
    		<td valign="top" width="50%"><div id = 'policylists'></div></td>	    		
    		<td valign="top"><div id = 'policyitems'></div></td>
    	</tr>
    	<tr align="center" id = "policysqltr">
    		<td valign="top" width="50%"><div id = 'policysqllists'></div></td>	    		
    		<td valign="top"><div id = 'policysqlitems'></div></td>
    	</tr>
    	<tr align="center" id = "policydepttr">
    		<td valign="top" width="50%"><div id = 'policydeptlists'></div></td>	    		
    		<td valign="top" width="50%"><div id = 'policydeptitems'></div></td>
    	</tr> 	
   	</table> 
  </body>
  <script type='text/javascript'>
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
	//���²�ҳ�����
	function GoMatchPage(mode){
		if(mode=="next"){
			$("#policytr").css({"display":"none"});
			$("#policysqltr").css({"display":"block"});
			//��ȡ���߱�׼�б�
			GetPolicySqlsHtml(currPolicyId,currPolicyName);
			//
		}else if(mode=="back"){
			$("#policytr").css({"display":"block"});
			$("#policysqltr").css({"display":"none"});
		}else if(mode=="nextsql"){
			$("#policydepttr").css({"display":"block"});
			$("#policysqltr").css({"display":"none"});
			//��ȡ���߻�����׼�б�
			GetPolicyDeptsHtml(currStandardId,currStandardDept,currStandardDesc,currStandardMoney,currStandardAccDesc); 
		}else if(mode=="backsql"){
			$("#policysqltr").css({"display":"block"});
			$("#policydepttr").css({"display":"none"});
			//��ȡ���߱�׼�б�
	        GetPolicySqlsHtml(currPolicyId,currPolicyName); 
		}
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
		currPolicyId = id;
		currPolicyName = name;
		//
		//ȡ��ҵ������
		GetPolicyItemHtml(currPolicyId);
	}
	//��������
	function ChioceDoSql(src,id,deptname,name,money,accdesc){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		currStandardId = id;
		currStandardDept = deptname;
		currStandardDesc = name;
		currStandardMoney = money;
		currStandardAccDesc = accdesc;
		//
		//ȡ�ñ�׼����
		GetPolicySqlItemHtml(currStandardId);
	}
	//��������
	function ChioceDoDept(src,id,name,money,accdesc){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		currDeptId = id;
		currDeptName = name;
		currDeptMoney = money;
		currDeptAccDesc = accdesc;
		//
		//ȡ�ñ�׼��������
		GetPolicyDeptItemHtml(currDeptId);
	}
	//ҵ���������̶���
	function policyFlowDefine(){
		//
		if(currPolicyId==""||currPolicyId==null){
			DisplayResult("��ʾ:û��ѡ��ǰҵ��!");				
			return;
		}		
		//var bWidth = document.body.clientWidth-30;  //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bWidth = document.body.clientWidth*0.6;  //�������
		var bHeight= document.body.clientHeight*0.6; //�����߶�
		var sArgu = "qpid="+currPolicyId+"&qpname="+currPolicyName+"";
		var oUrl = "/db/page/policy/manage/policycheckflow.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ���������̶���","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//ҵ������Ȩ�޶���
	function policyCheckPower(){
		//
		if(currPolicyId==""||currPolicyId==null){
			DisplayResult("��ʾ:û��ѡ��ǰҵ��!");				
			return;
		}		
		//var bWidth = document.body.clientWidth-30;  //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bWidth = document.body.clientWidth*0.6;  //�������
		var bHeight= document.body.clientHeight*0.6; //�����߶�
		var sArgu = "qpid="+currPolicyId+"&qpname="+currPolicyName+"";
		var oUrl = "/db/page/policy/manage/policycheckpower.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ������Ȩ�޶���","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//ҵ�������Ϣ����
	function policyInfo(){
		//
		if(currPolicyId==""||currPolicyId==null){
			DisplayResult("��ʾ:û��ѡ��ǰҵ��!");				
			return;
		}		
		var bWidth = document.body.clientWidth-30;  //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bHeight= 400; //�����߶�
		var sArgu = "qpid="+currPolicyId+"&qpname="+currPolicyName+"";
		var oUrl = "/db/page/policy/manage/policymanageinfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ�������Ϣ����","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//��ҵ���׼����ҳ��
	function sqlaction(){		
		//
		var suppname = "",notpname = "",acctype = "";
		if(currStandardId==""){
			DisplayResult("û��ѡ��ҵ���׼,�޷���������!");
			return; 
		}
		suppname= $("#ssuperpolicy").val();	
		notpname= $("#snotpolicy").val();		
		//
	   	//ѡ���ͥ���߳�Ա����
	    var isrdaccf = document.getElementById("rdaccf");
	    var isrdaccm = document.getElementById("rdaccm");
	    if(isrdaccf.checked == true){	    		
      	 	acctype = "family";	               
      	}else if(isrdaccm.checked == true){
      		acctype = "member";	 
      	}	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var sArgu = "qid="+currStandardId+"&qname="+currStandardDesc+"&qsuppname="+suppname+"&qnotpname="+notpname+"&qacctype="+acctype+"";
		var oUrl = "/db/page/policy/manage/policymanagesql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ���׼����","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}	
	//�򿪻�����׼����ҳ��
	function deptaction(){		
		//
		if(currDeptId==""){
			DisplayResult("û��ѡ�������׼,�޷���������!");
			return; 
		}
		//
	   	//ѡ���ͥ���߳�Ա����
	    var isrdaccf = document.getElementById("rdaccf");
	    var isrdaccm = document.getElementById("rdaccm");
	    if(isrdaccf.checked == true){	    		
      	 	acctype = "family";	               
      	}else if(isrdaccm.checked == true){
      		acctype = "member";	 
      	}			
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var sArgu = "qid="+currDeptId+"&qname="+currDeptName+"&qmoney="+currDeptMoney+"&qaccdesc="+currDeptAccDesc+"&qacctype="+acctype+"";					
		var oUrl = "/db/page/policy/manage/policymanagedeptsql.jsp?"+sArgu;
		jBox.open("iframe-jBoxID","iframe",oUrl,"���㹫ʽ����","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
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
                    oWidth:250,                              //�������ڿ��
                    oHeight:280                              //�������ڸ߶�
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
		
		$(function(){
	        //����
	        $("button").css({"display":"block","margin":"10px 0","width":"120px"});
	        $("button:first").css("background","red");
		});
		//��ѯ������׼
		function queryDept(mode){
			selquerydeptmode = mode;
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
		    if(selquerydeptmode=="qsql"){
		    	//ѡ����ò���
			    $("#sqdeptid").val(seldeptid);
			    $("#sqdeptname").val(seldeptfullname);
		    }else if(selquerydeptmode=="sql"){
		    	//ѡ����ò���
			    $("#sdeptid").val(seldeptid);
			    $("#sdeptname").val(seldeptfullname);
		    }else{
		    	//ѡ����ò���
			    $("#ddeptid").val(seldeptid);
			    $("#ddeptname").val(seldeptfullname);
		    }
		    
			closedept();
		}
		//�ÿջ���ѡ��[DeptTreeServlet�����ķ���]
		function cleardept(){
			if(selquerydeptmode=="qsql"){
		    	//ѡ����ò���
			    $("#sqdeptid").val("");
			    $("#sqdeptname").val("");
		    }else if(selquerydeptmode=="sql"){
		    	//ѡ����ò���
			    $("#sdeptid").val("");
			    $("#sdeptname").val("");
		    }else{
		    	//ѡ����ò���
			    $("#ddeptid").val("");
			    $("#ddeptname").val("");
		    }
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
</html>
