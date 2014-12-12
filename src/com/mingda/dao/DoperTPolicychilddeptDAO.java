package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTPolicychilddept;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTPolicychilddept entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTPolicychilddept
 * @author MyEclipse Persistence Tools
 */

public class DoperTPolicychilddeptDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(DoperTPolicychilddeptDAO.class);
	// property constants
	public static final String STATUS = "status";

	public void save(DoperTPolicychilddept transientInstance) {
		log.debug("saving DoperTPolicychilddept instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTPolicychilddept persistentInstance) {
		log.debug("deleting DoperTPolicychilddept instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTPolicychilddept findById(java.math.BigDecimal id) {
		log.debug("getting DoperTPolicychilddept instance with id: " + id);
		try {
			DoperTPolicychilddept instance = (DoperTPolicychilddept) getSession()
					.get("com.mingda.entity.DoperTPolicychilddept", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTPolicychilddept instance) {
		log.debug("finding DoperTPolicychilddept instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTPolicychilddept").add(
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
		log.debug("finding DoperTPolicychilddept instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTPolicychilddept as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all DoperTPolicychilddept instances");
		try {
			String queryString = "from DoperTPolicychilddept";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTPolicychilddept merge(DoperTPolicychilddept detachedInstance) {
		log.debug("merging DoperTPolicychilddept instance");
		try {
			DoperTPolicychilddept result = (DoperTPolicychilddept) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTPolicychilddept instance) {
		log.debug("attaching dirty DoperTPolicychilddept instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTPolicychilddept instance) {
		log.debug("attaching clean DoperTPolicychilddept instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}