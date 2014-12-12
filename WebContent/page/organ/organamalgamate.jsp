<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>机构合并</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
		<style type="text/css">
<!--
.oDiv {
	border: 3px double #CCCCCC;
	padding: 3px;
	height: 500px;
	OVERFLOW-y: scroll;
}

-->
<!--
.opDiv {
	padding: 2px;
}

-->
.opDiv1 {
	color: red;
}
</style>
	</head>
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	<script type="text/javascript">
		<!--
			var oorgid;
			function expand(orgid){
				oorgid=orgid;
				if("none"==$('c'+oorgid).style.display){
					var url = 'organgetchilds.do';
					var pars = 'orgid='+orgid;
					$('waiting').innerHTML = "正在读取数据......";
					$('waiting').style.display = "block";
					var myAjax = new Ajax.Request(url, {
						method :'post',
						parameters :pars,
						onComplete :showresultexpand
					});
				}else{
					$('c'+oorgid).style.display = "none";
				}
			}
			function showresultexpand(response){
				$('waiting').innerHTML = "";
				$('waiting').style.display = "none";
				var temptxt= response.responseText ;
				if(0==temptxt){
					alert("没有下属机构");
				}else{
					$('c'+oorgid).innerHTML =response.responseText;
					$('c'+oorgid).style.display = "block";
				}
			}
			function checkorg(cr){
				var	obj = document.getElementsByName("checkid");
				var tempstr='';
				var temp1='';
				var temp2='';
				for (var i =0; i<obj.length;i++) {
					if(obj[i].checked){
							temp2= obj[i];
						if(''==temp1){
							temp1= obj[i];
						}else{
							if(temp2.value.length==temp1.value.length){
								temp1 =temp2;
							}else{	
								alert("你选择的机构不是同一个级别的机构，请重新选择！");
								cr.checked=false;
								break;
							}
						}
					}
				}
				var orgidlength=0 ;
				for (var i =0; i<obj.length;i++) {
					if(obj[i].checked){
						var orgname=$('p'+obj[i].value).getElementsByTagName('span')[0].innerHTML;
						tempstr =tempstr+'<div class="opDiv" id="'+obj[i].value+'">'+orgname+'</div>'
						orgidlength =obj[i].value.length;
					}
				}
				$('orgchecked').innerHTML=tempstr;
				if(cr.checked){
					findparent(orgidlength);
				}
			}
			var temporglength=${requestScope.local.depth}+1;
			function findparent(orgidlength){
				var url = 'organfindbylevel.do';
				var pars = 'orglevel='+orgidlength;
				$('waiting').innerHTML = "正在读取数据......";
				$('waiting').style.display = "block";
				var myAjax = new Ajax.Request(url, {
					method :'post',
					parameters :pars,
					onComplete :showresultfindparent
				});
			}
			function showresultfindparent(response){
				$('waiting').innerHTML = "";
				$('waiting').style.display = "none";
				//alert(response.responseText);
				$('porg').innerHTML=response.responseText;
			}
			var orglen=0;
			function findchilds(obj){
				var url = 'organfindchildlevel.do';
				orglen =obj.options[obj.selectedIndex].value.length;
				var pars = 'orgid='+obj.options[obj.selectedIndex].value;
				$('waiting').innerHTML = "正在读取数据......";
				$('waiting').style.display = "block";
				var myAjax = new Ajax.Request(url, {
					method :'post',
					parameters :pars,
					onComplete :showresultfindchild
				});
			}
			function showresultfindchild(response){
				$('waiting').innerHTML = "";
				$('waiting').style.display = "none";
				var temp='';
				//alert(" a1   "  +orglen);
				//alert(" a2  "  +response.responseText);
				//alert("a3  "+temporglength);
				for (var i=temporglength;i<=orglen/2;i++){
					temp =temp+$('c'+i).outerHTML;
				}
				$('porg').innerHTML=temp+response.responseText;
			}
			function orgedit(){
				var ids ="";
				var divs = $('orgchecked').getElementsByTagName('div');
				if(null==divs){

				}else{
				    for(var i =0 ;i<divs.length;i++){
				    	 ids=ids + divs[i].id+',';
					}
				}
				if(''==ids){
					alert('没有选中任何机构');
				}else{
					ids = ids.substr(0,ids.length-1);
					$('ids').value=ids;
					if( null== inputform.corg){
						alert('请选择社区');
					}else{
					 var corg=inputform.corg;
					if(''==corg.value){
						alert('请选择社区');
					}else{
						inputform.submit();
					}
					}
				}
			}
		-->
	</script>
	<body>
		<br>
		当前机构：${requestScope.local.orgname}
		<table width="99%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="300px">
					<div id="organzone" class="oDiv">
						<c:forEach var="rs" items="${requestScope.childlist}">
							<div id="p${rs.organizationId}"
								style="height: 16px; cursor: hand">
								<c:if test="${rs.depth==5}">
									<input name="checkid" type="checkbox"
										value="${rs.organizationId}" onClick="checkorg(this)" />
								</c:if>
								<span style="height: 16px; cursor: hand"
									onclick="expand(${rs.organizationId})">${rs.orgname }</span>
							</div>
							<div id="c${rs.organizationId}" style="display: none"></div>
						</c:forEach>
					</div>
				</td>
				<td width="8px">
					&nbsp;
				</td>
				<td valign="top">
					<form name="inputform" action="organedit.do" method="post">
						已经选中的机构：
						<div id="orgchecked" class="opDiv1">

						</div>
						<div>
							<div class="opDiv">
								<br>
								<strong>迁移到>></strong>
								<br>
							</div>
							<div id="porg">

							</div>
						</div>
						<div class="opDiv">
							<br>
							<br>
							<input id="ids" name="ids" type="hidden" value="" />
							<button onclick="orgedit()">
								保存信息
							</button>
							<br>
						</div>
					</form>
				</td>
			</tr>
		</table>
	</body>
	<span id="waiting" style="display: none; color: red"></span>
</html>