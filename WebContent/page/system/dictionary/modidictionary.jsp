<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
  <title>�ޱ����ĵ�</title>
  <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css" />
</head>
    
<html:html>
  <body>
  <html:form action="/system/dictionary/modiDictionary.do" method="post" target="_self">
    <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" bordercolorlight="#639ECE" bordercolordark="#FFFFFF" class="table">
      <tr align="center">
        <td colspan="3">�ֵ�ֵ�޸�</td>
      </tr>
      <tr>
        <td width="5%" bgcolor="#A3C5F3" align="center">&nbsp;        </td>
        <td width="5%" bgcolor="#A3C5F3" align="center">���</td>
        <td width="90%" bgcolor="#A3C5F3">��ǰ״̬/����</td>
      </tr>
      <logic:present name="dv">
        <logic:iterate id="dvn" name="dv" indexId="idx">
          <tr>
            <td width="5%" align="center">
              <html:img width="16" height="16" border="0" src="/db/images/next.gif" alt=""/>
            </td>
            <td width="8%" align="center">
              <bean:write name="dvn" property="dvorder"/>
            </td>
            <td width="87%" align="left">
              <html:multibox property="selectdv">
                <bean:write name="dvn" property="dicID"/>
              </html:multibox>
              <bean:write name="dvn" property="dicName"/>
            </td>
          </tr>
        </logic:iterate>
      </logic:present>
      <tr>
        <td colspan="3">
          <div align="center">
            <html:hidden property="dsid"/>
            <html:submit value="����" property="Submit"/>
          </div>
        </td>
      </tr>
    </table>
  </html:form>
  </body>
</html:html>
