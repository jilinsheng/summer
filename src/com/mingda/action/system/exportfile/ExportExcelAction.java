/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.system.exportfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;

import com.mingda.common.exportfile.ExportFile;

/**
 * MyEclipse Struts Creation date: 09-04-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ExportExcelAction extends Action {
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
	static Logger log = Logger.getLogger(ExportExcelAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession hsession = request.getSession();
		// session 取得生成excel参数 sql:为查询语句，cols:显示字段中文和列序号
		HashMap map = (HashMap) hsession.getAttribute("cols");
		String sql = (String) hsession.getAttribute("sql");
		Document dictionary = (Document) this.servlet.getServletContext()
				.getAttribute("dictionary");
		log.error("生成excel sql语句>>>>" + sql);
		// OutputStream os = null;
		String headtitle = "";
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd-HH-mm-ss");
			java.util.Date currentTime_1 = new java.util.Date();
			String targetfile = formatter.format(currentTime_1) + headtitle
					+ ".xls";
			File file = new File("\\\\10.1.1.101\\i\\nongcun\\upload\\excel\\"
					+ targetfile);
			/*File file = new File("z:\\nongcun\\upload\\excel\\"
					+ targetfile);*/
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			response.setCharacterEncoding("GB18030");
			PrintWriter out = response.getWriter();
			/*
			 * response.reset();
			 * response.setContentType("APPLICATION/OCTET-STREAM");
			 * response.setHeader("Content-Disposition",
			 * "attachment; filename=\"" + new
			 * String(targetfile.getBytes("GBK"), "iso8859-1") + "\"");
			 */

			ExportFile exportfile = new ExportFile("XLS");
			if (null != map) {
				exportfile.exportFile(fileOut, map, sql, dictionary);
			}
			String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ "/upload/excel/"+targetfile;
			out.println("<a href=\""+basePath+"\">下载</a>");
			out.flush();
			out.close();
			fileOut.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}