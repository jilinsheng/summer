<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%
	SysTEmployee employee = (SysTEmployee) session
			.getAttribute("employee");
	String onno = employee.getSysTOrganization().getOrganizationId();
	String empno = employee.getEmployeeId().toString();
%>
<%
	//��ѯ����ģʽ
	String reqmode = request.getParameter("qmode");
	if (reqmode == null) {
		//Ĭ��Ϊ��Ϣ��ѯ
		reqmode = "1";//����������
	}
	//ҵ��ѡ��
	String reqpolicy = request.getParameter("qpolicy");

	if (reqpolicy == null) {
		//Ĭ��Ϊ��
		reqpolicy = "-1"; //ѡ��ҵ��[��ѡ]
	}
	//ҵ��������׼ѡ��
	String reqcheck = request.getParameter("qcheck");
	if (reqcheck == null) {
		//Ĭ��Ϊ��
		reqcheck = "-1"; //ѡ��ҵ��������׼[��ѡ]
	}	
	//ҵ����������ѡ��
	String reqflow = request.getParameter("qflow");
	if (reqflow == null) {
		//Ĭ��Ϊ��
		reqflow = "-1"; //ѡ��ҵ����������[��ѡ]
	}
	//ҵ������ѡ��
	String reqresult = request.getParameter("qresult");
	if (reqresult == null) {
		//Ĭ��Ϊ��
		reqresult = "-1"; //ѡ��ҵ������[��ѡ]
	}
	//ҵ������ѡ��
	String reqflag = request.getParameter("qflag");
	if (reqflag == null) {
		//Ĭ��Ϊ��
		reqflag = "-1"; //ѡ��ҵ������[��ѡ]
	}
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>������Ϣ��ѯ</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->

	<script type="text/javascript" src="page/js/jquery.js"></script>
	<script type="text/javascript" src="page/js/dynamictree.js"></script>

<style type="text/css">
<!--
body {
	margin: 0;
	margin-top: 0;
	padding: 0;
	background: #FCDAD5;
	font-family: "����";
	font-size: 12px;
}

input {
	font-family: "����";
	font-size: 12px;
}

.pointer {
	cursor: pointer;
}

.caption {
	font-size: 12px;
	color: #6BA6FF;
}

.itemstyle {
	font-size: 12px;
	color: #6BA6FF;
}

.queryform {
	line-height: 2;	
	height: 20px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	text-align: center;
	font-size: 12px;
	color: #660099;	
	background: url('/db/page/images/newscenter.gif');
}

#pagestatusdiv {	
	z-index: 2;	
	font-weight: bold;
	color: #FF0099;
	font-size: 14px;
	display: none;
}
-->
</style>

  <script type="text/javascript">  
    //��������������      
    var mode;      //����ģ�����
    var empid = "";        //��ǰ��¼�û���� 
    var qmode = "";        //��ǰ��ѯ���ô���ģʽcode   
            
    var deptid = "";        //��ǰ��¼���� 
    var deptfieldname = "";    //��ǰ�����ֶ�     
    var deptobjid = "";      //��ǰ����ѡ�ж���    
    var seldeptid;    //ѡ�л���
    var seldeptname;
    var seldeptfullname;
        
    var selselect = "";    //ѡ���ѯ����ֶ�
    var selfrom = "";    //ѡ���ѯ�����
    var selwhere = "";    //ѡ���ѯ�������
    var selorder = "";    //ѡ���ѯ�������
    
    //ҵ���ѯ������ID 
    var listpolicy = "selectpolicy";//ҵ��ѡ��������ID
    var listpolicycheck = "selectpolicycheck";//ҵ��������׼ѡ��������ID    
    var listpolicyflow = "selectpolicyflow";//ҵ����������ѡ��������ID
    var listpolicyresult = "selectpolicyresult";//ҵ������ѡ��������ID
    var listpolicyflag = "selectpolicyflag";//ҵ������ѡ��������ID
    
    
    var selpolicy = "";    	//ѡ��ҵ��
    var selpolicycheck = "";    //ѡ��ҵ��������׼    
    var selpolicyflow = "";    //ѡ��ҵ����������
    var selpolicyresult = "";    //ѡ��ҵ������
    var selpolicyflag = "";    //ѡ��ҵ������
    
    
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
    
    //==================================�ַ�ȥ����β�ո�BEG=================================
  
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
    //=================================�ַ�ȥ����β�ո�END===========================
    //ִ�в�ѯ
    function ExeSQL(){   
		var oForm,Els,i,iLen,oAtt,ObjId;
		var tvalueid,tqueryid,ttableid,tVvalue,tQvalue,tTvalue,tRvalue,ttxt,tid;
		var tfieldid,tFieldvalue;
		//��ѯ���
		selselect = "";
		selfrom = "";
		selwhere = ""; 
		
		//QueryManageServlet.java������Ϣ��ѯqueryinfoform  
		oForm =document.getElementById("queryinfoform");		
		Els= oForm.elements;
		iLen = Els.length;    
		for(i=iLen-1;i>=0;i--){            
		  	oAtt = Els[i].attributes;
		  	ObjId= oAtt.getNamedItem("id").value;
		  	if(ObjId.length>5){//query+id ��value+id��table+id��field+id
		    	ttxt =  ObjId.substring(0,5);  
		    	tid =  ObjId.substring(5,ObjId.length);
		    
		    	//QueryManageServlet.java��QueryManage.java����DIV		    
		    	if(ttxt=="query" || ttxt=="qdept" || ttxt=="qfmid"|| ttxt=="qmmid"){
			      	tvalueid =   "value"+tid;
			      	ttableid =   "table"+tid;
			      	tfieldid =   "field"+tid;
			      	//
			      	if(ttxt=="qfmid"){//��ͥ���ֶ�
			        	tqueryid =   "qfmid"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;		       
			      	}else if(ttxt=="qmmid"){//��Ա���ֶ�
			      		tqueryid =   "qmmid"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;
			      	}else if(ttxt=="query"){//
			      		tqueryid =   "query"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;
			      	}else if(ttxt=="qdept"){//�������⴦��
			        	tqueryid =   "qdept"+tid;		        
			        	//
			        	tQvalue =document.getElementById(tqueryid).value;
			        	if(tQvalue.length>0){
			        		tQvalue =seldeptid;	
			        	}else{
			        		tQvalue = "";	
			        	}  
			        	//		        
			      	}
			      	//alert("id: "+tqueryid+"val: "+tvalueid+"tab: "+ttableid+"field: "+tfieldid);
			      	tVvalue =document.getElementById(tvalueid).value;
			      	tTvalue =document.getElementById(ttableid).value;
			      	tFieldvalue =document.getElementById(tfieldid).value;
			      	//
			      	tQvalue = JHshTrim(tQvalue);
			      	tVvalue = JHshTrim(tVvalue);
			      	tTvalue = JHshTrim(tTvalue);
			      	tFieldvalue = JHshTrim(tFieldvalue);
			      	//
			      
			      	//alert(" :"+tQvalue+"  : "+tVvalue+"  :"+tTvalue+"  :"+tFieldvalue);
			      	//ѡ���ͥ���߳�Ա
				    var isrdf = document.getElementById("rdf");
				    var isrdm = document.getElementById("rdm");
				    //
			      	if(qmode=="0" || qmode=="1"){//������Ϣ��ҵ��������ҵ��������ѯ	    	
			          	//��ͥ��ѯ
			          	if(isrdf.checked == true){	
			          		if(ttxt=="qfmid"){//��ͥ���ֶ�
			          			//��ѯ�ֶ�                         
						      	var sf = selselect;
						      	if(MatchMatch(sf,tFieldvalue)==null){                              
						        	if (sf.length>0){ 
						          	tFieldvalue = tFieldvalue + ",";		            
						        	}
						        	//��ѯ�ֶ�
						        	selselect = tFieldvalue + selselect;  
						      	}		
			          		}    		
			      	 		//����ֵ��Ϊ��
					      	if(tQvalue.length>0){
					      		//��ѯ��                          
					      		var s = selfrom;
					      		if(MatchMatch(s,tTvalue)==null){                                
					        		if (s.length>0){            
					          		tTvalue = tTvalue + ",";
					        		}
					        		//��ѯ��
					        		selfrom = tTvalue + selfrom;  
					      		}					      		
					      		//����	
					      		//���������С��
					      		tRvalue = "��";                  
					      		//�滻����ֵ            
					      		var n = MatchReplace(tVvalue,tRvalue,tQvalue);
					      		//
						    	if(selwhere.length>0){
						      		selwhere = selwhere +" and "+ n;
						    	}else{
						      		selwhere = n;  
						    	}
					      	}	             
			      	  	}else if(isrdm.checked == true){
			      			if(ttxt=="qmmid"){//��Ա���ֶ�
			          			//��ѯ�ֶ�                         
						      	var sf = selselect;
						      	if(MatchMatch(sf,tFieldvalue)==null){                              
						        	if (sf.length>0){ 
						          	tFieldvalue = tFieldvalue + ",";		            
						        	}
						        	//��ѯ�ֶ�
						        	selselect = tFieldvalue + selselect;  
						      	}		
			          		} 
					      	//����		      
					      	//����ֵ��Ϊ��
					      	if(tQvalue.length>0){
					      		//��ѯ��                          
						      	var s = selfrom;
						      	if(MatchMatch(s,tTvalue)==null){                                
						        	if (s.length>0){            
						          	tTvalue = tTvalue + ",";
						        	}
						        	//��ѯ��
						        	selfrom = tTvalue + selfrom;  
						      	}
					      		//���������С��
					      		tRvalue = "��";                  
					      		//�滻����ֵ            
					      		var n = MatchReplace(tVvalue,tRvalue,tQvalue);
					      		//
						    	if(selwhere.length>0){
						      		selwhere = selwhere +" and "+ n;
						    	}else{
						      		selwhere = n;  
						    	}
						  	} 	
			      	  	}			      	  	
			      	}else if(qmode=="2"){//������Ϣ��ҵ��������ѯ	
			    	
			      	}else if(qmode=="3"){//������Ϣ��ҵ��������ѯ
			    		
			      	}else if(qmode=="4"||qmode=="5"||qmode=="6"||qmode=="7"||qmode=="8"||qmode=="9"||qmode=="10"){//������Ϣ��ҵ��������ѯ	    	 	
			    	  	//����ֵ��Ϊ��
				      	if(tQvalue.length>0){
				      		//��ѯ��                          
				      		var s = selfrom;
				      		if(MatchMatch(s,tTvalue)==null){                                
				        		if (s.length>0){            
				          		tTvalue = tTvalue + ",";
				        		}
				        		//��ѯ��
				        		selfrom = tTvalue + selfrom;  
				      		}
				      		//����	
				      		//���������С��
				      		tRvalue = "��";                  
				      		//�滻����ֵ            
				      		var n = MatchReplace(tVvalue,tRvalue,tQvalue);
				      		//
					    	if(selwhere.length>0){
					      		selwhere = selwhere +" and "+ n;
					    	}else{
					      		selwhere = n;  
					    	}
				      	}
			      	}		      	 
				}     
		  	}      
    	}
    	//����
    	var soid = "",soname = "";
    	if(isrdf.checked == true){	
    		soname = $("#forderitemquery").val();
	    	soid = $("#orderquery").val();
	    	selorder = 	soname + " " + soid; 
      	}else if(isrdm.checked == true){
      		soname = $("#morderitemquery").val();
	    	soid = $("#orderquery").val();
	    	selorder = 	soname + " " + soid;  
      	}
    	
    	//	    
	    //alert("select:"+selselect+"from:"+selfrom+"where:"+selwhere+" order "+selorder);
	    //
	    //
	    //��ѯ����ת������
	    GetPhySql();
    }
    
    //����ת��[�߼�����ת����������]
    function GetPhySql(){
    	var mode;    
	    var tselect,tfrom,twhere,torder,tmode,tbegpage,tendpage;
	    var tdept;  
	    var tpolicy,tpolicycheck,tpolicyflow,tpolicyresult,tpolicyflag;    
	    //      
	    tselect = JHshTrim(selselect);  
	    tfrom = JHshTrim(selfrom);
	    twhere = JHshTrim(selwhere);
	    //
	    torder = JHshTrim(selorder);
	    //	    
	    //������ѯģʽ��������ģ�鶨��tmode
	    tmode = "1";//ģʽ0ִ�в�ѯ1��ȡSQL���
	    tbegpage = "0";
	    tendpage = "0";
	    //����
	    tdept = deptid; 
	    //
	    tempid = empid;
	    
	    //ҵ��ѡ��
	    var olistpolicy = listpolicy;		          		
	    tpolicy = $("#"+olistpolicy).val();
	    //ҵ������
	    var olistpolicycheck = listpolicycheck;
	    tpolicycheck = $("#"+olistpolicycheck).val();	   
	    var olistpolicyflow = listpolicyflow;
	    tpolicyflow = $("#"+olistpolicyflow).val();
	    var olistpolicyresult = listpolicyresult;
	    tpolicyresult = $("#"+olistpolicyresult).val();
	    //ҵ������
	    var olistpolicyflag = listpolicyflag;
	    tpolicyflag = $("#"+olistpolicyflag).val();
	    //
	    //ѡ���ͥ���߳�Ա
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    //��ѯ����ģʽ�ڳ����������ж���
	    if(qmode=="0"){//��Ϣ��ѯ[������Ϣ�༭]	    	
	    	if(isrdf.checked == true){	    		
	      	 	mode = "exeallfamilysql";              
	      	}else if(isrdm.checked == true){
	      		mode = "exeallmembersql";  
	      	} 	
	    }else if(qmode=="1"){//������Ϣ��ҵ��������ҵ��������ѯ	    	
	    	if(isrdf.checked == true){	    		
	      	 	mode = "exefamilysql";              
	      	}else if(isrdm.checked == true){
	      		mode = "exemembersql";  
	      	} 	
	    }else if(qmode=="2"){//������Ϣ��ҵ��������ѯ	
	    	mode = "exefamilyoperchecksql";
	    	return;
	    }else if(qmode=="3"){//������Ϣ��ҵ��������ѯ
	    	mode = "exefamilyoperflagsql";
	    	return;    	
	    }else if(qmode=="4"||qmode=="5"||qmode=="6"||qmode=="7"||qmode=="8"||qmode=="9"||qmode=="10"){//������Ϣ��ҵ��������ѯ	
	    	//ת��ҵ�����ҳ��
	    	//window.parent.operatingzone.location.href="/db/page/policy/manage/policyrequest.jsp"+"?qpolicy="+tpolicy+"&qfrom="+tfrom+"&qwhere="+twhere;
	    	policyaction(qmode,tpolicy,tselect,tfrom,twhere,torder);
	    	return;
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
	            tdept: tdept,
	            tempid: tempid,	           
	            tpolicy: tpolicy,
	            tpolicycheck: tpolicycheck,
	            tpolicyflow: tpolicyflow,
	            tpolicyresult: tpolicyresult,
	            tpolicyflag: tpolicyflag               
	        },
	        function(result) {   //�ص�����
	        	//�����������
      			HiddenPageStatus();  	        	
	  			//
	          	if(result=="-1"){                
	             	//��ѯ��䲻�Ϸ�          
	          	}else{
	            	if(mode=="exefamilysql"){
	            		//��Ϣ��ͥ��ѯȡ���physql                  
		             	var fs= window.dialogArguments;		                
		                resultaction(result);
		            }else if(mode=="exemembersql"){
		            	//��Ϣ��Ա��ѯȡ���physql                  
		              	var fs= window.dialogArguments;		                
		                resultaction(result);
		      		}else if(mode=="exeallfamilysql"){
	            		//��Ϣȫ�ּ�ͥ��ѯȡ���physql                  
		             	
		            }else if(mode=="exeallmembersql"){
		            	//��Ϣȫ�ֳ�Ա��ѯȡ���physql                  
		              	
		      		}
	          	}                             
	    	});        
    }    

	//ȡ��ҵ��ѡ��
	function GetPolicyChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//��������		
		DisplayPageStatus();
		//
		$.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
		    {
		        action: "getPolicyChoice", //action����
		        sname: sname //����                              
		    },
		    function(result) {   //�ص�����
		    	//�����������
      			HiddenPageStatus();  	        	
	  			//		    	
		      	pardiv.innerHTML = result;
		      	//ҵ��ѡ��ҳ�����������		          	        	
		      	if(selpolicy!="-1"){		      				
		      		$("#"+sname).val(selpolicy);
		      		$("#"+sname).attr("disabled", "disabled");
		      	}
				//�����¼�
				$("#"+sname).change(function(){ChoicePolicyName();});	      	                                         
		    }
		);        
	}
	//ȡ��ҵ��������׼ѡ��������
	function GetPolicyCheckChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//��������		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
	        {
	            action: "getPolicyCheckChoice", //action����
	            sname: sname //����                              
	        },
	        function(result) {   //�ص�����
	        	//�����������
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result; 
	          	//ҵ��������׼ѡ��ҳ�����������		          	         	
	          	if(selpolicycheck!="-1"){	          		
	          		$("#"+sname).val(selpolicycheck);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}                              
	        }
	    );        
	}	
	//ȡ��ҵ����������ѡ��������
	function GetPolicyFlowChoice(pardivid,sname,pid){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  //	  
	  //
	  	//��������		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
	        {
	            action: "getPolicyFlowChoice", //action����
	            sname: sname, //����
	            pid: pid                              
	        },
	        function(result) {   //�ص�����
	        	//�����������
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result;	          		          	
	          	//ҵ������ѡ��ҳ�����������		          		          	
	          	if(selpolicyflow!="-1"){	          				
	          		$("#"+sname).val(selpolicyflow);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}	          	                                        
	        }
	    );        
	}
	//ȡ��ҵ������ѡ��������
	function GetPolicyResultChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//��������		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
	        {
	            action: "getPolicyResultChoice", //action����
	            sname: sname //����                              
	        },
	        function(result) {   //�ص�����
	        	//�����������
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result;
	          	//ҵ������ѡ��ҳ�����������		          		          	
	          	if(selpolicyresult!="-1"){	          				
	          		$("#"+sname).val(selpolicyresult);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}	          	                                        
	        }
	    );        
	}
	//ȡ��ҵ������ѡ��������
	function GetPolicyFlagChoice(pardivid,sname){
	  //      
	  var pardiv = document.getElementById(pardivid);
	  	//��������		
		//DisplayPageStatus();
		//
	    $.post("page/policy/query/PolicyQueryServlet",      //������ҳ���ַ
	        {
	            action: "getPolicyFlagChoice", //action����
	            sname: sname //����                              
	        },
	        function(result) {   //�ص�����
	        	//�����������
      			//HiddenPageStatus();  	        	
	  			//
	          	pardiv.innerHTML = result;
	          	//ҵ������ѡ��ҳ�����������			      			      	
	          	if(selpolicyflag!="-1"){	          				
	          		$("#"+sname).val(selpolicyflag);
	          		$("#"+sname).attr("disabled", "disabled");
	          	}	          	                                        
	        }
	    );        
	}	
	//ȡ���û���ѯҳ��
	function GetQueryExpHtml(empid,tmode){
	  //
	  	//��������		
		DisplayPageStatus();
		//
	    $.post("page/querymanage/QueryManageServlet",      //������ҳ���ַ
	        {
	            action: "getQueryExpHtml" , //action���� 
	            empid: empid                             
	        },
	        function(result) {   //�ص�����
	        	//	        	
	          	divquery.innerHTML = result; 
	          	//�����������
      			HiddenPageStatus();  	        	
	  			//	  			 
	          	//ѡ���ͥ���߳�Ա
			    ChoiceResult();                    
	        }
	    );        
	}
	//ѡ����ʾ���
	function ChoiceResult(){
		//ѡ���ͥ���߳�Ա
	    var isrdf = document.getElementById("rdf");
	    var isrdm = document.getElementById("rdm");
	    //  
	    if(isrdf.checked == true){	    		
      		$("#mbtd").css({"display":"none"}); 
      		$("#fmtd").css({"display":"block"});	            
      	}else if(isrdm.checked == true){
      		$("#fmtd").css({"display":"none"});
      		$("#mbtd").css({"display":"block"});
      	} 
	}
	//ѡ��ҵ��
	function ChoicePolicyName(){
		var olistpolicy = listpolicy;
		var olistpolicycheck = listpolicycheck;	
		var olistpolicyflow = listpolicyflow;
		var olistpolicyresult = listpolicyresult;
		var olistpolicyflag = listpolicyflag;
		var vpolicyid = $("#"+olistpolicy).val();
		//
		//
		if(vpolicyid=="-1"){//ѡ��[��]
			//ҵ��ѡ���ѡ��[��]
			$("#"+olistpolicycheck).val("-1");			
			$("#"+olistpolicyflow).val("-1");
			$("#"+olistpolicyresult).val("-1");
			$("#"+olistpolicyflag).val("-1");
			//	
			//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
			HiddenChoiceList();		
		}else{
			//��ʾ����ҵ���ѯѡ��
			DisplayChoiceList(vpolicyid);			
		}
		//
		//ҵ����������ѡ��
        GetPolicyFlowChoice("choicepolicyflow",listpolicyflow,vpolicyid); 
        //		
	}
	//��ʾҵ���ѯѡ������
	function DisplayChoiceList(policyid){
		//
		if(qmode=="0"){//��Ϣ��ѯ[ȫ�־�����Ϣ��ѯ]
			//
			//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
			HiddenChoiceList();	
			//
			//
			$("#queryoperform").css({"display":"none"});
			//
			$("#choicepolicy").css({"display":"none"});
			$("#spanpolicyitem").css({"display":"none"});
			$("#spanpolicy").css({"display":"none"});
			//		
		}else if(qmode=="1"){//������Ϣ��ҵ��������ҵ��������ѯ
			//		
			if(policyid == null || policyid=="-1"){
				//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
				HiddenChoiceList();
			}else{
				ShowChoiceList();
			}
			//
			//
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
		}else if(qmode=="2"){//������Ϣ��ҵ��������ѯ
			
			//		
			if(policyid == null || policyid=="-1"){
				//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
				HiddenChoiceList();
			}else{
				//
				ShowChoiceList();
				//
				$("#choicepolicyflag").css({"display":"none"});	
				$("#spanpolicyflagitem").css({"display":"none"});				
				$("#spanpolicyflag").css({"display":"none"});
				//						
			}
			//
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
			//		
			$("#rdm").css({"display":"none"});	
			$("#spanrdm").css({"display":"none"});
			//
		}else if(qmode=="3"){//������Ϣ��ҵ��������ѯ
			//
			
			//		
			if(policyid == null || policyid=="-1"){
				//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
				HiddenChoiceList();
			}else{
				//
				ShowChoiceList();
				//	
				$("#choicepolicycheck").css({"display":"none"});				
				$("#choicepolicyflow").css({"display":"none"});
				$("#choicepolicyresult").css({"display":"none"});				
				$("#spanpolicycheckitem").css({"display":"none"});				
				$("#spanpolicycheck").css({"display":"none"});
				
				$("#spanpolicyflow").css({"display":"none"});
				$("#spanpolicyresult").css({"display":"none"});
				//
			}
			//
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
			//		
			$("#rdf").css({"display":"none"});	
			$("#spanrdf").css({"display":"none"});
			//		
			$("#rdm").css({"display":"none"});	
			$("#spanrdm").css({"display":"none"});
			//
		}else if(qmode=="4"){//������Ϣ
			//		
			if(policyid == null || policyid=="-1"){
				//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
				HiddenChoiceList();
			}else{
				ShowChoiceList();
			}
			//
			//
			$("#queryoperform").css({"display":"none"});
			//
			$("#choicepolicy").css({"display":"none"});
			$("#spanpolicyitem").css({"display":"none"});
			$("#spanpolicy").css({"display":"none"});
		}else if(qmode=="5" ||qmode=="6" ||qmode=="7"||qmode=="8"||qmode=="9"){//������Ϣ��ҵ��ѡ���ѯ
			
			//		
			if(policyid == null || policyid=="-1"){
				//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
				HiddenChoiceList();
			}else{
				//��ҵ��������ҵ������ѡ��
				HiddenChoiceList();
			}
			//			
			$("#queryoperform").css({"display":"block"});
			//
			$("#choicepolicy").css({"display":"block"});
			$("#spanpolicyitem").css({"display":"block"});
			$("#spanpolicy").css({"display":"block"});
			//
			//		
			$("#rdf").css({"display":"none"});	
			$("#spanrdf").css({"display":"none"});
			//			
			$("#rdm").css({"display":"none"});	
			$("#spanrdm").css({"display":"none"});
			//
		}else if(qmode=="10"){//������Ϣ
			//		
			if(policyid == null || policyid=="-1"){
				//[��]ҵ��ѡ��ʱ��������ҵ���ѯѡ��
				HiddenChoiceList();
			}else{
				ShowChoiceList();
			}
			//
			//
			$("#queryoperform").css({"display":"none"});
			//
			$("#choicepolicy").css({"display":"none"});
			$("#spanpolicyitem").css({"display":"none"});
			$("#spanpolicy").css({"display":"none"});
		}		
	}
	//��ʾ��ѡ��ҵ���ѯѡ������
	function ShowChoiceList() {
		//
		$("#choicepolicycheck").css({"display":"block"});		
		$("#choicepolicyflow").css({"display":"block"});
		$("#choicepolicyresult").css({"display":"block"});		
		$("#spanpolicycheckitem").css({"display":"block"});		
		$("#spanpolicycheck").css({"display":"block"});
		
		$("#spanpolicyflow").css({"display":"block"});
		$("#spanpolicyresult").css({"display":"block"});	
		//
		$("#choicepolicyflag").css({"display":"block"});	
		$("#spanpolicyflagitem").css({"display":"block"});		
		$("#spanpolicyflag").css({"display":"block"});
		//
		//==============================xiu��ʱ�ر�=======================
		$("#spanpolicycheckitem").css({"display":"none"});	
		$("#trcheck").css({"display":"none"});				
		$("#trcheckflow").css({"display":"none"});
		$("#trcheckresult").css({"display":"none"});	
		//==============================xiu��ʱ�ر�=======================
				
	}
	//��������ҵ���ѯѡ������
	function HiddenChoiceList() {	
		//
		$("#choicepolicycheck").css({"display":"none"});
		
		$("#choicepolicyflow").css({"display":"none"});
		$("#choicepolicyresult").css({"display":"none"});		
		$("#spanpolicycheckitem").css({"display":"none"});		
		$("#spanpolicycheck").css({"display":"none"});
		
		$("#spanpolicyflow").css({"display":"none"});
		$("#spanpolicyresult").css({"display":"none"});	
		//
		$("#choicepolicyflag").css({"display":"none"});	
		$("#spanpolicyflagitem").css({"display":"none"});		
		$("#spanpolicyflag").css({"display":"none"});
		//
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
	//�򿪸������ô���
	function CallIniDialog()
	{		
		var myobj = new Array();
		myobj[0] = qmode;		
		showModalDialog("/db/page/querymanage/infoqueryini.jsp",myobj,"status:false;dialogWidth:500px;dialogHeight:400px");
		
		//ˢ��ҳ��
		//ȡ�ò�ѯ����ҳ��  
		GetQueryExpHtml(empid,qmode);
	}
	//�򿪸������ô���
	function CallAllIniDialog()
	{		
		showModalDialog("/db/page/info/search/infoquerynew.jsp","","status:false;dialogWidth:220px;dialogHeight:400px");		
	}    
    //��ʼ��ҳ��
    function IniPage(){    	
		//��ǰ��¼�û����    
		empid = "<%=empno%>";     
		//��¼����    
		deptid = "<%=onno%>";    //��ǰ��¼���� 
		//
		//��ѯ����[��request�ӹ���]
		qmode  = "<%=reqmode%>";		 //��ǰ��ѯ����ģʽΪ��Ϣ��ѯcode		
		//ȡ�ò�ѯ����ҳ��  
		GetQueryExpHtml(empid,qmode);		
		//
		//ѡ��ҵ���ѯ	
		selpolicy = "<%=reqpolicy%>";		 //ҵ��ѡ��
		selpolicycheck = "<%=reqcheck%>";		 //ҵ��������׼ѡ��
		selpolicyflow = "<%=reqflow%>";		 //ҵ����������ѡ��
		selpolicyresult = "<%=reqresult%>";		 //ҵ������ѡ��
		selpolicyflag = "<%=reqflag%>";		 //ҵ������ѡ��		
		//
		//ҵ��ѡ��
        GetPolicyChoice("choicepolicy",listpolicy);        
        //ҵ��������׼ѡ��
        GetPolicyCheckChoice("choicepolicycheck",listpolicycheck);        
        //ҵ����������ѡ��
        GetPolicyFlowChoice("choicepolicyflow",listpolicyflow,selpolicy);
        //ҵ������ѡ��
        GetPolicyResultChoice("choicepolicyresult",listpolicyresult);
        //ҵ������ѡ��
        GetPolicyFlagChoice("choicepolicyflag",listpolicyflag);
        //
        //��ʾ����ҵ���ѯѡ��
		DisplayChoiceList(selpolicy);
        //
      	      	
      	/***************************************
      	*[qmode]��ѯģʽ
      	*[0]ȫ�ֲ�ѯ
      	*[1]������Ϣ��ҵ��������ҵ��������ϲ�ѯ
      	*[2]������Ϣ��ҵ��������ѯ
      	*[3]������Ϣ��ҵ��������ѯ
      	****************************************/    	
    }
    //=================================ҳ�����END==================================
  </script>
	</head>
	<body onload="IniPage()" style="overflow-x:hidden;overflow-y:scroll">
		<script type="text/javascript" src="page/js/Calendar2.js"></script>
		<div id ="handlezone" style="display:block">
		<div id="divquery"></div>
		
		<div id="queryoperform">
			<table width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr class="queryform"  align="center">
					<td colspan='2'>ҵ���ѯѡ��</td>
				</tr>
				<tr align="center">
					<td colspan='2'>
						<span id='spanpolicyitem' class='caption'>ҵ���б�ѡ��</span>
					</td>
				</tr>
				<tr align="center">
					<td width="35%" valign="middle">
						<span id='spanpolicy' class='itemstyle' style="width: 100%">ҵ��ѡ��</span>
					</td>
					<td width="65%">
						<div id='choicepolicy' style="width: 100%"></div>
					</td>
				<tr align="center">
					<td colspan='2'>
						<span id='spanpolicycheckitem' class='caption'>ҵ������ѡ��</span>
					</td>
				</tr>
				<tr align="center" id = 'trcheck'>
					<td width="35%" valign="middle">
						<span id='spanpolicycheck' class='itemstyle' style="width: 100%">��������</span>
					</td>
					<td width="65%">
						<div id='choicepolicycheck' style="width: 100%"></div>
					</td>
				</tr>				
				<tr align="center" id = 'trcheckflow'>
					<td width="35%" valign="middle">
						<span id='spanpolicyflow' class='itemstyle' style="width: 100%">�������</span>
					</td>
					<td width="65%">
						<div id='choicepolicyflow' style="width: 100%"></div>
					</td>
				</tr>
				<tr align="center" id = 'trcheckresult'>
					<td width="35%" valign="middle">
						<span id='spanpolicyresult' class='itemstyle' style="width: 100%">��������</span>
					</td>
					<td width="65%">
						<div id='choicepolicyresult' style="width: 100%"></div>
					</td>
				</tr>
				<tr align="center">
					<td colspan='2'>
						<span id='spanpolicyflagitem' class='caption'>ҵ������ѡ��</span>
					</td>
				</tr>
				<tr align="center">
					<td width="35%" valign="middle">
						<span id='spanpolicyflag' class='itemstyle' style="width: 100%">����ѡ��</span>
					</td>
					<td width="65%">
						<div id='choicepolicyflag' style="width: 100%"></div>
					</td>
				</tr>
			</table>
		</div>
		<fieldset id = "resultfieldset">
			<legend>��ѯ��ʾ</legend>	
			<div align="center">
				<input type="radio" name="rd" id="rdf" value="rdf" onclick='ChoiceResult()' checked>
				<span id='spanrdf'>��ʾ��ͥ</span>
				</input>
				<input type="radio" name="rd" id="rdm" value="rdm" onclick='ChoiceResult()'>
				<span id='spanrdm'>��ʾ��Ա</span>
				</input>
			</div>
			<div align="center">
				<span class='pointer itemstyle' id="editexpitem"
					onclick='CallIniDialog()'>[��������]</span>
				<span class='pointer itemstyle' id="dyqueryitem" onclick='CallAllIniDialog()'>[�ۺϲ�ѯ]</span>			
			</div>
		</fieldset>
		<div align="center">
			<input id="btnexe" type='button' value=' �� ѯ ' onclick='ExeSQL()'></input>
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	   		<tr align="center">
	   			<td><div align="center" id='pagestatusdiv'><img src=/db/page/images/loadingtiao.gif></img></div></td>
	   		</tr>
   		</table>  


		<form id="resultaction">
			<input type="hidden" name="sql" />
			<input type="hidden" name="type" />
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
		</div>
	</body>
</html>

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
      	//ѡ���ֵ������choicedept����10λ
      	var cdept = deptobjid.substring(10,deptobjid.length);      
      	$("#"+cdept).val(seldeptname);
    	closedept();
  	}
  	//�ÿջ���ѡ��[DeptTreeServlet�����ķ���]
	function cleardept(){
		//ѡ����ò���
	    var cdept = deptobjid.substring(10,deptobjid.length);      
      	$("#"+cdept).val("");
    	closedept();	
	}
  	//�رջ���ѡ��[DeptTreeServlet�����ķ���]
  	function closedept(){
    	$("#bdiv").remove();
    	$("#odiv").remove();
    	showdiv();  
  	}
  	//����select
  	function hidediv(){ 
	  	//
		HiddenChoiceList();
		
		//
		$("#queryoperform").css({"display":"none"});
		//
		$("#choicepolicy").css({"display":"none"});
		$("#spanpolicyitem").css({"display":"none"});
		$("#spanpolicy").css({"display":"none"});
			 
	  	//������Ϣ��ѯѡ��������
	  	var Els=document.getElementById("queryinfoform").getElementsByTagName("select");
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
	  	var olistpolicy = listpolicy;  	
		var vpolicyid = $("#"+olistpolicy).val();  	
	  	//��ʾ��Ϣ��ѯѡ��������
	  	var Els=document.getElementById("queryinfoform").getElementsByTagName("select");
		var iLen = Els.length;    
		for(var i=0;i<iLen;i++){            
		  var oAtt = Els[i].attributes;
		  var ObjId= oAtt.getNamedItem("id").value;
		  $("#"+ObjId).css({"display":"block"});		 
		}
		DisplayChoiceList(vpolicyid);
	  	//
  	}  
  	//���ɲ���ѡ�����[TableInfoServlet�����ķ���]
  	function choicedept(objid){
    	deptobjid = "choicedept"+objid; 
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
                    oWidth:180,                              //�������ڿ��
                    oHeight:450                              //�������ڸ߶�
            };
            $.extend(defaults,options);
                       
            //����
            var cbtn = "<div id='cbtn'><img src='/db/page/images/close.gif'/><span id = 'stitle'>"+defaults.title+"</span></div><div>";
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
            $("#content").css({"font-size":"14px","padding":"1px 1px","overflow-x":"hidden","overflow-y":"auto","width":defaults.oWidth+"px","height":defaults.oHeight+"px"});
            $("#odiv").css({"background":defaults.oColor,"width":defaults.oWidth+"px","border":"1px black solid","z-index":"9999","position":"absolute","top":"1px","left":(document.body.clientWidth-defaults.oWidth)/2+"px"});
            $("#bdiv").css({"background":defaults.bColor,"width":defaults.bWidth,"height":defaults.bHeight,"z-index":"9998","position":"absolute","filter":"alpha(opacity:90)","left":10,"top":0});
    };
    })(jQuery);
    
    $(function(){         
  
          $("button").css({"display":"block","margin":"10px 0","width":"120px"});
          $("button:first").css("background","red");
          
          
    });
	//��ѯ������׼
	function queryDept(){
	  $.openWindow({"title":"����ѡ��","otype":"1"});
		hidediv();        
	}
    //=================================AJAXģ̬����=================================    
</script>

<%
	String url = request.getParameter("url");
	String checkbox=request.getParameter("checkbox");
	String resi =request.getParameter("resi");
%>
<script type="text/JavaScript">
<!--new ת�򵽽������action
function resultaction(sql){
	var resi ="<%=resi%>";
	var oForm =document.getElementById('resultaction');
	var rdf = document.getElementById("rdf");
	var rdm= document.getElementById("rdm");
	var rd ="";
	if(rdf.checked==true){
		rd=rdf.value;
	}
	if(rdm.checked==true){
		rd=rdm.value;
	}
	oForm.type.value=rd;
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
	//oForm.target="query";<link rel="stylesheet" href="../synthesis/dtree.css" type="text/css"></link>
	//oForm.action ="/db/page/querymanage/infoqueryresult.do";
	oForm.submit();
}
function policyaction(amode,apolicy,aselect,afrom,awhere,aorder){
	var oForm =document.getElementById('policyaction');	
	oForm.qpolicy.value=apolicy;
	oForm.qselect.value=aselect;
	oForm.qfrom.value=afrom;
	oForm.qwhere.value=awhere;
	oForm.qorder.value=aorder;
	oForm.target="oper";
	if(amode=="4"){//qmode=4[ҵ���߷�]
		oForm.action ="/db/page/policy/manage/policyinterview.jsp";	
	}else if(amode=="5"){//qmode=5[ҵ������]
		//oForm.action ="/db/page/policy/manage/policycheck.jsp";
		oForm.action ="/db/page/policy/approval/approval.jsp";
	}else if(amode=="6"){//qmode=6[��    ʾ]		
		oForm.action ="/db/page/policy/manage/policyarret.jsp";
	}else if(amode=="7"){//qmode=7[���鴦��]
		oForm.action ="/db/page/policy/manage/policycheck.jsp";
		oForm.action ="/db/page/policy/manage/policyrecheck.jsp";
	}else if(amode=="8"){//qmode=8[ҵ���ѯ]
		oForm.action ="/db/page/policy/manage/policyquery.jsp";		
	}else if(amode=="9"){//qmode=9[ͳ�Ʒ���]
		oForm.action ="/db/page/policy/manage/policyreport.jsp";
	}else if(amode=="10"){//qmode=10[ҵ��ɸѡ����]
		oForm.action ="/db/page/policy/manage/policyauto.jsp";
	}
	parent.frames.operating.cols="*,0";
	oForm.submit();
}
//-->
</script>
