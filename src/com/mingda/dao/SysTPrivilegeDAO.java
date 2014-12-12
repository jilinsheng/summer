package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTEmployee;
import com.mingda.entity.SysTPrivilege;
import com.mingda.entity.SysTRole;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTPrivilege entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTPrivilege
 * @author MyEclipse Persistence Tools
 */

public class SysTPrivilegeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTPrivilegeDAO.class);
	// property constants
	public static final String PARENTPRIVILEGEID = "parentprivilegeid";
	public static final String CODE = "code";
	public static final String DISPLAYNAME = "displayname";
	public static final String DETAIL = "detail";
	public static final String URL = "url";
	public static final String SEQUENCE = "sequence";
	public static final String DEPTH = "depth";
	public static final String STATUS = "status";
	public static final String TARGET = "target";
	public static final String NAV = "nav";
	public static final String ISLEAF = "isleaf";

	public SysTPrivilege save(SysTPrivilege transientInstance) {
		log.debug("saving SysTPrivilege instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return transientInstance;
	}

	public void delete(SysTPrivilege persistentInstance) {
		log.debug("deleting SysTPrivilege instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTPrivilege findById(java.lang.Long id) {
		log.debug("getting SysTPrivilege instance with id: " + id);
		try {
			SysTPrivilege instance = (SysTPrivilege) getSession().get(
					"com.mingda.entity.SysTPrivilege", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTPrivilege instance) {
		log.debug("finding SysTPrivilege instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTPrivilege").add(
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
		log.debug("finding SysTPrivilege instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SysTPrivilege as model where model."
					+ propertyName + "= ? order by model.sequence";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByParentprivilegeid(Object parentprivilegeid) {
		return findByProperty(PARENTPRIVILEGEID, parentprivilegeid);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByDisplayname(Object displayname) {
		return findByProperty(DISPLAYNAME, displayname);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByDepth(Object depth) {
		return findByProperty(DEPTH, depth);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByTarget(Object target) {
		return findByProperty(TARGET, target);
	}

	public List findByNav(Object nav) {
		return findByProperty(NAV, nav);
	}

	public List findByIsleaf(Object isleaf) {
		return findByProperty(ISLEAF, isleaf);
	}

	public List findAll() {
		log.debug("finding all SysTPrivilege instances");
		try {
			String queryString = "from SysTPrivilege order by " + SEQUENCE;
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTPrivilege merge(SysTPrivilege detachedInstance) {
		log.debug("merging SysTPrivilege instance");
		try {
			SysTPrivilege result = (SysTPrivilege) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTPrivilege instance) {
		log.debug("attaching dirty SysTPrivilege instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTPrivilege instance) {
		log.debug("attaching clean SysTPrivilege instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 通过一个登录的工作人员实体查询出<br>
	 * 此工作人员可以操作的菜单列表
	 * 
	 * @param sysTEmployee
	 * @return
	 */
	public List findBySysTEmployee(SysTEmployee sysTEmployee) {
		try {
			String queryString = "from SysTPrivilege as privilege "
					+ "where privilege.status=1 "
					+ "and exists(from privilege.sysTRoles as role inner join role.sysTEmployees employee where employee.employeeId=?) "
					+ " order by privilege.sequence ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, sysTEmployee.getEmployeeId());
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 通过一个登录的角色实体查询出<br>
	 * 此工作人员可以操作的菜单列表
	 * 
	 * @param role
	 * @return
	 */
	public List findByRole(SysTRole role) {
		try {
			/*
			 * String queryString = "from SysTPrivilege as privilege left outer
			 * join " + "privilege.sysTRoles role" + " order by
			 * privilege.sequence "; Query queryObject =
			 * getSession().createQuery(queryString);
			 */
			Query queryObject = getSession()
					.createSQLQuery(
							"select * from sys_t_privilege a left join "
									+ "sys_t_power b on b.privilege_id = a.privilege_id"
									+ " and b.role_id = ? order by a.sequence")
					.addEntity(SysTPrivilege.class);
			queryObject.setParameter(0, role.getRoleId());
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public boolean isPrivilegeByEmpid(String empid) {
		boolean flag = false;
		try {
			Query queryObject = getSession()
					.createSQLQuery(
							"select sys_t_emprole.employee_id,  sys_t_emprole.role_id,  sys_t_power.privilege_id"
									+ "  from sys_t_power, sys_t_emprole"
									+ "  where sys_t_power.role_id = sys_t_emprole.role_id"
									+ "  and sys_t_power.privilege_id = '102'"
									+ "  and sys_t_emprole.employee_id ='"
									+ empid + "'");
			List list = queryObject.list();
			if (null == list || list.size() <= 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return flag;

	}
}