<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html:html>
  <head>
  <%String dsid = request.getParameter("dsid");  %>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>�ޱ����ĵ�</title>
  <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css">
  </head>
  <body>
  <html:form action="/system/dictionary/addDictionary.do" method="post">
    <table width=100% border=1 cellpadding="0" cellSpacing=0 borderColorLight=#C0C0C0 borderColorDark=#ffffff bgColor=#ffffff>
      <tr bgcolor="#A3C5F3">
        <td height="24" colspan="3" bgcolor="#dbeaf5" scope="row">
          <div align="center">����ֵ������Ŀ</div>
        </td>
      </tr>
      <tr>
        <td width="166" height="24" scope="row" align="center">��Ŀ����</td>
        <td width="428" colspan="2" align="center">
          <html:text styleClass="xxx" property="dictvalue"/>
        </td>
      </tr>
      <tr align="center">
        <td height="24" colspan="3" scope="row">
          <html:hidden property="dsid" value="<%=dsid%>"/>
          <html:submit value="���" property="Submit"/>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <html:reset value="����"/>
        </td>
      </tr>
    </table>
  </html:form>
  </body>
</html:html>
