package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTInfolog;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTInfolog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTInfolog
 * @author MyEclipse Persistence Tools
 */

public class SysTInfologDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTInfologDAO.class);
	// property constants
	public static final String FAMILYID = "familyid";
	public static final String CODE = "code";
	public static final String ENTITYID = "entityid";
	public static final String LOG = "log";

	public void save(SysTInfolog transientInstance) {
		log.debug("saving SysTInfolog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTInfolog persistentInstance) {
		log.debug("deleting SysTInfolog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTInfolog findById(java.lang.Long id) {
		log.debug("getting SysTInfolog instance with id: " + id);
		try {
			SysTInfolog instance = (SysTInfolog) getSession().get(
					"com.mingda.entity.SysTInfolog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTInfolog instance) {
		log.debug("finding SysTInfolog instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTInfolog").add(
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
		log.debug("finding SysTInfolog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTInfolog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFamilyid(Object familyid) {
		return findByProperty(FAMILYID, familyid);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByEntityid(Object entityid) {
		return findByProperty(ENTITYID, entityid);
	}

	public List findByLog(Object log) {
		return findByProperty(LOG, log);
	}

	public List findByCodeAndEntityid(Object code, Object entityid) {
		try {
			String queryString = "from SysTInfolog as model where model.code = ? and model.entityid = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, code);
			queryObject.setParameter(1, entityid);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all SysTInfolog instances");
		try {
			String queryString = "from SysTInfolog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTInfolog merge(SysTInfolog detachedInstance) {
		log.debug("merging SysTInfolog instance");
		try {
			SysTInfolog result = (SysTInfolog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTInfolog instance) {
		log.debug("attaching dirty SysTInfolog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTInfolog instance) {
		log.debug("attaching clean SysTInfolog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}