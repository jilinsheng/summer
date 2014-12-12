package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTOptchecktype;

/**
 	* A data access object (DAO) providing persistence and search support for BizTOptchecktype entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTOptchecktype
  * @author MyEclipse Persistence Tools 
 */

public class BizTOptchecktypeDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTOptchecktypeDAO.class);
	//property constants



    
    public void save(BizTOptchecktype transientInstance) {
        log.debug("saving BizTOptchecktype instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTOptchecktype persistentInstance) {
        log.debug("deleting BizTOptchecktype instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTOptchecktype findById( java.math.BigDecimal id) {
        log.debug("getting BizTOptchecktype instance with id: " + id);
        try {
            BizTOptchecktype instance = (BizTOptchecktype) getSession()
                    .get("com.mingda.entity.BizTOptchecktype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTOptchecktype instance) {
        log.debug("finding BizTOptchecktype instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTOptchecktype")
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
      log.debug("finding BizTOptchecktype instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTOptchecktype as model where model." 
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
		log.debug("finding all BizTOptchecktype instances");
		try {
			String queryString = "from BizTOptchecktype";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTOptchecktype merge(BizTOptchecktype detachedInstance) {
        log.debug("merging BizTOptchecktype instance");
        try {
            BizTOptchecktype result = (BizTOptchecktype) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTOptchecktype instance) {
        log.debug("attaching dirty BizTOptchecktype instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTOptchecktype instance) {
        log.debug("attaching clean BizTOptchecktype instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}