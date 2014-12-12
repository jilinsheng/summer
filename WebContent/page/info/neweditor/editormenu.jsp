<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String familyId = (String) request.getAttribute("familyId");
%>
<html>
<head>
<title>Tab�˵�</title>
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
	padding-left: 4px; /* ����޿�϶ */
	padding-bottom: 23px;
	border-bottom: 1px solid #11a3ff; /* �˵����±߿� */
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
	display: block; /* ��Ԫ�� */
	color: #006eb3;
	text-decoration: none;
	padding: 5px 10px 3px 10px;
}

ul#tabnav a:hover {
	background-color: #006eb3;
	color: #FFFFFF;
}

body#home li.home,body#news li.news, /* ��ǰҳ��Ĳ˵��� */ body#sports li.sports,body#music li.music
	{
	border-bottom: 1px solid #FFFFFF; /* ��ɫ�±߿򣬸���<ul>�е���ɫ�±߿� */
	color: #000000;
	background-color: #FFFFFF;
}

body#home li.home a:link,body#home li.home a:visited, /* ��ǰҳ��Ĳ˵���ĳ����� */
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
		target="mainFrame" onclick="select(this)">������Ϣ</a></li>
	<li><a href="memberbaseinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">��Ա��Ϣ</a></li>
	<li><a href="incomeinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">����֧��</a></li>
	<li><a href="assetinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">�ʲ�</a></li>
	<!--<li>
				<a href="familyclass.do?codeId=<%=familyId%>&code=FAMILYCLASS" target="mainFrame" onclick="select(this)">��ͥ����</a>
			</li>
			-->
	<li><a href="annexinit.do?familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">֤�������ϴ�</a></li>
		<li><a href="mutilupload.do?qq=000&familyId=<%=familyId%>"
		target="mainFrame" onclick="select(this)">���֤��ί�����ϴ�</a></li>
	<!--<li>
			 editorInfoCardTrees.do?nodeName=FAMILY&nodeId=4745278 
				<a href="../../info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId=<%=familyId%>" target="mainFrame" onclick="select(this)">��ͥ��չ��Ϣ��д</a>
			</li>-->
</ul>
</body>
</html>
