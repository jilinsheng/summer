package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTOrgext;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTOrgext entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTOrgext
 * @author MyEclipse Persistence Tools
 */

public class SysTOrgextDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTOrgextDAO.class);
	// property constants
	public static final String CONTEXT = "context";

	public void save(SysTOrgext transientInstance) {
		log.debug("saving SysTOrgext instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTOrgext persistentInstance) {
		log.debug("deleting SysTOrgext instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTOrgext findById(com.mingda.entity.SysTOrgextId id) {
		log.debug("getting SysTOrgext instance with id: " + id);
		try {
			SysTOrgext instance = (SysTOrgext) getSession().get(
					"com.mingda.entity.SysTOrgext", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTOrgext instance) {
		log.debug("finding SysTOrgext instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTOrgext").add(
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
		log.debug("finding SysTOrgext instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTOrgext as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByContext(Object context) {
		return findByProperty(CONTEXT, context);
	}

	public List findAll() {
		log.debug("finding all SysTOrgext instances");
		try {
			String queryString = "from SysTOrgext";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTOrgext merge(SysTOrgext detachedInstance) {
		log.debug("merging SysTOrgext instance");
		try {
			SysTOrgext result = (SysTOrgext) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTOrgext instance) {
		log.debug("attaching dirty SysTOrgext instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTOrgext instance) {
		log.debug("attaching clean SysTOrgext instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}