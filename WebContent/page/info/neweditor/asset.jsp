<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
	<head>
		<title>�ʲ���Ϣ</title>
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
				if(!CheckNumber("����", inputform.farmland, true)){return false;}
				if(!CheckNumber("ˮ��", inputform.paddyfield, true)){return false;}
				if(!CheckNumber("����", inputform.glebe, true)){return false;}
				if(!CheckNumber("���ݼ���", inputform.roomcnt, true)){return false;}
				if(!CheckNumber("�������", inputform.buildarea, true)){return false;}
				if(!CheckNumber("������", inputform.animal, true)){return false;}
				if(!CheckNumber("ũ����", inputform.farmmachine,true)){return false;}
				if(!CheckNumber("�ʲ���ֵ", inputform.estimation, true)){return false;}
				return flag;
			}
			function counts(){
				if(CheckNumber("����", inputform.farmland, true)&&CheckNumber("ˮ��", inputform.paddyfield, true)&&CheckNumber("����", inputform.glebe, true)){				
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
				<input type="submit" value="����" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn" value="����" onclick="parent.location.reload('../../intro/modifamily.jsp')"/>
			</div>
		</form>
	</body>
</html>
