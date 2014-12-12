<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>欢迎页面</title>
	<style type="text/css">
body {
	margin: 0;
	padding: 0;
	font-family: "宋体";
	font-size: 12px;
}

.leftstyle {
	background: url(images/newsleft.gif) no-repeat;
	background-position: left;
	width: 140px;
	height: 25px;
}

.rightstyle {
	background: url(images/newsright.gif) no-repeat;
	background-position: right;
	height: 25px;
}

.centerstyle {
	background: url(images/newscenter.gif);
	background-repeat: repeat;
}

.borderstyle {
	border-width: 0px 1px 1px 1px;
	border-style: solid;
	border-color: #C2523A;
}

.captionstyle {
	background: url(images/leftbtn.gif) no-repeat;
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
	background-image: url(images/index_left1_46.gif);
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
	<script type="text/javascript">
var months = new Array("一", "二", "三","四", "五", "六", "七", "八", "九","十", "十一", "十二");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31,30, 31, 30, 31);
var days = new Array("日","一", "二", "三","四", "五", "六");
var classTemp;
var today=new getToday();
var year=today.year;
var month=today.month;
var newCal; 

function getDays(month, year) {
if (1 == month) return ((0 == year % 4) && (0 != (year % 100))) ||(0 == year % 400) ? 29 : 28;
else return daysInMonth[month];
}

function getToday() {
this.now = new Date();
this.year = this.now.getFullYear();
this.month = this.now.getMonth();
this.day = this.now.getDate();
}

function Calendar() {
newCal = new Date(year,month,1);
today = new getToday();   
var day = -1;
var startDay = newCal.getDay();
var endDay=getDays(newCal.getMonth(), newCal.getFullYear());
var daily = 0;
if ((today.year == newCal.getFullYear()) &&(today.month == newCal.getMonth()))
{
  day = today.day;
}
var caltable = document.all.caltable.tBodies.calendar;
var intDaysInMonth =getDays(newCal.getMonth(), newCal.getFullYear());

for (var intWeek = 0; intWeek < caltable.rows.length;intWeek++)
  for (var intDay = 0;intDay < caltable.rows[intWeek].cells.length;intDay++)
  {
  var cell = caltable.rows[intWeek].cells[intDay];
  var montemp=(newCal.getMonth()+1)<10?("0"+(newCal.getMonth()+1)):(newCal.getMonth()+1);       
  if ((intDay == startDay) && (0 == daily)){ daily = 1;}
  var daytemp=daily<10?("0"+daily):(daily);
  var d="<"+newCal.getFullYear()+"-"+montemp+"-"+daytemp+">";
  if(day==daily) cell.className="DayNow";
  else if(intDay==6) cell.className = "DaySat";
  else if (intDay==0) cell.className ="DaySun";
  else cell.className="Day";
  if ((daily > 0) && (daily <= intDaysInMonth))
  {
  cell.innerText = daily;
  daily++;
  } else
  {
  cell.className="CalendarTD";
  cell.innerText = "";
  }
}
document.all.year.value=year;
document.all.month.value=month+1;
}

function subMonth()
{
if ((month-1)<0)
{
  month=11;
  year=year-1;
} else
{
  month=month-1;
}
Calendar();
}

function addMonth()
{
if((month+1)>11)
{
  month=0;
  year=year+1;
} else
{
  month=month+1;
}
Calendar();
}

function setDate() 
{
if (document.all.month.value<1||document.all.month.value>12)
{
  alert("月的有效范围在1-12之间!");
  return;
}
year=Math.ceil(document.all.year.value);
month=Math.ceil(document.all.month.value-1);
Calendar();
}
</script>
	<script type="text/javascript">
function buttonOver()
{
var obj = window.event.srcElement;
obj.runtimeStyle.cssText = "background-color:#FFFFFF";
// obj.className="Hover";
}

function buttonOut()
{
var obj = window.event.srcElement;
window.setTimeout(function(){obj.runtimeStyle.cssText = "";},300);
}
</script>
	<style type="text/css">
Input {
	font-family: verdana;
	font-size: 9pt;
	text-decoration: none;
	background-color: #FFFFFF;
	height: 20px;
	border: 1px solid #666666;
	color: #000000;
}

.Calendar {
	font-family: verdana;
	text-decoration: none;
	width: 280;
	background-color: #C0D0E8;
	font-size: 9pt;
	border: 0px dotted #1C6FA5;
}

.CalendarTD {
	font-family: verdana;
	font-size: 7pt;
	color: #000000;
	background-color: #f6f6f6;
	height: 20px;
	width: 11%;
	text-align: center;
}

.Title {
	font-family: verdana;
	font-size: 11pt;
	font-weight: normal;
	height: 24px;
	text-align: center;
	color: #333333;
	text-decoration: none;
	background-color: #A4B9D7;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-bottom-style: 1px;
	border-top-color: #999999;
	border-right-color: #999999;
	border-bottom-color: #999999;
	border-left-color: #999999;
}

.Day {
	font-family: verdana;
	font-size: 7pt;
	color: #243F65;
	background-color: #E5E9F2;
	height: 20px;
	width: 11%;
	text-align: center;
}

.DaySat {
	font-family: verdana;
	font-size: 7pt;
	color: #FF0000;
	text-decoration: none;
	background-color: #E5E9F2;
	text-align: center;
	height: 18px;
	width: 12%;
}

.DaySun {
	font-family: verdana;
	font-size: 7pt;
	color: #FF0000;
	text-decoration: none;
	background-color: #E5E9F2;
	text-align: center;
	height: 18px;
	width: 12%;
}

.DayNow {
	font-family: verdana;
	font-size: 7pt;
	font-weight: bold;
	color: #000000;
	background-color: #FFFFFF;
	height: 20px;
	text-align: center;
}

.DayTitle {
	font-family: verdana;
	font-size: 9pt;
	color: #000000;
	background-color: #C0D0E8;
	height: 20px;
	width: 11%;
	text-align: center;
}

.DaySatTitle {
	font-family: verdana;
	font-size: 9pt;
	color: #FF0000;
	text-decoration: none;
	background-color: #C0D0E8;
	text-align: center;
	height: 20px;
	width: 12%;
}

.DaySunTitle {
	font-family: verdana;
	font-size: 9pt;
	color: #FF0000;
	text-decoration: none;
	background-color: #C0D0E8;
	text-align: center;
	height: 20px;
	width: 12%;
}

.DayButton {
	font-family: Webdings;
	font-size: 9pt;
	font-weight: bold;
	color: #243F65;
	cursor: hand;
	text-decoration: none;
}
</style>
</head>
<body style="overflow: hidden">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr valign="top">
			<td id="tz" height="168">
				<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="486" class="leftstyle">
							<span class="captionstyle">通知&amp;公告</span>
						</td>
						<td width="60%" background="center.gif" class="centerstyle">
							<div class="rightstyle"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="borderstyle">
							<table width="100%" cellpadding="0" cellspacing="0">
								<c:out value="${requestScope.notciehtml}" escapeXml="false"></c:out>
								<tr height="19">
									<td height="19" align="right">
										<a href="oa/noticedetail.do" class="more" target="_blank">更多...</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td id="c" width="280" align="center" valign="top">
				<table border="0" cellpadding="0" cellspacing="1" class="Calendar"
					id="caltable">
					<thead>
						<tr align="center" valign="middle">
							<td colspan="7" class="Title">
								<a href="javaScript:subMonth();" title="上一月" class="DayButton">3</a>
								<input name="year" type="text" size="4" maxlength="4"
									onkeydown="if (event.keyCode==13){setDate()}"
									onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
									onpaste="this.value=this.value.replace(/[^0-9]/g,'')" />
								年
								<input name="month" type="text" size="1" maxlength="2"
									onkeydown="if (event.keyCode == 13) {setDate()};" onkeyup="this.value = this.value.replace(/[^0-9]/g, '');" onpaste="this.value=this.value.replace(/[^0-9]/g,'')" />
								月
								<a href="JavaScript:addMonth();" title="下一月" class="DayButton">4</a>
							</td>
						</tr>
						<tr align="center" valign="middle">
							<script type="text/javascript">
	document.write("<td class=DaySunTitle id=diary >" + days[0] + "</td>");
	for ( var intLoop = 1; intLoop < days.length - 1; intLoop++)
		document
				.write("<td class=DayTitle id=diary>" + days[intLoop] + "</td>");
	document.write("<td class=DaySatTitle id=diary>" + days[intLoop] + "</td>");
</script>
						</tr>
					</thead>
					<tbody border="1 cellspacing=" 0" cellpadding="0" id="calendar"
						align="center">
						<script type="text/javascript">
	for ( var intWeeks = 0; intWeeks < 6; intWeeks++) {
		document.write("<tr style='cursor:hand'>");
		for ( var intDays = 0; intDays < days.length; intDays++)
			document
					.write("<td class=CalendarTD onMouseover='buttonOver();' onMouseOut='buttonOut();'></td>");
		document.write("</tr>");
	}
</script>
					</tbody>
				</table>
				<script type="text/javascript">
	Calendar();
</script>
			</td>
		</tr>
		<tr valign="top">
		<td>
			<table id="xf" width="100%" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td class="leftstyle">
							<span class="captionstyle">信息不合法数据</span>
						</td>
						<td width="60%" background="center.gif" class="centerstyle">
							<div class="rightstyle"></div>
						</td>
					</tr>
					<tr valign="top">
						<td colspan="2" class="borderstyle">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font:12px">
								<c:out value="${infohtml}" escapeXml="false"></c:out>
								<tr valign="bottom">
									<td height="25" colspan="3" align="right" class="more">
										<a target="_blank" href="../page/infovalidate/infovalidatequery.jsp">更多...&nbsp;&nbsp;</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td id="ssss" width="280" align="center" valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="leftstyle">
							<span class="captionstyle">系统相关下载</span>
						</td>
						<td width="60%" background="center.gif" class="centerstyle">
							<div class="rightstyle"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="borderstyle">
							<table  width="100%" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<td align="center">
										<img src="images/newsnet.png" alt="VPN客户端软件" width="24"
											height="24" />
									</td>
									<td class="more">
										VPN客户端软件
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newsprint.png" alt="报表打印控件" width="24"
											height="24" />
									</td>
									<td class="more">
										<a href="../software/view.exe" target="_blank">报表打印控件</a>
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newssoftware.png" alt="农村低保录入版" width="24"
											height="24" />
									</td>
									<td class="more">
										离线版低保软件
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newsie.png" alt="IE浏览器" width="24"
											height="24" />
									</td>
									<td class="more">
										IE7.0浏览器
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newskav.png" alt="卡巴斯基杀毒软件" width="24"
											height="24" />
									</td>
									<td class="more">
										卡巴斯基杀毒软件
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newrar.png" alt="解压缩软件" width="24"
											height="24" />
									</td>
									<td class="more">
										WinRAR解压缩软件
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newspic.png" alt="图片处理压缩软件" width="24"
											height="24" />
									</td>
									<td class="more">
										图片处理压缩软件
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newsmedia.png" alt="视频处理压缩软件" width="24"
											height="24" />
									</td>
									<td class="more">
										视频处理压缩软件
									</td>
								</tr>
								<tr>
									<td align="center">
										<img src="images/newshelp.png" alt="系统使用帮助手册" width="24"
											height="24" />
									</td>
									<td class="more">
										<a href="../software/help/index.html" target="_blank">系统使用帮助手册</a>
									</td>
								</tr>
								<tr>
									<td align="center">&nbsp;
									</td>
									<td class="more">
									&nbsp;
									</td>
								</tr>
								<tr>
									<td align="center">
										&nbsp;
									</td>
									<td class="more">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td></tr>
	</table>
</body>
</html:html>
<script>
	function sc() {
		if (document.body.offsetHeight - 380 > 0) {
			document.getElementById("xf").style.height = document.body.offsetHeight-250;
			document.getElementById("ssss").style.height = document.body.offsetHeight-250;
		} else {
		}
		;
	}
	setInterval(sc, 50);
</script>