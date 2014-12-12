package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTDictsort;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTDictsort entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTDictsort
 * @author MyEclipse Persistence Tools
 */

public class SysTDictsortDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTDictsortDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String SEQUENCE = "sequence";
	public static final String STATUS = "status";
	public static final String ISREAD = "isread";
	public static final String CODE = "code";

	public void save(SysTDictsort transientInstance) {
		log.debug("saving SysTDictsort instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTDictsort persistentInstance) {
		log.debug("deleting SysTDictsort instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTDictsort findById(java.lang.Long id) {
		log.debug("getting SysTDictsort instance with id: " + id);
		try {
			SysTDictsort instance = (SysTDictsort) getSession().get(
					"com.mingda.entity.SysTDictsort", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTDictsort instance) {
		log.debug("finding SysTDictsort instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTDictsort").add(
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
		log.debug("finding SysTDictsort instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTDictsort as model where model."
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

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByIsread(Object isread) {
		return findByProperty(ISREAD, isread);
	}

	public List findAll() {
		log.debug("finding all SysTDictsort instances");
		try {
			String queryString = "from SysTDictsort";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTDictsort merge(SysTDictsort detachedInstance) {
		log.debug("merging SysTDictsort instance");
		try {
			SysTDictsort result = (SysTDictsort) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTDictsort instance) {
		log.debug("attaching dirty SysTDictsort instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTDictsort instance) {
		log.debug("attaching clean SysTDictsort instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * 查询再用的字典列表
	 */
	public List findForUseful() {
		try {
			String queryString = "select ds from SysTDictsort ds where ds.status=1 order by ds.sequence";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}