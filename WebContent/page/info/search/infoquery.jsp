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
	String reqmode = request.getParameter("qmode");
	String reqpolicy = request.getParameter("qpolicy");
	if (reqpolicy == null) {
		reqpolicy = "-1"; //��ѡ��ҵ��
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��ѯ����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="/db/page/css/xiustyle.css" rel="stylesheet" type="text/css">
	<link href="/db/page/css/jBox.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/db/page/js/jquery0.js"></script>
	<script type="text/javascript" src="/db/page/js/TableSorter.js"></script>
	<script type="text/javascript" src="/db/page/js/jBox-1.0.0.0.js"></script>
	<script type="text/javascript" src="page/js/Calendar2.js"></script>
	<script type="text/javascript" src="/db/page/js/dynamictree.js"></script>
  </head>
  <script  type="text/javascript">
	//
	var empid = "",deptid = "",depth = "";
	//
	var selmode = "0";      //����ģ�����
	var selpid = "";		//ѡ���ѯҵ��ID
	var selpsta = "0";		//ѡ���ѯҵ��״̬
	var selselect = "";    	//ѡ���ѯ����ֶ�
	var selfrom = "";    	//ѡ���ѯ�����
	var selwhere = "";    	//ѡ���ѯ�������
	var selorder = "";    	//ѡ���ѯ�������
	//
	//����ת��[�߼�����ת����������]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,torder,tmode,tbegpage,tendpage; 
	    //      
	    tselect = JHshTrim(selselect);  
	    tfrom   = JHshTrim(selfrom);
	    twhere  = JHshTrim(selwhere);
	    //
	    torder  = JHshTrim(selorder);
	    // 
	    tmode 	 = "1";	//ģʽ0ִ�в�ѯ1��ȡSQL���
	    tbegpage = "0";
	    tendpage = "0";
	    
	    //
	    //ѡ���ͥ���߳�Ա
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    if(isrdf.checked == true){	    		
      	 	mode = "exefamilysql";              
      	}else if(isrdm.checked == true){
      		mode = "exemembersql";  
      	}
	    //��������		
		DisplayPageStatus();        
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
	            tdept: deptid,
	            tempid: empid,	           
	            tpolicy: selpid,
	            tpolicysta: selpsta             
	        },
	        function(result) {   //�ص�����
	        	//�����������
      			HiddenPageStatus();  	        	
	  			//
	          	if(result=="-1"){                
	             	//��ѯ��䲻�Ϸ�          
	          	}else{
	          		//����ش���
					CallPage(result);
	          	}                             
	    	});        
    }
	//��ȡ��ѯѡ��
	function getQueryExpHtml(){
		//
		//��������		
		DisplayPageStatus();
		//
	    $.post("/db/page/query/querypage.do",            //������ҳ���ַ
	        {
	            action: "getQueryExpHtml",             //action����
	            jempid: empid
	        },
	        function(result) {                    	//�ص�����
	        	//�����������
      			HiddenPageStatus();  	        	
	  			//
	        	divquery.innerHTML = result; 
	        }
	    );
	}
	
	//��ȡҵ��ѡ��������
	function getPolicySelect(sname){
		//
	    $.post("/db/page/policy/account/accountpage.do",            //������ҳ���ַ
	        {
	            action: "getPolicySelect",             //action����
	            sname: sname
	        },
	        function(result) {                    	//�ص�����
	        	divpolicy.innerHTML = result; 
	        	//ҵ��ѡ��У��
	        	if(selpid != "-1"){
	        		$("#"+sname).val(selpid);
					$("#"+sname).attr("disabled", "disabled");
					$("#stalist").attr("disabled", "disabled");
				} 	
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
	//��ʼ��
	function IniPage(){
		//
		empid = "<%=empno%>";    
		deptid = "<%=onno%>";
		depth = "<%=thno%>";
		//
		selmode  = "<%=reqmode%>";		 //��ǰ��ѯ����ģʽΪ��Ϣ��ѯcode
		selpid = "<%=reqpolicy%>";	 	 //ҵ��ѡ��
		//		
		//��ȡҵ��ѡ��������
		getPolicySelect("pidlist");	
		//��ȡ��ѯѡ��
		getQueryExpHtml();
		//���ݲ�ѯת��ģʽ
		var tmpmode = selmode;
		if(Number(tmpmode) >= 4){
			//ҵ���ѯ
			$("#rdm").attr("disabled", "disabled");
		}else{
			//��Ϣ��ѯ
			$("#rdm").attr("disabled", "");	
		}
	}
  </script>
  <body onload = "IniPage()"  style = "background: #FCDAD5;overflow-x:hidden;overflow-y:scroll;">
		<fieldset>
			<legend>
				��ѯ���ѡ��
			</legend>
			<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = "font-size: 12px;">				
				<tr align='center' >
					<td>
						<input type='radio' name='rdr' id='rdf' checked='checked'
							onclick='ChoiceResult()'></input>
						<span style="color: #6BA6FF;">��ͥ��ʾ</span>
						<input type='radio' name='rdr' id='rdm' onclick='ChoiceResult()'></input>
						<span style="color: #6BA6FF;">��Ա��ʾ</span>
					</td>					
				</tr>
			</table>			
		</fieldset>
		<div id = 'divquery'></div>
		<fieldset>
			<legend>
				��ѯҵ��ѡ��
			</legend>
			<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = "font-size: 12px;">
				<tr>
					<td width='50%' align='center'>
						<div id = 'divpolicy'></div>
					</td>
					<td  align='center'>
						<select id='stalist' style = "font-size:12px;">
							<option  value='0'>ȫ��</option>							
							<!-- <option  value='1'>��ͨ��</option> -->
							<!-- <option  value='2'>�ڱ���</option> -->
							<!-- <option  value='3'>ͣ����</option> -->
							<!-- <option  value='4'>��ͣ��</option> -->
							<option  value='1'>�ڱ���</option>
						</select>
					</td>
				</tr>				
			</table>
		</fieldset>		
		
		<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' style = "font-size: 12px;">
			<tr align='center' >				
				<td>
					<button title = '��ѯ' class='btn' id='btnquery' onclick="getQueryExpSQL()">��   ѯ</button>										
				</td>
				<td width='40'>
					<span class='pointer' title = '�����ѯ����' style="color: #6BA6FF;" onclick='CallIniDialog()'>[����]</span>											
				</td>
			</tr>
		</table>
		<div align="center" id='pagestatusdiv'><img src="/db/page/images/loadingtiao.gif"></img></div>
		
		<form id="resultaction">
			<input type="hidden" name="sql" />
			<input type="hidden" name="type" />
			<input type="hidden" name="type1" />
			<input type="hidden" name="url" />
			<input type="hidden" name="checkbox" />
		</form>
		<form id="policyaction">
			<input type="hidden" name="qpolicy" />
			<input type="hidden" name="qselect" />
			<input type="hidden" name="qfrom" />
			<input type="hidden" name="qwhere" />
			<input type="hidden" name="qorder" />			
		</form>
	</body>
</html>
<script  type="text/javascript">
	//ѡ���ѯ��ʾ���
	function ChoiceResult(){
		if(rdf.checked == true){
			$("#fmlist").css({"display":"block"});	    		
      		$("#mmlist").css({"display":"none"});       			            
      	}else if(rdm.checked == true){
      		$("#fmlist").css({"display":"none"}); 
      		$("#mmlist").css({"display":"block"});
      	}
	}
	//�򿪸������ô���
	function CallIniDialog(){
		var myobj = new Array();
		myobj[0] = selmode;		
		showModalDialog("/db/page/querymanage/infoqueryini.jsp",myobj,"status:false;dialogWidth:600px;dialogHeight:500px");
		
		//ˢ��ҳ��
		//��ȡ��ѯѡ��
		getQueryExpHtml();
	}
	//����ش���
	function CallPage(sql){
		var tmpmode = selmode;
		if(Number(tmpmode) >= 4){
			//ҵ���ѯ
			policyaction(selmode);
		}else{
			//��Ϣ��ѯ
			resultaction(sql)	
		}
	}
	//�����ѯSQL��Ϣ
	function getQueryExpSQL(){
		//ҳ��������
		var jname = "",jid = "",jvalue = "",jtable = "",jfield = "",jexpvalue = "",jdeptid = "";
		//
		selselect = "";    	//ѡ���ѯ����ֶ�
		selfrom = "";    	//ѡ���ѯ�����
		selwhere = "";    	//ѡ���ѯ�������
		selorder = "";    	//ѡ���ѯ�������
		//
		
		//
		for (i=0;i<document.all.length;i++) {			
			jname = document.all[i].name;
			//�Ƿ��ѯѡ�����
			if(jname=="fmqv[]" 
				|| jname=="mmqv[]" 
				|| jname=="dv[]" 
				|| jname=="qv[]")
			{
				//��ʼ��ѡ�������ѯID
				jdeptid = "";
				//��ȡҳ�������Ϣ
				jid = document.all[i].id;
				jvalue = document.all[i].value;
				//	
				jtable = document.getElementById("t"+jid).value;	
				jfield = document.getElementById("f"+jid).value;
				jexpvalue = document.getElementById("v"+jid).value;
				//
				//============�����ѯ���======================
				if (jname=="fmqv[]") {
					//��ͥ�����				
				    if(rdf.checked == true){		//��ͥ��ѯ
				    	//��ѯ�ֶ�                         
				      	var sf = selselect;
				      	if(MatchMatch(sf,jfield)==null){                              
				        	if (sf.length>0){ 
				          		jfield += ",";		            
				        	}
				        	//��ѯ�ֶ�
				        	selselect = jfield + selselect;  
				        	//��ѯ��                          
				      		var s = selfrom;
				      		if(MatchMatch(s,jtable)==null){                                
				        		if (s.length>0){            
				          			jtable += ",";	
				        		}
				        		//��ѯ��
				        		selfrom = jtable + selfrom;  
				      		}
				      	}
				    }
				}else if (jname=="mmqv[]") {
					//��Ա�����
					if(rdm.checked == true){		//��Ա��ѯ
						//��ѯ�ֶ�                         
				      	var sf = selselect;
				      	if(MatchMatch(sf,jfield)==null){                              
				        	if (sf.length>0){ 
				          		jfield += ",";		            
				        	}
				        	//��ѯ�ֶ�
				        	selselect = jfield + selselect;  
				        	//��ѯ��                          
				      		var s = selfrom;
				      		if(MatchMatch(s,jtable)==null){                                
				        		if (s.length>0){            
				          			jtable += ",";	
				        		}
				        		//��ѯ��
				        		selfrom = jtable + selfrom;  
				      		}
				      	}				      	
				    }
				}else if (jname=="dv[]") {
					//��������
					jdeptid = document.getElementById("d"+jid).value;										
				} else if (jname=="qv[]") {
					//��������
				}
				//����ֵ��Ϊ��
		      	if(jvalue.length>0){
		      		//������ѯУ��		      		
		      		if(jdeptid.length>0){		//�ǻ�����ѯ����
		      			jvalue = jdeptid;
		      		}
		      		//��ѯ��                          
		      		var s = selfrom;
		      		if(MatchMatch(s,jtable)==null){                                
		        		if (s.length>0){            
		          			jtable += ",";	
		        		}
		        		//��ѯ��
		        		selfrom = jtable + selfrom;  
		      		}
		      		//���������С��
		      		var trvalue = "��";                  
		      		//�滻����ֵ            
		      		var n = MatchReplace(jexpvalue,trvalue,jvalue);
		      		//��ѯ����
			    	if(selwhere.length>0){
			      		selwhere += " and "+ n;
			    	}else{
			      		selwhere = n;  
			    	}
		      	}				
				//============�����ѯ���======================
			}
		}
		//
		//=================����====================
		var tmpname = "",tmporder = "asc";
		
    	if(rdf.checked == true){			//��ʾ��ͥ
    		tmpname = $("#fmlist").val();
      	}else if(rdm.checked == true){	//��ʾ��Ա
      		tmpname = $("#mmlist").val();
      	}
      	//
      	if(rdoup.checked == true){			//����
    		tmporder = "asc";
      	}else if(rdodown.checked == true){	//����
      		tmporder = "desc";
      	}
		//
      	selorder = tmpname + " " + tmporder;
      	//=================����====================
      	//=================ҵ���ѯ================
      	selpid = $("#pidlist").val();
      	selpsta = $("#stalist").val();
      	//=================ҵ���ѯ================
		//
		//����ת��[�߼�����ת����������]
    	GetPhySql();
	}
</script>
<script type='text/javascript'>
	//=================================������ʽBEG================================
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
    //������ʽ����
    //���ص�һ��ƥ��λ��
    function MatchSearch(str,s)
    {
      var r, re; // ����������
      if (str == null){
        return;    
      }
      //g ��ȫ�Ĳ��ҳ��ֵ����� pattern�� 
      //i �����Դ�Сд�� 
      //m �����в��ң�
      //re   =   /s/gi;  
      re = new RegExp(s,"gi"); // ����������ʽ����
      r = str.search(re); // ���ַ��� str �в���ƥ�䡣
      return (r);  //ƥ���λ��
    }
    //������ʽת��
    //�����滻������ַ���
    function MatchReplace(str,olestr,newstr)
    {
      var r, re; // ����������      
      //g ��ȫ�Ĳ��ҳ��ֵ����� pattern�� 
      //i �����Դ�Сд�� 
      //m �����в��ң�
      //re   =   /olestr/gi;      
      re   =  new RegExp(olestr,"gi"); // ����������ʽ����                 
      r = str.replace(re,newstr);
      return(r);     
    }
    //=================================������ʽEND================================
    //==================================�ַ�ȥ����β�ո�BEG=========================
  
    //1���������ڶ�sString�ַ�������ǰ�ո�س�    
    function  JHshLTrim(sString){         
      var   sStr,i,iStart,sResult="";  
      sStr = sString.split("");   
      iStart = -1   ;   
      for (i=0;i<sStr.length;i++){   
        if (sStr[i]!=" "){   
          iStart = i;   
          break;   
        }   
      }   
      if (iStart == -1){  
        //��ʾsString�е������ַ����ǿո�,�򷵻ؿմ�  
        return "";
      }          
      else {   
        return sString.substring(iStart)   ;
      }   
    } 
    //2   ���������ڶ�sString�ַ������к�ո�س�
    function JHshRTrim(sString){     
      var  sStr,i,sResult = "",sTemp = "";   
        
        if (sString.length  == 0) { 
          return   "";
        }   //   ����sString�ǿմ�   
      sStr =  sString.split(""); 
      //���ַ������е���  
      for (i = sStr.length - 1;i>=0;i--)   
      {     
        sResult = sResult + sStr[i];     
      } 
      //�����ַ���ǰ�ո�س�   
      sTemp = JHshLTrim(sResult);    
      if (sTemp == "") {
        return  "";
      }     
      sStr  =  sTemp.split("");   
      sResult  = ""; 
      //�����������ַ����ٽ��е���  
      for (i = sStr.length - 1;i >= 0;i--)   
      {   
        sResult = sResult + sStr[i];   
      }   
      return  sResult;   
    }  
    //3���������ڶ�sString�ַ�������ǰ��ո�س�
    function JHshTrim(sString)   
    {   
      var strTmp;  
      strTmp = JHshRTrim(JHshLTrim(sString));
      return strTmp;   
    }
    //=================================�ַ�ȥ����β�ո�END===================
</script>
<script type='text/javascript'>
	//=================================AJAXģ̬����=========================
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
                   oWidth:200,                              //�������ڿ��
                   oHeight:380                              //�������ڸ߶�
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
	//
	//var inputseldeptid = "";	//ѡ�еĻ���ѡ���ID
	//��ѯ������׼
	function queryDept(src){
    	$.openWindow({"title":"����ѡ��","otype":"1"});
    	inputseldeptid = src.id;  
    	//����select
      	hidediv();  		    			
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
		
	    //ѡ����ò���
	    document.getElementById("d"+inputseldeptid).value = seldeptid;
	    document.getElementById(inputseldeptid).value = seldeptfullname;
	    //
		closedept();
	}
	//�ÿջ���ѡ��[DeptTreeServlet�����ķ���]
	function cleardept(){
		//ѡ����ò���
	    document.getElementById("d"+inputseldeptid).value = "";
	    document.getElementById(inputseldeptid).value = "";		
	    //
		closedept();	
	}
	//�رջ���ѡ��[DeptTreeServlet�����ķ���]
	function closedept(){
		$("#bdiv").remove();
		$("#odiv").remove();
		//��ʾselect
	  	showdiv();			
	}
	//
	//����select
  	function hidediv(){ 
	  	//������Ϣ��ѯѡ��������
	  	var Els=document.getElementsByTagName("select");
		var iLen = Els.length;    
		for(var i=0;i<iLen;i++){            
		  var oAtt = Els[i].attributes;
		  var ObjId= oAtt.getNamedItem("id").value;
		  $("#"+ObjId).css({"display":"none"});		 
		}
  	}
  	//��ʾselect
  	function showdiv(){
	  	//	
	  	//��ʾ��Ϣ��ѯѡ��������
	  	var Els=document.getElementsByTagName("select");
		var iLen = Els.length;    
		for(var i=0;i<iLen;i++){            
		  var oAtt = Els[i].attributes;
		  var ObjId= oAtt.getNamedItem("id").value;
		  $("#"+ObjId).css({"display":"block"});		 
		}
		//ѡ���ѯ��ʾ���
		ChoiceResult();
  	}  
	//=================================AJAXģ̬����=========================
</script>
<%
	String url = request.getParameter("url");
	String checkbox=request.getParameter("checkbox");
	String resi =request.getParameter("resi");
%>
<script type='text/javascript'>	
	//ת�򵽽������action
	function resultaction(sql){
		var resi ="<%=resi%>";
		var oForm =document.getElementById('resultaction');
		var rdf = document.getElementById("rdf");
		var rdm= document.getElementById("rdm");
		var rd ="";
		if(rdf.checked==true){
			rd="rdf";
		}
		if(rdm.checked==true){
			rd="rdm";
		}
		oForm.type.value=rd;
		oForm.type1.value=rd;
		oForm.sql.value=sql;
		oForm.url.value="<%=url%>";
		oForm.checkbox.value="<%=checkbox%>";
		if(1==resi){
			oForm.target="oper";
			oForm.action ="/db/page/info/synthesis/residentquery.do";
		}else{
			oForm.target="query";
			oForm.action ="/db/page/querymanage/infoqueryresult.do";
		}
		oForm.submit();
	}
	//ҵ����ҳ��
	function policyaction(amode){
		var oForm =document.getElementById('policyaction');	
		oForm.qpolicy.value=selpid;
		oForm.qselect.value=selselect;
		oForm.qfrom.value=selfrom;
		oForm.qwhere.value=selwhere;
		oForm.qorder.value=selorder;
		oForm.target="oper";
		if(amode=="4"){//qmode=4[ҵ���߷�]
			oForm.action ="/db/page/policy/manage/policyinterview.jsp";	
		}else if(amode=="5"){//qmode=5[ҵ������]
			oForm.action ="/db/page/policy/approval/approval.jsp";
		}else if(amode=="6"){//qmode=6[��    ʾ]		
			
		}else if(amode=="7"){//qmode=7[���鴦��]
			
		}else if(amode=="8"){//qmode=8[ҵ���ѯ]
					
		}else if(amode=="9"){//qmode=9[ͳ�Ʒ���]
			
		}else if(amode=="10"){//qmode=10[ҵ��ɸѡ����]
			
		}
		parent.frames.operating.cols="*,0";
		oForm.submit();
	}
</script>