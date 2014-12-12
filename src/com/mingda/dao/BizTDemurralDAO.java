package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTDemurral;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTDemurral entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTDemurral
 * @author MyEclipse Persistence Tools
 */

public class BizTDemurralDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTDemurralDAO.class);
	// property constants
	public static final String APPROVE = "approve";
	public static final String APPROVETYPE = "approvetype";
	public static final String REASON = "reason";
	public static final String CONTENT = "content";

	public void save(BizTDemurral transientInstance) {
		log.debug("saving BizTDemurral instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTDemurral persistentInstance) {
		log.debug("deleting BizTDemurral instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTDemurral findById(java.math.BigDecimal id) {
		log.debug("getting BizTDemurral instance with id: " + id);
		try {
			BizTDemurral instance = (BizTDemurral) getSession().get(
					"com.mingda.entity.BizTDemurral", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTDemurral instance) {
		log.debug("finding BizTDemurral instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTDemurral").add(
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
		log.debug("finding BizTDemurral instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTDemurral as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByApprove(Object approve) {
		return findByProperty(APPROVE, approve);
	}

	public List findByApprovetype(Object approvetype) {
		return findByProperty(APPROVETYPE, approvetype);
	}

	public List findByReason(Object reason) {
		return findByProperty(REASON, reason);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}
	public List<BizTDemurral> findBySSID(String ssid){
		String queryString = "from BizTDemurral de where de.bizTFamilystatus ='"+ssid+"' order by de.publishdate";
		Query queryObject = getSession().createQuery(queryString);
		return queryObject.list();
	}
	public List findAll() {
		log.debug("finding all BizTDemurral instances");
		try {
			String queryString = "from BizTDemurral";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTDemurral merge(BizTDemurral detachedInstance) {
		log.debug("merging BizTDemurral instance");
		try {
			BizTDemurral result = (BizTDemurral) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTDemurral instance) {
		log.debug("attaching dirty BizTDemurral instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTDemurral instance) {
		log.debug("attaching clean BizTDemurral instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}