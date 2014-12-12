<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>信息查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../../js/Calendar2.js"></script>
		<script type="text/javascript" src="../../js/jquery0.js"></script><script
			type="text/javascript" src="../../js/jBox-1.0.0.0.js"></script>
	<link rel="stylesheet" href="../../css/jBox.css" type="text/css"></link></head>
	<body>
		<br />
		<form action="infologquery.do" method="post">
			选择人员：
			<select name="empid" style="width:140px">
				<option value="">
					未选择...
				</option>
				<bean:write name="opt" scope="request" filter="false" />
			</select>
			&nbsp;开始时间：
			<input name="btime" type="text" onfocus="setday(this)" size="12" />
			&nbsp;结束时间：
			<input name="etime" type="text" onfocus="setday(this)" size="12" />
			<br/>
			查询项目：
			<select name="type" style="width:140px">
				<option value="all" selected>
					全部
				</option>
				<option value="mastername">
					户主姓名
				</option>
				<option value="paperid">
					户主证件号
				</option>
				<option value="famno">
					原家庭编号
				</option>
			</select>
				&nbsp;查&nbsp;询&nbsp;值：
			<input type="text" name="value" value="" size="12"/>&nbsp;
			<input type="submit" value="查询" />
			<input name="organizationId" type="hidden"
				value="<bean:write name="organizationId" scope="request"/>" />
		</form>
		<logic:present name="html" scope="request">
			<table align="center" width="98%" cellspacing="0" cellpadding="0"
				class="table7">
				<tr>
					<th>
						日志时间
					</th>
					<th>
						家庭编号
					</th>
					<th>
						户主姓名
					</th>
					<th>
						修改表名称
					</th>
					<th>
						操作
					</th>
				</tr>
				<bean:write name="html" scope="request" filter="false" />
			</table>
			<bean:write name="toolsmenu" scope="request" filter="false" />
		</logic:present>
	</body>
</html>
<script>
	<!-- 
		function view(id,entityid,code){
			var bWidth = document.body.clientWidth-50;  //背景宽度
			var bHeight= document.body.clientHeight-100; //背景高度
			//var bHeight= 400; //背景高度
			var oUrl='infologview.do?code='+code+'&id='+id+'&entityid='+entityid;
			jBox.open("iframe-jBoxID","iframe",oUrl,"详细信息","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
</script>