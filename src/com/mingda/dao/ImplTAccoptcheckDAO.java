package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTAccoptcheck;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImplTAccoptcheck entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTAccoptcheck
 * @author MyEclipse Persistence Tools
 */

public class ImplTAccoptcheckDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ImplTAccoptcheckDAO.class);
	// property constants
	public static final String COUNTMONEY = "countmoney";
	public static final String CHECKMONEY = "checkmoney";
	public static final String CHECKCHILDMONEY = "checkchildmoney";

	public void save(ImplTAccoptcheck transientInstance) {
		log.debug("saving ImplTAccoptcheck instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTAccoptcheck persistentInstance) {
		log.debug("deleting ImplTAccoptcheck instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImplTAccoptcheck findById(java.math.BigDecimal id) {
		log.debug("getting ImplTAccoptcheck instance with id: " + id);
		try {
			ImplTAccoptcheck instance = (ImplTAccoptcheck) getSession().get(
					"com.mingda.entity.ImplTAccoptcheck", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTAccoptcheck instance) {
		log.debug("finding ImplTAccoptcheck instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTAccoptcheck").add(
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
		log.debug("finding ImplTAccoptcheck instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ImplTAccoptcheck as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCountmoney(Object countmoney) {
		return findByProperty(COUNTMONEY, countmoney);
	}

	public List findByCheckmoney(Object checkmoney) {
		return findByProperty(CHECKMONEY, checkmoney);
	}

	public List findByCheckchildmoney(Object checkchildmoney) {
		return findByProperty(CHECKCHILDMONEY, checkchildmoney);
	}

	public List findAll() {
		log.debug("finding all ImplTAccoptcheck instances");
		try {
			String queryString = "from ImplTAccoptcheck";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTAccoptcheck merge(ImplTAccoptcheck detachedInstance) {
		log.debug("merging ImplTAccoptcheck instance");
		try {
			ImplTAccoptcheck result = (ImplTAccoptcheck) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTAccoptcheck instance) {
		log.debug("attaching dirty ImplTAccoptcheck instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTAccoptcheck instance) {
		log.debug("attaching clean ImplTAccoptcheck instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}