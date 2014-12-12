package com.mingda.common.paperprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.dao.ImplTPaperinfo1DAO;
import com.mingda.dao.ImplTPaperrecordDAO;
import com.mingda.dao.ImplTPapertypeDAO;
import com.mingda.entity.BizTFamilystatus;
import com.mingda.entity.ImplTPaperinfo1;
import com.mingda.entity.ImplTPaperrecord;

public class PaperPrint extends BaseHibernateDAO {
	static Logger log = Logger.getLogger(PaperPrint.class);

	/**
	 * 
	 * @param familyId
	 * @return
	 * @throws RuntimeException
	 */
	public List<Object[]> getPrintPapersByFamily(String familyId)
			throws RuntimeException {
		try {
			String sql = "select t.* ,(select sum(pr.flag) from impl_t_paperrecord pr where pr.papertype_id = t.papertype_id and pr.family_id =s.family_id ) as status from impl_t_papertype t, biz_t_familystatus s "
					+ "where s.policy_id = t.policy_id and s.family_id = '"
					+ familyId + "' and s.flag = '2' and t.flag ='1'";
			Query q = getSession().createSQLQuery(sql);
			return q.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		}
	}

	/**
	 * 读取城市低保证件信息
	 * 
	 * @param ptid
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	public ImplTPaperinfo1 getImplTPaperinfo1(String fid, String ptid)
			throws Exception {

		String sql = "";
		ImplTPaperinfo1 paper1 = new ImplTPaperinfo1();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = this.getSession().connection();

			// 证件家庭信息

			sql = "select (select unitname from sys_t_unit u where u.unit_id = mem.mainworkplace ) unitname , "
					+ "(select (select dict.item from sys_t_dictionary dict where dict.dictionary_id = u.unittype) "
					+ " from sys_t_unit u where u.unit_id = mem.mainworkplace ) unittype   "
					+ ", fam.*, mem.*,(trunc((to_char(sysdate, 'yyyyMMdd') -  to_char(mem.birthday, 'yyyyMMdd')) / 10000)) as age, (select org.fullname from sys_t_organization org where org.organization_id = substr( fam.organization_id,0,6)) orgname from Info_t_Family fam, info_t_member mem where "
					+ "mem.family_id = fam.family_id and "
					+ "fam.masterid = mem.member_id "
					+ "and fam.family_id = '"
					+ fid + "'";
			log.debug(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				paper1.setAddress(rs.getString("FAMADDRESS"));
				paper1.setAge(rs.getString("AGE"));
				paper1.setEnsure(rs.getString("ENSURECOUNT"));
				paper1.setFamilyno(rs.getString("FAMILYNO"));
				paper1.setMastername(rs.getString("MASTERNAME"));
				paper1.setMasterpapertye(rs.getString("MASTERPAPERTYPE"));
				paper1.setMasterpapercode(rs.getString("MASTERPAPERID"));
				paper1.setPopulation(rs.getString("POPULATION"));
				paper1.setResiaddr(rs.getString("RPRADDRESS"));
				paper1.setOrgname(rs.getString("orgname"));
				paper1.setMasterunit(rs.getString("unitname"));
				paper1.setMasterunittype(rs.getString("unittype"));
			}

			sql = "select (select unitname from sys_t_unit u where u.unit_id = mem.mainworkplace ) unitname , "
					+ "(select (select dict.item from sys_t_dictionary dict where dict.dictionary_id = u.unittype) "
					+ " from sys_t_unit u where u.unit_id = mem.mainworkplace ) unittype   "
					+ ",(select dv.item from sys_t_dictionary dv where dv.dictionary_id = mem.relmaster) as relmaster, mem.membername, "
					+ "(select dv.item from sys_t_dictionary dv where dv.dictionary_id = mem.sex) as sex,   "
					+ " (trunc((to_char(sysdate, 'yyyyMMdd') -  to_char(mem.birthday, 'yyyyMMdd')) / 10000)) as age , mem.paperid from info_t_member mem where mem.family_id = '"
					+ fid + "' order by mem.relmaster";
			log.debug(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				if (i == 0) {
					paper1.setMemage1(rs.getString("age"));
					paper1.setMemname1(rs.getString("membername"));
					paper1.setMemrelmaster1(rs.getString("relmaster"));
					paper1.setMemsex1(rs.getString("sex"));
					paper1.setMemunitname1(rs.getString("unitname"));
					paper1.setMemunittype1(rs.getString("unittype"));
					paper1.setMempaper1(rs.getString("paperid"));
				}
				if (i == 1) {
					paper1.setMemage2(rs.getString("age"));
					paper1.setMemname2(rs.getString("membername"));
					paper1.setMemrelmaster2(rs.getString("relmaster"));
					paper1.setMemsex2(rs.getString("sex"));
					paper1.setMemunitname2(rs.getString("unitname"));
					paper1.setMemunittype2(rs.getString("unittype"));
					paper1.setMempaper2(rs.getString("paperid"));
				}
				if (i == 2) {
					paper1.setMemage3(rs.getString("age"));
					paper1.setMemname3(rs.getString("membername"));
					paper1.setMemrelmaster3(rs.getString("relmaster"));
					paper1.setMemsex3(rs.getString("sex"));
					paper1.setMemunitname3(rs.getString("unitname"));
					paper1.setMemunittype3(rs.getString("unittype"));
					paper1.setMempaper3(rs.getString("paperid"));
				}
				if (i == 3) {
					paper1.setMemage4(rs.getString("age"));
					paper1.setMemname4(rs.getString("membername"));
					paper1.setMemrelmaster4(rs.getString("relmaster"));
					paper1.setMemsex4(rs.getString("sex"));
					paper1.setMemunitname4(rs.getString("unitname"));
					paper1.setMemunittype4(rs.getString("unittype"));
					paper1.setMempaper4(rs.getString("paperid"));
				}
				if (i == 4) {
					paper1.setMemage5(rs.getString("age"));
					paper1.setMemname5(rs.getString("membername"));
					paper1.setMemrelmaster5(rs.getString("relmaster"));
					paper1.setMemsex5(rs.getString("sex"));
					paper1.setMemunitname5(rs.getString("unitname"));
					paper1.setMemunittype5(rs.getString("unittype"));
					paper1.setMempaper5(rs.getString("paperid"));
				}
				i++;
			}
			// 业务信息
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			BizTFamilystatus bizTFamilystatus = this.getFamilyStatus(fid, ptid);
			if (null == bizTFamilystatus.getBegdt()) {
				paper1.setBegintime(dateFormat.format(new Date()));
			} else {
				paper1.setBegintime(dateFormat.format(bizTFamilystatus
						.getBegdt()));
			}
			paper1.setMoney(bizTFamilystatus.getMoney().longValue());
			// 其他相关信息
			dateFormat.format(new Date());
			paper1.setPaperdate(dateFormat.format(new Date()).toString());
			paper1.setSerial(UUID.randomUUID().toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != ps) {
				ps.close();
			}
		}
		return paper1;
	}

	/**
	 * 读取业务状态信息
	 * 
	 * @param fid
	 * @param ptid
	 * @return
	 */
	public BizTFamilystatus getFamilyStatus(String fid, String ptid) {
		String sql = "select s.* from "
				+ "biz_t_familystatus s where s.policy_id = "
				+ "(select pt.policy_id from impl_t_papertype pt where pt.papertype_id = '"
				+ ptid + "') and s.family_id = '" + fid + "' and s.flag = 2";
		log.debug(sql);
		return (BizTFamilystatus) getSession().createSQLQuery(sql)
				.addEntity("com.mingda.entity.BizTFamilystatus").uniqueResult();

	}

	/**
	 * 打印记录 flag 0 在用 1 作废
	 * 
	 * @param ptid
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	public ImplTPaperrecord savePrintHandle(String ptid, String fid,
			ImplTPaperrecord implTPaperrecord) throws Exception {

		try {
			// 打印证件记录
			ImplTPaperrecordDAO implTPaperrecordDAO = new ImplTPaperrecordDAO();
			implTPaperrecord.setFamilyId(new Long(fid));
			implTPaperrecord.setFlag("1");
			implTPaperrecord.setImplTPapertype(new ImplTPapertypeDAO()
					.findById(new Long(ptid)));
			implTPaperrecord.setOpttime(new Date());
			implTPaperrecord.setReason("首次打印");
			implTPaperrecord = implTPaperrecordDAO.save(implTPaperrecord);

			// 记录证件信息
			if ("1".equals(ptid)) {
				ImplTPaperinfo1 implTPaperinfo1 = this.getImplTPaperinfo1(fid,
						ptid);
				implTPaperinfo1.setImplTPaperrecord(implTPaperrecord);
				ImplTPaperinfo1DAO implTPaperinfo1DAO = new ImplTPaperinfo1DAO();
				implTPaperinfo1DAO.save(implTPaperinfo1);
			}
			return implTPaperrecord;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否打证
	 * 
	 * @param ptid
	 * @param fid
	 * @return
	 */
	public boolean isPrintHandle(String ptid, String fid) throws Exception {
		boolean flag = false;
		try {
			String sql = "select t.* ,(select sum(pr.flag) from impl_t_paperrecord pr where pr.papertype_id = t.papertype_id and pr.family_id =s.family_id ) as status from impl_t_papertype t, biz_t_familystatus s "
					+ "where s.policy_id = t.policy_id and s.family_id = '"
					+ fid
					+ "' and s.flag = '2' and t.flag ='1' and t.papertype_id ='"
					+ ptid + "'";
			log.debug("判断证件打印:" + sql);
			Query q = getSession().createSQLQuery(sql);
			List list = q.list();
			if (null == list) {
				flag = true;
			} else {
				Object[] object = (Object[]) list.get(0);
				if (null == object[5] || "".equals(object[5].toString())
						|| "0".equals(object[5].toString())) {
					flag = true;
				} else {
					flag = false;
				}
			}
		} catch (Exception re) {
			re.printStackTrace();
			throw re;
		}
		return flag;
	}

	/**
	 * 读家庭信息
	 * 
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	public Object[] getFamilyInfo(String fid) throws Exception {
		String sql = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] familyinfo = new String[2];
		try {
			con = this.getSession().connection();

			// 证件家庭信息

			sql = "select * from info_t_family fam where fam.family_id='" + fid
					+ "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				familyinfo[0] = rs.getString("FAMILYNO");
				familyinfo[1] = rs.getString("MASTERNAME");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return familyinfo;
	}

	/**
	 * 
	 * @param instance
	 * @return
	 * @throws Exception
	 */
	public List<ImplTPaperrecord> getPaperPrintBrowseByFamilyId(
			ImplTPaperrecord instance) throws Exception {
		try {
			ImplTPaperrecordDAO implTPaperrecordDAO = new ImplTPaperrecordDAO();
			return implTPaperrecordDAO.findByExample(instance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 查询在用证件
	 * 
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	public List<ImplTPaperrecord> getPaperUseful(String fid) throws Exception {
		ImplTPaperrecordDAO implTPaperrecordDAO = new ImplTPaperrecordDAO();
		return implTPaperrecordDAO.findPaperUseful(fid, "1");
	}

	/**
	 * 停用证件
	 * 
	 * @param prcid
	 * @throws Exception
	 */
	public void redoPaperHandle(String prcid) throws Exception {
		ImplTPaperrecordDAO implTPaperrecordDAO = new ImplTPaperrecordDAO();
		try {
			ImplTPaperrecord implTPaperrecord = implTPaperrecordDAO
					.findById(new Long(prcid));
			implTPaperrecord.setFlag("0");
			implTPaperrecordDAO.save(implTPaperrecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 验证证件
	 * 
	 * @param papertype
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public ImplTPaperrecord validatePaper(String papertype, String code)
			throws Exception {
		ImplTPaperrecordDAO implTPaperrecordDAO = new ImplTPaperrecordDAO();
		ImplTPaperrecord implTPaperrecord = implTPaperrecordDAO
				.findPaperByCode(papertype, code);
		return implTPaperrecord;
	}
}
