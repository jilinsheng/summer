package com.mingda.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTOrganization;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTOrganization entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTOrganization
 * @author MyEclipse Persistence Tools
 */

public class SysTOrganizationDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTOrganizationDAO.class);
	// property constants
	public static final String SERIALNUMBER = "serialnumber";
	public static final String ORGNAME = "orgname";
	public static final String FULLNAME = "fullname";
	public static final String PARENTORGID = "parentorgid";
	public static final String DEPTH = "depth";
	public static final String STATUS = "status";

	public void save(SysTOrganization transientInstance) {
		log.debug("saving SysTOrganization instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTOrganization persistentInstance) {
		log.debug("deleting SysTOrganization instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTOrganization findById(java.lang.String id) {
		log.debug("getting SysTOrganization instance with id: " + id);
		try {
			SysTOrganization instance = (SysTOrganization) getSession().get(
					"com.mingda.entity.SysTOrganization", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public String getOrgExtValue(String dictid, String orgid) {
		try {
			Object o = this.getSession().createSQLQuery(
					"select orgext.context from sys_t_orgext orgext where orgext.infomationtype ='"
							+ dictid + "' and orgext.organization_id ='"
							+ orgid + "'").uniqueResult();
			if (null == o) {
				return "";
			} else {
				return o.toString();
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByExample(SysTOrganization instance) {
		log.debug("finding SysTOrganization instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTOrganization").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SysTOrganization instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTOrganization as model where model."
					+ propertyName + "= ? order by model.organizationId";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SysTOrganization> findByParentId(Object value) {
		try {
			String queryString = "from SysTOrganization as model where model.parentorgid = "
					+ value
					+ " or model.organizationId ="
					+ value
					+ " order by model.organizationId";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SysTOrganization> findByDepth(Object value, String orgid) {
		try {
			String queryString = "from SysTOrganization as model where model.depth = "
					+ value
					+ " and model.organizationId  like '"
					+ orgid
					+ "%' order by model.organizationId";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySerialnumber(Object serialnumber) {
		return findByProperty(SERIALNUMBER, serialnumber);
	}

	public List findByOrgname(Object orgname) {
		return findByProperty(ORGNAME, orgname);
	}

	public List findByFullname(Object fullname) {
		return findByProperty(FULLNAME, fullname);
	}

	public List<SysTOrganization> findByParentorgid(Object parentorgid) {
		return findByProperty(PARENTORGID, parentorgid);
	}

	public BigDecimal findByMaxOrg(Object parentorgid) {
		String sql = "select decode( max(o.organization_id) ,null,"
				+ parentorgid
				+ "*100, max(o.organization_id) )+1 from sys_t_organization o where o.parentorgid ='"
				+ parentorgid + "'";
		return (BigDecimal) getSession().createSQLQuery(sql).uniqueResult();
	}

	public List<SysTOrganization> findByDepth(Object depth) {
		return findByProperty(DEPTH, depth);
	}

	public List<SysTOrganization> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<SysTOrganization> findAll() {
		log.debug("finding all SysTOrganization instances");
		try {
			String queryString = "from SysTOrganization";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTOrganization merge(SysTOrganization detachedInstance) {
		log.debug("merging SysTOrganization instance");
		try {
			SysTOrganization result = (SysTOrganization) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTOrganization instance) {
		log.debug("attaching dirty SysTOrganization instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTOrganization instance) {
		log.debug("attaching clean SysTOrganization instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}