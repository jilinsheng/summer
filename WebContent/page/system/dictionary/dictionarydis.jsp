<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
  <title>无标题文档</title>
  <style type="text/css">
    <!--
      body {
      margin-left: 0px;
      margin-top: 0px;
      margin-right: 0px;
      margin-bottom: 0px;
      }
    -->
  </style>
  <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css" />
</head>
    
<html:html>
  <body>
  <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" bordercolorlight="#639ECE" bordercolordark="#FFFFFF" class="table">
    <tr align="center">
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr align="center">
      <td colspan="4">字典值列表</td>
    </tr>
    <tr>
      <td width="5%" bgcolor="#A3C5F3" align="center">&nbsp;      </td>
      <td width="5%" bgcolor="#A3C5F3" align="center">序号</td>
      <td width="70%" bgcolor="#A3C5F3" align="center">名称</td>
      <td width="20%" bgcolor="#A3C5F3" align="center">当前状态</td>
    </tr>
    <logic:present name="dv">
      <logic:iterate id="dvn" name="dv" scope="request" indexId="idx">
        <tr>
          <td width="5%" align="center">
            <html:img width="16" height="16" border="0" src="/db/images/next.gif" alt=""/>
          </td>
          <td width="8%" align="center">
            <bean:write name="dvn" property="dvorder"/>
          </td>
          <td width="67%" align="center">
            <bean:write name="dvn" property="dicName"/>
          </td>
          <td width="20%" align="center">
            <bean:write name="dvn" property="dicSts"/>
          </td>
        </tr>
      </logic:iterate>
    </logic:present>
  </table>
  </body>
</html:html>
