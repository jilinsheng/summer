package com.mingda.common.exportfile;

import java.io.FileWriter;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.hibernate.Query;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.page.PageView;

public class ExportFile extends BaseHibernateDAO {
	private static int LENGHT = 10000;
	private String exporttype;
	private PageView pageview = new PageView();

	public ExportFile(String exporttype) {
		this.exporttype = exporttype;
	}

	public ExportFile() {
	}

	public String getExporttype() {
		return exporttype;
	}

	public void setExporttype(String exporttype) {
		this.exporttype = exporttype;
	}

	public String exportFile(OutputStream os, HashMap columns, String sql,
			Document dictionary) throws Exception {

		if ("PDF".equals(exporttype)) {
			return "";
		} else if ("TXT".equals(exporttype)) {
			return "";
		} else if ("XLS".equals(exporttype)) {
			//return this.exportExcelCsv(os, columns, sql, dictionary);
			 return exportExcel(os, columns, sql, dictionary);
		} else {
			return "不支持导出这种类型文件";
		}
	}

	@SuppressWarnings("unused")
	private String exportExcel(OutputStream os, HashMap columns, String sql,
			Document dictionary) throws Exception {
		Log4jApp.logger("生成excel语句:" + sql);
		WritableWorkbook workbook = null;
		WorkbookSettings ws = new WorkbookSettings();
		ws.setArrayGrowSize(204800);
		ws.setInitialFileSize(204800);
		ws.setGCDisabled(false);
		workbook = Workbook.createWorkbook(os, ws);
		WritableSheet sheet = null;
		List list = this.getQueryList(sql);
		if (null != list && list.size() > 0) {
			int lsize = list.size() / LENGHT;
			int rsize = list.size() % LENGHT;
			for (int m = 0; m < lsize; m++) {
				String worksheet = "List" + m;// 输出的excel文件工作表名
				sheet = workbook.createSheet(worksheet, m);
				Label label = null;
				if (null != columns) {
					int i = 0;
					for (Iterator keyi = columns.keySet().iterator(); keyi
							.hasNext();) {
						Object key = keyi.next();
						Long key1 = new Long(key.toString());
						if (columns.containsKey(key)) {
							String colname = (String) columns.get(key1
									.intValue());
							int index = colname.indexOf(",");
							if (-1 == index) {
							} else {
								colname = colname.split(",")[0];
							}
							label = new Label(i, 0, colname);
							sheet.addCell(label);
						}
						i++;
					}
					int j = 1;
					for (int n = m * LENGHT; n < (m + 1) * LENGHT; n++) {
						Object[] obj = (Object[]) list.get(n);
						i = 0;
						for (Iterator keyi = columns.keySet().iterator(); keyi
								.hasNext();) {
							Object key = keyi.next();
							Long key1 = new Long(key.toString());
							Object o = obj[key1.intValue()];
							String value = "";
							if (null == o) {
							} else {
								String colname = (String) columns.get(key1
										.intValue());
								int index = colname.indexOf(",");
								if (-1 == index) {
									value = o.toString();
								} else {
									String[] vals = colname.split(",");
									if ("dictval".equals(vals[1])) {
										value = pageview.getDictionartHandle()
												.getDictsortToValue(dictionary,
														o.toString());
									} else {
										value = o.toString();
									}
								}

							}
							label = new Label(i, j, value);
							sheet.addCell(label);
							i++;
						}
						Log4jApp.logger(n + " 行    值 ");
						j++;
					}
				}
			}
			if (0 != rsize) {
				String worksheet = "List" + (lsize);// 输出的excel文件工作表名
				sheet = workbook.createSheet(worksheet, lsize + 1);
				Label label = null;
				if (null != columns) {
					int i = 0;
					for (Iterator keyi = columns.keySet().iterator(); keyi
							.hasNext();) {
						Object key = keyi.next();
						Long key1 = new Long(key.toString());
						if (columns.containsKey(key)) {
							String colname = (String) columns.get(key1
									.intValue());
							int index = colname.indexOf(",");
							if (-1 == index) {
							} else {
								colname = colname.split(",")[0];
							}
							label = new Label(i, 0, colname);
							sheet.addCell(label);
						}
						i++;
					}
					int j = 1;
					for (int n = 0; n < rsize; n++) {
						Object[] obj = (Object[]) list
								.get((lsize * LENGHT) + n);
						i = 0;
						for (Iterator keyi = columns.keySet().iterator(); keyi
								.hasNext();) {
							Object key = keyi.next();
							Long key1 = new Long(key.toString());
							Object o = obj[key1.intValue()];
							String value = "";
							if (null == o) {

							} else {
								String colname = (String) columns.get(key1
										.intValue());
								int index = colname.indexOf(",");
								if (-1 == index) {
									value = o.toString();
								} else {
									String[] vals = colname.split(",");

									if ("dictval".equals(vals[1])) {
										value = pageview.getDictionartHandle()
												.getDictsortToValue(dictionary,
														o.toString());
									} else {
										value = o.toString();
									}
								}
							}
							label = new Label(i, j, value);
							sheet.addCell(label);
							i++;
						}
						j++;
					}
				}
			}
			workbook.write();
			workbook.close();
		}
		return "success";
	}

	private String exportExcelPoi(OutputStream os, HashMap columns, String sql,
			Document dictionary) throws Exception {
		Log4jApp.logger("生成excel语句:" + sql);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell label = null;

		List list = this.getQueryList(sql);
		if (null != list && list.size() > 0) {
			int lsize = list.size() / LENGHT;
			int rsize = list.size() % LENGHT;
			for (int m = 0; m < lsize; m++) {
				String worksheet = "List" + m;// 输出的excel文件工作表名
				sheet = workbook.createSheet(worksheet);
				if (null != columns) {
					int i = 0;
					row = sheet.createRow(0);
					for (Iterator keyi = columns.keySet().iterator(); keyi
							.hasNext();) {
						Object key = keyi.next();
						Long key1 = new Long(key.toString());
						if (columns.containsKey(key)) {
							String colname = (String) columns.get(key1
									.intValue());
							int index = colname.indexOf(",");
							if (-1 == index) {
							} else {
								colname = colname.split(",")[0];
							}
							label = row.createCell(i);
							label.setCellValue(colname);
						}
						i++;
					}
					int j = 1;
					for (int n = m * LENGHT; n < (m + 1) * LENGHT; n++) {
						Object[] obj = (Object[]) list.get(n);
						i = 0;
						row = sheet.createRow(j);
						for (Iterator keyi = columns.keySet().iterator(); keyi
								.hasNext();) {
							Object key = keyi.next();
							Long key1 = new Long(key.toString());
							Object o = obj[key1.intValue()];
							String value = "";
							if (null == o) {
							} else {
								String colname = (String) columns.get(key1
										.intValue());
								int index = colname.indexOf(",");
								if (-1 == index) {
									value = o.toString();
								} else {
									String[] vals = colname.split(",");
									if ("dictval".equals(vals[1])) {
										value = pageview.getDictionartHandle()
												.getDictsortToValue(dictionary,
														o.toString());
									} else {
										value = o.toString();
									}
								}

							}
							label = row.createCell(i);
							label.setCellValue(value);
							i++;
						}
						Log4jApp.logger(n + " 行    值 ");
						j++;
					}
				}
			}
			if (0 != rsize) {
				String worksheet = "List" + (lsize);// 输出的excel文件工作表名
				sheet = workbook.createSheet(worksheet);
				row = sheet.createRow(0);
				if (null != columns) {
					int i = 0;
					for (Iterator keyi = columns.keySet().iterator(); keyi
							.hasNext();) {
						Object key = keyi.next();
						Long key1 = new Long(key.toString());
						if (columns.containsKey(key)) {
							String colname = (String) columns.get(key1
									.intValue());
							int index = colname.indexOf(",");
							if (-1 == index) {
							} else {
								colname = colname.split(",")[0];
							}
							label = row.createCell(i);
							label.setCellValue(colname);
						}
						i++;
					}
					int j = 1;
					for (int n = 0; n < rsize; n++) {
						Object[] obj = (Object[]) list
								.get((lsize * LENGHT) + n);
						row = sheet.createRow(j);
						i = 0;
						for (Iterator keyi = columns.keySet().iterator(); keyi
								.hasNext();) {
							Object key = keyi.next();
							Long key1 = new Long(key.toString());
							Object o = obj[key1.intValue()];
							String value = "";
							if (null == o) {

							} else {
								String colname = (String) columns.get(key1
										.intValue());
								int index = colname.indexOf(",");
								if (-1 == index) {
									value = o.toString();
								} else {
									String[] vals = colname.split(",");

									if ("dictval".equals(vals[1])) {
										value = pageview.getDictionartHandle()
												.getDictsortToValue(dictionary,
														o.toString());
									} else {
										value = o.toString();
									}
								}
							}
							label = row.createCell(i);
							label.setCellValue(value);
							i++;
						}
						j++;
					}
				}
			}
			workbook.write(os);
		}
		return "success";
	}

	private String exportExcelCsv(OutputStream os, HashMap columns, String sql,
			Document dictionary) throws Exception {
		Log4jApp.logger("生成excel语句:" + sql);
		List list = this.getQueryList(sql);
		if (null != columns) {
			int i = 0;
			String a = "";
			for (Iterator keyi = columns.keySet().iterator(); keyi.hasNext();) {
				Object key = keyi.next();
				Long key1 = new Long(key.toString());
				if (columns.containsKey(key)) {
					String colname = (String) columns.get(key1.intValue());
					int index = colname.indexOf(",");
					if (-1 == index) {
					} else {
						colname = colname.split(",")[0];
					}
					a = a + colname + ",";
				}
				i++;
			}
			a = a + "\r\n";
			os.write(a.getBytes());
			for (int n = 0; n < list.size(); n++) {
				Object[] obj = (Object[]) list.get(n);
				i = 0;
				a = "";
				for (Iterator keyi = columns.keySet().iterator(); keyi
						.hasNext();) {
					Object key = keyi.next();
					Long key1 = new Long(key.toString());
					Object o = obj[key1.intValue()];
					String value = "";
					if (null == o) {

					} else {
						String colname = (String) columns.get(key1.intValue());
						int index = colname.indexOf(",");
						if (-1 == index) {
							value = o.toString();
						} else {
							String[] vals = colname.split(",");

							if ("dictval".equals(vals[1])) {
								value = pageview.getDictionartHandle()
										.getDictsortToValue(dictionary,
												o.toString());
							} else {
								value = o.toString();
							}
						}
						a = a + value + ",";
					}
					i++;
				}
				a = a + "\r\n";
				os.write(a.getBytes());
			}
		}
		return "success";
	}

	private List getQueryList(String sql) throws Exception {
		try {
			Query query = this.getSession().createSQLQuery(sql);
			return query.list();
		} catch (Exception ex) {
			throw ex;
		}
	}
}
