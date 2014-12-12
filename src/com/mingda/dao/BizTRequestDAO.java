package com.mingda.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTRequest;

/**
 	* A data access object (DAO) providing persistence and search support for BizTRequest entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTRequest
  * @author MyEclipse Persistence Tools 
 */

public class BizTRequestDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTRequestDAO.class);
	//property constants
	public static final String REQIDEA = "reqidea";
	public static final String REQMAN = "reqman";
	public static final String REQMANPAPERID = "reqmanpaperid";
	public static final String REQMANTEL = "reqmantel";
	public static final String REPFLAG = "repflag";



    
    public void save(BizTRequest transientInstance) {
        log.debug("saving BizTRequest instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTRequest persistentInstance) {
        log.debug("deleting BizTRequest instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTRequest findById( java.math.BigDecimal id) {
        log.debug("getting BizTRequest instance with id: " + id);
        try {
            BizTRequest instance = (BizTRequest) getSession()
                    .get("com.mingda.entity.BizTRequest", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTRequest instance) {
        log.debug("finding BizTRequest instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTRequest")
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
      log.debug("finding BizTRequest instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTRequest as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByReqidea(Object reqidea
	) {
		return findByProperty(REQIDEA, reqidea
		);
	}
	
	public List findByReqman(Object reqman
	) {
		return findByProperty(REQMAN, reqman
		);
	}
	
	public List findByReqmanpaperid(Object reqmanpaperid
	) {
		return findByProperty(REQMANPAPERID, reqmanpaperid
		);
	}
	
	public List findByReqmantel(Object reqmantel
	) {
		return findByProperty(REQMANTEL, reqmantel
		);
	}
	
	public List findByRepflag(Object repflag
	) {
		return findByProperty(REPFLAG, repflag
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTRequest instances");
		try {
			String queryString = "from BizTRequest";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTRequest merge(BizTRequest detachedInstance) {
        log.debug("merging BizTRequest instance");
        try {
            BizTRequest result = (BizTRequest) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTRequest instance) {
        log.debug("attaching dirty BizTRequest instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTRequest instance) {
        log.debug("attaching clean BizTRequest instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}