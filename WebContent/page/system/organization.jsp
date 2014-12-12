<%@ page language="java" pageEncoding="GB18030"%>
<%@page import="java.util.List"%>
<%@page import="com.mingda.entity.SysTOrganization"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>结构列表</title>
	<!--<script type="text/javascript" src="/db/page/js/ax.js"></script>-->
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
</head>
<style>
/*简单定义了一下全局*/
body {
	background-color: #FCDAD5;
	position: absolute;
	padding: 0;
	margin: 0px;
	font-size: 12px;
	font-family: Verdana, "宋体", Arial;
	color: #000;
}

body,td,th {
	font-size: 12px;
}
</style>
<script type="text/javascript">
var id="";//网页显示时用到的id
<!--
 function searchAccount(num,num1){
 id=num;
        var url = '/db/page/system/organizationGetChild.do';
        var pars="parentid="+num+"&l="+num1;
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'get',
        parameters: pars,
        onComplete: viewinfo
        });
        }
 function viewinfo(originalRequest){
 var req =originalRequest;
	var data=req.responseText;
	if(data=="empty"){
		var oFDIV=document.getElementById("c"+id);
		var oIMG=document.getElementById("img"+id);
		oIMG.src="/db/page/images/minus.png";
		oIMG.alt="折叠";
		oFDIV.style.display="none";
	}else{
		var oDIV=document.getElementById("c"+id);
		oDIV.innerHTML=data;
		}
	}
//-->
</script>
<%
	/*传送过来的url*/

		String url = (String) request.getAttribute("url");
		String tempflag = "";
		if (url.indexOf("?") == -1) {
			tempflag = "?";
		} else {
			tempflag = "&";
		}
		url = url + tempflag + "organizationId=";
		List orglist = (List) request.getAttribute("orglist");
		SysTOrganization sysTOrganization = (SysTOrganization) orglist
				.get(0);
%>
<body style="overflow-x: hidden;overflow-y:scroll;">
	<div style="width: 100%; border: 0 solid black;padding-left:4px;padding-top:2px;"
		id="TextScrollBox">
		<div style="position: relative; top: 0;" id="TextScroll">
			<div style="cursor: hand; position: relative; top: 0"
				id="f<%out.print(sysTOrganization.getOrganizationId());%>">
				<img id="img<%out.print(sysTOrganization.getOrganizationId());%>"
					src="/db/page/images/minus.png" alt="折叠"
					onClick="showhidden(<%out.print(sysTOrganization.getOrganizationId());%>,0)"></img>
				<%
					out
								.print("<span style=\"cursor : hand\" onclick=\"readyURL(this,"
										+ sysTOrganization.getOrganizationId()
										+ ")\" >");
				%>
				<%
					out.print(sysTOrganization.getOrgname());
				%>
				<%
					out.print("</span>");
				%>
			</div>
			<span id="c<%out.print(sysTOrganization.getOrganizationId());%>">
				<%
					for (int i = 1; i < orglist.size(); i++) {
							sysTOrganization = (SysTOrganization) orglist.get(i);
				%>
				<div style="cursor: hand" id="f<%out.print(sysTOrganization.getOrganizationId());%>">
					&nbsp;&nbsp;&nbsp;
					<img id="img<%out.print(sysTOrganization.getOrganizationId());%>"
						src="/db/page/images/plus.png" alt="展开"
						onClick="showhidden(<%out.print(sysTOrganization.getOrganizationId());%>,1)"></img>
					<%
						out.print("<span style=\"cursor : hand\" onclick=\"readyURL(this,"
												+ sysTOrganization.getOrganizationId()
												+ ")\" >");
					%>
					<%
						out.print(sysTOrganization.getOrgname());
					%>
					<%
						out.print("</span>");
					%>
				</div> <span style="cursor: hand; display: none"
				id="c<%out.print(sysTOrganization.getOrganizationId());%>"></span>
				<%
					}
				%> </span>
		</div>
	</div>
	<%-- 
	<img onmouseover="MoveTextDiv(this,'up',2)"
		src="/db/page/images/menu_pageup.gif" alt="向上" border="0" width="16px"
		height="16px" />
	<img onmouseover="MoveTextDiv(this,'down',2)"
		src="/db/page/images/menu_pagedown.gif" alt="向下" border="0"
		width="16px" height="16px" />--%>
</body>
<script type="text/JavaScript">
<!--
function MM_goToURL() {
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
function readyURL(obj,orgid) {
var url="<%=url%>";
var oObj=obj;
url=url+orgid;
//alert(url);
MM_goToURL('parent.frames[\'leftFrame\']',url);
}
//-->
</script>
</html:html>
<script>
<!--num1:显示层号 num:结构父id-->
function showhidden(num,num1){
var img = document.getElementById("img"+num);
var f =document.getElementById("f"+num);
var c =document.getElementById("c"+num);
var rUrl="/db/page/system/organizationGetChild.do?l="+num1+"&parentid="+num;
if(img.alt=="折叠"){
img.src="/db/page/images/plus.png";
c.style.display="none";
img.alt="展开";
return false;
}
if(img.alt=="展开"){
img.src="/db/page/images/minus.png";
c.style.display="block";
img.alt="折叠";
searchAccount(num,num1);
return false;
}
}
</script>
<script>     
<!--
function MoveTextDiv(obj,action,len){//上下移动层
function UpMove(){
if(TextScroll.offsetTop<0){
TextScroll.style.pixelTop+=len;
}
}
function DownMove(){
if(TextScroll.offsetTop>(TextScrollBox.offsetHeight-TextScroll.offsetHeight)){
TextScroll.style.pixelTop-=len;
}
}
scro=setInterval(action=="up"?UpMove:DownMove,50);
function obj.onmouseout(){
clearInterval(scro);
}
function out(){
clearInterval(scro);
}
}
-->
</script>
<script>
<!--
function sc(){
document.getElementById("TextScrollBox").style.height=document.body.offsetHeight-16;}
setInterval(sc,50);
-->
</script>