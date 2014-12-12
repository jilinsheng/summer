<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>

		<title>检索出家庭列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<link href="../../css/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<table width="100%" border="1" cellspacing="0" cellpadding="0"
			class="table1">
			<tr>
				<th>
					家庭编号
				</th>
				<th>
					姓名
				</th>
				<th>
					身份证号
				</th>
			</tr>
			<logic:present name="list" scope="request">
				<logic:iterate id="rs" name="list" scope="request">
					<tr>
						<td>
							<a
								href="../../info/card/newfamilyinfocard.do?code=FAMILY&entityId=<bean:write name="rs" property="familyId"/>" target="_self">
								<bean:write name="rs" property="picpath" />
							</a>
						</td>
						<td>
							<a
								href="../../info/card/newfamilyinfocard.do?code=FAMILY&entityId=<bean:write name="rs" property="familyId"/>" target="_self">
								<bean:write name="rs" property="membername" /> </a>
						</td>
						<td>
							<bean:write name="rs" property="otherreason" />
						</td>
					</tr>
				</logic:iterate>
			</logic:present>
		</table>
	</body>
</html>
<script language="javascript" type="text/javascript">
<!--
	function selval(obj , id){
		if(obj.checked==true){
			var objs =document.getElementsByName('checkbox');
			for(var i=0;i<objs.length;i++){
				objs[i].checked=false;
			}
			obj.checked=true;
			showModelessDialog("../../info/card/newfamilyinfocard.do?code=FAMILY&entityId=" + id,window,"status:false;dialogWidth:800px;dialogHeight:600px");
		}
	}
-->
</script>