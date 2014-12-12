package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTPolicymenu;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTPolicymenu entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTPolicymenu
 * @author MyEclipse Persistence Tools
 */

public class DoperTPolicymenuDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DoperTPolicymenuDAO.class);

	// property constants

	public void save(DoperTPolicymenu transientInstance) {
		log.debug("saving DoperTPolicymenu instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTPolicymenu persistentInstance) {
		log.debug("deleting DoperTPolicymenu instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTPolicymenu findById(java.math.BigDecimal id) {
		log.debug("getting DoperTPolicymenu instance with id: " + id);
		try {
			DoperTPolicymenu instance = (DoperTPolicymenu) getSession().get(
					"com.mingda.entity.DoperTPolicymenu", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTPolicymenu instance) {
		log.debug("finding DoperTPolicymenu instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTPolicymenu").add(
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
		log.debug("finding DoperTPolicymenu instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTPolicymenu as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all DoperTPolicymenu instances");
		try {
			String queryString = "from DoperTPolicymenu";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTPolicymenu merge(DoperTPolicymenu detachedInstance) {
		log.debug("merging DoperTPolicymenu instance");
		try {
			DoperTPolicymenu result = (DoperTPolicymenu) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTPolicymenu instance) {
		log.debug("attaching dirty DoperTPolicymenu instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTPolicymenu instance) {
		log.debug("attaching clean DoperTPolicymenu instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}