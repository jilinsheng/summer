<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>dictvalue.jsp</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb18030" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
</head>
<%String dsid=(String)request.getAttribute("dsid"); %>
<body>
	 
	<logic:present name="table" scope="request">
	<button class="btn" onclick="add(<%=dsid%>)">新建字典值</button>
	<bean:write name="table" scope="request" filter="false" />
	<button class="btn" onclick="add(<%=dsid%>)">新建字典值</button>
	</logic:present>
</body>
</html:html>
<script type="text/javascript">
<!--
	function changeText(dvid ,dsid){
	alert(dvid+" "+ dsid);
		var ev=	document.getElementById("ev"+dvid);
		ev.style.display="block";
		var dv=	document.getElementById("dv"+dvid);
		dv.style.display="none";
	}
	function onsave(dvid,dsid,obj){
		var ev=	document.getElementById("ev"+dvid);
		var dv=	document.getElementById("dv"+dvid);
		if(confirm("确定要保存修改内容吗?")) 
			{
			 	var pars="dvid="+dvid+"&itname="+obj.value+"&model=2";
				var url = '/db/page/system/dictionary/dicthandle.do';
			    var myAjax = new Ajax.Request(
			     	url,
			        	{
			        		method: 'post',
			        		parameters: pars,
			        		onComplete:showresult
			        	}
			        );
				dv.innerText=obj.value;
		}
		dv.style.display="block";
		ev.style.display="none";
		alert(dvid+" "+dsid+""+obj.value);
	}
	function edit(dvid,status,obj){
		var statustitle="";
		if(confirm("确定要保存修改内容吗?"))
			{	
				if (status=="0"){
					status="1";
					statustitle="在用";
				}else{
					status="0";
					statustitle="停用";
				}
			 	var pars="dvid="+dvid+"&status="+status+"&model=3";
				var url = '/db/page/system/dictionary/dicthandle.do';
			    var myAjax = new Ajax.Request(
			     	url,
			        	{
			        		method: 'post',
			        		parameters: pars,
			        		onComplete:showresult
			        	}
			        );
				obj.innerText=statustitle;
		}
	}
	function showresult(){
		alert(保存成功);
	}
	function add(dsid){
		var oDoc= document.getElementById("main");
		var num= oDoc.rows.length;
		var lastcell = oDoc.rows[oDoc.rows.length-1].cells[0];
		if(lastcell.innerText!="保存"){
		var oRow =oDoc.insertRow(oDoc.rows.length);
		var oCell1= oRow.insertCell(0);
		oCell1.innerHTML="<span onclick=\"addnode("+dsid+","+num+")\">保存</span>";
	 	var oCell2=oRow.insertCell(1);
		oCell2.innerHTML="<input id=\"txt\" name=\"txt\" type=\"text\" />";
		var oCell3= oRow.insertCell(2);
		oCell3.innerHTML="<span>在用</span>";
		}else {
		 alert("请保存然后在新增信息");
		}
	}
	function addnode(dsid,num){
		var txt= document.getElementById("txt").value;
			var pars="dsid="+dsid+"&dvname="+txt+"&status=1&model=1&sequence="+num;
				var url = '/db/page/system/dictionary/dicthandle.do';
			    var myAjax = new Ajax.Request(
			     	url,
			        	{
			        		method: 'post',
			        		parameters: pars,
			        		onComplete:viewtr
			        	}
			        );
	}
	function viewtr(){
		window.location.reload();
	}
//-->
</script>
