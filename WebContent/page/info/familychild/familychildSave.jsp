<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String nodeId = request.getParameter("nodeId");
	String nodeName = request.getParameter("nodeName");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>家庭子信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="/db/page/js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript" src="/db/page/js/Validator.js"></script>
		<link rel="stylesheet" href="/db/page/css/style.css" type="text/css"></link>
	</head>
	<body>
		<table width="100%" height="300" border="0" cellpadding="0"
			cellspacing="0" class="table5">
			<tr>
				<td width="120" align="left" valign="top"><jsp:include
						page="/page/info/familychild/FamilyChildInitServlet?nodeId=<%=nodeId%>&nodeName=<%=nodeName%>"
						flush="true"></jsp:include>
				</td>
				<td align="left" valign="top">
					<div id="operarea">
					</div>
				</td>
			</tr>
		</table>
		<div id ="divResult" style="visibility:hidden" onclick="this.style.visibility='hidden';"></div>
	</body>
</html>
<script type="text/javascript">
var m;
var o;
<!--
	function getChild(obj,code ,nodeId,oLevel){
		m=obj;
        var pars="code="+code+"&nodeId="+nodeId+"&level="+oLevel;
		var url = '/db/page/info/familychild/FamilyChildGetChildServlet';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: viewinfo
        	}
        );
	}
	function viewinfo(originalRequest){
		var req =originalRequest;
		var data=req.responseText;
		var oDIV=document.getElementById("c"+m);
		oDIV.innerHTML=data;
	}
-->
</script>
<script type="text/javascript">
<!--
	//资产树形列表
	function display(obj,oCode,num,oLevel){
		var c=document.getElementById("c"+obj);
		var img =document.getElementById("img"+obj);
		if(img.alt=="折叠"){
			img.src="/db/page/images/plus.png";
			c.style.display="none";
			img.alt="展开";
			return false;
		}
		if(img.alt=="展开"){
			img.src="/db/page/images/minus.png";
			c.style.display="block";
			img.alt="折叠";
			getChild(obj,oCode,num,oLevel);
			return false;
		}
	}
-->
</script>
<script type="text/javascript">
var oPopup = window.createPopup();
<!--
	function contextMenu(code,childcode,nodeId){
		var left = event.offsetX+10;
    	var top = event.offsetY+10;
    	var html="<div style=\"cursor:hand; width:60px; height:45px; background:#CCCCCC\">";
    	
    	if(code==null||code==""||code=="null"){
    	}else{
    		html+="<div style=\"cursor:hand;font-size:12px\" onMouseOver=\"this.style.backgroundColor='#FFCC99'\" onmouseout=\"this.style.backgroundColor='#CCCCCC'\" onclick =\"parent.editFamilyChild('"+code+"','"+childcode+"','"+nodeId+"','edit')\">修改</div>";
    	}
    	
    	if(childcode==null||childcode==""||childcode=="null"){
    	}else{
    		html+="<div style=\"cursor:hand;font-size:12px\" onMouseOver=\"this.style.backgroundColor='#FFCC99'\" onmouseout=\"this.style.backgroundColor='#CCCCCC'\" onclick =\"parent.addFamilyChildchild('"+code+"','"+childcode+"','"+nodeId+"','add')\">增加子项目</div>";
    	}
    	oPopup.document.body.innerHTML =html+"</div>"; 
   	 	oPopup.show(left, top, 60, 45, window.document.body);
	}
	//添加子结点-读取表单
	function addFamilyChildchild(code,childcode,nodeId,type){
		var temp="新建子项目";
		alert(temp);
		if(code==""){
			o="";
		}else{
			o="img"+code+nodeId;	
		}
        var pars="code="+code+"&nodeId="+nodeId+"&type="+type+"&childcode="+childcode;
        	alert(pars);
		var url = '/db/page/info/familychild/FamilyChildGetFormServlet';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: viewForm
        	}
        );
	}
	//修改本节点-读取表单
	function editFamilyChild(code,childcode,nodeId,type){
		alert("修改");
		o="";
		var pars="code="+code+"&nodeId="+nodeId+"&type="+type+"&childcode="+childcode;
		alert(pars);
		var url = '/db/page/info/familychild/FamilyChildGetFormServlet';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: viewForm
        	}
        );
	}
	//回显表单
	function viewForm(originalRequest){
		var req =originalRequest;
		var data=req.responseText;
		var oDIV=document.getElementById("operarea");
		alert(data)
		oDIV.innerHTML=data;
	}
	//回显保存后信息
	function viewResult(originalRequest){
		var req =originalRequest;
		var data=req.responseText;
		var oDIV=document.getElementById("divResult");
		alert(data)
		oDIV.innerHTML=data;
		alert(o);
		if(o!=''){
			if($(o).alt=="折叠"){
				$(o).alt="展开";
			}
			$(o).click();
		}else{
			window.location.reload();
		}
	}	
	//保存表单
	function familychildSave(){
		var oForm =document.getElementById("inputForm");
        var pars=Form.serialize(oForm);
		alert(pars);
		var result=checkForm(oForm);
        if(result=="ok"){
         $('divResult').style.visibility ="visible";
         $('divResult').innerHTML ="<img src=\"/db/page/images/loading.gif\" alt=\"进度条\" />正在保存...";
        }else{
         $('divResult').style.visibility ="visible";
         $('divResult').innerHTML ="项目:<"+result+">验证未通过!";
         return false;
        }
		var url = '/db/page/info/familychild/FamilyChildSaveServlet';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: viewResult
        	}
        );
	}
	//左键点击显示信息
	function viewFamilyChild(nodeId,nodeName){
		alert(nodeId+" "+nodeName);
		var url = '/db/page/info/infoinit.do';
        var pars="nodeId="+nodeId+"&nodeName="+nodeName;
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'post',
        parameters: pars,
        onComplete: viewFamilyChildResult
        });
     }
     function viewFamilyChildResult(response){
 		$('operarea').innerHTML = response.responseText;
	}
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