<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="GB2312"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<title>�ϴ�ί�����ļ�</title>
<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>
<body>
<%String familyid=request.getParameter("familyId"); %>
	<html:form enctype="multipart/form-data"
		action="/page/info/neweditor/mutilupload.do?qq=111">
		
		<input type="hidden" name="familyId" value="<%=familyid%>"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="24" colspan="6">
					<img src="../../images/ds.gif" width="14" height="14" /> <span
					class="style1"><strong>�ϴ�ί���鼰���֤</strong></span>
				</td>
			</tr>
			<logic:present name="ans" scope="request">
				<logic:iterate id="a" indexId="idx" name="ans" scope="request">
					<%
						Integer i = (Integer) pageContext.getAttribute("idx");
									int k = i.intValue();
									String aaa = "";
									String bbb = "";
									if (k == 0) {
										aaa = "��ͥί����";
										bbb = "ί������������ ���磺���֤��.jpg";
									} else {
										aaa = "��Ա���֤";
										bbb = "���֤�������� ���磺���֤��.jpg";
									}
				%>
					<tr>
						<td height="25" class="talbg"><%=aaa%></td>
						<td height="25" class="talbg">
							<div align="left">
								<bean:write name="a" property="anName" />
							</div>
						</td>
						<td height="25" class="talbg">
							<div align="left">
								<bean:write name="a" property="ffamilyid" />
							</div>
						</td>
						<td height="25" class="talbg">
							<div align="left">
								<bean:write name="a" property="anFilename" />
							</div>
						</td>
						<td><input type="file" id="file_<%=k%>" name="file[<%=k%>]" />
						</td>
						<td><%=bbb%></td>
					</tr>
				</logic:iterate>
			</logic:present>
			<tr>
				<td align="center" height="24" colspan="6"><html:submit value="�ϴ�" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html>