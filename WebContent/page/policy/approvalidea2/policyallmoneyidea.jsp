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
	String reqpid = request.getParameter("qpid");
	//
	String reqcountsql = request.getParameter("qcountsql");
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
	//����	
	reqcountsql = new String(reqcountsql.getBytes("ISO8859_1"), "UTF-8");	
	if (reqcountsql == null) {reqcountsql = "";}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>����ȫ������</title>
    
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
  	var pid = "";		//��ǰҵ��ID
  	var tabid = "0";	//����ҳ��ǩID
  	var allsql = "";	//ȫ����ѯSQL
  	//
	var accmode = "0";	//�û������޸ľ������ʶ
  	//
	var onecheck = "0";	//��һ����������
	var endcheck = "0";	//�������
	var report = "0";	//�ϱ�����(ȷ��������ʶ)
	//	
  	var sqlcount = 0;	 		//�ܻ���
	var sumpopcount = 0;		//���˿�
	var summoney = 0;		 	//������
	var sumolemoney = 0;		//�����ھ�����
	var sumnewmoney = 0;		//���ⷢ������
	//	
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
	
	//
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		pid =  "<%=reqpid%>";		 			//��ǰҵ��ID
		tabid = "<%=reqtabid%>";		 		//������ǩID	
		//	
		allsql = "<%=reqcountsql%>";		 	//ȫ����ѯSQL���	
		//
		sqlcount = "<%=reqsqlcount%>";	 		//�ܻ���
		sumpopcount = "<%=reqsumpopcount%>";	//���˿�
		summoney = "<%=reqsummoney%>";		 	//������
		sumolemoney = "<%=reqsumolemoney%>";	//�����ھ�����
		sumnewmoney = "<%=reqsumnewmoney%>";	//���ⷢ������
		//
		accmode = "<%=reqaccmode%>";			//�û������޸ľ������ʶ
		//
		onecheck = "<%=reqonecheck%>";		 	//��һ����������
		endcheck =  "<%=reqendcheck%>";		 	//�������
		report = "<%=reqreport%>";		 		//�ϱ�����(ȷ��������ʶ)			
		//
		//
		$("#btnok").get()[0].focus();
		//��Ϣ������
		IdeaInfoCount();		
		//��ȡ�����������ѡ���
		getCheckIdeaSelect();	
		//��ȡϵͳ����
		getSysTimeFormatDate();	
	}
  </script>
  <body  onload = "IniPage()">
  		<fieldset><legend>������Ϣ</legend>
	  		<div id = "ideainfocounttb"></div>
	  	</fieldset>
  		<fieldset><legend>�������</legend>    		
	  		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' id = 'moreideatb'>
	  			<tr valign='middle' class='mybackground myspace'>	  				
	  				<td width = "120" align='center' valign='middle'>�����������</td>
	  				<td align='left' valign='middle'>
	  					<div id = 'divresults'></div>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>������������</td>
	  				<td align='left' valign='middle'>
	  					<input type='text' size = '16' id = 'icheckdt' onFocus= 'setday(this)'></input>
	  				</td>
	  				<td width = "120" align='center' valign='middle'>�������(Ԫ)</td>
	  				<td align='left' valign='middle'>
	  					<input style = 'text-align: right;' type='text' size = '16' id = 'imoney' value = '0'></input>
	  				</td>
	  			</tr>
	  		</table>	  		
	  	</fieldset>
	  	<div align='center' id = 'divbtncheck' ><button class = 'btn' id = 'btnok' onclick="editCheckIdea()"> ȷ���������� </button></div>
  </body>
</html>
<script  type="text/javascript">
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
	function IdeaInfoCount(){	
		//	
		var html;
		html = "<table width='100%' cellpadding='0' cellspacing='0'>";	
  			html += "<tr class='colField' style = 'border-top-width: 1px;'>";	
  				html += "<td height='23'>�ܼ�ͥ����["+sqlcount+"]</td>";	
  				html += "<td height='23'>�ܱ����˿�["+sumpopcount+"]</td>";	
  				html += "<td height='23'>�ܼ�������["+ReplaceMoney(summoney)+"]</td>";	
  				html += "<td height='23'>�����ھ�����["+ReplaceMoney(sumolemoney)+"]</td>";	
  				html += "<td height='23' style = 'color: #6BA6FF;'>���ⷢ������["+ReplaceMoney(sumnewmoney)+"]</td>";	
  			html += "</tr>";	
  		html += "</table>";			
		//		
		ideainfocounttb.innerHTML=html;
	}	
</script>
<script  type="text/javascript">
	//��ȡϵͳ����
	function getSysTimeFormatDate(){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "getSysTimeFormatDate"             //action����
	        },
	        function(result) {                    	//�ص�����
	        	$("#icheckdt").val(result);   	
	        }
	    );
	}
</script>
<script type="text/javascript">
	//����ȷ��
	function editCheckIdea(){
		//
		var jresult = $("#resultlist").val();
		var jcheckdt = $("#icheckdt").val();
		var jmoney = $("#imoney").val();
		//
		if(jmoney=="0"){return;}
		//	
		//==================================================ת��
		allsql = encodeURI(allsql);
		//==================================================ת��
		//
	    $.post("/db/page/policy/approvalidea/policyideapage.do",            //������ҳ���ַ
	        {
	            action: "allMoneyCheckIdea",             //action����
	            jempid: empid,
	            jdeptid: deptid,
	            jdepth: depth,
	            jtabid: tabid,
	            jpid: pid,
	            jallsql: allsql,
	            jonecheck: onecheck,
	            jendcheck: endcheck,
	            jreport: report,
	            jresult: jresult,
	            jcheckdt: jcheckdt,
	            jmoney: jmoney
	        },
	        function(result) {                    	//�ص�����
	        	$("#divbtncheck").css({"display":"none"});
	        	alert(result);   	
	        }
	    );
	}
</script>
