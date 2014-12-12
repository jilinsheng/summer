package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.DoperTPolicychild;

/**
 * A data access object (DAO) providing persistence and search support for
 * DoperTPolicychild entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.DoperTPolicychild
 * @author MyEclipse Persistence Tools
 */

public class DoperTPolicychildDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(DoperTPolicychildDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String POLICYDESC = "policydesc";
	public static final String SQLTYPE = "sqltype";
	public static final String STATUS = "status";
	public static final String MONEYTYPE = "moneytype";

	public void save(DoperTPolicychild transientInstance) {
		log.debug("saving DoperTPolicychild instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DoperTPolicychild persistentInstance) {
		log.debug("deleting DoperTPolicychild instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DoperTPolicychild findById(java.math.BigDecimal id) {
		log.debug("getting DoperTPolicychild instance with id: " + id);
		try {
			DoperTPolicychild instance = (DoperTPolicychild) getSession().get(
					"com.mingda.entity.DoperTPolicychild", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DoperTPolicychild instance) {
		log.debug("finding DoperTPolicychild instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.DoperTPolicychild").add(
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
		log.debug("finding DoperTPolicychild instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DoperTPolicychild as model where model."
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

	public List findByPolicydesc(Object policydesc) {
		return findByProperty(POLICYDESC, policydesc);
	}

	public List findBySqltype(Object sqltype) {
		return findByProperty(SQLTYPE, sqltype);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByMoneytype(Object moneytype) {
		return findByProperty(MONEYTYPE, moneytype);
	}

	public List findAll() {
		log.debug("finding all DoperTPolicychild instances");
		try {
			String queryString = "from DoperTPolicychild";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DoperTPolicychild merge(DoperTPolicychild detachedInstance) {
		log.debug("merging DoperTPolicychild instance");
		try {
			DoperTPolicychild result = (DoperTPolicychild) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DoperTPolicychild instance) {
		log.debug("attaching dirty DoperTPolicychild instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DoperTPolicychild instance) {
		log.debug("attaching clean DoperTPolicychild instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}