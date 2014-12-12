<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb18030" />
		<title>家庭信息卡片</title>
		<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	color: #000000;
	background-color: #f7f7f7;
}

td {
	color: #333333;
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	font-weight: normal;
	text-align: left;
}

th {
	color: #999999;
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
	font-weight: normal;
	text-align: left;
	height: 20px;
	width: 80px;
	padding-left: 4px;
}

table caption {
	width: auto;
	text-align: center;
	background-color: #A8CCEA;
	padding: 0px;
	margin: 0px;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.familymenu {
	border: solid 0px;
	border-color: #86CFEE;
	height: 18px;
	padding: 0;
	margin: 0
}

.tablelist {
	border-collapse: collapse;
	border: 1px solid #999;
	border-width: 1px 0 0 1px;
	margin: 2px 0 2px 0;
	text-align: center;
}

.tablelist td {
	height: 23px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	font-size: 12px;
	background-color: #FFF;
	text-align: center;
}

.tablelist th {
	height: 23px;
	border: 1px solid #999;
	border-width: 0 1px 1px 0;
	margin: 2px 0 2px 0;
	text-align: center;
	font-size: 12px;
	text-align: center;
	background-color: #C9DDFE;
	color:#000000
}

.familymenu li {
	float: left;
	width: 90px;
	text-align: center;
	cursor: hand;
	height: 18px;
	background-color: #A8CCEA
}

.familymenu ul {
	list-style: none;
	padding: 0px;
	margin: 0px;
	height: 18px;
	width: 100%;
	background-color: #A8CCEA;
}

.listdivc {
	height: 18px;
	font-size: 12px;
	padding: 0px;
	margin: 0px;
}

.listdiv {
	height: 18px;
	font-size: 12px;
	padding: 0;
	margin: 0;
	display: block
}

.btn {
 width:60px;BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
}

#memberother ul {
	margin: 0 0 0 5px;
}

#memberother img.s {
	width: 34px;
	height: 18px;
}

#memberother .Opened img.s {
	background: url(../../images/tree/opened.gif) no-repeat 0 1px;
}

#memberother .Closed img.s {
	background: url(../../images/tree/closed.gif) no-repeat 0 1px;
}

#memberother .Child img.s {
	background: url(../../images/tree/child.gif) no-repeat 13px 2px;
}

#memberother {
	float: left;
	width: 100%;
	border: 1px solid #99BEEF;
	color: inherit;
	margin: 1px;
	padding: 1px;
}
-->
</style>
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
		<link rel="stylesheet" href="../../css/tree.css" type="text/css"></link>
	</head>
	<bean:define id="familyId" name="entityId" scope="request"></bean:define>
	<bean:define id="masterId" name="masterId" scope="request"></bean:define>
	<body onload="initpage(<%=masterId%>)" style="overflow-x:hidden;">
		<div
			style="width: 890; background-color: #3399CC; height: 24px; text-align: center; font-size: 12px; color: #FFFFFF">
						<a href="../../info/editor/editorInfoCardTrees.do?nodeName=FAMILY&nodeId=<%=familyId %>">
						<span style="color:white"><bean:write name="mastername" scope="request" />
						&nbsp;&nbsp;家庭信息</span>
						</a>
		</div>
		<div style="border: solid 1px; border-color: #86CFEE">
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td width="450" height="200" align="left" valign="top">
						<div
							style="height: 200px; border: solid 1px; border-color: #86CFEE; overflow-y: scroll">
							<table width="430" border="0" cellspacing="0" cellpadding="0">
								<caption>
									家庭基本信息
								</caption>
								<tr>
									<td>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<bean:write name="familybase" scope="request" filter="false" />
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td width="150" valign="top">
						<div id="familyclass"
							style="height: 200px; border: solid 1px; border-color: #86CFEE; overflow-y: scroll">
							<table width="130" border="0" cellspacing="0" cellpadding="0">
								<caption>
									<span style="cursor: hand"
										onclick="refreshclass('FAMILYCLASS',<%=familyId%>)">家庭分类</span>
								</caption>
								<bean:write name="familyclass" scope="request" filter="false" />
							</table>
						</div>
					</td>
					<td id="ftd" width="270">
						<div
							style="height: 98px; border: solid 1px; border-color: #86CFEE;overflow-y:scroll">
							<table width="90%" border="0" cellspacing="2" cellpadding="0">
								<caption>
									受理中业务
								</caption>
								<bean:write name="handle" filter="false"/>
							</table>
						</div>
						<div style="height: 2px"></div>
						<div
							style="height: 98px; border: solid 1px; border-color: #86CFEE;overflow-y:scroll">
							<table width="90%" border="0" cellspacing="2" cellpadding="0">
								<caption>
									正享受救助业务
								</caption>
								<bean:write name="oper" filter="false"/>
							</table>
						</div>
					</td>
				</tr>
			
				<tr>
					<td colspan="3">
						<div
							style="height: 298px; border: solid 1px; border-color: #86CFEE;">
							<table width="100%" height="296" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="100">
										<div id="memberpic"
											style="height: 120px; border: solid 0px; border-color: #86CFEE; text-align: center"></div>
										<div
											style="height: 174px; border: solid 0px; border-color: #86CFEE;">
											<table width="100%" border="0" cellspacing="1"
												cellpadding="0">
												<caption>
													成员列表
												</caption>
												<bean:write name="member" scope="request" filter="false" />
											</table>
										</div>
									</td>
									<td id="memberzone">
										<div id="operzone"
											style="height: 296px; border: solid 0px; border-color: #86CFEE;">
											<table height="296px" width="100%" border="0" cellspacing="0"
												cellpadding="1">
												<tr>
													<td width="150" valign="top" align="center">
														<div id="memberclass"
															style="height: 296px; border: solid 1px; border-color: #86CFEE; overflow-y: scroll"></div>
													</td>
													<td width="200" valign="top">
														<div id="membermain"
															style="height: 246px; border: solid 1px; border-color: #86CFEE; overflow-y: scroll">
														</div>
														<div id="memberbnt"
															style="height: 50px; border: solid 0px; border-color: #86CFEE; display: none">
															<table width="100%" border="0" cellpadding="0"
																cellspacing="1">
																<tr>
																	<td>
																		<input  class="btn" type="button" value="简历"
																			onclick="initMemberChildNodes(<%=masterId%>,'RESUME');" />
																		<input class="btn" type="button" value="赡抚养"
																			onclick="initMemberChildNodes(<%=masterId%>,'SUPPORTMAN')" />
																		<input class="btn" type="button" value="健康"
																			onclick="initMemberChildNodes(<%=masterId%>,'HEALTH')" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="btn" type="button" value="残疾"
																			onclick="initMemberChildNodes(<%=masterId%>,'DISABILITY')" />
																		<input class="btn" type="button" value="收入"
																			onclick="initMemberChildNodes(<%=masterId%>,'MEMBERINCOME')" />
																		<input class="btn" type="button" value="技能"
																			onclick="initMemberChildNodes(<%=masterId%>,'SKILL')" />
																	</td>
																</tr>
															</table>
														</div>
													</td>
													<td valign="top" align="left" id="memberchild">
														<%-- <div id="memberother" class="TreeMenu"
															style="height: 100px; border: solid 1px; border-color: #86CFEE; overflow: scroll"></div>--%>
														<div id="memberotherinfo"></div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
					<tr>
					<td colspan="3">
						<div class="familymenu">
							<ul id="menu">
								<li onclick="setTab(1,this,'Oper',<%=familyId%>)">
									救助记录
								</li>
								<li onclick="setTab(2,this,'ASSET',<%=familyId%>)">
									资产
								</li>
								<li onclick="setTab(3,this,'HOUSING',<%=familyId%>)">
									居住
								</li>
								<li onclick="setTab(4,this,'FAMILYINCOME',<%=familyId%>)">
									收入
								</li>
								<li onclick="setTab(5,this,'PAYOUT',<%=familyId%>)">
									支出
								</li>
								<li onclick="setTab(6,this,'PAPERS',<%=familyId%>)">
									原始证件
								</li>
								<li onclick="setTab(7,this,'ANNEX',<%=familyId%>)">
									多媒体资料
								</li>
							</ul>
						</div>
						<div id="familyzone"
							style="height: 200px; border: solid 1px; border-color: #86CFEE; display: none">
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
								<tr>
									<!--<td width="240px">
										<div class="TreeMenu" id="CategoryTree"
											style="height: 190px; border: solid 1px; border-color: #86CFEE; overflow-y: scroll">
										</div>
									</td>-->
									<td>
										<div id="viewfamilychild"
											style="height: 191px; border: solid 1px; border-color: #86CFEE; overflow-y: scroll; padding: 3px">
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<p align="center">
			<input value="关闭" type="button" onclick="window.close()" />
			&nbsp;&nbsp;
			<span id="result" style="display: none"><img
					src="../../images/loadingtiao.gif"></img>
			</span>
		</p>
	</body>
</html>
<script language="javascript" type="text/javascript">
	var curNode;
	var nodeId;
	var type;
	//展开 收缩
	function ChangeStatus(node,code,objtype,objid){
		if (node.parentNode){
			curNode=node;
			if(code=='FAMILYINCOME'||code=='FAMILYCLASS'||code=='PAYOUT'||code=='MEMBERINCOME'){
				node.parentNode.className = "Closed";
			}else{
			if (node.parentNode.className == "Opened"){
				node.parentNode.className = "Closed";
			}else{
				node.parentNode.className = "Opened";
			}
			if (node.parentNode.childNodes.length<=2){
				evolution(node.parentNode,code,objtype,objid);
			}
			}
		}
	}
	function ChangeMemberStatus(node,code,objtype,objid){
		if (node.parentNode){
			curNode=node;
		if(code=='FAMILYINCOME'||code=='FAMILYCLASS'||code=='PAYOUT'||code=='MEMBERINCOME'){
			}else{
			if (node.parentNode.className == "Opened"){
				node.parentNode.className = "Closed";
			}else{
				node.parentNode.className = "Opened";
			}
			if (node.parentNode.childNodes.length<=2){
				evolution(node.parentNode,code,objtype,objid);
			}
			}
		}
	}
	//ul 菜单 切换
	function setTab(m,obj,nodeName,parentId){
 		var tli=document.getElementById("menu").getElementsByTagName("li");
 		for(i=0;i<tli.length;i++){
 		if(i+1==m){
 			if(obj.style.backgroundColor=="#0066cc"){
				obj.style.backgroundColor="#A8CCEA";
				obj.style.color ="#000000";
				familyzone.style.display="none";
			}else{
			if(nodeName=='FAMILYINCOME'||nodeName=='FAMILYCLASS'||nodeName=='PAYOUT'||nodeName=='MEMBERINCOME'){
				getCurrentList(nodeName,parentId,parentId);
			}else if(nodeName=='Oper'){
			 	getOper(parentId);
			}else{
				//getFamilyChildList(nodeName,parentId);
				getCurrentList(nodeName,parentId,parentId);
			}
				obj.style.backgroundColor="#0066cc";
				obj.style.color ="#ffffff";
				familyzone.style.display="block";
			}
 		}else{
			tli[i].style.backgroundColor="#A8CCEA";
			tli[i].style.color ="#000000";
			}
 		}
	}
	//显示救助记录
	function getOper(nodeId){
		var pars="nodeId="+nodeId;
			var url = '/db/page/info/card/getOper.do';
	        var myAjax = new Ajax.Request(
	        url,
	        	{
	        		method: 'post',
	        		parameters: pars,
	        		onComplete:showNodeaa
	        	}
	        );
	}
	//显示救助记录
	//显示当前节点列表
	function getCurrentList(nodeName,nodeId,id){
		if(id==<%=masterId%>){
       		type=0;
        }else{type=1;}
		var pars="nodeName="+nodeName+"&nodeId="+nodeId+"&id="+id;
		var url = '/db/page/info/card/getCurrentList.do';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete:showNodeaa
        	}
        );
	}
		function getCurrentMemberList(nodeName,nodeId,id){
		if(id==<%=masterId%>){
       		type=0;
        }else{type=1;}
		var pars="nodeName="+nodeName+"&nodeId="+nodeId+"&id="+id;
		var url = '/db/page/info/card/getCurrentList.do';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete:showNodeMember
        	}
        );
	}
	//取家庭子节点列表
	function getFamilyChildList(nodeName,nodeId){
        var pars="nodeName="+nodeName+"&nodeId="+nodeId;
		var url = '/db/page/info/card/familyInfoCardTrees.do';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: viewFamilyChildList
        	}
        );
	}
	function viewFamilyChildList(originalRequest){
		$('CategoryTree').innerHTML=originalRequest.responseText;
	}
	//取成员子节点列表
	function initMemberChildNodes(nodeId,nodeName){
	<%--var pars="nodeName="+nodeName+"&nodeId="+nodeId;
	if(nodeName=='FAMILYINCOME'||nodeName=='FAMILYCLASS'||nodeName=='PAYOUT'||nodeName=='MEMBERINCOME'){
			getMemberNodeList(nodeId,nodeName)
			}else {
		var url = '/db/page/info/card/memberInfoCardTrees.do';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: function(response){
        			$('memberother').innerHTML=response.responseText;
        		}
        	}
        );
        getMemberNodeList(nodeId,nodeName);}--%>
        getMemberNodeList(nodeId,nodeName);
	}
	//取成员节点类表
	function getMemberNodeList(nodeId,nodeName){
		var pars="nodeName="+nodeName+"&nodeId="+nodeId+"&id="+<%=masterId%>;
		var url = '/db/page/info/card/getCurrentList.do';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete:function(response){
        		//alert(response.responseText);
        			$('memberotherinfo').innerHTML="<table class=\"tablelist\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolordark=\"#FFFFFF\" bordercolorlight=\"#CCCCCC\">"+response.responseText+"</table>";
        		}
        	}
        );
	}
	//展开属性节点
	function evolution(node,code,objtype,objid){
		curNode=node;
		var url = '/db/page/info/card/familyInfoCardTree.do';
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
	//member
	function evolutionMember(node,code,objtype,objid){
		curNode=node;
		var url = '/db/page/info/card/memberInfoCardTree.do';
        var pars="code="+code+"&objtype="+objtype+"&objid="+objid;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: viewChildMember
				        }
				        );
	}
	function viewChildMember(response){
		var nodes=response.responseXML.selectSingleNode("root").childNodes;
		var outtext="";
		for(var i=0;i<nodes.length;i++){
		 	outtext+=nodes.item(i).xml; 
		}
		curNode.innerHTML=outtext;
	}
	//member
	function viewNode(code,codeId,id){
		var cols;
       		type=0;
       		cols=1;
		var url = '/db/page/info/card/familyInfoCardLeaf.do';
        var pars="code="+code+"&codeId="+codeId+"&id="+id+"&cols="+cols;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: showNodeaa
				        }
				        );
	}
	function viewMemberNode(code,codeId,id){
		var cols;
        	cols=1;
		var url = '/db/page/info/card/memberInfoCardLeaf.do';
        var pars="code="+code+"&codeId="+codeId+"&id="+id+"&cols="+cols;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: showNodeMember
				        }
				        );
	}
	function showNodeaa(response){
			$('viewfamilychild').innerHTML=response.responseText;
	}
	function showNodeMember(response){
			$('memberotherinfo').innerHTML=response.responseText;
	}
	//显示家庭成员的信息
	function checkval(obj, nodeId){
		$('result').style.display="block";
		if(obj.checked){
			selout();
			obj.checked=true;
			getMemberPic('MEMBER',nodeId);
			getMemberBase('MEMBER',nodeId);
			getMemberClass('MEMBERCLASS',nodeId);
			$('memberbnt').innerHTML="<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\"><tr><td>"+	
			"<input style class=\"btn\" type=\"button\" value=\"简历\" onclick=\"initMemberChildNodes("+nodeId+",'RESUME');\" />&nbsp;"+
			"<input class=\"btn\" type=\"button\" value=\"赡抚养\" onclick=\"initMemberChildNodes("+nodeId+",'SUPPORTMAN')\" />&nbsp;"+
			"<input class=\"btn\" type=\"button\" value=\"健康\"  onclick=\"initMemberChildNodes("+nodeId+",'HEALTH')\" />&nbsp;"+"</td></tr>"+
			"<tr><td>"+"<input class=\"btn\" type=\"button\" value=\"残疾\" onclick=\"initMemberChildNodes("+nodeId+",'DISABILITY')\"/>&nbsp;"+
			"<input class=\"btn\" type=\"button\" value=\"收入\" onclick=\"initMemberChildNodes("+nodeId+",'MEMBERINCOME')\" />&nbsp;"+
			"<input class=\"btn\" type=\"button\" value=\"技能\" onclick=\"initMemberChildNodes("+nodeId+",'SKILL')\" />&nbsp;"+
			"</td></tr></table>";
			$('memberbnt').style.display='block';
			$('memberotherinfo').innerHTML="";
		}else{
			$('result').style.display="none";
		}
	}
	//装载页面
	function initpage(nodeId){
	$('result').style.display="block";
		getMemberPic('MEMBER',nodeId);
		getMemberBase('MEMBER',nodeId);
		getMemberClass('MEMBERCLASS',nodeId);
		$('memberbnt').style.display='block';
		return;
	}
	//列表全选
	function selall(){
		var objs =document.getElementsByName('checkbox');
		for(var i=0;i<objs.length;i++){
			objs[i].checked=true;
		}
	}
	//列表全取消
	function selout(){
		var objs =document.getElementsByName('checkbox');
		for(var i=0;i<objs.length;i++){
			objs[i].checked=false;
		}
	}
	//取成员一寸照片
	
	function getMemberPic(nodeName,nodeId){
		var url = '/db/page/info/card/getMemberPic.do';
        var pars="nodeName="+nodeName+"&nodeId="+nodeId;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: function(response){
				        $('memberpic').innerHTML=response.responseText;
				        }
				        }
				        );
	}
	//取成员的基本信息
	function getMemberBase(nodeName,nodeId){
	$('result').style.display="block";
		var url = '/db/page/info/card/getMemberBase.do';
        var pars="nodeName="+nodeName+"&nodeId="+nodeId;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: function(response){
				        //alert(response.responseText);
				        $('membermain').innerHTML="<table width=\"180px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><caption>成员基本信息</caption>"+response.responseText+"</table>";
				        }
				        }
				        );
	}
	//取成员的分类
	var cid;
	function getMemberClass(nodeName,nodeId){
	$('result').style.display="block";
		var url = '/db/page/info/card/getMemberClass.do';
		cid=nodeId;
        var pars="nodeName="+nodeName+"&nodeId="+nodeId;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: function(response){
				        //alert(response.responseText);
				        $('memberclass').innerHTML="<table width=\"130px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><caption><span style=\"cursor:hand ;background-color:#A8CCEA\" onclick=\"mrefreshclass('MEMBERCLASS',"+cid+")\">成员分类</span></caption>"+response.responseText+"</table>";
				        $('result').style.display="none";
				        }
				        }
				        );
	}
	
	function mrefreshclass(nodeName,nodeId){
	$('result').style.display="block";
	cid=nodeId;
		var url = '/db/page/info/card/refreshclass.do';
        var pars="nodeName="+nodeName+"&nodeId="+nodeId;
        // alert(nodeName+"  "+nodeId);
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: function(response){
				        //alert(response.responseText);
				        $('memberclass').innerHTML="<table width=\"130px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><caption><span style=\"cursor:hand ; backgroud-color:#A8CCEA\" onclick=\"mrefreshclass('MEMBERCLASS',"+cid+")\">成员分类</span></caption>"+response.responseText+"</table>";
				        $('result').style.display="none";
				        }
				        }
				        );
	}
	function refreshclass(nodeName,nodeId){
	$('result').style.display="block";
		var url = '/db/page/info/card/refreshclass.do';
        var pars="nodeName="+nodeName+"&nodeId="+nodeId;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: function(response){
				        //alert(response.responseText);
				        $('familyclass').innerHTML="<table width=\"130px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><caption><span style=\"cursor:hand ; backgroud-color:#A8CCEA\" onclick=\"refreshclass('"+nodeName+"',<%=familyId%>)\">家庭分类</span></caption>"+response.responseText+"</table>";
				        $('result').style.display="none";
				        }
				        }
				        );
	}
	function sc(){
	document.getElementById("ftd").style.width=document.body.offsetWidth-508;
	document.getElementById("memberchild").style.width=document.body.offsetWidth-460;
	}
setInterval(sc,50);
</script>