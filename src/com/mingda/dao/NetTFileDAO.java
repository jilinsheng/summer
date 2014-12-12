package com.mingda.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.NetTFile;

/**
 * A data access object (DAO) providing persistence and search support for
 * NetTFile entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.mingda.entity.NetTFile
 * @author MyEclipse Persistence Tools
 */

public class NetTFileDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(NetTFileDAO.class);
	// property constants
	public static final String FILENAME = "filename";
	public static final String FILETYPE = "filetype";
	public static final String FILEPATH = "filepath";
	public static final String RELTABLE = "reltable";

	public void save(NetTFile transientInstance) {
		log.debug("saving NetTFile instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(NetTFile persistentInstance) {
		log.debug("deleting NetTFile instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public NetTFile findById(java.lang.Long id) {
		log.debug("getting NetTFile instance with id: " + id);
		try {
			NetTFile instance = (NetTFile) getSession().get(
					"com.mingda.entity.NetTFile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(NetTFile instance) {
		log.debug("finding NetTFile instance by example");
		try {
			List results = getSession().createCriteria(
					"com.mingda.entity.NetTFile").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding NetTFile instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from NetTFile as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List<NetTFile> findByProperty(String reltable,String relid ) {
		try {
			String queryString = "from NetTFile as model where model.reltable= ? and model.relid = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, reltable);
			queryObject.setParameter(1, new BigDecimal(relid));
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findByFilename(Object filename) {
		return findByProperty(FILENAME, filename);
	}

	public List findByFiletype(Object filetype) {
		return findByProperty(FILETYPE, filetype);
	}

	public List findByFilepath(Object filepath) {
		return findByProperty(FILEPATH, filepath);
	}

	public List findByReltable(Object reltable) {
		return findByProperty(RELTABLE, reltable);
	}

	public List findAll() {
		log.debug("finding all NetTFile instances");
		try {
			String queryString = "from NetTFile";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public NetTFile merge(NetTFile detachedInstance) {
		log.debug("merging NetTFile instance");
		try {
			NetTFile result = (NetTFile) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(NetTFile instance) {
		log.debug("attaching dirty NetTFile instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(NetTFile instance) {
		log.debug("attaching clean NetTFile instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}