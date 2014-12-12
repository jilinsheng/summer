<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>dictsort.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<script type="text/javascript">
		function viewdict(dictid){
			var url ="dictinit.do?dictid="+dictid;
			window.parent.frames["mainFrame"].location.reload(url);
		}
	</script>
</head>
<body>
	<div>
		<button class="btn" onclick="viewdict('')">
			新建类别
		</button>
	</div>
	<logic:present name="table" scope="request">
		<bean:write name="table" scope="request" filter="false" />
	</logic:present>
	<div>
		<button class="btn" onclick="viewdict('')">
			新建类别
		</button>
	</div>
</body>
</html:html>
