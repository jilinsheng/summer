<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
	String thno = employee.getSysTOrganization().getDepth().toString();
%>
<%
	//
	String reqtabid = request.getParameter("qtabid");
	//
	String reqmode = request.getParameter("qmode");
	//
	String reqpid = request.getParameter("qpid");
	//
	String reqfmids = request.getParameter("qfmids");
	//
	String reqsqlcount = request.getParameter("qsqlcount");
	//
	String reqsumpopcount = request.getParameter("qsumpopcount");	
	//
	String reqsummoney= request.getParameter("qsummoney");	
	//
	String reqsumolemoney= request.getParameter("qsumolemoney");	
	//
	String reqsumnewmoney= request.getParameter("qsumnewmoney");
	//
	String reqaccmode= request.getParameter("qaccmode");
	//
	String reqonecheck= request.getParameter("qonecheck");	
	//
	String reqendcheck= request.getParameter("qendcheck");	
	//
	String reqreport= request.getParameter("qreport");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��������</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			font-family: "����";
			font-size: 12px;
		} 
		.pointer {
			cursor: pointer;
			color: #6BA6FF;
		}
		.legend {
			font-size: 12px;
			font-weight: lighter;
			color: #000000;
			text-align: center;
		}
		.btn { 
			BORDER-RIGHT: #002D96 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 
		    #002D96 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; FILTER: 
		    progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
		    StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); BORDER-LEFT: #002D96 
		    1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 0px; PADDING-BOTTOM: 0px;
		    BORDER-BOTTOM: #002D96 1px solid
	    }
	    #pagestatusdiv{
			position:absolute;
			z-index:2;
			color: #FF0099;
			font-weight: bold;
			font-size:14px;
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
	</style>
	<style type="text/css">
		.myspace{
			border-top:1px solid #DEEFFF;
			border-bottom:1px solid #DEEFFF;
			border-left:1px solid #DEEFFF;
			border-right:1px solid #DEEFFF;
			font-size:13px
		}
		.mybackground{
			color: #666666;
			background-color: #DEEFFF;
			font-size:12px;
		}
	</style>
  </head>
  <script  type="text/javascript">
  	//
	var empid = "",deptid = "",depth = "";
  	//
  	var mode = "one";	//��������(one/more)
  	var pid = "";		//��ǰҵ��ID
  	var tabid = "0";	//����ҳ��ǩID
  	var fmid = "";		//��ͥID
  	//	
  	var allsqlcount =0;
  	var allsumpopcount=0;
	var allsumolemoney=0;
	var allsumnewmoney=0;
	//
	var accmode = "0";	//�û������޸ľ������ʶ
	//
	var onecheck = "0";	//��һ����������
	var endcheck = "0";	//�������
	var report = "0";	//�ϱ�����(ȷ��������ʶ)
	//	
  	var sqlcount =0;
	var sumolemoney=0;
	var sumnewmoney=0;
	//
	//��ȡ����ԭ��ѡ
	function getRequestTypeCheckBox(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: "getRequestTypeCheckBox"            //action����
	        },
	        function(result) {                    	//�ص�����
	        	divqtypes.innerHTML = result;	        	
	        }
	    );
	}
	//��ȡ�����������ѡ���
	function getCheckIdeaSelect(){
		//
		var sname = "resultlist";
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: "getCheckIdeaSelect",             //action����
	            sname: sname,
	            stabid: tabid,
	            sonecheck: onecheck
	        },
	        function(result) {                    	//�ص�����
	        	divresults.innerHTML = result;	        	
	        }
	    );
	}
	//��ȡ�����˸�ѡ
	function getRequestPersonCheckBox(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: "getRequestPersonCheckBox",            //action����
	            deptid: deptid
	        },
	        function(result) {                    	//�ص�����
	        	divimans.innerHTML = result;	        	
	        }
	    );
	}
	//��ȡ��ѯXML
	function getIdeaResultForXml(){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: "getIdeaResultForXml",            //action����
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jpid: pid,
	            jaccmode: accmode,
	            jfmids: fmids
	            
	        },
	        function(result) {                    	//�ص�����
	        	ShowData(result); 	        	
	        }
	    );
	}
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		mode = "<%=reqmode%>";		 			//��������(one/more)
		pid =  "<%=reqpid%>";		 			//��ǰҵ��ID
		tabid = "<%=reqtabid%>";		 		//������ǩID	
		//	
		fmids = "<%=reqfmids%>";		 			//��ͥID			
		//
		allsqlcount = "<%=reqsqlcount%>";	 		//�ܻ���
		allsumpopcount = "<%=reqsumpopcount%>";	//���˿�
		allsummoney = "<%=reqsummoney%>";		 	//������
		allsumolemoney = "<%=reqsumolemoney%>";	//�����ھ�����
		allsumnewmoney = "<%=reqsumnewmoney%>";	//���ⷢ������
		//
		accmode = "<%=reqaccmode%>";		 	//�û��޸ľ������ʶ
		//
		onecheck = "<%=reqonecheck%>";		 	//��һ����������
		endcheck =  "<%=reqendcheck%>";		 	//�������
		report = "<%=reqreport%>";		 		//�ϱ�����(ȷ��������ʶ)	
		//
		//
		if(onecheck != "1"){	//���ǵ�һ������
			spanideaman.innerHTML="����������";
			$("#oneideatb").css({"display":"none"});			
		}else{			
			spanideaman.innerHTML="����������";
		}
		if(mode!="one"){		//���ǵ�������
			$("#oneideatb").css({"display":"none"});
			$("#ideastb").css({"display":"none"});
			$("#ideamanstb").css({"display":"none"});
		}
		//
		$("#btnok").get()[0].focus();
		//��Ϣ������
		IdeaInfoCount();
		//��ȡ����ԭ��ѡ
		//getRequestTypeCheckBox();
		//��ȡ�����������ѡ���
		getCheckIdeaSelect();
		//��ȡ�����˸�ѡ
		getRequestPersonCheckBox();	
		//��ȡϵͳ����
		getSysTimeFormatDate();
		//��ȡ��ѯXML
		getIdeaResultForXml();	
	}
  </script>
  <body  onload = "IniPage()">
  		<fieldset><legend>������Ϣ</legend>
	  		<div id = "ideainfocounttb"></div>
	  	</fieldset>
    	<fieldset><legend>�������</legend>
    		<!--
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'queryideatb'>  
	  			<tr valign='middle'>
	  				<td width = "120" align='center' valign='middle' class='mybackground myspace'>����ԭ��</td>
	  				<td align='left' valign='middle' class='myspace' colspan = '7'>
	  					<div id = 'divqtypes'></div>
	  				</td>
	  			</tr>	  			
	  		</table>
	  		-->
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'moreideatb'>
	  			<tr valign='middle' class='mybackground myspace' id = 'tridt'>
	  				<td width = "120" align='center' valign='middle'>������ʼ����</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'ibegdt' onFocus= 'setday(this)'></input>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>������������</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'ienddt' onFocus= 'setday(this)'></input>
	  				</td>
	  			</tr>	
	  			<tr valign='middle' class='mybackground myspace'>
	  				<td width = "120" align='center' valign='middle'>�����������</td>
	  				<td align='left' valign='middle'>
	  					<div id = 'divresults'></div>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>������������</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'icheckdt' onFocus= 'setday(this)'></input>
	  				</td>
	  			</tr>
	  		</table>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'ideastb'>
	  			<tr valign='middle' class='mybackground myspace'>
	  				<td width = "120" align='center' valign='middle'>�����������</td>
	  				<td align='left' valign='middle'>
	  					<textarea rows='2' style = 'width:100%' id = 'idesc'></textarea>
	  				</td>
	  			</tr>
	  		</table>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'oneideatb'>	  			  		
	  			<tr valign='middle' class='mybackground myspace'>
	  				<td width = "120" align='center' valign='middle'>�������(��)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imanall' value = '0'></input>
					</td>				
	  				<td width = "120" align='right' valign='middle'>ͬ��(��)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imanok' value = '0'></input>
					</td>
					<td width = "120" align='right' valign='middle'>��Ȩ(��)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imandel'  value = '0'></input>
					</td>
	  				<td width = "120" align='right' valign='middle'>��ͬ��(��)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imannot' value = '0'></input>
					</td>
	  			</tr>
	  		</table>
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'ideamanstb'>	  			
	  			<tr valign='middle'>
	  				<td width = "120" align='center' valign='middle' class='mybackground myspace'><span id = 'spanideaman'></span></td>
	  				<td align='left' height='23' valign='middle' class='myspace'>
	  					<div id = 'divimans'></div>
	  				</td>
	  			</tr>
	  		</table>
	  	</fieldset>
	  	<fieldset><legend>��������</legend>
	  		<div align='center' id = 'divideatb'></div>	  	
	  		<div id = "infocounttb"></div>
	  	</fieldset>
	  	<div align='center' id = 'divbtncheck' ><button class = 'btn' id = 'btnok' onclick="editCheckIdea()"> ȷ���������� </button></div>
	  	
  </body>
</html>
<script  type="text/javascript">
	//��ȡϵͳ����
	function getSysTimeFormatDate(){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "getSysTimeFormatDate"             //action����
	        },
	        function(result) {                    	//�ص�����
	        	$("#ibegdt").val(result);	
	        	$("#ienddt").val(result);
	        	$("#icheckdt").val(result);   	
	        }
	    );
	    //
	    $("#tridt").css({"display":"none"});
	}
</script>
<script type="text/javascript">
	//
	//
	function ShowData(data){
		var xmlDoc;	
		var xtable;	
		//		
		sqlcount=0;		
		sumolemoney=0;
		sumnewmoney=0;		
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//�ܻ���
			var countarr=root.selectNodes("/data/counts/sqlcount");
			sqlcount =countarr.item(0).firstChild.data;
			//���ھ��������			
			countarr=root.selectNodes("/data/counts/sumolemoney");
			sumolemoney =countarr.item(0).firstChild.data;			
			//�ⷢ���������
			countarr=root.selectNodes("/data/counts/sumnewmoney");
			sumnewmoney =countarr.item(0).firstChild.data;
					
			//
			//�����������
			var familyarr=root.selectNodes("/data/table/elements");
			xtable =familyarr.item(0).firstChild.data;
			divideatb.innerHTML=xtable;
			//
			//JS�������
			new TableSorter("ideatb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/
   						
		}else{	
			divideatb.innerHTML=data;
		}
		//��Ϣ������
		InfoCount();		
	}
	//��������ת��
	function ReplaceMoney(data){
		var s = data.toString();
        if(/[^0-9\.]/.test(s)) return "0.00";
        s=s.replace(/^(\d*)$/,"$1.");
        s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
        s=s.replace(".",",");
        var re=/(\d)(\d{3},)/;
        while(re.test(s))
                s=s.replace(re,"$1,$2");
        s=s.replace(/,(\d\d)$/,".$1");
        //return "��" + s.replace(/^\./,"0.");
        return s.replace(/^\./,"0.");       
    }
	//��Ϣ������
	function InfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField' style = 'border-top-width: 1px;' >";				
				html += "<td height='23'>С�Ƽ�ͥ����["+sqlcount+"]</td>";
	  			html += "<td height='23'>С�����ھ�����["+ReplaceMoney(sumolemoney)+"]</td>";	  			
	  			html += "<td height='23' style = 'color: #6BA6FF;'>С���ⷢ������["+ReplaceMoney(sumnewmoney)+"]</td>";
			html += "</tr>";
		html=html+"</table>";			
		//
		//			
		infocounttb.innerHTML=html;
	}
	//��Ϣ������
	function IdeaInfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>";	
  			html += "<tr class='colField' style = 'border-top-width: 1px;'>";	
  				html += "<td height='23' align='center' >�ܼ�ͥ����["+allsqlcount+"]</td>";	
  				html += "<td height='23' align='center' >�ܱ����˿�["+allsumpopcount+"]</td>";	
  				html += "<td height='23' align='center' >�ܼ�������["+ReplaceMoney(allsummoney)+"]</td>";	
  				html += "<td height='23' align='center' >�����ھ�����["+ReplaceMoney(allsumolemoney)+"]</td>";	
  				html += "<td height='23' align='center' style = 'color: #6BA6FF;'>���ⷢ������["+ReplaceMoney(allsumnewmoney)+"]</td>";	
  			html += "</tr>";	
  		html += "</table>";			
		//		
		ideainfocounttb.innerHTML=html;
	}	
</script>
<script type="text/javascript">
	//���¾�����
	function ChangMoneyRow(src,optid){
		var tpagemoney = 0;
		for (i=0;i<document.all.length;i++) {
			if (document.all[i].name=="money[]") {
				tpagemoney += Number(document.all[i].value);
			}
		}
		allsumnewmoney = Number(allsumnewmoney) - Number(sumnewmoney) + Number(tpagemoney);
		sumnewmoney = Number(tpagemoney);
		//��Ϣ������
		InfoCount();
		//��Ϣ������
		IdeaInfoCount();
		//���µ���������
		updateMoneyFromOptID(optid,src.value);	
	}
	//���¾�����
	function updateMoneyFromOptID(optid,money){
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: "updateMoneyFromOptID",             //action����
	            joptid: optid,
	            jmoney: money
	        },
	        function(result) {                    	//�ص�����
	        	alert(result);   	
	        }
	    );
	}
</script>
<script type="text/javascript">
	//����ȷ��
	function editCheckIdea(){
		//
		var actionmode = "addCheckIdea";
		//
		var jdesc = "",jmanids = "";
		var jmanall ="0",jmanok = "0",jmandel = "0",jmannot = "0";		
		//
		var jbegdt = $("#ibegdt").val();
		var jenddt = $("#ienddt").val();
		//
		var jresult = $("#resultlist").val();
		var jcheckdt = $("#icheckdt").val();
		//��������
		if(mode == "one"){				//��������
			//
			actionmode = "addCheckIdea";
			//		
			jdesc = $("#idesc").val();
			jmanids = "";//������
			//
			for (i=0;i<document.all.length;i++) {
				if (document.all[i].name=="cbxp[]") {
					if(document.all[i].checked == true){
						var tmpid = document.all[i].id;
						tmpid = tmpid.substring(1,tmpid.length);	//id��q����
						if(jmanids == ""){
							jmanids = tmpid;
						}else{
							jmanids += "," + tmpid;
						}
					}
				}
			}
			//
			if(onecheck != "1"){	//���ǵ�һ������
				jmanall = "0";
				jmanok = "0";
				jmandel = "0";
				jmannot = "0";
			}else{					//�ǵ�һ������
				jmanall = $("#imanall").val();
				jmanok = $("#imanok").val();
				jmandel = $("#imandel").val();
				jmannot = $("#imannot").val();
			}
			//==================================================ת��
			jdesc = encodeURI(jdesc);		
			//==================================================ת��
		}else{				//��������
			actionmode = "updateCheckIdea";
		}
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: actionmode,             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jtabid: tabid,
	            jpid: pid,
	            jfmids: fmids,
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jreport: report,
	            jbegdt: jbegdt,
	            jenddt: jenddt,
	            jresult: jresult,
	            jcheckdt: jcheckdt,
	            jdesc: jdesc,
	            jmanall: jmanall,
	            jmanok: jmanok,
	            jmandel: jmandel,
	            jmannot: jmannot,
	            jmanids: jmanids
	        },
	        function(result) {                    	//�ص�����
	        	$("#divbtncheck").css({"display":"none"});
	        	alert(result);   	
	        }
	    );
		//
		/*
		if(onecheck != "1"){	//���ǵ�һ������
			//
		    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
		        {
		            action: "updateCheckIdea",             //action����
		            jempid: empid,
		            jdeptid: deptid,
		            jdepth: depth,
		            jtabid: tabid,
		            jpid: pid,
		            jfmids: fmids,
		            jonecheck: onecheck,
		            jendcheck: endcheck,
		            jreport: report,
		            jbegdt: jbegdt,
		            jenddt: jenddt,
		            jresult: jresult,
		            jcheckdt: jcheckdt
		        },
		        function(result) {                    	//�ص�����
		        	$("#divbtncheck").css({"display":"none"});
		        	alert(result);   	
		        }
		    );
		}else{				//��һ������
			if(mode == "one"){					//��������
				var jdesc = $("#idesc").val();
				var jmanall = $("#imanall").val();
				var jmanok = $("#imanok").val();
				var jmandel = $("#imandel").val();
				var jmannot = $("#imannot").val();
				//
				//==================================================ת��
				jdesc = encodeURI(jdesc);		
				//==================================================ת��
				var jmanids = "";//������
				//
				for (i=0;i<document.all.length;i++) {
					if (document.all[i].name=="cbxp[]") {
						if(document.all[i].checked == true){
							var tmpid = document.all[i].id;
							tmpid = tmpid.substring(1,tmpid.length);	//id��q����
							if(jmanids == ""){
								jmanids = tmpid;
							}else{
								jmanids += "," + tmpid;
							}
						}
					}
				}
				//
			    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
			        {
			            action: "addCheckIdea",             //action����
			            jempid: empid,
			            jdeptid: deptid,
			            jdepth: depth,
			            jtabid: tabid,
			            jpid: pid,
			            jfmids: fmids,
			            jonecheck: onecheck,
			            jendcheck: endcheck,
			            jreport: report,
			            jbegdt: jbegdt,
			            jenddt: jenddt,
			            jresult: jresult,
			            jcheckdt: jcheckdt,
			            jdesc: jdesc,
			            jmanall: jmanall,
			            jmanok: jmanok,
			            jmandel: jmandel,
			            jmannot: jmannot,
			            jmanids: jmanids
			        },
			        function(result) {                    	//�ص�����
			        	$("#divbtncheck").css({"display":"none"});
			        	alert(result);   	
			        }
			    );
			}else{									//��������
				//
			    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
			        {
			            action: "updateCheckIdea",             //action����
			            jempid: empid,
			            jdeptid: deptid,
			            jdepth: depth,
			            jtabid: tabid,
			            jpid: pid,
			            jfmids: fmids,
			            jonecheck: onecheck,
			            jendcheck: endcheck,
			            jreport: report,
			            jbegdt: jbegdt,
			            jenddt: jenddt,
			            jresult: jresult,
			            jcheckdt: jcheckdt
			        },
			        function(result) {                    	//�ص�����
			        	$("#divbtncheck").css({"display":"none"});
			        	alert(result);   	
			        }
			    );
			}
		}
		*/
	}
</script>
<script type="text/javascript">
	//�򿪼�ͥ�鿴ҳ��
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣ��Ƭ&AfterIframeOpen()","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}	
</script>
<script type="text/javascript">
	//����������ҳ��
	function GetCheckIdeaFlow(fmid){
		var bWidth = document.body.clientWidth-30;   	//�������
		var bHeight= document.body.clientHeight-80; 	//�����߶�
		var sArgu = "qfmids="+fmid+"&qpid="+pid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideaflow.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��������&AfterIframeOpen()","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
</script>
<script type="text/javascript">
	//��ȡ��ͥ����ҵ�������ϸ��Ϣ
	function GetCheckPolicyInfosHtml(fmid,memid){
	    //
		//var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var bWidth= 450; //�������
		var sArgu = "qpid="+pid+"&qfmid="+fmid+"&qmemid="+memid+"";
		var oUrl = "/db/page/policy/approvalidea/policyideainfo.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ�������Ϣ��ϸ&AfterIframeOpen()","width="+bWidth+",height="+bHeight
			+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
</script>
<script type="text/javascript">
	//����ڰ�ť
	function AfterIframeOpen(){
		$("#btnok").get()[0].focus();
	}
</script>
