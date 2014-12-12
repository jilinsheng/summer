package com.mingda.common.validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.common.SessionFactory;
import com.mingda.common.log4j.Log4jApp;
import com.mingda.dao.BizTValidatetermDAO;
import com.mingda.entity.BizTValidateterm;

public class GenerateValidation extends BaseHibernateDAO {
	static Logger logger = Logger.getLogger(GenerateValidation.class);
	/**
	 * 手动生成当前机构未通过验证的家庭类表
	 * 
	 * @param organizationId
	 * @param termId
	 * @throws SQLException
	 */
	public void insertValidations(Long organizationId, Long termId)
			throws SQLException {
		PreparedStatement ps = null;
		try {
			Connection con = this.getConnection();
			String sql = "delete biz_t_validatelog t  where t.validateterm_id = '"
					+ termId
					+ "'"
					+ "and exists (select f.family_id from info_t_family f where f.organization_id like '"
					+ organizationId + "%' and t.family_id = f.family_id)";
			Log4jApp.logger("删除验证日志  " + sql);
			ps = con.prepareStatement(sql);
			ps.execute();
			BizTValidateterm term = new BizTValidatetermDAO().findById(termId);

			sql = "insert into biz_t_validatelog "
					+ " (validatelog_id, validateterm_id, content, valtime, status, family_id) "
					+ " select xvalidatelog_id.nextval, '"
					+ term.getValidatetermId() + "', '" + term.getItemdesc()
					+ "', sysdate, '1', info.family_id "
					+ "  from INFO_T_FAMILY info, " + "   ( "
					+ term.getPhysql() + " ) a "
					+ "  where info.organization_id like '" + organizationId
					+ "%' " + "    and info.family_id = a.FAMILY_ID ";
			Log4jApp.logger("插入验证日志y  " + sql);
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}

	}

	/**
	 * 取得验证类型的列表，组合验证查询语句
	 * 
	 * @return 查询语句
	 * @throws SQLException
	 */
	public String getTermSQL() throws SQLException {
		String sql = "";
		Connection con = this.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql = "select * from biz_t_validateterm t ";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			sql = "";
			while (rs.next()) {
				String tempsql = rs.getString("physql");
				if (null != tempsql && !"".equals(tempsql)) {

					String temp = tempsql
							.substring(tempsql.indexOf("select") + 6);
					temp = "select '" + rs.getString("validateterm_id")
							+ "', '" + rs.getString("name") + "', " + temp;
					sql = temp + " union all " + sql;

				}
			}
			sql = sql.substring(0, sql.lastIndexOf("union all"));
		} catch (SQLException e) {
			throw e;
		} finally {
			rs.close();
			ps.close();
		}
		return sql;
	}

	/**
	 * 取得类别列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BizTValidateterm> getValidateterms() throws SQLException {
		Connection con = this.getConnection();
		ArrayList<BizTValidateterm> list = new ArrayList<BizTValidateterm>();
		String sql = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql = "select t.* , (select dict.item from sys_t_dictionary dict where dict.dictionary_id=t.type) as item from biz_t_validateterm t where t.status ='1' order by t.type ,t.validateterm_id ";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				BizTValidateterm term = new BizTValidateterm();
				term.setName(rs.getString("name"));
				term.setValidatetermId(rs.getLong("validateterm_id"));
				term.setItemdesc(rs.getString("item"));
				list.add(term);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			rs.close();
			ps.close();
		}
		return list;
	}

	private Connection getConnection() {
		return this.getSession().connection();
	}
}
