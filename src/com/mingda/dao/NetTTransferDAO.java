package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.NetTTransfer;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * NetTTransfer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.NetTTransfer
 * @author MyEclipse Persistence Tools
 */

public class NetTTransferDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(NetTTransferDAO.class);
	// property constants
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String FLAG = "flag";

	public String save(NetTTransfer transientInstance) {
		log.debug("saving NetTTransfer instance");
		String id= "";
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
			id=transientInstance.getTransferId().toString();
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return id;
	}

	public void delete(NetTTransfer persistentInstance) {
		log.debug("deleting NetTTransfer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public NetTTransfer findById(java.lang.Long id) {
		log.debug("getting NetTTransfer instance with id: " + id);
		try {
			NetTTransfer instance = (NetTTransfer) getSession().get(
					"com.mingda.entity.NetTTransfer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	public List findByExample(NetTTransfer instance) {
		log.debug("finding NetTTransfer instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.NetTTransfer").add(
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
		log.debug("finding NetTTransfer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from NetTTransfer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findAll() {
		log.debug("finding all NetTTransfer instances");
		try {
			String queryString = "from NetTTransfer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public NetTTransfer merge(NetTTransfer detachedInstance) {
		log.debug("merging NetTTransfer instance");
		try {
			NetTTransfer result = (NetTTransfer) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(NetTTransfer instance) {
		log.debug("attaching dirty NetTTransfer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(NetTTransfer instance) {
		log.debug("attaching clean NetTTransfer instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}