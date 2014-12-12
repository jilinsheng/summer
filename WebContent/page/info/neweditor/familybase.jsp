<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>基本信息</title>
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../../js/Validator.js"></script>
		<script type="text/javascript" src="../../js/Calendar2.js"></script>
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript">
			<!--
				 var ids=false;
			 	function checkonload(){
			 		if (''==inputform.paperid.value){
				 		ids=false;
				 	}else{
			 		iteratorpaper(inputform.memberId.value,inputform.paperid.value);
					 	}
				 	}
				 function checkform(){
				 	 var flag=true;
					 if('390'==inputform.papertype.value){
						 if(!CheckIdCard('身份证号码',inputform.paperid)){
						 	flag=false;
						 }else{
						 	iteratorpaper(inputform.memberId.value,inputform.paperid.value);
						 	flag=ids;
						 }
					 }
					 if(''==inputform.membername.value){
					 	alert('姓名不能为空');
					 	flag=false;
					 }
					 if(!CheckDate('出生日期',inputform.birthday,true)){
					 	flag=false;
					 }
					 return flag;
				 }
				 function iteratorpaper(memid,obj){
					var url = '../../info/editor/idcarditerance.do';
			        var pars="memberid="+memid+"&paperid="+obj;
			        var myAjax = new Ajax.Request(
							        url,{
							        method: 'post',
							        parameters: pars,
							        onComplete: iteratorpaperback
							        }
							        );
				}
				function iteratorpaperback(response){
					if(response.responseText==0){
						ids= true;
					}else{
						alert("证件号码重复，请核对身份证码，重新输入！");
						ids= false;
					}
					return ids;
				}
				function editbypaperid(){
					 if('390'==inputform.papertype.value){
						 if(!CheckIdCard('身份证号码',inputform.paperid)){
						 }else{
						 	var flag=true;
						 	iteratorpaper(inputform.memberId.value,inputform.paperid.value);
						 	if(flag){
								var idcard=inputform.paperid.value;
								var icdate;
								var icsex=0;
									if(!idcard==""){ 
										switch(idcard.length){
											case 15:
												icdate="19"+ idcard.substr(6,2)+"-"+ idcard.substr(8,2)+"-"+idcard.substr(10,2);
												if ((idcard.substr(idcard.length-1,1)%2)!=0){
													icsex=0; 
												}else {
													icsex=1;
												}
												break;
											case 18:
												icdate=idcard.substr(6,4)+"-"+ idcard.substr(10,2)+"-"+idcard.substr(12,2);
												if ((idcard.substr(idcard.length-2,1)%2)!=0){
													icsex=0; 
												}else {
													icsex=1;
												}
												break;					
										} 
									}else{
										inputform.birthday.value="";
									}  
									if(icdate!="undefined") 
									{
										inputform.birthday.value=icdate;
									}
									if(icsex=="0" || icsex=="1"){
										inputform.sex.value="11"+icsex;
									}
						 	}
						 }
					 }
				}
				function perImg(o){
					pimg=inputform.inch;
					if(o!=""){
						document.getElementById("inch").src=o;
					}
				}
			-->
		</script>
	</head>
	<body onload="checkonload()">
		<form name="inputform" action="familybasesave.do" method="post"
			enctype="multipart/form-data" onsubmit="return checkform()">
			<input type="hidden" name="familyId" value="${familyId}" />
			<input type="hidden" name="memberId" value="${memberId}" />
			<input type="hidden" name="picpath" value="${picpath}" />
			<input type="hidden" name="<%=Constants.TOKEN_KEY%>"
				value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>" />
			<c:out value="${requestScope.pagehtml}" escapeXml="fasle">
			</c:out>
			<div align="center">
				<input type="submit" value="保存" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn" value="返回" onclick="parent.location.reload('../../intro/modifamily.jsp')"/>
			</div>
		</form>
	</body>
</html>
