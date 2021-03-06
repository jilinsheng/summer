package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTPolicychildsql;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTPolicychildsql entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTPolicychildsql
 * @author MyEclipse Persistence Tools
 */

public class DoperTPolicychildsqlDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(DoperTPolicychildsqlDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String SQLDESC = "sqldesc";
	public static final String PHYSQL = "physql";
	public static final String LOCSQL = "locsql";
	public static final String STATUS = "status";

	public void save(DoperTPolicychildsql transientInstance) {
		log.debug("saving DoperTPolicychildsql instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTPolicychildsql persistentInstance) {
		log.debug("deleting DoperTPolicychildsql instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTPolicychildsql findById(java.math.BigDecimal id) {
		log.debug("getting DoperTPolicychildsql instance with id: " + id);
		try {
			DoperTPolicychildsql instance = (DoperTPolicychildsql) getSession()
					.get("com.mingda.entity.DoperTPolicychildsql", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTPolicychildsql instance) {
		log.debug("finding DoperTPolicychildsql instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTPolicychildsql").add(
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
		log.debug("finding DoperTPolicychildsql instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTPolicychildsql as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findBySqldesc(Object sqldesc) {
		return findByProperty(SQLDESC, sqldesc);
	}

	public List findByPhysql(Object physql) {
		return findByProperty(PHYSQL, physql);
	}

	public List findByLocsql(Object locsql) {
		return findByProperty(LOCSQL, locsql);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all DoperTPolicychildsql instances");
		try {
			String queryString = "from DoperTPolicychildsql";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTPolicychildsql merge(DoperTPolicychildsql detachedInstance) {
		log.debug("merging DoperTPolicychildsql instance");
		try {
			DoperTPolicychildsql result = (DoperTPolicychildsql) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTPolicychildsql instance) {
		log.debug("attaching dirty DoperTPolicychildsql instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTPolicychildsql instance) {
		log.debug("attaching clean DoperTPolicychildsql instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}