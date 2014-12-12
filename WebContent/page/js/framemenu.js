
if (null == window.random) {
	if (null == window.fe005e9f_e51b_4307_88ff_b4b150368fd4) {
		window.fe005e9f_e51b_4307_88ff_b4b150368fd4 = 0;
	}
	window.random = function () {
		var s = "RID";
		var d = new Date();
		s += d.getYear();
		s += d.getMonth();
		s += d.getDate();
		s += d.getHours();
		s += d.getMinutes();
		s += d.getSeconds();
		s += d.getMilliseconds();
		s += parseInt(Math.random() * 1000000000000);
		s += (window.fe005e9f_e51b_4307_88ff_b4b150368fd4++);
		return s;
	};
}
if (null == window.$) {
	window.$ = function () {
		var elements = new Array();
		for (var i = 0; i < arguments.length; i++) {
			var element = arguments[i];
			if (typeof element == "string") {
				element = document.getElementById(element);
			}
			if (arguments.length == 1) {
				return element;
			}
			elements.push(element);
		}
		return elements;
	};
}
if (null == window.controlOffsetLeft) {
	window.controlOffsetLeft = function (itemName) {
		var item = $(itemName);
		var totalOffset = 0;
		do {
			try {
				totalOffset += item.offsetLeft;
				item = item.offsetParent;
			}
			catch (ee) {
			}
		} while (item != null);
		return totalOffset;
	};
}
if (null == window.controlOffsetTop) {
	window.controlOffsetTop = function (itemName) {
		var item = $(itemName);
		var totalOffset = 0;
		do {
			try {
				totalOffset += item.offsetTop;
				item = item.offsetParent;
			}
			catch (ee) {
			}
		} while (item != null);
		return totalOffset;
	};
}
if (null == window.FrameMenuConfig) {
	window.FrameMenuConfig = function (title, url, target, img, id, pid) {
		this.Items = new Array();
		this.title = title;
		this.url = url;
		this.img = img;
		this.target = target;
		this.parentId = pid;
		this.id = id == null ? random() : id;
	};
	window.FrameMenu = new FrameMenuConfig();//create a new instance
	FrameMenuConfig.ID2Item = {};
	FrameMenuConfig.WindowsList = new Array();
	FrameMenuConfig.TopWindow = window.createPopup();
	FrameMenuConfig.CssPrefix = "";
	FrameMenuConfig.CssText = null;
	FrameMenuConfig.WindowsWidth = 150;
	FrameMenuConfig.ItemHeight = 20;
	FrameMenuConfig.FolderImage = null;
	FrameMenuConfig.DefaultTarget = null;
	window.FrameMenuConfig.prototype.add = function (title, url, target, img) {
		if (null == title || title.length < 1) {
			return null;
		}
		var item = new FrameMenuConfig(title, url, target, img);
		item.parentId = this.id;
		this.Items.push(item);
		FrameMenuConfig.ID2Item[item.id] = item;
		return item;
	};
	window.FrameMenuConfig.prototype.add2 = function (title, url, target, img) {
		if (null == title || title.length < 1) {
			return null;
		}
		var item = new FrameMenuConfig(title, url, target, img);
		item.parentId = this.id;
		this.Items.push(item);
		FrameMenuConfig.ID2Item[item.id] = item;
		return this;
	};
	window.FrameMenuConfig.prototype.insert = function (pid, id, title, url, img, target) {
		if (null == title || title.length < 1) {
			return null;
		}
		var item = new FrameMenuConfig(title, url, img, target, id, pid);
		FrameMenuConfig.ID2Item[item.id] = item;
		if (null == pid) {
			this.Items.push(item);
		} else {
			var pitem = FrameMenuConfig.ID2Item[pid];
			if (null == pitem) {
				alert("\u8de8\u5e27\u83dc\u5355\u811a\u672c\u9519\u8bef\uff0c\u65e0\u6cd5\u627e\u5230ID=" + pid + "\u7684\u6811\u5f62\u8282\u70b9");
				return;
			}
			pitem.Items.push(item);
		}
		return item;
	};
	FrameMenuConfig.showMenu = function (control, pid, deep) {
		if (null == FrameMenuConfig.CssText) {
			FrameMenuConfig.initlize();
		}
		var ele = control == null ? event.srcElement : $(control);
		if (null == ele) {
			return;
		}
		deep = Math.max(1, null == deep ? 1 : deep);
		var width = FrameMenuConfig.WindowsWidth;
		var left = controlOffsetLeft(ele);
		var top = controlOffsetTop(ele);
		var items = FrameMenuConfig.getSubItems(pid);
		var height = items.length * FrameMenuConfig.ItemHeight;
		var popw = null;
		if (deep <= 1) {
			var scrolltop = window.document.body.scrollTop || window.document.documentElement.scrollTop;
			var scrollleft = window.document.body.scrollLeft || window.document.documentElement.scrollLeft;
			if ((ele == document || ele == document.body) && event != null) {
				left = event.x;
				top = event.y;
			} else {
				top += (ele.clientHeight | ele.innerHeight | 20) + 3 - scrolltop;
				left += -scrollleft;
			}
			popw = FrameMenuConfig.TopWindow;
		} else {
			popw = ele.document.parentWindow.window.b4b150368fd4_popwindow;
			if (null == popw) {
				popw = ele.document.parentWindow.window.createPopup();
				ele.document.parentWindow.window.b4b150368fd4_popwindow = popw;
			}
			left += width - 1;
		}
		FrameMenuConfig.WindowsList[deep] = popw;
		for (var n = deep; n < FrameMenuConfig.WindowsList.length; n++) {
			if (null != FrameMenuConfig.WindowsList[n] && null != FrameMenuConfig.WindowsList[n].hide) {
				FrameMenuConfig.WindowsList[n].hide();
			}
		}
		var parentTreeNode = "parent";
		for (var i = 0; i < (deep - 1); i++) {
			parentTreeNode += ".parent";
		}
		var cssprefix = FrameMenuConfig.CssPrefix;
		cssprefix = null == cssprefix ? "" : cssprefix;
		if (cssprefix.indexOf("#") == 0) {
			cssprefix = "id='" + cssprefix.substring(1) + "'";
		} else {
			if (cssprefix.length > 0) {
				cssprefix = "class='" + cssprefix + "'";
			}
		}
		popw.document.body.innerHTML = "";
		popw.document.write("<body leftmargin=0 topmargin=0 scroll=no style='border:solid menu 0px;' >");
		if (null != FrameMenuConfig.CssText && FrameMenuConfig.CssText.length > 0) {
			popw.document.write("<style>" + FrameMenuConfig.CssText + "</style>");
		}
		var strFolder = "";
		var strText = "";
		for (var n = 0; null != items && n < items.length; n++) {
			var item = items[n];
			var bFolder = FrameMenuConfig.getSubItems(item.id).length > 0;
			var url = parentTreeNode + ".FrameMenuConfig.goto('" + (null == item.url ? "" : item.url) + "','" + (null == item.target ? "" : item.target) + "')";
			var htxt = "<div " + (null != FrameMenuConfig.CssText && FrameMenuConfig.CssText.length > 0 ? "" : " style='background-color:#E8E8E8;width:" + width + "px;height:" + FrameMenuConfig.ItemHeight + "px;'") + " " + cssprefix + " onmouseover=\"" + parentTreeNode + ".FrameMenuConfig.showMenu(this,'" + item.id + "'," + (deep + 1) + ")\"" + ">" + (bFolder && null != FrameMenuConfig.FolderImage && FrameMenuConfig.FolderImage.length > 0 ? "<img src='" + FrameMenuConfig.FolderImage + "' style='width:16px;height:16px;border:0;float:right' />" : "") + "<a href='javascript:void(0)' onclick=\"" + url + "\">" + item.title + "</a>" + "</div>";
			if (bFolder) {
				strFolder += htxt;
			} else {
				strText += htxt;
			}
		}
		if (items.length > 0) {
			popw.document.write(strFolder + strText);
			popw.show(left, top, width, height, document.body);
		}
	};
	FrameMenuConfig.hideAllWindows = function () {
		for (var n = 0; null != FrameMenuConfig.WindowsList && n < FrameMenuConfig.WindowsList.length; n++) {
			if (null != FrameMenuConfig.WindowsList[n] && null != FrameMenuConfig.WindowsList[n].hide) {
				FrameMenuConfig.WindowsList[n].hide();
			}
		}
	};
	FrameMenuConfig.createFrameMenu = function (control, bremove) {
		var ele = $(control);
		if (bremove) {
			ele.innerHTML = "";
		}
		var items = FrameMenuConfig.getSubItems();
		for (var n = 0; n < items.length; n++) {
			var item = items[n];
			var bFolder = FrameMenuConfig.getSubItems(item.id).length > 0;
			var url = "javascript:{FrameMenuConfig.goto('" + (null == item.url ? "" : item.url) + "','" + (null == item.target ? "" : item.target) + "');}";
			var ah = document.createElement("<a href=\"" + url + "\" onmouseover=\"FrameMenuConfig.showMenu(this,'" + item.id + "',1)\">");
			ah.innerHTML = item.title;
			ele.appendChild(ah);
		}
	};
	FrameMenuConfig.getSubItems = function (pid) {
		if (null == pid) {
			return window.FrameMenu.Items;
		} else {
			var item = FrameMenuConfig.ID2Item[pid];
			if (null == item) {
				return new Array();
			} else {
				return item.Items;
			}
		}
	};
	FrameMenuConfig.goto = function (url, target) {
		FrameMenuConfig.hideAllWindows();
		if (null == url || url.length < 1) {
			return;
		}
		if (null == target || target.length < 1) {
			target = FrameMenuConfig.DefaultTarget;
		}
		if (null != target && target.length > 0 && target.toLowerCase() != "_self" && target.toLowerCase() != "self") {
			if (target.toLowerCase() == "_blank") {
				window.open(url);
			} else {
				if (window.parent == null || null == window.parent.frames[target]) {
					alert("\u8de8\u5e27\u83dc\u5355\u914d\u7f6e\u83dc\u5355\u9519\u8bef\uff0c\u627e\u4e0d\u5230\u5e27 " + target);
					return;
				} else {
					window.parent.frames[target].location = url;
				}
			}
		} else {
			window.location = url;
		}
	};
	//init styleSheet from this page
	FrameMenuConfig.initlize = function () {
		FrameMenuConfig.ItemHeight = Math.max(12, FrameMenuConfig.ItemHeight);
		FrameMenuConfig.WindowsWidth = Math.max(80, FrameMenuConfig.WindowsWidth);
		document.focus();
		document.onfocusout = function () {
			FrameMenuConfig.hideAllWindows();
		};
		FrameMenuConfig.CssText = "";
		for (var n = 0; FrameMenuConfig.CssPrefix != null && FrameMenuConfig.CssPrefix.length > 0 && n < document.styleSheets.length; n++) {
			var sts = document.styleSheets[n];
			for (var x = 0; x < sts.rules.length; x++) {
				var rr = sts.rules[x];
				if (rr.selectorText.indexOf(FrameMenuConfig.CssPrefix) >= 0) {
					FrameMenuConfig.CssText += rr.selectorText + "{" + rr.style.cssText + "}";
				}
			}
		}
	};
}

