<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
SysTEmployee employee =(SysTEmployee)session.getAttribute("employee");
String onno = employee.getSysTOrganization().getOrganizationId();
String empno = employee.getEmployeeId().toString();
%>
<%	
	//	
	//����qid
	String reqid= request.getParameter("qid");		
	if (reqid == null) {
		//Ĭ��Ϊ��
		reqid = "";    //�ձ��
	}
	//����qname
	String reqname= request.getParameter("qname");
	reqname = new String(reqname.getBytes("ISO8859_1"), "GB18030");//����		
	if (reqname == null) {
		//Ĭ��Ϊ��
		reqname = "";    //������
	}
	//����qmode
	String reqmode= request.getParameter("qmode");		
	if (reqmode == null) {
		//Ĭ��Ϊ��
		reqmode = "";    //�ձ�׼����ɸѡ��ʽ
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
    
    <title>���ౣ�ϱ�׼����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/db/page/js/seqfun.js"></script>
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>	
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
	
	
    <style type="text/css">	
		body {
			margin: 0;
			padding: 0;
			font-family: "����";
			font-size: 12px;
		}		
		.sqltable{
			color: #6BA6FF;
			font-size:12px;
		}		
		#sqlwhere{
			border-top:1px solid #43ACB2;
			border-left:1px solid #43ACB2;
			border-bottom:1px solid #43ACB2;
			border-right:1px solid #43ACB2;
			width:100%;
			height:323px;
			font-size:12px;
		}		
		#infotree{
			border-top:1px solid #43ACB2;
			border-left:1px solid #43ACB2;
			border-bottom:1px solid #43ACB2;
			border-right:1px solid #43ACB2;
			width:100%;
			height:220px; 
			overflow:scroll;
			font-size:12px;
		}		
		#sqlsexp{
			width:100%;
			font-size:12px;
		}
		.titles{
			line-height: 2;			
			color: #000000;
			font-size:12px;
			text-align: center;
			width:100%;
			height:18;
			background: #F5A89A;
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
		var selid = "";		//���
		var selname = "";	//����
		var selmode = "";	//ɸѡ��ʽ
		//		    
		var vleft = 0;  //�������
		var vtop= 0; //�����߶�
		//
		var empid = "";        //��ǰ��¼�û���� 		
		var deptid = "";		    //��ǰ��¼����	
		var deptfieldname = "";		//��ǰ�����ֶ�			
		var seldeptid;		//ѡ�л���
		var seldeptname;
		var seldeptfullname;
		
		var discid = "";			//��ǰ�ֵ���
		var discname = "";			//��ǰ�ֵ�����
		var seldiscid;		//ѡ���ֵ�ֵ
		var seldiscname;
		var seldiscfullname;
		var discfieldname = "";		//��ǰ�ֵ��ֶ�
		
		//
		var selselect = "";		//ѡ���ѯ����ֶ�
		var selfrom = "";		//ѡ���ѯ�����
		var selwhere = "";		//ѡ���ѯ�������
		//
		//��������SQL����Ƿ�Ϸ�
		function TestPhySql(mode) {			
			var sql = $("#sqlwhere").val();	 
			if(sql=="" || sql == null){
				DisplayResult("��ʾ:û����д����ʩ����׼����!");
				$("#sqlwhere").get()[0].focus();
				return;
			}   
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/info/search/TableInfoServlet",            //������ҳ���ַ
		        {
		            action: "testphysql",             //action����
		            sql: sql         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					// 
					if (result=="-1"){
		        		DisplayResult("��ʾ:�������ܲ��Ϸ�!");
		        	}else{
		        		if(mode=="test"){//���Խ��
		        			if(result=="0"){		        		
		        				DisplayResult("��ʾ:�������ƺϷ�,���޲�ѯ���!");		        		
		        	 		}else{
		        				DisplayResult("��ʾ:�������ƺϷ�!");
		        			} 
		        		}else if(mode=="getsql"){//ȡ���physql		        			
		        			//ȡ�ý���󱣴�
		        			SaveChildSql(sql,sql);
		        		}	
		        		
		        	}  		
		        }
		    );
		}
		//����ת��[�߼�����ת����������]
		function GetPhySql(mode){		
			var tselect,tfrom,twhere,tmode,tbegpage,tendpage;		
			
			//��ѯ����
		   	selwhere = $("#sqlwhere").val();
		   	//��ѯ���(��̨����)
		   	selselect = "";	
		   	//
			tselect = JHshTrim(selselect);	
			tfrom = JHshTrim(selfrom);
			twhere = JHshTrim(selwhere);
			//
			if(twhere==""|| twhere == null){
				DisplayResult("��ʾ:û����д����ʩ����׼����!");
				$("#sqlwhere").get()[0].focus();
				return;
			}
			//������ѯģʽ��������ģ�鶨��tmode
			if(mode=="test"){//���Խ��
				tmode = "0";
			}else{			//sql���
				tmode = "1";
			}
	      	tbegpage = "0";
	      	tendpage = "0";		
			//
		    $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
		        {
		            action: "getphysql", //action����
		            mode: "getsqlpolicychild",   //����
		            tselect: tselect,   //����
		            tfrom: tfrom,   //����
		            twhere: twhere,   //����
		            tmode: tmode,
	                tbegpage: tbegpage,
	                tendpage: tendpage,  //������ҳ
	                ttype: selmode		            
		        },
		        function(result) {   //�ص�����
		        	if (result=="-1"){
		        		DisplayResult("��ʾ:�������ܲ��Ϸ�!");
		        	}else{		        		
		        		if(mode=="test"){//���Խ��
		        			if(result=="0"){		        		
		        				DisplayResult("��ʾ:�������ƺϷ�,���޲�ѯ���!");		        		
		        	 		}else{
		        				DisplayResult("��ʾ:�������ƺϷ�!");
		        			} 
		        		}else if(mode=="getsql"){//ȡ���physql		        			
		        			//ȡ�ý���󱣴�
		        			if(selmode=="family"){
						   		//��ͥ				
								tselect = "INFO_T_FAMILY.FAMILY_ID";				
						   	}else if(selmode=="member"){
						   		//��Ա						   			
						   		tselect = "INFO_T_FAMILY.FAMILY_ID,INFO_T_MEMBER.MEMBER_ID";
						   	}		        			
		        			var locsql = "select "+ tselect+" from "+tfrom+" where "+twhere;
		        			SaveChildSql(result,locsql);
		        		}	        		
		        	}		        	        	   	
		        }
		    );    		
		}
		//�������
		function SaveChildSql(physql,locsql){
			var sphysql = "",slocsql = "";			
			//
			sphysql = physql;			
    		slocsql = locsql;    			
			//
		    if (!confirm("ȷ������["+selname+"]����ʩ����׼����?")) {
		        return;
		    }
			//
			//
		    $.post("page/policy/manage/PolicyManageServlet",            //������ҳ���ַ
		        {
		            action: "updatePolicyChildSql",             //action����
		            sid: selid,
		            sphysql: sphysql,
		            slocsql: slocsql        
		        },
		        function(result) {                    	//�ص�����
		        	//��ʾ��ʾ��Ϣdiv
					DisplayResult(result);										  		
		        }
		    );
		}		
		
		//ȡ�÷���ʩ����׼SQL����
		function GetPolicyChildSqlItem() {
			var mode = "";
		    //�������
			ResetSql();
			//ѡ���߼��������������
		    var isrdc = document.getElementById("rdc");
		    var isrdp = document.getElementById("rdp");
		    if(isrdc.checked == true){	    		
	      	 	mode = "loc";  
	      	 	$("#matchtd").css({"display":"block"});              
	      	}else if(isrdp.checked == true){
	      		mode = "phy";
	      		$("#matchtd").css({"display":"none"}); 
	      	}	
		    //��ʾҳ��״̬div
			DisplayPageStatus();
			//
		    $.post("page/policy/manage/PolicyManageServlet",   //������ҳ���ַ
		        {
		            action: "getPolicyChildSqlItem",             //action����
		            sid: selid,
		            mode: mode         
		        },
		        function(result) {                    	//�ص�����
		        	//����ҳ��״̬div
					HiddenPageStatus();	
					//
					if(result!="null"){
						if(mode=="loc"){
							//�߼�sql��䴦��		
							SetLogicSql(result);
						}else if(mode=="phy"){
							//$("#sqlwhere").val(result);
							document.getElementById("sqlwhere").innerHTML=result;//���������ַ�
						}    
					} 		
		        }
		    );
		}
		//��������ֵ
		function SetExpValue(){			
			var value = expvalue.value;	
			value = JHshTrim(value);			
		    //����ǰȷ��
		    if (value.length<=0){return;}
		    insertAtCaret(sqlwhere, " '"+value+"'");	
		}
		
		//���Բ�ѯ���
		function TestSql() {
			//ѡ���߼��������������
		    var isrdc = document.getElementById("rdc");
		    var isrdp = document.getElementById("rdp");
		    if(isrdc.checked == true){	    		
	      	 	GetPhySql("test");	               
	      	}else if(isrdp.checked == true){
	      		TestPhySql("test");	  
	      	}			
			          	            
		}
		//�����ѯ���
		function SaveSql() {
			//ѡ���߼��������������
		    var isrdc = document.getElementById("rdc");
		    var isrdp = document.getElementById("rdp");
		    if(isrdc.checked == true){	    		
	      	 	GetPhySql("getsql");               
	      	}else if(isrdp.checked == true){
	      		TestPhySql("getsql");     
	      	}			
				             	            
		}
		//����SQL���ĺ�����
		function ResetSql() {
			//����
			$("#sqlwhere").val("");			
			$("#sqlwhere").get()[0].focus();
			storeCaret(sqlwhere);				            
            //
			selselect = "";
			selfrom = "";
			selwhere = "";				             	            
		}
		//ȡ�û�����ͥ��Ϣ��
		function GetInfoTree(){
			//����������ͥ��Ϣ��
			var ulr = "page/info/search/TableTreeServlet";
			$("#infotree").empty();                //��������б�
			loadrootTree('infotree',ulr,'infotree','root');	
		}
		//ȡ�������������
		function GetExpSelect(tfieldtype){
			//
		    $.post("page/info/search/TableInfoServlet",      //������ҳ���ַ
		        {
		            action: "getexpselect" , //action���� 
                	fieldtype: tfieldtype   		            	            
		        },
		        function(result) {   //�ص�����
		        	divexp.innerHTML = result;	        	        	   	
		        }
		    );    		
		}
		//��ʾҳ��״̬div
		function DisplayPageStatus() {
			//
	    	vleft = document.body.clientWidth/1.8;  //�������	    	
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
	    	vleft = document.body.clientWidth/1.8;  //�������
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
		//
		//��ʼ��ҳ��
		function IniPage(){	
			//��ǰ��¼�û����    
			empid = "<%=empno%>";     
			//��¼����    
			deptid = "<%=onno%>";    //��ǰ��¼���� 
			//
			selid = "<%=reqid%>";    //��� 
			selname = "<%=reqname%>";    //����
			selmode = "<%=reqmode%>";    //ɸѡ��ʽ
			//
    		vleft = document.body.clientWidth/2;  //�������
			vtop= document.body.clientHeight/2; //�����߶�
			//
			//������ͥ��Ϣ��
			GetInfoTree();			
			//			
			//�����
			GetExpSelect("0");
			//ȡ�÷���ʩ����׼SQL����
			GetPolicyChildSqlItem();
		}		
	</script>
		
  </head>
  
  <body  onLoad="IniPage()">
  		<div id='pagestatusdiv'><img src=/db/page/images/loading.gif></img>�Ե�...</div> 
  		<div id='resultstatusdiv'></div> 
  		<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
	    	<tr align="center">
	    		<td valign="top" width="40%"  id = "matchtd">
	    			<table width="100%" border="0" cellspacing="0" cellpadding="0">	   		   		
				    	<tr align="center">
				    		<td valign="top" width="40%">
				    			<fieldset  class='list'>
				    			<legend  class='legend'>���ౣ�ϱ�׼����</legend>
				    				<span class = 'titles'>��ͥ��Ϣ��</span>    	  
							        <div id="infotree" align="left"></div>
							        <span class = 'titles'>�����</span>    	  
							        <div id = "divexp" align="left"></div>
							        <div align="center" id = "divexpvalue" align="left">
							        	<label class = "titles">����ֵ:
							        		<input  type="text" id="expvalue" ></input>
							            	<input type='button' value='ȷ��' name="BtnExp" onclick='SetExpValue()'></input>
							            </label>
							        </div>
				    			</fieldset>	
				    		</td> 
				    	</tr>	    	
			    	</table> 
	    		</td>
	    		<td valign="top" width="60%">	    		
	    			<fieldset  class='list'>
	    			<legend  class='legend'>[<%=reqname%>]���ౣ�ϱ�׼����</legend>  
	    				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    					<tr align="center" class = 'titles'>	
	    						<td valign="top">
	    							<input type="radio" name="rd" id="rdc" value="rdc" onclick="GetPolicyChildSqlItem()" checked />ѡ���������
									<input type="radio" name="rd" id="rdp" value="rdp" onclick="GetPolicyChildSqlItem()" />��׼SQL���
	    						</td>
	    					</tr>	    					
					    	<tr align="center">
					    		<td valign="top">
					    			<textarea name="sqlwhere" rows="6" id="sqlwhere" 
						        				onSelect="storeCaret(this);" 
						        				onClick="storeCaret(this);" 
						        				onKeyUp="storeCaret(this);">
						          	</textarea>
					    		</td>	
					    	</tr>					    	 	
				    	</table>				    	 
			          	<div class = "titles">
		      				<input type='button' value='���' onclick='ResetSql()'></input>            	
						  	<input type='button' value='У��' onclick='TestSql()'></input>	      	  
						  	<input type='button' value='����' onclick="GetPolicyChildSqlItem()"></input>
						  	<input type='button' value='����' onclick='SaveSql()'></input>
			            </div>		          	
	    			</fieldset>	
	    		</td>
	    	</tr>
	    </table>
  		
  </body>
</html>
<script type="text/javascript">		
	//�߼�sql��䴦��		
	function SetLogicSql(str)
	{
		//������ʽ�����߼�SQL���
		var r; // ����������
		var s = "select";
		var f = "from";
		var w = "where";
		var ilen,iselect,ifrom,iwhere;
		if (str == null){
			DisplayResult("����û����д,�޷�����!");	
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");			
			return false;		
		}
		ilen = str.length;
		r = MatchSearch(str,s);
		if (r<0){
			DisplayResult("������д����,�޷�����!");
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");					
			return false;
		}
		//selectλ��
		//6��ʾselect����
		iselect = r+6;
		ifrom = MatchSearch(str,f);
		if (ifrom<iselect){		
			DisplayResult("������д����,�޷�����!");	
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");	
			return false;	
		}else{
			selselect = JHshTrim(str.substring(iselect,ifrom-1));	
		}
		//whereλ��
		iwhere = MatchSearch(str,w);
		if (iwhere<ifrom){
			DisplayResult("������д����,�޷�����!");
			selselect = "";	
			selfrom = "";	
			selwhere = "";
			$("#sqlwhere").val("");
			return false;
		}else{
			//4��ʾfrom����
			//5��ʾwhere����
			selfrom = JHshTrim(str.substring(ifrom+4,iwhere-1));
			sqlwhere.value = JHshTrim(str.substring(iwhere+5,ilen));	
		}					 
	}
	//=================================ѡ�������BEG================================
	//ѡ���ѯ�ֶ�
	function SelectField(tfullname){
		var sfield;
		var s;
		var ilen;
		var stable;			
		var stablevalue;
		//ѡ���ֶ�
		sfield = tfullname;			
		
		//ȡ���ֶ�ǰ��ı�������
		var atable = sfield.split(".");			
		stable = atable[0];	
		//��ѯ��													
		s = selfrom;
		if(MatchMatch(s,stable)==null){																
			if (s.length>0){						
				stable = "," +stable;
			}
			//��ѯ��
			selfrom = selfrom + stable;	
		}	
		//�����ֶ�
		s = sqlwhere.value;
		if (s.length>0){						
			sfield = " " +sfield;
		}															
		insertAtCaret(sqlwhere, sfield);																		
	}
	//ѡ���ѯ����
	function SelectExp(){
		var sexp;
		sexp = sqlsexp.options[sqlsexp.selectedIndex].value;
		if (sexp=='a'){sexp = ">"}	
		if (sexp=='b'){sexp = "<"}
		if (sexp=='c'){sexp = "\'\'"}
		if (sexp=='d'){sexp = "\"\""}
		if (sexp=='likel'){sexp = "\' %\'"}
        if (sexp=='liker'){sexp = "\'% \'"}
        if (sexp=='likec'){sexp = "\'% %\'"}
		sexp = " " +sexp;			
		insertAtCaret(sqlwhere, sexp);	
	}		
	//=================================ѡ�������END================================	
	//=================================ѡ���ѯ����BEG==============================
	//ѡ���ڵ�[TableTreeServlet�����ķ���]
	function seltableclick(tid,tname){
		//alert(tid+tname);	
		CallTableDialog(tid,"edit");
	}		
	//ѡ���ֶνڵ�[TableTreeServlet�����ķ���]
	function selfieldclick(tid,tname,tfullname,tfieldmode){
		//alert(tid+tname+tfullname+tfieldmode);			
		//����
          	if(tfieldmode=="-4"){
          		deptfieldname = tfullname;           		
          		//alert("����");	           		
			queryDept();					
          	}
          	//������
          	else if(tfieldmode=="-3"){
          		//�����
			GetExpSelect(tfieldmode);
			           		
          		insertAtCaret(sqlwhere,"to_char(");
	    	SelectField(tfullname);
	    	insertAtCaret(sqlwhere,",'yyyy-mm-dd')");         		
          	}
          	//��ֵ��
          	else if(tfieldmode=="-2"){
          		//�����
			GetExpSelect(tfieldmode);
			
          		SelectField(tfullname);
          	}
          	//����
          	else if(tfieldmode=="-1"){
          		//�����
			GetExpSelect(tfieldmode);
			
          		SelectField(tfullname);
          	}
          	//�ַ���
          	else if(tfieldmode=="0"){
          		//�����
			GetExpSelect(tfieldmode);
          		SelectField(tfullname);
          	}
          	//�ֵ�ֵ	
          	else{
          		//���ֵ�
			discid = tfieldmode;
			discname = tname;
			discfieldname = tfullname;
			queryDisc();				
          	}					
	}
	//���ӱ�[TableTreeServlet�����ķ���]
	function addchild(tid){
		//alert(tid);
		CallTableDialog(tid,"add");	
	}
	//���ֶ�[TableTreeServlet�����ķ���]
	function addfield(tid){
		//alert(tid);
		CallFieldDialog(tid,"add");	
	}
	//ѡ���ѯ����
	function selectclickexp(){
		SelectExp();
	}
	//=================================ѡ���ѯ����END==============================
</script>
<script type="text/javascript">
 	//=================================ģ̬����ѡ��BEG==================================
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
		//��������ѡ��
		if(seldeptid==null) return;
		/*
		if (!confirm("�Ƿ�ȷ��ѡ��\n["+seldeptname+"]")) {
	        return;
	    }
	    */
	    SelectField(deptfieldname);
		insertAtCaret(sqlwhere, " like '"+seldeptid+"%'");
		closedept();
	}
	//�ÿջ���ѡ��[DeptTreeServlet�����ķ���]
	function cleardept(){		
		closedept();	
	}
	//�رջ���ѡ��[DeptTreeServlet�����ķ���]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();
		showdiv();	
	}
	//�ֵ�ѡ��[DiscTreeServlet�����ķ���]
	function seldiscclick(id,name,fullname){
	   //ѡ���ֵ�
	   $("#selname").html("ѡ��:"+fullname+"");
	   seldiscid = id;
	   seldiscname = name;
	   seldiscfullname = fullname;
	}
	//�ֵ�ѡ��ȷ��[DiscTreeServlet�����ķ���]
	function okdisc(){
		//�ֵ�ѡ��
		if(seldiscid==null) return;
		if (!confirm("�Ƿ�ȷ��ѡ��\n"+seldiscfullname+"")) {
	        return;
	    }
	    if(discfieldname!=""){
	        //ѡ���ֶ�
	        SelectField(discfieldname);
	    	insertAtCaret(sqlwhere, " = "+seldiscfullname);
	    }
		closedisc();
	}	
	//�ر��ֵ�ѡ��[DiscTreeServlet�����ķ���]
	function closedisc(){
		$("#bdiv").remove();
		$("#odiv").remove();
		showdiv();	
	}
	//����select
	function hidediv(){			
		$("#sqlsexp").css({"display":"none"});
	}
	//��ʾselect
	function showdiv(){			
		$("#sqlsexp").css({"display":"block"});	
	}		
	//=================================ģ̬����ѡ��END==================================
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
                    oWidth:220,                              //�������ڿ��
                    oHeight:300                              //�������ڸ߶�
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
	        }else if(defaults.otype=="2"){
	        	//���ֵ�ѡ��
	        	var ulr = "page/info/search/DiscTreeServlet";
	        	loadrootTree('content',ulr,discid,'root');
	        	//CSS
	        	$("#cbtn>img").css({"cursor":"pointer","float":"right"}).click(function(){closedisc();});
	        }else if(defaults.otype=="3"){
	        	
	        }
	        	        
	        //CSS		        
	        $("#cbtn").css({"font-size":"12px","background":"#F2F3F7","color":"#777","padding":"5px 5px"});
	        $("#stitle").css({"font-size":"12px","background":"#F2F3F7","color":"#777","padding":"5px 5px"});
	        $("#content").css({"font-size":"14px","padding":"1px 1px","overflow-x":"hidden","overflow-y":"auto","width":defaults.oWidth+"px","height":defaults.oHeight+"px"});
	        $("#odiv").css({"background":defaults.oColor,"width":defaults.oWidth+"px","border":"1px black solid","z-index":"9999","position":"absolute","top":"1px","left":(document.body.clientWidth-defaults.oWidth)/2+"px"});
	        $("#bdiv").css({"background":defaults.bColor,"width":defaults.bWidth,"height":defaults.bHeight,"z-index":"9998","position":"absolute","filter":"alpha(opacity:90)","left":10,"top":0});
	};
	})(jQuery);
	
	$(function(){
        //����		
        $("button").css({"display":"block","margin":"10px 0","width":"120px"});
        $("button:first").css("background","red");
	});
	//��ѯ��������
	function queryDept(){
    	$.openWindow({"title":"����ѡ��","otype":"1"});
		hidediv();				
	}
	//��ѯѡ���ֵ�
	function queryDisc(){			
		if(discname==""){
		  $.openWindow({"title":"�ֵ�ѡ��","otype":"2"});
		}else{
		  $.openWindow({"title":discname+"-�ֵ�ѡ��","otype":"2"});
		}	    	
		hidediv();
	}
	//=================================AJAXģ̬����=================================
</script>
