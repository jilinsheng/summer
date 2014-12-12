package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.ImplTInterfacedefine;

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
 * ImplTInterfacedefine entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.ImplTInterfacedefine
 * @author MyEclipse Persistence Tools
 */

public class ImplTInterfacedefineDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory
			.getLog(ImplTInterfacedefineDAO.class);
	// property constants
	public static final String DEFNAME = "defname";
	public static final String TEMPLATE = "template";

	public void save(ImplTInterfacedefine transientInstance) {
		log.debug("saving ImplTInterfacedefine instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ImplTInterfacedefine persistentInstance) {
		log.debug("deleting ImplTInterfacedefine instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Long getPk() {

		try {
			String queryString = "select hibernate_sequence.nextval from dual";
			Query queryObject = getSession().createSQLQuery(queryString);
			BigDecimal pk =(BigDecimal) queryObject.uniqueResult();
			return new Long(pk.longValue());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}

	}

	public ImplTInterfacedefine findById(Long id) {
		log.debug("getting ImplTInterfacedefine instance with id: " + id);
		try {
			ImplTInterfacedefine instance = (ImplTInterfacedefine) getSession()
					.get("com.mingda.entity.ImplTInterfacedefine", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ImplTInterfacedefine instance) {
		log.debug("finding ImplTInterfacedefine instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.ImplTInterfacedefine").add(
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
		log.debug("finding ImplTInterfacedefine instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ImplTInterfacedefine as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Object[]> findByOrganizationId(Object value) {
		try {
			String queryString = "select inter.interfacedefine_id,"
					+ " inter.defname, " + "inter.defdate," + "inter.filetype,"
					+ " organ.fullname," + "empext.name ,   emp.employee_id,"
					+ " organ.organization_id"
					+ " from impl_t_interfacedefine inter,"
					+ " sys_t_organization     organ,"
					+ " sys_t_employee         emp,"
					+ " sys_t_empext           empext"
					+ " where inter.organization_id = organ.organization_id"
					+ " and emp.employee_id = inter.employee_id"
					+ " and inter.organization_id like '" + value + "%'"
					+ " and empext.empext_id = emp.employee_id";
			Query queryObject = getSession().createSQLQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDefname(Object defname) {
		return findByProperty(DEFNAME, defname);
	}

	public List findByTemplate(Object template) {
		return findByProperty(TEMPLATE, template);
	}

	public List findAll() {
		log.debug("finding all ImplTInterfacedefine instances");
		try {
			String queryString = "from ImplTInterfacedefine";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ImplTInterfacedefine merge(ImplTInterfacedefine detachedInstance) {
		log.debug("merging ImplTInterfacedefine instance");
		try {
			ImplTInterfacedefine result = (ImplTInterfacedefine) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ImplTInterfacedefine instance) {
		log.debug("attaching dirty ImplTInterfacedefine instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ImplTInterfacedefine instance) {
		log.debug("attaching clean ImplTInterfacedefine instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}