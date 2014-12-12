<%@ page contentType="text/html; charset=gb18030" language="java"
	import="java.sql.*" errorPage=""%>
<%
	String nodeId = request.getParameter("nodeId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb18030" />
		<title>家庭信息</title>
		<link href="../../css/familylayout.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript" src="../../js/Calendar2.js"></script>
		<script type="text/javascript" src="../../js/Validator.js"></script>
	</head>
	<body>
		<div id="leftup">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<th>
						<img src="../../images/imgfolder.gif"></img>
					</th>
				</tr>
			</table>
		</div>
		<div id="leftdown">
			<jsp:include
				page="/page/info/familyclass/FamilyClassInitServlet?nodeId=<%=nodeId%>"
				flush="true"></jsp:include>
		</div>
		<script type="text/javascript">
<!--
	function saveFamily(inputForm){
		var url = '/db/page/info/family/FamilySaveServlet';
        var oForm =document.getElementsByName(inputForm)[0];
        var pars=Form.serialize(oForm);
        var result=checkForm(oForm);
        if(result=="ok"){
         $('divResult').style.visibility ="visible";
         $('divResult').innerHTML ="<img src=\"/db/page/images/loading.gif\" alt=\"进度条\" />正在保存...";
        }else{
         $('divResult').style.visibility ="visible";
         $('divResult').innerHTML ="项目:<"+result+">验证未通过!";
         return false;
        }
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'post',
        parameters: pars,
        onComplete: viewinfo
        });
        }
 function viewinfo(response){
 	var s= response.responseText.split(",");
 	alert(s[1]+"   "+s[2]);
 	 $('divResult').innerHTML = s[0];
 	 $('divResult').style.visibility ="visible";
 	 if(s[0]=='保存成功'){
 	 		if(s[2]==0){
 	 			window.parent.frames["familyFrame"].location="/db/page/info/family/familySave.jsp?nodeId="+s[1];
 	 		}
 	 		window.parent.frames["memberFrame"].location="/db/page/info/member/memberSave.jsp?nodeId="+s[1];
 	 }else{
 		alert("保存失败");
 	 }
	}
//-->
</script>
		<jsp:include
			page="/page/info/family/FamiyInitServlet?nodeId=<%=nodeId%>"
			flush="true"></jsp:include>
		<div id="right">
			<iframe id="family" src="" height="248" scrolling="auto"
				frameborder="0">
			</iframe>
		</div>
		<div id="divResult" style="visibility: hidden"
			onclick="this.style.visibility='hidden';"></div>
		<div id="bottomline">
			&nbsp;
		</div>
	</body>
</html>
<script type="text/javascript">
<!--
function sc(){
var o=document.getElementById("family");o.width=eval(document.body.offsetWidth-458);}
setInterval(sc,50);
-->
</script>
<script type="text/javascript">
<!--
	function checkForm(oForm){
		var els= oForm.elements;
		var j=els.length-1;
		for(var i=0;i<j;i++){
			var iObj=els[i];
			var oAtt = iObj.attributes;
			var rule= oAtt.getNamedItem("rule").value;
		if(rule==9){
		}else{
			var title= oAtt.getNamedItem("title").value;
		if(rule==5){
			if (!CheckDate(title,iObj,true)) return title;
		}else if(rule==6){
			if (!CheckChinese(title,iObj,true,30)) return title;
		}else if(rule==7){
			if(!CheckNumber(title,iObj,true)) return title;
		}else if(rule==8){
			if (!CheckIdCard(title,iObj)) return title;
		}
		else{
			}
		}
	}
	return 'ok';
	}
-->
</script>
<%
	String url = request.getParameter("url");
	if (url == null) {
		url = "javascript:#";
	} else if (url.indexOf("?") >= 0) {
		url = url + "&";
	} else {
		url = url + "?";
	}
%>
<script type="text/javascript">
<!--
	//初始化家庭子信息
	function initFamilyChild(nodeId,nodeName){
		var oIframe=$('family');
		oIframe.src="/db/page/info/familychild/familychildSave.jsp?nodeId="+nodeId+"&nodeName="+nodeName;
	}
	function initFamilyClass(nodeId,nodeName){
		var url = '/db/page/info/familyclass/FamilyClassInitServlet';
        var pars="nodeId="+nodeId+"&nodeName="+nodeName;
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'post',
        parameters: pars,
        onComplete: viewFamilyClass
        });
	}
 	function viewFamilyClass(response){
 		$('leftdown').innerHTML = response.responseText;
	}
	function ClassCommit(obj,nodeId,propertyName,nodeName){
		var obj1 =obj;
		var url = '/db/page/info/familyclass/ClassCommit';
        var pars="nodeId="+nodeId+"&nodeName="+nodeName+"&propertyName="+propertyName;
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'post',
        parameters: pars,
        onComplete: function (){ alert("保存成功");}
        });
        obj.src="/db/page/images/right.gif";
        obj.alt="是";
        }
-->
</script>