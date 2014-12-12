/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.neweditor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.action.oa.FileUpload;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTFamilyDAO;
import com.mingda.common.ibatis.dao.InfoTMemberDAO;
import com.mingda.common.ibatis.data.InfoTMember;

/**
 * MyEclipse Struts Creation date: 04-09-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class MemberbasesaveAction extends Action {
	static Logger log = Logger.getLogger(MemberbasesaveAction.class);
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

		String str = "";
		String familyId = "";
		SqlMapClient client = SqlMapper.getSqlMapper();
		try {
			client.startTransaction();
			// 令牌
			String token = "";
			// 照片
			// FileItem inchfileitem = null;
			// 表单项
			familyId = "";
			String memberId = "";
			String membername = "";
			String papertype = "";
			String paperid = "";
			String birthday = "";
			String sex = "";
			String rprtype = "";
			String rprkind = "";
			String ismarriage = "";
			String political = "";
			String nation = "";
			String ftap = "";
			String ftaplevel = "";
			String degreesort = "";
			String sicken = "";
			String deformity = "";
			String workability = "";
			String oldandinfirm = "0";
			String policy = "";
			String rpraddress = "";
			String picpath = "";
			String relmaster = "";
			String student = "";
			String health = "";
			String otherreason = "";
			String indiId = "";
			// 初始化common upload 类
			DiskFileUpload ff = new DiskFileUpload();
			ff.setSizeMax(41943040);
			ff.setSizeThreshold(4096);
			ff.setHeaderEncoding("gb18030");
			// 装载request对象
			List<FileItem> list = ff.parseRequest(request);
			for (FileItem item : list) {
				if ("org.apache.struts.taglib.html.TOKEN".equals(item
						.getFieldName())) {
					token = item.getString();
				} else if (item.isFormField()) {

					if ("familyId".equals(item.getFieldName())) {
						familyId = item.getString("gb18030");
					}
					if ("memberId".equals(item.getFieldName())) {
						memberId = item.getString("gb18030");
					}
					if ("membername".equals(item.getFieldName())) {
						membername = item.getString("gb18030");
					}
					if ("papertype".equals(item.getFieldName())) {
						papertype = item.getString("gb18030");
					}
					if ("paperid".equals(item.getFieldName())) {
						paperid = item.getString("gb18030");
					}
					if ("birthday".equals(item.getFieldName())) {
						birthday = item.getString("gb18030");
					}
					if ("sex".equals(item.getFieldName())) {
						sex = item.getString("gb18030");
					}
					if ("rprtype".equals(item.getFieldName())) {
						rprtype = item.getString("gb18030");
					}
					if ("rprkind".equals(item.getFieldName())) {
						rprkind = item.getString("gb18030");
					}
					if ("ismarriage".equals(item.getFieldName())) {
						ismarriage = item.getString("gb18030");
					}
					if ("political".equals(item.getFieldName())) {
						political = item.getString("gb18030");
					}
					if ("nation".equals(item.getFieldName())) {
						nation = item.getString("gb18030");
					}
					if ("ftap".equals(item.getFieldName())) {
						ftap = item.getString("gb18030");
					}
					if ("ftaplevel".equals(item.getFieldName())) {
						ftaplevel = item.getString("gb18030");
					}
					if ("degreesort".equals(item.getFieldName())) {
						degreesort = item.getString("gb18030");
					}
					if ("sicken".equals(item.getFieldName())) {
						sicken = item.getString("gb18030");
					}
					if ("deformity".equals(item.getFieldName())) {
						deformity = item.getString("gb18030");
					}
					if ("workability".equals(item.getFieldName())) {
						workability = item.getString("gb18030");
					}
					if ("oldandinfirm".equals(item.getFieldName())) {
						oldandinfirm = item.getString("gb18030");
					}
					if ("policy".equals(item.getFieldName())) {
						policy = item.getString("gb18030");
					}
					if ("rpraddress".equals(item.getFieldName())) {
						rpraddress = item.getString("gb18030");
					}
					if ("picpath".equals(item.getFieldName())) {
						picpath = item.getString("gb18030");
					}
					if ("relmaster".equals(item.getFieldName())) {
						relmaster = item.getString("gb18030");
					}
					if ("student".equals(item.getFieldName())) {
						student = item.getString("gb18030");
					}
					if ("health".equals(item.getFieldName())) {
						health = item.getString("gb18030");
					}
					if ("otherreason".equals(item.getFieldName())) {
						otherreason = item.getString("gb18030");
					}
					if ("indiId".equals(item.getFieldName())) {
						indiId = item.getString("gb18030");
					}
					// 获取表单字段
				} else {
					// 取出上传照片
					// inchfileitem = item;
				}
			}
			// 令牌判断
			if (request.getSession().getAttribute(
					"org.apache.struts.action.TOKEN") == null
					|| !token.equals(request.getSession()
							.getAttribute("org.apache.struts.action.TOKEN")
							.toString())) {
				// 重复提交转到提示页面
				return mapping.findForward("error");
			} else {

				InfoTMember member = new InfoTMember();

				InfoTMemberDAO memberdao = new InfoTMemberDAO(client);

				boolean isCreate = true;

				if (null != memberId && !"".equals(memberId)) {
					member = memberdao.selectMemberById(memberId);
					familyId = member.getFamilyId().toString();
					memberId = member.getMemberId().toString();
					isCreate = false;
				} else {
					memberId = memberdao.getMemberPK();
					member.setMemberId(new Integer(memberId));
				}

				Date date = null;
				if (null == birthday) {

				} else {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");

					date = simpleDateFormat.parse(birthday);
				}

				member.setBirthday(date);
				member.setDeformity(new Long(deformity));
				member.setDegreesort(new Long(degreesort));
				member.setFtap(new Long(ftap));
				member.setFtaplevel(new Long(ftaplevel));
				member.setIsmarriage(new Long(ismarriage));
				member.setMembername(membername);
				member.setNation(new Long(nation));
				if (null == oldandinfirm) {
					oldandinfirm = "0";
				}
				member.setOldandinfirm(new Long(oldandinfirm));
				member.setPaperid(paperid);
				member.setPapertype(new Long(papertype));
				member.setPicpath(picpath);
				member.setPolicy(new Long(policy));
				member.setPolitical(new Long(political));
				member.setRelmaster(new Long(relmaster));
				member.setRpraddress(rpraddress);
				// member.setRprkind(new Long(rprkind));
				member.setRprkind(new Long(0));
				member.setRprtype(new Long(rprtype));
				member.setSex(new Long(sex));
				member.setSicken(new Long(sicken));
				member.setWorkability(new Long(workability));
				member.setFamilyId(new Integer(familyId));
				member.setStudent(new Long(student));
				member.setOtherreason(otherreason);
				member.setHealth(new Long(health));
				member.setIndiId(indiId);
				// 业务逻辑
				// 保存家庭

				// /List<InfoTMember> mlist = memberdao
				// .getMembersByFamilyId(familyId);

				// if (null != mlist) {
				// int j = 0;
				// int i = 0;
				// for (InfoTMember infoTMember : mlist) {
				// if (null != infoTMember.getRprtype()
				// && infoTMember.getRprtype().intValue() == 261) {
				// j++;
				// }
				//
				// i++;
				// }
				//
				// if (isCreate) {
				// if (null != member.getRprtype()
				// && member.getRprtype().intValue() == 261) {
				// j++;
				// }
				//
				// i++;
				// } else {
				// if (null != member.getRprtype()
				// && member.getRprtype().intValue() == 261) {
				// j++;
				// }else{
				// j--;
				// }
				// }
				// InfoTFamilyDAO familydao = new InfoTFamilyDAO(client);
				// InfoTFamily infoTFamily = new InfoTFamily();
				// infoTFamily.setPopulation(new Long(i));
				// infoTFamily.setEnsurecount(new Long(j));
				// infoTFamily.setFamilyId(new Integer(familyId));
				// familydao.updateFamilySelective(infoTFamily);
				// }

				// 保存家庭

				// 保存成员

				// 保存成员
				// 上传照片
				String fname = (String) request.getSession().getAttribute(
						"fname");
				if (null != memberId && null != fname && !"".equals(fname)) {
					File src = new File("Z:\\pic\\" + fname);
					if (src.exists()) {
						// 判断系统路径
						FileUpload fu = new FileUpload();
						String inchpath = fu.isFileExists();
						if (null != member.getPicpath()
								&& !"".equals(member.getPicpath())) {
							File delfile = new File(inchpath+"inch\\"
									+ member.getPicpath().substring(1).replace("/", "\\"));
							log.debug(inchpath+"inch\\"
									+ member.getPicpath().substring(1).replace("/", "\\"));
							delfile.delete();
						}
						// 文件夹路径
						inchpath = inchpath + "inch/" + memberId + "/";
						// 照片名称
						String inchpathname = fname;
						// 建立文件夹
						File inchfile = new File(inchpath);
						if (!inchfile.exists()) {
							inchfile.mkdirs();
						}
						// 存储照片
						inchfile = new File(inchpath + inchpathname);
						fu.copy(src, inchfile);
						// member.setMemberId(new Integer(memberId));
						member.setPicpath("/" + memberId + "/" + inchpathname);
						// memberdao.updateMemberPicpath(member);
						src.delete();
						request.getSession().removeAttribute("fname");
					}
				}
				if (isCreate) {
					memberdao.insertmember(member);
				} else {
					memberdao.updateMember(member);
				}
				InfoTFamilyDAO familydao = new InfoTFamilyDAO(client);
				familydao.updateFamilySalType(member.getFamilyId());
				// 存储照片
				memberdao.getFamilyCount(member.getFamilyId());
				str = "成员姓名：" + member.getMembername() + "  修改成功";
			}
			client.commitTransaction();
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		this.resetToken(request);

		try {
			PrintWriter out = response.getWriter();
			out.println("<br><p style=\"font-size:12px\">");
			out.println(str);
			out.println("</p>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}