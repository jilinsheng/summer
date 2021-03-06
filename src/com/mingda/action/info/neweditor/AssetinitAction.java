/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.neweditor;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.dictionary.DictionaryHandle;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTAssetDAO;
import com.mingda.common.ibatis.data.InfoTAsset;

/**
 * MyEclipse Struts Creation date: 04-15-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="asset" path="/page/info/neweditor/asset.jsp"
 */
public class AssetinitAction extends Action {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String familyId = request.getParameter("familyId");

		SqlMapClient client = SqlMapper.getSqlMapper();
		try {
			Document dictionary = (Document) this.servlet.getServletContext()
					.getAttribute("dictionary");

			DictionaryHandle dh = new DictionaryHandle();

			InfoTAssetDAO assetdao = new InfoTAssetDAO(client);

			client.startTransaction();

			InfoTAsset infoTAsset = new InfoTAsset();

			infoTAsset.setFamilyId(new BigDecimal(familyId));
			infoTAsset = assetdao.selectByPrimaryId(infoTAsset);

			if (null == infoTAsset) {
				infoTAsset = new InfoTAsset();
			}

			Document pagedoc = DocumentHelper.createDocument();
			Element dict = null;

			Element root = pagedoc.addElement("table");
			root.addAttribute("width", "99%").addAttribute("class", "table8")
					.addAttribute("cellpadding", "0").addAttribute(
							"cellspacing", "0");

			String assetId = "";
			if (null == infoTAsset.getAssetId()) {
				assetId = "";
			} else {
				assetId=infoTAsset.getAssetId().toString();
			}
			BigDecimal farmland = infoTAsset.getFarmland();
			if (null == farmland) {
				farmland = new BigDecimal(0);
			}
			BigDecimal paddyfield = infoTAsset.getPaddyfield();
			if (null == paddyfield) {
				paddyfield = new BigDecimal(0);
			}
			BigDecimal glebe = infoTAsset.getGlebe();
			if (null == glebe) {
				glebe = new BigDecimal(0);
			}
			BigDecimal ownerhouse = infoTAsset.getOwnerhouse();
			if (null == ownerhouse) {
				ownerhouse = new BigDecimal(101);
			}
			BigDecimal roomcnt = infoTAsset.getRoomcnt();
			if (null == roomcnt) {
				roomcnt = new BigDecimal(0);
			}
			BigDecimal buildarea = infoTAsset.getBuildarea();
			if (null == buildarea) {
				buildarea = new BigDecimal(0);
			}
			BigDecimal struct = infoTAsset.getStruct();
			if (null == struct) {
				struct = new BigDecimal(0);
			}
			String repose = infoTAsset.getRepose();
			if (null == repose) {
				repose = "";
			}
			BigDecimal producergoods = infoTAsset.getProducergoods();
			if (null == producergoods) {
				producergoods = new BigDecimal(0);
			}
			BigDecimal animal = infoTAsset.getAnimal();
			if (null == animal) {
				animal = new BigDecimal(0);
			}
			BigDecimal farmmachine = infoTAsset.getFarmmachine();
			if (null == farmmachine) {
				farmmachine = new BigDecimal(0);
			}
			String other = infoTAsset.getOther();
			if (null == other) {
				other = "";
			}
			BigDecimal estimation = infoTAsset.getEstimation();
			if (null == estimation) {
				estimation = new BigDecimal(0);
			}
			Element tr1 = root.addElement("tr");
			tr1.addElement("th").addAttribute("width", "100").setText("土地(亩)：");
			tr1.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "farmland").addAttribute(
					"type", "text").addAttribute("onblur", "counts()").addAttribute("value", farmland.toString());
			tr1.addElement("th").addAttribute("width", "100").setText("水田(亩)");
			tr1.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "paddyfield").addAttribute("onblur", "counts()").addAttribute(
					"type", "text")
					.addAttribute("value", paddyfield.toString());
			tr1.addElement("th").addAttribute("width", "100").setText("旱田(亩)");
			tr1.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "glebe").addAttribute("type",
					"text").addAttribute("value", glebe.toString());

			Element tr2 = root.addElement("tr");
			tr2.addElement("th").addAttribute("width", "100").setText("自有住房");
			dict = dh.getDictsortToXML(dictionary, "101", ownerhouse);
			dict.addAttribute("name", "ownerhouse");
			tr2.addElement("td").addAttribute("width", "160").add(
					(Element) dict.clone());

			tr2.addElement("th").addAttribute("width", "100").setText("房屋间数(间)");
			tr2.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "roomcnt").addAttribute(
					"type", "text").addAttribute("value", roomcnt.toString());
			tr2.addElement("th").addAttribute("width", "100").setText("建筑面积(M2)");
			tr2.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "buildarea").addAttribute(
					"type", "text").addAttribute("value", buildarea.toString());

			Element tr3 = root.addElement("tr");
			tr3.addElement("th").addAttribute("width", "100").setText("房屋结构");
			dict = dh.getDictsortToXML(dictionary, "233", struct);
			dict.addAttribute("name", "struct");
			tr3.addElement("td").addAttribute("width", "160").add(
					(Element) dict.clone());
			tr3.addElement("th").addAttribute("width", "100").setText("座落位置");
			tr3.addElement("td").addAttribute("colspan", "3").addAttribute(
					"width", "300").addElement("input").addAttribute("name",
					"repose").addAttribute("style","width:100%").addAttribute("type", "text").addAttribute(
					"value", repose);

			Element tr4 = root.addElement("tr");
			tr4.addElement("th").addAttribute("width", "100").setText("生产资料");
			dict = dh.getDictsortToXML(dictionary, "101", producergoods);
			dict.addAttribute("name", "producergoods");
			tr4.addElement("td").addAttribute("width", "160").add(
					(Element) dict.clone());
			tr4.addElement("th").addAttribute("width", "100").setText("大牲畜(头)");
			tr4.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "animal").addAttribute(
					"type", "text").addAttribute("value", animal.toString());
			tr4.addElement("th").addAttribute("width", "100").setText("农机具(辆)");
			tr4.addElement("td").addAttribute("width", "160").addElement(
					"input").addAttribute("name", "farmmachine").addAttribute(
					"type", "text").addAttribute("value",
					farmmachine.toString());
			Element tr5 = root.addElement("tr");
			tr5.addElement("th").addAttribute("width", "100").setText("其他资产信息");
			tr5.addElement("td").addAttribute("colspan", "5").addAttribute(
					"width", "300").addElement("input").addAttribute("name",
					"other").addAttribute("type", "text").addAttribute("value",
					other).addAttribute("style", "width:100%");
			Element tr6 = root.addElement("tr");
			tr6.addElement("th").addAttribute("width", "100").setText("资产估值(元)");
			tr6.addElement("td").addAttribute("colspan", "5").addAttribute(
					"width", "300").addElement("input").addAttribute("name",
					"estimation").addAttribute("type", "text").addAttribute("style", "width:100%").addAttribute(
					"value", estimation.toString());

			request.setAttribute("pagehtml", root.asXML());
			request.setAttribute("familyId", familyId);
			request.setAttribute("assetId", assetId);
			client.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				client.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mapping.findForward("asset");
	}
}