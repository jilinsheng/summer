package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTIniqueryitem;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTIniqueryitem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTIniqueryitem
 * @author MyEclipse Persistence Tools
 */

public class SysTIniqueryitemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTIniqueryitemDAO.class);
	// property constants
	public static final String FIELDNAMELOC = "fieldnameloc";
	public static final String MATCHEXP = "matchexp";
	public static final String LOGICEXP = "logicexp";

	public void save(SysTIniqueryitem transientInstance) {
		log.debug("saving SysTIniqueryitem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTIniqueryitem persistentInstance) {
		log.debug("deleting SysTIniqueryitem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTIniqueryitem findById(java.math.BigDecimal id) {
		log.debug("getting SysTIniqueryitem instance with id: " + id);
		try {
			SysTIniqueryitem instance = (SysTIniqueryitem) getSession().get(
					"com.mingda.entity.SysTIniqueryitem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTIniqueryitem instance) {
		log.debug("finding SysTIniqueryitem instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTIniqueryitem").add(
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
		log.debug("finding SysTIniqueryitem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTIniqueryitem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFieldnameloc(Object fieldnameloc) {
		return findByProperty(FIELDNAMELOC, fieldnameloc);
	}

	public List findByMatchexp(Object matchexp) {
		return findByProperty(MATCHEXP, matchexp);
	}

	public List findByLogicexp(Object logicexp) {
		return findByProperty(LOGICEXP, logicexp);
	}

	public List findAll() {
		log.debug("finding all SysTIniqueryitem instances");
		try {
			String queryString = "from SysTIniqueryitem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTIniqueryitem merge(SysTIniqueryitem detachedInstance) {
		log.debug("merging SysTIniqueryitem instance");
		try {
			SysTIniqueryitem result = (SysTIniqueryitem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTIniqueryitem instance) {
		log.debug("attaching dirty SysTIniqueryitem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTIniqueryitem instance) {
		log.debug("attaching clean SysTIniqueryitem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}