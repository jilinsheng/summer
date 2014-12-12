package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTInfoapprove;

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
 * BizTInfoapprove entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTInfoapprove
 * @author MyEclipse Persistence Tools
 */

public class BizTInfoapproveDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTInfoapproveDAO.class);
	// property constants
	public static final String STATUS = "status";
	public static final String APPSTATE1 = "appstate1";
	public static final String APPCONTENT1 = "appcontent1";
	public static final String APPPERSON1 = "appperson1";
	public static final String APPSTATE2 = "appstate2";
	public static final String APPCONTENT2 = "appcontent2";
	public static final String APPPERSON2 = "appperson2";
	public static final String APPSTATE3 = "appstate3";
	public static final String APPCONTENT3 = "appcontent3";
	public static final String APPPERSON3 = "appperson3";
	public static final String APPSTATE4 = "appstate4";
	public static final String APPCONTENT4 = "appcontent4";
	public static final String APPPERSON4 = "appperson4";
	public static final String APPSTATE5 = "appstate5";
	public static final String APPCONTENT5 = "appcontent5";
	public static final String APPPERSON5 = "appperson5";

	public void save(BizTInfoapprove transientInstance) {
		log.debug("saving BizTInfoapprove instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTInfoapprove persistentInstance) {
		log.debug("deleting BizTInfoapprove instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTInfoapprove findById(java.lang.Long id) {
		log.debug("getting BizTInfoapprove instance with id: " + id);
		try {
			BizTInfoapprove instance = (BizTInfoapprove) getSession().get(
					"com.mingda.entity.BizTInfoapprove", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTInfoapprove instance) {
		log.debug("finding BizTInfoapprove instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTInfoapprove").add(
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
		log.debug("finding BizTInfoapprove instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTInfoapprove as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByAppstate1(Object appstate1) {
		return findByProperty(APPSTATE1, appstate1);
	}

	public List findByAppcontent1(Object appcontent1) {
		return findByProperty(APPCONTENT1, appcontent1);
	}

	public List findByAppperson1(Object appperson1) {
		return findByProperty(APPPERSON1, appperson1);
	}

	public List findByAppstate2(Object appstate2) {
		return findByProperty(APPSTATE2, appstate2);
	}

	public List findByAppcontent2(Object appcontent2) {
		return findByProperty(APPCONTENT2, appcontent2);
	}

	public List findByAppperson2(Object appperson2) {
		return findByProperty(APPPERSON2, appperson2);
	}

	public List findByAppstate3(Object appstate3) {
		return findByProperty(APPSTATE3, appstate3);
	}

	public List findByAppcontent3(Object appcontent3) {
		return findByProperty(APPCONTENT3, appcontent3);
	}

	public List findByAppperson3(Object appperson3) {
		return findByProperty(APPPERSON3, appperson3);
	}

	public List findByAppstate4(Object appstate4) {
		return findByProperty(APPSTATE4, appstate4);
	}

	public List findByAppcontent4(Object appcontent4) {
		return findByProperty(APPCONTENT4, appcontent4);
	}

	public List findByAppperson4(Object appperson4) {
		return findByProperty(APPPERSON4, appperson4);
	}

	public List findByAppstate5(Object appstate5) {
		return findByProperty(APPSTATE5, appstate5);
	}

	public List findByAppcontent5(Object appcontent5) {
		return findByProperty(APPCONTENT5, appcontent5);
	}

	public List findByAppperson5(Object appperson5) {
		return findByProperty(APPPERSON5, appperson5);
	}

	public List findAll() {
		log.debug("finding all BizTInfoapprove instances");
		try {
			String queryString = "from BizTInfoapprove";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTInfoapprove merge(BizTInfoapprove detachedInstance) {
		log.debug("merging BizTInfoapprove instance");
		try {
			BizTInfoapprove result = (BizTInfoapprove) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTInfoapprove instance) {
		log.debug("attaching dirty BizTInfoapprove instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTInfoapprove instance) {
		log.debug("attaching clean BizTInfoapprove instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}