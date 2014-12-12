package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.InfoTFamily;

/**
 * A data access object (DAO) providing persistence and search support for
 * InfoTFamily entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.InfoTFamily
 * @author MyEclipse Persistence Tools
 */

public class InfoTFamilyDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(InfoTFamilyDAO.class);
	// property constants
	public static final String FAMILYNO = "familyno";
	public static final String POPULATION = "population";
	public static final String ENSURECOUNT = "ensurecount";
	public static final String ASSETWORTH = "assetworth";
	public static final String ALLINCOME = "allincome";
	public static final String CONSULTINCOME = "consultincome";
	public static final String PAYOUT = "payout";
	public static final String STATUS = "status";
	public static final String MASTERNAME = "mastername";
	public static final String MASTERPAPERID = "masterpaperid";
	public static final String REGISTEREDADDRESS = "registeredaddress";
	public static final String FAMILYADDRESS = "familyaddress";
	public static final String PHONENUMBER = "phonenumber";
	public static final String OTHERINFO = "otherinfo";
	public static final String CONSULTINCOME2 = "consultincome2";
	public static final String INCOMEBASE = "incomebase";

	public void save(InfoTFamily transientInstance) {
		log.debug("saving InfoTFamily instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InfoTFamily persistentInstance) {
		log.debug("deleting InfoTFamily instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InfoTFamily findById(java.math.BigDecimal id) {
		log.debug("getting InfoTFamily instance with id: " + id);
		try {
			InfoTFamily instance = (InfoTFamily) getSession().get(
					"com.mingda.entity.InfoTFamily", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InfoTFamily instance) {
		log.debug("finding InfoTFamily instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.InfoTFamily").add(
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
		log.debug("finding InfoTFamily instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from InfoTFamily as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFamilyno(Object familyno) {
		return findByProperty(FAMILYNO, familyno);
	}

	public List findByPopulation(Object population) {
		return findByProperty(POPULATION, population);
	}

	public List findByEnsurecount(Object ensurecount) {
		return findByProperty(ENSURECOUNT, ensurecount);
	}

	public List findByAssetworth(Object assetworth) {
		return findByProperty(ASSETWORTH, assetworth);
	}

	public List findByAllincome(Object allincome) {
		return findByProperty(ALLINCOME, allincome);
	}

	public List findByConsultincome(Object consultincome) {
		return findByProperty(CONSULTINCOME, consultincome);
	}

	public List findByPayout(Object payout) {
		return findByProperty(PAYOUT, payout);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByMastername(Object mastername) {
		return findByProperty(MASTERNAME, mastername);
	}

	public List findByMasterpaperid(Object masterpaperid) {
		return findByProperty(MASTERPAPERID, masterpaperid);
	}

	public List findByRegisteredaddress(Object registeredaddress) {
		return findByProperty(REGISTEREDADDRESS, registeredaddress);
	}

	public List findByFamilyaddress(Object familyaddress) {
		return findByProperty(FAMILYADDRESS, familyaddress);
	}

	public List findByPhonenumber(Object phonenumber) {
		return findByProperty(PHONENUMBER, phonenumber);
	}

	public List findByOtherinfo(Object otherinfo) {
		return findByProperty(OTHERINFO, otherinfo);
	}

	public List findByConsultincome2(Object consultincome2) {
		return findByProperty(CONSULTINCOME2, consultincome2);
	}

	public List findByIncomebase(Object incomebase) {
		return findByProperty(INCOMEBASE, incomebase);
	}

	public List findAll() {
		log.debug("finding all InfoTFamily instances");
		try {
			String queryString = "from InfoTFamily";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InfoTFamily merge(InfoTFamily detachedInstance) {
		log.debug("merging InfoTFamily instance");
		try {
			InfoTFamily result = (InfoTFamily) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InfoTFamily instance) {
		log.debug("attaching dirty InfoTFamily instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InfoTFamily instance) {
		log.debug("attaching clean InfoTFamily instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}