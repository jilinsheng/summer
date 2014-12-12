package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTValidatelog;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTValidatelog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTValidatelog
 * @author MyEclipse Persistence Tools
 */

public class BizTValidatelogDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTValidatelogDAO.class);
	// property constants
	public static final String CONTENT = "content";
	public static final String STATUS = "status";
	public static final String FAMILY_ID = "familyId";

	public void save(BizTValidatelog transientInstance) {
		log.debug("saving BizTValidatelog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTValidatelog persistentInstance) {
		log.debug("deleting BizTValidatelog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTValidatelog findById(java.lang.Long id) {
		log.debug("getting BizTValidatelog instance with id: " + id);
		try {
			BizTValidatelog instance = (BizTValidatelog) getSession().get(
					"com.mingda.entity.BizTValidatelog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTValidatelog instance) {
		log.debug("finding BizTValidatelog instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTValidatelog").add(
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
		log.debug("finding BizTValidatelog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTValidatelog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByFamilyId(Object familyId) {
		return findByProperty(FAMILY_ID, familyId);
	}

	public List findAll() {
		log.debug("finding all BizTValidatelog instances");
		try {
			String queryString = "from BizTValidatelog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTValidatelog merge(BizTValidatelog detachedInstance) {
		log.debug("merging BizTValidatelog instance");
		try {
			BizTValidatelog result = (BizTValidatelog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTValidatelog instance) {
		log.debug("attaching dirty BizTValidatelog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTValidatelog instance) {
		log.debug("attaching clean BizTValidatelog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}