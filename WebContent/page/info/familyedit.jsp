<%@ page language="java" pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>无标题文档</title>
	</head>
	<%
		String nodeId = request.getParameter("nodeId");
		String url="";
		if (nodeId == null || nodeId.equals("") || nodeId.equals("null")) {
		url="/db/page/welcome.jsp";
		} else {
		 url="member/memberSave.jsp?nodeId="+nodeId;
		}
	%>
	<frameset rows="250,*" cols="*" framespacing="0" frameborder="no"
		border="0">
		<frame src="family/familySave.jsp?nodeId=<%=nodeId%>"
			name="familyFrame" id="familyFrame" title="家庭信息操作区域" scrolling="no" />
		<frame src="<%=url%>"
			name="memberFrame" id="memberFrame" title="成员信息操作区域" scrolling="no" />
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>
