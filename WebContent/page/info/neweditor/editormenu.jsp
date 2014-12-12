<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String familyId = (String) request.getAttribute("familyId");
%>
<html>
<head>
<title>Tab菜单</title>
<style>
body {
	margin-left: 0px;
	margin-top: 3px;
	margin-right: 0px;
	margin-bottom: 0px;
}

#ler {
	margin: 0px;
	width: 400px;
	padding: 0px;
	height: 100px;
	background-color: #99CC33;
}

ul#tabnav {
	list-style-type: none;
	margin: 0px;
	padding-left: 4px; /* 左侧无空隙 */
	padding-bottom: 23px;
	border-bottom: 1px solid #11a3ff; /* 菜单的下边框 */
	font: bold 12px verdana, arial;
}

ul#tabnav li {
	float: left;
	height: 22px;
	background-color: #a3dbff;
	margin: 0px 3px 0px 0px;
	border: 1px solid #11a3ff;
}

ul#tabnav a:link,ul#tabnav a:visited {
	display: block; /* 块元素 */
	color: #006eb3;
	text-decoration: none;
	padding: 5px 10px 3px 10px;
}

ul#tabnav a:hover {
	background-color: #006eb3;
	color: #FFFFFF;
}

body#home li.home,body#news li.news, /* 当前页面的菜单项 */ body#sports li.sports,body#music li.music
	{
	border-bottom: 1px solid #FFFFFF; /* 白色下边框，覆盖<ul>中的蓝色下边框 */
	color: #000000;
	background-color: #FFFFFF;
}

body#home li.home a:link,body#home li.home a:visited, /* 当前页面的菜单项的超链接 */
	body#news li.news a:link,body#news li.news a:visited,body#sports li.sports a:link,body#sports li.sports a:visited,body#music li.music a:link,body#music li.music a:visited
	{
	color: #000000;
	background-color: #FFFFFF;
}

body#home li.home a:hover,body#news li.news a:hover,body#sports li.sports a:hover,body#music li.music a:hover
	{
	color: #006eb3;
	text-decoration: underline;
}
</style>
<script type="text/javascript">
function select(obj){
	var collObjects = document.getElementsByTagName("a");

	for(var i=0; i<collObjects.length;i++){
		collObjects[i].style.background="a3dbff";
		collObjects[i].style.color="#006eb3";
	}
	
	obj.style.background="#006eb3";
	obj.style.color ="#FFFFFF";
}
</script>
</head>
<body id="blog">
<ul id="tabnav">
	<li><a href="familybaseinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">基本信息</a></li>
	<li><a href="memberbaseinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">成员信息</a></li>
	<li><a href="incomeinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">收入支出</a></li>
	<li><a href="assetinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">资产</a></li>
	<!--<li>
				<a href="familyclass.do?codeId=<%=familyId%>&code=FAMILYCLASS" target="mainFrame" onclick="select(this)">家庭分类</a>
			</li>
			-->
	<li><a href="annexinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">证明材料上传</a></li>
		<li><a href="mutilupload.do?qq=000&familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">身份证和委托书上传</a></li>
	<!--<li>
			 editorInfoCardTrees.do?nodeName=FAMILY&nodeId=4745278 
				<a href="../../info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId=<%=familyId%>" target="mainFrame" onclick="select(this)">家庭扩展信息填写</a>
			</li>-->
</ul>
</body>
</html>
