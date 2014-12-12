package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTBill;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ImplTBill entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTBill
 * @author MyEclipse Persistence Tools
 */

public class ImplTBillDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ImplTBillDAO.class);
	// property constants
	public static final String IMPLMONTH = "implmonth";
	public static final String MONEY = "money";
	public static final String FAMILYNO = "familyno";
	public static final String MASTERNAME = "mastername";
	public static final String PAPERID = "paperid";
	public static final String ACCOUTS = "accouts";
	public static final String BANKCODE = "bankcode";
	public static final String FAMILYADDRESS = "familyaddress";

	public void save(ImplTBill transientInstance) {
		log.debug("saving ImplTBill instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTBill persistentInstance) {
		log.debug("deleting ImplTBill instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ImplTBill findById(java.math.BigDecimal id) {
		log.debug("getting ImplTBill instance with id: " + id);
		try {
			ImplTBill instance = (ImplTBill) getSession().get(
					"com.mingda.entity.ImplTBill", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTBill instance) {
		log.debug("finding ImplTBill instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTBill")
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
		log.debug("finding ImplTBill instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ImplTBill as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByImplmonth(Object implmonth) {
		return findByProperty(IMPLMONTH, implmonth);
	}

	public List findByMoney(Object money) {
		return findByProperty(MONEY, money);
	}

	public List findByFamilyno(Object familyno) {
		return findByProperty(FAMILYNO, familyno);
	}

	public List findByMastername(Object mastername) {
		return findByProperty(MASTERNAME, mastername);
	}

	public List findByPaperid(Object paperid) {
		return findByProperty(PAPERID, paperid);
	}

	public List findByAccouts(Object accouts) {
		return findByProperty(ACCOUTS, accouts);
	}

	public List findByBankcode(Object bankcode) {
		return findByProperty(BANKCODE, bankcode);
	}

	public List findByFamilyaddress(Object familyaddress) {
		return findByProperty(FAMILYADDRESS, familyaddress);
	}

	public List findAll() {
		log.debug("finding all ImplTBill instances");
		try {
			String queryString = "from ImplTBill";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTBill merge(ImplTBill detachedInstance) {
		log.debug("merging ImplTBill instance");
		try {
			ImplTBill result = (ImplTBill) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTBill instance) {
		log.debug("attaching dirty ImplTBill instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTBill instance) {
		log.debug("attaching clean ImplTBill instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}