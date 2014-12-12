<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
	<head>
		<title>资产信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../../js/Validator.js"></script>
	</head>
	<script type="text/javascript">
		<!--
			function checkform(){
				var flag=true;
				if(!CheckNumber("土地", inputform.farmland, true)){return false;}
				if(!CheckNumber("水田", inputform.paddyfield, true)){return false;}
				if(!CheckNumber("旱田", inputform.glebe, true)){return false;}
				if(!CheckNumber("房屋间数", inputform.roomcnt, true)){return false;}
				if(!CheckNumber("建筑面积", inputform.buildarea, true)){return false;}
				if(!CheckNumber("大牲畜", inputform.animal, true)){return false;}
				if(!CheckNumber("农机具", inputform.farmmachine,true)){return false;}
				if(!CheckNumber("资产估值", inputform.estimation, true)){return false;}
				return flag;
			}
			function counts(){
				if(CheckNumber("土地", inputform.farmland, true)&&CheckNumber("水田", inputform.paddyfield, true)&&CheckNumber("旱田", inputform.glebe, true)){				
				inputform.glebe.value=inputform.farmland.value-inputform.paddyfield.value;
				}
			}
		-->
	</script>
	<body>
		<form action="assetsave.do" method="post" name="inputform"
			onsubmit="return checkform()">
			<input type="hidden" name="familyId" value="${familyId}" />
			<input type="hidden" name="assetId" value="${assetId}" />

			<c:out value="${requestScope.pagehtml}" escapeXml="fasle">
			</c:out>
			<div align="center">
				<input type="submit" value="保存" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn" value="返回" onclick="parent.location.reload('../../intro/modifamily.jsp')"/>
			</div>
		</form>
	</body>
</html>
