<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>moveexternalinquery.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	<style>
	body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: no-repeat;
	background-color: #FCDAD5;
}
	
	</style>
</head>

<body>
	<br />
	<form action="moveexternalinquery.do" method="post">
		<div>
			&nbsp;��ѯ��:
			<select name="type" style="width: 60%">
				<option value="all" selected="selected">
					ȫ��
				</option>
				<option value="mastername">
					��������
				</option>
				<option value="paperid">
					����֤����
				</option>
				<option value="famno">
					ԭ��ͥ���
				</option>
			</select>
		</div>
		<br />
		<div>
			&nbsp;��ѯֵ:
			<input type="text" name="value" value="" style="width: 60%" />
		</div>
		&nbsp;<input type="submit" value="��ѯ"/>
	</form>
	<logic:present name="html" scope="request">
		<table class="table7" width="210" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<th>
					����
				</th>
				<th>
					��������
				</th>
				<th>
					ԭ����
				</th>
			</tr>
			<bean:write name="html" scope="request" filter="false"/>
		</table>
	</logic:present>
</body>
</html:html>
	<script type="text/javascript">
		<!-- 
			function check(mid ,fid){
				//alert(mid +"   "+fid);
				parent.frames['operatingzone'].location.reload('moveexternalininit.do?familyid='+fid+'&mid='+mid);
			}
		-->
	</script>