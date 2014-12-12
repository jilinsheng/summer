<%@ page contentType="text/html; charset=gb18030" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String parentId = request.getParameter("nodeId");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb18030" />
		<title>成员信息</title>
		<link href="../../css/memberlayout.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript" src="../../js/Validator.js"></script>
	<script type="text/javascript" src="../../js/Calendar2.js"></script></head>
	<body style="overflow-x: hidden">
		<table width="100%" height="320" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="80" align="center" valign="top" bgcolor="#ffffff">
					<div id="memberlist">
						<jsp:include
							page="/page/info/member/MemberListServlet?nodeId=<%=parentId%>"
							flush="true"></jsp:include>
					</div>
				</td>
				<td height="320px" valign="top" bgcolor="#ffffff">
					<div id="memberoperzone" style="border:0 ; padding:0; margin:0;"> 
					</div>
				</td>
			</tr>
		</table>
		<div id="divResult" onclick="this.style.display='none'"></div>
	</body>
</html>
<script type="text/javascript">
<!--
	function changedisplay(num,code){
	var oDiv2 =document.getElementById("brow"+num);
	if(code==1){
		
		oDiv2.style.display="";
	}
	if(code==0){
		
		oDiv2.style.display="none";
	}
}
-->
</script>
<script type="text/javascript">
	var masterid;
<!--
	//保存成员
	function memberSave(inputForm){
	    if(inputForm==null||inputForm==""||inputForm=="null"){
	    inputForm="";
	    }
		masterid =inputForm;
    	var url = '/db/page/info/member/MemberSaveServlet';
    	var oForm =document.getElementById("inputForm"+masterid);
    	var pars=Form.serialize(oForm);
    	alert(pars);
    	var result=checkForm(oForm);
    	if(result=="ok"){
    		$('divResult').style.display ="block";
    		$('divResult').innerHTML ="<img src=\"/db/page/images/loading.gif\" alt=\"进度条\" />正在保存...";
    	}else{
        	$('divResult').style.display ="block";
        	$('divResult').innerHTML ="项目:<"+result+">验证未通过!";
        	return false;
    }
    var myAjax = new Ajax.Request(
   	url,
   		{
   		method: 'post',
    	parameters: pars,
    	onComplete: viewinfo
    	}
    	);
	}
    //添加成员表单
	function addmember(nodeId,parentId){
		if(nodeId==null){
			alert('家庭为空');
			return false;
     	}
     	var url = '/db/page/info/member/MemberInitServlet';
     	var pars="nodeId="+nodeId+"&parentId="+parentId;
     	alert(pars);
     	var myAjax = new Ajax.Request(
     url,
        {
        method: 'get',
        parameters: pars,
        onComplete: addmemberinfo
        }
        );
	}
 	//读取一个家庭成员
 		function initmember(nodeId){
		if(nodeId==null){
			alert('家庭为空');
			return false;
     	}
     	var url = '/db/page/info/member/MemberInitServlet';
     	var pars="nodeId="+nodeId;
     	alert(pars);
     	var myAjax = new Ajax.Request(
     url,
        {
        method: 'post',
        parameters: pars,
        onComplete: addmemberinfo
        }
        );
	}
 	//保存成员回显信息     
	function viewinfo(response){
		if(masterid==null||masterid==""){
			memberlist();
		}else{
 			var  ss = response.responseText.split(",");
  	 		$('divResult').style.display ="block";
 	 		$('divResult').innerHTML = ss[0];
 	 		$('MEMBERNAME'+masterid).innerHTML = ss[1];
 	 		//$('PAPERID'+masterid).innerHTML = ss[2];
 	 		//$('RELMASTER'+masterid).innerHTML = ss[3];
 	 		//$('SEX'+masterid).innerHTML = ss[4];
 	 		if(ss[3]=="本人"){
 	 			window.parent.frames["familyFrame"].location.reload();
 	 		}
 	 	}
	}
	//回显成员列表
	function memberlist(){
     	var url = '/db/page/info/member/MemberListServlet';
     	var pars="nodeId="+<%=parentId%>;
     	var myAjax = new Ajax.Request(
     url,
        {
        method: 'post',
        parameters: pars,
        onComplete: viewmemberlist
        }
        );
	}
	function viewmemberlist(response){
		$('memberlist').innerHTML=response.responseText;
		$('memberoperzone').innerHTML="";
		$('divResult').style.display ="none";
	}
	//
	//新建一个成员生成表单，显示在页面上   
	function addmemberinfo(response){
		$('memberoperzone').innerHTML=response.responseText;
	}
//-->
</script>
<script type="text/javascript">
<!--
	//验证表单信息
	function checkForm(oForm){
		var els= oForm.elements;
		var j=els.length-1;
		for(var i=0;i<j;i++){
			var iObj=els[i];
			var oAtt = iObj.attributes;
			var rule= oAtt.getNamedItem("rule").value;
			if(rule==9){
			}else
			{
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
			else{}
			}
		}
	return 'ok';
	}
-->
</script>
<script type="text/javascript">
<!--
function getSexDate(ic){
	var idcard=ic.value;
	var icdate;
	var icsex=0;
		if(!idcard==""){ 
			switch(idcard.length){
				case 15:
					icdate="19"+ idcard.substr(6,2)+"-"+ idcard.substr(8,2)+"-"+idcard.substr(10,2);
					if ((idcard.substr(idcard.length-1,1)%2)!=0){ 
　　						icsex=0; 
					}else {
						icsex=1;
					}
					break;
				case 18:
					icdate=idcard.substr(6,4)+"-"+ idcard.substr(10,2)+"-"+idcard.substr(12,2);
					if ((idcard.substr(idcard.length-2,1)%2)!=0){
　　						icsex=0; 
					}else {
						icsex=1;
					}
					break;					
			} 
		}else{
		document.getElementById("fmbBirthday").value="";
		}  
		if(icdate!="undefined") document.getElementById("fmbBirthday").value=icdate;
		if(icsex=="0" || icsex=="1"){
			document.getElementsByName("fmbSex")[icsex].checked="checked";
		}
	}
-->
</script>
<script type="text/javascript">
<!--
	function initMemberChildNodes(nodeId,nodeName){
		var oIframe=$('memberOper'+nodeId);
		oIframe.src="/db/page/info/memberchild/memberchildSave.jsp?nodeId="+nodeId+"&nodeName="+nodeName;	
	}
	function initMemberClass(nodeId,nodeName){
		masterid=nodeId;
		var url = '/db/page/info/familyclass/FamilyClassInitServlet';
        var pars="nodeId="+nodeId+"&nodeName="+nodeName;
        var myAjax = new Ajax.Request(
        url,
        {
        method: 'post',
        parameters: pars,
        onComplete: viewMemberClass
        });
	}
	 function viewMemberClass(response){
 	 $('fmsort'+masterid).innerHTML = response.responseText;
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