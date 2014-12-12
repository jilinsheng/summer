package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTDictionary;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTDictionary entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTDictionary
 * @author MyEclipse Persistence Tools
 */

public class SysTDictionaryDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTDictionaryDAO.class);
	// property constants
	public static final String ITEM = "item";
	public static final String SUPERDICID = "superdicid";
	public static final String SEQUENCE = "sequence";
	public static final String STATUS = "status";

	public SysTDictionary save(SysTDictionary transientInstance) {
		log.debug("saving SysTDictionary instance");
		try {
			if (transientInstance.getDictionaryId() != null) {
				String itname = transientInstance.getItem();
				String status = transientInstance.getStatus();
				transientInstance = this.findById(transientInstance
						.getDictionaryId());
				if (itname != null) {
					transientInstance.setItem(itname);
				}
				if (status != null) {
					transientInstance.setStatus(status);
				}
			}
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return transientInstance;
	}

	public void delete(SysTDictionary persistentInstance) {
		log.debug("deleting SysTDictionary instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTDictionary findById(java.lang.Long id) {
		log.debug("getting SysTDictionary instance with id: " + id);
		try {
			SysTDictionary instance = (SysTDictionary) getSession().get(
					"com.mingda.entity.SysTDictionary", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTDictionary instance) {
		log.debug("finding SysTDictionary instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTDictionary").add(
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
		log.debug("finding SysTDictionary instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTDictionary as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItem(Object item) {
		return findByProperty(ITEM, item);
	}

	public List findBySuperdicid(Object superdicid) {
		return findByProperty(SUPERDICID, superdicid);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all SysTDictionary instances");
		try {
			String queryString = "from SysTDictionary order by sequence";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTDictionary merge(SysTDictionary detachedInstance) {
		log.debug("merging SysTDictionary instance");
		try {
			SysTDictionary result = (SysTDictionary) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTDictionary instance) {
		log.debug("attaching dirty SysTDictionary instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTDictionary instance) {
		log.debug("attaching clean SysTDictionary instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}