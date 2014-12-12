package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTStandard;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTStandard entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTStandard
 * @author MyEclipse Persistence Tools
 */

public class DoperTStandardDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DoperTStandardDAO.class);
	// property constants
	public static final String DESCR = "descr";
	public static final String PHYSQL = "physql";
	public static final String LOCSQL = "locsql";
	public static final String PLANMONEY = "planmoney";
	public static final String PLANDESC = "plandesc";
	public static final String FLAG = "flag";
	public static final String SUPERPOLICY = "superpolicy";

	public void save(DoperTStandard transientInstance) {
		log.debug("saving DoperTStandard instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTStandard persistentInstance) {
		log.debug("deleting DoperTStandard instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTStandard findById(java.math.BigDecimal id) {
		log.debug("getting DoperTStandard instance with id: " + id);
		try {
			DoperTStandard instance = (DoperTStandard) getSession().get(
					"com.mingda.entity.DoperTStandard", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTStandard instance) {
		log.debug("finding DoperTStandard instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTStandard").add(
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
		log.debug("finding DoperTStandard instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTStandard as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDescr(Object descr) {
		return findByProperty(DESCR, descr);
	}

	public List findByPhysql(Object physql) {
		return findByProperty(PHYSQL, physql);
	}

	public List findByLocsql(Object locsql) {
		return findByProperty(LOCSQL, locsql);
	}

	public List findByPlanmoney(Object planmoney) {
		return findByProperty(PLANMONEY, planmoney);
	}

	public List findByPlandesc(Object plandesc) {
		return findByProperty(PLANDESC, plandesc);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findBySuperpolicy(Object superpolicy) {
		return findByProperty(SUPERPOLICY, superpolicy);
	}

	public List findAll() {
		log.debug("finding all DoperTStandard instances");
		try {
			String queryString = "from DoperTStandard";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTStandard merge(DoperTStandard detachedInstance) {
		log.debug("merging DoperTStandard instance");
		try {
			DoperTStandard result = (DoperTStandard) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTStandard instance) {
		log.debug("attaching dirty DoperTStandard instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTStandard instance) {
		log.debug("attaching clean DoperTStandard instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}