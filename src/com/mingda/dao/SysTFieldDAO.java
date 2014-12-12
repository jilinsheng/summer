package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTField;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTField entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTField
 * @author MyEclipse Persistence Tools
 */

public class SysTFieldDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTFieldDAO.class);
	// property constants
	public static final String LOGICNAME = "logicname";
	public static final String PHYSICALNAME = "physicalname";
	public static final String FIELDTYPE = "fieldtype";
	public static final String LENGTH = "length";
	public static final String DIGIT = "digit";
	public static final String ISPRIMARY = "isprimary";
	public static final String ISFOREIGN = "isforeign";
	public static final String DEFULTS = "defults";
	public static final String DICSORT = "dicsort";
	public static final String ISEXPAND = "isexpand";
	public static final String READONLY = "readonly";
	public static final String CONTROL = "control";
	public static final String PROOFRULE = "proofrule";
	public static final String SEQUENCE = "sequence";
	public static final String EXPLAINS = "explains";
	public static final String STATUS = "status";
	public static final String ISEMPTY = "isempty";
	public static final String VISIBLE = "visible";
	public static final String COMPUTEFLAG = "computeflag";
	public static final String COMPUTESQL = "computesql";
	public static final String COMPUTEFIELDS = "computefields";
	public static final String ISCAPTION = "iscaption";
	public static final String ISLIST = "islist";

	public void save(SysTField transientInstance) {
		log.debug("saving SysTField instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTField persistentInstance) {
		log.debug("deleting SysTField instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTField findById(java.lang.Long id) {
		log.debug("getting SysTField instance with id: " + id);
		try {
			SysTField instance = (SysTField) getSession().get(
					"com.mingda.entity.SysTField", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTField instance) {
		log.debug("finding SysTField instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTField")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SysTField instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTField as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLogicname(Object logicname) {
		return findByProperty(LOGICNAME, logicname);
	}

	public List findByPhysicalname(Object physicalname) {
		return findByProperty(PHYSICALNAME, physicalname);
	}

	public List findByFieldtype(Object fieldtype) {
		return findByProperty(FIELDTYPE, fieldtype);
	}

	public List findByLength(Object length) {
		return findByProperty(LENGTH, length);
	}

	public List findByDigit(Object digit) {
		return findByProperty(DIGIT, digit);
	}

	public List findByIsprimary(Object isprimary) {
		return findByProperty(ISPRIMARY, isprimary);
	}

	public List findByIsforeign(Object isforeign) {
		return findByProperty(ISFOREIGN, isforeign);
	}

	public List findByDefults(Object defults) {
		return findByProperty(DEFULTS, defults);
	}

	public List findByDicsort(Object dicsort) {
		return findByProperty(DICSORT, dicsort);
	}

	public List findByIsexpand(Object isexpand) {
		return findByProperty(ISEXPAND, isexpand);
	}

	public List findByReadonly(Object readonly) {
		return findByProperty(READONLY, readonly);
	}

	public List findByControl(Object control) {
		return findByProperty(CONTROL, control);
	}

	public List findByProofrule(Object proofrule) {
		return findByProperty(PROOFRULE, proofrule);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByExplains(Object explains) {
		return findByProperty(EXPLAINS, explains);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByIsempty(Object isempty) {
		return findByProperty(ISEMPTY, isempty);
	}

	public List findByVisible(Object visible) {
		return findByProperty(VISIBLE, visible);
	}

	public List findByComputeflag(Object computeflag) {
		return findByProperty(COMPUTEFLAG, computeflag);
	}

	public List findByComputesql(Object computesql) {
		return findByProperty(COMPUTESQL, computesql);
	}

	public List findByComputefields(Object computefields) {
		return findByProperty(COMPUTEFIELDS, computefields);
	}

	public List findByIscaption(Object iscaption) {
		return findByProperty(ISCAPTION, iscaption);
	}

	public List findByIslist(Object islist) {
		return findByProperty(ISLIST, islist);
	}

	public List findAll() {
		log.debug("finding all SysTField instances");
		try {
			String queryString = "from SysTField";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTField merge(SysTField detachedInstance) {
		log.debug("merging SysTField instance");
		try {
			SysTField result = (SysTField) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTField instance) {
		log.debug("attaching dirty SysTField instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTField instance) {
		log.debug("attaching clean SysTField instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}