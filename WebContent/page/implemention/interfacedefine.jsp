<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
		<title>�ӿڸ�ʽ����</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="../js/prototype-1.6.0.2.js"></script>
		<script type="text/javascript" src="../js/Calendar2.js"></script>

		<link rel="stylesheet" href="../css/style.css" type="text/css"></link>
		<style type="text/css">
<!--
#list {
	padding: 4px;
	border: 3px double #f5a89a;
}

#emp {
	padding: 4px;
	border: 3px double #f5a89a;
}

.legend {
	font-size: 12px;
	font-weight: lighter;
	color: #000000;
}

lable {
	width: 90px;
	text-align: center;
}
-->
</style>
	</head>
	<body>
		<br />
		<span>�Ѿ�����ӿڸ�ʽ
			<button class="btn" onclick="add('add')">
				�½�
			</button>
		</span>
		<table width="100%" cellspacing="0" cellpadding="0" class="table7">
			<tr>
				<th>
					��������
				</th>
				<th>
					�ƶ�ʱ��
				</th>
				<th>
					�ƶ���
				</th>
				<th>
					�ƶ���λ
				</th>
				<th>
					����
				</th>
			</tr>
			<c:forEach var="rs" items="${requestScope.interfacelist}">
				<tr>
					<td>
						<c:out value="${rs[1]}"></c:out>
					</td>
					<td>
						<c:out value="${rs[2]}"></c:out>
					</td>
					<td>
						<c:out value="${rs[5]}"></c:out>
					</td>
					<td>
						<c:out value="${rs[4]}"></c:out>
					</td>
					<td>
						<a href="#" onclick="add('<c:out value="${rs[0]}"></c:out>')">�༭</a>&nbsp;&nbsp;
						<a href="#" onclick="add('<c:out value="${rs[0]}"></c:out>')">ɾ��</a>

					</td>
				</tr>
			</c:forEach>
		</table>
		<form id="inputform" style="display:none">
			<div id="operzone">
				<fieldset id="list">
					<legend class="legend">
						����ӿ�
						<input id="pk" type="hidden" value="" />
					</legend>
					<div id="interfacezone">
						����ӿ����ͣ�(1)
						<input id="radioxls" type="radio" name="definetype" value="xls"
							onclick="initxmldoc('xls')" />
						EXCEL�ļ� &nbsp;&nbsp;(2)
						<input id="radioxml" type="radio" name="definetype" value="xml"
							onclick="initxmldoc('xml')" />
						XML�ļ� &nbsp;&nbsp;(3)
						<input id="radiotxt" type="radio" name="definetype" value="txt" onclick="initxmldoc('txt')"/>
						�ı��ļ�
						<br />
						����ӿ����ƣ�
						<input type="text" id="definename" name="definename" value="" />
						<br />
					</div>
					<div id="interfaceformtxt" style="display: none">
						<table>
							<tr id="trtxt" style="display: none">
								<td colspan="4">
									�ļ�ͷ��
									<input id="filehead" name="filehead" type="text" value=""
										size="12" onchange="previewtxt()"/>
									&nbsp;&nbsp;���ף�
									<input id="rowhead" name="rowhead" type="text" value=""
										size="12" onchange="previewtxt()"/>
									&nbsp;&nbsp;��β��
									<input id="rowend" name="rowend" type="text" value="" size="12" onchange="previewtxt()"/>
									&nbsp;&nbsp;�ļ�β��
									<input id="fileend" name="fileend" type="text" value=""
										size="12" onchange="previewtxt()"/>
								</td>
							</tr>
							<tr id="trxls" style="display: none">
								<td colspan="4">
									����
									<input id="xlscaption1" type="radio" name="caption" value="1" />
									��
									<input id="xlscaption2" type="radio" name="caption" value="0" />
									��
								</td>
							</tr>
							<tr id="trxml" style="display:none">
								<td colspan="4">
									���ڵ�
									<input id="rootelement" name="filehead" type="text" value=""
										size="12" onchange="previewtxt()"/>
									&nbsp;&nbsp;�нڵ�
									<input id="rowelement" name="rowhead" type="text" value=""
										size="12" onchange="previewtxt()"/>
									&nbsp;&nbsp;Ԫ�ؽڵ�
									<input id="element" name="rowend" type="text" value=""
										size="12" onchange="previewtxt()"/>
								</td>
							</tr>
							<tr>
								<td>
									�� ѡ �� �Σ�
									<select id="bxcol" name="column">
										<option value="">
											δѡ��...
										</option>
										<option value="FAMILYNO">
											��ͥ���
										</option>
										<option value="MASTERNAME">
											��������
										</option>
										<option value="PAPERID">
											֤����
										</option>
										<option value="POPULATION">
											�˿�
										</option>
										<option value="FAMILYADDRESS">
											��ͥסַ
										</option>
										<option value="ACCOUNTS">
											�ʺ�
										</option>
										<option value="BANKCODE">
											���д���
										</option>
										<option value="MONEY">
											���
										</option>
									</select>
								</td>
								<td>
									�ָ�����
									<SELECT id="fgcol" NAME="delimiter"
										onchange="viewdelimiters(this)">
										<option value="">
											δѡ��...
										</option>
										<option value="44">
											����
										</option>
										<option value="46">
											���
										</option>
										<option value="59">
											�ֺ�
										</option>
										<option value="32">
											�ո�
										</option>
										<option value="9">
											tab��
										</option>
										<option value="35">
											#
										</option>
										<option value="delimiters">
											�Զ���ָ���
										</option>
									</select>
								</td>
								<td>
									<span id="delimiters" style="display: none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
											id="zdyfg" name="delimiters" value="" type="text"
											maxlength="1" size="13" /> </span>
								</td>
								<td>
									<button class="btn" onclick="previewtxt()">
										��ӱ�ѡ�ֶ�
									</button>
									<button class="btn" onclick="cleantxt()">
										��ձ�ѡ�ֶ�
									</button>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									��ʽԤ��:
									<textarea rows="3" id="txt" style="width: 100%"></textarea>
								</td>
							</tr>
						</table>
					</div>
					<button class="btn" onclick="save()">
						�����ʽ
					</button>
					<span id="waiting" style="display: none"> ���ڶ�ȡ����...... </span>
				</fieldset>
			</div>
		</form>
	</body>
	<script type="text/javascript">
	var xmldoc;
	function save() {
		//priviewtxt();
		if (xmldoc == null) {
		} else {
			alert(xmldoc.xml);
			var url = '/db/page/implemention/interfacedefinesave.do?xmlstr=' + xmldoc.xml;
			var pars = '';
			$('waiting').innerHTML = "���ڶ�ȡ����......";
			$('waiting').style.display = "block";
			var myAjax = new Ajax.Request(url, {
				method :'post',
				parameters :pars,
				onComplete :showresultsave
			});
		}
	}
	function del() {
	}
	function add(id) {
		var url = '/db/page/implemention/interfacedefineview.do?id='+id;
		var pars="";
		$('waiting').innerHTML = "���ڶ�ȡ����......";
		$('waiting').style.display = "block";
		var myAjax = new Ajax.Request(url, {
			method :'post',
			parameters :pars,
			onComplete :showresultadd
		});
	}
	var ctype='';
	function initxmldoc(type) {
		$('filehead').value = '';
		$('fileend').value = '';
		$('rowhead').value = '';
		$('rowend').value = '';
		$('txt').value = '';
		xmldoc = null;
		ctype = type;
		if ('txt' == type) {
			$('trtxt').style.display = 'block';
			$('trxml').style.display = 'none';
			$('trxls').style.display = 'none';
			$('interfaceformtxt').style.display = 'block';
		}
		if ('xls' == type) {
			$('trtxt').style.display = 'none';
			$('trxml').style.display = 'none';
			$('trxls').style.display = 'block';
			$('interfaceformtxt').style.display = 'block';
		}
		if ('xml' == type) {
			$('trtxt').style.display = 'none';
			$('trxml').style.display = 'block';
			$('trxls').style.display = 'none';
			$('interfaceformtxt').style.display = 'block';
		}
	}
	function showresultadd(response) {
		//$('employee').innerHTML = response.responseText;
		var resultstr= response.responseText;
		alert(response.responseText);
		if(0==resultstr){
			alert("aaaaaaaaaaaaaaaa");
			$('trtxt').style.display = 'none';
			$('trxml').style.display = 'none';
			$('trxls').style.display = 'none';
			$('inputform').style.display='block';
			$('interfaceformtxt').style.display = 'none';
			alert("aaaaaaaaaaaaaaaa");
			$('waiting').style.display = "none";
			$('pk').value='';
			$('definename').value='';
			$('radiotxt').checked=false;
			$('radioxls').checked=false;
			$('radioxml').checked=false;
		}else{
			$('inputform').style.display='block';
			$('interfaceformtxt').style.display = 'block';
			xmldoc = new ActiveXObject('Microsoft.XMLDOM');
			xmldoc.loadXML(response.responseText);
			var rootelement = xmldoc.documentElement;
			var attrs = rootelement.attributes;
			var pk =attrs[0].value;
			var id=attrs[1].value;
			var title=attrs[2].value;
			alert(pk +"   " + id +"   " + title);
			$('definename').value=title;
			$('pk').value=pk;
			if('txt'==id){
				alert(id);
				var filehead='';
				if(null==xmldoc.selectSingleNode("/document/filehead").firstChild){
					filehead='';
				}else {
					filehead= xmldoc.selectSingleNode("/document/filehead").firstChild.nodeValue;
				}
				//var filehead= xmldoc.selectSingleNode("/document/filehead").firstChild.nodeValue;
				var fileend= '';
				if(null==xmldoc.selectSingleNode("/document/fileend").firstChild){
					fileend='';
				}else {
					fileend= xmldoc.selectSingleNode("/document/fileend").firstChild.nodeValue;
				}
				//
				var fileend= '';
				if(null==xmldoc.selectSingleNode("/document/fileend").firstChild){
					fileend='';
				}else {
					fileend= xmldoc.selectSingleNode("/document/fileend").firstChild.nodeValue;
				}
				//
				var rowhead= '';
				if(null==xmldoc.selectSingleNode("/document/rowhead").firstChild){
					rowhead='';
				}else {
					rowhead= xmldoc.selectSingleNode("/document/rowhead").firstChild.nodeValue;
				}
				//
				var rowend= '';
				if(null==xmldoc.selectSingleNode("/document/rowend").firstChild){
					rowend='';
				}else {
					rowend= xmldoc.selectSingleNode("/document/rowend").firstChild.nodeValue;
				}
				//var rowend= xmldoc.selectSingleNode("/document/rowend").firstChild.nodeValue;
				alert( filehead +""+fileend);
				$('radiotxt').checked=true;
				$('trtxt').style.display = 'block';
				$('trxml').style.display = 'none';
				$('trxls').style.display = 'none';
				$('filehead').value=filehead;
				$('fileend').value=fileend;
				$('rowhead').value=rowhead;
				$('rowend').value=rowend;
			}
			if('xml'==id){
				$('radioxls').checked=true;
				$('trtxt').style.display = 'none';
				$('trxml').style.display = 'block';
				$('trxls').style.display = 'none';
				var rootelement='';
				var rowelement='';
				var element= '';
				if(null ==xmldoc.selectSingleNode("/document/rootelement").firstChild){
				}else{
					rootelement= xmldoc.selectSingleNode("/document/rootelement").firstChild.nodeValue;
				}
				if(null ==xmldoc.selectSingleNode("/document/rowelement").firstChild){
				}else{
					rowelement= xmldoc.selectSingleNode("/document/rowelement").firstChild.nodeValue;
				}
				if(null ==xmldoc.selectSingleNode("/document/element").firstChild){
				}else{
					element= xmldoc.selectSingleNode("/document/element").firstChild.nodeValue;
				}
				//var rootelement= xmldoc.selectSingleNode("/document/rootelement").firstChild.nodeValue;
				//var rowelement= xmldoc.selectSingleNode("/document/rowelement").firstChild.nodeValue;
				//var element= xmldoc.selectSingleNode("/document/element").firstChild.nodeValue;
				$('rootelement').value=rootelement;
				$('rowelement').value=rowelement;
				$('element').value=element;
			}
			if('xls'==id){
				$('radioxml').checked=true;
				$('trtxt').style.display = 'none';
				$('trxml').style.display = 'none';
				$('trxls').style.display = 'block';
				var xls='';
				if(null==xmldoc.selectSingleNode("/document/xls").firstChild){
				}else{
					xls= xmldoc.selectSingleNode("/document/xls").firstChild.nodeValue;
					}
				if ('1'==xls) {
					$('xlscaption1').checked=true;
				}
				if ('0'==xls) {
					$('xlscaption2').checked=true;
				}
			}
			var viewtemp = "";
			var oNodes = xmldoc.selectNodes("/document/column");
			if ($('filehead').value == "") {
			} else {
				viewtemp = $('filehead').value + "\r\n"
			}
			viewtemp = viewtemp + $('rowhead').value
			for ( var i = 0; i < oNodes.length; i++) {
				var node = oNodes[i];
				var noders = node.attributes;
				var ch = noders[0].value;
				var fh = '';
				if ('' == noders[1].value) {

				} else {
					fh = String.fromCharCode(noders[1].value);
				}
				viewtemp = viewtemp + node.firstChild.nodeValue + fh;
			}
			viewtemp = viewtemp + $('rowend').value;
			if ($('fileend').value == "") {

			} else {
				viewtemp = viewtemp + "\r\n" + $('fileend').value;
			}
			$('txt').value = viewtemp;
			$('waiting').style.display = "none";		
		}	
		
	}
	function showresultsave(response) {
		//$('employee').innerHTML = response.responseText;
		$('waiting').style.display = "none";
	}
	function previewtxt() {
		var columns = new Array();
		if (xmldoc == null) {
			alert('����');
			xmldoc = new ActiveXObject('Microsoft.XMLDOM');
			xmldoc.async = false;
		} else {
			alert('��������');
			columns = xmldoc.selectNodes("/document/column");
			columns.length;
		}
		var filetype= ctype;
		if($('radiotxt').checked){
			filetype='txt';
		}
		if($('radioxml').checked){
			filetype='xml';
		}
		if($('radioxls').checked){
			filetype='xls';
		}
		var xmlhead = '<?xml version="1.0" encoding="UTF-8" ?>';
		var xmltitle = '<document pk="' + $('pk').value + '" id="'
				+ filetype + '" title="' + $('definename').value + '">';
		var xmlfoot = '</document>';
		var tempstr = '';
		var filehead = '';
		var fileend = '';
		var rowhead = ''
		var rowend = '';
		if ('txt' == filetype) {
			filehead = '<filehead>' + $('filehead').value + '</filehead>';
			fileend = '<fileend>' + $('fileend').value + '</fileend>';
			rowhead = '<rowhead>' + $('rowhead').value + '</rowhead>';
			rowend = '<rowend>' + $('rowend').value + '</rowend>';
		}
		if ('xml' == filetype) {
			filehead = '<rootelement>' + $('rootelement').value + '</rootelement>';
			fileend = '<rowelement>' + $('rowelement').value + '</rowelement>';
			rowhead = '<element>' + $('element').value + '</element>';
		}
		if ('xls' == filetype) {
			var xlscaption = '';
			if ($('xlscaption1').checked) {
				xlscaption = $('xlscaption1').value;
			}
			if ($('xlscaption2').checked) {
				xlscaption = $('xlscaption2').value;
			}
			filehead = '<xls>' + xlscaption + '</xls>';
		}
		xmldoc.loadXML(xmlhead + xmltitle + filehead + fileend + rowhead
				+ rowend + xmlfoot);
		//���ԭ�е��ֶ���Ϣ
		for ( var i = 0; i < columns.length; i++) {
			if (xmldoc.documentElement == null) {
			} else {
				xmldoc.documentElement.appendChild(columns.item(i));
			}
		}
		//��ǰѡ����ֶ�
		var oSelect = $('bxcol');
		var oDelimiter = $('fgcol').value;
		if ('' == oDelimiter) {
		} else {
			if (oDelimiter == 'delimiters') {
				oDelimiter = $('zdyfg').value;
				oDelimiter = oDelimiter.charCodeAt(0);
			}
		}
		var vtext = oSelect.options[oSelect.selectedIndex].text;

		var col = xmldoc.createElement("column");//�ֶ�
		col.text = vtext;
		var r1 = xmldoc.createAttribute("value"); //��������
		r1.value = oSelect.value;
		col.setAttributeNode(r1)
		var r2 = xmldoc.createAttribute("delimiter"); //��������
		r2.value = oDelimiter;
		col.setAttributeNode(r2)
		xmldoc.documentElement.appendChild(col);
		alert(xmldoc.xml);
		var viewtemp = "";
		var oNodes = xmldoc.selectNodes("/document/column");
		if ($('filehead').value == "") {
		} else {
			viewtemp = $('filehead').value + "\r\n"
		}
		viewtemp = viewtemp + $('rowhead').value
		for ( var i = 0; i < oNodes.length; i++) {
			var node = oNodes[i];
			var noders = node.attributes;
			var ch = noders[0].value;
			var fh = '';
			if ('' == noders[1].value) {

			} else {
				fh = String.fromCharCode(noders[1].value);
			}
			viewtemp = viewtemp + node.firstChild.nodeValue + fh;
		}
		viewtemp = viewtemp + $('rowend').value;
		if ($('fileend').value == "") {

		} else {
			viewtemp = viewtemp + "\r\n" + $('fileend').value;
		}
		$('txt').value = viewtemp;
	}
	function viewdelimiters(obj) {
		var v = obj.options[obj.selectedIndex].value;
		if (v == 'delimiters') {
			$('delimiters').style.display = 'block';
		} else {
			$('delimiters').style.display = 'none';
		}
	}
	function cleantxt() {
		if (xmldoc == null) {
			alert('û�������Ϣ');
		} else {
			var columns = xmldoc.selectNodes("/document/column");
			for ( var i = 0; i < columns.length; i++) {
				if (xmldoc.documentElement == null) {
				} else {
					xmldoc.documentElement.removeChild(columns.item(i));
				}
			}
		}
		$('txt').value = "";
	}
</script>
</html>