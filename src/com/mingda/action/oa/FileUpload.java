package com.mingda.action.oa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.ConstantDefine;
import com.mingda.common.UploadFile;
import com.mingda.dao.NetTFileDAO;
import com.mingda.entity.NetTFile;

public class FileUpload extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(UploadFile.class);
	private String uppath = ConstantDefine.UPLOADPATH;
	private long sizeMax;
	private int sizeThreshold;
	private HttpServletRequest request;
	
	private static final int BUFFER_SIZE = 16 * 1024;

	public long getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	public int getSizeThreshold() {
		return sizeThreshold;
	}

	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public FileUpload() {
	}

	public FileUpload(long sizeMax, int sizeThreshold,
			HttpServletRequest request) {
		this.request = request;
		this.sizeMax = sizeMax;
		this.sizeThreshold = sizeThreshold;
	}

	public void uploadFile(String tabelname, String tableid,
			List<FileItem> fileITems) throws Exception {
		Iterator it = fileITems.iterator();
		while (it.hasNext()) {
			FileItem fi = (FileItem) it.next();
			// 获得文件名，这个文件名包括路径：
			String fileName = fi.getName();
			log.debug("文件路径 " + fileName);
			if (null == fileName || "".equals(fileName)) {

			} else {
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
						fileName.length());
				String path = "";
				if ("file".equals(fi.getFieldName())) {
					path = "\\"+tabelname + "\\" + tableid + "\\";
					String dirpath = this.isFileExists() + path;
					File file = new File(dirpath);
					if (!file.exists()) {
						file.mkdirs();
					}
					log.error("文件上传路径: " + dirpath
							+ java.util.UUID.randomUUID() + "-" + fileName);
					String realpath = dirpath + java.util.UUID.randomUUID()
							+ "-" + fileName;
					log.error("realpath>>"+realpath);
					file = new File(realpath);
					NetTFile transientInstance = new NetTFile();
					transientInstance.setFilename(file.getName());
					transientInstance.setFilepath(realpath);
					transientInstance.setFiletype(fileName.substring(fileName
							.lastIndexOf("."), fileName.length()));
					transientInstance.setRelid(new BigDecimal(tableid));
					transientInstance.setReltable(tabelname);
					transientInstance.setUploadtime(new Date());
					fi.write(file);
					this.saveFileInfo(transientInstance);
				}
			}
		}
	}

	public String isFileExists() {
		log.debug(System.getProperty("os.name"));
		if (!"Linux".equals(System.getProperty("os.name"))) {
			this.uppath = ConstantDefine.UPLOADPATH;
			log.debug("window operation system");
		} else {
			log.debug("linux operation system");
			this.uppath = ConstantDefine.UPLOADPATHX;
		}
		return uppath;
	}

	public String getRealpathBySystem(String path) {
		log.debug(System.getProperty("os.name"));
		if (!"Linux".equals(System.getProperty("os.name"))) {
			path = (ConstantDefine.UPLOADPATH + path).replace("/", "\\");
		} else {
			log.debug("linux operation system");
			path = (ConstantDefine.UPLOADPATHX + path).replace("\\", "/");
		}
		return path;
	}

	private String saveFileInfo(NetTFile transientInstance) {
		String id = "";
		try {
			this.getSession().save(transientInstance);
			id = transientInstance.getFileId().toString();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		}
		return id;
	}

	public void delOaInfo(Connection con, String reltable, String relid,
			boolean isreceive) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			String sql = "select * from " + reltable + " where 5<>5";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			String pk = rsmd.getColumnName(1);
			if (isreceive) {
				sql = "delete net_t_receive where " + pk + " ='" + relid + "'";
				log.debug("删除表记录    " + sql);
				ps = con.prepareStatement(sql);
				ps.execute();
			}
			sql = " delete  " + reltable + " where  " + pk + " = " + relid;
			log.debug("删除表记录    " + sql);
			ps = con.prepareStatement(sql);
			ps.execute();

			NetTFileDAO filedao = new NetTFileDAO();
			List<NetTFile> filelist = filedao.findByProperty(reltable, relid);
			String path = this.isFileExists();
			for (NetTFile netfile : filelist) {
				String temp = netfile.getFilepath();
				// temp = temp.replace("/", "\\");
				File file = new File(temp);
				log.debug("file    " + temp);
				file.delete();
				filedao.delete(netfile);
			}
			String dirpath = this.getRealpathBySystem(reltable + "/" + relid + "/");
			log.debug("dir    " + dirpath);
			File dir = new File(dirpath);
			if (dir.exists()) {
				log.debug("a");
				dir.delete();
				log.debug("a");
			}

		} catch (SQLException e) {
			throw e;

		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != ps) {
				ps.close();
			}
		}
	}
	
	public void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
}
