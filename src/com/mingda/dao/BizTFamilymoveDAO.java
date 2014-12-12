package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTFamilymove;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTFamilymove entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTFamilymove
 * @author MyEclipse Persistence Tools
 */

public class BizTFamilymoveDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTFamilymoveDAO.class);
	// property constants
	public static final String FAMILY_ID = "familyId";
	public static final String OFAMILYNO = "ofamilyno";
	public static final String OORGID = "oorgid";
	public static final String OOPTPERSON = "ooptperson";
	public static final String NFAMILYNO = "nfamilyno";
	public static final String NORGID = "norgid";
	public static final String NOPTPERSON = "noptperson";
	public static final String ISOVER = "isover";

	public void save(BizTFamilymove transientInstance) {
		log.debug("saving BizTFamilymove instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTFamilymove persistentInstance) {
		log.debug("deleting BizTFamilymove instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTFamilymove findById(java.lang.Long id) {
		log.debug("getting BizTFamilymove instance with id: " + id);
		try {
			BizTFamilymove instance = (BizTFamilymove) getSession().get(
					"com.mingda.entity.BizTFamilymove", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTFamilymove instance) {
		log.debug("finding BizTFamilymove instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTFamilymove").add(
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
		log.debug("finding BizTFamilymove instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTFamilymove as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFamilyId(Object familyId) {
		return findByProperty(FAMILY_ID, familyId);
	}

	public List findByOfamilyno(Object ofamilyno) {
		return findByProperty(OFAMILYNO, ofamilyno);
	}

	public List findByOorgid(Object oorgid) {
		return findByProperty(OORGID, oorgid);
	}

	public List findByOoptperson(Object ooptperson) {
		return findByProperty(OOPTPERSON, ooptperson);
	}

	public List findByNfamilyno(Object nfamilyno) {
		return findByProperty(NFAMILYNO, nfamilyno);
	}

	public List findByNorgid(Object norgid) {
		return findByProperty(NORGID, norgid);
	}

	public List findByNoptperson(Object noptperson) {
		return findByProperty(NOPTPERSON, noptperson);
	}

	public List findByIsover(Object isover) {
		return findByProperty(ISOVER, isover);
	}

	public List findAll() {
		log.debug("finding all BizTFamilymove instances");
		try {
			String queryString = "from BizTFamilymove";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTFamilymove merge(BizTFamilymove detachedInstance) {
		log.debug("merging BizTFamilymove instance");
		try {
			BizTFamilymove result = (BizTFamilymove) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTFamilymove instance) {
		log.debug("attaching dirty BizTFamilymove instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTFamilymove instance) {
		log.debug("attaching clean BizTFamilymove instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}