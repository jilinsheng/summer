package com.mingda.common;

import java.sql.SQLException;
import java.util.HashMap;

import org.dom4j.Document;

///////////@SuppressWarnings("unchecked")
public class Pager {
	private int pageSize = 16; // ÿҳ��ʾ��¼��
	private int count = 0; // ��¼����
	private int pageCount = 0; // ��ҳ��
	private int page = 0; // ��ǰҳ��
	private String SQL;// �õ���ѯ��¼sql���
	private String url = "/db/page/querymanage/infoqueryresult.do";// ��ǰ��ѯ����
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
	 * ֻ����SQL����PageBean remark 0:sql 1:hql
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
			all = "<input name=\"selall\" type=\"radio\" value=\"ȫѡ\" onclick=\"selall()\">ȫѡ</input><input name=\"selall\" type=\"radio\" value=\"��ѡ\" onclick=\"selout()\">��ѡ</input>";
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
	 * ��ѯ���
	 * 
	 * @return
	 */
	/**
	 * ҳ��Ҫ��ʾ�Ĺ�������ҳ����
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
			str += "��ҳ ��һҳ&nbsp;";
		} else {
			str += "<a href='" + url + temp + "cur_page=1'>��ҳ</a>&nbsp;";
			str += "<a href='" + url + temp + "cur_page=" + (page - 1)
					+ "'>��һҳ</a>&nbsp;";
		}
		if (isLast()) {
			str += "��һҳ βҳ&nbsp;";
		} else {
			str += "<a href='" + url + temp + "cur_page=" + (page + 1)
					+ "'>��һҳ</a>&nbsp;";
			str += "<a href='" + url + temp + "cur_page=" + pageCount
					+ "'>βҳ</a>&nbsp;";
		}
		str += "&nbsp;��<b>" + this.count + "</b>����¼&nbsp;��<b>" + this.pageCount
				+ "</b>ҳ";
		/*
		 * str += "&nbsp;ת��<select name='page' onChange=\"location='" + url +
		 * temp + "cur_page='+this.options[this.selectedIndex].value\">"; for
		 * (int i = 1; i <= pageCount; i++) { if (i == page) str += "<option
		 * value='" + i + "' selected>��" + i + "ҳ</option>"; else str += "<option
		 * value='" + i + "'>��" + i + "ҳ</option>"; } str += "</select>";
		 */
		str += "&nbsp;ת��<input id=\"page\" type=\"text\" value=\""
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
	 * ����ÿҳ�����ʾ�ļ�¼��
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * ���õ�ǰҪ��ѯ��ҳ��
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * ���ò�ѯʹ�õ�HQL����SQL���
	 * 
	 * @param sql
	 */
	public void setSQL(String sql) {
		this.SQL = sql;
	}

	/**
	 * ���ò�ѯ�����ӵ�ַ
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
