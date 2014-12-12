package com.mingda.common.node;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.SessionFactory;
import com.mingda.dao.SysTDictionaryDAO;
import com.mingda.dao.SysTInfologDAO;
import com.mingda.dao.SysTTableDAO;
import com.mingda.entity.SysTEmployee;
import com.mingda.entity.SysTField;
import com.mingda.entity.SysTInfolog;
import com.mingda.entity.SysTTable;

public class TreeHandleImpl extends BaseHibernateDAO implements TreeHandle {
	static Logger logger = Logger.getLogger(TreeHandleImpl.class);
	private static final int INSERT = 1;
	private static final int UPDATE = 2;
	private static final int SELECTENTITYID = 3;
	private static final int SELECTPARENTID = 4;
	private SysTTableDAO systtabledao = new SysTTableDAO();
	private Document document;

	public TreeHandleImpl() {
		this.document = loadTree();
	}

	public TreeHandleImpl(Document document) {
		this.document = document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.common.node.TreeHandle#loadTree()
	 */
	@SuppressWarnings("unchecked")
	public Document loadTree() {
		Session session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		Document doc = createRoot();
		try {
			tx.begin();
			Iterator<SysTTable> it = systtabledao.findAll().iterator();
			while (it.hasNext()) {
				SysTTable systtable = it.next();
				Element curNode = ((Branch) doc.selectSingleNode(systtable
						.getPath())).addElement(systtable.getCode());
				curNode.addAttribute("nodeid", systtable.getTableId()
						.toString());// 节点ID
				curNode.addAttribute("title", systtable.getLogicname());// 逻辑名称
				curNode.addAttribute("table", systtable.getPhysicalname());// 实体名称
				evalProperty(curNode, systtable);// 生成属性节点
			}
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		return doc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.common.node.TreeHandle#getBranch()
	 */
	@SuppressWarnings("rawtypes")
	public Document getBranch(String code) {
		// Document document = createRoot();
		Document doc = createRoot();
		Iterator it = document.selectNodes("//" + code).iterator();
		Element root = doc.getRootElement();
		if (it.hasNext()) {
			Element element = (Element) it.next();
			root.add((Element) element.clone());
		}
		return doc;
	}

	@SuppressWarnings("unused")
	private Document getParent(String code) {
		Document doc = createRoot();
		Element srcroot = (Element) document.selectSingleNode("//" + code);
		Element root = doc.getRootElement();
		if (srcroot != null) {
			Element element = (Element) srcroot.getParent();
			root.add((Element) element.clone());
		}
		return doc;
	}

	@SuppressWarnings("rawtypes")
	public Document getChild(String code) {
		Document doc = createRoot();
		Element srcroot = (Element) document.selectSingleNode("//" + code);
		Iterator it = srcroot.elementIterator();
		Element objroot = doc.getRootElement();
		while (it.hasNext()) {
			Element srcnode = (Element) it.next();
			if (!srcnode.getName().equals("property")) {
				Element objnode = objroot.addElement(srcnode.getName());
				for (Iterator i = srcnode.attributeIterator(); i.hasNext();) {
					Attribute attribute = (Attribute) i.next();
					objnode.addAttribute(attribute.getName(), attribute
							.getText());
				}
				Iterator itchild = srcnode.elementIterator();
				while (itchild.hasNext()) {
					Element childsrc = (Element) itchild.next();
					if (childsrc.getName().equals("property")) {
						Element childobj = objnode.addElement(childsrc
								.getName());
						for (Iterator i = childsrc.attributeIterator(); i
								.hasNext();) {
							Attribute attribute = (Attribute) i.next();
							childobj.addAttribute(attribute.getName(),
									attribute.getText());
						}
					}
				}
			}
		}
		return doc;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.common.node.TreeHandle#getNode()
	 */
	@SuppressWarnings("rawtypes")
	public Element getNode(Element root, String code) {
		Element curNode = null;
		Iterator it = document.selectNodes("//" + code).iterator();
		if (it.hasNext()) {
			Element element = (Element) it.next();
			curNode = root.addElement(element.getName());
			for (Iterator i = element.attributeIterator(); i.hasNext();) {
				Attribute attribute = (Attribute) i.next();
				curNode.addAttribute(attribute.getName(), attribute.getText());
			}
			for (Iterator i = element.elementIterator("property"); i.hasNext();) {
				Element property = (Element) i.next();
				curNode.add((Element) property.clone());
			}
		}
		return curNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mingda.common.node.TreeHandle#selectSingleEntity(java.lang.String,
	 * java.lang.Long)
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public Document selectSingleEntity(String code, Long entityId) {
		Document rdoc = this.createRoot();
		Element rroot = rdoc.getRootElement();
		Document doc = this.createRoot();
		String sql = this.createSQL(code, SELECTENTITYID);
		Element element = getNode(doc.getRootElement(), code);
		if (entityId != null) {
			try {
				PreparedStatement ps = getSession().connection()
						.prepareStatement(sql);
				ps.setLong(1, entityId);
				ResultSet rs = ps.executeQuery();
				/**
				 * 改2008-09-22
				 */
				Document base = DocumentHelper.createDocument();
				Element baseroot = base.addElement("root");
				int lum = rs.getMetaData().getColumnCount();
				String[] colnames = new String[lum];
				for (int c = 1; c <= lum; c++) {
					colnames[c - 1] = rs.getMetaData().getColumnName(c);
				}
				if (rs.next()) {
					Element row = baseroot.addElement("row");
					for (int c = 1; c <= lum; c++) {
						Element col = row.addElement("col").addAttribute(
								"column", colnames[c - 1]);
						String value = rs.getString(c);
						if (null == value || "".equals(value)
								|| "null".equals(value)) {
							value = "";
						}
						col.setText(value);
					}
				}
				rs.close();
				ps.close();
				SysTDictionaryDAO dicdao = new SysTDictionaryDAO();
				for (Iterator it = baseroot.elementIterator(); it.hasNext();) {
					Element row = (Element) it.next();
					String caption = "";
					for (Iterator colit = row.elementIterator(); colit
							.hasNext();) {
						String content = "";
						Element e = (Element) colit.next();
						Element temp = (Element) element
								.selectSingleNode("//property[@column='"
										+ e.attributeValue("column") + "']");
						if (temp.attributeValue("status").equals("1")) {
							content = e.getText();
							temp.setText(content);
						}
						Attribute iscaption = temp.attribute("iscaption");
						Attribute dicsort = temp.attribute("dicsort");
						if (iscaption != null
								&& iscaption.getText().equals("1")) {
							String cap = null;

							if (dicsort != null) {
								if (temp.getText() != null
										&& !temp.getText().equals("")) {
									cap = dicdao.findById(
											new Long(temp.getText())).getItem();
								}
							} else {
								cap = temp.getText();
							}
							if (caption == null || caption.equals("")) {
								caption = cap;
							} else {
								caption += "," + cap;
							}
						}
					}
					element.addAttribute("caption", caption);
					rroot.add((Element) element.clone());
				}
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rroot.elements().isEmpty()) {
			return doc;
		} else {
			return rdoc;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.common.node.TreeHandle#selectEntities(java.lang.String,
	 * java.lang.Long)
	 */
	public Document selectEntities(String code, Long parentId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Document doc = this.createRoot();
		Element root = doc.getRootElement();
		Document rdoc = this.createRoot();
		Element rroot = rdoc.getRootElement();
		String sql = this.createSQL(code, SELECTPARENTID);
		try {
			if (parentId != null) {
				ps = getSession().connection().prepareStatement(sql);
				ps.setLong(1, parentId);
				rs = ps.executeQuery();
				Document base = DocumentHelper.createDocument();
				Element baseroot = base.addElement("root");
				int lum = rs.getMetaData().getColumnCount();
				String[] colnames = new String[lum];
				for (int c = 1; c <= lum; c++) {
					colnames[c - 1] = rs.getMetaData().getColumnName(c);
				}
				while (rs.next()) {
					Element row = baseroot.addElement("row");
					for (int c = 1; c <= lum; c++) {
						Element col = row.addElement("col").addAttribute(
								"column", colnames[c - 1]);
						String value = rs.getString(c);
						if (null == value || "".equals(value)
								|| "null".equals(value)) {
							value = "";
						}
						col.setText(value);
					}
				}
				rs.close();
				ps.close();
				SysTDictionaryDAO dicdao = new SysTDictionaryDAO();
				Element element = getNode(root, code);
				for (Iterator it = baseroot.elementIterator(); it.hasNext();) {
					Element row = (Element) it.next();
					String caption = "";
					for (Iterator colit = row.elementIterator(); colit
							.hasNext();) {
						String content = "";
						Element e = (Element) colit.next();
						Element temp = (Element) element
								.selectSingleNode("//property[@column='"
										+ e.attributeValue("column") + "']");
						if (temp.attributeValue("status").equals("1")) {
							content = e.getText();
							temp.setText(content);
						}
						Attribute iscaption = temp.attribute("iscaption");
						Attribute dicsort = temp.attribute("dicsort");
						if (iscaption != null
								&& iscaption.getText().equals("1")) {
							String cap = null;

							if (dicsort != null) {
								if (temp.getText() != null
										&& !temp.getText().equals("")) {
									cap = dicdao.findById(
											new Long(temp.getText())).getItem();
								}
							} else {
								cap = temp.getText();
							}
							if (caption == null || caption.equals("")) {
								caption = cap;
							} else {
								caption += "," + cap;
							}
						}
					}
					element.addAttribute("caption", caption);
					rroot.add((Element) element.clone());
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rroot.elements().isEmpty()) {
			return doc;
		} else {
			return rdoc;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.common.node.TreeHandle#saveEntity(org.dom4j.Document)
	 */
	public Document saveEntity(Document doc, Long employeeId) {
		String sql;
		Element root = doc.getRootElement();// 取到传入文档的根节点
		Document newdoc = createRoot();
		// 对根节点进行迭代，处理实体内容
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element entity = (Element) i.next(); // 取出数据实体
			Element struct = getNode(newdoc.getRootElement(), entity.getName());// 取出对应结构节点
			Element curNode = this.evalNodeValue(struct, entity);
			try {
				Element id = (Element) curNode
						.selectSingleNode("property[@isprimary='true']");// 取出字段节点
				Document oldentity = null;
				if (id.getText() != null && !id.getText().equals("")) {
					oldentity = this.selectSingleEntity(entity.getName(),
							new Long(id.getText()));
				}
				if (id.getText() == null || id.getText().equals("")) {
					sql = this.createSQL(curNode.getName(), 1);
					id.setText(systtabledao.getPrimary().toString());
				} else {
					sql = this.createSQL(curNode.getName(), 2);
				}
				for (Iterator it = curNode.elementIterator("property"); it
						.hasNext();) {
					Element field = (Element) it.next();
					String content = field.getText().replaceAll("'", "");
					sql = sql.replace(":" + field.attributeValue("column")
							+ " ", "'" + content + "'");
				}
				logger.debug("实体保存语句：" + sql);
				Transaction ts = getSession().beginTransaction();
				try {
					PreparedStatement ps = getSession().connection()
							.prepareStatement(sql);
					ps.execute();
					ps.close();
					// 所属家庭ID
					Long familyId = null;
					// 汇总字段保存
					Node pathnode = document.selectSingleNode("//"
							+ entity.getName());
					if (pathnode != null) {
						Long entityId = new Long(id.getText());
						String[] path = pathnode.getPath().split("/");
						for (int x = path.length - 1; x > 1; x--) {
							if (null != path[x] && "FAMILY".equals(path[x])) {
								familyId = new Long(entityId);
							}
							entityId = countfieldSave(path[x], entityId);
						}
					}
					// 保存信息实体日志
					Document newentity = selectSingleEntity(curNode.getName(),
							new Long(id.getText()));// 重新取出最新的数据实体
					// 比较两个新旧两个数据实体，如果有改动，对新实体中改动的字段增加一个alter属性，属性值为１，
					// 增加原值属性oldvalue属性值为原值
					if (this.compareProperty(newentity, oldentity)) {
						String log = newentity.asXML();
						SysTEmployee emp = new SysTEmployee();
						emp.setEmployeeId(employeeId);
						new SysTInfologDAO().save(new SysTInfolog(emp,
								new Date(), familyId, curNode.getName(),
								new Long(id.getText()), log));
					}
					ts.commit();
				} catch (SQLException e) {
					ts.rollback();
					e.printStackTrace();
				}

			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return newdoc;
	}

	/**
	 * 创建一个根节点
	 * 
	 * @return
	 */
	private Document createRoot() {
		// 创建文档,并写问结构与实体内容
		Document doc = DocumentHelper.createDocument();
		// 设置XML编码
		doc.setXMLEncoding("GB18030");
		doc.addElement("ROOT");
		return doc;
	}

	private boolean compareProperty(Document newentity, Document oldentity) {
		boolean flag = false;
		Iterator it = newentity.getRootElement().selectNodes("//property")
				.iterator();
		if (oldentity == null) {
			while (it.hasNext()) {
				Element e = (Element) it.next();
				e.addAttribute("alter", "1");
				e.addAttribute("oldvalue", "");
			}
			flag = true;
		} else {
			while (it.hasNext()) {
				Element e = (Element) it.next();
				Element o = (Element) oldentity
						.selectSingleNode("//property[@column='"
								+ e.attributeValue("column") + "']");
				if (o != null && !e.getText().equals(o.getText())) {
					e.addAttribute("alter", "1");
					e.addAttribute("oldvalue", o.getText());
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 节点属性赋值
	 * 
	 * @param element
	 * @param table
	 * @return
	 */
	private Element evalProperty(Element element, SysTTable table) {
		Iterator<SysTField> it = table.getSysTFields().iterator();
		while (it.hasNext()) {
			SysTField field = it.next();
			// 增加属性子节点(如果是主键，名称使用id，否则名称使用property)
			Element curNode = element.addElement("property");
			// 当前字段是否为主键
			if (field.getIsprimary() != null
					&& field.getIsprimary().equals("1")) {
				curNode.addAttribute("isprimary", "true");
			}
			// 当前字段是否为外键
			if (field.getIsforeign() != null
					&& field.getIsforeign().equals("1")) {
				curNode.addAttribute("isforeign", "true");
			}
			// 字段的中文名称(在界面上显的项目名称)
			curNode.addAttribute("title", field.getLogicname());
			// 对应的实体模型中的数据列
			curNode.addAttribute("column", field.getPhysicalname());
			// 对应实体模型中的数据类型（0:字符串 1:整数 2:数值 3:日期时间 4:枚举）
			curNode.addAttribute("type", field.getFieldtype().toString());
			if (field.getFieldtype().intValue() == 4) {
				// 使用的字典类型,对应字典类型表中的字典类型ID
				curNode.addAttribute("dicsort", field.getDicsort().toString());
			}
			// 实体模型中的字段最大长度
			curNode.addAttribute("length", field.getLength().toString());
			// 实体字段合使用的默认值
			curNode.addAttribute("default", field.getDefults());
			// 界面使用的控件,用户界面上使用的控件类型（0：隐藏域、1：文本框、2：下拉列表、3：日期控件）如果没有默认为隐藏域
			curNode.addAttribute("control", field.getControl().toString());
			// 使用的验证规则,对应字典,从字典中取值,对应JS代码
			curNode.addAttribute("rule", field.getProofrule().toString());
			// 实际字段是否为只读属性1：只读，0：非只读
			curNode.addAttribute("readonly", field.getReadonly());
			// 是否在显示在项目中 显示1:显示,0:不显示
			curNode.addAttribute("isvisible", field.getVisible());
			// 是否在主节点上显示1:显示,0:不显示
			curNode.addAttribute("iscaption", field.getIscaption());
			// 是否在列表上显示1:显示,0:不显示
			curNode.addAttribute("islist", field.getIslist());
			// 节点说明
			curNode.addAttribute("explain", field.getExplains());
			// 节点状态属性
			curNode.addAttribute("status", field.getStatus());
			// 是否为汇总字段
			curNode.addAttribute("computeflag", field.getComputeflag());
			// 汇总字段使用的SQL
			curNode.addAttribute("computesql", field.getComputesql());
			// 添回节点注释
			curNode.addComment(field.getExplains() != null ? field
					.getExplains() : "");
		}
		return element;
	}

	private Element evalNodeValue(Element target, Element source) {
		for (Iterator it = target.elementIterator("property"); it.hasNext();) {
			Element field = (Element) it.next();
			String colunm = field.attributeValue("column");
			Element value = (Element) source
					.selectSingleNode("property[@column='" + colunm + "']");
			if (value != null) {
				field.setText(value.getText());
			}
		}
		return target;
	}

	/**
	 * 生成使用的SQL <br>
	 * 1:插入语句,参数由字段集合决定<br>
	 * 2:更新语句,参数由字段集合决定,最后多加一个实体主键ID<br>
	 * 3:按主键查询时,参数只有一个,是数据实体的主键<br>
	 * 4:按父键查询时,参数只有一个,是数据实体的父键<br>
	 * 
	 * @param code
	 *            节点代码
	 * @param qsqlTypelType
	 *            生成的SQL类型
	 * @return
	 */
	private String createSQL(String code, int sqlType) {
		String sql = null;
		String fields = null;
		String values = null;
		String pkName = null;
		String fkName = null;
		String sequence = null;
		Document tempdoc = createRoot();
		// 取回节点的内容
		Element node = getNode(tempdoc.getRootElement(), code);
		// 取主键名称
		Element pkNode = ((Element) node
				.selectSingleNode("property[@isprimary='true']"));
		pkName = (pkNode == null) ? "" : pkNode.attributeValue("column");
		// 取外键名称
		Element fkNode = ((Element) node
				.selectSingleNode("property[@isforeign='true']"));
		fkName = (fkNode == null) ? "" : fkNode.attributeValue("column");
		for (Iterator i = node.elementIterator("property"); i.hasNext();) {
			Element element = (Element) i.next();
			String column = element.attributeValue("column");
			String type = element.attributeValue("type");
			switch (sqlType) {
			case INSERT:
				fields = (fields == null) ? column : fields + "," + column;
				if (type != null && type.equals("3")) {
					values = (values == null) ? "to_date(:" + column
							+ " ,'yyyy-mm-dd hh:mi:ss')" : values
							+ ",to_date(:" + column
							+ " ,'yyyy-mm-dd hh:mi:ss')";
				} else {
					values = (values == null) ? ":" + column + " " : values
							+ ",:" + column + " ";
				}

				break;
			case UPDATE:
				if (!column.equals(pkName)) {
					if (type != null && type.equals("3")) {
						fields = (fields == null) ? column + " = to_date(:"
								+ column + " ,'yyyy-mm-dd hh:mi:ss')" : fields
								+ "," + column + " = to_date(:" + column
								+ " ,'yyyy-mm-dd hh:mi:ss')";
					} else {
						fields = (fields == null) ? column + " = :" + column
								+ " " : fields + "," + column + " = :" + column
								+ " ";
					}
				}
				break;
			case SELECTENTITYID:
			case SELECTPARENTID:
				fields = (fields == null) ? column : fields + "," + column;
				break;
			default:
				fields = "";
				values = "";
			}
		}
		if ("INFO_T_MEMBER".equals(node.attributeValue("table"))) {
			sequence = "order by relmaster";
		} else {
			sequence = "";
		}
		switch (sqlType) {
		case INSERT:
			// 插入语句
			sql = " insert into " + node.attributeValue("table") + "(" + fields
					+ ") values ( " + values + ")";
			break;
		case UPDATE:// 更新语句

			sql = " update " + node.attributeValue("table") + " set " + fields
					+ " where " + pkName + "= :" + pkName + " ";
			break;
		case SELECTENTITYID:
			// 按主键查询
			sql = " select " + fields + " from " + node.attributeValue("table")
					+ " where " + pkName + "= :" + pkName + "  " + sequence
					+ "  ";
			break;
		case SELECTPARENTID:
			// 按父级实体ID查询
			sql = " select " + fields + " from " + node.attributeValue("table")
					+ " where " + fkName + "= :" + pkName + "  " + sequence
					+ "  ";
			break;
		default:
			sql = "";
			break;
		}
		logger.debug("生成语句:" + sql);
		return sql;
	}

	private Long countfieldSave(String code, Long entityId)
			throws HibernateException, SQLException {
		Document doc = selectSingleEntity(code, entityId);
		Element entity = (Element) doc.selectSingleNode("//" + code);
		String primary = ((Element) entity
				.selectSingleNode("//property[@isprimary='true']"))
				.attributeValue("column");
		Node parentId = entity
				.selectSingleNode("//property[@isforeign='true']");
		String tablename = ((Element) entity.selectSingleNode("//" + code))
				.attributeValue("table");
		Iterator it = entity.elementIterator();
		while (it.hasNext()) {
			Element property = (Element) it.next();
			if (property.attributeValue("computeflag") != null
					&& property.attributeValue("computeflag").equals("1")) {
				String sql = "update " + tablename + " set "
						+ property.attributeValue("column") + " = ("
						+ property.attributeValue("computesql") + ") where "
						+ primary + "=" + entityId;
				logger.debug("汇总字段：" + sql);
				PreparedStatement ps = getSession().connection()
						.prepareStatement(sql); // 根据sql创建PreparedStatement
				ps.executeUpdate();
				ps.close();
			}
		}
		if (parentId != null)
			return new Long(parentId.getText());
		else
			return null;
	}

	public Document getEntityLogs(String code, Long entityid) {
		Element root = this.createRoot().getRootElement();
		Iterator it = new SysTInfologDAO()
				.findByCodeAndEntityid(code, entityid).iterator();
		while (it.hasNext()) {
			SysTInfolog log = (SysTInfolog) it.next();
			try {
				Element entity = (Element) DocumentHelper.parseText(
						log.getLog()).getRootElement().element(code);
				entity.addAttribute("familyid", log.getFamilyid().toString());
				entity.addAttribute("operator", log.getSysTEmployee()
						.getEmployeeId().toString());
				entity.addAttribute("logid", log.getInfologId().toString());
				entity.addAttribute("recordtime",
						new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(log.getLogtime()));
				logger.debug("取出的实体内容:" + entity.asXML());
				root.add((Element) entity.clone());
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return root.getDocument();
	}

	public Document getLogAsXML(Long infologId) {
		Element root = this.createRoot().getRootElement();
		SysTInfolog log = new SysTInfologDAO().findById(infologId);
		try {
			if (log != null) {
				Element entity = DocumentHelper.parseText(log.getLog())
						.getRootElement().element(log.getCode());
				entity.addAttribute("familyid", log.getFamilyid().toString());
				entity.addAttribute("operator", log.getSysTEmployee()
						.getEmployeeId().toString());
				entity.addAttribute("logid", log.getInfologId().toString());
				entity.addAttribute("recordtime",
						new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(log.getLogtime()));
				root.add((Element) entity.clone());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return root.getDocument();
	}
}
