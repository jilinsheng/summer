package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTClasstype;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTClasstype entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTClasstype
 * @author MyEclipse Persistence Tools
 */

public class SysTClasstypeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTClasstypeDAO.class);
	// property constants
	public static final String FIELD_ID = "fieldId";
	public static final String PHYSQL = "physql";
	public static final String LOGICSQL = "logicsql";
	public static final String EXPLAINS = "explains";
	public static final String VIEWNAME = "viewname";
	public static final String ISCHECK = "ischeck";
	public static final String LEV5 = "lev5";
	public static final String LEV4 = "lev4";
	public static final String LEV3 = "lev3";
	public static final String LEV2 = "lev2";
	public static final String LEV1 = "lev1";
	public static final String STATUS = "status";

	public void save(SysTClasstype transientInstance) {
		log.debug("saving SysTClasstype instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTClasstype persistentInstance) {
		log.debug("deleting SysTClasstype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTClasstype findById(java.lang.Long id) {
		log.debug("getting SysTClasstype instance with id: " + id);
		try {
			SysTClasstype instance = (SysTClasstype) getSession().get(
					"com.mingda.entity.SysTClasstype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTClasstype instance) {
		log.debug("finding SysTClasstype instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTClasstype").add(
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
		log.debug("finding SysTClasstype instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTClasstype as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFieldId(Object fieldId) {
		return findByProperty(FIELD_ID, fieldId);
	}

	public List findByPhysql(Object physql) {
		return findByProperty(PHYSQL, physql);
	}

	public List findByLogicsql(Object logicsql) {
		return findByProperty(LOGICSQL, logicsql);
	}

	public List findByExplains(Object explains) {
		return findByProperty(EXPLAINS, explains);
	}

	public List findByViewname(Object viewname) {
		return findByProperty(VIEWNAME, viewname);
	}

	public List findByIscheck(Object ischeck) {
		return findByProperty(ISCHECK, ischeck);
	}

	public List findByLev5(Object lev5) {
		return findByProperty(LEV5, lev5);
	}

	public List findByLev4(Object lev4) {
		return findByProperty(LEV4, lev4);
	}

	public List findByLev3(Object lev3) {
		return findByProperty(LEV3, lev3);
	}

	public List findByLev2(Object lev2) {
		return findByProperty(LEV2, lev2);
	}

	public List findByLev1(Object lev1) {
		return findByProperty(LEV1, lev1);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all SysTClasstype instances");
		try {
			String queryString = "from SysTClasstype";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTClasstype merge(SysTClasstype detachedInstance) {
		log.debug("merging SysTClasstype instance");
		try {
			SysTClasstype result = (SysTClasstype) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTClasstype instance) {
		log.debug("attaching dirty SysTClasstype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTClasstype instance) {
		log.debug("attaching clean SysTClasstype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}