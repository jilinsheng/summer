<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��Ϣ��ѯ</title>
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
			ѡ����Ա��
			<select name="empid" style="width:140px">
				<option value="">
					δѡ��...
				</option>
				<bean:write name="opt" scope="request" filter="false" />
			</select>
			&nbsp;��ʼʱ�䣺
			<input name="btime" type="text" onfocus="setday(this)" size="12" />
			&nbsp;����ʱ�䣺
			<input name="etime" type="text" onfocus="setday(this)" size="12" />
			<br/>
			��ѯ��Ŀ��
			<select name="type" style="width:140px">
				<option value="all" selected>
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
				&nbsp;��&nbsp;ѯ&nbsp;ֵ��
			<input type="text" name="value" value="" size="12"/>&nbsp;
			<input type="submit" value="��ѯ" />
			<input name="organizationId" type="hidden"
				value="<bean:write name="organizationId" scope="request"/>" />
		</form>
		<logic:present name="html" scope="request">
			<table align="center" width="98%" cellspacing="0" cellpadding="0"
				class="table7">
				<tr>
					<th>
						��־ʱ��
					</th>
					<th>
						��ͥ���
					</th>
					<th>
						��������
					</th>
					<th>
						�޸ı�����
					</th>
					<th>
						����
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
			var bWidth = document.body.clientWidth-50;  //�������
			var bHeight= document.body.clientHeight-100; //�����߶�
			//var bHeight= 400; //�����߶�
			var oUrl='infologview.do?code='+code+'&id='+id+'&entityid='+entityid;
			jBox.open("iframe-jBoxID","iframe",oUrl,"��ϸ��Ϣ","width="+bWidth+",height="+bHeight+",center=true,minimizable=true,resize=true,draggable=false,model=true");
		}
</script>