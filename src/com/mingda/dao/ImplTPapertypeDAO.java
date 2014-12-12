package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTPapertype;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImplTPapertype entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTPapertype
 * @author MyEclipse Persistence Tools
 */

public class ImplTPapertypeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ImplTPapertypeDAO.class);
	// property constants
	public static final String PAPERNAME = "papername";
	public static final String PAPERXML = "paperxml";
	public static final String FLAG = "flag";
	public static final String POLICY_ID = "policyId";

	public void save(ImplTPapertype transientInstance) {
		log.debug("saving ImplTPapertype instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTPapertype persistentInstance) {
		log.debug("deleting ImplTPapertype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImplTPapertype findById(Long id) {
		log.debug("getting ImplTPapertype instance with id: " + id);
		try {
			ImplTPapertype instance = (ImplTPapertype) getSession().get(
					"com.mingda.entity.ImplTPapertype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTPapertype instance) {
		log.debug("finding ImplTPapertype instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTPapertype").add(
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
		log.debug("finding ImplTPapertype instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ImplTPapertype as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPapername(Object papername) {
		return findByProperty(PAPERNAME, papername);
	}

	public List findByPaperxml(Object paperxml) {
		return findByProperty(PAPERXML, paperxml);
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findByPolicyId(Object policyId) {
		return findByProperty(POLICY_ID, policyId);
	}

	public List findAll() {
		log.debug("finding all ImplTPapertype instances");
		try {
			String queryString = "from ImplTPapertype";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTPapertype merge(ImplTPapertype detachedInstance) {
		log.debug("merging ImplTPapertype instance");
		try {
			ImplTPapertype result = (ImplTPapertype) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTPapertype instance) {
		log.debug("attaching dirty ImplTPapertype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTPapertype instance) {
		log.debug("attaching clean ImplTPapertype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}