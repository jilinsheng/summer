<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	</link>
	<script language="javascript1.2" type="text/javascript">
		function shleft(){
			if (parent.operating.cols=="*,187"){
				parent.operating.cols="*,0";
				document.all.leftcon.innerHTML="<img src=\"images/conrightdown.gif\" width=\"8\" height=\"45\"/>";
			}
			else{
				parent.operating.cols="*,187";
				document.all.leftcon.innerHTML="<img src=\"images/conrightup.gif\" width=\"8\" height=\"45\"/>";
			}
		}
	</script>
	<link href="css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="vplace">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="left" valign="middle" id="leftcon" onClick="shleft();"
					style="cursor: hand">
					<img src="images/conrightup.gif" width="8" height="45" hspace="0"
						vspace="0" />
				</td>
			</tr>
		</table>
	</div>
</body>
</html:html>
