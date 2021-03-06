package com.mingda.action.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.entity.SysTEmployee;

public class RygcAction extends Action {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		SqlMapClient client = SqlMapper.getSqlMapper();

		byte[] bytes = null;
		HashMap params = new HashMap();
		Connection conn = null;
		try {
			InputStream reportStream = getServlet().getServletConfig()
					.getServletContext().getResourceAsStream(
							"/page/report/countryrygc.jasper");
			client.startTransaction();
			conn = client.getCurrentConnection();
			HttpSession httpsession = request.getSession();
			SysTEmployee employee = (SysTEmployee) httpsession
					.getAttribute("employee");
			params.put("oid", employee.getSysTOrganization()
					.getOrganizationId());
			bytes = this.generateHtml(params, reportStream, conn);
			response.setContentType("text/html");
			response.setContentLength(bytes.length);
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			client.endTransaction();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public byte[] generateHtml(Map parameters, InputStream reportStream,
			Connection conn) throws Exception {

		JRHtmlExporter exporter = new JRHtmlExporter();
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, conn);
			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					Boolean.FALSE);
			exporter
					.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter
					.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GBK");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporter.exportReport();
			byte[] bytes = oStream.toByteArray();
			return bytes;
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception("Report Export Failed.");
		}
	}

}
