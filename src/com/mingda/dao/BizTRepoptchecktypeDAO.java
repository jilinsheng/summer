package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTRepoptchecktype;

/**
 	* A data access object (DAO) providing persistence and search support for BizTRepoptchecktype entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTRepoptchecktype
  * @author MyEclipse Persistence Tools 
 */

public class BizTRepoptchecktypeDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTRepoptchecktypeDAO.class);
	//property constants



    
    public void save(BizTRepoptchecktype transientInstance) {
        log.debug("saving BizTRepoptchecktype instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTRepoptchecktype persistentInstance) {
        log.debug("deleting BizTRepoptchecktype instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTRepoptchecktype findById( java.math.BigDecimal id) {
        log.debug("getting BizTRepoptchecktype instance with id: " + id);
        try {
            BizTRepoptchecktype instance = (BizTRepoptchecktype) getSession()
                    .get("com.mingda.entity.BizTRepoptchecktype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTRepoptchecktype instance) {
        log.debug("finding BizTRepoptchecktype instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTRepoptchecktype")
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
      log.debug("finding BizTRepoptchecktype instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTRepoptchecktype as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all BizTRepoptchecktype instances");
		try {
			String queryString = "from BizTRepoptchecktype";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTRepoptchecktype merge(BizTRepoptchecktype detachedInstance) {
        log.debug("merging BizTRepoptchecktype instance");
        try {
            BizTRepoptchecktype result = (BizTRepoptchecktype) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTRepoptchecktype instance) {
        log.debug("attaching dirty BizTRepoptchecktype instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTRepoptchecktype instance) {
        log.debug("attaching clean BizTRepoptchecktype instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}