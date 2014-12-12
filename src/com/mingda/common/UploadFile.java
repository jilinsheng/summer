package com.mingda.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class UploadFile {
	static Logger log = Logger.getLogger(UploadFile.class);
	private String uppath = ConstantDefine.UPLOADPATH;

	public String getUppath() {
		return uppath;
	}

	public void setUppath(String uppath) {
		this.uppath = uppath;
	}

	public String upload(FormFile file, String path, String temp)
			throws IOException {

		path = this.isFileExists() + path;
		log.debug("上传路径:::" + path);
		String filename = "";
		InputStream inStream = null;
		if (file.getFileSize() > 0) {
			filename = file.getFileName().trim();
			log.debug(filename);
			int i = filename.indexOf(".");
			String hz = filename.substring(i);
			hz = hz.toLowerCase();
			if (hz.equals(".gif") || hz.equals(".jpg") || hz.equals(".bmp")
					|| hz.equals(".mpg") || hz.equals(".png")
					|| hz.equals(".wmv")) {
				File iofile = new File(path.trim());
				log.debug(path.trim());
				if (!iofile.exists()) {
					iofile.mkdirs();
				}
				try {
					inStream = file.getInputStream();
					filename = path + temp + hz;
					saveToFile(inStream, filename.trim(), file.getFileSize());
					inStream.close();
				} catch (FileNotFoundException e) {
					throw e;
				} catch (IOException e) {
					throw e;
				}
				String str = temp + hz;
				return str;
			}
		}
		return null;
	}

	private void saveToFile(InputStream inStream, String filename, int size) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(filename);
			byte[] b = new byte[size];
			int i = 0;
			while ((i = inStream.read(b, 0, size)) != -1) {
				out.write(b, 0, i);
			}
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String delFile(String realpath) {
		String result = "成功删除";
		File file = new File(realpath);
		try {
			if (file.exists()) {
				file.delete();
			} else {
				result = "没找到文件";
			}
		} catch (RuntimeException re) {
			result = "出错了";
			re.printStackTrace();
		}

		return result;

	}

	private String isFileExists() {
		log.debug(System.getProperty("os.name"));
		File file = new File(this.uppath);
		if (!"Linux".equals(System.getProperty("os.name"))) {
			log.debug("window operation system");
		} else {
			log.debug("linux operation system");
			this.uppath = ConstantDefine.UPLOADPATHX;
		}
		return uppath;

	}
}
