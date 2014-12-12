package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTIniquery;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTIniquery entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTIniquery
 * @author MyEclipse Persistence Tools
 */

public class SysTIniqueryDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTIniqueryDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String QXML = "qxml";

	public void save(SysTIniquery transientInstance) {
		log.debug("saving SysTIniquery instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			throw re;
		}
	}

	public void delete(SysTIniquery persistentInstance) {
		log.debug("deleting SysTIniquery instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTIniquery findById(java.math.BigDecimal id) {
		log.debug("getting SysTIniquery instance with id: " + id);
		try {
			SysTIniquery instance = (SysTIniquery) getSession().get(
					"com.mingda.entity.SysTIniquery", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTIniquery instance) {
		log.debug("finding SysTIniquery instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTIniquery").add(
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
		log.debug("finding SysTIniquery instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTIniquery as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findByEmpId(Object value) {
		try {
			String queryString = "from SysTIniquery as model where model.employeeId= ?";
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

	public List findByQxml(Object qxml) {
		return findByProperty(QXML, qxml);
	}

	public List findAll() {
		log.debug("finding all SysTIniquery instances");
		try {
			String queryString = "from SysTIniquery";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTIniquery merge(SysTIniquery detachedInstance) {
		log.debug("merging SysTIniquery instance");
		try {
			SysTIniquery result = (SysTIniquery) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTIniquery instance) {
		log.debug("attaching dirty SysTIniquery instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTIniquery instance) {
		log.debug("attaching clean SysTIniquery instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}