<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/page/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>����ʡ�������Ϲ�����Ϣϵͳ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="����ʡ,�ͱ�,����ϵͳ">
		<meta http-equiv="description" content="����ʡ�������Ϲ�����Ϣϵͳ">

	</head>
 <%String qmode = request.getParameter("qmode"); %>
 <%String url="/db/page/info/editor/editorInfoCardTrees.do"; %>
<frameset rows="*" cols="187,8,*" framespacing="0" id="fullcol">
			<frame src="/db/page/info/search/infoquery.jsp?qmode=<%=qmode %>&url=<%=url%>" scrolling="no" noresize="noresize"
				title="ϵͳ���˵�" frameborder="yes" border="1" />
			<frame src="/db/page/conleft.jsp" scrolling="no" noresize="noresize"
				title="����˴���ʾ���������˵�" name="leftcon" frameborder="no" border="0" />
			<frame src="/db/page/intro/modifamily.jsp" name="operatingzone" id="operatingzone"
				title="ϵͳ��������" frameborder="yes" border="1" />
		</frameset>
<noframes><body>
</body>
</noframes>
</html>
