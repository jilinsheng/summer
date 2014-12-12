package com.mingda.action.info.neweditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mingda.common.OperateImage;
import com.mingda.form.UploadPicActionForm;

public class UploadPicAction extends Action {
	static Logger log = Logger.getLogger(UploadPicAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UploadPicActionForm uploadPicActionForm = (UploadPicActionForm) form;

		org.apache.struts.upload.FormFile accfile = uploadPicActionForm
				.getPic();
		InputStream inn = null;
		HttpSession session = null;
		session = request.getSession();
		String fname = "";
		String fname1 = "";
		String rr = null;
		try {
			inn = accfile.getInputStream();
			String temp = accfile.getFileName();
			rr = java.util.UUID.randomUUID().toString();
			fname = rr + getExtention(temp);
			fname1 = "o" + rr + getExtention(temp);
			File dir = new File("z:\\pic\\");
			if (!dir.exists()) {
				dir.mkdir();
			}
			saveToFile(inn, "z:\\pic\\" + fname1);
			int x1 = 0;
			int x2 = 0;
			int y1 = 0;
			int y2 = 0;
			x1 = Integer.valueOf(uploadPicActionForm.getX1()).intValue();
			x2 = Integer.valueOf(uploadPicActionForm.getX2()).intValue();
			y1 = Integer.valueOf(uploadPicActionForm.getY1()).intValue();
			y2 = Integer.valueOf(uploadPicActionForm.getY2()).intValue();

			log.debug(x1 + "," + x2 + "," + y1 + "," + y2);

			OperateImage o = new OperateImage(x1, y1, x2, y2);
			o.setSrcpath("z:\\pic\\" + fname1);
			o.setSubpath("z:\\pic\\" + fname);
			o.cut();

			File file1 = new File("z:\\pic\\" + fname1);
			if (file1.exists()) {
				file1.delete();
			}
			inn.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		String str = "<script>parent.window.frames['pic'].location=parent.window.frames['pic'].location;"
				+ "window.close();" + "</script>";
		session.setAttribute("fname", fname);
		session.setAttribute("picid", rr.toString());
		request.setAttribute("str", fname);
		return mapping.findForward("pic");
	}

	private void saveToFile(InputStream in, String sFileName) {
		FileOutputStream out;
		// 这里可以保存倒数据库或者磁盘
		try {
			out = new FileOutputStream(sFileName);
			byte[] b = new byte[100000];
			int i = 0;
			while ((i = in.read(b, 0, 100000)) != -1) {
				out.write(b, 0, i);
			}
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
}
