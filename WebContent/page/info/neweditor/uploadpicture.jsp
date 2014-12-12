<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html"
	prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-nested"
	prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<base target="_self">
<head>
	<title>上传照片</title>
</head>
<link rel="stylesheet" type="text/css"
	href="../../imgareaselect/css/imgareaselect-default.css" />
<script type="text/javascript"
	src="../../imgareaselect/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="../../imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript"> 
JQ = $; //rename $ function 
</script>
<script type="text/javascript">
JQ(document).ready(function () {
    JQ('#idImg').imgAreaSelect({aspectRatio: '13:16', handles: true ,
        onSelectEnd: function (img, selection) {
         $('input[name=x1]').val(selection.x1);
            $('input[name=y1]').val(selection.y1);
            $('input[name=x2]').val(selection.x2);
            $('input[name=y2]').val(selection.y2);   
        }
    });
});
</script>
<script type="text/javascript">
<!--
function a(){
    var x1= document.getElementById("x1");
	var y1= document.getElementById("y1");
	var x2= document.getElementById("x2");
	var y2= document.getElementById("y2");
	var i =document.getElementById("idImg");
	if(""==x1.value){
		x1.value=1;
		y1.value=1;
		x2.value=i.width;
		y2.value=i.height;
	}
	alert(x1.value+'>>>'+y1.value+'>>>'+x2.value+'>>>'+y2.value);
	uploadpicForm.submit();
}
//-->
</script>
<script type="text/javascript" src="../../js/CJL.0.1.min.js"></script>
<script type="text/javascript" src="../../js/QuickUpload.js"></script>
<script type="text/javascript" src="../../js/ImagePreviewd.js"></script>
<html:html>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<html:form action="/page/info/neweditor/uploadpicture.do" method="post"
	enctype="multipart/form-data">
<input type="hidden" name="x1" value="" />
<input type="hidden" name="y1" value="" />
<input id="x2" type="hidden" name="x2" value="" />
<input id="y2" type="hidden" name="y2" value="" />
	
	<input id="idcard" name="pic" type="file" />
	<button onclick="a()">上传照片</button>
&nbsp;&nbsp;&nbsp;&nbsp;<html:reset value="重置" />
</html:form>
<div id="inchdiv">
	<img id="idImg" src=""></img>
</div>
<script type="text/javascript"> 
	var ip = new ImagePreview($$("idcard"), $$("idImg"), {
		maxWidth:0, maxHeight:0, action: "viewImg.jsp"});
		ip.img.src = ImagePreview.TRANSPARENT;
	ip.file.onchange = function(){ip.preview();}; 
</script>
<logic:present name="str" scope="request">
<script type="text/javascript">
var parent = window.dialogArguments;
var a ='<bean:write name="str"/>';
var pname='Z:\\pic\\'+a;
alert(pname);
parent.perImg(pname);
window.close();
</script>
</logic:present>
</html:html>