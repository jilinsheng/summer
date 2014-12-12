package com.mingda.action.info.neweditor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.form.info.MutilUploadForm;

public class MutilUploadAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MutilUploadForm umForm = (MutilUploadForm) form;
		ArrayList<AnnexDTO> ans = new ArrayList<AnnexDTO>();
		String familyid = request.getParameter("familyId");
		String qq = request.getParameter("qq");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		SqlMapClient client = SqlMapper.getSqlMapper();
		try {
			client.startTransaction();
			sql = "select fmi.membername as fm_name, fmi.familyno as familyno ,fmi.paperid as fm_paperid  "
					+ "from familymemberinfos fmi where fmi.family_id='"
					+ familyid + "'";
			con = client.getCurrentConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			int j = 0;
			while (rs.next()) {
				AnnexDTO a = new AnnexDTO();
				a.setAnName(rs.getString("familyno"));
				a.setFfamilyid(rs.getString("fm_name"));
				a.setAnFilename(rs.getString("fm_paperid"));
				if (j == 0) {
					ans.add(a);
				}
				ans.add(a);
				j++;
			}
			if ("111".equals(qq)) {

				int count = 0;
				try {
					count = umForm.getFileCount(); // 获得上传文件的总数
					System.out.println(count);
					for (int i = 0; i < count; i++) {

						if (i == 0) {
							FormFile file = umForm.getFile(i);
							System.out.println(file.getFileName());
							FileOutputStream fos = new FileOutputStream(
									"Z:\\ftproot\\yljz\\exwts\\"
											+ file.getFileName()); // 创建输出流
							fos.write(file.getFileData()); // 写入
							fos.flush();// 释放
							fos.close(); // 关闭
						} else {

							FormFile file = umForm.getFile(i);
							System.out.println(file.getFileName());
							FileOutputStream fos = new FileOutputStream(
									"Z:\\ftproot\\yljz\\exsfz\\"
											+ file.getFileName()); // 创建输出流
							fos.write(file.getFileData()); // 写入
							fos.flush();// 释放
							fos.close(); // 关闭
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				response.setContentType("text/html; charset=GB2312");
				PrintWriter pw = response.getWriter();
				pw.println("上传成功");
				pw.flush();
				pw.close();
				return null;

			}
			client.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != ps) {
					ps.close();
				}
				if (null != con) {
					con.close();
					client.endTransaction();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("ans", ans);
		return mapping.findForward("uploadfile");
	}

}
