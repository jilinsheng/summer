
<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>角色</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	<style type="text/css">
.rolediv {
	border: 1px solid #F5A89A;
}
</style>
	<script type="text/javascript" src="../js/dtree.js"></script>
	<link rel="stylesheet" href="../css/dtree.css" type="text/css"></link>
</head>

<body>
	<logic:present name="html" scope="request">
		<form name="inputForm" action="roleonesave.do" method="post">
			<table width="99%" cellspacing="3" cellpadding="0" border="0">
				<tr>
					<td valign="top">
						<bean:write name="html" scope="request" filter="false" />

					</td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" value="保存角色信息" />
					</td>
				</tr>
			</table>
		</form>
	</logic:present>
</body>
</html:html>
<script type="text/javascript">
<!-- 
	function ctlchild(obj ,id ,pid){
		var list =document.getElementsByTagName('input');
		for(var i = 0 ;i<list.length;i++){
			var temp = list[i];
			var atts = temp.attributes;
			var rule= atts.getNamedItem("id").value;
			if(id == rule){
			temp.checked=obj.checked;
			}
		}
	}		
-->
</script>
