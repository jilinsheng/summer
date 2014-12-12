package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.NetTReceive;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * NetTReceive entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.NetTReceive
 * @author MyEclipse Persistence Tools
 */

public class NetTReceiveDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(NetTReceiveDAO.class);
	// property constants
	public static final String FLAG = "flag";
	public static final String CONTENT = "content";

	public void save(NetTReceive transientInstance) {
		log.debug("saving NetTReceive instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(NetTReceive persistentInstance) {
		log.debug("deleting NetTReceive instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public NetTReceive findById(java.math.BigDecimal id) {
		log.debug("getting NetTReceive instance with id: " + id);
		try {
			NetTReceive instance = (NetTReceive) getSession().get(
					"com.mingda.entity.NetTReceive", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public NetTReceive findByNoticeId(String noticeid, String organizationid) {
		try {
			String queryString = "from NetTReceive as model where model.sysTOrganization='"+organizationid+"' and model.netTNotice='"+noticeid+"'";
			Query queryObject = getSession().createQuery(queryString);
			if (null != queryObject.list()&&0<queryObject.list().size()) {
				return (NetTReceive) queryObject.list().get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(NetTReceive instance) {
		log.debug("finding NetTReceive instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.NetTReceive").add(
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
		log.debug("finding NetTReceive instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from NetTReceive as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all NetTReceive instances");
		try {
			String queryString = "from NetTReceive";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public NetTReceive merge(NetTReceive detachedInstance) {
		log.debug("merging NetTReceive instance");
		try {
			NetTReceive result = (NetTReceive) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(NetTReceive instance) {
		log.debug("attaching dirty NetTReceive instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(NetTReceive instance) {
		log.debug("attaching clean NetTReceive instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}