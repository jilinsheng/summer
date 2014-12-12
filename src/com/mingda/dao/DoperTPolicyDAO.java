package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTPolicy;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTPolicy entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTPolicy
 * @author MyEclipse Persistence Tools
 */

public class DoperTPolicyDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DoperTPolicyDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DESCR = "descr";
	public static final String ISPRINT = "isprint";
	public static final String NOTE = "note";
	public static final String FLAG = "flag";
	public static final String MENUTYPE = "menutype";
	public static final String ACCTYPE = "acctype";

	public void save(DoperTPolicy transientInstance) {
		log.debug("saving DoperTPolicy instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTPolicy persistentInstance) {
		log.debug("deleting DoperTPolicy instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTPolicy findById(java.math.BigDecimal id) {
		log.debug("getting DoperTPolicy instance with id: " + id);
		try {
			DoperTPolicy instance = (DoperTPolicy) getSession().get(
					"com.mingda.entity.DoperTPolicy", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTPolicy instance) {
		log.debug("finding DoperTPolicy instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTPolicy").add(
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
		log.debug("finding DoperTPolicy instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTPolicy as model where model."
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

	public List findByDescr(Object descr) {
		return findByProperty(DESCR, descr);
	}

	public List findByIsprint(Object isprint) {
		return findByProperty(ISPRINT, isprint);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findByMenutype(Object menutype) {
		return findByProperty(MENUTYPE, menutype);
	}

	public List findByAcctype(Object acctype) {
		return findByProperty(ACCTYPE, acctype);
	}

	public List findAll() {
		log.debug("finding all DoperTPolicy instances");
		try {
			String queryString = "from DoperTPolicy";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTPolicy merge(DoperTPolicy detachedInstance) {
		log.debug("merging DoperTPolicy instance");
		try {
			DoperTPolicy result = (DoperTPolicy) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTPolicy instance) {
		log.debug("attaching dirty DoperTPolicy instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTPolicy instance) {
		log.debug("attaching clean DoperTPolicy instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}