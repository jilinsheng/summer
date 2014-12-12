<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	String temp = request.getAttribute("colspan").toString();
	int cnt = Integer.parseInt(temp);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>查询结果显示页</title>
	<%-- <link rel="stylesheet" href="../css/style.css" type="text/css"></link>
	<link rel="stylesheet" href="../css/tree.css" type="text/css"></link>--%>
	<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
	<style type="text/css">
body,p {
	font-family: "宋体";
	font-size: 9pt;
	font-weight: 600;
	margin: 0px;
}

a {
	text-decoration: none;
}

.colField {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	color: #000000;
	background: #F5A89A;
}

.colValue {
	font-family: "宋体";
	font-size: 9pt;
	text-align: center;
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	height: 25px;
}

.leftstyle {
	background: url(../images/newsleft.gif) no-repeat;
	background-position: left;
	width: 140px;
	height: 25px;
}

.rightstyle {
	background: url(../images/newsright.gif) no-repeat;
	background-position: right;
	height: 25px;
}

.centerstyle {
	background: url(../images/newscenter.gif);
	background-repeat: repeat;
}

.borderstyle {
	border-width: 0px 1px 1px 1px;
	border-style: solid;
	border-color: #C2523A;
}

.captionstyle {
	background: url(../images/leftbtn.gif) no-repeat;
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	font-weight: 600;
	padding-left: 15px;
	padding-top: 3px;
}

.more {
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	font-weight: 400;
}

.content {
	padding-left: 18px;
	padding-top: 3px;
	font: "Times New Roman", Times, serif;
	font-size: 9pt;
	background-image: url(../images/index_left1_46.gif);
	background-repeat: no-repeat;
	background-position: 3px;
}

a:link {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}
    </style>
	<script language="javascript" type="text/javascript">      
		function fullscreen(){
			if (parent.operating.cols=="*,225"){
				parent.operating.cols="0,*";     
				for(var i = 4; i <= <%=cnt%>; i++){
					var t = document.getElementById("col"+i);
					t.style.display="block"; 
			    }
			}
			else{ 
				parent.operating.cols="*,225"; 
			    for(var i = 4; i <= <%=cnt%>; i++){
					var t = document.getElementById("col"+i);
					t.style.display="none"; 
			    }
		    } 
		}
		function full(){
			if (parent.operating.cols=="*,225"){ 
			    for(var i = 4; i <= <%=cnt%>; i++){
					var t = document.getElementById("col"+i);
					t.style.display="none"; 
			    }
			}else{
				for(var i = 4; i <= <%=cnt%>; i++){
				var t = document.getElementById("col"+i);
				t.style.display="block"; 
				} 
			}
		}
</script>
</head>
<body style="overflow: hidden">
	<table id="showresultzone" width="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td class="leftstyle">
				<span class="captionstyle">查询结果</span>
			</td>
			<td width="60%" align="right" class="centerstyle">
				<div class="rightstyle" id="bnt">
					<button id="min" onClick="min()"
						style="border: 0px; width: 29px; height: 15">
						<img src="../images/min.gif" width="29" height="15" border="0" />
					</button>
					<button id="max" onClick="max()"
						style="border: 0px; width: 29px; height: 15">
						<img src="../images/max.gif" width="29" height="15" border="0" />
					</button>
					<button id="revert" onClick="revert()" width="29" height="15">
						<img src="../images/revert.gif" width="29" height="15" border="0"></img>
					</button>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="borderstyle">
				<table width="100%" border="0" cellspacing="0">
					<%
						for (int i = 1; i <= cnt; i++) {
								out.println("<col id='col" + i + "'>");
							}
					%>
					<bean:write name="familylist" filter="false" />
					<tfoot>
						<tr>
							<td class="colField" colspan="<%=cnt%>">
								<p align="center">
									<bean:write name="all" filter="false" />
									<bean:write name="tools" filter="false" /><button onclick="exportXLS()">导出excel</button>
								</p>
							</td>
						</tr>
					</tfoot>
				</table>
			</td>
		</tr>
	</table>
	<div id="toolsmenu"
		style="position: absolute; top: 0px; right: 0px; display: none">
		<table width="20" border="0" cellpadding="0" cellspacing="0"
			class="vsnap">
			<tr>
				<td>
					<button style="border: 0px; width: 20px; height: 28px"
						onClick="revert()">
						<img src="../images/vrevert.gif" width="20" height="28" />
					</button>
				</td>
			</tr>
			<tr>
				<td>
					<button style="border: 0px; width: 20px; height: 30px"
						onClick="max()">
						<img src="../images/vmax.gif" width="20" height="30" />
					</button>
				</td>
			</tr>
		</table>
</body>
</html:html>
<%
	String url = (String) request.getAttribute("url");
%>
<script>
//复选操作
	//全选
	function selall(){
	if (parent.operating.cols!="*,225"){ 
			revert();
		}
		var objs =document.getElementsByName('checkbox');
		for(var i=0;i<objs.length;i++){
			objs[i].checked=true;
		}
	}
	//列表反选
	function selout(){
	if (parent.operating.cols!="*,225"){ 
			revert();
		}
		var objs =document.getElementsByName('checkbox');
		for(var i=0;i<objs.length;i++){
			objs[i].checked=false;
		}
	}
	//多选操作
	//当选中一个时 框架变成 恢复模式  用来挑选操作的对象
	function checkval(obj, nodeId){
		if (parent.operating.cols!="*,225"){ 
			revert()
		}
		var tr= document.createElement("tr");
		alert(obj.parentNode.parentNode.cloneNode(true));
		var table= parent.frames("oper").document.all.shoppingbox;
		alert(table);
		table.innerHTML="<span>aaaaaaaa</span>";
		var url ="<%=url%>";
		var chs= document.getElementsByName("checkbox");
		var temp="<FAMILY>";
		for(var i=0;i<chs.length;i++){
			if(chs[i].checked){
				temp+="<FAMILYID>"+chs[i].value+"</FAMILYID>";
			}
		}
		temp+="</FAMILY>";
		//parent.frames("oper").location.reload(url+"?familyids="+temp);
	}

	//复选操作
	//单选操作
	function clickval(obj, nodeId){
	var url ="<%=url%>";
			obj.checked=true;
			var newname=new Array();
			//newname[0] ="";
			//showModelessDialog(url+"?nodeName=FAMILY&nodeId=" + nodeId,newname,"status:false;dialogWidth:800px;dialogHeight:600px");
			parent.frames("oper").location.reload(url+"?nodeName=FAMILY&nodeId=" + nodeId);
			if (parent.operating.cols!="*,225"){ 
				revert()
			}
			//if(newname[0]!=""){
				//document.getElementById("mastername"+nodeId).innerHTML=newname[0];
			//} 
		var imgs =  document.getElementsByName("ctrlopt");;
		for(var i=0; i<imgs.length;i++){ 		
			imgs[i].src="/db/page/images/check1.jpg";
		}
		
		obj.getElementsByTagName("img")[0].src="/db/page/images/check2.jpg";
	}
	function min(){
		parent.operating.cols="*,20";
		document.getElementById("showresultzone").style.display="none";
		document.getElementById("toolsmenu").style.display="block";
	}
	function max(){
		parent.operating.cols="0,*";
		for(var i = 3; i <= <%=cnt%>; i++){
				var t = document.getElementById("col"+i);
				t.style.display="block"; 
			} 
	document.getElementById("bnt").innerHTML="<button id=\"min\" style=\"border:0px;width:29px;height:15\" onclick=\"min()\"><img src=\"../images/min.gif\"></img></button><button id=\"revert\" style=\"border:0px;width:29px;height:15\" onclick=\"revert()\"><img src=\"../images/revert.gif\"></img></button>";		
		//document.getElementById("min").style.display="block";
		//document.getElementById("revert").style.display="block";
		document.getElementById("showresultzone").style.display="block";
		document.getElementById("toolsmenu").style.display="none";
		//document.getElementById("max").style.display="none";
	}
	function revert(){
		parent.operating.cols="*,225";
		 for(var i = 4; i <= <%=cnt%>; i++){
					var t = document.getElementById("col"+i);
					t.style.display="none"; 
			    }
document.getElementById("bnt").innerHTML="<button id=\"min\" style=\"border:0px;width:29px;height:15\" onclick=\"min()\"><img src=\"../images/min.gif\"></img></button><button id=\"max\" style=\"border:0px;width:29px;height:15\" onclick=\"max()\"><img src=\"../images/max.gif\"></img></button>";
		document.getElementById("showresultzone").style.display="block";
		document.getElementById("toolsmenu").style.display="none";
		//document.getElementById("revert").style.display="none";
	}
	//document.getElementById("max").style.display="none";
	document.getElementById("bnt").innerHTML="<button id=\"min\" style=\"border:0px;width:29px;height:15\" onclick=\"min()\"><img src=\"../images/min.gif\"></img></button><button id=\"revert\" style=\"border:0px;width:29px;height:15\" onclick=\"revert()\"><img src=\"../images/revert.gif\"></img></button>";
</script>
<script type="text/javascript" defer="defer">
	<%
		String isfirst = (String) request.getAttribute("isfirst");
		if (isfirst == null || isfirst.equals("")) {
		%>
			if (parent.operating.cols=="*,225"){ 
			    for(var i = 4; i <= <%=cnt%>; i++){
				var t = document.getElementById("col"+i);
					t.style.display="none"; 
			    }
			}else{
				for(var i = 4; i <= <%=cnt%>; i++){
				var t = document.getElementById("col"+i);
				t.style.display="block"; 
				} 
			}
		<%		
		} else { 
		%>
			parent.operating.cols="0,*";     
			for(var i = 4; i <= <%=cnt%>; i++){
				var t = document.getElementById("col"+i);
				t.style.display="block"; 
			} 
		<%
		}
	%>
</script>
<script>
	function exportXLS(){
		window.open("../system/exportfile/exportExcel.do","","height=100,width=200,status=no,toolbar=no,menubar=no,location=no,titlebar=no,left=200,top=100");
	}
</script>