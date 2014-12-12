package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTStandarddept;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTStandarddept entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTStandarddept
 * @author MyEclipse Persistence Tools
 */

public class DoperTStandarddeptDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(DoperTStandarddeptDAO.class);
	// property constants
	public static final String CHECKMONEY = "checkmoney";
	public static final String CHECKDESC = "checkdesc";
	public static final String ACCEXP = "accexp";
	public static final String ACCDESC = "accdesc";
	public static final String FLAG = "flag";
	public static final String ACCEXPPHYSQL = "accexpphysql";
	public static final String ACCEXPLOCSQL = "accexplocsql";

	public void save(DoperTStandarddept transientInstance) {
		log.debug("saving DoperTStandarddept instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTStandarddept persistentInstance) {
		log.debug("deleting DoperTStandarddept instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTStandarddept findById(java.math.BigDecimal id) {
		log.debug("getting DoperTStandarddept instance with id: " + id);
		try {
			DoperTStandarddept instance = (DoperTStandarddept) getSession()
					.get("com.mingda.entity.DoperTStandarddept", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTStandarddept instance) {
		log.debug("finding DoperTStandarddept instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTStandarddept").add(
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
		log.debug("finding DoperTStandarddept instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTStandarddept as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCheckmoney(Object checkmoney) {
		return findByProperty(CHECKMONEY, checkmoney);
	}

	public List findByCheckdesc(Object checkdesc) {
		return findByProperty(CHECKDESC, checkdesc);
	}

	public List findByAccexp(Object accexp) {
		return findByProperty(ACCEXP, accexp);
	}

	public List findByAccdesc(Object accdesc) {
		return findByProperty(ACCDESC, accdesc);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findByAccexpphysql(Object accexpphysql) {
		return findByProperty(ACCEXPPHYSQL, accexpphysql);
	}

	public List findByAccexplocsql(Object accexplocsql) {
		return findByProperty(ACCEXPLOCSQL, accexplocsql);
	}

	public List findAll() {
		log.debug("finding all DoperTStandarddept instances");
		try {
			String queryString = "from DoperTStandarddept";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTStandarddept merge(DoperTStandarddept detachedInstance) {
		log.debug("merging DoperTStandarddept instance");
		try {
			DoperTStandarddept result = (DoperTStandarddept) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTStandarddept instance) {
		log.debug("attaching dirty DoperTStandarddept instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTStandarddept instance) {
		log.debug("attaching clean DoperTStandarddept instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}