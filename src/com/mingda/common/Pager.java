package com.mingda.common;

import java.sql.SQLException;
import java.util.HashMap;

import org.dom4j.Document;

///////////@SuppressWarnings("unchecked")
public class Pager {
	private int pageSize = 16; // 每页显示记录数
	private int count = 0; // 记录总数
	private int pageCount = 0; // 总页数
	private int page = 0; // 当前页数
	private String SQL;// 得到查询记录sql语句
	private String url = "/db/page/querymanage/infoqueryresult.do";// 当前查询连接
	private int endrow;
	private int beiginrow;
	private int colsnum;
	private String all;
	private Document doc;
	private HashMap colname;

	public HashMap getColname() {
		return colname;
	}

	public void setColname(HashMap colname) {
		this.colname = colname;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Pager() {
	}

	/**
	 * 只传入SQL构造PageBean remark 0:sql 1:hql
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public Pager(int count, int page, String checkbox) {
		this.page = page;
		this.count = count;
		if (count % this.pageSize == 0) {
			this.pageCount = count / this.pageSize;
		} else {
			this.pageCount = (count / this.pageSize) + 1;
		}
		if (checkbox.equals("1")) {
			all = "<input name=\"selall\" type=\"radio\" value=\"全选\" onclick=\"selall()\">全选</input><input name=\"selall\" type=\"radio\" value=\"反选\" onclick=\"selout()\">反选</input>";
		} else {
			all = "";
		}
	}

	private boolean isFirst() {
		if (page == 1) {
			return true;

		} else {
			return false;
		}
	}

	private boolean isLast() {
		if (page == pageCount) {
			return true;
		} else {
			return false;
		}
	}

	public int getEndrow() {
		return pageSize * page;
	}

	public int getBeiginrow() {
		if (page == 1) {
			return 1;
		} else {
			return pageSize * (page - 1) + 1;
		}
	}

	/**
	 * 查询结果
	 * 
	 * @return
	 */
	/**
	 * 页面要显示的工具条网页代码
	 * 
	 * @return
	 */
	public String getToolsMenu() {
		if (url == null || url.equals("")) {
			return null;
		}
		String temp = "";
		if (url.indexOf("?") == -1) {
			temp = "?";
		} else {
			temp = "&";
		}
		String str = "";
		str += "";
		if (isFirst()) {
			str += "首页 上一页&nbsp;";
		} else {
			str += "<a href='" + url + temp + "cur_page=1'>首页</a>&nbsp;";
			str += "<a href='" + url + temp + "cur_page=" + (page - 1)
					+ "'>上一页</a>&nbsp;";
		}
		if (isLast()) {
			str += "下一页 尾页&nbsp;";
		} else {
			str += "<a href='" + url + temp + "cur_page=" + (page + 1)
					+ "'>下一页</a>&nbsp;";
			str += "<a href='" + url + temp + "cur_page=" + pageCount
					+ "'>尾页</a>&nbsp;";
		}
		str += "&nbsp;共<b>" + this.count + "</b>条记录&nbsp;共<b>" + this.pageCount
				+ "</b>页";
		/*
		 * str += "&nbsp;转到<select name='page' onChange=\"location='" + url +
		 * temp + "cur_page='+this.options[this.selectedIndex].value\">"; for
		 * (int i = 1; i <= pageCount; i++) { if (i == page) str += "<option
		 * value='" + i + "' selected>第" + i + "页</option>"; else str += "<option
		 * value='" + i + "'>第" + i + "页</option>"; } str += "</select>";
		 */
		str += "&nbsp;转到<input id=\"page\" type=\"text\" value=\""
				+ page
				+ "\" size=\"5\"/><input type=\"button\" value=\"GO\" onclick=\"document.location.reload('"
				+ url + temp
				+ "cur_page='+document.getElementById('page').value)\"/>";
		return str;
	}

	private void setCount(int count) {
		if (pageSize != 0) {
			pageCount = count / pageSize;
			if (count % pageSize != 0) {
				pageCount++;
			}
		}
		this.count = count;
	}

	/**
	 * 设置每页最多显示的记录数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 设置当前要查询的页码
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 设置查询使用的HQL或是SQL语句
	 * 
	 * @param sql
	 */
	public void setSQL(String sql) {
		this.SQL = sql;
	}

	/**
	 * 设置查询的连接地址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public int getColsnum() {
		return colsnum;
	}

	public void setColsnum(int colsnum) {
		this.colsnum = colsnum;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

}
