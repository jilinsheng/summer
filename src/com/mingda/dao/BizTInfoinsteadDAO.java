package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTInfoinstead;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTInfoinstead entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTInfoinstead
 * @author MyEclipse Persistence Tools
 */

public class BizTInfoinsteadDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTInfoinsteadDAO.class);
	// property constants
	public static final String OPTPERSON = "optperson";
	public static final String VALTYPE = "valtype";
	public static final String PKFIELD = "pkfield";
	public static final String PDVALUE = "pdvalue";
	public static final String OLDVALENUMERATE = "oldvalenumerate";
	public static final String OLDVALSTRING = "oldvalstring";
	public static final String OLDVALFLOAT = "oldvalfloat";
	public static final String NEWVALENUMERATE = "newvalenumerate";
	public static final String NEWVALSTRING = "newvalstring";
	public static final String NEWVALFLOAT = "newvalfloat";

	public void save(BizTInfoinstead transientInstance) {
		log.debug("saving BizTInfoinstead instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTInfoinstead persistentInstance) {
		log.debug("deleting BizTInfoinstead instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTInfoinstead findById(java.lang.Long id) {
		log.debug("getting BizTInfoinstead instance with id: " + id);
		try {
			BizTInfoinstead instance = (BizTInfoinstead) getSession().get(
					"com.mingda.entity.BizTInfoinstead", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTInfoinstead instance) {
		log.debug("finding BizTInfoinstead instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTInfoinstead").add(
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
		log.debug("finding BizTInfoinstead instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BizTInfoinstead as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOptperson(Object optperson) {
		return findByProperty(OPTPERSON, optperson);
	}

	public List findByValtype(Object valtype) {
		return findByProperty(VALTYPE, valtype);
	}

	public List findByPkfield(Object pkfield) {
		return findByProperty(PKFIELD, pkfield);
	}

	public List findByPdvalue(Object pdvalue) {
		return findByProperty(PDVALUE, pdvalue);
	}

	public List findByOldvalenumerate(Object oldvalenumerate) {
		return findByProperty(OLDVALENUMERATE, oldvalenumerate);
	}

	public List findByOldvalstring(Object oldvalstring) {
		return findByProperty(OLDVALSTRING, oldvalstring);
	}

	public List findByOldvalfloat(Object oldvalfloat) {
		return findByProperty(OLDVALFLOAT, oldvalfloat);
	}

	public List findByNewvalenumerate(Object newvalenumerate) {
		return findByProperty(NEWVALENUMERATE, newvalenumerate);
	}

	public List findByNewvalstring(Object newvalstring) {
		return findByProperty(NEWVALSTRING, newvalstring);
	}

	public List findByNewvalfloat(Object newvalfloat) {
		return findByProperty(NEWVALFLOAT, newvalfloat);
	}

	public List findAll() {
		log.debug("finding all BizTInfoinstead instances");
		try {
			String queryString = "from BizTInfoinstead";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTInfoinstead merge(BizTInfoinstead detachedInstance) {
		log.debug("merging BizTInfoinstead instance");
		try {
			BizTInfoinstead result = (BizTInfoinstead) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTInfoinstead instance) {
		log.debug("attaching dirty BizTInfoinstead instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTInfoinstead instance) {
		log.debug("attaching clean BizTInfoinstead instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}