package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTInterview;

/**
 	* A data access object (DAO) providing persistence and search support for BizTInterview entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTInterview
  * @author MyEclipse Persistence Tools 
 */

public class BizTInterviewDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTInterviewDAO.class);
	//property constants
	public static final String PERSON = "person";
	public static final String TYPE = "type";
	public static final String RESULT = "result";



    
    public void save(BizTInterview transientInstance) {
        log.debug("saving BizTInterview instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTInterview persistentInstance) {
        log.debug("deleting BizTInterview instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTInterview findById( java.math.BigDecimal id) {
        log.debug("getting BizTInterview instance with id: " + id);
        try {
            BizTInterview instance = (BizTInterview) getSession()
                    .get("com.mingda.entity.BizTInterview", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTInterview instance) {
        log.debug("finding BizTInterview instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTInterview")
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
      log.debug("finding BizTInterview instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTInterview as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPerson(Object person
	) {
		return findByProperty(PERSON, person
		);
	}
	
	public List findByType(Object type
	) {
		return findByProperty(TYPE, type
		);
	}
	
	public List findByResult(Object result
	) {
		return findByProperty(RESULT, result
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTInterview instances");
		try {
			String queryString = "from BizTInterview";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTInterview merge(BizTInterview detachedInstance) {
        log.debug("merging BizTInterview instance");
        try {
            BizTInterview result = (BizTInterview) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTInterview instance) {
        log.debug("attaching dirty BizTInterview instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTInterview instance) {
        log.debug("attaching clean BizTInterview instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}