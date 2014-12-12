<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>调整菜单顺序</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
	</head>

	<body>
		<br />
		<form action="menuchildsave.do" method="post">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="0" class="table7">
				<caption>
					调整菜单顺序
				</caption>
				<tr>
					<td>
						<select name="hasitem" multiple style="width: 100%" size="20">
							<c:forEach items="${list}" var="rs">
								<option value="<c:out value="${rs.privilegeId}" />,<c:out value="${rs.sequence}" />">
									<c:out value="${rs.displayname}"></c:out>
								</option>
							</c:forEach>
						</select>
					</td>
					<th width="40">
						<input name="buttong_04" type="button" value="上移" class="btn"
							onClick="moveup();">
						<br>
						<br>
						<br>
						<input name="buttong_05" type="button" value="下移" class="btn"
							onClick="movedown();">
					</th>
				</tr>
			</table>
			<p align="center">
				<button class="btn" onclick="save()">
					保存
				</button>
				<input type="hidden" name="xmlstr" value="" />
				<input type="hidden" name="pid" value="${privilege.privilegeId}" />
				<input type="hidden" name="sequence" value="${privilege.sequence}" />
			</p>
		</form>
	</body>
</html>
<script type="text/javascript">
	function moveup() {
		var select_body = document.forms[0].hasitem;
		if (select_body.selectedIndex != -1) {
			var flag = select_body.selectedIndex;
			if (flag == 0) {
				alert("已经是第一个");
				return;
			}
			for ( var i = 0; i < select_body.length; i++) {
				if (select_body.options[i].selected) {
					var nOptionu = document.createElement("option");
					var nOptiond = document.createElement("option");
					nOptionu.text = select_body.options[i].text;
					nOptionu.value = select_body.options[i].value;
					nOptiond.text = select_body.options[i - 1].text;
					nOptiond.value = select_body.options[i - 1].value;
					select_body.remove(i);
					select_body.remove(i - 1);
					document.forms[0].hasitem.add(nOptionu, i - 1);
					document.forms[0].hasitem.add(nOptiond, i);
					select_body.options[i - 1].selected = true;
				}
			}
		} else
			alert("请选择一个项目");
	}

	function movedown() {
		var select_body = document.forms[0].hasitem;
		if (select_body.selectedIndex != -1) {
			var flag = select_body.options.length;
			if (select_body.selectedIndex == flag - 1) {
				alert("已经是最后位置");
				return;
			}
			for ( var i = 0; i < select_body.length; i++) {
				if (select_body.options[i].selected) {
					var nOptionu = document.createElement("OPTION");
					var nOptiond = document.createElement("OPTION");
					nOptiond.text = select_body.options[i].text;
					nOptiond.value = select_body.options[i].value;
					nOptionu.text = select_body.options[i + 1].text;
					nOptionu.value = select_body.options[i + 1].value;
					select_body.remove(i);
					document.forms[0].hasitem.add(nOptiond, i + 1);
					select_body.options[i + 1].selected = true;
					break;
				}
			}
		} else
			alert("请选择一个项目");
	}
	function save() {
		var xmlstr = "<root>";
		var select_body = document.forms[0].hasitem;
		var flag = select_body.options.length;
		alert(flag);
		for ( var i = 0; i < flag; i++) {
			xmlstr = xmlstr + "<item seq=\"" + i + "\" >"
					+ select_body.options[i].value + "</item>";
		}
		xmlstr = xmlstr + "</root>";
		document.forms[0].xmlstr.value = xmlstr;
		document.forms[0].submit();
	}
</script>