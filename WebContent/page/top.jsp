<%@ page language="java" pageEncoding="GB18030"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<title>标题页</title>
	<style type="text/css">
body,td,th {
	margin: 0px;
	padding: 0px;
	color: #FFFFFF;
	font-size: 9pt;
}
    </style>
	<script type="text/javascript" src="js/prototype-1.6.0.2.js"></script>
</head>
<body onbeforeunload="body_onUnload()">
	<table width="100%" border="0" align="left" cellpadding="0"
		cellspacing="0">
		<tr>
			<td width="50%" height="33" align="left" valign="top"
				background="images/tabbg1.gif" style="margin: 0px; padding: 0px;">
				<div style="width:100%;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src ='images/nj-logo1.png',sizingMethod = 'image')"></div>
			<!-- 吉林市 和 吉林省标题v -->
			</td>
			<td width="50%" height="33" align="right" valign="bottom"
				background="images/tabbg1.gif">
				<span style="CURSOR: hand;color:#000;font:14px;" onClick="window.external.addFavorite('http://www.jldb.com','吉林农村低保信息管理系统')" ><strong>收藏本页</strong></span>
				&nbsp;
				<span onClick="window.open('../software/help/index.html')" style="CURSOR: hand;color:#000;font:14px;"><strong>帮助文档</strong></span>
			</td>
		</tr>
		<tr>
			<td width="50%" height="27" align="left" valign="middle"
				background="images/topbottom.gif" style="padding-top: 5px;">
				<span id="webtime"> <script>setInterval("webtime.innerHTML=new Date().toLocaleString();",1000);</script>
				</span>&nbsp;
				<!--  <a href="/db/page/oa/displaymsg.jsp" target="_blank">
				</a>--><img src="images/smile.gif" border="0"></img>&nbsp;欢迎您:&nbsp;
				<bean:write name="employee" property="sysTOrganization.orgname"
					scope="session" />
				&nbsp;
				<bean:write name="employee" property="sysTEmpext.name"
					scope="session" />
			</td>
			<td width="50%" height="27" align="right" valign="middle"
				background="images/topbottom.gif" style="padding-top: 5px;">
				<!--  <img src="images/000.gif" width="50" height="16" onClick="window.showModalDialog('common/Log4jServlet')"></img>-->
				<img border="0" src="images/000.gif" width="50" height="16" onclick ="top.window.location.reload()"></img>
				&nbsp;
				<img src="images/001.gif" width="50" height="16" onClick="logout()"></img>
				&nbsp;
				<img src="images/002.gif" width="50" height="16" onClick="closed()"></img>
				&nbsp;
			</td>
		</tr>
	</table>
</body>
</html:html>
<script language="javascript1.2" type="text/javascript">
	<!--
		function logout(){
			top.window.location.reload("../loginout.do");
		}
		function closed(){
			var url = '../loginout.do';
        	var pars='';
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: showresult
					        }
					       );
					  
		}
		function showresult(response){
			top.window.close();
		}
//ie 按关闭按钮
		function closed1(){
			var url = '../loginout.do';
        	var pars='';
	        var myAjax = new Ajax.Request(
					        url,{
					        method: 'post',
					        parameters: pars,
					        onComplete: showresult1
					        }
					       );
					  
		}
		function showresult1(response){
		}
		
		function body_onUnload()
		{
			//alert(document.body.clientWidth+" , "+window.event.clientX)
		var a =document.body.clientWidth -window.event.clientX ;
			if(event.altKey){
				closed1();
			}else if(a>-3&&a<22&&event.clientY<0){
				closed1();
			}
			
		 // if ( &&event.clientY<0||event.altKey)
		  //{
			//  event.returnValue="确定退出?";
		     //alert("安全退出");
		     ///closed1(); window.event.clientX>document.body.clientWidth&&
		 // }
		  //else
		 // {
		  // }
			//if(window.screenLeft>10000){
			//	closed1();
			//}
		} 
</script>