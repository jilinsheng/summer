<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title><bean:write name="title" scope="request" /></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
<!--
body {
	margin: 0;
	padding: 0;
	font-family: "宋体";
	font-size: 12px;
}

.leftstyle {
	background: url(../../images/newsleft.gif) no-repeat;
	background-position: left;
	width: 140px;
	height: 25px;
}

.rightstyle {
	background: url(../../images/newsright.gif) no-repeat;
	background-position: right;
	height: 25px;
}

.centerstyle {
	background: url(../../images/newscenter.gif);
	background-repeat: repeat;
}

.borderstyle {
	border-width: 0px 1px 1px 1px;
	border-style: solid;
	border-color: #C2523A;
}

.captionstyle {
	background: url(../../images/leftbtn.png) no-repeat;
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	font-weight: 600;
	padding-left: 15px;
	padding-top: 3px;
}

.more {
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	font-weight: 400;
}

.content {
	padding-left: 18px;
	padding-top: 3px;
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	background-image: url(../../images/index_left1_46.gif);
	background-repeat: no-repeat;
	background-position: 3px;
}
.table1 {
border-collapse:collapse;
}
.table1 th {
	font-weight:lighter;
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	color: #000000;
	background: #ECECEC;
	width: 100px;
}

.table1 td {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	height: 25px;
}
.sort {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	color: #0000FF;
	background: #ECECEC;
}

caption {
	background-color: #FCDAD5;
	height: 25px;
	text-align: center;
}
-->
</style>
<script type="text/javascript" src="../../js/jquery0.js"></script>
	<script type="text/javascript" src="../../js/jBox-1.0.0.0.js"></script>
	<link rel="stylesheet" href="../../css/jBox.css" type="text/css"></link>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="leftstyle">
				<span class="captionstyle"><bean:write name="caption"
						scope="request" filter="false" /> </span>
			</td>
			<td width="60%" align="right" class="centerstyle">
				<div class="rightstyle">
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="borderstyle">
				<table class="table1" width="100%" cellspacing="0" cellpadding="0"
					border="0">
					<bean:write name="info" scope="request" filter="false" />
				</table>
			</td>
		</tr>
	</table>

</body>
</html:html>
<script>
	<!--    
	var js=0;
			function showlist(nodeName , pid){
		     js=js+1;
			var bWidth = document.body.clientWidth-30;  //背景宽度
			var bHeight= document.body.clientHeight-80; //背景高度
			 //背景高度
			var oUrl='newchildlist.do?nodeName='+nodeName+'&pid='+pid;
			jBox.open("iframe-jBoxID2","iframe",oUrl,"详细信息","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
		function divclose(){
			
				window.parent.frames['_iframe-'+js].style.display='none';
		}
	-->
</script>
