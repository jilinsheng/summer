package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTFamilystatus;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTFamilystatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTFamilystatus
 * @author MyEclipse Persistence Tools
 */

public class BizTFamilystatusDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTFamilystatusDAO.class);
	// property constants
	public static final String MONEY = "money";
	public static final String FLAG = "flag";

	public void save(BizTFamilystatus transientInstance) {
		log.debug("saving BizTFamilystatus instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTFamilystatus persistentInstance) {
		log.debug("deleting BizTFamilystatus instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTFamilystatus findById(java.math.BigDecimal id) {
		log.debug("getting BizTFamilystatus instance with id: " + id);
		try {
			BizTFamilystatus instance = (BizTFamilystatus) getSession().get(
					"com.mingda.entity.BizTFamilystatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTFamilystatus instance) {
		log.debug("finding BizTFamilystatus instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTFamilystatus").add(
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
		log.debug("finding BizTFamilystatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTFamilystatus as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMoney(Object money) {
		return findByProperty(MONEY, money);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findAll() {
		log.debug("finding all BizTFamilystatus instances");
		try {
			String queryString = "from BizTFamilystatus";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTFamilystatus merge(BizTFamilystatus detachedInstance) {
		log.debug("merging BizTFamilystatus instance");
		try {
			BizTFamilystatus result = (BizTFamilystatus) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTFamilystatus instance) {
		log.debug("attaching dirty BizTFamilystatus instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTFamilystatus instance) {
		log.debug("attaching clean BizTFamilystatus instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}