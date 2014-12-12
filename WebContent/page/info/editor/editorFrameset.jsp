<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>��ͥ��Ϣά��</title>
	<meta http-equiv="Content-Type" content="text/html;   charset=GB18030">
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

<body onload="initpage()" style="overflow-x: hidden; overflow-y: scroll">
	<table width="99%" border="0" cellspacing="0" cellpadding="2">
		<tr>
			<td width="250" height="500">
				<div class="TreeMenu" id="CategoryTree"
					style="height: 100%; border: solid 1px; border-color: #cccccc; background: #ececec">
					<bean:write name="familytree" filter="false" />
				</div>
			</td>
			<td>
				<div id="oper"
					style="padding-left: 4px; padding-top: 4px; height: 100%; border: solid 0px; border-color: #cccccc;">
				</div>
			</td>
		</tr>
	</table>
	<div id="wait"
		style="position: absolute; right: 0px; bottom: 0px; display: none">
		���ڶ�ȡ����
		<img src="../../images/loadingtiao.gif"></img>
	</div>
</body>
</html:html>
<script language="javascript" type="text/javascript">
<!-- 
var curNode;
	function ChangeStatus(node,code,objtype,objid){
		if (node.parentNode){
			curNode=node;
			if(code=='FAMILYINCOME'||code=='FAMILYCLASS'||code=='MEMBERCLASS'||code=='PAYOUT'||code=='MEMBERINCOME'||code=='ASSET'){
			node.parentNode.className = "Opened";
			}else{
			if (node.parentNode.className == "Opened"){
				node.parentNode.className = "Closed";
			}else{
				node.parentNode.className = "Opened";
				$('wait').style.display="block";
				evolution(node.parentNode,code,objtype,objid);
			}
			}
			//if (node.parentNode.childNodes.length<=2){
			//}
		}
	}
	//չ�����Խڵ�
	function evolution(node,code,objtype,objid){
		curNode=node;
		var url = '/db/page/info/editor/editorInfoCardTree.do';
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
		//alert(outtext);
		curNode.innerHTML=outtext;
		curNode="";
		$('wait').style.display="none";
	}
	var savetype ;
	function viewNode(obj ,code,codeId,id){
	$('wait').style.display="block";
		var cols;
       		type=0;
       		cols=1;
       		savetype=code;
       		curNode =obj;
		var url = '/db/page/info/editor/editorInfoCardLeaf.do';
        var pars="code="+code+"&codeId="+codeId+"&id="+id+"&cols="+cols;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: showNodeaa
				        }
				        );
	}
	function showNodeaa(response){
		//alert(response.responseText);
			$('oper').innerHTML=response.responseText;
			$('wait').style.display="none";
	}
	//��ʾ��ǰ�ڵ��б�
	function getCurrentList(obj,nodeName,nodeId,id){
	$('wait').style.display="block";
		curNode =obj;
		var pars="nodeName="+nodeName+"&nodeId="+nodeId+"&id="+id;
		var url = '/db/page/info/editor/editorGetNodeList.do';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete:showNodeList
        	}
        );
	}
	function showNodeList(response){
		if(response.responseText==0){
		}else{
			$('oper').innerHTML=response.responseText;
		}
		$('wait').style.display="none";
	}
	
-->
</script>
<script type="text/javascript">
<!--
	//�������Ϣ��Ŀ
	function checkForm(oForm){
		var els= oForm.elements;
		var j=els.length-1;
		for(var i=0;i<j;i++){
			var iObj=els[i];
			var oAtt = iObj.attributes;
			var ruleo= oAtt.getNamedItem("rule");
			if(ruleo!=null){
			var rule =ruleo.value;
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
		var  pt =document.getElementById("MEMBER/PAPERTYPE");
		if(pt.value=="390"){
			if (!CheckIdCard(title,iObj)) return title;}
		}
		else{
			}
		}
	}
	}
	return 'ok';
	}
	//������
	function save(){
		var oForm =document.getElementById("inputForm");
        var pars=Form.serialize(oForm);
		var result=checkForm(oForm);
        if(result=="ok"){
         	$('divResult').style.visibility ="visible";
         	$('divResult').innerHTML ="<img src=\"/db/page/images/loading.gif\" alt=\"������\" />���ڱ���...";
        }else{
         	$('divResult').style.visibility ="visible";
         	$('divResult').innerHTML ="��Ŀ:<"+result+">��֤δͨ��!";
         return false;
        }
		var url='/db/page/info/familychild/FamilyChildSaveServlet';
        var myAjax = new Ajax.Request(
        url,
        	{
        		method: 'post',
        		parameters: pars,
        		onComplete: viewResult
        	}
        );
	}
	//���Ա������Ϣ
	var tnode;
	function viewResult(originalRequest){
		var req =originalRequest;
		var data=req.responseText;
		var s= data.split(",");
		var oDIV=document.getElementById("divResult");
		if(s[0]==1){
		 if(s[1]!=0){
		  	var f=parseInt(s[1]);
			window.frames("inch").document.all.id.value=f;
			window.frames("inch").document.all.con.value="aaaaa";
			}
			document.getElementById("savebnt").style.display='block';
			data="����ɹ�";
			$('divResult').style.visibility ="hidden";
		}else if(s[0]==2){
			data="û�б������Ŀ";	
		}else{
			data="����ʧ��";	
		}
		alert(data);
		if(curNode.tagName==("SPAN")){
			tnode =curNode;
			curNode.click();
			curNode.parentNode.className = "Closed";
			curNode.parentNode.getElementsByTagName("img")[0].click();
		}else {
			tnode.className = "Closed";
			tnode.getElementsByTagName("img")[0].click();
		}
	}
	//�����ڵ���Ϣ
	function parentHandle(){
		var tempnode=curNode;
		tempnode.parentNode.className = "Closed";
		nodeHandle(tempnode);
		tempnode.parentNode.getElementsByTagName("img")[0].click();
	}
	function nodeHandle(tempnode){
		tempnode.click();
	}
	//������Ϣ����
	function ClassCommit(obj,nodeName,nodeId,propertyName){
		var obj1 =obj;
		var val="";
		var url = '/db/page/info/familyclass/ClassCommit';
		if(obj.alt=="δ��ʵ"){
	   		val="0";
	    }else{
	      	val="1";
	    }
	  //  alert(val);
		var pars="nodeId="+nodeId+"&nodeName="+nodeName+"&propertyName="+propertyName+"&val="+val;
		var myAjax = new Ajax.Request(
	        url,
	        	{
	        		method: 'post',
	        		parameters: pars,
	        		onComplete: function (){ alert("����ɹ�");}
	        	});
	        	if(obj.alt=="δ��ʵ"){
	        		obj.src="/db/page/images/right.gif";
	        		obj.alt="�Ѻ�ʵ";
	        	}else{
	        		obj.src="/db/page/images/quest.gif";
	        		obj.alt="δ��ʵ";
	        	}
	}
	//��ѡ����
	var curId="";
	var curpId="";
	var curName="";
	function checkval(obj, nodeId , parentId, nodeName){
		if(obj.checked==true){
			var objs =document.getElementsByName('checkbox');
				for(var i=0;i<objs.length;i++){
					objs[i].checked=false;
				}
			curId =nodeId;
			curpId=parentId;
			curName=nodeName;
			obj.checked=true;
			//alert(curId+" "+curpId+" "+curName);
		}else{
			curId="";
			curpId="";
			curName="";
		}
	}
	//ȫѡ
	function selall(){
		
	}
	//�˽ڵ��б� ��������ڵ�
	function addNode(){
		var pid = document.getElementById("pid").value;
		var nodeName = document.getElementById("nodeName").value;
		//alert(pid+" "+nodeName);
		var cols;
       		type=0;
       		cols=1;
		var url = '/db/page/info/editor/addNodeInfo.do';
        var pars="code="+nodeName+"&pid="+pid+"&cols="+cols;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: showNodeaa
				        }
				        );
	}
	//�˽ڵ��б� �޸�����ڵ�
	function editNode(){
	//alert(curId);
		if(curId!=""&&curId!="null"&&curId!="undefined"){
		viewNode( curNode,curName,curId,curpId);
		}else {
			alert("û��ѡ���κ���Ϣ");
		}
		curId="";
		curpId="";
		curName="";
	}
	function deleteNode(){
		var url = '/db/page/info/editor/deleteNode.do';
        var pars="code="+curName+"&curId="+curId;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: deleteback
				        }
				        );
		curId="";
		curpId="";
		curName="";
		
	}
	function deleteback(originalRequest){
		var req =originalRequest;
		var data=req.responseText;
		if(0==data){
		alert("ɾ���ɹ�");
			}
		if(1==data){
		alert("����ɾ���ӽڵ���Ϣ");
			}
		if(3==data){
		alert("����������ɾ��");
		}
		curNode.click();
	}
	//ˢ�·���
	function refreshClass(type,id){
		var url = '/db/page/info/editor/refreshClass.do';
        var pars="code="+type+"&codeId="+id;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: showNodeaa
				        }
				        );
	}
	function initpage(){
		$('wait').style.display="block";
		document.getElementsByTagName('img')[0].click();
	}
	function writeBirthandSex(obj){
	$('savebnt').style.display="block";
	var pt= document.getElementById("MEMBER/PAPERTYPE");
	var memid= document.getElementById("MEMBER/MEMBER_ID");
	if(pt.value!="390"){
		return false;
	}
	if (!CheckIdCard("���֤��",obj)) return "���֤����";
	iteratorpaper(memid.value,obj.value);
	var oForm =document.getElementById("inputForm");	
	var idcard=obj.value;
	var icdate;
	var icsex=0;
		if(!idcard==""){ 
			switch(idcard.length){
				case 15:
					icdate="19"+ idcard.substr(6,2)+"-"+ idcard.substr(8,2)+"-"+idcard.substr(10,2);
					if ((idcard.substr(idcard.length-1,1)%2)!=0){ 
����						icsex=0; 
					}else {
						icsex=1;
					}
					break;
				case 18:
					icdate=idcard.substr(6,4)+"-"+ idcard.substr(10,2)+"-"+idcard.substr(12,2);
					if ((idcard.substr(idcard.length-2,1)%2)!=0){
����						icsex=0; 
					}else {
						icsex=1;
					}
					break;					
			} 
		}else{
		document.getElementById("MEMBER/BIRTHDAY").value="";
		}  
		if(icdate!="undefined") document.getElementById("MEMBER/BIRTHDAY").value=icdate;
		if(icsex=="0" || icsex=="1"){
		document.getElementById("MEMBER/SEX").value="11"+icsex;
		}
	}
	function iteratorpaper(memid,obj){
		var url = '/db/page/info/editor/idcarditerance.do';
        var pars="memberid="+memid+"&paperid="+obj;
        var myAjax = new Ajax.Request(
				        url,{
				        method: 'post',
				        parameters: pars,
				        onComplete: iteratorpaperback
				        }
				        );
	}
	function iteratorpaperback(response){
		if(response.responseText==0){
			$('savebnt').style.display="block";
		}else{
			alert("֤�������ظ�����˶����֤�룬�������룡");
			$('savebnt').style.display="none";
		}
	}
	function addunit(){
		//window.showModelessDialog("../../system/unit/unitquery.jsp",arr,"dialogWidth=400px;dialogHeight=500px;status=off");
		window.open("../../system/unit/unitquery.jsp","","height=600,width=400,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
	function parentClose(){
		window.location.reload("../../intro/modifamily.jsp");
	}
-->
</script>