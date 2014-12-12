<%@ page language="java" pageEncoding="GB18030"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Element"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.mingda.common.node.TreeHandle"%>
<%@page import="com.mingda.common.node.TreeHandleImpl"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
<html:base />
<title>删除家庭</title>
<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
<style type="text/css">
<!--
#list {
	padding: 4px;
	border: 3px double #f5a89a;
}

#emp {
	padding: 4px;
	border: 3px double #f5a89a;
}

.legend {
	font-size: 12px;
	font-weight: lighter;
	color: #000000;
}

lable {
	width: 90px;
	text-align: center;
}

.field {
	font-size: 9pt;
	font-weight: 300;
	color: #000066;
}

.value {
	font-size: 9pt;
	font-weight: 300;
	color: #000000;
}
-->
</style>
</head>
<body>

<fieldset id="list">
<form action="" id="inputForm"><legend class="legend">
删除家庭 </legend>
<div id="delfam">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2">
		<%
			String nodeName = request.getParameter("nodeName");
				String nodeId = request.getParameter("nodeId");
				Document tree = (Document) pageContext.getServletContext()
						.getAttribute("tree");
				TreeHandle th = new TreeHandleImpl(tree);
				Element family = th.selectSingleEntity(nodeName,
						new Long(nodeId)).getRootElement();
		%> <input type="hidden" name="familyId"
			value="<%=family.selectSingleNode(
								"//property[@column='FAMILY_ID']").getText()%>" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th height="25" class="field"><%=((Element) family
										.selectSingleNode("//property[@column='FAMILYNO']"))
										.attributeValue("title")%></th>
				<td class="field"><%=family.selectSingleNode(
								"//property[@column='FAMILYNO']").getText()%></td>
				<th class="field"><%=((Element) family
										.selectSingleNode("//property[@column='MASTERNAME']"))
										.attributeValue("title")%></th>
				<td class="field"><%=((Element) family
										.selectSingleNode("//property[@column='MASTERNAME']"))
										.getText()%></td>
				<th class="field"><%=((Element) family
										.selectSingleNode("//property[@column='MASTERPAPERID']"))
										.attributeValue("title")%></th>
				<td class="field"><%=((Element) family
										.selectSingleNode("//property[@column='MASTERPAPERID']"))
										.getText()%></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<th height="25" class="field">删除原因</th>
		<td height="25" class="field">
		<%
			Document doc = (Document) pageContext.getServletContext()
						.getAttribute("dictionary");
				Element dicsort = (Element) doc.getRootElement()
						.selectSingleNode("//DICTSORT[@id='310']");
				Iterator it = dicsort.elementIterator();
				while (it.hasNext()) {
					Element dicval = (Element) it.next();
					String id = dicval.attributeValue("id");
					String val = dicval.getText();
		%> <label full=":reason"> <input type="checkbox"
			name="reason" value="<%=id%>"><%=val%></input> </label> <%
 	}
 %>
		</td>
	</tr>
	<tr>
		<th height="25" class="field">删除说明</th>
		<td height="25" class="field"><textarea name="detail" id="detail"
			cols="45" rows="5"></textarea></td>
	</tr>
	<tr>
		<th height="25" colspan="2"><input type="button" value="删除家庭"
			onclick="remove()" /></th>
	</tr>
</table>
</div>
</form>
</fieldset>
<div id="waiting"></div>
</body>
</html:html>
<script type="text/javascript">
	<!--
	function checkform(){
	var flag=true; 
		for(var i=0;i<document.getElementsByName("reason").length;i++){
			if(document.getElementsByName("reason")[i].checked==true){
				flag=false;
				break;
			}
		}
		//alert(flag);
		if(flag){
			alert("删除家庭理由必须选择");
  			return false;
  		}else{
			if(confirm("你确认要删除此户家庭吗?")){ 
				return true; 
			}else{
				return false;
			}
 		}
	}		
		function remove(){
			if(!checkform()) return false;
			var url = '/db/page/info/remove/removefamily.do';
			$('waiting').innerHTML="正在删除数据......";
			$('waiting').style.display="block";
			var oForm =$("inputForm");
        	var pars=Form.serialize(oForm);
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: showresult
					        }
					       );
					  
		}
		function showresult(response){
			//alert(response.responseText);
			$('delfam').innerHTML=response.responseText; 
			$('waiting').style.display="none";
			if('删除成功'==response.responseText){
				parent.frames['query'].location.reload(parent.frames['query'].location.href);
			}
		}  
	-->
</script>