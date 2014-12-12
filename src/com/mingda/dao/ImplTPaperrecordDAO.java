package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTPaperrecord;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImplTPaperrecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTPaperrecord
 * @author MyEclipse Persistence Tools
 */

public class ImplTPaperrecordDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ImplTPaperrecordDAO.class);
	// property constants
	public static final String REASON = "reason";
	public static final String FLAG = "flag";

	public ImplTPaperrecord save(ImplTPaperrecord transientInstance) {
		log.debug("saving ImplTPaperrecord instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
			return transientInstance;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTPaperrecord persistentInstance) {
		log.debug("deleting ImplTPaperrecord instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImplTPaperrecord findById(java.lang.Long id) {
		log.debug("getting ImplTPaperrecord instance with id: " + id);
		try {
			ImplTPaperrecord instance = (ImplTPaperrecord) getSession().get(
					"com.mingda.entity.ImplTPaperrecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTPaperrecord instance) {
		log.debug("finding ImplTPaperrecord instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTPaperrecord").addOrder(
					Order.asc("opttime")).add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ImplTPaperrecord instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ImplTPaperrecord as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ImplTPaperrecord> findPaperUseful(String fid, String flag) {
		log
				.debug("finding ImplTPaperrecord instance with property: familyid , value: "
						+ fid);
		try {
			String queryString = "from ImplTPaperrecord as model left  join fetch model.implTPapertype where model.familyId='"
					+ fid + "' and  model.flag='" + flag + "'";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public ImplTPaperrecord findPaperByCode(String papertype, String code) {
		try {
			String tablename = "";
			if ("1".equals(papertype)) {
				tablename = "implTPaperinfo1s";
			}
			if ("2".equals(papertype)) {
				tablename = "implTPaperinfo2s";
			}
			String queryString = "from ImplTPaperrecord as model left  join fetch model."
					+ tablename + " papers where papers.serial ='" + code + "'";
			Query queryObject = getSession().createQuery(queryString);
			return (ImplTPaperrecord) queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findAll() {
		log.debug("finding all ImplTPaperrecord instances");
		try {
			String queryString = "from ImplTPaperrecord";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTPaperrecord merge(ImplTPaperrecord detachedInstance) {
		log.debug("merging ImplTPaperrecord instance");
		try {
			ImplTPaperrecord result = (ImplTPaperrecord) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTPaperrecord instance) {
		log.debug("attaching dirty ImplTPaperrecord instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTPaperrecord instance) {
		log.debug("attaching clean ImplTPaperrecord instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}