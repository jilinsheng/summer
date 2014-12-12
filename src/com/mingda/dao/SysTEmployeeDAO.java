package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTEmployee;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTEmployee entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTEmployee
 * @author MyEclipse Persistence Tools
 */

public class SysTEmployeeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTEmployeeDAO.class);
	// property constants
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String ISADMIN = "isadmin";

	public void save(SysTEmployee transientInstance) {
		log.debug("saving SysTEmployee instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTEmployee persistentInstance) {
		log.debug("deleting SysTEmployee instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTEmployee findById(java.lang.Long id) {
		log.debug("getting SysTEmployee instance with id: " + id);
		try {
			SysTEmployee instance = (SysTEmployee) getSession().get(
					"com.mingda.entity.SysTEmployee", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTEmployee instance) {
		log.debug("finding SysTEmployee instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTEmployee").add(
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
		log.debug("finding SysTEmployee instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTEmployee as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccount(Object account) {
		return findByProperty(ACCOUNT, account);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByIsadmin(Object isadmin) {
		return findByProperty(ISADMIN, isadmin);
	}

	public List findAll() {
		log.debug("finding all SysTEmployee instances");
		try {
			String queryString = "from SysTEmployee";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTEmployee merge(SysTEmployee detachedInstance) {
		log.debug("merging SysTEmployee instance");
		try {
			SysTEmployee result = (SysTEmployee) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTEmployee instance) {
		log.debug("attaching dirty SysTEmployee instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTEmployee instance) {
		log.debug("attaching clean SysTEmployee instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	// 更新此用户是否在线
	public void updateLine(String line, String empid) {
		log.debug("attaching clean SysTEmployee instance");
		try {
			getSession()
					.createSQLQuery(
							"update sys_t_employee emp  set emp.line ='"
									+ line
									+ "'where emp.employee_id ='"
									+ empid + "' ").executeUpdate();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	//取得当前在操作员人数
	public List<SysTEmployee> getEmpOnline(){
		try {
			String queryString = "from SysTEmployee as model where model.line ='1'";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

}