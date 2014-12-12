package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTMonth;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImplTMonth entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTMonth
 * @author MyEclipse Persistence Tools
 */

public class ImplTMonthDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ImplTMonthDAO.class);
	// property constants
	public static final String MONNAME = "monname";
	public static final String DETAIL = "detail";

	public void save(ImplTMonth transientInstance) {
		log.debug("saving ImplTMonth instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTMonth persistentInstance) {
		log.debug("deleting ImplTMonth instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImplTMonth findById(java.math.BigDecimal id) {
		log.debug("getting ImplTMonth instance with id: " + id);
		try {
			ImplTMonth instance = (ImplTMonth) getSession().get(
					"com.mingda.entity.ImplTMonth", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTMonth instance) {
		log.debug("finding ImplTMonth instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTMonth").add(
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
		log.debug("finding ImplTMonth instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ImplTMonth as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List<ImplTMonth> getMonthDoneList(String  organizationId ) {
		log.debug("finding ImplTMonth instance " +organizationId);
		try {
			String queryString = "from ImplTMonth as model where model.sysTOrganization like '"+organizationId+"%' order by model.monname desc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByMonname(Object monname) {
		return findByProperty(MONNAME, monname);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findAll() {
		log.debug("finding all ImplTMonth instances");
		try {
			String queryString = "from ImplTMonth";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTMonth merge(ImplTMonth detachedInstance) {
		log.debug("merging ImplTMonth instance");
		try {
			ImplTMonth result = (ImplTMonth) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTMonth instance) {
		log.debug("attaching dirty ImplTMonth instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTMonth instance) {
		log.debug("attaching clean ImplTMonth instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}