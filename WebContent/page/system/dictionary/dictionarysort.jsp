<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html:html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>无标题文档</title>
  <link href="../../css/CSS.CSS" rel="stylesheet" type="text/css">
  </head>
<SCRIPT LANGUAGE="JavaScript" type="">
<!--
function putUp()
{
    var strTempValue;
    var strTempText;
    var intCurIndex;

    intCurIndex = dictionarySortForm.dictvalue.selectedIndex;

    if (intCurIndex > 0)
    {
        strTempValue= dictionarySortForm.dictvalue.options.item(intCurIndex).value;
        strTempText    = dictionarySortForm.dictvalue.options.item(intCurIndex).text;


        dictionarySortForm.dictvalue.options.item(intCurIndex).value = dictionarySortForm.dictvalue.options.item(intCurIndex - 1).value;
        dictionarySortForm.dictvalue.options.item(intCurIndex).text = dictionarySortForm.dictvalue.options.item(intCurIndex - 1).text;
        dictionarySortForm.dictvalue.options.item(intCurIndex - 1).value= strTempValue;
        dictionarySortForm.dictvalue.options.item(intCurIndex - 1).text = strTempText;
        dictionarySortForm.dictvalue.selectedIndex = intCurIndex - 1;
    }
}

function putDown()
{
    var strTempValue;
    var strTempText;
    var intCurIndex;
    var intIndexCount;

    intCurIndex    = dictionarySortForm.dictvalue.selectedIndex;
    intIndexCount    =dictionarySortForm.dictvalue.length;


    if (intCurIndex < intIndexCount - 1)
    {
        strTempValue= dictionarySortForm.dictvalue.options.item(intCurIndex).value;
        strTempText    = dictionarySortForm.dictvalue.options.item(intCurIndex).text;


        dictionarySortForm.dictvalue.options.item(intCurIndex).value    = dictionarySortForm.dictvalue.options.item(intCurIndex + 1).value;
        dictionarySortForm.dictvalue.options.item(intCurIndex).text        = dictionarySortForm.dictvalue.options.item(intCurIndex + 1).text;
        dictionarySortForm.dictvalue.options.item(intCurIndex + 1).value= strTempValue;
        dictionarySortForm.dictvalue.options.item(intCurIndex + 1).text    = strTempText;
        dictionarySortForm.dictvalue.selectedIndex = intCurIndex + 1;
    }
}

//-->
</SCRIPT>
  <body>
  <html:form action="/system/dictionary/dictionarySort.do" method="post">
    <div align="center">
      <table width=100% border=1 cellpadding="0" cellSpacing=0 borderColorLight=#C0C0C0 borderColorDark=#ffffff bgColor=#ffffff>
        <tr bgcolor="#E1E1E1">
          <td height="24" colspan="2" scope="row">
            <div align="center">项 目 排 序</div>
          </td>
        </tr>
        <tr>
          <th width="89%" scope="row">
            <html:select styleClass="wwwww" multiple="multiple" property="dictvalue" size="10">
              <logic:present name="dict" scope="request">
                <html:options collection="dict" labelProperty="dicName" property="dicID"/>
              </logic:present>
            </html:select>
          </th>
          <th width="11%" scope="row">
            <p>
              <html:button value="向下" property="btnup" onclick="putDown()"/>
            </p>
            <p>
              <html:button value="向上" property="btndown" onclick="putUp()"/>
            </p>
          </th>
        </tr>
        <tr>
          <th height="24" colspan="2" scope="row">
            <div align="center">
              <html:hidden property="dictstring"/>
              <html:hidden property="dsid"/>
              <html:button value="保存" property="Submit3" onclick="msg()"/>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <html:reset value="重置"/>
            </div>
          </th>
        </tr>
      </table>
      <p>&nbsp;      </p>
    </div>
  </html:form>
  </body>
</html:html>
<script type="">
function msg(){
var intIndexCount;
    intIndexCount =dictionarySortForm.dictvalue.length;
    var ss="";
while(intIndexCount--){
ss=ss+dictionarySortForm.dictvalue.options.item(intIndexCount).value+",";
}
dictionarySortForm.dictstring.value=ss;
if (confirm("你确认要保存？")){
  document.dictionarySortForm.submit();
}
}
</script>
