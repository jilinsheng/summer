<%@page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>无标题文档</title>
	</head>
	<frameset rows="30,*" cols="*" frameborder="no" border="0"
		framespacing="0">
	<frame src="dicttop.jsp" name="topFrame" scrolling="no"
		noresize="noresize" id="topFrame" title="topFrame" />
	<frameset rows="*" cols="242,*" framespacing="0" frameborder="no"
		border="0">
	<frame src="/db/system/dictionary/queryDicList.do" name="leftFrame"
		scrolling="Yes" noresize="noresize" id="leftFrame" title="leftFrame" />
	<frameset rows="25,*" cols="*" framespacing="0" frameborder="no"
		border="0">
	<frame src="dicmenu.jsp" name="menuFrame" id="menuFrame"
		title="mainFrame" />
	<frame src="" name="mainFrame" id="mainFrame" title="mainFrame" />
	</frameset>
	</frameset>
	</frameset>
</html>
