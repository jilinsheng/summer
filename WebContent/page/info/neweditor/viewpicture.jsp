<%@page contentType="text/html; charset=GB18030"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030"><style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../../css/CSS.CSS" rel="stylesheet" type="text/css">
</head>
<body>
<table align="center" cellpadding="0" cellspacing="0">
<tr valign="middle">
<td align="center">

<%
  if (session.getAttribute("fname") != null) {
    String ff=(String)session.getAttribute("fname");
    if(!ff.equals("none")){
    String allname = (String) session.getAttribute("fname");
    String ip = request.getServerName();
    int port = request.getServerPort();
    String aaaaa = "http://" + ip + ":" + port + "/" + allname;
%>
<img src="<%=aaaaa%>" alt="" width="75" height="100"/>
<%
  } else {
    out.println("无");
%>
<%}
}%>
</td>
</tr>
</table>
</body>
