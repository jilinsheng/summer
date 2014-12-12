package com.mingda.action.querymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mingda.action.info.search.DBUtils;
import com.mingda.common.Pager;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.page.PageView;

public class QueryResult {
	public Pager getFamilyResult(String sql, Document tree,
			Document dictionary, String type, String curpage, String checkbox) {

		PageView pv = new PageView();
		Pager page = null;

		Connection conn = null; // 声明Connection对象
		PreparedStatement pstmt = null; // 声明PreparedStatement对象
		ResultSet rs = null; // 声明ResultSet对象

		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GB18030");
		Element root = doc.addElement("tbody");

		try {
			conn = DBUtils.getConnection(); // 获取数据库连接

			// 从sql语句导出列名
			int begin = sql.indexOf("select ") + 7;
			int end = sql.indexOf("from");
			String tempsql = sql.substring(begin, end);
			String[] cols = tempsql.split(",");
			// 从sql语句导出列名

			Element tr = root.addElement("tr");
			Element treeroot = tree.getRootElement();
			// 表头
			HashMap colnames = new HashMap();
			// 通过tree xml 查出 中文名称 字典值是否显示
			for (int i = 0; i < cols.length; i++) {
				cols[i] = cols[i].replace(".", ",");
				String[] col = cols[i].trim().split(",");
				String tablname = col[0];
				tablname = tablname.substring(7);
				String column = col[1];

				Element element = (Element) tree.selectSingleNode("//"
						+ tablname + "/property[@column='" + column + "']");
				if (element == null) {
					if (column.equals("FULLNAME")) {
						tr.addElement("td").addAttribute("class", "colField")
								.setText("所属");
						colnames.put(i, "所属");
					} else if (column.equals("ORGANIZATION_ID")) {

					}
				} else {

					if (element.attributeValue("column").equals("FAMILY_ID")) {
						tr.addElement("td").addAttribute("class", "colField")
								.addAttribute("height", "24").setText("操作");
					} else if (element.attributeValue("column").equals(
							"MEMBER_ID")) {

					} else if (column.equals("ORGANIZATION_ID")) {

					} else {
						tr.addElement("td").addAttribute("class", "colField")
								.setText(element.attributeValue("title"));
						colnames.put(i, element.attributeValue("title"));
					}
				}
			}
			Log4jApp.logger("hash map " + colnames.size());
			int totalrow = 0;
			pstmt = conn.prepareStatement("select count(1) from ( " + sql
					+ " )"); // 查出分页结果集 总数
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalrow = rs.getInt(1);
				if (curpage == null || curpage.equals("")) {
					page = new Pager(totalrow, new Long(1).intValue(), checkbox);
					page.setColname(colnames);
				} else {
					page = new Pager(totalrow, new Long(curpage).intValue(),
							checkbox);
					page.setColname(colnames);
				}
			}

			pstmt = conn
					.prepareStatement("select * from (select mytab.*, rownum row_num from ("
							+ sql
							+ ") mytab) where row_num between "
							+ page.getBeiginrow() + " and " + page.getEndrow());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Element temptr = root.addElement("tr");
				String pk = rs.getString("FAMILY_ID");
				for (int i = 0; i < cols.length; i++) {
					cols[i] = cols[i].replace(".", ",");
					String[] col = cols[i].trim().split(",");
					String tablname = col[0];
					tablname = tablname.substring(7);
					String column = col[1];
					Element element = (Element) tree.selectSingleNode("//"
							+ tablname + "/property[@column='" + column + "']");
					String value = rs.getString(column);
					if (value == null || value.equals("")) {
						value = "无";
					}
					if (element == null) {
						if (column.equals("FULLNAME")) {
							temptr.addElement("td").addAttribute("class",
									"colValue").setText(value);

						} else if (column.equals("ORGANIZATION_ID")) {

						}
					} else {
						if (element.attributeValue("column")
								.equals("FAMILY_ID")) {

							if (checkbox.equals("1")) {
								temptr.addElement("td").addAttribute("class",
										"colValue").addElement("input")
										.addAttribute("type", "checkbox")
										.addAttribute("name", "checkbox")
										.addAttribute("id", "checkbox")
										.addAttribute(
												"onclick",
												"checkval(this,"
														+ rs.getString(column)
														+ ")").addAttribute(
												"value", rs.getString(column));
							} else {
								temptr
										.addElement("td")
										.addAttribute("height", "22")
										.addAttribute("class", "colValue")
										.addElement("button")
										.addAttribute("style",
												"border:0px; CURSOR: hand; width:16px; height:16px;")
										.addAttribute("type", "checkbox")
										.addAttribute("name", "checkbox")
										.addAttribute("id", "checkbox")
										.addAttribute(
												"onclick",
												"clickval(this,"
														+ rs.getString(column)
														+ ")").addAttribute(
												"value", value).addElement(
												"img").addAttribute("name",
												"ctrlopt").addAttribute(
												"width", "16").addAttribute(
												"height", "16").addAttribute(
												"style", "CURSOR: hand")
										.addAttribute("src",
												"/db/page/images/check1.jpg");
							}
						} else if (element.attributeValue("column").equals(
								"FAMILYNO")) {
							temptr.addElement("td").addAttribute("class",
									"colValue").addElement("a").addAttribute(
									"href",
									"/db/page/info/card/newfamilyinfocard.do?entityId="
											+ pk)
									.addAttribute("target", "oper")
									.addAttribute("onclick",
											"javascript:revert();").setText(
											value);// 家庭ID和显示信息卡片在此操作
						} else if (element.attributeValue("column").equals(
								"MEMBER_ID")) {
						} else if (element.attributeValue("column").equals(
								"MASTERNAME")) {
							temptr.addElement("td").addAttribute("class",
									"colValue").addElement("a").addAttribute(
									"href",
									"/db/page/info/card/newfamilyinfocard.do?entityId="
											+ pk)
									.addAttribute("target", "oper")
									.addAttribute("onclick",
											"javascript:revert();").setText(
											value);// 家庭ID和显示信息卡片在此操作
						} else if (element.attributeValue("column").equals(
								"MEMBERNAME")) {
							temptr.addElement("td").addAttribute("class",
									"colValue").addElement("a").addAttribute(
									"href",
									"/db/page/info/card/newfamilyinfocard.do?entityId="
											+ pk)
									.addAttribute("target", "oper")
									.addAttribute("onclick",
											"javascript:revert();").setText(
											value);// 家庭ID和显示信息卡片在此操作

						} else if (column.equals("ORGANIZATION_ID")) {

						} else if (element.attributeValue("dicsort") != null) {
							temptr.addElement("td").addAttribute("class",
									"colValue").setText(
									pv.getDictionartHandle()
											.getDictsortToValue(dictionary,
													value));
						} else {
							temptr.addElement("td").addAttribute("class",
									"colValue").setText(value);
						}
					}
				}
			}
			page.setColsnum(cols.length + 1);
			page.setDoc(doc);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs); // 关闭结果集
			DBUtils.close(pstmt); // 关闭PreparedStatement
			//DBUtils.close(conn); // 关闭连接
		}
		return page;
	}
}
