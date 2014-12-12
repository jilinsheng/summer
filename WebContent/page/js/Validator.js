/**
 * �������ƣ�isNull ���ܣ��ǿ���֤ ����: ����ֵ��true(ͨ��)��false(ʧ��)
 */
function isNull(str) {
	str = str.replace(/^\s+|\s+$/g, "");
	if (str == "") {
		return true;
	} else {
		return false;
	}
}
function isDate(op, formatString) {
	formatString = formatString || "ymd";
	var m, year, month, day;
	switch (formatString) {
	case "ymd":
		m = op.match(new RegExp(
				"^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
		if (m == null) {
			return false;
		}
		day = m[6];
		month = m[5] * 1;
		year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
		break;
	case "dmy":
		m = op.match(new RegExp(
				"^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
		if (m == null) {
			return false;
		}
		day = m[1];
		month = m[3] * 1;
		year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
		break;
	default:
		break;
	}
	if (!parseInt(month)) {
		return false;
	}
	month = month == 0 ? 12 : month;
	var date = new Date(year, month - 1, day);
	return (typeof (date) == "object" && year == date.getFullYear()
			&& month == (date.getMonth() + 1) && day == date.getDate());
	function GetFullYear(y) {
		return ((y < 30 ? "20" : "19") + y) | 0;
	}
}
/**
 * �������ƣ�CheckText ���ܣ��ı��������֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ lenlimit �ִ������޶�
 * ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckText(valname, val, mode, lenlimit) {
	var vkeyWords = new RegExp("^(\\w|[\u4e00-\u9fa5]|[\\s]||[-])+$");
	var str = new String(val.value);
	if (mode && isNull(str)) {
		alert(valname + "\u4e0d\u80fd\u4e3a\u7a7a!");
		val.focus();
		val.select();
		return false;
	}
	if (isNull(str)) {
		return true;
	}
	if (lenlimit > 0 && str.length > lenlimit) {
		alert(valname + "\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + lenlimit
				+ "\u4e2a\u5b57\u7b26!");
		val.focus();
		val.select();
		return false;
	}
	if (!vkeyWords.test(str)) {
		alert(valname + "\u4e0d\u80fd\u5305\u62ec\u7279\u6b8a\u5b57\u7b26!");
		val.focus();
		val.select();
		return false;
	}
	return true;
}
/**
 * �������ƣ�CheckSelect ���ܣ������б��ѡ��֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ
 * ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckSelect(valname, val, mode) {
	var str = new String(val.value);
	if (mode && isNull(str)) {
		alert(valname + "\u4e0d\u80fd\u4e3a\u7a7a!");
		val.focus();
		val.select();
		return false;
	}
	if (val.value == "\u672a\u9009\u62e9") {
		alert(valname + "\u6ca1\u6709\u9009\u62e9!");
		val.focus();
		val.select();
		return false;
	}
	return true;
}
/**
 * �������ƣ�CheckRadio ���ܣ���ѡ��ť��ѡ��֤ ����:valname �ؼ����� val �ؼ� ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckRadio(valname, val) {
	var rdlen = val.length;
	var rdval = false;
	if (rdlen == undefined) {
		if (val.checked) {
			rdval = val.value;
		}
	} else {
		for (i = 0; i < rdlen; i++) {
			if (val[i].checked) {
				rdval = val[i].value;
				break;
			}
		}
	}
	if (rdval == "") {
		alert(valname + "\u6ca1\u6709\u9009\u62e9!");
		return false;
	}
	return true;
}
/**
 * �������ƣ�CheckChinese ���ܣ�������֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ lenlimit �ִ������޶�
 * ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckChinese(valname, val, mode, lenlimit) {
	var vkeyWords = new RegExp("[^\u4e00-\u9fa5]");
	var str = new String(val.value);
	if (mode && isNull(str)) {
		alert(valname + "\u4e0d\u80fd\u4e3a\u7a7a!");
		val.focus();
		val.select();
		return false;
	}
	if (isNull(str)) {
		return true;
	}
	if (lenlimit > 0 && str.length > lenlimit) {
		alert(valname + "\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + lenlimit
				+ "\u4e2a\u5b57\u7b26!");
		val.focus();
		val.select();
		return false;
	}
	if (vkeyWords.test(str)) {
		alert(valname + "\u53ea\u80fd\u8f93\u5165\u4e2d\u6587!");
		val.focus();
		val.select();
		return false;
	}
	return true;
}
/**
 * �������ƣ�CheckNumber ���ܣ���ֵ��֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ
 * ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckNumber(valname, val, mode) {
	var vkeyWords = new RegExp("^(-?\\d+)(\\.\\d+)?$");
	var str = new String(val.value);
	if (mode && isNull(str)) {
		alert(valname + "\u4e0d\u80fd\u4e3a\u7a7a!");
		val.focus();
		val.select();
		return false;
	}
	if (isNull(str)) {
		return true;
	}
	if (vkeyWords.test(str)) {
		return true;
	} else {
		alert(valname + "\u4e0d\u662f\u6570\u503c\u7c7b\u578b!");
		val.focus();
		val.select();
		return false;
	}
}
/**
 * �������ƣ�CheckDate ���ܣ�������֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ
 * ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckDate(valname, val, mode) {
	var str = new String(val.value);
	if (mode && isNull(str)) {
		alert(valname + "\u4e0d\u80fd\u4e3a\u7a7a!");
		val.focus();
		val.select();
		return false;
	}
	var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	var iaDate = new Array(3);
	var year, month, day;
	iaDate = str.toString().split("-");
	if (iaDate.length != 3) {
		alert(valname + "\u65e5\u671f\u683c\u5f0f\u4e0d\u6b63\u786e!");
		val.focus();
		val.select();
		return false;
	}
	if (iaDate[1].length > 2 || iaDate[2].length > 2) {
		alert(valname
				+ "\u4e2d\u6708\u4efd\u6216\u65e5\u671f\u4f4d\u6570\u4e0d\u6b63\u786e");
		val.focus();
		val.select();
		return false;
	}
	year = parseFloat(iaDate[0]);
	month = parseFloat(iaDate[1]);
	day = parseFloat(iaDate[2]);
	if (year < 1900 || year > 2100) {
		alert(valname + "\u4e2d\u5e74\u4efd\u586b\u5199\u4e0d\u6b63\u786e!");
		val.focus();
		val.select();
		return false;
	}
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
		iaMonthDays[1] = 29;
	}
	if (month < 1 || month > 12) {
		alert(valname + "\u4e2d\u6708\u4efd\u586b\u5199\u4e0d\u6b63\u786e!");
		val.focus();
		val.select();
		return false;
	}
	if (day < 1 || day > iaMonthDays[month - 1]) {
		alert(valname + "\u4e2d\u65e5\u671f\u586b\u5199\u4e0d\u6b63\u786e!");
		val.focus();
		val.select();
		return false;
	}
	return true;
}
/**
 * �������ƣ�CheckIdCard ���ܣ����֤��֤ ����:valname �ؼ����� val �ؼ� ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckIdCard(valname, val) {
	var _id = val.value;
	//alert(_id);
	if (_id == "") {
		return false;
	}
	var _valid = false;
	if (_id.length == 15) {
		_valid = validId15(_id);
	} else if (_id.length == 18) {
		_valid = validId18(_id);
	} else {
		_valid = false;
	}
	//alert(_valid);
	if (!_valid) {
		alert("���֤��������,����!");
		val.focus();
		return false;
	} else {
		return true;
	}
}
// У��18λ�����֤����

function validId18(_id) {
	var powers = new Array("7", "9", "10", "5", "8", "4", "2", "1", "6", "3",
			"7", "9", "10", "5", "8", "4", "2");
	var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
			"2");
	_id = _id + "";
	var _num = _id.substr(0, 17);
	var _parityBit = _id.substr(17);
	var _power = 0;
	for ( var i = 0; i < 17; i++) {
		// У��ÿһλ�ĺϷ���
		if (_num.charAt(i) < '0' || _num.charAt(i) > '9') {
			return false;
			break;
		} else {
			// ��Ȩ
			_power += parseInt(_num.charAt(i)) * parseInt(powers[i]);
		}
	}
	// ȡģ

	var mod = parseInt(_power) % 11;
	if (parityBit[mod] == _parityBit) {
		return true;
	}
	return false;
}
// У��15λ�����֤����

function validId15(_id) {
	var powers = new Array("7", "9", "10", "5", "8", "4", "2", "1", "6", "3",
			"7", "9", "10", "5", "8", "4", "2");
	var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
			"2");
	_id = _id + "";
	for ( var i = 0; i < _id.length; i++) {
		// У��ÿһλ�ĺϷ���
		if (_id.charAt(i) < '0' || _id.charAt(i) > '9') {
			return false;
			break;
		}
	}
	var year = _id.substr(6, 2);
	var month = _id.substr(8, 2);
	var day = _id.substr(10, 2);
	var sexBit = _id.substr(14);
	// У�����λ

	if (year < '01' || year > '90')
		return false;
	// У���·�

	if (month < '01' || month > '12')
		return false;
	// У����

	if (day < '01' || day > '31')
		return false;
	return true;
}

/**
 * �������ƣ�CheckEmail ���ܣ�Email��֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ lenlimit �ִ������޶�
 * ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckEmail(valname, val, mode, lenlimit) {
	var vkeyWords = new RegExp(
			"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*$");
	var str = new String(val.value);
	if (mode && isNull(str)) {
		alert(valname + "\u4e0d\u80fd\u4e3a\u7a7a!");
		val.focus();
		val.select();
		return false;
	}
	if (isNull(str)) {
		return true;
	}
	if (lenlimit > 0 && str.length > lenlimit) {
		alert(valname + "\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + lenlimit
				+ "\u4e2a\u5b57\u7b26!");
		val.focus();
		val.select();
		return false;
	}
	if (!vkeyWords.test(str)) {
		alert(valname + "E-mail\u683c\u5f0f\u9519\u8bef!");
		val.focus();
		val.select();
		return false;
	}
	return true;
}
/**
 * �������ƣ�CheckFile ���ܣ��ļ�������֤ ����:valname �ؼ����� val �ؼ� mode ��֤ģʽ img , size,
 * maxwidth, maxheight ����ֵ��true(ͨ��)��false(ʧ��)
 */
function CheckFile(valname, val, img, size, maxwidth, maxheight) {
	var vkeyWords = new RegExp("(.[jpe?g|gif|png])$", "i");
	var str = new String(val.value);
	if (!vkeyWords.test(str)) {
		alert("\u6587\u4ef6\u7c7b\u578b\u4e0d\u6b63\u786e,\u53ea\u80fd\u4e0a\u4f20(*.jpg\u3001*.jepg\u3001*.gif\u3001*.png)\u56fe\u7247\u6587\u4ef6!");
		return false;
	}
	if ((img.fileSize / 1024).toFixed(2) > size) {
		alert(valname + "\u6587\u4ef6\u5927\u5c0f\u8d85\u51fa" + size
				+ "KB(\u5343\u5b57\u8282)\u7684\u9650\u5236!");
		return false;
	}
	return true;
}
