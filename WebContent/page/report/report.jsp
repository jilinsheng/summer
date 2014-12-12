<link href="../css/style.css" rel="stylesheet" type="text/css">
<link href="../css/CSS.CSS" rel="stylesheet" type="text/css">
<%String sss = (String) request.getAttribute("urlll");  
java.net.InetAddress localhost = java.net.InetAddress
			.getLocalHost();
	String basePath = request.getScheme() + "://"
			+ localhost.getHostAddress() + ":"
			+ "7001";
%>
<%System.out.print(sss);%>
<%response.sendRedirect(basePath+sss);%>
