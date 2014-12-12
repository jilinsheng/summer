<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<title>系统操作区</title>
</head>
<frameset rows="24,*" cols="*" frameborder="no" border="0"
	framespacing="0">
	<%
		String strnav = request.getParameter("strnav");
			if ("".equals(strnav) || null == strnav) {
				strnav = "";
			} else {
				strnav = new String(strnav.getBytes("ISO8859_1"), "GB18030");
			}
			//strnav = java.net.URLEncoder.encode(strnav);
			//String strnav = new String(request.getParameter("strnav").getBytes(
			//"ISO8859_1"), "GB18030");
			String url = request.getParameter("handle");//查询后操作url
			String url1 = request.getParameter("url");//框架初始页面url1
			String checkbox = request.getParameter("checkbox");//复选框
			String qmode = request.getParameter("qmode");//查询方式
			String ifquery = request.getParameter("ifquery");//是否使用查询器 0:加载框架页没有查询器 1：加载查询器框架页 2：加载无框架页面 (空)：业务查询框架页面     
			String qpolicy = request.getParameter("qpolicy");//查询器是否是业务查询
			String policyId =request.getParameter("qpolicy");
			String resi = request.getParameter("resi");
			if (null == resi || "".equals(resi)) {
				resi = "";
			}
			String frame = "";
			if (qpolicy == null || qpolicy.equals("")) {
				qpolicy = "";
			} else {
				qpolicy = "&qpolicy=" + qpolicy;
			}
			if (ifquery == null || ifquery.equals("")) {
				frame = "*,225";
				ifquery = "4";
			} else if (ifquery.equals("0")) {
				frame = "*,0";
			} else if (ifquery.equals("2")) {
				frame = "*,0";
			} else if (ifquery.equals("1")) {
				frame = "*,225";
			}
			if (checkbox == null || checkbox == "" || !checkbox.equals("1")) {
				checkbox = "0";
			}
	%>
	<frame
		src="navigation.jsp?strnav=<%=strnav%>&ifquery=<%=ifquery%>&qmode=<%=qmode%>"
		scrolling="no" noresize="noresize" name="content" frameborder="0" />
	<frameset cols="<%=frame%>" frameborder="no" border="0"
		framespacing="0" id="operating">
		<frame id="oper" src="<%=url1%>?policyId=<%=policyId %>" scrolling="yes" noresize="noresize"
			name="oper" frameborder="0" />
		<%-- <frame id="conright" name="conright" src="conright.jsp" scrolling="no" noresize="noresize" frameborder="0"/>--%>
		<frame id="query" name="query"
			style="border-width: 0px; border-left-style: outset; border-left-width: 3px; border-left-color: #EE7C6B;"
			src="info/search/infoquery.jsp?qmode=<%=qmode%><%=qpolicy%>&url=<%=url%>&checkbox=<%=checkbox%>&resi=<%=resi%>"
			scrolling="auto" noresize="noresize" frameborder="0" />
	</frameset>
</frameset>
<noframes></noframes>
<body>
</body>
</html:html>
<%
	if (null != session.getAttribute("sql")) {
		session.removeAttribute("sql");
	}
	if (null != session.getAttribute("orgsql")) {
		session.removeAttribute("orgsql");
	}
%>