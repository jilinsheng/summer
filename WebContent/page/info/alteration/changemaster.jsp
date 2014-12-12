<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>户主变更</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
</head>
<body onload="initpage()">
	<br>
	<html:form action="/page/info/alteration/changemaster" method="post"
		target="my">
		<fieldset>
			<legend>
				户主变更
			</legend>
			<p>
			原户主：<bean:write name="oldmembername"/>&nbsp;&nbsp;&nbsp;&nbsp;选择新户主：
				<html:select name="master" property="memberId"
					onchange="showcurrent(this)">
					<html:option value="">未选择...</html:option>
					<html:options collection="list" labelProperty="membername"
						property="memberId" />
				</html:select>
				<html:hidden name="master" property="oldmemberpaperid" />
				<html:hidden name="master" property="oldmembername" />
				
			</p>
			<table class="table1" border="0" width="100%" cellpadding="0"
				cellspacing="0">
				<tr>
					<th>
						姓名
					</th>
					<th>
						性别
					</th>
					<th>
						身份证号
					</th>
					<th>
						与户主关系
					</th>
				</tr>
				<tbody id="main">
					<logic:present name="list" scope="request">
						<logic:iterate id="rs" name="list" scope="request">
							<tr id="main<bean:write name="rs" property="memberId"/>">
								<td>
									<bean:write name="rs" property="membername" />
									<html:hidden name="rs" property="memberId" />
								</td>
								<td>
									<bean:write name="rs" property="sex" />
								</td>
								<td>
									<bean:write name="rs" property="paperid" />
									<html:hidden name="rs" property="paperid" />
								</td>
								<td>
									<html:select property="relmaster" name="rs">
										<html:options collection="relmaster" labelProperty="item"
											property="dictionaryId" />
									</html:select>
								</td>
							</tr>
						</logic:iterate>
					</logic:present>
				</tbody>
				<tr>
					<td colspan="4">
						<br>
						<input type="button" value="保存" onclick="checkform()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="返回" onclick="parentClose()" />
						<input type="hidden" name="famId"
							value="<%=request.getAttribute("famId").toString()%>" />
						<br>
					</td>
				</tr>
			</table>
			<p>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

				&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="divResult" style="color: red"></span>
			</p>
		</fieldset>
	</html:form>
	<iframe name="my" style="display:none"></iframe>
</body>
</html:html>
<script>
	<!--
	 var lll ;
	 var cname="";
	 var cid="";
		function initpage(){
			var id =<bean:write name="oldmemberId"/>;
			document.getElementById("main"+id).style.display="none";
		}
		function showcurrent(obj){
	 		var curid=	obj.options[obj.selectedIndex].value;
	 		if(""==curid){}else{
	 		cid=obj.options[obj.selectedIndex].value;
	 		cname=obj.options[obj.selectedIndex].text;
	 		var  l=	document.getElementById("main").getElementsByTagName("tr");
	 		for(var i =0 ;i< l.length;i++){
	 			l[i].style.display="block";
	 			var temp=l[i].getElementsByTagName("td")[3];
	 			temp.getElementsByTagName("select")[0].value="0";
	 		}
	 		document.getElementById("main"+curid).getElementsByTagName("td")[3].getElementsByTagName("select")[0].value="112";
	 		document.getElementById("main"+curid).style.display="none"; 		
	 		}
		}
		function checkform(){
			var boolObj = false;
			var list= document.getElementsByName("relmaster");
			var mm = document.getElementsByName("memberId")[0];
			var m =mm.options[mm.selectedIndex].value;
			var temp=false;
			for(var i=0;i<list.length;i++){
				if (list[i].value=="0"){
					temp=true;
				}
			}
					if(m==""){
						alert("没有为家庭选择户主");
					}else{
						if(!temp){
							changemasterForm.submit();
						}else{
						alert("没有成员设置与户主关系");
							}
					}
		}
		function parentRefresh(){
		//	alert(cid+"   .  "+cname);
		//	if(cid!=""){
		//		var arrr= new Array();
		//		arrr= window.dialogArguments;
		//		arrr[0]=cname;
		//	}
		//	alert("我来了");
		//  window.close();
		}
		function parentClose(){
			<!-- 
				window.location.reload("../../intro/modifamily.jsp");
			-->
		}
	-->
</script>