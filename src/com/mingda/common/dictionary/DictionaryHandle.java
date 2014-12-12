package com.mingda.common.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.dao.SysTDictionaryDAO;
import com.mingda.dao.SysTDictsortDAO;
import com.mingda.entity.SysTDictionary;
import com.mingda.entity.SysTDictsort;

/**
 * 
 * @author wangjia
 * 
 */
public class DictionaryHandle {
	static Logger log = Logger.getLogger(DictionaryHandle.class);
	/**
	 * 
	 * @param list
	 *            字典列表
	 * @return dictionary 字典列表XML文档
	 *         <DICTIONARY><DICTSORT><DICTVALUE></DICTVALUE>
	 *         </DICTSORT></DICTIONARY>
	 */
	@SuppressWarnings("rawtypes")
	public Document createDictionaryXML() {
		Session session = null;
		session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List dslist = null;
		List dvlist = null;
		try {
			tx.begin();
			SysTDictsortDAO sysTDictsortDAO = new SysTDictsortDAO();
			dslist = sysTDictsortDAO.findForUseful();
			SysTDictionaryDAO sysTDictionaryDAO = new SysTDictionaryDAO();
			dvlist = sysTDictionaryDAO.findAll();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
			log.debug("hibernate关闭连接："+session.isOpen());
		}
		
		/*
		 * 字典值xml
		 */
		Document dictionaryvalue = DocumentHelper.createDocument();
		dictionaryvalue.setXMLEncoding("GB18030");
		Element dvroot = dictionaryvalue.addElement("DICTIONARYVALUE");
		Iterator dvit = dvlist.iterator();
		while (dvit.hasNext()) {
			SysTDictionary sysTDictionary = (SysTDictionary) dvit.next();
			Element dv = dvroot.addElement("DICTVALUE");
			dv.addAttribute("dsid", sysTDictionary.getSysTDictsort()
					.getDictsortId().toString());
			dv.addAttribute("id", sysTDictionary.getDictionaryId().toString());
			dv.setText(sysTDictionary.getItem());
			dv.addAttribute("title", sysTDictionary.getItem());
			dv.addAttribute("status", sysTDictionary.getStatus());
			dv.addAttribute("sequence", new Long(sysTDictionary.getSequence())
					.toString());
		}
		/*
		 * 字典表xml
		 */
		Document dictionary = DocumentHelper.createDocument();
		dictionary.setXMLEncoding("GB18030");
		Element root = dictionary.addElement("DICTVALUE");
		root.addComment("An XML dictionary");
		Iterator dsit = dslist.iterator();
		while (dsit.hasNext()) {
			Element dsElement = root.addElement("DICTSORT");
			SysTDictsort sysTDictsort = (SysTDictsort) dsit.next();
			dsElement.addAttribute("id", sysTDictsort.getDictsortId()
					.toString());
			dsElement.addAttribute("title", sysTDictsort.getName());
			dsElement.addAttribute("code", sysTDictsort.getCode());
			dsElement.addAttribute("isread", sysTDictsort.getIsread());
			dsElement.addAttribute("status", sysTDictsort.getStatus());
			Iterator dvitxml = dvroot.selectNodes(
					"//DICTVALUE[@dsid='"
							+ sysTDictsort.getDictsortId().toString() + "']")
					.iterator();
			while (dvitxml.hasNext()) {
				Node node = (Node) dvitxml.next();
				Node dvnode = (Node) node.clone();
				dsElement.add((Element) dvnode);
			}
		}
		return dictionary;
	}

	/**
	 * 
	 * @param doc
	 *            字典表形成的xml
	 * @return 根节点
	 */
	public Element getRoot(Document doc) {
		return doc.getRootElement();
	}

	/**
	 * 通过code值找到字典表类别
	 * 
	 * @param doc
	 *            xml文档
	 * @param code
	 *            字典表类别值
	 * @return 字典表一个类别
	 */
	@SuppressWarnings("unused")
	public Element getDictsort(Document doc, String code) {
		Element dictsort = null;
		Element root = this.getRoot(doc);
		Log4jApp.logger(root.getPath() + "/DICTSORT/@id");
		Element node = (Element) root.selectSingleNode(root.getPath()
				+ "/DICTSORT[@id='" + code + "']");
		return node;
	}

	/**
	 * 
	 * @param doc
	 *            XML文档
	 * @param code
	 *            字典表类别值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getDictsortToOption(Document doc, String dsid,
			String selectedValue) {
		if (selectedValue == null || selectedValue.equals("")
				|| selectedValue.equals("null")) {
			selectedValue = "";
		}
		String html = "<option value=\"\">未选择</option>";
		Element ent = this.getDictsort(doc, dsid);
		Iterator dvit = ent.elementIterator();
		while (dvit.hasNext()) {
			Element dv = (Element) dvit.next();
			if (selectedValue.equals(dv.attributeValue("id").toString().trim())) {
				html = html + "<option SELECTED value=\""
						+ dv.attributeValue("id").toString().trim() + "\">"
						+ dv.attributeValue("title").toString().trim()
						+ "</option>";
			} else {
				html = html + "<option value=\""
						+ dv.attributeValue("id").toString().trim() + "\">"
						+ dv.attributeValue("title").toString().trim()
						+ "</option>";
			}
		}
		Log4jApp.logger(html);
		return html;
	}

	/**
	 * 
	 * @param doc
	 * @param dsid
	 * @param selectedValue
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Element getDictsortToXML(Document doc, String dsid, Object selected) {
		Log4jApp.logger("字典：" + dsid);
		String selectedValue = "";
		if (null != selected) {
			selectedValue = selected.toString();
		}
		if (selectedValue == null || selectedValue.equals("")
				|| selectedValue.equals("null")) {
			selectedValue = "";
		}
		Document ddoc = DocumentHelper.createDocument();
		Element root = ddoc.addElement("select");
		root.addElement("option").addAttribute("value", "0").setText("未选择");
		Element ent = this.getDictsort(doc, dsid);
		Iterator dvit = ent.elementIterator();
		while (dvit.hasNext()) {
			Element dv = (Element) dvit.next();
			if (dv.attributeValue("status").equals("1")) {
				Element opt = root.addElement("option");
				if (selectedValue.equals(dv.attributeValue("id").toString()
						.trim())) {
					opt.addAttribute("value", dv.attributeValue("id")
							.toString().trim());
					opt.setText(dv.attributeValue("title").toString().trim());
					opt.addAttribute("SELECTED", "");
				} else {
					opt.addAttribute("value", dv.attributeValue("id")
							.toString().trim());
					opt.setText(dv.attributeValue("title").toString().trim());
				}
			}
		}
		return root;
	}

	/**
	 * 
	 * @param doc
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SysTDictionary> getDictsortToArrayList(Document doc,
			String code) {
		ArrayList<SysTDictionary> list = new ArrayList<SysTDictionary>();
		Element ent = this.getDictsort(doc, code);
		Iterator<SysTDictionary> dvit = ent.elementIterator();
		SysTDictionary sysTDictionary = new SysTDictionary();
		sysTDictionary.setDictionaryId(new Long(0));
		sysTDictionary.setItem("未选择");
		list.add(sysTDictionary);
		while (dvit.hasNext()) {
			Element dv = (Element) dvit.next();
			sysTDictionary = new SysTDictionary();
			sysTDictionary.setDictionaryId(new Long(dv.attributeValue("id")));
			sysTDictionary.setItem(dv.attributeValue("title"));
			list.add(sysTDictionary);
		}
		return list;
	}

	public SysTDictionary saveDictvalue(SysTDictionary dictvalue) {
		Session session = null;
		session = SessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			SysTDictionaryDAO sysTDictionaryDAO = new SysTDictionaryDAO();
			dictvalue = sysTDictionaryDAO.save(dictvalue);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}finally{
			SessionFactory.closeSession();
		}
		return dictvalue;

	}

	/**
	 * 
	 * @param doc
	 * @param dvid
	 * @return
	 */
	public String getDictsortToValue(Document doc, String dvid) {
		String dv = "";
		if (dvid == null || dvid.equals("")) {

		} else {
			Node node = doc
					.selectSingleNode("/DICTVALUE/DICTSORT/DICTVALUE[@id='"
							+ dvid + "']");
			if (node == null) {
				dv = dvid;
			} else {
				if ("0".equals(dvid)) {
					dv = "未填写";
				} else {
					dv = node.getText();
				}
			}
		}
		return dv;
	}

	public static void main(String[] args) {
		DictionaryHandle dh = new DictionaryHandle();
		log.debug(dh.getDictsortToValue(dh.createDictionaryXML(),
				"110"));
	}
}
