<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
<title>ɾ����ͥ��Ա</title>
</head>
<body>
<form name="removemember" action="removemember.do" method="post" >
<input type="hidden" name="familyId" value="${familyId}"/>
<input type="hidden" name="memberId" value="${memberId}"/>
<p align="center">
<br/>
ɾ����ͥ��Ա<br/><br/>
ɾ��ԭ��<input type="text" name="delreason"/>
<br/>
<br/>
<input type="submit" value="����"/>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="�ر�" onclick="window.close()"/>
</p>
</form>
</body>
</html>