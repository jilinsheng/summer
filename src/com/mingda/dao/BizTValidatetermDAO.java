package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTValidateterm;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTValidateterm entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTValidateterm
 * @author MyEclipse Persistence Tools
 */

public class BizTValidatetermDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTValidatetermDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ITEMDESC = "itemdesc";
	public static final String PHYSQL = "physql";
	public static final String LOCSQL = "locsql";
	public static final String STATUS = "status";

	public void save(BizTValidateterm transientInstance) {
		log.debug("saving BizTValidateterm instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTValidateterm persistentInstance) {
		log.debug("deleting BizTValidateterm instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTValidateterm findById(java.lang.Long id) {
		log.debug("getting BizTValidateterm instance with id: " + id);
		try {
			BizTValidateterm instance = (BizTValidateterm) getSession().get(
					"com.mingda.entity.BizTValidateterm", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTValidateterm instance) {
		log.debug("finding BizTValidateterm instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTValidateterm").add(
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
		log.debug("finding BizTValidateterm instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTValidateterm as model where model."
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

	public List findByItemdesc(Object itemdesc) {
		return findByProperty(ITEMDESC, itemdesc);
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
		log.debug("finding all BizTValidateterm instances");
		try {
			String queryString = "from BizTValidateterm";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List getTerms(String flag) {
		log.debug("finding all BizTValidateterm instances");
		try {
			String queryString = "from BizTValidateterm where status=? and physql is not null order by validatetermId";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, flag);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTValidateterm merge(BizTValidateterm detachedInstance) {
		log.debug("merging BizTValidateterm instance");
		try {
			BizTValidateterm result = (BizTValidateterm) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTValidateterm instance) {
		log.debug("attaching dirty BizTValidateterm instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTValidateterm instance) {
		log.debug("attaching clean BizTValidateterm instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}