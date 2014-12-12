<%@ page language="java" import="java.util.*" pageEncoding="gb18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base>
		<title>综合查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<style type="text/css">
<!--
.odiv {
	border: 3px double  #cccccc;;
	height: 300px;
	OVERFLOW-y: scroll;
}
-->
        </style>
		<script type="text/javascript" src="../../js/prototype-1.6.0.2.js"></script>
	</head>
	<script type="text/javascript">
		<!-- 
			function edit(colname,tablename,p){
				alert(colname + "   " +tablename +"   "+p);
				//判断字段信息
				
			}
			function readchild(oIMG , tablename ,dep){
				alert(oIMG.alt + "   " +tablename +"   "+dep);
				if("1"==oIMG.alt){
					oIMG.alt=0;
					oIMG.src="../../images/minus.png";
					var childs=$('C_'+tablename).children;
					alert(childs.length);
					if(0==childs.length){
						alert("1");
						findchild(tablename,dep);
					}else{
						alert("2");
						$('C_'+tablename).style.display='block';
					}
				}else{
					oIMG.alt=1;
					oIMG.src="../../images/plus.png";
					$('C_'+tablename).style.display='none';
				}
			}
			var ptablename;
			function findchild(tablename,dep){
				$('waiting').style.display='block';
				ptablename=tablename;
				alert(ptablename +"  "+tablename+"  "+dep);
				var url = 'findtablechild.do';
				var pars='tablename='+tablename+'&dep='+dep;
		        var myAjax = new Ajax.Request(
						        url,{
						        method: 'post',
						        parameters: pars,
						        onComplete: showfindchild
						}
				);
			}
			function showfindchild(response){
				alert(response.responseText);
				$('C_'+ptablename).innerHTML= response.responseText;
				$('C_'+ptablename).style.display='block';
				$('waiting').style.display='none';
			}
		-->
	</script>
	<body><br>
		<table width="99%" cellpadding="0" cellspacing="0" border="0">
			<tr><td>选择查询条件</td><td colspan="2" align="right"><span id="waiting" style="display: none; color: red"></span></td></tr>
			<tr>
				<td width="200px">
					<c:out value="${requestScope.html}" escapeXml="false"></c:out>
				</td>
				<td width="3px">&nbsp;</td>
				<td>a</td>
			</tr>
		</table>
	</body>
</html>
