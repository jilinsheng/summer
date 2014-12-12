<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<bean:define id="mastername" name="mastername"></bean:define>
<bean:define id="familyId" name="entityId"></bean:define>
<bean:define id="masterId" name="masterId"></bean:define>
<bean:define id="mcols" name="mcols"></bean:define>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="expires" content="0"/>
		<title><%=mastername%>家庭信息</title>
		<style type="text/css">
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
	border-collapse: collapse;
}

.table1 th {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	font-weight: lighter;
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

.table2 th {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	font-weight: lighter;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	color: #000000;
	background: #ECECEC;
	width: 100px;
}

.table2 td {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
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
</style>
		<script type="text/javascript">
function memberdis(objid){
	var cols=<%=mcols%>;
	var obj = document.getElementById("member"+objid);
	if(obj.style.display=='block'){
		obj.style.display='none';
	}else{
		for(var i=0;i<cols;i++){ 
			document.getElementById("member"+i).style.display='none';
		}
		obj.style.display='block';
	}
}
</script>
		<script type="text/javascript" src="../../js/jquery0.js"></script>
	</head>
	<script type="text/javascript" src="../../js/jBox-1.0.0.0.js"></script>
	<style>
.jBox {
	position: absolute;
	border: 1px solid #72C5F9;
	visibility: hidden;
	background-color: white;
	text-align: left;
}

.jBoxHandler {
	padding: 5px 0px;
	text-indent: 3px;
	font: bold 12px Arial;
	background-color: #F5A89A;
	color: #000000;
	cursor: move;
	overflow: hidden;
	width: auto;
	height: 14px;
}

.jBoxHandler .jBoxControls {
	position: absolute;
	right: 8px;
	top: 0px;
	cursor: hand;
	cursor: pointer;
}

.jBoxContent {
	background-color: #fff;
	color: #000;
	height: 150px;
	padding: 1px 2px 1px 1px;
	overflow: auto;
}

.jBoxStatus {
	border-top: 1px solid #B9B9B9;
	background-color: #F8F8F8;
	height: 13px;
}

.jBoxResize {
	float: right;
	width: 13px;
	height: 13px;
	cursor: nw-resize;
	font-size: 0;
}
</style>
	<body>

		<table id="all" align="center" width="630" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td class="leftstyle">
					<span class="captionstyle"><%=mastername%> 家庭综合信息</span>
				</td>
				<td width="60%" align="right" class="centerstyle">
					<!--<span class="captionstyle"> <a
						href="/db/page/system/syslog/syslogFrame.jsp?nodeName=FAMILY&nodeId=<%=familyId%>"
						target="_balnk">信息变更日志</a> </span>
				--></td>
				<td class="centerstyle">
					<div class="rightstyle">
					</div>

				</td>
			</tr>
			<tr>
				<td colspan="3" class="borderstyle">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table class="table1" width="100%" border="0" cellspacing="0"
									cellpadding="0" style="border-bottom: 1px solid;">
									<logic:present name="familybase" scope="request">
										<bean:write name="familybase" scope="request" filter="false" />
									</logic:present>
								</table>
							</td>
						</tr>
						<tr>
							<td bgcolor="#FFFFE0">
								<logic:present name="member" scope="request">
									<bean:write name="member" scope="request" filter="false" />
								</logic:present>
							</td>
						</tr>
						<tr>
							<td height="25" background="../../images/titmember.gif"
								style="cursor: hand; padding-left: 12px"
								onclick="javascript:if(document.getElementById('incomeinfo').style.display=='none'){document.getElementById('incomeinfo').style.display='block'}else{document.getElementById('incomeinfo').style.display='none'}">
								<bean:write name="familyincomecaption" filter="false" />
							</td>
						</tr>
						<tr>
							<td id="incomeinfo" style="display: none">
								<table width="100%" cellpadding="0" cellspacing="0"
									class="table1">
									<bean:write name="familyincome" filter="false" />
								</table>
							</td>
						</tr>
						<tr>
							<td height="25" background="../../images/titmember.gif"
								style="cursor: hand; padding-left: 12px"
								onclick="javascript:if(document.getElementById('asset').style.display=='none'){document.getElementById('asset').style.display='block'}else{document.getElementById('asset').style.display='none'}">
								<bean:write name="assetcaption" filter="false" />
							</td>
						</tr>
						<tr>
							<td id="asset" style="display: none">
								<table width="100%" cellpadding="0" cellspacing="0"
									class="table1">
									<bean:write name="asset" filter="false" />
								</table>
							</td>
						</tr>
						<tr>
							<td height="25" background="../../images/titmember.gif"
								style="cursor: hand; padding-left: 12px"
								onclick="javascript:if(document.getElementById('payout').style.display=='none'){document.getElementById('payout').style.display='block'}else{document.getElementById('payout').style.display='none'}">
								<bean:write name="familypayoutcaption" filter="false" />
							</td>
						</tr>
						<tr>
							<td id="payout" style="display: none">
								<table width="100%" cellpadding="0" cellspacing="0"
									class="table1">
									<bean:write name="familypayout" filter="false" />
								</table>
							</td>
						</tr>
						<tr>
							<td height="25" background="../../images/titmember.gif"
								style="cursor: hand; padding-left: 12px"
								onclick="javascript:if(document.getElementById('payout').style.display=='none'){document.getElementById('annex').style.display='block'}else{document.getElementById('annex').style.display='none'}">
								<bean:write name="familyannexcaption" filter="false" />
							</td>
						</tr>
						<tr>
							<td id="annex" style="display: none">
								<table width="100%" cellpadding="0" cellspacing="0"
									class="table1">
									<bean:write name="familyannex" filter="false" />
								</table>
							</td>
						</tr>
						<tr>
							<td height="25" background="../../images/titmember.gif"
								style="cursor: hand; padding-left: 12px"
								onclick="javascript:if(document.getElementById('handle').style.display=='none'){document.getElementById('handle').style.display='block'}else{document.getElementById('handle').style.display='none'}">
								救助记录
							</td>
						</tr>
						<tr>
							<td id="handle" style="display: none">
								<table width="100%" cellpadding="0" cellspacing="0"
									class="table1">
									<bean:write name="sallist" filter="false" />
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr><!--
			<tr>
				<td colspan="3" class="borderstyle" align="center">
					<button onclick="javascript:window.close()">
						关闭
					</button>
				</td>
			</tr>
		--></table>
	</body>
</html>
<script>
	<!-- 
		function showlist(nodeName , pid){
			var bWidth = document.body.clientWidth-30;  //背景宽度
			//var bHeight= document.body.clientHeight-80; //背景高度
			var bHeight= 400; //背景高度
			var oUrl='newchildlist.do?nodeName='+nodeName+'&pid='+pid;
			jBox.open("iframe-jBoxID","iframe",oUrl,"详细信息显示","width="+bWidth+",height=400,center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
	function sc(){
		//document.getElementById("xz").style.height=document.body.offsetHeight-360;
		if (document.body.offsetWidth-20){
			document.getElementById("all").style.width=document.body.clientWidth-30};
	}
	setInterval(sc,50);
</script>
<script>
	<!-- 
		function shownode(nodeName , pid){
			var bWidth = document.body.clientWidth-30;  //背景宽度
			var bHeight= document.body.clientHeight-80; //背景高度
			var bHeight= 400; //背景高度
			var oUrl='newviewchild.do?nodeName='+nodeName+'&nodeId='+pid;
			jBox.open("iframe-jBoxID","iframe",oUrl,"详细信息","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
</script>