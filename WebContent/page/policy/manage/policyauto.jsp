<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%	
	//ҵ��ѡ��
	String reqpolicy = request.getParameter("qpolicy");	
	
	if (reqpolicy == null) {
		//Ĭ��Ϊ��
		reqpolicy = "-1";    //ѡ��ҵ��[��ѡ]
	}
	//
	//����ҵ������URLDecoder
	//�Ӳ�ѯ����ղ�ѯ�ֶ�select
	String reqselect = request.getParameter("qselect");
	reqselect = new String(reqselect.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqselect == null) {
		//Ĭ��Ϊ��
		reqselect = "";    //�ֶ�
	}
	//�Ӳ�ѯ����ղ�ѯ��from
	String reqfrom = request.getParameter("qfrom");
	reqfrom = new String(reqfrom.getBytes("ISO8859_1"), "GB18030");//����	
	if (reqfrom == null) {
		//Ĭ��Ϊ��
		reqfrom = "";    //�ձ�
	}
	//�Ӳ�ѯ����ղ�ѯ����where
	String reqwhere= request.getParameter("qwhere");
	reqwhere = new String(reqwhere.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqwhere == null) {
		//Ĭ��Ϊ��
		reqwhere = "";    //������
	}
	//�Ӳ�ѯ����ղ�ѯ����order
	String reqorder= request.getParameter("qorder");
	reqorder = new String(reqorder.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqorder == null) {
		//Ĭ��Ϊ��
		reqorder = "";    //������
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
    
    <title>ҵ���Զ�ɸѡ����</title>
    
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
	
	<style type="text/css">
 <!--
  	body {
		margin: 0;
		padding: 0;
		font-family: "����";
		font-size: 12px;
	}
	a{
		text-decoration: none;
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
	.colTtems {		
		font-size: 12px;
		cursor: pointer;
		color: #6BA6FF;		
	}
	.pageTtems {
		color: #660099;		
	}
	.pointer {
		cursor: pointer;
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
	.mystyle {	
		line-height: 2;	
		font-size: 12px;
		color: #6BA6FF;
		height: 20px;		
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
  
  <script  type="text/javascript">
	//
	//		    
	var vleft = 0;  //�������
	var vtop= 0; //�����߶�
		
	var empid = "";        //��ǰ��¼�û���� 
	var deptid = "";        //��ǰ��¼���� 
	//
	var selmode = "2";		//��ǰ�û�ѡ����������ģʽ//�����ඨ��
	//
	var selselect = "";    	//ѡ���ѯ����ֶ�
	var selfrom = "";    	//ѡ���ѯ�����
	var selwhere = "";    	//ѡ���ѯ�������
	var selorder = "";    //ѡ���ѯ�������
	
	//
	var selpolicy = "-1";    	//ѡ��ҵ��
	//
	//
	var xmldata;	//XML����ʵ��
	//
	var colnum = 0,rownum =0;//����/����		
	//
	var sqlcount = "0";//�ܼ�¼��
	var sqlolepagecount = "0";//����ҳ��
	var sqlolepageselect = "";//��ҳ��ѡ��������
	var sqlpagecount = "0";//��ҳ��
	var sqlpagenum = "16";//ÿҳ��¼��
	//
	var sqlselpage = 1;//ѡ��ҳ
	//
	var selbegpage = 0;		//��ҳ��ʼ
	var selendpage = sqlpagenum;//��ҳ����	
	
	//
	//		
	//����ת��[�߼�����ת����������]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,tmode,tbegpage,tendpage;
	    var tdept;  
	    var tpolicy; 
	    //
	    //			    
		DisplayPageStatus(); 
	    //		   
	    //      
	    tselect = selselect;  
	    tfrom = selfrom;
	    twhere = selwhere;
	    torder = selorder;	    
	    //������ѯģʽ��������ģ�鶨��tmode
	    tmode = "1";//ģʽ0ִ�в�ѯ1��ȡSQL���
	    tbegpage = selbegpage;
	    tendpage = selendpage;
	    //����
	    tdept = deptid; 
	    //
	    tempid = empid;
	    //ҵ��ѡ��		    		          		
	    tpolicy = selpolicy;
	    //
	    //
	    $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
	        {
	            action: "getphysql", //action����
	            mode: "getAutoCheckFamilySql",
	            tselect: tselect,   //����
	            tfrom: tfrom,   //����
	            twhere: twhere,   //����
	            torder: torder, //����
	            tmode: tmode,
	            tbegpage: tbegpage,
	            tendpage: tendpage,  //������ҳ 
	            tdept: tdept,
	            tempid: tempid,	            
	            tpolicy: tpolicy         
	        },
	        function(result) {   //�ص�����		        	
	        	//�����������
     			HiddenPageStatus();  	        	
	  			//
	          	if(result=="0"){
	          		//��ѯ��䲻�Ϸ� 
	             	requestcon.innerHTML="��ǰ����ҵ��û��������������!";	
	          	}else if(result=="-1"){                 
	             	//��ѯ��䲻�Ϸ� 
	             	requestcon.innerHTML="��ǰ��¼�û��������Ǹ�ҵ���һ����������!";	             	      
	          	}else if(result=="-2"){ 
	          		//��ѯ��䲻�Ϸ� 
	             	requestcon.innerHTML="��ǰ����ҵ��û�����ñ�׼!";	
	          	}else{
	            	ShowData(result); 
	          }                             
	    });       
    }
	//
	//ȡ�õ�ǰҵ������
	function GetPolicyName(pardivid){
	  	//      
	  	var pardiv = document.getElementById(pardivid);
	  	var pid = selpolicy;
	  	if(pid=="-1"){//[��]ѡ��ҵ��
	  		pardiv.innerHTML = "��ǰҵ��:��ѡ";
	  		return;		  		
	  	}
	  	//
	  	$("#"+pardivid).empty();//���		  	
	  	//      
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getPolicyName", //action����
		        pid: pid                            
		    },
		    function(result) {   //�ص�����		    	
		    	pardiv.innerHTML = "��ǰѡ��ҵ��["+result + "]��������";          	  					      	                                         
		    }
		);        
	}		
	//ȡ��ҵ��ѡ��
	function GetCheckPolicyChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getCheckPolicyChoice", //action����
		        sname: sname //����                              
		    },
		    function(result) {   //�ص�����		    	
		      	pardiv.innerHTML = result;
		      	//ҵ��ѡ��ҳ�����������		          	        	
		      	if(selpolicy!="-1"){		      				
		      		$("#"+sname).val(selpolicy);			      		
		      	}else{
		      		selpolicy = $("#"+sname).val();		      		
		      	}
		      	//ҵ������
				GetPolicyName("spanpolicyitem");
				//
				//����ת��[�߼�����ת����������]
    			GetPhySql();
    			//
				//�����¼�
				$("#"+sname).change(function(){
												selpolicy = $("#"+sname).val();
												GetPolicyName("spanpolicyitem");
												//
												selbegpage = 0;		//��ҳ��ʼ
											 	selendpage = sqlpagenum;//��ҳ����
											 	//
												//ȡ�������ѯ�����ִ�з���XML��ʽ		  
												GetPhySql();
											  }
									);	
				      	                                         
		    }
		);        
	}		
	
	//��ʾҳ��״̬div
	function DisplayPageStatus() {
		//
    	vleft = document.body.clientWidth/2;  //�������
		//vtop= document.body.clientHeight/2; //�����߶�
		vtop= $("#spanpolicyitem").offset().top+20;   	
    	//
    	//$("#pagestatusdiv").css({"left":vleft,"top":vtop});	
	    var pagestatusdiv = $("#pagestatusdiv");            //��ȡ��ʾ��Ϣdiv		   		    
	    $(pagestatusdiv).fadeIn();                      //������Ϣ		    
	}		
	//����ҳ��״̬div
	function HiddenPageStatus() {
	    $("#pagestatusdiv").fadeOut();                  //������Ϣ
	}			
	//��ʼ��ҳ��
    function IniPage(){
	    //
    	vleft = document.body.clientWidth/2;  //�������
		//vtop= document.body.clientHeight/2; //�����߶�
		vtop= $("#spanpolicyitem").offset().top+20;			
    	//��ǰ��¼�û����    
		empid = "<%=empno%>";     
		//��¼����    
		deptid = "<%=onno%>";    //��ǰ��¼���� 
		//
		//ѡ��ҵ���ѯ	
		selpolicy = "<%=reqpolicy%>";		 //ҵ��ѡ��
		//
		selselect = "<%=reqselect%>";		 //��ѯѡ���ֶ�
		selfrom = "<%=reqfrom%>";		 //��ѯѡ���
		selwhere = "<%=reqwhere%>";		 //��ѯѡ������	
		selorder = "<%=reqorder%>";		 //��ѯѡ������
		//
	    //ҵ��ѡ��
        GetCheckPolicyChoice("choicepolicy","listpolicy");		
	   		        	   
    }
  </script>    
  </head>
  
  <body onload = "IniPage()"> 
  		<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr class = 'mybackground'>	   		
	   			<td valign="middle" align="right" class = "mystyle">ҵ��ѡ��:</td>
	   			<td valign="middle" align="left" width="20%" class = "mystyle"><div id = "choicepolicy"></div></td>
	   			<td valign="middle" align="left" class = "mystyle">
	   				<span  class = "pointer colTtems" id = "spanpolicyitem" onclick="CallStandardsDialog()"></span>
	   			</td>	   			
	   		</tr>	   		
	    	<tr align="center">
	    		<td valign="top" colspan = "3" class = "myspace"><div id="requestcon"> </div></td>	
	    	</tr>	    	    	
    	</table>
    	<!-- <div align="center"><input id = "btnclose" type="button" value=" �� �� "onclick="window.close();"></input></div> -->
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div></td>
	   		</tr>
   		</table> 
  </body>
</html>
<script type="text/javascript">
	//
	//
	function ShowData(data){
		var xmlDoc;
		//ȡ����ͷ
		var html= "",fmid = "";		
		//
		xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
		xmlDoc.async = false;
		if(xmlDoc.loadXML(data)){
			//
			var root =xmlDoc.documentElement;
			//�ܼ�¼��
			var countarr=root.selectNodes("/data/count/num");
			sqlcount =countarr.item(0).firstChild.data;
			//��ҳ��
			var j1 = sqlcount/sqlpagenum;
			var j2=Math.round(j1);
			if(j1>j2){
				sqlpagecount = j2+1;
			}else if(j1<=j2){
				sqlpagecount = j2;
			}
				
			//������
			var headarr=root.selectNodes("/data/ehead/cell");
			//
			colnum = headarr.length+1;
			//
			html += "<table id = 'requesttb' width='100%' cellpadding='0' cellspacing='0'>"
			html += "<tr class='colField'>";
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				if(i==headarr.length-2){
					//���ػ���ID
				}else{
					var temp ="<td height='23'>"+ss[1]+"</td>";
					html=html+temp;
				}
				
			}
			html += "<td>��ز���</td>";
			html=html+"</tr>";
			//
			//ȡ����¼ֵ
			xmldata = root.selectNodes("/data/list/entity");
			//
			//
			var rows=root.selectNodes("/data/list/entity");						
			//
			rownum = rows.length;
			//
			for(var i=0;i<rows.length;i++){
				var row = rows.item(i).childNodes;
				//
				fmid = row.item(0).firstChild.data;
				//									
				//		
				var temp="<tr ";						
				//��˫��
				var mathrow = i%2;				
				if(mathrow == 1){//������ɫ
					temp +=" style = 'background: #F2F5F7;'>";	
				}else{
					temp +=">";	
				}			
					//����ֵ	
					for (var j=1;j<row.length;j++){
						if(j==1){
							//��ͥ�����
							var temp1="<td height='23' class='colValue'>"+
								"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
							temp=temp+temp1;
						}else if(j==row.length-2){
							//���ػ���ID
						}else{
							var temp1="<td height='23' class='colValue'>"+
							row.item(j).firstChild.data+"</td>";
							temp=temp+temp1;
						}
					}
					//
					temp += "<td width='88px' height='23' class='colValue'>";
						temp += "<span  class = 'pointer'><img src='/db/page/images/checkfamily.png' alt= '�༭��ͥ' onclick= \"infoeditaction('"+fmid+"')\"/>&nbsp;</span>";						
						temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '�߷õ���' onclick=\"CallInterviewDialog('"+fmid+"')\"/>&nbsp;</span>";											
					temp += "</td>";
				temp=temp+"</tr>";
				
				//
				html=html+temp
				//
				
			}
			//			
			html=html+"</table>";
			//
			//�Ƿ��м�¼
			if(rownum==0){
				html += "<table width='100%' cellpadding='0' cellspacing='0'>"
					html += "<tr>";				
						html += "<td height='25' class='colValue'>�޲�ѯ���</td>";
					html += "</tr>";
				html=html+"</table>";
			}
			//
			//			
			//	
			html += "<table width='100%' cellpadding='0' cellspacing='0'>"
			
			html += "<tr class='colField'>";				
			html += "<td height='23'>";
				html += "��["+sqlcount+"]����¼  ��["+sqlpagecount+"]ҳ  ";					
				if(sqlselpage==1){
					html += "<span>��ҳ</span> ";
					html += "<span>��һҳ</span>�0�2";
				}else{
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('1')\">��ҳ</span> ";
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('2')\">��һҳ</span>�0�2";
				}
				if(sqlselpage==sqlpagecount){	
					html += "<span>��һҳ</span> ";
					html += "<span>βҳ</span>�0�2";
				}else{
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('3')\">��һҳ</span> ";
					html += "<span class = 'pointer pageTtems' onclick=\"ExePageFlag('4')\">βҳ</span> ";
				}	
				html += "ת��";
					//ѡ��ҳ����ѡ���
					html += "<span id = \"spanpages\"></span> ";
				html += "  ÿҳ<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>����¼</td>";
			html += "</tr>";		
			
			html=html+"</table>";			
			//
			
			//
			requestcon.innerHTML=html;
			
			//��ҳѡ��������			
			spanpages.innerHTML=GetPageGo();
			//	
			//ѡ��ҳ
			$("#selectpage").val(sqlselpage);
			//ÿҳ��ʾ����
			$("#divpagerow").val(sqlpagenum);
			//
			//
			//
			//
			//JS�������
			new TableSorter("requesttb");	
			/*
			new TableSorter("tb2", 0, 2 , 5, 6);
			new TableSorter("tb3").OnSorted = function(c, t)
			{
				alert("table is sorted by " + c.innerHTML + " " + (t ? "Asc" : "Desc"));
			}
			*/	
			//		
		}
		else{			
			requestcon.innerHTML="�޲�ѯ���";			
	
		}
	}
	//ѡ��ҳ�ı���
	function GetPageGo(){
		var html;
		html = "��<input type='text' id = 'selectpage' size = '3'>ҳ</input>";
		html += "<input type='button' value='Go' onclick=\"ExePage()\"></input>";
		return html;
	}
	//����ѡ��ҳ��ѯ
	function ExePage(){
		var stpage = $("#selectpage").val();
		sqlselpage = parseInt(stpage);
		//�����ҳ����
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;	
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();		
	}
	//ѡ��ҳ��ѯ
	function ExePageFlag(pageflag){	
		//
		//
	    
		$("#resultstatusdiv").css({"left":vleft,"top":vtop});		
		//	
		//
		var flag = "1";//��ҳ��ʶ
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//��ҳ
			if(sqlselpage==1){
				//			    
				DisplayResult("�Ѿ�����ҳ!!!"); 		   
			    //  
				//alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//��ҳ
			if(sqlselpage==1){
				//	
				DisplayResult("�Ѿ�����ҳ!!!"); 		   
			    //  
				//alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//��ҳ
			if(sqlselpage==sqlpagecount){
				//	
				DisplayResult("�Ѿ���βҳ!!!"); 		   
			    // 
				//alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//ҳβ
			if(sqlselpage==sqlpagecount){
				//	   
				DisplayResult("�Ѿ���βҳ!!!"); 		   
			    // 
				//alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlpagecount;			
		}
		
		//�����ҳ����
		selbegpage = (sqlselpage-1) * sqlpagenum +1;
		selendpage = sqlselpage * sqlpagenum;		
		//alert("b: "+ selbegpage + " e: " + selendpage);
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();		
	}
	//����ҳ��ʾ����
	function ChangPageRow(src){
		//
		sqlpagenum = src.value;
		//
		selbegpage = 0;		//��ҳ��ʼ
	 	selendpage = sqlpagenum;//��ҳ����
	 	sqlolepagecount = 0;//�ɷ�ҳѡ��������
	 	sqlselpage = 1;//��ʼ��һҳ
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
	}
</script>
<script type="text/javascript">
	//��ҵ�񵵴α�׼����
	function CallStandardsDialog()
	{
		/*
		var myobj = new Array();
		myobj[0] = selpolicy;
		if(selpolicy=="-1"){//[��]ѡ��ҵ��		  		
	  		return;		  		
	  	}				
		showModalDialog("/db/page/policy/manage/policystandards.jsp",myobj,"status:false;dialogWidth:700px;dialogHeight:400px");
		*/
		if(selpolicy=="-1"){//[��]ѡ��ҵ��		  		
	  		return;		  		
	  	}
		var bWidth = document.body.clientWidth-30;   //�������
		//var bHeight= document.body.clientHeight-80; //�����߶�
		var bHeight= 400; //�����߶�
		var sArgu = "qpid="+selpolicy+"";
		var oUrl = "/db/page/policy/manage/policystandards.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"ҵ�񵵴α�׼��ϸ","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		
	}
	//�����������ѯҳ��
	function CallRequestDialog(fmid){
		var afrom = "INFO_T_FAMILY",awhere = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		requestaction(afrom,awhere);
	}
	//���߷ü�¼��ѯҳ��
	function CallInterviewDialog(fmid){		
		var afrom = "",awhere = "",aorder = "";
		awhere = "INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var sArgu = "qfrom="+afrom+"&qwhere="+awhere+"&qorder="+aorder+"";
		var oUrl = "/db/page/policy/manage/policyinterview.jsp?"+sArgu+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ�߷ü�¼�Ǽ�","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//�򿪼�ͥ��Ϣҳ��
	function infoaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�
		var oUrl = "/db/page/info/card/newfamilyinfocard.do?entityId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣ��Ƭ","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");			
	}
	//�򿪼�ͥ�༭ҳ��
	function infoeditaction(afmid){	
		var bWidth = document.body.clientWidth-30;   //�������
		var bHeight= document.body.clientHeight-80; //�����߶�		
		var oUrl = "/db/page/info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId="+afmid+"";
		jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ��Ϣά��","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
	}
	//		
</script>
