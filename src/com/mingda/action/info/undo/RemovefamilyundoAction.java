/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.undo;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTFamilyDAO;
import com.mingda.common.ibatis.data.InfoTFamily;

/**
 * MyEclipse Struts Creation date: 12-05-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class RemovefamilyundoAction extends Action {
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
		
		String familyid = request.getParameter("familyid");
		SqlMapClient client = SqlMapper.getSqlMapper();
		
		try {
			
			InfoTFamilyDAO familyDAO = new InfoTFamilyDAO(client);
			client.startTransaction();
			List<String[]> list = familyDAO.valRemoveUndo(familyid);
			// ����֤�Ѿ�������
			if (null != list && list.size() > 0) {
				request.setAttribute("list", list);
				request
						.setAttribute("resultstr",
								"���ϼ�ͥ��Ա�Ѿ������ڱ�ļ�ͥ��,���ܹ��ָ��⻧��ͥ��");
			}
			// �ָ��ɹ�
			else {
				
				InfoTFamily infoTFamily = new InfoTFamily();
				infoTFamily.setStatus("1");
				infoTFamily.setTohtml("1");
				infoTFamily.setFamilyId(new Integer(familyid));
				familyDAO.updateFamilySelective(infoTFamily);
				request.setAttribute("resultstr", "�ָ��ɹ�");
			}
			
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
		return mapping.findForward("removefamilyundo");
	}
}