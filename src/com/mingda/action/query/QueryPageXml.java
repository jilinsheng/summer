package com.mingda.action.query;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.ConstantDefine;
import com.mingda.dao.SysTIniqueryDAO;
import com.mingda.entity.SysTIniquery;

public class QueryPageXml extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(QueryPageXml.class);

	@SuppressWarnings("rawtypes")
	public String SaveQuerySeqXml(String empid) {
		String sReturn = "";
		StringBuffer sXml = new StringBuffer("");
		Querypage querypage = new Querypage();
		sXml = querypage.getQueryExpHtml(empid);
		log.debug("@@@@@@@"+sXml.toString());
		SysTIniqueryDAO sysTIniqueryDAO = new SysTIniqueryDAO();
		SysTIniquery sysTIniquery = new SysTIniquery();
		List list = sysTIniqueryDAO.findByEmpId(new BigDecimal(empid));
		if (null != list && list.size() > 0) {
			sysTIniquery = (SysTIniquery) list.get(0);
			sysTIniquery.setQxml(sXml.toString());
			sysTIniqueryDAO.save(sysTIniquery);
		}
		return sReturn;
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public String GetQuerySeqXml(String empid) {
		String sReturn = "";
		String sempid = "";

		ConstantDefine constantdefine = new ConstantDefine();
		// �Ƿ��е�ǰ�û�������
		Querypage querypage = new Querypage();
		sempid = querypage.getQueryExp(empid);
		if (sempid.equals("")) {// ������
			sempid = constantdefine.ADMIN_ID;
		} else {
			// Ĭ����Ϣ��ѯѡ��
			sempid = empid;
		}
		// log.debug(sempid);
		SysTIniqueryDAO sysTIniqueryDAO = new SysTIniqueryDAO();
		SysTIniquery sysTIniquery = new SysTIniquery();
		List list = sysTIniqueryDAO.findByEmpId(new BigDecimal(sempid));
		if (null != list && list.size() > 0) {
			sysTIniquery = (SysTIniquery) list.get(0);
			sReturn = sysTIniquery.getQxml();
			log.debug(sReturn);
		}
		if (null == sReturn || "".equals(sReturn) || "null".equals(sReturn)) {
			sReturn = "<br>";
		}

		return sReturn;
	}
}
