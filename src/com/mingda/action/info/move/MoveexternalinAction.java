/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.mingda.action.info.move;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mingda.common.SessionFactory;
import com.mingda.common.ibatis.SqlMapper;
import com.mingda.common.ibatis.dao.InfoTFamilyDAO;
import com.mingda.common.ibatis.data.InfoTFamily;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.common.page.PageView;
import com.mingda.entity.SysTEmployee;

/**
 * MyEclipse Struts Creation date: 08-25-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class MoveexternalinAction extends Action {
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
		String result = "迁入成功";
		Session session = SessionFactory.getSession();
		HttpSession hsession = request.getSession();
		SysTEmployee employee = (SysTEmployee) hsession
				.getAttribute("employee");
		String familyId = request.getParameter("familyId");
		String fammoveid = request.getParameter("fammoveid");
		String community = request.getParameter("community");
		String registeredaddress = request.getParameter("registeredaddress");
		String familyaddress = request.getParameter("familyaddress");
		//String famno = request.getParameter("famno");
		PageView pv = new PageView();
		Transaction tx = session.beginTransaction();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String newfamno ="";
		String newfamno1 ="";
		try {
			con = session.connection();
			newfamno= pv.getNewFamno(community,con);
			newfamno1=newfamno;
			sql = "update biz_t_familymove set " + "  nfamilyno = '" + newfamno
					+ "'," + "  norgid = '"+community+"'," + "  noptperson = '"
					+ employee.getEmployeeId() + "'," + " nopttime = sysdate,"
					+ "isover = '1'" + "where familymove_id = '" + fammoveid
					+ "'";
			Log4jApp.logger("更新迁入记录：" + sql);
			ps = con.prepareStatement(sql);
			ps.execute();
			//删除审批信息
			sql ="delete from biz_t_optcheck opt where opt.family_id='"+familyId+"'";
			ps = con.prepareStatement(sql);
			ps.execute();
			
			tx.commit();
			newfamno="  新家庭编号:"+newfamno;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			result = "迁入失败";
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
				session.close();
			} catch (SQLException e) {
				result = "迁移失败";
				e.printStackTrace();
			}
		}
		//家庭保存
		/*Document tree =(Document)this.servlet.getServletContext().getAttribute("tree");
		TreeHandle treehandle = new TreeHandleImpl(tree);
		Document family =treehandle.selectSingleEntity("FAMILY",new Long(familyId));
		Log4jApp.logger(family.asXML());
		family.selectSingleNode("//property[@column='ORGANIZATION_ID']").setText(community);
		family.selectSingleNode("//property[@column='FAMILYNO']").setText(newfamno1);
		family.selectSingleNode("//property[@column='RPRADDRESS']").setText(registeredaddress);
		family.selectSingleNode("//property[@column='FAMADDRESS']").setText(familyaddress);
		family.selectSingleNode("//property[@column='STATUS']").setText("1");
		Log4jApp.logger(family.asXML());
		treehandle.saveEntity(family, employee.getEmployeeId());*/
		
		SqlMapClient client = SqlMapper.getSqlMapper();
		try {
			client.startTransaction();
			InfoTFamilyDAO familydao = new InfoTFamilyDAO(client);
			InfoTFamily family1 = new InfoTFamily();
			family1.setOrganizationId(new Long(community));
			family1.setFamilyId(new Integer(familyId));
			family1.setFamilyno(newfamno1);
			family1.setRpraddress(registeredaddress);
			family1.setFamaddress(familyaddress);
			family1.setStatus("1");
			familydao.updateFamilySelective(family1);
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
		//家庭保存
		response.setContentType("text/html");
		response.setCharacterEncoding("GB18030");

		try {
			PrintWriter out = response.getWriter();
			out.write("<script>alert('"+result+newfamno+"');");
			out.write("parent.frames['queryzone'].location.reload(parent.frames['queryzone'].location.href);</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}