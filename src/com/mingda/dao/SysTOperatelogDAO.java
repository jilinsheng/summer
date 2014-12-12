package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTOperatelog;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTOperatelog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTOperatelog
 * @author MyEclipse Persistence Tools
 */

public class SysTOperatelogDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTOperatelogDAO.class);
	// property constants
	public static final String LOGSORT = "logsort";
	public static final String CONTENT = "content";
	public static final String IPADDRESS = "ipaddress";
	public static final String SERVERIP = "serverip";

	public void save(SysTOperatelog transientInstance) {
		log.debug("saving SysTOperatelog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTOperatelog persistentInstance) {
		log.debug("deleting SysTOperatelog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTOperatelog findById(java.lang.Long id) {
		log.debug("getting SysTOperatelog instance with id: " + id);
		try {
			SysTOperatelog instance = (SysTOperatelog) getSession().get(
					"com.mingda.entity.SysTOperatelog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTOperatelog instance) {
		log.debug("finding SysTOperatelog instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTOperatelog").add(
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
		log.debug("finding SysTOperatelog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTOperatelog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLogsort(Object logsort) {
		return findByProperty(LOGSORT, logsort);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByIpaddress(Object ipaddress) {
		return findByProperty(IPADDRESS, ipaddress);
	}

	public List findByServerip(Object serverip) {
		return findByProperty(SERVERIP, serverip);
	}

	public List findAll() {
		log.debug("finding all SysTOperatelog instances");
		try {
			String queryString = "from SysTOperatelog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTOperatelog merge(SysTOperatelog detachedInstance) {
		log.debug("merging SysTOperatelog instance");
		try {
			SysTOperatelog result = (SysTOperatelog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTOperatelog instance) {
		log.debug("attaching dirty SysTOperatelog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTOperatelog instance) {
		log.debug("attaching clean SysTOperatelog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}