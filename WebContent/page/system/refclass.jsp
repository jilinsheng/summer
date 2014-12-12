<%@ page language="java" pageEncoding="GB18030"%>
<%@page import="org.dom4j.Element"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>ˢ·Ϣ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript">  
	function updatedata(objid){
		$('wait').style.display="block";  
		var pars="objid="+objid ; 
		var url = '/db/page/system/refclasssave.do'; 
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete:showresult
        	}
        );
        
	}
	function showresult(response){
		if(response.responseText==0){
		}else{
 			alert(response.responseText);
		}
		$('wait').style.display="none";
	}
	</script>
	<style type="text/css">
<!--
.colfield {
	border-width: 0px 1px 1px 0px;
	border-style: solid;
	border-color: #c0c0c0;
	color: #000066;
	height: 21px;
	background-image: url(../images/desktop_right_bg.gif);
	font-family: "Times New Roman", Times, serif;
	font-size: 9pt;
}

.colvalue {
	border-width: 0px 1px 1px 0px;
	border-style: solid;
	border-color: #c0c0c0;
	color: #000000;
	height: 25px;
	font-family: "Times New Roman", Times, serif;
	font-size: 9pt;
	cursor: hand;
}
 .btn3_mouseout {
             BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: 
            #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: 
            progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
            StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 
            1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; 
            BORDER-BOTTOM: #2C59AA 1px solid
            } 
            .btn3_mouseover {
             BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: 
            #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: 
            progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
            StartColorStr=#ffffff, EndColorStr=#D7E7FA); BORDER-LEFT: #2C59AA 
            1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; 
            BORDER-BOTTOM: #2C59AA 1px solid
            }
            .btn3_mousedown
            {
             BORDER-RIGHT: #FFE400 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: 
            #FFE400 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: 
            progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
            StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #FFE400 
            1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; 
            BORDER-BOTTOM: #FFE400 1px solid
            } 
            .btn3_mouseup {
             BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: 
            #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: 
            progid:DXImageTransform.Microsoft.Gradient(GradientType=0, 
            StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 
            1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; 
            BORDER-BOTTOM: #2C59AA 1px solid
            } 
-->
</style>
</head>
<body>
	<div id="wait"
		style="position: absolute; right: 0px; bottom: 0px;; display: none">
		<img src="../images/loadingtiao.gif"></img>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="border-width: 1px 0px 0px 1px; border-style: solid; border-color: #c0c0c0;">
		<%
			Element tbody = ((Element) request.getAttribute("info"));
				out.print(tbody.asXML());
				//log.debug(tbody.asXML());
		%>
	</table>
</body>
</html:html>
