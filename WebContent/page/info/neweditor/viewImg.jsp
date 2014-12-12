<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="com.mingda.common.*"%>
<%
	ImagePreview img = new ImagePreview();
	out.write(img.ProcessRequest(request));
%>

