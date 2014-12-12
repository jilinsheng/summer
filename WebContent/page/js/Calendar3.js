
document.write("<DIV id='cc' style='BORDER-RIGHT: #000000 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: #000000 1px solid; PADDING-LEFT: 0px; Z-INDEX: 2; BACKGROUND: #ffffff; FILTER: Alpha(opacity=85); LEFT: 505px; VISIBILITY: hidden; PADDING-BOTTOM: 0px; BORDER-LEFT: #000000 1px solid; WIDTH: 170px; LINE-HEIGHT: 22px; PADDING-TOP: 0px; BORDER-BOTTOM: #000000 1px solid; POSITION: absolute; TOP: 419px; HEIGHT: 60px'></DIV>");
document.write("<style>td {font-family: Arial, Helvetica, sans-serif;font-size: 10px;}table{border-collapse:collapse} .sel{font-family: Webdings;font-size: 9pt;font-weight: bold;color: #243F65;cursor:hand;text-decoration: none; background-color:DEDEEF}body {margin-left:0px;margin-top: 0px;}</style>");
var name;
function dateSelect(objname) {
	cc.style.visibility = "visible";
	cc.style.filter = "Alpha(opacity=85)";
	cc.style.left = document.body.scrollLeft + window.event.clientX + 2;
	cc.style.top = document.body.scrollTop + window.event.clientY + 2;
	name = objname;
	showSelect();
}
function hiddeninfo() {
	cc.style.visibility = "hidden";
}
function RunNian(The_Year) {
	if ((The_Year % 400 == 0) || ((The_Year % 4 == 0) && (The_Year % 100 != 0))) {
		return true;
	} else {
		return false;
	}
}
function GetWeekday(The_Year, The_Month) {
	var Allday;
	Allday = 0;
	if (The_Year > 2000) {
		for (i = 2000; i < The_Year; i++) {
			if (RunNian(i)) {
				Allday += 366;
			} else {
				Allday += 365;
			}
		}
		for (i = 2; i <= The_Month; i++) {
			switch (i) {
			  case 2:
				if (RunNian(The_Year)) {
					Allday += 29;
				} else {
					Allday += 28;
				}
				break;
			  case 3:
				Allday += 31;
				break;
			  case 4:
				Allday += 30;
				break;
			  case 5:
				Allday += 31;
				break;
			  case 6:
				Allday += 30;
				break;
			  case 7:
				Allday += 31;
				break;
			  case 8:
				Allday += 31;
				break;
			  case 9:
				Allday += 30;
				break;
			  case 10:
				Allday += 31;
				break;
			  case 11:
				Allday += 30;
				break;
			  case 12:
				Allday += 31;
				break;
			}
		}
	}
	return (Allday + 6) % 7;
}
function chooseday(The_Year, The_Month, The_Day) {
	var Firstday;
	var completely_date;
	if (The_Day != 0) {
		completely_date = The_Year + "-" + The_Month + "-" + The_Day;
	} else {
		completely_date = "";
	}
    //showdate 只是一个为了显示而采用的东西，
    //如果外部想引用这里的时间，可以通过使用 completely_date引用完整日期
    //也可以通过The_Year,The_Month,The_Day分别引用年，月，日
    //当进行月份和年份的选择时，认为没有选择完整的日期
	Firstday = GetWeekday(The_Year, The_Month);
	ShowCalender(The_Year, The_Month, The_Day, Firstday);
}
function chooses(The_Year, The_Month, The_Day) {
	var obj2 = document.getElementById(name);
	var Firstday;
	var completely_date;
	if (The_Day != 0) {
		completely_date = The_Year + "-" + The_Month + "-" + The_Day;
	} else {
		completely_date = "";
	}
	obj2.value = completely_date;
	hiddeninfo();
}
function nextmonth(The_Year, The_Month) {
	if (The_Month == 12) {
		chooseday(The_Year + 1, 1, 0, name);
	} else {
		chooseday(The_Year, The_Month + 1, 0, name);
	}
}
function prevmonth(The_Year, The_Month) {
	if (The_Month == 1) {
		chooseday(The_Year - 1, 12, 0);
	} else {
		chooseday(The_Year, The_Month - 1, 0);
	}
}
function prevyear(The_Year, The_Month) {
	chooseday(The_Year - 1, The_Month, 0);
}
function nextyear(The_Year, The_Month) {
	chooseday(The_Year + 1, The_Month, 0);
}
function ShowCalender(The_Year, The_Month, The_Day, Firstday) {
	var ycdate = "";
	var showstr;
	var Month_Day;
	var ShowMonth;
	var today;
	today = new Date();
	switch (The_Month) {
	  case 1:
		ShowMonth = "1\u6708";
		Month_Day = 31;
		break;
	  case 2:
		ShowMonth = "2\u6708";
		if (RunNian(The_Year)) {
			Month_Day = 29;
		} else {
			Month_Day = 28;
		}
		break;
	  case 3:
		ShowMonth = "3\u6708";
		Month_Day = 31;
		break;
	  case 4:
		ShowMonth = "4\u6708";
		Month_Day = 30;
		break;
	  case 5:
		ShowMonth = "5\u6708";
		Month_Day = 31;
		break;
	  case 6:
		ShowMonth = "6\u6708";
		Month_Day = 30;
		break;
	  case 7:
		ShowMonth = "7\u6708";
		Month_Day = 31;
		break;
	  case 8:
		ShowMonth = "8\u6708";
		Month_Day = 31;
		break;
	  case 9:
		ShowMonth = "9\u6708";
		Month_Day = 30;
		break;
	  case 10:
		ShowMonth = "10\u6708";
		Month_Day = 31;
		break;
	  case 11:
		ShowMonth = "11\u6708";
		Month_Day = 30;
		break;
	  case 12:
		ShowMonth = "12\u6708";
		Month_Day = 31;
		break;
	}
	showstr = "";
	showstr = "<Table cellpadding=0 cellspacing=0 border=1 bordercolor=#C0D0E8 width=95% align=center valign=top>"; //上边框颜色
	showstr += "<tr><td width=0 class='sel' onclick=prevyear(" + The_Year + "," + The_Month + ")>3</td><td width=0>   " + The_Year + "\u5e74 </td><td width=0 onclick=nextyear(" + The_Year + "," + The_Month + ") class='sel'>4</td><td width=0 class='sel' onclick=prevmonth(" + The_Year + "," + The_Month + ")>3</td><td width=40 align=center>" + ShowMonth + "</td><td width=0 onclick=nextmonth(" + The_Year + "," + The_Month + ") class='sel'>4</td><td><div  align='center' onclick='hiddeninfo()' style='cursor:hand'>\u8fd4\u56de</div></td></tr>";
	showstr += "<tr><td align=center width=100% colspan=7>";
	showstr += "<table cellpadding=0 cellspacing=0 border=1 bordercolor=#C0D0E8 width=100%>";//内边框颜色
	showstr += "<Tr align=center bgcolor=#C0D0E8> ";//星期td背景
	showstr += "<td><strong><font color=#ff0000>\u65e5</font></strong></td>";
	showstr += "<td><strong><font color=#000000>\u4e00</font></strong></td>";
	showstr += "<td><strong><font color=#000000>\u4e8c</font></strong></td>";
	showstr += "<td><strong><font color=#000000>\u4e09</font></strong></td>";
	showstr += "<td><strong><font color=#000000>\u56db</font></strong></td>";
	showstr += "<td><strong><font color=#000000>\u4e94</font></strong></td>";
	showstr += "<td><strong><font color=#ff0000>\u516d</font></strong></td>";
	showstr += "</Tr><tr>";
	for (i = 1; i <= Firstday; i++) {
		showstr += "<Td align=center bgcolor=#ffffff> </Td>";
	}//月前空日日期背景
	for (i = 1; i <= Month_Day; i++) {
		if ((The_Year == today.getYear()) && (The_Month == today.getMonth() + 1) && (i == today.getDate())) {
			bgColor = "#DEDEEF";//当前日期td颜色
			ycdate = "<b><font color='#ff0000'>" + i + "</font></b>";
		} else {
			bgColor = "#DEDEEF";//大部分td颜色
			ycdate = i + "";
		}
		if (The_Day == i) {
			bgColor = "#ffffff";
		}//选择日期td颜色
		showstr += "<td align=center bgcolor=" + bgColor + " style='cursor:hand' onclick=chooses(" + The_Year + "," + The_Month + "," + i + ")>" + ycdate + "</td>";
		Firstday = (Firstday + 1) % 7;
		if ((Firstday == 0) && (i != Month_Day)) {
			showstr += "</tr><tr>";
		}
	}
	if (Firstday != 0) {
		for (i = Firstday; i < 7; i++) {
			showstr += "<td align=center bgcolor=#ffffff> </td>";
		}//月尾空日td背景
		showstr += "</tr>";
	}
	//showstr += "</tr></table></td></tr><tr><td colspan='7'><div  align='center' onclick='hiddeninfo()' style='cursor:hand'>\u8fd4\u56de</div></td></tr></table>";
	cc.innerHTML = showstr;
}
function showSelect(name) {
	var The_Year, The_Day, The_Month;
	var today;
	var Firstday;
	today = new Date();
	The_Year = today.getYear();
	The_Month = today.getMonth() + 1;
	The_Day = today.getDate();
	Firstday = GetWeekday(The_Year, The_Month);
	ShowCalender(The_Year, The_Month, The_Day, Firstday);
}

