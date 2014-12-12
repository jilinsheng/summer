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
						.toString());// �ڵ�ID
				curNode.addAttribute("title", systtable.getLogicname());// �߼�����
				curNode.addAttribute("table", systtable.getPhysicalname());// ʵ������
				evalProperty(curNode, systtable);// �������Խڵ�
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
				 * ��2008-09-22
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
		Element root = doc.getRootElement();// ȡ�������ĵ��ĸ��ڵ�
		Document newdoc = createRoot();
		// �Ը��ڵ���е���������ʵ������
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element entity = (Element) i.next(); // ȡ������ʵ��
			Element struct = getNode(newdoc.getRootElement(), entity.getName());// ȡ����Ӧ�ṹ�ڵ�
			Element curNode = this.evalNodeValue(struct, entity);
			try {
				Element id = (Element) curNode
						.selectSingleNode("property[@isprimary='true']");// ȡ���ֶνڵ�
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
				logger.debug("ʵ�屣����䣺" + sql);
				Transaction ts = getSession().beginTransaction();
				try {
					PreparedStatement ps = getSession().connection()
							.prepareStatement(sql);
					ps.execute();
					ps.close();
					// ������ͥID
					Long familyId = null;
					// �����ֶα���
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
					// ������Ϣʵ����־
					Document newentity = selectSingleEntity(curNode.getName(),
							new Long(id.getText()));// ����ȡ�����µ�����ʵ��
					// �Ƚ������¾���������ʵ�壬����иĶ�������ʵ���иĶ����ֶ�����һ��alter���ԣ�����ֵΪ����
					// ����ԭֵ����oldvalue����ֵΪԭֵ
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
	 * ����һ�����ڵ�
	 * 
	 * @return
	 */
	private Document createRoot() {
		// �����ĵ�,��д�ʽṹ��ʵ������
		Document doc = DocumentHelper.createDocument();
		// ����XML����
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
	 * �ڵ����Ը�ֵ
	 * 
	 * @param element
	 * @param table
	 * @return
	 */
	private Element evalProperty(Element element, SysTTable table) {
		Iterator<SysTField> it = table.getSysTFields().iterator();
		while (it.hasNext()) {
			SysTField field = it.next();
			// ���������ӽڵ�(���������������ʹ��id����������ʹ��property)
			Element curNode = element.addElement("property");
			// ��ǰ�ֶ��Ƿ�Ϊ����
			if (field.getIsprimary() != null
					&& field.getIsprimary().equals("1")) {
				curNode.addAttribute("isprimary", "true");
			}
			// ��ǰ�ֶ��Ƿ�Ϊ���
			if (field.getIsforeign() != null
					&& field.getIsforeign().equals("1")) {
				curNode.addAttribute("isforeign", "true");
			}
			// �ֶε���������(�ڽ������Ե���Ŀ����)
			curNode.addAttribute("title", field.getLogicname());
			// ��Ӧ��ʵ��ģ���е�������
			curNode.addAttribute("column", field.getPhysicalname());
			// ��Ӧʵ��ģ���е��������ͣ�0:�ַ��� 1:���� 2:��ֵ 3:����ʱ�� 4:ö�٣�
			curNode.addAttribute("type", field.getFieldtype().toString());
			if (field.getFieldtype().intValue() == 4) {
				// ʹ�õ��ֵ�����,��Ӧ�ֵ����ͱ��е��ֵ�����ID
				curNode.addAttribute("dicsort", field.getDicsort().toString());
			}
			// ʵ��ģ���е��ֶ���󳤶�
			curNode.addAttribute("length", field.getLength().toString());
			// ʵ���ֶκ�ʹ�õ�Ĭ��ֵ
			curNode.addAttribute("default", field.getDefults());
			// ����ʹ�õĿؼ�,�û�������ʹ�õĿؼ����ͣ�0��������1���ı���2�������б�3�����ڿؼ������û��Ĭ��Ϊ������
			curNode.addAttribute("control", field.getControl().toString());
			// ʹ�õ���֤����,��Ӧ�ֵ�,���ֵ���ȡֵ,��ӦJS����
			curNode.addAttribute("rule", field.getProofrule().toString());
			// ʵ���ֶ��Ƿ�Ϊֻ������1��ֻ����0����ֻ��
			curNode.addAttribute("readonly", field.getReadonly());
			// �Ƿ�����ʾ����Ŀ�� ��ʾ1:��ʾ,0:����ʾ
			curNode.addAttribute("isvisible", field.getVisible());
			// �Ƿ������ڵ�����ʾ1:��ʾ,0:����ʾ
			curNode.addAttribute("iscaption", field.getIscaption());
			// �Ƿ����б�����ʾ1:��ʾ,0:����ʾ
			curNode.addAttribute("islist", field.getIslist());
			// �ڵ�˵��
			curNode.addAttribute("explain", field.getExplains());
			// �ڵ�״̬����
			curNode.addAttribute("status", field.getStatus());
			// �Ƿ�Ϊ�����ֶ�
			curNode.addAttribute("computeflag", field.getComputeflag());
			// �����ֶ�ʹ�õ�SQL
			curNode.addAttribute("computesql", field.getComputesql());
			// ��ؽڵ�ע��
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
	 * ����ʹ�õ�SQL <br>
	 * 1:�������,�������ֶμ��Ͼ���<br>
	 * 2:�������,�������ֶμ��Ͼ���,�����һ��ʵ������ID<br>
	 * 3:��������ѯʱ,����ֻ��һ��,������ʵ�������<br>
	 * 4:��������ѯʱ,����ֻ��һ��,������ʵ��ĸ���<br>
	 * 
	 * @param code
	 *            �ڵ����
	 * @param qsqlTypelType
	 *            ���ɵ�SQL����
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
		// ȡ�ؽڵ������
		Element node = getNode(tempdoc.getRootElement(), code);
		// ȡ��������
		Element pkNode = ((Element) node
				.selectSingleNode("property[@isprimary='true']"));
		pkName = (pkNode == null) ? "" : pkNode.attributeValue("column");
		// ȡ�������
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
			// �������
			sql = " insert into " + node.attributeValue("table") + "(" + fields
					+ ") values ( " + values + ")";
			break;
		case UPDATE:// �������

			sql = " update " + node.attributeValue("table") + " set " + fields
					+ " where " + pkName + "= :" + pkName + " ";
			break;
		case SELECTENTITYID:
			// ��������ѯ
			sql = " select " + fields + " from " + node.attributeValue("table")
					+ " where " + pkName + "= :" + pkName + "  " + sequence
					+ "  ";
			break;
		case SELECTPARENTID:
			// ������ʵ��ID��ѯ
			sql = " select " + fields + " from " + node.attributeValue("table")
					+ " where " + fkName + "= :" + pkName + "  " + sequence
					+ "  ";
			break;
		default:
			sql = "";
			break;
		}
		logger.debug("�������:" + sql);
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
				logger.debug("�����ֶΣ�" + sql);
				PreparedStatement ps = getSession().connection()
						.prepareStatement(sql); // ����sql����PreparedStatement
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
				logger.debug("ȡ����ʵ������:" + entity.asXML());
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
