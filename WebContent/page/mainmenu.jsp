<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
		<title>²Ëµ¥</title>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.change1 {
	color: #FFFFFF;
	font-size: 12px;
	font-weight: bold;
	background-color: #006FC0;
}
.change2 {
	color: #006FC0;
	font-size: 12px;
	font-weight: bold;
	background-color: #FFFFFF;
}
-->
</style>
		<script type="text/javascript">
function setTab(m,n){
var o1 = document.getElementById("content"+m);
var o2 = document.getElementById("content"+n);
var fra =document.getElementById("changezone");
o1.className="change1";
o2.className="change2";
if(m==2)
//fra.src="/db/page/system/organizationInit.do";
fra.src="/db/page/info/search/infoquery.jsp";
if(m==1)
fra.src="/db/page/readMenu.do";
}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--<tr>
				<td  id="content1" width="50%" height="20" class="change1" align="center">
					<span style="cursor: hand" onclick="setTab(1,2)">²Ë&nbsp;&nbsp;&nbsp;&nbsp;µ¥</span>
				</td>
				<td id="content2" width="50%" class="change2" align="center">
					<span style="cursor: hand" onclick="setTab(2,1)">²éÑ¯Æ÷</span>
				</td>
			</tr>-->
			<tr>
				<td colspan="2">
					<iframe style="overflow: hidden" name="changezone" frameborder="0"
						src="/db/page/info/search/infoquery.jsp" width="100%"></iframe>
				</td>
			</tr>
		</table>
	</body>
</html>
<script>
function countheight(){
    var fra =document.getElementById("changezone");
	fra.height = screen.height -93-window.screenTop;}
	setInterval(countheight,50);
</script>