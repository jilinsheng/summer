<%@ page language="java" pageEncoding="GB18030"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mingda.entity.*"%>
<%@page import="java.net.URI"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <meta   http-equiv="Content-Type"   content="text/html;   charset=GB18030">
<html:html lang="true">
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
<head>
	<html:base />
	<title>系统主菜单</title>
	<link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
	<script type="text/javascript" src="js/dtree.js"></script>
</head>
<body onload="expandIt('p1')">
	<%
		List menu = (List) request.getAttribute("sysmenu");
			Iterator it = menu.iterator();
			if (it != null) {
				int i = 0;
				String strnav = "";
				while (it.hasNext()) {
					SysTPrivilege sysTPrivilege = (SysTPrivilege) it.next();
					if (sysTPrivilege.getDepth().intValue() == 1) {
						strnav = sysTPrivilege.getDisplayname();
						if (i > 0) {
							out.print("document.write(d" + i + ");");
							out.print("</script>");
							out.print("</div>");
						}
						i = i + 1;
						out
								.print("<span onclick=expandIt(\"p" + i
										+ "\")>");
						out.print(sysTPrivilege.getDisplayname());
						out.print("</span>");
						out.print("<div id=\"p" + i + "\">");
						out.print("<script type=\"text/javascript\">");
						out.print("d" + i + " = new dTree('d" + i + "');");
						out
								.print("d"
										+ i
										+ ".add("
										+ sysTPrivilege.getPrivilegeId().intValue()
										+ ",-1,'"
										+ strnav
										+ "','','','','/db/page/images/base01.gif','/db/page/images/base01.gif','/db/page/images/base01.gif');");
					}
					if (sysTPrivilege.getDepth() > 1) {
						String tempflag = "?";
						if (sysTPrivilege.getUrl() != null
								&& sysTPrivilege.getUrl().indexOf("?") >= 0) {
							tempflag = "&";
						}
						strnav = sysTPrivilege.getDisplayname();
						out
								.print("d"
										+ i
										+ ".add("
										+ sysTPrivilege.getPrivilegeId()
										+ ","
										+ sysTPrivilege
												.getParentprivilegeid()
										+ ",'"
										+ sysTPrivilege.getDisplayname()
										+ "','operatingzone.jsp?url="
										+ sysTPrivilege.getUrl()
										+ "&strnav="
										+ strnav
										+ "&menuname="
										+ strnav
										+ "&menuid="
										+ sysTPrivilege.getPrivilegeId()
										+ "','"
										+ sysTPrivilege.getDisplayname()
										+ "','operatingzone','/db/page/images/base01.gif','/db/page/images/base01.gif','/db/page/images/base01.gif');");
					}
				}
				out.print("document.write(d" + i + ");");
				out.print("</script>");
				out.print("</div>");
	%>
	<script language="javascript" type="text/javascript">
		var c="<%=i%>";
		function expandIt(p){
		var sts;
			sts=document.getElementById(p).style.display;
			for(var i=1;i<=c;i++){
				document.getElementById('p'+i).style.display = "none";
			}
			if (sts== "none"){
				document.getElementById(p).style.display = "";
			}else{
				document.getElementById(p).style.display = "none";
			}
		}		
	</script>
	<%
		}
	%>
</body>
</html:html>