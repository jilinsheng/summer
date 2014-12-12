package com.mingda.dao;

import com.mingda.common.BaseHibernateDAO;
import com.mingda.entity.BizTCheckflow;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for BizTCheckflow entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.mingda.entity.BizTCheckflow
  * @author MyEclipse Persistence Tools 
 */

public class BizTCheckflowDAO extends BaseHibernateDAO  {
    private static final Log log = LogFactory.getLog(BizTCheckflowDAO.class);
	//property constants
	public static final String ACCDEPT = "accdept";
	public static final String USERACCFLAG = "useraccflag";
	public static final String CHECKFLAG = "checkflag";
	public static final String APPSTATE1 = "appstate1";
	public static final String APPSTATE2 = "appstate2";
	public static final String APPSTATE3 = "appstate3";
	public static final String APPSTATE4 = "appstate4";
	public static final String APPSTATE5 = "appstate5";



    
    public void save(BizTCheckflow transientInstance) {
        log.debug("saving BizTCheckflow instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BizTCheckflow persistentInstance) {
        log.debug("deleting BizTCheckflow instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BizTCheckflow findById( java.math.BigDecimal id) {
        log.debug("getting BizTCheckflow instance with id: " + id);
        try {
            BizTCheckflow instance = (BizTCheckflow) getSession()
                    .get("com.mingda.entity.BizTCheckflow", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BizTCheckflow instance) {
        log.debug("finding BizTCheckflow instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.mingda.entity.BizTCheckflow")
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
      log.debug("finding BizTCheckflow instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BizTCheckflow as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByAccdept(Object accdept
	) {
		return findByProperty(ACCDEPT, accdept
		);
	}
	
	public List findByUseraccflag(Object useraccflag
	) {
		return findByProperty(USERACCFLAG, useraccflag
		);
	}
	
	public List findByCheckflag(Object checkflag
	) {
		return findByProperty(CHECKFLAG, checkflag
		);
	}
	
	public List findByAppstate1(Object appstate1
	) {
		return findByProperty(APPSTATE1, appstate1
		);
	}
	
	public List findByAppstate2(Object appstate2
	) {
		return findByProperty(APPSTATE2, appstate2
		);
	}
	
	public List findByAppstate3(Object appstate3
	) {
		return findByProperty(APPSTATE3, appstate3
		);
	}
	
	public List findByAppstate4(Object appstate4
	) {
		return findByProperty(APPSTATE4, appstate4
		);
	}
	
	public List findByAppstate5(Object appstate5
	) {
		return findByProperty(APPSTATE5, appstate5
		);
	}
	

	public List findAll() {
		log.debug("finding all BizTCheckflow instances");
		try {
			String queryString = "from BizTCheckflow";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BizTCheckflow merge(BizTCheckflow detachedInstance) {
        log.debug("merging BizTCheckflow instance");
        try {
            BizTCheckflow result = (BizTCheckflow) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BizTCheckflow instance) {
        log.debug("attaching dirty BizTCheckflow instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BizTCheckflow instance) {
        log.debug("attaching clean BizTCheckflow instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}