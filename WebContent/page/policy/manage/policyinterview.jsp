<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
	//����ҵ������URLDecoder
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�߷ü�¼����</title>
    
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
	.menu, .menu li {
		margin: 0;
		padding: 0;
		height: 20px;
		list-style: none;
		overflow: hidden;
		text-align: center;					
	}
	.menu {
		border-bottom: 1px solid #DEEFFF;		
	}
	.menu .default {
		width: 86px;
		float: left;
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		background: url('/db/page/images/checkdefault.jpg') no-repeat;
	}
	.menu .active {
		width: 86px;
		float: left;
		font-size: 12px;
		line-height: 1.8;
		margin-left: 0px;
		cursor: pointer;		
		color: #FFFFFF;
		background: url('/db/page/images/checkactive.jpg') no-repeat;
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
		font-size: 12px;	
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
		background: url('/db/page/images/titmember.gif');
	}
	.mystyle {		
		font-size: 12px;
		font-weight: bold;	
		color: #666666;		
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
		var empid = "";        //��ǰ��¼�û���� 
		var deptid = "";        //��ǰ��¼���� 		
		//
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�		
		//
		var selselect = "";    	//ѡ���ѯ����ֶ�
		var selfrom = "";    	//ѡ���ѯ�����
		var selwhere = "";    	//ѡ���ѯ�������
		var selorder = "";    //ѡ���ѯ�������
		//
		var selfmid = "";	//ѡ���ͥID
		//
		var seltabwhere = "";    	//ѡ���ͥ��ѯ�߷�
		//
		var xmldata;	//XML����ʵ��
		//
		var colnum = 0,rownum =0;//����/����		
		//
		var sqlcount = "0";//�ܼ�¼��
		var sqlolepagecount = "0";//����ҳ��
		var sqlolepageselect = "";//��ҳ��ѡ��������
		var sqlpagecount = "0";//��ҳ��
		var sqlpagenum = "18";//ÿҳ��¼��
		//
		var sqlselpage = 1;//ѡ��ҳ
		//
		var selbegpage = 0;		//��ҳ��ʼ
		var selendpage = sqlpagenum;//��ҳ����
		//
		//
		var sqltabid = "1";//ѡ���ǩID
		//
		var sqlbegdt = "";//��ʼ����
		var sqlenddt = "";//��������
		//
		
		//����ת��[�߼�����ת����������]
	    function GetPhySql(){	    	
	    	var mode;    
		    var tselect,tfrom,twhere,torder,tmode,tbegpage,tendpage;
		    var tdept,ttabid,tbegdt,tenddt;  
		    //      
		    tselect = selselect;  
		    tfrom = selfrom;
		    twhere = selwhere;
		    torder = selorder;    
		    //��ͥ�߷ò�ѯ
		    twhere += seltabwhere;	
		    //������ѯģʽ��������ģ�鶨��tmode
		    tmode = "1";//ģʽ0ִ�в�ѯ1��ȡSQL���
		    tbegpage = selbegpage;
		    tendpage = selendpage;
		    //����
		    tdept = deptid;
		    //
		    ttabid = sqltabid;
		    tbegdt = sqlbegdt;
		    tenddt = sqlenddt;
		    //
		    mode = "interviewfamilysql";
		    //
		    //		    
			DisplayPageStatus(); 
			//
		    //
		    $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
		        {
		            action: "getphysql", //action����
		            mode: mode,   //����
		            tselect: tselect,   //����
		            tfrom: tfrom,   //����
		            twhere: twhere,   //����
		            torder: torder, //����
		            tmode: tmode,
		            tbegpage: tbegpage,
		            tendpage: tendpage,  //������ҳ
		            tdept: tdept,
		            ttabid: ttabid,
		            tbegdt: tbegdt,
		            tenddt: tenddt
		        },
		        function(result) {   //�ص�����	        	
		        	//�����������
      				HiddenPageStatus();  	        	
		  			//	        	
		  			//
		          	if(result=="-1"){                
		             	//��ѯ��䲻�Ϸ�          
		          	}else{
		            	if(mode=="interviewfamilysql"){
			            	//�߷ü�¼��ͥ��ѯȡ���physql                  
			              	//			  		
		          			ShowData(result); 		          			
			            }
		          }                             
		    });       
	    }
	   
	    //��ʾҳ��״̬div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/2+30;  //�������
			//vtop= document.body.clientHeight/2; //�����߶�
			vtop= 0;
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
			//
	    	vleft = document.body.clientWidth/2+30;  //�������
			//vtop= document.body.clientHeight/2; //�����߶�
			vtop= 0;
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
		//���߷ü�¼����
		function CallIntrViewIdeaDialog(mode,qid,fmno,fmname)
		{
			//������ͥID
			var bWidth = document.body.clientWidth-30;  //�������
			//var bHeight= document.body.clientHeight-80; //�����߶�
			var bHeight= 300; //�����߶�
			var sArgu = "qmode="+mode+"&qid="+qid+"&qfmno="+fmno+"&qfmname="+fmname+"";
			var oUrl = "/db/page/policy/manage/policyinterviewidea.jsp?"+sArgu+"";
			jBox.open("iframe-jBoxID","iframe",oUrl,"��ͥ�߷ü�¼�Ǽ�","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
				
		}
		//��ʼ��ҳ��
	    function IniPage(){	
	    	//��ǰ��¼�û����    
			empid = "<%=empno%>";     
			//��¼����    
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			//
	    	vleft = document.body.clientWidth/2+30;  //�������
			//vtop= document.body.clientHeight/2; //�����߶�
			vtop= 0;
			//
			selfrom = "<%=reqfrom%>";		 //��ѯѡ���
			selwhere = "<%=reqwhere%>";		 //��ѯѡ������	
			selorder = "<%=reqorder%>";		 //��ѯѡ������
		 	//
			selbegpage = 0;		//��ҳ��ʼ
		 	selendpage = sqlpagenum;//��ҳ����		 	
		 	//
			//��ѯ��ʾҳ��
			QueryInfoTab();			 			
	    }
  	</script>
  </head>
  
  <body onload = "IniPage()">
  		<script type="text/javascript" src="/db/page/js/Calendar2.js"></script>
  
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div> 
  		
    	<table class="tab" width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr>
	   			<td valign="top" class = "myspace" >
	   				<div id = "choicefamilyrequest"></div>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td valign="top" class = "myspace">
	   				<div id = "divrequestdt"></div>			   		
				</td>
	   		</tr>	   		
	    	<tr align="center">
	    		<td valign="top" class = "myspace"><div class="requestcon" id="requestcon"> </div></td>	
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
		var html= "",fmid = "",fmno = "",fmname = "";		
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
			if(sqltabid=="2"){//�߷ò�ѯѡ��
				html += "<td height='25'>����</td>";
			}else{
				html += "<td height='25'>�߷�</td>";
			}
			
			//
			for (var i=1;i<headarr.length;i++){
				var head =headarr.item(i);
				var ss =head.firstChild.data.split(".");
				if(sqltabid=="2"){//�߷ò�ѯѡ��
					if(i==3){//�߷ü�¼ID������
						var temp ="<td style='display:none' height='23'>"+ss[1]+"</td>";
						html=html+temp;
					}else{
						var temp ="<td height='25'>"+ss[1]+"</td>";
						html=html+temp;
					}
				}else{
					if(i==5){//���������
						var temp ="<td style='display:none' height='23'>"+ss[1]+"</td>";
						html=html+temp;
					}else if(i==6){//��������FULLNAME��
						var temp ="<td height='25'>����</td>";
						html=html+temp;
					}else{
						var temp ="<td height='25'>"+ss[1]+"</td>";
						html=html+temp;
					}
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
				fmid = row.item(0).firstChild.data;//��ͥID
				fmno = row.item(1).firstChild.data;//��ͥ���
				fmname = row.item(2).firstChild.data;//��������
				//
				//
				if(sqltabid=="2"){//�߷ò�ѯѡ��
					viewid = row.item(3).firstChild.data;
				}
				//										
				//
				var temp="<tr>";
					if(sqltabid=="2"){//�߷ò�ѯѡ��
						temp += "<td width='32px'height='23' class='colValue'>";						
							temp += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"RequestNow(this,'"+viewid+"','"+fmno+"','"+fmname+"','edit')\" />";									
						temp += "</td>";
					}else{
						temp += "<td width='32px'height='23' class='colValue'>";						
							temp += "<img src='/db/page/images/check1.jpg' id='img' onclick=\"RequestNow(this,'"+fmid+"','"+fmno+"','"+fmname+"','add')\" />";									
						temp += "</td>";
					}				
					
					for (var j=1;j<row.length;j++){
						if(sqltabid=="2"){//�߷ò�ѯѡ��
							if(j==1){//��ͥ�����
								var temp1="<td height='23' class='colValue'>"+
									"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
								temp=temp+temp1;
							}else if(j==3){//�߷ü�¼ID������
								var temp1="<td  style='display:none' height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}else{
								var temp1="<td height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}
						}else{
							if(j==1){//��ͥ�����
								var temp1="<td height='23' class='colValue'>"+
									"<span class = 'pointer colTtems' onclick= \"infoaction('"+fmid+"')\">"+row.item(j).firstChild.data+"</span></td>"
								temp=temp+temp1;
							}else if(j==5){//�������������
								var temp1="<td  style='display:none' height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}else{
								var temp1="<td height='23' class='colValue'>"+
								row.item(j).firstChild.data+"</td>";
								temp=temp+temp1;
							}
						}
						
					}
					//
					temp += "<td width='64px' height='23' class='colValue'>";
						//temp += "<span  class = 'pointer'><img src='/db/page/images/checkfamily.png' alt= '�༭��ͥ' onclick= \"infoeditaction('"+fmid+"')\"/>&nbsp;</span>";						
						if(sqltabid=="2"){//�߷ò�ѯѡ��
							//
						}else{
							temp += "<span  class = 'pointer'><img src='/db/page/images/checkreqidea.png' alt= '�߷ü�¼' onclick=\"CallRequestDialog("+fmid+")\"/>&nbsp;</span>";
						}						
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
				html += "<td height='25'>";
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
					  	/*
					  	html += "<select id = 'selectpage' onchange =\"ExePage()\">";
						for(var ipage =1;ipage<=sqlpagecount;ipage++){
					  		html += "<option value=\""+ipage+"\">��"+ipage+"ҳ</option>";
					  	}					
						html += "</select>";
						*/
						//ѡ��ҳ����ѡ���
						html += "<span id = \"spanpages\"></span> ";
					html += "  ÿҳ<input type=\"text\" size = \"2\" id = \"divpagerow\" onblur = \"ChangPageRow(this)\"></input>����¼</td>";
				html += "</tr>";
			html=html+"</table>";			
			//
			//			
			requestcon.innerHTML=html;
			//
			//
			//��ҳѡ��������
			/*
			if(sqlolepagecount==sqlpagecount){
				spanpages.innerHTML=GetPageSelect("0");
			}else{				
				spanpages.innerHTML=GetPageSelect("1");							
			}
			*/
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
	//ѡ��ҳ������
	function GetPageSelect(mode){
		var html;
		if(mode=="0"){//�͵ķ�ҳѡ��������
			sqlolepagecount = sqlpagecount;
			//
			return sqlolepageselect;
		}else{
			sqlolepagecount = sqlpagecount;			
			//
			html = "<select id = 'selectpage' onchange =\"ExePage()\">";
			for(var ipage =1;ipage<=sqlpagecount;ipage++){
		  		html += "<option value=\""+ipage+"\">��"+ipage+"ҳ</option>";
		  	}					
			html += "</select>";
			//
			sqlolepageselect = html;
			//			
		}
		return sqlolepageselect;
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
		var flag = "1";//��ҳ��ʶ
		//
		flag = pageflag;
		//		
		//
		if(flag=="1"){//��ҳ
			if(sqlselpage==1){				
				alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = 1;			
		}else if(flag=="2"){//��ҳ
			if(sqlselpage==1){
				alert("�Ѿ�����ҳ!!!");
				return;
			}
			sqlselpage = sqlselpage -1;
			if(sqlselpage<1){sqlselpage = 1;}
		}else if(flag=="3"){//��ҳ
			if(sqlselpage==sqlpagecount){
				alert("�Ѿ���βҳ!!!");
				return;
			}
			sqlselpage = sqlselpage +1;			
			if(sqlselpage>sqlpagecount){
				sqlselpage = sqlpagecount;				
			}
		}else if(flag=="4"){//ҳβ
			if(sqlselpage==sqlpagecount){
				alert("�Ѿ���βҳ!!!");
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
		//
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
	}
</script>
<script type="text/javascript">	
	//
	//���ڲ�ѯ
	function ChoiceDate(){
		sqlbegdt = $("#begdt").val();
		sqlenddt = $("#enddt").val();		
		if(sqlbegdt==""||sqlenddt==""){
			return;
		}
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
	}	
	//��������
	function RequestNow(src,fmid,fmno,fmname,mode){
		//
		var imgs =  document.getElementsByTagName("img");		
		for(var i=0; i<imgs.length;i++){			
			if(imgs[i].id == "img"){
				imgs[i].src="/db/page/images/check1.jpg";
			}
		}
		src.src="/db/page/images/check2.jpg";
		
		//���߷ü�¼����
 		CallIntrViewIdeaDialog(mode,fmid,fmno,fmname);
	}	
	//���߷ü�¼��ѯҳ��
	function CallRequestDialog(fmid){
		if(selwhere==""){
			seltabwhere ="INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		}else{
			seltabwhere =" and INFO_T_FAMILY.FAMILY_ID = '"+fmid+"'";
		}
		sqltabid = "2";//�߷ü�¼��ѯѡ��
		//
		document.getElementById("interviewlists").getElementsByTagName('li')[0].className='default';
		document.getElementById("interviewlists").getElementsByTagName('li')[1].className='active';
		//������ǩҳѡ��
		QueryInterView(sqltabid);
	  	selbegpage = 0;		//��ҳ��ʼ
 	  	selendpage = sqlpagenum;//��ҳ����
 	  	sqlselpage = 1;//��ʼ��һҳ
 	  	sqlpagenum = "16";//ÿҳ��¼��	
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
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
<script type="text/javascript">
	//
	//��ѯ��ʾҳ��
	function QueryInfoTab(){
		var html = "";
		html = "<ul id='interviewlists' class='menu'>";
		html += "<li id='li1' class='default'>�߷õǼ�</li>";
		html += "<li id='li2' class='default'>�߷ò�ѯ</li>";
		html += "</ul>";
		//			
		choicefamilyrequest.innerHTML=html;
		//
		init("interviewlists");
	}
	//������ǩҳѡ��
	function QueryInterView(stabid){
		var html = "";
		if(stabid=="2"){//�߷ò�ѯѡ��
			html = "<fieldset>";				
				html += "<table border='0' cellpadding='0' cellspacing='0' width='60%' align='center'>";
					html += "<tr class = 'pageTtems' valign='middle'>";
						html += "<td align='center' valign='middle'>";
							html += "<span>�߷�����:</span>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<input id='begdt' type='text' size='15' onFocus='setday(this)'/>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<span>��:</span>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<input id='enddt' type='text' size='15' onFocus='setday(this)'/>";
						html += "</td>";
						html += "<td align='center' valign='middle'>";
							html += "<input type='button' value='��ѯ' onclick='ChoiceDate()'/>";
						html += "</td>";
					html += "</tr>";			
				html += "</table>";			
			html += "</fieldset>";
			//
			requestcon.innerHTML="";			
			divrequestdt.innerHTML=html;	
			//
			sqlpagenum = "16";//ÿҳ��¼��			
			//
		}else{
			//
			requestcon.innerHTML="";			
			divrequestdt.innerHTML="";	
			//
			sqlpagenum = "18";//ÿҳ��¼��
			sqlbegdt = "";//��ʼ����
			sqlenddt = "";//��������
			//
			seltabwhere = "";//��ͥ�߷ò�ѯ
		}			
	}
</script>
<script  type="text/javascript">
	//
	function init(ids){
		document.getElementById(ids).getElementsByTagName('li')[0].className='active';			
		document.getElementById(ids).onclick=function(){onmousOver(ids);}//�������Ч��
		//
	  	selbegpage = 0;		//��ҳ��ʼ
 	  	selendpage = sqlpagenum;//��ҳ����
 	  	sqlselpage = 1;//��ʼ��һҳ
		//
		//Ĭ�ϱ�ǩѡ��
		sqltabid = "1";		
		//ȡ�������ѯ�����ִ�з���XML��ʽ		  
		GetPhySql();
	}
	function onmousOver(ids){		
		o = o || window.event;
		var obj=o.target || o.srcElement;
		if (obj.tagName=='LI'){
		  if (obj.className=='active')return;
		  var o=document.getElementById(ids).getElementsByTagName('li');
		  for (var i=0;i<=o.length-1;i++){o[i].className='default'}
		  obj.className='active';
		  //
		  //2Ϊ����li-1Ϊ��ǩ
		  var tid =  obj.id;
		  tid = tid.substring(2,tid.length);
		  //
		  sqltabid = tid;
		  //
		  //������ǩҳѡ��
		  QueryInterView(sqltabid);
		  //
		  selbegpage = 0;		//��ҳ��ʼ
	 	  selendpage = sqlpagenum;//��ҳ����
	 	  sqlselpage = 1;//��ʼ��һҳ
		  //ȡ�������ѯ�����ִ�з���XML��ʽ		  
		  GetPhySql();
		}
	}
</script>
