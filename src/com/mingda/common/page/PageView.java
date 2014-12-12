package com.mingda.common.page;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.node.TreeHandle;
import com.mingda.common.node.TreeHandleImpl;
import com.mingda.dao.SysTOrganizationDAO;
import com.mingda.dao.SysTUnitDAO;
import com.mingda.entity.SysTUnit;

public class PageView extends BaseHibernateDAO {

	static Logger log = Logger.getLogger(PageView.class);

	/**
	 * 
	 * @param element
	 *            xml元素
	 * @param parentpath
	 *            父级xpath
	 * @return java.lang.String html代码 <input name="username" value=""/>
	 */
	public String writeInputHTML(Element element, String parentpath, String type) {
		String html = element.attributeValue("column") + ":<input type=" + type
				+ " name=\"" + parentpath + element.getPath() + "\" value="
				+ element.getText();
		return html;
	}

	/**
	 * 
	 * @param element
	 *            xml元素
	 * @param parentpath
	 *            父级xpath
	 * @param doc
	 *            字典表xml
	 * @return ava.lang.String html代码 <select><option></option></select>
	 */
	public String writeSelectHTML(Element element, String parentpath,
			Document dictionary) {
		String html = element.attributeValue("column")
				+ ":<select name=\""
				+ parentpath
				+ "\">"
				+ this.getDictionartHandle().getDictsortToOption(dictionary,
						"SEX", element.getText().trim()) + "</select>";
		return html;
	}

	/**
	 * 
	 * @param currentElement
	 *            当前元素
	 * @param parentName
	 *            父节名称
	 * @param dictionary
	 *            字典xml
	 * @return String[2] display[0]: 标签头;display[1]: 标签html代码
	 */
	public String[] writeHtmlByXML(Element currentElement, String parentName,
			Document dictionary) {
		String[] display = new String[2];
		String html = "";
		String tagName = parentName + "/"
				+ currentElement.attributeValue("column");
		String tagType = currentElement.attributeValue("control").toString();
		String entityType = currentElement.attributeValue("type").toString();
		String tagDicsort = currentElement.attributeValue("dicsort");
		String defaultValue = currentElement.attributeValue("default");
		String title = currentElement.attributeValue("title");
		String value = currentElement.getTextTrim();
		String length = currentElement.attributeValue("length").toString();
		String tagReadonly = currentElement.attributeValue("readonly")
				.toString();
		String rule = currentElement.attributeValue("rule");
		String status = currentElement.attributeValue("status");
		/*
		 * jsTxt js 验证代码
		 */
		String jsTxt = "";
		if (rule.equals("6")) {
			jsTxt = "onchange=\"CheckChinese('" + title + "',this,'true','"
					+ length + "')\"";
		} else if (rule.equals("7")) {
			jsTxt = "onchange=\"CheckNumber('" + title + "',this,'true')\"";
		} else if (rule.equals("8")) {
			jsTxt = "onchange=\"CheckIdCard('" + title + "',this,'true')\"";
		} else if (rule.equals("5")) {
			jsTxt = "onchange=\"CheckDate('" + title + "',this,'true')\"";
		} else {
		}
		/**
		 * 设定值
		 */
		if (defaultValue == null || defaultValue.equals("")
				|| defaultValue.equals("null")) {
			defaultValue = "";
		}
		if (value == null || value.equals("") || value.equals("null")) {
			value = defaultValue;
		}
		/**
		 * 设定是不是只读
		 */
		if (tagReadonly.equals("1")) {
			tagReadonly = "readonly =\"true\"";
		} else if (tagReadonly.equals("1")) {
			// tagReadonly = "readonly =\"false\"";
			tagReadonly = "";
		} else {
			tagReadonly = "";
		}
		/**
		 * tagType 标签类型
		 */
		if (tagType.equals("1")) {
			html = "<input title=\"" + title + "\" name=\"" + tagName
					+ "\" type=\"hidden\" value=\"" + value + "\" rule=\""
					+ rule + "\"/>";
			display[0] = "h";
		} else if (tagType.equals("2")) {
			html = "<input title=\"" + title + "\" name=\"" + tagName
					+ "\" type=\"text\" value=\"" + value + "\" " + jsTxt + " "
					+ tagReadonly + "  rule=\"" + rule + "\"/>";
			display[0] = title;
		} else if (tagType.equals("3")) {
			html = "<select title=\""
					+ title
					+ "\" name=\""
					+ tagName
					+ "\"  rule=\""
					+ rule
					+ "\">"
					+ this.getDictionartHandle().getDictsortToOption(
							dictionary, tagDicsort, value) + "</select>";
			display[0] = title;
		} else if (tagType.equals("4")) {
			try {
				Date date = null;
				SimpleDateFormat format1 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				if (value == null || value.equals("")) {
				} else {
					date = format1.parse(value);
					format1 = new SimpleDateFormat("yyyy-MM-dd");
					value = format1.format(date);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			html = "<input title=\"" + title + "\" id=\"" + tagName
					+ "\" name=\"" + tagName + "\" type=\"text\" value=\""
					+ value + "\" " + jsTxt + "  rule=\"" + rule
					+ "\"  onfocus=\"setday(this)\" />";
			display[0] = title;
		} else {
		}
		display[1] = html;
		return display;
	}

	/**
	 * 
	 * @param currentElement
	 *            当前元素
	 * @param dictionary
	 *            字典表
	 * @return 字符串数组 标题 值
	 */

	public String[] writeTextByXML(Element currentElement, Document dictionary) {
		String[] display = new String[2];
		String html = "";
		String tagType = currentElement.attributeValue("control").toString();
		String title = currentElement.attributeValue("title");
		String value = currentElement.getTextTrim();
		if (tagType.equals("1")) {
			html = "";
			display[0] = "";
		} else if (tagType.equals("2")) {
			html = value;
			display[0] = title;
		} else if (tagType.equals("3")) {
			html = this.getDictionartHandle().getDictsortToValue(dictionary,
					value);
			display[0] = title;
		} else if (tagType.equals("4")) {
			try {
				Date date = null;
				SimpleDateFormat format1 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				if (value == null || value.equals("")) {
				} else {
					date = format1.parse(value);
					format1 = new SimpleDateFormat("yyyy-MM-dd");
					value = format1.format(date);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			html = value;
			display[0] = title;
		} else {
		}
		display[1] = html;
		return display;
	}

	/**
	 * 
	 * @param code
	 *            节点名称
	 * @param nodeId
	 *            父节点id
	 * @return 返回节点列表,并且包括下一级节点名称 算法错误!
	 */
	public Document extractElementByNode(String code, String nodeId) {
		Document currentdoc = null;
		if (code == null || code.equals("") || code.equals("null")) {
		} else {
			currentdoc = DocumentHelper.createDocument();
			currentdoc.setXMLEncoding("GB18030");
			Element current = currentdoc.addElement("root");
			TreeHandle treeHandle = new TreeHandleImpl();
			Document tempdoc = treeHandle
					.selectEntities(code, new Long(nodeId));
			Element temproot = tempdoc.getRootElement();
			Iterator tempit = temproot.elementIterator();
			while (tempit.hasNext()) {
				Element element = (Element) tempit.next();
				Element child = current.addElement(element.getName());
				Iterator it = element.elementIterator();
				String table = element.getName();
				child.addAttribute("table", table);
				while (it.hasNext()) {
					Element property = (Element) it.next();
					String column = property.attributeValue("column");
					String value = property.getText();
					child.addAttribute(column, value);
					if (property.attributeValue("isprimary") != null) {
						child.addAttribute("pk", value);
					}
					if (property.attributeValue("isforeign") != null) {
						child.addAttribute("fk", value);
					}
					if (property.attributeValue("isvisible") != null
							&& property.attributeValue("isvisible").equals("1")) {
						child.addAttribute("visible", value);
					}
				}
			}
			// 取孩子节点名称
			Document childdoc = treeHandle.getBranch(code);
			List childs = childdoc.selectNodes("//" + code);
			Iterator childit = childs.iterator();
			while (childit.hasNext()) {
				Element child = (Element) childit.next();
				Iterator it = child.elementIterator();
				while (it.hasNext()) {
					Element element = (Element) it.next();
					if (!element.getName().equals("property")) {
						Iterator currentit = current.elementIterator();
						while (currentit.hasNext()) {
							Element currentElement = (Element) currentit.next();
							currentElement
									.addAttribute("cn", element.getName());
						}
					}
				}
			}
		}
		return currentdoc;
	}

	/**
	 * 生成一个节点树形结构 包括其子结点和分支列表
	 * 
	 * @param nodeName
	 * @param nodeId
	 * @return
	 */
	public Document extractNodeTree(String nodeName, String nodeId) {
		Document doc = null;
		return doc;

	}

	/**
	 * 生成表格主体html代码
	 * 
	 * @param entity
	 *            单个实体
	 * @param dictionary
	 *            字典xml
	 * @param cols
	 *            列数
	 * @return 表格单元格
	 * @throws Exception
	 */
	public Document writeTablebyXML(Document entity, Document dictionary,
			int cols) throws Exception {
		Document doc = this.getDocument();
		Element root = doc.getRootElement();
		Iterator currentIt = entity.selectNodes("//property").iterator();
		int i = 0;
		Element tr = null;
		while (currentIt.hasNext()) {
			Element current = (Element) currentIt.next();
			List<Element> list = this.convertProperty(current, dictionary);
			if (list != null && !list.isEmpty()) {
				if ((i % cols) == 0) {
					tr = root.addElement("tr");
				}
				Iterator<Element> it = list.iterator();
				while (it.hasNext()) {
					Element e = it.next();
					tr.add((Element) e.clone());
				}
				i++;
			}
		}
		// 补齐
		if (tr == null) {
		} else {
			int j = cols - tr.elements().size() / 2;
			if (j != 0) {
				for (int cut = 0; cut < j * 2; cut++) {
					tr.addElement("td");
				}
			}
		}
		return doc;
	}

	/**
	 * 生成表格主体html代码并且带有表单域
	 * 
	 * @param entity
	 * @param dictionary
	 * @return html代码 <tbody>.......</tbody>
	 */
	public Document writeFormbyXML(Document entity, Document dictionary,
			int cols) {
		Document doc = this.getDocument();
		Element root = doc.getRootElement();
		Iterator currentIt = entity.selectNodes("//property").iterator();
		int i = 0;
		Element tr = null;
		while (currentIt.hasNext()) {
			Element current = (Element) currentIt.next();
			List<Element> list = this.convertPropertyforForm(current,
					dictionary);
			if (list != null) {
				Iterator<Element> it = list.iterator();
				if (list.size() == 1) {
					while (it.hasNext()) {
						Element e = it.next();
						root.add((Element) e.clone());
					}
				} else {
					if ((i % cols) == 0) {
						tr = root.addElement("tr");
					}
					while (it.hasNext()) {
						Element e = it.next();
						tr.add((Element) e.clone());
					}
					i++;
				}
			}
		}
		// 补齐
		if (tr != null) {
			int j = cols - tr.elements().size() / 2;
			if (j != 0) {
				for (int cut = 0; cut < j * 2; cut++) {
					tr.addElement("td");
				}
			}
		}
		return doc;

	}

	/**
	 * 生成列表表格 每行由一个radio触发操作事件 调用页面加入 js函数 function setPk(pk){return};
	 * 
	 * @param entity
	 * @param dictionary
	 * @return
	 */
	public Document writeListbyXML(Document entity, Document dictionary) {
		Document doc = this.getDocument();
		Element root = doc.getRootElement();

		Log4jApp.logger(entity.asXML());

		if (!entity.getRootElement().elements().isEmpty()
				&& !entity.getRootElement().selectSingleNode(
						"//property[@isprimary='true']").getText().equals("")) {

			Element thead = root.addElement("tr");
			Element first = thead.addElement("th");
			first.setText("操作");
			Iterator currentIt = entity.getRootElement().elementIterator();
			int i = 0;

			while (currentIt.hasNext()) {

				Element current = (Element) currentIt.next();
				Element tr = root.addElement("tr");
				Iterator it = current.elementIterator();
				String pk = "";
				String nodeName = current.getName();
				String fk = "";
				Element firsttd = tr.addElement("td");

				while (it.hasNext()) {

					Element child = (Element) it.next();

					if (child.attributeValue("isprimary") != null
							&& child.attributeValue("isprimary").equals("true")) {

						pk = child.getText();

					}
					if (child.attributeValue("isforeign") != null
							&& child.attributeValue("isforeign").equals("true")) {

						fk = child.getText();

					}
					if (child.attributeValue("islist") != null
							&& child.attributeValue("islist").equals("1")
							&& child.attributeValue("status").equals("1")) {

						if (i == 0) {

							thead.addElement("th").setText(
									child.attributeValue("title"));
						}

						String value = "";
						Element td = tr.addElement("td");
						if (child.attributeValue("dicsort") != null) {

							value = this.getDictionartHandle()
									.getDictsortToValue(dictionary,
											child.getText());
						} else {
							// 日期时间类型格式化
							if ("4".equals(child.attributeValue("control"))) {

								value = this.StrToDate(child.getText());

							} else {

								value = child.getText();
							}

						}

						td.setText(value);

					}
				}
				firsttd.addElement("input").addAttribute("type", "checkbox")
						.addAttribute("name", "checkbox").addAttribute("value",
								fk).addAttribute(
								"onclick",
								"checkval(this,'" + pk + "','" + fk + "','"
										+ nodeName + "')");
				firsttd.addElement("input").addAttribute("type", "hidden")
						.addAttribute("name", "pid").addAttribute("id", "pid")
						.addAttribute("value", fk);
				firsttd.addElement("input").addAttribute("type", "hidden")
						.addAttribute("name", "nodeName").addAttribute("id",
								"nodeName").addAttribute("value", nodeName);
				i++;
			}
		}
		return doc;
	}

	/**
	 * 
	 * @param enyity
	 *            表字段
	 * @param dictionary
	 *            字典表xml
	 * @return
	 */
	private List<Element> convertProperty(Element entity, Document dictionary) {
		Log4jApp.logger("convert list table:   " + entity.asXML());

		Document doc = this.getDocument();
		Element root = doc.getRootElement();
		String status = entity.attributeValue("status");

		if (!"1".equals(status)) {
			return null;
		}

		String tagDicsort = entity.attributeValue("dicsort");
		String title = entity.attributeValue("title");
		String value = entity.getTextTrim();
		String visible = entity.attributeValue("isvisible");
		String tagType = entity.attributeValue("control").toString();

		if ("1".equals(visible)) {
			Element e_title = root.addElement("th");
			e_title.setText(title);
			Element e_content = root.addElement("td").addElement("span");
			if ("MAINWORKPLACE".equals(entity.attributeValue("column"))) {
				if (!"".equals(value)) {

					SysTUnitDAO unitdao = new SysTUnitDAO();
					SysTUnit unit = unitdao.findById(new Long(value));
					value = unit.getUnitname();
					e_content.setText(value);
				} else {

					value = "";

				}
			} else if ("4".equals(tagType)) {
				value = this.StrToDate(value);
				e_content.setText(value);
			} else if (null != tagDicsort && !"".equals(tagDicsort)) {

				value = this.getDictionartHandle().getDictsortToValue(
						dictionary, value);
				
				if ("0".equals(value)) {

					value = "未填写";
					e_content.addAttribute("style", "color:#cccccc").setText(value);

				} else {
					e_content.setText(value);

				}

			} else {
				if (null == value || "".equals(value)) {

					value = "未填写";
					e_content.addAttribute("style", "color:#cccccc").setText(value);

				} else {

					e_content.setText(value);

				}
			}
			
			return root.elements();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param enyity
	 * @param dictionary
	 * @return
	 */
	private List<Element> convertPropertyforForm(Element entity,
			Document dictionary) {
		log.debug(entity.asXML());
		Document doc = this.getDocument();
		Element root = doc.getRootElement();
		String status = entity.attributeValue("status");
		if (!status.equals("1")) {
			return null;
		}
		String tagName = entity.getParent().getName() + "/"
				+ entity.attributeValue("column");
		String id = tagName;
		String tagType = entity.attributeValue("control").toString();
		String tagDicsort = entity.attributeValue("dicsort");
		String defaultValue = entity.attributeValue("default");
		String title = entity.attributeValue("title");
		String value = entity.getTextTrim();
		String length = entity.attributeValue("length").toString();
		String tagReadonly = entity.attributeValue("readonly").toString();
		String rule = entity.attributeValue("rule");
		Element caption = null;
		Element field = null;
		/*
		 * jsTxt js 验证代码
		 */
		String jsTxt = "";
		if (rule.equals("6")) {
			jsTxt = "CheckChinese('" + title + "',this,'true','" + length
					+ "')";
		} else if (rule.equals("7")) {
			jsTxt = "CheckNumber('" + title + "',this,'true')";
		} else if (rule.equals("8")) {
			jsTxt = "writeBirthandSex(this)";
		} else if (rule.equals("5")) {
			jsTxt = "CheckDate('" + title + "',this,'true')";
		} else {
			jsTxt = "return";
		}
		/**
		 * 设定值
		 */
		if (defaultValue == null || defaultValue.equals("")
				|| defaultValue.equals("null")) {
			defaultValue = "";
		}
		if (value == null || value.equals("") || value.equals("null")) {
			value = defaultValue;
		}
		/**
		 * 设定是不是只读
		 */
		if (tagReadonly.equals("1")) {
			tagReadonly = "true";
		} else if (tagReadonly.equals("0")) {
			// tagReadonly = "readonly =\"false\"";
			tagReadonly = "false";
		} else {
			tagReadonly = "false";
		}
		/**
		 * tagType 标签类型
		 */
		if (tagType.equals("1")) {
			field = root.addElement("input");
			field.addAttribute("title", title);
			field.addAttribute("name", tagName);
			field.addAttribute("type", "hidden");
			field.addAttribute("rule", rule);
			field.addAttribute("value", value);
			field.addAttribute("id", id);
		} else if (tagType.equals("2")) {
			caption = root.addElement("th")
					.addAttribute("style", "width:100px");
			caption.setText(title);
			field = root.addElement("td");
			Element input = field.addElement("input");
			input.addAttribute("style", "width:100%");
			input.addAttribute("title", title);
			input.addAttribute("id", id);
			input.addAttribute("name", tagName);
			input.addAttribute("type", "text");
			input.addAttribute("rule", rule);
			input.addAttribute("value", value);
			input.addAttribute("onchange", jsTxt);
			if (tagReadonly.equals("true")) {
				input.addAttribute("readonly", tagReadonly);
			}
		} else if (tagType.equals("3")) {
			caption = root.addElement("th")
					.addAttribute("style", "width:100px");
			caption.setText(title);
			field = root.addElement("td");
			Element select = field.addElement("select");
			select.addAttribute("id", id);
			select.addAttribute("title", title);
			select.addAttribute("name", tagName);
			select.addAttribute("rule", rule);
			Log4jApp.logger("下拉列表处理:" + entity.asXML());
			Iterator it = this.getDictionartHandle().getDictsortToXML(
					dictionary, tagDicsort, value).elementIterator();
			while (it.hasNext()) {
				Element el = (Element) it.next();
				select.add((Element) el.clone());
			}
			// 单位特殊处理
			if ("MAINWORKPLACE".equals(entity.attributeValue("column"))) {
				if ("RESUME".equals(entity.getParent().getName())) {
					field.addElement("button").addAttribute("class", "btn")
							.addAttribute("style", "width:20%").addAttribute(
									"onclick", "addunit()").setText("选择工作单位");
					select.addAttribute("style", "width:80%");
				}
				if (!"".equals(value)) {
					SysTUnitDAO unitdao = new SysTUnitDAO();
					SysTUnit unit = unitdao.findById(new Long(value));
					select.addElement("option").addAttribute("value",
							unit.getUnitId().toString()).addAttribute(
							"selected", "selected").setText(unit.getUnitname());
				}
			} else if (entity.attributeValue("column").equals("SICKTYPE")) {
				select.addAttribute("style", "width:100%");
			} else {
				select.addAttribute("style", "width:100%");
			}
		} else if (tagType.equals("4")) {
			caption = root.addElement("th")
					.addAttribute("style", "width:100px");
			caption.setText(title);
			field = root.addElement("td");
			Element input = field.addElement("input");
			// input.addAttribute("id", id);
			input.addAttribute("title", title);
			input.addAttribute("name", tagName);
			input.addAttribute("type", "text");
			input.addAttribute("style", "width:100%");
			input.addAttribute("rule", rule);
			input.addAttribute("value", this.StrToDate(value));
			// log.debug(value+"   "+ this.StrToDate(value));
			input.addAttribute("onchange", jsTxt);
			if (tagReadonly.equals("true")) {
				input.addAttribute("readonly", tagReadonly);
			}
			input.addAttribute("onfocus", "setday(this)");
		} else if (tagType.equals("10")) {
			caption = root.addElement("th");
			caption.setText(title);
			field = root.addElement("td");
			Element span = field.addElement("span").addAttribute("id", tagName);
			// 单位特殊处理
			if (entity.attributeValue("column").equals("MAINWORKPLACE")) {
				// 父节点是简历时可以编辑
				if ("RESUME".equals(entity.getParent().getName())) {
					field.addElement("button").addAttribute("class", "btn")
							.addAttribute("style", "width:20%").addAttribute(
									"onclick", "addunit()").setText("选择工作单位");
				}
				span.addAttribute("style", "width:80%");
				Element hidden = span.addElement("input");
				hidden.addAttribute("title", title);
				hidden.addAttribute("name", tagName);
				hidden.addAttribute("type", "hidden");
				hidden.addAttribute("rule", rule);
				hidden.addAttribute("value", value);
				hidden.addAttribute("id", id);
				if (!"".equals(value)) {
					SysTUnitDAO unitdao = new SysTUnitDAO();
					SysTUnit unit = unitdao.findById(new Long(value));
					if (null == unit) {
						span.setText("");
					} else {
						span.setText(unit.getUnitname());
					}
				}
			} else if (tagDicsort != null) {
				if ("".equals(value)) {
					span.addAttribute("style", "color:#cccccc").setText("未填写");
				} else {

					span.setText(this.getDictionartHandle().getDictsortToValue(
							dictionary, value));
				}
			} else {
				if ("".equals(value)) {
					span.addAttribute("style", "color:#cccccc").setText("未填写");
				} else {
					span.setText(value);
				}
			}

		} else {

		}
		return root.elements();
	}

	public Document extractChildNode(String nodeName, String nodeId) {
		TreeHandle tree = new TreeHandleImpl();
		Document doc = tree.selectSingleEntity(nodeName, new Long(nodeId));
		Element current = (Element) doc.getRootElement().selectSingleNode(
				"//" + nodeName);
		Document temp = tree.getChild(nodeName);
		Iterator tempit = temp.getRootElement().elementIterator();
		while (tempit.hasNext()) {
			Element element = (Element) tempit.next();
			current.addElement(element.getName()).addAttribute("title",
					element.attributeValue("title")).addAttribute("fk", nodeId);
		}
		return doc;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private String StrToDate(String value) {
		try {
			Date date = null;
			SimpleDateFormat format1 = null;
			if (value == null || value.equals("")) {
			} else {
				if (value.length() > 11) {
					format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else {
					format1 = new SimpleDateFormat("yyyy-MM-dd");
				}
				date = format1.parse(value.trim());
				format1 = new SimpleDateFormat("yyyy-MM-dd");
				value = format1.format(date);
			}
		} catch (ParseException e) {
		}
		return value;
	}

	public String StrToDate(String value, String frt) {
		try {
			Date date = null;
			SimpleDateFormat format1 = null;
			if (value == null || value.equals("")) {
			} else {
				if (value.length() > 11) {
					format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else {
					format1 = new SimpleDateFormat("yyyy-MM-dd");
				}
				date = format1.parse(value.trim());
				format1 = new SimpleDateFormat(frt);
				value = format1.format(date);
			}
		} catch (ParseException e) {
		}
		return value;
	}

	public String DateToDate(Date date, String frt) {
		SimpleDateFormat format1 = null;
		if (date == null) {
		} else {
			format1 = new SimpleDateFormat("yyyyMMdd");
			return format1.format(date);

		}

		return "";
	}

	/**
	 * 
	 * @return
	 */
	private Document getDocument() {
		// 创建文档,并写问结构与实体内容
		Document doc = DocumentHelper.createDocument();
		// 设置XML编码
		doc.setXMLEncoding("GB18030");
		doc.addElement("tbody");
		return doc;
	}

	public DictionaryHandle getDictionartHandle() {
		DictionaryHandle dictionartHandle = new DictionaryHandle();
		return dictionartHandle;
	}

	public String convertPaperidToBirth(String paperid) {
		String birth = "";
		if (paperid.length() == 15) {
			birth = "19" + paperid.substring(6, 8) + "-"
					+ paperid.substring(8, 10) + "-"
					+ paperid.substring(10, 12);
		} else if (paperid.length() == 18) {
			birth = paperid.substring(6, 10) + "-" + paperid.substring(10, 12)
					+ "-" + paperid.substring(12, 14);
		} else {
		}
		return birth;
	}

	public int getAgeByIdCard(String idcard) {
		Calendar ca = Calendar.getInstance();
		int nowYear = ca.get(Calendar.YEAR);
		int nowMonth = ca.get(Calendar.MONTH) + 1;
		int len = idcard.length();
		if (len == 18) {
			int IDYear = Integer.parseInt(idcard.substring(6, 10));
			int IDMonth = Integer.parseInt(idcard.substring(10, 12));
			if ((IDMonth - nowMonth) > 0) {
				return nowYear - IDYear - 1;
			} else {
				return nowYear - IDYear;
			}
		} else if (len == 15) {
			int IDYear = Integer.parseInt(idcard.substring(6, 8));
			int IDMonth = Integer.parseInt(idcard.substring(8, 10));
			if ((IDMonth - nowMonth) > 0) {
				return nowYear - IDYear - 1 - 1900;
			} else {
				return nowYear - IDYear - 1900;
			}
		} else {
			return 0;
		}

	}

	public String getFullname(String organization_id) {
		return new SysTOrganizationDAO().findById(organization_id)
				.getFullname();

	}

	public String getNewFamno(String orgid ,Connection con) throws Exception {
		String famno = "kkk";
		Log4jApp.logger("2:" + con.hashCode());
		String procedure = "{call generatefamno(?,?)}";
		CallableStatement cstmt;
		try {
			cstmt = con.prepareCall(procedure);
			cstmt.setString(1, orgid);
			cstmt.setString(2, famno);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR,
					"info_t_family.familyno%type");
			cstmt.execute();
			famno = cstmt.getString(2);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}
		return famno;
	}
}
