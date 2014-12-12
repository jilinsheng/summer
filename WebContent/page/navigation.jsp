<%@ page language="java" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.mingda.entity.SysTOperatelog"%>
<%@page import="com.mingda.dao.SysTOperatelogDAO"%>
<%@page import="com.mingda.entity.SysTEmployee"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.mingda.common.SessionFactory"%>
<%@page import="org.hibernate.Transaction"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<html:html lang="true">
<head>
<html:base />
<title>导航页</title>
<style type="text/css">
body,th,td {
	background: #F5A89A;
	margin: 0px;
	position: 0px;
	font-size: 12px;
	padding: 0px;
}
</style>
</head>
<body>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td height="24" align="left" valign="middle">
		<%
			Session sessionq = SessionFactory.getSession();
				Transaction tx = sessionq.beginTransaction();
				SysTOperatelogDAO sysTOperatelogDAO = new SysTOperatelogDAO();
				SysTOperatelog sysTOperatelog = new SysTOperatelog();
				sysTOperatelog.setContent(new String(request.getParameter(
						"strnav").getBytes("ISO8859_1"), "GB18030"));
				sysTOperatelog.setIpaddress(request.getRemoteAddr());
				sysTOperatelog.setLogsort("2");
				sysTOperatelog.setOperatetime(new java.util.Date());
				sysTOperatelog.setSysTEmployee((SysTEmployee) session
						.getAttribute("employee"));
				java.net.InetAddress   localhost   =   java.net.InetAddress.getLocalHost();
				sysTOperatelog.setServerip(request.getLocalAddr());
				sysTOperatelogDAO.save(sysTOperatelog);
				tx.commit();
				if (request.getParameter("strnav") != null) {
					out.print("&nbsp;");
					out
							.print("<img src=\"images/navarrow.gif\" width=\"9\" height=\"11\">&nbsp;");
					out.print(new String(request.getParameter("strnav")
							.getBytes("ISO8859_1"), "GB18030"));
				}
				sessionq.close();
		%>
		</td>
		<%
			String handle = "queryhandlewithout()";
				String ifquery = (String) request.getParameter("ifquery");
				if (ifquery.equals("1")) {
					handle = "queryhandle()";
				} else if (ifquery.equals("4")) {
					handle = "queryhandlewithout()";
				} else if (ifquery.equals("0")) {
					handle = "queryhandlewithout1()";
				}
		%>
		<td height="24" align="right" valign="middle">
		<%
			if (!ifquery.equals("2")) {
		%>
		<button style="border: 0px; width: 77; height: 22px"
			onClick="<%=handle%>"><img src="images/queryico.gif"></img>
		</button>
		<%
			}
		%> <%-- &nbsp;&nbsp;<span onclick="<%=handle%>">收起侧边栏</span>--%></td>
	</tr>
</table>
</body>
</html:html>
<script language="javascript" type="text/javascript">     
	<!--
	//查询器按钮
	    var src= parent.frames("query").document.location.href;
		function queryhandle(){
			//if (parent.operating.cols=="*,225"){
				//parent.operating.cols="*,0";
				//window.frames("inch").document.all.con
				//parent.frames("conright").leftcon.innerHTML="<img src=\"images/conrightdown.gif\" width=\"8\" height=\"45\"/>";
			//}
			//else{
				parent.frames("query").document.location.reload(src);
				parent.operating.cols="*,225";
			//}
		}
		//非查询器页面 查询器按钮所用方法
		function queryhandlewithout(){
			if (parent.frames.operating.cols=="*,225"){
				parent.frames.operating.cols="*,0";
				//window.frames("inch").document.all.con
				//parent.frames("conright").leftcon.innerHTML="<img src=\"images/conrightdown.gif\" width=\"8\" height=\"45\"/>";
			}
			else{
				parent.frames.operating.cols="*,225";
				//parent.frames("conright").leftcon.innerHTML="<img src=\"images/conrightup.gif\" width=\"8\" height=\"45\"/>";
			}
		}
		function queryhandlewithout1(){
			if (parent.frames('oper').operatingout.cols=="*,225"){
			parent.frames('oper').operatingout.cols="*,0";
				//window.frames("inch").document.all.con
				//parent.frames("conright").leftcon.innerHTML="<img src=\"images/conrightdown.gif\" width=\"8\" height=\"45\"/>";
			}
			else{
				parent.frames('oper').operatingout.cols="*,225";
				//parent.frames("conright").leftcon.innerHTML="<img src=\"images/conrightup.gif\" width=\"8\" height=\"45\"/>";
			}
		}
		function query(){
		//alert(parent.frames("query").document.getElementById("handlezone").style.display);
		// if(parent.operating.cols="*,225"){
		 	//parent.frames("query").document.getElementById("result").style.display="block";
		 	//parent.frames("query").document.getElementById("handlezone").style.display="none";
		 //}else{
		 	parent.operating.cols="*,225";
		 	
		 	//parent.frames("query").document.getElementById("result").style.display="none";
		 	//parent.frames("query").document.getElementById("handlezone").style.display="block";
		  //var obj=	parent.frames("query").document.getElementById("handlezone");
		 	//alert(obj.document.getElementById("full").outerHTML);
		//}
		}
	-->
</script>