package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.SysTTable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysTTable entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.SysTTable
 * @author MyEclipse Persistence Tools
 */

public class SysTTableDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SysTTableDAO.class);
	// property constants
	public static final String SUPER_TABLE_ID = "superTableId";
	public static final String LOGICNAME = "logicname";
	public static final String PHYSICALNAME = "physicalname";
	public static final String EXPLAINS = "explains";
	public static final String CODE = "code";
	public static final String DEPTH = "depth";
	public static final String SEQUENCE = "sequence";
	public static final String ISEXPAND = "isexpand";
	public static final String STATUS = "status";
	public static final String PATH = "path";

	public void save(SysTTable transientInstance) {
		log.debug("saving SysTTable instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysTTable persistentInstance) {
		log.debug("deleting SysTTable instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysTTable findById(java.lang.Long id) {
		log.debug("getting SysTTable instance with id: " + id);
		try {
			SysTTable instance = (SysTTable) getSession().get(
					"com.mingda.entity.SysTTable", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysTTable instance) {
		log.debug("finding SysTTable instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.SysTTable")
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
		log.debug("finding SysTTable instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysTTable as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySuperTableId(Object superTableId) {
		return findByProperty(SUPER_TABLE_ID, superTableId);
	}

	public List findByLogicname(Object logicname) {
		return findByProperty(LOGICNAME, logicname);
	}

	public List findByPhysicalname(Object physicalname) {
		return findByProperty(PHYSICALNAME, physicalname);
	}

	public List findByExplains(Object explains) {
		return findByProperty(EXPLAINS, explains);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByDepth(Object depth) {
		return findByProperty(DEPTH, depth);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByIsexpand(Object isexpand) {
		return findByProperty(ISEXPAND, isexpand);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByPath(Object path) {
		return findByProperty(PATH, path);
	}

	public List findAll() {
		log.debug("finding all SysTTable instances");
		try {
			String queryString = "from SysTTable as model order by model.depth,model.sequence";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findLikePath(Object path) {
		log.debug("finding all SysTTable instances");
		try {
			String queryString = "from SysTTable as model where model.path ||'/'|| model.code like '"
					+ path.toString().trim()
					+ "%' order by model.depth,model.sequence";
			log.debug(queryString);
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Long getPrimary() {
		String sql = "select INFOMATION_SEQUENCE.NEXTVAL from dual";
		Long pknumber=new Long(0);
		try {
			BigDecimal o = (BigDecimal) getSession().createSQLQuery(sql).list().get(0);
			log.debug("pk: "+o.longValue());
			pknumber=new Long(o.longValue());
			return pknumber;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysTTable merge(SysTTable detachedInstance) {
		log.debug("merging SysTTable instance");
		try {
			SysTTable result = (SysTTable) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysTTable instance) {
		log.debug("attaching dirty SysTTable instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysTTable instance) {
		log.debug("attaching clean SysTTable instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}