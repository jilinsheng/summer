package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTUnit;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTUnit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTUnit
 * @author MyEclipse Persistence Tools
 */

public class SysTUnitDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTUnitDAO.class);
	// property constants
	public static final String UNITNAME = "unitname";
	public static final String ADDRESS = "address";
	public static final String RUNSTATE = "runstate";
	public static final String INDUSTRY = "industry";
	public static final String UNITTYPE = "unittype";
	public static final String UNITFLAG = "unitflag";
	public static final String DETAIL = "detail";

	public SysTUnit save(SysTUnit transientInstance) {
		log.debug("saving SysTUnit instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return transientInstance;
	}

	public void delete(SysTUnit persistentInstance) {
		log.debug("deleting SysTUnit instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTUnit findById(java.lang.Long id) {
		log.debug("getting SysTUnit instance with id: " + id);
		try {
			SysTUnit instance = (SysTUnit) getSession().get(
					"com.mingda.entity.SysTUnit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTUnit instance) {
		log.debug("finding SysTUnit instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTUnit").add(Example.create(instance))
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
		log.debug("finding SysTUnit instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTUnit as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUnitname(Object unitname) {
		return findByProperty(UNITNAME, unitname);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByRunstate(Object runstate) {
		return findByProperty(RUNSTATE, runstate);
	}

	public List findByIndustry(Object industry) {
		return findByProperty(INDUSTRY, industry);
	}

	public List findByUnittype(Object unittype) {
		return findByProperty(UNITTYPE, unittype);
	}

	public List findByUnitflag(Object unitflag) {
		return findByProperty(UNITFLAG, unitflag);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findAll() {
		log.debug("finding all SysTUnit instances");
		try {
			String queryString = "from SysTUnit";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTUnit merge(SysTUnit detachedInstance) {
		log.debug("merging SysTUnit instance");
		try {
			SysTUnit result = (SysTUnit) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTUnit instance) {
		log.debug("attaching dirty SysTUnit instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTUnit instance) {
		log.debug("attaching clean SysTUnit instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}