<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>家庭分类信息</title>
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
		if(obj.innerHTML=="未核实"){
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
	        		onComplete: function (){ alert("保存成功");}
	        	});
	        	if(obj.innerHTML=="未核实"){
	        		//obj.src="/db/page/images/right.gif";
	        		obj.innerHTML="已核实";
	        		//obj.alt="已核实";
	        		b.value="取消";
	        	}else{
	        		//obj.src="/db/page/images/quest.gif";
	        		//obj.alt="未核实";
	        		obj.innerHTML="未核实";
	        		b.value="确认";
	        	}
	}
	</script>
	</head>
	<body>
		<c:out value="${str}" escapeXml="false"></c:out>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn" value="返回" onclick="parent.location.reload('../../intro/modifamily.jsp')"/></p>
	</body>
</html>
