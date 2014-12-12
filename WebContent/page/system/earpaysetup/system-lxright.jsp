<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=GBK" %>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.table {
	font-size: 9pt;
	line-height: 22px;
}
-->
</style>
<link href="../../css/CSS.CSS" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<html:form action="/system/earpaysetup/createEarningAction.do" method="POST">
  <table width="650"  border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" bordercolorlight="#639ECE" bordercolordark="#FFFFFF" class="table">
    <tr align="center">
      <td colspan="10" bgcolor="#A3C5F3">家庭或人员收入支出项目</td>
    </tr>
    <tr>
      <td>项目名称：</td>
      <td colspan="3"><html:text property="xmmc"/></td>
      <td>是否参与计算</td>
      <td><html:checkbox property="ifc"/></td>
      <td>是否在用</td>
      <td><html:checkbox property="ifs"/></td>
      <td>周期</td>
      <td><html:select property="select">
         <html:option value="月">月</html:option>
       <html:option value="一次性">一次性</html:option>

      </html:select>
      </td>
    </tr>
    <tr>
      <td colspan="10"><div align="center">
         <%
          String type=request.getParameter("type");
          String sts=request.getParameter("sts");
          %>
          <html:hidden property="type" value="<%=type%>"/>
          <html:hidden property="sts" value="<%=sts%>"/>
        <html:submit value="增加" property="B1"/>
      </div></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
