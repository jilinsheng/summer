package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTPost;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTPost entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTPost
 * @author MyEclipse Persistence Tools
 */

public class SysTPostDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTPostDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DETAIL = "detail";
	public static final String SEQUENCE = "sequence";
	public static final String STATUS = "status";

	public void save(SysTPost transientInstance) {
		log.debug("saving SysTPost instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTPost persistentInstance) {
		log.debug("deleting SysTPost instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTPost findById(java.lang.Long id) {
		log.debug("getting SysTPost instance with id: " + id);
		try {
			SysTPost instance = (SysTPost) getSession().get(
					"com.mingda.entity.SysTPost", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTPost instance) {
		log.debug("finding SysTPost instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTPost").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SysTPost instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTPost as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all SysTPost instances");
		try {
			String queryString = "from SysTPost";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTPost merge(SysTPost detachedInstance) {
		log.debug("merging SysTPost instance");
		try {
			SysTPost result = (SysTPost) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTPost instance) {
		log.debug("attaching dirty SysTPost instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTPost instance) {
		log.debug("attaching clean SysTPost instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}