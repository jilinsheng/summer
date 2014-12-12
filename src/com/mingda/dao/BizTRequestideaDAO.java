package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTRequestidea;

/**
 	* A data access object (DAO) providing persistence and search support for BizTRequestidea entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTRequestidea
  * @author MyEclipse Persistence Tools 
 */

public class BizTRequestideaDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTRequestideaDAO.class);
	//property constants
	public static final String FLAG = "flag";



    
    public void save(BizTRequestidea transientInstance) {
        log.debug("saving BizTRequestidea instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTRequestidea persistentInstance) {
        log.debug("deleting BizTRequestidea instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTRequestidea findById( java.math.BigDecimal id) {
        log.debug("getting BizTRequestidea instance with id: " + id);
        try {
            BizTRequestidea instance = (BizTRequestidea) getSession()
                    .get("com.mingda.entity.BizTRequestidea", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTRequestidea instance) {
        log.debug("finding BizTRequestidea instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTRequestidea")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding BizTRequestidea instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTRequestidea as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByFlag(Object flag
	) {
		return findByProperty(FLAG, flag
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTRequestidea instances");
		try {
			String queryString = "from BizTRequestidea";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTRequestidea merge(BizTRequestidea detachedInstance) {
        log.debug("merging BizTRequestidea instance");
        try {
            BizTRequestidea result = (BizTRequestidea) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTRequestidea instance) {
        log.debug("attaching dirty BizTRequestidea instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTRequestidea instance) {
        log.debug("attaching clean BizTRequestidea instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}