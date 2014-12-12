<%@ page language="java" pageEncoding="GB18030"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mingda.entity.*"%>
<%@page import="java.net.URI"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style>
/*简单定义了一下全局*/
body {
	background-color: #D9EEFF;
	padding: 0;
	margin: 0px;
	font-size: 12px;
	line-height: 1.7;
	font-family: Verdana, "宋体", Arial;
	list-style: none;
}

span {
	width: 148px;
	height: 22px;
	display: block;
	background: #006FC0;
	padding-top: 2px;
	padding-left: 0px;
	color: #FFFFFF;
	font-weight: 600;
	text-align: center;
	border-bottom: 1px solid #0295FF;
}
</style>
		<title>系统菜单</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
		<script type="text/javascript" src="js/dtree.js"></script>
	</head>

	<body style="overflow: hidden">
		<div style="width: 100%; overflow: hidden; border: 0 solid black"
			id="TextScrollBox">
			<div style="position: relative; top: 0;" id="TextScroll">
				<script type="text/javascript">
		<!--
		d = new dTree('d');
		<%  
			SysTEmployee employee =(SysTEmployee)session.getAttribute("employee");
			String strnav =employee.getSysTOrganization().getOrgname()+"&nbsp;"+ employee.getSysTEmpext().getName();
			List menu = (List) request.getAttribute("sysmenu");
			Iterator it = menu.iterator();
			if (it != null) {
			out.print("d.add(0,-1,'"
										+ strnav
										+ "','','','','','','');");
				while (it.hasNext()) {
				String target ="";
				String url="";
				String ico="";
					SysTPrivilege sysTPrivilege = (SysTPrivilege) it.next();
					if(sysTPrivilege.getUrl()==null||sysTPrivilege.getUrl().equals("")||sysTPrivilege.getUrl().equals("#")){
					url="welcome.jsp?";
					target="operatingzone";
					}else{
					
						if(sysTPrivilege.getTarget()==null||sysTPrivilege.getTarget().equals("")){
						//查询器框架区
						url = sysTPrivilege.getUrl();
						url=url.replace("?","&");
						url="/db/page/info/search/infoquery.jsp?url="+url;
						target="_self";
						}else{
						//操作框架区 operatingzone
						url=sysTPrivilege.getUrl();
						url=url.replace("?","&");
						target="operatingzone";
						url="operatingzone.jsp?url="+url;
						}
						}
						if(sysTPrivilege.getIco()==null||sysTPrivilege.getIco().equals("")){
						}else {
						ico="/db/page/tree/images/"+sysTPrivilege.getIco();
						}
						strnav = sysTPrivilege.getNav();
						out
								.print("d.add("
										+ sysTPrivilege.getPrivilegeId()
										+ ","
										+ sysTPrivilege
												.getParentprivilegeid()
										+ ",'"
										+ sysTPrivilege.getDisplayname()
										+ "','"+
										url+ "&strnav="
										+ strnav
										+ "&menuname="
										+ strnav
										+ "&menuid="
										+ sysTPrivilege.getPrivilegeId()
										+ "','"
										+ sysTPrivilege.getDisplayname()
										+ "','"+target+"','','','"+ico+"');");
					}

			}
		%>
		document.write(d);

		//-->
	</script>
			</div>
		</div>
		<table border="0" width="100%" cellpadding="0" cellspacing="0"
			bgcolor="">
			<tr>
				<td align="right">
					<img onMouseOver="MoveTextDiv(this,'left',4)"
						src="/db/page/images/menu_pageleft.gif" alt="向左" border="0"
						width="16px" height="16px" />
					<img onMouseOver="MoveTextDiv(this,'up',4)"
						src="/db/page/images/menu_pageup.gif" alt="向上" border="0"
						width="16px" height="16px" />
					<img onMouseOver="MoveTextDiv(this,'down',4)"
						src="/db/page/images/menu_pagedown.gif" alt="向下" border="0"
						width="16px" height="16px" />


					<img onMouseOver="MoveTextDiv(this,'right',4)"
						src="/db/page/images/menu_pageright.gif" alt="向右" border="0"
						width="16px" height="16px" />
				</td>
			</tr>
		</table>
	</body>
</html>
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
function LeftMove(){
if(TextScroll.offsetLeft<0){
TextScroll.style.pixelLeft+=len;
}
}
function RightMove(){
if(TextScroll.offsetLeft>(TextScrollBox.offsetWidth-TextScroll.offsetWidth)){
TextScroll.style.pixelLeft-=len;
}
}
var l;
if(action=="up"){
l=UpMove;
}else if(action=="down"){
l=DownMove;
}
else if (action=="right"){
l=RightMove;
}
else if(action=="left"){
l=LeftMove;
}
else{}
	scro=setInterval(l,50);
	function obj.onmouseout(){
	clearInterval(scro);
	}
	function out(){
		clearInterval(scro);
	}
	}
function sc(){
	document.getElementById("TextScrollBox").style.height=document.body.offsetHeight-20;}
	setInterval(sc,50);
-->
</script>
