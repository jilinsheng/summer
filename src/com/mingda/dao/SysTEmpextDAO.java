package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTEmpext;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTEmpext entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTEmpext
 * @author MyEclipse Persistence Tools
 */

public class SysTEmpextDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTEmpextDAO.class);
	// property constants
	public static final String WORKNO = "workno";
	public static final String NAME = "name";
	public static final String PAPERID = "paperid";
	public static final String SEX = "sex";
	public static final String NATION = "nation";
	public static final String ADDRESS = "address";
	public static final String OFFICEPHONE1 = "officephone1";
	public static final String OFFICEPHONE2 = "officephone2";
	public static final String HOMEPHONE = "homephone";
	public static final String MOBILEPHONE = "mobilephone";
	public static final String EMAIL = "email";
	public static final String STATUS = "status";

	public void save(SysTEmpext transientInstance) {
		log.debug("saving SysTEmpext instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTEmpext persistentInstance) {
		log.debug("deleting SysTEmpext instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTEmpext findById(java.lang.Long id) {
		log.debug("getting SysTEmpext instance with id: " + id);
		try {
			SysTEmpext instance = (SysTEmpext) getSession().get(
					"com.mingda.entity.SysTEmpext", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTEmpext instance) {
		log.debug("finding SysTEmpext instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTEmpext").add(
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
		log.debug("finding SysTEmpext instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTEmpext as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByWorkno(Object workno) {
		return findByProperty(WORKNO, workno);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByPaperid(Object paperid) {
		return findByProperty(PAPERID, paperid);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findByNation(Object nation) {
		return findByProperty(NATION, nation);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByOfficephone1(Object officephone1) {
		return findByProperty(OFFICEPHONE1, officephone1);
	}

	public List findByOfficephone2(Object officephone2) {
		return findByProperty(OFFICEPHONE2, officephone2);
	}

	public List findByHomephone(Object homephone) {
		return findByProperty(HOMEPHONE, homephone);
	}

	public List findByMobilephone(Object mobilephone) {
		return findByProperty(MOBILEPHONE, mobilephone);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all SysTEmpext instances");
		try {
			String queryString = "from SysTEmpext";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTEmpext merge(SysTEmpext detachedInstance) {
		log.debug("merging SysTEmpext instance");
		try {
			SysTEmpext result = (SysTEmpext) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTEmpext instance) {
		log.debug("attaching dirty SysTEmpext instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTEmpext instance) {
		log.debug("attaching clean SysTEmpext instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}