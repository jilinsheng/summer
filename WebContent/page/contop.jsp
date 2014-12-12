<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<link rel="stylesheet" href="css/main.css" type="text/css"></link>
	<script language="javascript1.2" type="text/javascript">
		function shtop(){
			if (parent.fullrow.rows!="60,8,*"){
				parent.fullrow.rows="60,8,*";
				document.all.topcon.innerHTML="<img src=\"images/contopup.gif\" width=\"45\" height=\"8\"/>";
			}
			else{
				parent.fullrow.rows="0,8,*";
				document.all.topcon.innerHTML="<img src=\"images/contopdown.gif\" width=\"45\" height=\"8\"/>";
			}
		}
	</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="8" align="center" valign="top" id="topcon"
				onclick="shtop();" style="cursor: hand">
				<img src="images/contopup.gif" width="45" height="8" />
			</td>
		</tr>
	</table>
</body>
</html:html>
