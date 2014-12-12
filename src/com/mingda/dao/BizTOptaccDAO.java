package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptacc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BizTOptacc entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.BizTOptacc
 * @author MyEclipse Persistence Tools
 */

public class BizTOptaccDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(BizTOptaccDAO.class);
	// property constants
	public static final String ACCYEAR = "accyear";
	public static final String ACCMONTH = "accmonth";
	public static final String ACCDESC = "accdesc";
	public static final String ACCFLAG = "accflag";
	public static final String ACCMONEYFLAG = "accmoneyflag";
	public static final String ACCMONEY = "accmoney";

	public void save(BizTOptacc transientInstance) {
		log.debug("saving BizTOptacc instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BizTOptacc persistentInstance) {
		log.debug("deleting BizTOptacc instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BizTOptacc findById(java.math.BigDecimal id) {
		log.debug("getting BizTOptacc instance with id: " + id);
		try {
			BizTOptacc instance = (BizTOptacc) getSession().get(
					"com.mingda.entity.BizTOptacc", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BizTOptacc instance) {
		log.debug("finding BizTOptacc instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.BizTOptacc").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	public List<BizTOptacc> getOverBatch(String organizationId) throws RuntimeException {
		try {
			String queryString = "from BizTOptacc model inner join fetch model.doperTPolicy left join fetch model.implTMonths as months where months is null and model.sysTOrganization like '"
					+ organizationId + "%' and model.accflag='2'  order by model.accyear , model.accmonth";
			List<BizTOptacc> results = getSession().createQuery(queryString).list();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BizTOptacc instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BizTOptacc as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccyear(Object accyear) {
		return findByProperty(ACCYEAR, accyear);
	}

	public List findByAccmonth(Object accmonth) {
		return findByProperty(ACCMONTH, accmonth);
	}

	public List findByAccdesc(Object accdesc) {
		return findByProperty(ACCDESC, accdesc);
	}

	public List findByAccflag(Object accflag) {
		return findByProperty(ACCFLAG, accflag);
	}

	public List findByAccmoneyflag(Object accmoneyflag) {
		return findByProperty(ACCMONEYFLAG, accmoneyflag);
	}

	public List findByAccmoney(Object accmoney) {
		return findByProperty(ACCMONEY, accmoney);
	}

	public List findAll() {
		log.debug("finding all BizTOptacc instances");
		try {
			String queryString = "from BizTOptacc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BizTOptacc merge(BizTOptacc detachedInstance) {
		log.debug("merging BizTOptacc instance");
		try {
			BizTOptacc result = (BizTOptacc) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BizTOptacc instance) {
		log.debug("attaching dirty BizTOptacc instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BizTOptacc instance) {
		log.debug("attaching clean BizTOptacc instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}