<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��ͥ������Ϣ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript">
		function ClassCommit(obj,nodeName,nodeId,propertyName){
		var obj1 =obj;
		var val="";
		var url = '/db/page/info/familyclass/ClassCommit';
		if(obj.innerHTML=="δ��ʵ"){
	   		val="0";
	    }else{
	      	val="1";
	    }
	  //  alert(val);
		var pars="nodeId="+nodeId+"&nodeName="+nodeName+"&propertyName="+propertyName+"&val="+val;
		var myAjax = new Ajax.Request(
	        url,
	        	{
	        		method: 'post',
	        		parameters: pars,
	        		onComplete: function (){ alert("����ɹ�");}
	        	});
	        	if(obj.innerHTML=="δ��ʵ"){
	        		//obj.src="/db/page/images/right.gif";
	        		obj.innerHTML="�Ѻ�ʵ";
	        		//obj.alt="�Ѻ�ʵ";
	        		b.value="ȡ��";
	        	}else{
	        		//obj.src="/db/page/images/quest.gif";
	        		//obj.alt="δ��ʵ";
	        		obj.innerHTML="δ��ʵ";
	        		b.value="ȷ��";
	        	}
	}
	</script>
	</head>
	<body>
		<c:out value="${str}" escapeXml="false"></c:out>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn" value="����" onclick="parent.location.reload('../../intro/modifamily.jsp')"/></p>
	</body>
</html>
