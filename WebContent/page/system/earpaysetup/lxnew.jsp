<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=GBK"%>
<html:html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
    <table width="650" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" bordercolorlight="#639ECE" bordercolordark="#FFFFFF" class="table">
      <tr align="center">
        <td colspan="10" bgcolor="#A3C5F3">��ͥ����Ա����֧����Ŀ</td>
      </tr>
      <tr>
        <td>��Ŀ���ƣ�</td>
        <td colspan="3">
          <html:text property="xmmc" name="cc"/>
        </td>
        <td>�Ƿ�������</td>
        <td>
          <html:checkbox property="ifc" name="cc"/>
        </td>
        <td>�Ƿ�����</td>
        <td>
          <html:checkbox property="ifs" name="cc"/>
        </td>
        <td>����</td>
        <td>
          <html:select property="select" name="cc">
            <html:option value="��">��</html:option>
            <html:option value="һ����">һ����</html:option>
          </html:select>
        </td>
      </tr>
      <tr>
        <td colspan="10">
          <div align="center">
            <html:hidden property="etid" name="cc"/>
            <html:submit value="����" property="B1"/>
          </div>
        </td>
      </tr>
    </table>
  </html:form>
  </body>
</html:html>
