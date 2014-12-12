package com.mingda.common.organ;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingda.common.BaseHibernateDAO;

@SuppressWarnings("unchecked")
public class OrganEditHandle extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(OrganEditHandle.class);

	/**
	 * 更新机构父级id
	 * 
	 * @param con
	 * @param porg
	 * @param orgid
	 * @throws SQLException
	 */
	public void updateOrganParentId(String porg, String orgid)
			throws RuntimeException {
		String sql = "";
		sql = "update info_t_family fam set fam.organization_id  ='' where fam.organization_id  in ()";
		try {
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 更新家庭表
	 * 
	 * @param con
	 * @param empid
	 * @param porg
	 * @param orgid
	 * @throws SQLException
	 */
	public void updateFamilyNewOrgid(String corg, String orgids, Long empid)
			throws RuntimeException {
		String sql = "";
		try {
			// 少 业务判断 更新打证
			sql = "insert into biz_t_familymove ( familymove_id, family_id, ofamilyno,oorgid,ooptperson,"
					+ "oopttime,nfamilyno,norgid,noptperson,nopttime,isover) "
					+ "select xfamilymove_id.nextval,fam.family_id,fam.familyno,fam.organization_id,'"
					+ empid
					+ "',sysdate,'', '"
					+ corg
					+ "', '"
					+ empid
					+ "',  sysdate,3 from info_t_family fam   where fam.organization_id in ("
					+ orgids + ")";
			log.debug("sql : " + sql);
			getSession().createSQLQuery(sql).executeUpdate();
			sql = "update info_t_family fam set fam.status='3' where fam.organization_id  in ("
					+ orgids + ")";
			log.debug("sql : " + sql);
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 确认机构迁移
	 * 
	 * @param con
	 * @param corg
	 * @param orgids
	 * @param empid
	 * @throws SQLException
	 */
	public void commitFamilyNewOrgid(String corg, String orgids)
			throws RuntimeException {
		String sql = "";
		try {
			sql = "update info_t_family fam set fam.status='1' , fam.familyno='' ,fam.organization_id ='"
					+ corg + "' where fam.organization_id  in (" + orgids + ")";
			log.debug("sql : " + sql);
			getSession().createSQLQuery(sql).executeUpdate();
			sql = "update biz_t_familymove mov set mov.isover =4,mov.nfamilyno = (select fam.familyno from info_t_family fam where fam.family_id = mov.family_id) "
					+ " where mov.isover = 3 and mov.norgid = '"
					+ corg
					+ "' and mov.nfamilyno is null";
			log.debug("sql : " + sql);
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 是否可以做机构迁移
	 * 
	 * @param con
	 * @param orgids
	 * @return
	 * @throws SQLException
	 */
	public boolean isCommitFamilyNewOrgid(String orgids)
			throws RuntimeException {
		boolean flag = true;
		String sql = "";
		List<Object[]> rs = null;
		try {

			sql = "select * from biz_t_familymove mo where mo.oorgid in("
					+ orgids + ") and mo.isover =3";
			log.debug("sql : " + sql);
			rs = getSession().createSQLQuery(sql).list();
			if (null != rs&&rs.size()>0) {
				flag = false;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	/**
	 * 取得机构id
	 * 
	 * @param con
	 * @param porg
	 * @return 机构id
	 * @throws SQLException
	 */
	public String getOrganID(Connection con, String porg)
			throws RuntimeException {
		String newOrganizationId = "";
		String sql = "select  max(organ.organization_id)+1   from sys_t_organization organ where organ.parentorgid='"
				+ porg + "'";
		try {
			BigDecimal organno = (BigDecimal) getSession().createSQLQuery(sql)
					.uniqueResult();
			if (null != organno) {
				newOrganizationId = organno.toString();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return newOrganizationId;
	}

	/**
	 * 取迁移机构
	 * 
	 * @param con
	 * @param orgid
	 * @return
	 * @throws SQLException
	 */
	public List<String[]> getOldOrgan(String orgid) throws RuntimeException {
		List<String[]> rslist = new ArrayList<String[]>();
		List<Object[]> rs = null;
		String sql = "select mo.oorgid, "
				+ "(select organ.orgname from sys_t_organization organ where organ.organization_id = mo.oorgid) as organname "
				+ "from biz_t_familymove mo where mo.norgid like '" + orgid
				+ "%' and mo.isover = 3 group by mo.oorgid";
		log.debug("查询" + sql);
		try {
			
			rs = getSession().createSQLQuery(sql).list();
			
			for (Object[] o : rs) {
				
				String[] strs = new String[2];
				BigDecimal oVal =(BigDecimal)o[0];
				strs[0] = oVal.toString();
				strs[1] = (String) o[1];
				
				rslist.add(strs);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return rslist;
	}

	/**
	 * 取迁移至新机构
	 * 
	 * @param con
	 * @param orgid
	 * @return
	 * @throws SQLException
	 */
	public List<String[]> getNewOrgan(String orgid) throws RuntimeException {
		List<String[]> rslist = new ArrayList<String[]>();
		List<Object[]> rs = null;
		String sql = "select mo.norgid, "
				+ "(select organ.orgname from sys_t_organization organ where organ.organization_id = mo.norgid) as organname  "
				+ "from  biz_t_familymove mo where mo.norgid like '" + orgid
				+ "%' and mo.isover = 3 group by mo.norgid";
		log.debug("查询" + sql);
		try {
			
			rs = getSession().createSQLQuery(sql).list();
			
			for (Object[] o : rs) {
				
				String[] strs = new String[3];
				BigDecimal oVal =(BigDecimal)o[0];
				strs[0] = oVal.toString();
				strs[1] = o[1].toString();
				
				rslist.add(strs);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return rslist;
	}

	/**
	 * 取机构信息(户 人)
	 * 
	 * @param con
	 * @param orgids
	 * @return
	 * @throws SQLException
	 */
	public List<String[]> getOrganInfo(String orgids) throws RuntimeException {
		List<String[]> rslist = new ArrayList<String[]>();
		List<Object[]> rs = null;
		String sql = "select (select count(1)"
				+ " from info_t_family fam"
				+ " where fam.organization_id = organ.organization_id)  as hs ,"
				+ " (select decode(sum(fam.population), null, 0, sum(fam.population)) "
				+ " from info_t_family fam"
				+ " where fam.organization_id = organ.organization_id) as rs,"
				+ " organ.orgname" + " from sys_t_organization organ"
				+ " where organ.organization_id in (" + orgids
				+ ") order by organ.organization_id";
		log.debug("查询" + sql);
		try {
			rs = getSession().createSQLQuery(sql).list();
			for (Object[] o : rs) {
				String[] strs = new String[3];
				strs[0] = o[2].toString();
				strs[1] = o[0].toString();
				strs[2] = o[1].toString();
				rslist.add(strs);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return rslist;
	}

	/**
	 * 恢复机构迁移
	 * 
	 * @param con
	 * @param replaceStr
	 * @throws SQLException
	 */
	public void undoFamilyNewOrgid(String oorgid, String norgid)
			throws RuntimeException {
		String sql = "";
		try {
			// 少 业务判断 更新打证
			sql = "delete biz_t_familymove  mo where mo.norgid ='" + norgid
					+ "' and mo.isover=3 ";
			log.debug("sql : " + sql);
			getSession().createSQLQuery(sql).executeUpdate();
			sql = "update info_t_family fam set fam.status='1' where fam.organization_id  in ("
					+ oorgid + ")";
			log.debug("sql : " + sql);
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 通过判断业务逻辑，机构是否可以迁移
	 * 
	 * @param con
	 * @param orgid
	 * @return
	 * @throws SQLException
	 */
	public boolean isOrganEditByPolicy(Connection con, String orgid)
			throws SQLException {
		return false;

	}

	/**
	 * 打证处理
	 * 
	 * @param con
	 * @param orgid
	 * @return
	 * @throws SQLException
	 */
	public void PrintPaperHandle(Connection con, String orgid) {

	}

	/**
	 * 判断是否可以再次迁移
	 * 
	 * @param con
	 * @param orgid
	 * @return
	 * @throws SQLException
	 */
	public boolean isRepeatHandle(String orgid) throws RuntimeException {
		String sql = "";
		List<Object[]> rs = null;
		try {
			log.debug("sql : " + sql);
			sql = "select * from  biz_t_familymove move  where move.ofamilyno like '220907%' and move.nfamilyno is null and move.isover=3";
			rs = getSession().createSQLQuery(sql).list();
			if (null != rs&&rs.size()>0) {
				return false;
			} else {
				return true;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
