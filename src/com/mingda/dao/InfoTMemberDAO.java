package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.InfoTMember;

/**
 * A data access object (DAO) providing persistence and search support for
 * InfoTMember entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.InfoTMember
 * @author MyEclipse Persistence Tools
 */

public class InfoTMemberDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(InfoTMemberDAO.class);
	// property constants
	public static final String PERSONINCOME = "personincome";
	public static final String MEMBERNAME = "membername";
	public static final String PAPERID = "paperid";
	public static final String INCHURL = "inchurl";
	public static final String LABOR = "labor";
	public static final String AGE = "age";
	public static final String REMARKS = "remarks";

	public void save(InfoTMember transientInstance) {
		log.debug("saving InfoTMember instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InfoTMember persistentInstance) {
		log.debug("deleting InfoTMember instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InfoTMember findById(java.lang.Integer id) {
		log.debug("getting InfoTMember instance with id: " + id);
		try {
			InfoTMember instance = (InfoTMember) getSession().get(
					"com.mingda.entity.InfoTMember", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InfoTMember instance) {
		log.debug("finding InfoTMember instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.InfoTMember").add(
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
		log.debug("finding InfoTMember instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from InfoTMember as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPersonincome(Object personincome) {
		return findByProperty(PERSONINCOME, personincome);
	}

	public List findByMembername(Object membername) {
		return findByProperty(MEMBERNAME, membername);
	}

	public List findByPaperid(Object paperid) {
		return findByProperty(PAPERID, paperid);
	}

	public List findByInchurl(Object inchurl) {
		return findByProperty(INCHURL, inchurl);
	}

	public List findByLabor(Object labor) {
		return findByProperty(LABOR, labor);
	}

	public List findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List findByRemarks(Object remarks) {
		return findByProperty(REMARKS, remarks);
	}

	public List findAll() {
		log.debug("finding all InfoTMember instances");
		try {
			String queryString = "from InfoTMember";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InfoTMember merge(InfoTMember detachedInstance) {
		log.debug("merging InfoTMember instance");
		try {
			InfoTMember result = (InfoTMember) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InfoTMember instance) {
		log.debug("attaching dirty InfoTMember instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InfoTMember instance) {
		log.debug("attaching clean InfoTMember instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}