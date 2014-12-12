<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>家庭信息维护</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript" src="../../js/Calendar2.js"></script>
	<script type="text/javascript" src="../../js/Validator.js"></script>
	<link rel="stylesheet" href="../../css/tree.css" type="text/css"></link>
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>

<body onload="initpage()" style="overflow-x:hidden;overflow-y:scroll">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td width="225" height="550">
				<div class="TreeMenu" id="CategoryTree"
					style="height: 100%; border: solid 1px; border-color: #cccccc; background:#ececec">
					<bean:write name="familytree" filter="false" />
				</div>
			</td>
		</tr>
	</table>
</body>
</html:html>
<script language="javascript" type="text/javascript">
<!-- 
	function ChangeStatus(node,code,objtype,objid){
		if (node.parentNode){
			if(code=='FAMILYINCOME'||code=='FAMILYCLASS'||code=='MEMBERCLASS'||code=='PAYOUT'||code=='MEMBERINCOME'){
			node.parentNode.className = "Opened";
			}else{
			if (node.parentNode.className == "Opened"){
				node.parentNode.className = "Closed";
			}else{
				node.parentNode.className = "Opened";
				evolution(node.parentNode,code,objtype,objid);
			}
			}
		}
	}
	//展开属性节点
	function evolution(node,code,objtype,objid){
		curNode=node;
		var url = '/db/page/system/syslog/syslogtableTree.do';
        var pars="code="+code+"&objtype="+objtype+"&objid="+objid;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: viewChild
				        }
				        );
	}
	function viewChild(response){
		var nodes=response.responseXML.selectSingleNode("root").childNodes;
		var outtext="";
		for(var i=0;i<nodes.length;i++){
		 	outtext+=nodes.item(i).xml; 
		}
		curNode.innerHTML=outtext;
	}
	function initpage(){
		document.getElementsByTagName('img')[0].click();
	}
	function viewNode(obj ,code,codeId,id){
	 parent.frames['mainFrame'].location.reload('syslogview.do?nodeId='+codeId+'&nodeName='+code);
	}
	function getCurrentList(obj ,code,codeId,id){
	if(code=='FAMILYINCOME'||code=='FAMILYCLASS'||code=='MEMBERCLASS'||code=='PAYOUT'||code=='MEMBERINCOME'){
	 parent.frames['mainFrame'].location.reload('syslogview.do?nodeId='+codeId+'&nodeName='+code);
			}
	}
-->
</script>