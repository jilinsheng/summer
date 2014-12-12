<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="org.dom4j.Document"%>
<%@page import="com.mingda.common.dictionary.DictionaryHandle"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>成员信息</title>
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>
	<style type="text/css">
<!--
a {
	text-decoration: none;
}
-->
</style>
	<script type="text/javascript" src="../../js/Validator.js"></script>
	<script type="text/javascript" src="../../js/Calendar2.js"></script>
	<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript">
				 var ids=false;
			function checkonload(){
		 		if (''==inputform.paperid.value){
			 		ids=false;
			 	}else{
			 		iteratorpaper(inputform.memberId.value,inputform.paperid.value);
				 	}
			 	}
				 function checkform(){
				 	 if(""==inputform.paperid.value||0==inputform.papertype.value){
					 	alert("证件类型或证件号码没有填写!");
						return false;
					 }else{
						 if(""==inputform.membername.value){
							 	alert("姓名不能为空");
							  	return false;
							 }
							 if(!CheckDate("出生日期",inputform.birthday,true)){
								 return false;
							 }
							  if(0==inputform.relmaster.value){
							 	alert("与户主关系未选择");
							 	return false;
							 }
							 if(0==inputform.rprtype.value){
							 	alert("户口类型未选择");
							 	return false;
							 }
						 if("390"==inputform.papertype.value){
							 if(!CheckIdCard('身份证号码',inputform.paperid)){
							 	return false;
							 }else{
							 	iteratorpaper(inputform.memberId.value,inputform.paperid.value);
							}
							 return ids;
						 }
					 }
					
					 return true;
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
						ids= false;
						alert("证件号码重复，请核对身份证码，重新输入！");
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
						//document.getElementById("inch").src=o;
						//alert(document.getElementById("inch").src);
						document.getElementById("inchdiv").innerHTML='<img id ="inch" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=\'scale\',src=\'' + o +'\');width:130;height:160" src=\'\'/>';
					}
				}
			 
		</script>
	<script type="text/javascript">
	 
				function add(){
					var familyId = ${familyId};
					window.location.reload("memberbaseinit.do?type=&familyId="+familyId);
				}
			function c(v1,v2){
				var url="removememberinit.do?familyId="+v1+"&memberId="+v2;
				var f="dialogWidth=400px;dialogHeight=300px;status=no;help=no;scroll=auto";
				window.showModalDialog(url,window,f);
				window.location.reload();
			}
			function up(){
				var url="uploadpicture.jsp";
				var f="dialogWidth=800px;dialogHeight=600px;status=no;help=no;scroll=auto";
				window.showModalDialog(url,window,f);
				//window.location.reload();
			}
		</script>
	<body onload="checkonload()">
		<table width="99%" cellspacing="0" cellpadding="0" class="table8">
			<tr>
				<th>
					姓名
				</th>
				<th>
					证件类型
				</th>
				<th>
					证件号
				</th>
				<th>
					与户主关系
				</th>
				<th colspan="2">
					操作
				</th>
			</tr>
			<c:out value="${memberlist}" escapeXml="false"></c:out>
		</table>
		<button class="btn" onclick="add()">
			新建成员
		</button>
		<br>
		<br>
		<div align="center" style="color: #ff0000">
			<c:out value="${model}"></c:out>
		</div>
		<form name="inputform" action="memberbasesave.do" method="post"
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
